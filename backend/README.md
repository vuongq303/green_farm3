# Green Farm backend: JavaScript và MySQL

Backend dùng Node.js 24+, JavaScript ES Modules và `mysql2/promise`. Điểm khởi
động là `src/main.js`; không cần TypeScript hoặc bước transpile. Một nguồn URL
chung cho app và backend nằm tại **`../gradle.properties`**, biến
`BACKEND_BASE_URL`. Backend đọc trực tiếp file này để tạo mọi URL discovery.

```properties
BACKEND_BASE_URL=http://10.37.233.55:8787/
```

`10.37.233.55` là IPv4 Windows người dùng cung cấp. Nếu mạng đổi IP, sửa chính
biến này, khởi động lại backend, **build và cài lại APK**. Đổi file cấu hình không
đổi được URL đã nhúng trong APK đang cài. Không còn biến `localBackendUrl`,
`PUBLIC_BASE_URL`, `BIND_HOST` hoặc fallback về localhost/`10.0.2.2`.

## Cấu hình và chạy

Từ thư mục `backend` trong PowerShell:

```powershell
npm ci
Copy-Item .env.example .env
```

Sửa `.env`: đặt `DB_PASSWORD` riêng, tài khoản database và
`RESEARCH_SESSION_SECRET` ngẫu nhiên, ít nhất 32 byte. Giữ session secret ổn định
qua các lần restart; thay secret sẽ làm token hiện có không hợp lệ. Có thể tạo
secret riêng bằng `node -e "console.log(require('node:crypto').randomBytes(48).toString('hex'))"`
rồi lưu vào `.env` của máy bạn, không đưa vào Git hoặc log hỗ trợ.

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

Backend kiểm tra kết nối MySQL, migration và các bảng cần thiết **trước khi mở
cổng HTTP**. Chỉ khi kiểm tra thành công mới ghi `database_ready` rồi `listening`.
Nếu MySQL không truy cập được, sai thông tin kết nối hoặc schema chưa sẵn sàng,
backend ghi `startup_database_error`, hướng dẫn kiểm tra `DB_*`/chạy migration,
đóng pool và thoát với mã **1**; không báo server đã chạy. Sửa cấu hình/khởi động
MySQL, chạy `npm run migrate` nếu cần, rồi chạy lại `npm start`.

Migration có khóa chống chạy đồng thời, lưu checksum, tạo bảng nếu chưa có và
không DROP/TRUNCATE/reset dữ liệu. Sau lần đầu, chạy lại giữ nguyên ID/nội dung.
Một schema có checksum không khớp sẽ bị từ chối thay vì tự viết lại lịch sử.

| Cấu hình backend | Giá trị/mục đích |
|---|---|
| `HOST` | Mặc định `0.0.0.0`, lắng nghe mọi interface IPv4. |
| `PORT` | Mặc định `8787`; phải khớp port của `BACKEND_BASE_URL`. |
| `DB_HOST`, `DB_PORT` | Mặc định `127.0.0.1`, `3306`; chỉ backend truy cập MySQL. |
| `DB_NAME`, `DB_USER`, `DB_PASSWORD` | Bắt buộc, chỉ trong `.env`/môi trường backend. |
| `DB_POOL_SIZE`, `DB_CONNECT_TIMEOUT_MS` | Pool mặc định 5 kết nối, timeout 5000 ms. |
| `RESEARCH_SESSION_SECRET` | Bắt buộc, riêng tư và ổn định, ít nhất 32 byte. |
| `SESSION_TTL_SECONDS` | Access token: mặc định 900 giây, tối đa 86400. |
| `REFRESH_TTL_SECONDS` | Refresh token: mặc định 2592000 giây; ít nhất access TTL, tối đa 7776000. |
| `BACKEND_CONFIG_FILE` | Tùy chọn đường dẫn file properties cho test/instance tách biệt. Vẫn đọc đúng khóa `BACKEND_BASE_URL`, không phải URL thứ hai. |

`.env` được Node tự đọc khi chạy entry point/migration; biến môi trường đã đặt
có ưu tiên. Listener này dùng HTTP trực tiếp. Validator nhận cấu hình origin
HTTP(S) IPv4 chuẩn; HTTPS không được mở bởi Node listener này và cần triển khai
TLS/reverse proxy rõ ràng trước khi dùng. Không có chứng chỉ/TLS giả.

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

Đăng nhập hiện là **anonymous legacy** (`anonymous:<credential>`), kiểm tra đúng
phép dẫn xuất password UTF-16 của APK rồi kiểm tra hash trong MySQL. Đây là giao
thức công khai có thể tự dẫn xuất, không phải chứng minh quyền sở hữu tài khoản
hiện đại. `NODE_ENV=production` vẫn bị từ chối. Không tạo đăng nhập Facebook thành
công giả; nút Connect qua SDK Facebook cũ chưa có backend độc lập tương đương.

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
