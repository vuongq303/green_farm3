import { DatabaseSync, backup } from 'node:sqlite';
import { createHash, randomBytes } from 'node:crypto';
import { existsSync, mkdirSync, readFileSync, readdirSync, statSync } from 'node:fs';
import { dirname, basename, join } from 'node:path';
import { fileURLToPath } from 'node:url';

const DEFAULT_DIRECTORY = fileURLToPath(new URL('../../sqlite-migrations/', import.meta.url));

export function sqliteMigrations(directory = DEFAULT_DIRECTORY) {
  return readdirSync(directory).filter(name => /^\d{3}_[a-z0-9_]+\.sql$/u.test(name)).sort().map(name => {
    const sql = readFileSync(join(directory, name), 'utf8');
    return { version: Number(name.slice(0, 3)), name, sql, checksum: createHash('sha256').update(sql).digest('hex') };
  });
}

/** SQLite's online backup API includes committed WAL pages in a consistent copy. */
export async function backupSqlite(database, filename) {
  const folder = join(dirname(filename), 'backups');
  mkdirSync(folder, { recursive: true, mode: 0o700 });
  const destination = join(folder, basename(filename) + '.before-migration-' + Date.now() + '-' + randomBytes(6).toString('hex') + '.sqlite');
  await backup(database, destination);
  return destination;
}

export async function migrateSqlite(filename, { directory = DEFAULT_DIRECTORY, startNewSqlite = false } = {}) {
  const existed = existsSync(filename) && statSync(filename).size > 0;
  mkdirSync(dirname(filename), { recursive: true, mode: 0o700 });
  const database = new DatabaseSync(filename);
  let backupFile;
  try {
    database.exec('PRAGMA busy_timeout=1000; PRAGMA foreign_keys=ON;');
    if (database.prepare('PRAGMA quick_check').get().quick_check !== 'ok') throw new Error('SQLITE_INTEGRITY_CHECK_FAILED');
    const ledger = database.prepare("SELECT name FROM sqlite_master WHERE type='table' AND name='gf_schema_migrations'").get();
    const migrations = sqliteMigrations(directory);
    const pending = [];
    const alreadyApplied = [];
    for (const migration of migrations) {
      const row = ledger ? database.prepare('SELECT checksum FROM gf_schema_migrations WHERE version=?').get(migration.version) : undefined;
      if (row && row.checksum !== migration.checksum) throw Object.assign(new Error('Applied SQLite migration changed; preserve database and add a new migration'), { code: 'MIGRATION_CHECKSUM_MISMATCH' });
      if (row) alreadyApplied.push(migration.version); else pending.push(migration);
    }
    if (pending.length && existed) backupFile = await backupSqlite(database, filename);
    database.exec('PRAGMA journal_mode=WAL; PRAGMA synchronous=FULL;');
    database.exec('BEGIN IMMEDIATE');
    try {
      database.exec(`CREATE TABLE IF NOT EXISTS gf_schema_migrations (
        version INTEGER PRIMARY KEY, name TEXT NOT NULL, checksum TEXT NOT NULL, applied_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
      ) STRICT`);
      const insert = database.prepare('INSERT INTO gf_schema_migrations(version,name,checksum) VALUES(?,?,?)');
      for (const migration of pending) {
        database.exec(migration.sql);
        insert.run(migration.version, migration.name, migration.checksum);
      }
      if (startNewSqlite) database.prepare(`INSERT INTO gf_storage_decisions(name,decision) VALUES('legacy-mysql','explicit-new-sqlite')
        ON CONFLICT(name) DO UPDATE SET decision=excluded.decision`).run();
      database.exec('COMMIT');
    } catch (error) {
      database.exec('ROLLBACK');
      throw error;
    }
    return { applied: pending.map(value => value.version), alreadyApplied, backupFile };
  } finally {
    database.close();
  }
}
