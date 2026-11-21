# Giftcode trong Green Farm 3

Giftcode dùng tài khoản website hiện có và chỉ lưu tiền thưởng vào nông trại đang mở. Danh mục ban đầu gồm **xu** và **cash**, tương ứng với hai bộ đếm và codec số dư có sẵn của game. Mỗi mã có thể chứa cả hai loại. Quà không được tính là thu nhập kiếm từ nhiệm vụ. Vật phẩm kho và vật thể đặt trên nông trại chưa có trong danh mục.

## Cấu hình và chạy

1. Sao lưu MySQL theo quy trình triển khai hiện có. Trong `backend/.env` đặt kết nối MySQL, `LEGACY_AUTH_ENABLED=false` và `AUTH_PUBLIC_BASE_URL` là origin HTTPS đang hoạt động, gồm tên miền và cổng nếu dùng cổng riêng.
2. Nếu Node nhận HTTP nội bộ từ reverse proxy, đặt `AUTH_TRUSTED_PROXY_IP` bằng IP thực mà Node thấy từ proxy. Proxy phải ghi đè `X-Forwarded-Proto` và `X-Forwarded-For` với protocol và IP của kết nối bên ngoài. Với tunnel trên cùng máy, bind Node vào `127.0.0.1` khi phù hợp. Kiểm tra origin trước khi build lại APK.
3. Chạy trong thư mục `backend`:

   ```bash
   npm ci
   npm run migrate
   npm run dev
   ```

   Migration `006_giftcodes.sql` thêm bảng mã, lượt đổi và audit. Danh sách phần thưởng được lưu dạng JSON và được chốt riêng cho mỗi lượt đổi. Migration chỉ thêm dữ liệu cấu trúc; các bản ghi người chơi được giữ nguyên. TLS, kiểm tra state, PKCE, redirect URI và CSRF vẫn áp dụng cho đăng nhập.
4. Chạy từ thư mục gốc. Thay origin ví dụ bằng đúng giá trị của backend rồi cài APK lên thiết bị đã được ADB nhận:

   ```bash
   ./gradlew :app:assembleDebug -PAUTH_BASE_URL=https://backend.example.com
   ./gradlew :app:installDebug -PAUTH_BASE_URL=https://backend.example.com
   ```

   Bản APK ở `app/build/outputs/apk/debug/app-debug.apk`. Nếu origin công khai đổi, cập nhật cấu hình backend, build và cài lại APK. Dùng Android SDK Platform 37.0 và 28, Build Tools 37.0.0, JDK 25 và Python 3.10+ như README.

## Cấp quyền và thao tác trên website

Trang quản trị nằm tại `/admin/giftcodes` trên cùng origin HTTPS. Để bật quyền quản trị, tạo mật khẩu riêng và sinh hash Argon2 trong thư mục `backend`. Ví dụ với Bash:

```bash
read -rs GIFTCODE_PASSWORD
printf '%s' "$GIFTCODE_PASSWORD" | node scripts/hash-giftcode-admin-password.js
unset GIFTCODE_PASSWORD
```

Lưu kết quả vào `GIFTCODE_ADMIN_PASSWORD_HASH` trong `backend/.env`, đặt trong dấu nháy đơn khi dùng shell để đọc file, rồi khởi động lại backend. Quản trị viên dùng mật khẩu riêng để đăng nhập. API kiểm tra phiên quản trị trên server, cookie quản trị giới hạn cùng origin. Phiên người chơi không cấp quyền quản trị. Audit ghi nhận thao tác tạo và sửa mã.

1. Chọn **Tạo mã**, nhập mã hoặc để hệ thống tạo ngẫu nhiên.
2. Tìm xu hoặc cash theo tên hoặc ID, đặt số lượng và xem trước danh sách quà. Chọn thời gian bắt đầu, thời gian hết hạn và giới hạn lượt nếu cần.
3. Lưu và sao chép mã từ danh sách. Trong game, đăng nhập, vào nông trại của mình, mở menu tạm dừng có nút **OPTIONS**, chọn **NHẬP GIFTCODE** hoặc **GIFT CODE**, nhập mã rồi chọn **NHẬN QUÀ**.
4. Lịch sử trong trang quản trị phân biệt lượt **đang chờ** với lượt **đã lưu vào game**. Có thể sửa phần thưởng, lịch hiệu lực, giới hạn và bật/tắt cho lượt sau. Chuỗi mã và phần thưởng của lượt đã ghi nhận giữ nguyên.

Game và server loại bỏ khoảng trắng ASCII, chuyển chữ ASCII sang chữ hoa và chấp nhận mã dài 4–48 ký tự gồm chữ, số và dấu gạch ngang.
Hộp thoại có bàn phím nhập mã trong game. Ô nhập và nút **Nhận quà** nằm
phía trên các phím. Người chơi cũng có thể dán mã hoặc dùng bàn phím vật lý.

## Lưu và khôi phục

- MySQL chốt số lượng phần thưởng trong một giao dịch và cho một tài khoản dùng mỗi mã một lần. Nếu bị mất phản hồi, nhập lại cùng mã hoặc mở lại hộp thoại trên cùng nông trại để lấy lượt đang chờ. Backend trả cùng `grant_id` và số lượng đã chốt.
- Game áp dụng cả hai số dư ở cuối frame của nông trại, ghi tiến độ và biên nhận bằng cùng một giao dịch SQLite, rồi gửi xác nhận. Nếu mất xác nhận, biên nhận ngăn cộng lần hai. Snapshot autosave mới giữ thứ tự phiên bản; snapshot cũ không ghi đè số dư đã lưu.
- Nếu một số dư vượt trần, game giữ nguyên cả hai số dư và lượt vẫn chờ ở backend. Sau khi số dư đủ chỗ hoặc lỗi lưu được khắc phục, mở lại hộp thoại để thử giao lại. Trần game hiện tại là 99.999.999 xu và 3.000.000 cash.
- Lượt chờ gắn với tài khoản và bản cài đang giữ nông trại. Đổi tài khoản không đổi chủ nông trại hoặc tải số dư từ server. Đổi thiết bị, xóa dữ liệu ứng dụng hoặc nhập lại một bản lưu tạo binding mới và không nhận lại các lượt đã ghi nhận ở binding cũ. File chuyển bản lưu chứa số dư đã lưu của nông trại. Phiên tài khoản, binding và biên nhận giftcode không được chuyển trong file.

## Kiểm tra

Chạy `npm test` từ `backend`. Chạy `npm run test:mysql` và `npm run test:browser` với database kiểm thử riêng có hậu tố `_test`. Chạy `npm run test:mysql:upgrade` với database trống có hậu tố `_upgrade_test`. Để chạy fixture xử lý lưu game, chạy từ thư mục gốc:

```bash
python3 -m unittest discover -s tests -p test_progress_save_controller.py
```

Kiểm thử trên thiết bị cần cài APK build với origin HTTPS của backend, đăng nhập bằng website, quay lại game qua deep link, đổi mã và mở lại ứng dụng để kiểm tra số dư. Kiểm thử SQLite native dùng harness ở `tests/sqlite-host`.

Trong kiểm thử trên emulator Android 9 x86-64, APK dùng origin HTTPS riêng và
trình duyệt thử nghiệm dựa trên Android System WebView. Lượt đổi tăng số dư
từ 2.000 xu và 50 tiền mặt lên 3.234 xu và 57 tiền mặt. Gửi lại mã giữ nguyên
hai số dư. Mở lại game khôi phục bản lưu, và SQLite vẫn chỉ có một biên nhận.
Ở màn hình ngang 800×480, nút **GIFT CODE** dùng chữ hoa của menu chính.
Hộp thoại hiển thị cùng lúc ô nhập, nút xác nhận và đủ bốn hàng phím.

Xem [mô tả API, schema và số đo index MySQL](../backend/GIFTCODES.md).

## Điểm tích hợp

| Phần | File chính |
| --- | --- |
| Mục Cài đặt và hộp thoại | `FarmGameEngine.java`, `GiftcodeDialog.kt` |
| Dùng phiên đăng nhập, gửi và khôi phục lượt đổi | `GiftcodeController.kt`, `GiftcodeClient.kt` |
| Cập nhật số dư và lưu biên nhận | `GiftcodeFarmStore.kt`, `ProgressSaveController.kt`, `GameSaveStore.kt` |
| API và transaction MySQL | `backend/src/giftcodes/router.js`, `backend/src/db/giftcode-store.js` |
| Giao diện quản trị | `backend/web/giftcodes/` |
