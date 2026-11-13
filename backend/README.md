# Green Farm backend: JavaScript và SQLite

Backend dùng **Node.js 24+**, JavaScript ES Modules và `node:sqlite` có sẵn trong
Node. Runtime không kết nối MySQL và không dùng database RAM/JSON làm fallback.
Dependency `mysql2` được giữ cho công cụ xuất dữ liệu MySQL cũ, không nằm trong
chuỗi import của server mới.

URL chung vẫn là **`BACKEND_BASE_URL` trong `../gradle.properties`**:

```properties
BACKEND_BASE_URL=http://10.37.233.55:8787/
```

Sửa URL khi IPv4/port thay đổi, khởi động lại backend và build/cài lại APK.
`0.0.0.0` chỉ dùng làm địa chỉ bind; app không dùng localhost hoặc server giả.

## Chạy instance SQLite mới

Từ `backend` trong PowerShell:

```powershell
npm ci
Copy-Item .env.example .env
# Sửa RESEARCH_SESSION_SECRET trong .env thành giá trị riêng ít nhất 32 byte.
npm run migrate
npm start
```

Giữ `RESEARCH_SESSION_SECRET` ổn định để token compatibility đã phát hành vẫn
hợp lệ. Không đưa `.env`, secret, token hoặc database vào Git. Node tự đọc
`.env`; biến môi trường đã đặt có ưu tiên. Có thể tạo secret bằng
`node -e "console.log(require('node:crypto').randomBytes(48).toString('hex'))"`
và lưu riêng vào `.env`.

| Cấu hình | Mặc định và ý nghĩa |
| --- | --- |
| `HOST`, `PORT` | `0.0.0.0`, `8787`; port khớp URL chung. |
| `SQLITE_PATH` | `./data/green-farm.sqlite`, luôn tính tương đối từ **backend/**. |
| `SQLITE_BUSY_TIMEOUT_MS` | `100`, từ 1–1000; ghi thử tối đa 3 lần với backoff ngắn. |
| `MAX_SNAPSHOT_BYTES` | `16777216` (16 MiB archive); có thể tăng đến 256 MiB. Vượt giới hạn trả lỗi, không cắt dữ liệu. |
| `GUEST_SESSION_TTL_SECONDS` | `2592000` (30 ngày); hết hạn cấp lại bằng proof bền vững của cùng người chơi. |
| `RESEARCH_SESSION_SECRET` | Bắt buộc, tối thiểu 32 byte; chỉ cho token giao thức legacy. |
| `SESSION_TTL_SECONDS`, `REFRESH_TTL_SECONDS` | `900`, `2592000`, cho access/refresh legacy. |
| `BACKEND_CONFIG_FILE` | Tùy chọn file properties khác cho instance kiểm thử riêng; vẫn đọc đúng một khóa `BACKEND_BASE_URL`. |
| `NEW_PLAYER_DEFAULTS_FILE` | Tùy chọn file cấu hình khởi tạo riêng; mặc định `./config/new_player_defaults.json`, tính từ backend/. |

SQLite nằm ở đường dẫn lưu bền vững, dùng WAL, `synchronous=FULL`, foreign keys
và transaction. Không chấp nhận `:memory:`, URI database hoặc đường dẫn nằm trong
thư mục tạm của hệ điều hành. Giữ cả database và WAL/SHM đang hoạt động; sao lưu
instance đang chạy bằng SQLite backup API thay vì copy riêng file `.sqlite`.

`npm run migrate` tạo schema khi được yêu cầu rõ ràng. Migration bổ sung trên
file có dữ liệu tạo backup nhất quán trong `data/backups/` trước, giữ checksum
ledger và không sửa migration đã áp dụng. File hỏng hoặc checksum sai bị từ
chối, không tự reset. **`npm start` không tự tạo hoặc migration database**: nó
kiểm tra file, tính toàn vẹn và schema trước `database_ready`/`listening`; lỗi
thoát mã 1 trước khi mở HTTP.

Nếu `.env` còn `DB_NAME`/`DB_USER` của MySQL cũ, backend không tự coi SQLite
trống là tài khoản mới. Làm theo [migration MySQL → SQLite](docs/MYSQL_TO_SQLITE.md):
xuất nhất quán, `npm run migrate -- --prepare-import`, rồi nhập backup. Server
chỉ chạy sau khi có import ledger. Chỉ khi chủ động tạo **instance mới riêng**,
dùng `npm run migrate -- --new-sqlite` để ghi lựa chọn này vào database; dữ liệu
MySQL gốc vẫn giữ nguyên. Không gán tài khoản legacy/Facebook cho guest mới.

`GET /health` và `/healthz` trả HTTP 200 khi schema sẵn sàng:

```json
{"status":"ok","backend":{"status":"running"},"sqlite":{"status":"ready","schemaVersion":4}}
```

Lỗi database sau startup trả 503; đăng nhập/lưu/tải không trả success giả. Log
HTTP chỉ gồm method, route template và status; không ghi body, query, token,
password, proof cài đặt hoặc dữ liệu nông trại.

## Cấu hình tài khoản mới

Sửa **[config/new_player_defaults.json](config/new_player_defaults.json)** để quản
lý tiền, cash, level, XP trong level, bước hướng dẫn khởi tạo, ngôn ngữ và giới
hạn số đối tượng. Giá trị gốc đã đối chiếu là **2000 xu/50 cash**, level1/XP0,
tutorial0 và ngôn ngữ `auto`. 1900 xu là số dư của một farm đã chi tiêu, không
phải mặc định. [Hướng dẫn và giới hạn](docs/NEW_PLAYER_DEFAULTS.md).

Server kiểm tra cấu hình trước khi mở cổng. Mỗi lần **tạo người chơi mới**, nó
đọc lại file và lưu bản cấu hình chuẩn hóa/hash vào `gf_player_initializations`
cùng transaction tạo người chơi. Sửa file tác động tài khoản tạo sau; reissue
phiên của người chơi cũ trả đúng bản đã đóng băng, không nạp/cấp lại tài nguyên.
Người chơi có trước migration002 nhận `initialization:null`, không tự gán
defaults vào save cũ. Cấu hình hỏng khi đang chạy làm tạo người chơi mới thất
bại và rollback; cấp lại phiên cũ vẫn dùng bản cấu hình đã lưu.

## Phiên khách và API save mới

Đây là **API mới của dự án**, không phải cấu trúc API Gameloft được suy đoán.
Client giữ proof ngẫu nhiên 256 bit trong bộ nhớ riêng của ứng dụng, commit trước
khi gọi server. Server chỉ lưu SHA-256 của proof và token, cấp UUID người chơi
riêng cùng bearer token ngẫu nhiên. Cấp lại với cùng proof giữ cùng người chơi;
không dùng Android ID hoặc `playerId` tùy ý để cấp quyền.

| Route | Request / response |
| --- | --- |
| `POST /api/v1/guest/sessions` | JSON `{installationSecret}` (base64url canonical của 32 byte). Trả `{playerId,accessToken,expiresAt,initialization}`; thời hạn tính theo Unix seconds. `initialization` là `{configHash,defaults}` hoặc null cho người chơi cũ. |
| Cấp lại guest có chủ sở hữu đã biết | Thêm `previousPlayerId`; proof không tồn tại trả 404 `SESSION_IDENTITY_NOT_FOUND`, không tạo profile khác. Proof trỏ ID khác trả 409 `SESSION_IDENTITY_CONFLICT`. |
| `GET /api/v1/save` | Header `Authorization: Bearer <accessToken>`. Trả `{revision,operationId,snapshotBase64,snapshotHash}`. |
| Save chưa tồn tại | Chỉ khi phiên hợp lệ và chưa có snapshot: 404 `{error:"NO_SAVE",code:"NO_SAVE",revision:0,initialization}`. 401/503 không phải profile rỗng. |
| `PUT /api/v1/save` | JSON `{operationId,expectedRevision,snapshotBase64,snapshotHash}` và cùng bearer header. |
| Commit thành công | 200 `{revision,operationId,snapshotHash,currentRevision,replayed}` sau COMMIT. |
| Request cũ / tranh chấp | 409 `REVISION_CONFLICT` với `currentRevision`; không ghi đè. |
| Cùng operation, khác payload/base revision | 409 `OPERATION_CONFLICT`; không nhận thưởng hay tăng revision. |
| `POST /api/v1/save/adoption` | Sau xác nhận nhập và cho phép riêng của quản trị server: bearer và JSON `{operationId,snapshotHash}`. Trả `{adoptionToken,expiresAt}`, chỉ khi profile của phiên chưa có save và approval đúng người chơi/hash còn hiệu lực. |

`operationId` là UUID; retry giữ nguyên ID, base revision và blob/hash. Receipt
`gf_save_operations` cùng snapshot được commit trong một transaction. Retry sau
khi mất response trả receipt cũ với `replayed:true`; `currentRevision` có thể
mới hơn `revision`, không làm database lùi lại. Nhiều request cùng base revision
chỉ một request mới có thể commit. Client không tự merge các mảng opaque khi có
xung đột và giữ bản chờ cục bộ để xử lý.

Snapshot đầu tiên của farm mới phải chứa `new_player_defaults_v1` với đúng
64 byte ASCII configHash. Backend giải mã các trường format4 đã được kiểm chứng
bằng bytecode/JVM gốc: xu/cash/level/XP, tutorial, ngôn ngữ và capacity, rồi so
với cấu hình đã lưu. Client không có trường API để tự chọn tiền/level khởi tạo.
Các snapshot sau vẫn là tiến trình bình thường, không bị nạp lại defaults.

Đối với save cục bộ đã chơi, client phải có xác nhận nhập rõ ràng. Quản trị
backend còn phải kiểm tra nguồn bản sao và cho phép **đúng người chơi/hash**
bằng lệnh dưới đây, chạy với `SQLITE_PATH` của chính instance đang dùng:

```powershell
node scripts/approve-local-adoption.js --player-id PLAYER_UUID --snapshot-hash SNAPSHOT_SHA256 --expires-in 900
```

Thay `PLAYER_UUID` và `SNAPSHOT_SHA256` bằng hai giá trị hiển thị ở client; không
gửi token/proof trong lệnh hoặc log. Đây là CLI có quyền truy cập file database,
không có HTTP endpoint tự cấp approval. Thiếu/hết hạn approval trả 403
`LOCAL_ADOPTION_APPROVAL_REQUIRED` và giữ nguyên bản chờ ở client. Khách mới
không thể tự gắn nhãn local-copy để bỏ qua cấu hình tiền/level khởi tạo.

Capability
adoption ngẫu nhiên chỉ cho phép **chép** đúng hash và operation vào guest đang
được xác thực, có hạn 5 phút và được tiêu thụ trong transaction commit save.
Nó không chứng minh hoặc nhận quyền sở hữu một tài khoản Facebook/guest khác.
PUT có `adoptionToken` tương ứng được giữ nguyên level/số dư/marker của bản cũ;
không gán lại mặc định. Chỉ client đã ghi nhận xác nhận nhập mới xin capability
khi gặp 403 `ADOPTION_AUTHORIZATION_REQUIRED` hoặc 400 `INITIALIZATION_MISMATCH`.
Approval của quản trị cũng được tiêu thụ cùng transaction này, rollback không
làm mất quyền retry. Farm mới bị sai cấu hình không tự chuyển sang luồng nhập
này. Replay receipt
được kiểm tra trước expiry của capability, nên retry sau mất ACK không tạo save
hay thưởng lặp. Capability không bỏ kiểm tra archive/nguồn offline-test.

Snapshot là **binary GF3SAVE1 hiện có** của `migration/SaveArchive.kt`, bọc base64;
`snapshotHash` là SHA-256 toàn bộ bytes archive (gồm checksum cuối). Backend kiểm
tra magic/version/schema4, package nguồn, generation/full snapshot, giới hạn,
UTF-8, tên record duy nhất, hash từng record/hash archive, `RMSHeader`,
`save_gameplay` format4/device binding, `save_farm0` và trạng thái reward/pose
theo flag. Trạng thái gốc của tiền/cash/XP/level, kho, map, cây/công trình, đồng
hồ, nhiệm vụ/tutorial nằm trong các record opaque đầy đủ; server không tự suy
diễn layout hay cộng phần thưởng. Side farms/settings cần thiết vẫn được giữ
trong archive. Đây chưa phải nền kinh tế do server kiểm chứng từng hành động.

Nguồn `.offlinetest` và mọi record `offline_test_balances_v1` đều bị từ chối,
kể cả marker đã đánh dấu deleted. Không hạ level hoặc tiền của save hợp lệ.
Archive đã lưu bị hỏng trả 503 `SAVED_SNAPSHOT_CORRUPT`, giữ nguyên blob thay vì
trả profile rỗng. Codecs hay dữ liệu không tương thích bị từ chối để xử lý.

## Giao thức khởi động legacy được giữ

Discovery, locator, `/authorize`, `/profiles/me/myprofile` và compliance vẫn theo
form/JSON gốc đã xác minh trong [Accept](../docs/backend/ACCEPT_PROTOCOL_EVIDENCE.md).
Tài khoản, salted-scrypt hash, HMAC session và compliance nay được lưu SQLite;
refresh token rotation vẫn transaction và chống replay.

Anonymous legacy có mật khẩu dẫn xuất công khai nên **không cấp quyền save API
mới**. Token save guest độc lập, không ánh xạ tự động sang tài khoản Facebook
hoặc anonymous cũ. `NODE_ENV=production` vẫn bị từ chối do compatibility auth
còn giới hạn này. Listener hiện dùng HTTP LAN; TLS/reverse proxy chưa được thêm.
Không coi khôi phục save API như đã khôi phục social/purchase/online economy.

## Kiểm thử

```powershell
npm test
npm run test:sqlite
```

Các suite SQLite tạo file kiểm thử riêng: hai người chơi, commit bytes thực tế,
khởi động lại process Node và mở lại DB, CAS đồng thời, retry/idempotency, lỗi
SQL giữa transaction, busy có giới hạn, phiên hết hạn/cấp lại, DB hỏng/mất và
guard migration MySQL, cấu hình chỉ tác động người chơi mới, từ chối tự cấp
capability, CLI approval và chép save cũ. Import MySQL được kiểm tra với source transaction mô
phỏng và target SQLite thật; chưa kết nối database MySQL của người dùng.
`npm run test:mysql` còn là kiểm tra store legacy riêng, yêu cầu DB test có đuôi
`_test`, không chạy mặc định. Suite HTTP/MySQL cũ đã được thay bằng kiểm thử
process HTTP/SQLite của runtime hiện tại.

Kết quả client Android/lifecycle/outbox nằm trong báo cáo bàn giao của dự án;
test HTTP thành công chưa chứng minh gameplay trên thiết bị đã hoạt động.
