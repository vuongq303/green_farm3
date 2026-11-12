# Khôi phục tiến độ bằng SQLite

Green Farm chạy bằng Android Java/MIDlet, với một module `app`. Mở project,
chọn **Android App `app`**, chọn thiết bị rồi **Run**. APK chuẩn nằm ở
`app/build/outputs/apk/debug/app-debug.apk`.

## Lưu trữ và phạm vi dữ liệu

`GameSaveStore` lưu vào database riêng, bền vững của ứng dụng:
`Context.getDatabasePath("greenfarm-progress.db")`. Slot cố định là `device-local`;
không tạo slot/ID ngẫu nhiên mỗi lần mở. Identity/khóa mã hóa gốc được giữ lại.
Database không nằm trong RAM, assets, thư mục tạm hoặc backend MySQL.

`GLLib` chuyển đọc/ghi record đã mã hóa sang SQLite, giữ nguyên serializer gốc:

| Record | Dữ liệu |
| --- | --- |
| `RMSHeader` và record phụ | Phiên bản, ngôn ngữ, âm thanh, cài đặt, thời gian và trạng thái phụ gốc. |
| `save_gameplay` | Tiền/XP/cấp độ, kho, nhiệm vụ/mục tiêu/thưởng, tutorial, hàng đợi, mở khóa và các mảng trạng thái gốc. |
| `save_farm0` | Khu vực, cây/đất, động vật, công trình, vị trí, trạng thái chăm sóc/sản xuất và timestamp tuyệt đối. |
| `pending_rewards_v1` | Thưởng đang bay chưa được cộng; phục hồi hiệu ứng rồi dùng logic trao thưởng gốc, không tự cộng lại mỗi lần tải. |
| `entity_pose_v1` | Transform sprite `aaN` mà codec farm gốc bỏ sót; không nhầm với thời gian animation `aaR`. |

Đây là bảng record BLOB có checksum SHA-256, không phải schema mới tự diễn giải
mọi trường obfuscate thành hàng cây/động vật. Cách này giữ dữ liệu chưa giải nghĩa
và mốc thời gian sản xuất của game. Pose dùng khóa theo codec vị trí, loại và
thứ tự xuất hiện; xử lý sentinel/đối tượng trùng vị trí bằng thứ tự cấp phát gốc.

MySQL backend tiếp tục lưu tài khoản anonymous, session và compliance. Lưu farm
cục bộ không phụ thuộc backend; chưa triển khai cloud farm save hoặc Facebook
Connect mới khi chưa có bằng chứng giao thức.

## Khởi tạo, migration và tải an toàn

`SQLiteOpenHelper` tự mở/tạo database và nâng `user_version` lên **4**. SQL thực tế:

1. [001_game_progress.sql](sqlite/001_game_progress.sql): `save_slots`,
   `save_records`, `save_commits`, `legacy_imports`, khóa/ràng buộc/index.
2. [002_complete_snapshot.sql](sqlite/002_complete_snapshot.sql): đánh dấu snapshot đầy đủ.
3. [003_pending_rewards.sql](sqlite/003_pending_rewards.sql): phiên bản snapshot có hàng đợi thưởng.
4. [004_entity_pose.sql](sqlite/004_entity_pose.sql): đánh dấu snapshot có pose.

Migration dùng `ALTER TABLE`, giữ record cũ; không chỉ gọi `CREATE TABLE IF NOT
EXISTS` rồi coi bảng cũ đã nâng cấp. Khởi tạo lặp không reset slot hoặc seed trùng.

Luồng thật: mở SQLite/migration → nhập RMS cũ có CRC hợp lệ → chặn ghi lúc tải
→ đọc global/farm → phục hồi pose và thưởng chờ → mở khóa gameplay/autosave.
Import RMS chỉ đọc file gốc, ghi dấu đã nhập và tombstone để dữ liệu đã xóa không
sống lại. Không có fallback ghi RMS khi SQLite lỗi.

Lỗi đọc, identity không khớp, checksum sai hoặc record bắt buộc bị thiếu không
được coi là người chơi mới. Loader dừng ở bước lỗi, giữ bản lưu và hiện **Thử lại**.
Database hỏng không bị tự xóa/tạo lại để che lỗi. Chỉ tạo farm mặc định sau khi
xác nhận slot chưa có bản lưu gameplay.

## Snapshot, autosave và thông báo

`ProgressSaveController` điều phối tại ranh giới frame/hành động đã hoàn tất dưới
khóa `ci.class`, thay việc thread `cq` vừa lưu vừa để game thay đổi dữ liệu.
Các yêu cầu được gộp; giới hạn lần thử khoảng 500 ms, checkpoint thời gian 8 giây,
không ghi toàn bộ database mỗi frame. Tutorial/cấp 1 được lưu sau khi tải xong,
thay các chặn gốc chỉ cho lưu sau tutorial/cấp 3.

Một transaction bao gồm header, gameplay, farm, pending reward và pose cùng trạng
thái phụ. Ghi lỗi dù bị serializer gốc bắt exception vẫn đánh dấu transaction lỗi,
rollback toàn bộ; generation tăng sau commit, không để snapshot cũ ghi đè mới.
Khi thăm farm khác, lưu global/thưởng chờ và giữ farm/pose chủ cũ. State machine
chặn unload nếu checkpoint chưa commit; không để loader đọc lại snapshot cũ
trước khi RAM đã được lưu. Callback lên cấp vẫn được mở đúng màn hình. Retry ở
bước dựng farm dọn riêng các actor dựng dở, không nhân đôi entity/lệnh chờ.

Overlay hiện **Chờ lưu…**, **Đang lưu…**, **Đã lưu**, hoặc
**Lưu thất bại · Chạm để thử lại**. Chỉ hiện đã lưu sau commit thành công.
Lỗi giữ dirty state, có thử lại tự động và thao tác chạm; mở lại storage không nạp
state mặc định đè lên RAM đang chơi. Pause/ẩn/thoát drain hành động, hủy preview
chưa xác nhận rồi flush (gồm cả thưởng được xử lý sau hide callback); lỗi lưu khi thoát được báo để thử lại.

## Nhiệm vụ và cảm ứng

Mode đặt mới/di chuyển/đặt từ kho (0/3/4) chỉ commit bằng **✓** hợp lệ; chạm bản đồ
không tự xác nhận. **✕** hiện cả trong tutorial, trả vị trí/transform cũ hoặc trả
vật đang đặt về kho; không tính tiến độ di chuyển. Nút dùng artwork gốc, target
tối thiểu 48 px, được kẹp trong viewport và tách nhau; GUI capture chặn chạm xuyên.
Mode mua lặp 1 giữ luồng gốc; trồng hạt trên đất đã có không đổi cơ chế.
Android cancel/mất focus và thay đổi kích thước thật xếp rollback vào đầu frame
game; xóa GUI capture/pointer đang chờ để không phát sinh ✓ giả. Callback resize
đầu tiên hoặc cùng kích thước giữ nguyên preview và cập nhật viewport gốc.

Hủy tutorial đặt lợn đầu quay về hướng dẫn chợ (15→13); hủy đặt từ kho mở lại
chọn kho (53→51). Hoàn thành hai bước này đòi xác nhận thành công, không chỉ thấy
`avE == null`. Di chuyển một trục hợp lệ được tính; kéo thử/hủy/không đổi chỗ không tính.

Quest thường giữ điều kiện gốc: mở/theo dõi là 1→2; hoàn thành là 3. Đối chiếu
31.000 trường hợp matcher/progress/completion với bytecode gốc đạt; 18 ca mở lại
quest không đổi counter hoặc nhận thưởng. Chưa tái hiện quest cụ thể người dùng
báo tự hoàn thành; không thay điều kiện bằng suy đoán. Xem
[bằng chứng movement/quest](MOVE_QUEST_RECOVERY_EVIDENCE.md).

## Kết quả và giới hạn kiểm thử

Baseline APK cũ trên emulator Android 9/API28, user 0: thu hoạch thật đạt XP
**1/13**, `save_gameplay` không có record; force-stop/mở lại về **0/13** và tutorial
Welcome. Đã tái hiện mất tiến độ; dữ liệu trước thử được backup, không uninstall/reset.

Bản cuối cùng source checkpoint `d27a6ca` đã build/cài qua task chuẩn, trên
emulator API28 (user 0, không xóa data): DB tự nâng **3→4**, giữ snapshot trước đó,
khôi phục tutorial/farm mà không chạy lại letter/Welcome. Đã hiện **“Đã lưu”**,
ghi `entity_pose_v1`, thu hoạch qua “New Task Available”, rồi force-stop/mở lại
khôi phục các ô đã thu hoạch và tutorial. Không có crash trong các PID game đã
kiểm tra. Chưa đọc được HUD XP ở tutorial, nên không kết luận giá trị XP cụ thể.

Ảnh: [baseline trước](sqlite-validation/screenshots/baseline-xp-before.png),
[baseline sau restart](sqlite-validation/screenshots/baseline-xp-after.png),
[SQLite báo đã lưu](sqlite-validation/screenshots/sqlite-saved.png),
[SQLite khôi phục sau restart](sqlite-validation/screenshots/sqlite-restored.png).
APK thử nhúng IPv4 cloud; bản giao nhúng IPv4 Windows trong `gradle.properties`,
khác URL backend. Windows LAN và chính APK nhúng IP Windows chưa chạy từ cloud.

116/116 test Python và 13/13 test resource đạt. 26/26 kiểm tra SQLite Android đạt, gồm WAL và FULL trên writer thật.
Log ở
[sqlite-validation](sqlite-validation/README.md). Probe dùng SQLite Android thật
với fixture riêng; [kết quả probe](sqlite-validation/probe-results.json) và
[kết quả chạy game](sqlite-validation/game-runtime.json) lưu riêng; kết quả lỗi/kill được ghi riêng với kiểm thử UI game.

| Ca bắt buộc | Đã kiểm tra | Thiết bị/bản cuối |
| --- | --- | --- |
| 1. DB mới tự tạo schema/index/slot | Prototype tạo DB thật; probe SQLite đã viết. | DB thật đã nâng 3→4; 26 kiểm tra SQLite Android đã đạt (gồm DB mới). |
| 2. Mở lại nhiều lần không reset/seed trùng | Probe import/idempotency và load guard đạt; slot ổn định. | Modern snapshot đã khôi phục sau force-stop; giữ slot/generation. |
| 3. Tiền/kho/cây/động vật/công trình/quest sau cold restart | Codec audit; pose/thưởng chờ có helper regression. | Farm/thu hoạch/tutorial đã qua cold restart; XP số, kho/động vật/công trình/claim chưa đủ ma trận. |
| 4. Restart backend không mất farm | Farm dùng SQLite riêng; backend MySQL không đổi. | Chưa chạy ca restart backend. |
| 5. Chỉ mở quest chưa làm mục tiêu | 18 ca chạy method gốc và recovered đạt. | Bản cuối mở drawer “New!”; bấm entry/nhãn chưa hiện detail/Claim. Chưa tái hiện quest cụ thể. |
| 6. Thiếu/đủ mục tiêu | 31.000 đối chiếu bytecode matcher/progress/completion đạt. | Chưa đi hết quest thực tế. |
| 7. Bấm nhận thưởng lặp/mở lại | Pending helper: snapshot, trao một lần, retry, rương/item callback đạt. | Chưa claim lặp qua UI thật. |
| 8. Kéo rồi ✕, mở lại vị trí/hướng cũ | Handler thật + accessor/flags thật đạt; lifecycle rollback có tích hợp. | Chưa chạy cold restart sau ✕. |
| 9. ✓ hợp lệ, lưu rồi mở lại vị trí mới | Handler guard, một trục và duplicate release đạt; pose codec đạt. | Chưa xác minh toàn luồng. |
| 10. Nút tại mép/màn hình nhỏ | 14.300 layout 128×240…800×480 không chồng/ngoài viewport. | Chưa đủ màn hình/cảm ứng thật. |
| 11. Lỗi đọc/ghi không reset/báo thành công sai | Helper reward/pose dữ liệu lỗi/retry/không mutate một phần đạt; probe có ca SQL lỗi. | Probe SQL RAISE(ABORT), checksum sai, thiếu bảng/pose và file DB hỏng đều đạt; giữ dữ liệu cũ, retry ghi thành công. |
| 12. Kill giữa transaction | Probe có `crash-uncommitted`/`crash-committed`; SQLite transaction có triển khai. | Halt 17 trước commit giữ generation cũ; halt 18 sau commit giữ đủ 5 record mới, trên Android thật. |

Focused tests: `test_ci_quest_progress.py`, `test_ci_move_controls.py`,
`test_pending_reward_state.py`, `test_entity_pose_state.py`. Đây là fixture chạy
method/helper thật với peer cô lập, không thay thế APK/emulator gameplay test.
`tools/GameSaveStoreProbe.java` dùng Android SQLite thật trong fixture riêng dưới
files của app; không thao tác bản lưu người dùng. Log/ảnh thử nghiệm riêng nằm ở
`.local/sqlite-runtime/`; không commit backup, database hoặc identity người chơi.
