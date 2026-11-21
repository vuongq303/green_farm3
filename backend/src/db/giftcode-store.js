import { randomUUID } from 'node:crypto';
import { HttpError } from '../http.js';
import { DatabaseUnavailableError } from './store.js';

const CODE_FIELDS = 'id, code, rewards, enabled, starts_at, expires_at, max_redemptions, redeemed_count';
const GRANT_FIELDS = 'grant_id, farm_id, rewards, redeemed_at, applied_at';
const STATUS_SQL = `CASE WHEN enabled = FALSE THEN 'disabled'
  WHEN starts_at > ? THEN 'scheduled'
  WHEN expires_at <= ? THEN 'expired'
  WHEN max_redemptions IS NOT NULL AND redeemed_count >= max_redemptions THEN 'exhausted'
  ELSE 'active' END`;
const ERRORS = {
  disabled: [409, 'giftcode_disabled'],
  scheduled: [409, 'giftcode_not_started'],
  expired: [409, 'giftcode_expired'],
  exhausted: [409, 'giftcode_exhausted'],
};

const date = value => value == null ? null : new Date(Number(value)).toISOString();

function status(row, now) {
  if (!row.enabled) return 'disabled';
  if (row.starts_at != null && Number(row.starts_at) > now) return 'scheduled';
  if (row.expires_at != null && Number(row.expires_at) <= now) return 'expired';
  if (row.max_redemptions != null && row.redeemed_count >= row.max_redemptions) return 'exhausted';
  return 'active';
}

function codeRecord(row, now) {
  return {
    id: String(row.id), code: row.code, rewards: row.rewards, enabled: Boolean(row.enabled),
    starts_at: date(row.starts_at), expires_at: date(row.expires_at), max_redemptions: row.max_redemptions,
    redeemed_count: row.redeemed_count, status: status(row, now),
  };
}

function grant(row) {
  return { grant_id: row.grant_id, rewards: row.rewards, status: row.applied_at == null ? 'pending' : 'applied' };
}

function range(startsAt, expiresAt, max, count) {
  if (startsAt != null && expiresAt != null && Number(startsAt) >= Number(expiresAt)) {
    throw new HttpError(400, 'invalid_date_range');
  }
  if (max != null && max < count) throw new HttpError(409, 'limit_below_redeemed');
}

async function audit(connection, id, operation, details, now) {
  await connection.execute(`INSERT INTO gf_giftcode_audit (giftcode_id, actor, operation, details, created_at)
    VALUES (?, 'operator', ?, ?, ?)`, [id, operation, JSON.stringify(details), now]);
}

async function getCode(executor, id, lock = '') {
  const [[row]] = await executor.execute(`SELECT ${CODE_FIELDS} FROM gf_giftcodes WHERE id = ? ${lock}`, [id]);
  if (!row) throw new HttpError(404, 'giftcode_not_found');
  return row;
}

function paged(rows, total, limit, offset) {
  return { items: rows, total: Number(total), limit, offset };
}

export function createGiftcodeStore(pool) {
  async function read(operation) {
    try { return await operation(); }
    catch (error) { if (error instanceof HttpError) throw error; throw new DatabaseUnavailableError(); }
  }

  async function transaction(operation) {
    return read(async () => {
      const connection = await pool.getConnection();
      try {
        await connection.beginTransaction();
        const result = await operation(connection);
        await connection.commit();
        return result;
      } catch (error) {
        try { await connection.rollback(); } catch { /* connection already failed */ }
        throw error;
      } finally { connection.release(); }
    });
  }

  return {
    async health() {
      return read(async () => {
        const [[version]] = await pool.execute('SELECT version FROM gf_schema_migrations WHERE version = 6');
        if (!version) throw new DatabaseUnavailableError();
        await pool.query('SELECT id, code, rewards FROM gf_giftcodes LIMIT 0');
        await pool.query('SELECT grant_id, farm_id, applied_at FROM gf_giftcode_redemptions LIMIT 0');
        await pool.query('SELECT id, actor, operation FROM gf_giftcode_audit LIMIT 0');
      });
    },

    async redeem({ code, userId, farmId, now }) {
      return transaction(async connection => {
        // Lock the code before checking prior grants and remaining capacity.
        // Admin edits take the same lock, so a grant snapshots one revision.
        const [[giftcode]] = await connection.execute(
          `SELECT ${CODE_FIELDS} FROM gf_giftcodes WHERE code = ? FOR UPDATE`, [code]);
        if (!giftcode) throw new HttpError(404, 'giftcode_not_found');
        const [[existing]] = await connection.execute(
          `SELECT ${GRANT_FIELDS} FROM gf_giftcode_redemptions WHERE giftcode_id = ? AND user_id = ?`, [giftcode.id, userId]);
        if (existing) {
          if (existing.farm_id !== farmId) throw new HttpError(409, 'giftcode_already_redeemed');
          return { created: false, grant: grant(existing) };
        }
        const unavailable = ERRORS[status(giftcode, now)];
        if (unavailable) throw new HttpError(...unavailable);
        const grantId = randomUUID();
        await connection.execute(`INSERT INTO gf_giftcode_redemptions
          (grant_id, giftcode_id, user_id, farm_id, rewards, redeemed_at) VALUES (?, ?, ?, ?, ?, ?)`,
          [grantId, giftcode.id, userId, farmId, JSON.stringify(giftcode.rewards), now]);
        await connection.execute('UPDATE gf_giftcodes SET redeemed_count = redeemed_count + 1 WHERE id = ?', [giftcode.id]);
        return { created: true, grant: grant({ grant_id: grantId, rewards: giftcode.rewards, applied_at: null }) };
      });
    },

    async pending({ userId, farmId }) {
      return read(async () => {
        const [rows] = await pool.execute(`SELECT ${GRANT_FIELDS} FROM gf_giftcode_redemptions
          WHERE user_id = ? AND farm_id = ? AND applied_at IS NULL ORDER BY redeemed_at, grant_id LIMIT 20`, [userId, farmId]);
        return rows.map(grant);
      });
    },

    async ack({ grantId, userId, farmId, now }) {
      return transaction(async connection => {
        const [[row]] = await connection.execute(`SELECT ${GRANT_FIELDS} FROM gf_giftcode_redemptions
          WHERE grant_id = ? AND user_id = ? AND farm_id = ? FOR UPDATE`, [grantId, userId, farmId]);
        if (!row) throw new HttpError(404, 'grant_not_found');
        if (row.applied_at == null) {
          await connection.execute('UPDATE gf_giftcode_redemptions SET applied_at = ? WHERE grant_id = ?', [now, grantId]);
          row.applied_at = now;
        }
        return grant(row);
      });
    },

    async list({ q, state, limit, offset, now }) {
      return transaction(async connection => {
        const primary = 'FROM gf_giftcodes WHERE code LIKE ?';
        const where = state ? primary + ` AND (${STATUS_SQL}) = ?` : primary;
        const args = state ? [q + '%', now, now, state] : [q + '%'];
        const [[{ total }]] = await connection.execute('SELECT COUNT(*) AS total ' + where, args);
        const [rows] = await connection.query(`SELECT ${CODE_FIELDS} ${where} ORDER BY code LIMIT ? OFFSET ?`,
          [...args, limit, offset]);
        return paged(rows.map(row => codeRecord(row, now)), total, limit, offset);
      });
    },

    async detail(id, now) { return read(async () => codeRecord(await getCode(pool, id), now)); },

    async create({ code, rewards, enabled, starts_at, expires_at, max_redemptions }, now) {
      range(starts_at, expires_at, max_redemptions, 0);
      return transaction(async connection => {
        let inserted;
        try {
          [inserted] = await connection.execute(`INSERT INTO gf_giftcodes
            (code, rewards, enabled, starts_at, expires_at, max_redemptions, created_at, updated_at)
            VALUES (?, ?, ?, ?, ?, ?, ?, ?)`,
            [code, JSON.stringify(rewards), enabled, starts_at, expires_at, max_redemptions, now, now]);
        } catch (error) {
          if (error.code === 'ER_DUP_ENTRY') throw new HttpError(409, 'giftcode_exists');
          throw error;
        }
        const result = codeRecord(await getCode(connection, inserted.insertId), now);
        await audit(connection, result.id, 'create', result, now);
        return result;
      });
    },

    async update(id, patch, now) {
      return transaction(async connection => {
        const before = await getCode(connection, id, 'FOR UPDATE');
        const previous = codeRecord(before, now);
        const next = { ...before, ...patch };
        range(next.starts_at, next.expires_at, next.max_redemptions, next.redeemed_count);
        await connection.execute(`UPDATE gf_giftcodes SET rewards = ?, enabled = ?, starts_at = ?, expires_at = ?, max_redemptions = ?, updated_at = ?
          WHERE id = ?`, [JSON.stringify(next.rewards), next.enabled, next.starts_at, next.expires_at, next.max_redemptions, now, id]);
        const result = codeRecord(next, now);
        await audit(connection, id, 'update', { before: previous, after: result }, now);
        return result;
      });
    },

    async history({ id, limit, offset }) {
      return transaction(async connection => {
        await getCode(connection, id);
        const [[{ total }]] = await connection.execute('SELECT COUNT(*) AS total FROM gf_giftcode_redemptions WHERE giftcode_id = ?', [id]);
        const [rows] = await connection.query(`SELECT r.grant_id, u.game_id, r.farm_id, r.rewards, r.redeemed_at, r.applied_at
          FROM gf_giftcode_redemptions r INNER JOIN users u ON u.id = r.user_id WHERE r.giftcode_id = ?
          ORDER BY r.redeemed_at DESC, r.grant_id DESC LIMIT ? OFFSET ?`, [id, limit, offset]);
        return paged(rows.map(row => ({ ...grant(row), game_id: row.game_id, farm_id: row.farm_id,
          redeemed_at: date(row.redeemed_at), applied_at: date(row.applied_at) })), total, limit, offset);
      });
    },
  };
}
