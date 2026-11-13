import assert from 'node:assert/strict';
import { mkdtempSync, writeFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import test from 'node:test';
import { normalizeBackendBaseUrl, readBackendBaseUrl, loadConfig, loadDatabaseConfig, loadMysqlDatabaseConfig } from '../src/config.js';

const valid = 'http://10.37.233.55:8787/';

test('base URL accepts only canonical unicast IPv4 origins and normalizes a slash', () => {
  assert.equal(normalizeBackendBaseUrl(valid.slice(0, -1)), valid);
  assert.equal(normalizeBackendBaseUrl('HTTP://10.37.233.55:8787'), valid);
  assert.equal(normalizeBackendBaseUrl('https://192.168.1.20'), 'https://192.168.1.20/');
  for (const value of ['', 'http://localhost:8787/', 'http://127.0.0.1:8787/', 'http://0.0.0.0:8787/', 'http://10.0.2.2:8787/', 'http://169.254.1.3/', 'http://224.0.0.1/', 'http://010.37.233.55/', 'http://192.168.01.1/', 'http://0xc0a80101/', 'http://3232235777/', 'http://[::1]/', 'ftp://192.168.1.20/', 'http://192.168.1.20:0/', 'http://192.168.1.20:65536/', 'http://192.168.1.20/path', 'http://user:pass@192.168.1.20/', 'http://192.168.1.20/?x=1', 'http://192.168.1.20/#fragment', ' http://192.168.1.20/']) {
    assert.throws(() => normalizeBackendBaseUrl(value), /BACKEND_BASE_URL/u, value);
  }
});

test('backend reads the same one root property and fails on missing/duplicate/bad values', t => {
  const directory = mkdtempSync(join(tmpdir(), 'greenfarm-config-'));
  t.after(() => rmSync(directory, { recursive: true }));
  const file = join(directory, 'gradle.properties');
  writeFileSync(file, '# Shared endpoint\nandroid.nonFinalResIds=false\nBACKEND_BASE_URL=' + valid + '\n');
  assert.equal(readBackendBaseUrl(file), valid);
  for (const body of ['', 'localBackendUrl=' + valid, 'BACKEND_BASE_URL=\n', 'BACKEND_BASE_URL=' + valid + '\nBACKEND_BASE_URL=' + valid]) {
    writeFileSync(file, body);
    assert.throws(() => readBackendBaseUrl(file), /BACKEND_BASE_URL/u);
  }
});

test('runtime uses a persistent SQLite path and preserves explicit legacy migration detection', t => {
  const directory = mkdtempSync(join(tmpdir(), 'greenfarm-runtime-config-'));
  t.after(() => rmSync(directory, { recursive: true }));
  const file = join(directory, 'gradle.properties');
  writeFileSync(file, 'BACKEND_BASE_URL=' + valid + '\n');
  const env = { BACKEND_CONFIG_FILE: file, DB_HOST: '127.0.0.1', DB_PORT: '3306', DB_NAME: 'green_farm_test', DB_USER: 'test_app', DB_PASSWORD: 'test-fixture-only', RESEARCH_SESSION_SECRET: 'test-secret-for-config-fixtures-only' };
  const config = loadConfig(env);
  assert.equal(config.host, '0.0.0.0');
  assert.equal(config.port, 8787);
  assert.equal(config.publicBaseUrl, valid);
  for (const key of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL']) assert.throws(() => loadConfig({ ...env, [key]: valid }), /Remove/u);
  assert.throws(() => loadConfig({ ...env, PORT: '8788' }), /PORT must match/u);
  assert.throws(() => loadConfig({ ...env, RESEARCH_SESSION_SECRET: '' }), /RESEARCH_SESSION_SECRET/u);
  assert.equal(config.database.requiresLegacyImport, true);
  assert.match(config.database.filename, /data[/\\]green-farm\.sqlite$/u);
  assert.equal(loadDatabaseConfig({}).requiresLegacyImport, false);
  for (const path of [':memory:', '', 'file:memory', join(tmpdir(), 'temporary.sqlite')]) assert.throws(() => loadDatabaseConfig({ SQLITE_PATH: path }), /SQLITE_PATH/u);
  assert.throws(() => loadMysqlDatabaseConfig({ ...env, DB_PASSWORD: '' }), /DB_PASSWORD/u);
});
