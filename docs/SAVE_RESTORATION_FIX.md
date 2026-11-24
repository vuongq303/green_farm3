# Sửa khôi phục tiến trình khi mở lại ứng dụng

## Phạm vi và nguyên nhân

Exception được ném tại `ProgressSaveController.globalLoadFinished(existed, loaded)`:

```text
java.lang.IllegalStateException:
Existing progress could not be restored; original save retained
```

Đường gọi ở bước tải 44 là:

```text
FarmGameEngine.qV()
  -> FarmGameEngine.fM(44)
     -> GameSaveStore.hasGameplaySave() == true
     -> FarmGameEngine.ax(false) == false
     -> ProgressSaveController.globalLoadFinished(true, false)
        -> check(!existed || loaded) -> IllegalStateException
```

Đây là assertion trên kết quả boolean, không phải exception bọc một exception
SQLite: bản thân nhánh này không có `Caused by`. Exception SQLite/checksum/codec
thật sẽ đi thẳng ra `qV()`, rồi được ghi cùng throwable đầy đủ tại
`ProgressSaveController.loadingFailed(step, failure)` bằng tag `GreenFarmSave`.
Không có logcat hoặc database của thiết bị người dùng trong workspace; báo cáo
này phân biệt cơ chế tái hiện bằng mã nguồn với kiểm chứng trên thiết bị đó.

`ax(false)` chỉ trả false khi không có buffer hoặc khi binding thiết bị bị từ
chối. Trong trường hợp record tồn tại, bộ đọc `GameSaveStore` đã kiểm checksum;
`w.Wn=false` mặc định nên không có giải mã XXTEA. Record còn tồn tại nhưng có UUID
SDK khác, cùng độ dài 36 ký tự, sẽ rơi vào nhánh từ chối binding trước khi decode
các trường gameplay.

Chuỗi nguyên nhân:

1. `rt()` ghi binding từ property `HDIDFV` vào đầu `save_gameplay`.
2. `ax()` đối chiếu binding đó với `rl()` / SDK `au.Os()`.
3. SDK `DataSharing` (`wrapper/e.java`, `init`) chỉ tìm provider authority có
   tiền tố `com.gameloft`. Provider riêng trong manifest đã đổi thành
   `${applicationId}.KeyProvider`, tức `com.quanhoang.greenfarm3.KeyProvider`.
4. Khi không có app Gameloft khác, provider riêng không nằm trong danh sách để
   SDK ghi UUID. `HDIDFV.Os()` sinh `UUID.randomUUID()`; giá trị cache trong tiến
   trình có thể mất sau cold start, khiến bản lưu cũ bị từ chối.
5. `globalLoadFinished` chuyển false đó thành exception chung; luồng tải bị
   chặn trước khi vào world. Đây không phải bằng chứng database bị hỏng.

Các kiểm chứng cũ cài đồng thời APK legacy có thể không thấy lỗi này vì provider
`com.gameloft...` của APK legacy vẫn được SDK tìm thấy.

Một đường lỗi độc lập cũng được xác nhận trong `ax()`: hàm đọc thêm record
`greenfarm3` rồi bỏ kết quả, trước khi đọc `save_gameplay`. Record phụ này không
thuộc danh sách primary; nếu không có record/marker nhập RMS, kho sẽ kiểm tra
thư mục legacy ngoài ngay cả khi SQLite chính còn tốt. Khi media không sẵn sàng,
chuỗi lỗi là `GameSaveStore.Failure("Cannot load saved game data")` với
`Caused by: IOException("Legacy save directory is unavailable")`. Lệnh đọc
thừa đã được bỏ. `GameRuntime.B` hiện chỉ đọc/giải mã, không có tác dụng khởi tạo
RecordStore nào cần giữ; lệnh đọc gameplay chính và quy trình nhập RMS thật
vẫn nguyên vẹn.

## Cách sửa và bảo toàn dữ liệu

Chỉ thay đổi production code trong `FarmGameEngine.ax(boolean)`: ở local mode,
record đọc từ kho riêng của ứng dụng (`aCw == null`) không bị kiểm tra quyền sở
hữu theo UUID của SDK. Tính toàn vẹn record vẫn do SQLite checksum và codec hiện
có kiểm tra. Không coi việc không có bản lưu hay decode thất bại là tải thành
công. Không bỏ `globalLoadFinished`, hàng rào tải hoặc kiểm tra farm.

Buffer cloud (`aCw != null`), build backend thông thường và cơ chế xác nhận save
đã nhập vẫn giữ nhánh kiểm tra định danh. Không thay đổi SDK authentication,
provider, flag local mode hoặc backend bypass để sửa một lỗi của tiến trình
local. Không tạo identity/token giả, không cần gọi backend để khôi phục local.

Không đổi schema 4, định dạng record, serializer, số dư ban đầu, giao diện,
gameplay, animation hoặc dữ liệu tài nguyên. Bản lưu của build có UUID không ổn
định được đọc trực tiếp; không cần reset, chuyển đổi hay ghi lại database.

Các bảo vệ hiện có được giữ nguyên:

- Khởi tạo SQLite trước khi tạo engine; load trước khi cho phép capture/save.
- Snapshot ghi trong transaction, WAL và `synchronous=FULL`, có checksum.
- Một `AsyncSaveQueue` với lock, coalescing và retry; không thêm worker/I/O mới.
- `beginLoading` bảo vệ record cũ; lỗi load chặn world và ghi mặc định.
- Migration bổ sung cột, không xóa/tạo lại database khi lỗi.
- Pause/hide yêu cầu capture; đường thoát chủ động chờ writer tối đa 10 giây.
  Không thể bảo đảm ghi mutation chưa capture khi hệ điều hành force-stop,
  SIGKILL hoặc mất điện; transaction đã commit trước đó vẫn là bản gốc hợp lệ.
- Giữ debounce backend 500 ms và các giới hạn batching SQLite hiện có.

## Kiểm thử và giới hạn

`tests/test_local_progress_restore.py` kiểm tra đúng prefix của `ax`, codec
binding thực tế và guard khôi phục. Mười JVM mới mô phỏng UUID thay đổi qua mười
cold start. Biên SDK local ném lỗi nếu bị gọi; kiểm tra bản lưu không đổi byte,
không yêu cầu người chơi mới. Có ca bản lưu thiếu/cắt cụt và kiểm tra normal/cloud
vẫn từ chối UUID sai. Đây không phải toàn bộ deserialize/world gameplay.

Biên storage trong regression còn từ chối mọi truy cập record legacy phụ:
khôi phục chỉ được đọc `save_gameplay` một lần, hoặc không đọc storage khi đang
dùng cloud buffer. Một fixture riêng biên dịch mã `DataSharing`/`HDIDFV` thực tế
để xác nhận provider đổi package không được tìm thấy và UUID đổi giữa các JVM.

`tests/sqlite-host/.../SQLiteSaveTest.java` bổ sung kiểm tra payload/checksum/
tombstone/generation không đổi sau load lỗi, snapshot mặc định không thể ghi đè,
migration 3→4 giữ toàn bộ record và queue không được đóng khi còn save bị khóa.
Các bài cold reopen 10 lần, rollback transaction và SQLite async có sẵn được giữ.

Kết quả chạy thực tế được ghi bên dưới sau khi các runner hoàn tất. Chưa có
thiết bị/emulator Android hoặc database thật của người dùng để xác minh UI,
nông trại hoàn chỉnh và 10 lần force-stop trên Android. Không diễn giải kiểm
thử prefix/blob/JVM như bằng chứng đã chơi toàn bộ game trên thiết bị.

Kết quả:

- Build APK local cuối bằng `:app:assembleDebug -PgreenfarmLocalDevelopment=true`:
  **đạt**, gồm kiểm chữ ký và cấu trúc runtime; artifact là
  `app/build/outputs/apk/debug/app-debug.apk`.
- Bản cũ: tái hiện đúng exception nêu trên, bytes gốc không đổi.
- Regression mới, bản sửa cuối: **5/5 đạt**; 10 cold JVM, SDK/legacy media
  không khả dụng, 1 lượt đọc gameplay, normal/cloud âm tính và dương tính.
- Bộ host rộng: **179 bài**, ban đầu 176 đạt/3 thất bại/0 bỏ qua. Hai ca
  rendering bị assertion timestamp do Sync restage file không đổi còn incremental
  javac giữ class cũ; refresh riêng task javac rồi chạy lại cả hai đều đạt
  (bao gồm kiểm tra 31 cặp overload/6 lớp và hai đường texture).
- Lỗi còn lại là fixture `test_gllib_recovered_algorithms` đã có từ lúc onboarding:
  fixture chỉ chọn `GameSaveStore.kt` mà thiếu `SaveBatchPolicy`, nên Kotlin báo
  unresolved reference. Build ứng dụng đầy đủ không có lỗi này. Không sửa fixture
  ngoài phạm vi để biến suite thành xanh.
- Các bài controller lưu/lifecycle/debounce/backend offline, currency codec,
  reward/pose, tutorial, chuyển save và kiểm tra runtime khác trong bộ rộng đạt.
- SQLite native: `compileTestJava` **đạt**, nhưng **0 bài runtime được chạy** vì
  Robolectric `nativeruntime-dist-compat:1.0.18` chuyển tải tới
  `gcp.mirrors.maven.org` bị policy mạng hiện tại trả 403. Cần áp dụng quyền mạng
  đã lưu trong bản nháp môi trường rồi chạy lại suite; không tắt TLS hay bỏ test.

Log kiểm chứng nằm trong `.local/validation/save-restoration/` (ignored):
`local-progress.log`, `original-error.log`, `python-suite.log`,
`sqlite-blocker.log`, `texture-rerun.log`, `float-rerun.log` và `build.log`.

`git diff` chỉ gồm một hàm production trong `FarmGameEngine.java`, hai file test
và tài liệu này. Không đổi schema/database người chơi, lockfile, tài nguyên,
manifest hoặc flag local mode. Chưa publish, commit hay triển khai lên thiết bị.
