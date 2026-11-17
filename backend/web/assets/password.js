/* Only first-party account forms run here. No provider OAuth or JS bridge. */
(() => {
  'use strict';
  const form = document.getElementById('account-form');
  const fields = document.getElementById('account-fields');
  const status = document.getElementById('account-status');
  const close = document.getElementById('close-account');
  const alternate = document.getElementById('account-mode-link');
  const returnToGame = document.getElementById('return-to-game');
  const returnStatus = document.getElementById('return-status');
  if (!form || !fields || !status || !close || !alternate || !returnToGame || !returnStatus) return;
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
  let completed = false;
  let requestId = 0;
  let controller = null;
  let handoffTimer = null;
  function showStatus(message, kind = 'error') {
    status.textContent = message;
    status.setAttribute('data-state', kind);
    // Bring persistent feedback above the mobile keyboard without clearing input.
    if (kind !== 'loading') {
      status.focus({ preventScroll: true });
      status.scrollIntoView({ block: 'nearest' });
      // Native browser alert for explicit success/failure; keep the page status
      // for accessibility and browsers that suppress alert dialogs.
      try { if (typeof window.alert === 'function') window.alert(message); } catch { /* Inline fallback. */ }
    }
  }
  function publicError(response, reply) {
    const code = reply && reply.error && reply.error.code;
    const messages = {
      account_exists: 'Tên tài khoản hoặc email đã được sử dụng.',
      username_exists: 'Tên tài khoản đã được sử dụng. Hãy chọn tên khác.',
      email_exists: 'Email đã được đăng ký. Hãy dùng email khác hoặc đăng nhập.',
      invalid_credentials: 'Tên tài khoản/email hoặc mật khẩu không đúng.',
      invalid_registration: 'Thông tin đăng ký không hợp lệ. Kiểm tra tên tài khoản, email và mật khẩu 12–128 ký tự.',
      csrf_failed: 'Phiên biểu mẫu không hợp lệ hoặc đã hết hạn. Hãy mở lại đăng nhập từ Green Farm 3.',
      attempt_expired: 'Phiên xác thực đã hết hạn. Hãy mở lại đăng nhập từ Green Farm 3.',
      rate_limited: 'Bạn đã gửi quá nhiều yêu cầu. Vui lòng chờ một lúc rồi thử lại.',
    };
    if (Object.prototype.hasOwnProperty.call(messages, code)) return messages[code];
    if (response.status === 403) return messages.csrf_failed;
    if (response.status === 429) return messages.rate_limited;
    if (response.status === 409 && mode === 'register') return messages.account_exists;
    if (response.status === 401) return messages.invalid_credentials;
    if (response.status === 400 && mode === 'register') return messages.invalid_registration;
    if (response.status >= 500) return 'Máy chủ tạm thời không thể xử lý yêu cầu. Hãy thử lại sau.';
    return mode === 'register' ? 'Không thể đăng ký. Hãy kiểm tra thông tin và thử lại.' :
      'Không thể đăng nhập. Hãy kiểm tra thông tin và thử lại.';
  }
  function cancelHandoff() {
    if (handoffTimer !== null) window.clearTimeout(handoffTimer);
    handoffTimer = null;
  }
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
  returnToGame.addEventListener('click', cancelHandoff);
  alternate.addEventListener('click', event => { if (busy) event.preventDefault(); });
  close.addEventListener('click', () => {
    closed = true;
    ++requestId;
    if (controller) controller.abort();
    cancelHandoff();
    clearPasswords();
  });
  window.addEventListener('pagehide', () => {
    closed = true;
    ++requestId;
    if (controller) controller.abort();
    cancelHandoff();
    clearPasswords();
  });
  // Browser back/forward cache can restore this exact form without reloading.
  // Ignore any response from the aborted visit before accepting another submit.
  window.addEventListener('pageshow', event => {
    if (!event.persisted || completed) return;
    closed = false;
    clearPasswords();
    setBusy(false);
    status.textContent = '';
    status.removeAttribute('data-state');
  });
  form.addEventListener('submit', async event => {
    event.preventDefault();
    if (busy || closed) return;
    const requiredNames = mode === 'register' ? ['username', 'email', 'password', 'password_confirmation'] :
      ['login', 'password'];
    const labels = { username: 'tên tài khoản', email: 'email', login: 'tên tài khoản hoặc email',
      password: 'mật khẩu', password_confirmation: 'xác nhận mật khẩu' };
    for (const name of requiredNames) {
      const input = form.elements.namedItem(name);
      if (!input.value || (!name.startsWith('password') && !input.value.trim())) {
        showStatus('Vui lòng nhập ' + labels[name] + '.');
        return;
      }
      if (!input.checkValidity()) {
        showStatus(name === 'username' ? 'Tên tài khoản cần 3–32 chữ không dấu, số hoặc dấu gạch dưới.' :
          name === 'email' ? 'Vui lòng nhập địa chỉ email hợp lệ.' :
          name === 'password' && mode === 'register' ? 'Mật khẩu cần có 12–128 ký tự.' :
          'Vui lòng kiểm tra ' + labels[name] + '.');
        return;
      }
    }
    if (typeof AbortController !== 'function' || typeof fetch !== 'function') {
      showStatus('Hãy cập nhật trình duyệt để đăng nhập an toàn, rồi mở lại đăng nhập từ trò chơi.');
      return;
    }
    const names = mode === 'register' ? ['csrf', 'attempt', 'username', 'email', 'password', 'password_confirmation'] :
      ['csrf', 'attempt', 'login', 'password'];
    const body = {};
    for (const name of names) body[name] = form.elements.namedItem(name).value;
    if (mode === 'register' && body.password !== body.password_confirmation) {
      showStatus('Mật khẩu xác nhận chưa khớp.');
      return;
    }
    setBusy(true);
    showStatus('Đang xử lý... ' + (mode === 'register' ? 'Đang tạo tài khoản...' : 'Đang đăng nhập...'), 'loading');
    controller = new AbortController();
    const requestController = controller;
    const activeRequest = ++requestId;
    let timeout;
    const deadline = new Promise((resolve, reject) => {
      timeout = window.setTimeout(() => {
        requestController.abort();
        reject(Object.assign(new Error(), { name: 'TimeoutError' }));
      }, 20000);
    });
    try {
      const { response, reply } = await Promise.race([(async () => {
        const response = await fetch(endpoint, {
          method: 'POST', credentials: 'same-origin', cache: 'no-store', redirect: 'error',
          headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
          body: JSON.stringify(body), signal: requestController.signal,
        });
        let reply;
        try { reply = await response.json(); }
        catch { if (response.ok) throw new Error('invalid_response'); }
        return { response, reply };
      })(), deadline]);
      if (closed || activeRequest !== requestId) return;
      clearPasswords();
      if (!response.ok) {
        showStatus(publicError(response, reply));
        return;
      }
      if (response.status !== (mode === 'register' ? 201 : 200) ||
          !reply || typeof reply !== 'object' || Array.isArray(reply) ||
          Object.keys(reply).length !== 1 || !Object.prototype.hasOwnProperty.call(reply, 'callback_url')) {
        throw new Error('invalid_response');
      }
      const target = appCode(reply.callback_url);
      showStatus((mode === 'register' ? 'Đăng ký thành công!' : 'Đăng nhập thành công!') +
        ' Đang quay lại Green Farm 3 để hoàn tất xác thực trong trò chơi...', 'success');
      // An async fetch may outlive the browser's user-activation window. Keep a
      // real user-gesture link available if Android blocks automatic navigation.
      returnToGame.href = target;
      returnToGame.hidden = false;
      returnStatus.hidden = false;
      close.href = target;
      form.hidden = true;
      alternate.hidden = true;
      completed = true;
      closed = true;
      // Give success feedback a paint before leaving; never retry automatically.
      handoffTimer = window.setTimeout(() => {
        handoffTimer = null;
        if (document.visibilityState === 'hidden') return;
        // A failed top-level custom-scheme navigation can replace this page with
        // an error document. Isolate the one automatic attempt so feedback and
        // the user-gesture return link survive when the browser blocks it.
        try {
          const handoff = document.createElement('iframe');
          handoff.hidden = true;
          handoff.title = 'Mở Green Farm 3';
          handoff.setAttribute('aria-hidden', 'true');
          handoff.setAttribute('sandbox', 'allow-top-navigation-to-custom-protocols');
          handoff.src = target;
          document.body.appendChild(handoff);
        } catch { /* The manual link remains usable. */ }
      }, 350);
    } catch (error) {
      if (!closed && activeRequest === requestId) showStatus(error && ['AbortError', 'TimeoutError'].includes(error.name) ?
        'Kết nối hết thời gian. Hãy kiểm tra mạng và thử lại.' :
        'Không thể kết nối an toàn hoặc phản hồi không hợp lệ. Hãy thử lại.');
    } finally {
      window.clearTimeout(timeout);
      if (controller === requestController) controller = null;
      if (activeRequest === requestId) {
        clearPasswords();
        if (!closed) setBusy(false);
      }
    }
  });
})();
