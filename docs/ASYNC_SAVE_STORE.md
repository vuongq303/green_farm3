# Asynchronous save storage

`GameSaveStore` keeps the schema-4 SQLite database and the existing encoded
record payloads. Serialization runs on the game thread. SQLite transactions,
checksums, row comparisons, commit metadata, and durable writes run through
`ProgressSaveController`'s single writer.

The capture interface is `beginCapture(wholeFarm)`, the existing
`GLLib`/`GameSaveStore.write`, and `finishCapture()`. `abortCapture()` discards a
failed capture. Every captured payload is copied from the serializer's scratch
buffer. A `Snapshot` exposes counts and flags, never its private byte arrays.
Capture reads use immutable memory published after load or successful capture;
capture does not acquire the SQLite lock or open/query the database.

Full snapshots require `RMSHeader`, `save_gameplay`, `save_farm0`,
`pending_rewards_v1`, and `entity_pose_v1`. Visited-farm snapshots require the
header, gameplay, and reward records, retaining the latest own-farm and pose
records. `applySnapshot` commits the whole supplied snapshot in one transaction.
It rechecks completeness and refuses to replace an own farm from a partial
snapshot. Existing `beginSave`/`commitSave` APIs remain available for isolated
storage probes and synchronous compatibility callers.

Incidental record writes use `WriteDispatcher` while the controller has an
active world. They become immutable absolute record mutations on the same
writer queue. They do not replay harvest, currency, purchase, or reward events.
`coalesceSnapshots` keeps the newest accepted value of every record, including
deletions. It retains a queued full farm when a later visited-farm snapshot is
merged into it.

Per-record in-memory versions are reserved when bytes are captured, before the
complete snapshot is published. They handle a concurrent SDK callback arriving
between serialization and enqueue: a late older capture cannot replace its
newer value in the logical cache, a merged pending snapshot, or a later SQLite
transaction. Aborting a capture publishes none of its bytes. Versions do not
alter the on-disk schema or codecs.

The confirmed cache and generation describe successful SQLite commits. The
logical cache also includes accepted queued snapshots. A failed write leaves
that logical state available for a fresh capture and retry. Applying or reopening
an older snapshot cannot roll it back. Status and generation change to saved
only after `endTransaction` succeeds. Unchanged record bytes cause no record-row
write; an unchanged snapshot with current completeness metadata creates no
extra generation or commit row. A legacy completeness-marker upgrade can
commit metadata with zero changed record rows.

The controller drains its writer before starting a new load or closing the
store. `beginLoading` then resets the logical cache from confirmed storage.
Decoding reads use SQLite and verify checksums; default and repair writes
calculated during decoding remain suppressed, including writes made while a
dispatcher is installed. Startup migration and read-only legacy RMS import run
before ordinary cached gameplay reads. Errors retain the database and original
legacy files, and never imply a new player.

`beginAuxCapture()` captures explicit settings without claiming a complete farm.
The pre-world language menu uses the narrower
`captureLanguageSetting(language)` operation: it copies an existing verified
header of at least 35 bytes and changes only byte 4. This header-only mutation
may commit while world decoding is still protected. General saves and captures
remain barred during loading. A rollback uses the same operation with the old
language and preserves the rest of the latest header.

SQLite remains in WAL mode with FULL synchronous durability. No database export
or copy runs on the gameplay save path. Chunking the existing farm blob would
require a new resumable format and is not part of this change: the immediate
stall is removed by moving the existing transaction to the writer and skipping
unchanged rows.

`tools/GameSaveStoreProbe.java` adds the `async-snapshots` command for a fresh
isolated fixture. It checks detached buffers, capture while the writer holds its
lock, concurrent callback ordering, coalescing, stale retries, visited-farm
retention, no-change deduplication, explicit settings, loading suppression, and
atomic rollback/retry after a real SQLite trigger failure. The existing probe
commands continue to cover WAL/FULL, migrations, legacy imports, corruption, and
process termination around transactions. These Android probes require an APK
containing the new implementation; source changes alone do not validate an
already installed older APK.
