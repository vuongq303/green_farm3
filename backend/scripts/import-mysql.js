import { resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { importMysqlExport } from '../src/db/mysql-legacy.js';

function argumentsFor(args) {
  if (args.length !== 4 || args[0] !== '--input' || args[2] !== '--sqlite-file' || !args[1] || !args[3]) {
    throw Object.assign(new Error('Specify --input <private-file.json> --sqlite-file <existing-database>'), { code: 'USAGE_IMPORT_MYSQL' });
  }
  return { exportFile: resolve(args[1]), sqliteFile: resolve(args[3]) };
}

if (process.argv[1] && resolve(process.argv[1]) === fileURLToPath(import.meta.url)) {
  try {
    const result = await importMysqlExport(argumentsFor(process.argv.slice(2)));
    console.log(JSON.stringify({ event: 'legacy_mysql_imported', ...result }));
  } catch (error) {
    console.error(JSON.stringify({ event: 'legacy_mysql_import_failed', code: error.code ?? 'IMPORT_FAILED' }));
    process.exitCode = 1;
  }
}
