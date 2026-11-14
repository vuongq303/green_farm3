# Môi trường cloud hiện tại

Cập nhật14/11/2026, checkout `/workspace/green_farm3`. Dùng skill
`cloud-environment-onboarding:setup`; draft install/start đã lưu nhưng chưa
publish. Không cần Firebase credential, Node, npm hoặc MySQL để build/chơi.
Các hướng dẫn backend trong lịch sử repository đã hết hiệu lực.

## Công cụ

* JDK25: `/workspace/.greenfarm/jdk-25` (Temurin từ image checksum-pinned).
* Android SDK: `/workspace/.greenfarm/android-sdk`, platform28,
  build-tools35.0.0, platform-tools, emulator37.2.13.
* Gradle9.7.1, AGP8.10.1, Kotlin2.4.21 theo pin repository.
* Cache: `/workspace/.cache/gradle`; JRE21 cho host test bytecode.
* Installer: `/workspace/.greenfarm/install.sh`; đã chạy thành công sau khi
  bỏ các bước backend. File bootstrap/môi trường nằm ngoài repository.

```bash
source /workspace/.greenfarm/activate.sh
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$PATH"
cd /workspace/green_farm3
./gradlew :app:assembleDebug --no-daemon
python3 -m unittest discover -s tests
python3 -m unittest discover -s tools -p 'test_*.py'
```

Cài APK cuối `app/build/outputs/apk/debug/app-debug.apk` bằng cập nhật (`adb
install -r` hoặc `:app:installDebug`), giữ đúng khóa ký. Không cài carrier APK
trong intermediates. Không uninstall/clear data/wipe AVD để vượt lỗi.
Pipeline kiểm provenance, namespace, metadata revision, policy offline,
chữ ký/alignment và toàn bộ asset/native payload.

## Android và SQLite

AVD `greenfarm-aosp28`, API28 AOSP x86, landscape800x480, density160,
`ANDROID_AVD_HOME=/workspace/.greenfarm/avd`. Chỉ khởi động khi cần kiểm thử,
không tạo lại AVD đã tồn tại. Giả lập không có KVM, rất chậm; phải đợi cả
`sys.boot_completed=1` và package service trước cài APK. Không chạy nhiều AVD.
Ví dụ:

```bash
export ANDROID_AVD_HOME=/workspace/.greenfarm/avd
export ANDROID_EMULATOR_HOME=/workspace/.greenfarm/android-user
"$ANDROID_HOME/emulator/emulator" -avd greenfarm-aosp28 -no-window -no-audio \
  -no-boot-anim -accel off -gpu swiftshader_indirect -memory 2048 -cores 1 \
  -no-snapshot -camera-back none -camera-front none -grpc-use-token
```

Sau khi APK đã cài xong và không cài/cập nhật lại trong lúc probe chạy:

```bash
python3 tools/run_game_save_probe.py --serial emulator-5554 --timeout 60 \
  --output /workspace/.greenfarm/sqlite-probe.json
```

Probe dùng SQLite Android thật, fixture riêng, không mở database người chơi.
Robolectric host test cần Maven mirror `gcp.mirrors.maven.org`; rule đã lưu vào
network draft nhưng runtime chưa áp dụng, dependency đó nhận403. Không coi host
SQLite suite đã chạy thành công; dùng native probe và ghi kết quả riêng.

Các tiến trình không được bảo đảm tồn tại sau restore cloud. MySQL/backend cũ
đã dừng; volume/credential riêng không bị xóa. Xem
[OFFLINE_IMPLEMENTATION.md](OFFLINE_IMPLEMENTATION.md) để biết sửa lỗi và giới
hạn kiểm thử hiện tại.
