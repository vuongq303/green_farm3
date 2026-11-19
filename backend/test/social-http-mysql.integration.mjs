import assert from 'node:assert/strict';
import { createServer } from 'node:http';
import { createHash, randomBytes } from 'node:crypto';
import { after, test } from 'node:test';
import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { createPoolFromConfig } from '../src/db/store.js';
import { createSocialStore } from '../src/db/social-store.js';
import { migrate } from '../src/db/migrate.js';
import { loadSocialAuthConfig, APP_REDIRECT_URI } from '../src/auth/config.js';
import { createSocialAuthRouter } from '../src/auth/router.js';

loadEnvironment();
const database = loadDatabaseConfig();
database.database = process.env.TEST_DB_NAME ?? database.database;
if (!database.database.endsWith('_test')) throw new Error('Social HTTP tests require a dedicated database ending in _test');
const pool = createPoolFromConfig(database);
after(() => pool.end());
const value = () => randomBytes(32).toString('base64url');
const challenge = text => createHash('sha256').update(text).digest('base64url');

test('real HTTP/SQL auth survives router restart, rotates/revokes and preserves independent accounts', async t => {
  await migrate(pool);
  const prefix = 'social-http-' + randomBytes(12).toString('hex');
  const attempts = [];
  const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: 'https://accounts.example.test',
    GOOGLE_OAUTH_CLIENT_ID: 'integration-fixture-client', GOOGLE_OAUTH_CLIENT_SECRET: 'integration-fixture-secret',
    FACEBOOK_APP_ID: '123456789', FACEBOOK_APP_SECRET: 'integration-fixture-facebook-secret' });
  const store = createSocialStore(pool);
  let identity = prefix + '-google';
  // Provider verification is injected only in this test. Cryptographic/provider
  // HTTP validation is exercised separately; no real provider login is claimed.
  const providers = { authorizationUrl(provider, attempt, state) { return 'https://provider.example.test/?state=' + state; },
    async verifyIdentity(provider, code) {
      if (code !== 'fixture-provider-code') throw new Error('Rejected test provider code');
      return { providerUserId: identity, displayName: 'Integration Farmer' };
    } };
  let server, origin;
  async function start() {
    const router = createSocialAuthRouter({ config, store, providers, isSecureRequest: () => true });
    server = createServer((req, res) => { void router.handle(req, res, new URL(req.url, 'http://test.invalid')); });
    await new Promise(resolve => server.listen(0, '127.0.0.1', resolve));
    origin = 'http://127.0.0.1:' + server.address().port;
  }
  async function stop() { server.closeAllConnections(); await new Promise(resolve => server.close(resolve)); }
  t.after(async () => {
    if (server?.listening) await stop();
    const ids = await pool.execute('SELECT id FROM users WHERE provider_user_id LIKE ?', [prefix + '%']);
    for (const { id } of ids[0]) {
      await pool.execute('DELETE FROM gf_auth_sessions WHERE user_id = ?', [id]);
      await pool.execute('DELETE FROM gf_auth_codes WHERE user_id = ?', [id]);
      await pool.execute('DELETE FROM users WHERE id = ?', [id]);
    }
    for (const id of attempts) await pool.execute('DELETE FROM gf_auth_attempts WHERE id = ?', [id]);
  });
  await start();
  const get = (path, access) => fetch(origin + path, { redirect: 'manual', headers: access ? { Authorization: 'Bearer ' + access } : {} });
  const post = (path, body, access) => fetch(origin + path, { method: 'POST', redirect: 'manual',
    headers: { 'Content-Type': 'application/json', ...(access ? { Authorization: 'Bearer ' + access } : {}) }, body: JSON.stringify(body) });
  async function login(provider) {
    const verifier = value(), state = value();
    const attemptResponse = await post('/auth/attempt', { provider, state, code_challenge: challenge(verifier), redirect_uri: APP_REDIRECT_URI });
    assert.equal(attemptResponse.status, 201);
    const attempt = await attemptResponse.json(); attempts.push(attempt.attempt_id);
    const redirect = await get('/auth/browser/start?id=' + attempt.attempt_id);
    const providerState = new URL(redirect.headers.get('location')).searchParams.get('state');
    const callback = await get('/auth/callback/' + provider + '?state=' + providerState + '&code=fixture-provider-code');
    assert.equal(callback.status, 200);
    const html = await callback.text();
    const target = new URL(html.match(/id="return-to-game"[^>]*href="([^"]+)"/u)[1].replaceAll('&amp;', '&'));
    assert.equal(target.searchParams.get('state'), state);
    assert.equal((await post('/auth/exchange', { code: target.searchParams.get('code'), code_verifier: value(), redirect_uri: APP_REDIRECT_URI })).status, 400);
    const exchanged = await post('/auth/exchange', { code: target.searchParams.get('code'), code_verifier: verifier, redirect_uri: APP_REDIRECT_URI });
    assert.equal(exchanged.status, 200);
    assert.equal((await post('/auth/exchange', { code: target.searchParams.get('code'), code_verifier: verifier, redirect_uri: APP_REDIRECT_URI })).status, 400);
    return exchanged.json();
  }
  const first = await login('google');
  assert.match(first.user.game_id, /^GF[0-9]{6,}$/u);
  await stop(); await start();
  assert.deepEqual((await (await get('/auth/me', first.access_token)).json()).user, first.user);
  const same = await login('google');
  assert.equal(same.user.game_id, first.user.game_id);
  identity = prefix + '-facebook';
  const other = await login('facebook');
  assert.notEqual(other.user.game_id, first.user.game_id);
  const rotatedResponse = await post('/auth/refresh', { refresh_token: first.refresh_token });
  assert.equal(rotatedResponse.status, 200);
  const rotated = await rotatedResponse.json();
  assert.equal((await get('/auth/me', first.access_token)).status, 401);
  assert.equal((await post('/auth/refresh', { refresh_token: first.refresh_token })).status, 401);
  assert.equal((await post('/auth/logout', { refresh_token: first.refresh_token }, first.access_token)).status, 204);
  assert.equal((await get('/auth/me', rotated.access_token)).status, 401, 'logout of old pair revokes the refreshed family');
  assert.equal((await post('/auth/refresh', { refresh_token: rotated.refresh_token })).status, 401);
  assert.equal((await get('/auth/me', same.access_token)).status, 200, 'independent same-user login remains valid');
  assert.equal((await get('/auth/me', other.access_token)).status, 200, 'other provider account remains valid');
  const [[stored]] = await pool.execute(`SELECT COUNT(*) AS count FROM gf_auth_sessions
    WHERE access_hash = ? OR refresh_hash = ?`, [Buffer.from(first.access_token), Buffer.from(first.refresh_token)]);
  assert.equal(Number(stored.count), 0, 'raw tokens are never stored as session hashes');
});
