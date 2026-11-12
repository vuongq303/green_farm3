import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { createPoolFromConfig } from '../src/db/store.js';
import { migrate } from '../src/db/migrate.js';

let pool;
let phase = 'configuration';
try {
  loadEnvironment();
  pool = createPoolFromConfig(loadDatabaseConfig());
  phase = 'migration';
  const result = await migrate(pool);
  console.log(JSON.stringify({ migration: 'complete', ...result }));
} catch (error) {
  // Driver messages can contain server details; never print raw errors/env.
  const failure = { migration: 'failed', phase, code: error.code ?? 'MIGRATION_FAILED' };
  // Configuration validators name the missing/invalid variable without its
  // value. Database/SQL errors remain code-only and never expose query inputs.
  if (phase === 'configuration' && /^(Configure DB_|DB_)/u.test(error.message ?? '')) {
    failure.message = error.message;
  }
  if (error.code === 'MIGRATION_CHECKSUM_MISMATCH') {
    failure.message = 'An applied migration changed; restore it and add a new migration instead of resetting data';
  }
  console.error(JSON.stringify(failure));
  process.exitCode = 1;
} finally {
  if (pool) await pool.end();
}
