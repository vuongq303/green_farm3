import { createHash, randomBytes } from 'node:crypto';
import { secureAuthRequest } from '../auth/config.js';
import { HttpError, json, respond } from '../http.js';
import { createGiftcodeAdminAuth, createRateLimit } from './admin-auth.js';
import { normalizeGiftcode, REWARD_CATALOG, validRewards } from './catalog.js';

const ADMIN = '/admin/api/giftcodes';
const UUID = /^[0-9a-f]{8}-[0-9a-f]{4}-4[0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/u;
const CODE_FIELDS = ['rewards', 'starts_at', 'expires_at', 'max_redemptions', 'enabled'];
const STATUS = new Set(['active', 'scheduled', 'expired', 'disabled', 'exhausted']);
const PLAYER_METHODS = {
  '/giftcodes/redeem': 'POST', '/giftcodes/pending': 'GET', '/giftcodes/ack': 'POST',
};

function only(object, keys) {
  if (Object.keys(object).some(key => !keys.includes(key))) throw new HttpError(400, 'invalid_request');
}

function query(url, keys) {
  const fields = Object.fromEntries(url.searchParams);
  only(fields, keys);
  if (Object.keys(fields).length !== url.searchParams.size) throw new HttpError(400, 'invalid_request');
  return fields;
}

function uuid(value, key) {
  if (typeof value !== 'string' || !UUID.test(value)) throw new HttpError(400, 'invalid_' + key);
  return value;
}

function date(value) {
  if (value === null) return null;
  if (typeof value !== 'string' || !/^\d{4}-\d\d-\d\dT\d\d:\d\d:\d\d(?:\.\d{3})?Z$/u.test(value)) {
    throw new HttpError(400, 'invalid_dates');
  }
  const parsed = new Date(value);
  if (!Number.isFinite(parsed.valueOf()) || parsed.valueOf() < 0 ||
      parsed.toISOString() !== (value.length === 20 ? value.slice(0, -1) + '.000Z' : value)) {
    throw new HttpError(400, 'invalid_dates');
  }
  return parsed.valueOf();
}

function codeFields(body, initial) {
  only(body, initial ? [...CODE_FIELDS, 'code'] : CODE_FIELDS);
  if (!initial && !Object.keys(body).length) throw new HttpError(400, 'invalid_request');
  const fields = initial ? { enabled: true, starts_at: null, expires_at: null, max_redemptions: null } : {};
  if (initial) {
    const normalized = body.code == null ? '' : normalizeGiftcode(body.code);
    if (normalized === undefined || (normalized && !/^[A-Z0-9-]{4,48}$/u.test(normalized))) {
      throw new HttpError(400, 'invalid_giftcode');
    }
    fields.code = normalized || randomBytes(10).toString('hex').toUpperCase();
  }
  if (initial || 'rewards' in body) fields.rewards = validRewards(body.rewards);
  if ('enabled' in body) {
    if (typeof body.enabled !== 'boolean') throw new HttpError(400, 'invalid_enabled');
    fields.enabled = body.enabled;
  }
  for (const key of ['starts_at', 'expires_at']) if (key in body) fields[key] = date(body[key]);
  if ('max_redemptions' in body) {
    const value = body.max_redemptions;
    if (value !== null && (!Number.isSafeInteger(value) || value < 1 || value > 1_000_000_000)) {
      throw new HttpError(400, 'invalid_redemption_limit');
    }
    fields.max_redemptions = value;
  }
  return fields;
}

function number(value, fallback, min, max) {
  if (value === undefined) return fallback;
  if (!/^\d{1,9}$/u.test(value) || Number(value) < min || Number(value) > max) {
    throw new HttpError(400, 'invalid_pagination');
  }
  return Number(value);
}

function pagination(fields) {
  return { limit: number(fields.limit, 20, 1, 100), offset: number(fields.offset, 0, 0, 1_000_000) };
}

function method(request, response, ...allowed) {
  if (!allowed.includes(request.method)) {
    response.setHeader('Allow', allowed.join(', '));
    throw new HttpError(405, 'method_not_allowed');
  }
}

function recognize(path) {
  if (Object.hasOwn(PLAYER_METHODS, path)) return path;
  if ([ADMIN, ADMIN + '/catalog', ADMIN + '/login', ADMIN + '/logout'].includes(path)) return path;
  const id = path.slice(ADMIN.length).match(/^\/([1-9]\d{0,19})(\/redemptions)?$/u);
  if (path.startsWith(ADMIN + '/') && id && BigInt(id[1]) <= 18_446_744_073_709_551_615n) {
    return ADMIN + '/:id' + (id[2] ?? '');
  }
  return path.startsWith(ADMIN) ? ADMIN + '/[unknown]' : '/giftcodes/[unknown]';
}

export function createGiftcodeRouter({ auth, giftcodes, store, socialStore, now = Date.now }) {
  const admin = createGiftcodeAdminAuth({ auth, giftcodes, now });
  const redeemLimit = createRateLimit({ now, maximum: 60, windowMs: 60 * 1000 });

  async function userId(request) {
    const match = /^Bearer ([A-Za-z0-9_-]{43})$/u.exec(request.headers.authorization ?? '');
    if (!match) throw new HttpError(401, 'invalid_session');
    const session = await socialStore.findAccess(createHash('sha256').update(match[1], 'utf8').digest(), Math.floor(now() / 1000));
    if (!session) throw new HttpError(401, 'invalid_session');
    return session.user.id;
  }

  async function player(request, response, url, route) {
    method(request, response, PLAYER_METHODS[route]);
    const accountId = await userId(request);
    if (route === '/giftcodes/pending') {
      const fields = query(url, ['farm_id']);
      const grants = await store.pending({ userId: accountId, farmId: uuid(fields.farm_id, 'farm_id') });
      respond(response, 200, { grants });
      return;
    }
    query(url, []);
    const body = await json(request);
    if (route === '/giftcodes/ack') {
      only(body, ['grant_id', 'farm_id']);
      const grant = await store.ack({ userId: accountId, farmId: uuid(body.farm_id, 'farm_id'),
        grantId: uuid(body.grant_id, 'grant_id'), now: now() });
      respond(response, 200, grant);
      return;
    }
    redeemLimit(accountId);
    only(body, ['code', 'farm_id']);
    const code = normalizeGiftcode(body.code);
    if (!code || !/^[A-Z0-9-]{4,48}$/u.test(code)) throw new HttpError(400, 'invalid_giftcode');
    const result = await store.redeem({ code, userId: accountId, farmId: uuid(body.farm_id, 'farm_id'), now: now() });
    respond(response, result.created ? 201 : 200, result.grant);
  }

  async function manage(request, response, url, route) {
    const mutation = ['POST', 'PATCH'].includes(request.method);
    if (mutation) admin.requireOrigin(request);

    if (route === ADMIN + '/login') {
      method(request, response, 'POST');
      query(url, []);
      const body = await json(request);
      only(body, ['password']);
      await admin.login(request, response, body.password);
      respond(response, 200, { ok: true });
      return;
    }
    if (route === ADMIN + '/logout') {
      method(request, response, 'POST');
      query(url, []);
      only(await json(request), []);
      admin.logout(request, response);
      respond(response, 200, { ok: true });
      return;
    }
    admin.requireSession(request);
    if (route === ADMIN + '/catalog') {
      method(request, response, 'GET');
      query(url, []);
      respond(response, 200, { items: REWARD_CATALOG });
    } else if (route === ADMIN) {
      method(request, response, 'GET', 'POST');
      if (mutation) {
        query(url, []);
        respond(response, 201, await store.create(codeFields(await json(request), true), now()));
      } else {
        const fields = query(url, ['q', 'status', 'limit', 'offset']);
        const q = normalizeGiftcode(fields.q ?? '');
        if (q === undefined || q.length > 48 || (fields.status && !STATUS.has(fields.status))) {
          throw new HttpError(400, 'invalid_filter');
        }
        respond(response, 200, await store.list({ q, state: fields.status, ...pagination(fields), now: now() }));
      }
    } else {
      const id = url.pathname.split('/')[4];
      if (route.endsWith('/redemptions')) {
        method(request, response, 'GET');
        const fields = query(url, ['limit', 'offset']);
        respond(response, 200, await store.history({ id, ...pagination(fields) }));
      } else {
        method(request, response, 'GET', 'PATCH');
        query(url, []);
        respond(response, 200, mutation
          ? await store.update(id, codeFields(await json(request), false), now())
          : await store.detail(id, now()));
      }
    }
  }

  return {
    recognize,
    async handle(request, response, url, route) {
      if (route.endsWith('/[unknown]')) throw new HttpError(404, 'route_not_found');
      if (!auth.publicBaseUrl) throw new HttpError(503, 'auth_not_configured');
      if (!secureAuthRequest(request, auth)) throw new HttpError(400, 'https_required');
      if (!store || !socialStore) throw new HttpError(503, 'database_unavailable');
      if (route.startsWith(ADMIN)) await manage(request, response, url, route);
      else await player(request, response, url, route);
    },
  };
}
