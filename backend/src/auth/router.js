import { createHash, randomBytes, timingSafeEqual } from 'node:crypto';
import { APP_REDIRECT_URI, authClientIp, secureAuthRequest } from './config.js';
import { createProviderClient, ProviderError } from './providers.js';
import { ASSETS, PAGE_HEADERS, renderError, renderSuccess, renderPasswordForm } from '../../web/pages.mjs';
import { hashPassword, normalizeIdentifier, PasswordError, registration, verifyPassword } from './passwords.js';

const random = () => randomBytes(32).toString('base64url');
const hash = value => createHash('sha256').update(value, 'utf8').digest();
const TOKEN = /^[A-Za-z0-9_-]{43}$/u;
const STATE = /^[A-Za-z0-9_-]{32,128}$/u;
const VERIFIER = /^[A-Za-z0-9._~-]{43,128}$/u;
const matches = (expression, value) => typeof value === 'string' && expression.test(value);
const PUBLIC_MESSAGES = {
  auth_not_configured: 'Chưa cấu hình địa chỉ HTTPS của máy chủ tài khoản.',
  oauth_not_configured: 'Nhà cung cấp đăng nhập chưa được cấu hình trên máy chủ.',
  https_required: 'Đăng nhập tài khoản yêu cầu kết nối HTTPS.',
  invalid_request: 'Yêu cầu đăng nhập không hợp lệ.',
  attempt_expired: 'Yêu cầu đăng nhập đã hết hạn hoặc đã được sử dụng. Hãy thử lại từ trò chơi.',
  invalid_code: 'Mã xác thực không hợp lệ, hết hạn hoặc đã được sử dụng.',
  invalid_session: 'Phiên đăng nhập đã hết hạn hoặc không hợp lệ.',
  access_denied: 'Bạn đã hủy đăng nhập. Có thể thử lại từ trò chơi.',
  authentication_failed: 'Không thể xác thực với nhà cung cấp. Hãy thử lại từ trò chơi.',
  provider_unavailable: 'Không thể kết nối nhà cung cấp đăng nhập. Hãy thử lại.',
  database_unavailable: 'Máy chủ tài khoản tạm thời không sẵn sàng. Hãy thử lại.',
  rate_limited: 'Có quá nhiều yêu cầu đăng nhập. Hãy chờ rồi thử lại.',
  route_not_found: 'Không tìm thấy API tài khoản.',
  method_not_allowed: 'Phương thức HTTP không được hỗ trợ.',
  json_required: 'Yêu cầu phải sử dụng JSON.',
  request_too_large: 'Yêu cầu vượt quá kích thước cho phép.',
  invalid_credentials: 'Tên tài khoản/email hoặc mật khẩu không đúng.',
  invalid_registration: 'Kiểm tra tên tài khoản, email và mật khẩu (12–128 ký tự); hai mật khẩu phải khớp.',
  account_exists: 'Tên tài khoản hoặc email đã được sử dụng.',
  username_exists: 'Tên tài khoản đã được sử dụng. Hãy chọn tên khác.',
  email_exists: 'Email đã được đăng ký. Hãy dùng email khác hoặc đăng nhập.',
  csrf_failed: 'Phiên biểu mẫu không hợp lệ. Hãy đóng và mở lại đăng nhập từ trò chơi.',
  password_unavailable: 'Máy chủ đang bận xử lý đăng nhập. Vui lòng chờ rồi thử lại.',
};

const WEB_COOKIE = '__Host-gf_auth_web';
const WEB_COOKIE_OPTIONS = 'Path=/; Secure; HttpOnly; SameSite=Strict';
function webCookie(request) {
  const raw = request.headers.cookie;
  if (typeof raw !== 'string' || raw.length > 4096) return undefined;
  const entries = raw.split(';').map(part => part.trim()).filter(part => part.startsWith(WEB_COOKIE + '='));
  if (entries.length !== 1) return undefined;
  const parts = entries[0].slice(WEB_COOKIE.length + 1).split('.');
  return parts.length === 2 && parts.every(part => TOKEN.test(part)) ? { session: parts[0], csrf: parts[1] } : undefined;
}
function sameText(first, second) {
  const a = Buffer.from(first ?? '', 'utf8'), b = Buffer.from(second ?? '', 'utf8');
  return a.length === b.length && timingSafeEqual(a, b);
}

class AuthError extends Error {
  constructor(status, code) { super(code); this.status = status; this.code = code; }
}

function fail(status, code) { throw new AuthError(status, code); }
function only(object, keys) {
  if (!object || typeof object !== 'object' || Array.isArray(object) ||
    Object.keys(object).some(key => !keys.includes(key))) fail(400, 'invalid_request');
}
function token(value) { if (typeof value !== 'string' || !TOKEN.test(value)) fail(401, 'invalid_session'); return value; }
function publicUser(user) { return { game_id: user.game_id, provider: user.provider, display_name: user.display_name ?? null }; }

function jsonReply(response, status, value) {
  const body = value === undefined ? '' : JSON.stringify(value);
  response.writeHead(status, {
    'Content-Type': 'application/json; charset=utf-8', 'Content-Length': Buffer.byteLength(body),
    'Cache-Control': 'no-store', Pragma: 'no-cache', 'X-Content-Type-Options': 'nosniff', 'Referrer-Policy': 'no-referrer'
  });
  response.end(body);
}
function pageReply(response, status, page) {
  response.writeHead(status, { ...PAGE_HEADERS, 'Content-Security-Policy': page.csp, 'Content-Length': Buffer.byteLength(page.html) });
  response.end(page.html);
}

async function readJson(request) {
  if (request.headers['content-type']?.split(';', 1)[0]?.trim().toLowerCase() !== 'application/json') {
    request.resume(); fail(415, 'json_required');
  }
  if (Number(request.headers['content-length']) > 8192) { request.resume(); fail(413, 'request_too_large'); }
  let size = 0;
  const chunks = [];
  try {
    for await (const chunk of request) {
      size += chunk.length;
      if (size > 8192) fail(413, 'request_too_large');
      chunks.push(chunk);
    }
  } catch (error) { if (error instanceof AuthError) throw error; fail(400, 'invalid_request'); }
  let result;
  try { result = JSON.parse(new TextDecoder('utf-8', { fatal: true }).decode(Buffer.concat(chunks))); }
  catch { fail(400, 'invalid_request'); }
  if (!result || typeof result !== 'object' || Array.isArray(result)) fail(400, 'invalid_request');
  return result;
}

function bearer(request) {
  const value = request.headers.authorization;
  if (typeof value !== 'string' || !/^Bearer [A-Za-z0-9_-]{43}$/u.test(value)) fail(401, 'invalid_session');
  return value.slice(7);
}

/** Bounded per-client rate buckets. Forwarded addresses are accepted only from
 * the single configured proxy, which must overwrite the incoming header. */
function limiter(now) {
  const buckets = new Map();
  return (client, group, max) => {
    const time = now();
    const key = group + ':' + client;
    let bucket = buckets.get(key);
    if (!bucket || time >= bucket.until) {
      if (buckets.size >= 10000) {
        for (const [id, entry] of buckets) if (time >= entry.until) buckets.delete(id);
        if (buckets.size >= 10000) fail(429, 'rate_limited');
      }
      bucket = { count: 0, until: time + 900000 }; buckets.set(key, bucket);
    }
    if (++bucket.count > max) fail(429, 'rate_limited');
  };
}

export function createSocialAuthRouter({ config, store, providers = createProviderClient(), now = Date.now,
  isSecureRequest = secureAuthRequest, errorLog = () => { } } = {}) {
  const rate = limiter(now);
  const seconds = () => Math.floor(now() / 1000);

  function configured(provider) {
    if (!config?.publicBaseUrl) fail(503, 'auth_not_configured');
    if (!store) fail(503, 'database_unavailable');
    // Password registration/login and shared sessions never require an OAuth
    // provider. Only a request explicitly choosing Facebook/Google checks it.
    if (provider && provider !== 'password' && !config.providers?.[provider]?.enabled) fail(503, 'oauth_not_configured');
  }
  function callback(attempt, values) {
    const url = new URL(APP_REDIRECT_URI);
    for (const [key, value] of Object.entries(values)) url.searchParams.set(key, value);
    url.searchParams.set('state', attempt.appState);
    return url.toString();
  }
  function newSession() {
    const access = random(), refresh = random();
    const time = seconds();
    return {
      access, refresh, record: {
        id: random(), accessHash: hash(access), refreshHash: hash(refresh), issuedAt: time,
        accessExpiresAt: time + config.accessTtl, refreshExpiresAt: time + config.refreshTtl
      }
    };
  }
  function sessionReply(session, user) {
    return {
      access_token: session.access, refresh_token: session.refresh, token_type: 'Bearer',
      expires_in: config.accessTtl, refresh_expires_in: config.refreshTtl, user: publicUser(user)
    };
  }

  async function passwordWebSession(request, attemptId, csrf) {
    const cookie = webCookie(request);
    if (!cookie || !matches(TOKEN, attemptId) || !matches(TOKEN, csrf) ||
      !sameText(cookie.csrf, csrf)) fail(403, 'csrf_failed');
    const web = await store.getPasswordWebSession(hash(cookie.session), seconds());
    if (!web || web.attempt.id !== attemptId) fail(400, 'attempt_expired');
    if (!timingSafeEqual(hash(csrf), web.csrfHash)) fail(403, 'csrf_failed');
    return { ...web, cookieHash: hash(cookie.session), csrfHash: hash(csrf) };
  }

  async function passwordReply(response, attempt, user, status) {
    const code = random();
    const issued = await store.issueCode({
      attemptId: attempt.id, userId: user.id, codeHash: hash(code),
      expiresAt: seconds() + config.codeTtl, now: seconds()
    });
    if (!issued) fail(400, 'attempt_expired');
    response.setHeader('Set-Cookie', WEB_COOKIE + '=; ' + WEB_COOKIE_OPTIONS + '; Max-Age=0');
    jsonReply(response, status, { callback_url: callback(attempt, { code }) });
  }

  async function handle(request, response, url) {
    const path = url.pathname;
    if (path !== '/auth' && !path.startsWith('/auth/')) return false;
    let browserAttempt;
    const browser = path.startsWith('/auth/browser/') || path.startsWith('/auth/callback/') || path.startsWith('/auth/web/');
    try {
      if (path.startsWith('/auth/assets/')) {
        if (request.method !== 'GET') fail(405, 'method_not_allowed');
        const asset = ASSETS.get(path.slice('/auth/assets/'.length));
        if (!asset) fail(404, 'route_not_found');
        response.writeHead(200, {
          'Content-Type': asset.contentType, 'Content-Length': asset.body.length,
          'Cache-Control': 'public, max-age=3600', 'X-Content-Type-Options': 'nosniff'
        });
        response.end(asset.body); return true;
      }
      configured();
       if (!isSecureRequest(request, config)) fail(400, 'https_required');
      const client = authClientIp(request, config);
      if (path === '/auth/attempt' || path === '/auth/facebook' || path === '/auth/google') {
        if (request.method !== 'POST') fail(405, 'method_not_allowed');
        rate(client, 'attempt', 20);
        const body = await readJson(request);
        only(body, ['provider', 'state', 'code_challenge', 'redirect_uri']);
        const provider = path === '/auth/attempt' ? body.provider : path.slice('/auth/'.length);
        if (!['facebook', 'google', 'password'].includes(provider) || (body.provider !== undefined && body.provider !== provider) ||
          !matches(STATE, body.state) || !matches(TOKEN, body.code_challenge) || body.redirect_uri !== APP_REDIRECT_URI) fail(400, 'invalid_request');
        configured(provider);
        const id = random();
        await store.createAttempt({
          id, provider, appState: body.state, codeChallenge: body.code_challenge,
          redirectUri: APP_REDIRECT_URI, createdAt: seconds(), expiresAt: seconds() + config.attemptTtl
        });
        jsonReply(response, 201, {
          attempt_id: id, authorization_url: config.publicBaseUrl +
            (provider === 'password' ? '/auth/web/login?attempt=' : '/auth/browser/start?id=') + id,
          expires_in: config.attemptTtl
        });
      } else if (path === '/auth/web/login' || path === '/auth/web/register') {
        if (request.method !== 'GET') fail(405, 'method_not_allowed');
        rate(client, 'web-form', 60);
        const id = url.searchParams.get('attempt');
        if (!matches(TOKEN, id) || url.searchParams.getAll('attempt').length !== 1 ||
          [...url.searchParams.keys()].some(key => key !== 'attempt')) fail(400, 'invalid_request');
        const known = await store.getAttempt(id);
        if (!known || known.provider !== 'password' || known.expiresAt <= seconds() || known.consumedAt != null) fail(400, 'attempt_expired');
        let cookie = webCookie(request), attempt;
        const web = cookie ? await store.getPasswordWebSession(hash(cookie.session), seconds()) : undefined;
        if (web?.attempt.id === id && timingSafeEqual(hash(cookie.csrf), web.csrfHash)) {
          attempt = web.attempt;
        } else {
          cookie = { session: random(), csrf: random() };
          attempt = await store.startPasswordAttempt(id, hash(cookie.session), hash(cookie.csrf), seconds());
          if (!attempt) fail(400, 'attempt_expired');
          response.setHeader('Set-Cookie', WEB_COOKIE + '=' + cookie.session + '.' + cookie.csrf + '; ' +
            WEB_COOKIE_OPTIONS + '; Max-Age=' + Math.max(1, Math.min(config.attemptTtl, attempt.expiresAt - seconds())));
        }
        pageReply(response, 200, renderPasswordForm({
          mode: path.endsWith('/register') ? 'register' : 'login',
          csrfToken: cookie.csrf, attemptId: id, closeUrl: callback(attempt, { error: 'access_denied' })
        }));
      } else if (path === '/auth/login' || path === '/auth/register') {
        if (request.method !== 'POST') fail(405, 'method_not_allowed');
        if (request.headers.origin !== config.publicBaseUrl ||
          (request.headers['sec-fetch-site'] && request.headers['sec-fetch-site'] !== 'same-origin')) fail(403, 'csrf_failed');
        rate(client, 'password', 30);
        const body = await readJson(request);
        const registering = path === '/auth/register';
        only(body, registering ? ['csrf', 'attempt', 'username', 'email', 'password', 'password_confirmation'] :
          ['csrf', 'attempt', 'login', 'password']);
        const web = await passwordWebSession(request, body.attempt, body.csrf);
        if (registering) {
          const fields = registration(body);
          rate(hash(fields.username).toString('hex'), 'register-identity', 6);
          rate(hash(fields.email).toString('hex'), 'register-email', 6);
          const passwordHash = await hashPassword(fields.password);
          const registered = await store.registerPasswordAndClaim({
            cookieHash: web.cookieHash, csrfHash: web.csrfHash,
            providerUserId: random(), username: fields.username, email: fields.email, passwordHash, now: seconds()
          });
          if (!registered) fail(400, 'attempt_expired');
          if (registered.duplicate) {
            fail(409, registered.field === 'username' ? 'username_exists' :
              registered.field === 'email' ? 'email_exists' : 'account_exists');
          }
          await passwordReply(response, registered.attempt, registered.user, 201);
        } else {
          const identifier = normalizeIdentifier(body.login);
          if (!identifier || typeof body.password !== 'string' || Buffer.byteLength(body.password, 'utf8') > 512) fail(401, 'invalid_credentials');
          rate(hash(identifier).toString('hex'), 'password-identity', 10);
          const credential = await store.findPasswordCredential(identifier);
          const verified = await verifyPassword(body.password, credential?.passwordHash);
          if (!verified || !credential || credential.accountStatus !== 'active') fail(401, 'invalid_credentials');
          const attempt = await store.claimPasswordAttempt(web.cookieHash, web.csrfHash, seconds());
          if (!attempt) fail(400, 'attempt_expired');
          const user = await store.loginPasswordUser(credential.userId, seconds());
          if (!user) fail(401, 'invalid_credentials');
          await passwordReply(response, attempt, user, 200);
        }
      } else if (path === '/auth/browser/start') {
        if (request.method !== 'GET') fail(405, 'method_not_allowed');
        rate(client, 'browser', 40);
        const id = url.searchParams.get('id');
        if (!TOKEN.test(id ?? '') || url.searchParams.getAll('id').length !== 1) fail(400, 'invalid_request');
        const known = await store.getAttempt(id);
        if (!known || !['facebook', 'google'].includes(known.provider)) fail(400, 'attempt_expired');
        configured(known.provider);
        const oauthState = random();
        const attempt = await store.startAttempt(id, { oauthStateHash: hash(oauthState), nonce: random(), providerVerifier: random() }, seconds());
        if (!attempt) fail(400, 'attempt_expired');
        response.writeHead(302, {
          Location: providers.authorizationUrl(attempt.provider, attempt, oauthState, config),
          'Cache-Control': 'no-store', 'Referrer-Policy': 'no-referrer'
        });
        response.end();
      } else if (path === '/auth/callback/google' || path === '/auth/callback/facebook') {
        if (request.method !== 'GET') fail(405, 'method_not_allowed');
        rate(client, 'callback', 60);
        const provider = path.slice('/auth/callback/'.length);
        configured(provider);
        const state = url.searchParams.get('state');
        if (!TOKEN.test(state ?? '') || url.searchParams.getAll('state').length !== 1 ||
          url.searchParams.getAll('code').length > 1 || url.searchParams.getAll('error').length > 1) fail(400, 'invalid_request');
        browserAttempt = await store.claimOAuthAttempt(hash(state), provider, seconds());
        if (!browserAttempt) fail(400, 'attempt_expired');
        if (url.searchParams.has('error')) fail(400, 'access_denied');
        const providerCode = url.searchParams.get('code');
        if (typeof providerCode !== 'string' || !providerCode || providerCode.length > 4096 || /[\u0000-\u001f\u007f]/u.test(providerCode)) fail(400, 'invalid_request');
        const identity = await providers.verifyIdentity(provider, providerCode, browserAttempt, config);
        if (!identity || typeof identity.providerUserId !== 'string' || !identity.providerUserId || identity.providerUserId.length > 255) fail(400, 'authentication_failed');
        const user = await store.upsertUser(provider, identity.providerUserId, identity.displayName, seconds());
        const code = random();
        const issued = await store.issueCode({
          attemptId: browserAttempt.id, userId: user.id, codeHash: hash(code),
          expiresAt: seconds() + config.codeTtl, now: seconds()
        });
        if (!issued) fail(400, 'attempt_expired');
        pageReply(response, 200, renderSuccess(callback(browserAttempt, { code })));
      } else if (path === '/auth/exchange') {
        if (request.method !== 'POST') fail(405, 'method_not_allowed');
        rate(client, 'exchange', 40);
        const body = await readJson(request);
        only(body, ['code', 'code_verifier', 'redirect_uri']);
        if (!matches(TOKEN, body.code) || !matches(VERIFIER, body.code_verifier) || body.redirect_uri !== APP_REDIRECT_URI) fail(400, 'invalid_code');
        const session = newSession();
        const user = await store.exchangeCode({
          codeHash: hash(body.code), codeChallenge: hash(body.code_verifier).toString('base64url'),
          redirectUri: APP_REDIRECT_URI, now: seconds(), session: session.record
        });
        if (!user) fail(400, 'invalid_code');
        jsonReply(response, 200, sessionReply(session, user));
      } else if (path === '/auth/me') {
        if (request.method !== 'GET') fail(405, 'method_not_allowed');
        rate(client, 'session', 120);
        const session = await store.findAccess(hash(bearer(request)), seconds());
        if (!session) fail(401, 'invalid_session');
        jsonReply(response, 200, { user: publicUser(session.user) });
      } else if (path === '/auth/refresh') {
        if (request.method !== 'POST') fail(405, 'method_not_allowed');
        rate(client, 'refresh', 60);
        const body = await readJson(request);
        only(body, ['refresh_token']);
        const refresh = token(body.refresh_token), session = newSession();
        const user = await store.rotateRefresh(hash(refresh), session.record, seconds());
        if (!user) fail(401, 'invalid_session');
        jsonReply(response, 200, sessionReply(session, user));
      } else if (path === '/auth/logout') {
        if (request.method !== 'POST') fail(405, 'method_not_allowed');
        rate(client, 'session', 120);
        const access = bearer(request);
        const body = await readJson(request);
        only(body, ['refresh_token']);
        const refresh = body.refresh_token === undefined ? undefined : token(body.refresh_token);
        await store.revoke(hash(access), refresh && hash(refresh), seconds());
        // Idempotent even after expiry/revocation. Never reveal another identity.
        jsonReply(response, 204);
      } else fail(404, 'route_not_found');
    } catch (error) {
      request.resume();
      if (response.headersSent || response.destroyed) return true;
      let failure = error instanceof AuthError ? error :
        error instanceof ProviderError ? new AuthError(502, error.code === 'provider_identity_invalid' ? 'authentication_failed' : 'provider_unavailable') :
          error instanceof PasswordError ? new AuthError(error.code === 'password_unavailable' ? 503 : 400, error.code) :
            error?.code === 'DATABASE_UNAVAILABLE' ? new AuthError(503, 'database_unavailable') : new AuthError(500, 'authentication_failed');
      if (failure.status >= 500) {
        try { errorLog({ event: 'social_auth_error', status: failure.status, code: failure.code }); } catch { }
      }
      if (failure.status === 429) response.setHeader('Retry-After', '900');
      if (browser) {
        const appError = failure.code === 'access_denied' ? 'access_denied' :
          failure.code === 'attempt_expired' ? 'attempt_expired' : 'authentication_failed';
        pageReply(response, failure.status, renderError({
          message: PUBLIC_MESSAGES[failure.code] ?? PUBLIC_MESSAGES.authentication_failed,
          returnUrl: browserAttempt ? callback(browserAttempt, { error: appError }) : undefined
        }));
      } else jsonReply(response, failure.status, { error: { code: failure.code, message: PUBLIC_MESSAGES[failure.code] ?? PUBLIC_MESSAGES.authentication_failed } });
    }
    return true;
  }

  return { handle };
}
