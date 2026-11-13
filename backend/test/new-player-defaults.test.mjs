import assert from 'node:assert/strict';
import test from 'node:test';
import { mkdtempSync, mkdirSync, readFileSync, writeFileSync, rmSync } from 'node:fs';
import { join } from 'node:path';
import { tmpdir } from 'node:os';
import { randomBytes, createHash, randomUUID } from 'node:crypto';
import { DatabaseSync } from 'node:sqlite';
import { loadNewPlayerDefaults, validateNewPlayerDefaults, initializationFor, DEFAULT_NEW_PLAYER_FILE } from '../src/services/new-player-defaults.js';
import { migrateSqlite } from '../src/db/sqlite-migrate.js';
import { createSqliteStore } from '../src/db/sqlite.js';
import { encodedFixture } from './helpers/archive.mjs';

const original = () => JSON.parse(readFileSync(DEFAULT_NEW_PLAYER_FILE, 'utf8'));
const invalid = value => assert.throws(() => validateNewPlayerDefaults(value), error => error.code === 'NEW_PLAYER_DEFAULTS_INVALID');

test('central defaults match original catalog and reject unsupported resource, XP, tutorial, language or template values', () => {
  const config = original();
  assert.equal(config.initialCoins, 2000); assert.equal(config.initialCash, 50); assert.equal(config.initialLevel, 1); assert.equal(config.initialExperience, 0); assert.equal(config.initialTutorialStep, 0); assert.equal(config.initialLanguage, 'auto');
  assert.deepEqual(config.initialCapacityLimits, { plots: 10, trees: 10, animals: 10, ponds: 1 });
  assert.deepEqual(validateNewPlayerDefaults(config), config);
  for (const [key, value] of [['schemaVersion', 2], ['initialLevel', 0], ['initialLevel', 51], ['initialCoins', -1], ['initialCoins', 100000000], ['initialCoins', 2.5], ['initialCash', 3000001], ['initialExperience', 13], ['initialTutorialStep', 1], ['initialLanguage', 'pt'], ['initialWorldTemplate', 'unlock-all']]) invalid({ ...config, [key]: value });
  invalid({ ...config, initialLevel: 50, initialExperience: 1 });
  assert.equal(validateNewPlayerDefaults({ ...config, initialLevel: 2, initialExperience: 18 }).initialExperience, 18);
  invalid({ ...config, initialLevel: 2, initialExperience: 19 });
  invalid({ ...config, initialItems: [{ id: 'invented-item', count: 999 }] });
  invalid({ ...config, initialCapacityLimits: { ...config.initialCapacityLimits, trees: 26 } });
  invalid({ ...config, initialCapacityLimits: { ...config.initialCapacityLimits, plots: 0 } });
  invalid({ ...config, initialCapacityLimits: { ...config.initialCapacityLimits, unknown: 1 } });
  const reordered = Object.fromEntries(Object.entries(config).reverse());
  assert.equal(initializationFor(reordered).configHash, initializationFor(config).configHash);
});

test('live configuration edits affect only newly created players, including retry/reissue and invalid-file rollback', async t => {
  const folder = mkdtempSync(join(tmpdir(), 'gf-defaults-live-')); t.after(() => rmSync(folder, { recursive: true, force: true }));
  const filename = join(folder, 'store.sqlite'); const configFile = join(folder, 'defaults.json');
  writeFileSync(configFile, '\uFEFF' + JSON.stringify(original())); await migrateSqlite(filename);
  const store = createSqliteStore({ filename }); t.after(() => store.close());
  const issue = (secret, extra = {}) => store.createGuestSession({ installationSecret: secret, accessToken: randomBytes(32).toString('base64url'), issuedAt: 100, expiresAt: 200, initializationFactory: () => loadNewPlayerDefaults(configFile), ...extra });
  const firstSecret = randomBytes(32).toString('base64url'); const first = await issue(firstSecret);
  const changed = { ...original(), initialCoins: original().initialCoins + 321, initialLanguage: 'vi' };
  writeFileSync(configFile, JSON.stringify(changed)); const second = await issue(randomBytes(32).toString('base64url'));
  assert.deepEqual(first.initialization.defaults, original()); assert.deepEqual(second.initialization.defaults, changed); assert.notEqual(first.initialization.configHash, second.initialization.configHash);
  const repeat = await issue(firstSecret, { previousPlayerId: first.playerId }); assert.equal(repeat.playerId, first.playerId); assert.deepEqual(repeat.initialization, first.initialization);
  writeFileSync(configFile, '{broken-config');
  const existing = await issue(firstSecret, { previousPlayerId: first.playerId }); assert.deepEqual(existing.initialization, first.initialization);
  await assert.rejects(issue(randomBytes(32).toString('base64url')), error => error.code === 'NEW_PLAYER_DEFAULTS_INVALID');
  const db = new DatabaseSync(filename, { readOnly: true });
  assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_players').get().n, 2); assert.equal(db.prepare('SELECT COUNT(*) AS n FROM gf_player_initializations').get().n, 2); db.close();
});

test('upgrading schema1 keeps existing players and exact farm bytes without assigning new initialization', async t => {
  const folder = mkdtempSync(join(tmpdir(), 'gf-defaults-upgrade-')); t.after(() => rmSync(folder, { recursive: true, force: true }));
  const directory = join(folder, 'schema1'); mkdirSync(directory);
  writeFileSync(join(directory, '001_accounts_players_progress.sql'), readFileSync(new URL('../sqlite-migrations/001_accounts_players_progress.sql', import.meta.url)));
  const filename = join(folder, 'store.sqlite'); await migrateSqlite(filename, { directory });
  const playerId = randomUUID(); const proof = randomBytes(32).toString('base64url'); const payload = encodedFixture({ marker: 33 });
  const db = new DatabaseSync(filename);
  db.prepare('INSERT INTO gf_players(id,installation_hash,created_at) VALUES(?,?,?)').run(playerId, createHash('sha256').update(proof).digest(), 100);
  db.prepare('INSERT INTO gf_farm_snapshots(player_id,revision,operation_id,snapshot_hash,snapshot_blob,updated_at) VALUES(?,?,?,?,?,?)').run(playerId, 7, randomUUID(), payload.snapshotHash, Buffer.from(payload.snapshotBase64, 'base64'), 200); db.close();
  const migrated = await migrateSqlite(filename); assert.deepEqual(migrated.applied, [2, 3, 4]); assert.ok(migrated.backupFile);
  const store = createSqliteStore({ filename }); t.after(() => store.close());
  const session = await store.createGuestSession({ installationSecret: proof, previousPlayerId: playerId, accessToken: randomBytes(32).toString('base64url'), issuedAt: 300, expiresAt: 400, initializationFactory: () => { throw new Error('Existing player must not read or apply defaults'); } });
  assert.equal(session.initialization, null); const loaded = await store.loadSnapshot(playerId); assert.equal(loaded.revision, 7); assert.equal(loaded.snapshotHash, payload.snapshotHash); assert.equal(loaded.snapshotBase64, payload.snapshotBase64);
  assert.equal((await store.health()).schemaVersion, 4);
});
