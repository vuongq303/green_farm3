import assert from 'node:assert/strict';
import { createHash, createHmac } from 'node:crypto';
import { mkdtempSync, readFileSync, readdirSync, rmSync, statSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { DatabaseSync } from 'node:sqlite';
import test from 'node:test';
import { exportMysqlSnapshot, importMysqlExport, validateExport, writePrivateExport } from '../src/db/mysql-legacy.js';
import { migrateSqlite } from '../src/db/sqlite-migrate.js';
import { createSqliteStore } from '../src/db/sqlite.js';
import { createSessionService } from '../src/services/sessions.js';

function values() {
  return {
    gf_accounts: [{ id: '18446744073709551610', identity_hash: Buffer.alloc(32, 3), client_id: 'legacy-client', credential: 'anonymous:existing-identity', password_salt: Buffer.alloc(16, 7), password_hash: Buffer.alloc(64, 9), created_at: '2026-01-01 12:34:56.123456', updated_at: '2026-02-01 12:34:56.654321', historical_facebook_subject: 'kept-provider-link' }],
    gf_sessions: [{ sid_hash: Buffer.alloc(32, 11), account_id: '18446744073709551610', device_id: 'existing-device', issued_at: '1700000000', expires_at: '1700000900', refresh_expires_at: '1702592000' }],
    gf_compliance: [{ account_id: '18446744073709551610', country: 'VN', age: 30, updated_at: '1700000001' }],
    gf_schema_migrations: [{ version: 1, name: '001_accounts_sessions.sql', checksum: 'a'.repeat(64), applied_at: '2026-01-01 00:00:00.000000' }],
    gf_facebook_links: [{ account_id: '18446744073709551610', provider_subject: 'kept-provider-link', provider_payload: Buffer.from([0, 1, 255]) }],
  };
}

function sourceConnection(rows = values(), { engine = 'InnoDB' } = {}) {
  const commands = [];
  return {
    commands,
    async query(sql) {
      commands.push(sql);
      if (sql === 'SELECT DATABASE() AS name') return [[{ name: 'legacy_green_farm' }]];
      if (sql.startsWith('SELECT * FROM `')) return [rows[sql.split('`')[1]]];
      return [[]];
    },
    async execute(sql, arguments_) {
      commands.push(sql);
      if (sql.includes('information_schema.TABLES')) return [Object.keys(rows).sort().map(name => ({ name, engine }))];
      if (sql.includes('information_schema.COLUMNS')) return [Object.keys(rows[arguments_[1]][0] ?? {}).map(name => ({ name, type: 'fixture', nullable: 'NO', defaultValue: null, extra: '' }))];
      throw new Error('Unexpected test query');
    },
    async commit() { commands.push('COMMIT'); },
    async rollback() { commands.push('ROLLBACK'); },
  };
}

function createTarget(file) {
  const db = new DatabaseSync(file);
  db.exec(`PRAGMA foreign_keys=ON; PRAGMA journal_mode=WAL;
    CREATE TABLE gf_accounts(id TEXT PRIMARY KEY, identity_hash BLOB UNIQUE NOT NULL, client_id TEXT NOT NULL, credential TEXT NOT NULL, password_salt BLOB NOT NULL, password_hash BLOB NOT NULL, created_at TEXT NOT NULL, updated_at TEXT NOT NULL);
    CREATE TABLE gf_sessions(sid_hash BLOB PRIMARY KEY, account_id TEXT NOT NULL REFERENCES gf_accounts(id), device_id TEXT NOT NULL, issued_at INTEGER NOT NULL, expires_at INTEGER NOT NULL, refresh_expires_at INTEGER NOT NULL);
    CREATE TABLE gf_compliance(account_id TEXT PRIMARY KEY REFERENCES gf_accounts(id), country TEXT NOT NULL, age INTEGER NOT NULL, updated_at INTEGER NOT NULL);`);
  db.close();
}

function directory(t) {
  const path = mkdtempSync(join(tmpdir(), 'gf-mysql-preserve-'));
  t.after(() => rmSync(path, { recursive: true, force: true }));
  return path;
}

function rewriteDigest(bundle) {
  bundle.sha256 = createHash('sha256').update(JSON.stringify(bundle.payload)).digest('hex');
  return bundle;
}

test('consistent source export preserves binary, decimal IDs, microseconds and unknown Facebook data', async () => {
  const source = sourceConnection();
  const bundle = await exportMysqlSnapshot(source, { now: () => new Date('2026-11-13T00:00:00Z') });
  assert.deepEqual(source.commands.slice(0, 2), ['SET TRANSACTION ISOLATION LEVEL REPEATABLE READ', 'START TRANSACTION WITH CONSISTENT SNAPSHOT, READ ONLY']);
  assert.equal(source.commands.at(-1), 'COMMIT');
  assert.equal(validateExport(bundle), bundle);
  const account = bundle.payload.tables.find(table => table.name === 'gf_accounts').rows[0];
  assert.equal(account.id.value, '18446744073709551610');
  assert.deepEqual(account.password_hash, { type: 'blob', value: Buffer.alloc(64, 9).toString('base64') });
  assert.equal(account.updated_at.value, '2026-02-01 12:34:56.654321');
  assert.ok(bundle.payload.tables.some(table => table.name === 'gf_facebook_links'));
  assert.ok(!Object.hasOwn(bundle.payload, 'password'));
});

test('nontransactional sources fail without committing or publishing a partial backup', async () => {
  const source = sourceConnection(values(), { engine: 'MyISAM' });
  await assert.rejects(exportMysqlSnapshot(source), { code: 'NONTRANSACTIONAL_SOURCE_TABLE' });
  assert.equal(source.commands.at(-1), 'ROLLBACK');
  assert.ok(!source.commands.includes('COMMIT'));
});

test('private export does not overwrite and rejects altered checksums or schema', async t => {
  const path = directory(t);
  const bundle = await exportMysqlSnapshot(sourceConnection());
  const file = join(path, 'old-mysql.json');
  writePrivateExport(file, bundle);
  assert.throws(() => writePrivateExport(file, bundle), { code: 'EEXIST' });
  assert.deepEqual(JSON.parse(readFileSync(file, 'utf8')), bundle);
  if (process.platform !== 'win32') assert.equal(statSync(file).mode & 0o777, 0o600);
  assert.equal(readdirSync(path).filter(name => name.includes('.tmp-')).length, 0);
  const altered = structuredClone(bundle);
  altered.payload.tables[0].rows[0].credential.value = 'changed';
  assert.throws(() => validateExport(altered), { code: 'LEGACY_EXPORT_CHECKSUM_MISMATCH' });
  const schema = structuredClone(bundle);
  schema.payload.tables.find(table => table.name === 'gf_accounts').columns = [{ name: 'id' }];
  assert.throws(() => validateExport(rewriteDigest(schema)), { code: 'INCOMPATIBLE_LEGACY_SCHEMA' });
});

test('real SQLite import backs up, commits all legacy values, archives unknowns and is replay-safe', async t => {
  const path = directory(t);
  const file = join(path, 'mysql.json');
  const sqliteFile = join(path, 'target.sqlite');
  const bundle = await exportMysqlSnapshot(sourceConnection());
  writePrivateExport(file, bundle);
  createTarget(sqliteFile);
  const result = await importMysqlExport({ exportFile: file, sqliteFile });
  assert.deepEqual(result.inserted, { accounts: 1, sessions: 1, compliance: 1 });
  assert.equal(result.archivedTables, 5);
  const before = new DatabaseSync(result.backupFile, { readOnly: true });
  assert.equal(before.prepare('SELECT COUNT(*) AS n FROM gf_accounts').get().n, 0);
  before.close();
  const db = new DatabaseSync(sqliteFile, { readOnly: true });
  const account = db.prepare('SELECT * FROM gf_accounts').get();
  assert.equal(account.id, '18446744073709551610');
  assert.equal(account.created_at, '2026-01-01 12:34:56.123456');
  assert.equal(account.updated_at, '2026-02-01 12:34:56.654321');
  assert.deepEqual(Buffer.from(account.password_hash), Buffer.alloc(64, 9));
  assert.deepEqual(Buffer.from(account.password_salt), Buffer.alloc(16, 7));
  const session = db.prepare('SELECT * FROM gf_sessions').get();
  assert.equal(session.account_id, account.id);
  assert.equal(session.refresh_expires_at, 1702592000);
  const archive = db.prepare('SELECT table_json FROM gf_imported_legacy_tables WHERE table_name = ?').get('gf_accounts');
  assert.equal(JSON.parse(archive.table_json).rows[0].historical_facebook_subject.value, 'kept-provider-link');
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_imported_legacy_tables').get().n, 5);
  assert.equal(db.prepare("SELECT COUNT(*) AS n FROM sqlite_master WHERE type='table' AND name LIKE '%guest%'").get().n, 0);
  db.close();
  const replay = await importMysqlExport({ exportFile: file, sqliteFile });
  assert.equal(replay.status, 'already_imported');
  const reopened = new DatabaseSync(sqliteFile, { readOnly: true });
  assert.equal(reopened.prepare('SELECT COUNT(*) AS n FROM gf_accounts').get().n, 1);
  assert.equal(reopened.prepare('SELECT COUNT(*) AS n FROM gf_sessions').get().n, 1);
  reopened.close();
});

test('a late conflicting row rolls back earlier inserts and never replaces target data', async t => {
  const path = directory(t);
  const file = join(path, 'mysql.json');
  const sqliteFile = join(path, 'target.sqlite');
  createTarget(sqliteFile);
  const bundle = await exportMysqlSnapshot(sourceConnection());
  writePrivateExport(file, bundle);
  const target = new DatabaseSync(sqliteFile);
  const source = values().gf_accounts[0];
  target.prepare('INSERT INTO gf_accounts VALUES (?, ?, ?, ?, ?, ?, ?, ?)').run('1', Buffer.alloc(32, 8), 'other-client', 'other-account', source.password_salt, source.password_hash, source.created_at, source.updated_at);
  const session = values().gf_sessions[0];
  target.prepare('INSERT INTO gf_sessions VALUES (?, ?, ?, ?, ?, ?)').run(session.sid_hash, '1', 'other-device', 1, 2, 3);
  target.close();
  await assert.rejects(importMysqlExport({ exportFile: file, sqliteFile }), { code: 'LEGACY_IMPORT_COLLISION' });
  const after = new DatabaseSync(sqliteFile, { readOnly: true });
  assert.equal(after.prepare('SELECT COUNT(*) AS n FROM gf_accounts').get().n, 1);
  assert.equal(after.prepare('SELECT credential FROM gf_accounts').get().credential, 'other-account');
  assert.equal(after.prepare('SELECT device_id FROM gf_sessions').get().device_id, 'other-device');
  assert.equal(after.prepare("SELECT COUNT(*) AS n FROM sqlite_master WHERE name='gf_legacy_imports'").get().n, 0);
  after.close();
  assert.equal(readdirSync(path).filter(name => name.endsWith('.bak')).length, 1);
});

test('corrupt exports never create a missing target or lose the original input', async t => {
  const path = directory(t);
  const file = join(path, 'mysql.json');
  const missing = join(path, 'missing.sqlite');
  const bundle = await exportMysqlSnapshot(sourceConnection());
  bundle.payload.tables[0].rows[0].credential.value = 'tampered';
  writeFileSync(file, JSON.stringify(bundle));
  await assert.rejects(importMysqlExport({ exportFile: file, sqliteFile: missing }), { code: 'LEGACY_EXPORT_CHECKSUM_MISMATCH' });
  assert.equal(readdirSync(path).includes('missing.sqlite'), false);
  assert.equal(statSync(file).size > 0, true);
});

test('import into actual migrated schema preserves a valid legacy session without creating any guest ownership', async t => {
  const path = directory(t);
  const file = join(path, 'mysql.json');
  const sqliteFile = join(path, 'actual.sqlite');
  const sourceRows = values();
  const sid = 'b'.repeat(48);
  sourceRows.gf_sessions[0].sid_hash = createHash('sha256').update(sid).digest();
  await migrateSqlite(sqliteFile);
  const bundle = await exportMysqlSnapshot(sourceConnection(sourceRows));
  writePrivateExport(file, bundle);
  await importMysqlExport({ exportFile: file, sqliteFile });
  const store = createSqliteStore({ filename: sqliteFile });
  try {
    assert.equal((await store.health()).status, 'ready');
    const secret = 'fixture-only-stable-legacy-session-secret';
    const payload = { sid, iat: 1700000000, exp: 1700000900 };
    const signed = 'v1.' + Buffer.from(JSON.stringify(payload)).toString('base64url');
    const token = signed + '.' + createHmac('sha256', secret).update(signed).digest('base64url');
    const sessions = createSessionService({ store, secret, ttl: 900, now: () => 1700000000000 });
    const recovered = await sessions.forToken(token);
    assert.equal(recovered.accountId, sourceRows.gf_accounts[0].id);
    assert.equal(recovered.credential, sourceRows.gf_accounts[0].credential);
  } finally { await store.close(); }
  const db = new DatabaseSync(sqliteFile, { readOnly: true });
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 0);
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_guest_sessions').get().n, 0);
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_farm_snapshots').get().n, 0);
  db.close();
});
