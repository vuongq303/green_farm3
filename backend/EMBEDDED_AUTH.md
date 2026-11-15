# Embedded account login

The right-hand account button now uses a project-owned username/email/password
website inside an Android WebView. Facebook keeps its existing official browser
OAuth flow. Google identities, sessions and server OAuth routes remain intact;
the right-hand button no longer starts Google OAuth. No provider OAuth page is
loaded inside the WebView.

This backend stores authentication only. It does not read, upload, bind, migrate
or reset Android gameplay SQLite. Accounts on one installation continue to use
that installation's local farm; changing accounts is not cloud-save switching.

## Flow and handoff

```mermaid
sequenceDiagram
    participant App as Native Green Farm UI
    participant Web as Restricted WebView
    participant API as HTTPS Node backend
    participant DB as MySQL accounts
    App->>App: Create random state and PKCE verifier
    App->>API: POST /auth/attempt provider=password, S256 challenge
    API->>DB: Save short-lived attempt
    API-->>App: /auth/web/login?attempt=opaque-id
    App->>Web: Open the exact pending URL
    Web->>API: GET login/register form
    API->>DB: Bind hashed cookie and CSRF to attempt
    API-->>Web: Secure HttpOnly SameSite=Strict cookie; escaped form
    Web->>API: Same-origin JSON login/register, cookie + CSRF
    API->>API: Validate input, rate limits, Argon2id
    API->>DB: Verify/create active password identity; consume attempt
    API-->>Web: Fixed app callback with one-use code and original state
    Web->>App: Native intercept; no external Intent/JS bridge
    App->>API: POST /auth/exchange code + PKCE verifier
    API-->>App: Existing access/refresh session and GAME_ID
    App->>App: Keystore storage; original world-loading flow
```

The WebView never receives an access token or refresh token. Login/register JSON
success contains only `callback_url`, whose fixed custom scheme carries a
60-second one-use code and the original app state. Knowing a `GAME_ID` or code
alone cannot authenticate: Android must validate state and exchange the code
with the pending PKCE verifier over HTTPS. Existing access/refresh rotation,
session-family logout and remembered-account welcome are reused.

An attempt expires after five minutes. Cookie `__Host-gf_auth_web` has `Path=/`,
no Domain, `Secure`, `HttpOnly`, `SameSite=Strict`, and a Max-Age no longer than
the remaining attempt. Its random session and CSRF components are stored only
as SHA-256 hashes in MySQL. The CSRF token is rendered in the escaped form;
POST additionally requires the exact configured `Origin`, a same-origin fetch
when that header is available, a matching hidden CSRF token and pending attempt.
The cookie is cleared after success and by the Android account WebView cleanup.
It is separate from Facebook/browser and gameplay data.

Wrong credentials leave the form usable. Registration uniqueness failures roll
back atomically without consuming the attempt. Concurrent registration requests
for the same pending attempt create only one account. Successful login/register
consumes the binding once; exchange independently prevents code replay.

## Schema and migration

Run the existing migration command; do not edit applied migrations or reset DB:

```sh
cd backend
npm ci
npm run migrate
```

Migration [004_password_accounts.sql](migrations/004_password_accounts.sql) adds:

- `password` to the existing `users.provider` and `gf_auth_attempts.provider`
  enums, preserving every existing Facebook/Google row and GAME_ID.
- `gf_password_accounts`: user ID foreign key, canonical unique username and
  email, Argon2id password hash, `account_status`, created/updated/last-login times.
- `gf_auth_web_sessions`: cookie hash, unique pending attempt, CSRF hash and expiry.

The existing `users`, codes, sessions, families and links remain the shared
identity/session system. A password identity gets its existing-format `GF...`
ID from its actual MySQL BIGINT ID, without JavaScript-number rounding. No
account is matched or merged with Facebook/Google using email. Disabling a
password credential blocks login, code issuance/exchange, access and refresh;
existing provider identities are unaffected.

## Configuration

Start with [.env.example](.env.example) and configure actual MySQL credentials and
the existing stable private `RESEARCH_SESSION_SECRET`. Keep secrets only on the
server. New password accounts require an HTTPS origin, not Google OAuth IDs:

```dotenv
HOST=127.0.0.1
PORT=8787
AUTH_PUBLIC_BASE_URL=https://accounts.your-domain.example
AUTH_TRUSTED_PROXY_IP=127.0.0.1
LEGACY_AUTH_ENABLED=false
```

The example domain is a placeholder. Put the same actual HTTPS origin in root
`gradle.properties` as `AUTH_BASE_URL`, rebuild and install over the existing
application using the same signing key. `BACKEND_BASE_URL` is the separate
legacy LAN discovery URL; changing it does not configure account authentication.

Facebook still needs `FACEBOOK_APP_ID`, `FACEBOOK_APP_SECRET`, a supported Graph
version and exact HTTPS `/auth/callback/facebook` redirect; see
[SOCIAL_AUTH.md](SOCIAL_AUTH.md). Those values are unnecessary for password
registration/login. Existing Google server settings can remain for compatibility,
but no Google client secret or Facebook secret is embedded in Android.

```sh
npm start
```

Without a configured HTTPS `AUTH_PUBLIC_BASE_URL`, the local `/health` endpoint
can be healthy while account routes report `503 oauth_not_configured`. The app
without `AUTH_BASE_URL` reports missing configuration. Neither state is a
successful login. This checkout does not deploy a domain or create real OAuth
applications, and it has no HTTP, fake-account or certificate-error bypass.

## HTTPS hosting and Cloudflare Tunnel preparation

An ordinary hosting reverse proxy can terminate HTTPS and forward to Node's
loopback listener. The exact trusted proxy must overwrite `X-Forwarded-Proto`
with `https` and `X-Forwarded-For` with one verified client IP. Do not trust a
subnet or forwarded header sent directly by an Internet client. Proxy access logs
must record sanitized paths such as Nginx `$uri`, not query strings, request
bodies or Authorization headers.

For a future **named Cloudflare Tunnel**, first configure your own Cloudflare
account, domain, tunnel and private credentials using Cloudflare's instructions.
Do not commit the credentials JSON. One possible server-only configuration is:

```yaml
# Private cloudflared config, not an Android asset or committed credential.
tunnel: <your-tunnel-id>
credentials-file: /private/path/<your-tunnel-id>.json
ingress:
  - hostname: accounts.your-domain.example
    service: http://127.0.0.1:8788
  - service: http_status:404
```

Use a loopback Nginx hop on port 8788 to normalize trusted headers before Node:

```nginx
server {
    listen 127.0.0.1:8788;
    server_name accounts.your-domain.example;
    access_log off; # Or a reviewed log_format containing only method, $uri, status.
    # This listener is reachable only by the local cloudflared process.
    # Cloudflare supplies CF-Connecting-IP; never expose this listener publicly.
    set_real_ip_from 127.0.0.1;
    real_ip_header CF-Connecting-IP;
    location /auth/ {
        proxy_pass http://127.0.0.1:8787;
        proxy_set_header Host $host;
        proxy_set_header X-Forwarded-Proto https;
        proxy_set_header X-Forwarded-For $remote_addr;
    }
}
```

Configure an Nginx `log_format` using `$request_method $uri $status` and apply it
to this server, or disable access logging there; the default request log includes
queries. Keep both local listeners bound to loopback and firewall direct access.
With remote/container proxies use the actual fixed peer IP instead. Verify your
proxy's header behavior before enabling the public endpoint. Run the named
tunnel only after setting real domain/configuration values; no tunnel has been
created or published by this change. Temporary tunnel hostnames require changing
both server and Android origin and rebuilding; they are unsuitable for stable
remembered sessions. Do not bypass SSL errors to use an untrusted certificate.

## API

All account APIs require HTTPS. Credential requests are JSON; responses are
non-cacheable. Errors are consistent `{error:{code,message}}` with public
messages; no SQL, password hash, credential or token is included in an error log.

| Route | Contract |
| --- | --- |
| `POST /auth/attempt` | Existing state/S256/fixed redirect fields, `provider:"password"`; 201 with the bound HTTPS form URL. |
| `GET /auth/web/login?attempt=...` | Starts or resumes the exact pending password attempt and renders login. |
| `GET /auth/web/register?attempt=...` | Same binding and cookie, registration mode. |
| `POST /auth/register` | `{attempt,csrf,username,email,password,password_confirmation}` plus cookie and exact Origin; 201 `{callback_url}`. |
| `POST /auth/login` | `{attempt,csrf,login,password}` plus cookie and exact Origin; 200 `{callback_url}`. |
| `POST /auth/exchange` | Existing one-use app code, PKCE verifier and fixed redirect; returns app tokens and public user. |
| `GET /auth/me` | Existing Bearer session validation. |
| `POST /auth/refresh` | Existing single-use refresh rotation. |
| `POST /auth/logout` | Existing current-family revocation; never touches farm data. |

Registration validates ASCII usernames `3..32` (`a-z`, digits, `_`) and practical
ASCII email addresses up to 254 characters (64-character local part, valid
domain labels). Both identifiers are trimmed/lowercased and separately unique.
Unicode/internationalized or quoted email local parts are not accepted. Passwords
are preserved exactly, require `12..128` Unicode code points and at most 512
UTF-8 bytes, and reject invalid Unicode/control characters. Confirmation must
match. The website may enforce a stricter UTF-16 input length on non-BMP text.

Passwords use pinned Argon2id with 64 MiB memory, three iterations, parallelism
one and a random 16-byte salt. No plaintext password is persisted. Unknown
accounts perform a random dummy Argon2 verification; wrong, unknown and disabled
accounts share the same public login error. At most two hashing/verifying jobs
run in one process; excess work returns a retryable 503 without an unbounded
queue. IP limits and hashed canonical-identifier limits cap retries (30 credential
requests per IP and 10 logins per identifier per 15 minutes; registration has
six attempts per username/email). These buckets are process-local: a production
multi-worker or multi-host deployment must add shared rate limiting at the
trusted proxy or a shared service. Rate limits are not described as global DB locks.

Forgot-password is intentionally absent. Adding it requires a fully configured
verified-email delivery and one-use reset flow; no unsafe reset-by-GAME_ID or
email-only endpoint is provided. Database maintenance may delete expired
`gf_auth_web_sessions` before removing old attempts/codes; active users, sessions
and gameplay data must not be reset for cleanup.

## Validation and limits

```sh
npm test
# Configure a dedicated DB ending in _test, never a production DB:
npm run test:mysql
```

Tests exercise actual Argon2, responsive-form JavaScript, production router and
prepared SQL with real MySQL. They cover creation, duplicate rollback, same-attempt
registration races, case-insensitive login, wrong/unknown/disabled accounts,
cookie/CSRF/Origin, HTTPS rejection, PKCE/replay, restart, refresh, logout and
existing provider regressions. HTTP test fixtures inject a TLS predicate on a
loopback listener; they are not a deployed HTTPS website or a real Android WebView.

Actual hosted HTTPS, device WebView/Keystore/touch behavior and real Facebook
OAuth still require the configured domain/provider and connected Android device.
No provider account or production login is claimed to have succeeded without
that verification.
