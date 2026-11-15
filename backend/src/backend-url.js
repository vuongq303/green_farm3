import { isIP } from 'node:net';
import { domainToASCII } from 'node:url';

/** Parse an HTTP(S) origin without resolving DNS or rewriting numeric IP aliases. */
export function normalizeHttpOrigin(value) {
  const invalid = () => new Error('Expected an HTTP(S) origin without credentials, path, query or fragment');
  if (typeof value !== 'string' || !value || value !== value.trim() ||
      /[\s\u0000-\u001f\u007f\\]/u.test(value)) throw invalid();
  const match = /^(https?):\/\/([^/?#]+)\/?$/iu.exec(value);
  if (!match || /[@%]/u.test(match[2])) throw invalid();
  const authority = match[2];
  const parts = authority.startsWith('[') ? /^\[([^\]]+)\](?::([0-9]+))?$/u.exec(authority) :
    /^([^:\[\]]+)(?::([0-9]+))?$/u.exec(authority);
  if (!parts || (parts[2] !== undefined && (Number(parts[2]) < 1 || Number(parts[2]) > 65535))) throw invalid();
  const rawHost = parts[1];
  if (authority.startsWith('[')) {
    if (isIP(rawHost) !== 6) throw invalid();
  } else if (isIP(rawHost) !== 4) {
    const ascii = domainToASCII(rawHost).toLowerCase();
    const host = ascii.endsWith('.') ? ascii.slice(0, -1) : ascii;
    if (!host || host.length > 253 || host.split('.').some(label => label.length > 63 ||
        !/^[a-z0-9](?:[a-z0-9-]*[a-z0-9])?$/u.test(label))) throw invalid();
  }
  let url;
  try { url = new URL(value); } catch { throw invalid(); }
  // WHATWG URLs accept octal/hex/short IPv4 aliases; configuration must be explicit.
  if (!authority.startsWith('[') && isIP(url.hostname) === 4 && isIP(rawHost) !== 4) throw invalid();
  const port = parts[2] === undefined ? '' : ':' + Number(parts[2]);
  return url.protocol + '//' + url.hostname.toLowerCase() + port + '/';
}
