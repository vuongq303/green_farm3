# Balance and save-controller host validation

Archived from the actual existing test logs after the source was frozen; no test
was rerun while collecting this evidence. Commands, case counts, timings and log
hashes are recorded in `index.json`. Both commands ran from the repository root.
All **3 unittest cases passed**: 2 isolated grant tests and 1 production controller
suite. The extra `PASS` lines describe checks inside that controller case; they
are not additional unittest cases.

The grant test compiles the actual `OfflineTestBalances` helper, original protected
wrapper classes and extracted original engine methods. It covers normal-build and
profile guards, 9,999,999 coins/cash, level50, matching shadow fields, original
max-level capacity limits, unchanged XP/tutorial/inventory, failed atomic capture,
legitimate spending and no refill after simulated reopening. Its second case
checks hook order inside the production save controller.

The controller case exercises actual save/language controllers with deterministic
Android/storage peers: cold lifecycle flush without engine initialization,
batching, detached snapshots, durable transitions, cancel/retry, late mutations
and restart handoff. Its grant peer represents normal mode; the dedicated grant
suite tests the real grant implementation separately.

These host checks do not establish Android startup/gameplay or SQLite durability.
Those results belong to the separate build/emulator validation evidence. Archived
logs contain only test names, timings and outcome messages; no credentials or
player-save payloads are included.
