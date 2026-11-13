# APK debug đã build

[Tải green-farm-3-debug.apk](green-farm-3-debug.apk) — khoảng 15 MB, phiên bản 4.4.2.
APK hoàn chỉnh của module **app**, gồm runtime DEX gốc, 108 lớp Java lõi và các
helper lưu nền/ngôn ngữ/tutorial. Nguồn app: checkpoint `cd8ff09`.

Đã build bằng `:app:assembleDebug`, cả khi IDE inject API24; DEX cuối vẫn 035,
minSdk16. Kiểm tra Application thật, DEX, 59 assets/native payload giữ nguyên,
chữ ký v1/v2/v3 và ZIP alignment đều đạt. Thông tin/SHA-256/chứng thư debug:
[build-info.json](build-info.json), [checksum](green-farm-3-debug.apk.sha256).

APK dùng **http://10.37.233.55:8787/** từ `BACKEND_BASE_URL` trong root
`gradle.properties`. Chạy [backend JavaScript/MySQL](../backend/README.md) trên
máy Windows đó, kiểm tra `/health` từ cả Windows và emulator. APK không chứa
server hoặc credential DB; đổi địa chỉ cần build/cài lại.

Farm được lưu vào SQLite trên thiết bị, schema4; MySQL backend lưu tài khoản,
session và compliance. Mở project, chọn **Android App app**, chọn máy rồi **Run**.

Cùng mã nguồn app, dùng URL cloud, đã chạy trên emulator API28: giữ bản lưu cũ,
mở được farm tiếng Việt sau lỗi sprite khởi tạo trễ và tự khởi động lại về English.
Bốn lần OK nhanh chỉ tạo một lần khởi động lại. Quest mở ra vẫn còn các mục tiêu
0/1; mua bê hợp lệ trừ đúng 100 xu. Nhãn lưu thông thường đã được bỏ.
**129/129** test mã, **20/20** test công cụ/ngôn ngữ và **27/27** kiểm tra SQLite
Android đạt. [Báo cáo và ma trận 12 trường hợp](../docs/ASYNC_GAMEPLAY_CHECKPOINT.md).

Bài đo cùng 100 yêu cầu snapshot tổng hợp giảm 100→6 commit, caller p95
457,60→31,85 ms; đây không phải 100 lượt thu hoạch hoặc phép đo FPS.
Encoder game gốc vẫn chạy ở ranh giới frame; SQLite chạy bằng một worker riêng,
giữ WAL/FULL. Các bản APK dùng cho benchmark/probe và UI được ghi riêng trong
build-info để tránh nhầm với bản nhúng IP Windows này.

Windows LAN và chính APK nhúng IP Windows chưa chạy được từ cloud. Chưa kiểm
chứng 50–100 lượt thu hoạch thật với tổng XP/kho/quest, FPS trước/sau, đủ 14 ngôn
ngữ trên thiết bị, toàn bộ di chuyển ✓/✕, vật nuôi/công trình, thiết bị thật hoặc
Android mới. Kết quả cold restart cuối và giới hạn còn lại nằm trong báo cáo.
Khóa ký riêng, database người chơi và credential không được commit.
