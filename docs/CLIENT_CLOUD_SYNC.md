# Client lưu tiến độ lên backend SQLite

Ứng dụng chính vẫn là module `app`, application ID và namespace
`com.quanhoang.greenfarm3`. URL duy nhất là `BACKEND_BASE_URL` ở root
`gradle.properties`; Gradle cung cấp cùng URL qua metadata
`greenfarm.backendBaseUrl` cho API mới. Các endpoint form/discovery cũ phục vụ
wrapper được giữ riêng; credential anonymous cũ không cấp quyền đọc/ghi save mới.

## Định danh và mở game

`BackendCloudController` tạo một installation secret ngẫu nhiên 256 bit và
commit vào SharedPreferences riêng **trước** request đầu tiên. Backend cấp
player UUID và access token; việc cấp lại dùng cùng proof, cùng player UUID.
Không suy ra quyền sở hữu từ device ID, Facebook ID hoặc player ID do client tự
chọn. Nếu mất proof khi database đã có owner/pending work, client dừng và giữ dữ
liệu, không tạo identity mới đè lên dữ liệu cũ.

`Start` chạy reconciliation ngoài UI thread trước khi mở MIDlet. Backend mất
kết nối, trả sai định dạng hoặc không xác nhận save không được coi như hồ sơ
trống. Chỉ `404 NO_SAVE` xác thực mới cho phép khởi tạo nông trại mới. Handoff
dùng `Activity.recreate()` vì launcher là `singleTop`; phiên gate không gọi
cleanup/exit của phiên game.

Nếu có local farm mà server xác nhận guest chưa có save, người dùng phải chấp
thuận **sao chép** dữ liệu. Database cũ được backup nhất quán trước migration;
bản `.gf3save` trước adoption cũng được giữ trong thư mục riêng của ứng dụng.
Đây là copy dữ liệu cục bộ vào guest đã xác thực, không phải xác nhận hoặc liên
kết tài khoản Facebook cũ. Backend còn yêu cầu operator phê duyệt đúng UUID và
hash bản sao bằng CLI; dialog lỗi cung cấp lệnh để sao chép, không cung cấp token.
Sau phê duyệt, nhấn Thử lại dùng chính UUID thao tác và snapshot đã chờ.

## Snapshot, pending và xác nhận

`GameSaveStore` nâng database cục bộ từ schema 4 lên 5 bằng migration cộng thêm
`cloud_state` và `cloud_outbox`; các record gameplay giữ nguyên định dạng.
`CloudMigrationBackup` ghi bản logical backup có checksum, các kiểu SQLite và
metadata trong cùng read/write transaction trước khi thay đổi schema. Không copy
riêng file SQLite khi WAL đang hoạt động và không xóa dữ liệu hỏng.

Payload cloud dùng codec đã có `GF3SAVE1`, logical schema 4: tên record, byte gốc,
tombstone, checksum và metadata của toàn slot. Nó giữ tiền/cash, XP/level, kho,
cây/công trình/map/thời gian, quest/tutorial, pending reward và hướng entity theo
serializer hiện có. Không dựng lại những trường chưa hiểu bằng JSON giả.
Các bảng cloud, proof và token không nằm trong archive. Snapshot có package test
hoặc record `offline_test_balances_v1` bị từ chối.

Ngay trong transaction ghi local farm/header/settings, một archive hoàn chỉnh
được đưa vào outbox. Một `head` bất biến chứa operation UUID, expected server
revision và hash; các thay đổi tiếp theo gộp thành một `tail`. Chỉ response commit
khớp UUID/hash/revision mới xóa head và nâng tail. Retry gửi lại nguyên snapshot,
không phát thưởng thêm và không dùng thời gian thiết bị để chọn save thắng.
Tranh chấp giữ local pending cùng bản server để đối chiếu, không tự merge mảng
opaque hoặc tự ghi đè bằng một save rỗng.

SDK cũ có thể đổi HDIDFV giữa hai process. Sau GET đã xác thực, client chỉ ghi
metadata đối chiếu binding khi owner/revision/receipt hiện hành khớp, không có
pending/conflict và **toàn bộ archive local** có đúng SHA-256 của bản server.
Metadata dùng binding và checksum của chính payload đã được xác minh; các byte
gameplay, danh tính SDK và tài khoản giữ nguyên. Cache chỉ bị invalidate sau
transaction commit. Cơ chế này cũng sửa bản đã lưu hợp lệ nhưng thiếu metadata,
không tự chấp nhận một file local tùy ý hoặc tạo farm mới để vượt lỗi đọc.
[Kiểm thử SQLite của binding](backend-restoration-validation/client-host/binding-pin-result.json).

Với hồ sơ mới, backend trả config khởi tạo đã chốt cho đúng player.
`NewPlayerDefaults` áp dụng bằng setter bảo vệ gốc, ghi marker config hash trong
**cùng snapshot đầu tiên**. Snapshot này được capture trước tutorial `0→1`.
Trong lúc chờ commit SQLite đầu tiên, input/capture mới bị giữ; callback SDK được
buffer riêng, nên chúng không gộp và làm đổi snapshot khởi tạo đang ghi.
Sau commit, mọi thay đổi gameplay tiếp tục được lưu bình thường. Save đang có
không được áp lại config hoặc reset tiến trình.

## Lifecycle và giới hạn

Batching và autosave hiện có tiếp tục capture tại ranh giới game frame; một
writer riêng ghi SQLite và một worker riêng gọi HTTP. Không chờ mạng trên UI
thread và không thêm banner lưu ở đầu màn hình. Lưu language/header riêng cũng
đưa toàn bộ archive mới vào outbox, tránh server giữ ngôn ngữ cũ sau restart.

Quit menu settle các reward đã kiếm/cancel preview và lưu clock **trước** final
capture. Continuation cleanup chỉ chạy trên game thread sau local durability
và một đợt đồng bộ mạng có deadline; nếu không có ACK, outbox vẫn tồn tại khi
thoát. Home/pause/scene transition dùng checkpoint local hiện có và đánh thức
worker cloud, không chỉ dựa vào `onDestroy`.

OS kill có thể xảy ra trước lần capture/commit mới nhất hoặc trước ACK mạng.
Chỉ snapshot đã commit local mới có thể retry sau kill; chỉ receipt backend
được commit mới chứng minh đã lưu trên server. Client không thông báo thành
công cloud chỉ vì queue local đã ghi xong.

Manual SAF import vào database đã liên kết cloud hoặc có pending work bị chặn
để tránh ghi đè profile server âm thầm. Export và cloud restore có kiểm soát vẫn
giữ codec/identity đã kiểm chứng. Luồng tự giải quyết mọi conflict hoặc nhập
archive tùy ý vào một profile cloud đang dùng chưa được triển khai.

Bằng chứng host ở
[client-host](backend-restoration-validation/client-host/index.json) và
[migration-host](cloud-save-validation/migration-host/index.json).
Kết quả Android/HTTP/SQLite thật được ghi riêng; host test hoặc build thành công
không chứng minh mọi thao tác game đã được chạy trên thiết bị.
