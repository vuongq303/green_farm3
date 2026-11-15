import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';
import { runInNewContext } from 'node:vm';
import { ASSETS, CSP, PAGE_HEADERS, renderError, renderSuccess, validateCallback } from '../web/pages.mjs';

const state = 's'.repeat(43);
const code = 'c'.repeat(43);
const callback = `com.quanhoang.greenfarm3://oauth/callback?code=${code}&state=${state}`;

test('auth handoff accepts only fixed code/state and error/state app callbacks', () => {
  assert.equal(validateCallback(callback), callback);
  const denied = `com.quanhoang.greenfarm3://oauth/callback?error=access_denied&state=${state}`;
  assert.equal(validateCallback(denied), denied);
  for (const invalid of [
    `https://evil.example/oauth/callback?code=${code}&state=${state}`,
    `javascript:alert(1)`,
    `com.quanhoang.greenfarm3://evil/callback?code=${code}&state=${state}`,
    `com.quanhoang.greenfarm3://oauth/other?code=${code}&state=${state}`,
    `com.quanhoang.greenfarm3://user:secret@oauth/callback?code=${code}&state=${state}`,
    `com.quanhoang.greenfarm3://oauth:123/callback?code=${code}&state=${state}`,
    `${callback}#leak`, `${callback}&state=duplicate`, `${callback}&%73tate=duplicate`,
    `${callback}&access_token=secret`, `${callback}&refresh_token=secret`,
    `${callback}&redirect_uri=https://evil.example`, `${callback}&error=access_denied`,
    `com.quanhoang.greenfarm3://oauth/callback?code=short&state=${state}`,
    `com.quanhoang.greenfarm3://oauth/callback?code=${code}&state=short`,
    `com.quanhoang.greenfarm3://oauth/callback?code=${code}&state=${'s'.repeat(129)}`,
    `com.quanhoang.greenfarm3://oauth/callback?error=%3Cscript%3E&state=${state}`,
    `com.quanhoang.greenfarm3://oauth/callback?state=${state}`,
    undefined, null, {},
  ]) assert.throws(() => validateCallback(invalid), TypeError);
});

test('success and error pages escape content, carry no tokens and keep the manual return visible', () => {
  const success = renderSuccess(callback);
  assert.equal(success.csp, CSP);
  assert.match(success.html, /id="return-to-game"/u);
  assert.match(success.html, /Quay lại trò chơi/u);
  assert.match(success.html, /Đăng nhập không đồng bộ nông trại/u);
  assert.match(success.html, /callback\.js" defer/u);
  assert.ok(success.html.includes(`code=${code}&amp;state=${state}`));
  assert.doesNotMatch(success.html, /access_token|refresh_token|password/iu);
  assert.doesNotMatch(success.html, /<script[^>]*>[^<]/u);
  const publicError = '<script>alert("bad")</script> & "unsafe"';
  const error = renderError({ message: publicError });
  assert.ok(error.html.includes('&lt;script&gt;alert(&quot;bad&quot;)&lt;/script&gt; &amp; &quot;unsafe&quot;'));
  assert.ok(!error.html.includes(publicError));
  assert.doesNotMatch(error.html, /return-to-game|callback\.js/u);
  const denied = `com.quanhoang.greenfarm3://oauth/callback?error=access_denied&state=${state}`;
  assert.match(renderError({ message: 'Đã hủy đăng nhập.', returnUrl: denied }).html, /return-to-game/u);
  assert.throws(() => renderSuccess(denied), /Success requires/u);
  assert.throws(() => renderError({ returnUrl: callback }), /Error requires/u);
});

test('HTML is noncacheable and assets are explicitly mapped without remote dependencies', () => {
  assert.equal(PAGE_HEADERS['Cache-Control'], 'no-store');
  assert.equal(PAGE_HEADERS['Referrer-Policy'], 'no-referrer');
  assert.equal(PAGE_HEADERS['X-Frame-Options'], 'DENY');
  assert.equal(PAGE_HEADERS['Content-Security-Policy'], CSP);
  assert.match(CSP, /default-src 'none'/u);
  assert.match(CSP, /script-src 'self'/u);
  assert.match(CSP, /frame-ancestors 'none'/u);
  assert.deepEqual([...ASSETS.keys()], ['styles.css', 'callback.js', 'green-farm-3.png']);
  assert.equal(ASSETS.get('../pages.mjs'), undefined);
  assert.equal(ASSETS.get('styles.css').contentType, 'text/css; charset=utf-8');
  assert.equal(ASSETS.get('callback.js').contentType, 'application/javascript; charset=utf-8');
  assert.equal(ASSETS.get('green-farm-3.png').contentType, 'image/png');
  assert.deepEqual(ASSETS.get('green-farm-3.png').body,
    readFileSync(new URL('../../app/src/main/res/mipmap-xhdpi-v4/ic_launcher.png', import.meta.url)));
  assert.doesNotMatch(ASSETS.get('styles.css').body.toString(), /@import|https?:\/\//u);
});

test('browser auto-return uses only validated app navigation and leaves a usable fallback', () => {
  const script = ASSETS.get('callback.js').body.toString();
  function browser(href, visibilityState = 'visible') {
    const calls = [];
    const link = { href };
    const status = { textContent: '' };
    const timers = [];
    runInNewContext(script, {
      URL,
      document: { visibilityState, getElementById: id => id === 'return-to-game' ? link : status },
      window: { setTimeout: fn => timers.push(fn), location: { replace: url => calls.push(url) } },
    });
    return { calls, link, status, timers };
  }
  const success = browser(callback);
  assert.equal(success.timers.length, 1);
  assert.equal(success.calls.length, 0);
  assert.equal(success.link.href, callback);
  assert.match(success.status.textContent, /Quay lại trò chơi/u);
  success.timers[0]();
  assert.deepEqual(success.calls, [callback]);
  const hidden = browser(callback, 'hidden');
  hidden.timers[0]();
  assert.deepEqual(hidden.calls, []);
  for (const invalid of [
    `https://evil.example/?code=${code}&state=${state}`, `${callback}&access_token=secret`,
    `${callback}#fragment`, `${callback}&state=duplicate`,
  ]) {
    const result = browser(invalid);
    assert.deepEqual(result.calls, []);
    assert.equal(result.timers.length, 0);
  }
});
