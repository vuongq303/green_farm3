import { createResearchServer } from './server.ts';

const bindHost = process.env.BIND_HOST ?? '127.0.0.1';
const portText = process.env.PORT ?? '8787';
const ttlText = process.env.SESSION_TTL_SECONDS ?? '900';
if (!/^[0-9]+$/u.test(portText) || Number(portText) < 1 || Number(portText) > 65535) {
  throw new Error('PORT must be an integer from 1 to 65535');
}
if (!/^[0-9]+$/u.test(ttlText)) throw new Error('SESSION_TTL_SECONDS must be an integer');
const publicBaseUrl = process.env.PUBLIC_BASE_URL ?? 'http://127.0.0.1:' + portText;
const secret = process.env.RESEARCH_SESSION_SECRET;
if (process.env.NODE_ENV === 'production') {
  throw new Error('This process-local research backend is not configured for production');
}

const server = createResearchServer({
  publicBaseUrl,
  sessionSecret: secret,
  sessionTtlSeconds: Number(ttlText),
});
server.on('error', error => {
  console.error(error instanceof Error ? error.message : 'Backend listener failed');
  process.exitCode = 1;
});
server.listen(Number(portText), bindHost, () => {
  console.log(JSON.stringify({
    mode: 'local-research',
    bind: bindHost + ':' + portText,
    public_base_url: new URL(publicBaseUrl).origin,
    session_secret: secret === undefined ? 'ephemeral; changes on restart' : 'environment supplied',
    sessions: 'memory only; lost on restart',
  }));
});

for (const signal of ['SIGINT', 'SIGTERM'] as const) {
  process.once(signal, () => server.close());
}
