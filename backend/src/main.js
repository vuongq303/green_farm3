import { createResearchServer } from './server.js';
import { loadEnvironment, loadConfig } from './config.js';
import { createMysqlStore } from './db/store.js';

let config;
try { loadEnvironment(); config = loadConfig(); }
catch (error) {
  console.error(error.message);
  process.exitCode = 1;
}

if (config) {
  const store = createMysqlStore(config.database);
  const server = createResearchServer({ ...config, store });
  server.on('error', error => {
    console.error(JSON.stringify({ event: 'listener_error', code: error.code ?? 'LISTENER_ERROR' }));
    process.exitCode = 1;
    void store.close().catch(() => console.error(JSON.stringify({ event: 'database_close_failed' })));
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
        void store.close().catch(() => {
          console.error(JSON.stringify({ event: 'database_close_failed' }));
          process.exitCode = 1;
        });
      });
      server.closeIdleConnections();
    });
  }
}
