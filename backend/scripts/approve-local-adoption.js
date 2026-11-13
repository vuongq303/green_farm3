import { loadEnvironment, loadDatabaseConfig } from '../src/config.js';
import { createSqliteStore } from '../src/db/sqlite.js';

const uuid = /^[0-9a-f]{8}-[0-9a-f]{4}-[1-5][0-9a-f]{3}-[89ab][0-9a-f]{3}-[0-9a-f]{12}$/u;
let store;
try {
  const values = new Map();
  const args = process.argv.slice(2);
  for (let index = 0; index < args.length; index += 2) {
    const key = args[index]; const value = args[index + 1];
    if (!['--player-id', '--snapshot-hash', '--expires-in'].includes(key) || values.has(key) || !value) throw new Error('INVALID_ARGUMENTS');
    values.set(key, value);
  }
  const playerId = values.get('--player-id'); const snapshotHash = values.get('--snapshot-hash');
  const expiresText = values.get('--expires-in') ?? '900'; const seconds = Number(expiresText);
  if (!uuid.test(playerId ?? '') || !/^[a-f0-9]{64}$/u.test(snapshotHash ?? '') || !/^[0-9]+$/u.test(expiresText) || !Number.isInteger(seconds) || seconds < 60 || seconds > 86400) throw new Error('INVALID_ARGUMENTS');
  loadEnvironment();
  store = createSqliteStore(loadDatabaseConfig());
  await store.health();
  const now = Math.floor(Date.now() / 1000);
  const approval = await store.approveLocalAdoption({ playerId, snapshotHash, now, expiresAt: now + seconds });
  console.log(JSON.stringify({ event: 'local_copy_approved', ...approval }));
} catch (error) {
  console.error(JSON.stringify({ event: 'local_copy_approval_failed', code: /^[A-Z_]+$/u.test(error.code ?? error.message ?? '') ? error.code ?? error.message : 'APPROVAL_FAILED', message: 'Use --player-id UUID --snapshot-hash SHA256 --expires-in 900 with the intended server SQLITE_PATH. Verify the source backup before approving; this permits only a copy to that guest, not ownership of a legacy account.' }));
  process.exitCode = 1;
} finally { if (store) await store.close(); }
