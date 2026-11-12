# Remove “Trò chơi +” / More Games

The game no longer creates an interactive More Games button, draws its label or
animation, opens either recommendation catalog, or requests its launch-animation
configuration. The removal uses packed GUI identities, so language changes,
tutorial completion, resume, and GUI reconstruction cannot enable the feature.
The separate automatic Welcome splash promotion is also disconnected at its
two game-side entry points.

## Original feature and SDK evidence

The original `ci.gO(int)` dispatch contains these recommendation-only routes:

| GUI identity | Original handler | Original behavior |
| --- | --- | --- |
| Layer 1, item 34 (`65570`) | `sn()` | Farm menu recommendation button |
| Layer 1, item 38 | `aH(boolean)`, `bp(int,int)`, `jM(int)` | Recommendation label and animated artwork |
| Layer 27, item 22 (`1769494`) | `Cn()` | Recommendation button in quit confirmation |
| Layer 27, item 23 | `ko(int)` | Recommendation label in quit confirmation |
| Confirmation kind 1 | `IW()`, case 1 | Alternate recommendation launch |
| State 45 | `lk(int)` | Legacy IGP catalog, touch handling, paint and initialization |
| Game resume | `jG()` tail | Close/reopen automatic Welcome splash promotion |
| Own farm entry at level 3+ | `jM()`, enter branch | Download/show automatic Welcome splash promotion |

`sn()`, `Cn()` and `IW()` called inherited `GLLib.j(int,boolean)`, which calls
`com.gameloft.android2d.e.a.M(int,boolean)`. That original SDK method calls `Us()`,
then `IGPFreemiumActivity.gD()`. The dialog configures a WebView, fetches
`https://201205igp.gameloft.com/redir/freemium/hdfreemium.php`, and provides catalog,
game information and store links. The legacy `aw` class identifies itself as
`IGP.java` and handles `more_games_url`, `more_games_status` and
`URL-TEMPLATE-GAME`.

The automatic Welcome path used `ci.jG()` and `ci.jM()` calls to inherited
`GLLib.dM(int)`, then `com.gameloft.android2d.g.a.mZ`, then
`SplashScreenActivity.mr/ms`. The latter constructs
`https://201205igp.gameloft.com/redir/ads/splashscreen_view.php` and starts a
`bl` downloader that posts a `bm` runnable to show the dialog. Its `bq` WebView
listener handles splash advertisement clicks, marketplace links and IGP
redirects. Both game-side calls and the exclusive resume check/close block are
deleted. `jG` still resumes the shared CRM manager; farm entry still performs
its regular gameplay setup. The similarly named `jE` cleanup method is unchanged.

These call relationships were checked in both the original Java decompilation
and `javap -c -p` output from `.local/bytecode-tools/greenfarm-original.jar`, whose
provenance is recorded in [ORIGINAL_BYTECODE_TOOLS.json](ORIGINAL_BYTECODE_TOOLS.json).
Original `ci.Cn` bytecode offsets 103/132 invoke `j:(IZ)V`; original
`GLLib.j:(IZ)V` offset 8 invokes `com/gameloft/android2d/e/a.M:(IZ)V`.
The original `ci.aH` bytecode sets layers/items 1/34 and 1/38 and calls `bp:(II)V`.

Both labels use text ID `12501` (text pack 12, index 213). Read-only decoding of
the original packs through the documented `GLLib` format confirms:

| Pack | Original text |
| --- | --- |
| VI | + Trò chơi |
| EN | + Games |
| FR | + Jeux |
| DE | + Spiele |
| IT | + Giochi |
| ES / LATAM | + Juegos |
| BR | + Jogos |
| TR | + Oyunlar |
| RU | + игры |
| PL | + gry |
| TH | + เกม |
| JP | +ゲーム |
| KR | +게임 |

## Removal boundaries

`MoreGamesRemoval` contains only the four GUI identities above. `ci.bd` clears
their packed visibility/input flags every time a row is reconstructed. The
visibility and enable setters (`e`, `f`) cannot re-enable them, and the query
methods (`bg`, `bh`) report them absent. The common renderer skips them and
`gK` excludes them while building collision rectangles and keyboard focus lists.
`gO` discards their events, including events queued before a menu transition.
The old listener switch cases and `sn`/`Cn` implementations are deleted.

The farm's recommendation animation creator, its draw block, and the popup's
recommendation label/position code are deleted. A stale legacy state 45 closes
on entry without calling `aw` or creating a catalog. A stale confirmation kind 1
closes without invoking the SDK. Other confirmation actions remain available.

The post-authentication `s.aN("igp_launch_value.json")` request and its `dB(String)`
parser/persistence callback are deleted. Other federation `t.Pt` responses still
populate `arX`. Recommendation animation counters are no longer updated every
frame. Startup no longer evaluates `URL-TEMPLATE-GAME` or calls the Wi-Fi bridge
solely to decide recommendation availability. The harmless existing `Mp()`
preference read remains in startup to preserve the tested case-38 continuation;
its value no longer produces an animation, catalog or network request.

The original SDK, shared browser, tracking, IAP, rewarded ads, support, legal,
review and game-update implementations remain intact. In particular, an `igp`
hostname alone is insufficient evidence of a recommendation request: the same
host transports privacy, terms, EULA, game reviews and game updates. Currency
plus buttons and the rewarded-ad button use different GUI identities. Packed
artwork and all language packs remain unchanged because their containers also
hold gameplay content.

The source and final-candidate packaged manifests have no IGP, HDGames or
SplashScreen activity/service/provider registrations to remove.
`IGPFreemiumActivity` and `SplashScreenActivity` extend `javax.microedition.lcdui.q`,
which extends `Dialog`; a manifest change would not disable these dialog paths.
The original IGP lifecycle adapter `javax.microedition.midlet.t` has return-only
methods. The shared startup attribution request through `MIDlet.akH` /
`Tracking.ar.OM` / `Tracking.as` still sends `hdloading.php`; it reads a response
code and does not initialize a catalog. Shared campaign/news/gift support,
privacy/terms, rewarded advertisements and native SDK code remain intact.

## Verification

Run:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 \
  python3 -m unittest discover -s tests -p test_more_games_removal.py -v
```

The tests compile and execute the actual reset, visibility, enable, query,
renderer, hitbox, animation-removal and stale-state methods in an isolated JVM.
The actual dispatcher guard executes with a recorder at the subsequent large
switch boundary; unrelated Android actions are not simulated. They check 8,091
identity/reset/render/listener conditions, check currency and other retained
controls, check collision rectangles, and deliberately remove four independent
removal boundaries to confirm the regressions fail. Read-only pack decoding
checks text ID 12501 in all 14 packaged languages. Source checks also verify the
removed catalog callers, listener cases, launch configuration request, and
preserved shared SDK routes.

Two additional tests compile and execute the actual final resume and farm-entry
neighborhoods across 336 language/state/age/level combinations. SDK peers record
calls, confirming no Welcome launch/check/close route remains while shared CRM
resume and four gameplay setup calls still run. Reintroducing either original
Welcome launch independently fails the behavioral check. These isolated
neighborhoods do not simulate the full Android lifecycle.

This is JVM control-flow verification, not an Android screenshot or a network
capture. Full APK build, installation, menu/language visual checks, and runtime
traffic verification are performed separately by the main task. No assets,
original SDK bytecode, native libraries or binary packs were modified here.
