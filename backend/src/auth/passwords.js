import argon2 from 'argon2';
import { randomBytes } from 'node:crypto';

// Bound the total memory/work in a server process, including dummy checks.
// Reverse-proxy/shared rate limiting is additionally required across workers.
const OPTIONS = Object.freeze({ type: argon2.argon2id, memoryCost: 65536, timeCost: 3, parallelism: 1,
  hashLength: 32 });
const ENCODED_HASH = /^\$argon2id\$v=19\$m=65536,t=3,p=1\$[A-Za-z0-9+/]{22}\$[A-Za-z0-9+/]{43}$/u;
let active = 0;
let dummyHash;

export class PasswordError extends Error {
  constructor(code) { super(code); this.code = code; }
}

function invalid() { throw new PasswordError('invalid_registration'); }

export function validPassword(value) {
  return typeof value === 'string' && value.isWellFormed() && [...value].length >= 12 && [...value].length <= 128 &&
    Buffer.byteLength(value, 'utf8') <= 512 && !/[\u0000-\u001f\u007f]/u.test(value);
}

export function validPasswordHash(value) {
  return typeof value === 'string' && ENCODED_HASH.test(value);
}

export function normalizeIdentifier(value) {
  if (typeof value !== 'string' || value.length > 254 || /[^\x20-\x7e]/u.test(value)) return undefined;
  const normalized = value.trim().toLowerCase();
  return normalized.length >= 3 && normalized.length <= 254 ? normalized : undefined;
}

export function registration(fields) {
  const username = normalizeIdentifier(fields.username), email = normalizeIdentifier(fields.email);
  if (!username || !/^[a-z0-9_]{3,32}$/u.test(username) || !email || email.length > 254 ||
      !validPassword(fields.password) || fields.password !== fields.password_confirmation) invalid();
  const parts = email.split('@');
  if (parts.length !== 2 || !parts[0] || parts[0].length > 64 || parts[0].startsWith('.') ||
      parts[0].endsWith('.') || parts[0].includes('..') || !/^[a-z0-9.!#$%&'*+/=?^_`{|}~-]+$/u.test(parts[0])) invalid();
  const labels = parts[1].split('.');
  if (labels.length < 2 || labels.at(-1).length < 2 || labels.some(label =>
    label.length > 63 || !/^[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/u.test(label))) invalid();
  return { username, email, password: fields.password };
}

async function bounded(work) {
  if (active >= 2) throw new PasswordError('password_unavailable');
  ++active;
  try { return await work(); }
  catch (error) {
    if (error instanceof PasswordError) throw error;
    throw new PasswordError('password_unavailable');
  } finally { --active; }
}

export function hashPassword(password) {
  if (!validPassword(password)) throw new PasswordError('invalid_registration');
  return bounded(() => argon2.hash(password, OPTIONS));
}

/** Unknown and disabled accounts perform the same expensive verification as a
 * wrong password. The random dummy credential is never an authenticatable user. */
export function verifyPassword(password, encoded) {
  return bounded(async () => {
    const validStored = validPasswordHash(encoded);
    if (!validStored && !dummyHash) dummyHash = argon2.hash(randomBytes(32), OPTIONS).catch(error => { dummyHash = undefined; throw error; });
    const reference = validStored ? encoded : await dummyHash;
    const candidate = typeof password === 'string' && Buffer.byteLength(password, 'utf8') <= 512 ? password : '';
    const valid = await argon2.verify(reference, candidate);
    return validStored && valid && validPassword(password);
  });
}
