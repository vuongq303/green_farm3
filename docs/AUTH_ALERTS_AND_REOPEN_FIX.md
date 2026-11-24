# Alert, registration uniqueness and account recovery on reopen

Tóm tắt: Website đã dùng `window.alert()` thật, chờ đóng Alert thành công rồi
tiếp tục callback. Username/email trùng được phân biệt bằng HTTP 409 và UNIQUE
index hiện có, kể cả đăng ký đồng thời. Android đã sửa các lỗi khôi phục auth
tái hiện được: pending mã hóa hỏng chặn session tốt, lỗi khóa vĩnh viễn bị lẫn
với lỗi tạm thời, và refresh token mới chưa được lưu trước khi mạng lỗi.

Không sửa codec hoặc reset nông trại. Các bài cold-start/save trên host đều đạt;
kiểm thử SQLite native bị chặn mạng và chưa có thiết bị/Logcat của bản đang lỗi.
Vì vậy **chưa thể khẳng định thông báo “Không thể đọc dữ liệu” cụ thể trên máy
người dùng đã hết**. Log an toàn mới giúp phân biệt lỗi phiên, khóa chuyển save,
mở SQLite và giải mã dữ liệu game mà không ghi token hoặc nội dung save.

## Baseline and scope

The checkout was clean. `git fetch origin` found PR #26 merged into `main`;
this work starts from `origin/main` commit `f011532` on
`fix/auth-alerts-save-reopen`. Existing local commits were preserved. There is
no gameplay, schema/migration, application ID, backend URL, Cloudflare, HTTPS or
farm storage format change.

**Status:** Alert and uniqueness behavior are verified against Chromium and real
MySQL. Reproducible encrypted-auth recovery defects are patched and covered by
host regressions. The player's particular “Không thể đọc dữ liệu” popup has
not been reproduced on the affected device, so this report does not claim every
gameplay/save read error has been fixed.

## Website alerts

`backend/web/assets/password.js` calls the real `window.alert()` once for each
validation error, HTTP/API failure, connection failure, and registration/login
success. Loading remains nonblocking. The existing form, accessible status,
loading indicator, identifier preservation, password clearing, timeout and
callback validation remain intact.

The success alert blocks before the callback link/timer is prepared. Once it is
dismissed, the existing delayed one-shot handoff continues and the manual
“Quay lại Green Farm 3” link remains. No callback is issued behind an open
success alert. The protected external-browser/Deep Link/PKCE flow is unchanged.

## Username and email uniqueness

The existing `gf_password_username` and `gf_password_email` UNIQUE indexes on
`gf_password_accounts` are reused. `registration()` still normalizes username
and email to lowercase before the store receives them. No migration or index
recreation is needed.

The store checks both normalized fields in the valid form transaction. UNIQUE
constraints remain authoritative if two requests pass the precheck together.
After an actual `ER_DUP_ENTRY`, the losing transaction rolls back and committed
conflicts are classified without parsing or exposing the database diagnostic.
The losing browser attempt remains usable, with no orphan user/account row.

HTTP 409 and `error.code = "account_exists"` are preserved. An additive
`error.conflicts` array contains only `username` and/or `email`; existing clients
can continue using the original code/message contract. Messages and Alerts:

| Conflict | Message |
| --- | --- |
| username | Tên tài khoản đã tồn tại. Vui lòng chọn tên khác. |
| email | Email đã được đăng ký. Vui lòng sử dụng email khác. |
| both | Tên tài khoản và email đã được sử dụng. |

No existing account is changed, matched to another identity or disclosed beyond
these requested uniqueness results.

## What was found on cold account recovery

Two concrete defects were identified independently of the farm decoder:

1. `SocialAccountFlow.recover()` read pending callback/attempt records before
   loading a remembered session. `SecureAccountStore` collapsed corrupt/auth-tag
   failures, missing/permanently invalid keys and temporary storage failures
   into the same `storage_error`. The flow left unusable records intact, so each
   retry could hit the same error before it reached an otherwise valid session.
2. `validateSession()` obtained a replacement refresh token, then called `/me`,
   and saved credentials only after that request succeeded. If the connection
   failed after successful rotation, storage retained the now-revoked old
   refresh token; another cold process could not restore the still-valid new
   session.

The fix distinguishes definitively unreadable auth records from temporary
storage/network failures. An unreadable pending record clears only
attempt/callback; a healthy remembered session can still be verified. An
unreadable session or a definitively expired/rejected refresh moves to
`SIGNED_OUT` and requests login, clearing only that auth session. Offline,
temporary Keystore/prefs and failed-write paths keep stored credentials.

Decrypting an existing record never generates a replacement key. A new-auth
write may rotate only the app's own positively identified permanently invalid
auth key. An unclassified key-recovery error is not sufficient evidence to
delete it. Valid keys are not replaced for a malformed ciphertext record.

Both refresh paths now validate identity/provider and persist replacement
credentials before subsequent `/me` network I/O. Generation/deadline checks
prevent stale work from rewriting state after logout/cancellation. Pending
state, callback validation, PKCE and existing encrypted envelope version 1 are
preserved. Preferences stay in `greenfarm_online_auth_v1`; the alias remains
`<package>.online-auth.v1`. Neither module opens gameplay SQLite or RMS.

## Exact read-error surfaces and safe logging

The literal phrase “Không thể đọc dữ liệu” was not found in current source or
the Vietnamese pack. These actual paths must be distinguished using the full
popup and exception metadata:

| Surface | Entry point / condition |
| --- | --- |
| “Không thể đọc hoặc lưu phiên đăng nhập an toàn…” | `SocialAccountFlow.messageFor("storage_error")`: encrypted account record/storage failure |
| “Không thể đọc bản lưu…” | `GloftGF2F.mj()` bootstrap: transfer gate or progress-store initialization failed |
| “Không thể đọc tiến độ. Bản lưu cũ được giữ nguyên.” | `ProgressSaveController.loadingFailed`: critical load steps 38/44/47 or failed store status |

`GreenFarmAuth` now reports only recovery stage/reason. `GreenFarmSave` reports
bootstrap transfer lock vs progress database, method, phase, step, known logical
records, database/slot, generation, typed result code, exception classes and
bounded method/line frames. Step 38 reads `RMSHeader`, step 44 global
`save_gameplay`, and step 47 farm/reward/pose state. Messages, payloads, file
paths, credentials and raw throwable text are excluded. Failures still show
their UI and retain existing save/write barriers; no error is merely hidden.

The existing private-SQLite UUID exemption and legacy-storage compatibility
fixes are already present in this baseline. Their cold-process regressions
passed; they were not guessed at or applied again.

## Validation and limits

- `npm test`: **76/76 passed**.
- Real MySQL integration: **40/40 passed**, no skipped tests. Includes distinct
  username/email/both conflicts, case normalization, conflicts belonging to
  separate existing accounts, corrected-form retry, unchanged original rows,
  and three synchronized concurrent-registration races through actual
  `ER_DUP_ENTRY` handling.
- Real Chromium HTTPS + MySQL integration: **passed**. Ten real Alert dialogs
  cover empty/mismatched input, registration/login success, three duplicate
  cases, CSRF and failed login. Success dialogs remain open while the test
  verifies no callback navigation occurs, then dismiss and verify the single
  handoff, fallback and real PKCE exchange/refresh. No success API result is
  substituted.
- Existing private-save restore regression: **8/8 passed**, including fresh
  processes, changing SDK UUIDs and both build modes without writes on reads.
- Complete production global gameplay codec: **2/2 passed**, including full
  encoded state round trips across 20 fresh JVMs with byte comparison and
  malformed/future-version rejection. Resource/storage boundaries are peers;
  this is not a full Android farm/SQLite-device lifecycle test.
- Metadata diagnostics **1/1**, startup lifecycle **2/2**, save controller
  **1/1** passed. Existing blocking/no-reset behavior remains checked.
- Auth recovery/store/controller regressions: **3/3 passed**. Production
  `SecureAccountStore` performs real AES/GCM and file-backed preference
  writes/reads in separate JVM processes. Cases include old version-1 envelope
  round trips, corrupt pending/session, missing/wrong/permanently invalid key,
  generic key-recovery/read/write failure preservation and unchanged isolated
  farm sentinel. Flow cases cover both refresh-then-offline branches and a new
  flow restoring their replacement credentials, wrong-identity rejection and
  signed-out fallback. Host Android Keystore/preferences remain peers; these
  checks do not establish hardware-backed Keystore or real SQLite health.
- Debug APK is built with normal online accounts, without local-development
  bonuses. Build/signature/alignment results are recorded in the final handoff.

Native SQLite/Robolectric execution is still blocked before its runtime tests
by the required JAR redirect to `gcp.mirrors.maven.org` and CONNECT 403. Maven
Central, repo1 and the official Gradle proxy lead to that same destination.
No checksum, TLS or assertion was bypassed. Apply the saved domain grant in
Environment settings, then rerun `./gradlew -p tests/sqlite-host test` with the
normal environment activation. Unrun database assertions are not counted as
passing.

No Android device/emulator or affected-player save/Logcat was available. The
exact player's read error needs the full popup and `GreenFarmSave`/
`GreenFarmAuth` lines, without credentials. The changes above fix demonstrated
auth recovery failures; they do not establish that an unrelated corrupt or
unsupported farm save has been repaired.

## Changed files

- Website: `backend/web/assets/password.js`.
- Backend: `backend/src/auth/router.js`, `backend/src/db/social-store.js`.
- Android auth: `SecureAccountStore.kt`, `SocialAccountFlow.kt`,
  `SocialAccountController.kt` under the game's Kotlin package.
- Restore diagnostics only: `SaveRestoreException.java`, `GloftGF2F.kt`,
  `ProgressSaveController.kt`.
- Regressions: backend `password-web.test.mjs`,
  `password-browser.integration.mjs`, `password-http-mysql.integration.mjs`,
  `password-mysql.integration.mjs`; host `test_social_account_flow.py`,
  `test_secure_account_store.py`, `test_save_restore_diagnostics.py`,
  `test_startup_lifecycle.py`.
- This report. Dependency declarations, lockfiles and migration SQL unchanged.

## Deployment and affected-device checks

Deploy the changed backend/web files and restart Node so cached in-memory
assets are refreshed. Existing content-hash asset URLs invalidate old browser
scripts. No new migration, Cloudflare or HTTPS change is required.

Install the new debug APK from
`app/build/outputs/apk/debug/app-debug.apk` over the existing app using the same
signing key. Never uninstall, clear data, delete database/WAL/save files or
create a default farm to get past a read failure.

On the affected device, run website login/register, return by Deep Link, play,
Back/exit and launcher reopen; also test force-stop/relaunch, process reclaim,
expired access token/refresh, offline launch and logout/reopen. Confirm the
correct account and unchanged farm. If a gameplay read fails, retain all files
and capture the typed diagnostic before taking any repair action.

Only isolated test records/files and the dedicated `_test` database were used
for writes. No deployed MySQL accounts or player SQLite were accessed. The
farm database, schema, codec, tutorial, economy, Facebook, UI and normal
gameplay load order are unchanged.
