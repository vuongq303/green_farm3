-- Legacy identity records remain separate from strong guest save ownership.
CREATE TABLE gf_storage_decisions (
  name TEXT PRIMARY KEY,
  decision TEXT NOT NULL,
  recorded_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
) STRICT;
CREATE TABLE gf_accounts (
  id TEXT PRIMARY KEY,
  identity_hash BLOB NOT NULL UNIQUE,
  client_id TEXT NOT NULL,
  credential TEXT NOT NULL,
  password_salt BLOB NOT NULL,
  password_hash BLOB NOT NULL,
  created_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_at TEXT NOT NULL DEFAULT CURRENT_TIMESTAMP
) STRICT;
CREATE TABLE gf_sessions (
  sid_hash BLOB PRIMARY KEY,
  account_id TEXT NOT NULL REFERENCES gf_accounts(id),
  device_id TEXT NOT NULL,
  issued_at INTEGER NOT NULL,
  expires_at INTEGER NOT NULL,
  refresh_expires_at INTEGER NOT NULL
) STRICT;
CREATE INDEX gf_sessions_expiry ON gf_sessions(refresh_expires_at);
CREATE TABLE gf_compliance (
  account_id TEXT PRIMARY KEY REFERENCES gf_accounts(id),
  country TEXT NOT NULL,
  age INTEGER NOT NULL,
  updated_at INTEGER NOT NULL
) STRICT;
CREATE TABLE gf_players (
  id TEXT PRIMARY KEY,
  installation_hash BLOB NOT NULL UNIQUE,
  created_at INTEGER NOT NULL
) STRICT;
CREATE TABLE gf_guest_sessions (
  token_hash BLOB PRIMARY KEY,
  player_id TEXT NOT NULL REFERENCES gf_players(id),
  issued_at INTEGER NOT NULL,
  expires_at INTEGER NOT NULL
) STRICT;
CREATE INDEX gf_guest_sessions_expiry ON gf_guest_sessions(expires_at);
CREATE TABLE gf_farm_snapshots (
  player_id TEXT PRIMARY KEY REFERENCES gf_players(id),
  revision INTEGER NOT NULL CHECK(revision > 0),
  operation_id TEXT NOT NULL,
  snapshot_hash TEXT NOT NULL,
  snapshot_blob BLOB NOT NULL,
  updated_at INTEGER NOT NULL
) STRICT;
-- Keep operation receipts permanently: an arbitrarily late retry cannot become
-- a new write or accidentally receive the acknowledgement for another blob.
CREATE TABLE gf_save_operations (
  player_id TEXT NOT NULL REFERENCES gf_players(id),
  operation_id TEXT NOT NULL,
  expected_revision INTEGER NOT NULL,
  committed_revision INTEGER NOT NULL,
  snapshot_hash TEXT NOT NULL,
  committed_at INTEGER NOT NULL,
  PRIMARY KEY(player_id,operation_id)
) STRICT;
