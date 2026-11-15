import test from 'node:test';
import assert from 'node:assert/strict';
import { runInNewContext } from 'node:vm';
import { ASSETS, CSP, PASSWORD_CSP, renderPasswordForm } from '../web/pages.mjs';

const attempt = 'a'.repeat(43);
const csrf = 'x'.repeat(43);
const state = 's'.repeat(43);
const code = 'c'.repeat(43);
const closeUrl = `com.quanhoang.greenfarm3://oauth/callback?error=access_denied&state=${state}`;
const callback = `com.quanhoang.greenfarm3://oauth/callback?code=${code}&state=${state}`;

test('embedded forms reuse local farm assets, keep validated context and contain no provider flow', () => {
  for (const mode of ['login', 'register']) {
    const page = renderPasswordForm({ mode, csrfToken: csrf, attemptId: attempt, closeUrl });
    assert.equal(page.csp, PASSWORD_CSP);
    assert.match(page.csp, /connect-src 'self'/u);
    assert.match(page.csp, /form-action 'none'/u);
    assert.match(CSP, /connect-src 'none'/u);
    assert.match(page.html, /password\.js" defer/u);
    assert.match(page.html, /id="close-account"/u);
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
function web({ mode = 'login', fetchReply = async () => ({ ok: true, json: async () => ({ callback_url: callback }) }) } = {}) {
  const listeners = {};
  const navigation = [];
  const requests = [];
  const timers = new Map();
  const clearedTimers = [];
  const attributes = {};
  const values = Object.fromEntries(Object.entries({ csrf, attempt, login: 'Player123', username: 'Player123',
    email: 'player@example.test', password: '  secret farm password  ', password_confirmation: '  secret farm password  ' })
    .map(([name, value]) => [name, { value }]));
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
  const status = { textContent: '' };
  Object.defineProperty(status, 'innerHTML', { set() { throw new Error('Unsafe HTML error rendering'); } });
  const close = { href: closeUrl, addEventListener: (event, fn) => { listeners['close:' + event] = fn; } };
  const alternate = { setAttribute: (key, value) => { attributes['alternate:' + key] = value; },
    addEventListener: (event, fn) => { listeners['alternate:' + event] = fn; } };
  const nodes = { 'account-form': form, 'account-fields': fields, 'account-status': status,
    'close-account': close, 'account-mode-link': alternate };
  class AbortController {
    signal = { aborted: false };
    abort() { this.signal.aborted = true; this.signal.onabort?.(); }
  }
  runInNewContext(script, {
    URL, AbortController,
    document: { getElementById: id => nodes[id] },
    fetch: (url, options) => { requests.push({ url, options }); return fetchReply(url, options); },
    window: {
      addEventListener: (event, fn) => { listeners['window:' + event] = fn; },
      setTimeout: (fn, milliseconds) => { const id = timers.size + 1; timers.set(id, { fn, milliseconds }); return id; },
      clearTimeout: id => clearedTimers.push(id),
      location: { replace: target => navigation.push(target) },
    },
  });
  const event = () => ({ prevented: false, preventDefault() { this.prevented = true; } });
  return { listeners, navigation, requests, timers, clearedTimers, attributes, values, fields, status,
    submit: () => listeners['form:submit'](event()), event };
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

test('public credential errors use text and restore retry without retaining passwords', async () => {
  const message = '<img src=x onerror=bad()> incorrect password';
  const result = web({ fetchReply: async () => ({ ok: false, json: async () => ({ error: { code: 'invalid_credentials', message } }) }) });
  await result.submit();
  assert.equal(result.status.textContent, message);
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
    assert.equal(result.fields.disabled, false);
    assert.equal(result.values.password.value, '');
    assert.match(result.status.textContent, /không hợp lệ/u);
  }
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
