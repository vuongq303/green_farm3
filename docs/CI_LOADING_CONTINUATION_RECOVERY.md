# CI selection and startup continuations

The matching original `ci.smali` under
`/workspace/tooling/original-smali/com/gameloft/android/ANMP/GloftGF2F/S800x480`
establishes these two source corrections. Original APK/DEX identities are
recorded in `JAVA_BLOCKERS_RECOVERY.md`.

## First eligible selection

`c(ZII)V` (original smali lines 207011–207802) scans eligible entries only until
the first match. The positive branch calls `gR(3)` at line 207714 and falls
directly into `:cond_260`, which clears bit 8 of `atm[3][12]` and returns. The
negative branch alone reaches `:cond_27c` (line 207749), increments the index and
jumps back to the scan. The exported Java omitted the positive branch's loop
exit. Restoring `break` after `gR(3)` preserves the common cleanup and prevents
repeated processing of the same eligible entry.

`tests/test_ci_selection_continuation.py` freshly compiles the source method and
compares it with the original converted method's unchanged Code attribute. Its
test-only superclass and sprite/table peers record calls and provide controlled
inputs; they are not packaged into the game. All 576 comparisons passed on
2026-11-12, covering enabled/previous flags, modes 0–2, every eligibility pattern
of rows 64–67, successful refreshes and propagated Exceptions/Errors. The check
compares call order, selected indices, all four selection arrays, GUI control
updates and common layer cleanup.

## CRM initialization continuation

In `fM(I)V`, loading step 38 invokes `Ml()` inside a caught-Exception region.
Original smali lines 223547–223558 then invoke `Mp()` and assign its result to
`aYJ`, on successful initialization as well as through the catch continuation.
The exported Java contained a `break` immediately after `Ml()`, skipping that
assignment when `Ml()` returned normally. Removing this inner `break` restores
the original shared continuation. No initialization defaults or CRM response
substitutions were added.

`tests/test_ci_startup_continuation.py` freshly compiles the current source's
step-38 branch and compares it with the entire original `fM` instruction flow
and exception handlers copied into a temporary fixture. All 432 comparisons
passed, covering `Ml` and `Mp` success, Exceptions and Errors, returned counts,
age/input/language branches and states 2/4. The check compares trace order,
`aYJ`, age/GUI state and propagated throwable identity. The temporary class
format and constant-pool references are adapted by JDK ASM; branch behavior is
not rewritten. Fixture peers are not packaged into the application.

## Scope of startup investigation

The current source control flow for `qV`, `il`, `Mm`, `qB`, `qS`, `fE`, `fF`,
`fG`, `kp`, all `qG` state actions and relevant GUI draw dispatchers was checked
against the original smali. No additional control-flow difference was identified
in that review. The original `Mm` catches missing CRM configuration and current
configuration errors, then sets `aYw`; those exceptions alone do not establish a
loader stall.

A read-only runtime snapshot before these two changes showed `asj=ask=38`,
`asm=true` and `apB=-1`. That establishes completed loading with an empty state
stack for that run. It does not establish the cause of the empty stack or prove
that either correction repairs Android rendering or reaches the age screen.
The original `im(5)` draws before queuing the next state, and its caller catches
Exceptions; a failed draw in that prefix is one path that can leave a completed
loader without its next state.

Subsequent caught-exception tracing identified the rendering overload loss
described in `CORE_FLOAT_DISPATCH_RECOVERY.md`: texture width 256 reached a
ten-element pointer array. Restoring the original float descriptors allowed
`im(5)` to queue state 60. The separate wrapped-text anchor correction then
matched the original age-screen text/layout region with zero differing pixels
across 233,220 pixels; the runtime comparison is retained under
`build/reports/restoration/runtime-core-source/text-anchor`. This establishes
that startup checkpoint, not successful farm gameplay.
