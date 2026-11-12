# Green Farm 3 — phục hồi Android từ JADX

Mục tiêu hiện tại là backend độc lập do chủ dự án quản lý. Xem
[kiến trúc và API có bằng chứng](docs/backend/ARCHITECTURE.md) và
[backend local](backend/README.md). Bản thử M0 dùng Node24, emulator và APK
riêng; các client dịch vụ cũ khác chưa được di chuyển hết. Kết quả Android
được ghi tại [checkpoint M0](docs/backend/M0_CHECKPOINT.md).

Dự án đã build được **APK từ 108 lớp Java lõi**, gồm `ci`, `GLLib`, sprite,
tile renderer và pathfinding; SDK/wrapper dùng DEX gốc làm dependency.
M0 đã vượt Accept và hiển thị tutorial nông trại trên API28 qua backend local.
Chưa xác nhận đầy đủ thao tác farm hoặc save/load; xem báo cáo M0.

Công cụ đã kiểm tra: JDK 21.0.9, Gradle Wrapper 8.11.1, Android Gradle Plugin
8.10.1, Android SDK Platform 28, Build Tools 35.0.0 và Python 3.10 trở lên.
Giữ nguyên `minSdk=16`, `targetSdk=28` để phục hồi bản cơ sở trước.

Sau khi đặt `JAVA_HOME` và `ANDROID_HOME` tới JDK/SDK trên máy:

```sh
./gradlew --no-daemon :core-source:assembleRecoveredDebug
```

APK: `core-source/build/outputs/apk/recovered/debug/app-core-source-debug.apk`.
Task kiểm tra chữ ký, zip alignment, DEX merge và giữ nguyên 49 assets, 10 native.
Metadata kích thước DEX được sinh lại; phép kiểm tra integrity gốc vẫn giữ nguyên.
Khóa debug riêng nằm trong `.local/`, không commit và được giữ qua `clean`.

Đối chứng giữ toàn bộ DEX: `./gradlew :app:assembleBaselineDebug`, APK tại
`app/build/outputs/apk/baseline/debug/app-baseline-debug.apk`.
`./gradlew :app:assembleDebug` vẫn dùng toàn bộ source SDK export để nghiên cứu;
đường này còn lỗi decompile và tách riêng với dependency DEX trong bản Java lõi.

Gradle tự sinh resource/SDK R alias trong `app/build/generated`, không sửa resource
gốc. Không chỉnh sửa file sinh tự động; sửa Java trong `app/src/main/java`.

Xem [tiến độ từ APK gốc](docs/ORIGINAL_APK_RECOVERY.md),
[môi trường cloud đã lưu](docs/ENVIRONMENT_SETUP.md),
[báo cáo giai đoạn đầu](docs/RESTORATION.md),
[danh sách phương thức chưa phục hồi](docs/decompilation-blockers.json) và
[checksum dữ liệu gốc](docs/original-payload.sha256).
