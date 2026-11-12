import { createHmac, randomBytes, timingSafeEqual } from 'node:crypto';
import { HttpError } from '../http.js';

// Evidence: original a.aR operates on the first 16 Java UTF-16 code units.
export function legacyPassword(credential) {
  let password = '';
  for (let index = 0; index < Math.min(16, credential.length); index++) {
    password += String.fromCharCode(credential.charCodeAt(index) % 25 + 97);
  }
  return password;
}

function equalText(left, right) {
  const a = Buffer.from(left, 'utf8');
  const b = Buffer.from(right, 'utf8');
  return a.length === b.length && timingSafeEqual(a, b);
}

export function createSessionService({ store, secret: suppliedSecret, ttl, refreshTtl = 2592000, now }) {
  if (!Number.isInteger(ttl) || ttl < 1 || ttl > 86400) throw new Error('sessionTtlSeconds must be an integer from 1 to 86400');
  if (!Number.isInteger(refreshTtl) || refreshTtl < ttl || refreshTtl > 7776000) throw new Error('refreshTtlSeconds must be between sessionTtlSeconds and 7776000');
  if (suppliedSecret === undefined) throw new Error('A persistent sessionSecret is required');
  const secret = Buffer.from(suppliedSecret);
  if (secret.length < 32) throw new Error('sessionSecret must contain at least 32 bytes');
  const signature = value => createHmac('sha256', secret).update(value).digest();
  const seconds = () => Math.floor(now() / 1000);

  function signToken(version, sid, issuedAt, expiresAt) {
    const payload = { sid, iat: issuedAt, exp: expiresAt };
    const signed = version + '.' + Buffer.from(JSON.stringify(payload)).toString('base64url');
    return signed + '.' + signature(signed).toString('base64url');
  }
  function tokens(sid, issuedAt, expiresAt, refreshExpiresAt) {
    return {
      access_token: signToken('v1', sid, issuedAt, expiresAt),
      refresh_token: signToken('r1', sid, issuedAt, refreshExpiresAt),
    };
  }
  function decodeToken(token, version) {
    if (!token || token.length > 2048) return undefined;
    const parts = token.split('.');
    if (parts.length !== 3 || parts[0] !== version || !/^[A-Za-z0-9_-]+$/u.test(parts[1]) || !/^[A-Za-z0-9_-]+$/u.test(parts[2])) return undefined;
    const actual = Buffer.from(parts[2], 'base64url');
    const expected = signature(parts[0] + '.' + parts[1]);
    if (actual.toString('base64url') !== parts[2] || actual.length !== expected.length || !timingSafeEqual(actual, expected)) return undefined;
    let payload;
    try { payload = JSON.parse(Buffer.from(parts[1], 'base64url').toString('utf8')); } catch { return undefined; }
    if (!payload || typeof payload !== 'object' || !/^[a-f0-9]{48}$/u.test(payload.sid) || !Number.isSafeInteger(payload.iat) || !Number.isSafeInteger(payload.exp)) return undefined;
    const current = seconds();
    if (payload.exp <= current || current < payload.iat) return undefined;
    return payload;
  }

  return {
    async authorize({ clientId, username, password, deviceId }) {
      const credential = username.startsWith('anonymous:') ? username.slice('anonymous:'.length) : '';
      if (!credential || credential.length > 1024 || !equalText(password, legacyPassword(credential))) {
        throw new HttpError(401, 'invalid_credentials');
      }
      const issuedAt = seconds();
      const sid = randomBytes(24).toString('hex');
      const expiresAt = issuedAt + ttl;
      const refreshExpiresAt = issuedAt + refreshTtl;
      await store.createSession({ sid, issuedAt, expiresAt, refreshExpiresAt, credential: username, clientId, deviceId, password });
      return tokens(sid, issuedAt, expiresAt, refreshExpiresAt);
    },
    async refresh({ clientId, refreshToken }) {
      const payload = decodeToken(refreshToken, 'r1');
      if (!payload) throw new HttpError(401, 'invalid_session');
      const previous = await store.findSession(payload.sid);
      if (!previous || previous.clientId !== clientId || previous.issuedAt !== payload.iat || previous.refreshExpiresAt !== payload.exp) {
        throw new HttpError(401, 'invalid_session');
      }
      const issuedAt = seconds();
      const sid = randomBytes(24).toString('hex');
      const expiresAt = issuedAt + ttl;
      const refreshExpiresAt = issuedAt + refreshTtl;
      await store.refreshSession({ previousSid: payload.sid, sid, issuedAt, expiresAt, refreshExpiresAt });
      return tokens(sid, issuedAt, expiresAt, refreshExpiresAt);
    },
    async forToken(token) {
      const payload = decodeToken(token, 'v1');
      if (!payload) return undefined;
      const session = await store.findSession(payload.sid);
      if (!session || session.issuedAt !== payload.iat || session.expiresAt !== payload.exp) return undefined;
      return session;
    },
  };
}
