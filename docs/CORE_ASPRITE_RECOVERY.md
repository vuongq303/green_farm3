# ASprite: đối chiếu decoder, text layout và IO với DEX gốc

Đầu vào là `green-farm-3.apk` 4.4.2/44220 có APK SHA-256
`34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`,
DEX SHA-256 `3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4`.
Bằng chứng chính là `S800x480/a.smali` (`ASprite.java`) và `bc.smali` trong DEX.
CFR/JADX đều còn vùng không decompile được ở decoder/text layout, nên không
thay toàn bộ phương thức bằng output decompiler khác.

## Các sửa đổi có bằng chứng

- `a(I[BIIIIIIZ)Z`: phục hồi 11 cạnh thoát loop mà JADX bỏ mất. Chín loop RLE
  kiểm tra **giá trị register cũ** rồi giảm counter sang register khác trong
  DEX: dùng postfix decrement với đúng điều kiện `>=0` hoặc `>0`, giữ cả run
  encoding `count+1`. Hai pass alpha còn lại kiểm tra register đã giảm và chỉ
  thiếu nhánh exit. Giữ nguyên row stride, palette lookup và format dispatch.
- Cùng decoder, format `22018` phải đọc byte ở offset hiện tại rồi tăng offset;
  JADX đã tăng trước khi đọc. Format `-23198` đọc alpha trước, palette index kế
  tiếp; source export đã lấy alpha từ byte sau cặp đó. Cả hai được phát hiện
  bởi test differential rồi xác nhận lại DEX `aget-byte` và các thanh ghi offset.
- `a(String,int,int,boolean):short[]`: control char1 và các control char không
  render đi tới join `:goto_15f`, áp dụng state và tăng index, bỏ qua phép đo
  width glyph. Khôi phục cạnh join bằng labeled block; không khởi tạo một glyph
  giả để làm biến `cCharAt` có giá trị. Control char2 giữ đúng cách đọc của DEX.
- Sáu phương thức đọc sprite có direct IO call và **không có catch handler**
  trong DEX: `a(l,int)`, `b(l)`, `d(l)`, `e(l)`, `f(l)`, `a(l,int,int,int,int)`.
  Java yêu cầu xử lý checked IOException nên dùng generic rethrow trả lại cùng
  instance/type, không wrap, swallow hay tạo giá trị mặc định. Không thay data
  order, byte format hoặc call sites.
- `bc.k(String,int,int):String`: bỏ một `break` trùng ngay sau `break` trong case
  dấu nháy. DEX chỉ có một cạnh về loop-increment `:cond_14/:goto_14`; các luật
  quote/escape và return không đổi.

Các loop RLE cũ không chỉ chặn compiler: nhiều nhánh không có exit vẫn hợp lệ
về cú pháp nhưng có thể treo khi được dùng. Vì vậy không chỉ xóa hai statement
compiler báo unreachable mà bỏ qua những nhánh graphics khác.

## Kiểm tra

`tests/test_asprite_recovery.py` compile **source hiện tại** `a.java` mỗi lượt,
không dùng classfile đã build trước đó. Hai fixture giữ nguyên Code attribute
của các method từ original classfile và source vừa compile; chỉ bỏ method/field
Android không liên quan. Fixture `GLLib.c` cũng dùng bytecode gốc (hàm gốc rỗng),
`w` và partial `l/DataInput` dùng classfile gốc. Không thêm stub vào ứng dụng.

Lệnh trên máy đã chuẩn bị:

```sh
JAVA_HOME=/workspace/tooling/jdk-21.0.9+10 \
ANDROID_HOME=/workspace/tooling/android-sdk \
python3 -m unittest discover -s tests -p test_asprite_recovery.py -v
```

Kết quả đạt:

- 800 ca decoder đối chiếu pixel/index buffer và outcome, bao phủ 13 format,
  RGBA/index ở các mode có đường thực thi gốc, stride0/24, input zero và random.
  Có so sánh trạng thái buffer khi input gây lỗi, không coi các ca đó là decode
  thành công. Mục tiêu là giữ chính xác hành vi gốc.
- 1.200 ca text layout: control chars1/2/3, space, newline, pipe, một số escape,
  chuỗi random, nhiều width/cut mode và formatting on/off; so sánh layout hoặc
  loại exception với bytecode gốc.
- 12 kiểm tra IO (sáu đường đọc × original/restored) xác nhận cùng exception
  instance được truyền ra, không bị thay bằng RuntimeException wrapper.

Test từng phát hiện lỗi pixel thật ở format22018/-23198 trước khi các offset
được sửa. Đây là kiểm tra thuật toán cô lập, không chứng minh toàn bộ đồ họa,
gameplay, asset pack hoặc save/load trên Android hoạt động. Classfile gốc được
dex2jar chuyển từ DEX đã xác thực; không coi chuyển đổi exit0 là bằng chứng
toàn bộ Java được khôi phục chính xác. Provenance công cụ tại
[ORIGINAL_BYTECODE_TOOLS.json](ORIGINAL_BYTECODE_TOOLS.json).

Lượt compile core chung sau nhóm sửa đầu còn hai lỗi: `bc` duplicate-break và
`l` không cài đủ interface DataInput. `bc` được sửa như trên; `l` không được
thêm method rỗng hoặc bỏ interface trong nhóm thay đổi này. Bản core-source cần
giữ nguyên class `l` gốc và công khai ranh giới Java/binary; không mô tả như một
bản toàn bộ ứng dụng được compile từ Java.
