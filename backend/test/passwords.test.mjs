import assert from 'node:assert/strict';
import { test } from 'node:test';
import { hashPassword, normalizeIdentifier, registration, validPassword, verifyPassword } from '../src/auth/passwords.js';

test('password registration validates canonical identifiers, confirmation and bounded Unicode passwords', () => {
  const body = { username: ' Farmer_123 ', email: ' Farmer@Example.TEST ', password: 'Mật khẩu nông trại 123', password_confirmation: 'Mật khẩu nông trại 123' };
  assert.deepEqual(registration(body), { username: 'farmer_123', email: 'farmer@example.test', password: body.password });
  assert.equal(normalizeIdentifier(['farmer']), undefined);
  for (const change of [{ username: 'a' }, { username: 'x@y' }, { username: 'farmer.name' }, { email: 'a..b@example.test' },
    { email: 'a@-example.test' }, { email: 'a@example' }, { email: 'a@exámple.test' }, { email: '.a@example.test' },
    { email: 'a@example..test' }, { password: 'short' }, { password_confirmation: 'different password' }]) {
    assert.throws(() => registration({ ...body, ...change }), { code: 'invalid_registration' });
  }
  assert.equal(validPassword('a'.repeat(11)), false);
  assert.equal(validPassword('a'.repeat(129)), false);
  assert.equal(validPassword('a'.repeat(12) + '\u0000'), false);
  assert.equal(validPassword('a'.repeat(12) + '\ud800'), false);
  assert.equal(validPassword('🌻'.repeat(128)), true);
  assert.equal(validPassword(' '.repeat(12)), true, 'password spaces are preserved instead of silently trimmed');
});

test('actual Argon2id hashes are salted, verify exact passwords and reject unknown/corrupt credentials', async () => {
  const password = 'Mật khẩu nông trại 123';
  const first = await hashPassword(password), second = await hashPassword(password);
  assert.match(first, /^\$argon2id\$v=19\$m=65536,t=3,p=1\$/u);
  assert.notEqual(first, second);
  assert.ok(!first.includes(password));
  assert.equal(await verifyPassword(password, first), true);
  assert.equal(await verifyPassword(password + ' ', first), false);
  assert.equal(await verifyPassword(password, undefined), false, 'unknown accounts perform a dummy verification');
  assert.equal(await verifyPassword(password, 'malformed-private-database-value'), false);
  assert.equal(await verifyPassword('x', first), false);
});

test('password hashing has a bounded process-wide concurrency and no unbounded waiting queue', async () => {
  const results = await Promise.allSettled(Array.from({ length: 3 }, () => hashPassword('bounded hashing 123')));
  assert.equal(results.filter(result => result.status === 'fulfilled').length, 2);
  const denied = results.find(result => result.status === 'rejected');
  assert.equal(denied.reason.code, 'password_unavailable');
  assert.equal(await verifyPassword('bounded hashing 123', results.find(result => result.status === 'fulfilled').value), true);
});
