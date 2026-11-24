# APK debug và chuyển save

[Tải game mới: green-farm-3-debug.apk](green-farm-3-debug.apk) — application ID
**com.quanhoang.greenfarm3**, khoảng15,8MB, version4.4.2. Đây là APK hoàn chỉnh
của module **app**, nguồn thực thi checkpoint `1064add`.

Phạm vi Kotlin **chưa hoàn tất**: 99 file Kotlin; trong236 lớp namespace game
nguyên gốc có83 Kotlin,28 Java và125 lớp integration còn lấy từ DEX. Toàn bộ110
lớp lõi có source (82 Kotlin/28 Java). Runtime MIDlet/SDK và10 thư viện native
vẫn được giữ; APK gốc còn là đầu vào build. Không coi Kotlin stdlib hoặc lớp
phụ compiler sinh là logic game đã chuyển.

Ứng dụng mới có thư mục dữ liệu riêng. Để giữ tiến trình bản cũ:

1. Dùng [APK cầu nối xuất save](green-farm-3-legacy-export-debug.apk), hoặc build
   `:app:assembleDebug -PgreenfarmLegacyExport=true` bằng **đúng khóa ký bản cũ**.
   Cập nhật bản cũ, không uninstall/clear data để cài cầu nối.
2. Mở màn Chuyển bản lưu, nhấn Chuẩn bị rồi Xuất bằng SAF.
3. Cài game mới, mở Chuyển bản lưu, Chuẩn bị và Nhập chính tệp vừa xuất; sau đó
   Mở game và kiểm tra farm. [Hướng dẫn đầy đủ](../docs/SAVE_TRANSFER_KOTLIN.md).

APK cầu nối giữ application ID cũ nhưng tất cả game class dùng namespace mới;
ngoại lệ hai literal authority/URI KeyProvider được kiểm chứng riêng. Một khóa
ký khác sẽ không cập nhật được ứng dụng cũ. Khóa riêng nằm trong `.local/`, không
được commit; dùng cùng khóa cho các lần cập nhật tiếp theo.

Hai APK được build sạch trên Linux, không override biến backend:

```sh
./gradlew :app:clean :app:assembleDebug -PgreenfarmLegacyExport=true --stacktrace
./gradlew :app:clean :app:assembleDebug --stacktrace
```

Kết quả tương ứng1m54s và1m44s,45task. DEX035, minSdk16, Application thật,
16 component game,0 game class thuộc package cũ,59 assets/native payload nguyên
byte, chữ ký v1/v2/v3 và ZIP alignment16KB đều đạt. Metadata, chứng thư và SHA:
[build-info.json](build-info.json), [checksum game](green-farm-3-debug.apk.sha256),
[checksum cầu nối](green-farm-3-legacy-export-debug.apk.sha256).

Cả hai dùng **http://10.37.233.55:8787/** từ root `gradle.properties`.
Chạy [backend JavaScript/MySQL](../backend/README.md) trên Windows đó; APK không
chứa server hoặc credential database. Đổi IP/port cần build/cài lại.

Cùng source với URL cloud đã được thử trên API28: SAF chuyển14record nguyên
byte từ app cũ sang app mới; farm mở lại,1900xu/50cash/XP7/level1/tutorial23/EN
được giữ sau lần load đầu và hai cold restart. Đổi sang Tiếng Việt cũng tự
restart và khôi phục farm với đúng số dư/XP/tutorial.154test host,20test tools,
regression cold receiver,27probe storage và30probe migration từ APK đã cài đạt.
[Báo cáo](../docs/KOTLIN_MIGRATION_CHECKPOINT.md),
[bằng chứng runtime](../docs/kotlin-validation/game-runtime.json).

Chính hai binary nhúng IP Windows và mạng LAN chưa được chạy từ cloud; build
Windows, thiết bị thật, Android mới, toàn bộ quest/kho/vật nuôi, âm thanh thực và
FPS trước/sau chưa được xác nhận đầy đủ. Các quan sát gameplay/ngôn ngữ bổ sung
được ghi riêng trong báo cáo, không suy ra từ build hoặc kiểm tra byte.

Mở project → **Android App app** → chọn thiết bị → **Run**. Output mặc định cuối
vẫn là APK game mới tại `app/build/outputs/apk/debug/app-debug.apk`.
