# Offline startup host checks

These logs are transcribed from the actual command outputs recorded in this
session. The commands were not rerun to create this evidence directory. They
compile temporary fixture shells containing the actual extracted source methods;
they do not install an APK or run Android.

The startup fixture passed 36 checks for the original local-farm route,
connectivity lookup, authentication-worker suppression and datacenter setup.
Both offline flag values were exercised. Normal mode retained the original
feature setting and network-query short-circuit behavior.

The focused regression rerun passed the original-bytecode comparisons: 432
startup traces, 20,000 quest objective predicates, 10,000 progress traces and
1,000 quest completion traces. The network-failure predicate check also passed.

The initial broader discovery was **not** a successful suite run. Its quest and
startup comparisons could not locate ASM because `GRADLE_USER_HOME` was omitted;
the focused rerun supplied the existing cache and passed. Its recovered-flow
check rejected stale staged source because an APK rebuild had not yet occurred.
The parent subsequently reran that check after the clean build; it passed 5,000
numeric-prefix cases and 2,000 Arabic cases against the original bytecode. The
actual output is archived as `recovered-flow-after-build.log`. Move controls,
network failures and selection continuation passed within the initial run.

Commands, environments, exit codes and output-file references are listed in
`index.json`. No passwords, tokens or player-save contents were included in
these host outputs. Emulator validation is recorded separately by the runtime
agent; these results alone make no claim that the game boots or plays correctly.
