# Chuyển dữ liệu MySQL cũ sang SQLite

Backend cũ chỉ có `gf_accounts`, `gf_sessions`, `gf_compliance` và lịch sử
migration trong MySQL; chưa có snapshot nông trại trên server. Các lệnh dưới đây
giữ ID, credential, salt/hash mật khẩu, thời hạn phiên và consent cũ. Chúng
**không** liên kết tài khoản anonymous/Facebook cũ với phiên khách mới.

MySQL gốc không bị UPDATE, DELETE, DROP hoặc đổi schema. Không cần xóa database
MySQL sau khi chuyển. Giữ secret phiên legacy cũ nếu cần tiếp tục dùng token đã
phát hành; thay secret sẽ làm chữ ký những token này không còn hợp lệ.

## Xuất bản sao nhất quán

Dùng Node.js 24+ và `npm ci` trong thư mục `backend`. Giữ thông tin `DB_HOST`,
`DB_PORT`, `DB_NAME`, `DB_USER`, `DB_PASSWORD` của MySQL cũ trong tệp `.env.mysql`
riêng, không commit. Tài khoản cần SELECT trên các bảng nguồn và đọc catalog;
không cần quyền sửa dữ liệu. Chạy từ `backend`, trong PowerShell:

Nếu MySQL cũ thực sự dùng mật khẩu trống, khai báo rõ `DB_PASSWORD=`. Công cụ
chấp nhận giá trị trống đã cấu hình, giữ nguyên mật khẩu không trống và từ chối
khi biến bị thiếu; không ép mọi kết nối thành password rỗng. Runtime SQLite mới
không yêu cầu mật khẩu database.

```powershell
node --env-file=.env.mysql scripts/export-mysql.js --output .local/mysql-before-sqlite.json
```

Lệnh xuất dùng `REPEATABLE READ` và
`START TRANSACTION WITH CONSISTENT SNAPSHOT, READ ONLY`. Nó lấy toàn bộ các bảng
BASE TABLE trong database đã chọn, gồm bảng liên kết Facebook bổ sung nếu có.
Không chạy DDL trong lúc xuất. Bảng không dùng InnoDB hoặc identifier ngoài
phạm vi được hỗ trợ làm lệnh **thất bại**, không bỏ qua bảng để báo thành công.

Giá trị BLOB có tag base64; BIGINT được đọc dưới dạng chuỗi, timestamp giữ cả
microsecond bằng `dateStrings`. Mỗi bảng giữ catalog cột và toàn bộ giá trị.
Envelope có checksum SHA-256 của payload. Lệnh không ghi password kết nối ra
output/log. File chứa dữ liệu tài khoản, có quyền `0600` trên Unix; hãy giữ trong
thư mục riêng có ACL phù hợp trên Windows. Không gửi bản export này trong log hỗ
trợ và không commit nó.

File đích được công bố nguyên tử bằng hard link trên NTFS/ext4 và không ghi đè
file đã tồn tại. Nếu filesystem không hỗ trợ hard link, chuyển đích sang NTFS
hoặc filesystem phù hợp; lệnh sẽ báo lỗi thay vì xuất file thành công giả.
Giới hạn bundle là 128 MiB; vượt giới hạn làm lệnh thất bại, không cắt bớt dữ liệu.

## Nhập vào SQLite đã migration

1. Dừng backend SQLite trong lúc nhập để dễ đối chiếu. Đừng xóa save Android.
2. Chạy `npm run migrate -- --prepare-import` để tạo các bảng đích. Nếu còn
   `DB_NAME`/`DB_USER` legacy, migration mặc định không tự tạo database rỗng;
   server tiếp tục từ chối chạy cho tới khi import hoàn tất. Chỉ dùng
   `npm run migrate -- --new-sqlite` khi chủ động chọn một instance mới riêng,
   không phải thao tác chuyển dữ liệu cũ.
3. Dùng đúng đường dẫn database `SQLITE_PATH` của instance. Mặc định là
   `backend/data/green-farm.sqlite`, độc lập working directory của backend.
   Ví dụ chạy từ thư mục `backend`:

```powershell
node scripts/import-mysql.js --input .local/mysql-before-sqlite.json --sqlite-file data/green-farm.sqlite
```

File SQLite phải tồn tại và có ba bảng legacy; lệnh không âm thầm tạo một
database trống ở đường dẫn nhập sai. Trước khi nhập, nó chạy `quick_check` rồi
tạo bản sao nhất quán bằng API `node:sqlite.backup()`, cạnh database với hậu tố
`.before-mysql-<timestamp>-<random>.bak`. Kết quả CLI chỉ ra đường dẫn backup.
Giữ backup và export nguồn cho tới khi kiểm chứng xong.

Toàn bộ import dùng một `BEGIN IMMEDIATE` transaction, foreign keys bật,
`synchronous=FULL`, busy timeout 2 giây. Thành công chỉ được trả sau COMMIT.
Các câu lệnh ghi dùng tham số. Bản ghi trùng khóa phải giống toàn bộ các trường
đã biết; nếu khác, lệnh rollback toàn bộ với `LEGACY_IMPORT_COLLISION`.
Không ghi đè tài khoản hoặc phiên đang có để giải quyết xung đột. ID BIGINT lớn
được giữ nguyên bằng TEXT, không chuyển qua số JavaScript mất chính xác.

Mọi bảng nguồn, kể cả bảng đã ánh xạ và các cột bổ sung, được giữ nguyên dưới
dạng JSON có tag trong `gf_imported_legacy_tables`. Lịch sử migration MySQL
không thay thế ledger migration SQLite. Dữ liệu Facebook chưa có schema được
ứng dụng mới hiểu cũng được lưu trong archive này, chưa được dùng để đăng nhập.
Archive là bảo toàn dữ liệu/cột, không phải trình khôi phục trigger/view/routine
MySQL. Database MySQL gốc vẫn được giữ nguyên.

`gf_legacy_imports` ghi checksum nguồn. Gửi lại đúng file trả
`already_imported`; không nhân đôi tài khoản/phiên hoặc thay đổi dữ liệu mới hơn.
File hỏng, sai checksum, thiếu bảng/cột cần thiết, BLOB sai kích thước, giá trị
không tương thích hoặc lỗi SQLite đều bị từ chối. Dữ liệu cũ và file export
được giữ nguyên, không reset hoặc tạo hồ sơ rỗng.

## Kiểm chứng đã chạy

```sh
node --test test/mysql-preservation.test.mjs
```

7 kiểm thử đã qua, gồm transaction READ ONLY của nguồn mô phỏng, round-trip
BLOB/ID/timestamp, backup SQLite thật, giữ cột/bảng Facebook, replay và rollback
toàn bộ khi gặp xung đột muộn. Import vào schema migration thực tế cũng xác nhận
token legacy còn đọc đúng identity khi secret được giữ, và không tạo bất kỳ
guest/player/farm ownership nào. Chưa kết nối MySQL của người dùng; vì vậy dữ liệu
MySQL thực tế và token sau import cần được kiểm tra với bản export của instance
đó. Không coi test source mô phỏng là kiểm thử migration MySQL thực tế.
