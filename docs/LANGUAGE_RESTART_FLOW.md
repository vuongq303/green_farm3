# Đổi ngôn ngữ và khởi động lại phiên chơi

Các lựa chọn trong menu ngôn ngữ gọi `LanguageRestartController.select()` thay vì
gán `ci.aYU` và gọi `Cf()` ngay lập tức. Hộp xác nhận lấy bản dịch theo **ngôn ngữ
đang dùng** trước khi nhận lựa chọn mới. Hủy hoặc nút Back đóng hộp thoại và giữ
nguyên ngôn ngữ, tiến độ và phiên hiện tại. Chọn lại ngôn ngữ đang dùng không mở
hộp thoại và không khởi động lại.

Trong tutorial, nút pause trên HUD có thể đang bị ẩn theo kịch bản cũ. Phím
**Menu của Android** vẫn mở màn hình Options có sẵn để vào Language. Đường này
chỉ hoạt động trong farm của mình đã tải xong, khi tutorial đang chạy và không
có thao tác đặt/di chuyển chưa xác nhận hoặc chuyển màn hình đang chờ. Callback
phím chỉ xếp yêu cầu; luồng game mở Options bằng push state 15, giữ màn hình cha
và trạng thái tutorial. Các phím khác và phím Menu ngoài tutorial dùng nguyên
handler MIDlet cũ. Không thêm điều khiển debug hoặc sửa tiến độ để mở menu.

Hộp thoại có bản dịch cho đủ 14 gói văn bản đang được game nạp: EN, DE, FR, IT,
ES, BR, JP, KR, RU, TR, PL, TH, VI và LATAM. Mã 20 dùng asset `LATAM`; không sửa
các gói nhị phân hoặc thêm các mã mà `fM(30)` không hỗ trợ.

Luồng chấp nhận:

1. Callback nút Đồng ý chỉ ghi quyết định vào bộ điều phối. Mỗi lựa chọn có một
   mã thế hệ; nhấn lặp hoặc callback cũ không tạo thêm lần lưu/khởi động lại.
2. Luồng game nhận quyết định tại đầu frame, xóa sự kiện chạm đang chờ và khóa
   cập nhật game cùng các callback chạm. Callback Android ACTION_CANCEL vẫn được
   phép dọn trạng thái thao tác.
3. Chuẩn bị PendingIntent tới launcher `Start`, rồi yêu cầu
   `ProgressSaveController.requestLanguageCheckpoint()`. Writer ghi gameplay đang
   chờ bằng ngôn ngữ cũ trước, rồi ghi header ngôn ngữ mới tại hàng rào cuối. Nếu
   một callback SDK tạo thêm thay đổi, phiên vẫn khóa tới khi snapshot mới cũng
   commit. Chỉ sau đó mới gọi `SaveCompletion.completed(true)` trên luồng game.
4. Chỉ callback thành công mới gán ngôn ngữ trong RAM và đặt alarm khởi chạy
   `Start`. Sau khi đặt alarm thành công, tiến trình hiện tại kết thúc; mọi game
   loop, worker và static của MIDlet cũ kết thúc cùng nó. Không dùng
   `Activity.recreate()` vì `GloftGF2F.acw` và các static của engine vẫn tồn tại.
5. Tiến trình mới đi qua bộ nạp bình thường, đọc header và snapshot đã commit.
   Không gọi lại `select()` từ đường nạp nên không hiện lại hộp xác nhận.

Nếu lưu thất bại, callback không gán `ci.aYU`; phiên chơi cũ tiếp tục và hiện
Thử lại/Hủy bằng ngôn ngữ cũ. Thử lại yêu cầu checkpoint mới; hủy giữ phiên đó.
Không đánh dấu lưu thành công, reset farm, chạy lại tutorial hay phát lại phần
thưởng trong bộ điều phối ngôn ngữ.

Nếu header mới đã được ghi hoặc đã được đưa vào cache trước khi lỗi xảy ra,
writer thực hiện một transaction bù để khôi phục ngôn ngữ cũ. Hộp thoại Thử
lại/Hủy chỉ hiện sau khi transaction bù commit; lỗi ghi transaction bù giữ phiên
khóa và được writer thử lại. Vì vậy thông báo giữ ngôn ngữ cũ luôn dựa trên thiết
lập đã lưu bền vững.

Nếu Android từ chối exact alarm, bộ điều phối dùng alarm thường và không yêu cầu
quyền mới. Nếu cả thao tác chuyển sang launcher thất bại sau khi đã commit,
tiến độ và ngôn ngữ mới đã được lưu; phiên vẫn khóa và hộp thoại báo riêng lỗi
khởi động lại. Nút Thử lại chỉ thực hiện lại chuyển launcher, không lưu hoặc
phát thưởng lần nữa.

Kiểm tra nguồn, không compile/build/cài APK/ADB:

```sh
python3 -m unittest discover -s tools -p 'test_language_restart_source.py' -v
```

Bộ kiểm tra này đối chiếu 14 tuyến menu và asset, ngôn ngữ hộp thoại, vị trí
thay đổi thiết lập, khóa callback chạm, và thứ tự đặt alarm/kết thúc tiến trình.
Nó không thay thế kiểm tra thực tế vòng đời Android và SQLite.

Kiểm tra hành vi các bộ điều phối thật bằng Java fixture cô lập:

```sh
python3 -m unittest discover -s tests -p 'test_progress_save_controller.py' -v
```

Bộ kiểm tra này chạy nguyên mã `ProgressSaveController`,
`LanguageRestartController`, `SaveBatchPolicy` và các hàm chuyển trạng thái được
trích từ `ci`. Writer thủ công điều khiển thời điểm xác nhận commit; Android và
storage là fixture. Các ca gồm hủy, nhấn Đồng ý lặp, retry sau lỗi lưu, thay đổi
SDK tới muộn, rollback ngôn ngữ đã commit, queue từ chối header ban đầu, Retry khi
writer còn bận, và retry riêng thao tác restart. Nó không tạo/cài APK và không
dùng ADB; alarm và kết thúc tiến trình được quan sát qua fixture.

Fixture cũng chạy hai override phím thật trong `GloftGF2F`: phím thường được
chuyển cho MIDlet đúng một lần, Menu lặp chỉ xếp một lần mở Options, và yêu cầu
không được mở khi đang visit farm, còn preview hoặc có chuyển màn hình khác.

Ma trận kiểm tra runtime khi được phép chạy thiết bị:

| Tình huống | Kết quả cần quan sát |
| --- | --- |
| Giữa tutorial, nhấn Menu Android → Options → Language | Mở menu cũ; không thay đổi bước tutorial, tiền hoặc phần thưởng đang chờ |
| VI → EN, chọn Hủy/Back | Hộp thoại tiếng Việt; ngôn ngữ và phiên không đổi |
| EN → VI, chọn OK một lần/nhiều lần | Hộp thoại tiếng Anh; chỉ một checkpoint và một phiên mới |
| Đổi ngôn ngữ khi còn tiến độ mới | Chờ commit trước khi thoát; farm, quest, tutorial và phần thưởng khôi phục đúng |
| SQLite bị lỗi khi lưu | Không restart; ngôn ngữ cũ và phiên cũ còn nguyên; Thử lại có thể thực hiện |
| Mở menu trước khi vào farm hoặc sau khi rời farm | Header mới không làm mất snapshot farm đã lưu |
| Khởi động lại hoặc quay lại app sau đổi ngôn ngữ | Không hiện lại xác nhận; không có game loop của phiên trước |
| Exact alarm bị từ chối | Không hỏi quyền; tự khởi động bằng alarm thường |
