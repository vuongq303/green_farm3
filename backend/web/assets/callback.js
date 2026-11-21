/* The server supplies only a validated one-time code/state or error/state link. */
(() => {
  'use strict';
  const link = document.getElementById('return-to-game');
  if (!link) return;
  // Require the fixed route independently of the browser's application-URL parser.
  const prefix = 'com.quanhoang.greenfarm3://oauth/callback?';
  const target = link.getAttribute('href');
  if (!target || target.length > 2048 || !target.startsWith(prefix)) return;
  const query = target.slice(prefix.length);
  if (!query || /[^A-Za-z0-9_=&-]/.test(query)) return;
  const keys = [...new URLSearchParams(query).keys()];
  if (keys.length !== 2 || new Set(keys).size !== 2 || !keys.includes('state') ||
      !keys.every(key => ['code', 'error', 'state'].includes(key))) return;
  const status = document.getElementById('return-status');
  if (status) status.textContent = 'Đang mở trò chơi… Nếu trình duyệt chặn, hãy chạm “Quay lại trò chơi”.';
  // Keep the fallback visible: Android browsers may require a user gesture.
  window.setTimeout(() => {
    if (document.visibilityState !== 'hidden') window.location.replace(target);
  }, 350);
})();
