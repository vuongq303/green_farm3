# Android online account flow

The online account is an authentication identity, not a farm save. All classes in
this module are independent of `GameSaveStore`, `ProgressSaveController`, RMS and
the gameplay SQLite schema. A different account on the same installation still
uses that installation's local farm; it must not be described as a downloaded or
synchronized farm.

## Configuration and platform requirements

`LocalDevelopmentConfig.AUTH_BASE_URL` is generated from the root Gradle
configuration by the build. It must be an HTTPS origin, for example the operator's
own `https://auth.example.org/`, with a trusted certificate. The actual deployment
domain and provider application configuration are required before a real login
can succeed. Empty configuration displays an error and never grants access.

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
| 0 | SIGNED_OUT | Facebook / Google buttons |
| 1 | CHECKING | Original loading indicator and dim overlay |
| 2 | BROWSER | Wait for browser; Back can cancel |
| 3 | EXCHANGING | Original loading indicator and dim overlay |
| 4 | WELCOME | Show remembered `GAME_ID`; Continue / Logout |
| 5 | READY | Continue existing world-loading flow |
| 6 | ERROR | Original error presentation; retry/provider buttons |
| 7 | LOGGING_OUT | Loading while local credentials are cleared/revoked |

Button actions are `choose("facebook")`, `choose("google")`,
`continueSaved()`, `logout()`, `cancel()` and `retry()`. Duplicate begin actions
are ignored while an operation is active. `onHostPaused()` / `onHostResumed()`
detect return from a closed browser. Network results are queued and published
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

The browser authorization URL must be HTTPS on the configured backend origin at
`/auth/browser/start?id=...`. Facebook/Google authorization and the provider
callback occur on the backend; provider client secrets are never in the APK.

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
```

These tests exercise the production state machine and production HTTP client
with deterministic storage/transport peers. They cover PKCE/state validation,
callback replay rejection, welcome/continue, token refresh, logout, provider
switching, process recovery, late cancellation, HTTPS/TLS isolation, malformed
responses and network errors. They do not establish real Facebook/Google login,
Android Keystore behavior on a physical device, App Links verification, or the
appearance and touch behavior of the native game UI. Those checks require the
configured providers/domain and an Android device or emulator.
