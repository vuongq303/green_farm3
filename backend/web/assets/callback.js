/* The server supplies only a validated one-time code/state or error/state link. */
(() => {
  'use strict';
  const link = document.getElementById('return-to-game');
  if (!link) return;
  let target;
  try {
    target = new URL(link.href);
    const keys = [...target.searchParams.keys()];
    if (target.protocol !== 'com.quanhoang.greenfarm3:' || target.hostname !== 'oauth' ||
        target.pathname !== '/callback' || target.username || target.password || target.port || target.hash ||
        keys.length !== 2 || new Set(keys).size !== 2 || !keys.includes('state') ||
        !keys.every(key => ['code', 'error', 'state'].includes(key))) return;
  } catch { return; }
  const status = document.getElementById('return-status');
  if (status) status.textContent = 'Đang mở trò chơi… Nếu trình duyệt chặn, hãy chạm “Quay lại trò chơi”.';
  // Keep the fallback visible: Android browsers may require a user gesture.
  window.setTimeout(() => {
    if (document.visibilityState !== 'hidden') window.location.replace(target.href);
  }, 350);
})();
