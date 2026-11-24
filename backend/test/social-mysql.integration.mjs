import assert from 'node:assert/strict';
import { createHash, randomBytes } from 'node:crypto';
import { after, test } from 'node:test';
import { loadDatabaseConfig, readEnvironment } from '../src/config.js';
import { createPoolFromConfig, createMysqlStore } from '../src/db/store.js';
import { createSocialStore } from '../src/db/social-store.js';
import { migrate } from '../src/db/migrate.js';

const env = readEnvironment();
const config = loadDatabaseConfig({ ...env, DB_NAME: env.TEST_DB_NAME ?? env.DB_NAME });
if (!config.database.endsWith('_test')) throw new Error('Social MySQL tests require a dedicated database ending in _test');
const pool = createPoolFromConfig(config);
const store = createSocialStore(pool);
await migrate(pool);
after(() => pool.end());

const hash = value => createHash('sha256').update(value).digest();
const token = () => randomBytes(32).toString('base64url');

function fixture(t) {
  const prefix = 'socialtest-' + randomBytes(12).toString('hex');
  const now = Math.floor(Date.now() / 1000);
  t.after(async () => {
    await pool.execute('DELETE FROM gf_auth_codes WHERE attempt_id LIKE ?', [prefix + '%']);
    await pool.execute(`DELETE FROM gf_auth_sessions WHERE user_id IN
      (SELECT id FROM users WHERE provider_user_id LIKE ?)`, [prefix + '%']);
    await pool.execute('DELETE FROM gf_auth_attempts WHERE id LIKE ?', [prefix + '%']);
    await pool.execute('DELETE FROM users WHERE provider_user_id LIKE ?', [prefix + '%']);
  });
  const attempt = (extra = {}) => ({ id: prefix + '-' + randomBytes(4).toString('hex'), provider: 'google',
    appState: token(), codeChallenge: token(), redirectUri: 'com.quanhoang.greenfarm3://oauth/callback',
    createdAt: now, expiresAt: now + 300, ...extra });
  const session = (extra = {}) => ({ id: token(), accessHash: hash(token()), refreshHash: hash(token()),
    issuedAt: now, accessExpiresAt: now + 900, refreshExpiresAt: now + 3600, ...extra });
  const account = (provider = 'google', suffix = '') => store.upsertUser(provider, prefix + suffix, 'Farm player', now);
  return { prefix, now, attempt, session, account };
}

async function readyCode(f, { expiresAt = f.now + 60, ...extra } = {}) {
  const record = f.attempt(extra);
  await store.createAttempt(record);
  const state = hash(token());
  await store.startAttempt(record.id, { oauthStateHash: state, nonce: token(), providerVerifier: token() }, f.now);
  await store.claimOAuthAttempt(state, record.provider, f.now);
  const account = await f.account();
  const codeHash = hash(token());
  assert.equal(await store.issueCode({ attemptId: record.id, userId: account.id, codeHash, expiresAt, now: f.now }), true);
  return { record, account, codeHash };
}

test('social migration is additive, idempotent and does not change legacy rows', async t => {
  const f = fixture(t);
  const legacy = createMysqlStore(config);
  t.after(() => legacy.close());
  const created = await legacy.createSession({ sid: token(), clientId: f.prefix, credential: 'anonymous:' + f.prefix,
    password: 'legacy-test-password', deviceId: 'legacy-device', issuedAt: f.now, expiresAt: f.now + 60,
    refreshExpiresAt: f.now + 120 });
  t.after(async () => {
    await pool.execute('DELETE FROM gf_sessions WHERE account_id = ?', [created.accountId]);
    await pool.execute('DELETE FROM gf_accounts WHERE id = ?', [created.accountId]);
  });
  const [[before]] = await pool.execute('SELECT * FROM gf_accounts WHERE id = ?', [created.accountId]);
  const again = await migrate(pool);
  assert.deepEqual(again.applied, []);
  assert.ok(again.alreadyApplied.includes(1) && again.alreadyApplied.includes(2) && again.alreadyApplied.includes(3));
  const [[afterRow]] = await pool.execute('SELECT * FROM gf_accounts WHERE id = ?', [created.accountId]);
  assert.deepEqual(afterRow, before);
  assert.deepEqual(await store.health(), { status: 'ready', schemaVersion: 5 });
});

test('concurrent provider identities retain one stable game ID without email merging', async t => {
  const f = fixture(t);
  const repeated = await Promise.all(Array.from({ length: 6 }, () => f.account()));
  assert.equal(new Set(repeated.map(row => row.id)).size, 1);
  assert.equal(new Set(repeated.map(row => row.game_id)).size, 1);
  assert.match(repeated[0].game_id, /^GF\d{6,}$/u);
  const facebook = await f.account('facebook');
  assert.notEqual(facebook.game_id, repeated[0].game_id, 'same external ID in another provider is a separate account');
  const another = await f.account('google', '-other');
  assert.notEqual(another.game_id, repeated[0].game_id);
  const restarted = createSocialStore(pool);
  assert.equal((await restarted.upsertUser('google', f.prefix, 'Updated name', f.now + 5)).game_id, repeated[0].game_id);
});

test('game IDs retain every BIGINT digit beyond JavaScript safe integer precision', async () => {
  const connection = await pool.getConnection();
  try {
    // This connection-local table shadows users only for this fixture. Its
    // large AUTO_INCREMENT does not advance or modify the real users table.
    const [[definition]] = await connection.query('SHOW CREATE TABLE users');
    await connection.query(definition['Create Table'].replace(/^CREATE TABLE /u, 'CREATE TEMPORARY TABLE '));
    await connection.query('ALTER TABLE users AUTO_INCREMENT = 9007199254740993');
    const pinned = {
      beginTransaction: () => connection.beginTransaction(),
      execute: (...args) => connection.execute(...args),
      commit: () => connection.commit(), rollback: () => connection.rollback(), release() {},
    };
    const isolated = createSocialStore({ getConnection: async () => pinned });
    const first = await isolated.upsertUser('google', 'bigint-fixture', 'Farmer', 1800000000);
    assert.equal(first.id, '9007199254740993');
    assert.equal(first.game_id, 'GF9007199254740993');
    const again = await isolated.upsertUser('google', 'bigint-fixture', 'Farmer', 1800000001);
    assert.equal(again.id, first.id);
    assert.equal(again.game_id, first.game_id);
    const another = await isolated.upsertUser('facebook', 'bigint-fixture', 'Farmer', 1800000001);
    assert.notEqual(another.id, first.id);
    assert.equal(another.game_id, 'GF' + another.id);
  } finally {
    await connection.query('DROP TEMPORARY TABLE IF EXISTS users');
    connection.release();
  }
});

test('provider OAuth state starts and is claimed once, rejecting wrong provider, expiry and replay', async t => {
  const f = fixture(t);
  const record = f.attempt();
  const state = { oauthStateHash: hash(token()), nonce: token(), providerVerifier: token() };
  await store.createAttempt(record);
  const starts = await Promise.all([store.startAttempt(record.id, state, f.now), store.startAttempt(record.id, state, f.now)]);
  assert.equal(starts.filter(Boolean).length, 1);
  assert.equal((await store.getAttempt(record.id)).appState, record.appState);
  assert.equal(await store.claimOAuthAttempt(state.oauthStateHash, 'facebook', f.now), undefined);
  const claims = await Promise.all([store.claimOAuthAttempt(state.oauthStateHash, 'google', f.now),
    store.claimOAuthAttempt(state.oauthStateHash, 'google', f.now)]);
  assert.equal(claims.filter(Boolean).length, 1);
  assert.equal(claims.find(Boolean).providerVerifier, state.providerVerifier);
  assert.equal(await store.claimOAuthAttempt(state.oauthStateHash, 'google', f.now), undefined);
  const expired = f.attempt({ expiresAt: f.now - 1 });
  await store.createAttempt(expired);
  assert.equal(await store.startAttempt(expired.id, { ...state, oauthStateHash: hash(token()) }, f.now), undefined);
});

test('one-time authorization codes require correct PKCE, redirect and expiry before creating a session', async t => {
  const f = fixture(t);
  const { record, account, codeHash } = await readyCode(f);
  const fields = { codeHash, codeChallenge: record.codeChallenge, redirectUri: record.redirectUri, now: f.now };
  assert.equal(await store.exchangeCode({ ...fields, codeChallenge: token(), session: f.session() }), undefined);
  assert.equal(await store.exchangeCode({ ...fields, redirectUri: 'evil://callback', session: f.session() }), undefined);
  assert.equal(await store.exchangeCode({ ...fields, now: f.now + 60, session: f.session() }), undefined);
  const sessions = [f.session(), f.session()];
  const results = await Promise.all(sessions.map(session => store.exchangeCode({ ...fields, session })));
  assert.equal(results.filter(Boolean).length, 1);
  const active = results[0] ? sessions[0] : sessions[1];
  assert.equal((await store.findAccess(active.accessHash, f.now)).user.game_id, account.game_id);
  assert.equal(await store.exchangeCode({ ...fields, session: f.session() }), undefined);
  assert.equal(await store.issueCode({ attemptId: record.id, userId: account.id,
    codeHash: hash(token()), expiresAt: f.now + 60, now: f.now }), false);
  const [[row]] = await pool.execute('SELECT access_hash, refresh_hash FROM gf_auth_sessions WHERE id = ?', [active.id]);
  assert.deepEqual(row.access_hash, active.accessHash);
  assert.deepEqual(row.refresh_hash, active.refreshHash);
});

test('failed session creation rolls back authorization-code consumption', async t => {
  const f = fixture(t);
  const first = await readyCode(f);
  const existing = f.session();
  await store.exchangeCode({ codeHash: first.codeHash, codeChallenge: first.record.codeChallenge,
    redirectUri: first.record.redirectUri, now: f.now, session: existing });
  const second = await readyCode(f);
  const exchange = { codeHash: second.codeHash, codeChallenge: second.record.codeChallenge,
    redirectUri: second.record.redirectUri, now: f.now };
  await assert.rejects(store.exchangeCode({ ...exchange, session: existing }), error => error.code === 'DATABASE_UNAVAILABLE');
  assert.ok(await store.exchangeCode({ ...exchange, session: f.session() }), 'code remains valid after rollback');
});

test('refresh rotates atomically, invalidates old access and rejects replay/expiry; logout is correlated', async t => {
  const f = fixture(t);
  const { record, account, codeHash } = await readyCode(f);
  const original = f.session({ accessExpiresAt: f.now + 1 });
  await store.exchangeCode({ codeHash, codeChallenge: record.codeChallenge, redirectUri: record.redirectUri,
    now: f.now, session: original });
  assert.equal(await store.findAccess(original.accessHash, f.now + 2), undefined);
  const next = [f.session(), f.session()];
  const rotation = await Promise.all(next.map(session => store.rotateRefresh(original.refreshHash, session, f.now + 2)));
  assert.equal(rotation.filter(Boolean).length, 1);
  const active = rotation[0] ? next[0] : next[1];
  assert.equal(rotation.find(Boolean).game_id, account.game_id);
  assert.equal(await store.findAccess(original.accessHash, f.now), undefined);
  assert.equal(await store.rotateRefresh(original.refreshHash, f.session(), f.now), undefined);
  assert.equal(await store.rotateRefresh(active.refreshHash, f.session(), f.now + 3600), undefined);
  assert.equal(await store.revoke(active.accessHash, hash(token()), f.now), false, 'unrelated refresh cannot revoke session');
  assert.ok(await store.findAccess(active.accessHash, f.now));
  assert.equal(await store.revoke(active.accessHash, active.refreshHash, f.now), true);
  assert.equal(await store.findAccess(active.accessHash, f.now), undefined);
  assert.equal(await store.rotateRefresh(active.refreshHash, f.session(), f.now), undefined);
  assert.equal(await store.revoke(active.accessHash, active.refreshHash, f.now), false);
});

test('logout using an older token pair revokes every refresh descendant but not independent logins', async t => {
  const f = fixture(t);
  async function login() {
    const { record, codeHash } = await readyCode(f);
    const session = f.session();
    await store.exchangeCode({ codeHash, codeChallenge: record.codeChallenge, redirectUri: record.redirectUri,
      now: f.now, session });
    return session;
  }
  const original = await login();
  const otherLogin = await login();
  const rotated = f.session();
  const latest = f.session();
  assert.ok(await store.rotateRefresh(original.refreshHash, rotated, f.now + 1));
  assert.ok(await store.rotateRefresh(rotated.refreshHash, latest, f.now + 2));
  assert.ok(await store.findAccess(latest.accessHash, f.now + 2));
  assert.equal(await store.revoke(original.accessHash, original.refreshHash, f.now + 3), true);
  for (const session of [original, rotated, latest]) {
    assert.equal(await store.findAccess(session.accessHash, f.now + 3), undefined);
    assert.equal(await store.rotateRefresh(session.refreshHash, f.session(), f.now + 3), undefined);
  }
  assert.ok(await store.findAccess(otherLogin.accessHash, f.now + 3), 'logout is device/session-family scoped');
  assert.ok(await store.rotateRefresh(otherLogin.refreshHash, f.session(), f.now + 3));
});

test('concurrent refresh and old-token logout never leave a usable descendant session', async t => {
  const f = fixture(t);
  for (let round = 0; round < 10; round++) {
    const { record, codeHash } = await readyCode(f);
    const original = f.session();
    await store.exchangeCode({ codeHash, codeChallenge: record.codeChallenge, redirectUri: record.redirectUri,
      now: f.now, session: original });
    const next = f.session();
    const refresh = () => store.rotateRefresh(original.refreshHash, next, f.now + 1);
    const logout = () => store.revoke(original.accessHash, original.refreshHash, f.now + 1);
    const results = await Promise.all(round % 2 ? [logout(), refresh()] : [refresh(), logout()]);
    assert.equal(results[round % 2 ? 0 : 1], true);
    assert.equal(await store.findAccess(original.accessHash, f.now + 1), undefined);
    assert.equal(await store.findAccess(next.accessHash, f.now + 1), undefined);
    assert.equal(await store.rotateRefresh(next.refreshHash, f.session(), f.now + 1), undefined);
    assert.equal(await store.rotateRefresh(original.refreshHash, f.session(), f.now + 1), undefined);
  }
});

test('a failed rotated-session insert rolls back revocation and preserves the original refresh', async t => {
  const f = fixture(t);
  async function login() {
    const { record, codeHash } = await readyCode(f);
    const session = f.session();
    await store.exchangeCode({ codeHash, codeChallenge: record.codeChallenge, redirectUri: record.redirectUri,
      now: f.now, session });
    return session;
  }
  const original = await login();
  const collision = await login();
  await assert.rejects(store.rotateRefresh(original.refreshHash, collision, f.now + 1),
    error => error.code === 'DATABASE_UNAVAILABLE');
  assert.ok(await store.findAccess(original.accessHash, f.now + 1));
  assert.ok(await store.findAccess(collision.accessHash, f.now + 1));
  const next = f.session();
  assert.ok(await store.rotateRefresh(original.refreshHash, next, f.now + 1));
  assert.ok(await store.findAccess(next.accessHash, f.now + 1));
  assert.equal(await store.revoke(original.accessHash, collision.refreshHash, f.now + 2), false);
  assert.ok(await store.findAccess(next.accessHash, f.now + 2));
});
