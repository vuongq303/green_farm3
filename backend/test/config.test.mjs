import assert from 'node:assert/strict';
import { mkdtempSync, writeFileSync, rmSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import test from 'node:test';
import { normalizeBackendBaseUrl, readBackendBaseUrl, loadConfig, loadDatabaseConfig } from '../src/config.js';

const valid = 'http://10.37.233.55:8787/';

test('base URL accepts HTTP(S) DNS, IPv4 and IPv6 origins without ambiguous URL syntax', () => {
  assert.equal(normalizeBackendBaseUrl(valid.slice(0, -1)), valid);
  assert.equal(normalizeBackendBaseUrl('HTTP://10.37.233.55:8787'), valid);
  assert.equal(normalizeBackendBaseUrl('https://192.168.1.20'), 'https://192.168.1.20/');
  for (const [input, expected] of [
    ['https://ipaq-gentle-acquisition-sacred.trycloudflare.com', 'https://ipaq-gentle-acquisition-sacred.trycloudflare.com/'],
    ['HTTP://LOCALHOST:8787/', 'http://localhost:8787/'],
    ['http://greenfarm-dev:8787', 'http://greenfarm-dev:8787/'],
    ['https://auth.example.com:9443', 'https://auth.example.com:9443/'],
    ['http://127.0.0.1:8787', 'http://127.0.0.1:8787/'],
    ['http://10.0.2.2:8787', 'http://10.0.2.2:8787/'],
    ['http://0.0.0.0:8787', 'http://0.0.0.0:8787/'],
    ['http://[::1]:8787', 'http://[::1]:8787/'],
    ['https://[2001:0db8:0000:0000:0000:0000:0000:0001]:9443', 'https://[2001:db8::1]:9443/'],
    ['https://tênmiền.vn', 'https://xn--tnmin-hsa0954c.vn/'],
  ]) assert.equal(normalizeBackendBaseUrl(input), expected);
  for (const value of ['', 'http://010.37.233.55/', 'http://192.168.01.1/', 'http://0xc0a80101/', 'http://3232235777/', 'ftp://192.168.1.20/', 'http://192.168.1.20:0/', 'http://192.168.1.20:65536/', 'http://192.168.1.20/path', 'http://user:pass@192.168.1.20/', 'http://192.168.1.20/?x=1', 'http://192.168.1.20/#fragment', ' http://192.168.1.20/', 'https://example.com:/', 'https://example.com:abc/', 'https://example..com/', 'https://-example.com/', 'https://example.com\\evil/', 'https://%65xample.com/']) {
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

test('runtime config keeps local port independent and validates secrets and MySQL credentials', t => {
  const directory = mkdtempSync(join(tmpdir(), 'greenfarm-runtime-config-'));
  t.after(() => rmSync(directory, { recursive: true }));
  const file = join(directory, 'gradle.properties');
  writeFileSync(file, 'BACKEND_BASE_URL=' + valid + '\n');
  const env = { BACKEND_CONFIG_FILE: file, DB_HOST: '127.0.0.1', DB_PORT: '3306', DB_NAME: 'green_farm_test', DB_USER: 'test_app', DB_PASSWORD: 'test-fixture-only', RESEARCH_SESSION_SECRET: 'test-secret-for-config-fixtures-only' };
  const config = loadConfig(env);
  assert.equal(config.host, '0.0.0.0');
  assert.equal(config.port, 8787);
  assert.equal(config.publicBaseUrl, valid);
  assert.equal(config.database.password, env.DB_PASSWORD, 'Database password must come from environment');
  for (const key of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL']) assert.throws(() => loadConfig({ ...env, [key]: valid }), /Remove/u);
  assert.equal(loadConfig({ ...env, PORT: '8788' }).port, 8788);
  assert.throws(() => loadConfig({ ...env, PORT: '0' }), /PORT must be/u);
  assert.throws(() => loadConfig({ ...env, RESEARCH_SESSION_SECRET: '' }), /RESEARCH_SESSION_SECRET/u);
  assert.throws(() => loadDatabaseConfig({ ...env, DB_PASSWORD: '' }), /DB_PASSWORD/u);
  assert.equal(loadConfig({ ...env, NODE_ENV: 'production' }).legacyAuthEnabled, false);
  assert.throws(() => loadConfig({ ...env, NODE_ENV: 'production', LEGACY_AUTH_ENABLED: 'true' }), /cannot be enabled in production/u);
});

test('public HTTPS tunnel origin is independent of the local Node bind address and port', t => {
  const directory = mkdtempSync(join(tmpdir(), 'greenfarm-tunnel-config-'));
  t.after(() => rmSync(directory, { recursive: true }));
  const file = join(directory, 'gradle.properties');
  const origin = 'https://ipaq-gentle-acquisition-sacred.trycloudflare.com';
  writeFileSync(file, 'BACKEND_BASE_URL=' + origin + '/\n');
  const env = { BACKEND_CONFIG_FILE: file, HOST: '127.0.0.1', PORT: '8787', DB_NAME: 'green_farm_test',
    DB_USER: 'fixture', DB_PASSWORD: 'fixture-password', RESEARCH_SESSION_SECRET: 'stable-test-session-secret-at-least-32-bytes',
    AUTH_PUBLIC_BASE_URL: origin, LEGACY_AUTH_ENABLED: 'false' };
  const config = loadConfig(env);
  assert.equal(config.publicBaseUrl, origin + '/');
  assert.equal(config.auth.publicBaseUrl, origin);
  assert.equal(config.auth.providers.password.enabled, true);
  assert.equal(config.host, '127.0.0.1');
  assert.equal(config.port, 8787);
  assert.equal(loadConfig({ ...env, HOST: '::1' }).host, '::1');
  assert.equal(loadConfig({ ...env, HOST: 'localhost' }).host, 'localhost');
});
