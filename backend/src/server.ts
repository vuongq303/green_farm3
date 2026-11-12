import { createHmac, randomBytes, timingSafeEqual } from 'node:crypto';
import { createServer } from 'node:http';
import type { IncomingMessage, Server, ServerResponse } from 'node:http';

export type RequestLog = { method: string; path: string; status: number };

export type ResearchServerOptions = {
  publicBaseUrl: string;
  sessionSecret?: string | Buffer;
  sessionTtlSeconds?: number;
  now?: () => number;
  log?: (entry: RequestLog) => void;
};

const MAX_FORM_BYTES = 8192;
const MAX_SESSIONS = 10000;
const DATACENTER_NAME = 'local';
const DATACENTER_ID = 'local-research';
const LEGACY_SCOPES = new Set(['storage', 'auth', 'config', 'asset', 'transaction', 'message']);
const URL_KEYS = [
  'pandora', 'gllive-ope', 'tapjoy', 'offline_items', 'game_portal',
  'status', 'crm_iap', 'ads_agency', 'marketing_site', 'online_connectivity',
] as const;

class HttpError extends Error {
  status: number;
  code: string;

  constructor(status: number, code: string) {
    super(code);
    this.status = status;
    this.code = code;
  }
}

type Session = { issuedAt: number; expiresAt: number; credential: string; clientId: string };
type SessionPayload = { sid: string; iat: number; exp: number };

function publicBase(value: string): string {
  const url = new URL(value);
  if (!['http:', 'https:'].includes(url.protocol) || url.username || url.password ||
      url.search || url.hash || url.pathname !== '/') {
    throw new Error('publicBaseUrl must be an HTTP(S) origin without credentials, path, query, or fragment');
  }
  return url.origin;
}

// Original a.aR(String): at most sixteen Java UTF-16 chars, mapped into a..y.
// This public deterministic derivation is compatibility, not proof of identity.
function legacyPassword(credential: string): string {
  let password = '';
  for (let index = 0; index < Math.min(16, credential.length); index++) {
    password += String.fromCharCode(credential.charCodeAt(index) % 25 + 97);
  }
  return password;
}

function equalText(left: string, right: string): boolean {
  const leftBytes = Buffer.from(left, 'utf8');
  const rightBytes = Buffer.from(right, 'utf8');
  return leftBytes.length === rightBytes.length && timingSafeEqual(leftBytes, rightBytes);
}

function respond(response: ServerResponse, status: number, value: object | string): void {
  const text = typeof value === 'string' ? value : JSON.stringify(value);
  response.writeHead(status, {
    'Content-Type': typeof value === 'string' ? 'text/plain; charset=utf-8' : 'application/json; charset=utf-8',
    'Content-Length': Buffer.byteLength(text),
    'Cache-Control': 'no-store',
  });
  response.end(text);
}

async function readBody(request: IncomingMessage): Promise<Buffer> {
  const declaredLength = Number(request.headers['content-length']);
  if (Number.isFinite(declaredLength) && declaredLength > MAX_FORM_BYTES) {
    request.resume();
    throw new HttpError(413, 'form_too_large');
  }
  return await new Promise((resolve, reject) => {
    let size = 0;
    let finished = false;
    let chunks: Buffer[] = [];
    request.on('data', (chunk: Buffer) => {
      if (finished) return;
      size += chunk.length;
      if (size > MAX_FORM_BYTES) {
        finished = true;
        chunks = [];
        reject(new HttpError(413, 'form_too_large'));
      } else {
        chunks.push(chunk);
      }
    });
    request.once('end', () => {
      if (!finished) {
        finished = true;
        resolve(Buffer.concat(chunks));
      }
    });
    const abort = () => {
      if (!finished) {
        finished = true;
        reject(new HttpError(400, 'incomplete_form'));
      }
    };
    request.once('aborted', abort);
    request.once('error', abort);
  });
}

async function form(request: IncomingMessage, allowed: Set<string>): Promise<Map<string, string>> {
  const contentType = request.headers['content-type']?.split(';', 1)[0]?.trim().toLowerCase();
  if (contentType !== 'application/x-www-form-urlencoded') {
    request.resume();
    throw new HttpError(415, 'form_content_type_required');
  }
  const body = await readBody(request);
  let text: string;
  try {
    text = new TextDecoder('utf-8', { fatal: true }).decode(body);
  } catch {
    throw new HttpError(400, 'invalid_form');
  }
  return parsePairs(text, allowed);
}

function parsePairs(text: string, allowed: Set<string>): Map<string, string> {
  const fields = new Map<string, string>();
  if (!text) return fields;
  for (const pair of text.split('&')) {
    const separator = pair.indexOf('=');
    if (separator < 1) throw new HttpError(400, 'invalid_form');
    let name: string;
    let value: string;
    try {
      name = decodeURIComponent(pair.slice(0, separator).replaceAll('+', ' '));
      value = decodeURIComponent(pair.slice(separator + 1).replaceAll('+', ' '));
    } catch {
      throw new HttpError(400, 'invalid_form');
    }
    if (fields.has(name)) throw new HttpError(400, 'duplicate_form_field');
    if (!allowed.has(name)) throw new HttpError(400, 'unexpected_form_field');
    fields.set(name, value);
  }
  return fields;
}

function required(fields: Map<string, string>, name: string, maxLength: number): string {
  const value = fields.get(name);
  if (!value || value.length > maxLength || /[\u0000-\u001f\u007f]/u.test(value)) {
    throw new HttpError(400, 'invalid_' + name);
  }
  return value;
}

function routeSegments(pathname: string): string[] {
  try {
    return pathname.split('/').slice(1).map(segment => decodeURIComponent(segment));
  } catch {
    throw new HttpError(400, 'invalid_path');
  }
}

function validateClientPath(client: string): void {
  if (!client || client.length > 512 || /[\u0000-\u0020\u007f/?#]/u.test(client)) {
    throw new HttpError(400, 'invalid_client_id');
  }
}

/** Independent, process-local research service. It never forwards requests. */
export function createResearchServer(options: ResearchServerOptions): Server {
  const baseUrl = publicBase(options.publicBaseUrl);
  const ttl = options.sessionTtlSeconds ?? 900;
  if (!Number.isInteger(ttl) || ttl < 1 || ttl > 86400) {
    throw new Error('sessionTtlSeconds must be an integer from 1 to 86400');
  }
  const secret = options.sessionSecret === undefined ? randomBytes(32) : Buffer.from(options.sessionSecret);
  if (secret.length < 32) throw new Error('sessionSecret must contain at least 32 bytes');
  const now = options.now ?? Date.now;
  const log = options.log ?? ((entry: RequestLog) => console.log(JSON.stringify(entry)));
  const sessions = new Map<string, Session>();
  const seconds = () => Math.floor(now() / 1000);

  function signature(value: string): Buffer {
    return createHmac('sha256', secret).update(value).digest();
  }

  function issueToken(clientId: string, credential: string): string {
    const issuedAt = seconds();
    for (const [id, session] of sessions) {
      if (session.expiresAt <= issuedAt) sessions.delete(id);
    }
    if (sessions.size >= MAX_SESSIONS) throw new HttpError(503, 'session_capacity');
    const sid = randomBytes(24).toString('hex');
    const expiresAt = issuedAt + ttl;
    sessions.set(sid, { issuedAt, expiresAt, clientId, credential });
    const payload: SessionPayload = { sid, iat: issuedAt, exp: expiresAt };
    const signed = 'v1.' + Buffer.from(JSON.stringify(payload)).toString('base64url');
    return signed + '.' + signature(signed).toString('base64url');
  }

  function sessionForToken(token: string | undefined): Session | undefined {
    if (!token || token.length > 2048) return undefined;
    const parts = token.split('.');
    if (parts.length !== 3 || parts[0] !== 'v1' ||
        !/^[A-Za-z0-9_-]+$/u.test(parts[1]!) || !/^[A-Za-z0-9_-]+$/u.test(parts[2]!)) return undefined;
    const actual = Buffer.from(parts[2]!, 'base64url');
    const expected = signature(parts[0] + '.' + parts[1]);
    if (actual.toString('base64url') !== parts[2] || actual.length !== expected.length ||
        !timingSafeEqual(actual, expected)) return undefined;
    let payload: SessionPayload;
    try {
      payload = JSON.parse(Buffer.from(parts[1]!, 'base64url').toString('utf8'));
    } catch {
      return undefined;
    }
    if (!payload || typeof payload !== 'object' || !/^[a-f0-9]{48}$/u.test(payload.sid) ||
        !Number.isSafeInteger(payload.iat) || !Number.isSafeInteger(payload.exp)) return undefined;
    const session = sessions.get(payload.sid);
    if (!session || session.issuedAt !== payload.iat || session.expiresAt !== payload.exp) return undefined;
    const currentTime = seconds();
    if (payload.exp <= currentTime) {
      sessions.delete(payload.sid);
      return undefined;
    }
    return currentTime >= payload.iat ? session : undefined;
  }

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

    async function handle(): Promise<void> {
      if (!request.url?.startsWith('/') || request.url.startsWith('//')) {
        throw new HttpError(400, 'invalid_path');
      }
      const url = new URL(request.url, 'http://local.invalid');
      const parts = routeSegments(url.pathname);
      let expectedMethod: string;

      if (parts.length === 1 && parts[0] === 'healthz') {
        path = '/healthz';
        expectedMethod = 'GET';
      } else if (parts.length === 3 && parts[0] === 'config' && parts[2] === 'datacenters') {
        path = '/config/:client/datacenters';
        validateClientPath(parts[1]!);
        expectedMethod = 'GET';
      } else if (parts.length === 5 && parts[0] === 'config' && parts[2] === 'datacenters' && parts[4] === 'urls') {
        path = '/config/:client/datacenters/:name/urls';
        validateClientPath(parts[1]!);
        if (parts[3] !== DATACENTER_NAME) throw new HttpError(404, 'unknown_datacenter');
        expectedMethod = 'GET';
      } else if (parts.length === 2 && parts[0] === 'locate' && ['auth', 'storage'].includes(parts[1]!)) {
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
                 (parts.length === 2 && parts[0] === 'locate' && LEGACY_SCOPES.has(parts[1]!)) ||
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

      if (path === '/healthz') {
        respond(response, 200, { status: 'ok', mode: 'local-research' });
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
        const session = sessionForToken(fields.get('access_token'));
        if (!session) throw new HttpError(401, 'invalid_session');
        respond(response, 200, { credential: session.credential });
      } else if (path === '/authorize') {
        const fields = await form(request, new Set(['client_id', 'username', 'password', 'scope', 'device_id']));
        const clientId = required(fields, 'client_id', 512);
        required(fields, 'device_id', 256);
        const username = required(fields, 'username', 1034);
        const password = required(fields, 'password', 16);
        const scopeText = fields.get('scope');
        if (!scopeText || scopeText.length > 256 || /[\u0000-\u0008\u000b\u000c\u000e-\u001f\u007f]/u.test(scopeText)) {
          throw new HttpError(400, 'invalid_scope');
        }
        const scope = scopeText.trim().split(/\s+/u);
        if (scope.some(value => !LEGACY_SCOPES.has(value))) throw new HttpError(400, 'invalid_scope');
        const credential = username.startsWith('anonymous:') ? username.slice('anonymous:'.length) : '';
        if (!credential || credential.length > 1024 || !equalText(password, legacyPassword(credential))) {
          throw new HttpError(401, 'invalid_credentials');
        }
        respond(response, 200, { access_token: issueToken(clientId, username) });
      } else {
        const fields = await form(request, new Set(['access_token', 'country', 'age']));
        if (!sessionForToken(fields.get('access_token'))) throw new HttpError(401, 'invalid_session');
        const country = required(fields, 'country', 2).toUpperCase();
        const ageText = required(fields, 'age', 3);
        if (!/^[A-Z]{2}$/u.test(country)) throw new HttpError(400, 'invalid_country');
        if (!/^(0|[1-9][0-9]{0,2})$/u.test(ageText)) throw new HttpError(400, 'invalid_age');
        const age = Number(ageText);
        if (age > 120) throw new HttpError(400, 'invalid_age');
        // Explicit project research rule; this makes no legal-compliance claim.
        respond(response, 200, { LOCAL_RESEARCH_16_PLUS: { compliant: age >= 16 } });
      }
    }

    void handle().catch(error => {
      request.resume();
      if (response.headersSent || response.destroyed) return;
      const failure = error instanceof HttpError ? error : new HttpError(500, 'internal_error');
      respond(response, failure.status, { error: failure.code });
    });
  });

  server.requestTimeout = 15000;
  server.headersTimeout = 10000;
  server.keepAliveTimeout = 1000;
  return server;
}
