# Chuyển bản lưu sang `com.quanhoang.greenfarm3`

Application ID mới có UID/bộ nhớ riêng. Bản mới không đọc `/data/data` của bản cũ,
không tự giả định đã nhập tiến độ và không xóa ứng dụng cũ. Cơ chế chuyển dùng tệp
`.gf3save` do người chơi chọn; định dạng nông trại, tiền, kho, đồng hồ, quest,
tutorial, thưởng đang chờ và hướng sprite trong các RMS record được giữ nguyên byte.

## Cách dùng

1. Giữ bản cũ và bản lưu của nó. Build/cài **cập nhật** bản cầu nối có application ID
   cũ, bằng đúng khóa ký đã dùng cho bản cũ:

   ```sh
   ./gradlew :app:assembleDebug -PgreenfarmLegacyExport=true
   ```

   Không gỡ bản cũ để khắc phục lỗi chữ ký: gỡ sẽ xóa dữ liệu riêng của ứng dụng.
   Cờ này chỉ đổi application ID để cập nhật ứng dụng cũ; mã nội bộ của bản cầu nối
   vẫn dùng package mới. Bản build mặc định là ứng dụng `com.quanhoang.greenfarm3`.
2. Trong bản cũ, mở farm và đợi checkpoint thành công. Mở **Chuyển bản lưu** qua
   menu Android được bổ sung; Android25+ còn có shortcut khi nhấn giữ biểu tượng
   game. Chọn **Chuẩn bị**, rồi **Xuất bản lưu hiện tại**, chọn nơi lưu tệp.
3. Cài bản mới bằng build mặc định. Mở **Chuyển bản lưu**, chọn **Chuẩn bị**,
   **Nhập bản lưu từ tệp**, chọn tệp vừa xuất. Sau khi kiểm tra checksum/cấu trúc,
   xác nhận thay thế. Import không tự chạy nếu chưa xác nhận.
4. Chọn **Mở game**, kiểm tra farm, ngôn ngữ và tiến độ. Giữ bản cũ/tệp xuất đến
   khi đã xác nhận đọc, chơi, lưu và mở lại ở bản mới.

Android19+ dùng Storage Access Framework, không yêu cầu hai ứng dụng chia sẻ
bộ nhớ riêng. Android16–18 xuất vào thư mục Downloads sau xác nhận và dùng trình
chọn tệp `GET_CONTENT` để nhập. Nếu thiết bị thiếu trình chọn tệp, giao diện báo
lỗi và giữ dữ liệu; không làm game văng.

Nếu ứng dụng đích đã có một farm hợp lệ, bản lưu đó được ghi thành
`files/greenfarm-before-import.gf3save` và `fsync` trước khi transaction thay thế.
Nút **Xuất bản sao trước lần nhập gần nhất** cho phép lấy tệp này để nhập lại.
Bản lưu đích không hoàn chỉnh/hỏng có dấu vết gameplay không bị âm thầm ghi đè;
phải giữ để chẩn đoán. Các cài đặt khởi tạo chưa có gameplay/farm có thể được thay
bằng bản đã chọn. Chỉ một bản sao trước lần nhập gần nhất được giữ tự động.

## Tính nhất quán và vòng đời

- `SaveTransferActivity` chạy trong process `:save_transfer`, không thêm launcher
  MAIN thứ hai hoặc đổi cấu hình Run **app**.
- Receiver `SaveTransferPreparationReceiver` **không exported**, chạy ở process
  game. Người chơi nhấn **Chuẩn bị** mới bắt đầu, mở activity không tự đóng game.
  Receiver đặt freeze của luồng input/frame hiện có, gọi
  `ProgressSaveController.flushForLifecycle()`; chỉ khi nhận durable
  acknowledgement mới kết thúc process cũ. Nếu lưu thất bại, giữ phiên cũ và báo
  thử lại, tháo freeze; yêu cầu chuẩn bị trùng không tạo checkpoint song song.
  Thao tác này cũng kết thúc loop/static cache của MIDlet cũ.
- `SaveTransferGate.enterGame()` giữ khóa file của hệ điều hành suốt vòng đời
  process game. Mỗi lần xuất/nhập cần lấy cùng khóa riêng. Người chơi mở lại game
  giữa lúc chọn tệp sẽ không khiến hai process đồng thời ghi/đọc sai snapshot;
  thao tác transfer bị từ chối nếu game đã giữ khóa. Game không được khởi tạo khi
  transfer đang giữ khóa.
- Xuất đọc một SQLite transaction nhất quán, kiểm tra `integrity_check`, checksum
  SHA-256 từng record, tombstone, generation và marker snapshot. Không copy riêng
  file `.db` rồi bỏ quên WAL. Giữ WAL và `synchronous=FULL`.
  Trên API28, `beginTransaction()` với handle `OPEN_READONLY` thực tế ném
  `SQLITE_READONLY`. Vì vậy đường xuất mở `OPEN_READWRITE|ENABLE_WRITE_AHEAD_LOGGING`,
  đặt FULL rồi bắt đầu `beginTransactionNonExclusive()` chỉ thực thi SELECT;
  không INSERT/UPDATE/DELETE bảng game. Khóa liên process giữ game đã đóng.
  Lookup identity dùng một JOIN SELECT nguyên tử, vẫn là handle read-only và
  không gọi BEGIN. Lỗi API này đã được tái hiện bằng Java tối thiểu trên Android,
  bản transaction WAL/FULL sửa lại đã chạy đạt; không quy lỗi cho Kotlin.
- Import kiểm tra toàn bộ tệp trong worker trước khi xin thay thế. Một transaction
  cập nhật slot, record, commit, trạng thái legacy import và binding chuyển save;
  lỗi ghi rollback toàn bộ. Không replay thao tác gameplay hay cộng thưởng khi retry.
  Giữ marker legacy cũ để các file RMS cũ không xuất hiện lại sau khi nhập.
- Tệp có magic/version, giới hạn 2.048 record, 64 MiB mỗi record và 128 MiB tổng
  payload; checksum toàn tệp bảo vệ cả metadata. Trùng tên, cắt cụt, dữ liệu dư,
  UTF-8 sai, checksum hoặc marker không hợp lệ bị từ chối trước khi import.

## Định danh bản lưu và tài khoản

Game gốc ghi HDIDFV ở đầu `save_gameplay` format4: byte version, độ dài little
endian 32-bit và chuỗi UTF-8. `ci.ax` trước đây từ chối UUID khác nếu độ dài bằng
nhau. Đổi application ID có thể tạo UUID SDK khác dù cùng điện thoại.

Import lưu thêm bảng `save_transfer_identity` có metadata version1 trong cùng
transaction; `PRAGMA user_version` vẫn4. Bảng chứa đúng binding và SHA-256 của
payload gameplay đã nhập. `SaveTransferIdentity.matches(saved,current)` chỉ chấp
nhận binding hiện tại hoặc binding đã nhập khi checksum **payload đang được lưu**
khớp metadata và chính prefix của payload. Hook chỉ ở phép so sánh binding khi
load gameplay; không bỏ kiểm tra gốc, không thay UUID SDK, `rv()`, xác thực hoặc
request backend. Sau khi game ghi snapshot mới, encoder gốc dùng định danh hiện
hành bình thường. Tên record, khóa codec và dữ liệu số không được đổi theo tên biến.

Tệp xuất chứa các RMS record của game và định danh trong save, nên cần giữ riêng
tư. Không xuất toàn bộ thư mục ứng dụng, SharedPreferences SDK/backend, password
MySQL, access/refresh token ngoài RMS hoặc khóa ký. Backend mới tiếp tục cấp phiên
riêng; tệp này không giả lập đăng nhập Gameloft hay chuyển tài khoản dịch vụ cũ.

## File và hợp đồng tích hợp

Các file ở `app/src/main/kotlin/com/quanhoang/greenfarm3/migration/`:

| File | Trách nhiệm |
|---|---|
| `SaveArchive.kt` | Codec transfer, giới hạn kích thước, checksum, giữ record byte chính xác. |
| `SaveArchiveDatabase.kt` | SQLite read transaction, backup và import transaction, giữ schema4. |
| `SaveTransferIdentity.kt` | Binding nhập hợp lệ được ràng buộc với checksum gameplay. |
| `SaveTransferGate.kt` | Khóa liên process, chống game mở khi transfer đang ghi. |
| `SaveTransferPreparationReceiver.kt` | Durable checkpoint ở process cũ rồi kết thúc process đó. |
| `SaveTransferActivity.kt` | Giao diện xuất/nhập, SAF, xác nhận, retry và xuất bản sao. |
| `SaveTransferShortcuts.kt` | Shortcut Android25+, không thêm launcher game thứ hai. |

`Start` phải gọi `SaveTransferGate.enterGame(this)` trước khi tạo MIDlet, xử lý
false bằng thông báo và dừng khởi tạo, sau đó cài shortcut. Manifest khai báo
activity transfer process riêng và receiver không exported. Cấu hình ký debug
hiện có được giữ; bridge APK phải được ký bằng khóa cũ để cập nhật trên máy thật.

## Kiểm chứng

`tests/test_save_transfer_archive.py` chạy **6/6** kiểm tra production Kotlin: byte
exact roundtrip/tombstone/time/language, thay từng byte bằng một bit, mọi vị trí
cắt cụt, trailing data, metadata/flags/generation/record bắt buộc, binding/UTF-8 và
bound trước allocation. Đây không phải kiểm thử UI hoặc chứng minh mọi game blob
có thể load: chỉ game gốc có đủ bảng entity để xác minh quan hệ pose/farm.

`tests/test_save_transfer_gate.py` chạy **3/3** kiểm tra khóa file thật giữa các
JVM do test tạo: game giữ khóa chặn transfer, transfer đang ghi chặn game mới,
kernel nhả khóa khi process test bị kill; đồng thời kiểm tra một owner freeze và
tháo freeze sau thất bại. Context/identity là peer để cô lập gate; đây không phải
Android UI hoặc phép đo vòng đời MIDlet.

`tools/run_save_transfer_probe.py` biên dịch production archive/database/gate/identity
với fixture riêng để chạy **Android SQLite thật**; không mở DB người chơi hoặc
cài/dừng game. Ví dụ khi một trong hai application ID đã được cài:

```sh
ANDROID_HOME=/path/to/android-sdk python3 tools/run_save_transfer_probe.py \
  --installed-runtime --classes-root app/build/tmp/kotlin-classes/debug \
  --package com.gameloft.android.ANMP.GloftGF2F --prefix st_example_01
```

Chọn prefix mới cho mỗi lần chạy. Chế độ `--installed-runtime` chỉ biên dịch probe,
nạp implementation/stdlib từ APK đã cài và ghi hash APK thực tế; không đóng gói
bản sao helper vào DEX probe. Chế độ source độc lập cũng có để chẩn đoán; hai lần
nạp lạnh DEX độc lập trả mã137 không có PASS, chưa được tính là SQLite đạt
(xem `save-transfer-cold-probe-attempts.json`). Sau đó Java tối thiểu có catch
stdout đã xác định `SQLiteReadOnlyDatabaseException` ở BEGIN: RuntimeInit đã
chuyển exception chưa bắt thành SIGKILL nên lần đầu chỉ thấy137. Đây là lỗi dùng
API transaction đã sửa ở trên; không phải bằng chứng lỗi Kotlin hoặc thiếu RAM.
Probe ghi rõ source SHA-256, DEX hash và từng check khi hoàn tất
trong `docs/kotlin-validation/save-transfer-sqlite.json`; payload tổng hợp
không phải farm có thể chơi. Các kết quả UI export cầu nối → import package mới,
đọc farm thật và mở lại phải được báo riêng trong checkpoint tổng của đợt chuyển
Kotlin. Không suy ra đã đạt từ test codec hoặc build thành công.

Nếu `app_process` của môi trường không chạy fixture được, dùng runner có
Application/Context Android được gắn đúng vòng đời:

```sh
ANDROID_HOME=/path/to/android-sdk python3 tools/run_save_transfer_instrumentation.py \
  --prepare-only --package com.quanhoang.greenfarm3
# Sau khi đã checkpoint game và cài APK đích hoàn chỉnh:
ANDROID_HOME=/path/to/android-sdk python3 tools/run_save_transfer_instrumentation.py \
  --use-prepared --package com.quanhoang.greenfarm3 --prefix st_instrument_01
```

Tool tạo APK kiểm thử riêng dưới `.local`, ký bằng khóa debug hiện có; DEX chỉ có
probe/Instrumentation, không sao chép helper hoặc stdlib của game. `am instrument`
tạo lại process đích nên phải lưu game trước; ContextWrapper giới hạn mọi database
trong thư mục fixture mới, không mở database người chơi. `--prepare-only` không
dùng ADB, không cài hoặc dừng ứng dụng. Kết quả ghi rõ hash APK đích, test APK,
từng check và giới hạn. Runner này không nằm trong APK game hoặc cấu hình Run app.
