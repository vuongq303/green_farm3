-- Executed automatically when user_version 1 is upgraded to 2; records are retained.

ALTER TABLE save_slots ADD COLUMN complete_snapshot_generation INTEGER NOT NULL DEFAULT 0;

UPDATE save_slots SET complete_snapshot_generation=generation WHERE EXISTS (SELECT 1 FROM save_records WHERE save_records.slot_id=save_slots.slot_id AND store_name='save_gameplay' AND deleted=0) AND EXISTS (SELECT 1 FROM save_records WHERE save_records.slot_id=save_slots.slot_id AND store_name='save_farm0' AND deleted=0);
