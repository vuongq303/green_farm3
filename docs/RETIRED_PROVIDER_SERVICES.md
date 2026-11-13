# Facebook Login and rewarded advertising retirement

This change applies to the backend application restored from `2b9f0d9`; it is
not an offline profile. Janus discovery, readiness, anonymous authorization,
privacy/Accept and the backend snapshot coordinator remain separate from the
retired provider features. The game does not manufacture Facebook credentials,
tokens, a successful login callback or advertising rewards.

## Owned source changes

`ServiceAvailability.kt` defines the temporary provider policy and the verified
packed GUI identities. `bt.kt` retains the original JVM bridge signatures with
the original provider-disabled results (false, empty or null). Its provider
dispatch code and SDK import have been removed, not merely guarded by a flag.
It reports no logged-in Facebook user. Existing account/link fields and saved
RMS records are not reset. `cm.kt` retains only an unregistered callback ABI;
it cannot fetch friends, grant a Connect reward or report completion. The old
Facebook app ID/secret initialization block and the engine's direct provider
invitation call have also been removed.

Eleven verified Facebook-only share/invitation/login action handlers return
before opening their old wait state. They cannot leave the UI waiting for a
callback from the removed provider. Ordinary gameplay and backend guest
authorization do not use these handlers.

The original engine state 47 is the Facebook Connect screen. Navigation to it
now selects the existing own-farm state 18, or visiting-farm state 37, after the
normal backend/privacy continuation. The backend-enabled predicate `qN()` and
the legacy guest client `s.java` have not been disabled to achieve this.

`b.kt` prevents creation, initialization, availability queries, display and
callbacks of the rewarded-ad bridge. `FarmGameEngine` also retires ad-only CRM
refresh, native queries and four ad screens (61, 62, 63, 65). Incoming stale
ad-view/reward callbacks return before reading account data or mutating money,
reward flags or game navigation. Purchased items and ordinary shop actions are
not converted into free rewards.

The native advertising manager combines providers. Rather than guess the
unverified boolean positions of its `DisableProviders` JNI API, all rewarded
video entrypoints using this manager are paused while the provider SDKs are
removed from the build. This also pauses their lucky-wheel/video-stamp screens;
it does not replace viewing an ad with a free spin or claim.

| UI identity | Verified purpose | Treatment |
| --- | --- | --- |
| Layer 15, rows 32/33/40 | Pause/settings Facebook Connect button, label and decoration | Hidden; input and drawing removed |
| Layer 9, rows 4/8 | Neighbors Facebook Connect button and label | Hidden; input and drawing removed |
| Layer 29 | Facebook Connect screen | Removed from startup navigation; its rows cannot reactivate |
| Layer 1, row 81 | Farm daily rewarded-video icon | Hidden; input and drawing removed |
| Layer 8, rows 318/319 | Shop watch-video button and label | Hidden; input and drawing removed |

The filters run during row reconstruction, visibility changes, drawing,
hitbox/listener traversal and event dispatch, including direct packed-flag
writes. Packed layouts use fixed coordinates: no new empty banner/container is
created, but hiding controls does not automatically move unrelated controls.
The former icon/button location becomes ordinary background. Final visual
verification of the shop/settings layouts belongs to the APK/emulator check;
host fixtures alone do not prove that no visual adjustment is needed.

Decompiler references to `AdRequest.MAX_CONTENT_URL_LENGTH` and
`NativeContentAd.ASSET_HEADLINE` in `FarmGameEngine` were aliases for unrelated
game values `512` and `"1001"`; their values are preserved without importing
those advertising classes. Similar compile-time aliases in other recovered
engine files are not SDK initialization calls.

Build/manifest/retained-DEX pruning is implemented separately by the packaging
task owner. The original SDK starts Facebook through
`MIDlet -> v.onCreate -> socialnetwork.s.onCreate`, independently of `bt.I`.
Advertising starts through `MIDlet -> o.onCreate -> AdsManager.Init`. Therefore
source-level UI guards alone must not be reported as removing SDK bootstrap.
The final APK inventory and packaging verifier determine which definitions and
compatibility bridges remain.

## Exit and legacy cloud bridge

`FarmGameEngine.IT()` quit actions 1 and 24 call the two-stage
`BackendCloudController.requestQuit(preparation, continuation)` API. Preparation
settles earned XP/coins/cash/items (`FarmEntity.oM()`), cancels an active move
preview when required and writes the current time record. `oM()` removes reward
particles after crediting them; it does not discard the farm. The coordinator
then captures the immutable checkpoint before running deferred tracking/shutdown
and loop exit. A pending quit ignores duplicate menu actions. Network failure
does not turn local durability into a server ACK; the journal remains pending
for the next launch.

The old `aZ(true)` Seshat upload now only coalesces the already durable modern
upload journal using `localCommitAvailable()`. `aZ(false)` retains the original
two-record local-container path. No legacy server upload reports success on
behalf of the new SQLite snapshot API.

The obsolete server-message restore action `Gc()` displays an honest retirement
notice and closes only its own confirmation modal. It retains the old
notification and save records; it neither launches a Seshat preview nor claims
that a restore succeeded. The normal startup coordinator performs the actual
authenticated modern snapshot load.

## Host validation

The actual logs are archived under
[`backend-restore-validation/provider-host`](backend-restore-validation/provider-host/README.md).
The focused provider/GUI/model suites passed 9 tests: 6,000 actual reward
callback traces, 8,165 provider-widget checks, 8,091 existing More Games checks,
336 welcome-entry neighborhoods and original-bytecode comparisons for five
unchanged model/algorithm groups. The retired ad-callback group intentionally
differs from the original SDK and is checked against the no-reward policy.

After the complete Facebook SDK-removal scope was approved, the final provider
fixture was rerun: 2 tests passed in 11.807 seconds. It additionally exercised
the real unavailable Facebook ABI with null inputs and inspected the emitted
`bt`, `bt$Companion` and `cm` constant pools: no Facebook or removed social
dispatcher reference remained. This supports the separate packaging closure
audit; it does not itself prove that every final APK SDK definition was removed.

The quit/container fixture passed 52 checks, using the actual `oM()` settlement
method for pending XP/coins/cash/items. It checks reward/checkpoint/shutdown
ordering, duplicate-action suppression, preservation of the false/local
container, retirement of the old restore action and prevention of the legacy
online upload. These host tests do not test real
backend acknowledgement, Android lifecycle timing, rendering or player SQLite
durability; those results must be reported separately.
