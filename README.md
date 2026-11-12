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

Backend M0 local vẫn là dịch vụ riêng, không phải task build Android. Khi dùng
emulator, chạy một lần ở terminal PowerShell backend:

```powershell
cd backend
$env:PUBLIC_BASE_URL = "http://10.0.2.2:8787"
npm start
```

Server cần Node24. Backend mặc định của APK là `http://10.0.2.2:8787/`.
Điện thoại thật cần endpoint backend mà nó truy cập được; cấu hình
`localBackendUrl` trước khi build. M0 dùng session RAM, chưa có tài khoản/DB
bền vững. Xem [backend](backend/README.md).

[Kết quả hợp nhất và sửa crash khởi động](docs/APP_BUILD_AND_STARTUP.md).
M0 từng vượt Accept và mở farm tutorial; chưa xác nhận đầy đủ thao tác farm,
save/load, đăng nhập Facebook, âm thanh hoặc Android mới. Các báo cáo recovered
cũ được giữ làm lịch sử checkpoint, không còn là hướng dẫn build hiện tại.

[Kiến trúc backend](docs/backend/ARCHITECTURE.md) ·
[Mốc M0](docs/backend/M0_CHECKPOINT.md) ·
[Dữ liệu gốc](docs/original-payload.sha256).
