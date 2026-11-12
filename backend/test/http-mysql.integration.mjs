import assert from 'node:assert/strict';
import { spawn } from 'node:child_process';
import { randomBytes } from 'node:crypto';
import { once } from 'node:events';
import { mkdtemp, rm, writeFile } from 'node:fs/promises';
import { request as httpRequest } from 'node:http';
import { createServer as createTcpServer } from 'node:net';
import { networkInterfaces, tmpdir } from 'node:os';
import { join } from 'node:path';
import { after, test } from 'node:test';
import { fileURLToPath } from 'node:url';
import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { createPoolFromConfig } from '../src/db/store.js';
import { migrate } from '../src/db/migrate.js';
import { legacyPassword } from '../src/services/sessions.js';

// Explicitly selected real-MySQL/real-process tests; no fake repository or
// production listeners are used. Only this test's children and rows are removed.
loadEnvironment();
const database = loadDatabaseConfig();
database.database = process.env.TEST_DB_NAME ?? database.database;
if (!database.database.endsWith('_test')) {
  throw new Error('HTTP/MySQL integration requires DB_NAME or TEST_DB_NAME ending in _test');
}
const pool = createPoolFromConfig(database);
after(() => pool.end());
await migrate(pool);

const backendDirectory = fileURLToPath(new URL('../', import.meta.url));
const localIpv4 = Object.values(networkInterfaces()).flat().find(address =>
  address && address.family === 'IPv4' && !address.internal && address.address !== '10.0.2.2')?.address ?? '192.0.2.10';

async function unusedPort() {
  const socket = createTcpServer();
  socket.listen(0, '127.0.0.1');
  await once(socket, 'listening');
  const port = socket.address().port;
  await new Promise((resolve, reject) => socket.close(error => error ? reject(error) : resolve()));
  return port;
}

function request(port, path, { method = 'GET', fields } = {}) {
  const body = fields === undefined ? undefined : new URLSearchParams(fields).toString();
  return new Promise((resolve, reject) => {
    const req = httpRequest({ hostname: '127.0.0.1', port, path, method,
      headers: { connection: 'close', ...(body === undefined ? {} : {
        'content-type': 'application/x-www-form-urlencoded', 'content-length': Buffer.byteLength(body),
      }) } }, response => {
      const chunks = [];
      response.on('data', chunk => chunks.push(chunk));
      response.on('error', reject);
      response.on('end', () => resolve({ status: response.statusCode,
        headers: response.headers, text: Buffer.concat(chunks).toString('utf8') }));
    });
    req.setTimeout(3000, () => req.destroy(new Error('Owned HTTP fixture request timed out')));
    req.on('error', reject);
    req.end(body);
  });
}

function json(response) {
  assert.match(response.headers['content-type'] ?? '', /^application\/json\b/u);
  return JSON.parse(response.text);
}

function assertLogsRedacted(log, secrets) {
  for (const line of log.split('\n').filter(Boolean)) {
    const entry = JSON.parse(line);
    assert.ok(Object.keys(entry).every(key => !/password|token|credential|client.?id|device.?id/iu.test(key)),
      'child logs must not contain sensitive data fields');
  }
  // A very short DB password may coincide with ordinary status codes or words.
  // Field validation above covers that case without mistaking "401" for a leak.
  for (const secret of secrets.filter(value => value.length >= 8)) {
    assert.ok(!log.includes(secret), 'child logs must redact identifiers, passwords and tokens');
  }
}

async function fixture(t) {
  const directory = await mkdtemp(join(tmpdir(), 'greenfarm-http-mysql-'));
  const port = await unusedPort();
  const baseUrl = `http://${localIpv4}:${port}`;
  const propertiesFile = join(directory, 'gradle.properties');
  await writeFile(propertiesFile, `BACKEND_BASE_URL=${baseUrl}/\n`);
  const clientId = 'http-mysql-integration-' + randomBytes(16).toString('hex');
  const credential = 'http-identity-' + randomBytes(16).toString('hex');
  const fields = { client_id: clientId, username: 'anonymous:' + credential,
    password: legacyPassword(credential), scope: 'leaderboard social storage auth message config',
    device_id: 'http-mysql-owned-device' };
  const env = { ...process.env, DB_HOST: database.host, DB_PORT: String(database.port),
    DB_NAME: database.database, DB_USER: database.user, DB_PASSWORD: database.password,
    HOST: '127.0.0.1', PORT: String(port), BACKEND_CONFIG_FILE: propertiesFile,
    RESEARCH_SESSION_SECRET: process.env.RESEARCH_SESSION_SECRET ?? randomBytes(32).toString('hex'),
    NODE_ENV: 'test', DB_CONNECT_TIMEOUT_MS: '250' };
  for (const deprecated of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL']) delete env[deprecated];
  const ownedChildren = new Set();
  const logs = [];

  async function start(overrides = {}) {
    const child = spawn(process.execPath, [join(backendDirectory, 'src/main.js')], {
      cwd: backendDirectory, env: { ...env, ...overrides }, stdio: ['ignore', 'pipe', 'pipe'],
    });
    ownedChildren.add(child);
    let output = '';
    child.stdout.on('data', data => { output += data.toString(); });
    child.stderr.on('data', data => { output += data.toString(); });
    await once(child, 'spawn');
    const deadline = Date.now() + 10000;
    while (!output.includes('"event":"listening"')) {
      if (child.exitCode !== null || child.signalCode !== null) throw new Error('Owned backend exited before listening');
      if (Date.now() > deadline) throw new Error('Owned backend did not start within ten seconds');
      await new Promise(resolve => setTimeout(resolve, 20));
    }
    return {
      async stop() {
        if (!ownedChildren.has(child)) return;
        if (child.exitCode === null && child.signalCode === null) {
          const exited = once(child, 'close');
          const timeout = setTimeout(() => child.kill('SIGKILL'), 5000);
          timeout.unref();
          child.kill('SIGTERM');
          try {
            const [code, signal] = await exited;
            assert.equal(signal, null, 'owned backend should stop gracefully');
            assert.equal(code, 0, 'owned backend should exit successfully');
          } finally { clearTimeout(timeout); }
        }
        logs.push(output);
        ownedChildren.delete(child);
      },
    };
  }

  t.after(async () => {
    // Even a failed assertion only stops PIDs spawned by this fixture.
    for (const child of ownedChildren) {
      if (child.exitCode === null && child.signalCode === null) {
        const exited = once(child, 'close');
        const timeout = setTimeout(() => child.kill('SIGKILL'), 5000);
        timeout.unref();
        child.kill('SIGTERM');
        try { await exited; } finally { clearTimeout(timeout); }
      }
    }
    await pool.execute('DELETE FROM gf_sessions WHERE account_id IN (SELECT id FROM gf_accounts WHERE client_id = ?)', [clientId]);
    await pool.execute('DELETE FROM gf_compliance WHERE account_id IN (SELECT id FROM gf_accounts WHERE client_id = ?)', [clientId]);
    await pool.execute('DELETE FROM gf_accounts WHERE client_id = ?', [clientId]);
    await rm(directory, { recursive: true, force: true });
  });
  return { port, baseUrl, fields, clientId, credential, start, logs,
    request: (path, options) => request(port, path, options),
    post: (path, fields) => request(port, path, { method: 'POST', fields }),
  };
}

test('real HTTP/MySQL authorization, profile and compliance survive a backend process restart', async t => {
  const f = await fixture(t);
  let server = await f.start();
  const health = await f.request('/health');
  assert.equal(health.status, 200);
  assert.equal(json(health).mysql.status, 'ready');
  const locator = await f.request('/locate/auth');
  assert.equal(locator.text, f.baseUrl);
  const storageLocator = await f.request('/locate/storage');
  assert.equal(storageLocator.text, f.baseUrl);
  const map = json(await f.request(`/config/${f.clientId}/datacenters/local/urls`));
  for (const value of Object.values(map)) assert.equal(new URL(value).origin, f.baseUrl);

  const incomplete = { ...f.fields };
  delete incomplete.device_id;
  assert.equal((await f.post('/authorize', incomplete)).status, 400);
  assert.equal((await f.post('/authorize', { ...f.fields, password: 'wrong-password' })).status, 401);
  const authorized = await f.post('/authorize', f.fields);
  assert.equal(authorized.status, 200);
  const tokens = json(authorized);
  assert.deepEqual(Object.keys(tokens).sort(), ['access_token', 'refresh_token']);
  assert.ok(tokens.access_token && tokens.refresh_token);
  assert.equal((await f.request('/profiles/me/myprofile?access_token=invalid-fixture')).status, 401);
  const profilePath = '/profiles/me/myprofile?' + new URLSearchParams({ access_token: tokens.access_token });
  assert.deepEqual(json(await f.request(profilePath)), { credential: f.fields.username });
  const compliance = await f.post('/users/me/compliance', { access_token: tokens.access_token, country: 'US', age: '30' });
  assert.equal(compliance.status, 200);
  assert.deepEqual(json(compliance), { LOCAL_RESEARCH_16_PLUS: { compliant: true } });
  const [[before]] = await pool.execute(`SELECT a.id, a.credential, c.country, c.age FROM gf_accounts a
    INNER JOIN gf_compliance c ON c.account_id = a.id WHERE a.client_id = ?`, [f.clientId]);
  assert.equal(before.credential, f.fields.username);
  assert.equal(before.country, 'US');
  assert.equal(before.age, 30);

  await server.stop();
  server = await f.start();
  const persisted = await f.request(profilePath);
  assert.equal(persisted.status, 200, 'the original token must remain valid after real process restart');
  assert.deepEqual(json(persisted), { credential: f.fields.username });
  const [[after]] = await pool.execute(`SELECT a.id, a.credential, c.country, c.age FROM gf_accounts a
    INNER JOIN gf_compliance c ON c.account_id = a.id WHERE a.client_id = ?`, [f.clientId]);
  assert.deepEqual(after, before);
  const rotated = await f.post('/authorize', { client_id: f.clientId, grant_type: 'refresh_token', refresh_token: tokens.refresh_token });
  assert.equal(rotated.status, 200);
  const renewed = json(rotated);
  assert.notEqual(renewed.access_token, tokens.access_token);
  assert.notEqual(renewed.refresh_token, tokens.refresh_token);
  assert.equal((await f.request(profilePath)).status, 401);
  assert.equal((await f.post('/authorize', { client_id: f.clientId, grant_type: 'refresh_token', refresh_token: tokens.refresh_token })).status, 401);
  const renewedProfile = await f.request('/profiles/me/myprofile?' + new URLSearchParams({ access_token: renewed.access_token }));
  assert.equal(renewedProfile.status, 200);
  assert.deepEqual(json(renewedProfile), { credential: f.fields.username });
  await server.stop();
  const log = f.logs.join('\n');
  assertLogsRedacted(log, [f.clientId, f.credential, f.fields.password, tokens.access_token,
    tokens.refresh_token, renewed.access_token, renewed.refresh_token, database.password]);
});

test('a real backend with an unavailable MySQL server reports HTTP503 without successful authorization', async t => {
  const f = await fixture(t);
  let unavailablePort;
  do { unavailablePort = await unusedPort(); } while (unavailablePort === f.port);
  const server = await f.start({ DB_HOST: '127.0.0.1', DB_PORT: String(unavailablePort) });
  const health = await f.request('/health');
  assert.equal(health.status, 503);
  assert.deepEqual(json(health), { status: 'degraded', backend: { status: 'running' }, mysql: { status: 'unavailable' } });
  const authorized = await f.post('/authorize', f.fields);
  assert.equal(authorized.status, 503);
  assert.deepEqual(json(authorized), { error: 'database_unavailable' });
  const [[count]] = await pool.execute('SELECT COUNT(*) AS total FROM gf_accounts WHERE client_id = ?', [f.clientId]);
  assert.equal(Number(count.total), 0);
  await server.stop();
  const log = f.logs.join('\n');
  assertLogsRedacted(log, [f.clientId, f.credential, f.fields.password, database.password]);
});
