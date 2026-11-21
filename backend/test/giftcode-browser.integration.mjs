import assert from 'node:assert/strict';
import { createHash, randomBytes, randomUUID, X509Certificate } from 'node:crypto';
import { readFileSync } from 'node:fs';
import { createServer as createHttpServer } from 'node:http';
import { createServer } from 'node:https';
import { test } from 'node:test';
import { chromium } from 'playwright';
import { hashPassword } from '../src/auth/passwords.js';
import { loadConfig, readEnvironment } from '../src/config.js';
import { migrate } from '../src/db/migrate.js';
import { createMysqlStore, createPoolFromConfig } from '../src/db/store.js';
import { createResearchServer } from '../src/server.js';

const env = readEnvironment();

test('Chromium quản trị tạo mã, cập nhật và hiển thị phần thưởng đã chốt từ MySQL', { timeout: 120000 }, async t => {
  const tls = {
    key: readFileSync(env.BROWSER_TEST_TLS_KEY),
    cert: readFileSync(env.BROWSER_TEST_TLS_CERT),
  };
  const server = createServer(tls);
  t.after(async () => {
    server.closeAllConnections();
    if (server.listening) await new Promise(resolve => server.close(resolve));
  });
  await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
  const origin = 'https://localhost:' + server.address().port;
  const password = randomBytes(24).toString('hex');
  const config = loadConfig({ ...env, AUTH_PUBLIC_BASE_URL: origin, LEGACY_AUTH_ENABLED: 'false',
    GIFTCODE_ADMIN_PASSWORD_HASH: await hashPassword(password) });
  assert.match(config.database.database, /_test$/u, 'Use an isolated test database');
  const pool = createPoolFromConfig(config.database);
  const store = createMysqlStore(config.database);
  const code = 'WEB-' + randomBytes(6).toString('hex').toUpperCase();
  let person;
  let browser;
  let migrationsReady = false;
  t.after(async () => {
    await browser?.close();
    try {
      if (migrationsReady) {
        const [[created]] = await pool.execute('SELECT id FROM gf_giftcodes WHERE code = ?', [code]);
        if (created) {
          await pool.execute('DELETE FROM gf_giftcode_redemptions WHERE giftcode_id = ?', [created.id]);
          await pool.execute('DELETE FROM gf_giftcode_audit WHERE giftcode_id = ?', [created.id]);
          await pool.execute('DELETE FROM gf_giftcodes WHERE id = ?', [created.id]);
        }
        if (person) {
          await pool.execute('DELETE FROM gf_auth_sessions WHERE user_id = ?', [person.id]);
          await pool.execute('DELETE FROM gf_auth_session_families WHERE user_id = ?', [person.id]);
          await pool.execute('DELETE FROM users WHERE id = ?', [person.id]);
        }
      }
    } finally {
      await Promise.allSettled([store.close(), pool.end()]);
    }
  });
  await migrate(pool);
  migrationsReady = true;
  const production = createResearchServer({ config, store, log: () => {} });
  server.on('request', production.listeners('request')[0]);
  const certificatePin = env.BROWSER_TEST_PIN_LOCAL_CERT === '1'
    ? createHash('sha256').update(new X509Certificate(tls.cert).publicKey.export({ type: 'spki', format: 'der' })).digest('base64')
    : undefined;
  browser = await chromium.launch({
    headless: true,
    args: certificatePin ? ['--ignore-certificate-errors-spki-list=' + certificatePin] : [],
    ...(env.CHROMIUM_EXECUTABLE_PATH ? { executablePath: env.CHROMIUM_EXECUTABLE_PATH } : {}),
  });
  const context = await browser.newContext({ viewport: { width: 1200, height: 880 } });
  context.setDefaultTimeout(10000);
  const plain = createHttpServer(production.listeners('request')[0]);
  t.after(async () => {
    plain.closeAllConnections();
    if (plain.listening) await new Promise(resolve => plain.close(resolve));
  });
  await new Promise(resolve => plain.listen(0, '127.0.0.1', resolve));
  const insecure = await context.newPage();
  await insecure.goto('http://127.0.0.1:' + plain.address().port + '/admin/giftcodes');
  await insecure.getByText('Trang quản trị yêu cầu kết nối HTTPS.').waitFor();
  assert.ok(await insecure.getByLabel('Mật khẩu quản trị').isDisabled());
  assert.ok(await insecure.getByRole('button', { name: 'Đăng nhập' }).isDisabled());
  await insecure.close();
  const page = await context.newPage();
  const pageErrors = [];
  page.on('pageerror', error => pageErrors.push(error.message));
  const loaded = await page.goto(origin + '/admin/giftcodes');
  assert.equal(loaded.status(), 200);
  assert.equal(loaded.headers()['cache-control'], 'no-store');
  assert.match(loaded.headers()['content-security-policy'], /frame-ancestors 'none'/u);
  const catalog = '/admin/api/giftcodes/catalog';
  assert.equal(await page.evaluate(async path => (await fetch(path)).status, catalog), 401);

  await page.getByLabel('Mật khẩu quản trị').fill('wrong-admin-passphrase');
  await page.getByRole('button', { name: 'Đăng nhập' }).click();
  await page.getByText('Mật khẩu quản trị chưa đúng.').waitFor();
  await page.getByLabel('Mật khẩu quản trị').fill(password);
  await page.getByRole('button', { name: 'Đăng nhập' }).click();
  await page.getByRole('heading', { name: 'Danh sách giftcode' }).waitFor();
  const adminCookie = (await context.cookies()).find(cookie => cookie.name === '__Host-gf_giftcode_admin');
  assert.equal(adminCookie?.httpOnly, true);
  assert.equal(adminCookie?.secure, true);
  assert.equal(adminCookie?.sameSite, 'Strict');
  assert.deepEqual(await page.evaluate(() => [localStorage.length, sessionStorage.length]), [0, 0]);

  await page.getByRole('button', { name: '+ Tạo giftcode' }).click();
  await page.getByRole('button', { name: 'Tạo ngẫu nhiên' }).click();
  assert.match(await page.getByLabel('Mã giftcode').inputValue(), /^GF-[0-9A-F]{16}$/u);
  await page.getByLabel('Mã giftcode').fill(code.toLowerCase().replace('-', ' - '));
  await page.getByLabel('Giới hạn lượt nhận').fill('5');
  await page.getByLabel('Tìm vật phẩm').fill('xu');
  await page.getByRole('button', { name: '+ Thêm' }).click();
  await page.getByLabel('Số lượng Xu').fill('20');
  await page.getByLabel('Tìm vật phẩm').fill('cash');
  await page.getByRole('button', { name: '+ Thêm' }).click();
  await page.getByLabel('Số lượng Tiền mặt').fill('2');
  await page.getByRole('button', { name: 'Xem trước' }).click();
  const preview = page.locator('#preview');
  await preview.getByText(code, { exact: true }).waitFor();
  await preview.getByText('× 20', { exact: true }).waitFor();
  await preview.getByText('× 2', { exact: true }).waitFor();
  await page.waitForFunction(() => [...document.querySelectorAll('#preview-rewards img')].length === 2 &&
    [...document.querySelectorAll('#preview-rewards img')].every(image => image.complete && image.naturalWidth > 0));
  const createStarted = performance.now();
  await page.getByRole('button', { name: 'Lưu giftcode' }).click();
  await page.getByText('Đã lưu giftcode ' + code + '.').waitFor();
  t.diagnostic('Chromium click → ghi MySQL → thông báo: ' + Math.round(performance.now() - createStarted) + ' ms');

  // Seed an ordinary authenticated identity, then use the real HTTPS player endpoints.
  const now = Math.floor(Date.now() / 1000);
  const session = randomBytes(32).toString('base64url');
  const access = randomBytes(32).toString('base64url');
  person = await store.social.upsertUser('facebook', code, 'Browser giftcode test', now);
  await pool.execute('INSERT INTO gf_auth_session_families (id, user_id, created_at) VALUES (?, ?, ?)', [session, person.id, now]);
  await pool.execute(`INSERT INTO gf_auth_sessions
    (id, user_id, family_id, access_hash, refresh_hash, issued_at, access_expires_at, refresh_expires_at)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)`, [
    session, person.id, session, createHash('sha256').update(access).digest(), randomBytes(32), now, now + 900, now + 3600,
  ]);
  const farmId = randomUUID();
  async function player(path, body) {
    return page.evaluate(async ({ path, body, access }) => {
      const response = await fetch(path, {
        method: 'POST', credentials: 'omit',
        headers: { Authorization: 'Bearer ' + access, 'Content-Type': 'application/json' },
        body: JSON.stringify(body),
      });
      return { status: response.status, body: await response.json() };
    }, { path, body, access });
  }
  const denied = await player('/admin/api/giftcodes', { code, rewards: [{ id: 'coins', quantity: 1 }] });
  assert.equal(denied.status, 401);
  const redeemed = await player('/giftcodes/redeem', { code, farm_id: farmId });
  assert.equal(redeemed.status, 201);
  assert.deepEqual(redeemed.body.rewards, [{ id: 'coins', quantity: 20 }, { id: 'cash', quantity: 2 }]);
  assert.equal((await player('/giftcodes/ack', { farm_id: farmId, grant_id: redeemed.body.grant_id })).status, 200);

  await page.getByLabel('Tìm theo mã').fill(code.toLowerCase());
  await page.getByRole('button', { name: 'Tìm', exact: true }).click();
  const row = page.getByRole('row').filter({ hasText: code });
  await row.getByText('1 / 5').waitFor();
  await row.getByRole('button', { name: 'Chi tiết ' + code }).click();
  await page.getByRole('heading', { name: 'Chi tiết ' + code }).waitFor();
  await page.getByLabel('Số lượng Xu').fill('30');
  await page.getByLabel('Bật giftcode').uncheck();
  await page.getByRole('button', { name: 'Xem trước' }).click();
  await preview.getByText('× 30', { exact: true }).waitFor();
  await page.getByRole('button', { name: 'Lưu giftcode' }).click();
  await row.getByText('Đã tắt', { exact: true }).waitFor();
  await page.getByLabel('Trạng thái', { exact: true }).selectOption('disabled');
  await row.getByRole('button', { name: 'Lịch sử ' + code }).click();
  const history = page.getByRole('dialog', { name: 'Lịch sử ' + code });
  await history.getByText('Đã lưu vào game', { exact: true }).waitFor();
  await history.getByText('× 20', { exact: true }).waitFor();
  await history.getByText('× 2', { exact: true }).waitFor();
  assert.equal(await history.getByText('× 30', { exact: true }).count(), 0);

  await page.setViewportSize({ width: 375, height: 760 });
  assert.equal(await page.evaluate(() => document.documentElement.scrollWidth), 375);
  await history.getByRole('button', { name: 'Đóng' }).click();
  await page.getByRole('button', { name: 'Đăng xuất' }).click();
  await page.getByRole('heading', { name: 'Đăng nhập quản trị' }).waitFor();
  assert.equal(await page.evaluate(async path => (await fetch(path)).status, catalog), 401);
  assert.deepEqual(pageErrors, []);
});
