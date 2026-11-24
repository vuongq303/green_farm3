# Phạm vi nguồn, DEX gốc và native

Bản gốc được pin SHA-256
`34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
[Danh sách từng lớp và từng payload](kotlin-validation/dependency-inventory.json)
được sinh bằng:

```sh
python tools/inventory_game_code.py --output docs/kotlin-validation/dependency-inventory.json
```

Không dùng số lượng file JADX trong `app/src/main/java` làm số lượng mã đã
khôi phục. Nhiều file SDK/integration vẫn chỉ là bản tham khảo, không nằm trong
source profile đang được AGP biên dịch.

## Nền trước Kotlin

| Thành phần gốc | Số lớp/payload | Nguồn runtime trước chuyển đổi |
|---|---:|---|
| Toàn bộ DEX | 6.383 lớp | Java core overlay và DEX gốc còn lại |
| Package game `com.gameloft.android.ANMP.GloftGF2F` | 236 lớp | Bao gồm engine và Android integration |
| Engine `S800x480` | 110 lớp | 108 Java khôi phục; `l` và `g` giữ từ DEX |
| Android integration trong package game | 126 lớp | DEX gốc; Java JADX chưa được build |
| Native | 10 file | `libAdsManager.so` và `libnativeU.so` trên5ABI |
| Assets | 49 file | Dữ liệu/hình/âm thanh gốc, không đổi byte |
| Resource dưới `res/` | 216 file | AGP liên kết lại, giữ resource IDs theo public/stable map |

Trong 126 lớp integration DEX gốc có89 lớp ở package gốc,33 lớp push notification,
2 lớp ở subpackage `a`,2 lớp installer. Danh sách JSON ghi từng descriptor,
file source tham khảo, ngôn ngữ source thực sự đang build và descriptor đích.
Nếu `Start.kt` được đưa vào profile, lớp launcher đó chuyển riêng từ DEX sang
Kotlin; các lớp integration khác không tự được khôi phục chỉ bởi đổi package.

Chín helper lưu dữ liệu/tutorial/ngôn ngữ được viết thêm trong quá trình phục
hồi không có trong236 lớp gốc, vì thế không được cộng nhầm vào tỷ lệ Kotlin hóa
của game gốc. Các lớp phụ do Kotlin/D8 sinh và stdlib cũng không phải logic game
đã chuyển. Kết quả cập nhật theo source hiện hành nằm trong JSON; báo cáo APK
merge ghi riêng `kotlin_compiled_classes` và `kotlin_runtime_class_count`.

Manifest gốc còn khai báo `.SoftKeyBoardActivity`, nhưng DEX gốc không định
nghĩa lớp này và không có cả literal tên đầy đủ lẫn suffix `SoftKeyBoardActivity`
trong DEX. Không có source JADX tương ứng. Component này không được cộng vào236
định nghĩa và được ghi riêng trong JSON. Verifier báo baseline exception bằng
cách đối chiếu chính manifest/DEX gốc, không tạo class rỗng hoặc miễn kiểm tra
cho component mới. Chưa loại trừ việc dựng tên động và chưa kiểm chứng luồng
keyboard tùy chọn; không tuyên bố đây là một chức năng đã sửa hoặc đã hoạt động.

## Phần còn phải khôi phục/chuyển

- Các lớp engine Java lớn vẫn chứa logic gameplay thực, gồm engine chính,
  graphics/sprite/animation, map/objects, task/quest, inventory và dữ liệu cấu hình.
  Việc sửa package/tên không biến chúng thành Kotlin. `ci.java` ở nền hiện tại
  có72.441 dòng, `GLLib.java` có8.551 dòng; phải chuyển theo nhóm có oracle hành vi.
- Các lớp Android integration chưa có Kotlin runtime replacement vẫn lấy method
  body từ DEX gốc. Trong đó có Activity/Push/installer, browser, IAP/SDK adapter,
  bootstrap/configuration và các helper obfuscate. Chưa có căn cứ coi toàn bộ126
  lớp là SDK bên ngoài và loại chúng khỏi phạm vi game.
- Bridge `javax.microedition.*`, `com.gameloft.android.wrapper.*`, ads/network
  SDK, Android support, Google/Firebase, Facebook, Unity/Vungle/IronSource và các
  namespace còn lại vẫn là DEX dependency. JSON kiểm kê số lớp theo namespace;
  chưa tuyên bố đã thay thế runtime MIDlet hoặc SDK bằng Kotlin.
- Con số236 là danh sách đầy đủ **trong package game gốc**, không chứng minh
  rằng mọi lớp ở package khác chỉ là dependency bên ngoài. JSON liệt kê riêng
  các descriptor `com.gameloft.a.*` chưa phân loại hành vi đầy đủ và runtime
  MIDlet. `com.gameloft.a.b.a` còn lấy từ DEX gốc với endpoint patch đã duyệt;
  chưa thay toàn bộ service bootstrap này bằng implementation Kotlin.
- `l` (endian asset reader) và `g` (marker thực sự trống) đã có nguồn Kotlin
  riêng; [oracle và khác biệt interface được mô tả ở đây](KOTLIN_ENDIAN_RECOVERY.md).

## JNI và tên động

Đã đọc printable strings và dynamic symbols của cả10 file `.so`. Không tìm thấy
`ANMP`, `GloftGF2F` hoặc JNI symbol thuộc package game cũ trong kết quả đó. Các native
entry points thuộc `com.gameloft.glads`, `com.gameloft.adsmanager` và
`com.gameloft.android.wrapper.NativeUtils`; các namespace này được giữ nguyên.
AdsManager có86 JNI entry names mỗi ABI, nativeU có10; toàn bộ tên/hash được ghi
trong JSON. Không sửa nhị phân native, không đổi ABI hoặc thư viện.

`InGameBrowserActivity.aW(int)` là một native declaration trong integration gốc.
Không tìm thấy direct JNI export của hàm này hoặc literal tên lớp đầy đủ trong
thư viện được cung cấp. Điều đó chưa loại trừ `RegisterNatives` hoặc tên lớp
được dựng động. Chưa kiểm chứng đầy đủ đường browser này và chưa chứng minh
nó lỗi ở bản gốc; không gán lỗi cho chuyển Kotlin hay bịa implementation native.

Các literal identity đã thấy trong DEX: app ID, `Start`, `KeyProvider`, retry/
push broadcast action, URI KeyProvider, OBB path, package suffix và link Play
Store. `RelocateGamePackage` đổi descriptor/types và các literal đó đồng bộ,
bao gồm references từ SDK ngoài vào game. `PushNotification.h` nhận tên lớp qua
`Class.forName`; `javax.microedition.lcdui.f` dựng tên `package + ".GLLib"`.
Đổi tên class graphics phải xử lý literal suffix này theo mapping đã kiểm chứng.

Package `com.quanhoang.greenfarm3.migration` là ngoại lệ literal có chủ đích:
plain string chứa identity của cả app cũ và mới phải giữ nguyên để xuất/nhập save.
Type descriptor và signature trong các lớp migration vẫn được relocate. Danh
sách exception owners được ghi ở report DEX; không giữ game class cũ để né rename.

Bridge xuất save dùng application ID cũ còn một ngoại lệ identity riêng:
`--application-id com.gameloft.android.ANMP.GloftGF2F` chỉ giữ authority và URI
`KeyProvider` của bridge ở hai literal trong `<clinit>()V`. Class provider vẫn
thuộc `com.quanhoang.greenfarm3`; mọi type reference vẫn đổi package. Một literal
authority và một literal URI là bắt buộc, không được thiếu/thừa hoặc nằm ở hàm
khác. Guard kiểm tra đảo policy, các class khác không đổi và full inverse DEX.
Report ghi `application_id/provider_identity_compatibility_exceptions`.
Main APK mới không dùng ngoại lệ đó. Không có literal oldApp `.provider` hoặc
`.firebaseinitprovider` trong DEX gốc cần đảo thêm; SDK dùng package của context.

Các tên cũ còn xuất hiện ngoài runtime class identity:

- `res/raw/google_services.json` chứa package của Firebase client gốc. Đây là
  cấu hình SDK cũ được giữ làm tài nguyên; đổi một chuỗi không tạo Firebase
  project/certificate mới. Đăng ký Firebase dưới app ID mới chưa được xác minh.
- Bốn source `R.java` tham khảo của SDK còn biểu thức oldApp.R.attr. Generator
  chuyển owner sang namespace đang build, bao gồm30 biểu thức styleable symbolic;
  các file tham khảo không được đưa thẳng vào compiler hoặc đóng gói thành alias cũ.
- APK gốc, metadata pin/oracle và báo cáo lịch sử giữ tên gốc để đối chiếu.
  Chúng không phải một bản game class alias đang chạy trong APK mới.

## Cơ chế build và bảo toàn

`MergeCoreDex` chỉ nhận core/helper đã duyệt, launcher/migration và Kotlin stdlib
explicit. Compile-view là `compileOnly`, không đổi thành runtime dependency.
Overlay package mới được chuẩn hóa ngược về identity gốc để thay đúng lớp; các
proof nguồn overlay và SDK giữ lại vẫn chạy. Sau endpoint patch, relocator đổi
toàn bộ identity game và phải inverse về canonical input tương đương.

Giai đoạn tên rõ nghĩa có cờ riêng `--rename-symbols`: ba class `ci/ch/GLLib`,
hai field đã xác minh `aYR:I/baa:Z` và hai static accessor `qD/qE()I` của đúng
owner engine được đổi đồng bộ. Class/field/method refs trong DEX đều dùng
rewriter cấu trúc; literal `.GLLib` chỉ đổi ở reflector `javax.microedition.lcdui.f`.
Mapping đầy đủ được ghi ở report `class_name_mapping/member_name_mapping`.
Compiler-view clone NameAndType cho đúng owner+descriptor, không thay mọi UTF8
`qD` hoặc `aYR` dùng chung với lớp khác. Các chỉ số pool cũ, code và attributes
giữ nguyên; fixture `Other.qD()` phải tiếp tục trỏ tới `Other.qD()`.

Kotlin stdlib làm string pool thực tế vượt65.535: đo được68.269 ở nhóm đầu,
14 class overlay có `const-string` được DexWriter nâng thành jumbo. Guard nay
dùng form canonical jumbo/goto32 với label/try/debug targets được giữ bởi
`MutableMethodImplementation`, thay vì so width bytecode. Runtime output không
bị đổi sang form proof. Fixture hồi quy ép pool66.000 chuỗi xác nhận promotion,
branch/try/debug và phát hiện mutation literal; không bỏ throw kiểm tra semantics.

Save vẫn SQLite schema4, WAL/FULL và codec record gốc. Đổi biến không đổi key hoặc
payload. Application ID mới có sandbox riêng; phải dùng luồng xuất/nhập được
triển khai riêng, không đọc trực tiếp thư mục private của app cũ.
