import { isIP } from 'node:net';
import { normalizeHttpOrigin } from '../backend-url.js';

export const APP_REDIRECT_URI = 'com.quanhoang.greenfarm3://oauth/callback';

function httpsOrigin(value) {
  try {
    const url = new URL(normalizeHttpOrigin(value));
    if (url.protocol !== 'https:') throw new Error();
    return url.origin;
  } catch { throw new Error('AUTH_PUBLIC_BASE_URL must be an HTTPS origin without credentials, path or query'); }
}

function setting(env, key, max = 512) {
  const value = env[key] ?? '';
  if (typeof value !== 'string' || value.length > max || /[\s\u0000-\u001f\u007f]/u.test(value)) {
    throw new Error('Invalid ' + key);
  }
  return value;
}

/** Optional OAuth configuration belongs to its provider. An incomplete or
 * invalid Facebook/Google setup must not prevent first-party account login or
 * validation of already-issued sessions. Disabled providers retain no secrets. */
function oauthProvider(env, name, publicBaseUrl) {
  const facebook = name === 'facebook';
  const disabled = { clientId: '', clientSecret: '', ...(facebook ? { version: 'v24.0' } : {}),
    enabled: false, configurationError: 'oauth_not_configured' };
  try {
    const clientId = setting(env, facebook ? 'FACEBOOK_APP_ID' : 'GOOGLE_OAUTH_CLIENT_ID');
    const clientSecret = setting(env, facebook ? 'FACEBOOK_APP_SECRET' : 'GOOGLE_OAUTH_CLIENT_SECRET', 2048);
    const version = facebook ? setting(env, 'FACEBOOK_GRAPH_VERSION') || 'v24.0' : undefined;
    if (!publicBaseUrl || !clientId || !clientSecret ||
        (facebook && (!/^[0-9]+$/u.test(clientId) || !/^v[0-9]{1,2}\.0$/u.test(version)))) return disabled;
    return { clientId, clientSecret, ...(facebook ? { version } : {}), enabled: true };
  } catch {
    // Do not surface raw settings in errors/logs. The selected provider returns
    // oauth_not_configured; Website continues through its independent path.
    return disabled;
  }
}

/** These credentials stay in the server environment, never in redirects/APKs. */
export function loadSocialAuthConfig(env = process.env) {
  const publicBaseUrl = env.AUTH_PUBLIC_BASE_URL ? httpsOrigin(env.AUTH_PUBLIC_BASE_URL) : null;
  const trustedProxyIp = env.AUTH_TRUSTED_PROXY_IP ?? '';
  if (trustedProxyIp && !isIP(trustedProxyIp)) throw new Error('AUTH_TRUSTED_PROXY_IP must be one exact proxy IP');
  const google = oauthProvider(env, 'google', publicBaseUrl);
  const facebook = oauthProvider(env, 'facebook', publicBaseUrl);
  return { publicBaseUrl, trustedProxyIp, redirectUri: APP_REDIRECT_URI,
    providers: { google, facebook, password: { enabled: Boolean(publicBaseUrl) } },
    attemptTtl: 300, codeTtl: 60, accessTtl: 900, refreshTtl: 2592000 };
}

function normalizedIp(value) { return value?.startsWith('::ffff:') ? value.slice(7) : value; }

export function trustedProxy(request, config) {
  return Boolean(config.trustedProxyIp && normalizedIp(request.socket.remoteAddress) === normalizedIp(config.trustedProxyIp));
}

export function secureAuthRequest(request, config) {
  return request.socket.encrypted === true || (trustedProxy(request, config) && request.headers['x-forwarded-proto'] === 'https');
}

export function authClientIp(request, config) {
  const forwarded = request.headers['x-forwarded-for'];
  if (trustedProxy(request, config) && typeof forwarded === 'string' && isIP(forwarded)) return forwarded;
  return normalizedIp(request.socket.remoteAddress) ?? 'unknown';
}
