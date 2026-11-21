import assert from 'node:assert/strict';
import { createHash, randomBytes } from 'node:crypto';
import { copyFile, mkdtemp, readdir, readFile, rm } from 'node:fs/promises';
import { createServer } from 'node:http';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { test } from 'node:test';
import { fileURLToPath } from 'node:url';
import { APP_REDIRECT_URI, loadSocialAuthConfig } from '../src/auth/config.js';
import { hashPassword } from '../src/auth/passwords.js';
import { createSocialAuthRouter } from '../src/auth/router.js';
import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { migrate } from '../src/db/migrate.js';
import { createSocialStore } from '../src/db/social-store.js';
import { createPoolFromConfig } from '../src/db/store.js';

const migrations = fileURLToPath(new URL('../migrations/', import.meta.url));
const token = () => randomBytes(32).toString('base64url');
const digest = value => createHash('sha256').update(value).digest();

async function seedSession(pool, { userId, familyId, revokedAt, now }) {
  const id = token(), access = token(), refresh = token();
  if (!familyId) {
    familyId = id;
    await pool.execute('INSERT INTO gf_auth_session_families (id, user_id, created_at) VALUES (?, ?, ?)',
      [id, userId, now]);
  }
  await pool.execute(`INSERT INTO gf_auth_sessions
    (id, user_id, access_hash, refresh_hash, issued_at, access_expires_at, refresh_expires_at, revoked_at)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)`,
  [id, userId, digest(access), digest(refresh), now, now + 900, now + 3600, revokedAt ?? null]);
  await pool.execute('INSERT INTO gf_auth_session_links (session_id, family_id) VALUES (?, ?)', [id, familyId]);
  return { id, access, refresh };
}

async function fixtureAccount(pool, { provider, externalId, gameId, name, now }) {
  await pool.execute(`INSERT INTO users
    (provider, provider_user_id, game_id, display_name, created_at, updated_at, last_login_at)
    VALUES (?, ?, ?, ?, ?, ?, ?)`, [provider, externalId, gameId, name, now - 100, now - 10, now - 10]);
  const [[row]] = await pool.execute('SELECT id FROM users WHERE provider = ? AND provider_user_id = ?',
    [provider, externalId]);
  return row.id;
}

async function listen(t, store, now) {
  const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: 'https://upgrade.example.test' });
  const router = createSocialAuthRouter({ config, store, isSecureRequest: () => true, now: () => now * 1000 });
  const server = createServer((req, res) => {
    void router.handle(req, res, new URL(req.url, 'http://127.0.0.1'));
  });
  await new Promise((resolve, reject) => {
    server.once('error', reject);
    server.listen(0, '127.0.0.1', resolve);
  });
  t.after(() => new Promise((resolve, reject) => server.close(error => error ? reject(error) : resolve())));
  const base = 'http://127.0.0.1:' + server.address().port;
  function get(path, access) {
    return fetch(base + path, { headers: access ? { Authorization: 'Bearer ' + access } : {} });
  }
  function post(path, body, headers = {}) {
    return fetch(base + path, { method: 'POST', body: JSON.stringify(body),
      headers: { 'Content-Type': 'application/json', ...headers } });
  }
  return { get, post, publicBaseUrl: config.publicBaseUrl };
}

test('MySQL v4 upgrade preserves account data, active tokens and the full rotation lineage', { timeout: 30_000 }, async t => {
  loadEnvironment();
  const config = loadDatabaseConfig();
  config.database = process.env.TEST_DB_NAME ?? config.database;
  assert.match(config.database, /^[a-zA-Z0-9_]+_upgrade_test$/u, 'Use a new database ending in _upgrade_test');
  const pool = createPoolFromConfig(config);
  t.after(() => pool.end());
  const [[{ total }]] = await pool.query('SELECT COUNT(*) AS total FROM information_schema.TABLES WHERE TABLE_SCHEMA = DATABASE()');
  assert.equal(Number(total), 0, 'Upgrade test requires a dedicated empty database');

  const v4 = await mkdtemp(join(tmpdir(), 'greenfarm-migrations-v4-'));
  t.after(() => rm(v4, { recursive: true, force: true }));
  for (const file of await readdir(migrations)) {
    if (/^00[1-4]_.+\.sql$/u.test(file)) await copyFile(join(migrations, file), join(v4, file));
  }
  assert.deepEqual(await migrate(pool, { directory: v4 }), { applied: [1, 2, 3, 4], alreadyApplied: [] });
  await assert.rejects(createSocialStore(pool).health(), { code: 'DATABASE_UNAVAILABLE' });

  const now = Math.floor(Date.now() / 1000);
  const password = 'old farm test ' + token();
  const passwordHash = await hashPassword(password);
  const passwordGameId = 'GF700007', facebookGameId = 'GF812345';
  const userId = await fixtureAccount(pool, { provider: 'password', externalId: token(),
    gameId: passwordGameId, name: 'Farm player', now });
  const facebookId = await fixtureAccount(pool, { provider: 'facebook', externalId: 'existing-facebook-fixture',
    gameId: facebookGameId, name: 'Facebook player', now });
  await pool.execute(`INSERT INTO gf_password_accounts
    (user_id, username, email, password_hash, account_status, created_at, updated_at, last_login_at)
    VALUES (?, 'oldfarm', 'oldfarm@example.test', ?, 'active', ?, ?, ?)`,
  [userId, passwordHash, now - 100, now - 10, now - 10]);
  const first = await seedSession(pool, { userId, now: now - 60, revokedAt: now - 30 });
  const active = await seedSession(pool, { userId, familyId: first.id, now: now - 30 });
  const independent = await seedSession(pool, { userId, now: now - 20 });
  const facebook = await seedSession(pool, { userId: facebookId, now: now - 43 });
  const snapshot = async () => {
    const [users] = await pool.query('SELECT * FROM users ORDER BY id');
    const [passwords] = await pool.query('SELECT * FROM gf_password_accounts ORDER BY user_id');
    const [sessions] = await pool.query(`SELECT id, user_id, access_hash, refresh_hash, issued_at, access_expires_at,
        refresh_expires_at, revoked_at FROM gf_auth_sessions ORDER BY id`);
    const [families] = await pool.query('SELECT * FROM gf_auth_session_families ORDER BY id');
    const [links] = await pool.query('SELECT * FROM gf_auth_session_links ORDER BY session_id');
    return { users, passwords, sessions, families, links };
  };
  const before = await snapshot();

  // The v4 links table could reference a family belonging to another account.
  // An interrupted v5 must leave version 5 unrecorded and allow a repaired retry.
  await pool.execute('UPDATE gf_auth_session_links SET family_id = ? WHERE session_id = ?', [first.id, facebook.id]);
  await assert.rejects(migrate(pool));
  const [unfinished] = await pool.query('SELECT version FROM gf_schema_migrations WHERE version = 5');
  assert.deepEqual(unfinished, []);
  await pool.execute('UPDATE gf_auth_session_links SET family_id = ? WHERE session_id = ?', [facebook.id, facebook.id]);
  assert.deepEqual(await migrate(pool), { applied: [5, 6], alreadyApplied: [1, 2, 3, 4] });
  assert.deepEqual(await snapshot(), before, 'Account, token, family and original link data must survive byte-for-byte');
  const [memberships] = await pool.query('SELECT id, family_id FROM gf_auth_sessions ORDER BY id');
  assert.deepEqual(memberships.map(row => [row.id, row.family_id]),
    before.links.map(row => [row.session_id, row.family_id]));
  await assert.rejects(pool.execute('UPDATE gf_auth_sessions SET family_id = ? WHERE id = ?', [first.id, facebook.id]),
    { code: 'ER_NO_REFERENCED_ROW_2' });
  assert.deepEqual(await migrate(pool), { applied: [], alreadyApplied: [1, 2, 3, 4, 5, 6] });
  assert.deepEqual(await snapshot(), before);

  const store = createSocialStore(pool);
  assert.deepEqual(await store.health(), { status: 'ready', schemaVersion: 5 });
  const { get, post, publicBaseUrl } = await listen(t, store, now);
  async function player(access, expected) {
    const response = await get('/auth/me', access);
    assert.equal(response.status, 200);
    const { user } = await response.json();
    assert.equal(user.game_id, expected);
    assert.deepEqual(Object.keys(user).sort(), ['display_name', 'game_id', 'provider']);
  }
  await player(active.access, passwordGameId);
  await player(facebook.access, facebookGameId);
  assert.equal((await get('/auth/me', first.access)).status, 401);
  assert.equal((await post('/auth/refresh', { refresh_token: first.refresh })).status, 401);
  const refresh = await post('/auth/refresh', { refresh_token: active.refresh });
  assert.equal(refresh.status, 200);
  const rotated = await refresh.json();
  await player(rotated.access_token, passwordGameId);
  assert.equal((await post('/auth/refresh', { refresh_token: active.refresh })).status, 401);
  const logout = await post('/auth/logout', { refresh_token: first.refresh }, { Authorization: 'Bearer ' + first.access });
  assert.equal(logout.status, 204);
  assert.equal((await get('/auth/me', rotated.access_token)).status, 401);
  assert.equal((await post('/auth/refresh', { refresh_token: rotated.refresh_token })).status, 401);
  await player(independent.access, passwordGameId);
  await player(facebook.access, facebookGameId);

  const verifier = token(), state = token();
  const start = await post('/auth/attempt', { provider: 'password', state,
    code_challenge: digest(verifier).toString('base64url'), redirect_uri: APP_REDIRECT_URI });
  assert.equal(start.status, 201);
  const { attempt_id: attempt } = await start.json();
  const form = await get('/auth/web/login?attempt=' + attempt);
  assert.equal(form.status, 200);
  const csrf = (await form.text()).match(/name="csrf" value="([A-Za-z0-9_-]{43})"/u)?.[1];
  assert.ok(csrf);
  const headers = { Cookie: form.headers.get('set-cookie').split(';')[0], Origin: publicBaseUrl,
    'Sec-Fetch-Site': 'same-origin' };
  const signIn = await post('/auth/login', { attempt, csrf, login: 'OLDFARM@EXAMPLE.TEST', password }, headers);
  assert.equal(signIn.status, 200);
  const callback = new URL((await signIn.json()).callback_url);
  assert.equal(callback.searchParams.get('state'), state);
  assert.equal(callback.protocol + '//' + callback.host + callback.pathname, APP_REDIRECT_URI);
  const exchange = await post('/auth/exchange', { code: callback.searchParams.get('code'), code_verifier: verifier,
    redirect_uri: APP_REDIRECT_URI });
  assert.equal(exchange.status, 200);
  await player((await exchange.json()).access_token, passwordGameId);

  // Rollback keeps ledger row 5 and restores the legacy link writer. A v5
  // process must reject that intermediate schema even with the ledger intact.
  await pool.query(`INSERT INTO gf_auth_session_links (session_id, family_id)
    SELECT s.id, s.family_id FROM gf_auth_sessions s
    LEFT JOIN gf_auth_session_links l ON l.session_id = s.id WHERE l.session_id IS NULL`);
  await pool.query(`ALTER TABLE gf_auth_sessions MODIFY COLUMN family_id
    VARCHAR(64) CHARACTER SET ascii COLLATE ascii_bin NULL`);
  const v4Session = await seedSession(pool, { userId, now });
  assert.deepEqual(await migrate(pool), { applied: [], alreadyApplied: [1, 2, 3, 4, 5, 6] });
  await assert.rejects(store.health(), { code: 'DATABASE_UNAVAILABLE' });

  // The documented roll-forward deliberately replays the already-ledgered
  // migration under maintenance so the new v4 links also get backfilled.
  const v5Filename = '005_session_family_membership.sql';
  const v5Sql = await readFile(join(migrations, v5Filename), 'utf8');
  const connection = await pool.getConnection();
  try {
    const [[record]] = await connection.execute('SELECT checksum FROM gf_schema_migrations WHERE version = 5');
    assert.equal(digest(v5Sql).toString('hex'), record.checksum);
    for (const statement of v5Sql.split(';').map(value => value.trim()).filter(Boolean)) {
      await connection.query(statement);
    }
  } finally { connection.release(); }
  assert.deepEqual(await store.health(), { status: 'ready', schemaVersion: 5 });
  await player(v4Session.access, passwordGameId);
});
