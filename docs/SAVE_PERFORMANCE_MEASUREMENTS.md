# Save performance measurements

The measured before/after comparison is a **storage replay**, not a harvest or frame-rate test. It exercises the real `GameSaveStore` in each installed APK, Android SQLite, WAL and `synchronous=FULL`, using an isolated `ContextWrapper`. It never opens the normal player database. The real player records were first preserved with a read-only `adb exec-out run-as ... cat` copy of the database and WAL, and the local copy passed `integrity_check`.

## Fixture and method

- Device: cloud emulator `emulator-5554`, Android API 28. Absolute timings include this emulator's software execution and host load.
- Before APK SHA-256: `bdfc975452e0cccbb886eb47f52be5190e0ef93c058aab0689a72ddc6278d933`.
- After APK SHA-256: `1dd040b97f0bb0fd446ba90efbe4de74afb03970f522253a6b6072daefed535f`.
- Fixture SHA-256: `c4c128d49cb98b70e163294dd17e9c41a4e156fe80682db6e987e8d6931d2921`.
- Identical probe DEX SHA-256 in both runs: `105a57a2420380dee9827b42966a1ac921846d189456d06cd3c50bb0933f8d50`.
- Captured source generation: 561. The original APK, database/WAL and binary fixture remain under the ignored `.local/save-performance-baseline/` directory.

| Real serialized record | Bytes |
| --- | ---: |
| `RMSHeader` | 35 |
| `save_gameplay` | 51,200 |
| `save_farm0` | 204,800 |
| `pending_rewards_v1` | 12 |
| `entity_pose_v1` | 204 |
| Total | 256,251 |

The probe first seeds these exact five records into a new private fixture database. It then submits 100 full snapshot requests, targeting a 20 ms interval. Every request deterministically XORs the final four bytes of the captured gameplay and farm payloads with its sequence number; the other three records remain identical. These are opaque storage stress bytes, **not loadable game saves or simulated harvest outcomes**. Both versions must use the identical binary fixture and mutation sequence. The stress case changes 25,600,000 logical payload bytes before batching.

The before timed call includes `beginSave`, five production `write` calls and `commitSave`. Synthetic payload construction, initial seeding, process startup and final verification are outside that timing. A synchronous call that misses its next 20 ms target starts the following request immediately; the replay does not fabricate asynchronous input throughput for the old writer. Before results therefore represent 100 explicit dirty snapshot requests, not the old controller's actual 500 ms gate during gameplay. Caller CPU samples use Android's `Debug.threadCpuTimeNanos`.

Changed durable commits are measured by generation growth, because the commit table retains only recent history. `/proc/self/io` is sampled around the workload: `wchar` counts write-syscall bytes and `write_bytes` is the kernel's block-I/O accounting; neither is a claim of flash wear or power consumption. WAL file length is not used as write volume because checkpoints and WAL reuse invalidate that comparison. Every final record is compared with its expected bytes, the production store is reopened, and all final records and the generation are checked again.

## Measured comparison

Reports using the identical final probe and fixture: [before-final-100.json](save-performance/before-final-100.json) and [after-final-100.json](save-performance/after-final-100.json). Captured process output: [before](save-performance/before-final-100.log) and [after](save-performance/after-final-100.log). Before was measured at 16:55 UTC and after at 17:11 UTC on 2026-11-12. The probe explicitly verifies WAL and FULL on each production writer connection. Other coordinated emulator operations and host CPU tests were paused during the after run.

| Metric | Before: synchronous store | After: capture + production queue |
| --- | ---: | ---: |
| Snapshot requests | 100 | 100 |
| Changed durable commits | 100 | 6 |
| Caller p50 | 242.48 ms | 3.48 ms |
| Caller p95 | 457.60 ms | 31.85 ms |
| Maximum caller block | 929.00 ms | 190.77 ms |
| Caller CPU p50 | 100.86 ms | 3.10 ms |
| Caller CPU p95 | 139.89 ms | 12.28 ms |
| Total caller CPU | 10,535.68 ms | 456.78 ms |
| Total caller time | 28,109.58 ms | 945.57 ms |
| Total workload elapsed, including final drain | 28,966.06 ms | 2,113.52 ms |
| Final queue drain | — | 118.22 ms |
| Worker apply calls | — | 6 |
| Worker apply p50 / p95 | — | 303.59 / 634.80 ms |
| Write-syscall bytes (`wchar`) | 43,198,419 | 2,608,739 |
| Kernel block write bytes (`write_bytes`) | 43,806,720 | 2,633,728 |
| Final record bytes and generation verified after reopen | Yes | Yes |
| Actual harvest actions | 0 | 0 |

For this equal-request workload, caller p95 fell by 93.0%, caller CPU total by 95.7%, and kernel-accounted block writes by 94.0%. The queue coalesced pending absolute snapshots into six commits and durably applied the final sequence 100. Worker commit work still takes hundreds of milliseconds in this emulator; it runs on the writer thread. The maximum capture/submit call was still 190.77 ms, so these results do not establish a frame budget or frame-rate improvement.

The old writer already avoids replacing unchanged record payloads. An unchanged replay is consequently only a no-op control and cannot establish a batching improvement. This measurement does not include original gameplay serialization, render frames, crop rewards, or the old controller's save-request frequency.

Earlier runs made while adding CPU/WAL instrumentation are retained: [initial](save-performance/before-delta-100.json) and [CPU instrumentation](save-performance/before-delta-cpu-100.json). Their caller p95 values were 758.23 and 683.22 ms, respectively, with the same 100 durable commits and fixture. This illustrates host/emulator timing variability; these runs are not separate gameplay measurements.

## After method

`--delivery async` calls the new production `beginCapture`, five `write` calls and `finishCapture` on the caller, then submits the immutable snapshot to the APK's real `AsyncSaveQueue`. Reflection permits the same probe DEX to load both old and new APKs. The production queue coalesces pending snapshots and applies them on its one writer thread; the probe does not implement a replacement queue. The workload still submits the same 100 explicit dirty snapshots, and waits for the final durable sequence before sampling total elapsed time and process I/O. Caller samples exclude this final drain, which is reported separately. Worker apply times come from the production completion callback.

The controller's `SaveBatchPolicy` is bypassed in both cases. This comparison measures storage capture and queue coalescing for equal snapshot requests, and cannot by itself prove the 200 ms debounce, 500 ms maximum wait or 50-action trigger in gameplay.

## Reproduce

The runner compiles only probe classes; it neither builds nor installs an APK. The storage implementation is loaded from the complete installed APK, and the report records that APK's hash. Use the same fixture for before and after:

```sh
JAVA_HOME=/workspace/tooling/jdk-25 \
ANDROID_HOME=/workspace/tooling/android-sdk \
ANDROID_USER_HOME=/workspace/tooling/android-user \
python3 tools/run_game_save_performance.py \
  --fixture .local/save-performance-baseline/fixture.bin \
  --prefix p_before_run_01 \
  --output docs/save-performance/before-run-01.json
```

Choose a fresh prefix for each run. After installing the new APK, add `--delivery async` and choose a new output path. `--capture-local-db PATH` creates the binary fixture from an already preserved **local** SQLite database. Optional `--runtime-apk PATH` loads a preserved APK without installing it; ART may need a cold whole-APK compilation before the timed workload, so this can exceed the command timeout on this emulator. `--timeout` allows an owned-process deadline from 5–180 seconds, and timeout reports/logs are retained. `--variant unchanged` retains all source bytes as a no-op control.

The requested 50–100 real-harvest before/after frame and action validation needs a separate controlled gameplay fixture. Storage replay alone does not establish that result or prove the controller's 200/500 ms scheduling bounds.
