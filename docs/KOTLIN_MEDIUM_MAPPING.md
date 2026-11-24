# Nhóm Kotlin: model, toán học và adapter

11 lớp implementation đã chuyển trực tiếp sang Kotlin: `e`, `bv`, `bs`, `be`,
`j`, `x`, `bl`, `ah`, `bt`, `cy`, `b`. Tên public/package callable và field JVM
được giữ ở checkpoint chuyển ngôn ngữ để Java/DEX hiện tại vẫn liên kết; việc đổi
package và tên lớp được ghi ở checkpoint tích hợp cuối.

| Lớp/phạm vi | Tên cũ | Tên mới hoặc vai trò đã xác minh | Bằng chứng |
|---|---|---|---|
| `e` | `Ko`, `Kq`, `Kw` | `playerIdentifier`, `description`, `operation` | `bq` dùng GLUID, Description và switch loại thao tác cloud. |
| `e` | `Kp`, `Kn`, `Ku`, `Kv` | `saveKeys`, `saveDate`, `saveVersion`, `storageOwner` | TOC dùng SeshatFileKey/Date/Version; owner là tham số path đầu của storage API, không giả định tài khoản đăng nhập. Chưa đổi đơn vị Date. |
| `e` | `Kr`, `Ks`, `Kt` | `serializedSave`, `serializedByteCount`, `appendedChunkCount` | Hàm append thêm byte và tăng counter đúng một lần. |
| `bv` | `Ki`, `akr`, `aks`, `akt`, `aku`, `akv` | `itemIdentifier`, `quantity`, `replacedQuantity`, `categoryCount`, `categories`, `billingMethods` | Khóa response item/quantity/replaced_quantity/category/billing_methods. |
| `bs` | `akh`, `aki`, `akj`, `akk`, `akg` | `outputCounter`, `reseedCounter`, `outputBytes`, `stateBytes`, `reseedInterval` | Thứ tự digest update, block generation và reseed mỗi10counter. |
| `bs` | `nQ`, `nR`, `u` | `reseedState`, `generateBlock`, `updateDigestWithLong` | Hàm private chỉ dùng trong random, long nhập digest theo little-endian. |
| `be` | local `i`, `i2`, `i3`, `f` | `row`, `column`, `index`, `sum` | Ma trận4x4; giữ thứ tự cộng float và hành vi input/output vector cùng object. |
| `j` | local `i2`, `bArr`, `i4`, `i5` | `byteOffset`, `columnWidths`, `row`, `column` | Asset/13 gồm header row/column và độ rộng từng cột1/2/4 byte. |
| `bl` | `ajD`, `ajE`, `ajF`, `ajG`, `ajH` | `startedAtMillis`, `stoppedAtMillis`, `maximumElapsedMillis`, `minimumElapsedMillis`, `totalElapsedMillis` | Timer profiler và chuỗi Sum/Max/Avg/Min gốc. |
| `bl` | `ajI`, `ajJ`, `ajK`, `count`, `name`, `s` | `groupIndex`, `groupEventCounts`, `durationsMillis`, `eventCount`, `displayName`, `recordElapsed` | Bounded history và event groups; giữ count khởi đầu1/min khởi đầu0, không sửa công thức khi port. |
| `cy` | `MQ`, `MR`, `mJ` | `executeRequest`, `pollSession`, `finishRequest` | Dispatch20mã online, đối chiếu session, kết thúc/reset cờ; vẫn sleep250ms trong monitor như bản gốc. |
| `b` | local `i`, `str`, `i2` trong callback | `adType`, `placement`, `mappedStatus` | Callback SDK rewarded ads; các field flags và identifier placement giữ nguyên. |

`x` có93 field mutable được Java/DEX truy cập, nên `@JvmField` là cần thiết ở
từng field đó. Chưa gán ý nghĩa riêng cho các flag obfuscate chưa đủ bằng chứng.
Các static method ở adapter/table dùng `@JvmStatic`; không đổi constructor,
field mutable thành constant hoặc interface Android/SDK.

Các điểm giữ hành vi dễ sai khi port:

- `bs` gọi `Random(0)`; override `setSeed(0)` không chạm digest chưa khởi tạo.
  `g(null,offset,0)` vẫn tạo block mà không dereference mảng; byte signed, unsigned
  shift, overflow Int/Long và những lần bỏ phần block dư được giữ nguyên.
- `e.a(save.hT())` giữ cả exception lẫn buffer trung gian của trường hợp self-alias;
  không chụp tham chiếu chunk data trước khi thay destination.
- `be.a(input,output)` không sao chép input khi input/output là cùng vector.
- `ah`/`b.Init` dùng `lowercase(Locale.getDefault())`, tương đương Java
  `String.toLowerCase()`; không chuyển ngầm sang quy tắc locale-independent.
- `ah.d(bc)` là no-op **đã có trong APK gốc**: `javap -c` cho đúng một instruction
  `return`. Bản Kotlin giữ hành vi đã xác minh đó, không tạo stub thay logic thiếu.
- Numeric parse lỗi ở `bv` tiếp tục giữ giá trị trước theo code gốc; không đổi
  cách báo server/API thất bại hoặc tạo dữ liệu đăng nhập thành công giả.

[ABI11lớp](kotlin-validation/medium-abi.json) không thiếu member gọi được hoặc
field descriptor/staticness. Test `test_kotlin_medium_models.py` chạy các class
bytecode APK gốc và Kotlin trong hai JVM tách biệt, cùng peers đồng hồ/resource:

- 1.000 cặp ma trận có cả alias, signed-zero, overflow, infinity/NaN. So sánh
  `Float.floatToIntBits`: NaN được canonical hóa, không cam kết payload NaN vốn
  không ổn định giữa các cách JIT cấp phát thanh ghi.
- Sáu seed digest/Random, buffer boundary, zero/null, block/reseed và inherited
  Random API; cloud buffer append, bản sao và self-alias failure.
- Profiler timers/history,34bảng packed, và90 tổ hợp placement/type/status qua
  ba callback ad. Sáu SHA256 trace giống bytecode gốc.

`bv`, CRM/social/online worker đã compile và kiểm ABI; chưa chạy SDK mạng gốc
thành công trên thiết bị ở nhóm test này. Bài JVM không xác nhận FPS, UI, SQLite
hoặc dịch vụ Facebook/Gameloft. Kết quả APK/emulator được ghi riêng.
