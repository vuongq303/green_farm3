# Tutorial sequence and input ownership

The original intro remains `aYR` in `ci.Es()`; the later guide remains `aYS` in
`ci.Ei()`. Their original save fields and SQLite codec are unchanged. Normal
quests still run concurrently through the original objective predicates and
counter updates; they do not enter the sequential tutorial controller.

Two recovered input paths could advance the intro without its action:

* `jN(8)` duplicated the original bytecode's shared speech branch into drag and
  final fall-through branches. Those branches accepted virtually any release
  to the right of the speech bubble's left edge, even during an action step.
  All speech branches now use the original eight speech step IDs and all four
  original rectangle bounds. Acknowledgement consumes that gesture.
* Several action steps only set shared `axW` to true when their original
  condition succeeds. An unrelated true value could bypass the condition.
  Steps 5/8/14/38/43/46/58/61 now assign `axW` from that same original expression
  each update. Step 2 also requires its existing camera-ready initialization.
  Placement steps 15/53 retain the explicit confirmed-placement requirement.

`TutorialFlowController` holds ephemeral session, step, frame, modal and pointer
ownership. `ci.V` records actual presses; `ci.jC` starts each update frame. Only
one tutorial sequence and one step update run per frame. A tutorial dialog or
quest modal owns the session/step that actually opens it. Its acknowledgement
requires a fresh gesture, runs once, and consumes queued/current GLLib input
before exposing the farm. This introduces no time-based input limit; rapid
harvest actions outside a modal transition continue through the original path.

Modal ownership binds when state entry actually runs, since requesting the
quest window can precede the same action's step 10→11 transition. Tokens from
an unloaded session cannot be revived by a delayed entry or acknowledgement.
World load and successful language restart invalidate the session token.

The eating animation also previously assigned intro step 33 whenever it ended
under the tutorial overlay, including during unrelated intro actions. Its
completion now requires step 32 and the epoch captured when eating began.
Growth animation completion at step 18 similarly uses the epoch captured when
that animation began. Expired tutorial callbacks leave ordinary entity and
quest processing intact.

The existing GUI button animation can defer `gO` dispatch after `Fi` selects a
button. `Fi` now captures the tutorial epoch with its pending `aAJ`; `Mr`
validates that epoch immediately before dispatch. Old-step/old-session button
callbacks are discarded. Ordinary GUI buttons carry token zero and retain the
original dispatch behavior.

Cold restore also preserves the growth completion boundary. Original `pR`
changes the tutorial cow from definition 16 to 17 before its cosmetic `aoR`
animation ends. The farm codec saves that definition and `Bj`, but omits `aoR`.
After the complete own-farm decode, intro step 18 therefore re-arms only the
original growth effect when no baby 16 remains and exactly one adult 17 exists.
It reuses the same effect constructor/animation and captures a fresh epoch;
it does not call `pR`, change the definition, spend currency, or grant rewards.
An existing effect, remaining baby, ambiguous adult list, other tutorial step,
or visiting farm is left unchanged. Progress advances through the original
`pG` animation-completion callback. Restored eating state `Bj=3` already passes
through decoder `fp(Bj)`, which captures its new session epoch normally.

The world GUI needs a separate reconstruction at intro steps 10–12. On world
entry, `jM(1)` calls `Ep`, which hides and disables the task-book control 53.
The original step 12 assumed the quest window was already open, so it never
restored that control after a cold load. Immediately after `Ep`, the narrow
`restoreWorldUi` hook shows/enables the book, restores the original step-10/11
hints and rebuilds layer-1 input rectangles. It does not call quest initialization
(`Ls`/`Lt`), modify tutorial flags, complete objectives or advance the step.
The player still opens the quest and chooses its objective's Go/Find action;
the original `tO`/`tR` handler then enters step 13.

Validation: `python -m unittest discover -s tests -p test_tutorial_sequence.py`
compiles the production ownership helper, actual `jN` input method, actual
eating completion handler and selected original `Es` condition branches into
an Android-free fixture. It covers contaminated completion flags, actual
conditions, duplicate and old-session/old-step callbacks, one modal owner,
same-gesture refusal, fresh-gesture acceptance, original speech hit bounds and
ordinary modal availability. The separate quest differential test compares
20,000 predicates, 10,000 progress traces and 1,000 completion traces against
the original converted bytecode. These are source-level handler checks; no
claim is made here about device rendering, touch delivery or a new APK run.
The growth restore regression runs the actual restore helpers, original `pG`
completion branch and original intro-18 branch: 20 unfinished-animation updates
remain at step 18, and completing that effect advances once to step 19. It also
covers repeated restore, remaining baby, ambiguous adult and unrelated state.
The cold quest-UI fixture verifies display/input enablement and GUI rebuilding
without changing step 12 or its flags, then runs the actual Go handler and its
original quest-guide branch to advance to step 13. Widget rendering and device
touch targets remain runtime checks.

Selected real-device-emulator checks are now recorded separately in the
[acceptance checkpoint](ASYNC_GAMEPLAY_CHECKPOINT.md) and
[runtime evidence](async-save-validation/game-runtime.json): cold-restored book,
quest objectives still0/1 before actual purchase, calf purchase, original
Vietnamese dialogue, and tutorial23 retained across language/cold restarts.
They do not establish every tutorial step or a full rapid-touch stress run.
