# Local development, reopen safety and autosave

## Configuration and scope

The ordinary build keeps the existing backend workflow. Opt into local play with:

```sh
./gradlew :app:assembleDebug -PgreenfarmLocalDevelopment=true -PpythonExecutable=python3
```

Use `gradlew.bat` on Windows. To restore the backend workflow, rebuild with
`-PgreenfarmLocalDevelopment=false` (the default). The flag is a build input,
not a preference stored in a player's database. The generated
`LocalDevelopmentConfig` and source `LocalDevelopmentMode` contain no credentials
or fabricated access/refresh tokens. Release packaging remains disabled by the
existing research pipeline.

Local mode skips the SDK bootstrap request/thread (`au.Qh`), federation discovery,
authentication and CRM startup polling, commerce bootstrap, and remote saves.
The existing Accept validation, profile/preferences serialization, loading states
and SQLite initialization remain in use. The existing 16+ research policy
classification is evaluated locally; it is not a server compliance response or an
authenticated online session. Network code remains available in ordinary builds.

Only new local player initialization (`aY(true)`) selects the existing currency
caps: **99,999,999 coins** and **3,000,000 cash**. Loading an existing gameplay
record (`aY(false)`) does not replace its balances, including zero. The original
integer codec and Kotlin integrity wrappers are retained. Currency additions
widen to `long` before applying the existing upper/lower caps, avoiding signed
integer overflow without changing prices, rewards or spending rules.

## SQLite reopen defect

Previously, opening an already complete internal SQLite save unconditionally
rescanned the legacy RMS directory. Reading a native SQLite record also checked
legacy imports before accepting the existing record/tombstone. Android can make
external app storage unavailable after restart (`getExternalFilesDir()` returns
null); those unnecessary legacy accesses then rejected an intact internal save.
This is a concrete failure path identified in source, not a claim that the
reported device failure was reproduced on an Android device.

A validated complete SQLite snapshot now opens without requiring old external
media. Existing records/tombstones take precedence over legacy imports. A missing
primary record is not reimported after the first generation, matching the prior
migration rule. A genuine first import still fails visibly if old media cannot
be inspected; it is never converted into a new player or a reset database.
Repeated initialization reuses the validated open connection and application
context rather than rescanning and hashing every record again.

No database version, schema, gameplay record layout or player data is reset.
The existing transaction, checksum, snapshot completeness, single-writer,
coalescing, bounded retry and lifecycle flush protections remain in place.

## Autosave

The recovered client already has a legacy remote `Farm` request; the backend in
this repository does **not** implement a cloud farm persistence endpoint. These
changes preserve that protocol rather than inventing a new save API.

The backend scheduler uses monotonic uptime and a trailing debounce of 500ms:
actions at 0,150,350,600ms make the latest upload eligible at 1100ms. It accepts
only a payload captured for newly observed mutations in the current session,
and only after the matching SQLite snapshot is durable. Loading a historical
local save alone never schedules an upload. One request may be in flight; newer
changes remain coalesced, stale callbacks cannot acknowledge a different session
or revision, unchanged payloads are suppressed and failures retain pending state
with bounded retry/backoff. Local mode does not enqueue these uploads.

The existing `_level` and `_manor` profile writes use the same scheduling and
durability checks, with their loaded values as baselines. They no longer submit
unchanged values on a periodic timer. Enqueue explicitly reports a full request
pool (including a locator consuming the last slot), so a silently dropped
request cannot leave autosave stuck waiting for a callback that will never come.

Local SQLite scheduling remains independent: 500ms debounce with the existing
2-second/50-operation safety bounds, immediate critical checkpoints and lifecycle
flushes. Backend debounce does not delay local persistence. There is no guarantee
for mutations killed by the OS before they can be captured/committed; abrupt
force-stop or power loss must not be described as a successful graceful flush.

## Verification

Useful commands (activate the prepared cloud toolchain first when applicable):

```sh
python3 -m unittest discover -s tests -p 'test_*.py'
./gradlew :app:assembleDebug -PgreenfarmLocalDevelopment=false
./gradlew :app:assembleDebug -PgreenfarmLocalDevelopment=true
./gradlew -p tests/sqlite-host test
cd backend && npm test
```

The new tests execute production Accept logic with SDK peers that throw on
access, actual currency setters/getters and codecs, deterministic backend
scheduling, and DEX guard readback/provenance. Native SQLite regressions cover
10 close/open/write cycles with lost legacy media, first-import failure data
preservation, repeated initialization under an external writer lock, and exact
maximum currency bytes through a database reopen.

The native SQLite suite requires Robolectric's
`org.robolectric:nativeruntime-dist-compat:1.0.18`. In this environment its Maven
URL redirects to `gcp.mirrors.maven.org`, denied by the active network policy.
The domain is already in the environment configuration draft from onboarding;
it must be applied in environment settings before rerunning native tests.
Compiling these tests is not evidence that their database assertions ran.

APK builds and isolated JVM tests do not establish Android UI/gameplay/FPS,
physical-device save/reopen behavior, or successful cloud farm persistence.
An Android device/emulator and, for remote saves, an implemented compatible
server remain necessary for those checks. No UI layout, image, texture, animation
or gameplay rule change is intended.

## Changed files

| Files | Purpose |
| --- | --- |
| `app/build.gradle` | Strict opt-in property, generated development config and task dependencies. |
| `app/src/main/java/com/quanhoang/greenfarm3/S800x480/FarmGameEngine.java` | Local startup guards, new-player currency defaults and safe additions, legacy Farm/profile upload scheduling. |
| `app/src/main/java/com/quanhoang/greenfarm3/S800x480/s.java` | Local request guards and explicit acceptance/rejection of autosave enqueue. |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/LocalDevelopmentMode.kt` | Shared build-time local-mode predicate. |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/BackendSavePolicy.kt` | Bounded single-request trailing scheduling and revision acknowledgements. |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/ProgressSaveController.kt` | Connect remote eligibility to mutation/capture/durability without delaying SQLite. |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/GameSaveStore.kt` | Validated reopen and authoritative SQLite record reuse. |
| `buildSrc/src/main/groovy/greenfarm/MergeOriginalSdkApk.groovy` | Include and require the verified SDK bootstrap guard in packaging. |
| `tools/PatchBackendEndpoint.java`, `tools/build_local_backend.py` | Guard exact SDK entry point while proving the original false branch and other DEX semantics unchanged. |
| `tools/prepare_core_dependencies.py` | Register the two new source helper identities. |
| `tests/test_local_startup.py`, `tests/test_local_currency.py`, `tests/test_backend_save_policy.py` | New executable regressions for the changed behavior and full request pool. |
| `tests/test_local_startup_guard.py`, `tests/test_build_local_backend.py` | DEX branch and packaging/provenance regressions. |
| `tests/test_progress_save_controller.py` | Controller integration including local/remote/history, durability and metadata. |
| `tests/sqlite-host/src/test/java/greenfarm/SQLiteSaveTest.java` | Native database regressions, compiled but blocked from execution here. |
| `README.md`, `docs/LOCAL_DEVELOPMENT_FIXES.md` | Build flag, behavior, root cause and validation scope. |

## Results on the prepared cloud machine

- Final Python/JVM discovery: 160 tests, **144 passed, 16 skipped, 0 failed**.
  Skipped tests are not counted as evidence of application readiness.
- Backend HTTP/config regression: **25 passed, 0 failed**.
- Normal (`greenfarmLocalDevelopment=false`) final APK assembly: passed.
- Local (`greenfarmLocalDevelopment=true`) final APK assembly: passed; the
  compiled predicate returns true. The local artifact is
  `app/build/outputs/apk/debug/app-debug.apk`; a copy of the normal artifact is
  retained at `.local/validation/app-debug-online.apk`.
- Both APK artifacts passed `apksigner verify` and `zipalign -c -p 4`.
- Native SQLite test compilation: passed; **13 database tests unexecuted** due
  to the dependency/network restriction above.
- `adb devices` returned an empty device list. Android UI, real gameplay,
  repeated application process restarts and FPS were not tested on a device.
- No dependency declarations, lockfiles, database schema, UI layouts, images,
  textures or animation assets were changed.
