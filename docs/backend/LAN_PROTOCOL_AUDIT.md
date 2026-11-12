# LAN endpoint và ranh giới giao thức được kiểm tra

Đối chiếu trên mã người dùng push ở `0c22c29` và DEX gốc 4.4.2. Đây là bằng
chứng client và kiểm kê dữ liệu, không phải lời khẳng định dịch vụ cũ còn hoạt
động. Các số dòng smali là dòng vật lý dưới `/workspace/tooling/original-smali`.
Xem thêm [hợp đồng Accept](ACCEPT_PROTOCOL_EVIDENCE.md),
[profile lúc khởi động](CONFIG_STARTUP_EVIDENCE.md),
[kiểm kê URL](NETWORK_INVENTORY.md) và [save game](GAME_STATE_BOUNDARY.md).

## Các lỗi cấu hình đã xác nhận trong phiên bản trước

- `backend/src/main.ts:3` mặc định chỉ bind `127.0.0.1`, không nhận kết nối vào
  IPv4 LAN. Nó có thể nhận kết nối qua alias host của Android Emulator, nhưng
  không tương đương với một listener LAN.
- `main.ts:10` mặc định quảng bá `http://127.0.0.1:<PORT>`. Vì discovery trả URL
  tuyệt đối, nếu chạy `npm start` mà không đặt `PUBLIC_BASE_URL`, request đầu
  có thể vào server qua `10.0.2.2` còn request sau quay về loopback của guest.
- `app/build.gradle` trước đây có mặc định `localBackendUrl=10.0.2.2`; công cụ
  patch trước đây từ chối HTTP tới IPv4 LAN. Không có một nguồn endpoint duy nhất.
- Patch cũ chỉ thay `com/gameloft/a/b/a.aL`, trong khi lớp game được biên dịch
  `S800x480/s.aL` còn EVE gốc. Hai bootstrap này là hai client riêng biệt.

Đây là các lỗi có thể chứng minh từ code. Chúng chưa xác định được chính xác
đường đi thất bại trên máy Windows của người dùng khi chưa đo kết nối tại máy đó.

Kiểm tra Manifest của chính APK cuối trước lần sửa này bằng `aapt2 dump xmltree`
đã thấy `INTERNET`, target SDK 28, `debuggable=true`, `usesCleartextTraffic=true`.
Vì vậy chưa có bằng chứng HTTP bị Manifest chặn là nguyên nhân trong APK đó.
Kiểm tra Manifest nguồn đơn thuần không thay thế kiểm tra APK cuối sau ghép DEX.

## Hai luồng client và định dạng HTTP

Wrapper khởi động qua `android/wrapper/au.Qh` → worker `ay` → SDK
`com/gameloft/a/b/a.Wm/Wn/Wo`. Accept sử dụng SDK này. Gameplay sử dụng client
riêng `S800x480/s`: `aK`/`iT` lấy datacenter, `aS`/`iU` lấy URL map, `cs`
ghép service base, route và tham số. Cả hai cần lấy bootstrap từ cùng cấu hình.

| Request có bằng chứng | Method và dữ liệu | Response được client đọc |
| --- | --- | --- |
| `/config/{client_id}/datacenters` | GET, không token/body | JSON array; phần tử đầu cần `name` và `_datacenter_id` không rỗng |
| `/config/{client_id}/datacenters/{name}/urls` | GET, không token/body | JSON object chứa các URL service; `pandora` là gốc locator |
| `/locate/auth`, `/locate/storage` | GET | **Plain text origin**, không JSON quotes, không newline, không slash cuối |
| `/authorize` | POST form: `client_id`, `username`, `password`, `scope`, `device_id` | SDK đọc `access_token`; core đọc cả `access_token` và `refresh_token` |
| `/users/me/compliance` | POST form: `access_token`, `country`, `age` | HTTP 200, JSON object gồm các policy object có `compliant` |
| `/profiles/me/myprofile` | GET query `access_token` | HTTP 200, JSON profile; startup so `credential` với toàn bộ username đã xác thực |

POST dùng `application/x-www-form-urlencoded`, không chuyển sang JSON body.
SDK dùng `URLEncoder` UTF-8 cho giá trị; core dùng encoder tương thích riêng
`s.bc`. Token nằm trong form hoặc query, không có app-defined Bearer hoặc Cookie
header trên các đường đi đã kiểm tra. Không suy ra việc không có cookie do hệ
thống tự thêm chỉ từ các class này. SDK chỉ đọc body cho 200/202; profile và
compliance có điều kiện thành công chặt hơn nên response thành công dùng 200.

SDK URL map đọc mười key: `pandora`, `gllive-ope`, `tapjoy`, `offline_items`,
`game_portal`, `status`, `crm_iap`, `ads_agency`, `marketing_site`,
`online_connectivity`. Core đọc tám key đầu. Các URL tuyệt đối trả về phải cùng
origin cấu hình; service chưa làm trả lỗi rõ, không trỏ lại localhost hoặc dịch
vụ Gameloft. `s.g`/SDK `h.g` giữ nguyên `http://` và không sửa slash, do đó
locator phải trả đúng origin thay vì chuỗi tùy ý.

### Core authorize và refresh

Các vị trí Java để định hướng: `s.java:370–403` tạo request `t.Nn`;
`1334–1337` khai báo bảng field; `1800–1918` ghép request, đặc biệt thay
`clientId` thành `client_id`. Các optional field là `access_token_only`,
`for_username`, `for_credential_type`, `grant_type`, `redirect_uri`; ở luồng
anonymous thông thường chúng null nên bị bỏ qua. Không vì có tên trong bảng
mà suy ra đã hỗ trợ link tài khoản hoặc OAuth redirect.

`s.cG` đọc `access_token` rồi `refresh_token` (`s.java:2284–2308`, original
smali `s:5586–5601`). Response M0 cũ chỉ có access token làm core gặp exception
parse đã được catch; token đầu có thể vẫn được gán. Điều đó không chứng minh
crash trực tiếp nhưng làm luồng refresh sau này thiếu dữ liệu.

Refresh có bằng chứng tại `s.o(String,String)` (`s.java:411–414`): POST
`/authorize` với `client_id`, `grant_type=refresh_token`, `refresh_token` và
`scope` nếu không null. Token refresh phải được lưu/kiểm tra thật, không tạo
response thành công cho một token không hợp lệ.

Feature flags trong `w.java:169–204` và `s.iM:302–352` tạo scope của core:
`leaderboard social storage auth message config`. M0 cũ chỉ chấp nhận sáu scope
khác, thiếu `leaderboard`/`social`; core có thể bị 400 dù SDK Accept thành công.
Chấp nhận scope declaration không có nghĩa đã triển khai chức năng tương ứng.

## Connect Facebook không phải đăng nhập độc lập của backend

`bt.nT` chuyển tới `android2d/socialnetwork/l.nT`, rồi `socialnetwork/s.nT/d`
gọi `LoginManager.logInWithReadPermissions`. Sau khi provider báo đăng nhập,
game đặt `s.aV("facebook")`, ID và password provider ở `ci.qJ`
(`ci.java:63118–63140`). Nhánh anonymous gần đó đặt type `anonymous` và password
qua hàm gốc `s.aR`.

Đổi endpoint hoặc đưa session vào MySQL không thay thế Facebook SDK thành hệ
thống tài khoản mới. Không chấp nhận username `facebook:*` tùy ý hoặc tạo trạng
thái Connect thành công giả để vượt hạn chế này. Luồng anonymous và profile của
dịch vụ nghiên cứu được phân biệt với đăng nhập Facebook chưa triển khai.

## Portal, tracking và phạm vi URL

`ci.aWU` không có fallback portal: khởi tạo null, nhận `game_portal` từ URL map
trong callback `ci.a(...)`, và về null nếu parse lỗi (`ci.java:60524,
63586–63593`). Nó xây các request online quest như `events.php`, `anoname.php`,
`post_score.php`, `reward.php`, `leaderboard.php` bằng đường dẫn tương đối.

Literal `https://game-portal-beta.gameloft.com/android/1557/public/` nằm trong
**`ci.rg()`**, không phải initializer của `aWU` (original `ci.smali:372160`).
Các caller dùng nó cho Facebook OpenGraph `achievement.php`, `levelup.php`,
`complete.php` (`ci.java:31747,56282,69685–69687`). Một patch riêng có whitelist
có thể chuyển gốc public portal sang endpoint unsupported thuộc backend mới;
không được gọi đó là đã triển khai Facebook hoặc phần thưởng online.

Ngoài ra inventory ghi EVE trong `G/bi.MW` (tracking), `PushNotification/aa`
(push), `vgold.gameloft.com/locate/auth` trong `G/bx.NX` (tracking). Chúng là các
giao thức phụ riêng, không phải bằng chứng rằng mọi URL có chữ `auth` đều là
đăng nhập game. Ads, store, support và Facebook còn có URL bên ngoài; không
rewrite mù toàn bộ string pool thành một API đăng nhập.

## Dữ liệu cần chuyển MySQL và dữ liệu còn trên thiết bị

Backend M0 trước đây chỉ có `Map sessions`: sid, issued/expires time, client ID,
credential. HMAC secret mặc định sinh mới mỗi lần chạy; token và session mất
khi restart. Profile trả credential từ session; compliance trả kết quả rule
nghiên cứu. Không có database, JSON snapshot hoặc farm state backend để âm
thầm migrate. Session, account/profile metadata và các ghi nhận mới có backend
quản lý phải được lưu MySQL thật và lỗi DB phải trả thất bại.

Farm hiện lưu RMS trên Android: `RMSHeader`, `save_gameplay`, `save_farm0`,
`online_gameplay`; `ci.av` → `ro/rt/ry` → `GLLib` → `javax.microedition.b.b`.
Cloud upload là nhánh riêng, dùng TOC/key và bytes `game_dedicated_slot.dat`.
Backend M0 chưa triển khai các route/codec cloud save đó. Không tạo một REST
`/farm` mới rồi tuyên bố game gốc đã đọc/ghi farm trong MySQL. Cần kiểm thử save
ở thời điểm game cho phép; tutorial nhìn thấy chưa chứng minh save bền vững.

## Lỗi mạng và callback

Transport SDK `a/b/e` ghi nhận lỗi HTTP/exception; `a/b/b.run` không đặt ready
khi bootstrap hoặc authorize thất bại. SDK không đặt connect/read timeout và
nhiều caller poll không có deadline. Profile thất bại có thể để Hestia đợi cờ
`adf` mãi; xem bằng chứng riêng. App còn sống không đồng nghĩa nút retry đã hoạt
động đúng trong mọi tình huống.

Đã sửa một nguyên nhân NPE cụ thể trong callback **`ci.a(...)`**, không phải
`qJ`: exception có message null bị dereference bởi hai `startsWith`
(`ci.java` trước sửa 63709; original smali 419049–419111). Thêm local message và
null guard, giữ các nhánh thông báo và thứ tự timeout. Test
`tests/test_ci_network_failure.py` biên dịch predicate thực tế, kiểm tra 13 loại
lỗi/message và xác nhận cả hai mutation bỏ null guard đều tái hiện NPE. Đây
không phải blanket try/catch hoặc giả báo đăng nhập thành công.
