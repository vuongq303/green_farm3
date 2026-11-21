# Green Farm backend: JavaScript và MySQL

Luồng Website độc lập với cấu hình/token Facebook; xem bản sửa và kiểm thử tại
[WEBSITE_AUTH_ISOLATION.md](../docs/WEBSITE_AUTH_ISOLATION.md).
Đăng ký/đăng nhập tài khoản riêng bằng trình duyệt ngoài, migration MySQL và cấu hình HTTPS/
Cloudflare Tunnel được mô tả trong [EMBEDDED_AUTH.md](EMBEDDED_AUTH.md). Nút Facebook
giữ OAuth chính thức; dữ liệu và API Google cũ được bảo toàn, xem
[SOCIAL_AUTH.md](SOCIAL_AUTH.md). Dữ liệu nông trại vẫn ở SQLite
trên thiết bị; các API legacy bên dưới chỉ phục vụ tương thích nghiên cứu và
không thay thế xác thực tài khoản mới.

Backend dùng Node.js 24+, JavaScript ES Modules và `mysql2/promise`. Điểm khởi
động là `src/main.js`; không cần TypeScript hoặc bước transpile. Một nguồn URL
chung cho app và backend nằm tại **`../gradle.properties`**, biến
`BACKEND_BASE_URL`. Backend đọc trực tiếp file này để tạo mọi URL discovery.

```properties
BACKEND_BASE_URL=https://backend.example.com/
```

Đây là URL ví dụ; dùng hostname, IPv4 hoặc IPv6 của backend thật. Nếu URL đổi, sửa
biến này, khởi động lại backend, **build và cài lại APK**. Đổi file cấu hình không
đổi được URL đã nhúng trong APK đang cài. Không còn biến `localBackendUrl`,
`PUBLIC_BASE_URL`, `BIND_HOST` hoặc fallback về localhost/`10.0.2.2`.

## Cấu hình và chạy

Từ thư mục `backend` trong PowerShell:

```powershell
npm ci
if (!(Test-Path .env)) { Copy-Item .env.example .env }
```

Điền thông tin MySQL thực tế vào `backend/.env`. Giữ
`LEGACY_AUTH_ENABLED=false` cho luồng tài khoản Website, OAuth và giftcode.
Khi cần phiên ẩn danh legacy trong môi trường nghiên cứu, đặt
`LEGACY_AUTH_ENABLED=true` và đặt `RESEARCH_SESSION_SECRET` ngẫu nhiên, ít nhất
32 byte UTF-8. Giữ secret đó ổn định qua các lần khởi động lại để bảo toàn
hiệu lực phiên legacy. Khi một mật khẩu có khoảng trắng ở đầu/cuối hoặc chứa
`#`, dùng dấu nháy trong file `.env` để giữ đúng giá trị. Giữ thông tin kết nối
và secret trong môi trường backend.

MySQL 8.0/8.4: dùng tài khoản quản trị của chính bạn để tạo database và tài khoản
ứng dụng một lần. Thay mật khẩu trong ví dụ trước khi chạy; không dùng tài khoản
root cho server:

```sql
CREATE DATABASE IF NOT EXISTS green_farm CHARACTER SET utf8mb4 COLLATE utf8mb4_bin;
CREATE USER 'green_farm_app'@'localhost' IDENTIFIED BY '<your-private-password>';
GRANT SELECT, INSERT, UPDATE, DELETE, CREATE, ALTER, INDEX, REFERENCES
  ON green_farm.* TO 'green_farm_app'@'localhost';
```

Nếu user đã tồn tại, quản lý mật khẩu/quyền của user đó thay vì chạy lại
`CREATE USER`. Tài khoản migration cần quyền tạo bảng; triển khai nghiêm ngặt có
thể dùng tài khoản migration riêng rồi chỉ cấp SELECT/INSERT/UPDATE/DELETE cho
user chạy app. Database không tự tạo và không bị reset bởi chương trình.

Sau khi `.env` khớp MySQL:

```powershell
npm run migrate
npm start
# Hoặc khi phát triển:
npm run dev
```

Lệnh migration chỉ cần nhóm `DB_*`. Lệnh khởi động đọc cấu hình server và các
tính năng đã bật, rồi kiểm tra kết nối và schema MySQL trước khi mở cổng HTTP.
Khi triển khai lên database đang hoạt động, dừng các tiến trình ghi, sao lưu và
kiểm tra khả năng phục hồi theo [hướng dẫn triển khai MySQL](database/README.md).
Migration khóa chống chạy đồng thời, đối chiếu checksum và giữ các bản ghi hiện
có. Bản v5 chuyển liên kết nhóm phiên vào bảng phiên. Bản v6 bổ sung các bảng
giftcode. Bảng liên kết cũ có thủ tục dọn dẹp riêng sau khi đối chiếu dữ liệu.

### Nguồn cấu hình

[`src/config.js`](src/config.js) đọc và chuẩn hóa cấu hình. Thứ tự ưu tiên là
**biến của tiến trình → `backend/.env` → mặc định**. Cả giá trị rỗng đã đặt
trong tiến trình cũng ghi đè file. Module xác định đường dẫn `.env` từ vị trí
của chính nó nên thứ tự này giống nhau dù chạy lệnh từ thư mục nào.
Ứng dụng khởi tạo cấu hình một lần rồi truyền từng nhóm đến các module sử dụng.

| Cấu hình backend | Giá trị/mục đích |
|---|---|
| `HOST`, `PORT` | Địa chỉ bind IPv4/IPv6 hoặc `localhost`, mặc định `0.0.0.0:8787`. Cổng từ 1 đến 65535. |
| `BACKEND_CONFIG_FILE` | Đường dẫn thay thế đến file có đúng một khóa `BACKEND_BASE_URL`. Ưu tiên dùng đường dẫn tuyệt đối. Mặc định đọc `gradle.properties` ở gốc repo. |
| `DB_HOST`, `DB_PORT` | Mặc định `127.0.0.1:3306`. Cổng từ 1 đến 65535. |
| `DB_NAME`, `DB_USER`, `DB_PASSWORD` | Bắt buộc. Tên database dài 1–64 ký tự ASCII gồm chữ, số hoặc gạch dưới. |
| `DB_POOL_SIZE` | Mặc định 5 kết nối, từ 1 đến 50. |
| `DB_CONNECT_TIMEOUT_MS` | Mặc định 5000 ms, từ 100 đến 30000 ms. |
| `LEGACY_AUTH_ENABLED` | Nhận `true` hoặc `false`. File mẫu chọn `false`. Khi biến vắng mặt, legacy mặc định bật ngoài production. Trong `NODE_ENV=production`, legacy mặc định tắt và giá trị `true` bị từ chối. |
| `RESEARCH_SESSION_SECRET` | Bắt buộc khi legacy bật, ít nhất 32 byte UTF-8. |
| `SESSION_TTL_SECONDS` | Access token legacy mặc định 900 giây, từ 1 đến 86400. |
| `REFRESH_TTL_SECONDS` | Refresh token legacy mặc định 2592000 giây, ít nhất bằng access TTL, tối đa 7776000. Hai TTL legacy được kiểm tra khi legacy bật. |
| `AUTH_PUBLIC_BASE_URL` | Origin HTTPS cho xác thực và giftcode. Khi bật, đặt cùng giá trị với `AUTH_BASE_URL` trong Gradle. |
| `AUTH_TRUSTED_PROXY_IP` | IP đầu nối của reverse proxy mà Node nhìn thấy. Proxy này phải ghi đè `X-Forwarded-Proto` và `X-Forwarded-For`. |
| `GOOGLE_OAUTH_CLIENT_ID`, `GOOGLE_OAUTH_CLIENT_SECRET` | Cặp cấu hình tùy chọn cho Google. |
| `FACEBOOK_APP_ID`, `FACEBOOK_APP_SECRET`, `FACEBOOK_GRAPH_VERSION` | Cặp cấu hình tùy chọn cho Facebook. Phiên bản Graph mặc định `v24.0`. |
| `GIFTCODE_ADMIN_PASSWORD_HASH` | Hash Argon2id tùy chọn để bật đăng nhập quản trị tại `/admin/giftcodes`. Xem [hướng dẫn giftcode](../docs/GIFTCODES.md). |

Origin gồm giao thức, hostname hoặc IP và cổng nếu cần. Node nghe HTTP trên
`HOST`/`PORT`. Để phục vụ xác thực Website hoặc OAuth, cấu hình chứng chỉ hợp lệ
trên reverse proxy và đặt origin HTTPS công khai. Đăng nhập bằng mật khẩu
Website hoạt động khi các cấu hình OAuth tùy chọn để trống.

### Dùng cấu hình trong mã

| Nhóm | Trường |
|---|---|
| `config.server` | `host`, `port`, `publicBaseUrl`. |
| `config.database` | `host`, `port`, `database`, `user`, `password`, `connectionLimit`, `connectTimeout`. |
| `config.sessions.legacy` | `secret`, `accessTtlSeconds`, `refreshTtlSeconds`. Giá trị `null` khi tắt. |
| `config.auth` | Origin HTTPS, IP proxy, redirect URI, provider và thời hạn của lượt xác thực, mã trao đổi và phiên tài khoản. |
| `config.giftcodes` | `adminPasswordHash` và `adminSessionTtl` (3600 giây). |

Điểm khởi động gọi `loadConfig()` rồi truyền `config.database` vào
`createMysqlStore()`. Khi tạo pool, module MySQL sử dụng `database.password`,
tương ứng với `DB_PASSWORD`. Script migration gọi `loadDatabaseConfig()`.
Khi thêm biến, khai báo ánh xạ, kiểu, giới hạn, mặc định và điều kiện bật tại
`src/config.js`, cập nhật `.env.example` và thêm kiểm thử cho giá trị hợp lệ
lẫn sai. Test có thể gọi `loadConfig(testEnv)` hoặc `loadDatabaseConfig(testEnv)`
với object riêng và dùng `readEnvironment({ env, envFile })` để thử thứ tự ưu
tiên. Truyền đúng nhóm cấu hình cho module dùng nó. HTML, API và APK chỉ nhận
các trường công khai theo giao thức đang sử dụng.

### Chẩn đoán khi khởi động

Backend ghi `database_ready` rồi `listening` sau khi xác nhận database sẵn sàng.
Khi lỗi, tiến trình thoát mã **1** trước khi mở cổng. Lỗi cấu hình ghi tên biến
cần sửa. Lỗi MySQL ghi mã và giai đoạn đã lọc để phân biệt các nguyên nhân.

| Sự kiện hoặc mã | Việc cần kiểm tra |
|---|---|
| `startup_configuration_error` · `INVALID_CONFIGURATION` | Sửa biến được ghi trong `variable`. Kiểm tra cả biến của tiến trình vì chúng ưu tiên hơn file `.env`. |
| `startup_database_error` · `MYSQL_CONNECTION_FAILED` | MySQL đang chạy và backend có đường đến `DB_HOST`/`DB_PORT`. |
| `MYSQL_AUTH_FAILED` | Kiểm tra `DB_USER`, `DB_PASSWORD` và host đăng nhập của tài khoản MySQL. |
| `MYSQL_DATABASE_NOT_FOUND` | Tạo database đích hoặc sửa `DB_NAME`. |
| `MYSQL_PERMISSION_DENIED` | Kiểm tra quyền của tài khoản trên database đã chọn. |
| `MYSQL_SCHEMA_NOT_READY` | Chạy `npm run migrate` trên đúng database trước khi khởi động lại. |
| `MYSQL_SCHEMA_CHECK_FAILED` | Kiểm tra phiên bản MySQL, quyền và trạng thái migration. |

Migration báo lỗi checksum nếu file đã áp dụng bị thay đổi. Khôi phục nội dung
file đó và thêm migration mới cho lần thay đổi tiếp theo. Log chẩn đoán dùng mã
và thông báo đã lọc để tránh lộ mật khẩu, token và chuỗi kết nối.

## Kiểm tra IPv4 và mạng

Trên Windows chạy `ipconfig`, chọn IPv4 Address của adapter LAN/Wi-Fi đang kết
nối, không chọn `0.0.0.0`, loopback hoặc adapter ảo không có đường tới emulator.
`0.0.0.0` chỉ là địa chỉ bind của server, không phải URL app. Kiểm tra:

```powershell
Invoke-RestMethod http://10.37.233.55:8787/health
Get-NetTCPConnection -State Listen -LocalPort 8787
```

Khi MySQL và migration sẵn sàng, `/health` trả HTTP200:

```json
{"status":"ok","backend":{"status":"running"},"mysql":{"status":"ready","schemaVersion":1}}
```

Nếu MySQL/schema mất sẵn sàng **sau khi server đã khởi động thành công**,
server phản hồi HTTP503 với backend `running`, MySQL `unavailable`.
Đăng nhập/đọc/ghi phụ thuộc DB cũng trả lỗi503;
không chuyển sang RAM/JSON hoặc trả đăng nhập thành công. `/healthz` là alias.

Chỉ khi Windows Firewall chặn kết nối, mở **đúng cổng TCP 8787** trên profile
Private và giới hạn mạng nội bộ, trong PowerShell quản trị:

```powershell
New-NetFirewallRule -DisplayName 'Green Farm backend 8787' -Direction Inbound -Action Allow -Protocol TCP -LocalPort 8787 -Profile Private -RemoteAddress LocalSubnet
```

Không cần tắt toàn bộ firewall hoặc mở cổng MySQL cho Android. Nếu dùng port
khác, cập nhật rule và cả `PORT`/URL chung. Từ trình duyệt trong emulator, mở
**cùng** `http://10.37.233.55:8787/health`. Chỉ host truy cập được chưa chứng minh
emulator có route qua LAN/VPN/firewall của máy bạn.

## Giao thức game được giữ

Bằng chứng gốc ở [Accept](../docs/backend/ACCEPT_PROTOCOL_EVIDENCE.md),
[startup profile](../docs/backend/CONFIG_STARTUP_EVIDENCE.md) và mã gốc
`S800x480/s.java`, `t.java`, `cs.java`. Không đổi client sang REST/JSON form mới.
POST vẫn là `application/x-www-form-urlencoded` UTF-8, giới hạn 8 KiB; discovery
locator vẫn là text thuần, không quote, không newline và không slash cuối.

| Route | Dữ liệu/giao thức |
|---|---|
| `GET /config/{client}/datacenters` | JSON datacenter array như M0. |
| `GET /config/{client}/datacenters/local/urls` | Đúng mười khóa gốc; mọi URL cùng origin cấu hình. |
| `GET /locate/auth`, `GET /locate/storage` | Origin text thuần từ `BACKEND_BASE_URL`. |
| `POST /authorize` | Ban đầu: form `client_id`, `username`, `password`, `scope`, `device_id`; JSON `access_token`, `refresh_token`. |
| `POST /authorize` (refresh) | Form `client_id`, `grant_type=refresh_token`, `refresh_token`, `scope` tùy chọn; rotation token trong transaction thật. |
| `GET /profiles/me/myprofile?access_token=...` | JSON `credential` đúng tài khoản của session đã kiểm tra. |
| `POST /users/me/compliance` | Form `access_token`, `country`, `age`; persist và trả policy `LOCAL_RESEARCH_16_PLUS` như M0. |

Core `s.cG` đọc cả access/refresh token; SDK wrapper cũ chỉ đọc access token và
bỏ qua khóa bổ sung. Core `s.o` thực hiện refresh qua cùng route. Scope gốc
`leaderboard social storage auth message config` được nhận cùng các scope M0
`asset transaction`; nhận tên scope không có nghĩa các dịch vụ đó đã hoạt động.

Luồng đăng nhập legacy là **anonymous legacy** (`anonymous:<credential>`), kiểm tra đúng
phép dẫn xuất password UTF-16 của APK rồi kiểm tra hash trong MySQL. Đây là giao
thức công khai có thể tự dẫn xuất, không phải chứng minh quyền sở hữu tài khoản
hiện đại. Trong `NODE_ENV=production`, xác thực anonymous legacy mặc định bị tắt;
`LEGACY_AUTH_ENABLED=true` bị từ chối. Giữ `LEGACY_AUTH_ENABLED=false` khi triển
khai hệ thống tài khoản Facebook/Google mới. Nút Connect qua SDK Facebook cũ
không được coi là xác thực của hệ thống OAuth mới; không tạo kết quả đăng nhập
thành công giả. Luồng provider chính thức và các cấu hình còn thiếu được mô tả
trong [SOCIAL_AUTH.md](SOCIAL_AUTH.md).

Session được ký HMAC, sid chỉ lưu SHA-256 trong DB. Access/refresh có thời hạn;
refresh token chỉ dùng một lần, giao dịch tạo session mới và xóa session cũ nên
replay bị từ chối. Hash password dùng salted scrypt. Request log chỉ gồm method,
route template và status; lỗi database log mã lỗi đã lọc, không SQL, password,
client ID, token, body hay query string.

## Dữ liệu MySQL và giới hạn phục hồi

- `gf_accounts`: ID ổn định, client namespace, credential gốc cần cho profile,
  salt/hash password; không lưu password nguyên văn.
- `gf_sessions`: tài khoản, device, sid hash, thời điểm cấp/hết hạn access/refresh.
- `gf_compliance`: country/age và thời điểm cập nhật của tài khoản.
- `gf_schema_migrations`: version/checksum migration, đồng thời khóa phát hành
  session để giữ giới hạn 10.000 session như backend trước.

Backend cũ chỉ có `Map` session RAM; không có database/file JSON hay export đã
lưu. Các process M0 còn chạy không bị xóa/reset. Session RAM không có snapshot
để chuyển đổi một cách có bằng chứng; không có script tự bịa ID hoặc nhập token
không tồn tại. Từ lần chạy MySQL này, mọi bản ghi thành công được commit bền vững.

Backend trước chưa quản lý farm, tiền, kho hay tiến trình gameplay. Dữ liệu farm
vẫn ở RMS phía thiết bị; không có save API farm có bằng chứng để chuyển thành
cloud save. Các profile write/delete, `/configs/users/me`, `/devices/mydevice`,
quảng cáo/thanh toán và dịch vụ auxiliary trả501/404 rõ ràng, không success giả.
Không diễn giải đọc profile identity như đã đọc/tải toàn bộ nông trại.

## Kiểm thử

```powershell
npm test
# Dùng database thử nghiệm RIÊNG, tên kết thúc bằng _test:
$env:DB_NAME='green_farm_test'
npm run test:mysql
Remove-Item Env:DB_NAME
```

`test:mysql` chạy tuần tự theo file vì hai suite dùng chung schema và cleanup có thể deadlock nếu chạy song song. Ca kiểm tra nhiều authorization đồng thời trong từng suite vẫn được giữ nguyên.

`npm run test:mysql:upgrade` cần một schema **rỗng, dùng một lần**, có tên kết
thúc bằng `_upgrade_test`. Cấu hình `DB_NAME` hoặc `TEST_DB_NAME` trỏ đến schema
đó và cấp quyền DDL/DML cho tài khoản kiểm thử. Bài kiểm thử dựng dữ liệu v4,
thử nâng cấp, khôi phục và nâng lại v5. Schema chứa dữ liệu giả sau khi chạy,
nên tạo schema rỗng khác cho lần chạy tiếp theo.

`npm test` kiểm tra HTTP/config bằng repository test double **chỉ trong test**,
không phải fallback runtime. `test:mysql` yêu cầu MySQL thật, migration thật,
không skip nếu thiếu DB; kiểm tra restart, account/session/profile/compliance,
refresh/replay, transaction rollback và concurrent identity. Nó cũng khởi động
process Node riêng, gọi HTTP thật, restart process rồi xác minh token/profile và
row MySQL còn nguyên; kiểm tra từ chối khởi động khi DB không truy cập được hoặc
sai mật khẩu. Runtime DB lỗi trả503 vẫn được kiểm tra trong bộ HTTP. Nó chỉ xóa các row
thuộc namespace ngẫu nhiên nó đã tạo trong DB `_test`, không reset database.

Các kiểm tra HTTP/DB riêng không thay thế việc chạy APK: cần xác nhận host và
emulator cùng truy cập `/health`, backend nhận đúng request, rồi kiểm tra UI,
network failure/timeout và save/load trên Android.
