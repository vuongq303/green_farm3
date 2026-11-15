import { readFileSync } from 'node:fs';

const CALLBACK_PROTOCOL = 'com.quanhoang.greenfarm3:';
const CALLBACK_HOST = 'oauth';
const CALLBACK_PATH = '/callback';
const BASE64URL = /^[A-Za-z0-9_-]+$/u;

export const CSP = "default-src 'none'; base-uri 'none'; object-src 'none'; frame-ancestors 'none'; form-action 'none'; script-src 'self'; style-src 'self'; img-src 'self'; connect-src 'none'";

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
