# Chuyển sang offline và sửa khôi phục tiến trình

Báo cáo ngày 14/11/2026. Những tài liệu trong `docs/backend/` và báo cáo checkpoint
cũ mô tả lịch sử; không phải hướng dẫn vận hành phiên bản này.

## Nguyên nhân đã xác định

* SQLite có thể chứa snapshot đầy đủ, checksum hợp lệ nhưng `FarmGameEngine.ax`
  vẫn trả về false: prefix định danh trong payload được so với HDIDFV do SDK
  online sinh lại. Trên Android API28 đã gặp lỗi bước44 với database integrity
  `ok`, generation 15 và11 record. Bộ nạp nay giữ prefix cũ như metadata opaque,
  kiểm tra chặt chiều dài, đọc SQLite mà không xác thực qua SDK. Không tạo farm
  mới để che lỗi đọc. Format4 và dữ liệu cũ được giữ.
* Launcher đặt `MIDlet.cnR=false`, bỏ qua callback đóng của MIDlet; đồng thời
  `Start.onDestroy` có thể kết thúc process khi Activity launcher bị hủy.
  Nay bật callback, gửi checkpoint trước pause, flush trước dọn world, và chỉ
  dọn một lần cho đúng engine sở hữu world. Lỗi flush giữ world và bản ghi chờ.
* Loader asset phụ thuộc đường dẫn/prefix wrapper có vòng đời riêng. Nay mở
  trực tiếp `assets/800x480/` bằng application AssetManager. Parser pack chia
  đoạn trước đây giữ stream/offset lỗi khi retry; nay đóng, vô hiệu cache lỗi,
  kiểm tra EOF và cho phép offset hợp lệ đi qua file split tiếp theo.
* Bỏ cấu hình Firebase nhưng giữ social SDK bootstrap làm ứng dụng chết ngay
  tại `FacebookSdk.sdkInitialize`; đã vô hiệu cả bootstrap và các cầu nối SDK.
  Frame game cũ còn gọi CRM/tracking manager đã bỏ, gây thoát sau khi vào world;
  đã gỡ nhánh CRM cùng các chờ phản hồi online. Lỗi frame/resource còn lại được
  log đầy đủ, không ẩn thông báo để coi là sửa xong.

## Lưu dữ liệu

Không thay schema 4 và không xóa database. Đường dẫn do Android
`Context.getDatabasePath("greenfarm-progress.db")` cấp. SQLite là nguồn bền vững;
world RAM được chụp trên game thread thành snapshot bất biến rồi chuyển cho
writer riêng. Cơ chế sẵn có được giữ và nối lại đúng vòng đời: debounce 500 ms,
giới hạn 2 giây/50 thay đổi, hàng đợi tuần tự, retry, transaction snapshot,
checksum/generation, WAL và `synchronous=FULL`. Header/gameplay/farm/reward/pose
phải đầy đủ trước khi công bố snapshot hoàn chỉnh. Lỗi đọc chặn khởi tạo farm
mới; corruption handler không tự xóa file.

Khởi động: mở SQLite/migration → nạp global → nạp farm → hoàn tất loading →
cho phép ghi/gameplay. Thoát bình thường và đổi ngôn ngữ đợi checkpoint durable
trước teardown/restart. Force-stop có thể mất RAM chưa commit; debounce có giới
hạn chờ, không phụ thuộc duy nhất callback thoát. Không hứa zero-loss sau kill
đột ngột trước commit.

Database trên giả lập đã được sao lưu cùng WAL/SHM trước thử sửa bộ nạp, không
reset/clear data/uninstall. Không có migration schema mới trong thay đổi này.
Chức năng xuất/nhập save cục bộ vẫn giữ. Backend cũ trong repository chỉ lưu
auth/session/compliance, không có API lưu farm; không truy cập hay xóa dữ liệu
Firebase từ xa. Nếu người dùng có dữ liệu riêng ngoài repository thì chưa thể
xác minh/chuyển dữ liệu đó tự động.

## Những thành phần loại bỏ

Xóa source Firebase và Google measurement, service FCM, Google services JSON,
Firebase properties/resource IDs, metadata chữ ký APK gốc không dùng trong source,
manifest provider/service/receiver, khai báo
Facebook/IAP/push/referrer và quyền Internet/network/WiFi/billing. Không thêm
backend thay thế. Xóa `backend/`, cấu hình endpoint và công cụ chèn URL.

`s`, `am`, `as`, `cj` giữ ABI tối thiểu để wrapper cũ liên kết được, trả trạng
thái không khả dụng, không gửi HTTP/tạo hàng đợi mạng. Build loại Firebase DEX
và vô hiệu social/IAP/ads/tracking trong runtime gốc chưa có source đầy đủ.
`tools/RemoveOnlineServices.java` kiểm tra không còn tham chiếu Firebase thực thi
và bytecode gameplay/storage không bị bước loại SDK sửa ngoài phạm vi cho phép.

APK gốc checksum-pinned vẫn là đầu vào phục hồi bytecode, có SDK lịch sử;
không phân phối archive đó. Một số source SDK cũ không được biên dịch và tài
liệu lịch sử vẫn chứa từ Firebase/URL. Không đánh đồng tìm chuỗi với hành vi
mạng: APK cuối không có quyền Internet, class Firebase hay component online.

`tools/package_game.py` kiểm tra policy offline, provenance, metadata revision,
chữ ký/alignment; đối chiếu từng asset/native payload nguyên byte với bản gốc.
Không tạo variant build APK offline phục vụ test; vẫn dùng `:app:assembleDebug`.

## Hộp thoại ngôn ngữ

Giữ quy trình checkpoint ngôn ngữ hiện có: văn bản theo ngôn ngữ vừa chọn,
chỉ công bố lựa chọn sau khi checkpoint commit, restart một lần. Layout mới
ẩn riêng widget giá/đồng tiền của hộp thoại này, chỉ có nút Khởi động lại nằm
giữa hai vị trí nút gốc; phục hồi layout mặc định trước hộp thoại kế tiếp.
Không đổi icon tiền của shop/gameplay. Test gọi mã layout thực và kiểm tra cả
single-action và hộp thoại hai nút kế tiếp.

## File chính

* `app/src/main/java/com/quanhoang/greenfarm3/S800x480/FarmGameEngine.java`,
  `GameRuntime.java`, `s.java`, `am.java`.
* `app/src/main/kotlin/com/quanhoang/greenfarm3/Start.kt`,
  `S800x480/GloftGF2F.kt`, `as.kt`, `cj.kt`,
  `migration/SaveTransferIdentity.kt`.
* `app/src/main/AndroidManifest.xml`, resource Firebase bị xóa,
  `app/build.gradle`, `gradle.properties`, `buildSrc/src/main/groovy/greenfarm/MergeOriginalSdkApk.groovy`.
* `tools/RemoveOnlineServices.java`, `package_game.py`, `MergeCoreDex.java`,
  `prepare_core_dependencies.py`, `restore_resource_classes.py`,
  `verify_runtime_apk.py`; kiểm thử trong `tests/test_*offline*`,
  `test_local_asset_open.py`, `test_local_save_identity.py`,
  `test_segmented_resource_reopen.py` và các fixture hồi quy liên quan.
* `backend/`, source Firebase/measurement, công cụ endpoint và kiểm thử chỉ
  dành cho transport online đã bị xóa. `git diff --name-status` là danh sách
  đầy đủ các file thay đổi, gồm hàng trăm file SDK.

## Kiểm thử và giới hạn

Kết quả cuối và bằng chứng thực thi được ghi trong `offline-validation/`.

* `:app:assembleDebug --no-daemon`: thành công2 phút51 giây. APK SHA256
  `cc3609fc3170ed964829edbbe6fb9ed8e47be50f6e394d8f1d0c2893969c0776`.
  Provenance xác nhận59 asset/native nguyên byte,102 lớp Firebase bị loại,
  1376 method SDK online bị vô hiệu,0 tham chiếu Firebase thực thi còn lại.
* Host suite:153 test,152đạt,1skip do bài relocation cũ hardcode dependency
  `/workspace/tooling/dexmerge`;20 test tools đều đạt. Test mới kiểm resource
  reopen/split/EOF/path, transport ABI không tạo worker, binding local không
  phụ thuộc SDK, barrier flush/teardown và layout single-action.
* Native SQLite API28:27/27 case đạt trên APK trước các sửa cuối ở SDK/loader
  (GameSaveStore không đổi). Có crash trước/sau commit, reopen, schema1/3,
  import legacy, corrupt/missing record, write failure và100 snapshot async.
  Xem `sqlite-earlier-apk.json` với hash chính xác APK đã thử.
  Lượt chạy lại `sqlite-verified.json` cũng đạt27/27 trên APK có toàn bộ sửa
  gameplay/lifecycle/loader cuối (hash `be44d2ef…`). APK phân phối sau đó chỉ
  dọn OAuth/crash/transport resource không dùng; không đổi GameSaveStore.
  Các test policy/config và5 test resource-ID đã chạy lại và đạt.
* Một lượt đầu trên APK `be44d2ef…` đã qua9 case rồi timeout ởcase10 khi giả lập bị
  lỗi framebuffer/SystemUI ANR; `sqlite-final-apk-timeout.json` ghi rõ
  `passed=false`. Lượt chạy lại27 case sau đó đã đạt; không tính lượt timeout là đạt.
* Database người chơi thực không reset: bản sao trước sửa integrity=ok,
  generation 15/complete 15; sau sửa và một lần thoát, integrity=ok,
  generation 21/complete 21,11 record, giữ nguyên prefix codec, tutorial1→2.
  Giải mã các trường wallet theo codec thực: level 1,XP 0,2000 xu,50 cash không
  thay đổi giữa hai bản sao database.
  `player-database-reopen.json` lưu kết quả không chứa payload người chơi.
  Chế độ máy bay được bật trên giả lập, quyền Internet đã bị loại khỏi APK.
  Lần cold reopen kế tiếp đã vào cảnh farm/tutorial; ảnh
  `screenshots/reopened-farm-wallet.png` xác nhận2000 xu/50 cash/level 1 trên UI,
  `screenshots/reopened-tutorial.png` ghi nhận nhân vật/cảnh đã tải đầy đủ.
  Các ảnh này và probe native dùng APK `be44d2ef…`, trước lượt dọn resource
  OAuth/transport cuối; không đổi mã gameplay/lifecycle/asset loader sau đó.
* APK phân phối `cc3609fc…` đã được cài cập nhật thành công, không reset data,
  rồi cold-start trong chế độ máy bay (`airplane_mode_on=1`). Ảnh
  `screenshots/distribution-cold-reopen.png` xác nhận farm và wallet
  level 1/XP 0/2000 xu/50 cash; `distribution-runtime.log` ghi capture và
  transaction write `ok=true`, không có lỗi load/frame trong lượt quan sát.

Test storage dùng database fixture riêng; không thay database người chơi bằng
fixture. 100 snapshot synthetic không đồng nghĩa đã trồng/thu hoạch100 ô trong
game. Chưa xác nhận đầy đủ100 ô, sản phẩm nhiều vật nuôi, mọi quest, âm thanh
nghe thực, FPS/độ mượt trên thiết bị thật và các phiên bản Android khác. Kiểm
tra đổi ngôn ngữ/restart đầy đủ trên APK phân phối còn cần xác minh;
layout/save barrier đã được kiểm bằng code thực trong test. Các thao tác UI
trên giả lập không KVM thường timeout hoặc bỏ lỡ tap trong lúc render chậm;
không coi đó là kiểm thử thao tác gameplay đạt.
Không suy từ build/test fixture rằng mọi thao tác gameplay đều đã đạt.

Môi trường cloud đã bỏ bước cài backend/npm/MySQL khỏi setup và lưu draft
onboarding offline. Draft chưa publish. Giả lập AOSP API28 x86 dùng software
emulation không KVM; không dùng tốc độ giả lập làm số liệu hiệu suất sản phẩm.
