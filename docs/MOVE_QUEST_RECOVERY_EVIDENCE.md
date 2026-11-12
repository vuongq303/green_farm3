# Movement and quest audit

Audited against the checked-in original APK's `classes.dex`, disassembled under
`.local`/the tooling directory, and its verified converted bytecode. The game is
an Android Java MIDlet adaptation; input is dispatched by `ci.Mr`/`Fi`, then the
gameplay handlers, rather than browser Pointer Events.

## Confirmed causes and corrections

* Original `ci.aD(Z)V` hides Cancel whenever a tutorial or `b.Ka` is active.
  `DR()V` additionally hides it in tutorial IDs 1003/1042/1029/1009, positions
  both buttons relative to the actor without viewport bounds, and `sv()V`
  refuses tutorial cancellation. These restrictions also exist in the original
  DEX; this is a targeted behavior correction, not a claimed JADX mismatch.
* Existing-object mode is `avD == 3`; first placement is 0 and inventory placement
  is 4. `DO()Z` used map presses and a small actor
  tap to call `DP()V`, which commits placement and emits quest events. The new
  placement handler keeps drag coordinates as rendering previews, consumes GUI
  captures and only commits through the valid explicit `rU` confirmation.
  The repeat-purchase mode 1 retains its original input flow, and normal seed
  planting on existing plots is unchanged. Payment/inventory rules remain in the
  original confirmation routine.
* `avM`/`avN` already preserve committed coordinates, but cancellation did not
  preserve the sprite facing: animal `fp(4)`/`fp(0)` resets it. The move snapshot
  now also retains/restores the real sprite transform `aoO.aaN`. The similarly
  named `lL()`/`dS()` accessors operate on elapsed animation time `aaR`, and must
  not be used as facing. The regression fixture extracts these accessors and
  `dU`'s transform remapping directly from the actual `aa.java` source.
  Original `avJ` handling remains intact: the actor's 16777216 flag is temporarily
  cleared during preview and restored once with `qr()` on completion/cancel.
  `qr()` queues clipping rectangles, not direction. Actual `fx/fy/fz` methods are
  extracted into the fixture to check that other `HL` bits remain unchanged.
* Tutorial stages 15/53 inferred success from `avE == null`, which becomes unsafe
  once cancellation is available. They now require successful confirmation;
  cancellation returns to the existing stage-13 market guidance / stage-51
  inventory selection and does not emit a move event. Stage 53 reopens inventory.
  Lifecycle cancellation uses the same rollback helper. The first purchased pig
  is mode 0; `bO` charges its price inside `DP`, so cancelling its preview does
  not need to invent a refund.
* Original `DP` counted action 6 only when **both** coordinates changed. The
  predicate now counts a valid confirmed change on either axis. Cancel, preview
  drag, and unchanged-position confirmation emit no movement event.
* `rU` now handles an already-consumed release with no selected actor as a no-op.
  Explicit confirmation queues a durable save through `ProgressSaveController`;
  the controller defers serialization during active preview modes.
* Original Android `javax.microedition.lcdui.l` maps `ACTION_CANCEL` to
  `U(-1,-1)` (also confirmed in the original smali). `ci.U` now recognizes the
  primary-pointer sentinel, clears GUI capture and queues rollback. The next
  `jC` drains pending press/drag/release flags and restores the preview under the
  game-thread lock, before UI dispatch; it neither fakes a button release nor
  serializes on the Android UI thread. Valid and secondary releases retain
  their original path. A real surface-size change queues the same cancellation;
  the initial/same-size callback preserves the preview and original viewport setup.

Buttons retain the original artwork. Their touch bounds are at least 48 logical
pixels. The pair is clamped as a group inside the current logical viewport,
with a gap, and stacked vertically when a narrow screen cannot fit the pair
horizontally. Hit rectangles are updated/registered after clamping, including
when a tutorial previously removed Cancel's rectangle. A disabled confirmation
has no active GUI rectangle and is still blocked from falling through to the map.

## Quest findings

The original quest state distinguishes available (1), active/tracked (2), and
completed (3). Opening/starting a quest through `lF(1)` performs 1→2. Objective
progress is maintained by action/target/count matching; the information-only
tutorial states intentionally advance on opening their specified screen.
Paid objective skip controls retain their original payment gates.

`tests/test_ci_quest_progress.py` compares the recovered Java with the original
method instructions for 20,000 matcher inputs, 10,000 progress/callback traces,
and 1,000 completion traces. All 31,000 match. It also executes original/recovered
`lF(1)` through 18 open/reopen cases, checking that 1→2 tracking leaves objective
counters unchanged and never invokes completion/reward claim. This is evidence against changing
quest predicates speculatively; it does not prove every quest content record is
correct or reproduce the user's unspecified quest-button sequence. The movement
confirmation correction prevents a concrete source of premature move progress.

Claiming a reward can create pending `ch.Kx == 47 && anX` pickups in `als[3]`.
These are absent from the original farm serializer, which walks `als[1]`.
`FX`/`FY` only initialize output byte arrays; they do not credit pickups. The
separate pending-reward snapshot integration preserves this missing state rather
than settling/removing pickup animations on every autosave.

## Durable entity transforms

Original `ci.i(..., ch)` stores an animal's `HL`, `Bj` and production state, but
does not store its sprite transform. `ci.y` rebuilds the sprite with `fp(Bj)`.
`EntityPoseState` therefore stores actual `aaN` in the atomic whole-farm snapshot.
It follows the exact persistent type list of `ci.p`, excluding transient effects.
Its keys use original codec grid coordinates and signed-byte definition values
(including the empty-plot 65535→-1 sentinel), plus an occurrence number. Original
factory insertion may reorder identical-key actors, so restoration recovers
stream occurrence order by ranking the original factory's `aoW` allocation
counter; the absolute counter is never saved or treated as an actor identity.
The complete record and every target match are checked before changing sprites.

`tests/test_entity_pose_state.py` runs the actual helper with original factory,
list-order/grid functions and sprite accessors. It verifies reversed duplicate
ordering, quantized coordinates, raw transform versus elapsed time, one-time
restore/retry, malformed data and mismatched targets without partial mutation.

## Verification boundaries

`tests/test_ci_move_controls.py` compiles the actual layout, cancellation,
confirmation/input handlers with deterministic peers. It checks 14,300 edge
layouts on logical screens from 128×240 to 800×480, tutorial Cancel visibility,
rollback of position/facing, no objective event on Cancel, preview-only drag,
GUI input capture, invalid/duplicate confirmation and single-axis movement.
It also extracts actual `ci.U`, the raw map-release block and GLLib pointer-state
methods to check queued cancel, empty pending buffers, valid releases, GUI bounds
and resize rollback. These fixtures do not inject Android MotionEvents on a device.
The full `DP` economy/world implementation is replaced by a recorder in this
fixture; these checks do not replace an APK/emulator touch test.

Run the focused checks with the installed JDK and verified ASM cache:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 \
GRADLE_USER_HOME=/workspace/tooling/gradle-home \
python3 -m unittest discover -s tests -p 'test_ci_*progress.py' -v
python3 -m unittest discover -s tests -p 'test_ci_move_controls.py' -v
```

The machine-specific paths above are local test-tool examples, not application
configuration. Android build, real touch dispatch and persistence verification
are reported separately by the integrated build/runtime checks.
