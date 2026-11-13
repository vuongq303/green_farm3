# Green Farm 3 — bản game chính dùng backend SQLite

[Tải APK game chính](green-farm-3-debug.apk), application ID
**com.quanhoang.greenfarm3**, module **app**, version4.4.2. Cấu hình backend được
nhúng từ root `gradle.properties`: **http://10.37.233.55:8787/**.
Thông tin nguồn, SHA và kiểm thử: [build-info.json](build-info.json).

Profile Offline Test, cờ Gradle và helper cấp9.999.999/level50 đã được gỡ.
Save package test không được nhập vào bản chính. Giá trị khởi tạo tài khoản
hiện quản lý tại [new_player_defaults.json](../backend/config/new_player_defaults.json):
**2000xu,50cash,level1,XP0,tutorial0,ngôn ngữauto**. Backend giữ một bản sao cấu
hình cho từng hồ sơ mới; chỉnh file chỉ ảnh hưởng hồ sơ tạo sau, không cần build
lại APK. [Cách chỉnh và các giới hạn](../backend/docs/NEW_PLAYER_DEFAULTS.md).
Mẫu farm/vật phẩm ban đầu vẫn tham chiếu dữ liệu nguyên bản, không tự mở khóa
hay tạo danh sách item chưa được xác minh.

Backend dùng JavaScript/Node24 và `node:sqlite`, file mặc định
`backend/data/green-farm.sqlite`. Thiết lập `.env` theo [README backend](../backend/README.md),
rồi chạy trong thư mục `backend`:

```powershell
npm ci
npm run migrate
npm start
```

Nếu đang dùng MySQL cũ, làm [export/import nhất quán](../backend/docs/MYSQL_TO_SQLITE.md)
trước khi chuyển instance đó sang SQLite; server không tự thay database cũ bằng
một database rỗng. Secret phiên phải được giữ ổn định. Dữ liệu local đã có được
backup trước migration và cần backend operator duyệt đúng UUID/hash khi sao chép
sang hồ sơ khách mới; ứng dụng cung cấp lệnh trong thông báo migration. Không
cần phê duyệt này cho một tài khoản mới không có save local.

Facebook SDK và Google Ads presentation SDK đã bị loại khỏi APK. Sáu lớp Google
Advertising Identifier dùng chung vẫn cần cho Firebase/định danh. Vị trí quảng
cáo và Facebook Login bị loại khỏi luồng game, không phát thưởng thay thế.
[Inventory và bằng chứng](../docs/backend-restoration-validation/packaging/windows-fixed/dependency-removal-inventory.json).
Resources/native nguyên bản được giữ; Java JADX tham chiếu và APK gốc còn phục
vụ đối chiếu. Chuyển toàn bộ logic sang Kotlin chưa hoàn tất.

Snapshot đầy đủ và outbox được ghi trong cùng transaction SQLite local; chỉ ACK
đúng operation/hash/revision mới xác nhận commit backend. Lỗi mạng giữ pending;
không có fallback tạo farm rỗng hay tự đăng nhập thành công.
[Xem thiết kế, mốc Git và kết quả](../docs/BACKEND_RESTORATION.md).

Build Windows:

```powershell
.\gradlew.bat :app:clean :app:assembleDebug --stacktrace
```

Mở project → chọn **Android App app** → chọn thiết bị → **Run**. Output hoàn chỉnh
là `app/build/outputs/apk/debug/app-debug.apk`. Đổi IP trong `BACKEND_BASE_URL` cần
khởi động lại backend, build và cài lại APK.

APK cầu nối xuất save [legacy-export lịch sử](https://github.com/vuongq303/green_farm3/blob/fdb30d12f5ced3b80c36d3e93907c6fc04f2a765/artifacts/green-farm-3-legacy-export-debug.apk)
thuộc mốc trước đợt này; không phải bản game mới đã gỡ SDK. Giữ việc xóa các
artifact cũ của commit `1dad293`, không phát hành lại utility đó. Dùng
[hướng dẫn chuyển save](../docs/SAVE_TRANSFER_KOTLIN.md)
và đúng khóa ký khi cập nhật ứng dụng cũ. Import SAF vào profile đã liên kết cloud
hoặc có pending hiện bị chặn để bảo vệ dữ liệu; cloud restore có kiểm tra ownership
vẫn là luồng đồng bộ được hỗ trợ.

Kiểm thử Android dùng APK cùng source với origin cloud riêng. Máy Windows và
đường LAN10.37.233.55 không truy cập được từ môi trường cloud; không coi build
Windows thành công là đã chạy thử kết nối trên máy bạn. Chi tiết luồng đã chạy
và giới hạn được ghi trong metadata/báo cáo runtime.
