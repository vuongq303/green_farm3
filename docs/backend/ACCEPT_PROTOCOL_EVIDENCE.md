# Janus / Accept protocol evidence from the original APK

This is a bytecode-derived client contract, not a description of a live service, a server implementation, or an authorization/policy design. No remote services were contacted. No app code, APK, or server was changed. The repository checkpoint is `ce19d54ea6ef67504a7fe9a50daf7863cf31ef74`. The original `green-farm-3.apk` SHA-256 is `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.

Evidence below uses **physical file line numbers** under `/workspace/tooling/original-smali/`, not the `.line` metadata inside smali. Names `a`, `e`, `h`, `i`, etc. mean `Lcom/gameloft/a/b/<name>;` unless another package is specified. Source markers identify `a/b/c` as `JW_Federation.java`, `e` as `JW_HTTP.java`, and `i` as `JW_PrivacyPolicyChecker.java`. Exported Java was used for orientation only; the smali control flow is authoritative. In particular, several Java exports incorrectly flatten polling loops and failure returns.

The companion `accept-contract.json` records the wire facts in machine-readable form. Native JSON types below describe the natural representation consumed by the named accessor. `getString` and `opt*` calls rely on Android JSON coercion; they are not strict JSON Schema type checks.

## Minimum library chain and readiness

1. The wrapper initializes the client identity, generated anonymous credential, password, device identifier, and callback in `Lcom/gameloft/android/wrapper/au;->Qh()V` (`au.smali:7808-7968`). It starts the datacenter-list worker `ay.run()V`, which calls `a.Wm()Ljava/lang/String;` (`ay.smali:22-29`). The wrapper's exact identity source mapping is at `au:7841-7912`: `GAME-SPACE`, `GAME-PRODUCT-ID`, `GAME-GGI`, `MIDlet-Version`, `GAME-PLATFORM`, `GAME-SHOP`.
2. `a.Wm()Ljava/lang/String;` GETs the datacenter list and emits callback type `0` (`a.smali:193-329`). The wrapper callback `Lcom/gameloft/android/wrapper/ax;->a(ILjava/lang/String;ILjava/lang/String;Ljava/lang/Exception;Ljava/lang/String;)V` parses the first list element, requires nonempty `name` and `_datacenter_id`, stores both, then calls `a.jn()V` (`ax.smali:57-118`).
3. `a.jn()V` creates a scratch HTTP object and starts `b` (`a:4883-4904`). `b.run()V` obtains the datacenter URL map through `a.Wn()Ljava/lang/String;`; only a nonnull, nonempty returned string proceeds to `a.Wo()Z` (`b.smali:19-53`). `Wo` locates the auth service and authorizes.
4. If `Wo()` returns true, `b.run` sets `a.Ne=true` through `a.aq(Z)Z` and emits callback type `3` with synthetic HTTP code `200` and null body (`b:55-82`; setter `a:2683-2691`). `a.fZ()Z` simply returns `Ne` (`a:3256-3264`). This is the strict library readiness flag checked by the privacy request. Device registration and game config requests are **not** on `b.run`'s readiness path.
5. `i.a(ILjava/lang/String;Lcom/gameloft/a/b/k;)Lcom/gameloft/a/b/l;` requires `a.fZ()==true` and no existing callback, stores pending age/country/callback, writes the cache, then calls `a.d(country,age,"lib_janus_privacy_policy")` (`i.smali:1085-1166`). `a.d` launches the HTTP worker and **blocks the caller** in a 100-ms polling loop until a response or transport failure (`a:3084-3141`). On its normal response branch it invokes `i.b(g)` before returning (`a:3185-3189`). Thus the HTTP worker is asynchronous, but `i.a` itself is not a fire-and-forget request.
6. For HTTP `200`, `i.b(g)` parses privacy and invokes `k.a(l,m)`; on success `l.id=0` and `m` contains the resulting age, country and raw response (`i:1168-1193`, `862-1071`; interface `k.smali:7`; callback dispatcher `i:783-805`). The surrounding game's Accept/offline/loading state machine is outside this document.

Readiness is weaker than successful authentication: `Wo()` catches an `access_token` extraction exception and still returns true once a transport-success body exists (`a:1027-1089`). A useful authenticated session therefore requires a parseable token response in addition to `Ne=true`.

## Shared request and transport rules

| Fact | Original bytecode evidence |
| --- | --- |
| Method id `0` is GET; `1` is POST. POST retains the parameter string as body; other methods append nonempty parameters as a query. | `e.a(Ljava/lang/String;Ljava/lang/String;IZ)V`, `e.smali:679-834`; `e.run()V`, `e:1404-1467,1628-1652`. |
| Generated parameters are `name=value` joined by `&`. Values use `URLEncoder.encode(value,"UTF-8")`; names are literal. Call sites skip null values. | `h.a(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Z)Ljava/lang/String;`, `h.smali:264-417`; `h.bc(Ljava/lang/String;)Ljava/lang/String;`, `h:3277-3306`; e.g. authorization loop `a:874-960`. |
| POST Content-Type is `application/x-www-form-urlencoded`, without a charset parameter. Body writes use `String.getBytes()` and explicit Content-Length uses Java string length. | `e.<init>()V`, `e:115-168`; `e.run()V`, `e:1654-1736`. Generated form values are percent-encoded/ASCII; do not infer another content format. |
| An explicit User-Agent is added only if `e.userAgent` is nonnull; caller header map entries are forwarded. No app-defined Authorization or Cookie header occurs in this transport. | `e.run()V`, `e:1470-1537,1654-1736`. Full-class negative check found no assignment to `e.userAgent` in original smali. Platform-generated headers/cookies remain unknown. |
| `h.g(value,true)` preserves any lowercase `http` prefix, including `http://`; otherwise it prefixes `https://`. It does not trim, validate, remove quotes, or strip trailing slashes. | `h.g(Ljava/lang/String;Z)Ljava/lang/String;`, `h:3534-3608`. This is not HTTPS enforcement. |
| Only HTTP `200` and `202` proceed to response-body reads. Other statuses, including `201`, `204`, `304`, set `BT=true` and do not read an error body. | `e.run()V`, `e:1883-1917`; default redirect flag `bwR=true` at `e:130`; `Wt()Z`, `e:669-677`. |
| Explicit `302` handling retries `Location` with the same method and stored POST body. No redirect-count limit is present. | `e.run()V`, `e:1766-1815,1832-1881`. Built-in HttpURLConnection redirect behavior is not configured here and can act first. |
| Response text is decoded UTF-8, without a response Content-Type check. Empty successful body becomes nonnull empty string. | `e.run()V`, `e:2001-2033,2115-2182`; `e.a(Ljava/io/ByteArrayOutputStream;Ljava/lang/String;)V`, `e:171-244`. |
| No application `setConnectTimeout`, `setReadTimeout`, or `getErrorStream` call appears in this class; polling has no deadline. | Whole `e.run()V`, `e:1216-2256`; polling examples `a:359-420,963-1010,3084-3141`. Runtime timeout defaults are unknown. |
| HTTP code, response message and Date are stored in `e.Bg:I`, `acH:String`, `BX:J`; body/code are copied into `g.bxa:String` / `g.bxb:int`. | `e.run()V`, `e:1738-1764`; `a.a(Lcom/gameloft/a/b/g;Lcom/gameloft/a/b/e;)V`, `a:1633-1649`; `g.smali:7-11`. |

Response transformations in `a.Wn`, `Wo`, and `hq` first apply `h.ht(String)` then `h.hu(String)`: these decode literal double-backslash-u and single-backslash-u followed by four hexadecimal digits (`h.smali:3657-3851`). They do not parse all JSON escapes or strip quotes. Where called, `h.hs(String)` trims a string longer than one character and, if the result starts `[`, blindly removes its first and last characters (`h:3610-3655`). This permits a textual single-object-array wrapper for URL-map/token consumers but does not prove an array schema. The privacy parser applies none of these transformations (`i:924-927`).

## Bootstrap HTTP contracts

### Datacenter list

`a.Wm()Ljava/lang/String;` issues GET `https://eve.gameloft.com/config/{client_id}/datacenters`, no query/body/token (`a.smali:193-232`; URL helper `a.aL(Ljava/lang/String;)Ljava/lang/String;`, `2369-2481`). It returns raw response text and emits callback type `0` (`a:281-317`).

The only demonstrated consumer reads `JSONArray[0]` as an object and `getString("name")`, `getString("_datacenter_id")`; both must be nonnull/nonempty for this wrapper to start initialization (`ax.a(ILjava/lang/String;ILjava/lang/String;Ljava/lang/Exception;Ljava/lang/String;)V`, `ax.smali:57-118`). Native representation: nonempty JSON array, first element object, those two fields strings. Remaining elements and all other fields are unconsumed. `name` becomes `Mx` through `a.aJ(String)` (`a:2358-2367`) and is used in the next path; `_datacenter_id` becomes `bwK` through `hp(String)` (`a:3442-3451`) but is not used by these request builders.

`client_id` is assembled as optional `GAME-SPACE + ":"`, then `GAME-PRODUCT-ID:GAME-GGI:MIDlet-Version:GAME-PLATFORM`, then optional nonempty `":" + GAME-SHOP` (`a.gX()Ljava/lang/String;`, `a:3266-3424`; property mapping `au.Qh()V`, `au:7841-7912`). The URL helper concatenates this string and the datacenter `name` directly into the path; neither is form-encoded there (`a:2428-2476`). No concrete deployed values or datacenter names are inferred.

### Datacenter URL map

`a.Wn()Ljava/lang/String;` issues GET `https://eve.gameloft.com/config/{client_id}/datacenters/{name}/urls`, no query/body/token (`a:331-356`; `a.aL(String)`, `2369-2481`). If `Mx` is null, `aL` returns `/config/{client_id}` without the datacenter suffix (`a:2446-2479`); the normal datacenter-list chain supplies a nonempty name.

After text transformations and `hs`, it reads the following fields in this exact sequence with `JSONObject.getString` (`a.Wn`, `a:423-558`):

| Field | Stored/use visible here |
| --- | --- |
| `pandora` | `Mn`; root for `/locate/{service}`. |
| `gllive-ope` | `Mp` after literal `social_player.php` append. |
| `tapjoy` | `Mq`. |
| `offline_items` | `Mr`. |
| `game_portal` | `Ms`. |
| `status` | `Mt`. |
| `crm_iap` | `Mu`. |
| `ads_agency` | `Mv`. |
| `marketing_site` | `bhQ`, also passed to `au.ek(String)`. |
| `online_connectivity` | `bwL`. |

Native field representation is string. All ten names are required for a complete exception-free parse. The single broad catch prints and continues, leaving earlier fields assigned and later fields unassigned; a missing auxiliary field does not itself make `Wn` return failure (`a:559-605`). `pandora` is the field actually needed by the subsequent auth locator. An HTTP failure returns null; HTTP `403` also globally disables request launching with `bP(false)` (`a:367-414`). A nonempty response is passed onward even after JSON extraction fails; empty response returns empty and ends `b.run` before authorization (`a:592-596`; `b:31-45`).

### Service locator

`a.hq(Ljava/lang/String;)Ljava/lang/String;` invokes `a.a(Ljava/lang/String;Lcom/gameloft/a/b/e;)Z`, which GETs `{pandora}/locate/{service}` (`a:2237-2356,3453-3523`). For `service="auth"` and `service="config"`, there are no query/body/token parameters. The `alert` special case has additional parameters, but is not part of the demonstrated Accept chain.

The response is a **plain service-base string**, processed through `ht`/`hu`, then through `h.g(response,true)` by the caller. There is no JSON object/string extraction, trimming, slash normalization or expiry/cache at this layer (`a.hq`, `3492-3516`; auth caller `a.Wo`, `640-648`; config caller `a.k`, `4984-4994`). Do not wrap it in JSON quotes. Auth operations request the auth location again for each operation rather than caching its result.

If `pandora` is null/empty, the private launcher returns false (`a:2244-2262`), but `hq` ignores that return and polls its never-started HTTP object (`a:3464-3496`), so the call can remain blocked. This is an observed bytecode failure path, not a server error schema.

### Authorization

`a.Wo()Z` requires nonnull `LZ` (device identifier) or immediately returns false (`a:628-636`). It locates auth, appends `/authorize`, and POSTs these form parameters (`a:701-960`):

| Parameter | Value | Emission condition |
| --- | --- | --- |
| `client_id` | `a.gX()` | Always appended by special `clientId` branch. |
| `username` | `a.js() = a.jv() + ":" + a.jt()` | Appended if nonnull; the concatenation produces a string. |
| `password` | `a.ju()` (`Nc`) | Only if nonnull. |
| `scope` | Literal `storage auth config asset transaction message` | Appended. |
| `device_id` | `LZ` | Appended after the nonnull precheck. |
| `access_token_only` | null in value array | **Omitted**, despite the parameter-name constant. |

The value array deliberately has fewer meaningful values than the names suggest: `clientId` does not consume a value-array index (`a:741-784,891-938`). Verify this indexing rather than treating the arrays as a direct zip. `js/jt/ju/jv` are at `a:4906-4972`. Wrapper `Qh` sets credential type to `anonymous`, credential to generated `j(true,true)`, password to `aR(credential)`, and device identifier via `f(identifier,true)` (`au:7918-7947`). `aR` maps up to the first 16 credential chars to `(char % 25)+97` (`a:2515-2573`); this is client credential derivation, not evidence of backend password validation.

After successful transport, `Wo` applies `ht`, `hu`, `hs`, parses JSONObject, and reads `access_token` via `getString` into static `Ma` (`a:1006-1046`). Native response: object with string `access_token` (or the textual single-object-array wrapper tolerated by `hs`). No token expiry, refresh token, user-id, error code or other field is consumed here. The catch prints and still returns true (`a:1048-1089`); HTTP failure returns false (`a:971-1004`). Actual token semantics and server authentication rules are unknown.

## Privacy/compliance request and response

`a.d(Ljava/lang/String;ILjava/lang/String;)Lcom/gameloft/a/b/g;` locates auth and POSTs `{auth_base}/users/me/compliance` (`a:2775-2784,2842-2855,3081`). Form values are `access_token=a.Wp()` if nonnull, `country=argument0` if nonnull, and `age=decimal argument1` always (`a:2857-2901`; omit-null/encode loop `a:2989-3058`). Age is a Java signed 32-bit int; this entry point does not validate a numeric range or ISO country format. The third argument is only a local diagnostic/callback tag, not a wire parameter. No bearer Authorization header is set.

The response path differs between transport failures and received bodies:

- Transport failure (`BT=true`), including HTTP other than 200/202, returns `g` **before calling `i.b(g)`**, leaving the pending privacy callback in place (`a:3092-3135`). A second request can then synchronously return status `3` for the outstanding callback.
- A received `202` body reaches `i.b(g)`, which clears the callback without invoking it because the code is not 200 (`a:3137-3189`; `i:1168-1193`).
- A received `200` body invokes `i.c(g)`; no response transformations are applied before JSONObject parsing (`i:902-929`).

`i.c(Lcom/gameloft/a/b/g;)V` accepts a root JSONObject with arbitrary top-level names. For every examined root property, `optJSONObject(name)` must yield an object and that object must have a property `compliant` (`i:932-965`; validator `i.K(Lorg/json/JSONObject;)Lcom/gameloft/a/b/l;`, `199-234`). `compliant` is read through `optBoolean` with its default/coercion behavior; there is no strict boolean-type check (`i.L(Lorg/json/JSONObject;)Z`, `236-248`). Extra properties **inside** a policy object are ignored; extra properties **at root** are also treated as policy objects and can cause failure.

Native consumed shape is therefore `{ "<policy-name>": { "compliant": <boolean>, ... }, ... }`. No policy name is required. Empty root `{}` reaches success without checking any policy. These are parser acceptance facts, not a recommended policy, known real response, or a justification for fabricating compliance. `data`, `COPPA`, and `GDPR` strings are initialized but their fields have no reads in original smali; neither a `data` envelope nor a special named-policy schema is demonstrated (`i:93-110`; full original-smali field-reference check).

If all examined values are true, submitted age remains. On the first false/coercion-false value, pending age becomes zero and the loop ends immediately, skipping remaining root entries. Both branches commit pending country + full raw answer and invoke success status `0` (`i:1029-1068`). There is no local country policy table or age threshold in this checker. JSONObject key iteration order is not a stable contract.

Empty string response produces status `6`; malformed JSON, a nonobject policy value, or a policy object without `compliant` produces `7` (`i:902-1025`, `199-234`). Null body is dereferenced at `isEmpty`, not handled as an empty-body status. On parse failure, callback receives a fresh default `m` (`age=0,country="",raw=""`), not current committed state (`i:878-880`; `m.smali:17-41`).

The callback interface is `k.a(Lcom/gameloft/a/b/l;Lcom/gameloft/a/b/m;)V` (`k.smali:7`); dispatcher invokes it and clears the pending callback (`i.a(l,m,String)`, `i:783-805`). Result fields are `m.bxy:I` age, `bxz:String` country, `bxA:String` raw response (`m.smali:7-13`). The diagnostic String argument to the dispatcher is not consumed.

| Numeric status | Observed meaning here | Evidence in `i.smali` |
| --- | --- | --- |
| `0` | Synchronous request accepted / cache operation success / privacy parse success (also noncompliance with age zero). | `1128-1138,627-631,768-772,1054-1062`. |
| `1` | Cache write/transform/encoding failure; request still runs. | `726-762,1141-1163`. |
| `2` | Cache read/decode/parse/missing-property failure. | `430-438,470-492,555-570`. |
| `3` | A callback is already outstanding. | `1113-1124`. |
| `4` | `a.fZ()` false; no request is issued. | `1094-1105`. |
| `6` | Empty response; also private parser's non200 branch, normally bypassed by outer wrapper. | `875-918,1168-1193`. |
| `7` | JSON or examined policy-object validation failure. | `199-234,952-1025`. |

`l` is a wrapper for private final `id:I`, not a demonstrated named enum (`l.smali:9-56`). No construction of status `5` occurs in this checker. Non200 error body schemas are unknown and unconsumed.

## Country cache and persistent privacy state

`i.hv(Ljava/lang/String;)Z` returns true only when supplied country equals committed country ignoring case, committed country is nonempty, and committed raw answer is nonempty (`i:1223-1272`). It checks no age, received/requested flag, JSON validity, expiry, timestamp or signature. Whether the game's Accept flow uses that result to skip a request is a caller-level question. A null supplied country is dereferenced rather than rejected explicitly.

The constructor initializes defaults, loads cache, then propagates age (`i.<init>()V`, `141-181`; `Init()V`, `183-197`). `i.Ww()Lcom/gameloft/a/b/m;` returns the live committed result (`i:1075-1083`). Pending-state helper `i.a(ZILjava/lang/String;Ljava/lang/String;)V` with false records pending age/country, received=false/requested=true; with true promotes age/country/raw, clears pending age, received=true/requested=false (`i:807-859`). `bQ(Z)V` ignores its argument, calls GLAds.SetAge only if received, and always calls AdsManager.SetAge (`i:1195-1221`).

The private file is `OF_PP.bin` (`i:58-60`), resolved under Android internal `getFilesDir()` by `com/gameloft/a/a/n.hl(String)[B` / `n.f(String,[B)Z` (`n.smali:4603-4634,4711-4740`; `au.Pq()String`, `au.smali:6505-6537`). This is local app storage; no server-side profile/save format is established by it.

Plaintext before the cache transform contains exactly:

| Field | Writer / reader |
| --- | --- |
| `m_age` | `put(String,int)` / `optInt`. |
| `m_privacyPolicyRawAnswer` | String `put` / `optString`. |
| `m_country` | String `put` / `optString`. |
| `m_privacyReceived` | `put(String,boolean)` / `optBoolean`. |
| `m_privacyRequestedByGame` | `put(String,boolean)` / `optBoolean`. |

`Wz()Lcom/gameloft/a/b/l;` writes these (`i:636-689`); `Wy()Lcom/gameloft/a/b/l;` requires all five names but performs no strict type validation and no reparse of saved policy (`i:511-624`). Pending age and pending country are not serialized, even when requested=true. The `m_temporaryAge` string is initialized but not used (`i:68-70`; full original-smali references).

Write sequence is JSON bytes → `n.a(bytes,Wx(),true)` → `n.e(bytes)` text encoding → file; read sequence is file → `n.bJ(text)` → `n.b(bytes,Wx(),true)` → JSON (`i:423-506,695-772`). `Wx()[B` builds sixteen key bytes by big-endian splitting integers `0x00fabcde,0xdeadbeaf,0xbeafdead,0x12141412` (`i:132-138,319-412`). Those exact transform calls are evidenced; this document does not infer a cryptographic algorithm or authentication guarantee. Missing file, decode IOException or null transformed bytes reset current and pending state through `WA()` and attempt writing defaults (`i:250-294,430-492`). Malformed cache JSON/missing names return status `2` without that explicit reset call (`i:504-572`).

## Auxiliary APIs visible in the same library

These contracts are observed, but neither is necessary to set `Ne=true` in `b.run`. Their higher-level use/response parsing is in `com/gameloft/a/a/o` and is outside this document's minimum Accept readiness claim.

| Operation | Wire contract | Body schema consumed in `a/b` | Evidence |
| --- | --- | --- | --- |
| Device registration | Locate auth; POST `{auth_base}/devices/mydevice`. Form fields: `access_token=Wp()`, `model=arg3`, `carrier=arg1`, `country=arg2`, `language=arg0`, `download_code=arg4`, `resolution=arg5`, `firmware=arg6`; omit each null. `arg7` is a local callback tag only. | None; body/code copied to `g`. | `a.a(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/gameloft/a/b/g;`, `a:1133-1620`, especially `1220-1326,1461-1504,1515-1603`. Caller examples `com/gameloft/a/a/o.D(String,String)I`, `o.smali:1024,1185`. |
| User config | Locate config; GET `{config_base}/configs/users/me` with `access_token=Wp()` if nonnull and `now=arg3` if nonnull. `If-None-Match: arg4` only if nonnull/nonempty; reads `Etag` response header into `g.bxc` on body-success branch. `arg0..2` do not affect this request, `arg5` is local callback tag. | Opaque here; no JSON properties parsed in this method. `304` is transport failure and this branch does not copy Etag. | `a.k(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/gameloft/a/b/g;`, `a:4974-5445`, especially `5062-5117,5252-5318,5331-5421`. Caller `com/gameloft/a/a/o.ex(I)I`, `o:2498,2617,2716`. |

Other profile/transaction methods in `a` are not reported as Accept requirements; their response schemas were not established by this investigation. No remote-side identity validation, age/country policy, legal text, error envelope, token lifetime, datacenter catalog contents, or game-config payload has been guessed.
