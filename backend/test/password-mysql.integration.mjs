import assert from 'node:assert/strict';
import { createHash, randomBytes } from 'node:crypto';
import { after, test } from 'node:test';
import { loadDatabaseConfig, loadEnvironment } from '../src/config.js';
import { createPoolFromConfig } from '../src/db/store.js';
import { createSocialStore } from '../src/db/social-store.js';
import { migrate } from '../src/db/migrate.js';

loadEnvironment();
const config = loadDatabaseConfig();
config.database = process.env.TEST_DB_NAME ?? config.database;
if (!config.database.endsWith('_test')) throw new Error('Password MySQL tests require a dedicated database ending in _test');
const pool = createPoolFromConfig(config);
const store = createSocialStore(pool);
await migrate(pool);
after(() => pool.end());

const hash = value => createHash('sha256').update(value).digest();
const token = () => randomBytes(32).toString('base64url');
// Password hashing itself is tested against the production hasher in unit/HTTP
// tests. This fixture only checks prepared persistence and account boundaries.
const passwordHash = '$argon2id$v=19$m=65536,t=3,p=1$c3RvcmUtZml4dHVyZS1zYWx0$ZmFrZS1maXh0dXJlLWhhc2gtZm9yLWRiLXRlc3Q';

function fixture(t) {
  const prefix = 'pwdtest-' + randomBytes(8).toString('hex');
  const now = Math.floor(Date.now() / 1000);
  t.after(async () => {
    await pool.execute('DELETE FROM gf_auth_codes WHERE attempt_id LIKE ?', [prefix + '%']);
    await pool.execute(`DELETE FROM gf_auth_sessions WHERE user_id IN
      (SELECT id FROM users WHERE provider_user_id LIKE ?)`, [prefix + '%']);
    await pool.execute('DELETE FROM gf_auth_attempts WHERE id LIKE ?', [prefix + '%']);
    await pool.execute('DELETE FROM users WHERE provider_user_id LIKE ?', [prefix + '%']);
  });
  const record = (suffix = '', extra = {}) => ({ providerUserId: prefix + suffix,
    username: prefix + suffix, email: prefix + suffix + '@example.test', passwordHash, now, ...extra });
  const attempt = (extra = {}) => ({ id: prefix + '-' + randomBytes(4).toString('hex'), provider: 'password',
    appState: token(), codeChallenge: token(), redirectUri: 'com.quanhoang.greenfarm3://oauth/callback',
    createdAt: now, expiresAt: now + 300, ...extra });
  const session = () => ({ id: token(), accessHash: hash(token()), refreshHash: hash(token()),
    issuedAt: now, accessExpiresAt: now + 900, refreshExpiresAt: now + 3600 });
  return { prefix, now, record, attempt, session };
}

async function readyPasswordCode(f, account) {
  const record = f.attempt(), cookieHash = hash(token()), csrfHash = hash(token());
  await store.createAttempt(record);
  assert.ok(await store.startPasswordAttempt(record.id, cookieHash, csrfHash, f.now));
  assert.ok(await store.claimPasswordAttempt(cookieHash, csrfHash, f.now));
  const codeHash = hash(token());
  assert.equal(await store.issueCode({ attemptId: record.id, userId: account.id,
    codeHash, expiresAt: f.now + 60, now: f.now }), true);
  return { record, codeHash };
}

test('password migration is additive, repeatable and preserves provider identities byte-for-byte', async t => {
  const f = fixture(t);
  const facebook = await store.upsertUser('facebook', f.prefix + '-fb', 'Existing Facebook', f.now);
  const google = await store.upsertUser('google', f.prefix + '-google', 'Existing Google', f.now);
  const [before] = await pool.execute('SELECT * FROM users WHERE id IN (?, ?) ORDER BY id', [facebook.id, google.id]);
  const again = await migrate(pool);
  assert.deepEqual(again.applied, []);
  assert.ok(again.alreadyApplied.includes(4));
  const [afterRows] = await pool.execute('SELECT * FROM users WHERE id IN (?, ?) ORDER BY id', [facebook.id, google.id]);
  assert.deepEqual(afterRows, before);
  assert.deepEqual(await store.health(), { status: 'ready', schemaVersion: 4 });
  const [[credentials]] = await pool.execute('SELECT COUNT(*) AS total FROM gf_password_accounts WHERE user_id IN (?, ?)',
    [facebook.id, google.id]);
  assert.equal(Number(credentials.total), 0, 'provider identities are never converted to password accounts');
});

test('password registration commits a stable game ID and duplicate username/email races leave no orphan user', async t => {
  const f = fixture(t), first = f.record();
  const account = await store.registerPasswordUser(first);
  assert.match(account.game_id, /^GF\d{6,}$/u);
  assert.equal(account.provider, 'password');
  const byUsername = await store.findPasswordCredential(first.username);
  const byEmail = await store.findPasswordCredential(first.email);
  assert.deepEqual(byUsername, byEmail);
  assert.equal(byUsername.userId, account.id);
  assert.equal(byUsername.passwordHash, passwordHash);
  assert.equal(byUsername.accountStatus, 'active');
  assert.equal(await store.findPasswordCredential("' OR 1=1 --"), undefined, 'identifier remains a prepared value');
  assert.equal(await store.registerPasswordUser(f.record('-a', { username: first.username })), undefined);
  assert.equal(await store.registerPasswordUser(f.record('-b', { email: first.email })), undefined);
  const [[count]] = await pool.execute('SELECT COUNT(*) AS total FROM users WHERE provider_user_id LIKE ?', [f.prefix + '%']);
  assert.equal(Number(count.total), 1, 'duplicate failures roll back the inserted users row');
  const attempts = Array.from({ length: 6 }, (_, index) => f.record('-r' + index, {
    username: f.prefix + '-race', email: f.prefix + '-race@example.test',
  }));
  const registered = await Promise.all(attempts.map(record => store.registerPasswordUser(record)));
  assert.equal(registered.filter(Boolean).length, 1);
  const raceUser = registered.find(Boolean);
  const restarted = createSocialStore(pool);
  assert.equal((await restarted.loginPasswordUser(raceUser.id, f.now + 10)).game_id, raceUser.game_id);
  const [[row]] = await pool.execute('SELECT last_login_at FROM gf_password_accounts WHERE user_id = ?', [raceUser.id]);
  assert.equal(Number(row.last_login_at), f.now + 10);
});

test('WebView cookie and CSRF are hashed, attempt bound, expiry checked and claimed only once', async t => {
  const f = fixture(t), record = f.attempt();
  const cookieHash = hash(token()), csrfHash = hash(token());
  await store.createAttempt(record);
  const starts = await Promise.all([store.startPasswordAttempt(record.id, cookieHash, csrfHash, f.now),
    store.startPasswordAttempt(record.id, hash(token()), hash(token()), f.now)]);
  assert.equal(starts.filter(Boolean).length, 1);
  // The first transaction starts immediately on this pool, but pick whichever
  // cookie actually won instead of relying on scheduling for the assertions.
  const [[saved]] = await pool.execute('SELECT cookie_hash, csrf_hash FROM gf_auth_web_sessions WHERE attempt_id = ?', [record.id]);
  const current = await store.getPasswordWebSession(saved.cookie_hash, f.now);
  assert.equal(current.attempt.id, record.id);
  assert.equal(current.attempt.appState, record.appState);
  assert.deepEqual(current.csrfHash, saved.csrf_hash);
  assert.equal(await store.getPasswordWebSession(hash(token()), f.now), undefined);
  assert.equal(await store.claimPasswordAttempt(saved.cookie_hash, hash(token()), f.now), undefined);
  assert.ok(await store.getPasswordWebSession(saved.cookie_hash, f.now), 'bad CSRF does not consume pending login');
  const claims = await Promise.all([store.claimPasswordAttempt(saved.cookie_hash, saved.csrf_hash, f.now),
    store.claimPasswordAttempt(saved.cookie_hash, saved.csrf_hash, f.now)]);
  assert.equal(claims.filter(Boolean).length, 1);
  assert.equal(await store.getPasswordWebSession(saved.cookie_hash, f.now), undefined);
  const expired = f.attempt({ expiresAt: f.now + 1 });
  await store.createAttempt(expired);
  const expiryCookie = hash(token()), expiryCsrf = hash(token());
  assert.ok(await store.startPasswordAttempt(expired.id, expiryCookie, expiryCsrf, f.now));
  assert.equal(await store.getPasswordWebSession(expiryCookie, f.now + 1), undefined);
  assert.equal(await store.claimPasswordAttempt(expiryCookie, expiryCsrf, f.now + 1), undefined);
  const provider = f.attempt({ provider: 'facebook' });
  await store.createAttempt(provider);
  assert.equal(await store.startPasswordAttempt(provider.id, hash(token()), hash(token()), f.now), undefined);
});

test('password authorization reuses existing PKCE exchange, refresh and logout without provider identity merging', async t => {
  const f = fixture(t), account = await store.registerPasswordUser(f.record());
  const social = await store.upsertUser('facebook', f.prefix, account.display_name, f.now);
  assert.notEqual(account.game_id, social.game_id);
  const { record, codeHash } = await readyPasswordCode(f, account);
  const exchange = { codeHash, codeChallenge: record.codeChallenge, redirectUri: record.redirectUri, now: f.now };
  assert.equal(await store.exchangeCode({ ...exchange, codeChallenge: token(), session: f.session() }), undefined);
  const session = f.session();
  assert.equal((await store.exchangeCode({ ...exchange, session })).game_id, account.game_id);
  assert.equal((await store.findAccess(session.accessHash, f.now)).user.game_id, account.game_id);
  assert.equal(await store.exchangeCode({ ...exchange, session: f.session() }), undefined);
  const refreshed = f.session();
  assert.equal((await store.rotateRefresh(session.refreshHash, refreshed, f.now)).game_id, account.game_id);
  assert.equal(await store.findAccess(session.accessHash, f.now), undefined);
  assert.equal(await store.revoke(session.accessHash, session.refreshHash, f.now), true);
  assert.equal(await store.findAccess(refreshed.accessHash, f.now), undefined);
  assert.equal(await store.rotateRefresh(refreshed.refreshHash, f.session(), f.now), undefined);
});

test('disabled password accounts cannot log in, exchange pending codes, use access or rotate refresh', async t => {
  const f = fixture(t), account = await store.registerPasswordUser(f.record());
  const first = await readyPasswordCode(f, account), session = f.session();
  await store.exchangeCode({ codeHash: first.codeHash, codeChallenge: first.record.codeChallenge,
    redirectUri: first.record.redirectUri, now: f.now, session });
  const pending = await readyPasswordCode(f, account);
  await pool.execute("UPDATE gf_password_accounts SET account_status = 'disabled' WHERE user_id = ?", [account.id]);
  assert.equal((await store.findPasswordCredential(f.record().username)).accountStatus, 'disabled');
  assert.equal(await store.loginPasswordUser(account.id, f.now), undefined);
  assert.equal(await store.findAccess(session.accessHash, f.now), undefined);
  assert.equal(await store.rotateRefresh(session.refreshHash, f.session(), f.now), undefined);
  assert.equal(await store.exchangeCode({ codeHash: pending.codeHash, codeChallenge: pending.record.codeChallenge,
    redirectUri: pending.record.redirectUri, now: f.now, session: f.session() }), undefined);
});

test('password attempt cannot issue an authorization code for a Facebook identity', async t => {
  const f = fixture(t), record = f.attempt();
  const account = await store.upsertUser('facebook', f.prefix, 'Facebook', f.now);
  const cookieHash = hash(token()), csrfHash = hash(token());
  await store.createAttempt(record);
  await store.startPasswordAttempt(record.id, cookieHash, csrfHash, f.now);
  await store.claimPasswordAttempt(cookieHash, csrfHash, f.now);
  assert.equal(await store.issueCode({ attemptId: record.id, userId: account.id,
    codeHash: hash(token()), expiresAt: f.now + 60, now: f.now }), false);
});

test('registration and WebView claim are atomic across duplicate credentials and concurrent submissions', async t => {
  const f = fixture(t), existing = f.record('-old');
  await store.registerPasswordUser(existing);
  const record = f.attempt(), cookieHash = hash(token()), csrfHash = hash(token());
  await store.createAttempt(record);
  await store.startPasswordAttempt(record.id, cookieHash, csrfHash, f.now);
  assert.deepEqual(await store.registerPasswordAndClaim({ ...f.record('-dup', { username: existing.username }),
    cookieHash, csrfHash }), { duplicate: true, field: 'username' });
  assert.deepEqual(await store.registerPasswordAndClaim({ ...f.record('-dup-email', { email: existing.email }),
    cookieHash, csrfHash }), { duplicate: true, field: 'email' });
  assert.deepEqual(await store.registerPasswordAndClaim({ ...f.record('-dup-both',
    { username: existing.username, email: existing.email }), cookieHash, csrfHash }),
    { duplicate: true, field: 'both' });
  assert.ok(await store.getPasswordWebSession(cookieHash, f.now), 'duplicate credentials roll back attempt consumption');
  assert.equal(await store.registerPasswordAndClaim({ ...f.record('-csrf'), cookieHash, csrfHash: hash(token()) }), undefined);
  const candidates = [f.record('-new-a'), f.record('-new-b')];
  const results = await Promise.all(candidates.map(candidate => store.registerPasswordAndClaim({ ...candidate, cookieHash, csrfHash })));
  assert.equal(results.filter(Boolean).length, 1, 'only one account is created by one pending WebView');
  const result = results.find(Boolean);
  assert.equal(result.attempt.id, record.id);
  assert.equal(result.attempt.consumedAt, f.now);
  assert.equal(result.user.provider, 'password');
  assert.equal(await store.getPasswordWebSession(cookieHash, f.now), undefined);
  const [[count]] = await pool.execute('SELECT COUNT(*) AS total FROM users WHERE provider_user_id LIKE ?', [f.prefix + '%']);
  assert.equal(Number(count.total), 2, 'invalid and losing requests leave no extra users behind');
  const codeHash = hash(token());
  assert.equal(await store.issueCode({ attemptId: record.id, userId: result.user.id,
    codeHash, expiresAt: f.now + 60, now: f.now }), true);
  assert.equal((await store.exchangeCode({ codeHash, codeChallenge: record.codeChallenge,
    redirectUri: record.redirectUri, now: f.now, session: f.session() })).game_id, result.user.game_id);
});

test('expired or already-consumed WebView cannot register any account', async t => {
  const f = fixture(t), record = f.attempt();
  const cookieHash = hash(token()), csrfHash = hash(token());
  await store.createAttempt(record);
  await store.startPasswordAttempt(record.id, cookieHash, csrfHash, f.now);
  await store.claimPasswordAttempt(cookieHash, csrfHash, f.now);
  assert.equal(await store.registerPasswordAndClaim({ ...f.record('-used'), cookieHash, csrfHash }), undefined);
  const expired = f.attempt({ expiresAt: f.now + 1 }), expiredCookie = hash(token()), expiredCsrf = hash(token());
  await store.createAttempt(expired);
  await store.startPasswordAttempt(expired.id, expiredCookie, expiredCsrf, f.now);
  assert.equal(await store.registerPasswordAndClaim({ ...f.record('-exp', { now: f.now + 1 }),
    cookieHash: expiredCookie, csrfHash: expiredCsrf }), undefined);
  const [[count]] = await pool.execute('SELECT COUNT(*) AS total FROM users WHERE provider_user_id LIKE ?', [f.prefix + '%']);
  assert.equal(Number(count.total), 0);
});
