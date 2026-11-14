# Native SQLite host checks

This Java/Robolectric harness builds no APK and adds no test profile to the game. Build the regular app classes first, then run from the repository root:

```sh
./gradlew :app:assembleDebug -PpythonExecutable=python3
./gradlew -p tests/sqlite-host test
```

Use JDK 25 for the pinned Gradle build and install a JDK/JRE 21 launcher for the host test process. Gradle discovers Java 21, or `GREENFARM_TEST_JAVA` can name its executable. Keep inherited proxy and CA trust.

Tests execute the production GameSaveStore/AsyncSaveQueue with native SQLite, an isolated Robolectric Context and API35 framework. API28 native-runtime startup failed in this host configuration; these checks do not establish API28 device readiness. The two test-only Gameloft peers select internal storage and prohibit implicit global Context lookup; SQLite, transactions, schema migrations, WAL, and prepared statements are not mocked.

Coverage: 1/10/50/100 absolute auxiliary-state submissions, stale/replayed snapshots, reopen, complete five-record storage snapshots, schema3 upgrade, writer lock recovery, rollback after every row has been written, and deferred-language protection against direct header writes. Payload fixtures are opaque test bytes, not playable farm saves. All deletion/cleanup is restricted to the isolated test Context. No installed app or player database is opened.

The timing sample measures 100 transactions, three 32KiB records per transaction, after five warmups. It does not measure harvesting, gameplay FPS, Android UI latency, or network sync. `-PstorageClasses=/absolute/classes` can run that same timing test against preserved baseline storage classes using `--tests '*storageOnlyTimingSample'`. Do not run APK builds concurrently against the same checkout.
