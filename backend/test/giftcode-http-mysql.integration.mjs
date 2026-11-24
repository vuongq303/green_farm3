import assert from 'node:assert/strict';
import { createHash, randomBytes, randomUUID } from 'node:crypto';
import { after, test } from 'node:test';
import { APP_REDIRECT_URI } from '../src/auth/config.js';
import { hashPassword } from '../src/auth/passwords.js';
import { loadConfig, loadDatabaseConfig, readEnvironment } from '../src/config.js';
import { migrate } from '../src/db/migrate.js';
import { createMysqlStore, createPoolFromConfig } from '../src/db/store.js';
import { createResearchServer } from '../src/server.js';

const env = readEnvironment();
const database = loadDatabaseConfig({ ...env, DB_NAME: env.TEST_DB_NAME ?? env.DB_NAME });
if (!database.database.endsWith('_test')) throw new Error('Giftcode tests require a dedicated database ending in _test');
const pool = createPoolFromConfig(database);
after(() => pool.end());

const adminPath = '/admin/api/giftcodes';
const publicOrigin = 'https://giftcodes.example.test';
const password = 'a safe test fixture password 123';
const value = () => randomBytes(32).toString('base64url');
const rewards = [{ id: 'coins', quantity: 1200 }, { id: 'cash', quantity: 7 }];

async function expect(response, status) {
  const result = await response.json();
  assert.equal(response.status, status, JSON.stringify(result));
  return result;
}

async function error(response, status, code) {
  assert.deepEqual(await expect(response, status), { error: code });
}

async function fixture(t) {
  await migrate(pool);
  const prefix = randomBytes(8).toString('hex');
  const users = 'gift_' + prefix + '_';
  const codes = 'GIFT-' + prefix.toUpperCase();
  const ids = new Set(), attempts = [];
  const store = createMysqlStore(database);
  const config = loadConfig({ ...env, DB_NAME: database.database, AUTH_PUBLIC_BASE_URL: publicOrigin,
    AUTH_TRUSTED_PROXY_IP: '127.0.0.1', LEGACY_AUTH_ENABLED: 'false',
    GIFTCODE_ADMIN_PASSWORD_HASH: await hashPassword(password) });
  let time = Date.now();
  const server = createResearchServer({
    config, now: () => time, store, log: () => {}, errorLog: () => {},
  });
  await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
  const origin = 'http://127.0.0.1:' + server.address().port;

  t.after(async () => {
    server.closeAllConnections();
    await new Promise(resolve => server.close(resolve));
    await store.close();
    for (const id of ids) {
      await pool.execute('DELETE FROM gf_giftcode_redemptions WHERE giftcode_id = ?', [id]);
      await pool.execute('DELETE FROM gf_giftcode_audit WHERE giftcode_id = ?', [id]);
      await pool.execute('DELETE FROM gf_giftcodes WHERE id = ?', [id]);
    }
    for (const attempt of attempts) {
      await pool.execute('DELETE FROM gf_auth_codes WHERE attempt_id = ?', [attempt]);
      await pool.execute('DELETE FROM gf_auth_attempts WHERE id = ?', [attempt]);
    }
    const [accounts] = await pool.execute('SELECT CAST(user_id AS CHAR) AS id FROM gf_password_accounts WHERE username LIKE ?', [users + '%']);
    for (const { id } of accounts) {
      await pool.execute('DELETE FROM gf_auth_sessions WHERE user_id = ?', [id]);
      await pool.execute('DELETE FROM users WHERE id = ?', [id]);
    }
  });

  const send = (path, { method = 'GET', body, headers = {} } = {}) => fetch(origin + path, {
    method,
    headers: { 'X-Forwarded-Proto': 'https', ...(body === undefined ? {} : { 'Content-Type': 'application/json', Origin: publicOrigin }), ...headers },
    body: body === undefined ? undefined : JSON.stringify(body),
  });
  const post = (path, body, headers) => send(path, { method: 'POST', body, headers });

  async function register(suffix) {
    const verifier = value(), state = value();
    const attempt = await expect(await post('/auth/attempt', {
      provider: 'password', state, code_challenge: createHash('sha256').update(verifier).digest('base64url'),
      redirect_uri: APP_REDIRECT_URI,
    }), 201);
    attempts.push(attempt.attempt_id);
    const form = await send('/auth/web/login?attempt=' + attempt.attempt_id);
    assert.equal(form.status, 200);
    const cookie = form.headers.get('set-cookie').split(';')[0];
    const csrf = (await form.text()).match(/name="csrf" value="([A-Za-z0-9_-]{43})"/u)[1];
    const username = users + suffix;
    const registered = await expect(await post('/auth/register', {
      attempt: attempt.attempt_id, csrf, username, email: username + '@example.test', password,
      password_confirmation: password,
    }, { Cookie: cookie, 'Sec-Fetch-Site': 'same-origin' }), 201);
    const link = new URL(registered.callback_url);
    assert.equal(link.searchParams.get('state'), state);
    const session = await expect(await post('/auth/exchange', {
      code: link.searchParams.get('code'), code_verifier: verifier, redirect_uri: APP_REDIRECT_URI,
    }), 200);
    return { Authorization: 'Bearer ' + session.access_token };
  }

  const admin = await post(adminPath + '/login', { password });
  assert.match(admin.headers.get('set-cookie'), /^__Host-gf_giftcode_admin=[A-Za-z0-9_-]{43}; Path=\/; Secure; HttpOnly; SameSite=Strict; Max-Age=3600$/u);
  const headers = { Cookie: admin.headers.get('set-cookie').split(';')[0] };
  await expect(admin, 200);
  const adminGet = path => send(adminPath + path, { headers });
  async function create(suffix, extra = {}) {
    const record = await expect(await post(adminPath, { code: suffix == null ? undefined : codes + '-' + suffix, rewards, ...extra }, headers), 201);
    ids.add(record.id);
    return record;
  }
  const patch = (id, body) => send(adminPath + '/' + id, { method: 'PATCH', body, headers });
  return { send, post, register, create, patch, adminGet, codes, headers, now: () => time, advance: ms => { time += ms; } };
}

test('authenticated concurrent giftcode requests enforce account and global limits and recover the same pending grant', async t => {
  const f = await fixture(t);
  const first = await f.register('a'), second = await f.register('b');
  const farm = randomUUID(), otherFarm = randomUUID();
  const code = await f.create('REPLAY', { max_redemptions: 1 });

  // Two deliveries precede either client making an acknowledgement.
  const take = (headers, farmId = farm) => f.post('/giftcodes/redeem', { code: ' ' + code.code.toLowerCase() + '\t', farm_id: farmId }, headers);
  const concurrent = await Promise.all([take(first), take(first)]);
  assert.deepEqual(concurrent.map(result => result.status).sort(), [200, 201]);
  // Simulate a lost response when the server already committed, then resume via pending.
  await Promise.all(concurrent.map(result => result.body.cancel()));
  const pending = await expect(await f.send('/giftcodes/pending?farm_id=' + farm, { headers: first }), 200);
  assert.equal(pending.grants.length, 1);
  const claimed = pending.grants[0];
  assert.deepEqual(claimed.rewards, rewards);
  assert.equal(claimed.status, 'pending');
  await error(await take(first, otherFarm), 409, 'giftcode_already_redeemed');
  await error(await take(second), 409, 'giftcode_exhausted');
  await error(await f.post('/giftcodes/ack', { grant_id: claimed.grant_id, farm_id: farm }, second), 404, 'grant_not_found');
  await error(await f.post('/giftcodes/ack', { grant_id: claimed.grant_id, farm_id: otherFarm }, first), 404, 'grant_not_found');
  assert.deepEqual(await expect(await f.send('/giftcodes/pending?farm_id=' + otherFarm, { headers: first }), 200), { grants: [] });

  await expect(await f.patch(code.id, { rewards: [{ id: 'coins', quantity: 9 }], enabled: false }), 200);
  assert.deepEqual(await expect(await take(first), 200), claimed);
  const ack = () => f.post('/giftcodes/ack', { grant_id: claimed.grant_id, farm_id: farm }, first);
  const applied = { ...claimed, status: 'applied' };
  const acknowledgements = await Promise.all([ack(), ack()]);
  for (const response of acknowledgements) assert.deepEqual(await expect(response, 200), applied);
  assert.deepEqual(await expect(await take(first), 200), applied);
  assert.deepEqual(await expect(await f.send('/giftcodes/pending?farm_id=' + farm, { headers: first }), 200), { grants: [] });
  const history = await expect(await f.adminGet('/' + code.id + '/redemptions?limit=1'), 200);
  assert.equal(history.total, 1);
  assert.deepEqual(history.items[0].rewards, rewards);
  assert.equal(history.items[0].status, 'applied');
  assert.equal(typeof history.items[0].applied_at, 'string');
  const [audit] = await pool.execute('SELECT operation FROM gf_giftcode_audit WHERE giftcode_id = ? ORDER BY id', [code.id]);
  assert.deepEqual(audit.map(entry => entry.operation), ['create', 'update']);

  const competing = await f.create('RACE', { max_redemptions: 1 });
  const race = await Promise.all([first, second].map(headers => f.post('/giftcodes/redeem', { code: competing.code, farm_id: farm }, headers)));
  assert.deepEqual(race.map(result => result.status).sort(), [201, 409]);
  for (const response of race) await response.body.cancel();
  const record = await expect(await f.adminGet('/' + competing.id), 200);
  assert.equal(record.redeemed_count, 1);
});

test('giftcode HTTPS, admin permissions, validation, time windows and paging are enforced', async t => {
  const f = await fixture(t);
  const headers = await f.register('a');
  const farm_id = randomUUID();
  const take = code => f.post('/giftcodes/redeem', { code, farm_id }, headers);
  await error(await f.post(adminPath, { rewards }, headers), 401, 'invalid_admin_session');
  await error(await f.post(adminPath, { rewards }, { ...f.headers, Origin: 'https://other.example.test' }), 403, 'csrf_failed');
  await error(await f.post('/giftcodes/redeem', { code: 'NONE', farm_id }, { ...headers, 'X-Forwarded-Proto': 'http' }), 400, 'https_required');
  await error(await f.post('/giftcodes/redeem', { code: 'NONE', farm_id }), 401, 'invalid_session');
  await error(await take(f.codes + '-MISSING'), 404, 'giftcode_not_found');
  await error(await take('x!'), 400, 'invalid_giftcode');
  await error(await f.post('/giftcodes/redeem', { code: 'NONE', farm_id, user_id: 999 }, headers), 400, 'invalid_request');
  await error(await f.post(adminPath, { rewards: [{ id: 'coins', quantity: 100_000_000 }] }, f.headers), 400, 'invalid_rewards');
  await error(await f.post(adminPath, { rewards: [rewards[0], rewards[0]] }, f.headers), 400, 'invalid_rewards');
  await error(await f.post(adminPath, { rewards: [{ id: 'unchecked-item', quantity: 1 }] }, f.headers), 400, 'invalid_rewards');
  const iso = delta => new Date(f.now() + delta).toISOString();
  const scheduled = await f.create('SCHEDULED', { starts_at: iso(60_000) });
  const expired = await f.create('EXPIRED', { expires_at: iso(-1) });
  const disabled = await f.create('DISABLED', { enabled: false });
  await error(await take(scheduled.code), 409, 'giftcode_not_started');
  await error(await take(expired.code), 409, 'giftcode_expired');
  await error(await take(disabled.code), 409, 'giftcode_disabled');
  const page = await expect(await f.adminGet('?q=' + f.codes.toLowerCase() + '&limit=1&offset=1'), 200);
  assert.equal(page.items.length, 1);
  assert.equal(page.total, 3);
  assert.equal(page.items[0].code, expired.code);
  const filter = await expect(await f.adminGet('?q=' + f.codes + '&status=scheduled'), 200);
  assert.equal(filter.items[0].code, scheduled.code);
  const generated = await f.create(null);
  assert.match(generated.code, /^[0-9A-F]{20}$/u);
  const catalog = await expect(await f.adminGet('/catalog'), 200);
  assert.deepEqual(catalog.items.map(item => item.id), ['coins', 'cash']);
  f.advance(60_000);
  await expect(await take(scheduled.code), 201);
  f.advance(900_000);
  await error(await take(scheduled.code), 401, 'invalid_session');
  await expect(await f.post(adminPath + '/logout', {}, f.headers), 200);
  await error(await f.send(adminPath + '/catalog', { headers: f.headers }), 401, 'invalid_admin_session');
});
