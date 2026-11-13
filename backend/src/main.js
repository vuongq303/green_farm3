import { createResearchServer } from './server.js';
import { loadEnvironment, loadConfig } from './config.js';
import { createSqliteStore } from './db/sqlite.js';
import { loadNewPlayerDefaults } from './services/new-player-defaults.js';

async function closeStore(store) {
  try { await store.close(); }
  catch {
    console.error(JSON.stringify({ event: 'database_close_failed' }));
    process.exitCode = 1;
  }
}

async function main() {
  let config;
  try { loadEnvironment(); config = loadConfig(); loadNewPlayerDefaults(config.newPlayerDefaultsFile); }
  catch (error) {
    console.error(error.message);
    process.exitCode = 1;
    return;
  }

  let store;
  try {
    store = createSqliteStore(config.database);
    const database = await store.health();
    console.log(JSON.stringify({ event: 'database_ready', schema_version: database.schemaVersion }));
  } catch (error) {
    console.error(JSON.stringify({
      event: 'startup_database_error',
      code: error?.code === 'LEGACY_MIGRATION_REQUIRED' ? 'LEGACY_MIGRATION_REQUIRED' : 'DATABASE_UNAVAILABLE',
      message: error?.code === 'LEGACY_MIGRATION_REQUIRED'
        ? 'Legacy MySQL configuration is present but its consistent export has not been imported. Follow docs/MYSQL_TO_SQLITE.md; do not silently start an empty account database. Use npm run migrate -- --new-sqlite only for an explicitly separate new instance.'
        : 'Cannot open the configured SQLite file or the schema is not ready. Check SQLITE_PATH, directory permissions and run npm run migrate before restarting. Existing data is retained.',
    }));
    process.exitCode = 1;
    if (store) await closeStore(store);
    return;
  }

  let server;
  try { server = createResearchServer({ ...config, store }); }
  catch {
    console.error(JSON.stringify({ event: 'startup_server_error', code: 'SERVER_INITIALIZATION_FAILED', message: 'Check the backend session configuration in backend/.env.' }));
    process.exitCode = 1;
    await closeStore(store);
    return;
  }
  server.on('error', error => {
    console.error(JSON.stringify({ event: 'listener_error', code: error.code ?? 'LISTENER_ERROR' }));
    process.exitCode = 1;
    void closeStore(store);
  });
  server.listen(config.port, config.host, () => {
    console.log(JSON.stringify({ event: 'listening', host: config.host, port: config.port, backend_base_url: config.publicBaseUrl, persistence: 'sqlite' }));
  });
  let closing = false;
  for (const signal of ['SIGINT', 'SIGTERM']) {
    process.once(signal, () => {
      if (closing) return;
      closing = true;
      server.close(() => {
        void closeStore(store);
      });
      server.closeIdleConnections();
    });
  }
}

await main();
