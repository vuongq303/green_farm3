# Game save/load evidence for the SQLite integration

Audit base: `eb8635d`, Android Green Farm 3 4.4.2. Source references below name
methods in `app/src/main/java/com/gameloft/android/ANMP/GloftGF2F/S800x480`.
Smali references are the original APK under `/workspace/tooling/original-smali`.
This is a code/bytecode audit, not a claim that gameplay or SQLite runtime tests
have already passed.

## Runtime and current storage

The game is Java Android, with the original MIDlet/Canvas runtime. `Start`
launches `GloftGF2F`; `GloftGF2F.mj()` constructs `ci` and calls `Init()`.
It is not a browser game, so SQLite belongs in the app's private persistent
database directory, rather than a WASM/in-memory store or the Node backend.
Backend MySQL account/session state is distinct from local gameplay saves.

Game records currently pass through `GLLib.e/a/B/bF` into
`javax.microedition.b.b`, the legacy RMS implementation. The full binary
record codecs already exist and should be preserved instead of inventing a new
mapping for every obfuscated field. See [RecordStore](RECORDSTORE_RECOVERY.md).

## Confirmed reason early progress is not saved

The following restrictions are present in the original DEX, not introduced by
JADX:

| Method | Existing restriction |
| --- | --- |
| `ci.av(false)` | Returns before serialization when `FC() < 3` **or** `aYR != -1`. Original smali `ci:178021–178375` explicitly contains `not to save before finishing tutorial`. |
| `ci.rr()` | Independently returns for `FC() < 3`; otherwise calls `av(false)`. Smali begins `373752`. |
| `ci.rt()` | Independently returns for `FC() < 3` or a pending move (`avD != -1`). Smali begins `373849`. |
| `ci.ry()` | Independently returns for `FC() < 3`, a pending move, or a visited farm (`aZB != 0`). Smali begins `376826`. |

`FC()` decodes/clamps the level represented by `aBa/aCN`. Calling `av(true)`
alone does not fix tutorial saving because `rt` and `ry` retain their own gates.
Saving during tutorial is therefore an intentional adaptation requested by the
user. It requires a completed-load guard; simply removing the level checks
would let uninitialized/default state overwrite a real save.

## Records already serialize the needed state

| Record/codec | Fields preserved by the original codec |
| --- | --- |
| `RMSHeader`, `ro/rp` | Format/app version, language, sound/notification flags, consent/settings and age/country-related cached state. `ro` also calls time, online and auxiliary settings writers. |
| `save_gameplay`, `rt/ax` | Tutorial `aYR`, guide `aYS`, quest IDs/progress/completion/reward arrays, level/XP/money, inventory arrays, production queues, relationship/unlock arrays, flags, app/leave timestamps and other opaque gameplay fields. |
| `save_farm0`, `ry/d` | Expansion bitset, `aEe`, followed by the entity stream from `p(byte[],int)`. |
| Entity stream | Type, position, original identity/flags/state; `p` dispatches crops 49, buildings 50, trees 52/69, animals 54 and other persisted entity types. |
| Crop `g`/`w` | Location, `HL`, stage `Bj`, absolute `amd` and duration `ame`. |
| Animal `i`/`y` | Location, `HL`, stage, `amF/amR/amG`, `amB/amJ/amC/amD`, care flags and dog-specific fields. |
| `online_gameplay`, `rw/ay` | Remembered invitations, requests and social state; not a proof that remote social operations are implemented. |
| `RMS_Time`, `RMS_TimeClock`, `rC/rD/rE/rF` | Existing time-related state. |

The original codec omits the sprite transform `aaN`; the integrated
`entity_pose_v1` record supplies it (the animation timer `aaR` is distinct).
The reader restores crop/animal timestamps, rather than merely storing a RAM
countdown. Persisting the exact original record bytes in SQLite retains fields
whose semantics are not fully decoded. It should not be described as a new
normalized SQL crop/animal schema.

## Load barrier and identity

The concrete loader is `fM(int)`:

1. Step 44 calls `ax(false)` for global gameplay.
2. Step 46 selects `aZB = aZC` and loads tiles.
3. Step 47 creates the grid/entity lists, then creates the starting farm if no
   save exists, or calls `az(false)` to load a saved own farm.
4. `jM(0)` is gameplay-state entry after loading; this is a safer point to
   enable autosave once step 44/47 succeeded.

`qV()` currently catches every `Exception` thrown by a load step and then
increments `asj`. A new SQLite exception must not be silently consumed there.
Keep the current step pending and surface the read error/retry before advancing.
The time-wrapper readers also swallow exceptions, making an initialization
barrier before `ci` construction important.

`ax(false)` returns false both for absent data and for a serialized device-ID
mismatch, and it sets `asq=true`. Step 47 currently ignores the false result of
`az(false)`. The SQLite integration must distinguish a genuinely absent save
from a failed existing save, including a gameplay record without its farm
record. Read failures must not be interpreted as a new player.

`rv()` reads `System.getProperty("HDIDFV")`; `rl()` reads wrapper `au.Os()`.
The wrapper caches the identity in persistent storage. `ax` compares saved
identity with the wrapper identity. Retain this check and a stable SQLite save
slot; do not generate a new slot on each start or erase a save when identity
validation fails.

## Atomic save and lifecycle hooks

`av` writes `ro`, `rt`, then `ry` independently. Each writer can catch/swallow a
write error; `av` has no finally around its `bbH` guard. A SQLite save transaction
must encompass those records, retain a sticky failure if an inner writer catches
an exception, and only report success after the complete commit. Unexpected
serialization failures also need to release the guard and preserve dirty state.

`rs()` intentionally saves global state while visiting another farm, without
writing the own-farm record. Its transaction should preserve the previous own
farm, not require a new farm record or serialize the visited farm as the owner.

The original frame method `jC()` uses `baa/bab` dirty flags and an eight-second
`baU` throttle. It can dispatch `bbG` to `cq`, a 50 ms background loop which calls
`rr/rs` while the game thread continues mutating state. SQLite alone makes record
writes atomic, but does not make concurrent serialization a coherent gameplay
snapshot. Capture/serialize a completed action on the game thread, then commit;
do not clear dirty flags before a commit succeeds. A frame-level time check is
fine, but it must not write the database every frame.

`GloftGF2F.al(boolean)` originally calls `HH` (notification scheduling), `EZ`
(drain queued actions), `Ms` (tracking shutdown), `rE` and then sets `Qn=-1`.
It does not perform a full gameplay save. `mk()` calls `ci.Pause()`, whose
existing implementation only toggles a flag and delegates. The real hide
notification `ci.jF()` conditionally calls save paths, but does not guarantee a
whole-state flush for tutorial. Flush committed state after `EZ` and before quit;
ensure pause/hide also requests a safe save.

Pending placement/move is represented by `avD != -1`; the existing save gate
already prevents its preview from becoming a farm save. Preserve that exclusion.
Confirm must finish the gameplay operation before saving; cancel must restore
the confirmed state, without producing move progress or persisting the preview.

## Verification still required

Runtime checks must cover a fresh/tutorial save, reload, a mature save, safe
legacy import, stable slot/identity, transaction rollback/process interruption,
read errors that block new-game creation, dirty-state retry, and both move
confirm/cancel paths. The code evidence above does not establish that all those
runtime cases have passed.

## Integration findings and transition checkpoint

The recovered frame scheduler now defers `rr/rz` to a completed game-thread
checkpoint. That requires an additional synchronous checkpoint before a world
transition: `qG` action 2 can remove the farm and begin loading SQLite before the
next 500 ms checkpoint. Without that gate the loader can replace recent live
changes with the last committed snapshot within the same process.

`aR` dispatches own-farm state 18 to `jM`, and visited-farm state 37 to `jP`.
Both exit handlers settle pending pickup effects through `ch.oM` and clear the
entity lists with `ch.oL`. The new `qG` preflight mirrors the exact stack removal
boundaries of actions 1, 2, 3, 4 and 6, before consuming `apA/apz` or invoking any
exit handler. It cancels unconfirmed movement, settles those effects and requires
an observed durable commit. Failure keeps the command and live world available
for retry, with the same eight-second failure backoff as autosave. Original
`anX` settlement guards prevent rewards from being awarded again at cleanup.
XP settlement may call `hW`, which enqueues the original level-up screen through
`fE(14)` and creates its currency pickup effects. The predicate is recomputed
after a successful checkpoint so that this push preserves the intact farm and
its original level-up UI, rather than falsely marking it as unloaded.

After successful removal `worldUnloaded` disarms automatic/lifecycle writes;
`qV` begins protected loading before its first loader operation. Both `jM(0)`
and `jP(0)` rearm saving after verified load completion. Visitor saves continue
to use `rs`'s global-only transaction, preserving the owner's farm record.
Lifecycle, movement-entry and transition checkpoints bypass the original
device-specific ten-second throttle; ordinary frame checkpoints retain their
own scheduling. A serializer that declines to commit is not reported as a
successful lifecycle/transition save.

Each own-farm step 47 resets both auxiliary restore flags before reconstruction.
Otherwise their process-wide `restored` flag would skip pose/reward restoration
on return from a visited farm, even though the entities had been recreated.
On retry of a failed step 47, successfully reopened storage is followed by
clearing the partial entity lists, actor references and command queue before
re-running that step. Without this retry-only cleanup, `fN/qY/az` could append
duplicate entities to the failed attempt. Failed storage reopening leaves RAM
untouched, and subsequent ordinary loader steps do not repeat that cleanup.

`tests/test_progress_save_controller.py` compiles the actual controller and
extracted actual `qG`/stack predictor against test-only Android/storage/game
fixtures. It verifies load and move barriers, non-per-frame checkpointing,
failure backoff, dirty requests arriving during commit, completion/load retries,
pending-command preservation, reward settlement before cleanup, forced
checkpoints, and an own-farm → visitor → own-farm cycle. These focused checks
pass; they do not replace Android SQLite, gameplay, process-death or device tests.
