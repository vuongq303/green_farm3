# Nâng cấp Android cho Green Farm 3

Nhánh `upgrade/android-modern` bắt đầu từ `origin/main` tại `093fa78`.
Đã fetch GitHub và đối chiếu các PR #27, #29, #30 về thứ tự ký APK, đăng nhập
và mở lại bản lưu. Bản đối chứng API 28 build lại thành công trên cùng checkout
trước khi nâng bộ công cụ.

## Cấu hình

| Thành phần | Trước | Sau |
| --- | --- | --- |
| Compile SDK | 28 | 37.0, Android 17 |
| Target SDK mặc định | 28 | 28 |
| Min SDK | 16 | 16 |
| Android Gradle Plugin | 8.10.1 | 9.5.1 |
| SDK Build Tools | 35.0.0 | 37.0.0 |
| Gradle Wrapper | 9.7.1, có SHA-256 | 9.7.1, cùng pin |
| JDK | 25 | 25, kiểm thử với 25.0.5.0.3 |
| Kotlin / bytecode | 2.4.21 / JVM 8 | 2.4.21 / JVM 8 |

AGP 9.5.1 dùng Kotlin tích hợp. Compiler của AGP mặc định là 2.2.10 và không đọc
được metadata runtime Kotlin 2.4.21. Build pin rõ compiler, Gradle plugin và thư
viện Kotlin theo cùng một phiên bản. Bản cloud dùng chế độ Kotlin in-process khi
daemon chậm kết nối. Cấu hình DEX tiếp tục dùng dexlib2 2.5.2 và các SDK gốc từ
APK đã pin. Không nâng hoặc gỡ các thư viện game khi chưa có bằng chứng tương
thích.

Cài SDK Platform 37.0, Build Tools 37.0.0 và Platform 28 bằng SDK Manager.
Platform 28 cung cấp thuộc tính framework để phục hồi chính xác tài nguyên cũ,
còn AAPT liên kết tài nguyên với Platform 37. Python 3.10+ và JDK 25 vẫn cần thiết.

```bash
./gradlew :app:assembleDebug :app:assembleDebugAndroidTest
./gradlew :app:assembleDebug -PgreenfarmLocalDevelopment=true
./gradlew :app:processDebugMainManifest -PgreenfarmTargetSdk=37
```

Hai lệnh assemble tạo bản Debug dùng luồng tài khoản website và bản Debug local
tương ứng. APK chính thức của mỗi lần build nằm ở
`app/build/outputs/apk/debug/app-debug.apk`. Phải dùng APK hoàn chỉnh, do task
ghép DEX tạo ra. Lệnh xử lý manifest chỉ giúp đối chiếu khai báo của target thử
nghiệm. Bản cầu nối xuất save của ứng dụng cũ giữ target 28.

## Thay đổi tương thích đã thực hiện

- Khai báo rõ component nào có thể nhận intent từ ngoài ứng dụng. Launcher và
  callback trình duyệt vẫn nhận intent tương ứng. Lệnh broadcast đóng các
  Activity chỉ gửi tới package hiện tại. Android 13+ dùng
  `RECEIVER_NOT_EXPORTED`. Các bản Android cũ dùng quyền chữ ký nội bộ.
  Hai lời gọi DEX trong `BaseActivity` được đối chiếu ngược với bytecode gốc.
- Quy tắc sao lưu Android 12+ áp dụng cùng ngoại lệ với quy tắc cũ cho tệp
  tùy chọn phiên được mã hóa bằng Keystore, trên cả cloud backup và chuyển thiết
  bị. DB nông trại và định dạng bản lưu không thay đổi.
- Vòng đời OpenGL dùng lại framebuffer, shader và buffer khi tạo Surface mới
  trên cùng context, đồng thời cho phép khởi tạo lại khi context thay đổi hoặc
  renderer nhận tín hiệu mất context. Viewport, tỷ lệ và ánh xạ touch hiện có
  được giữ nguyên. Phần tối ưu này có test hồi quy JVM cho các nhánh context.
- D8 mới sinh annotation `LambdaMethod` cho lambda desugaring. Bộ ghép chỉ chấp
  nhận kiểu cụ thể, có đúng ba phương thức abstract và không có field hoặc mã
  thực thi. Các điều kiện whitelist và đối chiếu ngữ nghĩa DEX còn lại vẫn áp
  dụng. Test từ chối stub sai chữ ký, thêm mã, đè lớp gốc hoặc thêm kiểu R8 khác.
- Pipeline giữ nguyên stable resource IDs và tài nguyên, giữ cả 5 ABI và phương
  thức nén gốc của thư viện JNI. Bộ xác minh kiểm tra `extractNativeLibs` trong
  APK cuối và báo căn chỉnh ELF riêng với căn chỉnh ZIP.
- Backend lấy mật khẩu từ `DB_PASSWORD` và kiểm tra HTTPS theo cấu hình proxy
  đáng tin cậy hiện có. Giao thức đăng ký, đăng nhập trình duyệt, deep link, PKCE,
  session và schema MySQL giữ nguyên. Ứng dụng vẫn mở trình duyệt ngoài.

## Bảo toàn dữ liệu khi nâng cấp

APK Debug build từ nguồn giữ `com.quanhoang.greenfarm3`, versionCode `44220`
và versionName `4.4.2`. APK SDK 28 và SDK 37 trong phiên kiểm thử ký cùng
chứng thư cloud. Trên emulator API 28, `adb install -r` thành công. Tệp SQLite
và WAL giữ nguyên từng byte ngay sau khi cập nhật. Bản lưu thử có schema v4,
11 bản ghi và slot `device-local` thế hệ 114. Sau khi mở lạnh, ứng dụng ghi
`global_restore result=RESTORE_SUCCESS` và Activity game hoạt động.

Fixture trên đúng APK đã cài chạy **30/30** ca Android SQLite trong thư mục
kiểm thử riêng. Các ca bao gồm toàn bộ record xuất/nhập, checksum/WAL, language,
tombstone, khóa chuyển save, rollback và giữ nguyên file lỗi. Sau khi đưa ứng
dụng xuống nền và mở lại, thiết bị dùng cùng tiến trình và hiển thị cùng khung
hình. Tương tác với hội thoại hướng dẫn đưa người chơi vào màn nông trại.
PackageManager phân giải intent BROWSABLE tới `SocialAuthCallbackActivity`.
Một lần mở lạnh khác ghi `RESTORE_SUCCESS` khi emulator không có mạng mặc
định. Trạng thái Wi-Fi, dữ liệu di động và chế độ máy bay được khôi phục sau
kiểm tra. Game hiển thị hướng dẫn thu hoạch. ADB hết thời gian chờ trước khi
xác minh được thao tác thu hoạch hoặc checkpoint sau thao tác.

APK đã đưa vào thư mục `artifacts/` trên GitHub được ký bằng chứng thư khác.
Kiểm thử trên chỉ xác nhận cặp APK ký cùng khóa cloud. Để cập nhật cho người
chơi có bản đã phân phối, ký APK mới bằng khóa phù hợp với chứng thư của bản
đang cài và xác minh cập nhật tại chỗ trước khi phát hành.

## Kiểm tra build và backend

- `:app:assembleDebug` thành công cho chế độ tài khoản và local.
  `:app:assembleDebugAndroidTest` tạo APK test. APK chính vượt qua ghép
  manifest, đối chiếu DEX, resource IDs, tài nguyên, căn chỉnh ZIP và xác minh
  chữ ký v1/v2/v3. APK fixture SQLite riêng được chạy trên API 28.
- Suite host chạy 219 ca và bộ công cụ chạy thêm 20 ca. Hai ca lỗi và một ca
  bị bỏ qua trong lượt đầu đều đạt khi chạy lại. Fixture renderer được cập
  nhật để mô phỏng GL context thực, kiểm tra DEX relocation được cấp các JAR
  Gradle đã tải, và fixture Keystore chạy bằng OpenJDK 21 qua
  `GREENFARM_TEST_JAVA`. Oracle JDK 25 từ chối provider Keystore mô phỏng
  được nạp từ thư mục class. Đây là fixture AES/GCM trên host.
- Gradle ghép thành công manifest thử nghiệm cho target 30, 34 và 37.
  Sau các lượt thử, project tiếp tục build theo target 28.
- Backend đạt 76/76 test Node.js, 40/40 test MySQL 8.4.13 và 1/1 test Chromium
  qua HTTPS. Cả 4 migration chạy thành công, lần chạy lại áp dụng 0 migration
  và `/health` báo MySQL sẵn sàng. Chromium kiểm tra đăng ký, đăng nhập, URL
  callback chứa code/state và việc đổi mã PKCE một lần. Kiểm thử Android ở
  bước này xác minh phân giải deep link trên thiết bị.

## Ma trận thiết bị

| Hệ điều hành | Kiểm tra trên thiết bị |
| --- | --- |
| Android 8 / API 26 | Chưa chạy APK. |
| Android 9 / API 28 | Cập nhật cùng khóa, SQLite/WAL giống từng byte, mở lạnh và restore offline, giao diện/touch hướng dẫn, foreground/background, phân giải deep link, fixture SQLite 30/30. |
| Android 10 / API 29 | Chưa chạy APK. |
| Android 12 / API 31 | Chưa chạy APK. |
| Android 13 / API 33 | Đã chuẩn bị system image, chưa chạy APK. |
| Android 14 / API 34 | Chưa chạy APK. |
| Android 15 / API 35 | Đã chuẩn bị system image, chưa chạy APK. |
| Android 16 / API 36 | Emulator x86_64, 1280×800, 240 dpi, trang 4 KiB. Package Manager từ chối cài vì hệ điều hành vẫn đang boot. |
| Android 17 / API 37 | Emulator x86_64, 1280×800, 240 dpi, trang 4 KiB. SystemServer bị watchdog khởi động lại trong cả hai lượt boot, gồm cả lượt dùng SwiftShader. Package Manager từ chối cài trước khi hệ điều hành sẵn sàng. |

Máy cloud không có KVM. Build SDK 37 và việc emulator báo đúng API là
các kiểm tra độc lập với việc chạy ứng dụng trên Android 17.

## Giới hạn của target cao hơn

- Với target 31+, Push SDK còn các `PendingIntent` thiếu mutability. Cần giữ
  khả năng hủy/thay thế alarm đã đặt trên bản cũ vì cờ là một phần định danh.
  Luồng mở Activity từ receiver thông báo cũng cần chuyển sang Activity
  PendingIntent phù hợp và thử trên thiết bị. Với target 33+, cần xác minh
  quyền nhận thông báo và đường dẫn từ chối quyền.
- Đổi ngôn ngữ hiện dùng alarm để khởi động tiến trình mới sau khi hoàn tất
  checkpoint. Cần thiết kế và kiểm chứng việc khởi động lại khi exact alarm
  bị từ chối hoặc khi hệ điều hành hạn chế background activity launch.
- Ở các target mới, phải thử edge-to-edge và việc Android 16–17 bỏ qua một số
  ràng buộc hướng/kích thước trên màn hình lớn. Viewport GL, scissor, touch và
  đường restart khi đổi cấu hình của MIDlet phải được kiểm tra cùng nhau.
- Hai ELF ARM64 có PT_LOAD căn 64 KiB. Các ELF x86_64 và ABI 32 bit còn lại
  dùng 4 KiB. Kiểm tra ZIP 16 KiB đạt nhưng không thay cho thử trên thiết bị
  16 KiB. Cần nguồn native phù hợp hoặc thư viện tương đương đã xác minh để
  nâng các ELF thiếu căn chỉnh, cùng kiểm tra việc load JNI và GPU.
- Certificate Transparency, các ràng buộc reflection, Android nhận deep link
  thật từ trình duyệt, khôi phục Keystore, khởi động lại thiết bị và luồng
  mua/trồng/thu hoạch trên từng API vẫn cần kiểm thử thiết bị trước khi tăng
  target mặc định.

## Các tệp thay đổi

- Build và cấu hình gồm `app/build.gradle`, `build.gradle`,
  `buildSrc/build.gradle`, `gradle.properties`,
  `buildSrc/src/main/groovy/greenfarm/MergeOriginalSdkApk.groovy`.
- Android gồm `app/src/main/AndroidManifest.xml`, `GameRuntime.java`,
  `ActivityExitBroadcast.kt`, `online_auth_data_extraction_rules.xml`.
- Backend gồm `backend/src/config.js`, `backend/src/auth/router.js`.
- Công cụ APK, DEX và probe gồm `MergeCoreDex.java`, `PatchBackendEndpoint.java`,
  `android_build_tools.py`, `build_local_backend.py`,
  `prepare_core_dependencies.py`, `verify_runtime_apk.py`,
  `kotlin_test_tools.py`, `run_game_save_probe.py`,
  `run_save_transfer_probe.py`, `run_save_transfer_instrumentation.py`.
- Test gồm `test_activity_exit_broadcast.py`, `test_android_build_tools.py`,
  `test_binary_baseline.py`, `test_build_local_backend.py`,
  `test_game_manifest_components.py`, `test_merge_core_dex.py`,
  `test_render_surface_lifecycle.py`, `test_resource_pack_io.py`,
  `test_verify_runtime_apk.py`.
- Tài liệu gồm `README.md`, `docs/ANDROID_MODERNIZATION.md`.
