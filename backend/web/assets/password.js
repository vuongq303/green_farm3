/* Only first-party account forms run here. No provider OAuth or JS bridge. */
(() => {
  'use strict';
  const form = document.getElementById('account-form');
  const fields = document.getElementById('account-fields');
  const submit = document.getElementById('account-submit');
  const submitLabel = document.getElementById('submit-label');
  const close = document.getElementById('close-account');
  const alternate = document.getElementById('account-mode-link');
  const notice = document.getElementById('account-notice');
  const noticeTitle = document.getElementById('notice-title');
  const noticeMessage = document.getElementById('notice-message');
  const dismissNotice = document.getElementById('notice-dismiss');
  const returnToGame = document.getElementById('return-to-game');
  if (!form || !fields || !submit || !submitLabel || !close || !alternate || !notice || !noticeTitle ||
      !noticeMessage || !dismissNotice || !returnToGame) return;
  if (typeof notice.showModal !== 'function') {
    submit.disabled = true;
    submitLabel.textContent = 'Hãy cập nhật trình duyệt';
    return;
  }
  const mode = form.getAttribute('data-mode');
  const endpoint = form.getAttribute('data-endpoint');
  if (!['login', 'register'].includes(mode) || endpoint !== '/auth/' + mode) return;
  const callbackPrefix = 'com.quanhoang.greenfarm3://oauth/callback?';
  function callbackParams(value) {
    // Older WebView versions parse the host of an application scheme as part
    // of its path. Require the canonical app route before reading its query.
    if (typeof value !== 'string' || value.length > 2048 || !value.startsWith(callbackPrefix)) return null;
    const query = value.slice(callbackPrefix.length);
    return query && !/[^A-Za-z0-9_=&-]/.test(query) ? new URLSearchParams(query) : null;
  }
  const exit = callbackParams(close.getAttribute('href'));
  if (!exit || exit.get('error') !== 'access_denied' || [...exit.keys()].length !== 2) return;
  const expectedState = exit.get('state');
  if (!/^[A-Za-z0-9_-]{32,128}$/.test(expectedState || '')) return;
  let busy = false;
  let closed = false;
  let completed = false;
  let requestId = 0;
  let controller = null;
  let handoffTimer = null;
  let restoreFocus = null;
  const idleLabel = submitLabel.textContent;
  const feedback = {
    username: { title: 'Tên tài khoản đã tồn tại', message: 'Tên tài khoản này đã được sử dụng. Vui lòng chọn tên khác.', field: 'username', kind: 'warning' },
    email: { title: 'Email đã được đăng ký', message: 'Email này đã được sử dụng. Vui lòng nhập email khác.', field: 'email', kind: 'warning', label: 'Đã hiểu' },
    account_exists: { title: 'Thông tin đã được sử dụng', message: 'Tên tài khoản hoặc email đã được sử dụng.', field: 'username', kind: 'warning' },
    both: { title: 'Thông tin đã được sử dụng', message: 'Tên tài khoản và email đã được sử dụng. Vui lòng chọn tên và email khác.', field: 'username', kind: 'warning' },
    invalid_credentials: { title: 'Đăng nhập thất bại', message: 'Tên tài khoản hoặc mật khẩu không chính xác.', field: 'password' },
    invalid_registration: { title: 'Thông tin đăng ký không hợp lệ', message: 'Kiểm tra tên tài khoản, email và mật khẩu 12–128 ký tự.', field: 'username', kind: 'warning' },
    csrf_failed: { title: 'Phiên biểu mẫu không hợp lệ', message: 'Hãy mở lại đăng nhập từ Green Farm 3.', kind: 'warning', label: 'Đã hiểu' },
    attempt_expired: { title: 'Phiên xác thực đã hết hạn', message: 'Hãy mở lại đăng nhập từ Green Farm 3.', kind: 'warning', label: 'Đã hiểu' },
    rate_limited: { title: 'Quá nhiều yêu cầu', message: 'Vui lòng chờ một lúc rồi thử lại.', kind: 'warning', label: 'Đã hiểu' },
    unavailable: { title: 'Máy chủ tạm thời gặp lỗi', message: 'Máy chủ tạm thời không thể xử lý yêu cầu. Hãy thử lại sau.' },
    connection: { title: 'Không thể kết nối', message: 'Không thể kết nối với máy chủ. Vui lòng kiểm tra Internet và thử lại.' },
    invalid_response: { title: 'Phản hồi không hợp lệ', message: 'Không thể xác thực phản hồi của máy chủ. Vui lòng thử lại.' },
  };
  function showNotice({ title, message, kind = 'error', label = 'Thử lại', field }, target) {
    if (notice.open) return;
    noticeTitle.textContent = title;
    noticeMessage.textContent = message;
    notice.setAttribute('data-kind', kind);
    dismissNotice.textContent = label;
    dismissNotice.hidden = Boolean(target);
    returnToGame.hidden = !target;
    if (target) returnToGame.href = target;
    restoreFocus = (field && form.elements.namedItem(field)) || submit;
    notice.showModal();
    (target ? returnToGame : dismissNotice).focus();
  }
  function showValidation(message, field) {
    showNotice({ title: 'Kiểm tra thông tin', message, field, kind: 'warning' });
  }
  function closeNotice() {
    notice.close();
    if (restoreFocus) restoreFocus.focus();
    restoreFocus = null;
  }
  dismissNotice.addEventListener('click', closeNotice);
  notice.addEventListener('cancel', event => {
    event.preventDefault();
    if (!completed) closeNotice();
  });
  // Each state has one action. Keep keyboard focus there while the page is inert.
  notice.addEventListener('keydown', event => {
    if (event.key !== 'Tab' || event.ctrlKey || event.altKey || event.metaKey) return;
    event.preventDefault();
    (completed ? returnToGame : dismissNotice).focus();
  });
  function publicError(response, reply) {
    const code = reply && reply.error && reply.error.code;
    if (code === 'account_exists' && mode === 'register') {
      const conflicts = reply.error.conflicts;
      if (Array.isArray(conflicts) && conflicts.length >= 1 && conflicts.length <= 2 &&
          new Set(conflicts).size === conflicts.length &&
          conflicts.every(field => field === 'username' || field === 'email')) {
        return feedback[conflicts.length === 2 ? 'both' : conflicts[0]];
      }
    }
    if (['account_exists', 'invalid_credentials', 'invalid_registration', 'csrf_failed', 'attempt_expired', 'rate_limited'].includes(code)) {
      return feedback[code];
    }
    if (response.status === 403) return feedback.csrf_failed;
    if (response.status === 429) return feedback.rate_limited;
    if (response.status === 409 && mode === 'register') return feedback.account_exists;
    if (response.status === 401) return feedback.invalid_credentials;
    if (response.status === 400 && mode === 'register') return feedback.invalid_registration;
    if (response.status >= 500) return feedback.unavailable;
    return { title: mode === 'register' ? 'Không thể đăng ký' : 'Không thể đăng nhập',
      message: 'Hãy kiểm tra thông tin và thử lại.' };
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
    submitLabel.textContent = value ? (mode === 'register' ? 'Đang đăng ký…' : 'Đang đăng nhập…') : idleLabel;
  }
  function appCode(value) {
    const params = callbackParams(value);
    if (!params || [...params.keys()].length !== 2 || params.get('state') !== expectedState ||
        !/^[A-Za-z0-9_-]{43}$/.test(params.get('code') || '')) throw new Error('invalid_callback');
    return value;
  }
  function complete(target) {
    close.href = target;
    form.hidden = true;
    alternate.hidden = true;
    completed = true;
    closed = true;
    showNotice(mode === 'register' ? {
      title: 'Đăng ký thành công!', message: 'Tài khoản của bạn đã được tạo thành công.', kind: 'success',
    } : {
      title: 'Đăng nhập thành công!', message: 'Xác thực thành công. Bạn có thể quay lại Green Farm 3.', kind: 'success',
    }, target);
    // Preserve the user-gesture link when the browser blocks the single automatic launch.
    handoffTimer = window.setTimeout(() => {
      handoffTimer = null;
      if (document.visibilityState === 'hidden') return;
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
    if (notice.open) closeNotice();
  });
  form.addEventListener('submit', async event => {
    event.preventDefault();
    if (busy || closed || notice.open) return;
    const requiredNames = mode === 'register' ? ['username', 'email', 'password', 'password_confirmation'] :
      ['login', 'password'];
    const labels = { username: 'tên tài khoản', email: 'email', login: 'tên tài khoản hoặc email',
      password: 'mật khẩu', password_confirmation: 'xác nhận mật khẩu' };
    for (const name of requiredNames) {
      const input = form.elements.namedItem(name);
      if (!input.value || (!name.startsWith('password') && !input.value.trim())) {
        showValidation('Vui lòng nhập ' + labels[name] + '.', name);
        return;
      }
      if (!input.checkValidity()) {
        showValidation(name === 'username' ? 'Tên tài khoản cần 3–32 chữ không dấu, số hoặc dấu gạch dưới.' :
          name === 'email' ? 'Vui lòng nhập địa chỉ email hợp lệ.' :
          name === 'password' && mode === 'register' ? 'Mật khẩu cần có 12–128 ký tự.' :
          'Vui lòng kiểm tra ' + labels[name] + '.', name);
        return;
      }
    }
    if (typeof AbortController !== 'function' || typeof fetch !== 'function') {
      showNotice({ title: 'Cần cập nhật trình duyệt',
        message: 'Hãy cập nhật trình duyệt để đăng nhập an toàn, rồi mở lại đăng nhập từ trò chơi.', label: 'Đã hiểu' });
      return;
    }
    const names = mode === 'register' ? ['csrf', 'attempt', 'username', 'email', 'password', 'password_confirmation'] :
      ['csrf', 'attempt', 'login', 'password'];
    const body = {};
    for (const name of names) body[name] = form.elements.namedItem(name).value;
    if (mode === 'register' && body.password !== body.password_confirmation) {
      showValidation('Mật khẩu xác nhận chưa khớp.', 'password_confirmation');
      return;
    }
    setBusy(true);
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
    let issue;
    let target;
    try {
      const { response, reply } = await Promise.race([(async () => {
        const response = await fetch(endpoint, {
          method: 'POST', credentials: 'same-origin', cache: 'no-store', redirect: 'error',
          // Older Chromium emits an opaque Origin for a no-referrer POST.
          // Send only the origin so the server can still enforce exact CSRF origin.
          referrerPolicy: 'origin',
          headers: { 'Content-Type': 'application/json', Accept: 'application/json' },
          body: JSON.stringify(body), signal: requestController.signal,
        });
        let reply;
        try { reply = await response.json(); }
        catch { if (response.ok) throw new Error('invalid_response'); }
        return { response, reply };
      })(), deadline]);
      if (closed || activeRequest !== requestId) return;
      if (!response.ok) {
        issue = publicError(response, reply);
        return;
      }
      if (response.status !== (mode === 'register' ? 201 : 200) ||
          !reply || typeof reply !== 'object' || Array.isArray(reply) ||
          Object.keys(reply).length !== 1 || !Object.prototype.hasOwnProperty.call(reply, 'callback_url')) {
        throw new Error('invalid_response');
      }
      target = appCode(reply.callback_url);
    } catch (error) {
      issue = ['invalid_response', 'invalid_callback'].includes(error && error.message) ?
        feedback.invalid_response : feedback.connection;
    } finally {
      window.clearTimeout(timeout);
      if (controller === requestController) controller = null;
      if (!closed && activeRequest === requestId) {
        clearPasswords();
        setBusy(false);
        if (target) complete(target);
        else if (issue) showNotice(issue);
      }
    }
  });
})();
