import { DatabaseUnavailableError } from './store.js';
import { requireSchemaVersion } from './diagnostics.js';
import { timingSafeEqual } from 'node:crypto';

// Keep identity arithmetic out of JavaScript numbers even if a caller supplies
// a pool without mysql2's bigNumberStrings option.
const USER_COLUMNS = 'CAST(u.id AS CHAR) AS user_id, u.game_id, u.provider, u.display_name';
const ACTIVE_USER = `(u.provider <> 'password' OR EXISTS
  (SELECT 1 FROM gf_password_accounts p WHERE p.user_id = u.id AND p.account_status = 'active'))`;
const SESSION_SCHEMA_READY = `SELECT version FROM gf_schema_migrations WHERE version = ?
  AND EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'gf_auth_sessions'
      AND COLUMN_NAME = 'family_id' AND IS_NULLABLE = 'NO'
  ) AND 2 = (
    SELECT COUNT(*) FROM information_schema.KEY_COLUMN_USAGE
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'gf_auth_sessions'
      AND CONSTRAINT_NAME = 'gf_auth_session_family_fk'
      AND REFERENCED_TABLE_SCHEMA = DATABASE() AND REFERENCED_TABLE_NAME = 'gf_auth_session_families'
      AND ((ORDINAL_POSITION = 1 AND COLUMN_NAME = 'family_id' AND REFERENCED_COLUMN_NAME = 'id')
        OR (ORDINAL_POSITION = 2 AND COLUMN_NAME = 'user_id' AND REFERENCED_COLUMN_NAME = 'user_id'))
  )`;

function user(row) {
  return { id: String(row.user_id ?? row.id), game_id: row.game_id,
    provider: row.provider, display_name: row.display_name ?? null };
}

function attempt(row) {
  if (!row) return undefined;
  return { id: row.id, provider: row.provider, appState: row.app_state,
    codeChallenge: row.code_challenge, redirectUri: row.redirect_uri,
    oauthStateHash: row.oauth_state_hash, nonce: row.nonce, providerVerifier: row.provider_verifier,
    createdAt: Number(row.created_at), expiresAt: Number(row.expires_at),
    startedAt: row.started_at == null ? null : Number(row.started_at),
    consumedAt: row.consumed_at == null ? null : Number(row.consumed_at) };
}

async function insertSession(connection, userId, session, familyId = session.id) {
  if (familyId === session.id) {
    await connection.execute(`INSERT INTO gf_auth_session_families
      (id, user_id, created_at) VALUES (?, ?, ?)`, [familyId, userId, session.issuedAt]);
  }
  await connection.execute(`INSERT INTO gf_auth_sessions
    (id, user_id, family_id, access_hash, refresh_hash, issued_at, access_expires_at, refresh_expires_at)
    VALUES (?, ?, ?, ?, ?, ?, ?, ?)`, [session.id, userId, familyId, session.accessHash, session.refreshHash,
    session.issuedAt, session.accessExpiresAt, session.refreshExpiresAt]);
}

async function insertPasswordUser(connection, { providerUserId, username, email, passwordHash, now }) {
  await connection.execute(`INSERT INTO users
    (provider, provider_user_id, display_name, created_at, updated_at, last_login_at)
    VALUES ('password', ?, ?, ?, ?, ?)`, [providerUserId, username, now, now, now]);
  const [[row]] = await connection.execute(`SELECT CAST(id AS CHAR) AS id, game_id, provider, display_name
    FROM users WHERE provider = 'password' AND provider_user_id = ? FOR UPDATE`, [providerUserId]);
  row.game_id = 'GF' + String(row.id).padStart(6, '0');
  await connection.execute('UPDATE users SET game_id = ? WHERE id = ?', [row.game_id, row.id]);
  await connection.execute(`INSERT INTO gf_password_accounts
    (user_id, username, email, password_hash, created_at, updated_at, last_login_at)
    VALUES (?, ?, ?, ?, ?, ?, ?)`, [row.id, username, email, passwordHash, now, now, now]);
  return user(row);
}

async function passwordConflicts(connection, username, email) {
  const [rows] = await connection.execute(`SELECT username, email FROM gf_password_accounts
    WHERE username = ? OR email = ?`, [username, email]);
  return ['username', 'email'].filter(field => rows.some(row => row[field] === (field === 'username' ? username : email)));
}

async function lockPasswordWebAttempt(connection, cookieHash, csrfHash, now) {
  const [[row]] = await connection.execute(`SELECT a.*, w.csrf_hash, w.expires_at AS web_expires_at
    FROM gf_auth_web_sessions w INNER JOIN gf_auth_attempts a ON a.id = w.attempt_id
    WHERE w.cookie_hash = ? FOR UPDATE`, [cookieHash]);
  if (!row || row.provider !== 'password' || row.started_at == null || row.consumed_at != null ||
      Number(row.expires_at) <= now || Number(row.web_expires_at) <= now ||
      !Buffer.isBuffer(csrfHash) || csrfHash.length !== row.csrf_hash.length ||
      !timingSafeEqual(row.csrf_hash, csrfHash)) return undefined;
  return row;
}

export async function checkSocialSchema(connection) {
  const [versions] = await connection.execute(SESSION_SCHEMA_READY, [5]);
  requireSchemaVersion(versions);
  await connection.query('SELECT id, game_id, provider, provider_user_id FROM users LIMIT 0');
  await connection.query('SELECT id, oauth_state_hash, code_challenge, consumed_at FROM gf_auth_attempts LIMIT 0');
  await connection.query('SELECT code_hash, attempt_id, user_id, used_at FROM gf_auth_codes LIMIT 0');
  await connection.query('SELECT id, family_id, access_hash, refresh_hash, revoked_at FROM gf_auth_sessions LIMIT 0');
  await connection.query('SELECT id, user_id, revoked_at FROM gf_auth_session_families LIMIT 0');
  await connection.query('SELECT user_id, username, email, password_hash, account_status FROM gf_password_accounts LIMIT 0');
  await connection.query('SELECT cookie_hash, attempt_id, csrf_hash, expires_at FROM gf_auth_web_sessions LIMIT 0');
  return { status: 'ready', schemaVersion: 5 };
}

/** Opaque-token/code hashes only; this store has no gameplay or SQLite API. */
export function createSocialStore(pool) {
  async function run(operation) {
    try { return await operation(); }
    catch { throw new DatabaseUnavailableError(); }
  }

  async function transaction(operation, { duplicateIsUndefined = false, duplicateResult } = {}) {
    let connection;
    try {
      connection = await pool.getConnection();
      await connection.beginTransaction();
      const result = await operation(connection);
      await connection.commit();
      return result;
    } catch (error) {
      if (connection) {
        try { await connection.rollback(); } catch { /* disconnected */ }
      }
      if (duplicateIsUndefined && error?.code === 'ER_DUP_ENTRY') return duplicateResult;
      throw new DatabaseUnavailableError();
    } finally { connection?.release(); }
  }

  return {
    async health() {
      return run(() => checkSocialSchema(pool));
    },

    async upsertUser(provider, providerUserId, displayName, now) {
      return transaction(async connection => {
        await connection.execute(`INSERT INTO users
          (provider, provider_user_id, display_name, created_at, updated_at, last_login_at)
          VALUES (?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE id = LAST_INSERT_ID(id),
          display_name = COALESCE(VALUES(display_name), display_name), updated_at = VALUES(updated_at),
          last_login_at = VALUES(last_login_at)`, [provider, providerUserId, displayName ?? null, now, now, now]);
        const [[row]] = await connection.execute(`SELECT CAST(id AS CHAR) AS id, game_id, provider, display_name
          FROM users WHERE provider = ? AND provider_user_id = ? FOR UPDATE`, [provider, providerUserId]);
        if (!row.game_id) {
          row.game_id = 'GF' + String(row.id).padStart(6, '0');
          await connection.execute('UPDATE users SET game_id = ? WHERE id = ?', [row.game_id, row.id]);
        }
        return user(row);
      });
    },

    async registerPasswordUser({ providerUserId, username, email, passwordHash, now }) {
      return transaction(connection => insertPasswordUser(connection,
        { providerUserId, username, email, passwordHash, now }), { duplicateIsUndefined: true });
    },

    async registerPasswordAndClaim({ cookieHash, csrfHash, providerUserId, username, email, passwordHash, now }) {
      const registered = await transaction(async connection => {
        const row = await lockPasswordWebAttempt(connection, cookieHash, csrfHash, now);
        if (!row) return undefined;
        const conflicts = await passwordConflicts(connection, username, email);
        if (conflicts.length) return { duplicate: true, conflicts };
        const account = await insertPasswordUser(connection, { providerUserId, username, email, passwordHash, now });
        await connection.execute('UPDATE gf_auth_attempts SET consumed_at = ? WHERE id = ?', [now, row.id]);
        return { user: account, attempt: attempt({ ...row, consumed_at: now }) };
      }, { duplicateIsUndefined: true, duplicateResult: { duplicate: true } });
      if (registered?.duplicate && !registered.conflicts) {
        // A concurrent registration can pass the check above. The existing
        // UNIQUE indexes reject it atomically; classify only committed fields
        // after rollback, rather than exposing/parsing a database diagnostic.
        return run(async () => ({ duplicate: true, conflicts: await passwordConflicts(pool, username, email) }));
      }
      return registered;
    },

    async findPasswordCredential(identifier) {
      return run(async () => {
        const [[row]] = await pool.execute(`SELECT CAST(user_id AS CHAR) AS user_id,
          username, email, password_hash, account_status FROM gf_password_accounts
          WHERE username = ? OR email = ?`, [identifier, identifier]);
        if (!row) return undefined;
        return { userId: String(row.user_id), username: row.username, email: row.email,
          passwordHash: row.password_hash, accountStatus: row.account_status };
      });
    },

    async loginPasswordUser(userId, now) {
      return transaction(async connection => {
        const [[row]] = await connection.execute(`SELECT ${USER_COLUMNS} FROM users u
          INNER JOIN gf_password_accounts p ON p.user_id = u.id
          WHERE u.id = ? AND u.provider = 'password' AND p.account_status = 'active' FOR UPDATE`, [userId]);
        if (!row) return undefined;
        await connection.execute('UPDATE users SET last_login_at = ?, updated_at = ? WHERE id = ?', [now, now, userId]);
        await connection.execute('UPDATE gf_password_accounts SET last_login_at = ? WHERE user_id = ?', [now, userId]);
        return user(row);
      });
    },

    async createAttempt(record) {
      return run(async () => {
        await pool.execute(`INSERT INTO gf_auth_attempts
          (id, provider, app_state, code_challenge, redirect_uri, created_at, expires_at)
          VALUES (?, ?, ?, ?, ?, ?, ?)`, [record.id, record.provider, record.appState,
          record.codeChallenge, record.redirectUri, record.createdAt, record.expiresAt]);
      });
    },

    async getAttempt(id) {
      return run(async () => {
        const [[row]] = await pool.execute('SELECT * FROM gf_auth_attempts WHERE id = ?', [id]);
        return attempt(row);
      });
    },

    async startPasswordAttempt(id, cookieHash, csrfHash, now) {
      return transaction(async connection => {
        const [[row]] = await connection.execute('SELECT * FROM gf_auth_attempts WHERE id = ? FOR UPDATE', [id]);
        if (!row || row.provider !== 'password' || row.started_at != null || row.consumed_at != null ||
            Number(row.expires_at) <= now) return undefined;
        await connection.execute('UPDATE gf_auth_attempts SET started_at = ? WHERE id = ?', [now, id]);
        await connection.execute(`INSERT INTO gf_auth_web_sessions (cookie_hash, attempt_id, csrf_hash, expires_at)
          VALUES (?, ?, ?, ?)`, [cookieHash, id, csrfHash, row.expires_at]);
        return attempt({ ...row, started_at: now });
      });
    },

    async getPasswordWebSession(cookieHash, now) {
      return run(async () => {
        const [[row]] = await pool.execute(`SELECT a.*, w.csrf_hash, w.expires_at AS web_expires_at
          FROM gf_auth_web_sessions w INNER JOIN gf_auth_attempts a ON a.id = w.attempt_id
          WHERE w.cookie_hash = ? AND w.expires_at > ? AND a.expires_at > ?
          AND a.provider = 'password' AND a.started_at IS NOT NULL AND a.consumed_at IS NULL`, [cookieHash, now, now]);
        return row ? { attempt: attempt(row), csrfHash: row.csrf_hash } : undefined;
      });
    },

    async claimPasswordAttempt(cookieHash, csrfHash, now) {
      return transaction(async connection => {
        const row = await lockPasswordWebAttempt(connection, cookieHash, csrfHash, now);
        if (!row) return undefined;
        await connection.execute('UPDATE gf_auth_attempts SET consumed_at = ? WHERE id = ?', [now, row.id]);
        return attempt({ ...row, consumed_at: now });
      });
    },

    async startAttempt(id, state, now) {
      return transaction(async connection => {
        const [[row]] = await connection.execute('SELECT * FROM gf_auth_attempts WHERE id = ? FOR UPDATE', [id]);
        if (!row || row.started_at != null || row.consumed_at != null || Number(row.expires_at) <= now) return undefined;
        await connection.execute(`UPDATE gf_auth_attempts SET oauth_state_hash = ?, nonce = ?,
          provider_verifier = ?, started_at = ? WHERE id = ?`,
        [state.oauthStateHash, state.nonce, state.providerVerifier ?? null, now, id]);
        return attempt({ ...row, oauth_state_hash: state.oauthStateHash, nonce: state.nonce,
          provider_verifier: state.providerVerifier ?? null, started_at: now });
      });
    },

    async claimOAuthAttempt(oauthStateHash, provider, now) {
      return transaction(async connection => {
        const [[row]] = await connection.execute(`SELECT * FROM gf_auth_attempts
          WHERE oauth_state_hash = ? AND provider = ? FOR UPDATE`, [oauthStateHash, provider]);
        if (!row || row.started_at == null || row.consumed_at != null || Number(row.expires_at) <= now) return undefined;
        await connection.execute('UPDATE gf_auth_attempts SET consumed_at = ? WHERE id = ?', [now, row.id]);
        return attempt({ ...row, consumed_at: now });
      });
    },

    async issueCode({ attemptId, userId, codeHash, expiresAt, now }) {
      return transaction(async connection => {
        const [[row]] = await connection.execute('SELECT * FROM gf_auth_attempts WHERE id = ? FOR UPDATE', [attemptId]);
        if (!row || row.consumed_at == null || Number(row.expires_at) <= now || expiresAt <= now) return false;
        const [[identity]] = await connection.execute(`SELECT u.id FROM users u
          WHERE u.id = ? AND u.provider = ? AND ${ACTIVE_USER} FOR UPDATE`, [userId, row.provider]);
        if (!identity) return false;
        const [existing] = await connection.execute('SELECT code_hash FROM gf_auth_codes WHERE attempt_id = ?', [attemptId]);
        if (existing.length) return false;
        await connection.execute(`INSERT INTO gf_auth_codes
          (code_hash, attempt_id, user_id, code_challenge, redirect_uri, expires_at) VALUES (?, ?, ?, ?, ?, ?)`,
        [codeHash, attemptId, userId, row.code_challenge, row.redirect_uri, expiresAt]);
        return true;
      });
    },

    async exchangeCode({ codeHash, codeChallenge, redirectUri, now, session }) {
      return transaction(async connection => {
        const [[row]] = await connection.execute(`SELECT c.used_at, c.expires_at, c.code_challenge,
          c.redirect_uri, ${USER_COLUMNS}
          FROM gf_auth_codes c INNER JOIN users u ON u.id = c.user_id WHERE c.code_hash = ? AND ${ACTIVE_USER} FOR UPDATE`, [codeHash]);
        if (!row || row.used_at != null || Number(row.expires_at) <= now ||
            row.code_challenge !== codeChallenge || row.redirect_uri !== redirectUri) return undefined;
        await connection.execute('UPDATE gf_auth_codes SET used_at = ? WHERE code_hash = ?', [now, codeHash]);
        await insertSession(connection, row.user_id, session);
        return user(row);
      });
    },

    async findAccess(accessHash, now) {
      return run(async () => {
        const [[row]] = await pool.execute(`SELECT s.id AS session_id, s.issued_at,
          s.access_expires_at, s.refresh_expires_at, ${USER_COLUMNS}
          FROM gf_auth_sessions s INNER JOIN users u ON u.id = s.user_id
          INNER JOIN gf_auth_session_families f ON f.id = s.family_id AND f.user_id = s.user_id
          WHERE s.access_hash = ? AND s.revoked_at IS NULL AND f.revoked_at IS NULL
          AND s.access_expires_at > ? AND ${ACTIVE_USER}`, [accessHash, now]);
        if (!row) return undefined;
        return { user: user(row), sessionId: row.session_id, issuedAt: Number(row.issued_at),
          accessExpiresAt: Number(row.access_expires_at), refreshExpiresAt: Number(row.refresh_expires_at) };
      });
    },

    async rotateRefresh(refreshHash, session, now) {
      return transaction(async connection => {
        const [[membership]] = await connection.execute('SELECT family_id FROM gf_auth_sessions WHERE refresh_hash = ?', [refreshHash]);
        if (!membership) return undefined;
        // Family first, token second: logout and rotation share this ordering.
        const [[family]] = await connection.execute(`SELECT CAST(user_id AS CHAR) AS user_id, revoked_at
          FROM gf_auth_session_families WHERE id = ? FOR UPDATE`, [membership.family_id]);
        if (!family || family.revoked_at != null) return undefined;
        const [[row]] = await connection.execute(`SELECT s.id AS session_id, s.revoked_at,
          s.refresh_expires_at, ${USER_COLUMNS} FROM gf_auth_sessions s
          INNER JOIN users u ON u.id = s.user_id WHERE s.refresh_hash = ? AND ${ACTIVE_USER} FOR UPDATE`, [refreshHash]);
        if (!row || row.revoked_at != null || String(row.user_id) !== String(family.user_id)
            || Number(row.refresh_expires_at) <= now) return undefined;
        await connection.execute('UPDATE gf_auth_sessions SET revoked_at = ? WHERE id = ?', [now, row.session_id]);
        await insertSession(connection, row.user_id, session, membership.family_id);
        return user(row);
      });
    },

    async revoke(accessHash, refreshHash, now) {
      if (!accessHash) return false;
      return transaction(async connection => {
        const [[membership]] = await connection.execute('SELECT family_id FROM gf_auth_sessions WHERE access_hash = ?', [accessHash]);
        if (!membership) return false;
        const [[family]] = await connection.execute(`SELECT revoked_at FROM gf_auth_session_families
          WHERE id = ? FOR UPDATE`, [membership.family_id]);
        if (!family || family.revoked_at != null) return false;
        const [[row]] = await connection.execute(`SELECT id, refresh_hash, revoked_at
          FROM gf_auth_sessions WHERE access_hash = ? FOR UPDATE`, [accessHash]);
        if (!row || (refreshHash && !row.refresh_hash.equals(refreshHash))) return false;
        // A response from an in-flight refresh may arrive after local logout.
        // Even an older token pair revokes all descendants through the family.
        await connection.execute('UPDATE gf_auth_session_families SET revoked_at = ? WHERE id = ?', [now, membership.family_id]);
        await connection.execute(`UPDATE gf_auth_sessions SET revoked_at = COALESCE(revoked_at, ?)
          WHERE family_id = ?`, [now, membership.family_id]);
        return true;
      });
    },
  };
}
