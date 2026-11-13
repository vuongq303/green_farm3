import assert from 'node:assert/strict';
import test from 'node:test';
import { mkdirSync, mkdtempSync, writeFileSync, readFileSync, rmSync, existsSync } from 'node:fs';
import { fileURLToPath } from 'node:url';
import { join } from 'node:path';
import { createServer } from 'node:net';
import { once } from 'node:events';
import { spawn } from 'node:child_process';
import { randomBytes, randomUUID } from 'node:crypto';
import { DatabaseSync } from 'node:sqlite';
import { migrateSqlite } from '../src/db/sqlite-migrate.js';
import { encodedFixture } from './helpers/archive.mjs';

const backend = fileURLToPath(new URL('..', import.meta.url));
async function workspace(t) {
  mkdirSync(join(backend, '.local'), { recursive: true });
  const folder = mkdtempSync(join(backend, '.local', 'sqlite-process-'));
  t.after(() => rmSync(folder, { recursive: true, force: true }));
  const reserve = createServer(); reserve.listen(0, '127.0.0.1'); await once(reserve, 'listening');
  const port = reserve.address().port; await new Promise(resolve => reserve.close(resolve));
  const properties = join(folder, 'endpoint.properties');
  writeFileSync(properties, `BACKEND_BASE_URL=http://192.168.40.55:${port}/\n`);
  const filename = join(folder, 'progress.sqlite');
  const env = { ...process.env, BACKEND_CONFIG_FILE: properties, SQLITE_PATH: filename, HOST: '127.0.0.1', PORT: String(port), RESEARCH_SESSION_SECRET: 'test-only-process-stable-session-secret' };
  for (const key of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL', 'NODE_ENV']) delete env[key];
  async function start(extra = {}) {
    const child = spawn(process.execPath, ['src/main.js'], { cwd: backend, env: { ...env, ...extra }, stdio: ['ignore', 'pipe', 'pipe'] });
    let output = ''; let errors = ''; let ready = false;
    child.stdout.on('data', value => { output += value; }); child.stderr.on('data', value => { errors += value; });
    const listening = new Promise((resolve, reject) => {
      const timeout = setTimeout(() => { child.kill('SIGKILL'); reject(new Error('Backend startup timeout')); }, 8000);
      child.stdout.on('data', () => {
        if (!ready && output.includes('"event":"listening"')) { ready = true; clearTimeout(timeout); resolve(); }
      });
      child.once('exit', code => { clearTimeout(timeout); if (!ready) reject(Object.assign(new Error('Backend refused startup'), { code, stdout: output, stderr: errors })); });
    });
    try { await listening; }
    catch (error) { return { failed: error, stdout: output, stderr: errors, code: child.exitCode }; }
    let stopped = false;
    const stop = async () => { if (!stopped) { stopped = true; const exited = once(child, 'exit'); child.kill('SIGTERM'); await exited; } };
    t.after(stop);
    return { stop, output: () => output, request: async (path, options = {}) => {
      const response = await fetch(`http://127.0.0.1:${port}${path}`, options);
      return { status: response.status, value: await response.json() };
    } };
  }
  const approve = (playerId, snapshotHash) => new Promise((resolve, reject) => {
    const child = spawn(process.execPath, ['scripts/approve-local-adoption.js', '--player-id', playerId, '--snapshot-hash', snapshotHash, '--expires-in', '900'], { cwd: backend, env, stdio: ['ignore', 'pipe', 'pipe'] });
    let stdout = ''; let stderr = ''; child.stdout.on('data', value => { stdout += value; }); child.stderr.on('data', value => { stderr += value; });
    child.once('exit', code => code === 0 ? resolve(JSON.parse(stdout)) : reject(new Error('Operator approval failed: ' + stderr)));
  });
  return { filename, folder, env, start, approve };
}

test('real Node process confirms SQLite readiness before listen and preserves two players after process restart', async t => {
  const w = await workspace(t); await migrateSqlite(w.filename, { startNewSqlite: true });
  let app = await w.start(); assert.ok(!app.failed, app.stderr);
  assert.ok(app.output().indexOf('database_ready') < app.output().indexOf('listening'));
  assert.equal((await app.request('/health')).value.sqlite.status, 'ready');
  const issue = async secret => {
    const response = await app.request('/api/v1/guest/sessions', { method: 'POST', headers: { 'content-type': 'application/json' }, body: JSON.stringify({ installationSecret: secret }) });
    assert.equal(response.status, 200); return response.value;
  };
  const first = await issue(randomBytes(32).toString('base64url')); const second = await issue(randomBytes(32).toString('base64url'));
  const operationId = randomUUID(); const payload = encodedFixture({ marker: 44 });
  const denied = await app.request('/api/v1/save/adoption', { method: 'POST', headers: { 'content-type': 'application/json', authorization: 'Bearer ' + first.accessToken }, body: JSON.stringify({ operationId, snapshotHash: payload.snapshotHash }) }); assert.equal(denied.status, 403); assert.equal(denied.value.code, 'LOCAL_ADOPTION_APPROVAL_REQUIRED');
  const approved = await w.approve(first.playerId, payload.snapshotHash); assert.equal(approved.event, 'local_copy_approved'); assert.equal(approved.playerId, first.playerId); assert.equal(approved.snapshotHash, payload.snapshotHash);
  const grant = await app.request('/api/v1/save/adoption', { method: 'POST', headers: { 'content-type': 'application/json', authorization: 'Bearer ' + first.accessToken }, body: JSON.stringify({ operationId, snapshotHash: payload.snapshotHash }) });
  assert.equal(grant.status, 200);
  const saved = await app.request('/api/v1/save', { method: 'PUT', headers: { 'content-type': 'application/json', authorization: 'Bearer ' + first.accessToken }, body: JSON.stringify({ operationId, expectedRevision: 0, ...payload, adoptionToken: grant.value.adoptionToken }) });
  assert.equal(saved.status, 200); assert.equal(saved.value.revision, 1);
  const db = new DatabaseSync(w.filename, { readOnly: true });
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 2);
  const committed = db.prepare('SELECT snapshot_hash,snapshot_blob FROM gf_farm_snapshots WHERE player_id=?').get(first.playerId);
  assert.equal(committed.snapshot_hash, payload.snapshotHash); assert.equal(Buffer.from(committed.snapshot_blob).toString('base64'), payload.snapshotBase64); db.close();
  await app.stop();
  app = await w.start(); assert.ok(!app.failed, app.stderr);
  const loaded = await app.request('/api/v1/save', { headers: { authorization: 'Bearer ' + first.accessToken } });
  assert.equal(loaded.status, 200); assert.equal(loaded.value.snapshotBase64, payload.snapshotBase64); assert.equal(loaded.value.operationId, operationId);
  const separate = await app.request('/api/v1/save', { headers: { authorization: 'Bearer ' + second.accessToken } });
  assert.equal(separate.status, 404); assert.equal(separate.value.code, 'NO_SAVE');
  const rendered = app.output(); assert.ok(!rendered.includes(first.accessToken)); assert.ok(!rendered.includes(second.accessToken));
});

test('missing/corrupt SQLite refuses startup before HTTP, preserves damaged bytes and does not create empty replacement', async t => {
  const w = await workspace(t);
  const missing = await w.start(); assert.equal(missing.code, 1); assert.ok(!missing.stdout.includes('listening')); assert.equal(existsSync(w.filename), false);
  const damaged = Buffer.from('retained-corrupt-fixture-database'); writeFileSync(w.filename, damaged);
  const corrupt = await w.start(); assert.equal(corrupt.code, 1); assert.ok(!corrupt.stdout.includes('listening')); assert.deepEqual(readFileSync(w.filename), damaged);
});

test('legacy MySQL configuration cannot silently start a fresh SQLite identity database', async t => {
  const w = await workspace(t); await migrateSqlite(w.filename);
  const refused = await w.start({ DB_NAME: 'existing_legacy_database', DB_USER: 'existing_legacy_user' });
  assert.equal(refused.code, 1); assert.ok(!refused.stdout.includes('listening')); assert.match(refused.stderr, /LEGACY_MIGRATION_REQUIRED/u);
  await migrateSqlite(w.filename, { startNewSqlite: true });
  const explicit = await w.start({ DB_NAME: 'existing_legacy_database', DB_USER: 'existing_legacy_user' }); assert.ok(!explicit.failed, explicit.stderr);
});

test('invalid new-player configuration refuses startup without changing existing database contents', async t => {
  const w = await workspace(t); await migrateSqlite(w.filename, { startNewSqlite: true });
  const configFile = join(w.folder, 'bad-defaults.json'); writeFileSync(configFile, '{"initialCoins":999999999}');
  const failed = await w.start({ NEW_PLAYER_DEFAULTS_FILE: configFile });
  assert.equal(failed.code, 1); assert.ok(!failed.stdout.includes('listening')); assert.match(failed.stderr, /Invalid new-player defaults/u);
  const db = new DatabaseSync(w.filename, { readOnly: true }); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 0); db.close();
});
