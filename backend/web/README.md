# Browser authentication pages

The Android game keeps its own original account UI. This directory supplies only
the browser handoff pages after the official Facebook or Google authorization
flow. There are no local password forms and no simulated provider logins.

`pages.mjs` exports:

- `renderSuccess(callbackUrl)` → `{ html, csp }`.
- `renderError({ message, returnUrl? })` → `{ html, csp }`.
- `PAGE_HEADERS` for HTML responses and `ASSETS`, an explicit filename-to-body /
  content-type map served at `/auth/assets/<filename>`.

Use only public, predefined messages for `renderError`; never pass raw provider
responses or exception messages. Both page types disable caching and referrers.
The CSP permits only same-origin CSS, JavaScript and images. The original game
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
