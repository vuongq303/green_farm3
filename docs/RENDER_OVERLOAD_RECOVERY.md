# Khôi phục kiểu tham số của lời gọi vẽ

## Bằng chứng runtime

Bản Java lõi sau khi sửa các vòng lặp hoàn tất loader nhưng đứng ở màn hình
đen. Đọc trạng thái bằng JDI cho thấy `asj=ask=38`, `asm=true`, `apB=-1`,
`asi=60`: loader đã chạy hết nhưng chưa đẩy màn hình tiếp theo.

Exception listener bắt được lỗi bị vòng game xử lý nội bộ:

```text
ArrayIndexOutOfBoundsException: length=10; index=256
GLLib.a(IIIIII)Z <- GLLib.a(aj,ay,IIIIIFFI)V
                 <- a <- ci <- loader
```

Lời gọi tọa độ texture có sáu đối số nguyên trong export JADX, khiến Java chọn
hàm kiểm tra pointer `a(IIIIII)Z`. Smali gốc chuyển bốn đối số cuối sang float
và gọi `a(IIFFFF)V`. Chỉ ép kiểu đúng các đối số đó, không đổi tên overload,
không sửa pointer hoặc bắt/bỏ qua thêm exception.

Hai callsite texture được đối chiếu trong `GLLib.smali`: dòng11537 và11183
(lần lượt các overload draw `aj,ay,IIIIIFFI` và `aj,ay,IIIIIFFFFFFFF`).
Sau sửa callsite đầu, emulator thực sự hiện age gate; JDI thấy `apB=0`,
`apC[0]=60`, `ash=null`, `asi=-1`. Chữ vẫn sai vì những lời gọi sprite/font
cũng mất kiểu float. Chưa coi lượt này là bản vẽ đúng hoặc chơi được game.

Log/ảnh chẩn đoán trong output bỏ qua Git:
`build/reports/restoration/runtime-core-source/continuation-fixes/` và
`build/reports/restoration/runtime-core-source/texture-overload-first/`.

## Các lớp peer dùng sprite

Các lời gọi dưới đây phải chọn `a.a(aj;IFFI)V`, khác với overload module
`a.a(aj;IIII)V`. Giữ phép tính nguyên và thứ tự tính biểu thức, chỉ ép tọa độ
kết quả sang float đúng như `int-to-float` trong DEX.

| Lớp/phương thức | Callsite Java | Bằng chứng smali gốc |
|---|---:|---|
| `aw.d(aj,IIIII)` | 1 | `aw.smali:7503`, chuyển p3/p4 sang float |
| `bf.a(aj,String,char[],IIIIZZZ)` | 7 ở nhóm sửa overload; 4 sau khi khôi phục đường nhánh dùng chung | `bf.smali:864,910,930,1023`; JADX nhân đôi phần nhánh dùng chung nên số callsite Java khác số instruction DEX |
| `ch.pI()` | 3 | `ch.smali:19964,20042,20139` |
| `ch.aE(II)` | 1 | `ch.smali:5918` |
| `ch.r(IIII)` | 1 | `ch.smali:33144` |

Kiểm tra descriptor là kiểm tra liên kết lời gọi. Nó bổ sung cho test thuật
toán và runtime; không thay thế thử hình ảnh, hiệu ứng hoặc gameplay thực tế.

Nhóm sau đã sửa phép căn dọc của wrapped text: phần tính anchor bottom/center
trước đây bị `i7=i2` ghi đè. 7.392 so sánh tọa độ, clipping, range và style với
Code gốc đạt. Ảnh emulator của APK cuối khớp pixel với ảnh APK gốc trong ba vùng
header/body/links, tổng233.220 pixel. Chỉ kết luận cho những vùng/màn hình đó;
chưa kiểm tra hình ảnh trong farm. Xem [runtime](runtime-core-source.json).
