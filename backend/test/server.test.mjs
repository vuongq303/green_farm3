import assert from 'node:assert/strict';
import { once } from 'node:events';
import { request as httpRequest } from 'node:http';
import test from 'node:test';

import { createResearchServer } from '../src/server.js';
import { createTestStore } from './helpers/store.mjs';

const PUBLIC_BASE = 'http://192.168.23.41:4141';
const CLIENT = 'research-client-fixture:1.0:Android';
const DEVICE = 'research-device-fixture';
const CREDENTIAL = 'local-fixture-\u{1f31e}-credential';
const SCOPES = 'storage auth config asset transaction message';
const URL_KEYS = [
  'pandora', 'gllive-ope', 'tapjoy', 'offline_items', 'game_portal',
  'status', 'crm_iap', 'ads_agency', 'marketing_site', 'online_connectivity',
];

// The original aR(String) operates on UTF-16 code units, including surrogate pairs.
function originalPassword(credential) {
  let password = '';
  for (let index = 0; index < Math.min(16, credential.length); index += 1) {
    password += String.fromCharCode((credential.charCodeAt(index) % 25) + 97);
  }
  return password;
}

function authorizeFields(overrides = {}) {
  return {
    client_id: CLIENT,
    username: `anonymous:${CREDENTIAL}`,
    password: originalPassword(CREDENTIAL),
    scope: SCOPES,
    device_id: DEVICE,
    ...overrides,
  };
}

async function fixture(t, options = {}) {
  const server = createResearchServer({
    publicBaseUrl: PUBLIC_BASE,
    sessionSecret: 'test-secret-for-local-research-only',
    store: createTestStore(),
    errorLog: () => {},
    log: () => {},
    ...options,
  });
  server.listen(0, '127.0.0.1');
  await once(server, 'listening');
  t.after(async () => {
    const closed = new Promise((resolve, reject) => {
      server.close((error) => error ? reject(error) : resolve());
    });
    server.closeAllConnections();
    await closed;
  });

  const address = server.address();
  assert.ok(address && typeof address === 'object');

  function request(path, { method = 'GET', body, headers = {} } = {}) {
    return new Promise((resolve, reject) => {
      const req = httpRequest({
        hostname: '127.0.0.1', port: address.port, path, method,
        headers: { connection: 'close', ...headers },
      }, (res) => {
        const chunks = [];
        res.on('data', (chunk) => chunks.push(chunk));
        res.on('error', reject);
        res.on('end', () => resolve({
          status: res.statusCode,
          headers: res.headers,
          text: Buffer.concat(chunks).toString('utf8'),
        }));
      });
      req.on('error', reject);
      req.end(body);
    });
  }

  const postForm = (path, fields, options = {}) => request(path, {
    method: 'POST',
    body: new URLSearchParams(fields).toString(),
    ...options,
    headers: { 'content-type': 'application/x-www-form-urlencoded', ...options.headers },
  });

  async function authorize(fields = authorizeFields()) {
    const response = await postForm('/authorize', fields);
    assert.equal(response.status, 200, response.text);
    const value = json(response);
    assert.deepEqual(Object.keys(value).sort(), ['access_token', 'refresh_token']);
    assert.equal(typeof value.access_token, 'string');
    assert.ok(value.access_token.length > 0);
    return value.access_token;
  }

  return { request, postForm, authorize };
}

function json(response) {
  assert.match(response.headers['content-type'] ?? '', /^application\/json\b/);
  return JSON.parse(response.text);
}

function compliance(accessToken, age = '16', country = 'US') {
  return { access_token: accessToken, country, age };
}

test('health, datacenter selection, ten URL keys, and plain auth locator use the configured base', async (t) => {
  const { request } = await fixture(t, { publicBaseUrl: `${PUBLIC_BASE}/` });
  assert.equal((await request('/healthz')).status, 200);

  const datacenters = await request(`/config/${CLIENT}/datacenters`);
  assert.equal(datacenters.status, 200);
  assert.deepEqual(json(datacenters), [{ name: 'local', _datacenter_id: 'local-research' }]);

  const urls = await request(`/config/${CLIENT}/datacenters/local/urls`);
  assert.equal(urls.status, 200);
  const value = json(urls);
  assert.deepEqual(Object.keys(value).sort(), URL_KEYS.toSorted());
  for (const key of URL_KEYS) {
    assert.equal(typeof value[key], 'string', key);
    assert.equal(new URL(value[key]).origin, PUBLIC_BASE, key);
    assert.ok(value[key].startsWith(PUBLIC_BASE), key);
  }
  assert.equal(value.pandora, PUBLIC_BASE);

  const locator = await request('/locate/auth');
  assert.equal(locator.status, 200);
  assert.equal(locator.text, PUBLIC_BASE);
  assert.ok(!locator.text.endsWith('\n'));
  assert.ok(!locator.text.endsWith('/'));

  const storageLocator = await request('/locate/storage');
  assert.equal(storageLocator.status, 200);
  assert.equal(storageLocator.text, PUBLIC_BASE);
  assert.match(storageLocator.headers['content-type'] ?? '', /^text\/plain\b/);
});

test('authorization accepts the original UTF-16 password transform and all compatibility scope declarations', async (t) => {
  const { authorize, postForm } = await fixture(t);
  const token = await authorize();
  assert.ok(token);

  // Scope whitespace is tokenized; the declarations do not expose additional routes.
  const response = await postForm('/authorize', authorizeFields({ scope: ' storage\tauth\nconfig asset transaction message ' }));
  assert.equal(response.status, 200, response.text);
});

test('fresh sessions are distinct and accepted by the compliance route', async (t) => {
  const { authorize, postForm } = await fixture(t);
  const first = await authorize();
  const second = await authorize();
  assert.notEqual(first, second);
  const response = await postForm('/users/me/compliance', compliance(first));
  assert.equal(response.status, 200);
  assert.deepEqual(json(response), { LOCAL_RESEARCH_16_PLUS: { compliant: true } });
});

test('the explicit local policy returns false below 16 and true from 16 through 120', async (t) => {
  const { authorize, postForm } = await fixture(t);
  const token = await authorize();
  for (const [age, country, expected] of [
    ['0', 'US', false], ['15', 'us', false], ['16', 'Fr', true], ['120', 'GB', true],
  ]) {
    const response = await postForm('/users/me/compliance', compliance(token, age, country));
    assert.equal(response.status, 200, `${age}: ${response.text}`);
    assert.deepEqual(json(response), { LOCAL_RESEARCH_16_PLUS: { compliant: expected } });
  }
});

test('compliance requires a session token and rejects forged or changed tokens', async (t) => {
  const { authorize, postForm } = await fixture(t);
  const token = await authorize();
  const alphabet = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_';
  const lastIndex = alphabet.indexOf(token.at(-1));
  assert.equal(lastIndex % 4, 0, 'SHA-256 base64url signature has canonical padding bits');
  const signatureAlias = `${token.slice(0, -1)}${alphabet[lastIndex + 1]}`;
  assert.deepEqual(
    Buffer.from(signatureAlias.split('.').at(-1), 'base64url'),
    Buffer.from(token.split('.').at(-1), 'base64url'),
    'alias changes the token text while retaining the decoded signature bytes',
  );
  for (const fields of [
    { country: 'US', age: '16' },
    compliance(''),
    compliance('not-a-session-token'),
    compliance(`${token}x`),
    compliance(`x${token.slice(1)}`),
    compliance(signatureAlias),
  ]) {
    assert.equal((await postForm('/users/me/compliance', fields)).status, 401);
  }
});

test('expiry uses the injected clock and session TTL', async (t) => {
  let now = 1_700_000_000_000;
  const { authorize, postForm } = await fixture(t, { now: () => now, sessionTtlSeconds: 2 });
  const token = await authorize();
  now += 1_999;
  assert.equal((await postForm('/users/me/compliance', compliance(token))).status, 200);
  now += 1;
  assert.equal((await postForm('/users/me/compliance', compliance(token))).status, 401);
});

test('tokens belong to the issuing server even when servers share an HMAC secret', async (t) => {
  const first = await fixture(t);
  const second = await fixture(t);
  const token = await first.authorize();
  assert.equal((await second.postForm('/users/me/compliance', compliance(token))).status, 401);
});

test('profile credentials belong to the authorized session and stay isolated between users', async (t) => {
  const { authorize, request } = await fixture(t);
  const firstFields = authorizeFields();
  const secondCredential = 'second-local-user-fixture';
  const secondFields = authorizeFields({
    username: `anonymous:${secondCredential}`,
    password: originalPassword(secondCredential),
    device_id: 'second-local-device-fixture',
  });
  const firstToken = await authorize(firstFields);
  const secondToken = await authorize(secondFields);
  for (const [token, username] of [[firstToken, firstFields.username], [secondToken, secondFields.username], [firstToken, firstFields.username]]) {
    const response = await request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: token })}`);
    assert.equal(response.status, 200, response.text);
    assert.deepEqual(json(response), { credential: username });
  }
});

test('profile reads require a valid unexpired session token', async (t) => {
  let now = 1_700_000_000_000;
  const { authorize, request } = await fixture(t, { now: () => now, sessionTtlSeconds: 2 });
  const token = await authorize();
  for (const query of ['', '?access_token=', '?access_token=invalid-session', `?${new URLSearchParams({ access_token: `${token}x` })}`]) {
    assert.equal((await request(`/profiles/me/myprofile${query}`)).status, 401);
  }
  now += 1_999;
  assert.equal((await request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: token })}`)).status, 200);
  now += 1;
  assert.equal((await request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: token })}`)).status, 401);
});

test('profile paths outside the implemented own-profile read remain unknown', async (t) => {
  const { authorize, request } = await fixture(t);
  const token = await authorize();
  for (const path of ['/profiles', '/profiles/me', '/profiles/me/other', '/profiles/someone/myprofile', '/profiles/me/myprofile/extra']) {
    const response = await request(`${path}?${new URLSearchParams({ access_token: token })}`);
    assert.equal(response.status, 404, path);
  }
});

test('authorization rejects missing fields, unsupported scope, and invalid credentials', async (t) => {
  const { postForm } = await fixture(t);
  for (const key of ['client_id', 'username', 'password', 'scope', 'device_id']) {
    const fields = authorizeFields();
    delete fields[key];
    assert.equal((await postForm('/authorize', fields)).status, 400, key);
  }
  for (const scope of ['', 'storage unknown']) {
    assert.equal((await postForm('/authorize', authorizeFields({ scope }))).status, 400);
  }
  for (const fields of [
    authorizeFields({ username: 'named-user' }),
    authorizeFields({ password: 'wrong-password' }),
  ]) {
    assert.equal((await postForm('/authorize', fields)).status, 401);
  }
});

test('compliance requires country and canonical integer age in the documented range', async (t) => {
  const { authorize, postForm } = await fixture(t);
  const token = await authorize();
  for (const key of ['country', 'age']) {
    const fields = compliance(token);
    delete fields[key];
    assert.equal((await postForm('/users/me/compliance', fields)).status, 400, key);
  }
  for (const age of ['', '-1', '121', '1.0', '+16', '016', '16 ', ' 16', '1e2', 'NaN']) {
    const response = await postForm('/users/me/compliance', compliance(token, age));
    assert.equal(response.status, 400, JSON.stringify(age));
  }
  for (const country of ['', 'U', 'USA', '1A', 'U ', 'éé']) {
    const response = await postForm('/users/me/compliance', compliance(token, '16', country));
    assert.equal(response.status, 400, JSON.stringify(country));
  }
});

test('both POST routes reject content types other than URL-encoded forms', async (t) => {
  const { request } = await fixture(t);
  for (const path of ['/authorize', '/users/me/compliance']) {
    for (const headers of [{}, { 'content-type': 'application/json' }, { 'content-type': 'text/plain' }]) {
      assert.equal((await request(path, { method: 'POST', body: '{}', headers })).status, 415);
    }
  }
});

test('form parsing rejects duplicate fields, malformed percent escapes, and malformed UTF-8', async (t) => {
  const { request, authorize } = await fixture(t);
  const token = await authorize();
  const authBody = new URLSearchParams(authorizeFields()).toString();
  const complianceBody = new URLSearchParams(compliance(token)).toString();
  for (const [path, body] of [
    ['/authorize', `${authBody}&device_id=duplicate`],
    ['/users/me/compliance', `${complianceBody}&age=17`],
    ['/authorize', `${authBody}&bad=%`],
    ['/authorize', `${authBody}&bad=%G0`],
    ['/authorize', `${authBody}&bad=%A`],
    ['/users/me/compliance', `${complianceBody}&bad=%C3%28`],
    ['/users/me/compliance', Buffer.concat([Buffer.from(`${complianceBody}&bad=`), Buffer.from([0xc3, 0x28])])],
  ]) {
    const response = await request(path, {
      method: 'POST', body,
      headers: { 'content-type': 'application/x-www-form-urlencoded; charset=UTF-8' },
    });
    assert.equal(response.status, 400, `${path}: ${String(body)}`);
  }
});

test('both POST routes reject bodies exceeding 8 KiB', async (t) => {
  const { request } = await fixture(t);
  for (const path of ['/authorize', '/users/me/compliance']) {
    const response = await request(path, {
      method: 'POST', body: `padding=${'x'.repeat(8 * 1024)}`,
      headers: { 'content-type': 'application/x-www-form-urlencoded' },
    });
    assert.equal(response.status, 413, path);
  }
});

test('known routes return 405 for the wrong method', async (t) => {
  const { request } = await fixture(t);
  for (const [path, method] of [
    ['/healthz', 'POST'],
    [`/config/${CLIENT}/datacenters`, 'POST'],
    [`/config/${CLIENT}/datacenters/local/urls`, 'POST'],
    ['/locate/auth', 'POST'],
    ['/locate/storage', 'POST'],
    ['/authorize', 'GET'],
    ['/users/me/compliance', 'GET'],
    ['/profiles/me/myprofile', 'POST'],
  ]) {
    assert.equal((await request(path, { method })).status, 405, `${method} ${path}`);
  }
});

test('unknown paths return 404 and explicit unsupported surfaces return 501', async (t) => {
  const { request } = await fixture(t);
  for (const path of ['/unknown', '/users/me', '/locate/not-auth', `/config/${CLIENT}/datacenters/not-local/urls`]) {
    assert.equal((await request(path)).status, 404, path);
  }
  assert.equal((await request('/unsupported/storage')).status, 501);
  assert.equal((await request('/locate/config')).status, 501);
});

test('request logs contain only method, redacted route path, and status', async (t) => {
  const entries = [];
  const { request, authorize, postForm } = await fixture(t, { log: (entry) => entries.push(entry) });
  await request(`/config/${CLIENT}/datacenters?access_token=query-secret-fixture`);
  const token = await authorize();
  await postForm('/users/me/compliance', compliance(token));
  await request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: token })}`);
  await request('/unknown?password=query-password-fixture');

  assert.ok(entries.length >= 5);
  assert.ok(entries.some((entry) => entry.path === '/profiles/me/myprofile' && entry.status === 200));
  for (const entry of entries) {
    assert.deepEqual(Object.keys(entry).sort(), ['method', 'path', 'status']);
    assert.equal(typeof entry.method, 'string');
    assert.equal(typeof entry.path, 'string');
    assert.equal(typeof entry.status, 'number');
    assert.ok(!entry.path.includes('?'));
  }
  const rendered = JSON.stringify(entries);
  for (const secret of [CLIENT, DEVICE, CREDENTIAL, token, originalPassword(CREDENTIAL), 'query-secret-fixture', 'query-password-fixture']) {
    assert.ok(!rendered.includes(secret), `log leaked ${secret.slice(0, 20)}`);
  }
});

test('core client scope declarations and durable refresh grant follow original request fields', async t => {
  let now = 1_700_000_000_000;
  const store = createTestStore();
  const first = await fixture(t, { store, now: () => now, sessionTtlSeconds: 2, refreshTtlSeconds: 20 });
  const initial = await first.postForm('/authorize', authorizeFields({ scope: 'leaderboard social storage auth message config' }));
  assert.equal(initial.status, 200);
  const tokens = json(initial);
  now += 2000;
  assert.equal((await first.request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: tokens.access_token })}`)).status, 401);
  // Same repository and signing secret model a server process restart.
  const restarted = await fixture(t, { store, now: () => now, sessionTtlSeconds: 2, refreshTtlSeconds: 20 });
  const refreshed = await restarted.postForm('/authorize', { client_id: CLIENT, grant_type: 'refresh_token', refresh_token: tokens.refresh_token });
  assert.equal(refreshed.status, 200);
  const rotated = json(refreshed);
  assert.notEqual(rotated.access_token, tokens.access_token);
  assert.notEqual(rotated.refresh_token, tokens.refresh_token);
  assert.equal((await restarted.request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: rotated.access_token })}`)).status, 200);
  assert.equal((await restarted.postForm('/authorize', { client_id: CLIENT, grant_type: 'refresh_token', refresh_token: tokens.refresh_token })).status, 401);
});

test('refresh rejects wrong namespace, invalid, expired and access tokens', async t => {
  let now = 1_700_000_000_000;
  const { postForm } = await fixture(t, { now: () => now, sessionTtlSeconds: 2, refreshTtlSeconds: 3 });
  const initial = json(await postForm('/authorize', authorizeFields()));
  for (const refreshToken of ['wrong', initial.access_token, initial.refresh_token + 'x']) {
    assert.equal((await postForm('/authorize', { client_id: CLIENT, grant_type: 'refresh_token', refresh_token: refreshToken })).status, 401);
  }
  assert.equal((await postForm('/authorize', { client_id: 'wrong-client', grant_type: 'refresh_token', refresh_token: initial.refresh_token })).status, 401);
  now += 3000;
  assert.equal((await postForm('/authorize', { client_id: CLIENT, grant_type: 'refresh_token', refresh_token: initial.refresh_token })).status, 401);
});

test('database failures never produce successful login/profile/compliance responses', async t => {
  const store = createTestStore();
  const { request, postForm, authorize } = await fixture(t, { store });
  const token = await authorize();
  store.setUnavailable(true);
  const health = await request('/health');
  assert.equal(health.status, 503);
  assert.deepEqual(json(health), { status: 'degraded', backend: { status: 'running' }, sqlite: { status: 'unavailable' } });
  for (const response of [
    await postForm('/authorize', authorizeFields()),
    await request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: token })}`),
    await postForm('/users/me/compliance', compliance(token)),
  ]) {
    assert.equal(response.status, 503);
    assert.deepEqual(json(response), { error: 'database_unavailable' });
  }
  store.setUnavailable(false);
  assert.equal((await request('/health')).status, 200);
  assert.equal((await request(`/profiles/me/myprofile?${new URLSearchParams({ access_token: token })}`)).status, 200);
});

test('a production server cannot silently substitute an in-memory repository', () => {
  assert.throws(() => createResearchServer({ publicBaseUrl: PUBLIC_BASE, sessionSecret: 'test-secret-for-local-research-only' }), /SQLite repository is required/u);
});
