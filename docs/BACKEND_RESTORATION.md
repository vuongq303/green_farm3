# Backend, tài khoản mới và bản game chính

## Căn cứ Git và phạm vi

Repository chính là `vuongq303/green_farm3`, module Android duy nhất `app`, nhánh
làm việc `work`. Bản Offline Test được thêm trực tiếp vào repository này trong
`1c33b14` và phát hành tại `fdb30d1`, với application ID riêng
`com.quanhoang.greenfarm3.offlinetest`; không phải một project độc lập.

Mốc đối chiếu trước Offline Test là `2b9f0d9`. Checkpoint `a86db0f` khôi phục
chọn lọc các file bị thay đổi để làm test, gỡ helper cấp tiền/level, cờ Gradle
`greenfarmOfflineTest` và APK test. Các thay đổi Kotlin, package
`com.quanhoang.greenfarm3`, codec, queue lưu, migration và sửa lỗi trước đó được
giữ. Không dùng reset/clean Git và không xóa database hoặc save trên thiết bị.
Tài liệu kiểm thử offline cũ chỉ còn là bằng chứng lịch sử, không phải cấu hình
hay chế độ có thể build trong bản hiện tại.

Yêu cầu cuối cùng trong file người dùng cung cấp gồm backend SQLite, cấu hình
khởi tạo tập trung và gỡ Facebook/Google Ads. Vì vậy phần dưới mô tả những thay
đổi mới sau checkpoint khôi phục; không coi chúng là mã giao thức Gameloft đã
được phục hồi.

## Cấu hình tài khoản mới

[new_player_defaults.json](../backend/config/new_player_defaults.json) là nguồn
cấu hình backend. Giá trị gốc đã được xác minh là 2.000 xu, 50 cash, level 1,
XP 0, tutorial 0. Ngôn ngữ mặc định là `auto`, giữ cách chọn theo thiết bị.
1.900 xu là số dư sau khi đã chơi, không phải số tiền khởi tạo.

Bằng chứng gồm `FarmGameEngine.aY/bc`, bảng `/13` số 20, bảng XP và phép đọc
JDI chỉ đọc từ APK đối chiếu:
[original-balance-tables.json](backend-restoration-validation/original-balance-tables.json).

Backend kiểm tra cấu hình khi khởi động và đọc lại khi tạo một người chơi mới.
Mỗi người chơi nhận một bản sao cấu hình cùng hash được lưu trong transaction
tạo hồ sơ. Sửa file ảnh hưởng hồ sơ tạo sau đó; hồ sơ cũ và bản lưu cũ không bị
đặt lại. Không gửi tiền/level khởi tạo từ client trong request tạo tài khoản.

XP là XP trong level hiện tại, không phải tổng XP tích lũy. Level hỗ trợ từ 1
đến 50, XP phải dưới ngưỡng level gốc; tại level 50 chỉ hỗ trợ seed XP 0. Các
giới hạn tiền/cash và ngôn ngữ được kiểm tra theo catalog đã xác minh. Tutorial
khởi tạo hiện chỉ hỗ trợ bước 0; không giả lập các điều kiện của bước về sau.

`initialWorldTemplate` tham chiếu mẫu `original-4.4.2`: đồ vật, công trình,
cây trồng và bố trí ban đầu vẫn do các record 3/4 của asset `/14` gốc cung cấp.
Không tự tạo danh sách vật phẩm dựa trên suy đoán. Hiện chưa hỗ trợ template
khác hoặc danh sách item tùy ý; cấu hình không hỗ trợ bị từ chối rõ ràng. Các
giới hạn ban đầu đã xác minh được đặt trong `initialCapacityLimits`.

Client chỉ áp dụng cấu hình ở hồ sơ mới được backend xác nhận chưa có save,
không có farm cục bộ và không có bản chờ. Marker khởi tạo và snapshot đầu được
commit cùng nhau. Bản sao tiến trình cục bộ đã có dùng luồng chấp thuận riêng,
không nhận lại tài nguyên khởi tạo.

## Định danh và lưu tiến trình

Backend JavaScript dùng `node:sqlite` của Node 24. Database mặc định là
`backend/data/green-farm.sqlite`; đường dẫn tương đối được tính từ `backend/`,
không phụ thuộc thư mục gọi lệnh. Không dùng database RAM hoặc tự tạo database
rỗng khi server khởi động. Xem [cách chạy và migration](../backend/README.md).

| API mới | Trách nhiệm |
| --- | --- |
| `POST /api/v1/guest/sessions` | Chứng minh secret cài đặt, nhận phiên riêng do server cấp và cấu hình đã gán cho hồ sơ. |
| `GET /api/v1/save` | Đọc đúng snapshot của người chơi được xác thực; chỉ `NO_SAVE` đã xác minh mới có nghĩa chưa có bản lưu. |
| `PUT /api/v1/save` | Ghi archive đầy đủ bằng revision CAS và mã lần lưu; trả thành công sau commit. |
| `POST /api/v1/save/adoption` | Cho phép sao chép bản lưu cục bộ đã được người dùng chấp thuận vào chính hồ sơ khách mới, ràng buộc token một lần với snapshot và operation. |

Phiên cloud mới độc lập với giao thức anonymous legacy phục vụ startup cũ.
Backend không dùng `playerId` tùy ý trong request để quyết định chủ save.
Không gán tài khoản Facebook cũ cho guest; migration MySQL giữ các ID/hash/phiên
và dữ liệu liên kết cũ. Chấp thuận sao chép local không phải xác minh chủ sở hữu
tài khoản Facebook hoặc liên kết với tài khoản đó.

Luồng copy cũ cần quản trị backend phê duyệt đúng người chơi và hash snapshot
bằng `scripts/approve-local-adoption.js`; xác nhận trên client không đủ. Đây là
để đáp ứng yêu cầu không cho client dùng đường import để tự chọn tiền/level
khởi tạo. Ứng dụng cung cấp lệnh phê duyệt trong lỗi migration có thể xử lý, giữ
nguyên snapshot chờ và cho thử lại. Tài khoản mới không có dữ liệu local không
cần bước này. Quyền copy không cho đọc dữ liệu của người chơi khác.

Snapshot dùng codec `GF3SAVE1` đang có, chứa nguyên record game, tombstone,
checksum, pending reward và entity pose. Tiền/kho/cây/nhiệm vụ không bị tách thành
các request độc lập. Backend kiểm tra archive và snapshot khởi tạo; các lần lưu
gameplay về sau vẫn do client tạo, chưa phải hệ thống kinh tế server-authoritative.

Database cục bộ nâng schema 4 lên 5 bằng thay đổi bổ sung, có backup logical
nhất quán trước DDL. Outbox được ghi trong cùng transaction với tiến trình.
Một operation đang gửi giữ nguyên bytes/ID; các thay đổi mới được gộp ở tail.
Chỉ receipt đúng ID/hash/revision mới được xác nhận. Xung đột giữ dữ liệu để xử
lý, không tự chọn một snapshot rỗng hoặc ghi đè theo đồng hồ thiết bị.

Lưu theo mốc gameplay, chuyển nền và thoát đi qua writer cục bộ hiện có; network
chạy ngoài luồng UI. Khi thoát, phần thưởng đã kiếm được được chốt trước capture.
Không có banner lưu ở đầu màn hình. Khi mạng lỗi hoặc process bị kill, chỉ dữ
liệu đã commit cục bộ mới được bảo vệ; phần chưa capture có thể mất. Outbox chưa
được server xác nhận sẽ thử lại, không được mô tả là đã lưu lên server.

## Facebook và Google Ads

Các nút/callback Facebook và quảng cáo được loại khỏi luồng game; không giả đăng
nhập thành công và không cấp thưởng thay thế. Các chỉnh sửa DEX được kiểm chứng
theo phạm vi riêng; không thay Application bằng lớp rỗng.

Xem [phạm vi SDK và bằng chứng](DISABLED_LEGACY_SERVICES.md),
[mã giao diện/callback](RETIRED_PROVIDER_SERVICES.md) và
[tương thích archive](CLOUD_SAVE_ARCHIVE_COMPATIBILITY.md).

## Kiểm chứng và APK

Kết quả build, runtime, SHA và những phần chưa kiểm chứng sẽ được ghi trong
metadata bàn giao cùng APK. Kiểm tra host không thay cho kiểm thử trên Android;
build Linux cũng không chứng minh đường mạng LAN Windows của người dùng.
