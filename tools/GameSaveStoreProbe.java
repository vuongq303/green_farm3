import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import com.gameloft.android.ANMP.GloftGF2F.S800x480.GameSaveStore;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.zip.CRC32;

/** Android-only, real SQLite verification against the helper in the installed
 * APK. Run in a separate app_process under run-as. Every command is restricted
 * to files/sqlite-probe-<fixture>; it never opens the actual player database.
 */
public final class GameSaveStoreProbe {
    private static final String PACKAGE = "com.gameloft.android.ANMP.GloftGF2F";
    private static final String SLOT = "device-local";
    private static final String[] RECORDS = {"RMSHeader", "save_gameplay", "save_farm0", "pending_rewards_v1", "entity_pose_v1"};

    private static final class IsolatedContext extends ContextWrapper {
        private final File root;
        private final File files;
        IsolatedContext(Context base, String fixture) {
            super(base);
            root = new File(base.getFilesDir(), "sqlite-probe-" + fixture);
            files = new File(root, "files");
            check(files.isDirectory() || files.mkdirs(), "Cannot create isolated probe files");
        }
        @Override public Context getApplicationContext() { return this; }
        @Override public File getFilesDir() { return files; }
        @Override public File getExternalFilesDir(String type) {
            File external = new File(root, "external");
            check(external.isDirectory() || external.mkdirs(), "Cannot create isolated external directory");
            return external;
        }
        @Override public File getDatabasePath(String name) {
            check(GameSaveStore.DATABASE_NAME.equals(name), "Unexpected probe database filename");
            File directory = new File(root, "databases");
            check(directory.isDirectory() || directory.mkdirs(), "Cannot create isolated databases directory");
            return new File(directory, name);
        }
        @Override public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            return openOrCreateDatabase(name, mode, factory, null);
        }
        @Override public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler handler) {
            int flags = SQLiteDatabase.CREATE_IF_NECESSARY | SQLiteDatabase.OPEN_READWRITE;
            if ((mode & Context.MODE_ENABLE_WRITE_AHEAD_LOGGING) != 0) {
                flags |= SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING;
            }
            return SQLiteDatabase.openDatabase(getDatabasePath(name).getPath(), factory, flags, handler);
        }
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    private static byte[] data(String label) throws Exception { return label.getBytes("UTF-8"); }
    private static byte[] digest(byte[] value) throws Exception { return MessageDigest.getInstance("SHA-256").digest(value); }
    private static void equal(String name, String value) throws Exception {
        check(Arrays.equals(GameSaveStore.read(name), data(value)), "Unexpected persisted record: " + name);
    }
    private static long scalar(SQLiteDatabase db, String sql) {
        Cursor cursor = db.rawQuery(sql, null);
        try { check(cursor.moveToFirst(), "SQL returned no row"); return cursor.getLong(0); }
        finally { cursor.close(); }
    }
    private static SQLiteDatabase raw(IsolatedContext context) {
        return SQLiteDatabase.openDatabase(context.getDatabasePath(GameSaveStore.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }
    private static void fresh(IsolatedContext context) {
        check(!context.getDatabasePath(GameSaveStore.DATABASE_NAME).exists(), "Use a fresh probe fixture name");
    }
    private static void snapshot(String stage, boolean wholeFarm) throws Exception {
        GameSaveStore.beginSave(wholeFarm);
        boolean committed = false;
        try {
            for (String record : RECORDS) {
                if (!wholeFarm && (record.equals("save_farm0") || record.equals("entity_pose_v1"))) continue;
                byte[] value = data(record + "-" + stage);
                GameSaveStore.write(record, value, 0, value.length);
            }
            GameSaveStore.commitSave();
            committed = true;
        } finally { if (!committed) GameSaveStore.abortSave(); }
    }
    private static GameSaveStore.Snapshot capture(String stage, boolean wholeFarm) throws Exception {
        GameSaveStore.beginCapture(wholeFarm);
        try {
            for (String record : RECORDS) {
                if (!wholeFarm && (record.equals("save_farm0") || record.equals("entity_pose_v1"))) continue;
                byte[] value = data(record + "-" + stage);
                GameSaveStore.write(record, value, 0, value.length);
                // The serializer's scratch buffer may be reused immediately.
                Arrays.fill(value, (byte) 0);
            }
            return GameSaveStore.finishCapture();
        } finally { GameSaveStore.abortCapture(); }
    }
    private static void applyOnWorker(final GameSaveStore.Snapshot snapshot) throws Exception {
        final AtomicReference<Throwable> failure = new AtomicReference<Throwable>();
        Thread worker = new Thread(new Runnable() {
            public void run() {
                try { GameSaveStore.applySnapshot(snapshot); }
                catch (Throwable cause) { failure.set(cause); }
            }
        }, "isolated-save-probe-writer");
        worker.start();
        worker.join(5000L);
        check(!worker.isAlive(), "Snapshot worker did not finish");
        if (failure.get() != null) throw new AssertionError("Snapshot worker failed", failure.get());
    }
    private static void persisted(IsolatedContext context, String name, String expected) throws Exception {
        SQLiteDatabase db = raw(context);
        try {
            Cursor cursor = db.rawQuery("SELECT payload,deleted FROM save_records WHERE store_name=? AND record_id=1", new String[]{name});
            try {
                check(cursor.moveToFirst() && cursor.getInt(1) == 0
                        && Arrays.equals(cursor.getBlob(0), data(expected)), "Unexpected durable record: " + name);
            } finally { cursor.close(); }
        } finally { db.close(); }
    }
    /** This command uses only its own fresh fixture and real Android SQLite. */
    private static void asyncSnapshots(final IsolatedContext context) throws Exception {
        fresh(context);
        GameSaveStore.initialize(context);
        GameSaveStore.Snapshot initial = capture("initial", true);
        check(initial.isGameplaySnapshot() && initial.isWholeFarm() && initial.recordCount() == 5
                && initial.byteCount() > 0, "Capture metadata is incorrect");
        check(GameSaveStore.generation() == 0, "Capture advanced durable generation");
        SQLiteDatabase db = raw(context);
        try { check(scalar(db, "SELECT COUNT(*) FROM save_records") == 0, "Capture performed SQLite writes"); }
        finally { db.close(); }
        applyOnWorker(initial);
        for (String record : RECORDS) persisted(context, record, record + "-initial");

        final GameSaveStore.Snapshot[] incidental = new GameSaveStore.Snapshot[1];
        GameSaveStore.setWriteDispatcher(new GameSaveStore.WriteDispatcher() {
            public void changed(GameSaveStore.Snapshot mutation) {
                incidental[0] = GameSaveStore.coalesceSnapshots(incidental[0], mutation);
            }
        });
        GameSaveStore.beginCapture(true);
        byte[] olderHeader = data("RMSHeader-before-sdk-callback");
        GameSaveStore.write("RMSHeader", olderHeader, 0, olderHeader.length);
        final AtomicReference<Throwable> callbackFailure = new AtomicReference<Throwable>();
        Thread callback = new Thread(new Runnable() {
            public void run() {
                try {
                    byte[] newerHeader = data("RMSHeader-from-newer-sdk-callback");
                    GameSaveStore.write("RMSHeader", newerHeader, 0, newerHeader.length);
                } catch (Throwable cause) { callbackFailure.set(cause); }
            }
        }, "isolated-save-probe-sdk-callback");
        callback.start();
        callback.join(5000L);
        check(!callback.isAlive() && callbackFailure.get() == null, "Incidental callback did not finish");
        for (String name : RECORDS) if (!name.equals("RMSHeader")) {
            byte[] value = data(name + "-before-sdk-callback");
            GameSaveStore.write(name, value, 0, value.length);
        }
        GameSaveStore.Snapshot olderCapture = GameSaveStore.finishCapture();
        equal("RMSHeader", "RMSHeader-from-newer-sdk-callback");
        check(incidental[0] != null, "Concurrent callback was not queued");
        // Queue order can differ from byte capture order. Both coalescing and
        // a delayed older commit must retain the newer per-record value.
        GameSaveStore.Snapshot reversed = GameSaveStore.coalesceSnapshots(incidental[0], olderCapture);
        applyOnWorker(incidental[0]);
        applyOnWorker(olderCapture);
        persisted(context, "RMSHeader", "RMSHeader-from-newer-sdk-callback");
        long beforeReversed = GameSaveStore.generation();
        applyOnWorker(reversed);
        check(GameSaveStore.generation() == beforeReversed, "Reverse coalescing replayed obsolete bytes");
        incidental[0] = null;
        final CountDownLatch locked = new CountDownLatch(1);
        final CountDownLatch release = new CountDownLatch(1);
        final AtomicReference<Throwable> holderFailure = new AtomicReference<Throwable>();
        Thread holder = new Thread(new Runnable() {
            public void run() {
                try {
                    GameSaveStore.beginSave();
                    locked.countDown();
                    try { check(release.await(5, TimeUnit.SECONDS), "Capture waited on SQLite writer lock"); }
                    finally { GameSaveStore.abortSave(); }
                } catch (Throwable cause) { holderFailure.set(cause); }
            }
        }, "isolated-save-probe-lock-holder");
        holder.start();
        check(locked.await(5, TimeUnit.SECONDS), "Writer did not acquire its transaction lock");
        GameSaveStore.Snapshot whileLocked;
        try {
            // This completes before releasing the SQLite lock. The timeout in
            // the holder exposes any accidental capture/read dependency on it.
            whileLocked = capture("while-locked", true);
            equal("RMSHeader", "RMSHeader-while-locked");
        } finally { release.countDown(); }
        holder.join(5000L);
        check(!holder.isAlive() && holderFailure.get() == null, "Capture blocked behind SQLite");
        GameSaveStore.Snapshot newer = capture("newer", true);
        applyOnWorker(whileLocked);
        equal("RMSHeader", "RMSHeader-newer");
        persisted(context, "RMSHeader", "RMSHeader-while-locked");
        // A writer reopen/retry must preserve accepted, newer logical bytes.
        GameSaveStore.reopen();
        equal("RMSHeader", "RMSHeader-newer");
        applyOnWorker(newer);

        GameSaveStore.Snapshot partial = capture("partial", false);
        applyOnWorker(partial);
        persisted(context, "save_gameplay", "save_gameplay-partial");
        persisted(context, "save_farm0", "save_farm0-newer");
        persisted(context, "entity_pose_v1", "entity_pose_v1-newer");
        GameSaveStore.Snapshot full = capture("full-coalesced", true);
        GameSaveStore.Snapshot visitor = capture("visitor-coalesced", false);
        GameSaveStore.Snapshot combined = GameSaveStore.coalesceSnapshots(full, visitor);
        check(combined.isWholeFarm() && combined.isGameplaySnapshot(), "Coalescing lost the pending full farm");
        applyOnWorker(combined);
        persisted(context, "RMSHeader", "RMSHeader-visitor-coalesced");
        persisted(context, "save_farm0", "save_farm0-full-coalesced");
        persisted(context, "entity_pose_v1", "entity_pose_v1-full-coalesced");

        long generation = GameSaveStore.generation();
        GameSaveStore.beginCapture(true);
        try {
            for (String name : RECORDS) {
                byte[] unchanged = GameSaveStore.read(name);
                GameSaveStore.write(name, unchanged, 0, unchanged.length);
            }
            applyOnWorker(GameSaveStore.finishCapture());
        } finally { GameSaveStore.abortCapture(); }
        check(GameSaveStore.generation() == generation, "Identical records caused another durable generation");
        db = raw(context);
        try { check(scalar(db, "SELECT changed_records FROM save_commits ORDER BY generation DESC LIMIT 1") == 5,
                "Full/partial coalescing wrote an unexpected number of records"); }
        finally { db.close(); }

        GameSaveStore.beginCapture(true);
        byte[] incomplete = data("must-never-publish");
        GameSaveStore.write("RMSHeader", incomplete, 0, incomplete.length);
        boolean rejected = false;
        try { GameSaveStore.finishCapture(); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.generation() == generation, "Incomplete capture was accepted");
        equal("RMSHeader", "RMSHeader-visitor-coalesced");
        GameSaveStore.beginCapture(true);
        GameSaveStore.write("RMSHeader", incomplete, 0, incomplete.length);
        GameSaveStore.abortCapture();
        equal("RMSHeader", "RMSHeader-visitor-coalesced");

        GameSaveStore.beginAuxCapture();
        byte[] header = data("RMSHeader-explicit-settings");
        GameSaveStore.write("RMSHeader", header, 0, header.length);
        GameSaveStore.Snapshot settings = GameSaveStore.finishCapture();
        check(!settings.isGameplaySnapshot() && settings.recordCount() == 1, "Settings became a complete game snapshot");
        applyOnWorker(settings);
        persisted(context, "RMSHeader", "RMSHeader-explicit-settings");
        db = raw(context);
        try {
            check(scalar(db, "SELECT complete_snapshot_generation FROM save_slots") == generation,
                    "Settings changed the complete game snapshot generation");
            check(scalar(db, "SELECT changed_records FROM save_commits ORDER BY generation DESC LIMIT 1") == 1,
                    "Settings rewrote unchanged game records");
        } finally { db.close(); }

        generation = GameSaveStore.generation();
        byte[] settingsBytes = data("queued-incidental-setting");
        GameSaveStore.write("ProbeAsyncSetting", settingsBytes, 0, settingsBytes.length);
        Arrays.fill(settingsBytes, (byte) 0);
        equal("ProbeAsyncSetting", "queued-incidental-setting");
        byte[] callerCopy = GameSaveStore.read("ProbeAsyncSetting");
        Arrays.fill(callerCopy, (byte) 0);
        equal("ProbeAsyncSetting", "queued-incidental-setting");
        check(incidental[0] != null && !incidental[0].isGameplaySnapshot()
                && GameSaveStore.generation() == generation, "Incidental write bypassed the writer queue");
        applyOnWorker(incidental[0]);
        incidental[0] = null;
        GameSaveStore.delete("ProbeAsyncSetting");
        check(GameSaveStore.read("ProbeAsyncSetting") == null && incidental[0] != null,
                "Incidental deletion was not queued as an absolute tombstone");
        applyOnWorker(incidental[0]);
        incidental[0] = null;

        GameSaveStore.beginLoading();
        check(!GameSaveStore.isCaptureAvailable(), "Capture was allowed during decoding");
        GameSaveStore.write("RMSHeader", incomplete, 0, incomplete.length);
        GameSaveStore.delete("save_farm0");
        GameSaveStore.finishLoading();
        check(incidental[0] == null, "Default/repair writes from decoding were queued for later");
        equal("RMSHeader", "RMSHeader-explicit-settings");
        persisted(context, "save_farm0", "save_farm0-full-coalesced");

        db = raw(context);
        try { db.execSQL("CREATE TRIGGER probe_async_write_failure BEFORE INSERT ON save_records WHEN NEW.store_name='save_gameplay' BEGIN SELECT RAISE(ABORT,'Owned async probe write failure'); END"); }
        finally { db.close(); }
        generation = GameSaveStore.generation();
        GameSaveStore.Snapshot failed = capture("retry-full", true);
        rejected = false;
        try { applyOnWorker(failed); }
        catch (AssertionError expected) { rejected = expected.getCause() instanceof GameSaveStore.Failure; }
        check(rejected && !GameSaveStore.isReady() && GameSaveStore.isCaptureAvailable()
                && GameSaveStore.generation() == generation, "Failed writer lost retryable capture state");
        persisted(context, "RMSHeader", "RMSHeader-explicit-settings");
        persisted(context, "save_farm0", "save_farm0-full-coalesced");
        GameSaveStore.Snapshot afterFailure = capture("retry-newer-partial", false);
        db = raw(context);
        try { db.execSQL("DROP TRIGGER probe_async_write_failure"); }
        finally { db.close(); }
        applyOnWorker(GameSaveStore.coalesceSnapshots(failed, afterFailure));
        check(GameSaveStore.generation() == generation + 1, "Retry was duplicated or failed to commit");
        persisted(context, "save_gameplay", "save_gameplay-retry-newer-partial");
        persisted(context, "save_farm0", "save_farm0-retry-full");
        persisted(context, "entity_pose_v1", "entity_pose_v1-retry-full");

        byte[] languageHeader = new byte[35];
        for (int index = 0; index < languageHeader.length; index++) languageHeader[index] = (byte) (index + 10);
        languageHeader[4] = 1;
        GameSaveStore.beginAuxCapture();
        GameSaveStore.write("RMSHeader", languageHeader, 0, languageHeader.length);
        applyOnWorker(GameSaveStore.finishCapture());
        GameSaveStore.beginLoading();
        generation = GameSaveStore.generation();
        GameSaveStore.Snapshot language = GameSaveStore.captureLanguageSetting(7);
        check(!language.isGameplaySnapshot() && language.recordCount() == 1
                && GameSaveStore.generation() == generation, "Language setting was not detached");
        GameSaveStore.write("RMSHeader", incomplete, 0, incomplete.length);
        applyOnWorker(language);
        languageHeader[4] = 7;
        check(Arrays.equals(GameSaveStore.read("RMSHeader"), languageHeader),
                "Language save changed another header byte or was suppressed by loading");
        check(GameSaveStore.isLoading() && GameSaveStore.generation() == generation + 1,
                "Language setting incorrectly released the world loading barrier");
        GameSaveStore.Snapshot rollback = GameSaveStore.captureLanguageSetting(1);
        applyOnWorker(rollback);
        languageHeader[4] = 1;
        check(Arrays.equals(GameSaveStore.read("RMSHeader"), languageHeader), "Language rollback did not preserve the header");
        GameSaveStore.finishLoading();
        GameSaveStore.setWriteDispatcher(null);
    }
    private static void verifyBaseline() throws Exception {
        equal("RMSHeader", "RMSHeader-v2");
        equal("save_gameplay", "save_gameplay-v2");
        equal("save_farm0", "save_farm0-v1");
        equal("pending_rewards_v1", "pending_rewards_v1-v2");
        equal("entity_pose_v1", "entity_pose_v1-v1");
        check(GameSaveStore.generation() == 2L, "Unexpected baseline generation");
    }
    private static void seed(IsolatedContext context) throws Exception {
        fresh(context);
        GameSaveStore.initialize(context);
        check(GameSaveStore.isReady() && !GameSaveStore.hasGameplaySave(), "New database is not empty and ready");
        java.lang.reflect.Field liveDatabaseField = GameSaveStore.class.getDeclaredField("database");
        liveDatabaseField.setAccessible(true);
        SQLiteDatabase liveDatabase = (SQLiteDatabase) liveDatabaseField.get(null);
        // A WAL read query can acquire a separate read-only pool connection.
        // Pin the primary writer so this assertion checks the connection which
        // actually commits the production snapshots.
        liveDatabase.beginTransaction();
        try {
            Cursor policy = liveDatabase.rawQuery("PRAGMA journal_mode", null);
            try { check(policy.moveToFirst() && "wal".equalsIgnoreCase(policy.getString(0)), "Production connection is not using WAL"); }
            finally { policy.close(); }
            long synchronous = scalar(liveDatabase, "PRAGMA synchronous");
            check(synchronous == 2L, "Production writer synchronous mode is " + synchronous + ", expected FULL (2)");
        } finally { liveDatabase.endTransaction(); }
        SQLiteDatabase db = raw(context);
        try {
            check(db.getVersion() == 4, "Schema was not migrated to version 4");
            check(scalar(db, "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name IN ('save_slots','save_records','save_commits','legacy_imports')") == 4, "Required tables missing");
            check(scalar(db, "SELECT COUNT(*) FROM sqlite_master WHERE type='index' AND name='save_records_generation'") == 1, "Required index missing");
        } finally { db.close(); }
        snapshot("v1", true);
        check(GameSaveStore.status() == GameSaveStore.STATUS_SAVED, "Commit did not report saved");
        snapshot("v2", false);
        verifyBaseline();
        GameSaveStore.beginLoading();
        byte[] defaults = data("must-not-overwrite-existing-progress");
        GameSaveStore.write("RMSHeader", defaults, 0, defaults.length);
        GameSaveStore.write("save_gameplay", defaults, 0, defaults.length);
        GameSaveStore.delete("save_farm0");
        GameSaveStore.finishLoading();
        verifyBaseline();
        GameSaveStore.beginSave();
        GameSaveStore.beginSave();
        byte[] cancelled = data("aborted");
        GameSaveStore.write("RMSHeader", cancelled, 0, cancelled.length);
        GameSaveStore.abortSave();
        boolean rejected = false;
        try { GameSaveStore.commitSave(); } catch (RuntimeException expected) { rejected = true; }
        check(rejected, "Poisoned nested transaction unexpectedly committed");
        GameSaveStore.reopen();
        verifyBaseline();
        GameSaveStore.beginSave();
        GameSaveStore.write("RMSHeader", cancelled, 0, cancelled.length);
        rejected = false;
        try { GameSaveStore.write("save_gameplay", null, 0, 0); }
        catch (RuntimeException expected) { rejected = true; }
        check(rejected, "Invalid write was accepted");
        rejected = false;
        try { GameSaveStore.commitSave(); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED, "Caught write failure falsely reported saved");
        GameSaveStore.reopen();
        verifyBaseline();
        GameSaveStore.close();
        GameSaveStore.initialize(context);
        verifyBaseline();
    }
    private static void crashUncommitted() throws Exception {
        verifyBaseline();
        GameSaveStore.beginSave();
        for (String record : RECORDS) {
            byte[] value = data(record + "-uncommitted");
            GameSaveStore.write(record, value, 0, value.length);
        }
        System.out.println("{\"command\":\"crash-uncommitted\",\"transaction_written\":true,\"committed\":false,\"expected_exit\":17}");
        System.out.flush();
        Runtime.getRuntime().halt(17);
    }
    private static void crashCommitted() throws Exception {
        verifyBaseline();
        snapshot("v3", true);
        check(GameSaveStore.status() == GameSaveStore.STATUS_SAVED, "Acknowledged snapshot is not saved");
        System.out.println("{\"command\":\"crash-committed\",\"committed\":true,\"expected_exit\":18}");
        System.out.flush();
        Runtime.getRuntime().halt(18);
    }
    private static void legacyRecord(File directory, String name, String suffix, String value) throws Exception {
        byte[] payload = data(value);
        DataOutputStream output = new DataOutputStream(new FileOutputStream(new File(directory, name + "_1" + suffix)));
        try { output.writeByte(1); output.writeInt(payload.length); output.write(payload); }
        finally { output.close(); }
    }
    private static void legacyHeader(File directory, String name) throws Exception {
        FileOutputStream output = new FileOutputStream(new File(directory, name));
        try { output.write(1); } finally { output.close(); }
    }
    private static void legacyCrc(File directory, String name, int generation, String value, boolean correct) throws Exception {
        CRC32 crc = new CRC32(); crc.update(data(value));
        DataOutputStream output = new DataOutputStream(new FileOutputStream(new File(directory, name + "_1_CRC" + generation)));
        try { output.writeLong(crc.getValue() + (correct ? 0 : 1)); } finally { output.close(); }
    }
    private static Map<String, String> legacyFingerprint(File directory) throws Exception {
        Map<String, String> result = new TreeMap<String, String>();
        File[] files = directory.listFiles();
        check(files != null, "Cannot inspect isolated legacy files");
        for (File file : files) if (file.isFile()) {
            result.put(file.getName(), Arrays.toString(fileDigest(file)));
        }
        return result;
    }
    private static byte[] fileDigest(File file) throws Exception {
        FileInputStream input = new FileInputStream(file);
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        try { byte[] buffer = new byte[4096]; int read; while ((read = input.read(buffer)) != -1) digest.update(buffer, 0, read); }
        finally { input.close(); }
        return digest.digest();
    }
    private static void legacy(IsolatedContext context) throws Exception {
        fresh(context);
        File files = context.getFilesDir();
        for (String name : new String[]{"RMSHeader", "save_gameplay", "save_farm0", "RMS_ProbeCache"}) {
            legacyHeader(files, name); legacyRecord(files, name, "", name + "-legacy");
        }
        legacyRecord(files, "save_gameplay", "_saved2", "gameplay-valid-backup");
        legacyCrc(files, "save_gameplay", 2, "gameplay-valid-backup", true);
        legacyRecord(files, "save_gameplay", "_saved3", "gameplay-invalid-backup");
        legacyCrc(files, "save_gameplay", 3, "gameplay-invalid-backup", false);
        Map<String, String> before = legacyFingerprint(files);
        GameSaveStore.initialize(context);
        equal("save_gameplay", "gameplay-valid-backup");
        equal("save_farm0", "save_farm0-legacy");
        check(before.equals(legacyFingerprint(files)), "Import changed or removed original RMS files");
        GameSaveStore.delete("RMS_ProbeCache");
        check(GameSaveStore.read("RMS_ProbeCache") == null, "Deleted record remains visible");
        GameSaveStore.reopen();
        check(GameSaveStore.read("RMS_ProbeCache") == null, "Deleted record resurrected from legacy files");
        check(before.equals(legacyFingerprint(files)), "Reopen changed original RMS files");
    }
    private static void migrateVersion(IsolatedContext context, int originalVersion) throws Exception {
        fresh(context);
        SQLiteDatabase db = context.openOrCreateDatabase(GameSaveStore.DATABASE_NAME, 0, null);
        try {
            db.execSQL("CREATE TABLE save_slots(slot_id TEXT PRIMARY KEY NOT NULL,generation INTEGER NOT NULL DEFAULT 0,created_at INTEGER NOT NULL,updated_at INTEGER NOT NULL)");
            db.execSQL("CREATE TABLE save_records(slot_id TEXT NOT NULL,store_name TEXT NOT NULL,record_id INTEGER NOT NULL,payload BLOB,checksum BLOB,deleted INTEGER NOT NULL DEFAULT 0,generation INTEGER NOT NULL,PRIMARY KEY(slot_id,store_name,record_id),FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))");
            db.execSQL("CREATE TABLE save_commits(slot_id TEXT NOT NULL,generation INTEGER NOT NULL,committed_at INTEGER NOT NULL,changed_records INTEGER NOT NULL,is_snapshot INTEGER NOT NULL,PRIMARY KEY(slot_id,generation),FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))");
            db.execSQL("CREATE TABLE legacy_imports(slot_id TEXT NOT NULL,store_name TEXT NOT NULL,imported_at INTEGER NOT NULL,PRIMARY KEY(slot_id,store_name),FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))");
            db.execSQL("CREATE INDEX save_records_generation ON save_records(slot_id,generation)");
            db.execSQL("INSERT INTO save_slots VALUES(?,7,1,2)", new Object[]{SLOT});
            for (String name : new String[]{"RMSHeader", "save_gameplay", "save_farm0"}) {
                byte[] payload = data(name + "-migration");
                db.execSQL("INSERT INTO save_records VALUES(?,?,1,?,?,0,7)", new Object[]{SLOT, name, payload, digest(payload)});
            }
            db.execSQL("INSERT INTO save_commits VALUES(?,7,2,3,1)", new Object[]{SLOT});
            if (originalVersion == 3) {
                db.execSQL("ALTER TABLE save_slots ADD COLUMN complete_snapshot_generation INTEGER NOT NULL DEFAULT 0");
                db.execSQL("UPDATE save_slots SET complete_snapshot_generation=7");
                db.execSQL("ALTER TABLE save_slots ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1))");
                db.execSQL("ALTER TABLE save_commits ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1))");
                db.execSQL("UPDATE save_slots SET snapshot_format=1");
                db.execSQL("UPDATE save_commits SET snapshot_format=1");
                byte[] pending = data("pending_rewards_v1-migration");
                db.execSQL("INSERT INTO save_records VALUES(?,?,1,?,?,0,7)", new Object[]{SLOT, "pending_rewards_v1", pending, digest(pending)});
            }
            db.setVersion(originalVersion);
        } finally { db.close(); }
        GameSaveStore.initialize(context);
        for (String name : new String[]{"RMSHeader", "save_gameplay", "save_farm0"}) equal(name, name + "-migration");
        if (originalVersion == 3) equal("pending_rewards_v1", "pending_rewards_v1-migration");
        check(GameSaveStore.generation() == 7L, "Migration reset the saved generation");
        GameSaveStore.close();
        db = raw(context);
        try {
            check(db.getVersion() == 4, "Migration did not reach version4");
            check(scalar(db, "SELECT pose_snapshot FROM save_slots") == 0, "Migration falsely required legacy pose data");
            if (originalVersion == 3) {
                check(scalar(db, "SELECT snapshot_format FROM save_slots") == 1, "Migration reset reward snapshot metadata");
                boolean rejected = false;
                try { db.execSQL("UPDATE save_slots SET snapshot_format=2"); }
                catch (RuntimeException expected) { rejected = true; }
                check(rejected, "Migration unexpectedly replaced the schema3 snapshot format constraint");
            }
        }
        finally { db.close(); }
        GameSaveStore.initialize(context);
        equal("save_gameplay", "save_gameplay-migration");
    }
    private static void legacyHeaderless(IsolatedContext context) throws Exception {
        fresh(context);
        File files = context.getFilesDir();
        legacyRecord(files, "ProbeSettingsWithoutHeader", "", "headerless-settings");
        legacyRecord(files, "ProbeSettingsEmptyHeader", "", "empty-header-settings");
        check(new File(files, "ProbeSettingsEmptyHeader").createNewFile(), "Cannot create empty registry fixture");
        Map<String, String> before = legacyFingerprint(files);
        GameSaveStore.initialize(context);
        equal("ProbeSettingsWithoutHeader", "headerless-settings");
        equal("ProbeSettingsEmptyHeader", "empty-header-settings");
        GameSaveStore.reopen();
        equal("ProbeSettingsWithoutHeader", "headerless-settings");
        check(before.equals(legacyFingerprint(files)), "On-demand import changed original registry files");
    }
    private static void corruption(IsolatedContext context) throws Exception {
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        try { db.execSQL("UPDATE save_records SET payload=? WHERE store_name='save_gameplay'", new Object[]{data("corrupt-without-updating-checksum")}); }
        finally { db.close(); }
        boolean rejected = false;
        try { GameSaveStore.initialize(context); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED, "Corrupt database was accepted as a new game");
        GameSaveStore.close();
        db = raw(context);
        try { check(scalar(db, "SELECT COUNT(*) FROM save_records") == RECORDS.length, "Corruption recovery reset records"); }
        finally { db.close(); }
    }
    private static void missingRecords(IsolatedContext context) throws Exception {
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        try { db.execSQL("DROP TABLE save_records"); } finally { db.close(); }
        boolean rejected = false;
        try { GameSaveStore.initialize(context); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED, "Missing records were accepted as a new game");
        GameSaveStore.close();
        db = raw(context);
        try { check(scalar(db, "SELECT generation FROM save_slots") == 2, "Missing-table recovery reset the slot"); }
        finally { db.close(); }
    }
    private static void repair(IsolatedContext context) throws Exception {
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        try { db.execSQL("DROP TABLE save_commits"); db.execSQL("DROP INDEX save_records_generation"); }
        finally { db.close(); }
        GameSaveStore.initialize(context);
        verifyBaseline();
        db = raw(context);
        try {
            check(scalar(db, "SELECT COUNT(*) FROM sqlite_master WHERE name='save_commits'") == 1, "Missing auxiliary table not recreated");
            check(scalar(db, "SELECT COUNT(*) FROM sqlite_master WHERE name='save_records_generation'") == 1, "Missing index not recreated");
        } finally { db.close(); }
    }
    private static void missingAllTables(IsolatedContext context) throws Exception {
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        int version;
        try {
            version = db.getVersion();
            for (String table : new String[]{"legacy_imports", "save_commits", "save_records", "save_slots"}) {
                db.execSQL("DROP TABLE " + table);
            }
        } finally { db.close(); }
        boolean rejected = false;
        try { GameSaveStore.initialize(context); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED,
                "An existing database with all save tables missing was treated as a new player");
        GameSaveStore.close();
        db = raw(context);
        try {
            check(db.getVersion() == version, "Missing-schema handling reset the database version");
            check(scalar(db, "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name IN ('save_slots','save_records','save_commits','legacy_imports')") == 0,
                    "Missing-schema handling recreated empty save tables");
        } finally { db.close(); }
    }
    private static void missingPose(IsolatedContext context) throws Exception {
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        try { db.execSQL("DELETE FROM save_records WHERE store_name='entity_pose_v1'"); }
        finally { db.close(); }
        boolean rejected = false;
        try { GameSaveStore.initialize(context); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED,
                "Missing acknowledged entity poses were silently accepted");
        GameSaveStore.close();
        db = raw(context);
        try {
            check(scalar(db, "SELECT generation FROM save_slots") == 2, "Missing pose handling reset the slot");
            check(scalar(db, "SELECT COUNT(*) FROM save_records") == RECORDS.length - 1, "Missing pose handling overwrote other records");
        } finally { db.close(); }
    }
    private static void legacyAfterSqlDamage(IsolatedContext context, boolean criticalOnly) throws Exception {
        fresh(context);
        File files = context.getFilesDir();
        for (String name : new String[]{"RMSHeader", "save_gameplay", "save_farm0"}) {
            legacyHeader(files, name);
            legacyRecord(files, name, "", name + "-obsolete-legacy");
        }
        Map<String, String> before = legacyFingerprint(files);
        GameSaveStore.initialize(context);
        snapshot("new-sql-progress", true);
        long generation = GameSaveStore.generation();
        check(generation > 0, "New SQL snapshot was not committed");
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        try {
            if (criticalOnly) {
                db.execSQL("DELETE FROM save_records WHERE store_name='save_gameplay'");
                db.execSQL("DELETE FROM legacy_imports WHERE store_name='save_gameplay'");
            } else {
                db.execSQL("DROP TABLE save_records");
                db.execSQL("DROP TABLE legacy_imports");
            }
        } finally { db.close(); }
        boolean rejected = false;
        try { GameSaveStore.initialize(context); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED,
                "Lost SQL progress was replaced with obsolete RMS data");
        GameSaveStore.close();
        db = raw(context);
        try {
            check(scalar(db, "SELECT generation FROM save_slots") == generation,
                    "Damaged SQL snapshot generation was reset");
            if (criticalOnly) {
                check(scalar(db, "SELECT COUNT(*) FROM save_records WHERE store_name='save_gameplay'") == 0,
                        "Missing SQL gameplay was silently imported from stale legacy files");
            } else {
                check(scalar(db, "SELECT COUNT(*) FROM sqlite_master WHERE type='table' AND name IN ('save_records','legacy_imports')") == 0,
                        "Lost progress tables were recreated before reporting failure");
            }
        } finally { db.close(); }
        check(before.equals(legacyFingerprint(files)), "Damaged SQL recovery modified original legacy files");
    }
    private static void sqliteWriteError(IsolatedContext context) throws Exception {
        GameSaveStore.close();
        SQLiteDatabase db = raw(context);
        try {
            // The production writer uses INSERT OR REPLACE, so an UPDATE
            // trigger would never exercise the actual failed write path.
            db.execSQL("CREATE TRIGGER probe_gameplay_write_failure BEFORE INSERT ON save_records WHEN NEW.store_name='save_gameplay' BEGIN SELECT RAISE(ABORT,'Owned probe write failure'); END");
        } finally { db.close(); }
        GameSaveStore.initialize(context);
        verifyBaseline();
        GameSaveStore.beginSave();
        byte[] header = data("RMSHeader-must-roll-back-after-trigger");
        GameSaveStore.write("RMSHeader", header, 0, header.length);
        byte[] gameplay = data("save_gameplay-triggered-error");
        boolean writeRejected = false;
        try { GameSaveStore.write("save_gameplay", gameplay, 0, gameplay.length); }
        catch (RuntimeException expected) { writeRejected = true; }
        check(writeRejected, "Owned SQLite trigger did not reject the actual INSERT");
        boolean commitRejected = false;
        try { GameSaveStore.commitSave(); } catch (RuntimeException expected) { commitRejected = true; }
        check(commitRejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED,
                "A caught SQLite write failure was committed or reported saved");
        GameSaveStore.reopen();
        verifyBaseline();
        GameSaveStore.close();
        db = raw(context);
        try { db.execSQL("DROP TRIGGER probe_gameplay_write_failure"); }
        finally { db.close(); }
        GameSaveStore.initialize(context);
        snapshot("v3", true);
        for (String name : RECORDS) equal(name, name + "-v3");
        check(GameSaveStore.generation() == 3L && GameSaveStore.status() == GameSaveStore.STATUS_SAVED,
                "Saving did not recover after the SQLite write fault was removed");
    }
    private static void corruptFile(IsolatedContext context) throws Exception {
        // Closing the only helper connection checkpoints its WAL before the
        // physical fault. The following operations touch this fixture only.
        GameSaveStore.close();
        File file = context.getDatabasePath(GameSaveStore.DATABASE_NAME);
        check(file.isFile() && file.length() >= 100, "No isolated SQLite file to corrupt");
        RandomAccessFile output = new RandomAccessFile(file, "rw");
        try {
            output.seek(0);
            output.write(new byte[]{'O', 'W', 'N', 'E', 'D', 'B', 'A', 'D'});
            output.getFD().sync();
        } finally { output.close(); }
        long damagedLength = file.length();
        byte[] damagedDigest = fileDigest(file);
        boolean rejected = false;
        try { GameSaveStore.initialize(context); } catch (RuntimeException expected) { rejected = true; }
        check(rejected && GameSaveStore.status() == GameSaveStore.STATUS_FAILED,
                "A physically corrupt SQLite file was accepted as a new player");
        GameSaveStore.close();
        // Do not open/query SQLite again: that could itself run a corruption
        // handler. Byte equality proves failed startup retained the damaged file.
        check(file.isFile() && file.length() == damagedLength,
                "Corruption handling deleted or replaced the progress database");
        check(Arrays.equals(damagedDigest, fileDigest(file)),
                "Corruption handling rewrote the progress database");
    }
    public static void main(String[] args) {
        try { run(args); }
        catch (Throwable failure) {
            System.err.println("{\"passed\":false,\"probe_failure\":true}");
            failure.printStackTrace(System.err);
            System.err.flush();
            System.exit(1);
        }
    }
    private static void run(String[] args) throws Exception {
        check(args.length == 2, "Usage: GameSaveStoreProbe <command> <isolated-fixture-name>");
        check(args[1].matches("[A-Za-z0-9_-]{1,48}"), "Unsafe probe fixture name");
        if (android.os.Looper.myLooper() == null) android.os.Looper.prepareMainLooper();
        // app_process is outside ActivityManager's registered application list,
        // so framework compatibility defaults cannot read Settings providers.
        // Only initialize those defaults; the production helper still enables
        // real WAL and FULL synchronous writes, asserted on its live connection.
        Class<?> walFlags = Class.forName("android.database.sqlite.SQLiteCompatibilityWalFlags");
        walFlags.getDeclaredMethod("init", String.class).invoke(null, "compatibility_wal_supported=false");
        Class<?> threadClass = Class.forName("android.app.ActivityThread");
        Method systemMain = threadClass.getDeclaredMethod("systemMain");
        Object thread = systemMain.invoke(null);
        Context system = (Context) threadClass.getDeclaredMethod("getSystemContext").invoke(thread);
        IsolatedContext context = new IsolatedContext(system.createPackageContext(PACKAGE, 0), args[1]);
        String command = args[0];
        if (command.equals("seed")) seed(context);
        else if (command.equals("async-snapshots")) asyncSnapshots(context);
        else if (command.equals("legacy")) legacy(context);
        else if (command.equals("legacy-no-header")) legacyHeaderless(context);
        else if (command.equals("migrate-v1")) migrateVersion(context, 1);
        else if (command.equals("migrate-v3")) migrateVersion(context, 3);
        else if (command.equals("missing-records-and-imports")) legacyAfterSqlDamage(context, false);
        else if (command.equals("missing-critical-legacy")) legacyAfterSqlDamage(context, true);
        else {
            check(context.getDatabasePath(GameSaveStore.DATABASE_NAME).isFile(), "Seed this isolated fixture first");
            GameSaveStore.initialize(context);
            if (command.equals("verify")) verifyBaseline();
            else if (command.equals("crash-uncommitted")) crashUncommitted();
            else if (command.equals("crash-committed")) crashCommitted();
            else if (command.equals("verify-committed")) {
                for (String name : RECORDS) equal(name, name + "-v3");
                check(GameSaveStore.generation() == 3L, "Committed snapshot generation did not persist");
            } else if (command.equals("corrupt-read")) corruption(context);
            else if (command.equals("missing-records")) missingRecords(context);
            else if (command.equals("missing-all-tables")) missingAllTables(context);
            else if (command.equals("missing-pose")) missingPose(context);
            else if (command.equals("sqlite-write-error")) sqliteWriteError(context);
            else if (command.equals("corrupt-file")) corruptFile(context);
            else if (command.equals("repair")) repair(context);
            else throw new AssertionError("Unknown probe command");
        }
        GameSaveStore.close();
        System.out.println("{\"command\":\"" + command + "\",\"passed\":true,\"isolated\":true,\"sqlite_runtime\":\"Android\"}");
        System.out.flush();
        System.exit(0);
    }
}
