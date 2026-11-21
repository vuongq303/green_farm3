import { readFileSync } from 'node:fs';
import { isIP } from 'node:net';
import { resolve } from 'node:path';
import { parseEnv } from 'node:util';
import { APP_REDIRECT_URI } from './auth/config.js';
import { validPasswordHash } from './auth/passwords.js';
import { normalizeHttpOrigin } from './backend-url.js';

const defaultEnvFile = new URL('../.env', import.meta.url);
const defaultPropertiesFile = new URL('../../gradle.properties', import.meta.url);
const defaultAccessTtl = 900;
const defaultRefreshTtl = 2592000;

export class ConfigurationError extends Error {
  constructor(variable, message) {
    super(message);
    this.name = 'ConfigurationError';
    this.variable = variable;
  }
}

export function configurationDiagnostic(error) {
  const code = 'INVALID_CONFIGURATION';
  return error instanceof ConfigurationError
    ? { code, variable: error.variable, message: error.message }
    : { code, message: 'Cannot initialize backend configuration' };
}

/** Read a fresh snapshot without mutating the process or its injected environment. */
export function readEnvironment({ env = process.env, envFile = defaultEnvFile } = {}) {
  try {
    return { ...parseEnv(readFileSync(envFile, 'utf8')), ...env };
  } catch (error) {
    if (error.code === 'ENOENT') return { ...env };
    throw new ConfigurationError('backend/.env', 'Cannot read or parse backend/.env');
  }
}

export function normalizeBackendBaseUrl(value) {
  try { return normalizeHttpOrigin(value); }
  catch { throw new ConfigurationError('BACKEND_BASE_URL', 'Set BACKEND_BASE_URL in the Gradle properties file to an HTTP(S) origin without credentials, path, query or fragment'); }
}

export function readBackendBaseUrl(propertiesFile = defaultPropertiesFile) {
  let content;
  try { content = readFileSync(propertiesFile, 'utf8'); }
  catch { throw new ConfigurationError('BACKEND_BASE_URL', 'Cannot read BACKEND_BASE_URL from the Gradle properties file. Check BACKEND_CONFIG_FILE if set.'); }
  const values = [];
  for (const line of content.split(/\r?\n/u)) {
    if (/^\s*[#!]/u.test(line)) continue;
    const match = /^\s*BACKEND_BASE_URL(?:\s*[=:]\s*|\s+)(.*?)\s*$/u.exec(line);
    if (match) values.push(match[1]);
  }
  if (values.length !== 1) throw new ConfigurationError('BACKEND_BASE_URL', 'Define BACKEND_BASE_URL exactly once in the Gradle properties file');
  return normalizeBackendBaseUrl(values[0]);
}

function integer(env, key, fallback, min, max) {
  const text = env[key] ?? String(fallback);
  const value = typeof text === 'string' && /^[0-9]+$/u.test(text) ? Number(text) : NaN;
  if (!Number.isSafeInteger(value) || value < min || value > max) {
    throw new ConfigurationError(key, key + ' must be an integer from ' + min + ' through ' + max);
  }
  return value;
}

function required(env, key) {
  if (typeof env[key] !== 'string' || !env[key]) {
    throw new ConfigurationError(key, 'Configure ' + key + ' in backend/.env or the process environment');
  }
  return env[key];
}

function boolean(env, key, fallback) {
  const value = env[key];
  if (value === undefined) return fallback;
  if (value === 'true') return true;
  if (value === 'false') return false;
  throw new ConfigurationError(key, key + ' must be true or false');
}

export function loadDatabaseConfig(env = readEnvironment()) {
  const database = required(env, 'DB_NAME');
  const user = required(env, 'DB_USER');
  const password = required(env, 'DB_PASSWORD');
  if (!/^[A-Za-z0-9_]{1,64}$/u.test(database)) {
    throw new ConfigurationError('DB_NAME', 'DB_NAME must contain 1..64 ASCII letters, digits or underscores');
  }
  const host = env.DB_HOST ?? '127.0.0.1';
  if (typeof host !== 'string' || !host || (isIP(host) === 0 && /[\s\u0000:/@\\?#\[\]]/u.test(host))) {
    throw new ConfigurationError('DB_HOST', 'DB_HOST must be a hostname or IP');
  }
  return {
    host,
    port: integer(env, 'DB_PORT', 3306, 1, 65535),
    database,
    user,
    password,
    connectionLimit: integer(env, 'DB_POOL_SIZE', 5, 1, 50),
    connectTimeout: integer(env, 'DB_CONNECT_TIMEOUT_MS', 5000, 100, 30000),
  };
}

function loadServerConfig(env) {
  for (const deprecated of ['PUBLIC_BASE_URL', 'BIND_HOST', 'BACKEND_BASE_URL']) {
    if (env[deprecated] !== undefined) {
      throw new ConfigurationError(deprecated, 'Remove ' + deprecated + ' from the backend environment. Set BACKEND_BASE_URL in the Gradle properties file and bind the listener with HOST.');
    }
  }
  const propertiesFile = env.BACKEND_CONFIG_FILE ?? '';
  if (typeof propertiesFile !== 'string' || propertiesFile.includes('\0')) {
    throw new ConfigurationError('BACKEND_CONFIG_FILE', 'BACKEND_CONFIG_FILE must be a file path');
  }
  const host = env.HOST ?? '0.0.0.0';
  if (host !== 'localhost' && isIP(host) === 0) {
    throw new ConfigurationError('HOST', 'HOST must be a local IP bind address or localhost');
  }
  return {
    host,
    port: integer(env, 'PORT', 8787, 1, 65535),
    publicBaseUrl: readBackendBaseUrl(propertiesFile ? resolve(propertiesFile) : defaultPropertiesFile),
  };
}

function loadLegacySessions(env) {
  const production = env.NODE_ENV === 'production';
  if (!boolean(env, 'LEGACY_AUTH_ENABLED', !production)) return null;
  if (production) {
    throw new ConfigurationError('LEGACY_AUTH_ENABLED', 'LEGACY_AUTH_ENABLED cannot be enabled in production');
  }
  const secret = required(env, 'RESEARCH_SESSION_SECRET');
  if (Buffer.byteLength(secret) < 32) {
    throw new ConfigurationError('RESEARCH_SESSION_SECRET', 'RESEARCH_SESSION_SECRET must contain at least 32 bytes');
  }
  const accessTtlSeconds = integer(env, 'SESSION_TTL_SECONDS', defaultAccessTtl, 1, 86400);
  const refreshTtlSeconds = integer(env, 'REFRESH_TTL_SECONDS', defaultRefreshTtl, 1, 7776000);
  if (refreshTtlSeconds < accessTtlSeconds) {
    throw new ConfigurationError('REFRESH_TTL_SECONDS', 'REFRESH_TTL_SECONDS must be at least SESSION_TTL_SECONDS');
  }
  return { secret, accessTtlSeconds, refreshTtlSeconds };
}

function httpsOrigin(value) {
  try {
    const url = new URL(normalizeHttpOrigin(value));
    if (url.protocol !== 'https:') throw new Error();
    return url.origin;
  } catch {
    throw new ConfigurationError('AUTH_PUBLIC_BASE_URL', 'AUTH_PUBLIC_BASE_URL must be an HTTPS origin without credentials, path or query');
  }
}

function oauthSetting(env, key, max = 512) {
  const value = env[key] ?? '';
  return typeof value === 'string' && value.length <= max && !/[\s\u0000-\u001f\u007f]/u.test(value) ? value : null;
}

function oauthProvider(env, name, publicBaseUrl) {
  const facebook = name === 'facebook';
  const graphDefault = 'v24.0';
  const clientId = oauthSetting(env, facebook ? 'FACEBOOK_APP_ID' : 'GOOGLE_OAUTH_CLIENT_ID');
  const clientSecret = oauthSetting(env, facebook ? 'FACEBOOK_APP_SECRET' : 'GOOGLE_OAUTH_CLIENT_SECRET', 2048);
  const graphVersion = facebook ? oauthSetting(env, 'FACEBOOK_GRAPH_VERSION') : undefined;
  const version = graphVersion === '' ? graphDefault : graphVersion;
  if (!publicBaseUrl || !clientId || !clientSecret ||
      (facebook && (!/^[0-9]+$/u.test(clientId) || !version || !/^v[0-9]{1,2}\.0$/u.test(version)))) {
    // A misconfigured provider is isolated from website/password sign-in.
    return { clientId: '', clientSecret: '', ...(facebook ? { version: graphDefault } : {}),
      enabled: false, configurationError: 'oauth_not_configured' };
  }
  return { clientId, clientSecret, ...(facebook ? { version } : {}), enabled: true };
}

export function loadSocialAuthConfig(env = readEnvironment()) {
  const configuredOrigin = env.AUTH_PUBLIC_BASE_URL ?? '';
  const publicBaseUrl = configuredOrigin === '' ? null : httpsOrigin(configuredOrigin);
  const trustedProxyIp = env.AUTH_TRUSTED_PROXY_IP ?? '';
  if (trustedProxyIp !== '' && !isIP(trustedProxyIp)) {
    throw new ConfigurationError('AUTH_TRUSTED_PROXY_IP', 'AUTH_TRUSTED_PROXY_IP must be one exact proxy IP');
  }
  return {
    publicBaseUrl,
    trustedProxyIp,
    redirectUri: APP_REDIRECT_URI,
    providers: {
      google: oauthProvider(env, 'google', publicBaseUrl),
      facebook: oauthProvider(env, 'facebook', publicBaseUrl),
      password: { enabled: Boolean(publicBaseUrl) },
    },
    attemptTtl: 300,
    codeTtl: 60,
    accessTtl: defaultAccessTtl,
    refreshTtl: defaultRefreshTtl,
  };
}

function loadGiftcodeConfig(env) {
  const hash = env.GIFTCODE_ADMIN_PASSWORD_HASH ?? '';
  if (hash !== '' && !validPasswordHash(hash)) {
    throw new ConfigurationError('GIFTCODE_ADMIN_PASSWORD_HASH',
      'Generate GIFTCODE_ADMIN_PASSWORD_HASH with node scripts/hash-giftcode-admin-password.js');
  }
  return { adminPasswordHash: hash || undefined, adminSessionTtl: 3600 };
}

/** Each entrypoint builds one config and passes it to its dependencies. */
export function loadConfig(env = readEnvironment()) {
  return {
    database: loadDatabaseConfig(env),
    server: loadServerConfig(env),
    sessions: { legacy: loadLegacySessions(env) },
    auth: loadSocialAuthConfig(env),
    giftcodes: loadGiftcodeConfig(env),
  };
}
