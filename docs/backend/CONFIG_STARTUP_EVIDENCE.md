# Original startup profile and config evidence

This investigation continues [ACCEPT_PROTOCOL_EVIDENCE.md](ACCEPT_PROTOCOL_EVIDENCE.md). All citations are **physical lines** in `/workspace/tooling/original-smali/` from the original APK, not `.line` metadata or reconstructed Java. No remote services were contacted. The original APK SHA-256 remains `34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b`.

Root's emulator/thread inspection reported that authorization completed but Hestia remained in `o.a(x,String)` waiting for `adf`, while the local service had returned one sanitized GET `501`. That runtime observation selects the path investigated below. It does not itself establish any remote response schema. The bytecode confirms that `Ne=true` is only Federation readiness; full startup needs additional library work.

## The startup wait is a profile-success flag

| Step | Original descriptor and physical lines |
| --- | --- |
| Hestia starts an operation worker, records operation ID and sets busy flag. | `Lcom/gameloft/a/a/o;->a([Ljava/lang/Object;)Z`, `o.smali:1529-1573`; initialization `o.m(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Z`, `3816-3853`. |
| Worker operation `0` calls Hestia `D`, then only after it returns runs its completion dispatcher. | `Lcom/gameloft/a/a/p;->run()V`, `p.smali:86-168`, especially `109-136`; dispatcher `p.mJ()V`, `29-82`. |
| In `D`, an uninitialized UserProfile triggers `o.a(x,"me")`. | `Lcom/gameloft/a/a/o;->D(Ljava/lang/String;Ljava/lang/String;)I`, `o.smali:558-577,1028-1039`. |
| That helper invokes `x.J("me",acR)`, ignores the returned boolean, then polls static `adf` every 100 ms without a failure/deadline condition. | `o.a(Lcom/gameloft/a/a/x;Ljava/lang/String;)V`, `o:1471-1517`. |
| `x.J` records crypto key and enqueues operation `0`, which calls `x.cF`. | `Lcom/gameloft/a/a/x;->J(Ljava/lang/String;Ljava/lang/String;)Z`, `x.smali:1783-1834`; `x.a([Ljava/lang/Object;)Z`, `619-663`; `Lcom/gameloft/a/a/y;->run()V`, `y.smali:50-89`. |
| `x.cF` calls `oo`; only return `0` sets `akR=true` and invokes `o.mr()`. Any nonzero return exits without `mr`. | `x.cF(Ljava/lang/String;)I`, `x:755-1131`, particularly `1111-1124`. |
| `o.mr` sets `adf=true`. | `o.mr()V`, `o:3561-3572`. |

The UserProfile worker's own completion method clears its busy flag and sets its own completed flag, but does **not** call `o.mr` (`y.mJ()V`, `y:29-46`). Therefore profile failure can finish that worker while leaving the Hestia thread waiting indefinitely. The later Hestia completion flags and optional `q.c(int,String,int)` callback cannot occur until `D` returns (`p.mJ`, `p:29-82`). This explains why returning a truthful failure from an unimplemented storage locator can expose a permanent wait in the original client.

## Exact storage discovery and profile GET contract

`Lcom/gameloft/a/a/x;->oo()I` calls `a.m(Kv,null,null,"stdRefreshGetProfile")` (`x.smali:1469-1486`). The original reader is:

`Lcom/gameloft/a/b/a;->m(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/gameloft/a/b/g;`, `a/b/a.smali:5447-5870`.

1. It calls `hq("storage")`, then `h.g(response,true)` (`a:5452-5462`). With the already verified locator builder, this is GET `{pandora}/locate/storage`, no token/query/body (`a.a(Ljava/lang/String;Lcom/gameloft/a/b/e;)Z`, `a:2237-2356`, particularly `2274-2284,2347-2350`). Locator response is plain service-base text through `ht/hu`, not a JSON-quoted string (`a.hq(Ljava/lang/String;)Ljava/lang/String;`, `a:3453-3523`). Existing `http://` remains HTTP; no slash trimming occurs (`h.g(Ljava/lang/String;Z)Ljava/lang/String;`, `h.smali:3534-3608`).
2. It appends fixed path `/profiles/me/myprofile` (`a:5538-5552,5634-5664`). Arguments `p0,p1,p2` do not select another profile in this method. Its only wire parameter is `access_token=Wp()` if nonnull, form-encoded into the GET query (`a:5561-5576,5713-5754`). Request: **GET `{storage_base}/profiles/me/myprofile?access_token={encoded_token}`**, no body or explicit bearer header. `p3` is only a local callback tag.
3. It returns raw body/code in `g`. A locator HTTP failure makes `hq` return null, then `m` returns null (`a:3478-3490,5475-5514`). A profile HTTP failure returns a nonnull `g` carrying the failure code and normally null body (`a:5767-5810`). Federation callback type `8` is not the Hestia-success flag; wrapper `ax` handles only callback IDs `0..5` (`android/wrapper/ax.smali:319-327`).

## Profile body fields actually consumed

`x.a(Lcom/gameloft/a/b/g;)V` maps null response to status `-1`, HTTP code greater than 200 to that code, otherwise status `0` plus raw body (`x.smali:574-617`). Original HTTP transport reads bodies only for 200/202, but this profile layer treats 202 as a nonzero status, so use **HTTP 200** for a successful own profile.

`x.oo()I` requires status `0` and a nonnull/nonempty body, constructs a root `JSONObject`, and serializes/encrypts that object into local `akS` (`x:1485-1499,1571-1597`). No named property is required by that initial parser; unknown properties are preserved. Empty body returns `-30` (`x:1624-1633`). A `JSONException` is swallowed without changing status `0` (`x:1635-1639`), so a malformed success body can falsely signal success; it is not a valid response contract.

There is exactly one original-smali caller of `x.e(Ljava/lang/String;Lorg/json/JSONObject;)I`: Hestia `D`, which asks for `credential` when a UserProfile is already initialized (`o.smali:573-606`). It calls `getString("credential")` on the output and compares it to **`a.js()`**, the full authorized username such as `anonymous:<generated-credential>` (`o:580-610`). A mismatch clears cached config and reinitializes the profile (`o:612-626`). `x.e` reads the stored object's named value with `get`, not a schema transform (`x:1836-1908`).

The minimal **meaningful own profile** for this startup identity use is consequently:

```json
{"credential":"<the username validated during this local session's authorization>"}
```

`credential` is a JSON string with the full authorized username, not the raw suffix, access token, device ID, or invented third-party account. Other fields are not required by the demonstrated startup reader. A local implementation can return this actual identity from its validated session metadata and keep the client namespace with that session; it must not return another user's identity or universally accept an invalid token.

`x.cF` also constructs an extensive hardcoded sample object containing dated purchases, devices, currencies, inventory, etc. (`x:795-1098`) and stores it separately as `akT` (`x:1103-1108`). This is a local template used by later profile-field validation, not evidence that those fields must be fabricated in a server response. The profile GET schema above does not adopt that sample.

A profile `404` triggers an explicit creation attempt: POST profile set with `{"total_playtime":0}`, then GET again (`x.oo`, `x:1496-1569`). That write path is a separate service operation. A pre-existing own session-backed identity profile avoids entering it without pretending to implement arbitrary profile writes.

After successful initial profile setup, `D` proceeds to the already observed device-registration POST and ignores its returned object (`o:1117-1187`; analogous credential-change branch `956-1026`). A device failure is not the `adf` wait just described. This does not establish that all later startup/game behavior can tolerate every unsupported service.

## Config response parser: evidence, not an empty success default

The config refresh operation is separate from initial profile setup. `o.ev(I)Z` queues operation `1`; `p.run` calls `o.ex(I)I` (`o.smali:3781-3814`; `p.smali:138-166`). `ex` invokes the verified config reader (`o:2498,2617,2716`), whose wire contract is GET `{config_base}/configs/users/me?access_token=...` with optional `now` and If-None-Match after GET `/locate/config` (`a/b/a.k(Stringx6)`, `a.smali:4974-5445`).

`ex` uses HTTP `200` as status `0`; records raw body; passes it and Etag (or literal `"null"`) to `E(String,String)` (`o:2561-2588,2783-2799`). A null config reader records `-1`; a failed first fetch marks failure flags and finishes rather than waiting for `adf` (`o:2502-2559,2875-2940`). A config failure therefore does not explain the independently observed profile-success wait.

The config storage/parser methods consume:

| Root key | Native type / rule | Original evidence |
| --- | --- | --- |
| `expiry` | Required for exception-free config storage: JSON null or Java-cast String. A nonempty string other than literal `"null"` is parsed as a date by `bU`; null/empty/`"null"` uses sentinel `2145938400`. This is a strict cast, unlike `getString` coercion. | `o.E(Ljava/lang/String;Ljava/lang/String;)I`, `o:1213-1402`, especially `1239-1268,1296-1317,1377-1401`. |
| `game` | If present, must be JSONObject; stored for later game access. Missing/wrong type leaves prior/default game object because JSONException is swallowed. | `o.bS(Ljava/lang/String;)V`, `o:1853-1899`; constructor default `{}` at `o:183-188`; accessor `VX`, `3586-3594`. |
| `newsfeeds` | If present, must be JSONArray; missing/wrong type leaves prior/default value. | `o.hn(Ljava/lang/String;)V`, `o:3008-3048`; constructor default null at `o:190-191`. |

Config storage writes encrypted raw JSON into a `config<expirySeconds>` local file and tracks expiry/name/Etag in `filesConfig` (`o.E`, `o:1272-1368`; `mE()I`, `3118-3352`). Missing `expiry` or JSON parsing errors are swallowed and `E` returns `0` without guaranteeing a stored usable config (`o:1392-1396→1370-1375`). Thus HTTP `200 {}` can create false progress and must not be treated as a proven config implementation.

After successful first storage, `ex` sets its load flag and calls `mD`, which reloads cached JSON, invokes `bS`, `hn`, offline-items parser `t.cs`, and CRM parser `k.q` (`o:2859-2871,2685-2689`; `o.mD()I`, `3855-4064`). These consumers add the following bounded requirements:

| Consumer | Additional consumed structure / limit | Original evidence |
| --- | --- | --- |
| `t.cs(String)I` | Requires root `offline_store` JSONObject, passes it to `G`; any exception/nonzero child result maps to `-0x7fffffe6`. | `Lcom/gameloft/a/a/t;->cs(Ljava/lang/String;)I`, `t.smali:511-555`, especially `520-554`. |
| `w.g(JSONObject)I` through `t.G` | Requires `offline_store.prices` JSONArray of objects. A zero-length array is explicitly accepted by this parser; malformed/missing array fails. Item-parser status is ignored, so a top-level success does not prove valid catalog entries. | `t.G(Lorg/json/JSONObject;)I`, `t:344-427`; `Lcom/gameloft/a/a/w;->g(Lorg/json/JSONObject;)I`, `w.smali:31-111`, especially `45-91,100-104`. |
| Promotion | `u.g` expects `promotion` object with `end_date` and `description` strings for its own success, but `t.G` ignores that return after resetting promotion strings. Omission leaves empty end date and skips date calculation; partial malformed promotion can still leave an assigned date. | `Lcom/gameloft/a/a/u;->g(Lorg/json/JSONObject;)I`, `u.smali:25-105`; `u.hM()V`, `107-123`; `t.G`, `t:371-424`. |
| CRM first parse | `tracking`, `tags`, `fatigue_groups`, `pointcut_actions`, `ads` and nested `game.geolocation` are conditional. The parser does not require them when absent; missing tracking/game paths reset four tracking flags false, missing ads resets an empty tags array. Included fields have nested type requirements and side effects. | `Lcom/gameloft/a/a/k;->q(Ljava/lang/String;Z)I`, `k.smali:4657-5049`; `k.o(Lorg/json/JSONObject;)I`, `4320-4591`; `k.p(Lorg/json/JSONObject;)V`, `4593-4655`. |

This proves that a root `{}` fails the offline-store consumer, and that empty `prices` is a deliberate **parser** path representing no offered items. It does not establish a complete startup response: `expiry`, cache storage, game-specific `game` fields, consumers of optional content, and runtime progression still need validation together. No empty config, inventory, or commerce content is fabricated here, and no config endpoint is enabled by this evidence alone.

## Authorized implementation boundary after this proof

The bounded local profile slice adds GET `/locate/storage` and token-validated GET `/profiles/me/myprofile`, returning only the authorized session's actual username as `credential`. Its identity/session behavior is a new local service design; the path/query/key compatibility is established above. Device registration, config, profile writes/deletes, save synchronization, ads, commerce, and arbitrary remote forwarding remain unimplemented. Tests of these local HTTP routes do not replace an emulator startup test.
