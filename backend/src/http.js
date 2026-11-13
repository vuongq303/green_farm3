// Legacy wire protocol: URL-encoded UTF-8 forms, plain locators and JSON responses.
const MAX_FORM_BYTES = 8192;

export class HttpError extends Error {
  constructor(status, code) {
    super(code);
    this.status = status;
    this.code = code;
  }
}

export function respond(response, status, value) {
  const text = typeof value === 'string' ? value : JSON.stringify(value);
  response.writeHead(status, {
    'Content-Type': typeof value === 'string' ? 'text/plain; charset=utf-8' : 'application/json; charset=utf-8',
    'Content-Length': Buffer.byteLength(text),
    'Cache-Control': 'no-store',
  });
  response.end(text);
}

async function readBody(request, maxBytes = MAX_FORM_BYTES, tooLarge = 'form_too_large') {
  const declaredLength = Number(request.headers['content-length']);
  if (Number.isFinite(declaredLength) && declaredLength > maxBytes) {
    request.resume();
    throw new HttpError(413, tooLarge);
  }
  return await new Promise((resolve, reject) => {
    let size = 0;
    let finished = false;
    let chunks = [];
    request.on('data', (chunk) => {
      if (finished) return;
      size += chunk.length;
      if (size > maxBytes) {
        finished = true;
        chunks = [];
        reject(new HttpError(413, tooLarge));
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

export async function jsonBody(request, allowed, maxBytes) {
  if (request.headers['content-type']?.split(';', 1)[0]?.trim().toLowerCase() !== 'application/json') {
    request.resume();
    throw new HttpError(415, 'JSON_CONTENT_TYPE_REQUIRED');
  }
  const bytes = await readBody(request, maxBytes, 'SNAPSHOT_TOO_LARGE');
  let value;
  try { value = JSON.parse(new TextDecoder('utf-8', { fatal: true }).decode(bytes)); }
  catch { throw new HttpError(400, 'INVALID_JSON'); }
  if (!value || Array.isArray(value) || typeof value !== 'object' || Object.keys(value).some(key => !allowed.has(key))) throw new HttpError(400, 'INVALID_JSON_FIELDS');
  return value;
}

export async function form(request, allowed) {
  const contentType = request.headers['content-type']?.split(';', 1)[0]?.trim().toLowerCase();
  if (contentType !== 'application/x-www-form-urlencoded') {
    request.resume();
    throw new HttpError(415, 'form_content_type_required');
  }
  const body = await readBody(request);
  let text;
  try {
    text = new TextDecoder('utf-8', { fatal: true }).decode(body);
  } catch {
    throw new HttpError(400, 'invalid_form');
  }
  return parsePairs(text, allowed);
}

export function parsePairs(text, allowed) {
  const fields = new Map();
  if (!text) return fields;
  for (const pair of text.split('&')) {
    const separator = pair.indexOf('=');
    if (separator < 1) throw new HttpError(400, 'invalid_form');
    let name;
    let value;
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

export function required(fields, name, maxLength) {
  const value = fields.get(name);
  if (!value || value.length > maxLength || /[\u0000-\u001f\u007f]/u.test(value)) {
    throw new HttpError(400, 'invalid_' + name);
  }
  return value;
}

export function routeSegments(pathname) {
  try {
    return pathname.split('/').slice(1).map(segment => decodeURIComponent(segment));
  } catch {
    throw new HttpError(400, 'invalid_path');
  }
}

export function validateClientPath(client) {
  if (!client || client.length > 512 || /[\u0000-\u0020\u007f/?#]/u.test(client)) {
    throw new HttpError(400, 'invalid_client_id');
  }
}
