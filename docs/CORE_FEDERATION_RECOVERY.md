# Federation: nonce join và exception metadata

Ba lỗi compile cuối tại `S800x480/s.java` được đối chiếu trực tiếp với
`s.smali` trong APK gốc 4.4.2/44220. APK/DEX hash và provenance công cụ xem
[ORIGINAL_BYTECODE_TOOLS.json](ORIGINAL_BYTECODE_TOOLS.json).

- **`cl(int):String`**: DEX khởi tạo register nonce bằng null. Khi
  `a(request,response)` trả false, `:cond_ca` chuyển null tới join `:goto_a9` rồi
  dùng fallback chuỗi rỗng. Bổ sung đúng nhánh `else {strCh=null;}`; không thêm
  nonce hoặc coi response lỗi là thành công.
- **`cs(int):String`**: DEX chỉ bắt `IOException` từ decode-response tại
  `:try_start_237..:try_end_240`, log qua `GLLib.d`, rồi trả response nguyên gốc.
  `GLLib.bJ` sau khôi phục không cần checked-throws declaration nên Java compiler
  không cho catch IOException trực tiếp. Helper private `decodeResponse(String)`
  chỉ forward tới chính `GLLib.bJ` và khai báo `throws IOException` cho compiler.
  Handler vẫn chỉ bắt IOException; không mở rộng catch, không thay dữ liệu.
- **`ae(boolean)` → `G(int,int)`**: DEX không có handler JSONException cho lời
  gọi tracking. Source của `G` có checked-throws declaration; tại caller dùng
  generic rethrow để cùng exception instance/type tiếp tục ra ngoài. Không
  wrap, swallow hoặc chuyển sang một kết quả mặc định.
- **Biên catch của `G`**: handler NoClassDefFoundError của DEX chỉ bảo vệ lần
  `TrackingManager.Qm()` đầu và kiểm tra null (`:try_start_b..:try_end_17`).
  Source export đã mở rộng try qua payload/dispatch. Thu hẹp lại đúng vùng gốc;
  payload `cv(cz(i))`, chọn c2dm/gcm/default event ID và lời gọi tracking cuối
  nằm ngoài handler đó, giữ nguyên thứ tự gọi và dữ liệu.

Helper typed-throws và generic rethrow chỉ giải quyết cách Java biểu diễn
checked exception; không thay đổi class/method descriptor được các caller DEX
sử dụng. Generic throw không tạo exception mới hoặc reset stack trace.

Các extract kiểm tra giữ tại
`/workspace/tooling/recovery-bytecode/s-{cl,cs,ae}.smali`; `G(II)V` được đọc từ
cùng bản disassembly. Các vùng handler kể trên là bằng chứng chính, không suy
ra hành vi từ lỗi compiler hoặc tên SDK. Kiểm tra compile không thay thế việc
thử request/timeout/tracking trên Android; không thay networking/online policy
trong nhóm sửa này.

Kiểm tra thực tế `:core-source:compileDebugJavaWithJavac` sau thay đổi không còn
diagnostic `s.java`; lượt đó vẫn exit1 với hai lỗi mới ở `by.java` (TCP worker).
Log lưu tại `/workspace/tooling/core-source-s-final-fix.log`. Không coi việc
compile qua Federation là xác nhận dịch vụ online hoặc toàn bộ APK hoạt động.
