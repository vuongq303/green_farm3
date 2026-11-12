-- Executed automatically when user_version 2 is upgraded to 3; legacy snapshots retain format 0.

ALTER TABLE save_slots ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1));

ALTER TABLE save_commits ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1));
