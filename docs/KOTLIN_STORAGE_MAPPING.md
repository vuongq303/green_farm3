# Kotlin: lưu tiến trình và codec phụ trợ

Ba implementation Kotlin thay thế hoàn toàn phần Java tương ứng; không gọi lại
bản Java cũ. Các entry point JVM được giữ tên và descriptor để Java/DEX đang có
vẫn gọi đúng. Package ở checkpoint nhóm đầu vẫn là package runtime cũ; kết quả
đổi package cuối cùng được báo riêng trong tài liệu migration chung.

| Phạm vi | Trước | Sau | Hành vi đã xác minh |
|---|---|---|---|
| `GameSaveStore` | lớp Java với static API | Kotlin `object`, các API dùng `@JvmStatic` | Giữ lời gọi static từ `GLLib`, queue, controller và probe Android. |
| `GameSaveStore.write` | ba biểu thức kiểm tra payload lặp | `validPayload(payload, offset, length)` | Cùng thứ tự kiểm tra null, offset, length và giới hạn64MiB; lỗi capture không làm hỏng transaction writer. |
| `GameSaveStore.Snapshot` | `Map<String, byte[]>` có tombstone null | `Map<String, ByteArray?>` | Null là xóa record, khác hoàn toàn record vắng trong map; giữ merge theo version của từng record. |
| `GameSaveStore` | `database` private static | `database` private static do Kotlin object sinh | Tên được giữ vì probe durability đọc live connection bằng reflection. |
| `PendingRewardState.capture/apply` | `e`, `v` | `effect`, `words` | Hiệu ứng pickup và28 word snapshot; giữ thứ tự tuyệt đối trong binary record. |
| `EntityPoseState.writeSnapshot` | `x`, `y` | `gridX`, `gridY` | Tọa độ grid đã lượng tử hóa, thu hẹp signed16bit giống Java `(short)`. |
| `EntityPoseState.restoreSnapshot` | biến local `entity` của target | `targetEntity` | Phân biệt actor đang duyệt với actor được đối chiếu identity của entry. |
| Hai codec | vòng lặp liên kết Java `for` | Kotlin `while`, cập nhật `aoi` sau xử lý | Không đổi traversal order, occurrence counter hoặc thứ tự restore pickup. |

Giữ nguyên `RMSHeader`, `save_gameplay`, `save_farm0`, `pending_rewards_v1`,
`entity_pose_v1`, tên database, slot, schema4, các SQL/migration và checksum SHA256.
Không đổi các field obfuscate của `ch`/`aa`: các codec này biết vị trí binary và
vai trò tổng thể nhưng chưa chứng minh ý nghĩa riêng của mọi word.

`EntityPoseState.spriteUpdated(ch)` giữ Java hook cho lớp actor package-private.
`internal` giữ phạm vi Kotlin phù hợp; `@JvmName("spriteUpdated")` chỉ tại hook đó
ngăn suffix tên internal của Kotlin để call site Java vẫn thấy đúng tên cũ. Các `@JvmStatic` là cầu nối
thật cho call site Java/DEX. Snapshot không cần `@JvmField`; trường private được
Kotlin truy cập qua getter nội bộ, public API đọc metadata vẫn giữ nguyên.

Kiểm thử nhóm này thực thi Kotlin thật cùng các Java peer trích từ logic gốc:

- `test_pending_reward_state.py`: resume pickup, settle/credit đúng một lần,
  binary malformed/flags, chest stage không random lại, rollback allocator/retry.
- `test_entity_pose_state.py`: signed definition/grid, occurrence duplicate,
  identity/format fail trước khi mutate, restore sprite lazy building17 qua
  decoder/initializer/update gốc; không tạo sprite giả hoặc xóa pose.
- `GameSaveStore` được compile với Android28 và dependency gốc; public JVM API
  được đối chiếu với Java baseline. [27/27 probe Android SQLite mới](kotlin-validation/store-probe-results.json)
  đã đạt trên APK `090e1cc7…0efa38be`, application ID `com.quanhoang.greenfarm3`.
  DEX probe chỉ chứa7 lớp kiểm tra; storage implementation được nạp từ APK đã cài,
  không đóng gói bản sao helper. Các fixture tách biệt không mở database người chơi.
  WAL/FULL, kill trước/sau commit, migration/legacy, corruption, write-error/retry
  và các snapshot async/ordering/coalescing đều được kiểm tra thực tế.
