import { readFileSync } from 'node:fs';
import { HttpError } from '../http.js';
import { decodeOriginalGameplayStats } from './original-gameplay-stats.js';

const catalog = JSON.parse(readFileSync(new URL('../../config/game_catalog_4_4_2.json', import.meta.url), 'utf8'));

/** Only the first save of a configured new farm is compared with its frozen
 * server defaults. Later saves represent normal purchases/progression; approved
 * copies of existing device-local progress take a separate authenticated path. */
export function assertInitialSnapshot(archive, initialization) {
  const marker = archive.records.get('new_player_defaults_v1');
  if (!marker) throw new HttpError(403, 'ADOPTION_AUTHORIZATION_REQUIRED');
  if (!Buffer.from(initialization.configHash, 'ascii').equals(marker)) throw new HttpError(400, 'INITIALIZATION_MISMATCH');
  let state;
  try { state = decodeOriginalGameplayStats(archive.records.get('save_gameplay'), archive.records.get('RMSHeader')); }
  catch { throw new HttpError(400, 'INITIALIZATION_MISMATCH'); }
  const expected = initialization.defaults;
  const matching = state.coins === BigInt(expected.initialCoins) && state.cash === expected.initialCash &&
    state.level === expected.initialLevel && state.experience === expected.initialExperience &&
    state.tutorialStep === expected.initialTutorialStep &&
    ['plots', 'trees', 'animals', 'ponds'].every(key => state.capacities[key] === expected.initialCapacityLimits[key]);
  const languageMatches = expected.initialLanguage === 'auto'
    ? Object.values(catalog.languageIndexByCode).includes(state.languageIndex)
    : catalog.languageIndexByCode[expected.initialLanguage] === state.languageIndex;
  if (!matching || !languageMatches) throw new HttpError(400, 'INITIALIZATION_MISMATCH');
}
