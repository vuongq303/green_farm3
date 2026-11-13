import test from 'node:test';
import assert from 'node:assert/strict';
import { readFileSync } from 'node:fs';
import { decodeOriginalGameplayStats } from '../src/services/original-gameplay-stats.js';

const proof = JSON.parse(readFileSync(new URL('../../docs/backend-restoration-validation/new-player-oracle/original-gameplay-oracle.json', import.meta.url)));

function syntheticRecord(vector, binding = 'public-oracle-fixture') {
  const bindingBytes = Buffer.from(binding);
  const prefixEnd = 5 + bindingBytes.length;
  const playerOffset = prefixEnd + 1335;
  const gameplay = Buffer.alloc(playerOffset + 46);
  gameplay[0] = 4;
  gameplay.writeInt32LE(bindingBytes.length, 1);
  bindingBytes.copy(gameplay, 5);
  Buffer.from(vector.encodedHex, 'hex').copy(gameplay, playerOffset);
  // These protected integer bytes are also present in the original JVM vectors:
  // level1, level2, level49 and level50. Using those raw values avoids a second
  // test-only implementation of the encoder.
  const encodedLevel = index => Buffer.from(proof.vectors[index].encodedHex, 'hex').subarray(0, 4);
  encodedLevel(0).copy(gameplay, playerOffset + 22);
  encodedLevel(2).copy(gameplay, playerOffset + 30);
  encodedLevel(3).copy(gameplay, playerOffset + 34);
  encodedLevel(4).copy(gameplay, playerOffset + 42);
  const header = Buffer.alloc(35);
  header[4] = 16;
  return { gameplay, header, prefixEnd, playerOffset };
}

test('original JVM protected-value vectors decode exactly, including 64-bit sign extension', () => {
  for (const vector of proof.vectors) {
    const { gameplay, header } = syntheticRecord(vector);
    assert.deepEqual(decodeOriginalGameplayStats(gameplay, header), {
      coins: BigInt(vector.coins), cash: vector.cash, level: vector.level,
      experience: vector.experience, tutorialStep: 0, languageIndex: 16,
      capacities: { plots: 1, trees: 2, animals: 49, ponds: 50 },
    });
  }
});

test('binding byte length controls the prefix and tutorial preserves signed -1', () => {
  const { gameplay, header, prefixEnd } = syntheticRecord(proof.vectors[0], 'định-danh-fixture');
  gameplay.writeInt8(-1, prefixEnd + 4);
  assert.equal(decodeOriginalGameplayStats(gameplay, header).tutorialStep, -1);
  assert.equal(decodeOriginalGameplayStats(gameplay, header).coins, 2000n);
});

test('unsupported format, invalid binding lengths and every truncated stats boundary are refused', () => {
  const { gameplay, header, playerOffset } = syntheticRecord(proof.vectors[0]);
  const wrong = Buffer.from(gameplay); wrong[0] = 3;
  assert.throws(() => decodeOriginalGameplayStats(wrong, header), /UNSUPPORTED/);
  for (const length of [-1, 0, 1025, 2147483647]) {
    const invalid = Buffer.from(gameplay); invalid.writeInt32LE(length, 1);
    assert.throws(() => decodeOriginalGameplayStats(invalid, header), /BINDING/);
  }
  for (let length = 0; length < playerOffset + 46; length++) {
    assert.throws(() => decodeOriginalGameplayStats(gameplay.subarray(0, length), header));
  }
  assert.throws(() => decodeOriginalGameplayStats(gameplay, header.subarray(0, 34)));
  assert.throws(() => decodeOriginalGameplayStats([], header));
});
