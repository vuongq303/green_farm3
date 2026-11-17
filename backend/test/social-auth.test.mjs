import assert from 'node:assert/strict';
import { createServer } from 'node:http';
import { createHash, generateKeyPairSync, randomBytes, sign } from 'node:crypto';
import { test } from 'node:test';
import { APP_REDIRECT_URI, loadSocialAuthConfig, secureAuthRequest, authClientIp } from '../src/auth/config.js';
import { createSocialAuthRouter } from '../src/auth/router.js';
import { createProviderClient } from '../src/auth/providers.js';

const value = () => randomBytes(32).toString('base64url');
const digest = text => createHash('sha256').update(text).digest();
const challenge = verifier => digest(verifier).toString('base64url');
const configured = () => loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: 'https://accounts.example.test',
  GOOGLE_OAUTH_CLIENT_ID: 'test-web-client.apps.googleusercontent.com', GOOGLE_OAUTH_CLIENT_SECRET: 'test-google-secret',
  FACEBOOK_APP_ID: '123456789', FACEBOOK_APP_SECRET: 'test-facebook-secret', AUTH_TRUSTED_PROXY_IP: '127.0.0.1' });

test('social config requires HTTPS and isolates incomplete provider configuration without leaking values', () => {
  assert.equal(loadSocialAuthConfig({}).publicBaseUrl, null);
  for (const origin of ['http://accounts.example.test', 'https://user:secret@accounts.example.test',
    'https://accounts.example.test/path', 'https://accounts.example.test/?token=secret']) {
    assert.throws(() => loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: origin }), /AUTH_PUBLIC_BASE_URL/u);
  }
  const incomplete = loadSocialAuthConfig({ GOOGLE_OAUTH_CLIENT_SECRET: 'private-test-value' });
  assert.equal(incomplete.providers.google.enabled, false);
  assert.equal(incomplete.providers.google.configurationError, 'oauth_not_configured');
  assert.ok(!JSON.stringify(incomplete).includes('private-test-value'));
  const config = configured();
  assert.equal(config.providers.google.enabled, true);
  assert.equal(config.providers.facebook.enabled, true);
  const request = { socket: { remoteAddress: '192.168.1.2' }, headers: { 'x-forwarded-proto': 'https', 'x-forwarded-for': '1.2.3.4' } };
  assert.equal(secureAuthRequest(request, config), false, 'untrusted clients cannot assert TLS');
  assert.equal(authClientIp(request, config), '192.168.1.2');
  request.socket.remoteAddress = '::ffff:127.0.0.1';
  assert.equal(secureAuthRequest(request, config), true);
  assert.equal(authClientIp(request, config), '1.2.3.4');
  request.socket.remoteAddress = '::1';
  assert.equal(secureAuthRequest(request, config), true, 'local tunnel may use IPv6 loopback');
  request.headers['x-forwarded-proto'] = 'http';
  assert.equal(secureAuthRequest(request, config), false, 'trusted proxy still must attest HTTPS');
  request.headers['x-forwarded-proto'] = 'https';
  delete request.headers['x-forwarded-proto'];
  request.headers['cf-visitor'] = '{"scheme":"https"}';
  assert.equal(secureAuthRequest(request, config), true, 'trusted Cloudflare visitor scheme is accepted when XFP is missing');
  request.headers['cf-visitor'] = '{"scheme":"http"}';
  assert.equal(secureAuthRequest(request, config), false, 'HTTP must not be accepted');
  request.headers['cf-visitor'] = '{"scheme":"https"}';
  request.headers['x-forwarded-proto'] = 'http';
  assert.equal(secureAuthRequest(request, config), false, 'a conflicting forwarded protocol must be rejected');
  request.headers['x-forwarded-proto'] = 'https';
  request.socket.remoteAddress = '192.168.1.2';
  assert.equal(secureAuthRequest(request, config), false, 'untrusted clients cannot forge Cloudflare scheme');
  request.socket.remoteAddress = '::ffff:127.0.0.1';
  delete request.headers['cf-visitor'];
  request.headers['x-forwarded-for'] = '1.2.3.4, 9.9.9.9';
  assert.equal(authClientIp(request, config), '127.0.0.1', 'proxy must overwrite instead of accepting a forwarded chain');
});

const { privateKey, publicKey } = generateKeyPairSync('rsa', { modulusLength: 2048 });
const jwk = { ...publicKey.export({ format: 'jwk' }), kid: 'test-key', alg: 'RS256', use: 'sig' };
const seconds = 1800000000;
function jwt(claims, header = { alg: 'RS256', kid: 'test-key' }) {
  const a = Buffer.from(JSON.stringify(header)).toString('base64url');
  const b = Buffer.from(JSON.stringify(claims)).toString('base64url');
  return a + '.' + b + '.' + sign('RSA-SHA256', Buffer.from(a + '.' + b), privateKey).toString('base64url');
}

test('Google provider verifies signed ID token, issuer, audience, expiry, nonce and fixed exchange destination', async () => {
  const config = configured(), attempt = { nonce: value(), providerVerifier: value() };
  const claims = { iss: 'https://accounts.google.com', aud: config.providers.google.clientId,
    sub: 'google-provider-subject', nonce: attempt.nonce, iat: seconds, exp: seconds + 300, name: 'Người chơi' };
  let token = jwt(claims);
  const calls = [];
  const provider = createProviderClient({ now: () => seconds * 1000, fetchImpl: async (url, options) => {
    calls.push({ url: String(url), options });
    return new Response(JSON.stringify(String(url).includes('/certs') ? { keys: [jwk] } : { id_token: token }), { status: 200 });
  } });
  assert.deepEqual(await provider.verifyIdentity('google', 'provider-code', attempt, config),
    { providerUserId: claims.sub, displayName: 'Người chơi' });
  const body = new URLSearchParams(calls[0].options.body);
  assert.equal(calls[0].url, 'https://oauth2.googleapis.com/token');
  assert.equal(calls[0].options.redirect, 'error');
  assert.equal(body.get('code_verifier'), attempt.providerVerifier);
  assert.equal(body.get('redirect_uri'), 'https://accounts.example.test/auth/callback/google');
  for (const changed of [{ iss: 'https://attacker.invalid' }, { aud: 'another-client' }, { nonce: value() },
    { exp: seconds }, { iat: seconds + 120 }, { sub: '' }, { azp: 'other-client' }]) {
    token = jwt({ ...claims, ...changed });
    await assert.rejects(provider.verifyIdentity('google', 'provider-code', attempt, config), { code: 'provider_identity_invalid' });
  }
  const signed = jwt(claims).split('.');
  signed[2] = (signed[2][0] === 'A' ? 'B' : 'A') + signed[2].slice(1);
  token = signed.join('.');
  await assert.rejects(provider.verifyIdentity('google', 'provider-code', attempt, config));
  token = jwt(claims, null);
  await assert.rejects(provider.verifyIdentity('google', 'provider-code', attempt, config), { code: 'provider_identity_invalid' });
});

test('Facebook verifies debug token application/user/expiry and independently checked me identity', async () => {
  const config = configured();
  let data = { is_valid: true, app_id: config.providers.facebook.clientId, user_id: 'facebook-subject',
    expires_at: seconds + 300, data_access_expires_at: seconds + 300 };
  let me = { id: data.user_id, name: 'Farmer' };
  const calls = [];
  const provider = createProviderClient({ now: () => seconds * 1000, fetchImpl: async (url, options) => {
    calls.push({ url: String(url), options });
    const text = String(url);
    return new Response(JSON.stringify(text.includes('/debug_token') ? { data } : text.includes('/me?') ? me :
      { access_token: 'provider-token-only-in-server-memory' }), { status: 200 });
  } });
  assert.deepEqual(await provider.verifyIdentity('facebook', 'provider-code', {}, config), { providerUserId: data.user_id, displayName: 'Farmer' });
  assert.ok(calls.every(call => call.url.startsWith('https://graph.facebook.com/v24.0/')));
  assert.ok(calls[1].options.headers.Authorization.startsWith('Bearer ' + config.providers.facebook.clientId + '|'));
  for (const changed of [{ is_valid: false }, { app_id: 'other-app' }, { expires_at: seconds },
    { data_access_expires_at: 'not-a-number' }]) {
    const before = data; data = { ...data, ...changed };
    await assert.rejects(provider.verifyIdentity('facebook', 'provider-code', {}, config), { code: 'provider_identity_invalid' });
    data = before;
  }
  me = { id: 'other-provider-subject' };
  await assert.rejects(provider.verifyIdentity('facebook', 'provider-code', {}, config), { code: 'provider_identity_invalid' });
});

/** Test-only recorders. Runtime always uses the separately tested MySQL store. */
function storeDouble() {
  const attempts = new Map(), codes = new Map(), sessions = new Map(), users = new Map();
  let nextId = 0;
  return {
    attempts, codes, sessions,
    async createAttempt(record) { attempts.set(record.id, { ...record }); },
    async getAttempt(id) { return attempts.get(id); },
    async startAttempt(id, state, now) {
      const row = attempts.get(id);
      if (!row || row.startedAt || row.expiresAt <= now) return;
      Object.assign(row, state, { startedAt: now }); return row;
    },
    async claimOAuthAttempt(state, provider, now) {
      const row = [...attempts.values()].find(row => row.oauthStateHash?.equals(state) && row.provider === provider);
      if (!row || row.consumedAt || row.expiresAt <= now) return;
      row.consumedAt = now; return row;
    },
    async upsertUser(provider, subject, name) {
      const key = provider + ':' + subject;
      if (!users.has(key)) users.set(key, { id: String(++nextId), game_id: 'GF' + String(nextId).padStart(6, '0'), provider, display_name: name });
      return users.get(key);
    },
    async issueCode(record) { const attempt = attempts.get(record.attemptId); codes.set(record.codeHash.toString('hex'), { ...record, attempt }); return true; },
    async exchangeCode({ codeHash, codeChallenge, redirectUri, now, session }) {
      const row = codes.get(codeHash.toString('hex'));
      if (!row || row.used || row.expiresAt <= now || row.attempt.codeChallenge !== codeChallenge || row.attempt.redirectUri !== redirectUri) return;
      row.used = true; const user = [...users.values()].find(user => user.id === row.userId);
      sessions.set(session.id, { ...session, user }); return user;
    },
    async findAccess(accessHash, now) {
      const session = [...sessions.values()].find(row => row.accessHash.equals(accessHash) && !row.revoked && row.accessExpiresAt > now);
      return session && { user: session.user };
    },
    async rotateRefresh(refreshHash, next, now) {
      const row = [...sessions.values()].find(row => row.refreshHash.equals(refreshHash) && !row.revoked && row.refreshExpiresAt > now);
      if (!row) return;
      row.revoked = true; sessions.set(next.id, { ...next, user: row.user }); return row.user;
    },
    async revoke(accessHash, refreshHash) {
      const row = [...sessions.values()].find(row => row.accessHash.equals(accessHash) && (!refreshHash || row.refreshHash.equals(refreshHash)));
      if (row) row.revoked = true; return Boolean(row);
    },
  };
}

async function fixture(t, extra = {}) {
  const config = configured(), store = storeDouble(), errors = [];
  const provider = { authorizationUrl(name, attempt, state) {
    return 'https://provider.example.test/authorize?state=' + state;
  }, async verifyIdentity(provider, code) {
    if (code !== 'provider-code') throw new Error('provider-secret-must-not-leak');
    return { providerUserId: provider + '-subject', displayName: 'Farmer' };
  } };
  const router = createSocialAuthRouter({ config, store, providers: provider, now: () => seconds * 1000,
    isSecureRequest: () => true, errorLog: entry => errors.push(entry), ...extra });
  const server = createServer((req, res) => { void router.handle(req, res, new URL(req.url, 'http://test.invalid')); });
  await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
  t.after(() => new Promise(resolve => { server.closeAllConnections(); server.close(resolve); }));
  const origin = 'http://127.0.0.1:' + server.address().port;
  const get = (path, headers = {}) => fetch(origin + path, { redirect: 'manual', headers });
  const post = (path, body, headers = {}) => fetch(origin + path, { method: 'POST', headers: { 'Content-Type': 'application/json', ...headers }, body: JSON.stringify(body), redirect: 'manual' });
  return { config, store, errors, get, post };
}

test('browser OAuth uses one-time code/state and app PKCE before releasing any session credentials', async t => {
  const f = await fixture(t), verifier = value(), state = value();
  let response = await f.post('/auth/attempt', { provider: 'google', state, code_challenge: challenge(verifier), redirect_uri: APP_REDIRECT_URI });
  assert.equal(response.status, 201);
  assert.equal(response.headers.get('cache-control'), 'no-store');
  const attempt = await response.json();
  assert.ok(attempt.authorization_url.startsWith(f.config.publicBaseUrl + '/auth/browser/start?'));
  response = await f.get('/auth/browser/start?id=' + attempt.attempt_id);
  assert.equal(response.status, 302);
  const providerState = new URL(response.headers.get('location')).searchParams.get('state');
  assert.notEqual(providerState, state);
  response = await f.get('/auth/callback/google?state=' + providerState + '&code=provider-code');
  assert.equal(response.status, 200);
  const html = await response.text();
  assert.ok(!html.includes('access_token') && !html.includes('refresh_token') && !html.includes('provider-code'));
  const match = html.match(/id="return-to-game"[^>]*href="([^"]+)"/u);
  assert.ok(match);
  const callback = new URL(match[1].replaceAll('&amp;', '&'));
  assert.equal(callback.origin, 'null');
  assert.equal(callback.searchParams.get('state'), state);
  const code = callback.searchParams.get('code');
  assert.equal((await f.get('/auth/callback/google?state=' + providerState + '&code=provider-code')).status, 400);
  assert.equal((await f.post('/auth/exchange', { code, code_verifier: value(), redirect_uri: APP_REDIRECT_URI })).status, 400);
  response = await f.post('/auth/exchange', { code, code_verifier: verifier, redirect_uri: APP_REDIRECT_URI });
  assert.equal(response.status, 200);
  const tokens = await response.json();
  assert.match(tokens.user.game_id, /^GF[0-9]+$/u);
  assert.equal(tokens.user.provider, 'google');
  assert.ok(!Object.hasOwn(tokens.user, 'id') && !Object.hasOwn(tokens.user, 'provider_user_id'));
  assert.equal((await f.post('/auth/exchange', { code, code_verifier: verifier, redirect_uri: APP_REDIRECT_URI })).status, 400);
  response = await f.get('/auth/me', { Authorization: 'Bearer ' + tokens.access_token });
  assert.deepEqual((await response.json()).user, tokens.user);
  response = await f.post('/auth/refresh', { refresh_token: tokens.refresh_token });
  assert.equal(response.status, 200);
  const refreshed = await response.json();
  assert.deepEqual(refreshed.user, tokens.user);
  assert.notEqual(refreshed.access_token, tokens.access_token);
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + tokens.access_token })).status, 401);
  assert.equal((await f.post('/auth/refresh', { refresh_token: tokens.refresh_token })).status, 401);
  assert.equal((await f.post('/auth/logout', { refresh_token: refreshed.refresh_token }, { Authorization: 'Bearer ' + refreshed.access_token })).status, 204);
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + refreshed.access_token })).status, 401);
  assert.equal((await f.post('/auth/refresh', { refresh_token: refreshed.refresh_token })).status, 401);
});

test('malformed JSON fields, forged callbacks and HTTPS spoofing fail with explicit sanitized errors', async t => {
  const f = await fixture(t);
  for (const bad of [null, [], {}, [value()]]) {
    const response = await f.post('/auth/attempt', { provider: 'google', state: bad, code_challenge: value(), redirect_uri: APP_REDIRECT_URI });
    assert.equal(response.status, 400);
    assert.equal((await response.json()).error.code, 'invalid_request');
  }
  assert.equal((await f.post('/auth/attempt', { provider: 'google', state: value(), code_challenge: value(), redirect_uri: 'https://attacker.invalid' })).status, 400);
  const forged = await f.get('/auth/callback/google?state=' + value() + '&code=provider-code');
  assert.equal(forged.status, 400);
  assert.ok(!(await forged.text()).includes('data-return-url="com.quanhoang'));
  const insecure = await fixture(t, { isSecureRequest: () => false });
  const response = await insecure.post('/auth/attempt', { provider: 'google', state: value(), code_challenge: value(), redirect_uri: APP_REDIRECT_URI }, { 'X-Forwarded-Proto': 'https' });
  assert.equal(response.status, 400);
  assert.equal((await response.json()).error.code, 'https_required');
  assert.equal(insecure.errors[0]?.event, 'auth_https_rejected');
  assert.equal(insecure.errors[0]?.proxy_configured, true);
  assert.equal(insecure.errors[0]?.forwarded_proto, 'https');
  const missing = await fixture(t, { config: loadSocialAuthConfig({}) });
  const unavailable = await missing.post('/auth/attempt', {});
  assert.equal(unavailable.status, 503);
  assert.equal((await unavailable.json()).error.code, 'auth_not_configured');
});

test('website attempts and account sessions ignore broken OAuth settings and never select a provider implicitly', async t => {
  const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: 'https://accounts.example.test',
    FACEBOOK_APP_ID: '123456789', GOOGLE_OAUTH_CLIENT_SECRET: 'incomplete-google-fixture' });
  let providerCalls = 0;
  const forbidden = () => { ++providerCalls; throw new Error('OAuth must not run for Website'); };
  const f = await fixture(t, { config, providers: { authorizationUrl: forbidden, verifyIdentity: forbidden } });
  const request = { state: value(), code_challenge: value(), redirect_uri: APP_REDIRECT_URI };
  const website = await f.post('/auth/attempt', { ...request, provider: 'password' });
  assert.equal(website.status, 201);
  assert.match((await website.json()).authorization_url, /^https:\/\/accounts\.example\.test\/auth\/web\/login\?attempt=/u);
  const missing = await f.post('/auth/attempt', request);
  assert.equal(missing.status, 400);
  assert.equal((await missing.json()).error.code, 'invalid_request');
  for (const provider of ['facebook', 'google']) {
    const unavailable = await f.post('/auth/attempt', { ...request, provider });
    assert.equal(unavailable.status, 503);
    assert.equal((await unavailable.json()).error.code, 'oauth_not_configured');
  }
  assert.equal((await f.post('/auth/facebook', { ...request, provider: 'password' })).status, 400);
  assert.equal((await f.get('/auth/me', { Authorization: 'Bearer ' + value() })).status, 401);
  assert.equal((await f.post('/auth/refresh', { refresh_token: value() })).status, 401);
  assert.equal((await f.post('/auth/logout', { refresh_token: value() }, { Authorization: 'Bearer ' + value() })).status, 204);
  assert.equal(providerCalls, 0);

  const noStore = await fixture(t, { config, store: undefined });
  const unavailableDatabase = await noStore.post('/auth/attempt', { ...request, provider: 'password' });
  assert.equal(unavailableDatabase.status, 503);
  assert.equal((await unavailableDatabase.json()).error.code, 'database_unavailable');
});

test('attempt requests are rate limited and provider failure never appears as authenticated success', async t => {
  const f = await fixture(t);
  for (let i = 0; i < 20; i++) assert.equal((await f.post('/auth/attempt', { provider: 'google', state: value(), code_challenge: value(), redirect_uri: APP_REDIRECT_URI })).status, 201);
  const limited = await f.post('/auth/attempt', { provider: 'google', state: value(), code_challenge: value(), redirect_uri: APP_REDIRECT_URI });
  assert.equal(limited.status, 429);
  assert.equal(limited.headers.get('retry-after'), '900');
});
