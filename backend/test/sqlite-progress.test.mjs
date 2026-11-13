import assert from 'node:assert/strict';
import test from 'node:test';
import { mkdtempSync, rmSync, readFileSync, writeFileSync, readdirSync } from 'node:fs';
import { join } from 'node:path';
import { tmpdir } from 'node:os';
import { randomBytes, randomUUID, createHash } from 'node:crypto';
import { DatabaseSync } from 'node:sqlite';
import { once } from 'node:events';
import { createResearchServer } from '../src/server.js';
import { createSqliteStore } from '../src/db/sqlite.js';
import { migrateSqlite } from '../src/db/sqlite-migrate.js';
import { decodeSnapshot, validateArchive } from '../src/services/snapshots.js';
import { archiveFixture, encodedFixture } from './helpers/archive.mjs';
import { legacyPassword } from '../src/services/sessions.js';

async function fixture(t, options = {}) {
  const folder = mkdtempSync(join(tmpdir(), 'gf-sqlite-progress-'));
  const filename = join(folder, 'progress.sqlite');
  await migrateSqlite(filename);
  let store = createSqliteStore({ filename, busyTimeoutMs: 10 });
  let server;
  const logs = [];
  async function start() {
    server = createResearchServer({ publicBaseUrl: 'http://192.168.23.41:4141/', sessionSecret: 'fixture-only-stable-research-secret', store, log: value => logs.push(value), errorLog: value => logs.push(value), ...options });
    server.listen(0, '127.0.0.1');
    await once(server, 'listening');
  }
  async function stop() {
    if (server?.listening) { const stopped = new Promise(resolve => server.close(resolve)); server.closeAllConnections(); await stopped; }
    await store.close();
  }
  await start();
  t.after(async () => { await stop(); rmSync(folder, { recursive: true, force: true }); });
  async function request(path, { method = 'GET', token, body, headers = {} } = {}) {
    const response = await fetch(`http://127.0.0.1:${server.address().port}${path}`, { method, headers: { ...(body === undefined ? {} : { 'content-type': 'application/json' }), ...(token ? { authorization: 'Bearer ' + token } : {}), ...headers }, body: body === undefined ? undefined : JSON.stringify(body) });
    return { status: response.status, value: await response.json() };
  }
  async function guest(secret = randomBytes(32).toString('base64url'), extra = {}) {
    const result = await request('/api/v1/guest/sessions', { method: 'POST', body: { installationSecret: secret, ...extra } });
    assert.equal(result.status, 200, JSON.stringify(result.value));
    return { ...result.value, secret };
  }
  // These transport/CAS fixtures represent explicitly approved copies of
  // pre-existing local archives. Fresh configured farms are tested separately.
  const save = async (token, expectedRevision, saveOptions = {}) => {
    const body = { operationId: randomUUID(), expectedRevision, ...encodedFixture(), ...saveOptions };
    let result = await request('/api/v1/save', { method: 'PUT', token, body });
    if (result.status === 403 && result.value.code === 'ADOPTION_AUTHORIZATION_REQUIRED') {
      const owner = await store.findGuestSession(token, Math.floor((options.now ?? Date.now)() / 1000));
      const approvedAt = Math.floor((options.now ?? Date.now)() / 1000);
      await store.approveLocalAdoption({ playerId: owner.playerId, snapshotHash: body.snapshotHash, now: approvedAt, expiresAt: approvedAt + 900 });
      const grant = await request('/api/v1/save/adoption', { method: 'POST', token, body: { operationId: body.operationId, snapshotHash: body.snapshotHash } });
      if (grant.status === 200) result = await request('/api/v1/save', { method: 'PUT', token, body: { ...body, adoptionToken: grant.value.adoptionToken } });
    }
    return result;
  };
  return { filename, logs, request, guest, save, store: () => store, async restart() { await stop(); store = createSqliteStore({ filename, busyTimeoutMs: 10 }); await start(); } };
}

test('real HTTP+SQLite isolates two players, commits full binary bytes and preserves sessions/save through restart', async t => {
  const f = await fixture(t);
  const first = await f.guest(); const second = await f.guest();
  assert.notEqual(first.playerId, second.playerId);
  for (const account of [first, second]) assert.equal((await f.request('/api/v1/save', { token: account.accessToken })).value.code, 'NO_SAVE');
  const one = { ...encodedFixture({ marker: 11 }), operationId: randomUUID() };
  const two = { ...encodedFixture({ marker: 22 }), operationId: randomUUID() };
  assert.equal((await f.save(first.accessToken, 0, one)).value.revision, 1);
  assert.equal((await f.save(second.accessToken, 0, two)).value.revision, 1);
  await f.restart();
  for (const [account, payload] of [[first, one], [second, two]]) {
    const loaded = await f.request('/api/v1/save', { token: account.accessToken });
    assert.equal(loaded.status, 200); assert.equal(loaded.value.revision, 1);
    assert.equal(loaded.value.snapshotBase64, payload.snapshotBase64); assert.equal(loaded.value.snapshotHash, payload.snapshotHash);
  }
  const sql = new DatabaseSync(f.filename, { readOnly: true });
  try { assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 2); assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_farm_snapshots').get().n, 2); }
  finally { sql.close(); }
});

test('guest proof retries preserve identity; known owner missing/conflicting never creates a replacement account', async t => {
  let now = 1700000000000;
  const f = await fixture(t, { now: () => now, guestSessionTtlSeconds: 2 });
  const first = await f.guest();
  const repeated = await f.guest(first.secret, { previousPlayerId: first.playerId });
  assert.equal(repeated.playerId, first.playerId); assert.notEqual(repeated.accessToken, first.accessToken);
  now += 2000;
  assert.equal((await f.request('/api/v1/save', { token: first.accessToken })).status, 401);
  const restored = await f.guest(first.secret, { previousPlayerId: first.playerId });
  assert.equal(restored.playerId, first.playerId);
  const missing = await f.request('/api/v1/guest/sessions', { method: 'POST', body: { installationSecret: randomBytes(32).toString('base64url'), previousPlayerId: first.playerId } });
  assert.equal(missing.status, 404); assert.equal(missing.value.code, 'SESSION_IDENTITY_NOT_FOUND');
  const conflict = await f.request('/api/v1/guest/sessions', { method: 'POST', body: { installationSecret: first.secret, previousPlayerId: randomUUID() } });
  assert.equal(conflict.status, 409); assert.equal(conflict.value.code, 'SESSION_IDENTITY_CONFLICT');
  const sql = new DatabaseSync(f.filename, { readOnly: true }); try { assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 1); } finally { sql.close(); }
});

test('idempotent receipts, stale/reordered writes and concurrent CAS cannot roll progress backwards', async t => {
  const f = await fixture(t); const player = await f.guest();
  const oldOperation = randomUUID();
  const old = encodedFixture({ marker: 1 });
  assert.equal((await f.save(player.accessToken, 0, { ...old, operationId: oldOperation })).value.revision, 1);
  const results = await Promise.all([f.save(player.accessToken, 1, encodedFixture({ marker: 2 })), f.save(player.accessToken, 1, encodedFixture({ marker: 3 }))]);
  assert.deepEqual(results.map(value => value.status).sort(), [200, 409]);
  const duplicate = await f.save(player.accessToken, 0, { ...old, operationId: oldOperation });
  assert.equal(duplicate.status, 200); assert.equal(duplicate.value.replayed, true); assert.equal(duplicate.value.revision, 1); assert.equal(duplicate.value.currentRevision, 2);
  const changed = await f.save(player.accessToken, 0, { ...encodedFixture({ marker: 8 }), operationId: oldOperation });
  assert.equal(changed.status, 409); assert.equal(changed.value.code, 'OPERATION_CONFLICT');
  const stale = await f.save(player.accessToken, 0);
  assert.equal(stale.status, 409); assert.equal(stale.value.currentRevision, 2);
  const sql = new DatabaseSync(f.filename, { readOnly: true }); try { assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_save_operations').get().n, 2); } finally { sql.close(); }
});

test('SQL failure rolls snapshot and operation receipt back together, then exact request can retry', async t => {
  const f = await fixture(t); const player = await f.guest();
  const sql = new DatabaseSync(f.filename);
  sql.exec("CREATE TRIGGER reject_receipt BEFORE INSERT ON gf_save_operations BEGIN SELECT RAISE(ABORT,'fixture disk failure'); END;");
  const request = { operationId: randomUUID(), ...encodedFixture() };
  const failed = await f.save(player.accessToken, 0, request);
  assert.equal(failed.status, 503);
  assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_farm_snapshots').get().n, 0);
  assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_save_operations').get().n, 0);
  sql.exec('DROP TRIGGER reject_receipt');
  assert.equal((await f.save(player.accessToken, 0, request)).status, 200);
  assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_save_operations').get().n, 1);
  sql.close();
});

test('busy SQLite is bounded, observable and preserves pending request for a later retry', async t => {
  const f = await fixture(t); const player = await f.guest(); const sql = new DatabaseSync(f.filename);
  sql.exec('BEGIN IMMEDIATE');
  const started = Date.now(); const failed = await f.save(player.accessToken, 0);
  assert.equal(failed.status, 503); assert.ok(Date.now() - started < 2000);
  sql.exec('ROLLBACK'); sql.close();
  assert.equal((await f.save(player.accessToken, 0)).status, 200);
});

test('invalid credentials/claims, corrupt archives and offline-test preset never overwrite an existing farm', async t => {
  const f = await fixture(t); const player = await f.guest();
  assert.equal((await f.save(player.accessToken, 0)).status, 200);
  const malicious = [encodedFixture({ sourcePackage: 'com.quanhoang.greenfarm3.offlinetest' }), encodedFixture({ extraRecords: [['offline_test_balances_v1', null]] }), { snapshotBase64: 'AAAA', snapshotHash: '0'.repeat(64) }];
  for (const bad of malicious) assert.equal((await f.save(player.accessToken, 1, bad)).status, 400);
  const unknown = await f.save(player.accessToken, 1, { playerId: randomUUID() }); assert.equal(unknown.status, 400);
  assert.equal((await f.request('/api/v1/save')).status, 401);
  assert.equal((await f.request('/api/v1/save', { token: randomBytes(32).toString('base64url') })).status, 401);
  assert.equal((await f.request('/api/v1/save?playerId=' + player.playerId, { token: player.accessToken })).status, 400);
  assert.equal((await f.request('/api/v1/save', { token: player.accessToken })).value.revision, 1);
  const salt = 'never-log-installation-proof';
  assert.ok(!JSON.stringify(f.logs).includes(player.secret)); assert.ok(!JSON.stringify(f.logs).includes(player.accessToken));
});

test('legacy anonymous tokens remain usable for compatibility profile but cannot access strong save API', async t => {
  const f = await fixture(t);
  const account = await f.store().createSession({ sid: 'fixture-sid', issuedAt: 1700000000, expiresAt: 1800000000, refreshExpiresAt: 1900000000, credential: 'anonymous:fixture', clientId: 'fixture-client', deviceId: 'fixture-device', password: legacyPassword('fixture') });
  assert.ok(account.accountId);
  assert.equal((await f.request('/api/v1/save', { token: 'v1.public-derived-password-token' })).status, 401);
  const sql = new DatabaseSync(f.filename, { readOnly: true }); try { assert.equal(sql.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 0); } finally { sql.close(); }
});

test('archive validator checks outer/record checksums and keeps opaque bytes unchanged', () => {
  const bytes = archiveFixture();
  assert.equal(validateArchive(bytes).recordCount, 5);
  assert.deepEqual(decodeSnapshot(bytes.toString('base64'), createHash('sha256').update(bytes).digest('hex')), bytes);
  const bad = Buffer.from(bytes); bad[100] ^= 1;
  assert.throws(() => validateArchive(bad), /INVALID_SNAPSHOT/u);
  assert.throws(() => validateArchive(Buffer.concat([bytes, Buffer.from([0])])), /INVALID_SNAPSHOT/u);
  assert.throws(() => decodeSnapshot(bytes.toString('base64') + '\n', createHash('sha256').update(bytes).digest('hex')), /INVALID_SNAPSHOT/u);
});

test('migration is idempotent, preserves existing data, backs up additions and rejects changed migration files', async t => {
  const folder = mkdtempSync(join(tmpdir(), 'gf-sqlite-migrate-')); t.after(() => rmSync(folder, { recursive: true, force: true }));
  const filename = join(folder, 'store.sqlite');
  await migrateSqlite(filename);
  const db = new DatabaseSync(filename); db.prepare('INSERT INTO gf_players(id,installation_hash,created_at) VALUES(?,?,?)').run('fixture-player', randomBytes(32), 1); db.close();
  const again = await migrateSqlite(filename); assert.deepEqual(again.applied, []);
  const copied = join(folder, 'migrations'); (await import('node:fs')).mkdirSync(copied);
  const originals = new URL('../sqlite-migrations/', import.meta.url);
  for (const name of readdirSync(originals)) writeFileSync(join(copied, name), readFileSync(new URL(name, originals)));
  writeFileSync(join(copied, '999_preferences.sql'), 'CREATE TABLE future_preferences(id TEXT PRIMARY KEY) STRICT;');
  const result = await migrateSqlite(filename, { directory: copied }); assert.ok(result.backupFile);
  const backup = new DatabaseSync(result.backupFile, { readOnly: true }); assert.equal(backup.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 1); assert.equal(backup.prepare("SELECT name FROM sqlite_master WHERE name='future_preferences'").get(), undefined); backup.close();
  writeFileSync(join(copied, '001_accounts_players_progress.sql'), '-- changed');
  await assert.rejects(migrateSqlite(filename, { directory: copied }), error => error.code === 'MIGRATION_CHECKSUM_MISMATCH');
  const reopened = new DatabaseSync(filename, { readOnly: true }); assert.equal(reopened.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 1); reopened.close();
});
