# Cấu hình khởi tạo người chơi

File điều chỉnh là **`backend/config/new_player_defaults.json`**. Backend đọc
và kiểm tra khi startup, sau đó đọc lại chỉ khi tạo một người chơi mới. Có thể
thay file bằng nội dung JSON hợp lệ mà không build lại APK hoặc sửa source.
Nên lưu file nguyên vẹn/replace nguyên tử để lần đọc không gặp tệp đang viết dở.
File JSON có UTF-8 BOM của trình soạn thảo Windows cũng được hỗ trợ.

Giá trị mặc định lấy từ `FarmGameEngine.aY()`, `aZw`, `bc()` và luồng ngôn ngữ
`kR()/fM(30)`: 2000 xu, 50 cash, level1, XP0, tutorial0, ngôn ngữ tự chọn của
thiết bị, capacity10/10/10/1. Snapshot đầu tiên được client chốt trước khi
tutorial chuyển từ0 sang1; sau đó hướng dẫn diễn ra bình thường.

Không lấy số dư1900 của farm đã mua vật phẩm làm mặc định. Dữ liệu gốc và bảng
XP đã đối chiếu bằng source/bytecode và JDWP chỉ đọc tại
[bằng chứng](../../docs/backend-restoration-validation/original-balance-tables.json).
`config/game_catalog_4_4_2.json` là catalog tương thích của phiên bản game gốc,
không phải file để cấp lại tiền người chơi.

| Khóa | Giá trị được hỗ trợ |
| --- | --- |
| `schemaVersion` | `1`. |
| `initialLevel` | Số nguyên1–50. |
| `initialExperience` | XP **trong level**, từ0 đến `aBv[level]-1` trong catalog; level50 chỉ0 vì original `hX()` không cộng thêm XP ở cấp tối đa. Level1:0–12; level2:0–18. |
| `initialCoins` | Số nguyên0–99.999.999. |
| `initialCash` | Số nguyên0–3.000.000, đúng giới hạn game gốc. |
| `initialTutorialStep` | Chỉ `0`. Bước khác cần trạng thái nhiệm vụ chưa được xác minh là seed hợp lệ, nên bị từ chối. |
| `initialLanguage` | `auto`, `en`, `de`, `fr`, `it`, `es`, `pt-br`, `ja`, `ko`, `ru`, `pl`, `tr`, `th`, `vi`, `es-419`. `auto` giữ lựa chọn có sẵn của thiết bị/game. |
| `initialCapacityLimits` | Đúng bốn khóa: `plots`1–60, `trees`0–25, `animals`0–40, `ponds`0–20. Đây là **giới hạn số lượng**, không phải vật phẩm được tặng. |
| `initialWorldTemplate` | Chỉ `original-4.4.2`. Giữ seed nguyên gốc từ assets `/14`, các phần `fN(3)/fN(4)`. |

Các trường đều bắt buộc, không nhận khóa thừa, chuỗi thay số, số thập phân, giá
trị ngoài khoảng hoặc `initialItems` với ID chưa xác minh. Không tự đổi seed
quest/vật phẩm/công trình/map để hợp thức hóa cấu hình sai. Seed world nguyên
bản bao gồm các đối tượng ban đầu; SHA-256 assets14 được ghi trong catalog.
Đổi danh sách vật phẩm/template cần khôi phục và kiểm chứng schema riêng trước,
hiện không có API tự bịa item hoặc mở khóa toàn bộ.

Khi tạo người chơi, backend chuẩn hóa theo thứ tự khóa cố định và hash SHA-256
của JSON UTF-8 không khoảng trắng, lưu `{configHash,defaults}` trong SQLite cùng
transaction tạo player. Thứ tự: schemaVersion, initialLevel, initialExperience,
initialCoins, initialCash, initialTutorialStep, initialLanguage,
initialCapacityLimits (plots,trees,animals,ponds), initialWorldTemplate.
Phiên cấp lại luôn nhận bản đã lưu. Người chơi cũ từ schema1 không bị thêm
defaults/reset sau migration. Client không được gửi `initialCoins`, `level` hay
ID người khác vào route tạo phiên để thay cấu hình server.

Farm mới dùng setter bảo vệ/codec gốc ở client. Marker cấu hình và toàn bộ farm
được chốt cùng snapshot đầu tiên; backend kiểm tra marker và các trường số đã
giải mã trước commit. Nếu backend không xác nhận, client giữ outbox bền vững;
không tự báo đồng bộ thành công. Save cũ được chép theo xác nhận, approval của
quản trị server cho đúng UUID/hash và capability riêng, không áp lại giá trị
khởi tạo. Không có HTTP tự cấp approval: client không thể dùng nhãn local-copy
để thay cấu hình khởi tạo bằng tiền/level tùy ý. Xem lệnh quản trị trong README.
Các snapshot gameplay sau vẫn do client
mô phỏng; đây không phải hệ thống chống gian lận hay nền kinh tế server-authoritative.

Các kiểm tra cấu hình dùng file/SQLite riêng, bao gồm đổi file giữa hai người
chơi, retry phiên cũ, file sai và rollback tạo player, upgrade schema1 giữ blob,
first-save sai số tiền/marker, sao chép save đã chơi và retry capability.
