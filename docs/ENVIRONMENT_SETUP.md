# Môi trường cloud đã thiết lập

Ngày kiểm tra: 2026-11-12. Dùng checkout hiện có tại
`/workspace/green_farm3`; không tạo worktree mới nếu người dùng chưa yêu cầu.
Quy trình chính là build 108 lớp Java lõi, ghép dependency từ DEX gốc, rồi kiểm
thử Android riêng. Build toàn bộ Java SDK do JADX xuất vẫn là hướng nghiên cứu
còn lỗi; không dùng kết quả đó để đánh giá toolchain đã hỏng.

## Công cụ và dữ liệu được giữ lại

| Thành phần | Đường dẫn/phiên bản |
| --- | --- |
| JDK | `/workspace/tooling/jdk-21.0.9+10`, Temurin 21.0.9+10 |
| Gradle Wrapper | 8.11.1; Android Gradle Plugin 8.10.1 |
| Android SDK | `/workspace/tooling/android-sdk`, Platform28 revision6, Build Tools35.0.0 |
| Platform Tools | 37.0.3 |
| Emulator | 37.2.13, build16740729; API28 default x86 image revision4 |
| AVD hiện có | `/workspace/tooling/avds/GreenFarm28.avd`, 800x480, 160dpi |
| Cache Gradle | `/workspace/tooling/gradle-home` |
| Archive và công cụ bytecode | `/workspace/tooling/recovery-bytecode`; cache dependency trong `.local/bytecode-tools` |
| Installer đầy đủ | `/workspace/tooling/setup-greenfarm.sh` |
| Hướng dẫn khởi động đầy đủ | `/workspace/tooling/START.md` |

Installer mới thay thế vai trò của `/workspace/tooling/install.sh` cũ, vốn thiếu
JDK, emulator, system image và công cụ bytecode. Nó kiểm tra SHA-256 cho từng
archive; các archive Google còn được đối chiếu SHA-1 do Google công bố. Tải qua
HTTPS sử dụng proxy và CA được cấp, không tắt kiểm tra TLS. Không ghi token hoặc
mật khẩu proxy vào cấu hình; chỉ cập nhật hostname/port Gradle cần sử dụng.

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
export JAVA_HOME=/workspace/tooling/jdk-21.0.9+10
export ANDROID_HOME=/workspace/tooling/android-sdk
export ANDROID_USER_HOME=/workspace/tooling/android-user
export ANDROID_AVD_HOME=/workspace/tooling/avds
export GRADLE_USER_HOME=/workspace/tooling/gradle-home
export GREENFARM_ORIGINAL_JAR=/workspace/green_farm3/.local/bytecode-tools/greenfarm-original.jar
export GRADLE_OPTS="${GRADLE_OPTS:+$GRADLE_OPTS }-Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts"
export PATH="$JAVA_HOME/bin:$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"
cd /workspace/green_farm3
./gradlew --no-daemon --project-cache-dir /workspace/tooling/project-cache :core-source:assembleRecoveredDebug
```

APK đích là
`core-source/build/outputs/apk/recovered/debug/app-core-source-debug.apk`.
Task này kiểm tra provenance và việc ghép DEX, chữ ký, zip alignment, resource ID,
assets/native cùng metadata kích thước build. Chi tiết giới hạn phục hồi và kết
quả chạy game được ghi riêng trong [ORIGINAL_APK_RECOVERY.md](ORIGINAL_APK_RECOVERY.md).

Kiểm tra gọn đã chạy trong môi trường này, tất cả đều đạt và không bị skip:

```bash
python3 -m unittest discover -s tests -p 'test_prepare_core_dependencies.py' -v
python3 -m unittest discover -s tests -p 'test_update_build_revision.py' -v
python3 -m unittest discover -s tests -p 'test_as_available_slot.py' -v
python3 -m unittest discover -s tests -p 'test_s_request_lookup.py' -v
```

Tương ứng có 9 test dependency, 8 test metadata, 130 so sánh slot và 2.415 so sánh
lookup với bytecode gốc. Installer chạy lại hai lần thành công; JDK và Wrapper
báo đúng phiên bản, APK/DEX gốc khớp pin, thư viện host của emulator được phân
giải với đường dẫn thư viện đi kèm launcher. Trong công việc lưu cấu hình này,
không build lại toàn bộ hoặc khởi động lại emulator đang được kiểm thử song song.

## Cấu hình nháp đã lưu, runtime chưa áp dụng

Tool `update_environment_config_draft` trả `status: saved` và
`requires_publish: true`. Đọc lại xác nhận **revision2** chứa đúng toàn bộ nội dung
`install_script` và `start_skill` đã chuẩn bị. Danh sách repository hiện có được
giữ nguyên. Network vẫn là `restricted`, giữ preset `package_managers` và thêm
duy nhất rule tùy chỉnh `eve.gameloft.com`.

Đây là kết quả lưu cấu hình thành công, thay cho lỗi `INVALID_ARGUMENT` ở lần
onboarding trước. Không có kết quả chứng minh đã publish hoặc khôi phục một máy
mới. Người dùng cần review và save thay đổi trong environment settings, sau đó
publish môi trường khi giao diện cho phép.

Runtime hiện tại vẫn báo spec/observed revision1, trạng thái policy `enforced`,
`observations_current: true`; danh sách host tùy chỉnh đang rỗng. Vì vậy rule cho
`eve.gameloft.com` **chưa được áp dụng**. Không lặp lại request mạng khi policy
chưa đổi. Sau khi thay đổi thực sự được áp dụng, kiểm tra status rồi thử lại
request gốc qua proxy/CA có sẵn. Host từng bị proxy từ chối và guest từng lỗi DNS;
các quan sát đó không chứng minh dịch vụ đã ngừng hoạt động. Không thêm proxy
vào guest hoặc bỏ kiểm tra TLS để vượt lỗi này.

Hướng dẫn `start_skill` đã lưu gồm lệnh chạy lại emulator bằng software
acceleration, kiểm tra `sys.boot_completed`, tái sử dụng thiết bị hiện có và giữ
nguyên save khi thay APK khác chữ ký. Không suy ra gameplay, performance, âm
thanh hoặc tương thích Android mới chỉ từ các kiểm tra môi trường trên.
