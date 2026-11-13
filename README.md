# Green Farm 3

Project hiện có một module ứng dụng Android: **`app`**. Mở thư mục project
(`D:\green_farm3` trên Windows), Sync Gradle, chọn cấu hình **Android App `app`**,
chọn máy ảo/điện thoại rồi bấm **Run**.

Application ID và namespace chính hiện là **`com.quanhoang.greenfarm3`**.
Ứng dụng mới có bộ nhớ riêng; chuyển tiến trình từ bản cũ theo
[hướng dẫn xuất/nhập save](docs/SAVE_TRANSFER_KOTLIN.md), dùng đúng khóa ký của bản
cũ cho APK cầu nối. Không gỡ bản cũ trước khi kiểm tra bản nhập.

Pipeline chuẩn khôi phục resource, biên dịch Java/Kotlin được chọn và các helper,
ghép runtime DEX còn lại, đổi package/symbol có kiểm chứng, cập nhật metadata
kích thước DEX, ký và kiểm tra APK. AGP cung cấp đúng
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
gốc ở `app/src/main/java/com/quanhoang/greenfarm3` và
`app/src/main/kotlin/com/quanhoang/greenfarm3`.

Giữ các phiên bản bạn đã cập nhật: Gradle9.7.1 có checksum, JDK25 theo daemon
criteria, AGP8.10.1; thêm Kotlin2.4.21/JVM8. Cần SDK Platform28,
Build Tools35.0.0 và Python3.10+.
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
`http://10.37.233.55:8787/health`. Backend kiểm tra MySQL/schema trước khi mở
cổng; lỗi thì báo rõ và thoát mã 1. Nếu DB mất kết nối sau khi đã chạy, endpoint
trả 503, không chuyển sang RAM/JSON. Hướng dẫn lấy IPv4, migration
và rule Windows Firewall đúng cổng nằm trong README backend.

Tài khoản anonymous, access/refresh session và compliance hiện được lưu vào
MySQL. Gameplay/save nông trại chuyển sang SQLite bền vững trên thiết bị
(`greenfarm-progress.db`, schema 4), tự tạo/migration và nhập RMS cũ có CRC hợp lệ.
Snapshot giữ codec gốc, gồm tutorial, quest, kho, farm, timestamp, thưởng đang chờ
và hướng sprite. Snapshot tại cuối frame được gom200/500ms rồi ghi bằng một
worker SQLite; lỗi đọc dừng tải, lỗi ghi giữ snapshot chờ và retry có kiểm soát. Đặt/di chuyển có ✓/✕, preview không được lưu như vị trí đã
xác nhận. Chưa triển khai cloud farm save hoặc Facebook Connect mới.

[Checkpoint hiện tại: Kotlin, package và chuyển save](docs/KOTLIN_MIGRATION_CHECKPOINT.md).
Đã chuyển **99 file Kotlin**, trong đó 83 lớp thuộc namespace game gốc;
**28 lớp Java** vẫn chứa logic thật và **125 lớp tích hợp** còn lấy từ DEX gốc.
Toàn bộ 110 lớp lõi có bản biên dịch từ source (82 Kotlin/28 Java).
Đây chưa phải toàn bộ game Kotlin hoặc build độc lập hoàn toàn khỏi APK gốc.
[Kiểm kê từng lớp/native](docs/KOTLIN_DEPENDENCY_INVENTORY.md) và
[bảng đổi tên có bằng chứng](docs/VERIFIED_GAME_NAMES.md).

[Checkpoint lưu nền trước Kotlin](docs/ASYNC_GAMEPLAY_CHECKPOINT.md).
Đã bỏ nhãn lưu thông thường và phần đề cử Trò chơi+/Welcome cross-promotion.
Đổi ngôn ngữ có xác nhận bằng ngôn ngữ hiện tại, chờ lưu trước khi tự khởi động lại;
trong tutorial, phím Menu Android mở màn hình Options hiện có. Không reset farm.
154 test host và20 test công cụ/ngôn ngữ đã đạt; regression cold receiver được
kiểm tra riêng sau lỗi UI thực tế. 30 ca Android SQLite cho migration đã đạt
trên fixture source; phạm vi APK/UI hiện tại được ghi rõ trong checkpoint.
Bài tải cùng100snapshot tổng hợp giảm100→6commit; đây không phải100thu hoạch/FPS.
Các mốc UI và phần chưa xác minh ghi rõ trong checkpoint, không coi unit/build là
kiểm thử gameplay toàn bộ. [Lịch sử SQLite](docs/SQLITE_PROGRESS_RECOVERY.md).

[Kết quả hợp nhất và sửa crash khởi động](docs/APP_BUILD_AND_STARTUP.md).
[Thư mục ghép APK và chẩn đoán lỗi build Windows](docs/WINDOWS_APK_MERGE_PATHS.md).
[Kiểm kê giao thức LAN/MySQL](docs/backend/LAN_PROTOCOL_AUDIT.md).
M0 từng vượt Accept và mở farm tutorial; chưa xác nhận đầy đủ thao tác farm,
save/load, đăng nhập Facebook, âm thanh hoặc Android mới. Các báo cáo recovered
cũ được giữ làm lịch sử checkpoint, không còn là hướng dẫn build hiện tại.

[Kiến trúc backend](docs/backend/ARCHITECTURE.md) ·
[Mốc M0](docs/backend/M0_CHECKPOINT.md) ·
[Dữ liệu gốc](docs/original-payload.sha256).
