# Website sign-in through the Android browser

Source synchronized with `origin/main` before editing; both HEAD and origin/main
were `f018ebf1cc107c1a1000193c4254ef383cafe745`. The working tree was clean.
Existing ignored development configuration, database and signing key were preserved.

## Result

The Website button opens the existing HTTPS account form through Android
`ACTION_VIEW`. Registration and login use the existing backend API and MySQL
accounts. Success automatically opens
`com.quanhoang.greenfarm3://oauth/callback?code=…&state=…`; a visible return button
remains if the browser requires a user gesture. Passwords, access tokens and
refresh tokens never appear in that URL.

The callback validates the exact endpoint, unique allowed parameters, pending
state and expiry. It persists the handoff in the existing encrypted account
store, then exchanges the 60-second one-use code with the pending PKCE verifier.
A Website success shows the verified account name and GAME_ID in the existing
welcome panel, followed by its existing Continue action. The Facebook button
keeps its existing browser and success behavior.

Warm return prefers the actual live `MIDlet.cnz` over launcher `cnA`, and uses
non-clearing task flags. It does not call game initialization or reset the engine.
Cold return opens Start and recovers encrypted pending state. Actual gameplay
pause/resume is forwarded through the existing verified MIDlet lifecycle bridge.
Callback recovery wins over the browser-close grace period, including delivery
while recovery is CHECKING. Closing the browser without authenticating cannot
create a session; the existing cancel/retry behavior remains available.

The dedicated Website WebView Activity/policy and embedded-only APIs/tests were
removed. No separate WebView Gradle dependency existed. Unrelated game, ad and
Facebook SDK WebViews remain unchanged.

## Actual checks

| Check | Result |
| --- | --- |
| `cd backend && npm test` | 67 passed, 0 skipped |
| `cd backend && TEST_DB_NAME=green_farm_test npm run test:mysql` | 35 passed, 0 skipped |
| Production account-flow Kotlin harness | Passed: state/PKCE, callback replay/timeout, browser-close, cold recovery race, remembered session/refresh/logout, provider isolation |
| Production Controller + CallbackActivity harness | Passed: ACTION_VIEW, launcher underneath a live MIDlet, warm/cold return, destroyed host, forged/expired callback, name display/logout, Facebook regression |
| HTTPS auth-client harness | Passed: system trust isolation, origin/URL validation, response limits, errors/deadlines and credential redaction |
| Production legacy lifecycle harness | 2 passed: online pause/resume forwarding, unchanged legacy Facebook delegation and pinned MIDlet integrity |
| Packaging/core-source/account-renderer/artwork checks | 26 passed (includes the existing account-engine UI harness) |
| `git diff --check` | Passed |
| Native SQLite harness | Test execution blocked by HTTP 403 downloading `org.robolectric:nativeruntime-dist-compat:1.0.18` after Maven redirects to `gcp.mirrors.maven.org`; no native test pass claimed |

The MySQL tests exercise actual registration/login → callback → PKCE exchange →
verified identity, one-use/replay and 60-second expiry across backend restart,
remembered sessions, refresh and logout. Browser form tests execute the production
JavaScript against deterministic DOM/navigation peers, including blocked automatic
navigation and late replies after browser Back/Forward cache restoration. Android
host tests compile the production Kotlin with isolated platform/vault peers;
they are not Android instrumentation or a physical-device browser round trip.

No Android device/emulator is attached; actual browser intent resolution,
Android Keystore/device process death, live HTTPS/tunnel login and gameplay
save/load remain unverified on a device. No DNS/Tunnel/network setting changes
were made to work around these limitations.

`GameSaveStore`, `ProgressSaveController`, gameplay engine logic/assets, SQLite
schema/migrations, account encryption/session records, backend API implementation,
MySQL migrations and root URL configuration are unchanged. Tests do not open a
player's farm database. Source isolation is verified; an on-device before/after
SQLite comparison was not possible without a device.

## Build

Command: `./gradlew :app:assembleDebug -PgreenfarmLocalDevelopment=false`.
The final build uses online-account mode and the existing configured URLs.
Result: **BUILD SUCCESSFUL** (7m 53s).

Artifact: `app/build/outputs/apk/debug/app-debug.apk`.
SHA-256: `349321078ad3aa0137f05b9b3712bcf457a5cc597c691ac0228e202644d70573`.

Final APK signature and zip alignment checks passed. The packaged binary manifest
contains the exported VIEW/DEFAULT/BROWSABLE callback for the exact scheme/host/path.
DEX class definitions contain the callback Activity and contain neither
`EmbeddedAccountActivity` nor `AuthWebViewPolicy` (including their inner classes).
Generated configuration has `ENABLED=false`, `ONLINE_ACCOUNTS=true`; root URL
configuration remains unchanged. The output SHA-256 matches pipeline provenance.
The packaging pipeline verified original asset/native payload preservation and
reviewed DEX transformations.

The updated local backend was restarted and passed `/health` plus a request for
the new browser handoff JavaScript. This is not a live HTTPS/tunnel/device login.

## Changed files

| File | Change |
| --- | --- |
| `README.md` | Updated |
| `app/src/main/AndroidManifest.xml` | Updated |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/AuthWebViewPolicy.kt` | Removed |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/EmbeddedAccountActivity.kt` | Removed |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/LegacyFacebookLifecycle.kt` | Updated |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/SocialAccountController.kt` | Updated |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/SocialAccountFlow.kt` | Updated |
| `app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/SocialAuthCallbackActivity.kt` | Updated |
| `backend/EMBEDDED_AUTH.md` | Updated |
| `backend/README.md` | Updated |
| `backend/test/password-http-mysql.integration.mjs` | Updated |
| `backend/test/password-web.test.mjs` | Updated |
| `backend/web/README.md` | Updated |
| `backend/web/assets/password.js` | Updated |
| `backend/web/assets/styles.css` | Updated |
| `backend/web/pages.mjs` | Updated |
| `docs/SOCIAL_AUTH_ANDROID.md` | Updated |
| `docs/WEBSITE_BROWSER_AUTH_REPORT.md` | Added |
| `tests/test_auth_webview_policy.py` | Removed |
| `tests/test_browser_account_controller.py` | Added |
| `tests/test_embedded_account_activity.py` | Removed |
| `tests/test_embedded_account_controller.py` | Removed |
| `tests/test_legacy_facebook_lifecycle.py` | Updated |
| `tests/test_social_account_engine.py` | Updated |
| `tests/test_social_account_flow.py` | Updated |
| `tools/prepare_core_dependencies.py` | Updated |
