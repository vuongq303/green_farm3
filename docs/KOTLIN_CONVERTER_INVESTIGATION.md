# Công cụ chuyển Java → Kotlin đã kiểm tra

Toolchain của project dùng Kotlin **2.4.21**, Gradle **9.7.1**, AGP **8.10.1**,
JDK **25**, JVM target **1.8** và Android SDK **28**. Không đổi các phiên bản
Gradle/AGP/JDK/SDK. Plugin Kotlin được Maven xuất bản với variant `gradle96`;
Gradle hiện tại đã chọn variant này và `./gradlew help --stacktrace` thành công.

Compiler test độc lập được chuẩn bị bởi `tools/ensure_kotlin_compiler.py`.
Tám artifact host có phiên bản và SHA-256 cố định; download từ Maven Central qua
proxy/CA hiện có, cache dưới `.local/kotlin-compiler`. Compiler, reflect và
coroutines dùng cho test **không** được đóng gói vào APK. APK chỉ thêm Kotlin
standard library 2.4.21 từ configuration riêng; `compile-view.jar` vẫn compileOnly.

## Khảo sát converter chính thức

Đã kiểm tra công cụ thực tế trước khi quyết định chuyển các nhóm mã bằng tay:

- JAR `kotlin-compiler-embeddable-2.4.21.jar` không có entry `j2k`/`nj2k`.
- Thử nạp `org.jetbrains.kotlin.j2k.JavaToKotlinConverter` bằng JVM compiler
  classpath trả về `ClassNotFoundException`; đây không phải API trong compiler CLI.
- Maven Central trả HTTP 404 cho hai artifact giả định `org.jetbrains.kotlin:j2k`
  và `org.jetbrains.kotlin:kotlin-j2k`. Không sử dụng tên artifact này làm dependency.
- Không tìm thấy IntelliJ IDEA/Android Studio hoặc J2K JAR cài trong executor;
  danh sách tool đang có cũng không cung cấp `convert_java_to_kotlin`.
- Đã đọc converter, project service và MCP tool chính thức tại revision
  [`09f03735d4674eea9f112165fe45744a25fbdd56`](https://github.com/JetBrains/intellij-community/tree/09f03735d4674eea9f112165fe45744a25fbdd56/plugins/kotlin/j2k).
  `JavaToKotlinService` cần IntelliJ `Project`, `Module`, `PsiJavaFile`, Kotlin
  Analysis API, read/write actions trên EDT và xử lý external usages. MCP tool
  gọi service của một project IDE đã mở; không phải executable độc lập chỉ nhận
  đường dẫn Java. Module IML phụ thuộc PSI, indexing, analysis, editor, refactoring
  và nhiều module IDE khác, ngoài compiler embeddable.
- Thử HEAD tới bản cài IDEA chính thức qua proxy hiện tại bị chặn ở CONNECT với
  HTTP 403 cho `download.jetbrains.com`. Không đổi network policy hoặc bỏ proxy
  để vượt giới hạn này.

Các kết quả trên mô tả **khả năng của môi trường đang dùng**, không chứng minh
rằng J2K không thể chạy headless trong một môi trường có đầy đủ IDE SDK/service.
Không có lần chuyển tự động bằng J2K thành công nào được tính vào kết quả project.
Không dùng thay thế regex hoặc đổi đuôi `.java` thành `.kt` để tuyên bố đã chuyển
các lớp game lớn. Phạm vi Kotlin/Java/DEX còn lại được kiểm kê riêng trong báo cáo
bàn giao; build thành công không tự chứng minh tương đương gameplay.

Nguồn đã đọc:

- [JavaToKotlinConverter.kt](https://github.com/JetBrains/intellij-community/blob/09f03735d4674eea9f112165fe45744a25fbdd56/plugins/kotlin/j2k/k2/src/org/jetbrains/kotlin/j2k/JavaToKotlinConverter.kt)
- [JavaToKotlinService.kt](https://github.com/JetBrains/intellij-community/blob/09f03735d4674eea9f112165fe45744a25fbdd56/plugins/kotlin/j2k/k2/src/org/jetbrains/kotlin/j2k/JavaToKotlinService.kt)
- [J2kMcpToolset.kt](https://github.com/JetBrains/intellij-community/blob/09f03735d4674eea9f112165fe45744a25fbdd56/plugins/kotlin/j2k/mcp/src/org/jetbrains/kotlin/j2k/mcp/J2kMcpToolset.kt)
