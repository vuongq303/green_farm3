'use strict';

const apiRoot = '/admin/api/giftcodes';
const icons = new Map([['coins', '/admin/giftcodes/assets/coins.png'], ['cash', '/admin/giftcodes/assets/cash.png']]);
const https = location.protocol === 'https:';
const pageSize = 20;
const dateFormat = new Intl.DateTimeFormat('vi', { dateStyle: 'medium', timeStyle: 'short' });
const numberFormat = new Intl.NumberFormat('vi');
const statuses = new Map([
  ['active', 'Đang hoạt động'], ['scheduled', 'Chưa có hiệu lực'], ['expired', 'Hết hạn'],
  ['exhausted', 'Hết lượt'], ['disabled', 'Đã tắt'],
  ['pending', 'Chờ lưu vào game'], ['applied', 'Đã lưu vào game'],
]);
const messages = new Map(Object.entries({
  invalid_admin_session: 'Phiên quản trị đã hết hạn. Vui lòng đăng nhập lại.',
  invalid_admin_credentials: 'Mật khẩu quản trị chưa đúng.',
  admin_not_configured: 'Máy chủ chưa bật quyền quản trị giftcode.',
  invalid_request: 'Kiểm tra mã, thời gian hiệu lực, giới hạn lượt và phần thưởng.',
  invalid_giftcode: 'Mã phải có từ 4 đến 48 ký tự A–Z, 0–9 hoặc dấu gạch ngang.',
  invalid_rewards: 'Kiểm tra loại và số lượng từng phần thưởng.',
  invalid_enabled: 'Chọn trạng thái bật hoặc tắt cho giftcode.',
  invalid_dates: 'Kiểm tra ngày giờ bắt đầu và hết hạn.',
  invalid_date_range: 'Ngày hết hạn phải sau ngày bắt đầu.',
  giftcode_exists: 'Mã này đã tồn tại. Hãy kiểm tra danh sách hoặc chọn mã khác.',
  giftcode_not_found: 'Không tìm thấy giftcode này.',
  invalid_redemption_limit: 'Giới hạn lượt nhận phải từ 1 đến 1.000.000.000.',
  limit_below_redeemed: 'Giới hạn lượt nhận phải lớn hơn hoặc bằng số lượt đã dùng.',
  invalid_pagination: 'Không thể mở trang kết quả này.',
  invalid_filter: 'Kiểm tra bộ lọc giftcode.',
  csrf_failed: 'Yêu cầu phải được gửi từ đúng địa chỉ HTTPS của trang quản trị.',
  auth_not_configured: 'Máy chủ chưa đặt địa chỉ HTTPS xác thực.',
  https_required: 'Trang quản trị yêu cầu kết nối HTTPS.',
  password_unavailable: 'Máy chủ đang bận xử lý đăng nhập. Vui lòng thử lại.',
  admin_session_capacity: 'Có quá nhiều phiên quản trị đang mở. Vui lòng thử lại sau.',
  rate_limited: 'Bạn đã gửi quá nhiều yêu cầu. Hãy chờ rồi thử lại.',
  database_unavailable: 'Máy chủ dữ liệu tạm thời không sẵn sàng.',
}));
const byId = id => document.getElementById(id);
const editor = byId('editor');
const history = byId('history');
const state = {
  catalog: new Map(),
  listRequest: null,
  historyRequest: null,
  codeOffset: 0,
  historyOffset: 0,
  search: '',
  filter: '',
  historyCode: null,
  editing: null,
  rewards: new Map(),
  draft: null,
  saving: false,
};

function element(tag, text, className) {
  const node = document.createElement(tag);
  if (text != null) node.textContent = text;
  if (className) node.className = className;
  return node;
}

function normalizeCode(value) {
  return value.replace(/[ \t\r\n\f\v]+/gu, '')
    .replace(/[a-z]/gu, char => char.toUpperCase());
}

function notice(id, text = '', error = false) {
  const node = byId(id);
  node.textContent = text;
  node.hidden = !text;
  node.classList.toggle('notice-error', error);
}

class RequestError extends Error {
  constructor(status, code) {
    super(code);
    this.status = status;
    this.code = code;
  }
}

async function request(path = '', { method = 'GET', body, signal } = {}) {
  if (!https) throw new RequestError(400, 'https_required');
  const timeout = AbortSignal.timeout(15000);
  const response = await fetch(apiRoot + path, {
    method,
    credentials: 'same-origin',
    redirect: 'error',
    cache: 'no-store',
    headers: body === undefined ? { Accept: 'application/json' }
      : { Accept: 'application/json', 'Content-Type': 'application/json' },
    body: body === undefined ? undefined : JSON.stringify(body),
    signal: signal ? AbortSignal.any([signal, timeout]) : timeout,
  });
  const contentType = response.headers.get('content-type') ?? '';
  const data = contentType.startsWith('application/json') ? await response.json() : null;
  if (!response.ok || !data) throw new RequestError(response.status, data?.error);
  return data;
}

function showError(error, target = 'notice') {
  if (error.name === 'AbortError') return;
  if (error.status === 401 && error.code !== 'invalid_admin_credentials') {
    lock(messages.get('invalid_admin_session'));
    return;
  }
  const message = messages.get(error.code) ?? (
    error.name === 'TimeoutError' ? 'Máy chủ phản hồi quá chậm. Kiểm tra danh sách trước khi thử lưu lại.'
      : error.status ? 'Không thể hoàn thành yêu cầu. Vui lòng kiểm tra dữ liệu rồi thử lại.'
        : 'Không thể kết nối với máy chủ. Kiểm tra mạng rồi thử lại.'
  );
  notice(target, message, true);
}

function lock(message = '', focus = true) {
  state.listRequest?.abort();
  state.historyRequest?.abort();
  state.listRequest = null;
  state.historyRequest = null;
  if (editor.open) editor.close();
  if (history.open) history.close();
  state.catalog.clear();
  byId('codes').replaceChildren();
  byId('history-entries').replaceChildren();
  byId('dashboard').hidden = true;
  byId('login-panel').hidden = false;
  byId('logout').hidden = true;
  byId('admin-password').value = '';
  notice('notice', message, Boolean(message));
  if (focus) byId('admin-password').focus();
}

async function unlock() {
  const { items } = await request('/catalog');
  state.catalog = new Map(items.map(item => [item.id, item]));
  byId('login-panel').hidden = true;
  byId('dashboard').hidden = false;
  byId('logout').hidden = false;
  notice('notice');
  await loadCodes(0);
}

function dateLabel(value, empty = 'Không giới hạn') {
  return value ? dateFormat.format(new Date(value)) : empty;
}

function localTime(value) {
  if (!value) return '';
  const date = new Date(value);
  return new Date(date.valueOf() - date.getTimezoneOffset() * 60000).toISOString().slice(0, 19);
}

function isoTime(fieldId) {
  const value = byId(fieldId).value;
  if (!value) return null;
  const date = new Date(value);
  if (!Number.isFinite(date.valueOf()) || date.valueOf() < 0 || date.getUTCFullYear() > 9999) {
    throw new RangeError(fieldId);
  }
  return date.toISOString();
}

function stateBadge(status) {
  return element('span', statuses.get(status) ?? 'Không xác định',
    'state' + (statuses.has(status) ? ' state-' + status : ''));
}

function action(label, callback, description) {
  const button = element('button', label, 'text-button');
  button.type = 'button';
  if (description) button.setAttribute('aria-label', description);
  button.addEventListener('click', callback);
  return button;
}

function cell(label, content, className) {
  const td = element('td', null, className);
  td.dataset.label = label;
  td.append(content);
  return td;
}

async function copyCode(code) {
  try {
    await navigator.clipboard.writeText(code);
    notice('notice', 'Đã sao chép mã ' + code + '.');
  } catch {
    notice('notice', 'Trình duyệt chưa cho phép sao chép. Bạn có thể chọn và sao chép mã ' + code + ' trong danh sách.', true);
  }
}

async function editCode(id, button) {
  button.disabled = true;
  try {
    const code = await request('/' + encodeURIComponent(id));
    if (!byId('dashboard').hidden) openEditor(code);
  } catch (error) {
    showError(error);
  } finally {
    button.disabled = false;
  }
}

function codeRow(code) {
  const row = element('tr');
  const actions = element('div', null, 'row-actions');
  actions.append(
    action('Sao chép', () => copyCode(code.code), 'Sao chép ' + code.code),
    action('Chi tiết', event => editCode(code.id, event.currentTarget), 'Chi tiết ' + code.code),
    action('Lịch sử', () => openHistory(code), 'Lịch sử ' + code.code),
  );
  const usage = numberFormat.format(code.redeemed_count) + ' / ' +
    (code.max_redemptions == null ? '∞' : numberFormat.format(code.max_redemptions));
  row.append(
    cell('Mã', element('span', code.code, 'code')),
    cell('Trạng thái', stateBadge(code.status)),
    cell('Hết hạn', dateLabel(code.expires_at)),
    cell('Lượt đã dùng', usage),
    cell('Thao tác', actions),
  );
  return row;
}

function updatePager(prefix, offset, total) {
  const pages = Math.max(1, Math.ceil(total / pageSize));
  byId(prefix + '-page').textContent = 'Trang ' + (Math.floor(offset / pageSize) + 1) + ' / ' + pages;
  byId(prefix + '-prev').disabled = offset <= 0;
  byId(prefix + '-next').disabled = offset + pageSize >= total;
}

async function loadCodes(offset) {
  state.listRequest?.abort();
  const controller = new AbortController();
  state.listRequest = controller;
  byId('code-results').setAttribute('aria-busy', 'true');
  byId('codes-prev').disabled = true;
  byId('codes-next').disabled = true;
  const query = new URLSearchParams({ q: state.search, status: state.filter, limit: pageSize, offset });
  try {
    const { items, total } = await request('?' + query, { signal: controller.signal });
    if (state.listRequest !== controller) return;
    if (offset > 0 && offset >= total) {
      await loadCodes(Math.max(0, Math.floor((total - 1) / pageSize) * pageSize));
      return;
    }
    state.codeOffset = offset;
    byId('codes').replaceChildren(...items.map(codeRow));
    byId('list-empty').hidden = items.length > 0;
    byId('result-count').textContent = numberFormat.format(total) + ' mã';
    updatePager('codes', offset, total);
  } catch (error) {
    if (state.listRequest === controller) showError(error);
  } finally {
    if (state.listRequest === controller) byId('code-results').setAttribute('aria-busy', 'false');
  }
}

function rewardChip(reward, editable) {
  const item = state.catalog.get(reward.id);
  const row = element('li', null, 'reward');
  if (item?.icon && item.icon === icons.get(item.id)) {
    const image = element('img', null, 'reward-icon');
    image.src = item.icon;
    image.alt = '';
    row.append(image);
  } else {
    const icon = element('span', null, 'reward-icon');
    icon.setAttribute('aria-hidden', 'true');
    row.append(icon);
  }
  const title = element('span', item?.name ?? reward.id, 'reward-name');
  title.append(element('small', reward.id, 'reward-id'));
  const quantity = element('span', null, 'quantity');
  if (editable) {
    const input = element('input');
    input.type = 'number';
    input.min = '1';
    input.max = String(item?.max_quantity ?? Number.MAX_SAFE_INTEGER);
    input.step = '1';
    input.required = true;
    input.inputMode = 'numeric';
    input.value = String(reward.quantity);
    input.setAttribute('aria-label', 'Số lượng ' + (item?.name ?? reward.id));
    input.addEventListener('input', () => state.rewards.set(reward.id, input.valueAsNumber));
    quantity.append(input, action('Xóa', () => {
      state.rewards.delete(reward.id);
      renderRewards();
      byId('reward-picker').focus();
    }, 'Xóa ' + (item?.name ?? reward.id)));
  } else quantity.textContent = '× ' + numberFormat.format(reward.quantity);
  row.append(title, quantity);
  return row;
}

function updateRewardPicker() {
  const term = byId('reward-search').value.trim().toLocaleLowerCase('vi');
  const options = [...state.catalog.values()]
    .filter(item => !state.rewards.has(item.id) && (item.name.toLocaleLowerCase('vi').includes(term) || item.id.includes(term)))
    .map(item => {
      const option = element('option', item.name + ' · ' + item.id);
      option.value = item.id;
      return option;
    });
  const empty = element('option', 'Không có vật phẩm phù hợp');
  empty.value = '';
  byId('reward-picker').replaceChildren(...(options.length ? options : [empty]));
  byId('add-reward').disabled = !options.length;
}

function renderRewards() {
  const rows = [...state.rewards].map(([id, quantity]) => rewardChip({ id, quantity }, true));
  byId('reward-rows').replaceChildren(...rows);
  updateRewardPicker();
}

function openEditor(code = null) {
  if (editor.open || history.open) return;
  state.editing = code;
  state.rewards = new Map((code?.rewards ?? []).map(reward => [reward.id, reward.quantity]));
  state.draft = null;
  notice('editor-error');
  byId('editor-title').textContent = code ? 'Chi tiết ' + code.code : 'Tạo giftcode';
  byId('editor-form').reset();
  byId('editor-form').hidden = false;
  byId('preview').hidden = true;
  byId('code').value = code?.code ?? '';
  byId('code').readOnly = Boolean(code);
  byId('random-code').hidden = Boolean(code);
  byId('starts-at').value = localTime(code?.starts_at);
  byId('expires-at').value = localTime(code?.expires_at);
  byId('max-redemptions').value = code?.max_redemptions ?? '';
  byId('enabled').checked = code?.enabled ?? true;
  byId('editor-lock-note').hidden = !code?.redeemed_count;
  renderRewards();
  editor.showModal();
}

function randomCode() {
  const bytes = crypto.getRandomValues(new Uint8Array(8));
  return 'GF-' + Array.from(bytes, byte => byte.toString(16).padStart(2, '0')).join('').toUpperCase();
}

function details(list, fields) {
  list.replaceChildren(...fields.flatMap(([label, value]) => [element('dt', label), element('dd', value)]));
}

function preview() {
  notice('editor-error');
  const code = normalizeCode(byId('code').value) || randomCode();
  if (!/^[A-Z0-9-]{4,48}$/u.test(code)) {
    notice('editor-error', messages.get('invalid_giftcode'), true);
    byId('code').focus();
    return;
  }
  if (!state.rewards.size) {
    notice('editor-error', 'Thêm ít nhất một phần thưởng.', true);
    byId('reward-search').focus();
    return;
  }
  let start, expires;
  try {
    start = isoTime('starts-at');
    expires = isoTime('expires-at');
  } catch (error) {
    notice('editor-error', messages.get('invalid_dates'), true);
    byId(error.message).focus();
    return;
  }
  if (start && expires && start >= expires) {
    notice('editor-error', messages.get('invalid_date_range'), true);
    byId('expires-at').focus();
    return;
  }
  byId('code').value = code;
  const draft = {
    code, starts_at: start, expires_at: expires,
    enabled: byId('enabled').checked,
    max_redemptions: byId('max-redemptions').valueAsNumber || null,
    rewards: [...state.rewards].map(([id, quantity]) => ({ id, quantity })),
  };
  state.draft = draft;
  details(byId('preview-fields'), [
    ['Mã', draft.code],
    ['Bắt đầu', dateLabel(draft.starts_at, 'Ngay sau khi lưu')],
    ['Hết hạn', dateLabel(draft.expires_at)],
    ['Giới hạn lượt nhận', draft.max_redemptions == null ? 'Không giới hạn' : numberFormat.format(draft.max_redemptions)],
    ['Trạng thái', draft.enabled ? 'Bật' : 'Tắt'],
  ]);
  byId('preview-rewards').replaceChildren(...draft.rewards.map(reward => rewardChip(reward, false)));
  byId('editor-form').hidden = true;
  byId('preview').hidden = false;
  byId('preview-title').focus();
}

function patchFields(draft, original) {
  const updates = {};
  for (const [field, value] of Object.entries(draft)) {
    if ((field === 'starts_at' || field === 'expires_at') && localTime(value) === localTime(original[field])) continue;
    if (field !== 'code' && JSON.stringify(value) !== JSON.stringify(original[field])) updates[field] = value;
  }
  return updates;
}

async function saveCode() {
  if (!state.draft || state.saving) return;
  state.saving = true;
  byId('save-code').disabled = true;
  byId('back-to-editor').disabled = true;
  editor.querySelector('.close-button').disabled = true;
  notice('editor-error');
  try {
    const editing = state.editing;
    const fields = editing ? patchFields(state.draft, editing) : state.draft;
    if (editing && Object.keys(fields).length === 0) {
      editor.close();
      return;
    }
    const saved = await request(editing ? '/' + encodeURIComponent(editing.id) : '', {
      method: editing ? 'PATCH' : 'POST', body: fields,
    });
    editor.close();
    notice('notice', 'Đã lưu giftcode ' + saved.code + '.');
    await loadCodes(editing ? state.codeOffset : 0);
  } catch (error) {
    showError(error, 'editor-error');
  } finally {
    byId('save-code').disabled = false;
    byId('back-to-editor').disabled = false;
    editor.querySelector('.close-button').disabled = false;
    state.saving = false;
  }
}

function historyEntry(entry) {
  const item = element('li', null, 'history-item');
  const account = element('div', 'Tài khoản ' + entry.game_id, 'reward-name');
  const grant = element('span', 'Lượt nhận ' + entry.grant_id, 'history-id');
  const farm = element('span', 'Nông trại ' + entry.farm_id, 'history-id');
  const time = element('p', 'Đổi mã ' + dateLabel(entry.redeemed_at), 'muted');
  if (entry.applied_at) time.append(' · Đã lưu ' + dateLabel(entry.applied_at));
  const rewards = element('ul', null, 'reward-list');
  rewards.append(...entry.rewards.map(reward => rewardChip(reward, false)));
  item.append(account, grant, farm, stateBadge(entry.status), time, rewards);
  return item;
}

function openHistory(code) {
  if (editor.open || history.open) return;
  state.historyCode = code.id;
  byId('history-title').textContent = 'Lịch sử ' + code.code;
  byId('history-entries').replaceChildren();
  byId('history-empty').hidden = true;
  byId('history-page').textContent = '';
  history.showModal();
  void loadHistory(0);
}

async function loadHistory(offset) {
  state.historyRequest?.abort();
  const controller = new AbortController();
  state.historyRequest = controller;
  const query = new URLSearchParams({ offset, limit: pageSize });
  byId('history-results').setAttribute('aria-busy', 'true');
  byId('history-prev').disabled = true;
  byId('history-next').disabled = true;
  notice('history-error');
  try {
    const { items, total } = await request('/' + encodeURIComponent(state.historyCode) + '/redemptions?' + query,
      { signal: controller.signal });
    if (state.historyRequest !== controller || !history.open) return;
    state.historyOffset = offset;
    byId('history-entries').replaceChildren(...items.map(historyEntry));
    byId('history-empty').hidden = items.length > 0;
    updatePager('history', offset, total);
  } catch (error) {
    if (history.open && state.historyRequest === controller) showError(error, 'history-error');
  } finally {
    if (state.historyRequest === controller) byId('history-results').setAttribute('aria-busy', 'false');
  }
}

byId('login-form').addEventListener('submit', async event => {
  event.preventDefault();
  const password = byId('admin-password');
  const login = byId('login');
  if (login.disabled || !password.value) return;
  const entered = password.value;
  password.value = '';
  login.disabled = true;
  notice('notice');
  try {
    await request('/login', { method: 'POST', body: { password: entered } });
    await unlock();
  } catch (error) {
    showError(error);
    password.focus();
  } finally {
    login.disabled = false;
  }
});
byId('logout').addEventListener('click', async () => {
  const button = byId('logout');
  if (button.disabled) return;
  button.disabled = true;
  try {
    await request('/logout', { method: 'POST', body: {} });
    lock();
  } catch (error) {
    showError(error);
  } finally {
    button.disabled = false;
  }
});
function search() {
  const query = normalizeCode(byId('query').value);
  if (!/^[A-Z0-9-]{0,48}$/u.test(query)) {
    notice('notice', 'Tìm theo chữ A–Z, số 0–9 hoặc dấu gạch ngang.', true);
    byId('query').focus();
    return;
  }
  state.search = query;
  state.filter = byId('filter-status').value;
  notice('notice');
  void loadCodes(0);
}
byId('filters').addEventListener('submit', event => {
  event.preventDefault();
  search();
});
byId('filter-status').addEventListener('change', search);
byId('codes-prev').addEventListener('click', () => loadCodes(state.codeOffset - pageSize));
byId('codes-next').addEventListener('click', () => loadCodes(state.codeOffset + pageSize));
byId('new-code').addEventListener('click', () => openEditor());
byId('random-code').addEventListener('click', () => { byId('code').value = randomCode(); });
byId('reward-search').addEventListener('input', updateRewardPicker);
byId('add-reward').addEventListener('click', () => {
  const id = byId('reward-picker').value;
  if (!state.catalog.has(id) || state.rewards.has(id)) return;
  state.rewards.set(id, 1);
  renderRewards();
  byId('reward-rows').lastElementChild.querySelector('input').focus();
});
byId('editor-form').addEventListener('submit', event => { event.preventDefault(); preview(); });
byId('back-to-editor').addEventListener('click', () => {
  byId('preview').hidden = true;
  byId('editor-form').hidden = false;
  byId('code').focus();
  notice('editor-error');
});
byId('save-code').addEventListener('click', saveCode);
byId('history-prev').addEventListener('click', () => loadHistory(state.historyOffset - pageSize));
byId('history-next').addEventListener('click', () => loadHistory(state.historyOffset + pageSize));
history.addEventListener('close', () => state.historyRequest?.abort());
editor.addEventListener('cancel', event => { if (state.saving) event.preventDefault(); });
for (const button of document.querySelectorAll('[data-close]')) {
  button.addEventListener('click', () => {
    if (button.dataset.close !== 'editor' || !state.saving) byId(button.dataset.close).close();
  });
}

if (https) {
  void unlock().catch(error => {
    lock('', false);
    if (error.status !== 401) showError(error);
  });
} else {
  lock(messages.get('https_required'), false);
  byId('admin-password').disabled = true;
  byId('login').disabled = true;
}
