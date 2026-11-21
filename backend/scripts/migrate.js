import { configurationDiagnostic, loadDatabaseConfig } from '../src/config.js';
import { databaseDiagnostic } from '../src/db/diagnostics.js';
import { createPoolFromConfig } from '../src/db/store.js';
import { migrate } from '../src/db/migrate.js';

let pool;
let phase = 'configuration';
try {
  const database = loadDatabaseConfig();
  phase = 'migration';
  pool = createPoolFromConfig(database);
  const result = await migrate(pool);
  console.log(JSON.stringify({ migration: 'complete', ...result }));
} catch (error) {
  const failure = phase === 'configuration'
    ? { phase, ...configurationDiagnostic(error) }
    : databaseDiagnostic(error, 'migration');
  console.error(JSON.stringify({ migration: 'failed', ...failure }));
  process.exitCode = 1;
} finally {
  if (pool) {
    try { await pool.end(); }
    catch {
      console.error(JSON.stringify({ event: 'database_close_failed' }));
      process.exitCode = 1;
    }
  }
}
