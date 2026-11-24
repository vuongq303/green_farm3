# Khởi động Green Farm 3 offline

## Bản build

```powershell
.\gradlew.bat :app:assembleDebug -PgreenfarmOfflinePlay=true
```

Trên Linux/macOS, dùng `./gradlew :app:assembleDebug -PgreenfarmOfflinePlay=true -PpythonExecutable=python3`. APK hoàn chỉnh nằm tại `app/build/outputs/apk/debug/app-debug.apk`. Dùng cùng cờ với `:app:installDebug` nếu máy đã kết nối thiết bị. Cài đè bằng cùng khóa ký để giữ dữ liệu ứng dụng. Dự án dùng JDK 25, Android SDK Platform 37.0 và 28, Build Tools 37.0.0 và Python 3.10+.

`greenfarmOfflinePlay` là tùy chọn build. Bỏ cờ khi cần bản Debug dùng luồng tài khoản mặc định. Chế độ này dùng mức tiền khởi đầu thông thường miễn là không bật `greenfarmLocalDevelopment`. Tài khoản, giftcode và các dịch vụ online cần bản build có kết nối tương ứng.

## Truy vết

1. `FarmGameEngine.il(2)` chọn trạng thái chào mừng sau logo. State 60 gọi `lA()`, dựng màn nhập tuổi trên layer 44 với text và các liên kết điều khoản gốc. Chạm ô tuổi (action `2883590`) chạy `Dx()` để mở nhập số. `lA(2)` đọc và parse thông tin nhập mỗi frame. Nhấn ACCEPT (action `2883588`) gọi `Dt()`. Tuổi trống, không hợp lệ hoặc không dương đi vào hộp thoại 777.
2. Trong luồng Janus cũ, `Dt()` chỉ tiếp tục khi `qO()` xác nhận kết nối và `com.gameloft.a.b.a.fZ()` xác nhận SDK đã qua discovery và authorize. Khi không có policy đúng country trong `OF_PP.bin`, nó gọi `i.a(age, country, new cn())`. `a.d()` gửi POST `/users/me/compliance` với `access_token`, `country`, `age` và đợi worker trả kết quả. `i.b()` xử lý HTTP 200 rồi parser gọi `cn.a(l, m)` với age, country và raw JSON. Callback cập nhật cờ phân loại policy. Handler tiếp tục và chọn state sau khi request trả về. Backend repo hiện trả đối tượng `LOCAL_RESEARCH_16_PLUS` với cờ Boolean `compliant`. Xem [bằng chứng request và callback](backend/ACCEPT_PROTOCOL_EVIDENCE.md).
3. Đoạn chung của `Dt()` ghi tuổi và cờ qua `ro()` (`RMSHeader` và record liên quan) và ghi lựa chọn giới tính qua `qP()`. Ngay sau đó `cb()` gửi telemetry qua `baf`. Các mode bỏ qua Janus không tạo tracker trong bước khởi tạo engine, nên `cb()` có thể gặp null trước khi gọi `fF(18)`.
4. `fF(18)` đưa game vào trình tải sẵn có. `qV()` chạy các bước `fM()`, gồm restore `save_gameplay` từ SQLite, nạp tileset, dựng lại đối tượng và tải farm. Farm, tài nguyên và tiến trình đến từ APK và SQLite. Response compliance không cung cấp dữ liệu của các bước đó. State 47 là luồng kết nối hoặc tài khoản riêng.

## Luồng sau sửa

Bản offline hiện lại màn điều khoản cho hồ sơ chưa có tuổi và xác nhận đã lưu. Link Privacy Policy, Terms of Use và EULA vẫn dùng cùng handler khi thiết bị có mạng. ACCEPT hợp lệ dùng phân loại tuổi cục bộ 16+ đã có trong chế độ local, lưu các record hiện hành rồi đưa thẳng vào state 18. Giá trị phân loại này không phải chứng nhận compliance từ máy chủ. Người dưới ngưỡng vẫn vào farm cục bộ với phân loại hạn chế hiện có.

Chế độ này dùng lại cổng bỏ qua Janus, CRM, telemetry và remote saves. Callback Facebook cũ không khởi tạo. Nhánh online-accounts không được bật trong APK offline. Các bước startup không cần JSON giả, token, dữ liệu người chơi thay thế hoặc thao tác xóa kho SQLite.

## Kiểm chứng

- Build offline đã qua `apksigner verify` và `zipalign -c -p 4`. Build xác nhận cấu hình `OFFLINE_PLAY=true`, `ENABLED=false` và `ONLINE_ACCOUNTS=false`.
- 14 bài hồi quy JVM/Python đã qua. Chúng kiểm tra tuổi trống, luồng load 18, khả năng mở lại khi đã lưu xác nhận, các ranh giới telemetry và SDK, luồng tài khoản online, mức tiền khởi đầu và các controller lưu tiến trình. Phép thử consent trên JVM dùng các peer thay cho game loop và kho dữ liệu Android.
- APK đã cài và mở trên emulator Android 9/API 28 khi Wi-Fi và giao diện radio đều ở trạng thái DOWN, với route probe IPv4 và IPv6 đều unreachable. Màn điều khoản xuất hiện, ACCEPT với ô tuổi trống hiện “Please enter a valid age.” và bàn phím game nhận tuổi 30.
- Sau lần bấm ACCEPT hợp lệ, emulator phần mềm ngừng trả cả lệnh ADB và ảnh màn hình. Log emulator báo lỗi color buffer. Khởi động lại cùng AVD bằng bộ dựng hình phần mềm khác và giữ nguyên dữ liệu vẫn không lấy được shell ADB. Kết quả vào farm và mở lại từ SQLite trên Android còn chờ xác nhận trên một thiết bị ổn định.
