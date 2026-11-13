# Provider and exit host validation

These are copied outputs from actual focused host runs during this restoration.
No host test opens a real player database. Commands run in
`/workspace/green_farm3`.

Provider/GUI/model checks:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 ANDROID_HOME=/workspace/tooling/android-sdk GRADLE_USER_HOME=/workspace/tooling/gradle-home GREENFARM_ORIGINAL_JAR=/workspace/green_farm3/.local/bytecode-tools/greenfarm-original.jar PYTHONPATH=tests python -m unittest test_retired_provider_services test_more_games_removal test_kotlin_medium_models -v
```

Result: exit 0, 9 tests in 45.505 seconds. The output line labeled
`medium Kotlin differential ads` is the digest produced by the reused runner;
the test explicitly requires that retired callback digest to differ from the
original. The five other model/algorithm groups still match original bytecode.

Exit/container checks:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 PYTHONPATH=tests python -m unittest test_backend_quit_checkpoint -v
```

Result: exit 0, 52 actual branch/order/container checks, 1 test in 0.898 seconds.
The test includes the real reward-settlement method for pending XP, coins,
cash and inventory, verifies it runs before the immutable checkpoint, and
checks that shutdown cannot replay those rewards. Two additional checks verify
the obsolete restore action leaves farm/notification data intact and closes
only its own confirmation. An earlier 42-check fixture
used a coarse `oM` peer with an inaccurate disposal label; source review showed
that `oM` settles earned reward particles, so both call ordering and the fixture
were corrected. That earlier result is superseded by the archived final run.
The first fixture attempt lacked an `arg` peer declaration and failed Java peer
compilation; adding that unchanged field to the fixture resolved the setup
error. No application code was changed to make that fixture compile.

The earlier broad run exposed stale test expectations about the now-retired
native query and CRM resume call. Those expectations were updated to the new
provider policy before the successful run archived here. The original gameplay
and non-ad model comparisons were retained.

These results alone establish neither real Android behavior nor successful
server commits. SDK/manifest pruning and final APK/emulator verification are
recorded separately.

Final complete-Facebook-removal ABI rerun:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 ANDROID_HOME=/workspace/tooling/android-sdk GRADLE_USER_HOME=/workspace/tooling/gradle-home GREENFARM_ORIGINAL_JAR=/workspace/green_farm3/.local/bytecode-tools/greenfarm-original.jar PYTHONPATH=tests python -m unittest test_retired_provider_services -v
```

Result: exit 0, 2 tests in 11.807 seconds, archived as
`full-facebook-abi.log`. This rerun adds actual unavailable Facebook ABI calls
and scans the compiled `bt`, `bt$Companion` and `cm` constant pools for removed
SDK references. The first fixture attempt used package-private Java `ai`/`ay`
peers, which Kotlin correctly rejected as public return types; making those
fixture types public fixed the fixture without changing application behavior.
