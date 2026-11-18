import assert from 'node:assert/strict';
import { createHash, randomBytes, X509Certificate } from 'node:crypto';
import { readFileSync, mkdirSync } from 'node:fs';
import { createServer, request } from 'node:https';
import { join } from 'node:path';
import { test } from 'node:test';
import { chromium } from 'playwright';
import { loadConfig, loadEnvironment } from '../src/config.js';
import { createMysqlStore, createPoolFromConfig } from '../src/db/store.js';
import { migrate } from '../src/db/migrate.js';
import { createResearchServer } from '../src/server.js';
import { APP_REDIRECT_URI } from '../src/auth/config.js';

// Explicit integration target: a real browser, HTTPS and a dedicated MySQL DB.
// Supply a localhost certificate whose CA the browser already trusts, or opt
// into pinning this fixture's exact public key for an isolated browser process.
// Node always verifies the supplied CA and hostname. No production TLS options
// or global trust stores are changed, and ignoreHTTPSErrors is never enabled.
loadEnvironment();
for (const name of ['BROWSER_TEST_TLS_KEY', 'BROWSER_TEST_TLS_CERT', 'BROWSER_TEST_TLS_CA']) {
  if (!process.env[name]) throw new Error('Set ' + name + ' to the local HTTPS fixture file; see docs/WEBSITE_AUTH_RELIABILITY_FIX.md');
}
const tls = {
  key: readFileSync(process.env.BROWSER_TEST_TLS_KEY),
  cert: readFileSync(process.env.BROWSER_TEST_TLS_CERT),
};
const ca = readFileSync(process.env.BROWSER_TEST_TLS_CA);
const random = () => randomBytes(32).toString('base64url');
const password = 'Browser integration correct password 123';

test('real Chromium HTTPS form → MySQL identity → one-use PKCE session → remembered account', { timeout: 180000 }, async t => {
  const server = createServer(tls);
  t.after(async () => {
    server.closeAllConnections();
    if (server.listening) await new Promise(resolve => server.close(resolve));
  });
  await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
  const origin = 'https://localhost:' + server.address().port;
  const config = loadConfig({ ...process.env, AUTH_PUBLIC_BASE_URL: origin, LEGACY_AUTH_ENABLED: 'false' });
  assert.ok(config.database.database.endsWith('_test'), 'browser tests require a dedicated DB_NAME ending in _test');
  const store = createMysqlStore(config.database);
  const pool = createPoolFromConfig(config.database);
  const prefix = 'browser_' + randomBytes(8).toString('hex');
  const attempts = [];
  const backendErrors = [];
  let browser;
  let migrated = false;
  t.after(async () => {
    try {
      await browser?.close();
      if (migrated) {
        // Only identities and attempts created by this run are removed.
        const [users] = await pool.execute('SELECT CAST(user_id AS CHAR) AS id FROM gf_password_accounts WHERE username = ?', [prefix]);
        for (const { id } of users) {
          await pool.execute('DELETE FROM gf_auth_session_families WHERE user_id = ?', [id]);
          await pool.execute('DELETE FROM gf_auth_sessions WHERE user_id = ?', [id]);
          await pool.execute('DELETE FROM gf_auth_codes WHERE user_id = ?', [id]);
          await pool.execute('DELETE FROM users WHERE id = ?', [id]);
        }
        for (const id of attempts) {
          await pool.execute('DELETE FROM gf_auth_codes WHERE attempt_id = ?', [id]);
          await pool.execute('DELETE FROM gf_auth_attempts WHERE id = ?', [id]);
        }
      }
    } finally {
      await Promise.allSettled([store.close(), pool.end()]);
    }
  });
  await migrate(pool);
  migrated = true;
  await store.health();
  await store.social.health();
  const production = createResearchServer({ ...config, store, log: () => {}, errorLog: error => backendErrors.push(error) });
  server.on('request', production.listeners('request')[0]);

  function api(path, body, token) {
    return new Promise((resolve, reject) => {
      const data = body === undefined ? undefined : JSON.stringify(body);
      const req = request(origin + path, { ca, method: data ? 'POST' : 'GET', headers: {
        ...(data ? { 'Content-Type': 'application/json', 'Content-Length': Buffer.byteLength(data) } : {}),
        ...(token ? { Authorization: 'Bearer ' + token } : {}),
      } }, res => {
        let text = '';
        res.setEncoding('utf8');
        res.on('data', chunk => { text += chunk; });
        res.on('end', () => {
          try { resolve({ status: res.statusCode, body: JSON.parse(text) }); }
          catch (error) { reject(error); }
        });
      });
      req.on('error', reject);
      req.setTimeout(10000, () => req.destroy(new Error('HTTPS API timed out')));
      req.end(data);
    });
  }
  assert.equal((await api('/health')).body.mysql.status, 'ready');
  const testCertificatePin = process.env.BROWSER_TEST_PIN_LOCAL_CERT === '1'
    ? createHash('sha256').update(new X509Certificate(tls.cert).publicKey.export({ type: 'spki', format: 'der' })).digest('base64')
    : undefined;
  browser = await chromium.launch({ headless: true,
    args: testCertificatePin ? ['--ignore-certificate-errors-spki-list=' + testCertificatePin] : [],
    ...(process.env.CHROMIUM_EXECUTABLE_PATH ? { executablePath: process.env.CHROMIUM_EXECUTABLE_PATH } : {}) });
  const context = await browser.newContext({ viewport: { width: 393, height: 720 }, isMobile: true, hasTouch: true });
  const page = await context.newPage();
  const errors = [];
  const dialogs = [];
  let expectingDialog = false;
  page.on('dialog', async dialog => {
    dialogs.push({ type: dialog.type(), message: dialog.message() });
    if (!expectingDialog) {
      errors.push('Unexpected duplicate native alert: ' + dialog.message());
      await dialog.dismiss();
    }
  });
  page.on('pageerror', error => errors.push(error.message));
  const violations = [];
  await page.exposeFunction('recordCspViolation', directive => violations.push(directive));
  await page.addInitScript(() => document.addEventListener('securitypolicyviolation', event => {
    window.recordCspViolation(event.violatedDirective);
  }));
  const cdp = await context.newCDPSession(page);
  await cdp.send('Page.enable');
  const navigations = [];
  cdp.on('Page.frameRequestedNavigation', event => {
    if (event.url.startsWith(APP_REDIRECT_URI)) navigations.push(event.url);
  });
  async function attempt(mode = 'login') {
    const verifier = random(), state = random();
    const result = await api('/auth/attempt', { provider: 'password', state,
      code_challenge: createHash('sha256').update(verifier).digest('base64url'), redirect_uri: APP_REDIRECT_URI });
    assert.equal(result.status, 201);
    attempts.push(result.body.attempt_id);
    await page.goto(result.body.authorization_url.replace('/web/login', '/web/' + mode));
    await page.locator('#account-form').waitFor();
    assert.equal(await page.locator('#account-form').getAttribute('data-mode'), mode);
    return { state, verifier };
  }
  async function fillRegistration(confirmation = password, username = prefix, email = prefix + '@example.test') {
    await page.locator('[name=username]').fill(username);
    await page.locator('[name=email]').fill(email);
    await page.locator('[name=password]').fill(password);
    await page.locator('[name=password_confirmation]').fill(confirmation);
  }
  async function acknowledge(action, pattern, success = false) {
    const previousDialogs = dialogs.length;
    const previousNavigations = navigations.length;
    expectingDialog = true;
    const nextDialog = page.waitForEvent('dialog');
    const acting = action();
    const dialog = await nextDialog;
    try {
      assert.equal(dialog.type(), 'alert', 'feedback must use a real window.alert dialog');
      assert.match(dialog.message(), pattern);
      if (success) {
        // Keep the actual modal open past the 350 ms launch delay. No callback
        // navigation may happen until the user dismisses this native dialog.
        await new Promise(resolve => setTimeout(resolve, 450));
        assert.equal(navigations.length, previousNavigations, 'success alert must be acknowledged before handoff');
      }
    } finally {
      expectingDialog = false;
      await dialog.accept();
      await acting;
    }
    assert.equal(dialogs.length, previousDialogs + 1, 'each outcome produces exactly one native alert');
  }
  async function submit(status, alertPattern, success = false) {
    const response = page.waitForResponse(res => ['/auth/login', '/auth/register'].includes(new URL(res.url()).pathname));
    await acknowledge(() => page.locator('#account-submit').click(), alertPattern, success);
    assert.equal((await response).status(), status);
    await page.waitForFunction(() => document.getElementById('account-status').getAttribute('data-state') !== 'loading');
  }
  async function feedback(pattern, kind = 'error') {
    const status = page.locator('#account-status');
    assert.match(await status.innerText(), pattern);
    assert.equal(await status.getAttribute('data-state'), kind);
    assert.ok(await status.isVisible());
    const bounds = await status.boundingBox();
    assert.ok(bounds.y >= 0 && bounds.y < 720, 'feedback is scrolled into the mobile viewport');
  }
  async function exchange(pending, successPattern) {
    await feedback(successPattern, 'success');
    const link = page.locator('#return-to-game');
    assert.ok(await link.isVisible(), 'a manual callback remains when the browser cannot open an Android app');
    const callback = await link.getAttribute('href');
    const target = new URL(callback);
    assert.equal(target.protocol, 'com.quanhoang.greenfarm3:');
    assert.equal(target.host, 'oauth');
    assert.equal(target.pathname, '/callback');
    assert.equal(target.searchParams.get('state'), pending.state);
    assert.deepEqual([...target.searchParams.keys()].sort(), ['code', 'state']);
    await page.waitForTimeout(650);
    assert.equal(navigations.filter(url => url === callback).length, 1, 'one real script navigation attempts the returned callback');
    await page.waitForTimeout(400);
    assert.equal(navigations.filter(url => url === callback).length, 1, 'there is no redirect loop');
    assert.ok(await link.isVisible());
    const body = { code: target.searchParams.get('code'), code_verifier: pending.verifier, redirect_uri: APP_REDIRECT_URI };
    assert.equal((await api('/auth/exchange', { ...body, code_verifier: random() })).status, 400);
    const session = await api('/auth/exchange', body);
    assert.equal(session.status, 200);
    assert.equal(session.body.user.display_name, prefix);
    assert.equal((await api('/auth/exchange', body)).status, 400, 'authorization code cannot be replayed');
    assert.equal((await api('/auth/me', undefined, session.body.access_token)).body.user.game_id, session.body.user.game_id);
    return session.body;
  }

  const signup = await attempt('register');
  await acknowledge(() => page.locator('#account-submit').click(), /Vui lòng nhập tên tài khoản/u);
  await feedback(/Vui lòng nhập tên tài khoản/u);
  await fillRegistration('different confirmation 123');
  await acknowledge(() => page.locator('#account-submit').click(), /xác nhận chưa khớp/u);
  await feedback(/xác nhận chưa khớp/u);
  await fillRegistration();
  await submit(201, /Đăng ký thành công/u, true);
  const registered = await exchange(signup, /Đăng ký thành công/u);
  if (process.env.BROWSER_TEST_ARTIFACTS) {
    mkdirSync(process.env.BROWSER_TEST_ARTIFACTS, { recursive: true });
    await page.screenshot({ path: join(process.env.BROWSER_TEST_ARTIFACTS, 'registration-success.png'), fullPage: true });
  }

  await attempt('register');
  await fillRegistration();
  await submit(409, /Tên tài khoản và email đã được sử dụng/u);
  await feedback(/đã được sử dụng/u);
  assert.equal(await page.locator('[name=username]').inputValue(), prefix);
  assert.equal(await page.locator('[name=email]').inputValue(), prefix + '@example.test');
  assert.equal(await page.locator('[name=password]').inputValue(), '');
  await fillRegistration(password, prefix, 'other_' + prefix + '@example.test');
  await submit(409, /^Tên tài khoản đã tồn tại\. Vui lòng chọn tên khác\.$/u);
  await feedback(/Tên tài khoản đã tồn tại/u);
  await fillRegistration(password, prefix + '_2', prefix + '@example.test');
  await submit(409, /^Email đã được đăng ký\. Vui lòng sử dụng email khác\.$/u);
  await feedback(/Email đã được đăng ký/u);

  await attempt();
  await page.locator('[name=login]').fill(prefix);
  await page.locator('[name=password]').fill(password);
  await page.evaluate(() => { document.querySelector('[name=csrf]').value = 'x'.repeat(43); });
  await submit(403, /Phiên biểu mẫu.*mở lại đăng nhập/u);
  await feedback(/Phiên biểu mẫu/u);

  const login = await attempt();
  await page.locator('[name=login]').fill('not_' + prefix);
  await page.locator('[name=password]').fill(password);
  let releaseRequest;
  const requestGate = new Promise(resolve => { releaseRequest = resolve; });
  let loginRequests = 0;
  await page.route('**/auth/login', async route => {
    ++loginRequests;
    await requestGate;
    await route.continue(); // Delay delivery, never substitute the real API response.
  });
  const beforeLoadingDialogs = dialogs.length;
  const submitting = submit(401, /mật khẩu không đúng/u);
  await page.waitForFunction(() => document.getElementById('account-status').getAttribute('data-state') === 'loading');
  assert.ok(await page.locator('#account-submit').isDisabled());
  assert.equal(dialogs.length, beforeLoadingDialogs, 'loading must not open a blocking dialog');
  await page.evaluate(() => document.getElementById('account-form').requestSubmit());
  releaseRequest();
  await submitting;
  assert.equal(loginRequests, 1, 'loading ignores duplicate submits while the real request is pending');
  await page.unroute('**/auth/login');
  await feedback(/mật khẩu không đúng/u);
  await page.locator('[name=login]').fill(prefix);
  await page.locator('[name=password]').fill('wrong password 123');
  await submit(401, /mật khẩu không đúng/u);
  await feedback(/mật khẩu không đúng/u);
  await page.locator('[name=password]').fill(password);
  await submit(200, /Đăng nhập thành công/u, true);
  const signedIn = await exchange(login, /Đăng nhập thành công/u);
  assert.equal(signedIn.user.game_id, registered.user.game_id);
  await page.close();
  assert.equal((await api('/auth/me', undefined, signedIn.access_token)).body.user.game_id, registered.user.game_id,
    'closing the browser does not discard the verified app session');
  const refreshed = await api('/auth/refresh', { refresh_token: signedIn.refresh_token });
  assert.equal(refreshed.status, 200);
  assert.equal(refreshed.body.user.game_id, registered.user.game_id);
  assert.equal(dialogs.length, 10, 'one native alert per tested validation, API error or success; no loading alert');
  assert.deepEqual(errors, [], 'no JavaScript exception or duplicate alert prevented submit or feedback');
  assert.deepEqual(violations, [], 'the actual response CSP allowed all required assets, requests and feedback');
  assert.deepEqual(backendErrors, []);
});
