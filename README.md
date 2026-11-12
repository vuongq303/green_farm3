# Green Farm 3

Project hiện có một module ứng dụng Android: **`app`**. Mở thư mục project
(`D:\green_farm3` trên Windows), Sync Gradle, chọn cấu hình **Android App `app`**,
chọn máy ảo/điện thoại rồi bấm **Run**.

Pipeline chuẩn tự khôi phục resource, biên dịch 108 lớp Java lõi, ghép runtime
DEX gốc, cập nhật metadata kích thước DEX, ký và kiểm tra APK. AGP cung cấp đúng
APK hoàn chỉnh cho `assembleDebug`, `installDebug` và Android Studio; APK trung
gian nằm riêng trong `intermediates`, không dùng để cài.

```powershell
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:installDebug
```

APK chuẩn: `app/build/outputs/apk/debug/app-debug.apk`.
Bản APK debug đã build để tải nằm trong [artifacts/](artifacts/README.md).
Không cần module `core-source`, task recovered riêng, cấu hình Run recovered
hoặc cài APK thủ công. Không sửa file trong `app/build/generated`; sửa code
gốc ở `app/src/main/java`.

Giữ các phiên bản bạn đã cập nhật: Gradle9.7.1 có checksum, JDK25 theo daemon
criteria, AGP8.10.1; cần SDK Platform28, Build Tools35.0.0 và Python3.10+.
Trên Windows mặc định dùng `python`; có thể đặt `pythonExecutable` trong Gradle
properties nếu Python nằm ở đường dẫn khác. Script chạy UTF-8 và gọi JDK/SDK
bằng argument array, hỗ trợ đường dẫn có khoảng trắng. Khóa debug nằm trong
`.local/`, không commit và không bị `clean` xóa. Release chưa bật trong pipeline
nghiên cứu này.

Địa chỉ backend của app và các URL discovery dùng chung **một biến** trong
`gradle.properties` ở thư mục gốc:

```properties
BACKEND_BASE_URL=http://10.37.233.55:8787/
```

Đây là IPv4 Windows bạn đã cung cấp. Đổi IP/port phải sửa biến này, khởi động
lại backend, **build và cài lại APK**. Không có fallback localhost/`10.0.2.2`;
không dùng `localBackendUrl` nữa. Backend đọc trực tiếp cùng file properties.

Backend dùng JavaScript ES Modules, Node.js 24+ và MySQL 8.0/8.4. Tạo database/
user MySQL và cấu hình mật khẩu riêng theo [hướng dẫn backend](backend/README.md),
sau đó chạy trong PowerShell:

```powershell
cd backend
npm ci
Copy-Item .env.example .env
# Sửa .env: DB_* và RESEARCH_SESSION_SECRET riêng, ổn định qua restart.
npm run migrate
npm start
# Khi phát triển: npm run dev
```

Listener mặc định `HOST=0.0.0.0`, `PORT=8787`. App chỉ gọi HTTP; tài khoản MySQL
chỉ ở `backend/.env`, không nằm trong APK. Kiểm tra từ Windows **và** emulator:
`http://10.37.233.55:8787/health`. Endpoint báo cả backend và MySQL; DB/schema
chưa sẵn sàng trả 503, không chuyển sang RAM/JSON. Hướng dẫn lấy IPv4, migration
và rule Windows Firewall đúng cổng nằm trong README backend.

Tài khoản anonymous, access/refresh session và compliance hiện được lưu vào
MySQL. Gameplay/save nông trại vẫn dùng RMS gốc trên thiết bị; chưa chuyển cloud
farm save hoặc Facebook Connect sang giao thức mới không có bằng chứng.

[Kết quả hợp nhất và sửa crash khởi động](docs/APP_BUILD_AND_STARTUP.md).
[Thư mục ghép APK và chẩn đoán lỗi build Windows](docs/WINDOWS_APK_MERGE_PATHS.md).
[Kiểm kê giao thức LAN/MySQL](docs/backend/LAN_PROTOCOL_AUDIT.md).
M0 từng vượt Accept và mở farm tutorial; chưa xác nhận đầy đủ thao tác farm,
save/load, đăng nhập Facebook, âm thanh hoặc Android mới. Các báo cáo recovered
cũ được giữ làm lịch sử checkpoint, không còn là hướng dẫn build hiện tại.

[Kiến trúc backend](docs/backend/ARCHITECTURE.md) ·
[Mốc M0](docs/backend/M0_CHECKPOINT.md) ·
[Dữ liệu gốc](docs/original-payload.sha256).
