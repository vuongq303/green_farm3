import { createHash, timingSafeEqual } from 'node:crypto';
import { HttpError } from '../http.js';

export const DEFAULT_MAX_SNAPSHOT_BYTES = 16 * 1024 * 1024;
const MAX_RECORD_BYTES = 64 * 1024 * 1024;
const MAX_TOTAL_BYTES = 128 * 1024 * 1024;
const PACKAGES = new Set(['com.quanhoang.greenfarm3', 'com.gameloft.android.ANMP.GloftGF2F']);
const hash = bytes => createHash('sha256').update(bytes).digest();
const invalid = () => { throw new HttpError(400, 'INVALID_SNAPSHOT'); };
const valid = condition => { if (!condition) invalid(); };

/** Mirrors the existing Android SaveArchive GF3SAVE1 codec; no game rules invented. */
export function validateArchive(bytes, maxBytes = DEFAULT_MAX_SNAPSHOT_BYTES) {
  valid(Buffer.isBuffer(bytes) && bytes.length >= 96 && bytes.length <= maxBytes);
  let offset = 0;
  const take = size => { valid(Number.isSafeInteger(size) && size >= 0 && offset + size <= bytes.length); const result = bytes.subarray(offset, offset + size); offset += size; return result; };
  const int = () => take(4).readInt32BE();
  const long = () => take(8).readBigInt64BE();
  const text = () => { const length = int(); valid(length > 0 && length <= 4096); try { return new TextDecoder('utf-8', { fatal: true }).decode(take(length)); } catch { invalid(); } };
  valid(take(8).equals(Buffer.from('GF3SAVE1')) && int() === 1 && int() === 4);
  const sourcePackage = text();
  valid(PACKAGES.has(sourcePackage));
  const generation = long();
  const completeGeneration = long();
  const snapshotFormat = int();
  const poseSnapshot = int();
  const createdAt = long();
  const updatedAt = long();
  valid(generation > 0n && completeGeneration > 0n && completeGeneration <= generation && [0, 1].includes(snapshotFormat) && [0, 1].includes(poseSnapshot) && createdAt >= 0n && updatedAt >= 0n);
  const count = int();
  valid(count > 0 && count <= 2048);
  const records = new Map();
  let total = 0;
  for (let index = 0; index < count; index++) {
    const name = text();
    valid(name.length <= 512 && !name.includes('\0') && !name.includes('/') && !records.has(name) && name !== 'offline_test_balances_v1');
    const recordGeneration = long();
    valid(recordGeneration >= 0n && recordGeneration <= generation);
    const size = int();
    valid(size >= -1 && size <= MAX_RECORD_BYTES);
    total += Math.max(0, size);
    valid(total <= MAX_TOTAL_BYTES);
    let payload = null;
    if (size >= 0) {
      payload = take(size);
      valid(timingSafeEqual(take(32), hash(payload)));
    }
    records.set(name, payload);
  }
  const checksumOffset = offset;
  valid(timingSafeEqual(take(32), hash(bytes.subarray(0, checksumOffset))) && offset === bytes.length);
  valid((records.get('RMSHeader')?.length ?? 0) >= 35 && (records.get('save_farm0')?.length ?? 0) > 0);
  const gameplay = records.get('save_gameplay');
  valid(gameplay?.length >= 6 && gameplay[0] === 4);
  const bindingLength = gameplay.readInt32LE(1);
  valid(bindingLength >= 1 && bindingLength <= 1024 && bindingLength + 5 <= gameplay.length);
  try {
    const binding = new TextDecoder('utf-8', { fatal: true }).decode(gameplay.subarray(5, 5 + bindingLength));
    valid(!/[\u0000-\u001f]/u.test(binding));
  } catch { invalid(); }
  function words(name, magic, width, rewards) {
    const payload = records.get(name);
    valid(payload?.length >= 12 && payload.readInt32BE(0) === magic && payload.readInt32BE(4) === 1);
    const number = payload.readInt32BE(8);
    valid(number >= 0 && number <= 16384 && payload.length === 12 + number * width * 4);
    for (let i = 0; i < number; i++) {
      const at = 12 + i * width * 4;
      if (rewards) valid((payload.readInt32BE(at + 19 * 4) & ~3) === 0);
      else valid(payload.readInt32BE(at + 4 * 4) >= 0 && payload.readInt32BE(at + 5 * 4) >= 0 && payload.readInt32BE(at + 5 * 4) <= 7);
    }
  }
  if (snapshotFormat === 1) words('pending_rewards_v1', 0x47465257, 28, true);
  if (poseSnapshot === 1) words('entity_pose_v1', 0x47465053, 6, false);
  return { sourcePackage, generation: generation.toString(), recordCount: count, bytes: bytes.length, records };
}

export function parseSnapshot(base64, expectedHash, maxBytes = DEFAULT_MAX_SNAPSHOT_BYTES) {
  valid(typeof base64 === 'string' && base64.length <= Math.ceil(maxBytes / 3) * 4 && base64.length % 4 === 0);
  valid(typeof expectedHash === 'string' && /^[a-f0-9]{64}$/u.test(expectedHash));
  const bytes = Buffer.from(base64, 'base64');
  valid(bytes.toString('base64') === base64 && hash(bytes).toString('hex') === expectedHash);
  return { bytes, archive: validateArchive(bytes, maxBytes) };
}

export function decodeSnapshot(base64, expectedHash, maxBytes = DEFAULT_MAX_SNAPSHOT_BYTES) {
  return parseSnapshot(base64, expectedHash, maxBytes).bytes;
}
