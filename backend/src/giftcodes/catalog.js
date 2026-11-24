import { HttpError } from '../http.js';

// These are the actual engine currency caps. A farm whose balance is too
// high to credit the full amount keeps the entire grant pending.
export const REWARD_CATALOG = Object.freeze([
  Object.freeze({ id: 'coins', name: 'Xu', max_quantity: 99_999_999, icon: '/admin/giftcodes/assets/coins.png' }),
  Object.freeze({ id: 'cash', name: 'Tiền mặt', max_quantity: 3_000_000, icon: '/admin/giftcodes/assets/cash.png' }),
]);

export function normalizeGiftcode(value) {
  if (typeof value !== 'string' || value.length > 256 || /[^a-zA-Z0-9\- \t\r\n\f\v]/u.test(value)) return undefined;
  return value.replace(/[ \t\r\n\f\v]/gu, '').toUpperCase();
}

export function validRewards(value) {
  if (!Array.isArray(value) || !value.length || value.length > REWARD_CATALOG.length) {
    throw new HttpError(400, 'invalid_rewards');
  }
  const seen = new Set();
  return value.map(entry => {
    const item = REWARD_CATALOG.find(reward => reward.id === entry?.id);
    if (!item || seen.has(item.id) || Object.keys(entry).some(key => !['id', 'quantity'].includes(key)) ||
        !Number.isSafeInteger(entry.quantity) || entry.quantity < 1 || entry.quantity > item.max_quantity) {
      throw new HttpError(400, 'invalid_rewards');
    }
    seen.add(item.id);
    return { id: item.id, quantity: entry.quantity };
  });
}
