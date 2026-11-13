# Gỡ Facebook Login và Google Ads SDK

Mã game dùng backend thật, application ID `com.quanhoang.greenfarm3`. Phần tắt
dịch vụ dưới đây không bỏ kiểm tra server, không cấp tài nguyên, không tạo tài
khoản hoặc kết quả đăng nhập thành công giả.

## Bằng chứng từ APK gốc

APK gốc có SHA256 trong `docs/ORIGINAL_BYTECODE_TOOLS.json`. Trong DEX gốc,
`javax.microedition.midlet.MIDlet` gọi đúng 12 callback lifecycle vào mỗi child:

- `javax.microedition.midlet.v`: kế thừa `SocialNetwork_Facebook` (`com.gameloft.android2d.socialnetwork.s`). `onCreate(Bundle)` khởi tạo FacebookSdk, CallbackManager và tracker.
- `javax.microedition.midlet.o`: static initializer nạp `AdsManager`; `onCreate(Bundle)` gọi GLAdsV2 và AdsManager.Init. Các callback resume/pause gọi native AdsManager.

MIDlet còn gọi `AdsManager.IsFullScreenAdOnScreen()` hai lần và
`AdsManager.HandleBackKey()` một lần trong `onKeyUp`. Phải gỡ cả những lời gọi
này khi tắt lifecycle quảng cáo để phím Back không gọi native chưa khởi tạo.

Chạy Android thật còn phát hiện `UnsatisfiedLinkError` từ
`JW_PrivacyPolicyChecker` (`com.gameloft.a.b.i.bQ`) truyền tuổi sang native
`AdsManager.SetAge`. Vì vậy không đủ chỉ tắt nút và lifecycle. Audit toàn bộ APK
xác định thêm sáu lời gọi void trong SDK cần bỏ: SetAge ở privacy checker, hai
native factory/initialize trong AdsManager, tracking event trong GLAds.init và
native Pause/Resume trong child quảng cáo. Các bước lưu tuổi, điều khoản, consent
và xác thực Janus xung quanh giữ nguyên; không nạp lại thư viện quảng cáo để che lỗi.

`AdMob`, `AdMobBanner`, `AdMobInterstitial`, `AdMobNative` trong APK là các lớp
wrapper rỗng có sẵn từ bản gốc. Phần lựa chọn provider còn ở native AdsManager;
chưa có bằng chứng đủ để ánh xạ an toàn từng cờ provider. Phiên bản này tạm dừng
toàn bộ luồng quảng cáo thưởng cũ; không tự cấp phần thưởng hoặc chờ callback vô
hạn. Đồng thời bộ đóng gói loại bỏ **1.880 lớp SDK hiển thị/mediation Google Ads**
sau khi chứng minh đây là tập không có tham chiếu đi vào từ mã được giữ.

Sáu lớp `ads.identifier` được giữ theo allowlist chính xác: `AdvertisingIdClient`,
`AdvertisingIdClient$Info`, `a`, `b`, `zzb`, `zzc`. Chúng được wrapper định danh
thiết bị, Firebase measurement và Moat analytics tham chiếu; đây là API định
danh dùng chung, không phải banner/interstitial/rewarded AdMob. Native và assets
giữ nguyên từng byte. Bộ đóng gói tiếp tục bỏ **717 lớp Facebook SDK và 24 lớp
bridge chỉ phục vụ provider đó**; không đóng gói lớp Facebook giả thay thế.

## Cơ chế build

`PatchDisabledSdkLifecycle.java` áp dụng sau khi ghép source, đổi endpoint và
relocate package. Nó chỉ sửa lớp MIDlet thật, giữ nguyên Application, các lớp
SDK và thư viện native:

1. Đòi hỏi đúng 33 vị trí: 24 lifecycle, hai truy vấn boolean, một handler Back,
   sáu native propagation/factory/pause/resume trong năm owner đã review. Sai
   caller/chữ ký/kiểu lệnh hoặc thiếu vị trí làm build lỗi.
2. Thay void invoke bằng ba NOP; truy vấn boolean dùng ba NOP và const false ở
   đúng thanh ghi nhận kết quả. Số code unit không đổi; giữ nhánh, try range và
   địa chỉ debug.
3. Đọc lại DEX đã ghi, xác nhận không còn dispatch của ba owner SDK trong MIDlet.
4. Đảo ngược các vị trí sửa và so sánh canonical semantics riêng từng owner; so sánh
   tất cả lớp còn lại. Chỉ xuất DEX và report sau khi các kiểm tra đạt.
5. Audit mọi class/method trong DEX, gồm alias/subclass của AdsManager, đòi hỏi
   không còn bất kỳ invoke nào đến native AdsManager. Game adapter không giữ
   lời gọi trong nhánh false. Kiểm tra này chạy lại trên DEX cuối sau khi bỏ
   Facebook, trước khi đóng gói/ký APK; report được ràng buộc bằng hash.

`PruneGoogleAds.java` sau đó loại đúng 1.880 lớp theo namespace đã kiểm kê, trừ
allowlist sáu lớp identifier. Bộ audit kiểm tra superclass/interface, kiểu field,
chữ ký hàm/tham số, instruction reference, catch type, annotation và encoded
value lồng nhau, cùng chuỗi tên lớp dùng cho reflection. Bất kỳ tham chiếu đi vào
nào đều làm build lỗi. DEX đọc lại phải có đúng tập lớp còn lại, giữ canonical
semantics của từng lớp; ghép lại các định nghĩa đã bỏ phải khôi phục canonical
semantics đầu vào. Áp dụng lại hoặc thay đổi số lớp cần review sẽ bị từ chối.

`PruneFacebookSdk.java` kiểm tra cùng tập tham chiếu và bỏ đúng 741 lớp đã kiểm
kê. Lớp dữ liệu `com.gameloft.android2d.socialnetwork.i` vẫn giữ nguyên field,
getter/setter và trạng thái liên kết cũ. Chỉ hàm `UN()` lấy ảnh avatar từ provider
đã gỡ trả về `null`, đúng kiểu kết quả không khả dụng mà caller sẵn có xử lý.
Đây là gỡ nguồn ảnh Facebook; không thay thế logic trang trại. Trước sửa phải
khớp fingerprint canonical của toàn bộ DTO gốc. Bộ kiểm tra đảo lại riêng thân
hàm và ghép lại các lớp đã bỏ để chứng minh toàn bộ dữ liệu/hàm khác giữ nguyên.

`build_local_backend.py` kiểm tra chuỗi hash chính xác:
base → endpoint → relocation → policy SDK → bỏ Google Ads → bỏ Facebook → APK ký. Các kiểm tra provenance
cũ vẫn giữ nguyên. Build cuối vẫn là `:app:assembleDebug`, `installDebug` và
Run cấu hình `app`.

Manifest bỏ các activity Google AdMob, Facebook Login/custom tab và Facebook
Audience Network cùng metadata Facebook ApplicationId. Firebase, measurement,
Google Games/AppState và quyền mạng vẫn được giữ. Bộ kiểm tra APK đọc binary
manifest, đòi hỏi các mục Google không liên quan vẫn có và URL metadata cloud
khớp đúng biến `BACKEND_BASE_URL` duy nhất.

`ServiceAvailability` và adapter source xử lý giao diện/trạng thái không khả dụng.
Adapter Facebook không còn import, lời gọi hoặc callback SDK provider.
Dữ liệu liên kết Facebook đã lưu không bị xóa. Backend dùng phiên riêng để bảo
vệ save; không coi việc tắt Facebook là xác thực cho tài khoản cũ.

## Kiểm chứng

`tests/test_disabled_sdk_lifecycle.py` kiểm tra DEX gốc thật, vị trí bị thiếu/trùng,
caller sai, kiểu kết quả sai, ghi đè đầu vào và áp dụng patch hai lần. Các test
`test_google_ads_pruning.py` kiểm tra SDK gốc và 20 tình huống tham chiếu/metadata.
`test_facebook_sdk_pruning.py` kiểm tra SDK thật, caller còn tham chiếu, fingerprint
DTO bị sửa, thiếu lớp, áp dụng hai lần và bảo toàn field/getter. Fixture SDK không
được cài; APK đầy đủ biên dịch source được kiểm tra riêng trước bàn giao.
Packager kiểm tra hash/revision nối tiếp; verifier từ chối ID/lớp/nhãn offline
test, các lớp presentation Google Ads, metadata/component Facebook/AdMob còn
sót và việc xóa nhầm Google hoặc shared identifier.

Các kiểm tra này chứng minh phạm vi patch/đóng gói. Kết quả khởi động, kết nối
backend và chơi trên Android được ghi trong báo cáo runtime riêng.
