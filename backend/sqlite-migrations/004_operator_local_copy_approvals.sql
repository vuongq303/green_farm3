-- A client cannot self-authorize an arbitrary initial balance by labelling it
-- a local copy. Only the server operator's filesystem CLI can create approvals.
CREATE TABLE gf_local_adoption_approvals (
  id TEXT PRIMARY KEY,
  player_id TEXT NOT NULL REFERENCES gf_players(id),
  snapshot_hash TEXT NOT NULL,
  issued_at INTEGER NOT NULL,
  expires_at INTEGER NOT NULL,
  used_at INTEGER
) STRICT;
CREATE INDEX gf_local_adoption_approvals_lookup ON gf_local_adoption_approvals(player_id,snapshot_hash,expires_at);
ALTER TABLE gf_local_adoption_grants ADD COLUMN approval_id TEXT REFERENCES gf_local_adoption_approvals(id);
