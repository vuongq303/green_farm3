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
- Lưu giữa tutorial làm lộ hai khoảng phục hồi trước đây bị chặn bởi game gốc:
  bước12 mất nút nhiệm vụ sau `Ep()`, bước18 có con vật trưởng thành nhưng mất
  hiệu ứng `aoR` mà bước hướng dẫn đang chờ.
- Nút “Trò chơi +” nằm ở định danh GUI chung mọi ngôn ngữ; có đường SDK catalog,
  request cấu hình riêng và Welcome splash quảng bá chuyển qua IGP/market.

## Thay đổi chính

- `GameSaveStore`: chụp record bất biến bằng ThreadLocal và cache đã kiểm chứng,
  không SQLite I/O/khóa writer trên đường capture. Worker apply transaction;
  chỉ sửa record thay đổi. Mỗi record có thứ tự chấp nhận để callback SDK và
  snapshot enqueue ngược thứ tự không ghi đè dữ liệu mới. Schema vẫn4, giữ codec,
  checksum, legacy import, tombstone, WAL và FULL trên writer.
- `SaveBatchPolicy`: debounce200ms, maxWait500ms, ngưỡng50 dấu thay đổi; checkpoint
  thời gian8s. Đây là thời điểm được phép capture tại cuối một frame hoàn tất,
  không phải bảo đảm realtime khi OS/render đang giữ luồng game.
- `AsyncSaveQueue`: một ScheduledExecutor writer, một pending snapshot được gộp,
  retry snapshot tuyệt đối với backoff1–8s. Ack A không xóa B. Không replay thao tác
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
  Sau thành công, alarm/PendingIntent mở Start và kết thúc process cũ, tránh
  tái dùng MIDlet/static loop. Không gọi lại confirmation lúc đọc settings.
- `TutorialFlowController/ci/ch`: một cập nhật bước/frame, ownership modal/gesture,
  epoch kiểm tra callback cũ; giữ điều kiện gameplay gốc và quest thường song song.
  Khôi phục book10–12 mà không khởi tạo quest/thưởng; khôi phục riêng hiệu ứng
  tăng trưởng18 từ trạng thái con vật đã lưu, không tăng trưởng hoặc cộng thưởng lại.

## Đo trước/sau

[Phương pháp và đầy đủ số liệu](SAVE_PERFORMANCE_MEASUREMENTS.md): cùng APK helper
được kiểm thử, cùng5record256,251byte, cùng probe DEX và100 yêu cầu snapshot tổng
hợp thay đổi opaque payload, không đụng DB người chơi.

| Chỉ số | Trước | Sau |
|---|---:|---:|
| Caller p50 |242.48ms|3.48ms|
| Caller p95 |457.60ms|31.85ms|
| Caller CPU tổng |10,535.68ms|456.78ms|
| Commit bền vững |100|6|
| Block write |43,806,720B|2,633,728B|
| Tổng thời gian gồm drain |28.966s|2.114s|

Đây là **100 yêu cầu lưu trữ tổng hợp**, không phải100 lượt thu hoạch thật.
Chưa có phép đo FPS/frame time gameplay trước/sau. Không kết luận toàn game mượt
chỉ từ bảng này. Benchmark gọi capture/queue trực tiếp, không chứng minh riêng
cadence200/500ms của controller; cadence được kiểm tra bằng test deterministic.

## Kiểm thử và phạm vi

128/128 test mã và20/20 test công cụ/ngôn ngữ đạt.
Bằng chứng mã/controller: `tests/test_async_save_queue.py`,
`tests/test_progress_save_controller.py`, `tests/test_tutorial_sequence.py`,
`tests/test_more_games_removal.py`, `tools/test_language_restart_source.py`.
Các test thực thi code production với peer; không được coi là UI Android.

[27 kiểm tra SQLite Android](async-save-validation/probe-results.json) đều đạt:
transaction/kill/legacy/migration/corruption cùng capture bất biến, ghi trong khi
capture, thứ tự per-record, gộp full/partial, lỗi ghi và retry. Probe chỉ mở fixture
riêng; APK đo/probe: `1dd040b97f0bb0fd446ba90efbe4de74afb03970f522253a6b6072daefed535f`.

APK UI sau hai sửa cold-restore và gỡ Welcome entry:
`327c4c41ff29cfa31f9c22dab19a4881b50330ae4b222b11de1c35fe51cae8b9`.
Trên API28/user0: farm cũ khôi phục; một lượt thu hoạch và burst3 ô/~0.48s hoạt động,
không Saved overlay/chồng thoại/crash. Một worker SQLite được thấy; cq không còn
được tạo trong code. Bản cuối đã hiện lại book ở bước12 và giữ các ô đã thu hoạch.

Kiểm chứng UI đổi ngôn ngữ, Go/Find, Quit/MoreGames, cold restart tiếp theo đang
được ghi nhận. Chưa xác nhận50–100 lượt thu hoạch thật, số dư/XP toàn bộ, FPS,
14 ngôn ngữ trên thiết bị, repeated restart/timer qua UI hoặc Android mới.
Không xóa bản lưu để vượt tutorial.

## Cách chạy

Mở project → Android App **app** → chọn thiết bị → **Run**.
`:app:assembleDebug`/`:app:installDebug` dùng APK hoàn chỉnh; không task recovered.
`BACKEND_BASE_URL` duy nhất vẫn ở root `gradle.properties`, IPv4 Windows
`http://10.37.233.55:8787/`. Các APK Android cloud dùng URL override cho môi trường
thử; Windows LAN/binary nhúng IP Windows chưa được chạy từ cloud.
