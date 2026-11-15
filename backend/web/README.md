# Authentication pages

The Android game keeps its own original account UI. The right button opens the
first-party login/register website in a restricted Android WebView; Facebook
continues to use its official external-browser OAuth flow. Existing Google
identities and server OAuth routes remain intact, but the game no longer offers
Google on that button. Provider OAuth is never loaded into this WebView.

`pages.mjs` exports:

- `renderSuccess(callbackUrl)` → `{ html, csp }`.
- `renderError({ message, returnUrl? })` → `{ html, csp }`.
- `renderPasswordForm({ mode, csrfToken, attemptId, closeUrl })` → `{ html, csp }`,
  where mode is `login` or `register`.
- `PAGE_HEADERS` for HTML responses and `ASSETS`, an explicit filename-to-body /
  content-type map served at `/auth/assets/<filename>`.

Use only public, predefined messages for `renderError`; never pass raw provider
responses or exception messages. Both page types disable caching and referrers.
The CSP permits only same-origin CSS, JavaScript and images. Password forms also
permit same-origin JSON requests; native form submissions remain blocked. The original game
launcher icon is copied unchanged from `app/src/main/res/mipmap-xhdpi-v4`.

## Deployment

Configure the backend's public authentication origin to an HTTPS domain. Register
its exact provider callback URLs in the Facebook and Google application settings
as documented by the backend auth configuration. Client secrets stay on the
server. A reachable LAN HTTP server alone cannot complete this production flow.

The return URL is fixed to
`com.quanhoang.greenfarm3://oauth/callback`. Only one authorization code plus
`state`, or one public error code plus `state`, is accepted. Access tokens,
refresh tokens and passwords cannot be placed in the return URL. The Android app
still validates state and exchanges the short-lived code using its PKCE verifier;
rendering this page does not authenticate the app by itself.

After verification, the page tries to open the app and keeps a visible
“Quay lại trò chơi” button for browsers that require a user gesture. If the
provider was canceled, the error page can return the validated error/state pair.
If state cannot be trusted, omit the return URL and instruct the user to retry
from the game. No pages read or modify gameplay SQLite or farm data.

## Embedded account forms

The game first creates a PKCE-bound password attempt. It loads only
`/auth/web/login?attempt=...` and the matching `/auth/web/register` page on the
configured HTTPS origin. A short-lived Secure/HttpOnly/SameSite cookie binds the
form to that attempt. Each form carries a CSRF token; the server verifies it and
the Origin header. Login posts `{csrf, attempt, login, password}` to
`/auth/login`. Registration posts `{csrf, attempt, username, email, password,
password_confirmation}` to `/auth/register`.

`password.js` submits JSON, disables duplicate submissions, bounds each request
to 20 seconds, and shows public server errors as text. Password fields are cleared
after responses and when closing/leaving. No password, token or user profile is
stored in browser storage or sent through a JavaScript bridge. Registration
accepts an ASCII username of 3–32 letters/digits/underscores, an email, and a
12–128 character password with matching confirmation; the server remains the
authority for validation and account uniqueness.

Success returns only `{callback_url}` with a short-lived authorization code and
the pending state. The script validates the fixed app callback, checks the
pending state and navigates internally; Android intercepts that navigation,
checks state again and exchanges the code using its encrypted PKCE verifier.
The page never receives app access/refresh tokens and cannot authenticate merely
by supplying a GAME_ID. Its close button returns an `access_denied` callback.
The WebView is closed after a validated handoff; it does not launch Chrome.

Real HTTPS configuration and Android device tests are still required. Rendering
a form or passing host tests does not prove a live WebView login round trip.
