/* Only first-party account forms run here. No provider OAuth or JS bridge. */
(() => {
  'use strict';
  const form = document.getElementById('account-form');
  const fields = document.getElementById('account-fields');
  const status = document.getElementById('account-status');
  const close = document.getElementById('close-account');
  const alternate = document.getElementById('account-mode-link');
  if (!form || !fields || !status || !close || !alternate) return;
  const mode = form.getAttribute('data-mode');
  const endpoint = form.getAttribute('data-endpoint');
  if (!['login', 'register'].includes(mode) || endpoint !== '/auth/' + mode) return;
  let expectedState;
  try {
    const exit = new URL(close.href);
    if (exit.protocol !== 'com.quanhoang.greenfarm3:' || exit.hostname !== 'oauth' ||
        exit.pathname !== '/callback' || exit.username || exit.password || exit.port || exit.hash ||
        exit.searchParams.get('error') !== 'access_denied' ||
        [...exit.searchParams.keys()].length !== 2) return;
    expectedState = exit.searchParams.get('state');
    if (!/^[A-Za-z0-9_-]{32,128}$/.test(expectedState || '')) return;
  } catch { return; }
  let busy = false;
  let closed = false;
  let controller = null;
  function clearPasswords() {
    form.querySelectorAll('input[type="password"]').forEach(input => { input.value = ''; });
  }
  function setBusy(value) {
    busy = value;
    fields.disabled = value;
    form.setAttribute('aria-busy', String(value));
    form.classList.toggle('is-busy', value);
    alternate.setAttribute('aria-disabled', String(value));
  }
  function appCode(value) {
    if (typeof value !== 'string' || value.length > 2048) throw new Error('invalid_callback');
    const target = new URL(value);
    const keys = [...target.searchParams.keys()];
    if (target.protocol !== 'com.quanhoang.greenfarm3:' || target.hostname !== 'oauth' ||
        target.pathname !== '/callback' || target.username || target.password || target.port || target.hash ||
        keys.length !== 2 || new Set(keys).size !== 2 || !keys.includes('code') || !keys.includes('state') ||
        target.searchParams.get('state') !== expectedState ||
        !/^[A-Za-z0-9_-]{43}$/.test(target.searchParams.get('code') || '')) throw new Error('invalid_callback');
    return target.href;
  }
  alternate.addEventListener('click', event => { if (busy) event.preventDefault(); });
  close.addEventListener('click', () => {
    closed = true;
    if (controller) controller.abort();
    clearPasswords();
  });
  window.addEventListener('pagehide', () => {
    closed = true;
    if (controller) controller.abort();
    clearPasswords();
  });
  form.addEventListener('submit', async event => {
    event.preventDefault();
    if (busy || closed) return;
    if (!form.reportValidity()) return;
    if (typeof AbortController !== 'function' || typeof fetch !== 'function') {
      status.textContent = 'WebView cần được cập nhật để đăng nhập an toàn. Hãy đóng trang và thử lại.';
      return;
    }
    const names = mode === 'register' ? ['csrf', 'attempt', 'username', 'email', 'password', 'password_confirmation'] :
      ['csrf', 'attempt', 'login', 'password'];
    const body = {};
    for (const name of names) body[name] = form.elements.namedItem(name).value;
    if (mode === 'register' && body.password !== body.password_confirmation) {
      status.textContent = 'Mật khẩu xác nhận chưa khớp.';
      return;
    }
    setBusy(true);
    status.textContent = mode === 'register' ? 'Đang tạo tài khoản...' : 'Đang đăng nhập...';
    controller = new AbortController();
    const requestController = controller;
    const timeout = window.setTimeout(() => requestController.abort(), 20000);
    try {
      const response = await fetch(endpoint, {
        method: 'POST', credentials: 'same-origin', cache: 'no-store', redirect: 'error',
        headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
        body: JSON.stringify(body), signal: requestController.signal,
      });
      const reply = await response.json();
      clearPasswords();
      if (closed) return;
      if (!response.ok) {
        status.textContent = reply && reply.error && typeof reply.error.message === 'string' ?
          reply.error.message.slice(0, 600) : 'Không thể đăng nhập. Hãy kiểm tra thông tin và thử lại.';
        return;
      }
      if (!reply || typeof reply !== 'object' || Array.isArray(reply) ||
          Object.keys(reply).length !== 1 || !Object.prototype.hasOwnProperty.call(reply, 'callback_url')) {
        throw new Error('invalid_response');
      }
      const target = appCode(reply.callback_url);
      status.textContent = 'Thành công! Đang quay lại trò chơi để hoàn tất đăng nhập...';
      closed = true;
      window.location.replace(target);
    } catch (error) {
      if (!closed) status.textContent = error && error.name === 'AbortError' ?
        'Kết nối hết thời gian. Hãy kiểm tra mạng và thử lại.' :
        'Không thể kết nối an toàn hoặc phản hồi không hợp lệ. Hãy thử lại.';
    } finally {
      window.clearTimeout(timeout);
      controller = null;
      clearPasswords();
      if (!closed) setBusy(false);
    }
  });
})();
