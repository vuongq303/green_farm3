# Android browser account and Facebook flow

The online account is an authentication identity, not a farm save. All classes in
this module are independent of `GameSaveStore`, `ProgressSaveController`, RMS and
the gameplay SQLite schema. A different account on the same installation still
uses that installation's local farm; it must not be described as a downloaded or
synchronized farm.

The right-hand account button now selects `password` and opens the first-party
login/register website in the device’s default browser using ACTION_VIEW. The left Facebook
button retains the official system-browser OAuth flow. Google is no longer a
new-login button or accepted `choose()` action; existing Google identities and
saved sessions remain readable and are not migrated or deleted.

## Configuration and platform requirements

`LocalDevelopmentConfig.AUTH_BASE_URL` is generated from the root Gradle
configuration by the build. It must be an HTTPS origin, for example the operator's
own `https://auth.example.org/`, with a trusted certificate. The actual deployment
domain is required before real password-account login can succeed. Password
accounts need MySQL and the backend's password routes, not Google/Facebook OAuth
credentials. Facebook separately needs its real provider configuration. Empty
configuration displays an error and never grants access.

The new account store requires Android 6.0 / API 23 or newer. Older Android
versions receive an explicit unsupported-device message; there is no plaintext
token fallback. This does not migrate, reset or delete any farm data.

`SocialAuthClient` uses a dedicated `SSLContext` initialized with system trust and
sets that socket factory on every authentication connection. It intentionally
does not inherit the original game's global legacy HTTP TLS factory. HTTP is
rejected, redirects on JSON API requests are disabled, connection/read timeouts
are 10/15 seconds, and response bodies are limited to 64 KiB.
An independent 45-second monotonic deadline disconnects the HTTP operation and
is checked while reading, so a server cannot keep it alive by dripping bytes.
The UI state machine also expires checking, exchange and logout operations after
60 seconds. Late network completions cannot enter the game; logout timeout does
not restore local credentials.

## Renderer integration

Initialize `SocialAccountController.initialize(context, authBaseUrl)` from the
launcher, then call `startupGate()` and `tick()` from the game's account gate
after original loading. `status()`, `message()`, `gameId()` and `busy()` describe
the native-rendered UI. No Android dialog or new account screen is rendered by
this module.

Status values:

| Value | State | Renderer behavior |
| --- | --- | --- |
| 0 | SIGNED_OUT | Facebook / Website buttons |
| 1 | CHECKING | Original loading indicator and dim overlay |
| 2 | BROWSER | Wait for external Website/Facebook browser; Back can cancel |
| 3 | EXCHANGING | Original loading indicator and dim overlay |
| 4 | WELCOME | Show verified name and `GAME_ID`; Continue / Logout |
| 5 | READY | Continue existing world-loading flow |
| 6 | ERROR | Original error presentation; retry/provider buttons |
| 7 | LOGGING_OUT | Loading while local credentials are cleared/revoked |

Button actions are `choose("facebook")`, `choose("password")`,
`continueSaved()`, `logout()`, `cancel()` and `retry()`. Duplicate begin actions
are ignored while an operation is active. `onHostPaused()` / `onHostResumed()`
detect return from either browser flow. A validated saved callback takes priority
over the browser-close grace period, including a callback arriving during recovery. Network results are queued and published
by `tick()`, without invoking the game engine from a network thread.

## Browser callback

The initial fallback callback is:

```text
com.quanhoang.greenfarm3://oauth/callback
```

Register `com.quanhoang.greenfarm3.S800x480.SocialAuthCallbackActivity` as an
exported, transparent Activity with a BROWSABLE/DEFAULT VIEW intent filter for
that exact scheme, host `oauth`, and path `/callback`. The callback Activity
accepts only `state` and exactly one of `code` / `error`. Duplicated or unknown
query parameters, fragments, wrong endpoints, expired attempts and mismatched
states are rejected. Intent extras are never used as authentication results.
Rejected handoffs log only `GreenFarmAccount: callback_rejected`, never the URI,
code, state, verifier or tokens. Credential-bearing model `toString()` methods
also return redacted text to guard against accidental diagnostic logging.

Verified HTTPS Android App Links need an actual owned domain and an
`assetlinks.json` containing the package and signing-certificate fingerprint.
This custom-scheme configuration does not claim App Links verification. A
custom-scheme interceptor cannot exchange a captured code without the app's PKCE
verifier, but can prevent delivery; App Links should replace that fallback when
the deployment domain is available, with an explicit corresponding callback
allowlist change on both server and client.

The app generates a cryptographically random state and a 32-byte random PKCE
verifier and uses S256. Pending state/verifier and the callback code are encrypted
before the browser handoff; a process restart can recover them. The pending
record is consumed before exchange, and the backend independently enforces
one-time, short-lived codes. A process death during an already-consumed exchange
may require a fresh login; it cannot grant an unverified session.

The Facebook authorization URL must be HTTPS on the configured backend origin
at `/auth/browser/start?id=...`. Provider authorization and callback remain
in the external browser; provider client secrets are never in the APK.

Password account attempts use the same `POST /auth/attempt`, state and PKCE,
with `provider: "password"`. Their authorization URL must instead be the exact
same-origin `/auth/web/login?attempt=<43-character ID>`. A provider redirect
cannot be substituted for that URL. The server's login/register success returns
only a short-lived `callback_url` carrying app code/state. The website opens the
fixed deep link; Android exchanges the code directly over HTTPS using its encrypted
PKCE verifier. GAME_ID alone never grants access. The exchanged provider must
match the pending attempt; a mismatched session is rejected and revoked.

## External browser and Activity lifecycle

The dedicated Website WebView Activity and navigation policy are removed; no
WebViewClient, WebChromeClient or JavaScript bridge remains in account login.
Other game/advertising WebViews are unrelated and unchanged. No extra WebView
dependency was present in Gradle.

The exported callback Activity validates the exact scheme/host/path, unique
state and code/error fields, pending state and expiry before saving a handoff.
Warm callbacks reorder the existing game Activity to the front without clearing
the task. Cold callbacks open Start, which initializes authentication and recovers
the encrypted callback. There are no gameplay initialization calls in the warm
handoff. A pending browser request is not relaunched automatically after process
recovery; the game can wait for it or cancel/retry. Back/closing the browser
without authentication never grants a session.

The browser owns its Secure/HttpOnly/SameSite cookie. It is bound to the attempt,
cleared by the backend on success and otherwise expires. Android does not read
passwords, clear browser storage or touch gameplay SQLite. Website success tries
automatic return and retains a visible return link if the browser requires a tap.

## Session storage and logout

`SecureAccountStore` encrypts independent `session`, `attempt` and `callback`
records using AES/GCM with an Android Keystore key. The only preference file is
`greenfarm_online_auth_v1.xml`; its values contain authenticated ciphertext,
not token plaintext. Additional authenticated data binds records to their
package and record type. Sessions are also bound to the configured backend
origin, so changing servers cannot send a previous server's tokens elsewhere.
Authentication preferences should be excluded from Android backup; Keystore
keys cannot be transferred to another device.

The backend validates remembered sessions before the welcome screen. Continue
validates again and refreshes if needed; no cached-session/offline bypass enters
the game. Logout removes only the auth records, attempts server revocation when
possible, and never touches the farm database. Concurrent refresh/logout is
handled by server session-family revocation.

## Validation and limits

```sh
python3 -m unittest discover -s tests -p 'test_social_account_flow.py'
python3 -m unittest discover -s tests -p 'test_social_auth_client.py'
python3 -m unittest discover -s tests -p 'test_browser_account_controller.py'
```

These tests exercise the production state machine, HTTPS client, callback/launch
adapter and callback Activity with deterministic storage/platform/transport peers.
They cover PKCE/state validation,
callback replay rejection, welcome/continue, token refresh, logout, method
switching, external browser launch and warm/cold task handoff, process recovery, late cancellation,
HTTPS/TLS isolation, hostile navigation, malformed responses and network errors.
The production Activity/helpers also compile against Android SDK 28.
They do not establish a live Android browser login or real Facebook login,
Android Keystore behavior on a physical device, App Links verification, or the
appearance and touch behavior of the native game UI. Those checks require the
configured HTTPS backend (and Facebook provider for its OAuth flow) plus an
Android device or emulator. No live browser-to-device round trip is claimed by the host tests.
