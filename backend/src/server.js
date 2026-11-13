import { createServer } from 'node:http';
import { randomBytes } from 'node:crypto';
import { HttpError, respond, form, jsonBody, parsePairs, required, routeSegments, validateClientPath } from './http.js';
import { createSessionService } from './services/sessions.js';
import { normalizeBackendBaseUrl } from './config.js';
import { decodeSnapshot, parseSnapshot, DEFAULT_MAX_SNAPSHOT_BYTES } from './services/snapshots.js';
import { loadNewPlayerDefaults } from './services/new-player-defaults.js';
import { assertInitialSnapshot } from './services/initial-snapshot-policy.js';

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
    throw new Error('A SQLite repository is required; there is no memory fallback');
  }
  const now = options.now ?? Date.now;
  const sessions = createSessionService({ store, secret: options.sessionSecret, ttl: options.sessionTtlSeconds ?? 900, refreshTtl: options.refreshTtlSeconds ?? 2592000, now });
  const log = options.log ?? (entry => console.log(JSON.stringify(entry)));
  const errorLog = options.errorLog ?? (entry => console.error(JSON.stringify(entry)));
  const maxSnapshotBytes = options.maxSnapshotBytes ?? DEFAULT_MAX_SNAPSHOT_BYTES;
  const guestTtl = options.guestSessionTtlSeconds ?? 2592000;
  const initializationFactory = () => loadNewPlayerDefaults(options.newPlayerDefaultsFile);
  if (!Number.isSafeInteger(maxSnapshotBytes) || maxSnapshotBytes < 65536 || maxSnapshotBytes > 256 * 1024 * 1024) throw new Error('Invalid maxSnapshotBytes');
  if (!Number.isSafeInteger(guestTtl) || guestTtl < 1 || guestTtl > 7776000) throw new Error('Invalid guestSessionTtlSeconds');
  const secretText = text => typeof text === 'string' && /^[A-Za-z0-9_-]{43}$/u.test(text) && Buffer.from(text, 'base64url').length === 32 && Buffer.from(text, 'base64url').toString('base64url') === text;

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

      if (['/api/v1/guest/sessions', '/api/v1/save', '/api/v1/save/adoption'].includes(url.pathname)) {
        path = url.pathname;
        if (url.search) throw new HttpError(400, 'QUERY_NOT_ALLOWED');
        const allowedMethods = path === '/api/v1/save' ? ['GET', 'PUT'] : ['POST'];
        if (!allowedMethods.includes(request.method)) {
          response.setHeader('Allow', allowedMethods.join(', '));
          throw new HttpError(405, 'method_not_allowed');
        }
        const issuedAt = Math.floor(now() / 1000);
        if (path.endsWith('/sessions')) {
          const body = await jsonBody(request, new Set(['installationSecret', 'previousPlayerId']), 4096);
          if (!secretText(body.installationSecret)) throw new HttpError(400, 'INVALID_INSTALLATION_SECRET');
          if (body.previousPlayerId !== undefined && (typeof body.previousPlayerId !== 'string' || !/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/u.test(body.previousPlayerId))) throw new HttpError(400, 'INVALID_PREVIOUS_PLAYER_ID');
          const value = await store.createGuestSession({ installationSecret: body.installationSecret, previousPlayerId: body.previousPlayerId, accessToken: randomBytes(32).toString('base64url'), issuedAt, expiresAt: issuedAt + guestTtl, initializationFactory });
          respond(response, 200, value);
          return;
        }
        const authorization = request.headers.authorization;
        const token = typeof authorization === 'string' ? /^Bearer ([A-Za-z0-9_-]{43})$/u.exec(authorization)?.[1] : undefined;
        if (!secretText(token)) throw new HttpError(401, 'INVALID_SESSION');
        const session = await store.findGuestSession(token, issuedAt);
        if (!session) throw new HttpError(401, 'INVALID_SESSION');
        if (path === '/api/v1/save/adoption') {
          const body = await jsonBody(request, new Set(['operationId', 'snapshotHash']), 4096);
          if (typeof body.operationId !== 'string' || !/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/u.test(body.operationId)) throw new HttpError(400, 'INVALID_OPERATION_ID');
          if (typeof body.snapshotHash !== 'string' || !/^[a-f0-9]{64}$/u.test(body.snapshotHash)) throw new HttpError(400, 'INVALID_SNAPSHOT_HASH');
          const grant = await store.authorizeLocalAdoption({ playerId: session.playerId, operationId: body.operationId, snapshotHash: body.snapshotHash, adoptionToken: randomBytes(32).toString('base64url'), now: issuedAt, expiresAt: issuedAt + 300 });
          respond(response, 200, grant);
          return;
        }
        if (request.method === 'GET') {
          const saved = await store.loadSnapshot(session.playerId);
          if (!saved) { respond(response, 404, { error: 'NO_SAVE', code: 'NO_SAVE', revision: 0, initialization: await store.getPlayerInitialization(session.playerId) }); return; }
          // Detect damage on reads too. Never report a corrupt stored blob as
          // an empty profile and let a client silently overwrite it.
          try { decodeSnapshot(saved.snapshotBase64, saved.snapshotHash, maxSnapshotBytes); }
          catch { throw new HttpError(503, 'SAVED_SNAPSHOT_CORRUPT'); }
          respond(response, 200, saved);
          return;
        }
        const body = await jsonBody(request, new Set(['operationId', 'expectedRevision', 'snapshotBase64', 'snapshotHash', 'adoptionToken']), Math.ceil(maxSnapshotBytes / 3) * 4 + 4096);
        if (typeof body.operationId !== 'string' || !/^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/u.test(body.operationId)) throw new HttpError(400, 'INVALID_OPERATION_ID');
        if (!Number.isSafeInteger(body.expectedRevision) || body.expectedRevision < 0 || body.expectedRevision >= Number.MAX_SAFE_INTEGER) throw new HttpError(400, 'INVALID_REVISION');
        if (body.adoptionToken !== undefined && !secretText(body.adoptionToken)) throw new HttpError(400, 'INVALID_ADOPTION_TOKEN');
        const parsed = parseSnapshot(body.snapshotBase64, body.snapshotHash, maxSnapshotBytes);
        const committed = await store.saveSnapshot({ playerId: session.playerId, operationId: body.operationId, expectedRevision: body.expectedRevision, snapshotHash: body.snapshotHash, snapshotBytes: parsed.bytes, now: issuedAt, adoptionToken: body.adoptionToken,
          validateFirstSnapshot: initialization => assertInitialSnapshot(parsed.archive, initialization) });
        respond(response, 200, committed);
        return;
      } else if (parts.length === 1 && ['health', 'healthz'].includes(parts[0])) {
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
          const sqlite = await store.health();
          respond(response, 200, { status: 'ok', backend: { status: 'running' }, sqlite });
        } catch {
          try { errorLog({ event: 'database_health_failed', code: 'DATABASE_UNAVAILABLE' }); } catch {}
          respond(response, 503, { status: 'degraded', backend: { status: 'running' }, sqlite: { status: 'unavailable' } });
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
        : error?.code === 'REVISION_CONFLICT' ? Object.assign(new HttpError(409, 'REVISION_CONFLICT'), { currentRevision: error.currentRevision })
        : error?.code === 'OPERATION_CONFLICT' ? new HttpError(409, 'OPERATION_CONFLICT')
        : error?.code === 'SESSION_IDENTITY_NOT_FOUND' ? new HttpError(404, 'SESSION_IDENTITY_NOT_FOUND')
        : error?.code === 'SESSION_IDENTITY_CONFLICT' ? new HttpError(409, 'SESSION_IDENTITY_CONFLICT')
        : error?.code === 'NEW_PLAYER_DEFAULTS_INVALID' ? new HttpError(503, 'NEW_PLAYER_DEFAULTS_INVALID')
        : error?.code === 'ADOPTION_AUTHORIZATION_REQUIRED' ? new HttpError(403, 'ADOPTION_AUTHORIZATION_REQUIRED')
        : error?.code === 'INITIALIZATION_MISMATCH' ? new HttpError(400, 'INITIALIZATION_MISMATCH')
        : error?.code === 'LOCAL_ADOPTION_APPROVAL_REQUIRED' ? new HttpError(403, 'LOCAL_ADOPTION_APPROVAL_REQUIRED')
        : new HttpError(500, 'internal_error');
      if (failure.status >= 500) {
        try { errorLog({ event: 'request_error', method: request.method ?? 'UNKNOWN', path, status: failure.status, code: failure.code }); } catch {}
      }
      respond(response, failure.status, { error: failure.code, ...(path.startsWith('/api/v1/') ? { code: failure.code } : {}), ...(failure.currentRevision !== undefined ? { currentRevision: failure.currentRevision } : {}) });
    });
  });

  server.requestTimeout = 15000;
  server.headersTimeout = 10000;
  server.keepAliveTimeout = 1000;
  return server;
}
