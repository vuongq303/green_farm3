# Cấu hình cloud cho backend M0

Cấu hình nháp đã được cập nhật và đọc lại xác nhận ở **revision3**:

- `start_skill` hướng dẫn Node24, backend loopback và APK local riêng; giữ các
  lệnh build core/đối chứng, khởi động emulator và bảo toàn dữ liệu đã có.
- Đã xóa duy nhất custom rule `eve.gameloft.com` do workflow cũ thêm. Network
  vẫn `restricted`, giữ preset `package_managers`; không còn custom rule.
- Giữ nguyên `install_script`, repository, secrets và runtime requirements.
  Node đã có sẵn, phiên bản kiểm tra `v24.22.1`; không đổi installer.

Lệnh khởi động backend trong một phiên terminal bền vững:

```bash
cd /workspace/green_farm3/backend
PUBLIC_BASE_URL=http://10.0.2.2:8787 npm start
```

Kiểm tra từ host bằng `curl --fail http://127.0.0.1:8787/healthz` và yêu cầu
HTTP200 với `status=ok`, `mode=local-research`. Backend dùng Node standard library,
không cần `npm install`. `10.0.2.2` là địa chỉ Android Emulator dùng để gọi loopback
host; không dùng `127.0.0.1` làm origin trả cho app trong emulator.

Sau khi kích hoạt JDK/SDK như hướng dẫn core, build artifact riêng:

```bash
cd /workspace/green_farm3
./gradlew --no-daemon :core-source:assembleLocalBackendDebug
```

APK nằm tại
`core-source/build/outputs/apk/backend-local/debug/app-local-backend-debug.apk`.
Target core/đối chứng thông thường vẫn giữ vai trò so sánh với bản gốc. Trước khi
thay APK khác chữ ký phải giữ dữ liệu app/AVD; không uninstall hoặc wipe-data để
bỏ qua bước bảo toàn save.

M0 chỉ lưu session trong RAM và mất session khi restart. Chưa cài hoặc cấu hình
PostgreSQL; chưa có persistent account/farm, cloud save hoặc production auth.
Các network client legacy khác vẫn còn, nên chưa thể khẳng định toàn bộ game
độc lập khỏi Gameloft. Xem [ARCHITECTURE.md](ARCHITECTURE.md) và
[backend/README.md](../../backend/README.md) để biết phạm vi thực tế.

Kết quả tool là `status=saved`, `requires_publish=true`: đã lưu nháp, chưa xác
nhận publish hoặc khôi phục một máy mới. Người dùng review/save trong environment
settings rồi Publish khi giao diện cho phép. Runtime vẫn ở spec/observed
revision1, `observations_current=true`, policy `enforced`, không có custom host.
Việc cập nhật cấu hình này không chạy server, build APK hoặc can thiệp emulator;
kết quả chạy backend/Android được kiểm chứng riêng. Hướng dẫn revision2 trong
[ENVIRONMENT_SETUP.md](../ENVIRONMENT_SETUP.md) là lịch sử trước khi đổi sang M0;
`start_skill` revision3 là hướng dẫn workflow hiện tại.
