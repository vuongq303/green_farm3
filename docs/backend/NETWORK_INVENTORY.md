# Original APK: static network inventory

`eve.gameloft.com` is a bootstrap/configuration dependency. Original bytecode
also contains service discovery, authentication/account operations, storage
operations, advertising, tracking, push and support links. Restoring the EVE
response alone would not establish that account login, cloud operations or farm
gameplay can complete. This report does not define replacement responses or a
server protocol.

## Inputs and reproduction

- Original APK: `green-farm-3.apk`, SHA-256
  `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.
- Original `classes.dex`: SHA-256
  `3901b6678b4523947273914b30fe94a750e149f89e0a7a1fa3932261d7539ec4`.
- Original baksmali evidence: `/workspace/tooling/original-smali`, previously
  recovered as described in [ORIGINAL_APK_RECOVERY.md](../ORIGINAL_APK_RECOVERY.md).
  Line numbers below refer to these smali files, not repaired Java exports.
- [Machine-readable results](network-inventory.json) record original DEX string
  indices/offsets, APK entry offsets, method/line references and hashes of
  referenced smali files. They also hash all 49 assets and 10 native libraries.

Run from the repository root using Python 3 and its standard library:

```sh
python3 tools/inventory_network.py \
  --smali /workspace/tooling/original-smali \
  > /tmp/network-inventory.json
cmp docs/backend/network-inventory.json /tmp/network-inventory.json
```

The [scanner](../../tools/inventory_network.py) checks both fixed checksums and
fails on a different APK. Omitting `--smali` still scans the APK; method evidence
and route tables then remain empty. Inputs are read only. No dependency install,
device interaction, build, DNS lookup or HTTP request is part of this command.

The recorded scan found **93 sanitized endpoint/domain literals and 44 distinct
host strings** across the original DEX and payload. Those totals include SDKs,
templates, documentation/example links, XML namespaces and asset support text;
they are not counts of live services or observed requests. There are also
123 declarative Federation request-table rows. Query values, fragments and long
path identifiers are redacted. Third-party endpoint references are capped at
three while retaining total counts; Gameloft references are retained in full.

## Game-linked services and construction sites

Paths in this section are relative to the original smali root.
`G/` abbreviates `com/gameloft/android/ANMP/GloftGF2F/`.

| Literal or constructed destination | Role established by static evidence | Original reference |
| --- | --- | --- |
| `https://eve.gameloft.com/config/{client}/datacenters` | Bootstrap datacenter lookup. `{client}` comes from `s.gX()`. | `G/S800x480/s.smali`: `aL(String)` at 2388/2442; `iT()` at 10741/10769. |
| `https://eve.gameloft.com/config/{client}/datacenters/{dc}/urls` | Bootstrap list of service locations for the selected datacenter. The caller supplies the datacenter. | `G/S800x480/s.smali`: `aL(String)` at 2470/2480, `iU()` at 10836; wrapper counterpart `com/gameloft/a/b/a.smali`: `aL(String)` at 2369. |
| `https://eve.gameloft.com/config/…` | Separate tracking configuration lookup; response parser uses `etsv2` and `gdid`. | `G/bi.smali`: `MW()` URL construction at 129–152, `MX()` response parsing at 259/299. This is separate from the AcceptSDK gate. |
| `https://eve.gameloft.com:443/config/…` | Push configuration lookup. | `G/PushNotification/aa.smali`: literals at 658/877. |
| `{discovered-base}/locate/{service}` | Federation service discovery. `alert` adds parameter names `client_id` and `credential`; values are omitted here. | `G/S800x480/s.smali`: private `a(String,am)` at 2042, `/locate/` at 2094; `b(String,int,int)` at 3892 reads the reply. |
| `{service-base}` plus route and request parameters | Auth, storage and other requests use the `Mj` service-base array and the declarative `MI`/`MJ` route/parameter tables. The literal scan cannot name the returned hosts. | `G/S800x480/s.smali`: `c(int,int,boolean)` writes discovered bases at 4537–4548; `cs(int)` reads them at 8492 and builds paths/parameters; `am.run()` opens `java.net.URL` connections at 1305/1329/1626. |
| `https://vgold.gameloft.com/locate/auth` | Lookup in the tracking component. `bx` identifies itself with `TrackingManager` log text; `m` uses `GLOTv3` and handles server timestamp/redirect data. The word `auth` alone does not make this the game's account-login host. | `G/bx.smali`: `NX()` at 2228, base/path constants at 2299/2301, `m.c(String,String)` call at 2303; `G/m.smali`: `GLOTv3/` at 71, HTTP worker `run()`. |
| `state-gold.gameloft.com`, default port `8080` | Connection-status/state tracking configuration. `ci.O(String,String)` passes this host/port as reporting fields through `wrapper.au.a(…)` into `a/b/a.a(…)`. A `statetrackers` config string can replace them. The report destination is built separately in that wrapper method; the host/port literals are not proof of a direct socket connection to state-gold. | `G/S800x480/ci.smali`: default at 16699/16703; reporting call at 151689–151693; config key at 418137 and replacement at 418171/418179. `com/gameloft/android/wrapper/au.smali`: 9764/9783. `com/gameloft/a/b/a.smali`: 1697, `connectionstatus` at 1795, field names `domain`/`port` at 1830/1854. |
| `https://game-portal-beta.gameloft.com/android/1557/public/` | Default portal URL for online quest/content flow; configuration can supply a portal URL. The `beta` hostname text does not establish environment availability. | `G/S800x480/ci.smali`: literal at 372164 and `Portal URL` config log at 418115. |
| `http://interstatic01.gameloft.com/beta-games/android/1557/icon_GE3_200.png` | Static image associated with game promotion/store flow. | `G/S800x480/ci.smali`: 29065, 107169, 414237. |

The service-name array in `s.<clinit>()` contains `message`, `asset`, `auth`,
`leaderboard`, `social`, `storage`, `lobby`, `matchmaker`, `gllive`, `config`,
`chat` and `alert` (162–234). This documents available client machinery, not
proof that this game enables or invokes every service. Feature flags and
request state affect which routes run. `s.g(String,boolean)` at 9934 preserves
an already supplied HTTP(S) prefix and prepends `https://` otherwise; a literal
scheme cannot be assigned to every remotely supplied service base.

## Authentication, account and cloud request evidence

`s.ja()` initializes request metadata, not response examples. The JSON retains
the request-field names, string constants and source lines. Representative rows:

| Request field | Path constants in table order | Parameter names relevant to its role | Smali line |
| --- | --- | --- | --- |
| `t.Nm`, `t.Nn` | `authorize` | `clientId`, `username`, `password`, `scope`, `device_id`, `access_token_only`; `Nn` also has `for_username`, `for_credential_type`, `grant_type`, `redirect_uri` | 13758, 13832 |
| `t.Nt` | `authorize` | `clientId`, `scope`, `grant_type`, `refresh_token` | 14248 |
| `t.Nu` | `encrypt_token` | `access_token`, `nonce` | 14310 |
| `t.Nv` | `authenticate` | `password` | 14364 |
| `t.Np`, `t.Nq` | `users`, `me`, `credentials` (plus `unlink` in `Nq`) | `access_token`; `Np` also has `credential`, `password`, `if_exists` | 14002, 14072 |
| `t.Os`, `t.Ot` | `data` | `access_token`; `Ot` also has `data`, `visibility` | 15399, 15459 |
| `t.Ou` | `data`, `me`, `delete` | `access_token` | 15335 |
| `t.Ov`, `t.Ow` | `profiles`, `myprofile` | `access_token`; set row has `object`, `visibility`, `operation`, read row has `include_fields` | 15527, 15603 |

`clientId`, `myprofile` and null table slots can be special/dynamic substitutions;
the rows above intentionally do not invent complete REST paths. `s.cs(int)`
assembles the service base, path components and parameters at runtime.
`s.cG(int)` parses response keys `access_token` and `refresh_token` and stores
them in `s.Ma` and `s.Mc` (5586–5601). The HTTP worker has GET/POST/DELETE
branches (`G/S800x480/am.smali`: 1361, 1374, 1713); assigning a verb to an
individual route would require following its metadata and branch conditions.

These are account/token and cloud-data capabilities in the shipped client.
They do not establish a valid user, server schema, current account policy,
successful save conflict handling, or server authority over all farm state.
No credential values, embedded auth constants or token contents are reproduced.

## Ads, analytics, IAP, social and native separation

| Group | Representative evidence | Interpretation and limit |
| --- | --- | --- |
| Gameloft ads/cross promotion | `https://578756.gameloft.com/ads/adserver/index.php` in `com/gameloft/glads/GLAds.smali:78`; `201205igp.gameloft.com/redir/…` in wrapper `ar`, splash, IGP and core `ci` | Ad/cross-promotion configuration and click/view/browser templates. Parameter names include device/ad identifiers; values are redacted. These are not farm-save endpoints. |
| Third-party ads/measurement | Google ads/DoubleClick, Unity Ads, Moat, Integral Ad Science hosts | SDK URL literals and references are recorded separately in JSON. SDK inclusion does not prove an ad or measurement request executes. |
| Analytics | `app-measurement.com`, `ssl.google-analytics.com`, `www.google-analytics.com`; Gameloft GLOTv3 discovery above | Keep SDK telemetry and Gameloft tracking separate from account/cloud role claims. |
| IAP/store/browser | Google Play, Amazon, `market.android.com`, carrier IAP profiles | `assets/IAP_profiles` includes AT&T US Tier 2/3 TEST URL records for `http://wapshop.gameloft.com/php5/us/attusa_test/javafreemium/` at entry offsets `0x540` and `0x61b`. Presence does not establish a selected payment channel or a successful purchase. Parameters are withheld. |
| Social | `graph.facebook.com` in `com/gameloft/android2d/socialnetwork/s.smali:3700`; Facebook SDK graph templates; Google auth scope links | Social provider machinery, distinct from Gameloft service discovery. Scope/namespace/documentation URLs are not automatically request destinations. |
| Legal/support | Gameloft support, banned-account and EULA URLs in `G/as.smali`, `G/S800x480/ci.smali` and language assets | User-facing support/legal links. A `bannedaccount` path string does not prove a ban. Binary language-string boundaries are unresolved, so automatic asset inventory reports domains only. |
| Native | Ten libraries: `libAdsManager.so` and `libnativeU.so` for five ABIs | Native literal scan found Play/Amazon URLs in AdsManager and an ARM64 compiler-diagnostic URL; no Gameloft backend-host literal. This is not a native callgraph or proof that native code has no network use. |

Representative uncompressed offsets in `lib/x86/libAdsManager.so` are
`0x10a13a`/`0x10a151` for Play HTTP/HTTPS and `0x10a171`/`0x10a187` for Amazon.
It references `GLURLConnection`; its Java bridge exposes `SetURL(String)`
(`com/gameloft/glads/GLURLConnection.smali:448`). Native callers may therefore
supply URLs absent from this literal list. `assets/InitialFeed.bin` is a
54,124-byte nonplain payload, SHA-256
`3d677c104f3ec8d38ff03d08eb3f7ea0a6514779d72d1c698172049edd734b74`;
no literal URL/domain was found and decoding was not attempted.

## What remains unknown

The inventory establishes original string-pool membership and named smali
construction/reference sites. The scanner does not reassemble smali to verify
every opcode against DEX, and a matching string does not prove a method ran.
It does not resolve remote configuration, redirects, encrypted/binary asset
configuration, runtime substitutions, native-generated URLs or returned service
hosts. Scheme-less domains outside the Gameloft family are not exhaustively
extracted. The existing EVE DNS failure describes the observed gate attempt;
this offline report makes no additional liveness or reachability claim.

Further work on a compatible backend would first need authorized observations
or documented fixtures of the actual request/response contracts, selected
service configuration, account transition and cloud-data behavior. This report
supplies bounded pointers for that work and no fabricated success state.
