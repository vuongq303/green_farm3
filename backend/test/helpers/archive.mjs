import { createHash } from 'node:crypto';

export function archiveFixture({ marker = 7, sourcePackage = 'com.quanhoang.greenfarm3', extraRecords = [], generation = 1, gameplayPayload, headerPayload } = {}) {
  const hash = value => createHash('sha256').update(value).digest();
  const int = value => { const bytes = Buffer.alloc(4); bytes.writeInt32BE(value); return bytes; };
  const long = value => { const bytes = Buffer.alloc(8); bytes.writeBigInt64BE(BigInt(value)); return bytes; };
  const text = value => { const bytes = Buffer.from(value); return Buffer.concat([int(bytes.length), bytes]); };
  const binding = Buffer.from('fixture-device-binding');
  const gameplay = Buffer.alloc(5 + binding.length + 4);
  gameplay[0] = 4;
  gameplay.writeInt32LE(binding.length, 1);
  binding.copy(gameplay, 5);
  gameplay.writeInt32LE(marker, 5 + binding.length);
  const records = [
    ['RMSHeader', headerPayload ?? Buffer.alloc(35, marker)], ['save_gameplay', gameplayPayload ?? gameplay], ['save_farm0', Buffer.from([marker, 8, 9])],
    ['pending_rewards_v1', Buffer.concat([int(0x47465257), int(1), int(0)])],
    ['entity_pose_v1', Buffer.concat([int(0x47465053), int(1), int(0)])], ...extraRecords,
  ].sort(([a], [b]) => a.localeCompare(b, 'en'));
  const parts = [Buffer.from('GF3SAVE1'), int(1), int(4), text(sourcePackage), long(generation), long(generation), int(1), int(1), long(1000), long(2000), int(records.length)];
  for (const [name, payload] of records) {
    parts.push(text(name), long(generation), int(payload === null ? -1 : payload.length));
    if (payload !== null) parts.push(payload, hash(payload));
  }
  const body = Buffer.concat(parts);
  return Buffer.concat([body, hash(body)]);
}

export function encodedFixture(options) {
  const bytes = archiveFixture(options);
  return { snapshotBase64: bytes.toString('base64'), snapshotHash: createHash('sha256').update(bytes).digest('hex') };
}
