import { resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { loadEnvironment } from '../src/config.js';
import { exportMysqlSnapshot, loadMysqlLegacyConfig, writePrivateExport } from '../src/db/mysql-legacy.js';

function outputArgument(args) {
  if (args.length !== 2 || args[0] !== '--output' || !args[1]) throw Object.assign(new Error('Specify --output <private-file.json>'), { code: 'USAGE_EXPORT_MYSQL' });
  return resolve(args[1]);
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  let connection;
  try {
    const output = outputArgument(process.argv.slice(2));
    loadEnvironment();
    const mysql = await import('mysql2/promise');
    connection = await mysql.default.createConnection(loadMysqlLegacyConfig());
    const bundle = await exportMysqlSnapshot(connection);
    writePrivateExport(output, bundle);
    console.log(JSON.stringify({ event: 'legacy_mysql_exported', file: output, tables: bundle.payload.tables.length, sha256: bundle.sha256 }));
  } catch (error) {
    console.error(JSON.stringify({ event: 'legacy_mysql_export_failed', code: error.code ?? 'EXPORT_FAILED' }));
    process.exitCode = 1;
  } finally { if (connection) await connection.end(); }
}
