import { createServer } from 'node:http';
import { HttpError, respond, form, parsePairs, required, routeSegments, validateClientPath } from './http.js';
import { createSessionService } from './services/sessions.js';
import { normalizeBackendBaseUrl } from './config.js';

const DATACENTER_NAME = 'local';
const DATACENTER_ID = 'local-research';
const LEGACY_SCOPES = new Set(['storage', 'auth', 'config', 'asset', 'transaction', 'message', 'leaderboard', 'social']);
const URL_KEYS = [
  'pandora', 'gllive-ope', 'tapjoy', 'offline_items', 'game_portal',
  'status', 'crm_iap', 'ads_agency', 'marketing_site', 'online_connectivity',
];

/** Original HTTP contracts, with an explicitly supplied persistent repository. */
export function createResearchServer(options) {
  const baseUrl = normalizeBackendBaseUrl(options.publicBaseUrl).replace(/\/$/u, '');
  const store = options.store;
  if (!store || ['health', 'createSession', 'refreshSession', 'findSession', 'saveCompliance'].some(name => typeof store[name] !== 'function')) {
    throw new Error('A MySQL repository is required; there is no memory/file fallback');
  }
  const now = options.now ?? Date.now;
  const sessions = createSessionService({ store, secret: options.sessionSecret, ttl: options.sessionTtlSeconds ?? 900, refreshTtl: options.refreshTtlSeconds ?? 2592000, now });
  const log = options.log ?? (entry => console.log(JSON.stringify(entry)));
  const errorLog = options.errorLog ?? (entry => console.error(JSON.stringify(entry)));

  const server = createServer((request, response) => {
    let path = '[unmatched]';
    response.once('finish', () => {
      // Route templates deliberately exclude client IDs, queries and form values.
      try {
        log({ method: request.method ?? 'UNKNOWN', path, status: response.statusCode });
      } catch {
        // A failed log sink must not alter a completed protocol response.
      }
    });

    async function handle() {
      if (!request.url?.startsWith('/') || request.url.startsWith('//')) {
        throw new HttpError(400, 'invalid_path');
      }
      const url = new URL(request.url, 'http://local.invalid');
      const parts = routeSegments(url.pathname);
      let expectedMethod;

      if (parts.length === 1 && ['health', 'healthz'].includes(parts[0])) {
        path = '/' + parts[0];
        expectedMethod = 'GET';
      } else if (parts.length === 3 && parts[0] === 'config' && parts[2] === 'datacenters') {
        path = '/config/:client/datacenters';
        validateClientPath(parts[1]);
        expectedMethod = 'GET';
      } else if (parts.length === 5 && parts[0] === 'config' && parts[2] === 'datacenters' && parts[4] === 'urls') {
        path = '/config/:client/datacenters/:name/urls';
        validateClientPath(parts[1]);
        if (parts[3] !== DATACENTER_NAME) throw new HttpError(404, 'unknown_datacenter');
        expectedMethod = 'GET';
      } else if (parts.length === 2 && parts[0] === 'locate' && ['auth', 'storage'].includes(parts[1])) {
        path = '/locate/' + parts[1];
        expectedMethod = 'GET';
      } else if (parts.length === 3 && parts[0] === 'profiles' && parts[1] === 'me' && parts[2] === 'myprofile') {
        path = '/profiles/me/myprofile';
        expectedMethod = 'GET';
      } else if (parts.length === 1 && parts[0] === 'authorize') {
        path = '/authorize';
        expectedMethod = 'POST';
      } else if (parts.length === 3 && parts[0] === 'users' && parts[1] === 'me' && parts[2] === 'compliance') {
        path = '/users/me/compliance';
        expectedMethod = 'POST';
      } else if (parts[0] === 'unsupported' ||
                 (parts.length === 2 && parts[0] === 'locate' && LEGACY_SCOPES.has(parts[1])) ||
                 (parts[0] === 'devices' && parts[1] === 'mydevice') ||
                 (parts[0] === 'configs' && parts[1] === 'users')) {
        path = '[unsupported]';
        throw new HttpError(501, 'operation_not_implemented');
      } else {
        throw new HttpError(404, 'route_not_found');
      }

      if (request.method !== expectedMethod) {
        response.setHeader('Allow', expectedMethod);
        request.resume();
        throw new HttpError(405, 'method_not_allowed');
      }

      if (path === '/health' || path === '/healthz') {
        try {
          const mysql = await store.health();
          respond(response, 200, { status: 'ok', backend: { status: 'running' }, mysql });
        } catch {
          try { errorLog({ event: 'database_health_failed', code: 'DATABASE_UNAVAILABLE' }); } catch {}
          respond(response, 503, { status: 'degraded', backend: { status: 'running' }, mysql: { status: 'unavailable' } });
        }
      } else if (path === '/config/:client/datacenters') {
        respond(response, 200, [{ name: DATACENTER_NAME, _datacenter_id: DATACENTER_ID }]);
      } else if (path === '/config/:client/datacenters/:name/urls') {
        const urls = Object.fromEntries(URL_KEYS.map(key => [key,
          key === 'pandora' ? baseUrl : baseUrl + '/unsupported/' + key + '/',
        ]));
        respond(response, 200, urls);
      } else if (path === '/locate/auth' || path === '/locate/storage') {
        respond(response, 200, baseUrl);
      } else if (path === '/profiles/me/myprofile') {
        const fields = parsePairs(url.search.slice(1), new Set(['access_token']));
        const session = await sessions.forToken(fields.get('access_token'));
        if (!session) throw new HttpError(401, 'invalid_session');
        respond(response, 200, { credential: session.credential });
      } else if (path === '/authorize') {
        const fields = await form(request, new Set(['client_id', 'username', 'password', 'scope', 'device_id', 'grant_type', 'refresh_token']));
        const clientId = required(fields, 'client_id', 512);
        const grantType = fields.get('grant_type');
        const scopeText = fields.get('scope');
        if (scopeText !== undefined) {
          if (!scopeText || scopeText.length > 256 || /[\u0000-\u0008\u000b\u000c\u000e-\u001f\u007f]/u.test(scopeText) ||
              scopeText.trim().split(/\s+/u).some(value => !LEGACY_SCOPES.has(value))) throw new HttpError(400, 'invalid_scope');
        }
        let tokens;
        if (grantType === 'refresh_token') {
          if (['username', 'password', 'device_id'].some(name => fields.has(name))) throw new HttpError(400, 'invalid_refresh_form');
          const refreshToken = required(fields, 'refresh_token', 2048);
          tokens = await sessions.refresh({ clientId, refreshToken });
        } else {
          if (grantType !== undefined || fields.has('refresh_token')) throw new HttpError(400, 'invalid_grant_type');
          const deviceId = required(fields, 'device_id', 256);
          const username = required(fields, 'username', 1034);
          const password = required(fields, 'password', 16);
          if (scopeText === undefined) throw new HttpError(400, 'invalid_scope');
          tokens = await sessions.authorize({ clientId, username, password, deviceId });
        }
        respond(response, 200, tokens);
      } else {
        const fields = await form(request, new Set(['access_token', 'country', 'age']));
        const session = await sessions.forToken(fields.get('access_token'));
        if (!session) throw new HttpError(401, 'invalid_session');
        const country = required(fields, 'country', 2).toUpperCase();
        const ageText = required(fields, 'age', 3);
        if (!/^[A-Z]{2}$/u.test(country)) throw new HttpError(400, 'invalid_country');
        if (!/^(0|[1-9][0-9]{0,2})$/u.test(ageText)) throw new HttpError(400, 'invalid_age');
        const age = Number(ageText);
        if (age > 120) throw new HttpError(400, 'invalid_age');
        await store.saveCompliance({ accountId: session.accountId, country, age, updatedAt: Math.floor(now() / 1000) });
        // Explicit project research rule; this makes no legal-compliance claim.
        respond(response, 200, { LOCAL_RESEARCH_16_PLUS: { compliant: age >= 16 } });
      }
    }

    void handle().catch(error => {
      request.resume();
      if (response.headersSent || response.destroyed) return;
      const failure = error instanceof HttpError ? error
        : error?.code === 'DATABASE_UNAVAILABLE' ? new HttpError(503, 'database_unavailable')
        : error?.code === 'SESSION_CAPACITY' ? new HttpError(503, 'session_capacity')
        : error?.code === 'INVALID_SESSION' ? new HttpError(401, 'invalid_session')
        : error?.code === 'INVALID_CREDENTIALS' ? new HttpError(401, 'invalid_credentials')
        : new HttpError(500, 'internal_error');
      if (failure.status >= 500) {
        try { errorLog({ event: 'request_error', method: request.method ?? 'UNKNOWN', path, status: failure.status, code: failure.code }); } catch {}
      }
      respond(response, failure.status, { error: failure.code });
    });
  });

  server.requestTimeout = 15000;
  server.headersTimeout = 10000;
  server.keepAliveTimeout = 1000;
  return server;
}
