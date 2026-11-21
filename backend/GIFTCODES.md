# API giftcode và schema MySQL

Xem [hướng dẫn chạy và cấp quyền quản trị](../docs/GIFTCODES.md).

Migration `006_giftcodes.sql` thêm các bảng `gf_giftcodes`,
`gf_giftcode_redemptions` và `gf_giftcode_audit`. Migration runner ghi checksum
sau khi hoàn tất. Các bảng tài khoản và phiên hiện có được giữ nguyên. Bảng đổi
mã tham chiếu `users.id` và có khóa duy nhất `(giftcode_id, user_id)`.

## API game

Mọi yêu cầu đi qua HTTPS và mang bearer token của phiên game. Mã dùng A–Z, 0–9
và dấu gạch nối, dài 4–48 ký tự sau khi bỏ khoảng trắng ASCII và đổi sang chữ hoa.
`farm_id` và `grant_id` dùng UUID v4 chữ thường.

| API | Yêu cầu | Kết quả |
|---|---|---|
| `POST /giftcodes/redeem` | `{code, farm_id}` | `201` grant mới, hoặc `200` grant cũ cho cùng tài khoản/nông trại |
| `GET /giftcodes/pending?farm_id=...` | Query `farm_id` | `200 {grants:[...]}`, tối đa 20 grant chưa xác nhận |
| `POST /giftcodes/ack` | `{grant_id, farm_id}` | `200` grant cùng trạng thái `applied` |

Mỗi grant gồm `grant_id`, `rewards` gồm các cặp `{id,quantity}`, và `status`
bằng `pending` hoặc `applied`. Khi đổi, giao dịch khóa hàng mã trước khi kiểm
tra hạn, giới hạn và các lượt đổi đã lưu. Giao dịch ghi bản sao danh sách thưởng,
tăng số lượt đã dùng và trả ID ổn định. Nếu ứng dụng mất phản hồi, gửi lại cùng
mã trả về grant cũ hoặc lấy grant đang chờ qua API pending. Game xác nhận sau
khi lưu SQLite. Cùng tài khoản gửi lại mã từ nông trại khác nhận lỗi
`giftcode_already_redeemed`. ACK cho grant thuộc tài khoản hoặc nông trại khác
nhận `grant_not_found`.

## API quản trị

API ở `/admin/api/giftcodes`. Đăng nhập qua `POST /login` với `{password}`.
Máy chủ so khớp hash Argon2id và cấp cookie Secure, HttpOnly, SameSite Strict.
Cookie có hạn một giờ, được thu hồi lúc đăng xuất và cần đăng nhập lại khi
tiến trình backend khởi động lại. Backend dùng một mật khẩu quản trị riêng
và ghi người thao tác là `operator`. Mọi thao tác ghi từ trình duyệt kiểm tra
origin, và phiên game thông thường không cấp quyền quản trị.

| Đuôi API | Phương thức | Công dụng |
|---|---|---|
| URL gốc | GET, POST | Tìm theo tiền tố `q`, lọc `status`, phân trang `limit`/`offset` hoặc tạo mã |
| `/catalog` | GET | Danh mục Xu và Tiền mặt đã xác minh |
| `/:id` | GET, PATCH | Chi tiết, cập nhật thưởng, lịch, giới hạn và trạng thái |
| `/:id/redemptions` | GET | Lịch sử đổi mã có phân trang |
| `/login`, `/logout` | POST | Cấp hoặc thu hồi phiên quản trị |

Mỗi lượt đổi giữ nguyên phần thưởng đã chốt. Trường `code` cố định sau khi tạo.
Có thể chỉnh giới hạn nếu giới hạn mới lớn hơn hoặc bằng số lượt đã dùng.
Ngày giờ API dùng ISO 8601 UTC với hậu tố `Z`. Các lần tạo và sửa được ghi vào
`gf_giftcode_audit`.

## Đo hiệu suất truy vấn pending

Đo trên Codex Cloud x86-64, MySQL 8.4.13 trong Docker qua loopback, Node 24.22.3
và một kết nối mysql2. Dataset riêng gồm 20.000 grant tổng hợp của một tài khoản,
phân bố qua 100 nông trại, một phần đã xác nhận. Mỗi trường hợp làm ấm 8 lần
rồi chạy xen kẽ 100 lần cho mỗi truy vấn trên cùng dữ liệu. Bản đối chứng thêm
`IGNORE INDEX (gf_giftcode_pending)` ngay sau tên bảng.

```sql
SELECT grant_id, farm_id, rewards, redeemed_at, applied_at
FROM gf_giftcode_redemptions
WHERE user_id = ? AND farm_id = ? AND applied_at IS NULL
ORDER BY redeemed_at, grant_id
LIMIT 20
```

| Truy vấn | p50 | p95 |
|---|---:|---:|
| Đối chứng với index bị bỏ qua | 9,10 ms | 26,02 ms |
| Sử dụng index mặc định | 0,64 ms | 1,97 ms |

`EXPLAIN ANALYZE` ghi nhận 20.000 hàng được quét trước bước sắp xếp trong đối
chứng và 20 hàng đọc qua index mặc định. Số đo gồm thời gian đi về từ Node đến
MySQL và giải mã kết quả. Đây là số đo truy vấn cục bộ, chưa đo độ trễ đổi mã
trên thiết bị hay qua Internet.
