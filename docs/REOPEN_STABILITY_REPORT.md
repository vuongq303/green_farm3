# Ổn định mở lại và đổi ngôn ngữ Green Farm 3

Bản sửa dựa trên checkpoint `67b0c46`. Không thay schema 4, codec save, tiền/kho/cây/vật nuôi, tài khoản, URL backend hoặc tài nguyên gốc. Không thêm thư viện hoặc khôi phục APK offline. Đây là báo cáo lỗi đã xác minh bằng source/fault injection và các giới hạn chưa thể kiểm tra trên thiết bị.

## Kiến trúc và phạm vi

`Start` (Android Activity) khởi tạo wrapper MIDlet `GloftGF2F`, rồi `FarmGameEngine`/`GameRuntime` và renderer OpenGL. Tài nguyên được đóng thành các pack số trong assets. Tiến độ cục bộ do `GameSaveStore` quản lý; trạng thái gameplay trong RAM được chụp thành snapshot tuyệt đối, đưa sang writer SQLite tuần tự. Không thêm nguồn cloud save hoặc thay đổi quyền sở hữu dữ liệu giữa client/backend.

## Nguyên nhân xác minh được

1. **Vòng lặp đọc pack không kết thúc.** `GameRuntime.c(boolean,String)` là hàm rỗng. Reader cũ gọi nó khi EOF nhưng vẫn cộng/trừ giá trị `-1`, rồi bắt exception và tiếp tục lặp; skip cũng lặp nếu `skip()` trả 0. Reproducer trích hàm từ checkpoint cũ bị timeout 750 ms cho cả hai trường hợp. Bản sửa đọc/skip có tiến triển hoặc ném `ResourceFailure`; không cấp buffer giả/đọc thiếu như thành công.
2. **Cache pack nửa khởi tạo.** Tên `RI` được gán trước khi stream/header hợp lệ; mở lại cùng tên có thể bỏ qua open dù `RJ` hoặc bảng offset không dùng được. Chỉ tái sử dụng khi stream/tables còn hợp lệ, kiểm header/offset và vô hiệu đúng pack lỗi. Có test mở thiếu file/header bị cắt, sửa nguồn rồi mở lại cùng đường dẫn.
3. **Cache MIME ảnh hưởng pack đang tải.** `bm('/0')` từng dùng chung `RJ`/`RN` nhưng chỉ khôi phục stream, không khôi phục cursor; cache MIME còn có thể publish trước khi đọc xong. Đã dùng stream cục bộ và chỉ publish cache hoàn chỉnh. Test xác nhận cursor của pack đang mở không đổi và cache lỗi không được dùng lại.
4. **Loader đánh dấu bước lỗi là đã tải.** `FarmGameEngine.qV()` bắt exception không thuộc save rồi vẫn tăng `asj`. Resource failure hiện giữ nguyên bước, backoff 250/500 ms, tổng 3 lần mỗi đợt; sau đó báo lỗi và đợi người dùng thử lại. Không reset toàn bộ game hoặc SQLite. Hai bước SDK tùy chọn48(tracking)/127(IAP setup) vẫn giữ hành vi log rồi tiếp tục với lỗi **không phải** ResourceFailure; không mở rộng việc bỏ qua sang quest/định nghĩa gameplay.
5. **Race của renderer trước/sau Init.** Constructor đăng ký canvas, đặt `QD` và `Qn=-1` trước khi `Init` hoàn tất; callback draw có thể coi đây là thoát và giải phóng tài nguyên. Thêm readiness theo instance: không chạy frame trước Init thành công. Callback từ instance lỗi hoặc instance cũ không được vẽ, đổi GL state hoặc đóng owner mới. Surface setup trước Init vẫn được phép.
6. **Singleton startup có thể bị giữ sau lỗi.** `acw` đã được publish nhưng constructor/Init thất bại không rollback. Bootstrap nay khóa theo lớp để hai MIDlet không tạo hai engine, giữ hợp đồng publish trước Init, bắt cả hai dạng lỗi và đánh dấu đúng candidate thất bại. Nút retry có thể tạo candidate mới; không teardown/reset một engine đang hoạt động.

Những lỗi này đã được tái hiện bằng source/fixture điều khiển được. Chưa có log hoặc chạy trên chính thiết bị gặp thông báo nên **không khẳng định mọi lần lỗi tài nguyên người dùng thấy đều do duy nhất các đường trên**.

## Sửa thông báo ngôn ngữ và restart

- Dùng lại engine 20/GUI4 của game; thành công chỉ có **Khởi động lại**, dịch theo locale vừa chọn. Không có Hủy/Để sau/Đóng và Back không bỏ qua popup.
- Không chỉ đổi chuỗi: stage đúng font/charmap gốc Latin, VI, TH, JP, KR; xác minh đủ slot và publish cả nhóm sau cùng. Tái dùng cache hợp lệ; cache đã giải phóng phải nạp lại. Không `hs()` nhóm font cũ đang được tham chiếu; chỉ giữ một nhóm cũ trong thời gian bắt buộc chờ restart.
- Save gameplay/header phải durable trước thông báo thành công. Nhấn Restart chuẩn bị launcher handoff rồi làm `FINAL_SAVING` bất đồng bộ để lưu cả thay đổi SDK/settings đến trong lúc popup mở. Chỉ đặt alarm và kết thúc process sau ack cuối. Chạm lặp không nhân đôi handoff; đọc header lúc khởi động không gọi `select()`, nên không tự tạo vòng lặp restart.
- Lỗi checkpoint/handoff giữ tiến trình hiện tại, một nút Restart để thử lại; không tự lặp theo frame. Font target lỗi chỉ thử 2 lần mỗi yêu cầu, sau đó dùng font/locale còn hợp lệ để báo **lỗi tài nguyên** và một nút Thử lại. Đó không phải thông báo thành công bằng ngôn ngữ cũ; lựa chọn đã lưu vẫn được giữ.
- Không đăng xuất tài khoản vì kiến trúc này chỉ cần nạp lại MIDlet/static/font. Không đổi số dư, farm hoặc schema.

Chi tiết: [LANGUAGE_RESTART_FLOW.md](LANGUAGE_RESTART_FLOW.md).

## Tối ưu có bằng chứng

- **Resume UI:** bỏ `Thread.sleep(1000)` khỏi `Start.onResume`. Dimensions đến từ `View.post(bt)` trên cùng UI queue, nên sleep không thể làm dimensions sẵn sàng. Giữ nguyên callback `bt/bu`, gate dimensions, audio/super lifecycle và wrapper. Fixture cùng cold/warm branch: trước 1000,3/1014,7 ms, sau 0,010/0,006 ms. Đây là thời gian JVM của hàm, không phải FPS Android.
- **SQLite xóa lặp:** cache payload bỏ key null nhưng kiểm tra tombstone cũ lại dựa vào key null tồn tại. 100 lệnh delete lặp tạo 100 commit thừa, generation 1→101. Chỉ đọc cờ deleted khi cần phân biệt chưa có record với tombstone; sau sửa giữ generation 1, kể cả sau reopen. Vẫn tạo tombstone đầu tiên để không nhập lại dữ liệu RMS cũ. Tạo lại rồi xóa cũng được kiểm tra.
- Giữ debounce 500 ms/max wait 2000 ms/ngưỡng 50, một writer và một pending snapshot gộp theo version, transaction, prepared statement, WAL, synchronous FULL, retry hữu hạn và barrier thoát. Không bỏ checkpoint tiền/kho hoặc đổi snapshot tuyệt đối thành delta có nguy cơ cộng trùng.
- Không nạp lại toàn bộ asset khi retry một record/bước; không tạo thêm worker tải tài nguyên. Shared pack monitor và thứ tự khóa engine→pack được giữ; font hook không lấy engine lock từ bên trong pack lock.

## Kiểm thử

Kết quả cuối và hash APK: [reopen-stability-results.json](maintenance-validation/reopen-stability-results.json).

Kết quả cuối: **138/153 bài host đạt, 15 bỏ qua; 20/20 bài công cụ và 9/9 bài SQLite host đạt**. Các bài bỏ qua là những kiểm tra có prerequisite artifact/tool chưa sẵn sàng trong môi trường, không được tính là đạt. `assembleDebug` thành công (2 phút 22 giây); xác minh chữ ký v1/v2/v3 và `zipalign -P 16` đạt.

Các nhóm kiểm tra đã chạy gồm:

- Pack reader: 7 regression, 1.504 lượt đọc xuôi/ngược từ 15 pack chuẩn và 14 text locale, segmented pack, EOF/read exception, zero-progress, mở lại cùng tên, MIME atomic/cursor isolation và pre-Init/failed/stale renderer.
- Loader retry: không advance khi lỗi, không spin trong backoff, dừng sau 3 lần, retry thủ công, không reset loading/storage, phân biệt SDK tùy chọn và tài nguyên thật, 5 pipeline mới liên tiếp.
- Bootstrap/resume: storage barrier, constructor/Init failure, fresh retry, nhiều MIDlet đồng thời chỉ tạo một engine, repeated resume, dimensions chưa sẵn sàng và Activity finishing.
- Ngôn ngữ: 14 locale, nút duy nhất, Back, staged font cache/owner, font failure có giới hạn, late writes trước restart, final save failure/retry và alarm handoff được mô phỏng có kiểm soát.
- Native SQLite/Robolectric API 35: 9 bài, gồm idempotency/stale order, reopen, complete snapshot, WAL/migration, rollback sau data rows, database lock/recovery, bảo vệ header và repeated delete tombstone.

Lệnh tái lập (kích hoạt JDK/SDK đúng như môi trường hiện tại):

```sh
python3 -m unittest discover -s tests
python3 -m unittest discover -s tools -p 'test_*.py'
./gradlew :app:assembleDebug -PpythonExecutable=python3
./gradlew -p tests/sqlite-host test
```

Những test host thu hoạch/trồng/vật nuôi trước đó vẫn chạy trong suite chung. Chúng là test logic/controller/codec/hiệu ứng với peers hoặc payload cô lập; không được gọi là thao tác 100 ô hay kiểm tra animation/số dư thực trên Android.

## Giới hạn và kiểm tra thiết bị còn cần

- Chưa chạy APK trên emulator/thiết bị thật. Môi trường không có KVM và chưa có Android device đã xác minh. Native SQLite host dùng framework API 35; không chứng minh Android API 16–28 hoặc toàn bộ vòng đời thiết bị hoạt động đúng.
- Cần cold launch, force-close/reopen nhiều lần, background/resume và restore sau resource lỗi trên thiết bị. Kiểm riêng context-loss GL, âm thanh, resident texture và việc kết thúc Activity.
- Cần đổi EN↔VI/TH/JP/KR, xem glyph/wrapping/clipping/font và nút duy nhất; kiểm alarm restart trên Android mới có hạn chế exact alarm. Nếu target font thực sự hỏng, lỗi tài nguyên sẽ hiện bằng locale còn đọc được, không giả vờ đã tải thành công.
- Cần thao tác harvest 1/10/50/100 ô, trồng liên tục, nhiều vật nuôi, thoát khi writer bận và kiểm farm sau reopen. Không có frame trace/FPS hoặc memory profiler trên thiết bị; không tuyên bố hết mọi giật/đứng.
- Mở/migration DB đầu phiên và serialization snapshot vẫn theo kiến trúc hiện có; không đưa world mutable sang worker tùy tiện. Dữ liệu RAM chưa commit không được bảo đảm khi OS kill đột ngột.
- Chưa sửa API/account/network hoặc backend, và không tải thêm thư viện. Các cấu hình APK offline đã xóa vẫn vắng mặt.

## File thay đổi

Không xóa file trong đợt sửa này. Không sửa asset, native library, pin APK/tool, dependency production hoặc schema.

Mã nguồn:

- [GameRuntime.java](../app/src/main/java/com/quanhoang/greenfarm3/S800x480/GameRuntime.java): pack I/O, cache MIME, readiness/owner của renderer.
- [FarmGameEngine.java](../app/src/main/java/com/quanhoang/greenfarm3/S800x480/FarmGameEngine.java): retry từng bước và staging font.
- [GloftGF2F.kt](../app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/GloftGF2F.kt): khởi tạo tuần tự, rollback candidate lỗi.
- [ProgressSaveController.kt](../app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/ProgressSaveController.kt): thông báo lỗi bootstrap/resource và retry thủ công.
- [LanguageRestartController.kt](../app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/LanguageRestartController.kt): popup bắt buộc, locale đích và checkpoint cuối.
- [GameSaveStore.kt](../app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/GameSaveStore.kt): bỏ ghi tombstone trùng.
- [Start.kt](../app/src/main/kotlin/com/quanhoang/greenfarm3/Start.kt): bỏ sleep trên UI.

Kiểm thử:

- [test_resource_pack_io.py](../tests/test_resource_pack_io.py), [test_resource_load_retry.py](../tests/test_resource_load_retry.py), [test_startup_lifecycle.py](../tests/test_startup_lifecycle.py), [test_language_notification_fonts.py](../tests/test_language_notification_fonts.py): regression mới.
- [test_progress_save_controller.py](../tests/test_progress_save_controller.py), [test_language_restart_source.py](../tools/test_language_restart_source.py): cập nhật contract popup/restart.
- [SQLiteSaveTest.java](../tests/sqlite-host/src/test/java/greenfarm/SQLiteSaveTest.java): regression xóa lặp.

Tài liệu/kết quả: [README.md](../README.md), [LANGUAGE_RESTART_FLOW.md](LANGUAGE_RESTART_FLOW.md), [MAINTENANCE_REPORT.md](MAINTENANCE_REPORT.md) (đánh dấu lịch sử), báo cáo này và [reopen-stability-results.json](maintenance-validation/reopen-stability-results.json).
