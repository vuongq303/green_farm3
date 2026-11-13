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
