# Khôi phục có đối chiếu DEX trong `S800x480/ci.java`

Nhóm thay đổi này chỉ sửa biểu diễn Java bị JADX làm hỏng trong `ci.java`.
Chưa chứng minh toàn bộ core Java biên dịch hoặc hành vi gameplay của bản Java
đã phục hồi. Bản đối chứng giữ DEX gốc là một đường build riêng.

## Đầu vào và công cụ

Đối chiếu `green-farm-3.apk` trong commit
`228cc7dd7b4b413f430e93c74cc147a1ffb8a23c`, phiên bản 4.4.2/44220.

- APK SHA-256: `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
- `classes.dex` SHA-256: `3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4`.
- Bằng chứng chính: `com/gameloft/android/ANMP/GloftGF2F/S800x480/ci.smali`
  được baksmali3.0.3 disassemble trực tiếp từ DEX. Có 6.383 lớp trong smali;
  dex2jar2.4 cũng xuất 6.383 classfile, exit0 và không báo lỗi chuyển đổi.
- JADX1.5.3 và CFR0.152 được dùng bổ sung; một số output vẫn chứa vùng không
  decompile được. Không dùng chúng thay thế DEX làm nguồn xác nhận.

Nguồn tải, checksum và mức xác thực từng công cụ được lưu trong
[ORIGINAL_BYTECODE_TOOLS.json](ORIGINAL_BYTECODE_TOOLS.json). Các SHA-256 của
dex2jar/JADX là fingerprint tính sau tải từ release chính thức qua TLS, không
được trình bày như checksum độc lập do upstream công bố. Baksmali đến từ SDK
archive đã đối chiếu checksum metadata chính thức; CFR đối chiếu Maven SHA-1.

## Các thay đổi đã đối chiếu

| Thành phần | Bằng chứng và điều được giữ nguyên |
|---|---|
| 16 call site `j.bY(int)` bị tham số `long j` che tên lớp | DEX gọi `S800x480/j->bY(I)`. Fully qualify tên lớp ở đúng các call site lỗi; không đổi tên tham số, lớp hay dữ liệu. |
| 30 `JavaUtils.Constants` và 13 `FirebaseAnalytics.Param` reference | Thay symbolic constant do JADX chèn bằng literal đúng `.field public static final` trong `JavaUtils$Constants.smali`/`FirebaseAnalytics$Param.smali`. Ví dụ `0x12c`=300, `0x140`=320, `SCORE`=`"score"`. Không thay giá trị hoặc thứ tự styleable. |
| `La()Z` | Khôi phục nhánh khi country property null. DEX `:cond_59` đi tới kết quả false của lần tìm country, rồi cùng join `:goto_3e`; Java dùng `else` cho vòng tìm thay `break` ngoài loop. Giữ nguyên thứ tự đọc SIM/country, uppercase và luật kết hợp kết quả. |
| `a(IIIIIIZI)V` — push crafting queue | DEX `if-eqz p6,:cond_f1` bỏ qua tìm alternative khi flag false. Nhánh alternative hết 104 phần tử cũng fallback về recipe index gốc; giữ nguyên cột18/cột13, các array write và `GH()`. |
| `a(IIJIZB)V` — load crafting queue | DEX `if-eqz p5,:cond_111` dùng giá trị cột18 ban đầu; alternative tìm thấy dùng cột18 của alternative, không thấy vẫn giữ giá trị ban đầu. Khôi phục join `:goto_100` bằng `if/else`, không thay fallback hoặc cách nạp timestamp. |
| `jh(I)Z` — tìm room của reward | DEX `:goto_247` tăng counter ở `:cond_29b` đến room khớp hoặc `KV`, sau đó cùng đọc `KW[index][14]` tại `:cond_276`. Khôi phục lifetime counter và loop; giữ nguyên cả hành vi gốc nếu không có phần tử khớp, không thêm fallback dữ liệu mới. |
| Constructor `(Object,Object)` | Đưa `super(obj,obj2)` lên trước hai khai báo local chưa gán, không di chuyển các statement khởi tạo hoặc thread startup có hiệu ứng. |
| `fM(I)V` | Bỏ assignment `z=false` xuất hiện trước khai báo local `boolean z=false`; đây là một register zero bị JADX biểu diễn trùng. Giữ một local false và toàn bộ switch/nhánh tải dữ liệu. |

Khôi phục thêm **sáu accessor có thật trong DEX**, thay cho việc đổi quyền private
của field để sibling class truy cập:

| Descriptor gốc | Nội dung chính xác |
|---|---|
| `a(L.../ci;Z)Z` | Ghi `instance.aYE`, trả lại boolean đầu vào. |
| `MM()Ljava/lang/Object;` | Đọc `aXG`. |
| `MN()I` | Đọc `aXN`. |
| `MO()Z` | Đọc `aXH`. |
| `MP()I` | Đọc `aXL`. |
| `bD(Z)Z` | Ghi `adu`, trả lại boolean đầu vào. |

Caller trong `cs`/`cy` được phục hồi riêng; xem
[CORE_PEER_ACCESS_RECOVERY.md](CORE_PEER_ACCESS_RECOVERY.md). Không thêm stub,
không nâng quyền các field private, không thay thuật toán farming/crafting.

## Kiểm tra và giới hạn

Lượt diagnostic ban đầu compile 109 file `S800x480` bằng JDK21.0.9, source/target8,
`-proc:none -sourcepath ''`, classpath Android28, Apache legacy, AGP `R.jar` và
JAR chuyển đổi từ APK: 292 diagnostic lỗi, trong đó 181 tại `ci.java`.

Sau nhóm sửa này và đưa bốn SDK R alias đã compile lên đầu classpath, lượt
diagnostic còn 76 lỗi ở các file khác, exit1, không còn diagnostic `ci.java`
trong pha compiler khi đó. Hai con số không phải phép so sánh chỉ riêng sửa
`ci`: thứ tự classpath và source peer cũng được cải thiện. Chưa qua hết kiểm tra
flow/checked exception của toàn bộ core; compiler có thể báo lỗi tiếp khi những
lỗi mô hình kiểu còn lại được giải quyết.

Log/command đầy đủ của lượt trên giữ trong máy tại
`/workspace/tooling/recovery-bytecode/core-ci-fixes-1.log`. Dạng lệnh của lượt
kiểm tra là:

```sh
javac -source 8 -target 8 -proc:none -Xmaxerrs 10000 \
  -d <diagnostic-classes> -sourcepath '' \
  -cp '<sdk-r-classes>:<android-28/android.jar>:<org.apache.http.legacy.jar>:<AGP-R.jar>:<greenfarm-original.jar>' \
  @<danh-sách-109-file-S800x480>
```

Đây là **diagnostic riêng**, không loại SDK khỏi build
Java sản phẩm, không tạo APK và không xác nhận game chạy. Các lượt build sau có
thể có số lỗi khác; báo cáo này ghi đúng giới hạn của nhóm thay đổi.

Các giới hạn còn lại gồm mô hình kiểu/stream của export chưa khớp DEX, tên
package/lớp trùng nhau, metadata inner class bị thiếu trong JAR chuyển đổi và
checked-exception declaration của Java export. Những giới hạn đó không được
coi là thay đổi behavior hoặc được che bằng implementation rỗng. Việc compile
và chạy baseline dùng DEX gốc cũng không xác nhận các phương thức Java đã sửa
được thực thi; cần đối chứng bản core Java riêng khi build của nó hoàn thành.

Một ví dụ mô hình kiểu đã kiểm tra trực tiếp là `S800x480/l`: cả source và
`l.smali` khai báo `java.io.DataInput`, nhưng DEX concrete class không chứa
`readUTF`/`readFully` và một số method mà compiler Java yêu cầu. Đây là hình dạng
bytecode tối ưu chưa thể biểu diễn nguyên dạng bằng source hiện tại; nhóm sửa
`ci` không thêm method rỗng, bỏ interface hoặc đoán semantics để vượt lỗi này.

## Lượt kiểm tra flow tiếp theo

Sau khi các peer/type blocker được giải quyết và module `core-source` được tạo,
compiler đi sâu hơn và báo 35 diagnostic flow trong `ci.java`. Chúng được phục
hồi theo DEX, không khởi tạo local bằng0/null tùy ý:

| Method | Join/nhánh gốc được khôi phục |
|---|---|
| `Ev()` | Ba vòng tìm icon thoát cả khi tìm thấy và hết bảng; giữ fallback frame0 đã có trong DEX. Không đổi giới hạn đọc đầu vào trông giống off-by-one nhưng tồn tại trong bản gốc. |
| `KV()` | Thoát tìm ID; fallback chọn phần tử đầu khác300/301 rồi chạy `aTE/aTG/lH/j` một lần tại join chung, không lặp side effect cho mọi row. |
| `HR()`, `HS()`, `iq(int)`, `ir(int)` | Bỏ sáu `break` chết/trùng sau các nhánh đã thoát, không bỏ điều kiện hay lời gọi trong case. |
| `KI()` | Switch thứ hai case3/11 cùng đi `:pswitch_5a3`: counter0, chỉ thay chuỗi null/trim-empty, tăng mỗi vòng. Bỏ `break` giả sau log cash để cả đường thường/catch tiếp tục tới `hT`, đúng `:goto_f4`. |
| `a(char[],int)` | Thoát lookahead khi tìm ký tự không-transparent hoặc hết chuỗi; giữ nguyên thuật toán Arabic và private `g/h` của `ci`. |
| `ax(boolean)` | Chỉ thêm hai cạnh thoát vòng đọc bảy/sáu trường `azz`; giữ byte offset+8, outer-row increment và continuation của các loop khác. |
| `M(String,String)` | Outer catch rank ghi rank1/score0; outer catch quest ghi type0/target0. Giữ thứ tự đọc `amount` trước `gift` theo try-region gốc. |
| `O(int,int,int)` | Mọi nhánh category ghi cùng register item-ID rồi mới kiểm tra/gọi `aa`; không gán lần lượt từ những local của các nhánh chưa chạy. Giữ inventory delta, limit và `aIm/baa`. |
| `dk(String)` | Bỏ comma/dot/space đúng logic gốc; sau invalid char, giữ prefix đã parse nếu có, hoặc reset prefix rỗng rồi tiếp tục. Join cuối trả parse-prefix hoặc0; overflow/null vẫn giữ exception gốc. |
| `Ld()` | Catch connectivity trả false đúng DEX, không thêm fallback online. |
| `jl(int)`, `ki(int)` | Hai loop bỏ qua row bạn bè hết phần đếm hoặc row không hợp lệ phải rơi vào render join, không tiếp tục vòng vô hạn. |
| `gL(int)` | Sprite null/frame âm bỏ qua row; giữ riêng offset/dimension và clipping cho type205, type204 dùng kích thước của chính row. Không ghi đè lại bằng local chưa gán sau dispatch. |
| `lv(int)` | Outer catch giữ chuỗi giá ban đầu; inner catch giữ giá trị thay thế hiện tại, đúng register fallback. |
| Constructor | Chiều cao fallback là min(width,height) đã đo trước parse; các parse lỗi sau giữ chiều cao hợp lệ mới nhất. |
| `jg(int)` | Khôi phục loop render `atk/atj/atl` với `!lQ`, `!aIQ/aIR/aIS`, đủ `lR()` và counter; timer/aIP loop từ0 tăng đến6. Bonus branch giữ `!IK`/`!IL`, cooldown yêu cầu `aTK` và `0<remaining<86400`, đúng các label `:cond_7f1`, `:cond_90a`, `:cond_8f4`. |

Các method body/exception table được đối chiếu trực tiếp trong cùng
`ci.smali`; read-only review độc lập cũng xác nhận các join của `M/O/KI` và
`Ev/KV/ax/gL/lv/constructor/jg`. Không có gameplay stub hoặc exception swallow
mới. Các điều kiện online/IAP chỉ được trả về đúng nhánh gốc, không giả reward
hoặc thay chính sách dịch vụ.

Lệnh thực tế `:core-source:compileDebugJavaWithJavac` sau lượt này không còn báo
diagnostic `ci`, đã xuất `ci.class`; build khi đó vẫn exit1 vì sáu lỗi flow ở
`aa.java`. Log: `/workspace/tooling/core-source-ci-late-fix.log`. Đây là trạng
thái của lượt kiểm tra đó, không thay thế kết quả build/running APK sau này.

`tests/test_ci_recovered_flow.py` dùng Code attribute của original và `ci.class`
vừa được module compile, kiểm tra source/staging giống nhau và class không cũ
hơn staging. Kết quả đạt **5.000 ca numeric-prefix và 2.000 ca Arabic của CI**
so với original bytecode, gồm Unicode digits, prefix lỗi, overflow/null outcome,
Lam/Alef và transparent characters. Test giữ superclass/helper cần thiết,
bỏ initialization Android không liên quan; không thêm implementation vào game.
Các loop render, inventory, online và constructor vẫn cần kiểm tra tích hợp
trên Android; test hai thuật toán pure này không xác nhận các đường đó.
