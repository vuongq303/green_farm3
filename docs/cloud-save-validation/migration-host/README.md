# Migration, portable archive and cloud journal host validation

The actual combined command in `index.json` passed **9 unittest cases**. Its log
also contains60 assertions printed by the3 SQLite suites:23 archive installation,
11 pre-upgrade backup and26 cloud journal checks. Those printed checks are inside
3 unittest cases and must not be counted as60 separate test cases. The other6
cases exercise the existing portable archive codec.

Production Kotlin runs on the host JVM. The Android API peers keep one actual
Python SQLite connection per handle and forward SQL, transactions and row values;
they do not implement a replacement archive, migration backup or cloud journal.
An independent Python decoder additionally compares each backup table definition,
column and typed value with the actual SQLite source. Databases and guest/account
labels are synthetic and confined to temporary fixture directories.

Coverage includes schema4/5 without downgrade, data-only archive export, linked
profile/outbox import refusal, owner/revision/hash validation, atomic receipt and
world replacement, rollback on record/receipt SQL triggers, retained backups,
post-commit binding invalidation, incomplete/header-only backups, bad-backup and
bounds failure, durable pending UUID/hash, head/tail coalescing, matching ACK,
duplicate/stale ACK refusal, conflict retention and offline-test marker rejection.

This is host validation. Android lifecycle/gameplay, SQLiteOpenHelper migration,
actual server transport and emulator/device behavior require separate evidence.
No ADB, Android build or real player data was used for these tests. The log has
only test names, timings and result messages; no credentials or save bytes.
