import { createHash, randomBytes, scrypt, timingSafeEqual } from 'node:crypto';
import { promisify } from 'node:util';
import mysql from 'mysql2/promise';

const derivePassword = promisify(scrypt);
const MAX_SESSIONS = 10000;
const SCRYPT_OPTIONS = { N: 16384, r: 8, p: 1, maxmem: 64 * 1024 * 1024 };

export class DatabaseUnavailableError extends Error {
  constructor() {
    super('MySQL is unavailable or its schema is not ready');
    this.code = 'DATABASE_UNAVAILABLE';
  }
}

function applicationError(code, message) {
  const error = new Error(message);
  error.code = code;
  return error;
}

function sanitizedError(error) {
  if (['INVALID_CREDENTIALS', 'INVALID_SESSION', 'SESSION_CAPACITY'].includes(error?.code)) return error;
  // Do not return driver messages, SQL, connection strings or credential values.
  return new DatabaseUnavailableError();
}

function digest(text) {
  return createHash('sha256').update(text, 'utf8').digest();
}

export function createPoolFromConfig(config) {
  return mysql.createPool({
    host: config.host,
    port: config.port,
    database: config.database,
    user: config.user,
    password: config.password,
    connectionLimit: config.connectionLimit ?? 5,
    connectTimeout: config.connectTimeout ?? 5000,
    waitForConnections: true,
    queueLimit: 100,
    charset: 'utf8mb4',
    timezone: 'Z',
    supportBigNumbers: true,
    bigNumberStrings: true,
    multipleStatements: false,
  });
}

/** No in-memory/JSON fallback: every successful write is committed in MySQL. */
export function createMysqlStore(config) {
  const pool = createPoolFromConfig(config);
  let closed = false;

  async function transaction(operation) {
    let connection;
    try {
      connection = await pool.getConnection();
      await connection.beginTransaction();
      const result = await operation(connection);
      await connection.commit();
      return result;
    } catch (error) {
      if (connection) {
        try { await connection.rollback(); } catch { /* connection already failed */ }
      }
      throw sanitizedError(error);
    } finally {
      connection?.release();
    }
  }

  return {
    async health() {
      try {
        const [versions] = await pool.execute('SELECT version FROM gf_schema_migrations WHERE version = ?', [1]);
        if (versions.length !== 1) throw new DatabaseUnavailableError();
        // A TCP connection alone is insufficient: validate all required tables.
        await pool.query('SELECT id, identity_hash, client_id, credential, password_salt, password_hash FROM gf_accounts LIMIT 0');
        await pool.query('SELECT sid_hash, account_id, device_id, issued_at, expires_at, refresh_expires_at FROM gf_sessions LIMIT 0');
        await pool.query('SELECT account_id, country, age, updated_at FROM gf_compliance LIMIT 0');
        return { status: 'ready', schemaVersion: 1 };
      } catch {
        throw new DatabaseUnavailableError();
      }
    },

    async createSession({ sid, issuedAt, expiresAt, refreshExpiresAt = expiresAt, credential, clientId, deviceId, password }) {
      const identity = digest(JSON.stringify([clientId, credential]));
      const salt = randomBytes(16);
      const passwordHash = await derivePassword(password, salt, 64, SCRYPT_OPTIONS);
      return transaction(async connection => {
        // Serialize issuance so pruning/capacity remain deterministic even for
        // concurrent requests. This existing migration row is a stable mutex.
        const [schema] = await connection.execute('SELECT version FROM gf_schema_migrations WHERE version = ? FOR UPDATE', [1]);
        if (schema.length !== 1) throw new DatabaseUnavailableError();
        await connection.execute('DELETE FROM gf_sessions WHERE refresh_expires_at <= ?', [issuedAt]);
        const [[count]] = await connection.query('SELECT COUNT(*) AS total FROM gf_sessions');
        if (Number(count.total) >= MAX_SESSIONS) throw applicationError('SESSION_CAPACITY', 'Session capacity reached');
        await connection.execute(`INSERT INTO gf_accounts
          (identity_hash, client_id, credential, password_salt, password_hash)
          VALUES (?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = LAST_INSERT_ID(id)`,
        [identity, clientId, credential, salt, passwordHash]);
        const [[account]] = await connection.execute(`SELECT id, client_id, credential, password_salt, password_hash
          FROM gf_accounts WHERE identity_hash = ? FOR UPDATE`, [identity]);
        if (!account || account.client_id !== clientId || account.credential !== credential) {
          throw applicationError('INVALID_CREDENTIALS', 'Invalid credentials');
        }
        const checked = account.password_salt.equals(salt) ? passwordHash :
          await derivePassword(password, account.password_salt, 64, SCRYPT_OPTIONS);
        if (checked.length !== account.password_hash.length || !timingSafeEqual(checked, account.password_hash)) {
          throw applicationError('INVALID_CREDENTIALS', 'Invalid credentials');
        }
        await connection.execute(`INSERT INTO gf_sessions
          (sid_hash, account_id, device_id, issued_at, expires_at, refresh_expires_at) VALUES (?, ?, ?, ?, ?, ?)`,
        [digest(sid), account.id, deviceId, issuedAt, expiresAt, refreshExpiresAt]);
        return { accountId: String(account.id) };
      });
    },

    async findSession(sid) {
      try {
        const [rows] = await pool.execute(`SELECT s.issued_at, s.expires_at, s.refresh_expires_at, a.id AS account_id,
          a.credential, a.client_id FROM gf_sessions s
          INNER JOIN gf_accounts a ON a.id = s.account_id WHERE s.sid_hash = ?`, [digest(sid)]);
        if (!rows.length) return undefined;
        const row = rows[0];
        return { issuedAt: Number(row.issued_at), expiresAt: Number(row.expires_at), refreshExpiresAt: Number(row.refresh_expires_at),
          accountId: String(row.account_id), credential: row.credential, clientId: row.client_id };
      } catch (error) {
        throw sanitizedError(error);
      }
    },

    async refreshSession({ previousSid, sid, issuedAt, expiresAt, refreshExpiresAt }) {
      return transaction(async connection => {
        const [schema] = await connection.execute('SELECT version FROM gf_schema_migrations WHERE version = ? FOR UPDATE', [1]);
        if (schema.length !== 1) throw new DatabaseUnavailableError();
        const [[previous]] = await connection.execute(`SELECT s.account_id, s.device_id, s.refresh_expires_at,
          a.credential, a.client_id FROM gf_sessions s
          INNER JOIN gf_accounts a ON a.id = s.account_id WHERE s.sid_hash = ? FOR UPDATE`, [digest(previousSid)]);
        if (!previous || Number(previous.refresh_expires_at) <= issuedAt) {
          throw applicationError('INVALID_SESSION', 'Invalid session');
        }
        await connection.execute(`INSERT INTO gf_sessions
          (sid_hash, account_id, device_id, issued_at, expires_at, refresh_expires_at) VALUES (?, ?, ?, ?, ?, ?)`,
        [digest(sid), previous.account_id, previous.device_id, issuedAt, expiresAt, refreshExpiresAt]);
        await connection.execute('DELETE FROM gf_sessions WHERE sid_hash = ?', [digest(previousSid)]);
        return { accountId: String(previous.account_id), credential: previous.credential, clientId: previous.client_id };
      });
    },

    async saveCompliance({ accountId, country, age, updatedAt }) {
      return transaction(async connection => {
        const [accounts] = await connection.execute('SELECT id FROM gf_accounts WHERE id = ? FOR UPDATE', [accountId]);
        if (!accounts.length) throw applicationError('INVALID_SESSION', 'Invalid session');
        await connection.execute(`INSERT INTO gf_compliance (account_id, country, age, updated_at)
          VALUES (?, ?, ?, ?) ON DUPLICATE KEY UPDATE country = VALUES(country), age = VALUES(age), updated_at = VALUES(updated_at)`,
        [accountId, country, age, updatedAt]);
        await connection.execute('UPDATE gf_accounts SET updated_at = CURRENT_TIMESTAMP(6) WHERE id = ?', [accountId]);
      });
    },

    async close() {
      if (!closed) {
        closed = true;
        await pool.end();
      }
    },
  };
}
