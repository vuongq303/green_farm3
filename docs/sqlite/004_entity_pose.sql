-- Executed automatically when user_version 3 is upgraded to 4; prior snapshots retain pose_snapshot=0.

ALTER TABLE save_slots ADD COLUMN pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1));

ALTER TABLE save_commits ADD COLUMN pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1));
