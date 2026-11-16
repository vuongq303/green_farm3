import { readFileSync } from 'node:fs';

const CALLBACK_PROTOCOL = 'com.quanhoang.greenfarm3:';
const CALLBACK_HOST = 'oauth';
const CALLBACK_PATH = '/callback';
const BASE64URL = /^[A-Za-z0-9_-]+$/u;

export const CSP = "default-src 'none'; base-uri 'none'; object-src 'none'; frame-ancestors 'none'; form-action 'none'; script-src 'self'; style-src 'self'; img-src 'self'; connect-src 'none'";
export const PASSWORD_CSP = CSP.replace("connect-src 'none'", "connect-src 'self'");

/** Callback pages must never be cached or leak their provider query in Referer. */
export const PAGE_HEADERS = Object.freeze({
  'Content-Type': 'text/html; charset=utf-8',
  'Cache-Control': 'no-store',
  Pragma: 'no-cache',
  'Referrer-Policy': 'no-referrer',
  'X-Content-Type-Options': 'nosniff',
  'X-Frame-Options': 'DENY',
  'Content-Security-Policy': CSP,
});

const asset = (filename, contentType) => Object.freeze({
  body: readFileSync(new URL('./assets/' + filename, import.meta.url)),
  contentType,
});

// An explicit map avoids resolving an HTTP pathname as a filesystem path.
export const ASSETS = new Map([
  ['styles.css', asset('styles.css', 'text/css; charset=utf-8')],
  ['callback.js', asset('callback.js', 'application/javascript; charset=utf-8')],
  ['password.js', asset('password.js', 'application/javascript; charset=utf-8')],
  ['green-farm-3.png', asset('green-farm-3.png', 'image/png')],
]);

export function escapeHtml(value) {
  return String(value).replace(/[&<>"']/gu, character => ({
    '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;',
  })[character]);
}

/** Only the app's fixed code/state (or error/state) callback is navigable. */
export function validateCallback(value) {
  let url;
  try { url = new URL(value); } catch { throw new TypeError('Invalid app callback'); }
  if (typeof value !== 'string' || value.length > 2048 ||
      url.protocol !== CALLBACK_PROTOCOL || url.hostname !== CALLBACK_HOST ||
      url.pathname !== CALLBACK_PATH || url.port || url.username || url.password || url.hash) {
    throw new TypeError('Invalid app callback');
  }
  const keys = [...url.searchParams.keys()];
  const code = url.searchParams.get('code');
  const error = url.searchParams.get('error');
  const state = url.searchParams.get('state');
  if (keys.length !== 2 || new Set(keys).size !== 2 ||
      !keys.every(key => ['code', 'error', 'state'].includes(key)) ||
      !state || state.length < 32 || state.length > 128 || !BASE64URL.test(state) ||
      (code !== null) === (error !== null) ||
      (code !== null && (code.length < 32 || code.length > 256 || !BASE64URL.test(code))) ||
      (error !== null && !/^[a-z][a-z0-9_]{0,63}$/u.test(error))) {
    throw new TypeError('Invalid app callback');
  }
  return url.href;
}

function page({ title, heading, message, callbackUrl, success }) {
  const callback = callbackUrl ? validateCallback(callbackUrl) : null;
  const safeMessage = String(message).slice(0, 600);
  const html = `<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="referrer" content="no-referrer">
  <meta name="robots" content="noindex, nofollow">
  <title>${escapeHtml(title)} · Green Farm 3</title>
  <link rel="stylesheet" href="/auth/assets/styles.css">
  ${callback ? '<script src="/auth/assets/callback.js" defer></script>' : ''}
</head>
<body>
  <main class="farm-panel" aria-labelledby="page-heading">
    <img class="game-logo" src="/auth/assets/green-farm-3.png" width="96" height="96" alt="Green Farm 3">
    <p class="game-title">Green Farm 3</p>
    <h1 id="page-heading">${escapeHtml(heading)}</h1>
    <p class="message" role="status">${escapeHtml(safeMessage)}</p>
    ${callback ? `<a id="return-to-game" class="farm-button" href="${escapeHtml(callback)}">Quay lại trò chơi</a>
    <p id="return-status" class="hint" aria-live="polite">Nếu trò chơi chưa tự mở, hãy chạm nút ở trên.</p>
    <noscript><p class="hint">Chạm “Quay lại trò chơi” để tiếp tục.</p></noscript>` :
    '<p class="hint">Bạn có thể đóng trang này và thử lại từ Green Farm 3.</p>'}
    ${success ? '<p class="account-note">Trò chơi sẽ kiểm tra mã xác thực trước khi đăng nhập. Đăng nhập không đồng bộ nông trại.</p>' : ''}
  </main>
  <footer>Green Farm 3 · Tài khoản trò chơi</footer>
</body>
</html>`;
  return { html, csp: CSP };
}

export function renderSuccess(callbackUrl) {
  const validated = validateCallback(callbackUrl);
  if (!new URL(validated).searchParams.has('code')) throw new TypeError('Success requires an authorization code');
  return page({
    title: 'Xác thực thành công',
    heading: 'Xác thực thành công!',
    message: 'Đã xác thực tài khoản với nhà cung cấp. Đang quay lại Green Farm 3 để hoàn tất đăng nhập.',
    callbackUrl: validated,
    success: true,
  });
}

/** message must be a public, predefined error; never a raw provider response. */
export function renderError({ message = 'Không thể hoàn tất đăng nhập. Hãy quay lại trò chơi và thử lại.', returnUrl } = {}) {
  if (returnUrl && !new URL(validateCallback(returnUrl)).searchParams.has('error')) {
    throw new TypeError('Error requires an error callback');
  }
  return page({
    title: 'Chưa đăng nhập',
    heading: 'Chưa thể đăng nhập',
    message,
    callbackUrl: returnUrl,
    success: false,
  });
}

/** Own-account credentials stay in this HTTPS form; only an app code leaves it. */
export function renderPasswordForm({ mode, csrfToken, attemptId, closeUrl } = {}) {
  if (!['login', 'register'].includes(mode) ||
      typeof csrfToken !== 'string' || !/^[A-Za-z0-9_-]{43}$/u.test(csrfToken) ||
      typeof attemptId !== 'string' || !/^[A-Za-z0-9_-]{43}$/u.test(attemptId)) {
    throw new TypeError('Invalid password form context');
  }
  const close = validateCallback(closeUrl);
  if (new URL(close).searchParams.get('error') !== 'access_denied') {
    throw new TypeError('Form close requires an access_denied callback');
  }
  const register = mode === 'register';
  const heading = register ? 'Đăng ký tài khoản' : 'Đăng nhập tài khoản';
  const target = '/auth/' + mode;
  const alternate = '/auth/web/' + (register ? 'login' : 'register') + '?attempt=' + attemptId;
  const fields = register ? `
      <label for="account-username">Tên tài khoản</label>
      <input id="account-username" name="username" type="text" required minlength="3" maxlength="32" pattern="[A-Za-z0-9_]{3,32}" autocomplete="username" autocapitalize="none" spellcheck="false" aria-describedby="username-hint">
      <p id="username-hint" class="field-hint">3–32 ký tự: chữ không dấu, số hoặc dấu gạch dưới.</p>
      <label for="account-email">Email</label>
      <input id="account-email" name="email" type="email" required maxlength="254" autocomplete="email" autocapitalize="none" spellcheck="false">
      <label for="account-password">Mật khẩu</label>
      <input id="account-password" name="password" type="password" required minlength="12" maxlength="128" autocomplete="new-password" aria-describedby="password-hint">
      <p id="password-hint" class="field-hint">12–128 ký tự. Bạn có thể dùng dấu cách và dán mật khẩu.</p>
      <label for="account-password-confirmation">Xác nhận mật khẩu</label>
      <input id="account-password-confirmation" name="password_confirmation" type="password" required minlength="12" maxlength="128" autocomplete="new-password">` : `
      <label for="account-login">Tên tài khoản hoặc email</label>
      <input id="account-login" name="login" type="text" required minlength="3" maxlength="254" autocomplete="username" autocapitalize="none" spellcheck="false">
      <label for="account-password">Mật khẩu</label>
      <input id="account-password" name="password" type="password" required maxlength="128" autocomplete="current-password">`;
  const html = `<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <meta name="referrer" content="no-referrer">
  <meta name="robots" content="noindex, nofollow">
  <title>${heading} · Green Farm 3</title>
  <link rel="stylesheet" href="/auth/assets/styles.css">
  <script src="/auth/assets/password.js" defer></script>
</head>
<body class="password-page">
  <main class="farm-panel password-panel" aria-labelledby="page-heading">
    <a id="close-account" class="close-account" href="${escapeHtml(close)}" aria-label="Đóng và quay về trò chơi">× <span>Đóng</span></a>
    <header class="password-brand">
      <img class="game-logo" src="/auth/assets/green-farm-3.png" width="64" height="64" alt="">
      <p class="game-title">Green Farm 3</p>
    </header>
    <h1 id="page-heading">${heading}</h1>
    <p class="password-intro">${register ? 'Tạo tài khoản để bắt đầu chuyến phiêu lưu nông trại.' : 'Chào bạn! Đăng nhập để tiếp tục vào trò chơi.'}</p>
    <form id="account-form" data-endpoint="${target}" data-mode="${mode}" method="post" action="${target}">
      <fieldset id="account-fields">
        <input type="hidden" name="csrf" value="${escapeHtml(csrfToken)}">
        <input type="hidden" name="attempt" value="${escapeHtml(attemptId)}">${fields}
        <button id="account-submit" class="farm-button" type="submit"><span class="submit-label">${register ? 'Đăng ký' : 'Đăng nhập'}</span><span class="form-spinner" aria-hidden="true"></span></button>
      </fieldset>
    </form>
    <p id="account-status" class="form-status" role="status" aria-live="polite"></p>
    <a id="return-to-game" class="farm-button" hidden>Quay lại Green Farm 3</a>
    <p id="return-status" class="hint" hidden>Nếu trình duyệt chưa tự mở trò chơi, hãy chạm nút ở trên. Mã xác thực chỉ có hiệu lực trong thời gian ngắn.</p>
    <a id="account-mode-link" class="account-mode-link" href="${escapeHtml(alternate)}">${register ? 'Đã có tài khoản? Đăng nhập' : 'Chưa có tài khoản? Đăng ký'}</a>
    <p class="account-note">Nông trại hiện được lưu trên thiết bị này. Đăng nhập không đồng bộ dữ liệu nông trại.</p>
    <noscript><p class="form-status">Trang đăng nhập cần JavaScript. Hãy đóng trang và thử lại từ trò chơi.</p></noscript>
  </main>
  <footer>Green Farm 3 · Tài khoản trò chơi</footer>
</body>
</html>`;
  return { html, csp: PASSWORD_CSP };
}
