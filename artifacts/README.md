# APK debug đã build

[Tải green-farm-3-debug.apk](green-farm-3-debug.apk) — khoảng 15 MB, phiên bản 4.4.2.
Đây là APK hoàn chỉnh của module `app`, gồm runtime DEX gốc và 108 lớp Java lõi.

Nguồn: commit `28d273e`. Đã build bằng task chuẩn `:app:assembleDebug`,
kiểm tra lớp Application thật, DEX, assets/native, chữ ký và ZIP alignment.
Chi tiết phiên bản, SHA-256 và chứng thư debug trong [build-info.json](build-info.json).
Checksum riêng: [green-farm-3-debug.apk.sha256](green-farm-3-debug.apk.sha256).

APK dùng **`http://10.37.233.55:8787/`**, IPv4 Windows người dùng cung cấp.
Chạy [backend JavaScript/MySQL](../backend/README.md) trên máy đó và kiểm tra
`/health` từ cả Windows lẫn emulator. APK không chứa server hoặc credential DB.
Địa chỉ chung ở `gradle.properties`, biến `BACKEND_BASE_URL`; đổi IP cần restart
backend, build và cài lại APK. Không dùng biến `localBackendUrl` cũ.

Đã kiểm thử một bản cùng source, chỉ override URL sang IPv4 cloud, trên emulator
API 28: bootstrap/authorize/profile nhận200 từ MySQL-backed server, app mở tới
Connect/Not now. Đã build cả khi IDE inject API24; overlay/final vẫn DEX035 theo
minSdk16. [Checkpoint và giới hạn](../docs/backend/LAN_MYSQL_CHECKPOINT.md).

Mạng Windows `10.37.233.55` và chính APK nhúng IP đó chưa được chạy thử từ cloud.
Facebook Connect, UI retry đầy đủ, cloud farm save, gameplay/save-load toàn bộ
và Android mới chưa được xác nhận. Khóa ký riêng không được commit.
