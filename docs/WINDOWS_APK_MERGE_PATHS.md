# Thư mục ghép APK trên Windows

Lỗi `Carrier APKs, merged APKs and merge work must have separate directories`
được ném trong `MergeOriginalSdkApk.merge()` trước bước dọn dẹp. Điều kiện là
bất kỳ hai thư mục nào trùng nhau hoặc một thư mục nằm trong thư mục kia.
Log Windows được cung cấp chưa có giá trị của ba provider, nên chưa xác định
được cặp đường dẫn thực tế gây lỗi trên máy người dùng.

Điều kiện `Path.startsWith(Path)` sau `canonicalFile` được giữ nguyên. Nó so
sánh từng thành phần đường dẫn, dùng quy tắc phân biệt hoa/thường của hệ thống
file Windows; không phải phép so sánh tiền tố chuỗi. Không bỏ guard, không
nuốt exception và không bỏ bước ghép runtime SDK.

## Thay đổi

- `app/build.gradle`: sau khi đăng ký `toTransformMany(SingleArtifact.APK)`,
  đặt rõ `outputApks` bằng `layout.buildDirectory.dir("outputs/apk/${variant.name}")`.
  Thứ tự này tránh để cấu hình đầu ra mặc định của AGP ghi đè giá trị đã đặt trước.
- Work dùng `intermediates/original-sdk-merge/${variant.name}`; tên task cũng
  được tạo theo variant. Không hardcode đường dẫn máy người dùng.
- `MergeOriginalSdkApk.groovy`: luôn in ba đường dẫn tuyệt đối đã canonical hóa
  và nêu từng cặp bị xung đột trong exception, trước mọi thao tác xóa/ghi.
- Input vẫn do AGP nối từ artifact của producer trước. Không tự nối input vào
  output. Final artifact, metadata và IDE deployment vẫn đi qua transform chuẩn.

Với checkout cloud và variant `debug`, log của cả hai lượt build ghi đúng ba
đường dẫn tuyệt đối sau; không cặp nào trùng hoặc có quan hệ cha–con:

| Vai trò | Đường dẫn tuyệt đối |
| --- | --- |
| Carrier input | `/workspace/green_farm3/app/build/intermediates/apk/debug/packageDebug` |
| Merged output | `/workspace/green_farm3/app/build/outputs/apk/debug` |
| Merge work | `/workspace/green_farm3/app/build/intermediates/original-sdk-merge/debug` |

APK cuối: `app/build/outputs/apk/debug/app-debug.apk`. Chỉ có module Android
`app`; `compile-view.jar` vẫn là `compileOnly`. Verifier của task vẫn kiểm tra
định nghĩa thật của `javax.microedition.midlet.App` cùng các lớp khởi động và
giữ nguyên assets/native, không thay bằng Application rỗng.

## Lệnh build đúng

Trong checkout này, chạy literal `:app --stacktrace` trả lỗi chọn task:
`Task 'app' not found in root project`. `:app` là đường dẫn module, không phải
tên task assemble hoặc build variant. Trên Windows dùng:

```powershell
.\gradlew.bat :app:assembleDebug --stacktrace
.\gradlew.bat :app:installDebug --stacktrace
```

Trong Android Studio: Sync Gradle → chọn Android App `app` → chọn thiết bị → Run.

## Kiểm chứng

- `./gradlew :app --stacktrace` sau sửa: lỗi chọn task `app`, không phải lỗi ghép.
  Không thêm task alias để che lỗi chọn lệnh; dùng task assemble đầy đủ bên trên.
- `:app:assembleDebug --stacktrace`: thành công trong 1 phút 7 giây.
- Chạy lại assemble/install với `android.injected.apk.location` cố tình trỏ tới
  work: thành công trong 58 giây. Output được pin vẫn tách biệt; metadata/IDE
  redirect vẫn trỏ tới `outputs/apk/debug/output-metadata.json`.
- Negative test qua init script local: đặt output bằng work. Guard từ chối,
  chỉ rõ `merged APK output overlaps merge work`; file sentinel trong work còn
  nguyên sau lỗi, chứng minh chưa xóa dữ liệu. Init script không nằm trong cấu
  hình build thông thường.
- Sáu assertion Groovy với filesystem Windows mô phỏng Jimfs đạt: ba cặp thư
  mục tách biệt, tên khác hoa/thường, thư mục con và tên `debug-other`. Đây là
  mô phỏng quy tắc đường dẫn, không phải build trên Windows thật.
- APK cuối có 6.397 định nghĩa lớp trong một DEX, Application thật
  `javax.microedition.midlet.App`; 49 assets và 10 thư viện native giữ nguyên.
  Chữ ký/ZIP alignment và metadata APK được kiểm tra trong pipeline.
- `:app:installDebug` đã cài đúng APK: SHA-256 của APK trên emulator trùng file
  build. Đã mở trên API 28, giữ process sống qua mốc 20 giây, không có FATAL hoặc
  lỗi thiếu Application; ảnh ghi nhận màn hình Connect/Not now.

APK SHA-256: `c1d503c7a9a7d29ecaa2756376d70a8eb3ff3b0b81b9fde0884f6dbedf11591a`.
File APK đã commit trong `artifacts/` trùng byte với kết quả build này nên không
thay binary một lần nữa. [Log, kết quả và ảnh](windows-merge-validation/result.json)
được lưu cùng checkpoint; Logcat đầy đủ ở `.local/windows-merge-fix/` trên cloud.

Chưa chạy native Windows hoặc bấm Run trong Android Studio Windows. Các lệnh
thực tế trên cloud dùng `./gradlew` tương đương, không chạy file `.bat`. Chưa
thử đăng nhập, thiết bị thật, Android mới hoặc toàn bộ gameplay trong lần sửa này.
Không kết luận chính xác cặp gây lỗi Windows cũ khi chưa có đường dẫn/log từ máy đó.

Nếu Windows vẫn lỗi, lấy log của `:app:assembleDebug --stacktrace`, đặc biệt ba
dòng `carrier APK input`, `merged APK output`, `merge work` và các dòng
`overlaps` ngay dưới exception. Những giá trị này phân biệt được provider bị
nối nhầm, output override từ IDE/plugin, hoặc đường dẫn canonical trỏ vào nhau.
Không cần cung cấp mật khẩu hay token để chẩn đoán lỗi thư mục.
