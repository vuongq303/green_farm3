# Checkpoint: IPv4 LAN, JavaScript và MySQL

Thực hiện trên mã mới nhất người dùng push `0c22c29`; giữ bản sửa D8 overlay
theo minSdk, scoped PROJECT classes, whitelist/phiên bản DEX/lớp bảo vệ trong
`MergeCoreDex` và runtime Application gốc. Không sửa file sinh trong build.

## Cấu hình sử dụng

**`gradle.properties:5`** là nguồn URL chung duy nhất:

```properties
BACKEND_BASE_URL=http://10.37.233.55:8787/
```

Backend đọc trực tiếp file properties này, app nhận giá trị khi build. Sửa IP
cần restart backend, build và cài lại APK. Không có fallback về localhost,
`10.0.2.2` hoặc EVE trong hai bootstrap đã chuyển. Biến `localBackendUrl` cũ bị
từ chối rõ ràng. Validation kiểm tra HTTP(S), IPv4, port và origin/trailing slash;
không nhận credentials/path/query/fragment trong URL.

Backend mặc định bind `HOST=0.0.0.0`, `PORT=8787`. Địa chỉ bind không phải URL
app. MySQL chỉ cấu hình tại `backend/.env`, không được đưa vào APK. Xem
[README backend](../../backend/README.md) để lấy IPv4 bằng `ipconfig`, tạo MySQL
database/user, đặt DB password/session secret, mở đúng cổng Windows Firewall
Private khi cần và chạy migration.

```powershell
cd D:\green_farm3\backend
npm ci
Copy-Item .env.example .env
# Điền DB_* và RESEARCH_SESSION_SECRET riêng trong .env trước bước tiếp.
npm run migrate
npm start
# Hoặc: npm run dev
```

Từ Windows và trình duyệt emulator, kiểm tra cùng
`http://10.37.233.55:8787/health`. HTTP200 phải kèm MySQL `ready`; DB/schema lỗi
trả503, không báo thành công hoặc chuyển sang RAM. Sau đó:

```powershell
cd D:\green_farm3
.\gradlew.bat :app:assembleDebug
.\gradlew.bat :app:assembleDebug -Pandroid.injected.build.api=24
```

Android Studio vẫn **Sync → chọn app → thiết bị → Run**. APK hoàn chỉnh ở
`app/build/outputs/apk/debug/app-debug.apk`.

## Các thay đổi và nguyên nhân có bằng chứng

- Backend cũ chỉ bind loopback. Đã tái hiện trên cloud: localhost health200,
  IPv4 của host và emulator bị connection refused. Backend mới nhận cùng IPv4
  từ host và emulator với health200/MySQL ready.
- Backend cũ có thể trả discovery origin localhost sau khi request đầu đã vào
  đúng server. Nay mọi locator/URL map xuất phát từ cùng BACKEND_BASE_URL.
- Patch cũ từ chối HTTP LAN và bỏ sót bootstrap `S800x480/s.aL`. Nay patch đúng
  hai owner đã đối chiếu: SDK một literal, core hai literal; có reverse proof
  và kiểm tra các lớp khác không đổi.
- Final APK cũ đã cho HTTP; không quy lỗi mạng cho Manifest khi chưa có bằng
  chứng. Nay main policy chặn HTTP, debug override cho HTTP và verifier kiểm
  tra chính Manifest cuối: INTERNET, debuggable, cleartext, Application thật.
- Core đọc refresh token và khai báo scope khác SDK. Giữ form/plaintext gốc,
  bổ sung đúng các field/scope đã có bằng chứng; không đổi sang REST/JSON mới.
- Thêm hai null guard ở callback `ci.a(...)` để exception mạng có message null
  không gây NPE; giữ phân loại lỗi và nhánh timeout gốc.

Backend tổ chức JavaScript ESM: `src/main.js`, `config.js`, `server.js`,
`http.js`, `services/sessions.js`, `db/store.js`, `db/migrate.js`.
Migration `001_accounts_sessions.sql` thêm account, session/refresh và
compliance; password salted scrypt, sid hash, truy vấn tham số hóa và transaction.
Migration có checksum/lock, không reset dữ liệu. Không có dataset JSON/SQLite
hoặc bản export RAM cũ để tự tạo script import; giữ process M0 cũ chạy và backup
dữ liệu emulator trước thử nghiệm. Farm vẫn dùng RMS trên thiết bị.

## Kiểm chứng thực tế

- Node24/MySQL8.4.13 thật, migration thật và chạy lại idempotent. `npm start`
  và `npm run dev` đều health200/MySQL ready.
- **157 test đạt, không skip:** 110 Python, 13 resource, 25 HTTP/config và
  9 test MySQL/HTTP thật. Test khởi động lại tiến trình xác minh cùng account ID,
  token/profile/compliance còn nguyên; refresh rotation/replay và rollback.
- Missing/sai credential:400/401; MySQL không truy cập được:503. Log kiểm tra
  không chứa password, token, credential, query/body hay client/device ID.
- Build chuẩn thành công trong79 giây. Build/cài có injected API24 thành công
  trong77 giây: carrier037, overlay035 theo minSdk16, final035. Không sửa header
  DEX thủ công hoặc nới whitelist để ghép.
- Emulator API28 truy cập `/health` qua IPv4 cloud, sau đó chính app gửi
  discovery/authorize/profile nhận200; một account/session thật đã ghi MySQL.
  Game hiển thị Connect/Not now. Chưa gọi đây là đã kiểm thử toàn bộ nông trại.
- Thử dừng MySQL: health và authorize trả503. Thử dừng riêng backend: connection
  refused. App còn process, không FATAL trong cửa sổ quan sát tối thiểu20 giây
  của mỗi trường hợp; dịch vụ và database được khôi phục sau thử nghiệm.

Một test fixture startup cũ thiếu peer `GloftGF2F`, gây lỗi JVM verification khi
dùng conversion gốc mới hơn. Đã thêm peer **chỉ trong test**, giữ nguyên bytecode
gốc và432 phép so sánh; không thêm stub vào APK. Full suite sau sửa đạt.

[Kết quả máy đọc](lan-mysql-result.json), [log/ảnh](validation/lan-mysql/) và
[kiểm kê giao thức](LAN_PROTOCOL_AUDIT.md) ghi rõ từng phạm vi. Các IP cloud chỉ
dùng trong fixture/override kiểm thử; root properties vẫn giữ IP Windows đã cho.

## Chưa xác minh hoặc chưa triển khai

Chưa truy cập mạng LAN Windows của người dùng hoặc bấm Run trên Windows thật.
Phải chạy health từ host và emulator tại máy đó để kết luận route/firewall đã
đúng; kiểm thử cloud không thay thế việc này.

Facebook Connect còn gọi SDK Facebook; không có tài khoản thành công giả.
Farm save/load gốc vẫn RMS; chưa khôi phục cloud farm codec/API. Các dịch vụ
chưa triển khai trả501/404, không nhận scope rồi giả đã thực hiện social/store.
Tracking/push/portal share và các SDK ngoài luồng backend đã chuyển vẫn có URL
legacy riêng, được liệt kê trong audit; chưa gọi toàn bộ game độc lập dịch vụ cũ.

UI retry và các vòng poll SDK không có deadline chưa được xác minh đầy đủ;
không crash trong20 giây không chứng minh mọi callback sẽ kết thúc hoặc tự retry.
Chưa kiểm thử thiết bị thật, Android mới hoặc gameplay/save-load đầy đủ.
