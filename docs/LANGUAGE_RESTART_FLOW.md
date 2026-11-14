# Đổi ngôn ngữ và khởi động lại phiên chơi

Menu hiện có vẫn gọi `LanguageRestartController.select()`, hỗ trợ 14 locale của các gói text gốc. Không sửa tài khoản hay đăng xuất backend: game phải nạp lại text/font và MIDlet, không cần hủy session đăng nhập.

1. Luồng game khóa thao tác, chụp tiến độ đầy đủ rồi writer SQLite ghi. Sau đó ghi lựa chọn ngôn ngữ vào header. Callback phát sinh trong lúc ghi phải được đưa vào checkpoint trước khi báo thành công.
2. Sau khi có xác nhận durable, mở **engine20 / GUI4** hiện có: khung, nền, font, vị trí nút, animation và input handler gốc. Không dùng `AlertDialog` cho luồng ngôn ngữ. Nội dung và nút lấy qua `textFor()` theo locale đang hiển thị, không theo locale mới chưa nạp.
3. **Khởi động lại**: chuẩn bị PendingIntent launcher `Start`, đặt alarm, rồi kết thúc tiến trình cũ để MIDlet/static/worker được tạo lại. Không reset farm, không đăng xuất tài khoản. Nếu handoff lỗi, popup game cho thử lại hoặc để sau; thử lại chỉ chạy handoff, không nhận lại thưởng hoặc lưu lại gameplay.
4. **Để sau**: đóng popup, tiếp tục với text/font và ngôn ngữ đang hiển thị. `deferredLanguage` giữ lựa chọn đã lưu; các snapshot sau vá byte4 của header đã tách, không đổi biến locale trong lúc render. Storage cũng giữ lựa chọn này khi Options/SDK gọi ghi header trực tiếp, không sửa buffer của caller. Lần mở game sau đọc lựa chọn mới. Chọn lại locale đang hiển thị sẽ hủy lựa chọn đang chờ mà không cần khởi động lại. Đây không phải thông báo giả rằng locale đã áp dụng ngay.
5. Nếu ghi thất bại, không báo thành công. Nếu header đã publish trước khi lỗi, checkpoint bù phải khôi phục thiết lập trước đó trước khi thả barrier. Retry SQLite tự động tối đa 5 lần; khi dừng vẫn giữ snapshot trong RAM và cho người dùng thử lại. Barrier bù chưa durable không cho đóng popup để tiếp tục với dữ liệu chưa nhất quán.

Popup dùng callback một lần, dọn input cũ, và đợi state20 pop xong trước khi tiếp tục retry/handoff. Nó không thay các popup tutorial khác hoặc tạo thêm hệ thống render riêng. Phím Menu Android trong tutorial và thao tác chuyển save được giữ.

Kiểm tra trên host:

```sh
python3 -m unittest discover -s tools -p test_language_restart_source.py -v
python3 -m unittest discover -s tests -p test_progress_save_controller.py -v
```

Đây là kiểm tra nguồn và điều phối thật với Android peers điều khiển được, không phải kiểm tra font, clipping, ảnh popup hay alarm trên thiết bị. Xem [báo cáo bảo trì](MAINTENANCE_REPORT.md) để biết kết quả và giới hạn mới nhất.
