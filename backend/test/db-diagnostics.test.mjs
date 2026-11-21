import assert from 'node:assert/strict';
import { test } from 'node:test';
import { DatabaseStartupError, databaseDiagnostic } from '../src/db/diagnostics.js';

test('MySQL diagnostics distinguish connectivity, login, database selection, permissions and schema', () => {
  for (const [driverCode, phase, code] of [
    ['ENOTFOUND', 'connection', 'MYSQL_CONNECTION_FAILED'],
    ['ER_ACCESS_DENIED_ERROR', 'authentication', 'MYSQL_AUTH_FAILED'],
    ['ER_BAD_DB_ERROR', 'database', 'MYSQL_DATABASE_NOT_FOUND'],
    ['ER_DBACCESS_DENIED_ERROR', 'database', 'MYSQL_PERMISSION_DENIED'],
    ['ER_NO_SUCH_TABLE', 'schema', 'MYSQL_SCHEMA_NOT_READY'],
    ['ER_BAD_FIELD_ERROR', 'schema', 'MYSQL_SCHEMA_NOT_READY'],
    ['MIGRATION_CHECKSUM_MISMATCH', 'migration', 'MIGRATION_CHECKSUM_MISMATCH'],
    ['MIGRATION_LOCK_UNAVAILABLE', 'migration', 'MIGRATION_LOCK_UNAVAILABLE'],
  ]) {
    const input = { code: driverCode, message: 'private user, password, and SQL' };
    const result = databaseDiagnostic(input);
    assert.deepEqual(Object.keys(result).sort(), ['code', 'message', 'phase']);
    assert.equal(result.phase, phase);
    assert.equal(result.code, code);
    assert.ok(!JSON.stringify(result).includes(input.message));
  }
});

test('diagnostics present fixed output for unknown driver errors even after wrapping', () => {
  const input = { code: 'private_driver_identifier', message: 'private query and connection URL' };
  const startupError = new DatabaseStartupError(input, 'schema');
  assert.deepEqual(databaseDiagnostic(startupError), databaseDiagnostic(input, 'schema'));
  assert.equal(startupError.code, 'MYSQL_SCHEMA_CHECK_FAILED');
  assert.equal(databaseDiagnostic(input, 'migration').code, 'MIGRATION_FAILED');
  assert.ok(!JSON.stringify(startupError).includes(input.code));
  assert.ok(!startupError.message.includes(input.message));
});
