const DIAGNOSTICS = {
  MYSQL_CONNECTION_FAILED: {
    phase: 'connection',
    message: 'Cannot connect to MySQL. Check that MySQL is running and reachable at DB_HOST and DB_PORT.',
  },
  MYSQL_AUTH_FAILED: {
    phase: 'authentication',
    message: 'MySQL rejected the login. Check DB_USER, DB_PASSWORD and the account host permissions.',
  },
  MYSQL_DATABASE_NOT_FOUND: {
    phase: 'database',
    message: 'The MySQL database selected by DB_NAME does not exist. Create it or correct DB_NAME.',
  },
  MYSQL_PERMISSION_DENIED: {
    phase: 'database',
    message: 'MySQL denied access. Check the grants for DB_USER on the database selected by DB_NAME.',
  },
  MYSQL_SCHEMA_NOT_READY: {
    phase: 'schema',
    message: 'The required MySQL schema is missing or outdated. Run npm run migrate against this database before restarting.',
  },
  MYSQL_SCHEMA_CHECK_FAILED: {
    phase: 'schema',
    message: 'The MySQL schema check failed. Check MySQL compatibility, migration status and database permissions.',
  },
  MIGRATION_CHECKSUM_MISMATCH: {
    phase: 'migration',
    message: 'An applied migration changed. Restore it and add a new migration to preserve existing data.',
  },
  MIGRATION_LOCK_UNAVAILABLE: {
    phase: 'migration',
    message: 'The MySQL migration lock could not be acquired. Check for a running migration and retry after it finishes.',
  },
  MIGRATION_FAILED: {
    phase: 'migration',
    message: 'The MySQL migration failed. Check migration status and the migration account permissions.',
  },
};

const DRIVER_CODES = {
  ECONNREFUSED: 'MYSQL_CONNECTION_FAILED',
  ECONNRESET: 'MYSQL_CONNECTION_FAILED',
  ETIMEDOUT: 'MYSQL_CONNECTION_FAILED',
  ENOTFOUND: 'MYSQL_CONNECTION_FAILED',
  EAI_AGAIN: 'MYSQL_CONNECTION_FAILED',
  ENETUNREACH: 'MYSQL_CONNECTION_FAILED',
  EHOSTUNREACH: 'MYSQL_CONNECTION_FAILED',
  PROTOCOL_CONNECTION_LOST: 'MYSQL_CONNECTION_FAILED',
  PROTOCOL_SEQUENCE_TIMEOUT: 'MYSQL_CONNECTION_FAILED',
  ER_CON_COUNT_ERROR: 'MYSQL_CONNECTION_FAILED',
  ER_TOO_MANY_USER_CONNECTIONS: 'MYSQL_CONNECTION_FAILED',
  ER_ACCESS_DENIED_ERROR: 'MYSQL_AUTH_FAILED',
  ER_ACCESS_DENIED_NO_PASSWORD_ERROR: 'MYSQL_AUTH_FAILED',
  ER_NOT_SUPPORTED_AUTH_MODE: 'MYSQL_AUTH_FAILED',
  ER_BAD_DB_ERROR: 'MYSQL_DATABASE_NOT_FOUND',
  ER_DBACCESS_DENIED_ERROR: 'MYSQL_PERMISSION_DENIED',
  ER_TABLEACCESS_DENIED_ERROR: 'MYSQL_PERMISSION_DENIED',
  ER_COLUMNACCESS_DENIED_ERROR: 'MYSQL_PERMISSION_DENIED',
  ER_SPECIFIC_ACCESS_DENIED_ERROR: 'MYSQL_PERMISSION_DENIED',
  ER_NO_SUCH_TABLE: 'MYSQL_SCHEMA_NOT_READY',
  ER_BAD_FIELD_ERROR: 'MYSQL_SCHEMA_NOT_READY',
};

export class DatabaseStartupError extends Error {
  constructor(error, phase) {
    const diagnostic = databaseDiagnostic(error, phase);
    super(diagnostic.message);
    this.code = diagnostic.code;
    this.diagnostic = diagnostic;
  }
}

export function requireSchemaVersion(rows) {
  if (rows.length !== 1) throw new DatabaseStartupError({ code: 'MYSQL_SCHEMA_NOT_READY' });
}

/** Log only mapped codes and fixed messages, never driver errors or connection settings. */
export function databaseDiagnostic(error, phase = 'connection') {
  if (error instanceof DatabaseStartupError) return error.diagnostic;
  const input = error?.code;
  let code;
  if (Object.hasOwn(DIAGNOSTICS, input)) code = input;
  else if (Object.hasOwn(DRIVER_CODES, input)) code = DRIVER_CODES[input];
  else if (phase === 'migration') code = 'MIGRATION_FAILED';
  else if (phase === 'schema') code = 'MYSQL_SCHEMA_CHECK_FAILED';
  else code = 'MYSQL_CONNECTION_FAILED';
  return { code, ...DIAGNOSTICS[code] };
}
