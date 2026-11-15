import { isIP } from 'node:net';

export const APP_REDIRECT_URI = 'com.quanhoang.greenfarm3://oauth/callback';

function httpsOrigin(value) {
  try {
    const url = new URL(value);
    if (url.protocol !== 'https:' || url.username || url.password || url.search || url.hash ||
        (url.pathname !== '/' && url.pathname !== '') || !url.hostname ||
        ['localhost', '0.0.0.0', '127.0.0.1', '[::1]'].includes(url.hostname)) throw new Error();
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

/** These credentials stay in the server environment, never in redirects/APKs. */
export function loadSocialAuthConfig(env = process.env) {
  const publicBaseUrl = env.AUTH_PUBLIC_BASE_URL ? httpsOrigin(env.AUTH_PUBLIC_BASE_URL) : null;
  const trustedProxyIp = env.AUTH_TRUSTED_PROXY_IP ?? '';
  if (trustedProxyIp && !isIP(trustedProxyIp)) throw new Error('AUTH_TRUSTED_PROXY_IP must be one exact proxy IP');
  const google = { clientId: setting(env, 'GOOGLE_OAUTH_CLIENT_ID'), clientSecret: setting(env, 'GOOGLE_OAUTH_CLIENT_SECRET', 2048) };
  const facebook = { clientId: setting(env, 'FACEBOOK_APP_ID'), clientSecret: setting(env, 'FACEBOOK_APP_SECRET', 2048),
    version: setting(env, 'FACEBOOK_GRAPH_VERSION') || 'v24.0' };
  if (!/^v[0-9]{1,2}\.0$/u.test(facebook.version)) throw new Error('Invalid FACEBOOK_GRAPH_VERSION');
  if (facebook.clientId && !/^[0-9]+$/u.test(facebook.clientId)) throw new Error('Invalid FACEBOOK_APP_ID');
  for (const [provider, config] of Object.entries({ google, facebook })) {
    if (Boolean(config.clientId) !== Boolean(config.clientSecret)) throw new Error('Configure both ' + provider + ' OAuth client ID and secret');
    config.enabled = Boolean(publicBaseUrl && config.clientId && config.clientSecret);
  }
  return { publicBaseUrl, trustedProxyIp, redirectUri: APP_REDIRECT_URI,
    providers: { google, facebook }, attemptTtl: 300, codeTtl: 60, accessTtl: 900, refreshTtl: 2592000 };
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
