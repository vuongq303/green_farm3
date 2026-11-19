import assert from 'node:assert/strict';
import { createServer } from 'node:http';
import { createHash, randomBytes } from 'node:crypto';
import { after, test } from 'node:test';
import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { createPoolFromConfig } from '../src/db/store.js';
import { createSocialStore } from '../src/db/social-store.js';
import { migrate } from '../src/db/migrate.js';
import { APP_REDIRECT_URI, loadSocialAuthConfig } from '../src/auth/config.js';
import { createSocialAuthRouter } from '../src/auth/router.js';
import { ProviderError } from '../src/auth/providers.js';

loadEnvironment();
const database = loadDatabaseConfig();
database.database = process.env.TEST_DB_NAME ?? database.database;
if (!database.database.endsWith('_test')) throw new Error('Password HTTP tests require a dedicated database ending in _test');
const pool = createPoolFromConfig(database);
after(() => pool.end());
const value = () => randomBytes(32).toString('base64url');
const challenge = text => createHash('sha256').update(text).digest('base64url');
const password = 'actual MySQL password test 123';

async function fixture(t, extra = {}) {
  await migrate(pool);
  const prefix = 'pw_' + randomBytes(8).toString('hex');
  const { authEnvironment = {}, transformStore = store => store, ...routerOptions } = extra;
  const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: 'https://accounts.example.test', ...authEnvironment });
  const attempts = [], errors = [];
  let server, origin;
  async function start() {
    const router = createSocialAuthRouter({ config, store: transformStore(createSocialStore(pool)), isSecureRequest: () => true,
      errorLog: entry => errors.push(entry), ...routerOptions });
    server = createServer((req, res) => { void router.handle(req, res, new URL(req.url, 'http://test.invalid')); });
    await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
    origin = 'http://127.0.0.1:' + server.address().port;
  }
  async function stop() { server.closeAllConnections(); await new Promise(resolve => server.close(resolve)); }
  t.after(async () => {
    if (server?.listening) await stop();
    const [users] = await pool.execute('SELECT CAST(user_id AS CHAR) AS id FROM gf_password_accounts WHERE username LIKE ?', [prefix + '%']);
    for (const { id } of users) {
      await pool.execute('DELETE FROM gf_auth_sessions WHERE user_id = ?', [id]);
      await pool.execute('DELETE FROM gf_auth_codes WHERE user_id = ?', [id]);
      await pool.execute('DELETE FROM users WHERE id = ?', [id]);
    }
    for (const id of attempts) {
      await pool.execute('DELETE FROM gf_auth_codes WHERE attempt_id = ?', [id]);
      await pool.execute('DELETE FROM gf_auth_attempts WHERE id = ?', [id]);
    }
  });
  await start();
  const get = (path, headers = {}) => fetch(origin + path, { redirect: 'manual', headers });
  const post = (path, body, headers = {}) => fetch(origin + path, { method: 'POST', redirect: 'manual',
    headers: { 'Content-Type': 'application/json', ...headers }, body: JSON.stringify(body) });
  async function form() {
    const verifier = value(), state = value();
    const response = await post('/auth/attempt', { provider: 'password', state, code_challenge: challenge(verifier), redirect_uri: APP_REDIRECT_URI });
    assert.equal(response.status, 201);
    const attempt = await response.json(); attempts.push(attempt.attempt_id);
    assert.equal(attempt.authorization_url, config.publicBaseUrl + '/auth/web/login?attempt=' + attempt.attempt_id);
    const page = await get('/auth/web/login?attempt=' + attempt.attempt_id);
    assert.equal(page.status, 200);
    const setCookie = page.headers.get('set-cookie');
    assert.match(setCookie, /^__Host-gf_auth_web=[A-Za-z0-9_-]{43}\.[A-Za-z0-9_-]{43}; Path=\/; Secure; HttpOnly; SameSite=Strict; Max-Age=/u);
    assert.equal(page.headers.get('cache-control'), 'no-store');
    const cookie = setCookie.split(';')[0], html = await page.text();
    const csrf = html.match(/name="csrf" value="([A-Za-z0-9_-]{43})"/u)[1];
    assert.ok(!html.includes('access_token') && !html.includes('refresh_token'));
    return { verifier, state, attempt: attempt.attempt_id, cookie, csrf,
      headers: { Cookie: cookie, Origin: config.publicBaseUrl, 'Sec-Fetch-Site': 'same-origin' } };
  }
  async function exchange(form, response) {
    assert.equal(response.headers.get('set-cookie'), '__Host-gf_auth_web=; Path=/; Secure; HttpOnly; SameSite=Strict; Max-Age=0');
    const result = await response.json();
    assert.deepEqual(Object.keys(result), ['callback_url']);
    const target = new URL(result.callback_url);
    assert.equal(target.protocol, 'com.quanhoang.greenfarm3:');
    assert.equal(target.searchParams.get('state'), form.state);
    assert.deepEqual([...target.searchParams.keys()].sort(), ['code', 'state']);
    const code = target.searchParams.get('code');
    assert.equal((await post('/auth/exchange', { code, code_verifier: value(), redirect_uri: APP_REDIRECT_URI })).status, 400);
    const exchanged = await post('/auth/exchange', { code, code_verifier: form.verifier, redirect_uri: APP_REDIRECT_URI });
    assert.equal(exchanged.status, 200);
    assert.equal((await post('/auth/exchange', { code, code_verifier: form.verifier, redirect_uri: APP_REDIRECT_URI })).status, 400);
    return exchanged.json();
  }
  return { prefix, config, errors, get, post, form, exchange, attempts, restart: async () => { await stop(); await start(); } };
}

async function publicError(response, status, code, message, conflicts) {
  assert.equal(response.status, status);
  assert.match(response.headers.get('content-type'), /^application\/json/u);
  assert.equal(response.headers.get('cache-control'), 'no-store');
  const result = await response.json();
  assert.deepEqual(result, { error: { code, message, ...(conflicts ? { conflicts } : {}) } });
  return result;
}

test('real password API returns public errors and keeps the pending form usable after validation or temporary database failure', async t => {
  let unavailable = false;
  const f = await fixture(t, { transformStore: store => ({ ...store,
    async getPasswordWebSession(...args) {
      if (unavailable) throw Object.assign(new Error('private database connection diagnostic'), { code: 'DATABASE_UNAVAILABLE' });
      return store.getPasswordWebSession(...args);
    },
  }) });
  const form = await f.form();
  const body = { attempt: form.attempt, csrf: form.csrf, username: f.prefix,
    email: f.prefix + '@example.test', password, password_confirmation: password };
  for (const invalid of [{ username: '' }, { email: '' }, { password: 'short', password_confirmation: 'short' },
    { password_confirmation: password + '-different' }]) {
    await publicError(await f.post('/auth/register', { ...body, ...invalid }, form.headers), 400,
      'invalid_registration', 'Kiểm tra tên tài khoản, email và mật khẩu (12–128 ký tự); hai mật khẩu phải khớp.');
  }
  await publicError(await f.post('/auth/register', { ...body, csrf: value() }, form.headers), 403,
    'csrf_failed', 'Phiên biểu mẫu không hợp lệ. Hãy đóng và mở lại đăng nhập từ trò chơi.');
  unavailable = true;
  await publicError(await f.post('/auth/register', body, form.headers), 503, 'database_unavailable',
    'Máy chủ tài khoản tạm thời không sẵn sàng. Hãy thử lại.');
  assert.deepEqual(f.errors, [{ event: 'social_auth_error', status: 503, code: 'database_unavailable' }],
    'neither the public error nor structured server log includes database diagnostics');
  unavailable = false;
  const registered = await f.post('/auth/register', body, form.headers);
  assert.equal(registered.status, 201, 'retries reuse the original unconsumed form and real database');
  assert.equal((await f.exchange(form, registered)).user.display_name, f.prefix);
});

test('registration reports normalized username, email and combined conflicts without changing existing identities', async t => {
  const f = await fixture(t);
  for (const username of [f.prefix, f.prefix + '_b']) {
    const signup = await f.form();
    const registered = await f.post('/auth/register', { attempt: signup.attempt, csrf: signup.csrf,
      username, email: username + '@example.test', password, password_confirmation: password }, signup.headers);
    assert.equal(registered.status, 201);
    await f.exchange(signup, registered);
  }
  const originalRows = async () => (await pool.execute(`SELECT u.*, p.* FROM users u
    INNER JOIN gf_password_accounts p ON p.user_id = u.id WHERE p.username IN (?, ?) ORDER BY u.id`,
  [f.prefix, f.prefix + '_b']))[0];
  const before = await originalRows();
  const scenarios = [
    [f.prefix.toUpperCase(), f.prefix + '_new@example.test', ['username'], 'Tên tài khoản đã tồn tại. Vui lòng chọn tên khác.'],
    [f.prefix + '_new', (f.prefix + '@example.test').toUpperCase(), ['email'], 'Email đã được đăng ký. Vui lòng sử dụng email khác.'],
    [f.prefix.toUpperCase(), (f.prefix + '@example.test').toUpperCase(), ['username', 'email'], 'Tên tài khoản và email đã được sử dụng.'],
    [f.prefix.toUpperCase(), (f.prefix + '_b@example.test').toUpperCase(), ['username', 'email'], 'Tên tài khoản và email đã được sử dụng.'],
  ];
  for (const [index, [username, email, conflicts, message]] of scenarios.entries()) {
    const form = await f.form();
    const fields = { attempt: form.attempt, csrf: form.csrf, username, email, password, password_confirmation: password };
    await publicError(await f.post('/auth/register', fields, form.headers), 409, 'account_exists', message, conflicts);
    const corrected = f.prefix + '_r' + index;
    const registered = await f.post('/auth/register', { ...fields, username: corrected, email: corrected + '@example.test' }, form.headers);
    assert.equal(registered.status, 201, 'a conflict never consumes the attempt or creates a session');
    assert.equal((await f.exchange(form, registered)).user.display_name, corrected);
  }
  assert.deepEqual(await originalRows(), before, 'duplicate registration never updates existing account data');
});

for (const [description, authEnvironment, facebookConfigured] of [
  ['incomplete and invalid Facebook/Google configuration', {
    FACEBOOK_APP_ID: '123456789', GOOGLE_OAUTH_CLIENT_SECRET: 'incomplete-google-test-secret',
  }, false],
  ['a Facebook provider API failure', {
    FACEBOOK_APP_ID: '123456789', FACEBOOK_APP_SECRET: 'valid-facebook-test-secret',
  }, true],
]) {
  test(`website register/login/remembered sessions remain independent of ${description}`, async t => {
    let providerCalls = 0;
    const providers = {
      authorizationUrl(provider, attempt, state) {
        ++providerCalls;
        assert.equal(provider, 'facebook');
        return 'https://www.facebook.com/v24.0/dialog/oauth?state=' + state;
      },
      async verifyIdentity() { ++providerCalls; throw new ProviderError(); },
    };
    const f = await fixture(t, { authEnvironment, providers });
    const facebook = await f.post('/auth/attempt', { provider: 'facebook', state: value(),
      code_challenge: value(), redirect_uri: APP_REDIRECT_URI });
    if (facebookConfigured) {
      assert.equal(facebook.status, 201);
      const attempt = await facebook.json(); f.attempts.push(attempt.attempt_id);
      const redirect = await f.get('/auth/browser/start?id=' + attempt.attempt_id);
      assert.equal(redirect.status, 302);
      const state = new URL(redirect.headers.get('location')).searchParams.get('state');
      const refused = await f.get('/auth/callback/facebook?state=' + state + '&code=fixture-provider-code');
      assert.equal(refused.status, 502);
      assert.ok(!(await refused.text()).includes('valid-facebook-test-secret'));
      assert.equal(providerCalls, 2);
    } else {
      assert.equal(facebook.status, 503);
      assert.equal((await facebook.json()).error.code, 'oauth_not_configured');
      assert.equal(providerCalls, 0);
    }
    const callsBeforeWebsite = providerCalls;
    const signup = await f.form();
    const registration = await f.post('/auth/register', { attempt: signup.attempt, csrf: signup.csrf,
      username: f.prefix, email: f.prefix + '@example.test', password, password_confirmation: password }, signup.headers);
    assert.equal(registration.status, 201);
    const registered = await f.exchange(signup, registration);
    assert.equal(registered.user.provider, 'password');
    assert.equal(registered.user.display_name, f.prefix);
    await f.restart();
    assert.deepEqual((await (await f.get('/auth/me', { Authorization: 'Bearer ' + registered.access_token })).json()).user,
      registered.user, 'remembered website account is checked without Facebook');
    const login = await f.form();
    const signedIn = await f.post('/auth/login', { attempt: login.attempt, csrf: login.csrf,
      login: f.prefix + '@EXAMPLE.TEST', password }, login.headers);
    assert.equal(signedIn.status, 200);
    const repeated = await f.exchange(login, signedIn);
    assert.equal(repeated.user.game_id, registered.user.game_id);
    const refresh = await f.post('/auth/refresh', { refresh_token: repeated.refresh_token });
    assert.equal(refresh.status, 200);
    const rotated = await refresh.json();
    assert.equal(rotated.user.game_id, registered.user.game_id);
    assert.equal((await f.post('/auth/logout', { refresh_token: rotated.refresh_token },
      { Authorization: 'Bearer ' + rotated.access_token })).status, 204);
    assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + rotated.access_token })).status, 401);
    assert.equal(providerCalls, callsBeforeWebsite, 'website routes and sessions never call any OAuth provider');
  });
}

test('real password website/HTTP/MySQL register, PKCE exchange, restart, remembered identity, refresh and logout', async t => {
  const f = await fixture(t), form = await f.form();
  let response = await f.get('/auth/web/register?attempt=' + form.attempt, { Cookie: form.cookie });
  assert.equal(response.status, 200, 'mode switching reuses the bound HttpOnly cookie');
  assert.ok((await response.text()).includes('name="password_confirmation"'));
  response = await f.post('/auth/register', { attempt: form.attempt, csrf: form.csrf, username: f.prefix.toUpperCase(),
    email: f.prefix + '@Example.TEST', password, password_confirmation: password }, form.headers);
  assert.equal(response.status, 201);
  const registered = await f.exchange(form, response);
  assert.equal(registered.user.provider, 'password');
  assert.match(registered.user.game_id, /^GF[0-9]{6,}$/u);
  const [[stored]] = await pool.execute('SELECT password_hash, username, email FROM gf_password_accounts WHERE username = ?', [f.prefix]);
  assert.match(stored.password_hash, /^\$argon2id\$v=19\$m=65536,t=3,p=1\$/u);
  assert.ok(!stored.password_hash.includes(password));
  assert.equal(stored.email, f.prefix + '@example.test');
  await f.restart();
  assert.deepEqual((await (await f.get('/auth/me', { Authorization: 'Bearer ' + registered.access_token })).json()).user, registered.user);
  const login = await f.form();
  response = await f.post('/auth/login', { attempt: login.attempt, csrf: login.csrf,
    login: f.prefix + '@EXAMPLE.test', password }, login.headers);
  assert.equal(response.status, 200);
  const repeated = await f.exchange(login, response);
  assert.equal(repeated.user.game_id, registered.user.game_id);
  response = await f.post('/auth/refresh', { refresh_token: repeated.refresh_token });
  assert.equal(response.status, 200);
  const rotated = await response.json();
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + repeated.access_token })).status, 401);
  assert.equal((await f.post('/auth/logout', { refresh_token: repeated.refresh_token }, { Authorization: 'Bearer ' + repeated.access_token })).status, 204);
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + rotated.access_token })).status, 401);
  assert.equal((await f.post('/auth/refresh', { refresh_token: rotated.refresh_token })).status, 401);
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + registered.access_token })).status, 200);
});

test('browser password handoff survives a backend restart but its code expires after sixty seconds', async t => {
  let clock = Date.now();
  const f = await fixture(t, { now: () => clock }), signup = await f.form();
  const response = await f.post('/auth/register', { attempt: signup.attempt, csrf: signup.csrf,
    username: f.prefix, email: f.prefix + '@example.test', password, password_confirmation: password }, signup.headers);
  assert.equal(response.status, 201);
  const result = await response.json();
  assert.deepEqual(Object.keys(result), ['callback_url']);
  const callback = new URL(result.callback_url);
  assert.equal(callback.origin, 'null', 'handoff uses the fixed app deep link, not an arbitrary website');
  assert.equal(callback.protocol + '//' + callback.host + callback.pathname, APP_REDIRECT_URI);
  assert.equal(callback.searchParams.get('state'), signup.state);
  assert.deepEqual([...callback.searchParams.keys()].sort(), ['code', 'state']);
  const body = { code: callback.searchParams.get('code'), code_verifier: signup.verifier, redirect_uri: APP_REDIRECT_URI };
  await f.restart();
  clock += 60_000;
  const expired = await f.post('/auth/exchange', body);
  assert.equal(expired.status, 400);
  assert.equal((await expired.json()).error.code, 'invalid_code');
  const [[sessions]] = await pool.execute(`SELECT COUNT(*) AS total FROM gf_auth_sessions s
    JOIN users u ON u.id = s.user_id WHERE u.display_name = ?`, [f.prefix]);
  assert.equal(Number(sessions.total), 0, 'expiry cannot create or persist an app session');
  // The real registered identity is retained; only the short-lived handoff expired.
  const login = await f.form();
  const signedIn = await f.post('/auth/login', { attempt: login.attempt, csrf: login.csrf,
    login: f.prefix, password }, login.headers);
  assert.equal(signedIn.status, 200);
  assert.equal((await f.exchange(login, signedIn)).user.display_name, f.prefix);
});

test('password HTTP rejects cross-origin/CSRF and duplicate accounts, keeps wrong-login retry, and enforces disabled status', async t => {
  const f = await fixture(t), form = await f.form();
  const body = { attempt: form.attempt, csrf: form.csrf, username: f.prefix,
    email: f.prefix + '@example.test', password, password_confirmation: password };
  let response = await f.post('/auth/register', body, { ...form.headers, Origin: 'https://attacker.invalid' });
  assert.equal(response.status, 403);
  response = await f.post('/auth/register', { ...body, csrf: value() }, form.headers);
  assert.equal(response.status, 403);
  response = await f.post('/auth/register', body, { Origin: f.config.publicBaseUrl });
  assert.equal(response.status, 403);
  response = await f.post('/auth/register', body, form.headers);
  assert.equal(response.status, 201);
  const account = await f.exchange(form, response);
  const duplicate = await f.form();
  response = await f.post('/auth/register', { ...body, attempt: duplicate.attempt, csrf: duplicate.csrf }, duplicate.headers);
  await publicError(response, 409, 'account_exists', 'Tên tài khoản và email đã được sử dụng.', ['username', 'email']);
  response = await f.post('/auth/login', { attempt: duplicate.attempt, csrf: duplicate.csrf, login: f.prefix, password: password + '-wrong' }, duplicate.headers);
  assert.equal(response.status, 401);
  const invalid = await publicError(response, 401, 'invalid_credentials', 'Tên tài khoản/email hoặc mật khẩu không đúng.');
  response = await f.post('/auth/login', { attempt: duplicate.attempt, csrf: duplicate.csrf, login: f.prefix + '_missing', password }, duplicate.headers);
  assert.equal(response.status, 401);
  assert.deepEqual(await response.json(), invalid, 'unknown and wrong-password responses do not enumerate accounts');
  response = await f.post('/auth/login', { attempt: duplicate.attempt, csrf: duplicate.csrf, login: f.prefix, password }, duplicate.headers);
  assert.equal(response.status, 200, 'duplicates and wrong passwords leave the same form usable');
  const pendingCode = await response.json();
  await pool.execute("UPDATE gf_password_accounts SET account_status = 'disabled' WHERE username = ?", [f.prefix]);
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + account.access_token })).status, 401);
  assert.equal((await f.post('/auth/refresh', { refresh_token: account.refresh_token })).status, 401);
  assert.equal((await f.post('/auth/exchange', { code: new URL(pendingCode.callback_url).searchParams.get('code'),
    code_verifier: duplicate.verifier, redirect_uri: APP_REDIRECT_URI })).status, 400);
  const disabled = await f.form();
  response = await f.post('/auth/login', { attempt: disabled.attempt, csrf: disabled.csrf, login: f.prefix, password }, disabled.headers);
  assert.equal(response.status, 401);
  assert.deepEqual(await response.json(), invalid);
});

test('password route requires HTTPS, bounds brute force, expires forms and never enables browser OAuth for password', async t => {
  const insecure = await fixture(t, { isSecureRequest: () => false });
  const refused = await insecure.post('/auth/attempt', { provider: 'password', state: value(), code_challenge: value(), redirect_uri: APP_REDIRECT_URI });
  assert.equal(refused.status, 400);
  assert.equal((await refused.json()).error.code, 'https_required');
  const f = await fixture(t), form = await f.form();
  assert.equal((await f.get('/auth/browser/start?id=' + form.attempt)).status, 400);
  assert.equal((await f.get('/auth/web/login?attempt=' + form.attempt + '&next=https://attacker.invalid', { Cookie: form.cookie })).status, 400);
  for (let i = 0; i < 10; i++) {
    const response = await f.post('/auth/login', { attempt: form.attempt, csrf: form.csrf, login: f.prefix + '_missing', password }, form.headers);
    assert.equal(response.status, 401);
  }
  const limited = await f.post('/auth/login', { attempt: form.attempt, csrf: form.csrf, login: f.prefix + '_missing', password }, form.headers);
  assert.equal(limited.headers.get('retry-after'), '900');
  await publicError(limited, 429, 'rate_limited', 'Có quá nhiều yêu cầu đăng nhập. Hãy chờ rồi thử lại.');
  await pool.execute('UPDATE gf_auth_attempts SET expires_at = 1 WHERE id = ?', [form.attempt]);
  assert.equal((await f.get('/auth/web/login?attempt=' + form.attempt, { Cookie: form.cookie })).status, 400);
  const expired = await f.post('/auth/login', { attempt: form.attempt, csrf: form.csrf, login: f.prefix + '_missing', password }, form.headers);
  await publicError(expired, 400, 'attempt_expired', 'Yêu cầu đăng nhập đã hết hạn hoặc đã được sử dụng. Hãy thử lại từ trò chơi.');
});
