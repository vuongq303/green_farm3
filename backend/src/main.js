import { createResearchServer } from './server.js';
import { loadEnvironment, loadConfig } from './config.js';
import { createMysqlStore } from './db/store.js';

async function closeStore(store) {
  try { await store.close(); }
  catch {
    console.error(JSON.stringify({ event: 'database_close_failed' }));
    process.exitCode = 1;
  }
}

async function main() {
  let config;
  try { loadEnvironment(); config = loadConfig(); }
  catch (error) {
    console.error(error.message);
    process.exitCode = 1;
    return;
  }

  const store = createMysqlStore(config.database);
  try {
    // mysql2 pools connect lazily. Creating a pool does not prove that MySQL
    // accepts the credentials or that the required migration has been applied.
    const database = await store.health();
    console.log(JSON.stringify({ event: 'database_ready', schema_version: database.schemaVersion }));
  } catch {
    console.error(JSON.stringify({
      event: 'startup_database_error',
      code: 'DATABASE_UNAVAILABLE',
      message: 'Cannot connect to MySQL or the required schema is not ready. Check DB_* in backend/.env, start MySQL and run npm run migrate before restarting.',
    }));
    process.exitCode = 1;
    await closeStore(store);
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
    console.log(JSON.stringify({ event: 'listening', host: config.host, port: config.port, backend_base_url: config.publicBaseUrl, persistence: 'mysql' }));
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
