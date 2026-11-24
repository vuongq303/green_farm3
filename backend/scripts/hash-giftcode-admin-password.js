import { hashPassword } from '../src/auth/passwords.js';

try {
  const chunks = [];
  let size = 0;
  for await (const chunk of process.stdin) {
    size += chunk.length;
    if (size > 514) throw new Error('oversized password');
    chunks.push(chunk);
  }
  const password = new TextDecoder('utf-8', { fatal: true }).decode(Buffer.concat(chunks)).replace(/\r?\n$/u, '');
  console.log(await hashPassword(password));
} catch {
  console.error('Provide a 12–128 character admin password on stdin (maximum 512 UTF-8 bytes).');
  process.exitCode = 1;
}
