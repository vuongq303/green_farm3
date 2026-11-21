import { readFileSync, existsSync } from 'node:fs';
import { isIP } from 'node:net';
import { resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { loadSocialAuthConfig } from './auth/config.js';
import { validPasswordHash } from './auth/passwords.js';
import { normalizeHttpOrigin } from './backend-url.js';

const backendDirectory = fileURLToPath(new URL('..', import.meta.url));
const defaultPropertiesFile = fileURLToPath(new URL('../../gradle.properties', import.meta.url));

/** Loads backend-only secrets. Existing environment values take precedence. */
export function loadEnvironment(file = resolve(backendDirectory, '.env')) {
  if (existsSync(file)) process.loadEnvFile(file);
}

export function normalizeBackendBaseUrl(value) {
  try { return normalizeHttpOrigin(value); }
  catch { throw new Error('Set BACKEND_BASE_URL in root gradle.properties to an HTTP(S) origin with a hostname, IPv4 or bracketed IPv6 and optional port; no credentials/path/query/fragment'); }
}

export function readBackendBaseUrl(propertiesFile = defaultPropertiesFile) {
  let content;
  try { content = readFileSync(propertiesFile, 'utf8'); }
  catch { throw new Error('Cannot read BACKEND_BASE_URL from configuration file: ' + propertiesFile); }
  const values = [];
  for (const line of content.split(/\r?\n/u)) {
    if (/^\s*[#!]/u.test(line)) continue;
    const match = /^\s*BACKEND_BASE_URL(?:\s*[=:]\s*|\s+)(.*?)\s*$/u.exec(line);
    if (match) values.push(match[1]);
  }
  if (values.length !== 1) throw new Error('Define BACKEND_BASE_URL exactly once in ' + propertiesFile);
  try { return normalizeBackendBaseUrl(values[0]); }
  catch { throw new Error('Invalid BACKEND_BASE_URL in ' + propertiesFile + '; use an HTTP(S) hostname, IPv4 or bracketed IPv6 origin'); }
}

function integer(env, key, fallback, min, max) {
  const text = env[key] ?? String(fallback);
  if (!/^[0-9]+$/u.test(text) || !Number.isSafeInteger(Number(text)) || Number(text) < min || Number(text) > max) {
    throw new Error(key + ' must be an integer from ' + min + ' through ' + max);
  }
  return Number(text);
}

export function loadDatabaseConfig(env = process.env) {
  for (const key of ['DB_NAME', 'DB_USER', 'DB_PASSWORD']) {
    if (typeof env[key] !== 'string' || !env[key]) throw new Error('Configure ' + key + ' in backend/.env');
  }
  if (!/^[A-Za-z0-9_]+$/u.test(env.DB_NAME) || env.DB_NAME.length > 64) throw new Error('DB_NAME must contain 1..64 ASCII letters, digits or underscores');
  const host = env.DB_HOST ?? '127.0.0.1';
  if (!host || /[\s\u0000]/u.test(host)) throw new Error('DB_HOST must be a hostname or IP');
  return {
    host,
    port: integer(env, 'DB_PORT', 3306, 1, 65535),
    database: env.DB_NAME,
    user: env.DB_USER,
    password: env.DB_PASSWORD,
    connectionLimit: integer(env, 'DB_POOL_SIZE', 5, 1, 50),
    connectTimeout: integer(env, 'DB_CONNECT_TIMEOUT_MS', 5000, 100, 30000),
  };
}

export function loadConfig(env = process.env) {
  for (const deprecated of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL']) {
    if (env[deprecated] !== undefined) throw new Error('Remove ' + deprecated + ' from backend environment; URL comes only from root gradle.properties and listener uses HOST');
  }
  const publicBaseUrl = readBackendBaseUrl(env.BACKEND_CONFIG_FILE ? resolve(env.BACKEND_CONFIG_FILE) : defaultPropertiesFile);
  const host = env.HOST ?? '0.0.0.0';
  if (host !== 'localhost' && isIP(host) === 0) throw new Error('HOST must be a local IP bind address or localhost');
  const port = integer(env, 'PORT', 8787, 1, 65535);
  // The public origin can belong to a proxy or tunnel; PORT only binds local Node.
  const sessionSecret = env.RESEARCH_SESSION_SECRET;
  if (!sessionSecret || Buffer.byteLength(sessionSecret) < 32) throw new Error('Set a stable RESEARCH_SESSION_SECRET of at least 32 bytes in backend/.env');
  const legacyText = env.LEGACY_AUTH_ENABLED ?? (env.NODE_ENV === 'production' ? 'false' : 'true');
  if (!['true', 'false'].includes(legacyText)) throw new Error('LEGACY_AUTH_ENABLED must be true or false');
  const legacyAuthEnabled = legacyText === 'true';
  if (env.NODE_ENV === 'production' && legacyAuthEnabled) throw new Error('Legacy anonymous authentication cannot be enabled in production');
  const sessionTtlSeconds = integer(env, 'SESSION_TTL_SECONDS', 900, 1, 86400);
  const refreshTtlSeconds = integer(env, 'REFRESH_TTL_SECONDS', 2592000, 1, 7776000);
  if (refreshTtlSeconds < sessionTtlSeconds) throw new Error('REFRESH_TTL_SECONDS must be at least SESSION_TTL_SECONDS');
  const giftcodeAdminPasswordHash = env.GIFTCODE_ADMIN_PASSWORD_HASH || undefined;
  if (giftcodeAdminPasswordHash && !validPasswordHash(giftcodeAdminPasswordHash)) {
    throw new Error('Generate GIFTCODE_ADMIN_PASSWORD_HASH with node scripts/hash-giftcode-admin-password.js');
  }
  return { host, port, publicBaseUrl, sessionSecret, sessionTtlSeconds, refreshTtlSeconds, database: loadDatabaseConfig(env),
    legacyAuthEnabled, auth: loadSocialAuthConfig(env), giftcodeAdminPasswordHash };
}
