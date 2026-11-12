-- Executed automatically by GameSaveStore.Helper.onCreate; SQLiteOpenHelper tracks user_version.

CREATE TABLE IF NOT EXISTS save_slots (slot_id TEXT PRIMARY KEY NOT NULL, generation INTEGER NOT NULL DEFAULT 0 CHECK(generation>=0), created_at INTEGER NOT NULL, updated_at INTEGER NOT NULL);

CREATE TABLE IF NOT EXISTS save_records (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, record_id INTEGER NOT NULL CHECK(record_id=1), payload BLOB, checksum BLOB, deleted INTEGER NOT NULL DEFAULT 0 CHECK(deleted IN(0,1)), generation INTEGER NOT NULL CHECK(generation>=0), PRIMARY KEY(slot_id,store_name,record_id), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id), CHECK((deleted=1 AND payload IS NULL AND checksum IS NULL) OR (deleted=0 AND payload IS NOT NULL AND checksum IS NOT NULL AND length(checksum)=32 AND length(payload)<=67108864)));

CREATE TABLE IF NOT EXISTS save_commits (slot_id TEXT NOT NULL, generation INTEGER NOT NULL, committed_at INTEGER NOT NULL, changed_records INTEGER NOT NULL, is_snapshot INTEGER NOT NULL CHECK(is_snapshot IN(0,1)), PRIMARY KEY(slot_id,generation), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id));

CREATE TABLE IF NOT EXISTS legacy_imports (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, imported_at INTEGER NOT NULL, PRIMARY KEY(slot_id,store_name), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id));

CREATE INDEX IF NOT EXISTS save_records_generation ON save_records(slot_id,generation);
