// Explicit test double for HTTP protocol tests only. Production main.js always
// constructs createMysqlStore; this file is not imported by application code.
export function createTestStore() {
  const accounts = new Map();
  const sessions = new Map();
  const compliance = new Map();
  let nextId = 1;
  let unavailable = false;
  const check = () => {
    if (unavailable) throw Object.assign(new Error('test database unavailable'), { code: 'DATABASE_UNAVAILABLE' });
  };
  return {
    setUnavailable(value) { unavailable = value; },
    async health() { check(); return { status: 'ready', schemaVersion: 1 }; },
    async createSession(value) {
      check();
      const key = JSON.stringify([value.clientId, value.credential]);
      if (!accounts.has(key)) accounts.set(key, String(nextId++));
      sessions.set(value.sid, { ...value, accountId: accounts.get(key) });
    },
    async findSession(sid) { check(); return sessions.get(sid); },
    async refreshSession(value) {
      check();
      const previous = sessions.get(value.previousSid);
      if (!previous || previous.refreshExpiresAt <= value.issuedAt) throw Object.assign(new Error('invalid session'), { code: 'INVALID_SESSION' });
      sessions.set(value.sid, { ...previous, ...value });
      sessions.delete(value.previousSid);
    },
    async saveCompliance(value) { check(); compliance.set(value.accountId, { ...value }); },
    complianceFor(accountId) { return compliance.get(accountId); },
    async close() {},
  };
}
