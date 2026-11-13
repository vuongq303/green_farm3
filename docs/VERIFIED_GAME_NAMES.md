# Tên game đã xác minh trước khi refactor

Bảng này mô tả những symbol có đủ bằng chứng để đổi tên. Không áp dụng quy tắc
“mọi a là tiền” hoặc gán ý nghĩa cho tên obfuscated chỉ từ cách viết.

| Symbol gốc trong `S800x480` | Tên đích | Bằng chứng trách nhiệm/hành vi |
|---|---|---|
| class `ci` | `FarmGameEngine` | Lớp final kế thừa runtime, sở hữu update frame, state stack, load/save farm, quest/tutorial và điều phối gameplay. MIDlet tạo lớp này rồi gọi `Init()`. |
| class `ch` | `FarmEntity` | Pool/danh sách world entity, factory tạo/đặt entity, codec instance, tọa độ/grid, sprite/animation; dùng chung cho cây trồng, vật nuôi, công trình và nhân vật. |
| class `GLLib` | `GameRuntime` | Base runtime của engine, GL/input/media, đồng hồ, file/RMS và các thuật toán hỗ trợ. Không đổi tên thành renderer đơn thuần vì vai trò rộng hơn. |
| field `ci.aYR:I` | `FarmGameEngine.currentTutorialStepIndex:I` | `Es()` switch bước hướng dẫn, `jN()` xử lý thoại/hành động, codec đọc ở vị trí gốc 20678 và ghi ở 68875. Field là **int**, codec cố ý ghi/read **byte**; giữ nguyên cả hai. `-1` vẫn là giá trị sentinel. |
| field `ci.baa:Z` | `FarmGameEngine.hasUnsavedGameChanges:Z` | Nhiều hành động game đặt true; save controller chuyển cờ thành sequence rồi clear. Không chuyển khóa save hoặc ý nghĩa “đã commit”. |
| static method `ci.qD():I` | `FarmGameEngine.currentEngineState():I` | Đọc state ở đỉnh stack `apC`/`apB`; các transition test đã kiểm chứng giá trị trả về. |
| static method `ci.qE():I` | `FarmGameEngine.previousEngineState():I` | Đọc state cha trong cùng stack, trả sentinel khi không có parent; giữ nguyên điều kiện và giá trị. |

Tên `ci.bab` vẫn giữ: hiện cùng tham gia dirty signal, nhưng các nơi đặt cờ tập
trung ở một số đường dịch vụ/daily state và chưa chứng minh được vai trò riêng.
Không đổi nó thành “farmDirty”, “onlineDirty” hoặc “forceFullSave” bằng suy đoán.
Các field/hàm obfuscated khác trong engine/entity/runtime cũng được giữ nếu chưa
có phân tích riêng. Bảng này không có nghĩa toàn bộ mã game đã được đặt lại tên.

## Phương pháp

`tools/RenameVerifiedSymbols.java` dùng `JavacTask.analyze`, `Trees.getElement` và
owner đã resolve để chọn đúng khai báo, constructor, type reference, field access
và class literal. Hai method được chọn bằng owner + static + descriptor `()I`; overload
khác được giữ. Local/parameter tên `aYR`, field `Other.aYR`, lời gọi `Other.qD()`, field/biến tên `ci`
ở scope khác không bị đổi. Tất cả symbol Java phải resolve thành công trước khi
file đầu tiên được ghi. File class được đổi tên tương ứng. Chạy mặc định chỉ dry-run.

`tools/rename_verified_symbols.py` kiểm tra các helper Kotlin cùng package rồi
đổi type reference đã khoanh vùng và field/method truy cập có owner
`ci`/`FarmGameEngine`. Method call chỉ đổi đúng dạng không tham số; field tên `qD`
và lời gọi overload có tham số không bị đổi.
Tên bị shadow hoặc xuất hiện trong string interpolation sẽ dừng để phân tích
riêng; string/comment payload giữ nguyên. Đây là subset có guard của các helper
đã kiểm kê, không phải Kotlin resolver tổng quát. Sau đó build xác minh cả hai
ngôn ngữ với tên mới.

Bộ DEX relocation phải ánh xạ type/member cả hai chiều, gồm cả reference từ SDK.
Chỉ sửa source mà để DEX cũ gọi tên cũ là không đủ. Reflection đặc biệt đã tìm thấy:
`javax.microedition.lcdui.f` ghép package của engine với chuỗi **`.GLLib`**; DEX
rewriter phải đổi đúng literal đó sang `.GameRuntime`. Chuỗi `GLLib.quit` là lệnh/
nhãn runtime, không phải class identity và không được đổi tùy tiện.

Kiểm kê native không thấy game package hoặc ba class trên trong JNI của 10 file
`.so`; các SDK/native khác vẫn được giữ. Không có native method khai báo trực tiếp
trong ba lớp này. APK sau refactor vẫn phải qua kiểm tra class/reference và chạy
Android; phân tích source không thay thế kiểm thử đó.

## Kiểm tra công cụ

`tests/test_verified_symbol_refactor.py` chạy Java AST refactor trên project nhỏ
có local/field/parameter trùng tên, method khác owner/overload/field trùng tên,
constructor, qualified field, monitor class
literal và dữ liệu string/comment; compile lại Java sau refactor. Ca thứ hai xác
minh Kotlin shadow/template bị từ chối và member khác owner giữ nguyên.

```sh
python3 -m unittest discover -s tests -p test_verified_symbol_refactor.py -v
```

Cả 2 ca đạt. Báo cáo áp dụng trên project thật và kết quả APK được ghi ở checkpoint
chuyển Kotlin/package; không coi test công cụ là đã chạy gameplay sau đổi tên.
