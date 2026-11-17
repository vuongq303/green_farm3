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
    assert.match(page.html, /aria-live="polite"/u);
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
  navigate = () => {} } = {}) {
  const listeners = {};
  const navigation = [];
  const topNavigation = [];
  const frames = [];
  const requests = [];
  const timers = new Map();
  const clearedTimers = [];
  let timerId = 0;
  const attributes = {};
  const values = Object.fromEntries(Object.entries({ csrf, attempt, login: 'Player123', username: 'Player123',
    email: 'player@example.test', password: '  secret farm password  ', password_confirmation: '  secret farm password  ' })
    .map(([name, value]) => [name, { value, checkValidity: () => true }]));
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
  const status = { textContent: '', setAttribute: (name, value) => { attributes['status:' + name] = value; },
    removeAttribute: name => { delete attributes['status:' + name]; },
    focus: () => { attributes.statusFocused = true; }, scrollIntoView: () => { attributes.statusScrolled = true; } };
  Object.defineProperty(status, 'innerHTML', { set() { throw new Error('Unsafe HTML error rendering'); } });
  const close = { href: closeUrl, addEventListener: (event, fn) => { listeners['close:' + event] = fn; } };
  const alternate = { setAttribute: (key, value) => { attributes['alternate:' + key] = value; },
    addEventListener: (event, fn) => { listeners['alternate:' + event] = fn; } };
  const returnToGame = { hidden: true, addEventListener: (event, fn) => { listeners['return:' + event] = fn; } };
  const returnStatus = { hidden: true };
  const nodes = { 'account-form': form, 'account-fields': fields, 'account-status': status,
    'close-account': close, 'account-mode-link': alternate, 'return-to-game': returnToGame, 'return-status': returnStatus };
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
  return { listeners, navigation, topNavigation, frames, requests, timers, clearedTimers, attributes, values, fields, status,
    form, alternate, close, returnToGame, returnStatus,
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
  assert.equal(result.returnStatus.hidden, false);
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
  assert.match(result.status.textContent, /chưa khớp/u);
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
      assert.equal(result.returnStatus.hidden, false);
      assert.equal(result.close.href, callback, 'close must not cancel an already-issued successful code');
      assert.equal(result.form.hidden, true);
      assert.equal(result.fields.disabled, true);
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
  assert.equal(result.status.textContent, 'Tên tài khoản/email hoặc mật khẩu không đúng.');
  assert.ok(!result.status.textContent.includes(message));
  assert.equal(result.fields.disabled, false);
  assert.equal(result.attributes['aria-busy'], 'false');
  assert.equal(result.values.password.value, '');
  assert.deepEqual(result.navigation, []);
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
    assert.match(result.status.textContent, /không hợp lệ/u);
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
  assert.match(timed.status.textContent, /hết thời gian/u);
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
  assert.doesNotMatch(script, /localStorage|sessionStorage|indexedDB|addJavascriptInterface|window\.open|console\.|innerHTML/u);
  assert.doesNotMatch(script, /access_token|refresh_token/u);
  assert.doesNotMatch(ASSETS.get('styles.css').body.toString(), /@import|https?:\/\//u);
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
    assert.match(result.status.textContent, expected);
    assert.equal(result.attributes['status:data-state'], 'error');
    assert.equal(result.attributes.statusFocused, true);
    assert.equal(result.attributes.statusScrolled, true);
  }
  const malformed = web({ mode: 'register' });
  malformed.values.email.checkValidity = () => false;
  await malformed.submit();
  assert.match(malformed.status.textContent, /email hợp lệ/u);
  assert.equal(malformed.requests.length, 0);
  assert.match(renderPasswordForm({ mode: 'register', csrfToken: csrf, attemptId: attempt, closeUrl }).html,
    /novalidate/u, 'browser validation must not silently bypass the persistent message handler');
});

test('HTTP and public error codes remain useful for JSON or HTML failures and keep non-sensitive input for retry', async () => {
  for (const [mode, status, reply, expected] of [
    ['register', 409, { error: { code: 'account_exists' } }, /tên tài khoản hoặc email đã được sử dụng/iu],
    ['register', 400, { error: { code: 'invalid_registration' } }, /đăng ký không hợp lệ/u],
    ['login', 401, { error: { code: 'invalid_credentials' } }, /mật khẩu không đúng/u],
    ['login', 400, { error: { code: 'attempt_expired' } }, /hết hạn.*mở lại/iu],
    ['login', 403, null, /phiên biểu mẫu.*mở lại/iu],
    ['register', 429, { error: { code: 'rate_limited' } }, /quá nhiều yêu cầu/u],
    ['register', 502, null, /máy chủ tạm thời/iu],
    ['login', 500, { error: { code: 'private_failure', message: 'SQL password=private stack trace' } }, /máy chủ tạm thời/iu],
  ]) {
    const result = web({ mode, fetchReply: async () => ({ ok: false, status, json: async () => {
      if (reply === null) throw new SyntaxError('HTML upstream error');
      return reply;
    } }) });
    await result.submit();
    assert.match(result.status.textContent, expected);
    assert.doesNotMatch(result.status.textContent, /SQL|stack|private/u);
    assert.equal(result.fields.disabled, false);
    assert.equal(result.values.username.value, 'Player123');
    assert.equal(result.values.login.value, 'Player123');
    assert.equal(result.values.email.value, 'player@example.test');
    assert.equal(result.values.password.value, '');
    assert.equal(result.attributes['status:data-state'], 'error');
    assert.deepEqual(result.navigation, []);
    result.values.password.value = result.values.password_confirmation.value = '  retry farm password  ';
    await result.submit();
    assert.equal(result.requests.length, 2);
  }
});

test('mode-specific success is visible before the single delayed handoff and survives blocked navigation', async () => {
  for (const mode of ['login', 'register']) {
    const result = web({ mode, navigate: () => { throw new Error('External app launch blocked'); } });
    await result.submitWithoutHandoff();
    assert.match(result.status.textContent, mode === 'register' ? /Đăng ký thành công/u : /Đăng nhập thành công/u);
    assert.match(result.status.textContent, /Đang quay lại Green Farm 3/u);
    assert.equal(result.attributes['status:data-state'], 'success');
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
    assert.match(result.status.textContent, /thành công/u);
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
    assert.match(result.status.textContent, /không hợp lệ/u);
  }
  let finishBody;
  const stalled = web({ fetchReply: async () => ({ ok: true, status: 200,
    json: () => new Promise(resolve => { finishBody = resolve; }) }) });
  const pending = stalled.submit();
  // Allow the real fetch continuation to start reading the body.
  await new Promise(resolve => setImmediate(resolve));
  [...stalled.timers.values()].find(timer => timer.milliseconds === 20000).fn();
  await pending;
  assert.match(stalled.status.textContent, /hết thời gian/u);
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
  assert.match(blankIdentifier.status.textContent, /tên tài khoản hoặc email/u);
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
