# Kotlin: language, tutorial ownership and retired recommendation GUI

The three implementations are now Kotlin under `app/src/main/kotlin`, initially
keeping their JVM owner names so the recovered Java engine and the original DEX
can call the same descriptors. These are complete implementations; there is no
Java delegate or runtime fallback to a previous implementation.

`@JvmStatic` is used only on entry points called from Java. `SaveCompletion` keeps
`completed(Z)V`. The tutorial helper explicitly synchronizes on
`TutorialFlowController.class`, preserving its old static synchronized monitor,
not a new companion-object monitor. No public `@JvmField` or `open` declaration
is needed for these final helpers. Generated companion/accessor classes must be
included by the build pipeline.

## Verified identifier mapping

Scope is the named class; entries are private implementation symbols unless
noted. Public method names/descriptors remain compatible until all engine callers
can be migrated together.

| Class | Java name | Kotlin name | Verified role |
|---|---|---|---|
| `LanguageRestartController` | `LOCK` | `selectionLock` | Serializes UI decisions and game-thread checkpoint decisions. |
| same | `state` | `restartState` | IDLE/CONFIRMING/ACCEPTED/SAVING/restart state, still volatile. |
| same | `generation` | `selectionGeneration` | Rejects callbacks from a previous language selection. |
| same | `selectedLanguage` | `requestedLanguage` | Proposed language, distinct from the current live `ci.aYU`. |
| same | `dialogText` | `currentLanguageDialogText` | Seven dialog strings captured in the current language. |
| same | `handoff` | `restartHandoff` | Prepared Android alarm/pending-intent operation after durable save. |
| same | `menuHeld` | `menuKeyHeld` | Latches physical Menu key presses. |
| same | `menuRequested` | `menuOpenRequested` | Transfers Menu decision to the game thread. |
| same methods | `ticket` | `selectionTicket` | Selection generation attached to confirmation/save/retry callbacks. |
| `RestartHandoff.restart` | `when` | `restartAtElapsedRealtimeMillis` | Alarm timestamp from elapsedRealtime, plus exactly350ms. |
| `TutorialFlowController` | `frame`, `lastStepFrame` | `frameIndex`, `lastStepFrameIndex` | Enforces at most one sequential tutorial update per game frame. |
| same | `session`, `epoch` | `sessionId`, `stepEpoch` | Invalidates callbacks across worlds and tutorial steps. |
| same | `gesture`, `consumedGesture` | `gestureId`, `consumedGestureId` | Prevents one gesture closing a modal and activating underlying UI. |
| same | `sequence`, `step` | `activeSequence`, `activeStepIndex` | Selects original intro/update tutorial state machine. |
| same | `modal`, `Modal` | `modalOwner`, `ModalOwner` | Current modal's engine state and ownership token. |
| same methods | `intro`, `update`, `owner`, `token` | `introStepIndex`, `updateStepIndex`, `ownerSequence`, `callbackEpoch` | Distinguishes the two original sequence indices and callback generation. |
| same restore | `grown` | `grownCow` | Unique valid adult definition17 when recovering tutorial18's visual effect. |
| `MoreGamesRemoval` | `layer`, `item` | `layerId`, `itemId` | Original packed GUI identifiers, values unchanged. |
| same | `row` | `row`, then `packedFlags` | Nullable JVM input checked only after matching a removed widget; preserves Java's short-circuit/null behavior. |

At this group’s initial checkpoint, engine symbols were kept for a separate
symbol-aware rename: `ci.aYR`, `ci.aYS`, `ci.aYU`, entity fields,
GUI flags and IDs are not rewritten by a textual short-name substitution.
No save keys, binary fields, tutorial step values, item definitions or packed GUI
indices have been renamed or renumbered by this group.

## Checks run for this group

- Compiled all three real Kotlin files against the current production Java
  classes, original dependency view, Android28 and Kotlin2.4.21/JVM8.
- `tools/test_language_restart_source.py`:7/7 structural/locale contracts passed.
- `tests/test_tutorial_sequence.py`: the production Kotlin helper is compiled
  together with the same extracted Java engine handlers and recording peers;
  ownership, actual step conditions, stale callbacks, gestures, growth restore
  and restored task-book behavior passed.
- `tests/test_more_games_removal.py`:6/6 passed, including8,091 real GUI-boundary
  checks,336 resume/farm combinations,14 original text packs and mutation checks.
  A nullable-row regression additionally checks irrelevant widgets return before
  dereferencing a null row, while a removed widget still throws for a null row.
- Actual save/language-controller behavior is exercised separately by
  `tests/test_progress_save_controller.py` against the Kotlin implementation.

These JVM fixtures do not establish rendering, touch delivery, Android alarm
behavior or a real game session. APK and emulator checks are reported separately
by the parent migration checkpoint.


The language controller also honors
`com.quanhoang.greenfarm3.migration.SaveTransferGate.isPreparingTransfer()`: explicit
save export/import preparation blocks pointer callbacks and game updates, draining
stale input before the transfer checkpoint. Ordinary background saves do not set
this flag. The controller fixture verified five blocked frames without changing
engine/tutorial/credits or save queue state, then resumed after cancellation.
