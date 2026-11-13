import assert from 'node:assert/strict';
import test from 'node:test';
import { mkdtempSync, rmSync, readFileSync, writeFileSync } from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import { randomBytes, randomUUID, createHash } from 'node:crypto';
import { DatabaseSync } from 'node:sqlite';
import { once } from 'node:events';
import { createResearchServer } from '../src/server.js';
import { createSqliteStore } from '../src/db/sqlite.js';
import { migrateSqlite } from '../src/db/sqlite-migrate.js';
import { encodedFixture } from './helpers/archive.mjs';
import { DEFAULT_NEW_PLAYER_FILE, initializationFor } from '../src/services/new-player-defaults.js';

const catalog = JSON.parse(readFileSync(new URL('../config/game_catalog_4_4_2.json', import.meta.url)));
const oracle = JSON.parse(readFileSync(new URL('../../docs/backend-restoration-validation/new-player-oracle/original-gameplay-oracle.json', import.meta.url)));

function configuredArchive(initialization, changes = {}, { omitMarker = false, markerHash = initialization.configHash } = {}) {
  const defaults = { ...initialization.defaults, ...changes };
  const binding = Buffer.from('configured-fixture-binding'); const prefix = binding.length + 5; const at = prefix + 1335;
  const gameplay = Buffer.alloc(at + 46); gameplay[0] = 4; gameplay.writeInt32LE(binding.length, 1); binding.copy(gameplay, 5);
  const encodedInt = value => ((value << 9) | (value >> 23)) ^ -1413816336;
  const put = (offset, value) => gameplay.writeInt32LE(encodedInt(value), at + offset);
  put(0, defaults.initialLevel); put(4, defaults.initialExperience); put(16, defaults.initialCash);
  const value = BigInt(defaults.initialCoins); const rotated = BigInt.asIntN(64, (value << 9n) | (value >> 55n));
  gameplay.writeBigInt64LE(BigInt.asIntN(64, rotated ^ BigInt(-1413816336)), at + 8);
  put(22, defaults.initialCapacityLimits.plots); put(30, defaults.initialCapacityLimits.trees); put(34, defaults.initialCapacityLimits.animals); put(42, defaults.initialCapacityLimits.ponds);
  gameplay.writeInt8(defaults.initialTutorialStep, prefix + 4);
  const header = Buffer.alloc(35); header[4] = defaults.initialLanguage === 'auto' ? 0 : catalog.languageIndexByCode[defaults.initialLanguage];
  const extraRecords = omitMarker ? [] : [['new_player_defaults_v1', Buffer.from(markerHash, 'ascii')]];
  return { ...encodedFixture({ gameplayPayload: gameplay, headerPayload: header, extraRecords }), gameplay, at };
}

async function fixture(t, configOverrides = null) {
  const folder = mkdtempSync(join(tmpdir(), 'gf-initial-policy-')); const filename = join(folder, 'store.sqlite');
  await migrateSqlite(filename); const store = createSqliteStore({ filename }); let now = 1700000000000;
  const configFile = join(folder, 'defaults.json');
  if (configOverrides) writeFileSync(configFile, JSON.stringify({ ...JSON.parse(readFileSync(DEFAULT_NEW_PLAYER_FILE)), ...configOverrides }));
  const server = createResearchServer({ publicBaseUrl: 'http://192.168.40.55:4141/', sessionSecret: 'test-initial-policy-stable-secret', store, newPlayerDefaultsFile: configOverrides ? configFile : undefined, now: () => now, log() {}, errorLog() {} });
  server.listen(0, '127.0.0.1'); await once(server, 'listening');
  t.after(async () => { const stopped = new Promise(resolve => server.close(resolve)); server.closeAllConnections(); await stopped; await store.close(); rmSync(folder, { recursive: true, force: true }); });
  const request = async (path, method, token, body) => {
    const response = await fetch(`http://127.0.0.1:${server.address().port}${path}`, { method, headers: { 'content-type': 'application/json', ...(token ? { authorization: 'Bearer ' + token } : {}) }, body: body === undefined ? undefined : JSON.stringify(body) });
    return { status: response.status, value: await response.json() };
  };
  const guest = async () => { const response = await request('/api/v1/guest/sessions', 'POST', null, { installationSecret: randomBytes(32).toString('base64url') }); assert.equal(response.status, 200); return response.value; };
  const put = (account, payload, extra = {}) => request('/api/v1/save', 'PUT', account.accessToken, { operationId: randomUUID(), expectedRevision: 0, snapshotBase64: payload.snapshotBase64, snapshotHash: payload.snapshotHash, ...extra });
  const grant = (account, operationId, snapshotHash) => request('/api/v1/save/adoption', 'POST', account.accessToken, { operationId, snapshotHash });
  const approve = (account, snapshotHash, seconds = 900) => store.approveLocalAdoption({ playerId: account.playerId, snapshotHash, now: Math.floor(now / 1000), expiresAt: Math.floor(now / 1000) + seconds });
  return { filename, request, guest, put, grant, approve, advance(seconds) { now += seconds * 1000; } };
}

test('first configured snapshot matches original JVM numeric bytes and frozen server defaults; subsequent normal spending is allowed', async t => {
  const f = await fixture(t); const player = await f.guest();
  const empty = await f.request('/api/v1/save', 'GET', player.accessToken);
  assert.equal(empty.status, 404); assert.deepEqual(empty.value.initialization, player.initialization);
  const payload = configuredArchive(player.initialization);
  assert.equal(payload.gameplay.subarray(payload.at, payload.at + 20).toString('hex'), oracle.vectors[0].encodedHex);
  assert.equal((await f.put(player, payload)).status, 200);
  const db = new DatabaseSync(f.filename, { readOnly: true });
  const receipt = db.prepare('SELECT origin,config_hash,revision FROM gf_player_initialization_receipts WHERE player_id=?').get(player.playerId);
  assert.equal(receipt.origin, 'configured-new-player'); assert.equal(receipt.config_hash, player.initialization.configHash); assert.equal(receipt.revision, 1); db.close();
  const spent = configuredArchive(player.initialization, { initialCoins: player.initialization.defaults.initialCoins - 10, initialExperience: 1, initialTutorialStep: 8 });
  assert.equal((await f.put(player, spent, { expectedRevision: 1 })).status, 200);
  const loaded = await f.request('/api/v1/save', 'GET', player.accessToken); assert.equal(loaded.value.revision, 2); assert.equal(loaded.value.snapshotHash, spent.snapshotHash);
});

test('fresh profiles cannot override initial resources, marker or tutorial through request fields or blob', async t => {
  const f = await fixture(t); const player = await f.guest();
  const omitted = await f.put(player, configuredArchive(player.initialization, {}, { omitMarker: true })); assert.equal(omitted.status, 403); assert.equal(omitted.value.code, 'ADOPTION_AUTHORIZATION_REQUIRED');
  for (const payload of [
    configuredArchive(player.initialization, { initialCoins: player.initialization.defaults.initialCoins + 1 }),
    configuredArchive(player.initialization, { initialLevel: 2 }),
    configuredArchive(player.initialization, { initialLevel: 50, initialCoins: 99999999, initialCash: 3000000 }),
    configuredArchive(player.initialization, { initialTutorialStep: 1 }),
    configuredArchive(player.initialization, {}, { markerHash: 'a'.repeat(64) }),
  ]) { const invalid = await f.put(player, payload); assert.equal(invalid.status, 400); assert.equal(invalid.value.code, 'INITIALIZATION_MISMATCH'); }
  const fieldOverride = await f.put(player, configuredArchive(player.initialization), { initialCoins: 99999999 }); assert.equal(fieldOverride.status, 400);
  const newOverride = await f.request('/api/v1/guest/sessions', 'POST', null, { installationSecret: randomBytes(32).toString('base64url'), initialLevel: 50 }); assert.equal(newOverride.status, 400);
  const bypass = await f.grant(player, randomUUID(), configuredArchive(player.initialization, { initialLevel: 50, initialCoins: 99999999, initialCash: 3000000 }).snapshotHash); assert.equal(bypass.status, 403); assert.equal(bypass.value.code, 'LOCAL_ADOPTION_APPROVAL_REQUIRED');
  const db = new DatabaseSync(f.filename, { readOnly: true }); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_farm_snapshots').get().n, 0); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_player_initialization_receipts').get().n, 0); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 1); db.close();
});

test('explicit configured language is checked while auto preserves a supported device-selected language', async t => {
  const explicit = await fixture(t, { initialLanguage: 'en' }); const first = await explicit.guest();
  assert.equal((await explicit.put(first, configuredArchive(first.initialization, { initialLanguage: 'vi' }))).status, 400);
  assert.equal((await explicit.put(first, configuredArchive(first.initialization))).status, 200);
  const automatic = await fixture(t); const second = await automatic.guest();
  assert.equal((await automatic.put(second, configuredArchive(second.initialization, { initialLanguage: 'vi' }))).status, 200);
});

test('approved local-copy grants bind player, operation and hash; expiration/rollback/replay never reset an existing farm', async t => {
  const f = await fixture(t); const player = await f.guest(); const other = await f.guest();
  const payload = encodedFixture({ marker: 33 }); const operationId = randomUUID();
  await f.approve(player, payload.snapshotHash);
  const first = await f.grant(player, operationId, payload.snapshotHash); assert.equal(first.status, 200);
  assert.equal((await f.put(other, payload, { operationId, adoptionToken: first.value.adoptionToken })).status, 403);
  assert.equal((await f.put(player, payload, { operationId: randomUUID(), adoptionToken: first.value.adoptionToken })).status, 403);
  assert.equal((await f.put(player, encodedFixture({ marker: 34 }), { operationId, adoptionToken: first.value.adoptionToken })).status, 403);
  f.advance(301);
  assert.equal((await f.put(player, payload, { operationId, adoptionToken: first.value.adoptionToken })).status, 403);
  const renewed = await f.grant(player, operationId, payload.snapshotHash); assert.equal(renewed.status, 200);
  const db = new DatabaseSync(f.filename);
  db.exec("CREATE TRIGGER reject_policy_commit BEFORE INSERT ON gf_save_operations BEGIN SELECT RAISE(ABORT,'fixture failure'); END;");
  assert.equal((await f.put(player, payload, { operationId, adoptionToken: renewed.value.adoptionToken })).status, 503);
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_farm_snapshots').get().n, 0); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_player_initialization_receipts').get().n, 0); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_local_adoption_grants WHERE used_at IS NOT NULL').get().n, 0); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_local_adoption_approvals WHERE used_at IS NOT NULL').get().n, 0);
  db.exec('DROP TRIGGER reject_policy_commit');
  assert.equal((await f.put(player, payload, { operationId, adoptionToken: renewed.value.adoptionToken })).status, 200);
  assert.equal(db.prepare('SELECT origin FROM gf_player_initialization_receipts WHERE player_id=?').get(player.playerId).origin, 'approved-local-copy');
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_local_adoption_grants WHERE used_at IS NOT NULL').get().n, 1); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_local_adoption_approvals WHERE used_at IS NOT NULL').get().n, 1); db.close();
  f.advance(301);
  const replay = await f.put(player, payload, { operationId }); assert.equal(replay.status, 200); assert.equal(replay.value.replayed, true); assert.equal(replay.value.revision, 1);
  assert.equal((await f.grant(player, randomUUID(), payload.snapshotHash)).status, 409);
  const original = await f.request('/api/v1/save', 'GET', player.accessToken); assert.equal(original.value.snapshotBase64, payload.snapshotBase64);
});

test('approved copies preserve an old normal initialization marker and played resources; a grant never bypasses archive validation', async t => {
  const f = await fixture(t); const player = await f.guest();
  const oldInitialization = initializationFor({ ...player.initialization.defaults, initialCash: player.initialization.defaults.initialCash + 25 });
  const played = configuredArchive(player.initialization, { initialCoins: player.initialization.defaults.initialCoins - 100, initialExperience: 7, initialTutorialStep: 23, initialLanguage: 'vi' }, { markerHash: oldInitialization.configHash });
  const operationId = randomUUID();
  const ordinary = await f.put(player, played, { operationId }); assert.equal(ordinary.status, 400); assert.equal(ordinary.value.code, 'INITIALIZATION_MISMATCH');
  const selfAuthorized = await f.grant(player, operationId, played.snapshotHash); assert.equal(selfAuthorized.status, 403); assert.equal(selfAuthorized.value.code, 'LOCAL_ADOPTION_APPROVAL_REQUIRED');
  await f.approve(player, played.snapshotHash);
  const permission = await f.grant(player, operationId, played.snapshotHash); assert.equal(permission.status, 200);
  assert.equal((await f.put(player, played, { operationId, adoptionToken: permission.value.adoptionToken })).status, 200);
  const loaded = await f.request('/api/v1/save', 'GET', player.accessToken); assert.equal(loaded.value.snapshotBase64, played.snapshotBase64);
  const db = new DatabaseSync(f.filename, { readOnly: true }); assert.equal(db.prepare('SELECT origin FROM gf_player_initialization_receipts WHERE player_id=?').get(player.playerId).origin, 'approved-local-copy'); assert.deepEqual(JSON.parse(db.prepare('SELECT config_json FROM gf_player_initializations WHERE player_id=?').get(player.playerId).config_json), player.initialization.defaults); db.close();

  const other = await f.guest(); const invalidBytes = Buffer.from(played.snapshotBase64, 'base64'); invalidBytes[0] ^= 1;
  const malformed = { snapshotBase64: invalidBytes.toString('base64'), snapshotHash: createHash('sha256').update(invalidBytes).digest('hex') }; const malformedOperation = randomUUID();
  await f.approve(other, malformed.snapshotHash);
  const grant = await f.grant(other, malformedOperation, malformed.snapshotHash); assert.equal(grant.status, 200);
  const rejected = await f.put(other, malformed, { operationId: malformedOperation, adoptionToken: grant.value.adoptionToken }); assert.equal(rejected.status, 400); assert.equal(rejected.value.code, 'INVALID_SNAPSHOT');
  const read = new DatabaseSync(f.filename, { readOnly: true }); assert.equal(read.prepare('SELECT COUNT(*) AS n FROM gf_farm_snapshots WHERE player_id=?').get(other.playerId).n, 0); assert.equal(read.prepare('SELECT used_at FROM gf_local_adoption_grants WHERE player_id=?').get(other.playerId).used_at, null); read.close();
});
