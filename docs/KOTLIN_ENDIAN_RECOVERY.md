# Khôi phục bộ đọc asset endian từ DEX

`S800x480/l` là `EndianInputStream` theo metadata JADX và hành vi bytecode.
`l.java` chỉ có 92 dòng và không biên dịch được vì thiếu các phương thức của
`DataInput`. Chính DEX gốc cũng không định nghĩa các phương thức interface đó.
Game gọi trực tiếp `Lm` cho thao tác byte/raw và dùng sáu hàm numeric của `l`.

`l.kt` thay toàn bộ lớp DEX này bằng Kotlin. Tên lớp và ba field public `Lm`,
`Ln`, `order` tạm giữ để bảo toàn ABI trong Java/DEX còn lại; dùng `@JvmField`
chỉ ở ba field được truy cập trực tiếp. Constructor nhận null như bản gốc;
`read`, `available`, `cf` vẫn mở, sáu hàm numeric vẫn final. Không chuyển
`close`, `mark`, `reset` hoặc `skip` sang backing stream: bản gốc kế thừa hành vi
của `InputStream`, kể cả `close` không đóng stream bên dưới.

| Phạm vi cũ | Tên rõ nghĩa trong Kotlin | Hành vi đã kiểm chứng |
|---|---|---|
| `l.Lo` private | `scratchBytes` | Buffer tạm 8 byte để đọc little endian |
| `l.cf(int)` | `setByteOrder(int)`; `cf` là entry ABI | 0: big endian, mọi giá trị khác: little endian |
| `l.iu/ix` private | `readLittleEndianUnsignedShort()` | Ghép hai byte không dấu, `readShort` cast về short |
| `l.iv` private | `readLittleEndianInt()` | Ghép 4 byte; byte cao giữ dấu trước shift24 |
| `l.iw` private | `readLittleEndianLong()` | Ghép 8 byte với mask255L và shift theo đơn vị bit |
| `l.is/it` private | `readFloat/readDouble` dùng bit conversion | Giữ bit float/double từ integer gốc |
| `l` class, `Lm/Ln/order` fields | Giữ tên ABI tạm thời | Chưa tuyên bố toàn bộ mã hết tên obfuscate |

Các phương thức `DataInput` bị thiếu được hoàn thiện bằng backing
`DataInputStream`: readFully/skipBytes/readBoolean/readByte/readUnsignedByte/
readChar/readLine/readUTF. Chúng không thay đường endian numeric của game.
Đây là phần mở rộng hợp đồng interface, không được báo là hàm đã có trong DEX.

`g` thực sự là marker package-private trống, không có field hoặc method trong
DEX. `g.kt` giữ marker đó; không thay một hàm gameplay bằng stub. Kotlin sinh
constructor private và Metadata; không có logic gameplay bị loại bỏ.

`tests/test_endian_input_stream.py` compile Kotlin production và chạy Java
oracle đối chiếu với lớp `l` thật từ bản dex2jar đã pin: 2.000 payload ngẫu nhiên,
4 giá trị byte-order, 6 phép đọc numeric, byte-read/available sau mỗi phép đọc,
EOF ở độ dài0–7, field/method ABI, null constructor và close/mark/reset. Các
phương thức interface bổ sung được kiểm tra riêng, không giả gọi là differential.

Sau khi các test này đạt, `MergeCoreDex` yêu cầu cả `l` và `g` có trong overlay
đã compile và thay hai định nghĩa DEX gốc. `compile-view.jar` không đóng gói
bản sao hai lớp này. Profile108 lớp Java cũ vẫn kiểm tra riêng; hai lớp Kotlin
được phục hồi bổ sung được ghi trong provenance.
