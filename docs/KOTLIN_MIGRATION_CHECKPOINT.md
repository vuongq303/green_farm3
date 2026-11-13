# Chuyển Kotlin và package — checkpoint

Điểm khôi phục trước thay đổi: `checkpoint/pre-kotlin-5c24d6e`; cây nguồn của
`main` sau merge PR `c8e58c5` giống checkpoint đó. Checkout ban đầu sạch, không
có chỉnh sửa người dùng bị ghi đè. Bản lưu thiết bị chưa bị xóa/reset.

## Build đối chiếu và nhóm Kotlin

Toolchain giữ Gradle 9.7.1, AGP 8.10.1, JDK 25, SDK 28/build-tools 35.0.0.
Thêm Kotlin 2.4.21, JVM target 1.8; `compile-view.jar` vẫn chỉ dùng khi biên dịch.
Kotlin stdlib được đưa vào D8 bằng đầu vào program riêng, không đóng gói SDK giả.

Lệnh thực tế trong môi trường theo `docs/ENVIRONMENT_SETUP.md`:

```sh
./gradlew :app:assembleDebug --stacktrace
```

| Mốc | Kết quả thực tế |
|---|---|
| Trước đổi mã | SUCCESS 1m12s, 42 task; source hiện tại đã build được |
| Nhóm lưu/queue/tutorial/ngôn ngữ, stream endian | SUCCESS 1m41s, 44 task; APK SHA256 `3a6ce2bc1938abaedf63bf084578f934c334a9cb208c41e809ed788932739c12` |
| Thêm callback/model, launcher/MIDlet và UI chuyển save | SUCCESS 1m11s, 44 task; APK SHA256 `4cec751a55207f9269cf8aeb7dec062c6a0d0b597b0936f3b2725f79e9111626` |

Các APK nhóm này còn application ID cũ và được giữ riêng trong `.local`.
Chữ ký v1/v2/v3, DEX 035, Application thật và 59 assets/native payload được kiểm
tra. APK nhóm hai có 7.645 định nghĩa lớp, 149 lớp có Kotlin Metadata và 62.045
method references; giữ kiểm tra giới hạn single DEX.

Kotlin runtime làm pool chuỗi vượt 65.535; DexWriter đổi `const-string` thành
`const-string/jumbo`. Đã sửa phép canonicalize phục vụ **kiểm chứng**, giữ nguyên
giá trị/register/đích nhảy/try/debug và kiểm tra biến đổi literal/branch phải bị
từ chối. Không bỏ kiểm tra SDK hoặc nới giới hạn DEX để vượt build.

## Phạm vi đã chuyển ở mốc hai

Theo [kiểm kê đầy đủ](kotlin-validation/dependency-inventory.json), trong 236 lớp
thuộc namespace game gốc: 83 lớp có nguồn Kotlin, 28 lớp nguồn Java, 125 lớp tích
hợp còn dùng DEX gốc. Toàn bộ 110 lớp lõi có bản thay thế từ source (82 Kotlin,
28 Java); không còn lấy `l`/`g` lõi từ DEX. Chín helper phục hồi và bảy thành phần
chuyển save mới không nằm trong số 236 lớp gốc.

Đây **chưa phải chuyển toàn bộ game sang Kotlin**. Các lớp Java lớn và tích hợp
DEX chưa được thay bằng stub hoặc suy đoán logic. SDK/runtime MIDlet ngoài
namespace game và 10 thư viện native vẫn được giữ, kiểm kê riêng. Chi tiết:
[dependency](KOTLIN_DEPENDENCY_INVENTORY.md), [converter](KOTLIN_CONVERTER_INVESTIGATION.md).

Test đã chạy trước đổi package: 138/138 đạt, không skip; riêng callback có 34.336
so sánh hành vi với bytecode gốc, model có đối chiếu codec/toán/digest, stream có
đối chiếu endian/EOF. ABI report kiểm tra các member Java/DEX cũ cần gọi vào
Kotlin. Build/unit không thay thế kiểm tra gameplay trên Android.

## Save và phần đang tiếp tục

[Chuyển save](SAVE_TRANSFER_KOTLIN.md) dùng tệp có checksum, transaction import,
backup bản đang có và khóa giữa các process. ID thiết bị cũ chỉ được chấp nhận
cho payload đã nhập và có hash khớp; không đổi xác thực mạng/SDK. UI phải yêu cầu
người dùng nhấn Chuẩn bị trước khi lưu/đóng game.

Đổi package, tên symbol, APK mới và kiểm thử UI/SQLite sau chuyển đổi được bổ sung
ở checkpoint tiếp theo. Các lần probe `app_process` thoát 137 chưa cho kết quả
SQLite; không được tính là đạt. Xem [log](kotlin-validation/save-transfer-cold-probe-attempts.json).

## Checkpoint package mới

Sau khi chuyển source sang `com.quanhoang.greenfarm3`, đã chạy build sạch
`:app:clean :app:assembleDebug --stacktrace`; sửa các alias `R.styleable` còn trỏ
namespace cũ và giữ nguyên các giá trị/index resource. Build package-only cuối
đạt 1m55s/44 task, APK SHA256
`972d82fe52aa259db7e395aaff315b55b5919df7766cd8b151fa8bf930ed8ec5`.
Manifest có application ID mới, launcher mới, 16 game component có định nghĩa;
DEX có 7.645 lớp và **0 lớp game thuộc package cũ**. Inverse relocation proof,
chữ ký v1/v2/v3, DEX035 và 59 payload đều đạt. Đây vẫn là checkpoint build,
chưa phải kết quả cài/chơi game của package mới.

Manifest APK gốc khai báo `.SoftKeyBoardActivity` nhưng chính DEX gốc không có
lớp đó. Verifier báo rõ baseline dangling component này; không tạo lớp rỗng,
xóa entry hoặc cho phép bỏ sót component mới. KeyProvider phải có class mới,
authority theo application ID thực; bản exporter giữ riêng đúng hai literal
authority/URI cũ đã xác minh và có inverse proof.

Lỗi SQLite read-only transaction của phần chuyển save đã được tái hiện thật
trên API28. Bản sửa dùng transaction SELECT với WAL/FULL; identity lookup dùng
một JOIN SELECT nguyên tử. **23/23 kiểm tra Android SQLite từ production source
đã đạt**: [kết quả](kotlin-validation/save-transfer-source-sqlite.json).
Không tính kết quả đó là UI hoặc probe từ APK cài đặt; các lần thoát137 cũ được
giữ làm bằng chứng lỗi đã tìm được, không phải lỗi Kotlin/thiếu RAM.

## Checkpoint tên có nghĩa

Đã áp dụng [bảy ánh xạ](VERIFIED_GAME_NAMES.md) bằng symbol owner đã resolve:
`ci/ch/GLLib` → `FarmGameEngine/FarmEntity/GameRuntime`, hai field và hai accessor
state. Javac Trees sửa 5.160 vị trí trong 22 file Java; Kotlin có guard sửa 326
tham chiếu trong 39 file. Không đổi biến local trùng tên, overload khác hoặc
member thuộc owner khác. Field tutorial vẫn là int và codec vẫn ép byte như cũ.

`:app:assembleDebug --stacktrace` đạt 1m56s/44 task sau refactor. APK checkpoint
SHA256 `138fc0e9439a08fe5cfeccbb43667f206d8a42933fc9bd8d461055520fc135cb`;
merger/relocator xác minh mode rename, full inverse, không còn lớp game package
cũ, tài nguyên/native/chữ ký/component đều đạt. Các test tập trung queue/controller/
Gloft/refactor đạt 9/9; reward/pose/model đạt 4/4 sau rename.

Tên chưa đủ bằng chứng vẫn giữ và được liệt kê trong inventory/mapping; không
tuyên bố toàn bộ 72 nghìn dòng engine đã được đặt tên hoặc chuyển Kotlin.

## Bảo toàn database khi chuyển save

Các đường mở database của migration dùng `DatabaseErrorHandler` không xóa file,
giống nguyên tắc đã có ở GameSaveStore. Handler mặc định Android có thể xóa DB
khi phát hiện hỏng; exporter/importer nay từ chối và giữ nguyên dữ liệu để phục
hồi. Fixture Android thực tế đã kiểm chứng bytes file hỏng không bị đổi/xóa khi
xuất, nhập hoặc lookup binding; target hợp lệ khác vẫn giữ nguyên.
[30/30 ca source/SQLite](kotlin-validation/save-transfer-source-sqlite.json) đạt.
Chưa coi đó là xuất/nhập qua giao diện hoặc probe từ APK được cài đặt.

## Receiver chạy khi game chưa mở

Thử UI trên bản exporter đã phát hiện lỗi thật: receiver khởi động process mới
mà chưa có MIDlet, nhưng `flushForLifecycle` gọi vào engine trước khi kiểm tra
writer. Static initializer của engine cần `GloftGF2F.acx`, dẫn đến
`ExceptionInInitializerError`. Màn chuyển save báo timeout; database vẫn giữ
nguyên, chưa xuất/nhập thành công ở lần đó.

Đã đưa kiểm tra writer-null lên trước thao tác engine, **bên trong cùng monitor**.
Nếu writer tồn tại thì vẫn cancel preview, capture và chờ durable barrier như
cũ. Regression mới chạy JVM riêng, static initializer engine cố ý lỗi nếu bị
khởi tạo quá sớm: [trước sửa thất bại](kotlin-validation/cold-flush-regression-before.log),
[sau sửa đạt](kotlin-validation/cold-flush-regression-after.log), counter engine
vẫn bằng 0 khi không có writer. Các trường hợp writer đang hoạt động vẫn đạt.

Full suite sau package/symbol đạt **154/154**, không skip, cùng **20/20** test tools.
Lượt đầy đủ này dùng APK cloud `e5026716…` trước sửa cold receiver; regression
cold và các barrier đã được chạy lại riêng sau sửa. Không coi các test host này
là bằng chứng chuyển save qua giao diện đã hoàn thành.
