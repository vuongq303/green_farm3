import assert from 'node:assert/strict';
import { createHash, randomBytes, X509Certificate } from 'node:crypto';
import { readFileSync, mkdirSync } from 'node:fs';
import { createServer, request } from 'node:https';
import { join } from 'node:path';
import { test } from 'node:test';
import { setTimeout as delay } from 'node:timers/promises';
import { chromium } from 'playwright';
import { loadConfig, readEnvironment } from '../src/config.js';
import { createMysqlStore, createPoolFromConfig } from '../src/db/store.js';
import { migrate } from '../src/db/migrate.js';
import { createResearchServer } from '../src/server.js';
import { APP_REDIRECT_URI } from '../src/auth/config.js';

// Explicit integration target: a real browser, HTTPS and a dedicated MySQL DB.
// Supply a localhost certificate whose CA the browser already trusts, or opt
// into pinning this fixture's exact public key for an isolated browser process.
// Node always verifies the supplied CA and hostname. No production TLS options
// or global trust stores are changed, and ignoreHTTPSErrors is never enabled.
const env = readEnvironment();
for (const name of ['BROWSER_TEST_TLS_KEY', 'BROWSER_TEST_TLS_CERT', 'BROWSER_TEST_TLS_CA']) {
  if (!env[name]) throw new Error('Set ' + name + ' to the local HTTPS fixture file; see docs/WEBSITE_AUTH_RELIABILITY_FIX.md');
}
const tls = {
  key: readFileSync(env.BROWSER_TEST_TLS_KEY),
  cert: readFileSync(env.BROWSER_TEST_TLS_CERT),
};
const ca = readFileSync(env.BROWSER_TEST_TLS_CA);
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
  const config = loadConfig({ ...env, AUTH_PUBLIC_BASE_URL: origin, LEGACY_AUTH_ENABLED: 'false' });
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
          await pool.execute('DELETE FROM gf_auth_sessions WHERE user_id = ?', [id]);
          await pool.execute('DELETE FROM gf_auth_session_families WHERE user_id = ?', [id]);
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
  const production = createResearchServer({ config, store, log: () => {}, errorLog: error => backendErrors.push(error) });
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
  const testCertificatePin = env.BROWSER_TEST_PIN_LOCAL_CERT === '1'
    ? createHash('sha256').update(new X509Certificate(tls.cert).publicKey.export({ type: 'spki', format: 'der' })).digest('base64')
    : undefined;
  browser = await chromium.launch({ headless: true,
    args: testCertificatePin ? ['--ignore-certificate-errors-spki-list=' + testCertificatePin] : [],
    ...(env.CHROMIUM_EXECUTABLE_PATH ? { executablePath: env.CHROMIUM_EXECUTABLE_PATH } : {}) });
  const context = await browser.newContext({ viewport: { width: 393, height: 720 }, isMobile: true, hasTouch: true });
  const page = await context.newPage();
  const errors = [];
  page.on('dialog', async dialog => {
    errors.push('Unexpected native browser dialog: ' + dialog.type());
    await dialog.dismiss();
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
    const authorization = new URL(result.body.authorization_url);
    assert.equal(authorization.origin, origin);
    assert.equal(authorization.pathname, '/auth/web/login');
    assert.ok(authorization.search === '?attempt=' + result.body.attempt_id &&
      !authorization.username && !authorization.password && !authorization.hash,
    'authorization URL contains the expected attempt and no additional data');
    await page.goto(result.body.authorization_url.replace('/web/login', '/web/' + mode));
    await page.locator('#account-form').waitFor();
    assert.equal(await page.locator('#account-form').getAttribute('data-mode'), mode);
    assert.equal(await page.locator('dialog').count(), 1);
    assert.equal(await page.locator('#account-status, .form-status').count(), 0);
    return { state, verifier };
  }
  async function fillRegistration(confirmation = password, username = prefix, email = prefix + '@example.test') {
    await page.locator('[name=username]').fill(username);
    await page.locator('[name=email]').fill(email);
    await page.locator('[name=password]').fill(password);
    await page.locator('[name=password_confirmation]').fill(confirmation);
  }
  async function submit(status) {
    const response = page.waitForResponse(res => ['/auth/login', '/auth/register'].includes(new URL(res.url()).pathname));
    await page.locator('#account-submit').click();
    assert.equal((await response).status(), status);
  }
  async function feedback(title, message, { kind = 'error', action = 'Thử lại' } = {}) {
    const modal = page.getByRole('alertdialog', { name: title, exact: true });
    await modal.waitFor();
    assert.equal(await page.locator('dialog[open]').count(), 1);
    assert.equal(await modal.getAttribute('data-kind'), kind);
    assert.equal(await modal.locator('#notice-message').innerText(), message);
    assert.equal(await page.locator('#account-form').getAttribute('aria-busy'), 'false');
    const button = modal.getByRole(kind === 'success' ? 'link' : 'button', { name: action, exact: true });
    assert.ok(await button.evaluate(node => node === document.activeElement));
    const bounds = await modal.boundingBox();
    const viewport = page.viewportSize();
    assert.ok(bounds.x >= 0 && bounds.x + bounds.width <= viewport.width && bounds.y >= 0 && bounds.y + bounds.height <= viewport.height,
      'the full modal fits in the mobile viewport');
  }
  async function dismiss(field, keyboard = false) {
    if (keyboard) await page.keyboard.press('Escape');
    else await page.locator('#notice-dismiss').click();
    await page.locator('dialog[open]').waitFor({ state: 'detached' });
    if (field) assert.ok(await page.locator(`[name=${field}]`).evaluate(input => input === document.activeElement));
  }
  async function exchange(pending) {
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
  await page.locator('#account-submit').click();
  await feedback('Kiểm tra thông tin', 'Vui lòng nhập tên tài khoản.', { kind: 'warning' });
  await page.keyboard.press('Tab');
  await page.keyboard.press('Shift+Tab');
  assert.ok(await page.locator('#notice-dismiss').evaluate(button => button === document.activeElement));
  await dismiss('username', true);
  await fillRegistration('different confirmation 123');
  await page.locator('#account-submit').click();
  await feedback('Kiểm tra thông tin', 'Mật khẩu xác nhận chưa khớp.', { kind: 'warning' });
  await dismiss('password_confirmation');
  await fillRegistration();
  await submit(201);
  await feedback('Đăng ký thành công!', 'Tài khoản của bạn đã được tạo thành công.', { kind: 'success', action: 'Vào trò chơi' });
  const registered = await exchange(signup);
  if (env.BROWSER_TEST_ARTIFACTS) {
    mkdirSync(env.BROWSER_TEST_ARTIFACTS, { recursive: true });
    await page.screenshot({ path: join(env.BROWSER_TEST_ARTIFACTS, 'registration-success.png'), animations: 'disabled' });
  }

  await attempt('register');
  await fillRegistration();
  await submit(409);
  await feedback('Thông tin đã được sử dụng', 'Tên tài khoản và email đã được sử dụng. Vui lòng chọn tên và email khác.', { kind: 'warning' });
  assert.equal(await page.locator('[name=username]').inputValue(), prefix);
  assert.equal(await page.locator('[name=email]').inputValue(), prefix + '@example.test');
  assert.equal(await page.locator('[name=password]').inputValue(), '');
  await dismiss('username');
  await fillRegistration(password, prefix, 'other_' + prefix + '@example.test');
  await submit(409);
  await feedback('Tên tài khoản đã tồn tại', 'Tên tài khoản này đã được sử dụng. Vui lòng chọn tên khác.', { kind: 'warning' });
  await dismiss('username');
  await page.setViewportSize({ width: 320, height: 640 });
  await fillRegistration(password, prefix + '_2', prefix + '@example.test');
  await submit(409);
  await feedback('Email đã được đăng ký', 'Email này đã được sử dụng. Vui lòng nhập email khác.', { kind: 'warning', action: 'Đã hiểu' });
  if (env.BROWSER_TEST_ARTIFACTS) {
    await page.screenshot({ path: join(env.BROWSER_TEST_ARTIFACTS, 'duplicate-email.png'), animations: 'disabled' });
  }
  await dismiss('email');
  await page.setViewportSize({ width: 393, height: 720 });

  await attempt();
  await page.locator('[name=login]').fill(prefix);
  await page.locator('[name=password]').fill(password);
  await page.evaluate(() => { document.querySelector('[name=csrf]').value = 'x'.repeat(43); });
  await submit(403);
  await feedback('Phiên biểu mẫu không hợp lệ', 'Hãy mở lại đăng nhập từ Green Farm 3.', { kind: 'warning', action: 'Đã hiểu' });

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
  const submitting = submit(401);
  await page.locator('#account-form[aria-busy=true]').waitFor();
  assert.ok(await page.locator('#account-submit').isDisabled());
  assert.equal(await page.locator('dialog[open]').count(), 0, 'loading stays on the submit button');
  assert.equal(await page.locator('#submit-label').innerText(), 'Đang đăng nhập…');
  await page.evaluate(() => document.getElementById('account-form').requestSubmit());
  releaseRequest();
  await submitting;
  assert.equal(loginRequests, 1, 'loading ignores duplicate submits while the real request is pending');
  await page.unroute('**/auth/login');
  await feedback('Đăng nhập thất bại', 'Tên tài khoản hoặc mật khẩu không chính xác.');
  await page.waitForTimeout(450);
  assert.ok(await page.getByRole('alertdialog').isVisible(), 'an error waits for acknowledgement');
  await dismiss('password');
  await page.locator('[name=login]').fill(prefix);
  await page.locator('[name=password]').fill('wrong password 123');
  await submit(401);
  await feedback('Đăng nhập thất bại', 'Tên tài khoản hoặc mật khẩu không chính xác.');
  await dismiss('password');
  await page.route('**/auth/login', route => route.abort('internetdisconnected'));
  await page.locator('[name=password]').fill(password);
  await page.locator('#account-submit').click();
  await feedback('Không thể kết nối', 'Không thể kết nối với máy chủ. Vui lòng kiểm tra Internet và thử lại.');
  await dismiss();
  await page.unroute('**/auth/login');
  await page.locator('[name=password]').fill(password);
  await submit(200);
  await feedback('Đăng nhập thành công!', 'Xác thực thành công. Bạn có thể quay lại Green Farm 3.', { kind: 'success', action: 'Vào trò chơi' });
  const signedIn = await exchange(login);
  assert.equal(signedIn.user.game_id, registered.user.game_id);
  // The handler is absent on the host. Verify that Enter still requests the
  // validated custom-protocol URL through the real manual link.
  const manualLink = page.getByRole('link', { name: 'Vào trò chơi', exact: true });
  const manualCallback = await manualLink.getAttribute('href');
  const manualResult = new Promise(resolve => {
    cdp.on('Page.frameRequestedNavigation', event => {
      if (event.url === manualCallback) resolve(event.reason);
    });
  });
  await manualLink.press('Enter');
  assert.equal(await Promise.race([manualResult, delay(2500, 'no request', { ref: false })]), 'anchorClick');
  await page.close();
  assert.equal((await api('/auth/me', undefined, signedIn.access_token)).body.user.game_id, registered.user.game_id,
    'closing the browser does not discard the verified app session');
  const refreshed = await api('/auth/refresh', { refresh_token: signedIn.refresh_token });
  assert.equal(refreshed.status, 200);
  assert.equal(refreshed.body.user.game_id, registered.user.game_id);
  assert.deepEqual(errors, [], 'no JavaScript exception or native browser dialog prevented submit or feedback');
  assert.deepEqual(violations, [], 'the actual response CSP allowed all required assets, requests and feedback');
  assert.deepEqual(backendErrors, []);
});
