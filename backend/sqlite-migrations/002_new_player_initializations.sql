-- Existing players intentionally receive no row: a schema upgrade must not
-- overwrite their progress with a newly configured starting balance.
CREATE TABLE gf_player_initializations (
  player_id TEXT PRIMARY KEY REFERENCES gf_players(id),
  config_hash TEXT NOT NULL,
  config_json TEXT NOT NULL,
  created_at INTEGER NOT NULL
) STRICT;
