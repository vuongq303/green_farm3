# Báo cáo sửa lỗi save/load sau cold start

**Trạng thái: PATCHED — AWAITING DEVICE VALIDATION.** Đã sửa source và có bằng chứng trên host/emulator; chưa xác minh bản database thực tế đang lỗi của người dùng. Không gọi đây là FIXED cho mọi trường hợp.

1. **Nguyên nhân được chứng minh:** Android Studio Debug mặc định không bật local-development. Bản vá cũ vẫn kiểm tra UUID SDK khi đọc SQLite trong chế độ này. SDK có thể sinh UUID mới vì cơ chế DataSharing chỉ tìm authority `com.gameloft*`, bỏ qua provider đã đổi package. Bản save còn nguyên bị từ chối vì UUID khác.
2. **Vị trí:** điều kiện cũ `FarmGameEngine.java:20684`, nhánh trả false `:20686–20692`; exception chung tại `ProgressSaveController.kt:136` là hậu quả. Điều kiện mới tại `FarmGameEngine.java:20707` phân biệt nguồn SQLite và buffer. Không dùng cờ build để quyết định quyền đọc local nữa.
3. **Đã sửa:** giữ nguyên codec/gameplay, thêm phân loại lỗi có cause; kiểm tra version/chiều dài; bỏ tác dụng phụ đổi cờ người chơi/ghi header khi từ chối identity; chặn mọi ghi sau restore lỗi, kể cả header snapshot đã capture trước đó; giữ nguyên schema 4, transaction, WAL, checksum và single writer. Sửa đường retry sau khi mở lại storage để không tự khóa vì hàng rào mới.
4. **Vì sao đúng nguyên nhân:** SQLite private là slot `device-local`, không phải phản hồi cloud. Đọc slot này không cần HDIDFV thay đổi theo process. Buffer cloud/legacy vẫn bị kiểm tra identity chặt; không dùng checksum local hay identity nhập từ save để xác thực server. Backend/session/refresh không bị sửa.
5. **Đã chạy:** tổng hợp chính xác theo ID: 187/187 bài host đạt, không còn bài bị bỏ qua; 20 bài công cụ đạt;25 unit backend và 10 integration MySQL đạt. Codec global và farm chạy 20 JVM mới, UUID thay đổi, reserialize khớp byte. 31 lệnh kiểm tra SQLite Android thật trên candidate đạt, gồm process chết trước/sau commit, corruption, migration và hàng rào ghi. APK cuối cập nhật bằng `install -r`, giữ database cũ và đã vào farm với `RESTORE_SUCCESS`. 20/20 cold start Android đã đạt, 20 PID mới/21 hash UUID, mỗi vòng có restore→world-ready capture→commit thành công; 40 bản sao DB hợp lệ. Debug mặc định (cờ local tắt) cũng đạt 3 lần restore đầy đủ, gồm 2 cold start bổ sung có UUID thay đổi tự nhiên. Background/foreground, kill process và reboot emulator đều đạt; tiến trình thu hoạch mới được giữ nguyên. Thoát qua nút Exit trong game chưa chạy được vì UI tutorial không cung cấp đường thoát đó; không tính force-stop thành thoát bình thường.
6. **Giới hạn:** 100 đối tượng cây/10 công trình trong test codec dùng resource/factory peers, không phải100 cây đã trồng bằng giao diện Android. Chưa có save thật của máy người dùng; Robolectric runtime bị chặn tải dependency (đã compile, không tính là pass). Không suy diễn host hoặc fixture SQLite thành gameplay đầy đủ.
7. **APK:** bản local được yêu cầu: `/workspace/greenfarm-cloud/save-fix/greenfarm-save-fix-local-debug.apk`. Bản Debug mặc định: `/workspace/greenfarm-cloud/save-fix/greenfarm-save-fix-default-debug.apk`. Cả hai build thành công, applicationId `com.quanhoang.greenfarm3`, chữ ký/alignment hợp lệ. Hash và manifest ở phần bằng chứng chi tiết.
8. **Cài giữ dữ liệu:** không uninstall/clear data/xóa DB hoặc WAL. Giữ keystore cũ; tốt nhất áp dụng source rồi Run Debug trong checkout Android Studio cũ. Chỉ dùng `adb install -r` khi chữ ký trùng. Nếu báo `UPDATE_INCOMPATIBLE`, dừng và build/ký bằng khóa cũ, không gỡ app để vượt lỗi. APK cloud có thể khác khóa của máy Windows.
9. **Rủi ro còn lại:** save thật có thể có lỗi khác ngoài UUID; dữ liệu hỏng không được tự bịa để phục hồi. Cloud/legacy không có binding xác minh được sẽ bị từ chối, kể cả một số luồng chuyển thiết bị cũ. Mutation chưa capture/commit vẫn có thể mất khi Android kill process. Nếu máy thật vẫn lỗi, giữ nguyên dữ liệu và cung cấp exception chain/result code mới cùng bản export local đã loại dữ liệu tài khoản nếu cần; không gửi token/mật khẩu.

Phần chi tiết sau đây ghi rõ đường đi dữ liệu, phạm vi từng test và cách tái chạy.

---

# Cold-start restore investigation and patch

Status: **PATCHED — AWAITING DEVICE VALIDATION**. Source/host reproduction establishes the default-debug UUID rejection. This is not a claim that the affected player's actual database or every requested farm/lifecycle scenario has been validated.

## 1. Root cause and evidence

The user confirmed the failing app was launched with Android Studio's ordinary Debug configuration, without `greenfarmLocalDevelopment=true`.

Before this patch, `FarmGameEngine.ax(false)` exempted private SQLite progress from SDK identity checks only when that build flag was enabled. With the default false value, it compared the saved UUID against `rl()` → `au.Os()`. The legacy HDIDFV implementation generates a random UUID; DataSharing discovers only `com.gameloft*` provider authorities, while this application's own provider authority is `com.quanhoang.greenfarm3.KeyProvider`. Its process cache therefore does not establish persistence through the renamed provider. Different cold processes can receive different SDK UUIDs.

The original offending condition was `FarmGameEngine.java:20684` (`!LocalDevelopmentMode.isEnabled() || aCw != null`), with rejection at lines20686–20692. The old mismatch branch changed player flags, attempted `ro()`, and returned false. `globalLoadFinished(existed=true, loaded=false)` then threw the generic exception at original `ProgressSaveController.kt:136`, exactly the guard shown in the screenshot. The replacement identity decision is at current `FarmGameEngine.java:20707`; cause-preserving completion is at current `ProgressSaveController.kt:135`. The guard was the symptom, not evidence of SQLite corruption. There were exactly two false returns in `ax`: missing bytes and identity mismatch; decode/storage errors threw instead. The old prefix regression reproduced the mismatch branch in ordinary mode. The replacement tests exercise both build modes with a different SDK UUID in every fresh JVM.

`GameRuntime.B` → `c` → `d` reads `GameSaveStore.read` directly; it does not download cloud state. The store uses the fixed `device-local` slot. `w.Wn` is false with no production assignment enabling it, so the current SQLite record is not encrypted with that volatile UUID. The SDK provider and backend authentication were not rewritten.

Additional defects established during investigation:

- Future gameplay versions were parsed using the current layout rather than rejected.
- Length-prefixed strings allocated arrays without checking record bounds.
- Restore failure did not establish an explicit store-level write barrier; a pre-captured language-only snapshot could bypass the ordinary loading guard.
- Storage/open/checksum errors lost their specific classification through generic wrappers; bootstrap swallowed its cause.
- Review of the new barrier found an explicit completion-retry path requiring `beginLoading()` after reopening. A barrier-aware controller regression failed before this correction and passed after it.

The screenshot and user response identify an applicable source defect; no copy of the affected player's database or full original logcat was supplied. Other faults in that particular save cannot be ruled out.

## 2. Code locations and changes

- `app/src/main/java/com/quanhoang/greenfarm3/S800x480/FarmGameEngine.java`: `ax` preserves typed storage failures and wraps decoder/engine exceptions with their original causes; `restoreGlobalGameplay` makes the identity decision from the actual source. Private SQLite reads do not require HDIDFV in either build mode. Buffered data still requires an exact nonempty current identity. Legacy buffered formats 0–2, which cannot establish that binding, are rejected. The old `asP`, different-length and imported-local identity exemptions cannot authorize buffer data. Unknown versions are rejected before field restoration; `o(byte[],int)` validates lengths before allocation. No currency or item defaults were changed.
- `SaveRestoreException.java`: explicit result codes and cause-preserving classification.
- `ProgressSaveController.kt`: typed missing-record guard, store write barrier on failure, diagnostic result/step/database status/generation/local-mode logging, and explicit completion-retry initialization.
- `GameSaveStore.kt`: separate failed-restore state rather than treating engine rejection as database corruption. It blocks gameplay captures, language captures, already-captured snapshot application and premature load completion. Explicit load retry revalidates reads. Existing schema 4, WAL, FULL synchronization, SHA-256, transactions, rollback and single writer remain.
- `GloftGF2F.kt`: logs the original bootstrap storage exception.
- Test/probe helpers include the production Java diagnosis class in their compile/runtime classpaths. `prepare_core_dependencies.py` recognizes the added helper as source rather than an original APK class.

The decoder body remains the existing implementation; the small wrapper separates storage, format and engine failures. No gameplay or database architecture rewrite, reset, destructive migration or new schema is introduced.

## 3. Startup trace and failure boundaries

| Stage | Success / state | Failure / safety / dependencies |
|---|---|---|
| Application / SDK | Android context and MIDlet initialize; SDK may discover providers/cache UUID | SDK UUID is volatile under renamed-provider discovery. It is no longer a prerequisite for restoring the private SQLite slot. Buffered/cloud checks remain separate. |
| SQLite initialization | Open app-private database; configure WAL, FULL sync, foreign keys and busy timeout; validate/migrate schema 1→4; validate required snapshot records/checksums | Typed open/read/schema/checksum failures preserve causes. No automatic deletion/reset. First legacy import may require external files; a complete SQLite snapshot skips that import. |
| `beginLoading()` | Under lock, establish loading/protection, validate existing gameplay, reset logical view to confirmed records | Cannot load during a pending transaction. Existing progress is protected; failed validation does not clear the failure barrier. |
| Step 44 / `ax(false)` | Copy a supplied buffer or read private `save_gameplay`; validate version, string bounds and appropriate identity boundary; run original global decoder | Truly absent save returns false. Previously present but missing save raises SAVE_NOT_FOUND at completion. Decode/version/identity/storage/engine errors retain distinct codes and causes. Partial in-memory decode cannot enable autosave. |
| `globalLoadFinished` | Mark global state loaded only on successful restoration or genuinely new game | Existing failed progress cannot become a default world. No blanket success return. |
| Farm load / step 47 | Original farm/entity decode plus pending reward/pose restore | Failure blocks world entry and writes; saved records remain authoritative. The independent full-global codec fixture is not proof of complete farm/entity restoration. |
| `enterWorld()` | Requires global+own-farm loaded and no load block, then `finishLoading`; register dispatcher and request save | Failed loads cannot register a writable world. Explicit retry is user-triggered; no unlimited retry loop. |
| Frame/lifecycle writer | Capture on game thread, detached absolute snapshot, one writer, transaction commit then durable acknowledgement | Uncaptured mutations and uncommitted/queued writes can be lost on force-stop. A committed snapshot and WAL are the durability boundary; process exit alone is not proof that a mutation committed. |

There is no demonstrated SQLite/WAL race causing the reported false return. Locks, read validation, game-thread capture and the loading/write barriers are retained. A previously active writer must become idle before loader reinitialization. The existing queue retains failed ordinary writes and uses bounded attempts; the patch does not discard a pending save to force a retry through.

## 4. Result codes and preservation

`SAVE_NOT_FOUND`, `SQLITE_OPEN_FAILED`, `SQLITE_READ_FAILED`, `CHECKSUM_MISMATCH`, `SAVE_DECODE_FAILED`, `SAVE_VERSION_UNSUPPORTED`, `IDENTITY_MISMATCH`, `ENGINE_RESTORE_FAILED`, `RESTORE_SUCCESS`; write paths also use `SQLITE_WRITE_FAILED`.

An absent record on a new database is distinct from an incomplete previously committed snapshot. The latter is rejected by storage validation. A successful checksum establishes local blob integrity, never server authentication or account ownership. No backend authorization/session/refresh/compliance code changed. Exact device binding on legacy buffers is an additional conservative restriction, not a replacement for server account authorization. Legitimate legacy cross-device cloud restoration may now require a separately designed authenticated ownership path; this patch does not fabricate one.

On failed restore, no new farm/save is synthesized and autosave cannot overwrite the old one. The original cause remains in the logged exception chain. Logs add only outcome, load step, store status, generation and build mode; no new UUID, token, credential or gameplay-payload logging is introduced.

## 5. Executed validation

| Check | Result / scope |
|---|---|
| Full existing/new host suite | **187/187 unique discovered cases passed**, no remaining case IDs. Full verbose run:171 testsRun, 158 method passes, 13 method skips, 2 setUpClass skips representing 16 unrun cases. Supported original-JAR override, verified tool cache and genuine javac refresh enabled all 29 previously unrun cases; final targeted 29/29 passed. Exact ID union: `/workspace/greenfarm-cloud/save-fix-host-test-id-union.json`; skip/failure diagnosis: `save-fix-host-skips.md`. Two timestamp guards failed before examining bytecode and were corrected by rebuilding output; one fixture gained required production compilation dependencies only. Assertions were not weakened. |
| Tools | 20 passed, zero skipped. |
| Source identity/prefix tests | 8 passed; 40 fresh JVM restores, 20 in each build mode, changing UUID. Strict buffer/cloud mismatch, imported-local alias, empty identity and formats 0–2 rejected. Prefix scope is explicit. |
| Complex farm codec | 1 test passed (2.482 s), included in the 187-ID union; 100 crop objects, 4 seed fixture IDs, mixed states and 100 distinct timers/coordinates plus 10 buildings, farm store/expansion,20 fresh JVM restores, complete 204800-byte reserialization identical. Production ry/p/az/d/h/g/w/c/s and fi/fj/fy/fz bodies run; resource lookup/factories are peers. This is not a100-crop Android UI test and not an atomic global+farm SQLite fixture. |
| Full global gameplay codec | 2 passed; 20 fresh JVMs, nonzero economy obfuscation keys, byte-identical reserialization, inventory/money/quest/timestamp checks. Storage/resources are peers. |
| Controller/lifecycle | Passed; blocked autosave and preservation of durable progress after failure, explicit retry, bounded queue retry, lifecycle capture, language compensation and backend isolation. A barrier-aware retry regression was first reproduced failing, then corrected and rerun passing. |
| Backend/cloud boundary | 25 unit tests and 10 real MySQL integration tests passed, including rejected forged/foreign-namespace tokens and durable refresh/session behavior. No backend security code changed. |
| Android production SQLite | 31/31 matrix commands passed on emulator API28 with installed candidate APK, production storage and real Android SQLite in isolated contexts. Includes expected process `halt(17)` before commit / `halt(18)` after commit and subsequent reads, schema1/3 migration, checksum/future-schema rejection, missing records, rollback/write failure, failed-restore/header barrier and asynchronous snapshots. Seed/setup commands count in31; this is not31 gameplay scenarios. Candidate SHA-256: `ab32c915442f86c9e3fa90cf7ab5750d475384d60c4893be8f11ef9286f48d3f`. The21storage DEX classes were independently reserialized from candidate/final APKs and match SHA-256 `5d9a75d5a2624cf865c4b06e03d45bf94dc3bdcad68e157a0802d55a0e9f25df`; the same comparison also matches the default-debug APK. Evidence: `/workspace/greenfarm-cloud/storage-dex-comparison.txt` and `default-storage-dex-comparison.txt`. |
| Robolectric native SQLite | Test source compiles, but runtime dependency download remains blocked by current network policy at `gcp.mirrors.maven.org`; not counted as passed. Actual Android SQLite evidence above is separate. |
| Final local APK | `assembleDebug -PgreenfarmLocalDevelopment=true` succeeded; final SHA-256 `e2ea9bc2f0d734a5bedd52a142da8c066842ab54c24551c8540e69bcd822b3f6`. Package/applicationId `com.quanhoang.greenfarm3`, versionCode44220, minSdk16/target28. Signature v1/v2/v3 and zip alignment passed. Manifest provider authority remains `com.quanhoang.greenfarm3.KeyProvider`. |
| Preserve-save app update | `adb install -r` from candidate to immutable final APK succeeded; existing actual game database retained. Final app logged `RESTORE_SUCCESS` and rendered tutorial farm. Actual database backup had integrity_check=ok and valid per-record checksums. |

Final local APK: `/workspace/greenfarm-cloud/save-fix/greenfarm-save-fix-local-debug.apk`. Ordinary default-debug build also succeeded; `/workspace/green_farm3/app/build/outputs/apk/debug/app-debug.apk` is not used as an immutable reference. The copied default-debug artifact is `/workspace/greenfarm-cloud/save-fix/greenfarm-save-fix-default-debug.apk`, SHA-256 `9c695f9e3a910a91c4e30695a0ccf04d8bc55e4ebbec0cb93e03e968f69576c2`; its signature and alignment passed too. Signature certificate SHA-256: `4c828d942468336b26bfab70a500198da5807d24d2cfb0ca6c52f801bb2ab510`. APK signature, badging and manifest evidence are under `/workspace/greenfarm-cloud/save-fix/`.

Host fixtures explicitly state their boundaries; they are not renamed as Android gameplay tests. Android cold-loop result: **20/20 passed on the immutable final local APK** (`e2ea9bc…822b3f6`),20 unique application PIDs,21 distinct hashes of naturally changing saved HDIDFV bindings. Every case requires ordered `RESTORE_SUCCESS` → game-thread world-ready capture → later matching successful writer acknowledgement, followed by a rendered-farm screenshot and SQLite checks. All 40 before/after SQLite backups have integrity_check=ok, valid blob checksums, gameplay+farm records and nondecreasing generations. No application data was cleared/reset between cycles. Report: `/workspace/greenfarm-cloud/cold-starts/results.json`; screenshots/logs and consistent backups are in that directory. A first step44-only attempt was explicitly archived and excluded; it was not counted among 20.

The actual UI harvest changed a wheat patch on the generated test farm and committed successfully (`harvest-swipe.png`, `harvest-commit.log`). This is a real game mutation, not the100-object host fixture. Actual final-local lifecycle: **3/3 passed**: background/foreground retained the same PID; Android `am kill` genuinely removed the background process and a new process restored/committed; emulator reboot restored/committed again. Each case checked SQLite integrity, checksum and critical records. The harvested crop gap remained visible after kill and reboot. Report: `/workspace/greenfarm-cloud/lifecycle/results.json`; screenshots `background-am-kill.png` and `emulator-reboot.png`.

Read-only extraction of actual snapshot global state ran the complete production decoder with source-proven dimensions/obfuscation keys; it verified checksums first and performed no SDK calls/writes. XP and inventory slot[0][9] changed 0→2 after real UI harvesting, then remained 2 after kill and reboot. Entire decoded inventory, coins/cash and tutorial fields match the committed after-harvest baseline across kill/reboot. Resource/queue callbacks are disclosed peers; actual farm family 54 has variable animal layout, so no guessed crop-count parser was used. Evidence `/workspace/greenfarm-cloud/actual-state.json` and `actual-state-extraction.md`.

Normal in-game Exit/Back quit remains **unrun**: the current tutorial did not expose an Exit action; Recents did not offer an app card. Force-stop/am kill are not relabeled as normal quit. The production quit/lifecycle controller path is covered by host tests, which is a separate result. Default Debug (local-development flag **false**, verified from compiled DEX) also passed: one preserved-database APK update/start and **2/2 additional fresh force-stop/start cycles**, each requiring global restore, ordered world-ready capture and later matching successful commit, SQLite integrity/checksum/generation checks and rendered farm. The 2 additional cases changed saved SDK binding naturally (3 distinct hashes across 2 cycles). Thus 20 designated local cold starts and 3 designated default-debug restores passed; no claim of 20 default cycles is made. Evidence `/workspace/greenfarm-cloud/default-debug/results.json` and `default-cold-starts-final/results.json`.

The default test mapped only the emulator guest's configured 10.37.233.55:8787 destination through adb reverse to the actual host Node/MySQL backend. Health returned 200/MySQL ready. The baked URL, app source, database ownership and backend authentication were not changed for the route. Existing optional Facebook dialogs were handled with actual **Not now → No** button taps; there was no fabricated login, callback, token or cloud success. Initial manual harness attempts timed out while waiting for those dialogs, and remain in separate evidence files; they are not counted as successful cold tests. Fresh final cases used visual confirmation of the known dialogs and the same unchanged strong readiness assertions.

Read-only actual-state extraction after default-debug restoration and the final second default cold-start snapshot also matches the post-harvest committed baseline: XP 2, inventory[0][9]=2, entire inventory arrays/coins/cash/tutorial unchanged. These initial currency values belong to the generated local-development test farm, whose pre-existing new-player budget was not modified by this patch; they are not changes to the user's farm. Actual affected-device save, full 100-crop Android UI/simulation and a normal in-game Exit path remain outside this completed emulator evidence.

## 6. Remaining validation

A full farm with 100 actual crops, multiple types, buildings, inventory, currency and staggered harvest times requires complete engine/device validation. The global codec fixture's 100 actor indices are not 100 planted crops. Device reboot and restoration of the user's actual old save must be reported separately from process-level fixture checks.

## 7. Reproduction commands

Activate the prepared cloud tools: `source /workspace/greenfarm-cloud/activate.sh`.

```sh
./gradlew :app:assembleDebug -PgreenfarmLocalDevelopment=true -PpythonExecutable=python3 --no-daemon
# Historical fixtures accept this supported override instead of old /workspace/tooling paths.
export GREENFARM_ORIGINAL_JAR=/workspace/green_farm3/.local/bytecode-tools/greenfarm-original.jar
# Refresh genuine javac output if a fixture rejects stale source/class timestamps.
./gradlew :app:compileDebugJavaWithJavac -PpythonExecutable=python3 --rerun-tasks --no-daemon
python3 -m unittest discover -s tests
python3 -m unittest discover -s tools -p 'test_*.py'
./gradlew -p tests/sqlite-host test --no-daemon
```

The native Robolectric suite needs `gcp.mirrors.maven.org`, which is denied by the current runtime policy; compilation alone is not a test pass. The Android storage probe instead executes installed production storage against Android's real SQLite in isolated fixture contexts; it must never point at the player's database. See `tools/run_game_save_probe.py --help` for its device/SDK/output parameters and fresh prefix requirement.

## 8. Install without losing data

1. Keep the installed app and its data. Do not uninstall, clear storage, delete the database, delete WAL, change applicationId or use a reset script.
2. Preserve the original signing keystore, especially the checkout's ignored `.local` debug keystore. This cloud APK uses this cloud checkout's key; it need not match Android Studio's previously installed APK.
3. Safest for the affected device: apply the source patch in the existing Android Studio checkout and build/run Debug using its existing key and `com.quanhoang.greenfarm3`. The local restore fix works without enabling the local-development flag.
4. When sideloading an APK signed by the **same** key, use `adb install -r <apk>`. If Android reports `INSTALL_FAILED_UPDATE_INCOMPATIBLE`, stop and rebuild/sign with the original key. Do not uninstall to bypass that error.
5. Before changing the installation, make an app-supported export/backup where available. An ADB database backup must first force-stop the app and preserve the database together with any WAL/SHM files; copying only the main database while it is running can omit committed WAL state. Do not share raw account/token material in chat.
6. Confirm the existing farm, inventory and currency after reopening. If loading still fails, preserve the app/data and provide the new `GreenFarmSave` exception chain/result code; an authorized, sanitized local-save export is needed to diagnose that particular save. No recovery by replacing it with a default farm is acceptable.

## 9. Risks and limits

- The actual affected database and complete original exception history remain unavailable.
- Existing corrupt/truncated/unsupported records are refused and retained; this patch does not invent missing progress or repair corrupted bytes.
- Old cloud/buffer formats without verifiable binding and cross-device mismatches are conservatively refused. No claim that legacy Facebook/cloud gameplay APIs are functional is made.
- A force-stop can lose mutations that were not captured and committed; WAL cannot persist mutations that existed only in engine memory.
- Emulator storage fixtures, host global codec tests, fresh tutorial gameplay and a complex existing player farm are different validation targets.
