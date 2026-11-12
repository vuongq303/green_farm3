import assert from 'node:assert/strict';
import { randomBytes } from 'node:crypto';
import { createServer } from 'node:net';
import { after, test } from 'node:test';
import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { createMysqlStore, createPoolFromConfig } from '../src/db/store.js';
import { migrate } from '../src/db/migrate.js';

// This explicitly selected suite uses a real, isolated MySQL database. It never
// drops/truncates tables and cleans up only its own randomly namespaced rows.
loadEnvironment();
const config = loadDatabaseConfig();
config.database = process.env.TEST_DB_NAME ?? config.database;
if (!config.database.endsWith('_test')) {
  throw new Error('test:mysql requires a dedicated DB_NAME or TEST_DB_NAME ending in _test');
}
const pool = createPoolFromConfig(config);
after(async () => pool.end());

function fixture(t) {
  const clientId = 'mysql-integration-' + randomBytes(16).toString('hex');
  const store = createMysqlStore(config);
  t.after(async () => {
    await store.close();
    await pool.execute('DELETE FROM gf_sessions WHERE account_id IN (SELECT id FROM gf_accounts WHERE client_id = ?)', [clientId]);
    await pool.execute('DELETE FROM gf_compliance WHERE account_id IN (SELECT id FROM gf_accounts WHERE client_id = ?)', [clientId]);
    await pool.execute('DELETE FROM gf_accounts WHERE client_id = ?', [clientId]);
  });
  const now = Math.floor(Date.now() / 1000);
  const session = (extra = {}) => ({ sid: randomBytes(24).toString('hex'), issuedAt: now,
    expiresAt: now + 900, refreshExpiresAt: now + 3600, clientId,
    credential: 'anonymous:storage-fixture-🌞-\'parameterized',
    deviceId: 'mysql-integration-device', password: 'fixture-password', ...extra });
  return { clientId, store, now, session };
}

test('real MySQL migrations are additive, checksum tracked and idempotent', async () => {
  const first = await migrate(pool);
  assert.ok([...first.applied, ...first.alreadyApplied].includes(1));
  const again = await migrate(pool);
  assert.deepEqual(again, { applied: [], alreadyApplied: [1] });
});

test('accounts, sessions, profile identity and compliance survive store restart', async t => {
  const { store, now, session } = fixture(t);
  const fields = session();
  const created = await store.createSession(fields);
  assert.equal(typeof created.accountId, 'string');
  await store.saveCompliance({ accountId: created.accountId, country: 'US', age: 30, updatedAt: now });
  const [[row]] = await pool.execute('SELECT credential, password_salt, password_hash FROM gf_accounts WHERE id = ?', [created.accountId]);
  assert.equal(row.credential, fields.credential);
  assert.equal(row.password_salt.length, 16);
  assert.equal(row.password_hash.length, 64);
  assert.notEqual(row.password_hash.toString('utf8'), fields.password);

  await store.close();
  const restarted = createMysqlStore(config);
  t.after(() => restarted.close());
  assert.deepEqual(await restarted.health(), { status: 'ready', schemaVersion: 1 });
  const persisted = await restarted.findSession(fields.sid);
  assert.deepEqual(persisted, { issuedAt: fields.issuedAt, expiresAt: fields.expiresAt,
    refreshExpiresAt: fields.refreshExpiresAt, credential: fields.credential,
    clientId: fields.clientId, accountId: created.accountId });
  const repeated = await restarted.createSession(session());
  assert.equal(repeated.accountId, created.accountId, 'repeat login preserves account ID');
  const [[compliance]] = await pool.execute('SELECT country, age, updated_at FROM gf_compliance WHERE account_id = ?', [created.accountId]);
  assert.equal(compliance.country, 'US');
  assert.equal(compliance.age, 30);
  assert.equal(Number(compliance.updated_at), now);
});

test('wrong stored password and failed session insert roll back related account writes', async t => {
  const { store, clientId, session } = fixture(t);
  const fields = session();
  const original = await store.createSession(fields);
  const invalid = session({ password: 'wrong-fixture-password' });
  await assert.rejects(store.createSession(invalid), error => error.code === 'INVALID_CREDENTIALS');
  assert.equal(await store.findSession(invalid.sid), undefined);
  // A deliberate duplicate sid fails after inserting a different account. That
  // account must roll back together with its session, preserving existing data.
  await assert.rejects(store.createSession(session({ sid: fields.sid, credential: 'anonymous:rollback-fixture' })),
    error => error.code === 'DATABASE_UNAVAILABLE');
  const [[count]] = await pool.execute('SELECT COUNT(*) AS total FROM gf_accounts WHERE client_id = ?', [clientId]);
  assert.equal(Number(count.total), 1);
  assert.equal((await store.findSession(fields.sid)).accountId, original.accountId);
});

test('concurrent first authorizations retain one account identity', async t => {
  const { store, clientId, session } = fixture(t);
  const results = await Promise.all(Array.from({ length: 4 }, () => store.createSession(session())));
  assert.equal(new Set(results.map(row => row.accountId)).size, 1);
  const [[count]] = await pool.execute('SELECT COUNT(*) AS total FROM gf_accounts WHERE client_id = ?', [clientId]);
  assert.equal(Number(count.total), 1);
});

test('refresh preserves the account, rotates atomically and rejects replay', async t => {
  const { store, now, session } = fixture(t);
  const original = session({ expiresAt: now + 1 });
  const account = await store.createSession(original);
  const other = session({ credential: 'anonymous:other-refresh-fixture', issuedAt: now + 2 });
  await store.createSession(other);
  assert.ok(await store.findSession(original.sid), 'access expiry must not erase a still-valid refresh session');
  const sid = randomBytes(24).toString('hex');
  const rotation = { previousSid: original.sid, sid, issuedAt: now + 2,
    expiresAt: now + 902, refreshExpiresAt: now + 3602 };
  assert.equal((await store.refreshSession(rotation)).accountId, account.accountId);
  assert.equal(await store.findSession(original.sid), undefined);
  assert.equal((await store.findSession(sid)).refreshExpiresAt, rotation.refreshExpiresAt);
  await assert.rejects(store.refreshSession({ ...rotation, sid: randomBytes(24).toString('hex') }),
    error => error.code === 'INVALID_SESSION');
  // An insertion failure must leave the old refresh session usable.
  await assert.rejects(store.refreshSession({ ...rotation, previousSid: sid, sid: other.sid }),
    error => error.code === 'DATABASE_UNAVAILABLE');
  assert.ok(await store.findSession(sid));
});

test('expired refresh sessions cannot rotate or generate a new session', async t => {
  const { store, now, session } = fixture(t);
  const fields = session({ issuedAt: now - 10, expiresAt: now - 2, refreshExpiresAt: now - 1 });
  await store.createSession(fields);
  const sid = randomBytes(24).toString('hex');
  await assert.rejects(store.refreshSession({ previousSid: fields.sid, sid,
    issuedAt: now, expiresAt: now + 900, refreshExpiresAt: now + 3600 }),
  error => error.code === 'INVALID_SESSION');
  assert.equal(await store.findSession(sid), undefined);
});

test('an unavailable database returns explicit sanitized errors without memory fallback', async t => {
  const listener = createServer();
  await new Promise(resolve => listener.listen(0, '127.0.0.1', resolve));
  const port = listener.address().port;
  await new Promise(resolve => listener.close(resolve));
  const store = createMysqlStore({ ...config, host: '127.0.0.1', port, connectTimeout: 250 });
  t.after(() => store.close());
  for (const operation of [() => store.health(), () => store.findSession('unavailable-fixture')]) {
    await assert.rejects(operation(), error => error.code === 'DATABASE_UNAVAILABLE' &&
      error.message === 'MySQL is unavailable or its schema is not ready');
  }
});
