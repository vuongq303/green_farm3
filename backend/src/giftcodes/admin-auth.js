import { createHash, randomBytes } from 'node:crypto';
import { authClientIp } from '../auth/config.js';
import { verifyPassword } from '../auth/passwords.js';
import { HttpError } from '../http.js';

const COOKIE = '__Host-gf_giftcode_admin';
const COOKIE_OPTIONS = 'Path=/; Secure; HttpOnly; SameSite=Strict';
const SESSION_TTL = 3600;
const hash = value => createHash('sha256').update(value).digest('base64url');

function sessionToken(request) {
  const raw = request.headers.cookie;
  if (typeof raw !== 'string' || raw.length > 4096) return undefined;
  const entries = raw.split(';').map(part => part.trim()).filter(part => part.startsWith(COOKIE + '='));
  const value = entries.length === 1 ? entries[0].slice(COOKIE.length + 1) : undefined;
  if (value && /^[A-Za-z0-9_-]{43}$/u.test(value)) return value;
}

export function createRateLimit({ windowMs, maximum, now }) {
  const clients = new Map();
  return client => {
    const time = now();
    let bucket = clients.get(client);
    if (!bucket || bucket.expiresAt <= time) {
      if (clients.size >= 2048) {
        for (const [key, entry] of clients) if (entry.expiresAt <= time) clients.delete(key);
        if (clients.size >= 2048) throw new HttpError(429, 'rate_limited');
      }
      bucket = { requests: 0, expiresAt: time + windowMs };
      clients.set(client, bucket);
    }
    if (++bucket.requests > maximum) throw new HttpError(429, 'rate_limited');
  };
}

export function createGiftcodeAdminAuth({ config, adminPasswordHash, now }) {
  const sessions = new Map();
  const loginLimit = createRateLimit({ now, maximum: 8, windowMs: 15 * 60 * 1000 });
  const requireEnabled = () => {
    if (!adminPasswordHash) throw new HttpError(503, 'admin_not_configured');
  };

  return {
    requireOrigin(request) {
      if (request.headers.origin !== config.publicBaseUrl ||
          (request.headers['sec-fetch-site'] != null && request.headers['sec-fetch-site'] !== 'same-origin')) {
        throw new HttpError(403, 'csrf_failed');
      }
    },

    async login(request, response, password) {
      requireEnabled();
      loginLimit(authClientIp(request, config));
      let valid;
      try { valid = await verifyPassword(password, adminPasswordHash); }
      catch { throw new HttpError(503, 'password_unavailable'); }
      if (!valid) throw new HttpError(401, 'invalid_admin_credentials');
      const time = now();
      for (const [key, expiresAt] of sessions) if (expiresAt <= time) sessions.delete(key);
      if (sessions.size >= 128) throw new HttpError(503, 'admin_session_capacity');
      const previous = sessionToken(request);
      if (previous) sessions.delete(hash(previous));
      const token = randomBytes(32).toString('base64url');
      sessions.set(hash(token), time + SESSION_TTL * 1000);
      response.setHeader('Set-Cookie', COOKIE + '=' + token + '; ' + COOKIE_OPTIONS + '; Max-Age=' + SESSION_TTL);
    },

    requireSession(request) {
      requireEnabled();
      const token = sessionToken(request);
      const key = token ? hash(token) : undefined;
      const expiresAt = sessions.get(key);
      if (expiresAt > now()) return;
      sessions.delete(key);
      throw new HttpError(401, 'invalid_admin_session');
    },

    logout(request, response) {
      const token = sessionToken(request);
      if (token) sessions.delete(hash(token));
      response.setHeader('Set-Cookie', COOKIE + '=; ' + COOKIE_OPTIONS + '; Max-Age=0');
    },
  };
}
