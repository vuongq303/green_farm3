# Đổi ngôn ngữ và khởi động lại phiên chơi

Menu hiện có vẫn gọi `LanguageRestartController.select()`, hỗ trợ14 locale từ các gói text gốc. Thay ngôn ngữ không đăng xuất tài khoản hoặc reset farm; cần tạo lại MIDlet/static để áp dụng toàn bộ text và cấu hình ở lần khởi động mới.

1. Luồng game khóa thao tác, chụp tiến độ đầy đủ và để writer SQLite commit. Sau đó ghi lựa chọn ngôn ngữ vào header. Callback phát sinh trong lúc ghi cũng phải trở thành durable trước khi báo thành công.
2. Khi checkpoint thành công, nạp đúng **nhóm font của ngôn ngữ vừa chọn**, theo cùng mapping với loader gốc: `/2` cho nhóm chung, `/3` TH, `/4` VI, `/5` KR, `/6` JP; JP/KR còn dùng slot Latin từ `/2`. Tạo các font/charmap trong nhóm tạm, xác minh đủ slot rồi mới đổi đồng thời `aZh` và `asA[2]`. Không giải phóng font mà màn hình cũ còn có thể tham chiếu, không nạp lại texture/sprite/farm.
3. Thông báo dùng **engine20 / GUI4** hiện có, nội dung `textFor(selectedLanguage)` và chỉ có một nút **Khởi động lại** được dịch theo lựa chọn. Không có Hủy/Để sau/Đóng; Back không bỏ qua checkpoint. Live `aYU` và các text pack gameplay vẫn giữ nguyên cho tới khi tiến trình mới nạp lại; không trộn header với font bằng cách đổi locale giữa frame.
4. Nhấn Khởi động lại chuẩn bị PendingIntent launcher `Start`, rồi vào `FINAL_SAVING`: một checkpoint bất đồng bộ cuối xử lý các thay đổi SDK/settings đến trong lúc popup mở. Chỉ sau xác nhận durable cuối mới đặt alarm và kết thúc tiến trình cũ. Chạm lặp không tạo nhiều handoff; đường nạp save không gọi lại `select()` nên không tạo vòng lặp restart.
5. Nếu checkpoint cuối hoặc handoff lỗi, không kết thúc tiến trình. Popup target-language giữ một nút Khởi động lại để người dùng thử lại; checkpoint dùng giá trị tuyệt đối và không gọi lại nhận thưởng. Không tự retry restart theo từng frame.
6. Nếu font target không tải được, chỉ thử nạp nhóm đó tối đa2 lần mỗi yêu cầu. Font cũ còn hợp lệ được giữ/khôi phục để hiện **lỗi tài nguyên** với một nút Thử lại. Đây không phải thông báo thành công bằng locale cũ: do glyph target chưa dùng được, lỗi được hiển thị bằng font/locale còn hợp lệ. Header lựa chọn đã commit không bị xóa hoặc đổi lại. Chỉ thao tác Thử lại mới nạp lại font, không phát lại gameplay hoặc vòng lặp tự động.
7. Lỗi lưu trước thông báo thành công tiếp tục dùng cơ chế rollback/bù hiện có; không báo thành công hoặc khởi động lại khi chưa có dữ liệu nhất quán. Retry SQLite vẫn hữu hạn và snapshot chưa lưu giữ trong RAM.

`deferredLanguage`/header override hiện dùng để giữ lựa chọn durable trong thời gian **chờ bắt buộc khởi động lại**, không còn là tùy chọn Để sau. Nó bảo vệ cả ghi header trực tiếp từ Options/SDK và không sửa buffer của caller.

Các popup giữ callback một lần, dọn input cũ và pop state20 trước prompt/handoff kế tiếp. Phím Menu tutorial, ownership của popup tutorial, và tính năng chuyển save được giữ nguyên.

Kiểm tra host:

```sh
python3 -m unittest discover -s tools -p test_language_restart_source.py -v
python3 -m unittest discover -s tests -p test_progress_save_controller.py -v
python3 -m unittest discover -s tests -p test_language_notification_fonts.py -v
```

Các test chạy controller thật và font-staging hook được trích từ engine với Android/pack peers điều khiển được:14 locale, nút duy nhất, Back, final save, late writes, lỗi font có giới hạn, tái dùng cache hợp lệ và phục hồi font đã giải phóng. Chưa xác nhận glyph decode, clipping, ảnh popup hoặc alarm trên Android thật; cần kiểm thử thiết bị, đặc biệt đổi giữa VI/TH/JP/KR và nhóm font chung.

Khôi phục constructor/Init chỉ áp dụng trước mốc engine sẵn sàng; tại đó chưa thể mở menu/popup ngôn ngữ. Không reset trạng thái thông báo hoặc lựa chọn ngôn ngữ khi chỉ pause/resume một engine đang hoạt động.
