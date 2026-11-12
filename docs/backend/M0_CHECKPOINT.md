# M0 — backend local, Accept và mở đầu nông trại

Ngày kiểm thử: 2026-11-12. Baseline: `ce19d54`; checkpoint phân tích/kiến trúc:
`f193242`. [Kiến trúc và API](ARCHITECTURE.md) được trình bày trước khi đổi APK.
Người dùng chọn local + emulator. M0 là một mốc nghiên cứu, chưa phải phiên bản
chơi lâu dài hoặc đã loại hết phụ thuộc Gameloft.

## Kết quả chạy thực tế

Trên AOSP Android9/API28 x86, APK thử đã hoàn tất discovery, authorize và profile
với server local; nhập30, chọn giới tính và Accept nhận privacy HTTP200. Game
chuyển khỏi age gate sang màn hình Connect / Not now (state47, JDI đọc `aTO=30`,
`aUb=true`). Chọn Not now, rồi No ở lời mời Facebook, đi qua thư mở đầu và tới
màn hình Cindy Lou “Welcome!” với cảnh nông trại phía sau.

![Nông trại và tutorial đã hiện trên emulator](screenshots/farm-welcome.png)

Đây là bằng chứng đã mở được farm tutorial; chưa phải kiểm thử trồng/thu hoạch,
di chuyển, nhiệm vụ, kho hoặc lưu/tải tiến trình gameplay. Emulator không bật
âm thanh; chưa kiểm tra hiệu năng thiết bị thật hay Android mới.

## Đã triển khai và build

- Backend [Node24/TypeScript](../../backend/README.md), không dependency npm hoặc
  forwarding upstream. Phiên token ký HMAC riêng, RAM-only, policy nghiên cứu
  có tên và giới hạn tuổi rõ ràng; không dùng `{}` để bỏ qua privacy.
- Bảy API tương thích đã được client Android gọi thành công: hai route config,
  locate/auth, authorize, locate/storage, profiles/me/myprofile, compliance.
  `/healthz` là API vận hành mới. PostgreSQL chưa triển khai trong mốc này.
- APK riêng chỉ đổi một instruction URL của Janus; reverse-patch xác nhận
  semantics class đó khớp trước sửa, các class khác giữ canonical DEX. 108
  nguồn Java lõi không đổi. 49 assets và10 native giữ nguyên.
- Integrity guard không đổi. Chỉ metadata REVISION-2408 được tính lại cho DEX
  output7.880.880 bytes → revision8.021.516; ký/zipalign/verify đạt.
- `:core-source:assembleLocalBackendDebug` đạt BUILD SUCCESSFUL trong48s,
  51 task được tái dùng. 33 test mới đạt, không skip:18 HTTP thật,7 DEX patch,
  8 packaging. Tái dùng kết quả81 test của baseline; không chạy lại phần không đổi.

APK: `core-source/build/outputs/apk/backend-local/debug/app-local-backend-debug.apk`.
SHA-256: `0555a6845a5e9f35d0c80c4ade09408e66ac486e74aa5ec7229ba0e15dfdd0d9`.
Đây là bản debug cho backend local; bản `app-core-source-debug.apk` vẫn giữ
đường đối chứng cũ. Dữ liệu emulator trước M0 đã backup vào `.local/backend-m0/`,
không commit backup, khóa ký, token, cache hay APK output.

## Phụ thuộc phát hiện khi chạy

Bản server5 API đầu tiên đã authorize thành công nhưng kẹt splash: Hestia chờ
profile-success vô thời hạn khi route storage chưa hỗ trợ. Sau khi đối chiếu
smali mới thêm hai route profile/storage. Profile lấy `credential` từ phiên
anonymous đã xác thực của server mới, không lấy các giá trị mẫu như tiền/level
trong template của SDK. [Bằng chứng bổ sung](CONFIG_STARTUP_EVIDENCE.md).

Device/config chưa hỗ trợ trả501; các phản hồi này không được gọi là thành công.
Log hiện vẫn có request DNS tới EVE/vgold từ client legacy khác. M0 mới chuyển
Janus sang local, chưa đạt mục tiêu không có mọi request Gameloft. Không nới
policy mạng để thử upstream; rule EVE cũ đã được gỡ khỏi draft. [Môi trường](ENVIRONMENT_M0.md).

## Chạy lại

```sh
cd /workspace/green_farm3/backend
npm test
PUBLIC_BASE_URL=http://10.0.2.2:8787 npm start
```

Ở terminal đã kích hoạt JDK/SDK theo hướng dẫn môi trường:

```sh
cd /workspace/green_farm3
./gradlew --no-daemon :core-source:assembleLocalBackendDebug
```

Giữ server chạy trong khi thử APK. Token/phiên bị mất khi server restart; cần
khởi động lại app để handshake lại. Không xóa dữ liệu game để xử lý session.
Không dùng anonymous derivation công khai này làm xác thực production.

## Bằng chứng và bước kế tiếp

[Kết quả JSON](m0-runtime.json), [proof một endpoint](endpoint-patch.json),
[log build](validation/build-m0.log), [HTTP tests](validation/backend-http-tests.log),
[DEX tests](validation/endpoint-dex-tests.log), [packaging tests](validation/packaging-tests.log),
[log route local đã bỏ query/token](validation/server-local.log).

M1 cần kiểm thử tutorial và gameplay local, chốt điểm save hợp lệ theo logic gốc,
rồi force-stop/relaunch kiểm tra tiến trình. Sau đó mới triển khai PostgreSQL,
tài khoản riêng và snapshot có revision/checksum. Các API cloud, profile đầy đủ,
hàng xóm, kinh tế/IAP và migration phải tiếp tục dựa trên bytecode và phép thử,
không suy ra từ URL inventory hoặc một lần vào được màn hình farm.
