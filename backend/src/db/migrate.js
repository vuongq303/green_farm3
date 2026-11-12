import { createHash } from 'node:crypto';
import { readdir, readFile } from 'node:fs/promises';
import { join } from 'node:path';
import { fileURLToPath } from 'node:url';

const DEFAULT_DIRECTORY = fileURLToPath(new URL('../../migrations/', import.meta.url));

/** DDL is additive and MySQL may commit each statement independently. A missing
 * ledger entry is retried safely; an applied migration is never edited/reset. */
export async function migrate(pool, { directory = DEFAULT_DIRECTORY } = {}) {
  const connection = await pool.getConnection();
  let lockName;
  try {
    const [[database]] = await connection.query('SELECT DATABASE() AS name');
    if (!database.name) throw new Error('A database must be selected before migration');
    lockName = 'greenfarm_migrate_' + createHash('sha256').update(database.name).digest('hex').slice(0, 32);
    const [[lock]] = await connection.execute('SELECT GET_LOCK(?, 10) AS acquired', [lockName]);
    if (Number(lock.acquired) !== 1) throw new Error('Another Green Farm migration is running');
    await connection.query(`CREATE TABLE IF NOT EXISTS gf_schema_migrations (
      version INT UNSIGNED NOT NULL PRIMARY KEY,
      name VARCHAR(128) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
      checksum CHAR(64) CHARACTER SET ascii COLLATE ascii_bin NOT NULL,
      applied_at TIMESTAMP(6) NOT NULL DEFAULT CURRENT_TIMESTAMP(6)
    ) ENGINE=InnoDB`);
    const filenames = (await readdir(directory)).filter(name => /^\d{3}_[a-z0-9_]+\.sql$/u.test(name)).sort();
    const result = { applied: [], alreadyApplied: [] };
    const versions = new Set();
    for (const filename of filenames) {
      const version = Number(filename.slice(0, 3));
      if (versions.has(version)) throw new Error('Duplicate migration version');
      versions.add(version);
      const sql = await readFile(join(directory, filename), 'utf8');
      const checksum = createHash('sha256').update(sql).digest('hex');
      const [rows] = await connection.execute('SELECT checksum FROM gf_schema_migrations WHERE version = ?', [version]);
      if (rows.length) {
        if (rows[0].checksum !== checksum) {
          const error = new Error('An applied migration changed; add a new numbered migration instead');
          error.code = 'MIGRATION_CHECKSUM_MISMATCH';
          throw error;
        }
        result.alreadyApplied.push(version);
        continue;
      }
      // These repository-controlled DDL files contain no procedures or quoted
      // semicolons. User values are never interpolated into this SQL.
      for (const statement of sql.split(';').map(value => value.trim()).filter(Boolean)) {
        await connection.query(statement);
      }
      await connection.execute('INSERT INTO gf_schema_migrations (version, name, checksum) VALUES (?, ?, ?)',
        [version, filename, checksum]);
      result.applied.push(version);
    }
    return result;
  } finally {
    if (lockName) {
      try { await connection.execute('SELECT RELEASE_LOCK(?)', [lockName]); } catch { /* connection may be gone */ }
    }
    connection.release();
  }
}
