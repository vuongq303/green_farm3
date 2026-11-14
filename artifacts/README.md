# APK hiện tại

[green-farm-3-debug.apk](green-farm-3-debug.apk) là bản offline được build bằng
`:app:assembleDebug`, application ID `com.quanhoang.greenfarm3`.
[SHA-256](green-farm-3-debug.apk.sha256), [provenance](build-info.json),
[báo cáo sửa lỗi và kiểm thử](../docs/OFFLINE_IMPLEMENTATION.md).

APK không có quyền Internet, Firebase class/provider/config; các asset/native
được đối chiếu nguyên byte với APK đầu vào. Không cần chạy backend.
Cài cập nhật bằng đúng khóa ký hiện tại, không uninstall hoặc clear data để
thử mở lại. Cần sao lưu trước mọi thao tác chuyển phiên bản.

`green-farm-3-legacy-export-debug.apk` là binary cầu nối lịch sử **chưa được
build lại trong lần sửa này**, có mã online cũ, không phải sản phẩm offline để
chơi. Chỉ giữ để không mất đường xuất dữ liệu từ application ID cũ. Tham khảo
[quy trình chuyển save](../docs/SAVE_TRANSFER_KOTLIN.md); chữ ký phải khớp bản cũ.
Không cài cầu nối thay cho bản offline mới hoặc suy ra kiểm thử bản mới áp dụng
cho binary cầu nối này.
