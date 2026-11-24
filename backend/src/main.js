import { createResearchServer } from './server.js';
import { configurationDiagnostic, loadConfig } from './config.js';
import { databaseDiagnostic } from './db/diagnostics.js';
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
  try { config = loadConfig(); }
  catch (error) {
    console.error(JSON.stringify({ event: 'startup_configuration_error', ...configurationDiagnostic(error) }));
    process.exitCode = 1;
    return;
  }

  let store;
  try {
    store = createMysqlStore(config.database);
    const database = await store.checkStartup({
      social: Object.values(config.auth.providers).some(provider => provider.enabled),
      giftcodes: Boolean(config.auth.publicBaseUrl),
    });
    console.log(JSON.stringify({ event: 'database_ready', schema_version: database.schemaVersion }));
  } catch (error) {
    console.error(JSON.stringify({ event: 'startup_database_error', ...databaseDiagnostic(error) }));
    process.exitCode = 1;
    if (store) await closeStore(store);
    return;
  }

  let server;
  try { server = createResearchServer({ config, store }); }
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
  const { host, port, publicBaseUrl } = config.server;
  server.listen(port, host, () => {
    console.log(JSON.stringify({ event: 'listening', host, port, backend_base_url: publicBaseUrl, persistence: 'mysql' }));
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
