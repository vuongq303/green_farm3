# Kotlin small-core checkpoint

68 original core Java implementations (53,137 source bytes before conversion)
were replaced by Kotlin under `app/src/main/kotlin`. This group deliberately
retains the original JVM class and entry-point names while engine and retained
DEX callers are migrated. It is a real implementation conversion, not a set of
Kotlin wrappers calling the old Java classes.

## Verified responsibilities and coverage

| Classes | Responsibility established by source/data flow |
|---|---|
| `ab`, `ae`, `ad` | MediaPlayer completion logging and SoundPool load-result/index updates. |
| `af`, `bh`, `ag`, `av`, `ax`, `y` | Fixed-point constants/division, screen-relative GUI constants and original keyboard/key configuration. |
| `al`, `bo`, `ce`, `cf`, `bi`, `bk` | Numeric mixing, seeded integer sequence, scalar storage and guarded integer/long storage. |
| `aj`, `bb`, `cc`, `cd` | Graphics scalar, packed entity helper fields, three/four-component float arrays. Fields whose role remains unclear retain their old ABI names. |
| `an`, `ar`, `aq`, `as`, `ap` | Existing HTTP trust policy, response callback contract, request/worker-slot queue and account-operation worker. |
| `c`, `d`, `az`, `ba`, `bw`, `bn`, `bg`, `au` | Billing methods/prices, parsed offline-store items/promotion and purchase-buffer state. JSON keys and numeric error codes are unchanged. |
| `bp`, `br`, `u` | Detached save payload, existing save-manager worker dispatch and federation response holder/decoding. |
| `ai`, `bu`, `ca` | Social-user image lookup/cache and user-profile worker dispatch. |
| `bd` | Three-byte-key linked-hash dictionary with original probing, signed arithmetic and capacity limit. |
| `bm`, `v` | Profiler call-tree node and named GLLib resource entry. |
| `cj`, `ck`, `cl`, `cm`, `cp`, `cq`, `cs` | Bootstrap credentials, CRM rewarded-video callback, analytics event queue, social initialization, ad-status polling, preserved retired save poller type and background game operation. The previous fix still does not start `cq`. |
| `cn`, `cr` | Existing privacy/compliance response flags, including COPPA/ITALY/OFT branches. |
| `co`, `cu`, `cv`, `cw`, `cx` | Online-quest refresh/name/leaderboard/reward/score callbacks, with existing null/status/retry handling. |
| `ct`, `cz`, `da`, `db`, `dc`, `dd`, `h`, `o`, `p`, `q`, `r` | Original typed records/queues, reward dispatch, analytics payload copy, CRM owner record and IAP timing/result helpers. Uncertain record fields remain unchanged. |

No save keys, item IDs, packed GUI IDs, numeric error values, tutorial states or
formats were renamed. Several original classes are compatibility names while
larger engine classes still call them; a Kotlin filename alone does not mean the
whole engine or every short identifier has been recovered.

## Identifier mapping

Names below refer to private fields, parameters or locals. Public/package JVM
members retain their old names to preserve current callers.

| Scope | Old symbol | New symbol | Evidence |
|---|---|---|---|
| `aj`, `ce`, `cf` | `acz`, `alg`, `alh` | `storedValue` | Direct scalar getter/setter. `aj`'s precise graphics interpretation is not asserted. |
| `al.q` | `j`, `j2`, `j3` | `value`, `firstMix`, `secondMix` | Two identical signed16-bit-shift/XOR/multiplication rounds. |
| `bo.aq` | `i`, `i2`, `i3`, `i4` | `value`, `multiplier`, `lowValue`, `lowMultiplier` | Explicit modulo10,000 multiplication terms; all operations remain Int. |
| `bi`, `bk` | `ajA`, `ajx`/`ajB`, `ajy`/`ajC` | `invalidValueDetected`, `encodedValue`, `randomMask` | XOR decode, hash validation and reset on mismatch. `ajz` remains the externally visible checksum field. |
| `ad.onLoadComplete` | `i`, `i2`, `i3`, `i4` | `sampleId`, `status`, `soundIndex`, `index` | Searches `ac.ma`, tests status0 and updates `ac.mb`. |
| `o.run` | `jCurrentTimeMillis` | `readyAtMillis` | Original system/elapsed clock selection, then `n.p`. Exact delays2000/5000ms remain unchanged. |
| `aq` methods | URL/body/method/callback/timeout `str`/`i` parameters | `url`, `payload`, `requestMethod`, `callback`, `timeoutMillis` | Copied unchanged into original request fields and `am.c` dispatch. |
| `as` | `Ml`, `adE`, `adF`, `jf`, `mM` | `workers`, `requestSlots`, `pendingRequests`, `availableSlotIndex`, `dispatchPendingRequests` | Three worker/request slots and original FIFO transfer loop. Both internal scheduling operations still hold the class monitor. |
| `az` | `ait`, `aiu` | `currencyCode`, `price` | Exact JSON keys currency/price and Double parsing. |
| `ba`, `d`, `bw` | `aiv`, `Km` | `prices`, `billingMethods`, `items` | Collections hold parsed price/method/item records. |
| `c` | `Ki`, `Kj`, `Kk`, `Kl` | `billingName`, `billingType`, `prices`, `replacedPrices` | Parsed name/type/price/replaced_price keys. |
| `bn` | `Kq`, `ajO` | `description`, `endDate` | Exact promotion JSON keys. |
| `bg` | `aji`, `Jn`, `ajj`, `ajk` | `instance`, `initialized`, `items`, `promotion` | Singleton/init/parsed-store data flow. |
| `au` | `adN`, `adO`, `adP`, `adQ` | `instance`, `purchaseData`, `requestStarted`, `legacyModeValue` | Getter/init calls establish first three roles; precise meaning of mode1/2 remains unverified, so no stronger name is claimed. |
| `bd` | `aiS`, `aiT`, `aiU`, `eJ`, `eK` | `slotToEntry`, `entryKeys`, `entryValues`, `hashSlot`, `findSlot` | Short slot-index table and Int key/value arrays with quadratic probing. |
| `bm` | `ajL`, `ajM`, `ajN` | `eventOccurrenceIndex`, `parentNode`, `childNodes` | `GLLib.bH` passes `bl.getCount()-1`; insertion links parent and bounds children by `w.Zq`. |
| `u` | `Qe`, `Qf`, `Qg`, `Qh`, `Qi` | `federationStatusCode`, `decodedResponseText`, `rawResponseBytes`, `responseErrorCode`, `responseErrorMessage` | Constructor receives federation callback fields; `jB` lazily caches `new String(bytes)` using the default charset. |
| Quest callbacks | `i`, `str`, loop `i2` | `statusCode`, `responseBody`, `questIndex` | HTTP status checks and original response handlers. Score retry is still exactly60000ms added to a Long timestamp. |
| Privacy callbacks | `mVar`, `str2` | `response`, `policyData` | Original policy data string, flags and default-locale country uppercase. |
| `dc.MU` | `iIi`, `iIi2` | `rewardKind`, `rewardCount` | Original low/high16-bit decoding and reward dispatcher calls. |

Unknown ABI fields in holders (`bb`, `ct`, `db`, `q`, etc.), remaining public
short methods and uncertain values are listed here rather than assigned guessed
coin/inventory names. They can be renamed together once their remaining engine
callers and data role are fully verified.

## JVM compatibility details

- Mutable Java-visible fields remain actual mutable fields through `@JvmField`;
  final owner fields remain final. Static fields/methods remain static. Arrays
  retain their primitive/reference component types and original allocation sizes.
- Java non-final classes and overridable instance methods remain `open`. Kotlin
  visibility widens package-private entries when needed because Kotlin has no
  package-private declaration. Synthetic constructor and field descriptors stay
  available; this is not a change to binary save keys.
- `h(f)` remains usable by current source and `h(f,g)` from the original APK was
  restored as a compatibility constructor. The latter is Kotlin-internal to
  accept internal marker `g`, but its JVM constructor remains public with the
  original descriptor. The marker parameter has no executable role.
- Reference inputs are nullable where Java accepted null. Null is dereferenced
  only at the original operation boundary; e.g. `bd.f(null,0,2)` returns−1 and
  `bd.f(null,0,3)` still fails, while quest callbacks preserve null-body guards.
- `aq.mK` keeps its instance monitor. Dex2jar expresses the original DEX's lock
  as `monitorenter/monitorexit`; Kotlin uses ACC_SYNCHRONIZED. Merely comparing
  that flag would incorrectly report a behavior change.
- The original `an` trust-manager methods were verified in original bytecode:
  two methods are single RETURN instructions; getAcceptedIssuers returns null.
  Their Kotlin bodies preserve that existing policy; they are not new recovery
  stubs or a claim of modern TLS validation.

## Verification

`tools/check_kotlin_core_abi.py` reads class-file descriptors from the verified
original converted APK and emitted Kotlin class files. It requires all68 classes,
188 original non-private method/constructor entry points and160 fields, including
static/instance form, mutability, interfaces, superclass and extensibility.
Kotlin companion/accessor classes are extra generated members, not substitutes
for missing required entry points. `--compiled-package` supports checking the
same contracts after package relocation.

`tests/test_kotlin_core_callbacks.py` compiles actual Kotlin implementations,
loads unchanged original method bytecode in isolated class loaders and compares
signed overflow/extremes, deterministic random sequences, encoded-value storage
and tamper recovery, linked-hash lookup/reset/capacity/null handling, default
records and array contents, detached save payload, lazy response decode, online
callbacks, privacy flags, event retry/dequeue, fixed-point values/division and
static initialization under three configurations. Boundary peers replace SDK or
Android services, never gameplay algorithms; all live DBs are untouched.
An actual two-thread test verifies `aq.mK` blocks while the object's monitor is
held, for both original DEX-derived code and Kotlin. This is JVM validation;
rendering, input, networking and audio on Android require separate APK tests.
