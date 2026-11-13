import { createHash, randomBytes } from 'node:crypto';
import { existsSync, mkdirSync, openSync, closeSync, writeFileSync, fsyncSync, linkSync, unlinkSync, chmodSync, readFileSync, statSync } from 'node:fs';
import { dirname, resolve } from 'node:path';
import { DatabaseSync, backup } from 'node:sqlite';

const FORMAT = 'greenfarm-mysql-preservation';
const MAX_BYTES = 128 * 1024 * 1024;
const REQUIRED = ['gf_accounts', 'gf_sessions', 'gf_compliance', 'gf_schema_migrations'];
const LEGACY_COLUMNS = {
  gf_accounts: ['id', 'identity_hash', 'client_id', 'credential', 'password_salt', 'password_hash', 'created_at', 'updated_at'],
  gf_sessions: ['sid_hash', 'account_id', 'device_id', 'issued_at', 'expires_at', 'refresh_expires_at'],
  gf_compliance: ['account_id', 'country', 'age', 'updated_at'],
};
const identifier = value => typeof value === 'string' && /^[A-Za-z0-9_$]{1,64}$/u.test(value);
const sha256 = text => createHash('sha256').update(text).digest('hex');

function fail(code) { throw Object.assign(new Error(code), { code }); }
function check(condition, code = 'INVALID_LEGACY_EXPORT') { if (!condition) fail(code); }

/** Old DB_* values are read only by the explicit migration command. */
export function loadMysqlLegacyConfig(env = process.env) {
  for (const key of ['DB_NAME', 'DB_USER']) {
    if (typeof env[key] !== 'string' || !env[key]) fail('MISSING_' + key);
  }
  check(typeof env.DB_PASSWORD === 'string', 'MISSING_DB_PASSWORD');
  check(identifier(env.DB_NAME), 'INVALID_DB_NAME');
  const port = Number(env.DB_PORT ?? 3306);
  check(Number.isInteger(port) && port >= 1 && port <= 65535, 'INVALID_DB_PORT');
  return {
    host: env.DB_HOST ?? '127.0.0.1', port, database: env.DB_NAME,
    user: env.DB_USER, password: env.DB_PASSWORD,
    charset: 'utf8mb4', timezone: 'Z', supportBigNumbers: true,
    bigNumberStrings: true, dateStrings: true, multipleStatements: false,
    connectTimeout: 5000,
  };
}

function encode(value) {
  if (value === null) return { type: 'null' };
  if (Buffer.isBuffer(value) || value instanceof Uint8Array) return { type: 'blob', value: Buffer.from(value).toString('base64') };
  if (typeof value === 'bigint') return { type: 'bigint', value: String(value) };
  if (value instanceof Date) return { type: 'string', value: value.toISOString() };
  if (typeof value === 'string' || typeof value === 'boolean') return { type: typeof value, value };
  if (typeof value === 'number' && Number.isFinite(value)) return { type: 'number', value };
  if (typeof value === 'object') return { type: 'json', value: JSON.parse(JSON.stringify(value)) };
  fail('UNSUPPORTED_MYSQL_VALUE');
}

function decode(entry) {
  check(entry && typeof entry === 'object' && !Array.isArray(entry));
  if (entry.type === 'null') { check(Object.keys(entry).length === 1); return null; }
  check(Object.keys(entry).length === 2 && Object.hasOwn(entry, 'value'));
  const { type, value } = entry;
  if (type === 'string' && typeof value === 'string') return value;
  if (type === 'boolean' && typeof value === 'boolean') return value;
  if (type === 'number' && typeof value === 'number' && Number.isFinite(value)) return value;
  if (type === 'bigint' && typeof value === 'string' && /^-?(0|[1-9][0-9]*)$/u.test(value)) return value;
  if (type === 'blob' && typeof value === 'string') {
    const bytes = Buffer.from(value, 'base64');
    check(bytes.toString('base64') === value);
    return bytes;
  }
  if (type === 'json' && value !== undefined) return value;
  fail('INVALID_LEGACY_EXPORT');
}

/** The caller owns the connection. No credentials are included in the bundle. */
export async function exportMysqlSnapshot(connection, { now = () => new Date() } = {}) {
  let started = false;
  try {
    await connection.query('SET TRANSACTION ISOLATION LEVEL REPEATABLE READ');
    await connection.query('START TRANSACTION WITH CONSISTENT SNAPSHOT, READ ONLY');
    started = true;
    const [[database]] = await connection.query('SELECT DATABASE() AS name');
    check(identifier(database?.name), 'INVALID_SOURCE_DATABASE');
    const [catalog] = await connection.execute(`SELECT TABLE_NAME AS name, ENGINE AS engine
      FROM information_schema.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_TYPE = ? ORDER BY TABLE_NAME`,
    [database.name, 'BASE TABLE']);
    check(REQUIRED.every(name => catalog.some(table => table.name === name)), 'MISSING_LEGACY_TABLE');
    const tables = [];
    for (const table of catalog) {
      check(identifier(table.name), 'UNSUPPORTED_MYSQL_IDENTIFIER');
      // A consistent snapshot cannot protect nontransactional source tables.
      check(String(table.engine).toUpperCase() === 'INNODB', 'NONTRANSACTIONAL_SOURCE_TABLE');
      const [columns] = await connection.execute(`SELECT COLUMN_NAME AS name, COLUMN_TYPE AS type,
        IS_NULLABLE AS nullable, COLUMN_DEFAULT AS defaultValue, EXTRA AS extra
        FROM information_schema.COLUMNS WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ? ORDER BY ORDINAL_POSITION`,
      [database.name, table.name]);
      check(columns.length && columns.every(column => identifier(column.name)), 'UNSUPPORTED_MYSQL_IDENTIFIER');
      // Identifier is validated against a strict ASCII allowlist, not user SQL.
      const [rows] = await connection.query('SELECT * FROM `' + table.name + '`');
      tables.push({ name: table.name, engine: table.engine, columns,
        rows: rows.map(row => Object.fromEntries(columns.map(column => [column.name, encode(row[column.name])]))),
      });
    }
    await connection.commit();
    started = false;
    const payload = { database: database.name, exportedAt: now().toISOString(), tables };
    const serialized = JSON.stringify(payload);
    check(Buffer.byteLength(serialized) <= MAX_BYTES, 'LEGACY_EXPORT_TOO_LARGE');
    return { format: FORMAT, version: 1, sha256: sha256(serialized), payload };
  } catch (error) {
    if (started) { try { await connection.rollback(); } catch {} }
    throw error;
  }
}

/** Atomic publication without overwriting an earlier backup (NTFS/ext4 links). */
export function writePrivateExport(filename, bundle) {
  const output = resolve(filename);
  mkdirSync(dirname(output), { recursive: true, mode: 0o700 });
  const temporary = output + '.tmp-' + randomBytes(8).toString('hex');
  let descriptor;
  try {
    descriptor = openSync(temporary, 'wx', 0o600);
    writeFileSync(descriptor, JSON.stringify(bundle) + '\n');
    fsyncSync(descriptor);
    closeSync(descriptor);
    descriptor = undefined;
    // link fails with EEXIST; rename could overwrite a racing destination.
    linkSync(temporary, output);
    return output;
  } finally {
    if (descriptor !== undefined) closeSync(descriptor);
    if (existsSync(temporary)) unlinkSync(temporary);
  }
}

export function validateExport(bundle) {
  check(bundle?.format === FORMAT && bundle.version === 1 && /^[a-f0-9]{64}$/u.test(bundle.sha256));
  check(bundle.payload && identifier(bundle.payload.database) && typeof bundle.payload.exportedAt === 'string');
  check(sha256(JSON.stringify(bundle.payload)) === bundle.sha256, 'LEGACY_EXPORT_CHECKSUM_MISMATCH');
  check(Array.isArray(bundle.payload.tables) && bundle.payload.tables.length <= 10000);
  const names = new Set();
  for (const table of bundle.payload.tables) {
    check(identifier(table.name) && !names.has(table.name));
    names.add(table.name);
    check(Array.isArray(table.columns) && table.columns.length > 0 && table.columns.length <= 4096 && Array.isArray(table.rows));
    const columns = table.columns.map(column => column.name);
    check(columns.every(identifier) && new Set(columns).size === columns.length);
    if (LEGACY_COLUMNS[table.name]) check(LEGACY_COLUMNS[table.name].every(column => columns.includes(column)), 'INCOMPATIBLE_LEGACY_SCHEMA');
    for (const row of table.rows) {
      check(row && typeof row === 'object' && !Array.isArray(row));
      check(Object.keys(row).length === columns.length && columns.every(column => Object.hasOwn(row, column)));
      for (const value of Object.values(row)) decode(value);
    }
  }
  check(REQUIRED.every(name => names.has(name)), 'MISSING_LEGACY_TABLE');
  return bundle;
}

function string(value, maximum, minimum = 0) {
  check(typeof value === 'string' && value.length >= minimum && value.length <= maximum, 'INCOMPATIBLE_LEGACY_ROW');
  return value;
}
function integer(value) {
  check((typeof value === 'number' && Number.isSafeInteger(value)) || (typeof value === 'string' && /^(0|[1-9][0-9]*)$/u.test(value)), 'INCOMPATIBLE_LEGACY_ROW');
  const number = Number(value);
  check(Number.isSafeInteger(number) && number >= 0, 'INCOMPATIBLE_LEGACY_ROW');
  return number;
}
function id(value) {
  const text = String(value);
  check(/^[1-9][0-9]{0,19}$/u.test(text) && BigInt(text) <= 18446744073709551615n, 'INCOMPATIBLE_LEGACY_ROW');
  return text;
}
function blob(value, size) {
  check(Buffer.isBuffer(value) && value.length === size, 'INCOMPATIBLE_LEGACY_ROW');
  return value;
}
function projectRows(table) {
  return table.rows.map(row => Object.fromEntries(Object.entries(row).map(([key, value]) => [key, decode(value)])));
}
function matches(left, right) {
  if (left instanceof Uint8Array || right instanceof Uint8Array) {
    return left instanceof Uint8Array && right instanceof Uint8Array && Buffer.from(left).equals(Buffer.from(right));
  }
  return left === right;
}
function insertRetaining(db, table, key, columns, row) {
  const where = key.map(name => name + ' = ?').join(' AND ');
  const previous = db.prepare('SELECT ' + columns.join(', ') + ' FROM ' + table + ' WHERE ' + where).get(...key.map(name => row[name]));
  if (previous) {
    check(columns.every(name => matches(previous[name], row[name])), 'LEGACY_IMPORT_COLLISION');
    return false;
  }
  db.prepare('INSERT INTO ' + table + ' (' + columns.join(', ') + ') VALUES (' + columns.map(() => '?').join(', ') + ')').run(...columns.map(name => row[name]));
  return true;
}

/** An online SQLite backup is completed before the all-or-nothing import. */
export async function importMysqlExport({ exportFile, sqliteFile }) {
  check(statSync(exportFile).size <= MAX_BYTES, 'LEGACY_EXPORT_TOO_LARGE');
  const bundle = validateExport(JSON.parse(readFileSync(exportFile, 'utf8')));
  const target = resolve(sqliteFile);
  check(existsSync(target) && statSync(target).isFile(), 'SQLITE_TARGET_MUST_EXIST');
  const db = new DatabaseSync(target, { allowExtension: false });
  let backupFile;
  let transaction = false;
  try {
    db.exec('PRAGMA foreign_keys = ON; PRAGMA busy_timeout = 2000; PRAGMA synchronous = FULL;');
    check(db.prepare('PRAGMA quick_check').all().every(row => row.quick_check === 'ok'), 'SQLITE_TARGET_CORRUPT');
    for (const name of REQUIRED.slice(0, 3)) {
      check(db.prepare("SELECT name FROM sqlite_master WHERE type = 'table' AND name = ?").get(name), 'SQLITE_TARGET_NOT_MIGRATED');
    }
    backupFile = target + '.before-mysql-' + new Date().toISOString().replace(/[:.]/gu, '-') + '-' + randomBytes(5).toString('hex') + '.bak';
    const reserved = openSync(backupFile, 'wx', 0o600);
    closeSync(reserved);
    await backup(db, backupFile);
    chmodSync(backupFile, 0o600);
    db.exec('BEGIN IMMEDIATE');
    transaction = true;
    db.exec(`CREATE TABLE IF NOT EXISTS gf_legacy_imports (
      source_sha256 TEXT PRIMARY KEY, imported_at TEXT NOT NULL, table_count INTEGER NOT NULL
    ); CREATE TABLE IF NOT EXISTS gf_imported_legacy_tables (
      source_sha256 TEXT NOT NULL, table_name TEXT NOT NULL, table_json TEXT NOT NULL,
      PRIMARY KEY (source_sha256, table_name), FOREIGN KEY (source_sha256) REFERENCES gf_legacy_imports(source_sha256)
    );`);
    if (db.prepare('SELECT source_sha256 FROM gf_legacy_imports WHERE source_sha256 = ?').get(bundle.sha256)) {
      db.exec('COMMIT'); transaction = false;
      return { status: 'already_imported', sourceSha256: bundle.sha256, backupFile };
    }
    const tables = new Map(bundle.payload.tables.map(table => [table.name, table]));
    const inserted = { accounts: 0, sessions: 0, compliance: 0 };
    for (const raw of projectRows(tables.get('gf_accounts'))) {
      const row = {
        id: id(raw.id), identity_hash: blob(raw.identity_hash, 32),
        client_id: string(raw.client_id, 512, 1), credential: string(raw.credential, 1034, 1),
        password_salt: blob(raw.password_salt, 16), password_hash: blob(raw.password_hash, 64),
        created_at: string(raw.created_at, 64, 1), updated_at: string(raw.updated_at, 64, 1),
      };
      if (insertRetaining(db, 'gf_accounts', ['id'], Object.keys(row), row)) inserted.accounts++;
    }
    for (const raw of projectRows(tables.get('gf_sessions'))) {
      const row = { sid_hash: blob(raw.sid_hash, 32), account_id: id(raw.account_id), device_id: string(raw.device_id, 256, 1),
        issued_at: integer(raw.issued_at), expires_at: integer(raw.expires_at), refresh_expires_at: integer(raw.refresh_expires_at) };
      check(row.expires_at >= row.issued_at && row.refresh_expires_at >= row.expires_at, 'INCOMPATIBLE_LEGACY_ROW');
      if (insertRetaining(db, 'gf_sessions', ['sid_hash'], Object.keys(row), row)) inserted.sessions++;
    }
    for (const raw of projectRows(tables.get('gf_compliance'))) {
      const row = { account_id: id(raw.account_id), country: string(raw.country, 2, 2), age: integer(raw.age), updated_at: integer(raw.updated_at) };
      check(/^[A-Z]{2}$/u.test(row.country) && row.age <= 120, 'INCOMPATIBLE_LEGACY_ROW');
      if (insertRetaining(db, 'gf_compliance', ['account_id'], Object.keys(row), row)) inserted.compliance++;
    }
    db.prepare('INSERT INTO gf_legacy_imports (source_sha256, imported_at, table_count) VALUES (?, ?, ?)').run(bundle.sha256, new Date().toISOString(), bundle.payload.tables.length);
    const archive = db.prepare('INSERT INTO gf_imported_legacy_tables (source_sha256, table_name, table_json) VALUES (?, ?, ?)');
    for (const table of bundle.payload.tables) archive.run(bundle.sha256, table.name, JSON.stringify(table));
    check(db.prepare('PRAGMA foreign_key_check').all().length === 0, 'LEGACY_IMPORT_FOREIGN_KEY_ERROR');
    db.exec('COMMIT'); transaction = false;
    return { status: 'imported', sourceSha256: bundle.sha256, inserted, archivedTables: bundle.payload.tables.length, backupFile };
  } catch (error) {
    if (transaction) { try { db.exec('ROLLBACK'); } catch {} }
    throw error;
  } finally { db.close(); }
}
