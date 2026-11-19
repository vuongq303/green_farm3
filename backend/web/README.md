# Authentication pages

The Android game keeps its own original account UI. The right button opens the
first-party login/register website in the device's external browser; Facebook
continues to use its official external-browser OAuth flow. Existing Google
identities and server OAuth routes remain intact, but the game no longer offers
Google on that button. Website authentication uses no Android WebView or JS bridge.

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

## Browser account forms

The game first creates and securely retains a PKCE-bound password attempt, then opens
`/auth/web/login?attempt=...` and the matching `/auth/web/register` page on the
configured HTTPS origin. A short-lived Secure/HttpOnly/SameSite cookie binds the
form to that attempt. Each form carries a CSRF token; the server verifies it and
the Origin header. Login posts `{csrf, attempt, login, password}` to
`/auth/login`. Registration posts `{csrf, attempt, username, email, password,
password_confirmation}` to `/auth/register`.

Password form script and stylesheet URLs include SHA-256 versions of their
served bytes. After a backend restart/deploy, new forms therefore fetch the new
assets even if a browser cached an earlier release for an hour. The existing
OAuth handoff page URLs are unchanged.

`password.js` validates required fields, submits JSON and disables duplicate
submissions. While the request runs, the submit button shows its loading label
and spinner. The 20-second deadline covers both the response and JSON body.
Validation results, API errors and successes appear in one themed `alertdialog`.
Its title and description label the dialog for assistive technology. The dialog
focuses its action, keeps the form inert and restores focus to the relevant input
or submit button when an error is dismissed. Errors stay open until the user
presses the action button or Escape. Known error codes and HTTP statuses map to
predefined Vietnamese messages, including connection and non-JSON proxy errors.
Passwords clear after a request or when leaving. Username and email remain
available for retry. The browser page uses a same-origin form and the server is
the authority for validation and account uniqueness.

Success returns only `{callback_url}` with a 60-second one-use authorization code and
the pending state. The script validates the fixed app callback, checks the
pending state and requires HTTP 201 for registration or HTTP 200 for login.
After opening the registration or login success dialog, it tries the app deep
link once after 350 ms so the browser can paint the dialog first. The
attempt uses a hidden sandboxed iframe restricted by CSP to the app scheme
(custom protocols have opaque origins), with JavaScript enforcing the exact
callback host/path and code/state; a blocked external-protocol navigation therefore cannot replace the
form document with a browser error page. The “Vào trò chơi” action in the dialog
is an ordinary top-level link and works with a user gesture. Android checks
the callback against its retained state before exchanging the code using its
encrypted PKCE verifier, including when Android has recreated the app process.
The page receives a one-use code and state. After success the credential form is
hidden and the dialog keeps its return action available for browsers that block
the automatic launch. Before success the page close button returns an
`access_denied` callback.
If browser back/forward cache restores an unfinished form, aborted responses
from the previous visit cannot redirect it or clear newly entered credentials.

Real HTTPS configuration and Android device tests are still required. Rendering
a form or passing host tests does not prove a live Android/browser login round trip.
