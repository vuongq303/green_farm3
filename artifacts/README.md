# APK debug đã build

[Tải green-farm-3-debug.apk](green-farm-3-debug.apk) — khoảng 15 MB, phiên bản 4.4.2.
APK hoàn chỉnh của module **app**, gồm runtime DEX gốc, 108 lớp Java lõi và các
helper SQLite. Nguồn app: checkpoint `d27a6ca`.

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

Cùng mã nguồn app, dùng URL cloud, đã chạy trên emulator API28: tự nâng DB3→4,
hiện “Đã lưu”, thu hoạch/tiếp tục tutorial và khôi phục farm sau force-stop.
**116/116** test mã, **13/13** test resource, **26/26** kiểm tra SQLite Android đạt.
[Báo cáo và ma trận kiểm chứng](../docs/SQLITE_PROGRESS_RECOVERY.md).

Windows LAN và chính APK nhúng IP Windows chưa chạy được từ cloud. XP số/toàn
bộ số dư, quest detail/claim, di chuyển ✓/✕ qua UI, ma trận vật nuôi/công trình,
restart backend ở mốc này, thiết bị thật và Android mới chưa được xác nhận đủ.
Mở sách quest đã hiện drawer “New!”; chưa tái hiện lỗi quest cụ thể tự hoàn thành.
Khóa ký riêng, database người chơi và credential không được commit.
