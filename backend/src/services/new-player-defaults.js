import { readFileSync, statSync } from 'node:fs';
import { createHash } from 'node:crypto';
import { fileURLToPath } from 'node:url';

export const DEFAULT_NEW_PLAYER_FILE = fileURLToPath(new URL('../../config/new_player_defaults.json', import.meta.url));
const catalog = JSON.parse(readFileSync(new URL('../../config/game_catalog_4_4_2.json', import.meta.url), 'utf8'));
if (catalog.schemaVersion !== 1 || catalog.templateId !== 'original-4.4.2' || catalog.experienceToNextLevelByLevel.length !== 51) throw new Error('Unsupported original game catalog');

export class NewPlayerDefaultsError extends Error {
  constructor() { super('Invalid new-player defaults; check config/new_player_defaults.json against the verified game catalog'); this.code = 'NEW_PLAYER_DEFAULTS_INVALID'; }
}

function valid(condition) { if (!condition) throw new NewPlayerDefaultsError(); }
const integer = (value, minimum, maximum) => Number.isSafeInteger(value) && value >= minimum && value <= maximum;
function exactKeys(value, keys) {
  valid(value && typeof value === 'object' && !Array.isArray(value));
  valid(Object.keys(value).length === keys.length && keys.every(key => Object.hasOwn(value, key)));
}

/** Normalizes one documented schema before hashing; defaults are not repeated in code. */
export function validateNewPlayerDefaults(value) {
  exactKeys(value, ['schemaVersion', 'initialLevel', 'initialExperience', 'initialCoins', 'initialCash', 'initialTutorialStep', 'initialLanguage', 'initialCapacityLimits', 'initialWorldTemplate']);
  valid(value.schemaVersion === 1 && integer(value.initialLevel, 1, catalog.maxLevel));
  const maximumXp = value.initialLevel === catalog.maxLevel ? 0 : catalog.experienceToNextLevelByLevel[value.initialLevel] - 1;
  valid(integer(value.initialExperience, 0, maximumXp));
  valid(integer(value.initialCoins, 0, catalog.maxCoins) && integer(value.initialCash, 0, catalog.maxCash));
  // Other tutorial steps require state that has not been recovered as a seed.
  valid(value.initialTutorialStep === 0 && catalog.languages.includes(value.initialLanguage));
  valid(value.initialWorldTemplate === catalog.templateId);
  const capacities = value.initialCapacityLimits;
  exactKeys(capacities, ['plots', 'trees', 'animals', 'ponds']);
  for (const key of ['plots', 'trees', 'animals', 'ponds']) valid(integer(capacities[key], key === 'plots' ? 1 : 0, catalog.capacityMaximums[key]));
  return {
    schemaVersion: value.schemaVersion,
    initialLevel: value.initialLevel,
    initialExperience: value.initialExperience,
    initialCoins: value.initialCoins,
    initialCash: value.initialCash,
    initialTutorialStep: value.initialTutorialStep,
    initialLanguage: value.initialLanguage,
    initialCapacityLimits: { plots: capacities.plots, trees: capacities.trees, animals: capacities.animals, ponds: capacities.ponds },
    initialWorldTemplate: value.initialWorldTemplate,
  };
}

export function initializationFor(value) {
  const defaults = validateNewPlayerDefaults(value);
  const configHash = createHash('sha256').update(JSON.stringify(defaults), 'utf8').digest('hex');
  return { configHash, defaults };
}

/** Called only inside creation of a new player, never to reseed existing saves. */
export function loadNewPlayerDefaults(filename = DEFAULT_NEW_PLAYER_FILE) {
  try {
    valid(statSync(filename).isFile() && statSync(filename).size <= 16384);
    return initializationFor(JSON.parse(readFileSync(filename, 'utf8').replace(/^\uFEFF/u, '')));
  } catch { throw new NewPlayerDefaultsError(); }
}
