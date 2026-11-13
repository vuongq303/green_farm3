# Local database and portable save compatibility

Local SQLite schema5 adds cloud journal tables to the existing schema4 game
tables. The portable `.gf3save` codec remains `GF3SAVE1`, archive version1,
schema header4, with the existing format4 opaque gameplay payload. It exports
only `save_slots` metadata and `save_records` payloads/tombstones. Cloud journal
rows, guest session credentials and pending request metadata are not added to
the archive. Existing account labels inside original game records are retained.

`SaveArchiveDatabase.read()` supports local schema4 and schema5 and reads a
committed consistent snapshot. A new manual import creates schema4, which the
normal game can subsequently upgrade. Import into an existing unlinked schema5
database keeps schema5 and its unrelated tables; it never downgrades the database.

Manual import is refused when the local cloud state has an owner, prior revision,
adoption, acknowledgement, conflict or a pending outbox request. The original
world, session and archive remain available. Use the game's profile recovery or
conflict flow for a linked account. Import never silently logs out a guest,
rebinds a Facebook profile or erases pending work to make the replacement fit.

The cloud controller uses the separate controlled
`installCloud(context, snapshot, playerId, revision, hash)` operation. Before
calling it, the controller must authenticate the session, verify the SHA-256 of
the downloaded archive, close `GameSaveStore`, and exclude concurrent cloud
writers. The destination must already be schema5 with that same owner and no
pending outbox. A response older than the destination's acknowledged revision, or
a different hash for that same acknowledged revision, is rejected. Both preflight
and the SQLite writer transaction check these conditions.

An existing complete world is exported, checksum-validated and synced to
`files/greenfarm-before-import.gf3save` before it is replaced. Incomplete or
corrupt existing gameplay prevents replacement. Within one SQLite transaction,
cloud restoration installs every game record and transfer binding together with
the server revision, acknowledgement hash, adoption flag and cleared conflict.
It retains the owner and unrelated cloud/session rows. A failure rolls back all
of these changes. Only a successful commit invalidates the in-process imported
binding cache, so the next validation reads the new committed gameplay binding.

`tests/test_save_archive_database_cloud.py` compiles the production Kotlin
archive/database/identity code and runs it against actual host SQLite via thin
Android API peers. It injects SQL failures both while inserting records and while
updating the cloud receipt, checks rollback and backups, and verifies that private
session data is absent from exports. This host validation does not establish
Android lifecycle, UI, networking or device-specific SQLite behavior. The existing
`test_save_transfer_archive.py` separately verifies binary compatibility,
checksums, truncation, bounds, required records and UTF-8/device-binding validation.

The pre-schema5 migration first preserves a separate private logical SQLite copy,
`files/greenfarm-before-cloud-migration.gf3dbbackup`. It does not require a complete
farm or valid game payload: header-only and incomplete game data retain every
typed value from `save_slots`, `save_records`, `save_commits`, `legacy_imports` and
`save_transfer_identity` when present. The copy is taken within the existing
upgrade transaction before schema changes, then checksum-verified, synced and
renamed. The first verified copy is retained. A corrupt existing backup or bounds
failure blocks migration and preserves the source database. No main-file-only
copy is taken while SQLite may have committed data in WAL.

That recovery file is intentionally separate from a portable game save and is
never automatically imported or executed. Its big-endian layout is: 8-byte magic
`GF3DBK01`, 32-bit format version1, 32-bit original SQLite user version, table count,
then each table's UTF-8 name/DDL, column count/names and row count/values. Strings
and blobs have 32-bit byte lengths. Each value has a one-byte SQLite type tag:
0=null, 1=signed64-bit integer, 2=IEEE75464-bit float, 3=UTF-8 string, 4=blob.
A SHA-256 trailer covers all preceding bytes. Limits are128MiB, 65,536 total rows,
2,048 save-record rows,128 columns and64MiB per field. The host migration test
decodes this format independently and compares its rows, value types, columns
and DDL with the actual SQLite source, including incomplete gameplay bytes.
