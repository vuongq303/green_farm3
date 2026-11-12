# Android SQLite probe

The completed schema4 run `s4_run_20261112_c1` passed all 26 cases on Android 9
(`emulator-5554`) against installed APK SHA-256
`bdfc975452e0cccbb886eb47f52be5190e0ef93c058aab0689a72ddc6278d933`.

`probe-results.json` records the latest run against the installed complete APK.
The runner writes each result immediately; `passed` becomes true only after the
whole matrix finishes. The report includes the installed APK hash, probe DEX
hash, isolated fixture prefix and actual process exit codes. Raw output is kept
in the corresponding `probe-<prefix>.log`.

Run after building and installing the debug APK, with a JDK and Android SDK:

```sh
python3 tools/run_game_save_probe.py --serial emulator-5554 --sdk /path/to/android-sdk --timeout 60
```

The runner compiles only `GameSaveStoreProbe` and its isolated context. The
production `GameSaveStore` is loaded from the installed APK; the database is the
real Android SQLite implementation. Each fixture has separate files and database
directories beneath `files/sqlite-probe-<prefix>`. The normal player database is
never opened or modified by this runner. Crash tests deliberately terminate only
their probe process with exit 17 (uncommitted) or 18 (committed).

The Android 9 `app_process` bootstrap prepares a main Looper and initializes
SQLite compatibility defaults before loading the application context. This avoids
a Settings-provider lookup by an unregistered test process. The production
helper still explicitly enables WAL and FULL synchronization. The probe checks
those policies on the real primary writer inside a transaction: a separate WAL
reader can correctly report its own different synchronous mode.

Earlier raw logs are retained for failed harness attempts. The initial missing
Looper and Settings-provider failures occurred before storage initialization;
the first synchronous assertion queried a reader instead of the writer. These
test-process failures do not establish a gameplay-process crash. The probe tests
record persistence, transactions, import and failure handling using fixed BLOBs;
gameplay serialization and UI behavior require the separate game runtime checks.
