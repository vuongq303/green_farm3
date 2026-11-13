# APK kiểm thử offline riêng

Profile này là bản kiểm thử tách khỏi ứng dụng thường. Chỉ bật khi truyền
`-PgreenfarmOfflineTest=true`; mặc định là `false`. Không kết hợp với
`-PgreenfarmLegacyExport=true`.

```powershell
.\gradlew.bat :app:clean :app:assembleDebug -PgreenfarmOfflineTest=true --stacktrace
```

Trên Linux dùng `./gradlew` với cùng task và tham số. APK hoàn chỉnh vẫn đi qua
pipeline `app`, ghép runtime/SDK gốc, ký và kiểm tra trước khi xuất tại
`app/build/outputs/apk/debug/app-debug.apk`. Không cài carrier APK trong
`app/build/intermediates`.

| Thuộc tính | Bản thường | Bản kiểm thử offline |
| --- | --- | --- |
| Application ID | `com.quanhoang.greenfarm3` | `com.quanhoang.greenfarm3.offlinetest` |
| Namespace mã game | `com.quanhoang.greenfarm3` | `com.quanhoang.greenfarm3` |
| Nhãn launcher | Nhãn tài nguyên gốc | `Green Farm 3 Offline Test` |
| Quyền `INTERNET` trong APK | Có | Không có |
| Đăng nhập và kết nối server của game | Luồng hiện có | Tạm dừng bằng cờ profile |

`generateTestProfile` tạo lớp Kotlin `OfflineTestConfig` và manifest debug từ
template `app/src/debug/AndroidManifest.xml`. Các helper gameplay vẫn là mã nguồn
được review trong `app/src/main/kotlin`, không phải lớp giả được sinh để ghép DEX.
Bộ đóng gói kiểm tra lớp config có Kotlin metadata và thực sự nằm trong overlay.
`KeyProvider` giữ tên lớp trong namespace mã game, còn authority và URI sử dụng
application ID riêng; chỉ hai literal đã xác minh được thay thế, có kiểm tra đảo
ngược ngữ nghĩa.

Các quyền đọc trạng thái mạng/Wi-Fi vẫn được giữ để SDK gốc có thể phát hiện
offline an toàn. Bộ kiểm tra đọc **binary manifest của APK cuối**, từ chối profile
offline nếu còn bất kỳ khai báo quyền `INTERNET` nào, kể cả khai báo giới hạn SDK.
Các kiểm tra mạng của bản thường vẫn được giữ.

Xem [OFFLINE_TEST_BALANCES.md](OFFLINE_TEST_BALANCES.md) về số tiền, level và cách
lưu trạng thái kiểm thử. Bản thường không tự đọc được bộ nhớ riêng của ứng dụng
test; không chuyển save test sang bản chơi thật để kiểm tra mốc này.

Để build lại bản thường, bỏ cờ profile. Để xác minh APK test thủ công:

```powershell
python tools/verify_runtime_apk.py --apk app/build/outputs/apk/debug/app-debug.apk --original green-farm-3.apk --sdk "$env:ANDROID_HOME" --metadata app/build/outputs/apk/debug/output-metadata.json --application-id com.quanhoang.greenfarm3.offlinetest --game-package com.quanhoang.greenfarm3 --offline-test
```

Build thành công chỉ chứng minh pipeline/đóng gói; báo cáo chạy trên emulator hoặc
thiết bị được ghi riêng sau kiểm thử thực tế.
