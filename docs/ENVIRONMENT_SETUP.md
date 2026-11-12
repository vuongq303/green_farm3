# Môi trường cloud đã thiết lập

Ngày kiểm tra: 2026-11-12. Dùng checkout hiện có tại
`/workspace/green_farm3`; không tạo worktree mới nếu người dùng chưa yêu cầu.
Project chỉ có module Android `app`. Task chuẩn biên dịch 108 lớp Java lõi,
ghép dependency từ DEX gốc và cung cấp cùng APK hoàn chỉnh cho assemble, install
và Android Studio Run. Không cần module hoặc task recovered/baseline riêng.

## Công cụ và dữ liệu được giữ lại

| Thành phần | Đường dẫn/phiên bản |
| --- | --- |
| JDK | `/workspace/tooling/jdk-25` → `/workspace/tooling/jdk-25-install/jdk-25.0.5.0.3`, Oracle JDK25 |
| Gradle Wrapper | 9.7.1, daemon criteria JVM25; Android Gradle Plugin 8.10.1 |
| Android SDK | `/workspace/tooling/android-sdk`, Platform28 revision6, Build Tools35.0.0 |
| Platform Tools | 37.0.3 |
| Emulator | 37.2.13, build16740729; API28 default x86 image revision4 |
| AVD hiện có | `/workspace/tooling/avds/GreenFarm28.avd`, 800x480, 160dpi |
| Cache Gradle | `/workspace/tooling/gradle-home` |
| Archive và công cụ bytecode | `/workspace/tooling/recovery-bytecode`; cache dependency trong `.local/bytecode-tools` |
| Installer đầy đủ | `/workspace/tooling/setup-greenfarm.sh` |
| Hướng dẫn khởi động đầy đủ | `/workspace/tooling/START.md` |
| Backend local M0 | Node v24.22.1, standard library, session trong RAM |

Installer mới thay thế vai trò của `/workspace/tooling/install.sh` cũ, vốn thiếu
JDK, emulator, system image và công cụ bytecode. Nó kiểm tra SHA-256 cho từng
archive; các archive Google còn được đối chiếu SHA-1 do Google công bố. Tải qua
HTTPS sử dụng proxy và CA được cấp, không tắt kiểm tra TLS. Không ghi token hoặc
mật khẩu proxy vào cấu hình; chỉ cập nhật hostname/port Gradle cần sử dụng.

Pin Wrapper/daemon do người dùng đưa lên được giữ nguyên byte. SHA-256 của
Gradle9.7.1 là
`acd53f1edaf02f1a8ff99879f8a34b302661a057d9b063ae9e35b552f804d20a`.
Installer kiểm tra các pin này rồi dùng Wrapper/cache hiện có; không ghi lại
file pin hoặc cài đè bản Gradle cũ.

Archive JDK được giữ tại
`/workspace/tooling/oracle-jdk-25-linux-x64.tar.gz`, SHA-256:
`6deafad80454d1f968105b1f582d89b8c2e88a5dfc98116a8226e0ec2fd5df8e`.
URL cố định để tái lập là
`https://download.oracle.com/java/25/archive/jdk-25.0.5.0.3_linux-x64_bin.tar.gz`;
file `.sha256` chính thức tại URL đó đã được đọc qua HTTPS và khớp archive.
Provenance lần tải ban đầu từ Oracle `latest` nằm tại
`/workspace/tooling/jdk-25-provenance.json`. Installer mới dùng cache đã xác minh
hoặc URL cố định, không dựa vào nội dung thay đổi của `latest`.

Installer giữ nguyên AVD đã có và dữ liệu game; không chạy/dừng emulator hay ADB.
Nếu AVD chưa tồn tại, nó tạo cấu hình mới; không dùng `-wipe-data`. Snapshot có
thể giữ file và cache, nhưng tiến trình emulator cần được khởi động lại khi một
phiên mới thực sự cần kiểm thử Android.

## Lệnh cho instance hiện tại

Làm mới và kiểm tra công cụ:

```bash
bash /workspace/tooling/setup-greenfarm.sh
```

Mỗi phiên lệnh cần kích hoạt môi trường:

```bash
export JAVA_HOME=/workspace/tooling/jdk-25
export ANDROID_HOME=/workspace/tooling/android-sdk
export ANDROID_USER_HOME=/workspace/tooling/android-user
export ANDROID_AVD_HOME=/workspace/tooling/avds
export GRADLE_USER_HOME=/workspace/tooling/gradle-home
export GREENFARM_ORIGINAL_JAR=/workspace/green_farm3/.local/bytecode-tools/greenfarm-original.jar
export GRADLE_OPTS="${GRADLE_OPTS:+$GRADLE_OPTS }-Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"
cd /workspace/green_farm3
./gradlew --no-daemon --project-cache-dir /workspace/tooling/project-cache :app:assembleDebug
```

APK đích duy nhất là `app/build/outputs/apk/debug/app-debug.apk`. Pipeline chuẩn
kiểm tra provenance, ghép DEX, chữ ký, zip alignment, resource ID, assets/native
và metadata kích thước build. Không cài APK carrier trong `intermediates`.

Sau khi thiết bị đích sẵn sàng và đã bảo toàn dữ liệu/chữ ký, cài bằng task chuẩn:

```bash
./gradlew --no-daemon --project-cache-dir /workspace/tooling/project-cache :app:installDebug
```

Không uninstall hoặc wipe-data để vượt lỗi khác chữ ký. Hướng dẫn `start_skill`
giữ quy trình backup app/AVD, tái sử dụng emulator hiện có và kiểm tra
`sys.boot_completed`. Android Studio dùng JVM25 và cấu hình Android App `app`
chuẩn; không thêm before-run task tùy chỉnh.

Backend M0 chạy trong phiên terminal bền vững riêng:

```bash
cd /workspace/green_farm3/backend
PUBLIC_BASE_URL=http://10.0.2.2:8787 npm start
```

Host kiểm tra `http://127.0.0.1:8787/healthz`; emulator gọi host qua `10.0.2.2`.
M0 không cần `npm install`, không forward tới dịch vụ Gameloft. Session chỉ nằm
trong RAM; chưa có PostgreSQL, persistent account/farm hoặc production auth.
Các network client legacy khác vẫn còn, nên chưa thể khẳng định toàn bộ game
độc lập khỏi Gameloft. APK hoàn chỉnh không tự khởi động backend.

## Kiểm chứng và giới hạn

Installer JDK25 chạy lại hai lần thành công. Wrapper báo Gradle9.7.1,
launcher JVM25 và daemon criteria25; APK/DEX gốc khớp pin, thư viện host emulator
được phân giải bằng đường dẫn thư viện đi kèm launcher. Hash của file Wrapper,
daemon criteria, khóa debug và cấu hình AVD không đổi sau lượt chạy lặp lại.
Log/proof được giữ tại:

- `/workspace/tooling/setup-greenfarm-jdk25-repeat.log`
- `/workspace/tooling/setup-greenfarm-jdk25-verification.json`

Kiểm chứng ứng dụng là phần riêng: **117 test đạt, không skip** (104 test phục
hồi/đóng gói/runtime và13 test resource); assemble/install thực tế thành công và
APK chuẩn đã khởi động tới UI game trên API28. Xem
[APP_BUILD_AND_STARTUP.md](APP_BUILD_AND_STARTUP.md) cùng artifact/log được dẫn
ở đó. Phần cập nhật cấu hình môi trường không chạy lại build hoặc GUI; không có
tuyên bố đã bấm Run trực tiếp trong Android Studio Windows. Chưa xác nhận toàn
bộ gameplay/save-load, đăng nhập Facebook, âm thanh hoặc Android mới.

## Cấu hình nháp đã lưu, runtime chưa áp dụng

Tool `update_environment_config_draft` trả `status: saved` và
`requires_publish: true`. Đọc lại xác nhận **revision4** chứa đúng toàn bộ nội dung
`install_script` và `start_skill` đã chuẩn bị. Danh sách repository hiện có được
giữ nguyên; secrets/runtime requirements không đổi. Network vẫn `restricted`,
giữ preset `package_managers`, không có custom rule. Rule truy cập vendor từ
workflow cũ đã được gỡ ở revision3; workflow local hiện tại không cần rule đó.

Đây là kết quả lưu cấu hình thành công, thay cho lỗi `INVALID_ARGUMENT` ở lần
onboarding trước. Không có kết quả chứng minh đã publish hoặc khôi phục một máy
mới. Người dùng cần review và save thay đổi trong environment settings, sau đó
publish môi trường khi giao diện cho phép.

Runtime quản lý vẫn báo spec/observed revision1, policy `enforced`,
`observations_current: true`, danh sách host tùy chỉnh rỗng. Trạng thái này khác
với việc file/toolchain trong instance đã được chuẩn bị và kiểm tra trực tiếp;
nó không xác nhận đã áp dụng hoặc publish draft revision4. Các tiến trình đang
chạy không được bảo đảm tồn tại khi khôi phục snapshot.
