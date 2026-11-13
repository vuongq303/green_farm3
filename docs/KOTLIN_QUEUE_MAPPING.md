# Kotlin: hàng đợi lưu và checkpoint gameplay

Nhóm này chuyển triển khai thật của ba lớp sang Kotlin, không gọi lại bản Java cũ:

- `AsyncSaveQueue.kt`: một worker SQLite, một pending ticket được gộp và retry snapshot tuyệt đối.
- `SaveBatchPolicy.kt`: lịch debounce 200 ms, maxWait 500 ms, ngưỡng 50 dấu thay đổi.
- `ProgressSaveController.kt`: capture cuối frame, durable barrier khi đổi world/lifecycle/ngôn ngữ, rollback preference và thông báo lỗi có giới hạn.

Các tên public vốn đã mô tả đúng trách nhiệm được giữ lại. Giai đoạn còn có lời gọi
Java/DEX phải giữ constructor, tên hàm, descriptor và field của API này. `ci.qD`,
`ci.av`, `ci.rs`, `ci.aYU` và các peer obfuscated chưa được đổi tên trong nhóm này;
chúng là hợp đồng ở checkpoint chuyển Kotlin đầu tiên. Sau đó lớp `ci/ch/GLLib`,
field `aYR/baa` và accessor `qD/qE` được đổi tên đồng bộ bằng source/DEX refactor
trong [bảng 7 tên đã xác minh](VERIFIED_GAME_NAMES.md). Các hàm codec `av/rs`
và field ngôn ngữ `aYU` vẫn giữ nếu chưa có phân tích riêng.

## Ánh xạ tên đã xác minh

Phạm vi dưới đây là **khai báo và tham chiếu riêng của từng lớp**, không phải quy
tắc thay tên toàn project. Không tên nào thay khóa SQLite, codec hoặc dữ liệu save.

| Phạm vi | Tên cũ | Tên mới | Ý nghĩa xác minh từ nơi đọc/ghi |
|---|---|---|---|
| `SaveBatchPolicy` field | `first` | `firstChangeUptimeMillis` | Uptime lúc chấp nhận thay đổi đầu tiên trong batch chưa capture. |
| `SaveBatchPolicy` field | `last` | `lastChangeUptimeMillis` | Uptime của thay đổi gần nhất, dùng cho debounce. |
| `SaveBatchPolicy` field | `operations` | `pendingOperationCount` | Số dấu thay đổi, chặn trên ở 50; không khẳng định một dấu luôn là một lần thu hoạch. |
| `SaveBatchPolicy.changed/due` parameter | `now` | `nowUptimeMillis` | Mốc `SystemClock.uptimeMillis`, đơn vị ms. |
| `SaveBatchPolicy.changed` parameter | `count` | `newOperationCount` | Số dấu thay đổi mới được chuyển vào batch. |
| `AsyncSaveQueue` field | `pending` | `pendingTicket` | Snapshot tuyệt đối chờ writer tiếp theo, có thể đã gộp nhiều yêu cầu. |
| `AsyncSaveQueue` field | `running` | `writeScheduledOrRunning` | Writer đã được lên lịch hoặc đang chạy; giữ true cả trong backoff. |
| `AsyncSaveQueue` field | `failures` | `consecutiveFailureCount` | Số lần lỗi liên tiếp để chọn backoff 1–8 giây. |
| `AsyncSaveQueue` field | `durableSequence` | `lastDurableSequence` | Sequence đã nhận commit thành công; hàm public `durableSequence()` giữ nguyên. |
| `AsyncSaveQueue` private function | `merge` | `mergeTickets` | Gộp snapshot, lấy sequence lớn nhất và checkpoint mới nhất không null. |
| `AsyncSaveQueue.writeNext` local | `start` | `startNanos` | Mốc `System.nanoTime` trước apply. |
| `AsyncSaveQueue.writeNext` local | `duration` | `durationNanos` | Thời gian apply, đơn vị ns. |
| `AsyncSaveQueue.writeNext` local | `delay` | `retryDelayMillis` | Thời gian chờ retry, đơn vị ms. |
| `AsyncSaveQueue.awaitIdle` local | `end` | `deadlineNanos` | Hạn chờ theo đồng hồ monotonic ns. |
| `AsyncSaveQueue.awaitIdle` local | `remaining` | `remainingNanos` | Thời gian còn lại trước hạn chờ ns. |
| `ProgressSaveController` field | `requests` | `requestedSequence` | Atomic sequence của các thay đổi/yêu cầu lưu đã chấp nhận. |
| `ProgressSaveController` field | `completions` | `completedWrites` | Kết quả writer được game thread lấy ra xử lý. |
| `ProgressSaveController` field | `batch` | `batchPolicy` | Chính sách xác định thời điểm capture. |
| `ProgressSaveController` field | `writer` | `sqliteWriteQueue` | Hàng đợi sở hữu duy nhất worker SQLite. |
| `ProgressSaveController` field | `observedRequest` | `lastObservedRequestSequence` | Sequence đã đưa vào chính sách batch. |
| `ProgressSaveController` field | `savedRequest` | `durableRequestSequence` | Sequence cao nhất game thread đã xác nhận commit. |
| `ProgressSaveController` field | `lastCapture` | `lastCaptureUptimeMillis` | Uptime của capture thành công gần nhất. |
| `ProgressSaveController` field | `lastAttempt` | `lastCaptureAttemptUptimeMillis` | Uptime của lần thử capture gần nhất; giới hạn retry lỗi serialize. |
| `ProgressSaveController` field | `unloadSequence` | `pendingUnloadSaveSequence` | Checkpoint phải commit trước khi thực hiện command rời world. |
| `ProgressSaveController` field | `unloadAction` | `pendingUnloadAction` | Action của command gắn với checkpoint rời world. |
| `ProgressSaveController` field | `unloadTarget` | `pendingUnloadTargetState` | Engine state đích của command đó. |
| `ProgressSaveController` field | `unloadDepth` | `pendingUnloadStackDepth` | Độ sâu stack state của command đó. |
| `ProgressSaveController` field | `acknowledgedEffects` | `legacyCallbackSequence` | Sequence đã chạy callback phụ sau durable ack, tránh callback lặp. |
| `ProgressSaveController.checkpointBeforeMove` local | `mode` | `previousMoveMode` | Move mode tạm thay khi capture rồi khôi phục trong finally. |
| `ProgressSaveController.capture` local | `oldLanguage` | `previousLanguage` | Locale của phiên hiện tại được khôi phục sau capture preference. |

## Hợp đồng JVM và tính đồng thời

- API static của controller dùng `@JvmStatic`; các hàm này tiếp tục được Java và DEX gọi bằng descriptor cũ.
- Sáu field public của `AsyncSaveQueue.Result` dùng `@JvmField`, vì caller Java đọc trực tiếp field thay vì getter.
- Các hằng số policy dùng `const val`, giữ public static final và giá trị cũ.
- Các hàm queue trước đây `synchronized` dùng `@Synchronized` trên chính instance queue. `wait/notifyAll` vẫn cùng monitor đó.
- Controller đồng bộ lifecycle bằng `FarmGameEngine::class.java`, là monitor engine `ci.class` được đổi tên nhất quán cùng các caller. Các flag load/save trước đây `volatile` vẫn có `@Volatile`.
- `===` được dùng cho checkpoint identity; không thay so sánh tham chiếu bằng `equals`.
- Dữ liệu nullable như pending ticket, checkpoint và failure giữ nullable. Tham số exception không dùng của `isCriticalLoadFailure` cũng nullable, tránh thêm kiểm tra null vào hợp đồng Java cũ.
- `beginCapture/finishCapture` vẫn chạy trên game thread; apply/hash/SQLite vẫn chạy trên worker. Không đổi schema 4, codec hoặc trạng thái retry/rollback.

## Kiểm tra đã chạy

`tests/test_async_save_queue.py` biên dịch **hai file Kotlin production** với peer Java
và chạy 5 ca: cadence, A bị giữ và B/C gộp, lỗi/retry, checkpoint hủy, lifecycle/interrupt/close.
`tests/test_progress_save_controller.py` biên dịch controller/policy/language Kotlin
với Android/SQLite/writer peer có điều khiển, kiểm tra batching, detached snapshot,
durable transition, Cancel/OK lặp, retry/rollback, late SDK mutation và restart handoff.
Cả hai bộ đều đạt sau chuyển đổi. Đối chiếu classfile của baseline Java với Kotlin
giữ đủ 46 member public cũ (descriptor, static và synchronized flags), gồm các
interface/field kết quả: [bảng ABI](kotlin-validation/queue-public-abi.json).
Đối chiếu này thực hiện trước khi đổi package; đổi package là bước di chuyển có
chủ ý, không phải giữ nguyên descriptor chứa tên package cũ. Đây là JVM behavioral fixture, không được xem là
kiểm thử SQLite Android hoặc thao tác UI trên thiết bị.

Lệnh:

```sh
python3 -m unittest discover -s tests -p test_async_save_queue.py -v
python3 -m unittest discover -s tests -p test_progress_save_controller.py -v
```

`tools/kotlin_test_tools.py` gọi compiler Kotlin 2.4.21, sau đó javac phát các Java peer
với Kotlin classes/stdlib trong classpath. Compiler host được bootstrap dưới `.local`
bằng URL Maven chính thức và SHA-256 ghim trong `tools/ensure_kotlin_compiler.py`; compiler,
reflect và coroutines dùng cho tooling không được đóng gói vào APK.
