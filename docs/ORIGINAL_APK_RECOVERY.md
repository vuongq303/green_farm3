# Tiếp tục phục hồi từ APK gốc

> Báo cáo đối chứng của checkpoint `ce19d54`. Mục tiêu mới dùng backend độc lập;
> xem [kiến trúc/API](backend/ARCHITECTURE.md) và [mốc local M0](backend/M0_CHECKPOINT.md).
> Hướng cho phép EVE ở lịch sử bên dưới không còn là kế hoạch hiện tại.

## Đầu vào đã xác minh

Đã fast-forward checkout tới commit GitHub
`228cc7dd7b4b413f430e93c74cc147a1ffb8a23c`, chỉ bổ sung `green-farm-3.apk`;
giữ lại toàn bộ sửa đổi phục hồi trước đó.

- Package: `com.gameloft.android.ANMP.GloftGF2F`.
- Phiên bản: `4.4.2`, versionCode `44220`, min16/target28.
- APK SHA-256: `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
- DEX SHA-256: `3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4`.
- Toàn bộ 49 assets và 10 thư viện native trong source export khớp byte với APK.

## Ba đường build riêng biệt

### Lõi Java biên dịch được

```sh
./gradlew --no-daemon :core-source:assembleRecoveredDebug
```

Build đã đạt `BUILD SUCCESSFUL`. APK chính:
`core-source/build/outputs/apk/recovered/debug/app-core-source-debug.apk`.
Chỉ dùng artifact này; `core-source/build/outputs/apk/debug/` là carrier D8 trung
gian chưa có toàn bộ dependency, không phải bản để cài.

108 lớp `S800x480` biên dịch từ Java, gồm `ci`, `GLLib`, `GloftGF2F`, sprite,
tile renderer và pathfinding. `l` giữ bytecode gốc do DEX đã shrink các method
DataInput mà Java yêu cầu; `g` là token synthetic rỗng không có source export.
Không thêm method rỗng hoặc đổi tên runtime để vượt hạn chế này.

dex2jar chỉ dùng cho compile-only classpath. Compile view giải quyết một xung
đột class/package bằng visibility tra cứu; **không đóng gói jar này**. Runtime
SDK/wrapper lấy trực tiếp ClassDef/instructions từ DEX gốc qua dexlib2. Có 6.250
lớp gốc giữ lại; 147 lớp overlay gồm 108 lớp core và R sinh lại. Kết quả có 6.397
lớp, DEX035; canonical DEX trước/sau xác nhận toàn bộ nhóm giữ lại và overlay.
Không chuyển SDK runtime qua dex2jar và không sửa logic quảng cáo/IAP/online.

Phép kiểm tra integrity `Pt/bh` vẫn giữ nguyên. Lượt thử đầu phát hiện metadata
`REVISION-2408` cũ không khớp DEX mới, làm xuất hiện thông báo local dùng câu chữ
“account banned”. Đã bổ sung bước sinh lại **riêng giá trị build revision** trong
resource `content.bin` của APK output theo tổng kích thước DEX mới XOR140.988.
Giá trị được tính lại mỗi lần build và ghi trong provenance cạnh APK.
DEX gốc7.655.124 tương ứng revision7.792.744 đã được xác thực trước.
Các byte định nghĩa khác và resource nguồn gốc không thay đổi; không vô hiệu hóa
guard hoặc tạo trạng thái tài khoản giả. [Bằng chứng](BUILD_REVISION_METADATA.md).

Đây là bản Java lõi + dependency gốc, chưa phải toàn bộ 5.365 file Java SDK.
Runtime của artifact Java được kiểm tra riêng với APK gốc/đối chứng; không suy
ra kết quả chạy chỉ từ việc biên dịch thành công.

### Bản đối chứng có thể tạo APK

```sh
./gradlew --no-daemon :app:assembleBaselineDebug
```

Kết quả: `app/build/outputs/apk/baseline/debug/app-baseline-debug.apk`.

Task này chạy pipeline AGP xử lý resource và manifest debug đã phục hồi, sau đó
đóng gói cùng **DEX gốc**, assets và native gốc. Dùng Build Tools35.0.0 để
zipalign/sign/verify; mỗi lần chạy đều xác nhận cả bytes và cách nén của 60
DEX/assets/native entries không thay đổi. Có file `.provenance.json` đi kèm.
Chữ ký v1/v2/v3 đã xác minh; manifest có debug enabled. Không patch quảng cáo,
thanh toán, root detection, save hay logic trò chơi.
[Log Gradle đã lưu](baseline-build-validation.log).

**Đây là binary baseline, không phải bằng chứng toàn bộ Java đã biên dịch được.**
Nó cho phép so sánh hành vi trước khi thay dần phần mã bị decompiler làm hỏng.
File APK đầu vào được kiểm tra checksum cố định; nếu thay phiên bản cần xem xét
lại nguồn và provenance. `.local/baseline-debug.keystore` được tạo riêng, không
commit và không bị `clean` xóa; giữ khóa nếu muốn cập nhật bản debug mà giữ save.

### Export toàn bộ Java SDK còn lỗi decompile

```sh
./gradlew --no-daemon :app:assembleDebug
```

Sau khi sửa những phương thức bên dưới, build đã vượt qua lỗi parse `??` cuối
cùng nhưng vẫn dừng ở ít nhất 1.000 diagnostic semantic (giới hạn log đang đặt
1.000): package/class trùng tên, mất metadata lớp lồng, synthetic accessor bị
JADX inline, sai kiểu và dependency compile-time thiếu. Không xóa các package
khỏi build này để che lỗi. Bản đối chứng không dùng Java export bị hỏng.

## Phương thức đã phục hồi từ DEX

| Thành phần | Bằng chứng và kiểm tra |
|---|---|
| RecordStore writer `javax.microedition.b.b.b(int,byte[],int,int)` | Khôi phục CRC, record header, backup retention/checkpoint625, generation cap10000 và exception cleanup. 8 test chạy chính phương thức sản phẩm; [chi tiết](RECORDSTORE_RECOVERY.md). |
| Notification `j.a(Context,Bitmap,String)` | Tách register bị merge theo exception table, giữ cả việc bỏ qua boolean `compress()` và hành vi close-null của bản gốc. |
| Wrapper `b.ea(String)` | Khôi phục ZIP entry/prefix size và cách trả kết quả một phần khi gặp lỗi; không còn missing-method throw. |

Hai phương thức notification/wrapper được kiểm tra bằng harness Java cô lập:
18 assertion với ZIP/file IO thật và adapter Bitmap riêng của test; không thêm
stub vào ứng dụng. [Bằng chứng và giới hạn](JAVA_BLOCKERS_RECOVERY.md).

Đã phục hồi synthetic accessor mà JADX inline sai và một nhóm branch/loop,
class-shadowing trong `ci`. Xem [chi tiết peer/accessor](CORE_PEER_ACCESS_RECOVERY.md)
và [bằng chứng `ci`](CORE_CI_RECOVERY.md). Các sửa này thuộc đường Java, không thay
DEX của bản đối chứng. Probe core với dependency bytecode chỉ là chẩn đoán, không
chứng minh APK từ core Java chạy được. Thêm `compileOnly` JSR-3053.0.2 cho
annotation SDK cũ; không đóng gói thêm bản SDK trùng lớp.

Các kiểm tra so sánh bytecode chuyên biệt bao gồm: 10.800 trường hợp định dạng số,
2.000 Arabic trong GLLib, 7 vector LZMA, 800 decoder ảnh, 1.200 layout chữ, 5.000
numeric-prefix và 2.000 Arabic trong CI, 4.000 call-trace tile, 2.000 pathfinding,
448 cleanup HTTP và 516 trường hợp TCP. Đây là fixture/unit kiểm tra thuật toán
và lỗi với bytecode chuyển đổi đối chiếu smali, không thay thế test chơi farm.
Xem [GLLib](GLLIB_RECOVERY.md), [sprite](CORE_ASPRITE_RECOVERY.md),
[CI](CORE_CI_RECOVERY.md) và [các lớp peer](CORE_PEER_ACCESS_RECOVERY.md).

Smali gốc giữ tại `/workspace/tooling/original-smali`, baksmali3.0.3 từ SDK;
CFR và dex2jar dùng để đối chiếu bổ sung. Smali và DEX là bằng chứng chính,
không coi dex2jar không báo lỗi là bảo đảm hành vi được giữ hoàn toàn.

## Môi trường và kiểm tra lại

Dùng JDK21.0.9, Gradle8.11.1, AGP8.10.1, SDK Platform28, Build Tools35.0.0 và
Python3.10+. Trong cloud, các biến và CA/proxy đã chuẩn bị như hướng dẫn
[giai đoạn đầu](RESTORATION.md#chạy-lại).

```sh
export JAVA_HOME=/workspace/tooling/jdk-21.0.9+10
export ANDROID_HOME=/workspace/tooling/android-sdk
export PATH="$JAVA_HOME/bin:$PATH"
python3 -m unittest discover -s tests -v
python3 -m unittest discover -s tools -p 'test_*.py' -v
sha256sum -c docs/original-payload.sha256
```

Test tool, test định dạng RecordStore và harness Java không thay thế việc thử
save/load trên Android. Không tự gỡ bản game của người dùng để vượt lỗi chữ ký.

## Runtime

### Bản Java lõi đã chạy thử

APK cuối SHA-256:
`67c6f29a7d32548e40b60b8bd55abaff179c7b4f127a41a86af3bac808d23c03`.
Build `:core-source:assembleRecoveredDebug` thành công trong44s; log lưu tại
[core-source-build-validation.log](core-source-build-validation.log).
DEX output7.880.852 bytes, revision8.021.544; metadata được tạo tự động.

Đã cài APK này bằng `adb install -r -g` trên AOSP API28 x86, chạy qua
Start/MIDlet/loader tới màn hình tuổi/điều khoản. Nhập30 và chọn Male hoạt động;
JDI chỉ đọc xác nhận `aTL=30`, state60, loader hoàn tất, stack hợp lệ.
Header, nội dung và liên kết khớp từng pixel với ảnh APK gốc trên ba vùng kiểm
tra (233.220 pixel); ảnh nền động ngoài các vùng đó không nằm trong phép so sánh.
Theo dõi thêm158s sau bước khởi động: PID giữ nguyên, không có `FATAL EXCEPTION`
hoặc thông báo integrity/ban trong log/ảnh được kiểm tra.

Các lỗi runtime đã khôi phục theo DEX: hai vòng lặp lookup thiếu return,
nhánh thoát selection, continuation CRM,57 callsite mất kiểu float trong nhóm
render ban đầu, anchor text bị ghi đè, ba nhánh hiệu ứng và các nhánh glyph `bf`.
Sửa overload không đổi tên lớp/phương thức; nhóm glyph sau đó gộp lại đường
nhánh dùng chung đúng như DEX. Bằng chứng:
[continuation](CI_LOADING_CONTINUATION_RECOVERY.md),
[render](RENDER_OVERLOAD_RECOVERY.md),
[descriptor](CORE_FLOAT_DISPATCH_RECOVERY.md),
[font](BF_TEXT_BRANCH_FINDINGS.md), [hiệu ứng](SPRITE_EFFECT_BRANCH_FINDINGS.md).

68 test phục hồi và13 test resource đạt, không skip. Các phép so sánh mới gồm
7.392 anchor,4.920 glyph,18.432 transform/effect và5.760 màu. Test glyph/hiệu ứng
yêu cầu đi đến draw thành công; regression mutation phát hiện các lỗi cũ.
Đây là kiểm tra các phương thức/đường nhánh được chọn, không chứng minh mọi
nhánh trong hàng nghìn file decompile đều đúng. Build toàn bộ source SDK và
việc audit những nhánh gameplay chưa chạy vẫn chưa hoàn tất.

Accept vẫn ở màn hình tuổi; log báo DNS `eve.gameloft.com` như APK gốc/đối chứng.
Chưa vượt privacy gate, chưa vào farm, chưa thử thao tác farm hoặc save/load;
emulator chạy `-no-audio`, âm thanh chưa được xác nhận. Chưa thử Android mới.
[Ảnh bản Java](screenshots/core-java-age-touch.png),
[kết quả có cấu trúc](runtime-core-source.json).

### APK gốc và bản đối chứng

Đã cài Android Emulator chính thức và system image AOSP Android9/API28 x86.
Chạy software emulation (`-accel off`, SwiftShader) vì không có `/dev/kvm`.
APK gốc cài thành công, đi qua Start/MIDlet và hiển thị màn hình tuổi/điều khoản
bằng đồ họa game. Nhập tuổi thử30 và checkbox nhận cảm ứng. APK debug đối chứng
cũng cài được, hiển thị splash/loading và cùng màn hình tuổi, nhận nhập30 và
checkbox, với GL thread/game activity hoạt động. Không thấy `FATAL EXCEPTION`
trong log game của hai lượt; SystemUI ANR do emulator chậm đã được tách riêng.
Không xác nhận performance hoặc âm thanh qua software emulator.

Màn hình tuổi của bản gốc chưa qua được Accept: Janus/privacy chưa tải cấu hình.
Guest báo DNS cho `eve.gameloft.com`; request từ host qua proxy bị CONNECT403 và
miền chưa nằm trong policy cho phép. Đây là điều kiện truy cập môi trường chưa
đủ, không chứng minh server Gameloft đã ngừng hoạt động. Chưa tạo privacy cache
giả, bỏ qua consent hoặc thay logic online để ép vào farm. **Chưa kiểm tra được
vào trang trại, thao tác nông trại và save/load tiến trình gameplay.**

Đã lưu và đọc lại xác nhận bản nháp cấu hình revision2, gồm installer đầy đủ,
hướng dẫn khởi động và rule `eve.gameloft.com`, giữ nguyên preset/repository.
Lỗi `INVALID_ARGUMENT` của lượt onboarding trước đã được giải quyết. Runtime
vẫn dùng revision1: rule mới chưa áp dụng. Cần review/save trong environment
settings, rồi Publish khi giao diện cho phép; sau khi runtime thực sự cập nhật,
kiểm tra lại mạng/proxy/CA tin cậy cho guest Android. Không tắt TLS hoặc đi vòng
policy. Xem [cấu hình đã lưu và giới hạn](ENVIRONMENT_SETUP.md).

Original userdata đã được lưu riêng cùng full sparse AVD backup, có đối chiếu
hash trước khi đổi chữ ký để cài bản debug. App startup đã ghi các record
RMS_Time/RMS_TimeClock/RMSHeader cùng file CRC/saved; đây chưa phải test lưu farm.

Ảnh: [bản gốc ở age gate](screenshots/original-age-gate.png),
[splash APK build lại](screenshots/rebuilt-splash.png),
[touch ở bản debug](screenshots/rebuilt-age-touch.png).
[Kết quả runtime có cấu trúc](runtime-comparison.json).
Log đầy đủ trong `build/reports/restoration/runtime-original/` và
`build/reports/restoration/runtime-rebuilt/`.
