# Báo cáo phục hồi Green Farm 3

> Báo cáo lịch sử của giai đoạn chưa có APK gốc. Người dùng đã bổ sung APK tại
> commit `228cc7d`; tình trạng mới và bản build đối chứng được ghi trong
> [ORIGINAL_APK_RECOVERY.md](ORIGINAL_APK_RECOVERY.md).

## Trạng thái

**Chưa tạo được APK, chưa chạy trò chơi.** Resource/manifest đã link được,
native đã merge đủ năm ABI. Build đầy đủ dừng ở Java; unit test Android chưa chạy.
Không có APK/DEX/smali gốc trong workspace để đối chiếu mã riêng của Gameloft.

## Phân tích và 10 vấn đề ưu tiên

| # | Vấn đề và bằng chứng | Xử lý hiện tại |
|---|---|---|
| 1 | 11 phương thức có `Method not decompiled`, đặc biệt `javax/microedition/b/b.java:249` ghi RecordStore. `n()` bắt lỗi từ writer; `GLLib` dùng RecordStore cho lưu tiến trình. | Giữ nguyên, chưa bịa thuật toán hoặc CRC/backup. Cần DEX gốc. Xem `decompilation-blockers.json`. |
| 2 | Java không hợp lệ: 47 khai báo `??` ban đầu; initializer trong hai interface `ax`, `t`; còn lỗi generic, lớp lồng và exception flow. | Sửa phần có bằng chứng trực tiếp hoặc nguồn/bytecode chính thức. Còn `PushNotification/j.java:58` trộn String/stream/exception vào cùng register. |
| 3 | Obfuscation tạo tên không biểu diễn được như hiện tại trong Java: `android.support.v7.c.a` vừa là lớp vừa là package; inner class mất `static`. | Đã ghi nhận qua compiler, chưa đổi tên hàng loạt hoặc suy diễn inheritance. Cần bảng ánh xạ có đối chiếu bytecode. |
| 4 | SDK nằm lẫn trong 5.365 file Java, dependency block trống; thiếu cả một số lớp R của support library. | Dùng `org.apache.http.legacy` có sẵn trong SDK. Không thêm AAR trùng toàn bộ package. Google/Firebase 15.0.x, Facebook 5.5.1, Unity 3.0.1. |
| 5 | Năm `R.java` dịch ngược xung đột/giữ ID cũ; 195 resource literal khác nằm trong bảy file ngoài R.java. | Bỏ app R khỏi nguồn staging, sinh bốn SDK R alias, giữ styleable/index; AAPT2 `--stable-ids` giữ 1.858 ID từ `public.xml`. |
| 6 | 23 ảnh `.9.png` là PNG đã biên dịch với `npTc`, thiếu viền nguồn. | Tái tạo viền từ metadata trong output, xác nhận AAPT2 round-trip giữ pixel, stretch, padding, `npOl`. |
| 7 | 12 giá trị style enum/flags/dimension được export thành số; một ID chỉ có public declaration. | Đổi về ký hiệu xác thực với SDK `attrs.xml`; khai báo lại 107 ID đã có trong `public.xml`, không tự gán ID. |
| 8 | Manifest mang metadata APK trùng Gradle, `package` không phù hợp AGP8 và text rác sau intent-filter. | Bỏ phần decode thừa; giữ application, activities, services, providers và permissions. Bản merged debug có `debuggable=true`. |
| 9 | 10 `.so` nằm ở `src/main/lib`, không phải `jniLibs`; một số tên JNI không khớp Java. | Cấu hình sourceSets để đóng gói, kiểm tra 10 file merged khớp byte gốc. Chưa sửa các JNI mismatch chưa chứng minh cần cho baseline. |
| 10 | Thiếu Wrapper, chưa có workflow tái lập; chưa có emulator/system image và `/dev/kvm`. Android mới còn thay đổi storage, thiết bị ID, receiver/PendingIntent. | Thêm Wrapper 8.11.1 có SHA-256; dùng AGP 8.10.1, JDK 21.0.9, SDK28/Build Tools35. Giữ target28; chỉ kiểm tra Android mới sau baseline. |

## Cấu trúc và luồng khởi động

- Game package có 236 file; `S800x480` có 109 file. `GLLib.java` dài 8.677 dòng,
  `ci.java` dài 72.441 dòng. Có 352 cảnh báo JADX trong `S800x480`; cảnh báo không
  tự chứng minh một phương thức sai, nhưng cần kiểm tra bằng bytecode/test.
- `Start.onCreate` → `Nh` → runnable `bt` lấy kích thước → `bu` → `wrapper.ae`
  chọn Activity → `S800x480.GloftGF2F`/`MIDlet` → `mj()` tạo `ci` và `Init()`.
- `GLLib.run()` rỗng không đủ để kết luận mất vòng lặp: `onDrawFrame()` gọi
  `jJ()` để cập nhật/vẽ. Không thêm một vòng lặp game mới.
- MIDlet tải `nativeU` qua `NativeUtils` và tải `AdsManager` trong child lifecycle.
  Sai cách đóng gói native có thể làm lỗi trước khi game vào màn hình chính.
- 49 assets khoảng 24 MiB: pack game, locale, IAP và dữ liệu feed. Chưa đủ bằng
  chứng xác nhận dữ liệu hoàn chỉnh so với APK. Giữ nguyên toàn bộ file.
- Save dùng internal hoặc app-specific external storage. Writer bị mất logic có
  liên quan CRC và bản ghi dự phòng `_saved*`, `_CRC*`; không thay bằng writer mới
  chỉ để vượt biên dịch.

## Các nhóm thay đổi

1. **Công cụ build:** Wrapper 8.11.1, SHA-256 distribution; `.gitignore`; giữ
   AGP8.10.1/min16/target28/Java source8. Máy hiện có Temurin21.0.9+10 đã xác thực
   checksum bản phát hành chính thức. Java runtime21 ban đầu có compiler module
   nhưng không có binary `javac`; không phải mọi lỗi ban đầu đều do thiếu JDK.
2. **Resource/manifest/native:** chuyển đổi trong `app/build/generated`, original
   resources giữ nguyên. `restoreResources` làm lại đầy đủ sau `clean`;
   `generateStableResourceIds` và SDK R aliases là dependency của `preBuild`.
   Nguồn Java được Sync sang staging, thay đúng bốn SDK R và bỏ duy nhất app R
   dịch ngược. Không loại package gameplay hay SDK để ép build thành công.
3. **Java có thể khôi phục chắc chắn:** `ax`, `t` dùng initializer hợp lệ, giữ
   nguyên 148 biểu thức và thứ tự tính ID; 32 builder locals lấy kiểu trực tiếp
   từ constructor; sáu khai báo khác tách kiểu theo nơi sử dụng có sẵn.
4. **SDK có nguồn đối chiếu:** Facebook `AppEventStore` và Unity
   `Cache.setFileContent` phục hồi từ upstream đúng phiên bản; Google `zzbl`,
   `zzcz` và khai báo exception của `zzbk` từ artifact Maven15.0.0.
   Xem [nguồn và checksum](BYTECODE_RECOVERY.md).

Nguồn Facebook:
https://raw.githubusercontent.com/facebook/facebook-android-sdk/sdk-version-5.5.1/facebook-core/src/main/java/com/facebook/appevents/AppEventStore.java

Nguồn Unity: tag `3.0.1`, commit
`0885aed0c58fdec731065e189a8c31c98893131d`:
https://github.com/Unity-Technologies/unity-ads-android/blob/3.0.1/lib/src/main/java/com/unity3d/services/core/api/Cache.java

## Chạy lại

Máy cần JDK21 đã kiểm tra, Python3.10+, SDK `platforms;android-28`,
`build-tools;35.0.0`, `platform-tools`. Đặt `JAVA_HOME`, `ANDROID_HOME` phù hợp
với máy và cấu hình proxy/CA tin cậy nếu môi trường yêu cầu. Không tắt TLS.

Trong cloud hiện tại:

```sh
cd /workspace/green_farm3
export JAVA_HOME=/workspace/tooling/jdk-21.0.9+10
export ANDROID_HOME=/workspace/tooling/android-sdk
export ANDROID_USER_HOME=/workspace/tooling/android-user
export GRADLE_USER_HOME=/workspace/tooling/gradle-home
export GRADLE_OPTS='-Djavax.net.ssl.trustStore=/etc/ssl/certs/java/cacerts'
./gradlew --no-daemon --console=plain \
  --project-cache-dir /workspace/tooling/project-cache \
  --continue :app:assembleDebug :app:mergeDebugNativeLibs :app:testDebugUnitTest
```

`GRADLE_USER_HOME` của máy này chứa cấu hình proxy cho Gradle. Không commit cấu
hình máy hoặc credential vào project. Không cần server HTTP để build ứng dụng.
Mỗi task cloud đã cô lập; dùng checkout hiện tại, không cần Git worktree mới.

Kiểm tra resource/native riêng:

```sh
./gradlew --no-daemon :app:processDebugResources :app:mergeDebugNativeLibs
```

Kiểm tra tool phục hồi, gồm AAPT2 round-trip khi `ANDROID_HOME` đã đặt:

```sh
python3 -m unittest discover -s tests -v
python3 -m unittest discover -s tools -p 'test_*.py' -v
python3 tools/audit_decompilation.py
sha256sum -c docs/original-payload.sha256
```

## Lịch sử kiểm tra và giới hạn

| Lượt | Kết quả |
|---|---|
| Ban đầu | AAPT2 không compile được compiled nine-patch. |
| Sau khôi phục ảnh/manifest/native | Chạy qua merge resource; link dừng vì 12 giá trị style dạng số. |
| Sau sửa style và Java có bằng chứng | Link resource/native thành công; còn 15 lỗi parse trong ba file. |
| Sau khôi phục Google bytecode và ổn định resource ID | Build đầy đủ exit1: còn ba diagnostic parse, cùng tại `PushNotification/j.java:58`. Đây chưa phải tổng lỗi semantic của dự án. |
| Kiểm tra độc lập resource/native | Gradle thành công. Tất cả 1.858 ID linked trùng `public.xml`; 10 native merged giữ nguyên byte. Bốn SDK R alias biên dịch riêng với AGP R.jar và Android.jar thành công. |
| Tool tests | 20 tests passed, không skip khi cung cấp SDK. Đây là test tool phục hồi, không phải test gameplay. |
| Dữ liệu | So sánh Git xác nhận cả 392 file assets/native/resources được track giữ nguyên byte; checksum 49 assets + 10 native lưu trong `original-payload.sha256`. |

Log build đầy đủ trong máy:
`build/reports/restoration/build-final.log` (exit1).
Một bản log của lượt build này được lưu cùng mã tại
[build-validation.log](build-validation.log).
Log thăm dò semantic riêng:
`build/reports/restoration/semantic-probe.log`, command/source list/exit code
cùng thư mục. Phép thăm dò bỏ riêng unit notification không parse được, dùng
nguồn staged cùng Android.jar, Apache legacy jar và AGP R.jar; **không** thay
đổi build, không tạo APK và không xác nhận phần code còn lại hoạt động.
Lượt thăm dò với classpath trên báo 3.702 diagnostic lỗi và 81 cảnh báo, exit4;
con số có lỗi dây chuyền và thiếu unit đã loại, không phải 3.702 bug độc lập.
Các ví dụ xác nhận được gồm package/class trùng tên, enum nằm trong inner class
không static, thiếu support R, sai generic và truy cập private field giữa những
lớp JADX tách ra. Kết quả này thay cho con số 3.605 của lượt thăm dò sơ bộ dùng
nguồn raw và classpath chưa đầy đủ.
Danh sách marker tại [decompilation-blockers.json](decompilation-blockers.json)
chỉ là cận dưới của lỗi còn lại.

## Việc tiếp theo cần APK/DEX gốc

Cần đúng bản `versionName=4.4.2`, `versionCode=44220`, package
`com.gameloft.android.ANMP.GloftGF2F` (nếu bản cài còn OBB/data đi kèm, giữ lại
để đối chiếu). Không có đủ bytecode để khôi phục chính xác writer RecordStore,
wrapper và luồng exception/register của `PushNotification/j.java` từ export này.

Sau khi có đầu vào gốc:

1. Disassemble DEX, lập bảng ánh xạ tên cho các xung đột package/class, đối chiếu
   các method hỏng thay vì sửa inheritance hoặc thêm stub hàng loạt.
2. Phục hồi writer RecordStore/CRC/backup, dựng build baseline giữ target28.
3. Cài và kiểm tra splash, farm, touch, audio, save/load, pause/resume trên
   thiết bị/emulator phù hợp; chỉ sau đó kiểm tra Android mới.

APK debug được ký bằng khóa khác bản Gameloft có thể không cập nhật đè bản gốc.
Không tự gỡ bản cài đang chứa save để vượt lỗi chữ ký.

## SDK online và JNI

Ads/IAP/social được gọi từ cả `ci`, wrapper và MIDlet lifecycle. Phương án tiếp
theo là lập call graph và cô lập initialization/callback ở ranh giới dịch vụ đã
xác định; giữ nguyên mã gameplay và định dạng save, không xóa package hoặc giả
lập kết quả thanh toán/reward. Chưa áp dụng offline facade khi chưa có baseline.

Trong cả năm ABI, JNI export của ba hàm Unity-mode/orientation mang tên
`com.gameloft.glads.Utils`, còn Java khai báo tại `AndroidUtils`. Hiện caller
`IsUnityMode` bắt `UnsatisfiedLinkError` và fallback false. `DisableProviders`
không có Java caller; browser unread callback cũng bắt lỗi linkage. Giữ nguyên
để kiểm tra khi chạy thực tế; chưa chứng minh chúng chặn baseline. Các lớp
Gameloft mà native tham chiếu đều có nguồn. ARM64 LOAD alignment là `0x10000`,
không có bằng chứng để kết luận native không hỗ trợ page16KiB chỉ vì tuổi mã.
