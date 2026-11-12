# APK debug đã build

[Tải green-farm-3-debug.apk](green-farm-3-debug.apk) — khoảng 15 MB, phiên bản 4.4.2.
Đây là APK hoàn chỉnh của module `app`, gồm runtime DEX gốc và 108 lớp Java lõi.

Nguồn: commit `b0f3674`. Đã build lại bằng `:app:assembleDebug --rerun-tasks`,
kiểm tra lớp Application thật, DEX, assets/native, chữ ký và ZIP alignment.
Chi tiết phiên bản, SHA-256 và chứng thư debug trong [build-info.json](build-info.json).
Checksum riêng: [green-farm-3-debug.apk.sha256](green-farm-3-debug.apk.sha256).

APK dùng backend thử nghiệm `http://10.0.2.2:8787/`. Chạy
[backend M0](../backend/README.md) trên máy host để dùng với Android Emulator.
APK không chứa server; điện thoại thật cần build lại với `localBackendUrl`
mà điện thoại truy cập được.

SHA-256 trùng bản đã mở tới phần giới thiệu game trên emulator API 28 trong
[checkpoint trước](../docs/APP_BUILD_AND_STARTUP.md). Lượt bàn giao này kiểm tra
build/đóng gói, không chạy lại gameplay. Chưa xác minh toàn bộ thao tác farm,
save/load, đăng nhập tài khoản hoặc Android mới. Khóa ký riêng không được commit.
