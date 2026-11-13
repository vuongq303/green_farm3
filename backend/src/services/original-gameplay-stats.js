// Original 4.4.2 format4 layout and signed Java bit operations, checked against
// original DEX, verbatim-method JVM vectors and a read-only normal save backup.
// Evidence: docs/backend-restoration-validation/new-player-oracle/.
const XOR_KEY = -1413816336;
const SHIFT = 9;
const PLAYER_FIELDS_AFTER_BINDING = 27 + 4 * 23 + 304 * 4;

function decodeProtectedInt(value) {
  const rotated = value ^ XOR_KEY;
  return (rotated << (32 - SHIFT)) | (rotated >> SHIFT);
}

function decodeProtectedLong(value) {
  const rotated = BigInt.asIntN(64, value ^ BigInt(XOR_KEY));
  return BigInt.asIntN(64, (rotated << BigInt(64 - SHIFT)) | (rotated >> BigInt(SHIFT)));
}

export function decodeOriginalGameplayStats(gameplayBuffer, rmsHeaderBuffer) {
  if (!Buffer.isBuffer(gameplayBuffer) || !Buffer.isBuffer(rmsHeaderBuffer) ||
      gameplayBuffer.length < 6 || gameplayBuffer[0] !== 4 || rmsHeaderBuffer.length < 35) {
    throw new Error('UNSUPPORTED_ORIGINAL_GAMEPLAY_FORMAT');
  }
  const bindingBytes = gameplayBuffer.readInt32LE(1);
  if (bindingBytes < 1 || bindingBytes > 1024) throw new Error('INVALID_GAMEPLAY_BINDING_LENGTH');
  const prefixEnd = 5 + bindingBytes;
  const playerOffset = prefixEnd + PLAYER_FIELDS_AFTER_BINDING;
  if (playerOffset + 46 > gameplayBuffer.length) throw new Error('TRUNCATED_ORIGINAL_GAMEPLAY');
  return {
    coins: decodeProtectedLong(gameplayBuffer.readBigInt64LE(playerOffset + 8)),
    cash: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset + 16)),
    level: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset)),
    experience: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset + 4)),
    tutorialStep: gameplayBuffer.readInt8(prefixEnd + 4),
    languageIndex: rmsHeaderBuffer[4],
    capacities: {
      plots: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset + 22)),
      trees: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset + 30)),
      animals: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset + 34)),
      ponds: decodeProtectedInt(gameplayBuffer.readInt32LE(playerOffset + 42)),
    },
  };
}
