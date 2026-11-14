# Green Farm 3 — lưu cục bộ

Ứng dụng Android có một module `app`, namespace/application ID
`com.quanhoang.greenfarm3`. Tiến trình nông trại được lưu bằng SQLite trên thiết
bị; không cần tài khoản, Firebase, MySQL, Node.js hoặc backend để chạy game.
APK chính không có quyền Internet, truy cập trạng thái mạng hoặc billing.

## Build và chạy

Giữ toolchain đã ghim: Gradle 9.7.1 (checksum trong wrapper), JDK 25, AGP 8.10.1,
Kotlin 2.4.21, Android SDK Platform 28, Build Tools 35.0.0, Python 3.10+.

```sh
./gradlew :app:assembleDebug -PpythonExecutable=python3
./gradlew :app:installDebug
```

Trên Windows dùng `gradlew.bat`. Android Studio dùng cấu hình Android App `app`.
APK hoàn chỉnh duy nhất là `app/build/outputs/apk/debug/app-debug.apk`.
Không cài APK carrier trong `intermediates`; không có variant APK offline riêng.

Pipeline hiện tại biên dịch source, ghép phần runtime gốc chưa khôi phục,
đổi namespace, loại Firebase/measurement và vô hiệu các cầu nối online còn cần
cho ABI của wrapper, cập nhật metadata kích thước DEX rồi ký/xác minh APK.
`tools/package_game.py` từ chối đóng gói nếu còn quyền Internet, Firebase provider,
Firebase class/cấu hình. Toàn bộ asset và native library được đối chiếu byte/hash
với APK gốc; bước loại SDK kiểm tra lại semantics của gameplay/storage đã ghép.

`green-farm-3.apk` là đầu vào nghiên cứu bất biến, được kiểm SHA-256; nó không
phải bản APK để phân phối. SDK online trong archive gốc không được đưa nguyên
vẹn vào sản phẩm cuối. Không sửa `app/build/generated`; sửa source gốc trong
`app/src/main/java/com/quanhoang/greenfarm3` và `app/src/main/kotlin/com/quanhoang/greenfarm3`.

## Dữ liệu và tài nguyên

SQLite `greenfarm-progress.db`, schema 4, nằm ở đường dẫn Android do application
context cấp (`getDatabasePath`). Không đổi application ID, codec hay tên bản lưu;
không xóa/reset database cũ. Header, gameplay, farm, reward và pose được ghi trong
snapshot transaction; settings và các record RMS khác dùng cùng writer.

Writer tuần tự giữ snapshot tuyệt đối, debounce 500 ms, giới hạn chờ 2 giây/50
thay đổi, retry có giới hạn, WAL và synchronous FULL. Pause gửi checkpoint trước
khi renderer dừng; thoát có kiểm soát phải đợi ghi bền vững trước khi dọn world
hoặc kết thúc process. OS force-stop vẫn có thể mất những thay đổi chưa commit;
không có callback Android nào bảo đảm cứu RAM sau khi process đã bị kill.
Lỗi đọc giữ nguyên bản lưu và chặn tạo farm mới; lỗi ghi giữ snapshot để thử lại.

Pack thiết yếu và 14 locale có trong `assets/800x480/`. Loader mở trực tiếp từ
APK bằng application AssetManager, không phụ thuộc tiền tố độ phân giải/cache
wrapper, OBB hoặc máy chủ. Lỗi ghi rõ path/stage/index, giữ bước tải để retry.

Chuyển save giữa hai application ID bằng [hướng dẫn xuất/nhập](docs/SAVE_TRANSFER_KOTLIN.md).
Không gỡ bản cũ trước khi kiểm tra bản nhập. Bản cầu nối phải dùng khóa ký cũ.

## Kiểm thử

```sh
python3 -m unittest discover -v -s tests
python3 -m unittest discover -s tools -p 'test_*.py'
./gradlew :app:assembleDebug
./gradlew -p tests/sqlite-host test
```

Host SQLite dùng JDK/JRE 21 launcher; có thể đặt `GREENFARM_TEST_JAVA`.
Nếu cần, đặt `GREENFARM_ORIGINAL_JAR` đến
`.local/bytecode-tools/greenfarm-original.jar` do `prepareCoreDependencies` tạo
để bật các differential test. Kiểm thử native SQLite trực tiếp với APK debug
đã cài trên Android:

```sh
python3 tools/run_game_save_probe.py --serial emulator-5554 --timeout 60
```

Probe chỉ dùng `files/sqlite-probe-<fixture>`, không mở database người chơi;
không tạo variant game hoặc thay helper trong APK bằng implementation test.
Bộ kiểm tra này không thay thế thao tác trồng/thu hoạch trên UI.

Phạm vi thay đổi, nguyên nhân lỗi và kết quả thực chạy được ghi tại
[OFFLINE_IMPLEMENTATION.md](docs/OFFLINE_IMPLEMENTATION.md).
Tài liệu trong `docs/backend/` và các checkpoint cũ là lịch sử kiến trúc trước
chuyển đổi, không phải hướng dẫn khởi động hiện tại. Mã backend, cấu hình URL
backend và bước patch endpoint đã được loại khỏi workflow chính.
