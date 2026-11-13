import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { migrateSqlite } from '../src/db/sqlite-migrate.js';
import { existsSync } from 'node:fs';

let phase = 'configuration';
try {
  loadEnvironment();
  const config = loadDatabaseConfig();
  const flags = process.argv.slice(2);
  if (flags.some(flag => !['--new-sqlite', '--prepare-import'].includes(flag)) || new Set(flags).size !== flags.length || flags.length > 1) throw Object.assign(new Error('Choose only --prepare-import or --new-sqlite, or omit flags for the existing/new SQLite instance'), { code: 'INVALID_MIGRATION_ARGUMENTS' });
  const startNewSqlite = process.argv.includes('--new-sqlite');
  const prepareImport = process.argv.includes('--prepare-import');
  if (config.requiresLegacyImport && !existsSync(config.filename) && !startNewSqlite && !prepareImport) {
    throw Object.assign(new Error('Legacy MySQL settings exist. Export MySQL first; then use npm run migrate -- --prepare-import and import the consistent backup. Use --new-sqlite only for an explicitly separate new database.'), { code: 'LEGACY_MIGRATION_REQUIRED' });
  }
  phase = 'migration';
  const result = await migrateSqlite(config.filename, { startNewSqlite });
  console.log(JSON.stringify({ migration: 'complete', ...result }));
} catch (error) {
  // Driver messages can contain server details; never print raw errors/env.
  const failure = { migration: 'failed', phase, code: error.code ?? 'MIGRATION_FAILED' };
  // Configuration validators name the missing/invalid variable without its
  // value. Database/SQL errors remain code-only and never expose query inputs.
  if (phase === 'configuration' && /^SQLITE_/u.test(error.message ?? '')) {
    failure.message = error.message;
  }
  if (error.code === 'MIGRATION_CHECKSUM_MISMATCH') {
    failure.message = 'An applied migration changed; restore it and add a new migration instead of resetting data';
  }
  if (error.code === 'LEGACY_MIGRATION_REQUIRED') failure.message = error.message;
  console.error(JSON.stringify(failure));
  process.exitCode = 1;
}
