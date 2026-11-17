# Website login/registration: verified fixes and deployment

## Scope and baseline

Work started at `2c0334e` after `git fetch origin`; `origin/main` had the same
commit and the checkout was clean. Merge history included PR #23 (system-browser
website authentication) and PR #24 (explicit loopback HTTPS proxy trust).
No reset, source synchronization overwrite, backend URL or tunnel change was made.

The configured deployed origin was probed read-only at `/health`,
`/auth/assets/password.js` and `/auth/web/login`. All requests were rejected by
this environment's outbound proxy with HTTP CONNECT 403, before reaching the
website. Its deployed code and actual phone behavior therefore remain unverified.
The results below describe the repaired checkout and isolated local services.

## Findings and changes

1. **MySQL credentials were discarded.** `loadDatabaseConfig` validated
   `DB_PASSWORD` but returned `password: ""`. A direct MySQL request using the
   supplied credentials succeeded while the application migration/startup failed.
   It now passes `env.DB_PASSWORD` to the existing pool. No schema, session format,
   migration history or API contract changed.
2. **Feedback did not cover every browser/API outcome.** The existing frontend
   already had a submit handler and nested-error/callback processing; there is no
   evidence that its script was globally broken or blocked by the original CSP.
   Native HTML validation could prevent the submit handler from showing a
   persistent message. Non-JSON proxy failures lost their HTTP error meaning;
   raw server text could be shown; success navigated before feedback could paint.
   The form now presents required/invalid-field and confirmation errors in-page,
   maps known error codes and HTTP statuses to public Vietnamese text, preserves
   username/email, clears passwords after requests, focuses/scrolls feedback into
   the mobile viewport, and bounds both fetch and JSON parsing to 20 seconds.
   Passwords are never trimmed. Duplicate requests are blocked while loading.
   Only login HTTP 200 and registration HTTP 201 with the exact validated
   `callback_url` contract are accepted as success.
   Password-form script/style URLs include their content SHA-256, so a browser
   does not reuse the previous deployment's one-hour cached files.
3. **Top-level auto-navigation could destroy the fallback.** Real Chromium
   reproduced registration success followed by `location.replace(callback)`
   replacing the document when no app handled the scheme. The success message
   and manual link disappeared. The password flow now paints its distinct
   success message, waits 350 ms, then tries the validated callback once in a
   hidden sandboxed iframe. Unsupported/blocked launch leaves the parent page
   and “Quay lại Green Farm 3” link intact. The close link uses that same issued
   callback after success. Clicking manually or leaving cancels a pending timer.
   The password page alone permits `frame-src com.quanhoang.greenfarm3:`; actual
   Chromium rejected a host/path CSP source for this custom scheme. JavaScript
   still enforces the exact `oauth/callback` host/path, pending state, one code,
   and no extra query fields. The frame cannot run scripts or open popups.
   OAuth/Facebook callback navigation and its CSP are unchanged.
4. **Android already implements the required contract.** Its manifest receiver,
   `onCreate`/`onNewIntent`, controller and encrypted pending state already
   validate callback shape/state/expiry and exchange the code with the retained
   PKCE verifier. A warm return foregrounds the existing game with
   `REORDER_TO_FRONT` without `CLEAR_TOP`; cold process recovery reuses encrypted
   pending state. The verified display name feeds the existing welcome screen.
   No Android source or gameplay/SQLite code needed changing.

The existing API already returns nested public `{error:{code,message}}`,
registration 201, duplicate 409, invalid credentials 401, bad input/expired
attempt 400, CSRF 403, rate limit 429 and temporary backend error 503. Tests now
assert these real responses, retry behavior, and PKCE/code replay rejection.

## Validation

- Backend unit tests: **73/73 passed** with `npm test`.
- Real MySQL suite: **36/36 passed** with a dedicated `green_farm_test` database.
  `LEGACY_AUTH_ENABLED=true` was scoped to this test command because it also
  tests the separate legacy protocol; production auth settings were not changed.
- Chromium mobile-viewport HTTPS integration: **passed**, using production
  `createResearchServer` routing, MySQL/Argon2, real Secure/HttpOnly/SameSite
  cookies, Origin/CSRF checks and delivered CSP. It checks missing fields,
  mismatched confirmation, registration success, duplicate 409, unknown login
  and wrong-password 401, loading and duplicate submit prevention, success
  feedback, exactly one callback attempt, retained fallback, real PKCE exchange,
  wrong-verifier/replayed-code rejection, `/auth/me`, browser close and refresh.
  No successful authentication response is stubbed. Delaying delivery of one
  real request tests loading without substituting its response. No JavaScript
  exception or CSP violation occurred in the passing run.
- Android production host checks: **6 passed** across SocialAccountFlow,
  BrowserAccountController, SocialAuthClient, account UI/game entry and Facebook
  lifecycle. State mismatch, invalid/replayed/expired callbacks, cold/warm
  handoff, remembered/refresh/logout and existing gameplay entry are covered.
- Debug APK: `./gradlew :app:assembleDebug --max-workers=2 --console=plain`
  **passed**, with normal online-account configuration (local development false).
  APK signatures v1/v2/v3 and zip alignment passed.
- `git diff --check`: passed. Changes are limited to backend config, website
  presentation/behavior, related tests/dependencies, and this documentation.

**Limits:** Chromium with a mobile viewport is not an Android phone. The tests
prove the website attempts the real issued callback and preserves fallback,
then verify the real backend exchange independently of the Android OS. Android
logic is tested separately on the host. No Android device/emulator or real
Keystore integration was available (`/dev/kvm` absent). A full installed-game
browser round trip still needs the device checks below. The live deployment was
not tested or changed.

## Reproduce browser/API checks

Use Node 24+, OpenSSL, installed Chromium (or Playwright's installed browser), and
a MySQL test database whose `DB_NAME` ends in `_test`. Supply private `DB_*` and
`RESEARCH_SESSION_SECRET` through the environment; do not put values in Git or
terminal output. From `backend`:

```sh
npm ci
npm test
LEGACY_AUTH_ENABLED=true npm run test:mysql
npm run test:browser
```

`test:browser` generates a one-day localhost certificate in a temporary directory
and pins only its exact public key for its disposable Chromium process. Node's
HTTPS API client verifies the certificate against the supplied CA and localhost
SAN. It never enables global `ignoreHTTPSErrors`, modifies system trust, changes
production HTTPS, or uses a fake login result. Temporary key/certificate files
are removed on exit. Only rows created by the run are cleaned from the dedicated
test database; no application/player database is selected.

For an already trusted localhost certificate, supply `BROWSER_TEST_TLS_KEY`,
`BROWSER_TEST_TLS_CERT`, `BROWSER_TEST_TLS_CA` and omit
`BROWSER_TEST_PIN_LOCAL_CERT`. `CHROMIUM_EXECUTABLE_PATH` can select Chromium;
the wrapper uses installed `chromium` when available. Optional
`BROWSER_TEST_ARTIFACTS` saves a success screenshot outside the checkout.

## Files changed

- `backend/src/config.js`: actual database password.
- `backend/web/pages.mjs`, `assets/password.js`, `assets/styles.css`,
  `backend/web/README.md`: form validation, safe status messages, success and
  isolated auto handoff/fallback, narrowly scoped password-page CSP.
- `backend/test/password-web.test.mjs`,
  `backend/test/password-http-mysql.integration.mjs`: frontend and real API
  regression assertions.
- `backend/test/social-auth.test.mjs`: align the stale IPv6 assertion with the
  correct existing `::1` peer; runtime proxy behavior unchanged.
- `backend/test/password-browser.integration.mjs`,
  `backend/scripts/test-password-browser.sh`, `backend/package.json`,
  `backend/package-lock.json`: reproducible real-browser integration target and
  pinned Playwright development dependency.
- This report.

## Deploy and check on Android

1. Deploy the changed backend/web files and run `npm ci` using the deployment's
   existing environment, database credentials, stable session secret, HTTPS and
   tunnel settings. Restart the Node process: page assets are read into memory
   at startup. No new migration or database reset is introduced by this fix.
   Keep the current URL/tunnel configuration; this task did not alter it.
2. Check the real HTTPS `/health` reports MySQL ready. Start login from the game
   so the attempt, cookie and PKCE binding are real; a standalone login URL
   without an attempt is intentionally invalid.
3. On an installed Android device, exercise registration (duplicate, missing,
   mismatch and success), login (unknown user, wrong password and success),
   automatic foreground return, manual return with browser blocking auto launch,
   cancel/back, invalid callback and reopening with a remembered valid session.
   Verify the account name after exchange; website success alone does not mean
   the game has authenticated.
4. APK output: `app/build/outputs/apk/debug/app-debug.apk`. Existing compatible
   clients need only the backend/web deployment; there is no new client change.
   If installing the rebuilt APK, update in place using the same signing key as
   the installed app. Do not uninstall, clear app data or reset either database.

Only the isolated `_test` database was used for test writes/migrations. No
deployed MySQL accounts or player SQLite were accessed or modified. Account
switching still does not synchronize or rebind a farm. Gameplay, tutorial,
currency/items/level, Facebook, logout and session formats remain unchanged.
