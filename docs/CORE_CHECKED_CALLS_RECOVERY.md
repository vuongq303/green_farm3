# Transparent checked-exception bridges trong `ao` và `at`

Đối chiếu APK/DEX 4.4.2/44220 có checksum tại
[ORIGINAL_BYTECODE_TOOLS.json](ORIGINAL_BYTECODE_TOOLS.json). Nhóm này chỉ xử lý
bốn nơi compiler Java yêu cầu checked-exception declaration; không thêm giá trị
mặc định, retry hoặc handler nuốt lỗi.

| Call site | Bằng chứng DEX | Biểu diễn Java |
|---|---|---|
| `ao.ew(I)I` → `ms().ex(i)` | `ew` không có catch hoặc Throws annotation. | Catch Exception rồi generic-rethrow cùng instance/type, giữ nguyên result và singleton lookup. |
| `ao.D(String,String)I` → `i.ic().aw(config)` | Hai catch trong DEX chỉ bảo vệ việc parse config đầu method (`:try_start_23..:try_end_29` và `:try_start_29..:try_end_2f`). Lời gọi CRM `aw` ở ngoài các handler đó. | Giữ result của đúng lời gọi; nếu có Exception thì rethrow cùng instance, không biến lỗi thành result khác0 hoặc đổi cờ init/config. |
| `at.a(I,cg)I` → `cg.eV(extraBits)` | Method không có handler hoặc Throws annotation. | Chỉ bridge IOException quanh lời gọi bit reader trong nhánh extraBits>0. Giữ nguyên lookup length table và phép cộng. |
| `at.b(I,cg)I` → `cg.eV(extraBits)` | Tương tự method a, không có handler. | Giữ distance table/bit width và cùng IOException instance; nhánh không extra bits không thay đổi. |

Source của `ao.ex`, `i.aw` và `cg.eV` có checked-throws declaration mà các
caller DEX không cần. Private generic helper chỉ thực hiện `throw failure`;
không tạo RuntimeException wrapper hoặc reset stack trace. Các catch Java mới
chỉ làm cầu nối tức thời rồi rethrow; không thay đổi những vùng catch gốc.

Smali extract trong máy:
`/workspace/tooling/recovery-bytecode/ao-{ew,ex,D}.smali` và
`at-{a,b}.smali`. Đường `D` giữ nguyên các handler parse ban đầu và toàn bộ
cache/default configuration logic; không vô hiệu hóa CRM/IAP/online logic.
Nhóm này không sửa `au`, `i`, `cg`, hoặc bitstream format.

Kiểm tra compiler scoped đầu tiên thiếu các core class chưa được build nên
báo missing dependency. Kiểm tra lại compile current source `ao`, `at` và
`cg` (không sửa `cg`) với original classfile JAR + Android28/Apache/AGP R.jar
đã **exit0**, chỉ còn bảy warning legacy. Log:
`/workspace/tooling/recovery-bytecode/ao-at-check-original-deps.log`. Đây không
phải build APK đầy đủ hoặc kiểm tra hoạt động dịch vụ/decompression trên Android.
