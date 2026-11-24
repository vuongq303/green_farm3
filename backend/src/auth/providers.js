import { createHash, createHmac, createPublicKey, timingSafeEqual, verify } from 'node:crypto';

export class ProviderError extends Error {
  constructor(code = 'provider_unavailable') { super(code); this.code = code; }
}

const GOOGLE_TOKEN = 'https://oauth2.googleapis.com/token';
const GOOGLE_KEYS = 'https://www.googleapis.com/oauth2/v3/certs';
const boundedString = (value, max) => typeof value === 'string' && value.length > 0 && value.length <= max && !/[\u0000-\u001f\u007f]/u.test(value);
const equalText = (a, b) => typeof a === 'string' && typeof b === 'string' &&
  Buffer.byteLength(a) === Buffer.byteLength(b) && timingSafeEqual(Buffer.from(a), Buffer.from(b));

/** Outbound destinations are fixed provider endpoints; user input cannot select a URL. */
export function createProviderClient({ fetchImpl = fetch, now = Date.now } = {}) {
  let googleKeys;
  let keysUntil = 0;

  async function json(url, options = {}) {
    let response;
    try {
      response = await fetchImpl(url, { ...options, redirect: 'error', signal: AbortSignal.timeout(10000) });
      let size = 0;
      const chunks = [];
      for await (const chunk of response.body ?? []) {
        size += chunk.length;
        if (size > 131072) throw new Error();
        chunks.push(chunk);
      }
      const value = JSON.parse(Buffer.concat(chunks).toString('utf8'));
      if (!response.ok || !value || typeof value !== 'object' || Array.isArray(value)) throw new Error();
      return value;
    } catch { throw new ProviderError(); }
  }

  async function googleIdentity(code, attempt, config) {
    const reply = await json(GOOGLE_TOKEN, { method: 'POST', headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ code, client_id: config.providers.google.clientId,
        client_secret: config.providers.google.clientSecret, redirect_uri: config.publicBaseUrl + '/auth/callback/google',
        grant_type: 'authorization_code', code_verifier: attempt.providerVerifier }).toString() });
    if (!boundedString(reply.id_token, 16384)) throw new ProviderError('provider_identity_invalid');
    const parts = reply.id_token.split('.');
    if (parts.length !== 3 || parts.some(part => !/^[A-Za-z0-9_-]+$/u.test(part))) throw new ProviderError('provider_identity_invalid');
    let header, claims;
    try {
      header = JSON.parse(Buffer.from(parts[0], 'base64url').toString('utf8'));
      claims = JSON.parse(Buffer.from(parts[1], 'base64url').toString('utf8'));
    } catch { throw new ProviderError('provider_identity_invalid'); }
    if (!header || typeof header !== 'object' || Array.isArray(header) || !claims || typeof claims !== 'object' ||
        Array.isArray(claims) || header.alg !== 'RS256' || !boundedString(header.kid, 128)) throw new ProviderError('provider_identity_invalid');
    async function keys(refresh = false) {
      if (refresh || !googleKeys || now() >= keysUntil) {
        const result = await json(GOOGLE_KEYS);
        if (!Array.isArray(result.keys) || result.keys.length > 20) throw new ProviderError();
        googleKeys = result.keys;
        keysUntil = now() + 3600000;
      }
      return googleKeys;
    }
    let key = (await keys()).find(candidate => candidate.kid === header.kid);
    if (!key) key = (await keys(true)).find(candidate => candidate.kid === header.kid);
    if (!key || key.kty !== 'RSA' || (key.alg && key.alg !== 'RS256') || (key.use && key.use !== 'sig')) throw new ProviderError('provider_identity_invalid');
    let signatureValid = false;
    try { signatureValid = verify('RSA-SHA256', Buffer.from(parts[0] + '.' + parts[1]), createPublicKey({ key, format: 'jwk' }), Buffer.from(parts[2], 'base64url')); }
    catch { /* malformed provider key/signature is rejected */ }
    const seconds = Math.floor(now() / 1000);
    const clientId = config.providers.google.clientId;
    const audience = Array.isArray(claims.aud) ? claims.aud.includes(clientId) : claims.aud === clientId;
    if (!signatureValid || !['accounts.google.com', 'https://accounts.google.com'].includes(claims.iss) ||
        !audience || (Array.isArray(claims.aud) && claims.aud.length > 1 && claims.azp !== clientId) ||
        (claims.azp !== undefined && claims.azp !== clientId) || !Number.isSafeInteger(claims.exp) || claims.exp <= seconds ||
        !Number.isSafeInteger(claims.iat) || claims.iat > seconds + 60 || seconds - claims.iat > 600 ||
        !equalText(claims.nonce, attempt.nonce) || !boundedString(claims.sub, 255)) throw new ProviderError('provider_identity_invalid');
    return { providerUserId: claims.sub, displayName: boundedString(claims.name, 160) ? claims.name : null };
  }

  async function facebookIdentity(code, attempt, config) {
    const provider = config.providers.facebook;
    const root = 'https://graph.facebook.com/' + provider.version;
    const reply = await json(root + '/oauth/access_token', { method: 'POST',
      headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
      body: new URLSearchParams({ client_id: provider.clientId, client_secret: provider.clientSecret,
        redirect_uri: config.publicBaseUrl + '/auth/callback/facebook', code }).toString() });
    if (!boundedString(reply.access_token, 8192)) throw new ProviderError('provider_identity_invalid');
    const debug = new URL(root + '/debug_token');
    debug.searchParams.set('input_token', reply.access_token);
    // App access token is sent in an HTTPS header, not exposed to browser/app.
    const checked = await json(debug, { headers: { Authorization: 'Bearer ' + provider.clientId + '|' + provider.clientSecret } });
    const data = checked.data;
    const seconds = Math.floor(now() / 1000);
    if (!data || data.is_valid !== true || String(data.app_id) !== provider.clientId ||
        !boundedString(data.user_id, 255) || !Number.isSafeInteger(data.expires_at) || data.expires_at <= seconds ||
        (data.data_access_expires_at !== undefined && (!Number.isSafeInteger(data.data_access_expires_at) ||
          (data.data_access_expires_at !== 0 && data.data_access_expires_at <= seconds)))) {
      throw new ProviderError('provider_identity_invalid');
    }
    const me = new URL(root + '/me');
    me.searchParams.set('fields', 'id,name');
    me.searchParams.set('appsecret_proof', createHmac('sha256', provider.clientSecret).update(reply.access_token).digest('hex'));
    const identity = await json(me, { headers: { Authorization: 'Bearer ' + reply.access_token } });
    if (!equalText(identity.id, data.user_id)) throw new ProviderError('provider_identity_invalid');
    return { providerUserId: identity.id, displayName: boundedString(identity.name, 160) ? identity.name : null };
  }

  return {
    authorizationUrl(provider, attempt, oauthState, config) {
      const google = provider === 'google';
      const url = new URL(google ? 'https://accounts.google.com/o/oauth2/v2/auth' :
        'https://www.facebook.com/' + config.providers.facebook.version + '/dialog/oauth');
      url.searchParams.set('client_id', config.providers[provider].clientId);
      url.searchParams.set('redirect_uri', config.publicBaseUrl + '/auth/callback/' + provider);
      url.searchParams.set('response_type', 'code');
      url.searchParams.set('scope', google ? 'openid profile' : 'public_profile');
      url.searchParams.set('state', oauthState);
      if (google) {
        url.searchParams.set('prompt', 'select_account');
        url.searchParams.set('nonce', attempt.nonce);
        url.searchParams.set('code_challenge', createHash('sha256').update(attempt.providerVerifier).digest('base64url'));
        url.searchParams.set('code_challenge_method', 'S256');
      }
      return url.toString();
    },
    verifyIdentity(provider, code, attempt, config) {
      return provider === 'google' ? googleIdentity(code, attempt, config) : facebookIdentity(code, attempt, config);
    },
  };
}
