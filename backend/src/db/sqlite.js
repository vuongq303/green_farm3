import { DatabaseSync } from 'node:sqlite';
import { existsSync } from 'node:fs';
import { createHash, randomBytes, randomUUID, scrypt, timingSafeEqual } from 'node:crypto';
import { promisify } from 'node:util';
import { setTimeout as delay } from 'node:timers/promises';
import { sqliteMigrations } from './sqlite-migrate.js';
import { initializationFor } from '../services/new-player-defaults.js';

const derivePassword = promisify(scrypt);
const SCRYPT_OPTIONS = { N: 16384, r: 8, p: 1, maxmem: 64 * 1024 * 1024 };
const MAX_SESSIONS = 10000;
const digest = value => createHash('sha256').update(value, 'utf8').digest();
const problem = code => Object.assign(new Error(code), { code });
const publicErrors = new Set(['INVALID_CREDENTIALS', 'INVALID_SESSION', 'SESSION_CAPACITY', 'REVISION_CONFLICT', 'OPERATION_CONFLICT', 'SESSION_IDENTITY_NOT_FOUND', 'SESSION_IDENTITY_CONFLICT', 'LEGACY_MIGRATION_REQUIRED', 'NEW_PLAYER_DEFAULTS_INVALID', 'ADOPTION_AUTHORIZATION_REQUIRED', 'INITIALIZATION_MISMATCH', 'LOCAL_ADOPTION_APPROVAL_REQUIRED']);

export class DatabaseUnavailableError extends Error {
  constructor() { super('SQLite is unavailable, corrupt, or the required migration is not ready'); this.code = 'DATABASE_UNAVAILABLE'; }
}

function sanitized(error) {
  if (publicErrors.has(error?.code)) return error;
  return new DatabaseUnavailableError();
}

/** Every successful operation is on the configured persistent SQLite file. */
export function createSqliteStore(config) {
  if (!config?.filename || config.filename === ':memory:' || !existsSync(config.filename)) throw new DatabaseUnavailableError();
  let database;
  try {
    database = new DatabaseSync(config.filename);
    database.exec(`PRAGMA foreign_keys=ON; PRAGMA busy_timeout=${config.busyTimeoutMs ?? 100}; PRAGMA synchronous=FULL;`);
    if (database.prepare('PRAGMA journal_mode').get().journal_mode !== 'wal') throw new DatabaseUnavailableError();
    if (database.prepare('PRAGMA quick_check').get().quick_check !== 'ok') throw new DatabaseUnavailableError();
  } catch (error) {
    try { database?.close(); } catch {}
    throw sanitized(error);
  }
  let closed = false;
  const sql = (statement, ...values) => database.prepare(statement).run(...values);
  const row = (statement, ...values) => database.prepare(statement).get(...values);
  function initialization(playerId) {
    const saved = row('SELECT config_hash,config_json FROM gf_player_initializations WHERE player_id=?', playerId);
    if (!saved) return null;
    try {
      const parsed = initializationFor(JSON.parse(saved.config_json));
      if (parsed.configHash !== saved.config_hash) throw new DatabaseUnavailableError();
      return parsed;
    } catch { throw new DatabaseUnavailableError(); }
  }

  async function transaction(operation) {
    for (let attempt = 0; ; attempt++) {
      let begun = false;
      try {
        database.exec('BEGIN IMMEDIATE');
        begun = true;
        const result = operation();
        if (result?.then) throw new Error('SQLite transaction callback must be synchronous');
        database.exec('COMMIT');
        return result;
      } catch (error) {
        if (begun) { try { database.exec('ROLLBACK'); } catch {} }
        const busy = error?.errcode === 5 || error?.errcode === 6 || /SQLITE_(BUSY|LOCKED)/u.test(error?.code ?? '') || /database is locked/u.test(error?.message ?? '');
        if (busy && attempt < 2) { await delay((attempt + 1) * 20); continue; }
        throw sanitized(error);
      }
    }
  }

  return {
    async health() {
      try {
        if (closed) throw new DatabaseUnavailableError();
        for (const migration of sqliteMigrations()) {
          if (row('SELECT checksum FROM gf_schema_migrations WHERE version=?', migration.version)?.checksum !== migration.checksum) throw new DatabaseUnavailableError();
        }
        for (const [table, columns] of [
          ['gf_accounts', 'id,identity_hash,client_id,credential,password_salt,password_hash'],
          ['gf_sessions', 'sid_hash,account_id,device_id,issued_at,expires_at,refresh_expires_at'],
          ['gf_compliance', 'account_id,country,age,updated_at'],
          ['gf_players', 'id,installation_hash,created_at'],
          ['gf_guest_sessions', 'token_hash,player_id,issued_at,expires_at'],
          ['gf_farm_snapshots', 'player_id,revision,operation_id,snapshot_hash,snapshot_blob'],
          ['gf_save_operations', 'player_id,operation_id,committed_revision,snapshot_hash'],
          ['gf_player_initializations', 'player_id,config_hash,config_json,created_at'],
          ['gf_local_adoption_grants', 'token_hash,player_id,operation_id,snapshot_hash,expires_at,used_at,approval_id'],
          ['gf_local_adoption_approvals', 'id,player_id,snapshot_hash,expires_at,used_at'],
          ['gf_player_initialization_receipts', 'player_id,origin,config_hash,snapshot_hash,revision'],
        ]) database.prepare(`SELECT ${columns} FROM ${table} LIMIT 0`).all();
        if (config.requiresLegacyImport) {
          const imported = row("SELECT name FROM sqlite_master WHERE type='table' AND name='gf_legacy_imports'") && row('SELECT COUNT(*) AS count FROM gf_legacy_imports').count > 0;
          const explicitNew = row("SELECT decision FROM gf_storage_decisions WHERE name='legacy-mysql'")?.decision === 'explicit-new-sqlite';
          if (!imported && !explicitNew) throw problem('LEGACY_MIGRATION_REQUIRED');
        }
        return { status: 'ready', schemaVersion: Math.max(...sqliteMigrations().map(value => value.version)) };
      } catch (error) { throw sanitized(error); }
    },

    async createSession({ sid, issuedAt, expiresAt, refreshExpiresAt = expiresAt, credential, clientId, deviceId, password }) {
      const identity = digest(JSON.stringify([clientId, credential]));
      let existing;
      try { existing = row('SELECT * FROM gf_accounts WHERE identity_hash=?', identity); } catch (error) { throw sanitized(error); }
      const salt = existing ? Buffer.from(existing.password_salt) : randomBytes(16);
      const passwordHash = await derivePassword(password, salt, 64, SCRYPT_OPTIONS);
      return transaction(() => {
        sql('DELETE FROM gf_sessions WHERE refresh_expires_at<=?', issuedAt);
        if (row('SELECT COUNT(*) AS count FROM gf_sessions').count >= MAX_SESSIONS) throw problem('SESSION_CAPACITY');
        let account = row('SELECT * FROM gf_accounts WHERE identity_hash=?', identity);
        if (!account) {
          const id = randomUUID();
          sql('INSERT INTO gf_accounts(id,identity_hash,client_id,credential,password_salt,password_hash) VALUES(?,?,?,?,?,?)', id, identity, clientId, credential, salt, passwordHash);
          account = row('SELECT * FROM gf_accounts WHERE id=?', id);
        }
        // Concurrent initial authorization can select a different salt. Retry
        // outside the transaction instead of hashing while holding the lock.
        if (!Buffer.from(account.password_salt).equals(salt)) throw problem('INVALID_CREDENTIALS');
        if (account.client_id !== clientId || account.credential !== credential || !timingSafeEqual(Buffer.from(account.password_hash), passwordHash)) throw problem('INVALID_CREDENTIALS');
        sql('INSERT INTO gf_sessions(sid_hash,account_id,device_id,issued_at,expires_at,refresh_expires_at) VALUES(?,?,?,?,?,?)', digest(sid), account.id, deviceId, issuedAt, expiresAt, refreshExpiresAt);
        return { accountId: account.id };
      }).catch(async error => {
        // The only retryable identity race is a newly inserted, identical user.
        if (!existing && error.code === 'INVALID_CREDENTIALS') {
          const current = row('SELECT * FROM gf_accounts WHERE identity_hash=?', identity);
          if (current) {
            const checked = await derivePassword(password, Buffer.from(current.password_salt), 64, SCRYPT_OPTIONS);
            if (current.client_id === clientId && current.credential === credential && timingSafeEqual(checked, Buffer.from(current.password_hash))) {
              return transaction(() => {
                if (row('SELECT COUNT(*) AS count FROM gf_sessions').count >= MAX_SESSIONS) throw problem('SESSION_CAPACITY');
                sql('INSERT INTO gf_sessions(sid_hash,account_id,device_id,issued_at,expires_at,refresh_expires_at) VALUES(?,?,?,?,?,?)', digest(sid), current.id, deviceId, issuedAt, expiresAt, refreshExpiresAt);
                return { accountId: current.id };
              });
            }
          }
        }
        throw error;
      });
    },

    async findSession(sid) {
      try {
        const value = row(`SELECT s.issued_at,s.expires_at,s.refresh_expires_at,a.id AS account_id,a.credential,a.client_id
          FROM gf_sessions s INNER JOIN gf_accounts a ON a.id=s.account_id WHERE s.sid_hash=?`, digest(sid));
        return value ? { issuedAt: value.issued_at, expiresAt: value.expires_at, refreshExpiresAt: value.refresh_expires_at, accountId: value.account_id, credential: value.credential, clientId: value.client_id } : undefined;
      } catch (error) { throw sanitized(error); }
    },

    async refreshSession({ previousSid, sid, issuedAt, expiresAt, refreshExpiresAt }) {
      return transaction(() => {
        const previous = row(`SELECT s.account_id,s.device_id,s.refresh_expires_at,a.credential,a.client_id FROM gf_sessions s
          INNER JOIN gf_accounts a ON a.id=s.account_id WHERE s.sid_hash=?`, digest(previousSid));
        if (!previous || previous.refresh_expires_at <= issuedAt) throw problem('INVALID_SESSION');
        sql('INSERT INTO gf_sessions(sid_hash,account_id,device_id,issued_at,expires_at,refresh_expires_at) VALUES(?,?,?,?,?,?)', digest(sid), previous.account_id, previous.device_id, issuedAt, expiresAt, refreshExpiresAt);
        sql('DELETE FROM gf_sessions WHERE sid_hash=?', digest(previousSid));
        return { accountId: previous.account_id, credential: previous.credential, clientId: previous.client_id };
      });
    },

    async saveCompliance({ accountId, country, age, updatedAt }) {
      return transaction(() => {
        if (!row('SELECT id FROM gf_accounts WHERE id=?', accountId)) throw problem('INVALID_SESSION');
        sql(`INSERT INTO gf_compliance(account_id,country,age,updated_at) VALUES(?,?,?,?)
          ON CONFLICT(account_id) DO UPDATE SET country=excluded.country,age=excluded.age,updated_at=excluded.updated_at`, accountId, country, age, updatedAt);
        sql('UPDATE gf_accounts SET updated_at=CURRENT_TIMESTAMP WHERE id=?', accountId);
      });
    },

    async createGuestSession({ installationSecret, previousPlayerId, accessToken, issuedAt, expiresAt, initializationFactory }) {
      return transaction(() => {
        const installationHash = digest(installationSecret);
        let player = row('SELECT id FROM gf_players WHERE installation_hash=?', installationHash);
        if (!player && previousPlayerId) throw problem('SESSION_IDENTITY_NOT_FOUND');
        if (player && previousPlayerId && player.id !== previousPlayerId) throw problem('SESSION_IDENTITY_CONFLICT');
        if (!player) {
          if (typeof initializationFactory !== 'function') throw problem('NEW_PLAYER_DEFAULTS_INVALID');
          const initial = initializationFactory();
          const checked = initializationFor(initial.defaults);
          if (checked.configHash !== initial.configHash) throw problem('NEW_PLAYER_DEFAULTS_INVALID');
          player = { id: randomUUID() };
          sql('INSERT INTO gf_players(id,installation_hash,created_at) VALUES(?,?,?)', player.id, installationHash, issuedAt);
          sql('INSERT INTO gf_player_initializations(player_id,config_hash,config_json,created_at) VALUES(?,?,?,?)', player.id, checked.configHash, JSON.stringify(checked.defaults), issuedAt);
        }
        sql('DELETE FROM gf_guest_sessions WHERE expires_at<=?', issuedAt);
        if (row('SELECT COUNT(*) AS count FROM gf_guest_sessions').count >= MAX_SESSIONS) throw problem('SESSION_CAPACITY');
        sql('INSERT INTO gf_guest_sessions(token_hash,player_id,issued_at,expires_at) VALUES(?,?,?,?)', digest(accessToken), player.id, issuedAt, expiresAt);
        return { playerId: player.id, accessToken, expiresAt, initialization: initialization(player.id) };
      });
    },

    async getPlayerInitialization(playerId) {
      try { return initialization(playerId); } catch (error) { throw sanitized(error); }
    },

    async authorizeLocalAdoption({ playerId, operationId, snapshotHash, adoptionToken, now, expiresAt }) {
      return transaction(() => {
        if (row('SELECT revision FROM gf_farm_snapshots WHERE player_id=?', playerId)) throw Object.assign(problem('REVISION_CONFLICT'), { currentRevision: row('SELECT revision FROM gf_farm_snapshots WHERE player_id=?', playerId).revision });
        const approval = row('SELECT id,expires_at FROM gf_local_adoption_approvals WHERE player_id=? AND snapshot_hash=? AND used_at IS NULL AND expires_at>? ORDER BY expires_at DESC LIMIT 1', playerId, snapshotHash, now);
        if (!approval) throw problem('LOCAL_ADOPTION_APPROVAL_REQUIRED');
        sql('DELETE FROM gf_local_adoption_grants WHERE expires_at<=?', now);
        const effectiveExpiry = Math.min(expiresAt, approval.expires_at);
        sql('INSERT INTO gf_local_adoption_grants(token_hash,player_id,operation_id,snapshot_hash,issued_at,expires_at,approval_id) VALUES(?,?,?,?,?,?,?)', digest(adoptionToken), playerId, operationId, snapshotHash, now, effectiveExpiry, approval.id);
        return { adoptionToken, expiresAt: effectiveExpiry };
      });
    },

    /** Called only by the local server-operator CLI, never exposed by HTTP. */
    async approveLocalAdoption({ playerId, snapshotHash, now, expiresAt }) {
      return transaction(() => {
        if (!row('SELECT id FROM gf_players WHERE id=?', playerId)) throw problem('INVALID_SESSION');
        const current = row('SELECT revision FROM gf_farm_snapshots WHERE player_id=?', playerId);
        if (current) throw Object.assign(problem('REVISION_CONFLICT'), { currentRevision: current.revision });
        const approvalId = randomUUID();
        sql('INSERT INTO gf_local_adoption_approvals(id,player_id,snapshot_hash,issued_at,expires_at) VALUES(?,?,?,?,?)', approvalId, playerId, snapshotHash, now, expiresAt);
        return { approvalId, playerId, snapshotHash, expiresAt };
      });
    },

    async findGuestSession(accessToken, now) {
      try {
        const session = row('SELECT player_id,expires_at FROM gf_guest_sessions WHERE token_hash=? AND expires_at>?', digest(accessToken), now);
        return session ? { playerId: session.player_id, expiresAt: session.expires_at } : undefined;
      } catch (error) { throw sanitized(error); }
    },

    async loadSnapshot(playerId) {
      try {
        const saved = row('SELECT revision,operation_id,snapshot_hash,snapshot_blob FROM gf_farm_snapshots WHERE player_id=?', playerId);
        return saved ? { revision: saved.revision, operationId: saved.operation_id, snapshotHash: saved.snapshot_hash, snapshotBase64: Buffer.from(saved.snapshot_blob).toString('base64') } : undefined;
      } catch (error) { throw sanitized(error); }
    },

    async saveSnapshot({ playerId, operationId, expectedRevision, snapshotHash, snapshotBytes, now, adoptionToken, validateFirstSnapshot }) {
      return transaction(() => {
        const currentRevision = row('SELECT revision FROM gf_farm_snapshots WHERE player_id=?', playerId)?.revision ?? 0;
        const receipt = row('SELECT expected_revision,committed_revision,snapshot_hash FROM gf_save_operations WHERE player_id=? AND operation_id=?', playerId, operationId);
        if (receipt) {
          if (receipt.expected_revision !== expectedRevision || receipt.snapshot_hash !== snapshotHash) throw problem('OPERATION_CONFLICT');
          return { revision: receipt.committed_revision, operationId, snapshotHash, currentRevision, replayed: true };
        }
        if (currentRevision !== expectedRevision) throw Object.assign(problem('REVISION_CONFLICT'), { currentRevision });
        const revision = currentRevision + 1;
        if (currentRevision === 0) {
          const initial = initialization(playerId);
          let origin = 'legacy-existing-player';
          if (initial) {
            const grant = adoptionToken ? row(`SELECT g.player_id,g.operation_id,g.snapshot_hash,g.expires_at,g.used_at,g.approval_id,
              a.player_id AS approved_player,a.snapshot_hash AS approved_hash,a.expires_at AS approval_expiry,a.used_at AS approval_used
              FROM gf_local_adoption_grants g INNER JOIN gf_local_adoption_approvals a ON a.id=g.approval_id WHERE g.token_hash=?`, digest(adoptionToken)) : undefined;
            if (grant && grant.player_id === playerId && grant.operation_id === operationId && grant.snapshot_hash === snapshotHash && grant.expires_at > now && grant.used_at === null && grant.approved_player === playerId && grant.approved_hash === snapshotHash && grant.approval_expiry > now && grant.approval_used === null) {
              origin = 'approved-local-copy';
              sql('UPDATE gf_local_adoption_grants SET used_at=? WHERE token_hash=?', now, digest(adoptionToken));
              sql('UPDATE gf_local_adoption_approvals SET used_at=? WHERE id=?', now, grant.approval_id);
            } else {
              if (typeof validateFirstSnapshot !== 'function') throw problem('INITIALIZATION_MISMATCH');
              // This callback only inspects detached archive facts, never does
              // I/O or waits for the mutable game while the DB lock is held.
              validateFirstSnapshot(initial);
              origin = 'configured-new-player';
            }
          }
          sql('INSERT INTO gf_player_initialization_receipts(player_id,origin,config_hash,snapshot_hash,revision,committed_at) VALUES(?,?,?,?,?,?)', playerId, origin, initial?.configHash ?? null, snapshotHash, revision, now);
        }
        sql(`INSERT INTO gf_farm_snapshots(player_id,revision,operation_id,snapshot_hash,snapshot_blob,updated_at) VALUES(?,?,?,?,?,?)
          ON CONFLICT(player_id) DO UPDATE SET revision=excluded.revision,operation_id=excluded.operation_id,snapshot_hash=excluded.snapshot_hash,snapshot_blob=excluded.snapshot_blob,updated_at=excluded.updated_at`, playerId, revision, operationId, snapshotHash, snapshotBytes, now);
        sql('INSERT INTO gf_save_operations(player_id,operation_id,expected_revision,committed_revision,snapshot_hash,committed_at) VALUES(?,?,?,?,?,?)', playerId, operationId, expectedRevision, revision, snapshotHash, now);
        return { revision, operationId, snapshotHash, currentRevision: revision, replayed: false };
      });
    },

    async close() {
      if (!closed) { closed = true; database.close(); }
    },
  };
}
