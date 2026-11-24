import { createHash } from 'node:crypto';
import { readFileSync } from 'node:fs';
import { PAGE_HEADERS } from './pages.mjs';

const root = '/admin/giftcodes';
const csp = "default-src 'none'; base-uri 'none'; object-src 'none'; frame-ancestors 'none'; form-action 'none'; script-src 'self'; style-src 'self'; img-src 'self'; connect-src 'self'";
const asset = (path, contentType) => ({
  body: readFileSync(new URL(path, import.meta.url)),
  contentType,
});
const assets = new Map([
  ['giftcodes.css', asset('./giftcodes/giftcodes.css', 'text/css; charset=utf-8')],
  ['giftcodes.js', asset('./giftcodes/giftcodes.js', 'application/javascript; charset=utf-8')],
  ['green-farm-3.png', asset('./assets/green-farm-3.png', 'image/png')],
  ['farm.png', asset('../../docs/kotlin-validation/runtime-ui/vietnamese-farm-after-restart.png', 'image/png')],
  ['coins.png', asset('../../app/src/main/res/drawable-nodpi/giftcode_coins.png', 'image/png')],
  ['cash.png', asset('../../app/src/main/res/drawable-nodpi/giftcode_cash.png', 'image/png')],
]);
const assetUrl = name => root + '/assets/' + name;
const versionedAssetUrl = name => assetUrl(name) + '?v=' + createHash('sha256').update(assets.get(name).body).digest('hex');
const html = readFileSync(new URL('./giftcodes/index.html', import.meta.url), 'utf8')
  .replaceAll('{{STYLE_URL}}', versionedAssetUrl('giftcodes.css'))
  .replaceAll('{{SCRIPT_URL}}', versionedAssetUrl('giftcodes.js'));

/** Serve the declared page and assets. Management data comes from the authenticated API. */
export function handleGiftcodeAdminPage(request, response, url) {
  const page = url.pathname === root || url.pathname === root + '/';
  const assetPrefix = root + '/assets/';
  const file = url.pathname.startsWith(assetPrefix) && assets.get(url.pathname.slice(assetPrefix.length));
  if (!page && !file) return false;
  if (request.method !== 'GET' && request.method !== 'HEAD') {
    request.resume();
    response.writeHead(405, { Allow: 'GET, HEAD', 'Cache-Control': 'no-store' });
    response.end();
    return true;
  }
  const body = page ? html : file.body;
  const headers = page ? { ...PAGE_HEADERS, 'Content-Security-Policy': csp } : {
    'Content-Type': file.contentType,
    'X-Content-Type-Options': 'nosniff',
    'Cache-Control': 'public, max-age=3600',
    'Referrer-Policy': 'no-referrer',
  };
  response.writeHead(200, { ...headers, 'Content-Length': Buffer.byteLength(body) });
  response.end(request.method === 'HEAD' ? undefined : body);
  return true;
}
