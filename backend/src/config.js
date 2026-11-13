import { readFileSync, existsSync } from 'node:fs';
import { isIP } from 'node:net';
import { resolve, relative, isAbsolute, sep } from 'node:path';
import { tmpdir } from 'node:os';
import { fileURLToPath } from 'node:url';

const backendDirectory = fileURLToPath(new URL('..', import.meta.url));
const defaultPropertiesFile = fileURLToPath(new URL('../../gradle.properties', import.meta.url));

/** Loads backend-only secrets. Existing environment values take precedence. */
export function loadEnvironment(file = resolve(backendDirectory, '.env')) {
  if (existsSync(file)) process.loadEnvFile(file);
}

export function normalizeBackendBaseUrl(value) {
  const error = () => new Error('Set BACKEND_BASE_URL in root gradle.properties to http(s)://<LAN IPv4>:<port>/ (no credentials, path, query or fragment)');
  if (typeof value !== 'string' || !value || value !== value.trim() || /\s/u.test(value)) throw error();
  // Reject ambiguous IPv4 spellings, including octal/hex/integer URL aliases.
  const match = /^(https?):\/\/([0-9]+\.[0-9]+\.[0-9]+\.[0-9]+)(?::([0-9]+))?\/?$/iu.exec(value);
  if (!match || isIP(match[2]) !== 4) throw error();
  const octets = match[2].split('.').map(Number);
  if (octets.some((part, index) => String(part) !== match[2].split('.')[index]) ||
      octets[0] === 0 || octets[0] === 127 || octets[0] >= 224 ||
      (octets[0] === 169 && octets[1] === 254) || match[2] === '10.0.2.2') throw error();
  if (match[3] && (!/^[1-9][0-9]*$/u.test(match[3]) || Number(match[3]) > 65535)) throw error();
  const url = new URL(value);
  return url.origin + '/';
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
  catch { throw new Error('Invalid BACKEND_BASE_URL in ' + propertiesFile + '; use http(s)://<LAN IPv4>:<port>/'); }
}

function integer(env, key, fallback, min, max) {
  const text = env[key] ?? String(fallback);
  if (!/^[0-9]+$/u.test(text) || !Number.isSafeInteger(Number(text)) || Number(text) < min || Number(text) > max) {
    throw new Error(key + ' must be an integer from ' + min + ' through ' + max);
  }
  return Number(text);
}

export function loadMysqlDatabaseConfig(env = process.env) {
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

/** Runtime storage. MySQL configuration is read only by explicit legacy tools. */
export function loadDatabaseConfig(env = process.env) {
  const configured = env.SQLITE_PATH ?? './data/green-farm.sqlite';
  if (typeof configured !== 'string' || !configured.trim() || configured !== configured.trim() || configured.includes('\u0000') || configured === ':memory:' || configured.startsWith('file:')) {
    throw new Error('SQLITE_PATH must name a persistent SQLite file');
  }
  const filename = resolve(backendDirectory, configured);
  const temporary = resolve(tmpdir());
  // path.relative follows the host filesystem's Windows drive/case rules.
  const fromTemporary = relative(temporary, filename);
  if (!fromTemporary || (!isAbsolute(fromTemporary) && fromTemporary !== '..' && !fromTemporary.startsWith('..' + sep))) throw new Error('SQLITE_PATH must not be in the system temporary directory');
  return { filename, busyTimeoutMs: integer(env, 'SQLITE_BUSY_TIMEOUT_MS', 100, 1, 1000), requiresLegacyImport: Boolean(env.DB_NAME || env.DB_USER) };
}

export function loadConfig(env = process.env) {
  for (const deprecated of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL']) {
    if (env[deprecated] !== undefined) throw new Error('Remove ' + deprecated + ' from backend environment; URL comes only from root gradle.properties and listener uses HOST');
  }
  const publicBaseUrl = readBackendBaseUrl(env.BACKEND_CONFIG_FILE ? resolve(env.BACKEND_CONFIG_FILE) : defaultPropertiesFile);
  const host = env.HOST ?? '0.0.0.0';
  if (host !== '0.0.0.0' && isIP(host) !== 4) throw new Error('HOST must be an IPv4 bind address');
  const port = integer(env, 'PORT', 8787, 1, 65535);
  const url = new URL(publicBaseUrl);
  const advertisedPort = Number(url.port || (url.protocol === 'https:' ? 443 : 80));
  if (advertisedPort !== port) throw new Error('PORT must match the port in BACKEND_BASE_URL in root gradle.properties');
  if (url.protocol !== 'http:') throw new Error('This direct Node listener uses HTTP; HTTPS requires an explicit TLS/reverse-proxy setup before use');
  const sessionSecret = env.RESEARCH_SESSION_SECRET;
  if (!sessionSecret || Buffer.byteLength(sessionSecret) < 32) throw new Error('Set a stable RESEARCH_SESSION_SECRET of at least 32 bytes in backend/.env');
  if (env.NODE_ENV === 'production') throw new Error('Legacy anonymous compatibility authentication is not production account ownership');
  const sessionTtlSeconds = integer(env, 'SESSION_TTL_SECONDS', 900, 1, 86400);
  const refreshTtlSeconds = integer(env, 'REFRESH_TTL_SECONDS', 2592000, 1, 7776000);
  if (refreshTtlSeconds < sessionTtlSeconds) throw new Error('REFRESH_TTL_SECONDS must be at least SESSION_TTL_SECONDS');
  const guestSessionTtlSeconds = integer(env, 'GUEST_SESSION_TTL_SECONDS', 2592000, 60, 7776000);
  const maxSnapshotBytes = integer(env, 'MAX_SNAPSHOT_BYTES', 16 * 1024 * 1024, 65536, 256 * 1024 * 1024);
  const newPlayerDefaultsFile = resolve(backendDirectory, env.NEW_PLAYER_DEFAULTS_FILE ?? './config/new_player_defaults.json');
  return { host, port, publicBaseUrl, sessionSecret, sessionTtlSeconds, refreshTtlSeconds, guestSessionTtlSeconds, maxSnapshotBytes, newPlayerDefaultsFile, database: loadDatabaseConfig(env) };
}
