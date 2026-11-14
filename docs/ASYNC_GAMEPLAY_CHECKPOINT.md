> Checkpoint lịch sử. Cấu hình hiện tại là 500/2000 ms, ngưỡng 50 và retry hữu hạn; xem [báo cáo cập nhật](MAINTENANCE_REPORT.md).

# Checkpoint: lưu nền, tutorial và ngôn ngữ

Nền mã: `2198e07`, một module Android Java/MIDlet `app`. Backend vẫn JavaScript/MySQL
cho account/session; nông trại nằm trong SQLite riêng trên thiết bị. Không xóa data,
đổi package, đổi codec game, resource ID, assets hoặc native libraries.

## Nguyên nhân đã xác định

- `ProgressSaveController.frameFinished → ci.av/rs → beginSave/commitSave` trước đây
  serialize và ghi WAL/FULL đồng bộ dưới khóa frame. Dirty state liên tiếp khiến
  snapshot toàn bộ record và commit chặn luồng game khoảng mỗi 500 ms.
- `cq` còn một vòng polling 50 ms dù controller đã nhận yêu cầu trực tiếp.
- Nhãn lưu là TextView gắn vào DecorView, phát sinh cập nhật UI và một overlay.
- Tutorial `jN` có nhánh nhận click lời thoại đặt `axW` cho cả bước gameplay;
  một số điều kiện chỉ đặt true mà không loại bỏ cờ cũ. Callback modal, animation
  và nút GUI bị trễ chưa xác minh bước/phiên hiện tại.
- Pose restore từng yêu cầu sprite phải có ngay; building 17 hợp lệ chỉ tạo sprite
  theo animation. Bản lưu thật có 10/10 key khớp farm nhưng sprite này vắng khi decode.
  Helper nay giữ transform chờ rồi áp dụng khi code gốc tạo sprite, không xóa pose
  hay bỏ kiểm tra identity. Chính dữ liệu lỗi đã mở lại được không sửa SQL.
- Lưu giữa tutorial làm lộ hai khoảng phục hồi trước đây bị chặn bởi game gốc:
  bước 12 mất nút nhiệm vụ sau `Ep()`, bước 18 có con vật trưởng thành nhưng mất
  hiệu ứng `aoR` mà bước hướng dẫn đang chờ.
- Nút “Trò chơi +” nằm ở định danh GUI chung mọi ngôn ngữ; có đường SDK catalog,
  request cấu hình riêng và Welcome splash quảng bá chuyển qua IGP/market.

## Thay đổi chính

- `GameSaveStore`: chụp record bất biến bằng ThreadLocal và cache đã kiểm chứng,
  không SQLite I/O/khóa writer trên đường capture. Worker apply transaction;
  chỉ sửa record thay đổi. Mỗi record có thứ tự chấp nhận để callback SDK và
  snapshot enqueue ngược thứ tự không ghi đè dữ liệu mới. Schema vẫn 4, giữ codec,
  checksum, legacy import, tombstone, WAL và FULL trên writer.
- `SaveBatchPolicy`: debounce 200 ms, maxWait 500 ms, ngưỡng 50 dấu thay đổi; checkpoint
  thời gian 8 s. Đây là thời điểm được phép capture tại cuối một frame hoàn tất,
  không phải bảo đảm realtime khi OS/render đang giữ luồng game.
- `AsyncSaveQueue`: một ScheduledExecutor writer, một pending snapshot được gộp,
  retry snapshot tuyệt đối với backoff 1–8 s. Ack A không xóa B. Không replay thao tác
  thu hoạch/trao thưởng khi retry. Lifecycle và chuyển world chờ durable barrier.
- `ProgressSaveController/ci/GloftGF2F`: encoder chạy tại ranh giới game-thread,
  SQLite/hash/so sánh record/commit chạy ở worker; bỏ polling cq. Không export DB
  hoặc gọi API sau từng thu hoạch. Encoder farm gốc vẫn chụp theo **batch**;
  chưa thay farm BLOB bằng schema mỗi cây/con vật.
- Bỏ hoàn toàn View/nhãn lưu thông thường. Chỉ log lỗi và một toast ngắn cho một
  đợt lỗi thực; không có View hoặc vùng chặn chạm ở vị trí nhãn cũ.
- `MoreGamesRemoval/ci`: bỏ button/label/animation/hitbox/listener/catalog entry,
  request riêng và hai entry Welcome splash. Giữ currency+, rewarded ads,
  privacy/terms, shared tracking/CRM và SDK runtime gốc.
- `LanguageRestartController`: xác nhận bằng ngôn ngữ hiện tại (14 mã menu),
  Cancel giữ phiên; OK một lần khóa thao tác, lưu gameplay trước rồi preference.
  Nếu cần rollback preference, chỉ báo trở lại setting cũ sau durable rollback.
  Trong tutorial, phím Menu Android mở Options hiện có (không bỏ bước).
  Sau thành công, alarm/PendingIntent mở Start và kết thúc process cũ, tránh
  tái dùng MIDlet/static loop. Không gọi lại confirmation lúc đọc settings.
- `TutorialFlowController/ci/ch`: một cập nhật bước/frame, ownership modal/gesture,
  epoch kiểm tra callback cũ; giữ điều kiện gameplay gốc và quest thường song song.
  Khôi phục book 10–12 mà không khởi tạo quest/thưởng; khôi phục riêng hiệu ứng
  tăng trưởng 18 từ trạng thái con vật đã lưu, không tăng trưởng hoặc cộng thưởng lại.

## Đo trước/sau

[Phương pháp và đầy đủ số liệu](SAVE_PERFORMANCE_MEASUREMENTS.md): cùng APK helper
được kiểm thử, cùng 5 record/256.251 byte, cùng probe DEX và 100 yêu cầu snapshot tổng
hợp thay đổi opaque payload, không đụng DB người chơi.

| Chỉ số | Trước | Sau |
|---|---:|---:|
| Caller p50 |242.48ms|3.48ms|
| Caller p95 |457.60ms|31.85ms|
| Caller CPU tổng |10,535.68ms|456.78ms|
| Commit bền vững |100|6|
| Block write |43,806,720B|2,633,728B|
| Tổng thời gian gồm drain |28.966s|2.114s|

Đây là **100 yêu cầu lưu trữ tổng hợp**, không phải 100 lượt thu hoạch thật.
Chưa có phép đo FPS/frame time gameplay trước/sau. Không kết luận toàn game mượt
chỉ từ bảng này. Benchmark gọi capture/queue trực tiếp, không chứng minh riêng
cadence 200/500 ms của controller; cadence được kiểm tra bằng test deterministic.

## Kiểm thử và phạm vi

129/129 test mã và 20/20 test công cụ/ngôn ngữ đạt.
Bằng chứng mã/controller: `tests/test_async_save_queue.py`,
`tests/test_progress_save_controller.py`, `tests/test_tutorial_sequence.py`,
`tests/test_more_games_removal.py`, `tools/test_language_restart_source.py`.
Các test thực thi code production với peer; không được coi là UI Android.

[27 kiểm tra SQLite Android](async-save-validation/probe-results.json) đều đạt:
transaction/kill/legacy/migration/corruption cùng capture bất biến, ghi trong khi
capture, thứ tự per-record, gộp full/partial, lỗi ghi và retry. Probe chỉ mở fixture
riêng; APK đo/probe: `1dd040b97f0bb0fd446ba90efbe4de74afb03970f522253a6b6072daefed535f`.

APK UI theo từng checkpoint được phân biệt với APK Windows bàn giao:

- `1dd040b97f0bb0fd446ba90efbe4de74afb03970f522253a6b6072daefed535f`:
  probe/benchmark và thu hoạch thật một ô rồi burst 3 ô trong khoảng 0,48 s.
  Ô đã thu hoạch hiển thị đúng, không nhãn Saved, không FATAL EXCEPTION.
- `327c4c41ff29cfa31f9c22dab19a4881b50330ae4b222b11de1c35fe51cae8b9`:
  book tutorial bước 12 hiện lại; mở Farm Diary “Big Shoes to Fill” thấy ba
  mục tiêu 0/1; Go/Find mở chợ. Mua và xác nhận đặt bê trừ đúng 100 xu
  (2.000 → 1.900, cash 50). Mở sách không tự hoàn thành mục tiêu.
- `e99e12fdf6805149d586cd66e76d3dc679abc9a7b5f35d80ba63d5298e464382`:
  Menu mở Options, không MoreGames. Chọn VI hiện confirmation bằng English đang
  dùng; Cancel giữ PID, byte ngôn ngữ và generation. Bốn OK nhanh dẫn đến một
  tiến trình mới tự động; kiểm thử phát hiện lỗi khôi phục sprite lazy.
- **Bản sửa cuối**, nguồn app `cd8ff09`, APK cloud
  `a9ce1b7da2d3f9515bca3c47955dcf713c1c548f9401efb24a8132059e7aa759`:
  mở lại chính dữ liệu từng lỗi, không sửa/xóa SQL; farm, các ô đã thu hoạch và bò
  trưởng thành còn nguyên, lời thoại tiếng Việt gốc hiển thị. Đổi ngược VI → EN
  hiện confirmation bằng tiếng Việt hiện tại; bốn OK nhanh chỉ tạo một lần tự
  khởi động lại (PID 20377 → 20959), ổn định qua 65 s quan sát. Farm đã vào lại,
  không loader lỗi 47/FATAL, một worker `GreenFarm-SQLit`. Cold restart cuối cũng
  mở lại farm: PID 21432, ngôn ngữ EN/0, tutorial 23, header generation 1090,
  gameplay generation 1138. Không lặp xác nhận hoặc overlay lưu. HUD bị tutorial
  ẩn nên chưa đọc lại độc lập toàn bộ số dư/XP cuối; chưa thử restart dài hạn.

[Bằng chứng runtime đã lọc](async-save-validation/game-runtime.json),
[hộp thoại tiếng Việt](async-save-validation/ui/vietnamese-confirm-english.png),
[farm sau cold restart](async-save-validation/ui/farm-after-cold-reopen.png).

Không xóa hoặc reset bản lưu để vượt tutorial. Các ảnh/log thử runtime được giữ
riêng với database, credential và thông tin định danh; chỉ bằng chứng đã lọc mới
được đưa vào repository.

### Ma trận 12 trường hợp nghiệm thu

“Đạt ở mức code/probe” có nghĩa đã thực thi các helper/controller production với
peer kiểm thử hoặc SQLite Android fixture; không tương đương hoàn tất thao tác UI
trên một farm thật. Kết quả dưới đây giới hạn đúng dữ liệu đã quan sát.

| # | Trường hợp yêu cầu | Kết quả đã xác minh | Phần còn thiếu |
|---|---|---|---|
| 1 | Thu hoạch nhanh 50–100 cây, đúng sản phẩm/XP/quest | **Một phần UI:** một ô và burst 3 ô thật hoạt động, hiện ô trống/XP. | Chưa chạy 50–100 lượt thật, chưa đối chiếu toàn bộ kho/XP/quest trước–sau. 100 snapshot tổng hợp không thay thế ca này. |
| 2 | Thu hoạch liên tục vượt maxWait vẫn lưu | **Đạt ở mức code:** test thời gian điều khiển xác minh debounce 200 ms, maxWait 500 ms và ngưỡng 50; benchmark queue có 6 commit. | Chưa đo cadence với thu hoạch liên tục thật. MaxWait là lịch capture tại ranh giới frame, không bảo đảm commit trong 500 ms khi OS/render/SQLite chậm. |
| 3 | Tiếp tục thao tác khi batch A đang ghi | **Đạt ở mức code/probe:** chặn writer A, enqueue/gộp B/C, xác minh ack A không xóa B; snapshot bất biến và thứ tự per-record được kiểm tra trên Android SQLite. | Chưa đối chiếu sản phẩm/XP qua UI với writer cố ý làm chậm. |
| 4 | Lưu xong đóng/mở lại khôi phục đúng | **Đạt trên dữ liệu đã thử:** các ô đã thu hoạch, farm và bò trưởng thành tồn tại qua tự restart ngôn ngữ và cold restart cuối; locale EN và tutorial 23 được giữ. Chính bản lưu từng lỗi mở lại được sau sửa pose. | Chưa có ma trận đầy đủ mọi công trình/vật nuôi/kho đồ hoặc đối chiếu số dư/XP cuối khi HUD đang ẩn. |
| 5 | Ghi lỗi rồi retry không mất/nhân đôi sản phẩm | **Đạt ở mức code/probe:** transaction rollback, retry snapshot tuyệt đối, ghi khi đang capture, thứ tự record và gộp full/partial; không replay thao tác nhận thưởng. | Chưa giả lập disk-full/I/O lỗi trên farm UI đang chơi. |
| 6 | Bấm nhanh intro không chồng thoại/bỏ bước | **Đạt ở mức code; một phần UI:** kiểm tra ownership bước/phiên/gesture, callback cũ, điều kiện gameplay gốc; UI sách quest không tự hoàn thành, Go/Find và mua bê đúng luồng. | Chưa stress toàn bộ intro bằng chuỗi touch nhanh trên thiết bị. |
| 7 | Đổi ngôn ngữ khi còn thay đổi chờ lưu | **Đạt ở mức code; một phần UI:** controller kiểm tra gameplay → preference, freeze/ack/rollback/retry; VI → EN thực tế tự restart một lần và vào lại farm. | Chưa chứng minh bằng UI việc OK trùng đúng một batch gameplay đang bị giữ có chủ ý. |
| 8 | Đổi ngôn ngữ giữa tutorial giữ đúng tiến độ | **Đạt trên đoạn tutorial đã thử:** farm ở giữa tutorial được khôi phục với VI, tiếp tục lời thoại gốc và đổi lại EN; sửa cold restore bước 10–12/18 có test riêng. | Chưa bao phủ mọi bước, mọi trạng thái thưởng hoặc tất cả 14 ngôn ngữ trên thiết bị. |
| 9 | OK nhiều lần hoặc Cancel | **Đạt UI trên các đường đã thử:** Cancel EN → VI giữ PID/header/generation; 4 OK nhanh VI → EN chỉ một PID mới, không lặp confirmation trong 65 s. | Chưa lặp hàng chục chu kỳ hoặc thử process bị OS kill đúng lúc chuyển giao. |
| 10 | Không MoreGames và nhãn Saving/Saved ở mọi nơi | **Đạt ở mức code/resource; một phần UI:** kiểm tra định danh GUI chung, 14 bộ ngôn ngữ, route/listener/request riêng; UI farm/menu đã thử không nhãn lưu/MoreGames. | Chưa duyệt mọi màn hình/ngôn ngữ trên thiết bị. Không coi SDK/asset gốc còn trong APK là một route quảng bá đang hoạt động. |
| 11 | Nhiều restart không nhân đôi vòng lặp/listener/writer | **Một phần:** phiên cũ kết thúc bằng kill process; PID mới có đúng một worker SQLite, không lặp restart qua 65 s; cold restart cuối cũng chỉ một worker và không lặp xác nhận. | Chưa stress nhiều chu kỳ hoặc kiểm đếm mọi timer/listener Java trong game gốc; thread name không đủ chứng minh mọi vòng lặp. |
| 12 | Đóng đột ngột khi có dữ liệu chờ | **Đạt ở mức Android SQLite fixture:** kiểm tra kill/transaction, dữ liệu đã commit nhất quán với WAL/FULL. | Chưa kill farm UI đúng giữa batch. Dữ liệu chỉ ở RAM hoặc snapshot chưa commit có thể mất khi process chết; maxWait không biến RAM thành dữ liệu bền vững. |

### File thay đổi và vai trò

Các file Java dưới
`app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480/`:

| File | Vai trò |
|---|---|
| `GameSaveStore.java` | Capture bất biến, cache, thứ tự record, gộp snapshot, apply SQLite chỉ cho record đổi; giữ schema/codec/durability. |
| `AsyncSaveQueue.java`, `SaveBatchPolicy.java` | Một writer nền, pending coalescing, ack/retry và lịch 200/500 ms/50 dấu thay đổi. |
| `ProgressSaveController.java` | Kết nối dirty gameplay → capture/queue, durable barrier, chuyển world/lifecycle và checkpoint ngôn ngữ; bỏ overlay lưu thường xuyên. |
| `ci.java`, `GloftGF2F.java` | Tích hợp cuối frame, bỏ polling và đường catalog/Welcome, Menu Options trong tutorial, giữ callback game gốc. |
| `LanguageRestartController.java` | Confirmation bằng locale hiện tại, Cancel/OK một lần, lưu rồi tự restart phiên; xử lý thất bại có retry. |
| `TutorialFlowController.java`, `ch.java` | Ownership modal/callback, chuyển bước tuần tự theo điều kiện thật, cold restore UI/hiệu ứng và hook sprite. |
| `EntityPoseState.java` | Giữ transform đang chờ đến khi sprite hợp lệ được code gốc tạo, vẫn kiểm tra identity/format. |
| `MoreGamesRemoval.java` | Danh sách GUI entry/hitbox/label riêng cho cross-promotion và kiểm soát route tương ứng. |

`tools/prepare_core_dependencies.py` đưa các helper vào pipeline `app`; các
`tests/test_*.py`, probe Java và runner dưới `tools/` kiểm chứng các luồng trên.
Báo cáo chuyên biệt: [queue](ASYNC_SAVE_QUEUE.md), [store](ASYNC_SAVE_STORE.md),
[ngôn ngữ](LANGUAGE_RESTART_FLOW.md), [tutorial](TUTORIAL_SEQUENCE_RECOVERY.md),
[MoreGames](REMOVE_MORE_GAMES.md).

### Giới hạn hiệu năng còn lại

SQLite/hash/record comparison đã ra worker, nhưng encoder gốc vẫn chạy ở cuối
frame và tạo snapshot farm BLOB theo batch. Log live chưa được kiểm soát có
75 capture: median 95,257 ms, p95 1.168,008 ms, max 3.030,676 ms; các lần đó
trùng tải emulator/probe/compile, không có phép đo CPU hoặc trước/sau tương ứng.
Không thể quy toàn bộ thời gian đó cho SQLite hoặc dùng để tuyên bố FPS đã tăng.
Codec vẫn khoảng 563 KB scratch allocation và 308 KB bản sao record trong workload
9 record đã khảo sát. Cần ca 50–100 thu hoạch thật với frame/CPU profile trước khi
kết luận toàn bộ thao tác/render đã mượt. Máy thật, Android mới, Windows LAN và
backend/session ở các tình huống mất kết nối chưa được kiểm chứng ở mốc này.

## Cách chạy

Mở project → Android App **app** → chọn thiết bị → **Run**.
`:app:assembleDebug`/`:app:installDebug` dùng APK hoàn chỉnh; không task recovered.
`BACKEND_BASE_URL` duy nhất vẫn ở root `gradle.properties`, IPv4 Windows
`http://10.37.233.55:8787/`. Các APK Android cloud dùng URL override cho môi trường
thử; Windows LAN/binary nhúng IP Windows chưa được chạy từ cloud.
