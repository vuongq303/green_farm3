# Một module app và sửa crash ngay khi mở

Áp dụng trên commit mới nhất người dùng push: `a066490`. Giữ nguyên byte các
file pin Gradle9.7.1/checksum và daemon JVM25. Giữ cách dùng ExecOperations mà
người dùng đã sửa; cải tiến các helper Windows mà không quay lại phiên bản cũ.

Người dùng đã đính chính lỗi là **văng ngay khi mở**, chưa tới đăng nhập.
Lần sửa này tập trung vào APK khởi động và pipeline cài đặt, không giả thành
công cho một luồng đăng nhập chưa có backend/tài khoản tương ứng.

## Nguyên nhân đã tái hiện

Cài APK carrier cũ rồi mở trên emulator API28 cho đúng lỗi:

```text
FATAL EXCEPTION: main
RuntimeException: Unable to instantiate application javax.microedition.midlet.App
Caused by: ClassNotFoundException: Didn't find class "javax.microedition.midlet.App"
  at android.app.LoadedApk.makeApplication(...)
```

Carrier SHA-256:
`447e5ae3647bcc29cbb6bdb4f16d4b7daacbeb1603f1fc75b2b19d6370b0ccf9`.
Verifier đọc bảng ClassDefs của DEX thực sự và từ chối carrier vì thiếu `App`;
không chỉ tìm chuỗi tên lớp trong file.

Pipeline cũ tạo APK core trước khi ghép DEX gốc. Chạy custom task recovered
trước Run không đổi artifact mà module Android cung cấp cho IDE. Vì vậy một
APK recovered hoàn chỉnh tồn tại trên đĩa không bảo đảm IDE cài đúng APK đó.
Lỗi này xảy ra trước khi Activity/game/login được khởi tạo.

## Cấu trúc mới

`settings.gradle` chỉ include `app`. Đã bỏ `core-source/build.gradle`. `buildSrc`
là build logic nội bộ, không phải module ứng dụng Android thứ hai.

`app/build.gradle` quản lý preparation/resource/native/signing/ghép DEX. Chỉ
108 lớp lõi và các SDK R alias được biên dịch. `compile-view.jar` vẫn là
**compileOnly**; runtime SDK, MIDlet, Application và stream classes lấy từ DEX
gốc. Manifest không bị xóa/đổi Application, không thêm `App` rỗng.

```text
prepare resources + sources + compile-only dependencies
    → compile/D8 → packageDebug (carrier riêng trong intermediates)
    → mergeOriginalSdkDebugApk (SingleArtifact.APK.toTransformMany)
    → metadata APK cuối do AGP sinh
    → assembleDebug / installDebug / Android Studio deployment
```

Carrier: `app/build/intermediates/apk/debug/packageDebug/app-debug.apk`.
Work ghép hiện tại: `app/build/intermediates/original-sdk-merge/debug/`.
APK hoàn chỉnh: `app/build/outputs/apk/debug/app-debug.apk`.
Các thư mục được kiểm tra không chồng lấn; không overwrite carrier/input.
Task ghép không phụ thuộc `assembleDebug`, nên không có vòng lặp.
Xem [cập nhật đường dẫn và log chẩn đoán Windows](WINDOWS_APK_MERGE_PATHS.md).

AGP tự cập nhật `output-metadata.json` và APK IDE listing/redirect. Task cài
dùng final artifact; cấu hình `.run/app.run.xml` chỉ có before-run Gradle chuẩn,
không chạy recovered task. `generateDebugSources` cũng chạy các preparation
task để source sinh ra và compile-only jar có sẵn khi IDE setup từ checkout mới.

Mỗi artifact cuối phải qua `tools/verify_runtime_apk.py` trước khi được công bố:
App/launcher/MIDlet có định nghĩa thật, superclass cần thiết có mặt, không trùng
class giữa DEX, package/manifest đúng, assets/native không đổi. Metadata kích
thước DEX vẫn được sinh lại, guard gốc không bị tắt. Release chưa bật vì pipeline
này dùng khóa debug nghiên cứu.

## Windows và JDK25

- Python được chọn qua `pythonExecutable`, mặc định `python` trên Windows.
- Child process dùng JDK của daemon Gradle, không lệ thuộc JAVA_HOME cũ của
  terminal/Android Studio; Python xử lý source/resource ở UTF-8.
- Gọi `java(.exe) -jar apksigner.jar`, `zipalign(.exe)`, `keytool(.exe)` bằng
  argument array; không chạy batch file qua shell. Giữ khóa `.local` qua clean.
- Ba harness kiểm thử đã dùng ASM nội bộ JDK cũ. JDK25 bỏ package đó; thay bằng
  ASM9.7.1 riêng cho test/audit, có pin SHA-256 từ Maven chính thức. Không đưa
  thư viện kiểm thử này vào APK hoặc giảm assertion/skip để làm test xanh.

## Kiểm chứng

- Actual Gradle Tooling API lấy AGP v2 Versions/BasicAndroidProject/
  AndroidProject/AndroidDsl/ProjectSyncIssues thành công, `SYNC_ERRORS=0`, chỉ
  có variant debug của `:app`. IDE listing resolve tới metadata của APK cuối.
- `:app:assembleDebug` và `:app:installDebug` thực thi thành công trên
  Gradle9.7.1/JDK25. Lượt không đổi tái dùng toàn bộ42 task.
- APK cuối: một DEX,6.397 định nghĩa lớp, Application thật
  `javax.microedition.midlet.App`, launcher `com.gameloft.android.ANMP.GloftGF2F.Start`.
  49 assets +10 native giữ nguyên byte/cách nén;1.858 resource ID khớp public.xml.
- APK đọc ngược từ thiết bị sau `installDebug` có SHA trùng file build chuẩn.
  Đã mở và hoạt động trên API28, không còn FATAL/missing Application ở các log
  kiểm tra sau sửa; tiếp tục được tới UI của game.
- 104 test phục hồi/đóng gói/runtime và13 test resource đạt, không skip.
  Ba test ASM bị ảnh hưởng cũng đạt với cả JDK25 và JDK21.

Chi tiết/hash/log/ảnh nằm trong `app-unification-result.json` và thư mục
`app-unification/` cạnh báo cáo này. Log đầy đủ trên workspace được giữ tại
`build/reports/app-unification/`.

Chưa bấm Run trực tiếp trong Android Studio Windows hoặc thử điện thoại của
người dùng; bằng chứng IDE là model/metadata của chính AGP và cài đặt chuẩn
`installDebug`. Chưa xác minh riêng đăng nhập Facebook, các failure case tài
khoản, Android mới, âm thanh hoặc toàn bộ gameplay/save-load. Backend local M0
vẫn là dịch vụ riêng; APK hoàn chỉnh không tự triển khai server.

## Sử dụng

Mở project → **Sync Gradle → chọn Android App `app` → chọn thiết bị → Run**.
Không chọn APK trung gian, không dùng recovered task hoặc cài thủ công.
