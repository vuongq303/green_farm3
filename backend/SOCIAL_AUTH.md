# Facebook / Google accounts

The current Android right-hand button has moved to project-owned password login
inside a restricted WebView; see [EMBEDDED_AUTH.md](EMBEDDED_AUTH.md). Facebook
keeps this official browser flow. Existing Google identities/server routes are
preserved for compatibility, but the new right-hand button does not start Google
OAuth. No provider OAuth is embedded in a WebView.

This implementation adds account authentication and stable game IDs. MySQL has
no farm payload endpoint, and login/logout does not read, migrate, reset or bind
the Android gameplay SQLite database. The same device continues to show its
local farm after changing accounts; that farm is not represented as a cloud save.

The latest Facebook/Google requirement uses the providers' official browser
authorization pages. There is no project password form or simulated login.
First successful provider verification creates the account automatically.

## Flow

```mermaid
sequenceDiagram
    participant Game as Green Farm 3
    participant API as HTTPS backend
    participant Browser
    participant Provider as Facebook / Google
    participant DB as MySQL accounts
    Game->>Game: Generate state and PKCE verifier
    Game->>API: POST /auth/attempt (provider, state, S256 challenge)
    API->>DB: Save expiring attempt
    API-->>Game: HTTPS authorization_url
    Game->>Browser: Open authorization_url
    Browser->>API: GET /auth/browser/start?id=...
    API-->>Browser: Redirect to official provider with independent state
    Browser->>Provider: User consent/login
    Provider->>API: HTTPS /auth/callback/provider (code, state)
    API->>DB: Atomically claim provider state
    API->>Provider: Exchange code; verify provider identity
    API->>DB: Find/create unique provider identity; issue hashed one-use app code
    API-->>Browser: Success page and app callback code/state
    Browser->>Game: com.quanhoang.greenfarm3://oauth/callback?code=...&state=...
    Game->>Game: Check pending state
    Game->>API: POST /auth/exchange (code, verifier, redirect_uri)
    API->>DB: Transaction: verify PKCE/expiry, consume code, create session
    API-->>Game: Opaque access/refresh tokens and GAME_ID
    Game->>Game: Secure token storage; native welcome/continue
```

The application PKCE verifier never goes through the browser. Provider callback
state is independent from app callback state. App codes expire after 60 seconds;
attempts expire after 5 minutes. App codes, provider states and refresh rotations
are single-use. Failed PKCE verification does not consume a valid code.

## Backend structure

```text
backend/
  src/auth/config.js       HTTPS/provider configuration, trusted proxy check
  src/auth/providers.js    Google RS256/OIDC and Facebook debug_token/me checks
  src/auth/router.js       JSON APIs, browser redirects, rate limits, safe errors
  src/db/social-store.js   Prepared SQL and atomic state/code/session operations
  web/pages.mjs            Escaped handoff/success/error HTML and asset map
  web/assets/              Local farm-style CSS, logo and callback script
  migrations/002_social_auth.sql
  migrations/003_auth_session_families.sql
  test/social-auth.test.mjs
  test/social-mysql.integration.mjs
  test/social-http-mysql.integration.mjs
```

## Configuration and startup

Use Node.js 24+ and MySQL 8.0/8.4. Copy `.env.example` to your private `.env` and
fill its `DB_*`, stable `RESEARCH_SESSION_SECRET` and OAuth settings. Do not commit
`.env`, provider secrets or database passwords.

```dotenv
HOST=127.0.0.1
PORT=8787
DB_HOST=127.0.0.1
DB_PORT=3306
DB_NAME=green_farm
DB_USER=green_farm_app
DB_PASSWORD=<secure value entered only on the server>
RESEARCH_SESSION_SECRET=<stable private random value of at least 32 bytes>
AUTH_PUBLIC_BASE_URL=https://accounts.your-domain.example
AUTH_TRUSTED_PROXY_IP=127.0.0.1
GOOGLE_OAUTH_CLIENT_ID=<Google Web OAuth client ID>
GOOGLE_OAUTH_CLIENT_SECRET=<Google Web OAuth client secret>
FACEBOOK_APP_ID=<Facebook app ID>
FACEBOOK_APP_SECRET=<Facebook app secret>
FACEBOOK_GRAPH_VERSION=v24.0
LEGACY_AUTH_ENABLED=false
```

Values in angle brackets and the example domain are placeholders, not working
credentials. Configure either or both providers with complete ID/secret pairs.
Missing providers return HTTP 503 `oauth_not_configured`; they never authenticate
a user automatically.

### Running only the existing local backend

The current environment has no supplied HTTPS domain or Facebook/Google OAuth
applications. You can still run and test the local backend without inventing
provider credentials: configure the actual local `DB_*` values and a stable
private `RESEARCH_SESSION_SECRET`, and leave `AUTH_PUBLIC_BASE_URL`,
`GOOGLE_OAUTH_CLIENT_ID`, `GOOGLE_OAUTH_CLIENT_SECRET`, `FACEBOOK_APP_ID` and
`FACEBOOK_APP_SECRET` empty. Keep `LEGACY_AUTH_ENABLED=false` for the new account
flow. Run the installation/migration/start commands below, then check the local
`/health` endpoint on port 8787.

In this local-only state the backend and MySQL can be healthy while account
endpoints return `503 oauth_not_configured`. An Android build without a real
HTTPS `AUTH_BASE_URL` also reports missing account configuration. Neither case
is a successful Facebook/Google login. No HTTP authentication bypass or fake
provider account is enabled. Complete the real domain, TLS and provider-console
configuration below before testing an actual provider/browser/device login.

```sh
cd backend
npm ci
npm run migrate
npm start
```

Migrations are additive and checksum-tracked. Never edit an applied migration or
drop/reset the database to resolve a checksum error. Legacy `gf_accounts`,
`gf_sessions` and `gf_compliance` remain intact. The old native protocol's LAN
`BACKEND_BASE_URL` in the repository-root `gradle.properties` is separate from
`AUTH_PUBLIC_BASE_URL`; the new Android `AUTH_BASE_URL` must match the latter.
The direct Node listener uses HTTP only behind a TLS reverse proxy. Do not set
the native app's authentication URL to the private HTTP listener.

`NODE_ENV=production` disables legacy anonymous authentication by default and
rejects `LEGACY_AUTH_ENABLED=true`. Keep it false for account deployments. No
Facebook/Google client secret is required or permitted in Android build config.

## HTTPS reverse proxy

Provision a real domain and valid certificate. Bind Node to loopback, firewall
its direct port, and configure the exact proxy peer in `AUTH_TRUSTED_PROXY_IP`.
For example, inside an existing Nginx HTTPS `server` block with your certificate:

```nginx
location /auth/ {
    proxy_pass http://127.0.0.1:8787;
    proxy_set_header Host $host;
    proxy_set_header X-Forwarded-Proto https;
    proxy_set_header X-Forwarded-For $remote_addr;
}
```

The proxy must overwrite these headers, never trust or append inbound values.
Only the exact configured peer may assert HTTPS. For container proxying use the
actual fixed proxy peer address rather than trusting an entire subnet. An
untrusted `X-Forwarded-Proto: https` does not enable authentication over HTTP.
Proxy access logs must record a sanitized path such as Nginx `$uri`, not
`$request` or `$request_uri`: provider callback queries contain one-use codes and
state. Do not log Authorization headers, bodies, tokens or provider responses.

Official outbound hosts are `accounts.google.com`, `oauth2.googleapis.com`,
`www.googleapis.com`, `www.facebook.com` and `graph.facebook.com`. Keep certificate
verification enabled. The website assets are served from `/auth/assets/`; there
are no external script/CDN dependencies. CSP and no-store/no-referrer headers
protect callback pages. Browsers may block automatic custom-scheme navigation,
so the success page always includes “Quay lại trò chơi”.

## Provider consoles and Android configuration

### Google

Create a **Web application** OAuth client on the Google Cloud OAuth console.
Configure the consent screen/test users and the exact authorized redirect:

```text
https://accounts.your-domain.example/auth/callback/google
```

Put this Web client's ID and secret in the server environment. This browser
flow is not the deprecated embedded WebView flow and does not request a Google
password in the game. The server checks the signed ID token against Google's
JWKS and validates issuer, audience/authorized party, expiry, issue time, nonce
and subject. Email is not used for identity matching.

### Facebook

Configure Facebook Login with web OAuth enabled, the actual app domain and:

```text
https://accounts.your-domain.example/auth/callback/facebook
```

Put the App ID and App Secret only in the server environment. Keep development
testers/roles configured until the app is approved for public users. Verify that
the configured Graph API version is supported by the provider. The server
exchanges the provider code, checks `debug_token` validity, application ID, user
ID and expiry, then independently checks `/me` with `appsecret_proof`.

### Android

The package/application ID remains `com.quanhoang.greenfarm3`, and the existing
signing key remains unchanged. Configure the app's `AUTH_BASE_URL` build property
to the same real HTTPS origin, then build and reinstall the generated debug APK.
The fallback callback is fixed:

```text
com.quanhoang.greenfarm3://oauth/callback
```

Custom-scheme interception does not yield an authenticated session without the
device's PKCE verifier. A verified HTTPS Android App Link is preferable once a
real domain is available: it additionally needs a reviewed manifest/config
change and `/.well-known/assetlinks.json` hosted on that domain. This checkout
does not falsely claim a verified App Link for an unprovided domain.

To inspect the **existing** debug certificate's SHA-1/SHA-256 fingerprints:

```sh
keytool -list -v -keystore .local/baseline-debug.keystore -alias androiddebugkey
```

For a Facebook Android platform entry, configure the same package, launcher
`com.quanhoang.greenfarm3.Start` and the key hash derived from that certificate:

```sh
keytool -exportcert -alias androiddebugkey -keystore .local/baseline-debug.keystore | openssl sha1 -binary | openssl base64
```

Use the release certificate's fingerprints for a separately signed deployment.
Do not create a replacement signing key just to make login work. Browser Web
OAuth credentials are separate from optional Google Android client IDs/key
hashes; provider secrets are never copied into the APK.

## API contract

All credential responses are JSON with `Cache-Control: no-store`. Error shape:
`{"error":{"code":"invalid_session","message":"..."}}`. Except browser/static
routes, send `Content-Type: application/json`; no credentials in query strings.

| Method/path | Request / response |
|---|---|
| `POST /auth/attempt` | `{provider,state,code_challenge,redirect_uri}` → 201 `{attempt_id,authorization_url,expires_in}` |
| `POST /auth/facebook`, `POST /auth/google` | Provider-specific attempt aliases; same state/PKCE/fixed redirect requirements |
| `GET /auth/browser/start?id=...` | Single-use attempt starts official provider consent |
| `GET /auth/callback/google`, `/auth/callback/facebook` | Server-only provider code exchange and app handoff page |
| `POST /auth/exchange` | `{code,code_verifier,redirect_uri}` → tokens and public user |
| `GET /auth/me` | `Authorization: Bearer <access_token>` → `{user}` |
| `POST /auth/refresh` | `{refresh_token}` → rotated tokens and the same user |
| `POST /auth/logout` | Bearer access token plus `{refresh_token}` → idempotent 204 |

Token response fields: `access_token`, `refresh_token`, `token_type: "Bearer"`,
`expires_in: 900`, `refresh_expires_in: 2592000`, and
`user: {game_id, provider, display_name}`. The provider's subject, provider tokens,
private internal database ID and client secrets are not returned to Android.

## MySQL schema and operational boundaries

- `users`: stable provider identity and unique `GF` game ID, names and timestamps.
- `gf_auth_attempts`: expiring app PKCE binding plus hashed provider state, nonce
  and ephemeral server-side provider verifier.
- `gf_auth_codes`: SHA-256 code hash, attempt/user binding, PKCE challenge,
  redirect binding, expiry and one-use consumption timestamp.
- `gf_auth_sessions`: only access/refresh hashes, expiries and revocation.
- `gf_auth_session_families` / `gf_auth_session_links`: revocation follows every
  refresh rotation; logging out with an older token pair also revokes an
  in-flight refresh descendant. Independent logins remain independent.

Unique `(provider, provider_user_id)` prevents duplicate identities. Accounts
from different providers are never merged merely because their emails match.
Transactions/row locks protect code consumption, concurrent identity creation,
refresh replay and logout/refresh races. No table stores a Facebook/Google
password or gameplay payload. Migration 003 preserves older session rows but
cannot reconstruct refresh lineage that schema 002 did not record; it does not
invent relationships between independent historical logins.

Plan a periodic, reviewed retention job for expired authentication rows. Delete
expired codes before their attempts and revoke/delete expired session families
through the auth-table foreign-key relationships; preserve users and all legacy
or gameplay data. No automatic cleanup/reset of player data is introduced here.

## Validation and remaining live configuration

```sh
npm test
# Dedicated MySQL test DB only; DB_NAME or TEST_DB_NAME must end with _test:
npm run test:mysql
```

The unit suite verifies Google signature/claims, Facebook token/identity checks,
malformed input, forged state, PKCE, one-use callbacks/codes, rate limits and
sanitized errors using explicit **test-only** provider transport/identity
fixtures. Real MySQL tests exercise additive migration, restart persistence,
stable IDs, races, rollback, refresh and logout through the actual store and
HTTP router. These checks do not establish successful login with a live Google
or Facebook account. That requires the actual HTTPS domain/certificate, valid
provider credentials/console redirects and a device/browser round trip.
