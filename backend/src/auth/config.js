import { isIP } from 'node:net';

export const APP_REDIRECT_URI = 'com.quanhoang.greenfarm3://oauth/callback';

function normalizedIp(value) { return value?.startsWith('::ffff:') ? value.slice(7) : value; }
function isLoopback(value) { return value === '127.0.0.1' || value === '::1'; }

export function trustedProxy(request, config) {
  const peer = normalizedIp(request.socket.remoteAddress);
  const configured = normalizedIp(config?.trustedProxyIp);
  // cloudflared connecting to localhost may use either IPv4 or IPv6.
  // Only trust loopback when the operator explicitly configured a loopback proxy.
  return Boolean(configured && (peer === configured || (isLoopback(peer) && isLoopback(configured))));
}

export function secureAuthRequest(request, config) {
  return request.socket.encrypted === true || (trustedProxy(request, config) && request.headers['x-forwarded-proto'] === 'https');
}

export function authClientIp(request, config) {
  const forwarded = request.headers['x-forwarded-for'];
  if (trustedProxy(request, config) && typeof forwarded === 'string' && isIP(forwarded)) return forwarded;
  return normalizedIp(request.socket.remoteAddress) ?? 'unknown';
}
