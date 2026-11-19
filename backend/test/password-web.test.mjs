import test from 'node:test';
import assert from 'node:assert/strict';
import { runInNewContext } from 'node:vm';
import { createHash } from 'node:crypto';
import { ASSETS, CSP, PASSWORD_CSP, renderPasswordForm } from '../web/pages.mjs';

const attempt = 'a'.repeat(43);
const csrf = 'x'.repeat(43);
const state = 's'.repeat(43);
const code = 'c'.repeat(43);
const closeUrl = `com.quanhoang.greenfarm3://oauth/callback?error=access_denied&state=${state}`;
const callback = `com.quanhoang.greenfarm3://oauth/callback?code=${code}&state=${state}`;

test('browser forms reuse local farm assets, keep validated context and contain no provider flow', () => {
  for (const mode of ['login', 'register']) {
    const page = renderPasswordForm({ mode, csrfToken: csrf, attemptId: attempt, closeUrl });
    assert.equal(page.csp, PASSWORD_CSP);
    assert.match(page.csp, /connect-src 'self'/u);
    assert.match(page.csp, /frame-src com\.quanhoang\.greenfarm3:(?:;|$)/u);
    assert.doesNotMatch(page.csp, /frame-src[^;]*(?:https?:|\*)/u);
    assert.match(page.csp, /form-action 'none'/u);
    assert.match(CSP, /connect-src 'none'/u);
    assert.match(page.html, /password\.js\?v=[a-f0-9]{64}" defer/u);
    assert.match(page.html, /id="close-account"/u);
    assert.match(page.html, /id="return-to-game" class="farm-button" hidden/u);
    assert.equal((page.html.match(/<dialog\b/gu) || []).length, 1);
    assert.match(page.html, /role="alertdialog" aria-modal="true" aria-labelledby="notice-title" aria-describedby="notice-message"/u);
    assert.doesNotMatch(page.html, /account-status|form-status/u);
    assert.ok(page.html.includes(`name="csrf" value="${csrf}"`));
    assert.ok(page.html.includes(`name="attempt" value="${attempt}"`));
    assert.ok(page.html.includes(`/auth/web/${mode === 'login' ? 'register' : 'login'}?attempt=${attempt}`));
    assert.ok(page.html.includes(`error=access_denied&amp;state=${state}`));
    assert.ok(page.html.includes(`data-endpoint="/auth/${mode}"`));
    assert.match(page.html, /nông trại hiện được lưu|Nông trại hiện được lưu/u);
    assert.doesNotMatch(page.html, /Google|Facebook|access_token|refresh_token|https?:\/\/|on(?:click|submit)=/u);
    assert.doesNotMatch(page.html, /<script[^>]*>[^<]/u);
    if (mode === 'register') {
      for (const field of ['username', 'email', 'password', 'password_confirmation']) assert.ok(page.html.includes(`name="${field}"`));
      assert.match(page.html, /autocomplete="new-password"/u);
      assert.match(page.html, /minlength="12"/u);
    } else {
      assert.match(page.html, /name="login"/u);
      assert.match(page.html, /autocomplete="current-password"/u);
      assert.doesNotMatch(page.html, /name="email"|password_confirmation/u);
    }
  }
});

test('form context rejects XSS, arbitrary redirects and non-cancel close callbacks', () => {
  const valid = { mode: 'login', csrfToken: csrf, attemptId: attempt, closeUrl };
  for (const changes of [
    { mode: '<script>' }, { csrfToken: `" onfocus="bad()` }, { csrfToken: 'short' },
    { attemptId: 'a'.repeat(44) }, { attemptId: '../other' },
    { closeUrl: 'https://evil.example/' }, { closeUrl: callback },
    { closeUrl: closeUrl + '&access_token=secret' }, { closeUrl: closeUrl + '&state=duplicate' },
    { closeUrl: closeUrl.replace('access_denied', 'authentication_failed') },
  ]) assert.throws(() => renderPasswordForm({ ...valid, ...changes }), TypeError);
  assert.throws(() => renderPasswordForm(), TypeError);
});

const script = ASSETS.get('password.js').body.toString();
function web({ mode = 'login', fetchReply = async () => ({ ok: true, json: async () => ({ callback_url: callback }) }),
  navigate = () => {}, dialogAvailable = true } = {}) {
  const listeners = {};
  const navigation = [];
  const topNavigation = [];
  const shown = [];
  const frames = [];
  const requests = [];
  const timers = new Map();
  const clearedTimers = [];
  let timerId = 0;
  const attributes = {};
  const values = Object.fromEntries(Object.entries({ csrf, attempt, login: 'Player123', username: 'Player123',
    email: 'player@example.test', password: '  secret farm password  ', password_confirmation: '  secret farm password  ' })
    .map(([name, value]) => [name, { value, checkValidity: () => true, focus: () => { attributes.focus = name; } }]));
  const form = {
    elements: { namedItem: name => values[name] },
    getAttribute: key => key === 'data-mode' ? mode : key === 'data-endpoint' ? '/auth/' + mode : null,
    setAttribute: (key, value) => { attributes[key] = value; },
    querySelectorAll: () => [values.password, values.password_confirmation],
    classList: { toggle: (key, value) => { attributes[key] = value; } },
    reportValidity: () => true,
    addEventListener: (event, fn) => { listeners['form:' + event] = fn; },
  };
  const fields = { disabled: false };
  const title = { textContent: '' };
  const message = { textContent: '' };
  Object.defineProperty(message, 'innerHTML', { set() { throw new Error('Unsafe HTML error rendering'); } });
  const submitLabel = { textContent: mode === 'register' ? 'Đăng ký' : 'Đăng nhập' };
  const submitButton = { focus: () => { attributes.focus = 'submit'; } };
  const notice = { open: false, setAttribute: (name, value) => { attributes['notice:' + name] = value; },
    showModal() { assert.equal(this.open, false); this.open = true; shown.push(title.textContent); },
    close() { this.open = false; },
    addEventListener: (event, fn) => { listeners['notice:' + event] = fn; } };
  if (!dialogAvailable) delete notice.showModal;
  const dismiss = { focus: () => { attributes.focus = 'dismiss'; },
    addEventListener: (event, fn) => { listeners['dismiss:' + event] = fn; } };
  const close = { href: closeUrl, addEventListener: (event, fn) => { listeners['close:' + event] = fn; } };
  const alternate = { setAttribute: (key, value) => { attributes['alternate:' + key] = value; },
    addEventListener: (event, fn) => { listeners['alternate:' + event] = fn; } };
  const returnToGame = { hidden: true, focus: () => { attributes.focus = 'return'; },
    addEventListener: (event, fn) => { listeners['return:' + event] = fn; } };
  const nodes = { 'account-form': form, 'account-fields': fields, 'account-notice': notice,
    'notice-title': title, 'notice-message': message, 'notice-dismiss': dismiss,
    'submit-label': submitLabel, 'account-submit': submitButton, 'close-account': close,
    'account-mode-link': alternate, 'return-to-game': returnToGame };
  class AbortController {
    signal = { aborted: false };
    abort() { this.signal.aborted = true; this.signal.onabort?.(); }
  }
  runInNewContext(script, {
    URL, AbortController,
    document: { visibilityState: 'visible', getElementById: id => nodes[id],
      createElement: tag => {
        assert.equal(tag, 'iframe');
        return { attributes: {}, setAttribute(name, value) { this.attributes[name] = value; } };
      },
      body: { appendChild: frame => { frames.push(frame); navigation.push(frame.src); navigate(frame.src); } },
    },
    fetch: (url, options) => { requests.push({ url, options }); return Promise.resolve(fetchReply(url, options)).then(reply => {
      if (reply.status === undefined) reply.status = mode === 'register' ? 201 : 200;
      return reply;
    }); },
    window: {
      alert: () => assert.fail('native alerts are forbidden on the password form'),
      addEventListener: (event, fn) => { listeners['window:' + event] = fn; },
      setTimeout: (fn, milliseconds) => { const id = ++timerId; timers.set(id, { fn, milliseconds }); return id; },
      clearTimeout: id => clearedTimers.push(id),
      location: { replace: target => { topNavigation.push(target); } },
    },
  });
  const openGame = () => {
    for (const [id, timer] of timers) if (timer.milliseconds === 350 && !clearedTimers.includes(id)) {
      timers.delete(id); timer.fn();
    }
  };
  const event = () => ({ prevented: false, preventDefault() { this.prevented = true; } });
  return { listeners, navigation, topNavigation, frames, shown, requests, timers, clearedTimers, attributes, values, fields,
    form, alternate, close, returnToGame, notice, title, message, dismiss, submitButton, submitLabel,
    dismissNotice: () => listeners['dismiss:click'](),
    submitWithoutHandoff: () => listeners['form:submit'](event()), openGame,
    submit: async () => { await listeners['form:submit'](event()); openGame(); }, event };
}

test('login posts only credentials/CSRF through same-origin JSON, blocks repeats and hands back only code/state', async () => {
  let finish;
  const result = web({ fetchReply: () => new Promise(resolve => { finish = resolve; }) });
  const pending = result.submit();
  assert.equal(result.fields.disabled, true);
  assert.equal(result.attributes['aria-busy'], 'true');
  await result.submit();
  assert.equal(result.requests.length, 1);
  const switching = result.event();
  result.listeners['alternate:click'](switching);
  assert.equal(switching.prevented, true);
  const request = result.requests[0];
  assert.equal(request.url, '/auth/login');
  assert.equal(request.options.credentials, 'same-origin');
  assert.equal(request.options.redirect, 'error');
  assert.equal(request.options.cache, 'no-store');
  assert.deepEqual(JSON.parse(request.options.body), { csrf, attempt, login: 'Player123', password: '  secret farm password  ' });
  assert.equal(request.options.headers.Authorization, undefined);
  assert.equal([...result.timers.values()][0].milliseconds, 20000);
  finish({ ok: true, json: async () => ({ callback_url: callback }) });
  await pending;
  assert.deepEqual(result.navigation, [callback]);
  assert.equal(result.returnToGame.href, callback);
  assert.equal(result.returnToGame.hidden, false);
  assert.equal(result.notice.open, true);
  assert.equal(result.form.hidden, true);
  assert.equal(result.alternate.hidden, true);
  assert.equal(result.values.password.value, '');
  assert.equal(result.values.password_confirmation.value, '');
  assert.deepEqual(result.clearedTimers, [1]);
});

test('registration checks confirmation before sending and uses only the dedicated registration endpoint', async () => {
  const result = web({ mode: 'register' });
  result.values.password_confirmation.value = 'does not match';
  await result.submit();
  assert.equal(result.requests.length, 0);
  assert.match(result.message.textContent, /chưa khớp/u);
  result.dismissNotice();
  result.values.password_confirmation.value = result.values.password.value;
  await result.submit();
  assert.equal(result.requests[0].url, '/auth/register');
  assert.deepEqual(JSON.parse(result.requests[0].options.body), { csrf, attempt, username: 'Player123',
    email: 'player@example.test', password: '  secret farm password  ', password_confirmation: '  secret farm password  ' });
  assert.deepEqual(result.navigation, [callback]);
});

test('both browser forms keep a real code-only return link when automatic app navigation is blocked', async () => {
  for (const mode of ['login', 'register']) {
    for (const navigate of [() => {}, () => { throw new Error('Browser requires user gesture'); }]) {
      const result = web({ mode, navigate });
      await result.submit();
      assert.deepEqual(result.navigation, [callback]);
      assert.equal(result.returnToGame.href, callback);
      assert.equal(result.returnToGame.hidden, false);
      assert.equal(result.notice.open, true);
      assert.equal(result.close.href, callback, 'close must not cancel an already-issued successful code');
      assert.equal(result.form.hidden, true);
      assert.equal(result.attributes['aria-busy'], 'false');
      assert.equal(result.values.password.value, '');
      result.listeners['window:pageshow']({ persisted: true });
      await result.submit();
      assert.equal(result.requests.length, 1, 'back cache must not resubmit consumed credentials');
    }
  }
});

test('public credential errors use text and restore retry without retaining passwords', async () => {
  const message = '<img src=x onerror=bad()> incorrect password';
  const result = web({ fetchReply: async () => ({ ok: false, json: async () => ({ error: { code: 'invalid_credentials', message } }) }) });
  await result.submit();
  assert.equal(result.message.textContent, 'Tên tài khoản hoặc mật khẩu không chính xác.');
  assert.ok(!result.message.textContent.includes(message));
  assert.equal(result.fields.disabled, false);
  assert.equal(result.attributes['aria-busy'], 'false');
  assert.equal(result.values.password.value, '');
  assert.deepEqual(result.navigation, []);
  result.dismissNotice();
  assert.equal(result.attributes.focus, 'password');
  result.values.password.value = 'replacement password';
  await result.submit();
  assert.equal(result.requests.length, 2);
});

test('success refuses other origins, extra credentials, duplicate codes and mismatched pending states', async () => {
  for (const reply of [
    { callback_url: 'https://evil.example/' },
    { callback_url: callback, access_token: 'secret' },
    { callback_url: callback + '&access_token=secret' },
    { callback_url: callback + '&code=' + code },
    { callback_url: callback.replace(state, 't'.repeat(43)) },
    { callback_url: callback + '#fragment' },
    { callback_url: closeUrl },
    { game_id: 'GF000001' },
  ]) {
    const result = web({ fetchReply: async () => ({ ok: true, json: async () => reply }) });
    await result.submit();
    assert.deepEqual(result.navigation, []);
    assert.equal(result.returnToGame.hidden, true);
    assert.equal(result.returnToGame.href, undefined);
    assert.equal(result.fields.disabled, false);
    assert.equal(result.values.password.value, '');
    assert.equal(result.title.textContent, 'Phản hồi không hợp lệ');
  }
});

test('browser back cache restores an unfinished form but never accepts a late reply from its previous visit', async () => {
  let finish;
  const result = web({ fetchReply: () => new Promise(resolve => { finish = resolve; }) });
  const previousVisit = result.submit();
  result.listeners['window:pagehide']();
  result.listeners['window:pageshow']({ persisted: true });
  assert.equal(result.fields.disabled, false);
  result.values.password.value = 'new visit password';
  finish({ ok: true, json: async () => ({ callback_url: callback }) });
  await previousVisit;
  assert.deepEqual(result.navigation, []);
  assert.equal(result.returnToGame.hidden, true);
  assert.equal(result.values.password.value, 'new visit password');
  assert.equal(result.fields.disabled, false);
});

test('timeout and leaving abort requests; late success cannot navigate after closing', async () => {
  const timed = web({ fetchReply: (url, options) => new Promise((resolve, reject) => {
    options.signal.onabort = () => reject(Object.assign(new Error(), { name: 'AbortError' }));
  }) });
  const pending = timed.submit();
  [...timed.timers.values()][0].fn();
  await pending;
  assert.equal(timed.title.textContent, 'Không thể kết nối');
  assert.equal(timed.fields.disabled, false);
  assert.equal(timed.values.password.value, '');
  for (const leaving of ['close:click', 'window:pagehide']) {
    let finish;
    const result = web({ fetchReply: () => new Promise(resolve => { finish = resolve; }) });
    const pendingClose = result.submit();
    result.listeners[leaving]();
    assert.equal(result.requests[0].options.signal.aborted, true);
    assert.equal(result.values.password.value, '');
    finish({ ok: true, json: async () => ({ callback_url: callback }) });
    await pendingClose;
    assert.deepEqual(result.navigation, []);
  }
});

test('password website contains no storage, uncontrolled bridge or external navigation API', () => {
  assert.doesNotMatch(script, /localStorage|sessionStorage|indexedDB|addJavascriptInterface|window\.(?:open|alert)|console\.|innerHTML/u);
  assert.doesNotMatch(script, /access_token|refresh_token/u);
  assert.doesNotMatch(ASSETS.get('styles.css').body.toString(), /@import|https?:\/\//u);
});

test('browsers without modal support ask for an update before sending credentials', () => {
  const result = web({ dialogAvailable: false });
  assert.equal(result.submitButton.disabled, true);
  assert.equal(result.submitLabel.textContent, 'Hãy cập nhật trình duyệt');
  assert.equal(result.listeners['form:submit'], undefined);
  assert.deepEqual(result.requests, []);
});

test('required and invalid fields produce persistent visible feedback without sending requests', async () => {
  for (const [mode, name, expected] of [
    ['register', 'username', /tên tài khoản/u], ['register', 'email', /email/u],
    ['register', 'password', /mật khẩu/u], ['register', 'password_confirmation', /xác nhận mật khẩu/u],
    ['login', 'login', /tên tài khoản hoặc email/u], ['login', 'password', /mật khẩu/u],
  ]) {
    const result = web({ mode });
    result.values[name].value = '';
    await result.submit();
    assert.equal(result.requests.length, 0);
    assert.match(result.message.textContent, expected);
    assert.equal(result.attributes['notice:data-kind'], 'warning');
    assert.equal(result.attributes.focus, 'dismiss');
    assert.equal(result.notice.open, true);
    const escape = result.event();
    result.listeners['notice:cancel'](escape);
    assert.equal(escape.prevented, true);
    assert.equal(result.notice.open, false);
    assert.equal(result.attributes.focus, name);
  }
  const malformed = web({ mode: 'register' });
  malformed.values.email.checkValidity = () => false;
  await malformed.submit();
  assert.match(malformed.message.textContent, /email hợp lệ/u);
  assert.equal(malformed.requests.length, 0);
  assert.match(renderPasswordForm({ mode: 'register', csrfToken: csrf, attemptId: attempt, closeUrl }).html,
    /novalidate/u, 'browser validation must not silently bypass the persistent message handler');
});

test('HTTP and public error codes remain useful for JSON or HTML failures and keep non-sensitive input for retry', async () => {
  for (const [mode, status, reply, expected] of [
    ['register', 409, { error: { code: 'account_exists' } }, /tên tài khoản hoặc email đã được sử dụng/iu],
    ['register', 400, { error: { code: 'invalid_registration' } }, /đăng ký không hợp lệ/u],
    ['login', 401, { error: { code: 'invalid_credentials' } }, /mật khẩu không chính xác/u],
    ['login', 400, { error: { code: 'attempt_expired' } }, /hết hạn.*mở lại/iu],
    ['login', 403, null, /phiên biểu mẫu.*mở lại/iu],
    ['register', 429, { error: { code: 'rate_limited' } }, /quá nhiều yêu cầu/iu],
    ['register', 502, null, /máy chủ tạm thời/iu],
    ['login', 500, { error: { code: 'private_failure', message: 'SQL password=private stack trace' } }, /máy chủ tạm thời/iu],
  ]) {
    const result = web({ mode, fetchReply: async () => ({ ok: false, status, json: async () => {
      if (reply === null) throw new SyntaxError('HTML upstream error');
      return reply;
    } }) });
    await result.submit();
    const copy = result.title.textContent + '. ' + result.message.textContent;
    assert.match(copy, expected);
    assert.doesNotMatch(copy, /SQL|stack|private/u);
    assert.equal(result.fields.disabled, false);
    assert.equal(result.values.username.value, 'Player123');
    assert.equal(result.values.login.value, 'Player123');
    assert.equal(result.values.email.value, 'player@example.test');
    assert.equal(result.values.password.value, '');
    assert.equal(result.notice.open, true);
    assert.deepEqual(result.navigation, []);
    result.dismissNotice();
    result.values.password.value = result.values.password_confirmation.value = '  retry farm password  ';
    await result.submit();
    assert.equal(result.requests.length, 2);
  }
});

test('mode-specific success is visible before the single delayed handoff and survives blocked navigation', async () => {
  for (const mode of ['login', 'register']) {
    const result = web({ mode, navigate: () => { throw new Error('External app launch blocked'); } });
    await result.submitWithoutHandoff();
    assert.equal(result.title.textContent, mode === 'register' ? 'Đăng ký thành công!' : 'Đăng nhập thành công!');
    assert.equal(result.message.textContent, mode === 'register' ? 'Tài khoản của bạn đã được tạo thành công.' :
      'Xác thực thành công. Bạn có thể quay lại Green Farm 3.');
    assert.equal(result.attributes['notice:data-kind'], 'success');
    assert.equal(result.attributes.focus, 'return');
    assert.deepEqual(result.navigation, [], 'give feedback a browser paint before navigation');
    assert.equal(result.returnToGame.href, callback);
    assert.equal(result.returnToGame.hidden, false);
    result.openGame();
    result.openGame();
    assert.deepEqual(result.navigation, [callback], 'never create an automatic launch loop');
    assert.deepEqual(result.topNavigation, [], 'auto launch must not replace the fallback document');
    assert.equal(result.frames.length, 1);
    assert.equal(result.frames[0].hidden, true);
    assert.equal(result.frames[0].attributes.sandbox, 'allow-top-navigation-to-custom-protocols');
    assert.equal(result.frames[0].src, callback);
    assert.equal(result.returnToGame.hidden, false);
    assert.equal(result.notice.open, true);
  }
  const manual = web();
  await manual.submitWithoutHandoff();
  manual.listeners['return:click']();
  manual.openGame();
  assert.deepEqual(manual.navigation, [], 'a manual return cancels the pending automatic attempt');
  const leaving = web();
  await leaving.submitWithoutHandoff();
  leaving.listeners['window:pagehide']();
  leaving.openGame();
  assert.deepEqual(leaving.navigation, []);
});

test('unexpected 2xx status cannot authenticate and deadline includes a stalled JSON body', async () => {
  for (const [mode, status] of [['register', 200], ['login', 201], ['login', 204]]) {
    const result = web({ mode, fetchReply: async () => ({ ok: true, status, json: async () => ({ callback_url: callback }) }) });
    await result.submit();
    assert.deepEqual(result.navigation, []);
    assert.equal(result.returnToGame.hidden, true);
    assert.equal(result.title.textContent, 'Phản hồi không hợp lệ');
  }
  let finishBody;
  const stalled = web({ fetchReply: async () => ({ ok: true, status: 200,
    json: () => new Promise(resolve => { finishBody = resolve; }) }) });
  const pending = stalled.submit();
  // Allow the real fetch continuation to start reading the body.
  await new Promise(resolve => setImmediate(resolve));
  [...stalled.timers.values()].find(timer => timer.milliseconds === 20000).fn();
  await pending;
  assert.equal(stalled.title.textContent, 'Không thể kết nối');
  assert.equal(stalled.fields.disabled, false);
  assert.equal(stalled.values.password.value, '');
  finishBody({ callback_url: callback });
  await new Promise(resolve => setImmediate(resolve));
  assert.deepEqual(stalled.navigation, [], 'a late body must not hand off after the timeout');
});

test('password validation preserves exact printable spaces instead of trimming valid credentials', async () => {
  for (const mode of ['login', 'register']) {
    const result = web({ mode });
    const exactPassword = ' '.repeat(12);
    result.values.password.value = exactPassword;
    result.values.password_confirmation.value = exactPassword;
    await result.submit();
    assert.equal(result.requests.length, 1);
    const submitted = JSON.parse(result.requests[0].options.body);
    assert.equal(submitted.password, exactPassword);
    if (mode === 'register') assert.equal(submitted.password_confirmation, exactPassword);
    assert.deepEqual(result.navigation, [callback]);
  }
  const blankIdentifier = web();
  blankIdentifier.values.login.value = '   ';
  await blankIdentifier.submit();
  assert.equal(blankIdentifier.requests.length, 0, 'identifiers still require non-whitespace input');
  assert.match(blankIdentifier.message.textContent, /tên tài khoản hoặc email/u);
});


test('password form asset URLs use hashes of shipped bytes to bypass an old deployment cache', () => {
  for (const mode of ['login', 'register']) {
    const context = { mode, csrfToken: csrf, attemptId: attempt, closeUrl };
    const html = renderPasswordForm(context).html;
    for (const [filename, attribute] of [['password.js', 'src'], ['styles.css', 'href']]) {
      const digest = createHash('sha256').update(ASSETS.get(filename).body).digest('hex');
      assert.ok(html.includes(`${attribute}="/auth/assets/${filename}?v=${digest}"`),
        filename + ' version must describe the currently served asset bytes');
    }
    assert.equal(renderPasswordForm(context).html, html, 'asset versions must be deterministic');
    assert.ok(!html.includes('password.js"'), 'do not leave an unversioned cached script on password pages');
  }
});


test('validation, API and connection failures show one modal while loading stays in the button', async () => {
  const invalid = web({ mode: 'register' });
  invalid.values.username.value = '';
  await invalid.submit();
  await invalid.submit();
  assert.deepEqual(invalid.shown, ['Kiểm tra thông tin']);
  assert.equal(invalid.message.textContent, 'Vui lòng nhập tên tài khoản.');
  assert.equal(invalid.requests.length, 0);
  let finish;
  const pending = web({ fetchReply: () => new Promise(resolve => { finish = resolve; }) });
  const submission = pending.submit();
  assert.deepEqual(pending.shown, [], 'loading must remain in the button');
  assert.equal(pending.submitLabel.textContent, 'Đang đăng nhập…');
  await pending.submit();
  assert.deepEqual(pending.shown, []);
  finish({ ok: false, status: 401, json: async () => ({ error: { code: 'invalid_credentials' } }) });
  await submission;
  assert.deepEqual(pending.shown, ['Đăng nhập thất bại']);
  assert.equal(pending.submitLabel.textContent, 'Đăng nhập');
  assert.equal(pending.requests.length, 1);
  assert.equal(pending.fields.disabled, false);
  const failedConnection = web({ fetchReply: async () => { throw new TypeError('private diagnostic'); } });
  await failedConnection.submit();
  assert.deepEqual(failedConnection.shown, ['Không thể kết nối']);
  assert.equal(failedConnection.message.textContent, 'Không thể kết nối với máy chủ. Vui lòng kiểm tra Internet và thử lại.');
});

test('one success modal survives the callback attempt and cannot repeat on back cache or resubmit', async () => {
  for (const mode of ['login', 'register']) {
    const result = web({ mode });
    await result.submitWithoutHandoff();
    const title = mode === 'register' ? 'Đăng ký thành công!' : 'Đăng nhập thành công!';
    assert.deepEqual(result.shown, [title]);
    assert.equal(result.notice.open, true);
    assert.deepEqual(result.navigation, []);
    result.openGame();
    assert.deepEqual(result.navigation, [callback]);
    assert.equal(result.notice.open, true);
    result.listeners['window:pageshow']({ persisted: true });
    await result.submit();
    result.openGame();
    assert.deepEqual(result.shown, [title]);
    assert.equal(result.requests.length, 1);
    assert.deepEqual(result.navigation, [callback]);
  }
});

test('duplicate notices distinguish only allowlisted username/email conflicts and keep a generic fallback', async () => {
  for (const [conflicts, title, message, action] of [
    [['username'], 'Tên tài khoản đã tồn tại', 'Tên tài khoản này đã được sử dụng. Vui lòng chọn tên khác.', 'Thử lại'],
    [['email'], 'Email đã được đăng ký', 'Email này đã được sử dụng. Vui lòng nhập email khác.', 'Đã hiểu'],
    [['username', 'email'], 'Thông tin đã được sử dụng', /Tên tài khoản và email đã được sử dụng/u, 'Thử lại'],
    [['email', 'username'], 'Thông tin đã được sử dụng', /Tên tài khoản và email đã được sử dụng/u, 'Thử lại'],
    [undefined, 'Thông tin đã được sử dụng', 'Tên tài khoản hoặc email đã được sử dụng.', 'Thử lại'],
    [['email', 'email'], 'Thông tin đã được sử dụng', 'Tên tài khoản hoặc email đã được sử dụng.', 'Thử lại'],
    [['private_column'], 'Thông tin đã được sử dụng', 'Tên tài khoản hoặc email đã được sử dụng.', 'Thử lại'],
  ]) {
    const result = web({ mode: 'register', fetchReply: async () => ({ ok: false, status: 409,
      json: async () => ({ error: { code: 'account_exists', conflicts, message: 'SQL private exception' } }) }) });
    await result.submit();
    assert.deepEqual(result.shown, [title]);
    if (typeof message === 'string') assert.equal(result.message.textContent, message);
    else assert.match(result.message.textContent, message);
    assert.equal(result.dismiss.textContent, action);
    assert.equal(result.values.username.value, 'Player123');
    assert.equal(result.values.email.value, 'player@example.test');
    assert.equal(result.fields.disabled, false);
    assert.deepEqual(result.navigation, []);
  }
});
