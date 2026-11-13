-- One-time permission to copy an explicitly approved device-local save to the
-- authenticated guest's own profile. It does not claim any Facebook account.
CREATE TABLE gf_local_adoption_grants (
  token_hash BLOB PRIMARY KEY,
  player_id TEXT NOT NULL REFERENCES gf_players(id),
  operation_id TEXT NOT NULL,
  snapshot_hash TEXT NOT NULL,
  issued_at INTEGER NOT NULL,
  expires_at INTEGER NOT NULL,
  used_at INTEGER
) STRICT;
CREATE INDEX gf_local_adoption_grants_expiry ON gf_local_adoption_grants(expires_at);
CREATE TABLE gf_player_initialization_receipts (
  player_id TEXT PRIMARY KEY REFERENCES gf_players(id),
  origin TEXT NOT NULL CHECK(origin IN ('configured-new-player','approved-local-copy','legacy-existing-player')),
  config_hash TEXT,
  snapshot_hash TEXT NOT NULL,
  revision INTEGER NOT NULL,
  committed_at INTEGER NOT NULL
) STRICT;
