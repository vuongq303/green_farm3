package com.gameloft.android.ANMP.GloftGF2F.S800x480;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/** Durable storage for the game's existing encoded RMS records.
 * Serialization, device binding, entity IDs and timers remain in the game.
 * This class never interprets a failed read as a missing/new save.
 */
public final class GameSaveStore {
    public static final int STATUS_IDLE = 0;
    public static final int STATUS_SAVING = 1;
    public static final int STATUS_SAVED = 2;
    public static final int STATUS_FAILED = 3;
    public static final String DATABASE_NAME = "greenfarm-progress.db";
    private static final int VERSION = 4;
    private static final int MAX_RECORD_BYTES = 64 * 1024 * 1024;
    private static final String SLOT = "device-local";
    private static final ReentrantLock LOCK = new ReentrantLock(true);
    private static final Pattern LEGACY_FILE = Pattern.compile("^(.+)_1(?:_saved([0-9]+)|_CRC([0-9]+))?$");
    private static final Set<String> TOUCHED = new HashSet<String>();
    private static final Map<String, byte[]> RECORD_CHANGES = new HashMap<String, byte[]>();
    private static final Map<String, Long> APPLIED_VERSIONS = new HashMap<String, Long>();
    private static final ThreadLocal<Capture> CAPTURE = new ThreadLocal<Capture>();
    private static final ThreadLocal<Boolean> APPLYING_SNAPSHOT = new ThreadLocal<Boolean>();
    private static final Object CACHE_LOCK = new Object();
    // The values are private, immutable byte arrays. Publishing this map never
    // requires the game thread to wait for the SQLite writer's lock.
    private static volatile Map<String, byte[]> confirmedRecords = Collections.emptyMap();
    private static volatile Map<String, byte[]> logicalRecords = Collections.emptyMap();
    private static Map<String, Long> logicalVersions = Collections.emptyMap();
    private static volatile boolean logicalReady;
    private static volatile WriteDispatcher writeDispatcher;
    private static long acceptedVersion;
    private static Helper helper;
    private static SQLiteDatabase database;
    private static Context context;
    private static int depth;
    private static boolean poisoned;
    private static boolean requireWholeFarm;
    private static int writes;
    private static long nextGeneration;
    private static volatile boolean ready;
    private static volatile boolean loading;
    private static volatile boolean protectExistingSnapshot;
    private static volatile int currentStatus = STATUS_IDLE;
    private static volatile String error = "";
    private static volatile long currentGeneration;

    private GameSaveStore() {}

    public static final class Failure extends RuntimeException {
        Failure(String message, Throwable cause) { super(message, cause); }
    }

    /** Detached absolute record values; safe to retain/retry on one writer. */
    public static final class Snapshot {
        private final Map<String, byte[]> records;
        private final Map<String, Long> versions;
        private final boolean wholeFarm;
        private final boolean gameplaySnapshot;
        private final boolean loadingHeaderOnly;
        private final long bytes;

        private Snapshot(Map<String, byte[]> captured, Map<String, Long> versions,
                boolean wholeFarm, boolean gameplaySnapshot, boolean loadingHeaderOnly) {
            this.records = Collections.unmodifiableMap(new HashMap<String, byte[]>(captured));
            this.versions = Collections.unmodifiableMap(new HashMap<String, Long>(versions));
            this.wholeFarm = wholeFarm;
            this.gameplaySnapshot = gameplaySnapshot;
            this.loadingHeaderOnly = loadingHeaderOnly;
            long size = 0L;
            for (byte[] value : records.values()) if (value != null) size += value.length;
            this.bytes = size;
        }

        public boolean isWholeFarm() { return wholeFarm; }
        public boolean isGameplaySnapshot() { return gameplaySnapshot; }
        public int recordCount() { return records.size(); }
        public long byteCount() { return bytes; }
    }

    private static final class Capture {
        final boolean wholeFarm;
        final boolean gameplaySnapshot;
        final Map<String, byte[]> base;
        final Map<String, byte[]> records = new HashMap<String, byte[]>();
        final Map<String, Long> versions = new HashMap<String, Long>();
        boolean poisoned;
        Capture(boolean wholeFarm, boolean gameplaySnapshot, Map<String, byte[]> base) {
            this.wholeFarm = wholeFarm;
            this.gameplaySnapshot = gameplaySnapshot;
            this.base = base;
        }
    }

    /** Start serialization on the game thread. This performs no SQLite I/O. */
    public static void beginCapture(boolean wholeFarm) {
        beginCapture(wholeFarm, true);
    }

    /** Capture an explicit settings change without declaring a game snapshot. */
    public static void beginAuxCapture() { beginCapture(false, false); }

    /** The language menu is available after header decoding, before world load.
     * Preserve its verified header and change only the explicit language byte.
     */
    public static Snapshot captureLanguageSetting(int language) {
        if (language < 0 || language > 255 || !logicalReady) {
            throw new Failure("Language settings cannot be captured before storage is ready", null);
        }
        synchronized (CACHE_LOCK) {
            byte[] existing = logicalRecords.get("RMSHeader");
            if (existing == null || existing.length < 35) {
                throw new Failure("The saved language header is missing or truncated", null);
            }
            byte[] header = Arrays.copyOf(existing, existing.length);
            header[4] = (byte) language;
            Map<String, byte[]> records = new HashMap<String, byte[]>();
            records.put("RMSHeader", header);
            return acceptSnapshot(records, false, false, true);
        }
    }

    private static void beginCapture(boolean wholeFarm, boolean gameplaySnapshot) {
        if (CAPTURE.get() != null) throw new Failure("A game snapshot is already being captured", null);
        if (loading) throw new Failure("Game progress is still loading", null);
        if (!logicalReady) throw new Failure("Game storage is not ready for a snapshot", null);
        CAPTURE.set(new Capture(wholeFarm, gameplaySnapshot, logicalRecords));
    }

    public static Snapshot finishCapture() {
        Capture capture = CAPTURE.get();
        if (capture == null) throw new Failure("No game snapshot is being captured on this thread", null);
        try {
            if (capture.poisoned) throw new Failure("The captured game snapshot has already failed", null);
            if (capture.gameplaySnapshot) {
                requireCaptured(capture, "RMSHeader");
                requireCaptured(capture, "save_gameplay");
                requireCaptured(capture, "pending_rewards_v1");
                if (capture.wholeFarm) {
                    requireCaptured(capture, "save_farm0");
                    requireCaptured(capture, "entity_pose_v1");
                }
            }
            Snapshot snapshot = new Snapshot(capture.records, capture.versions,
                    capture.wholeFarm, capture.gameplaySnapshot, false);
            publishLogicalSnapshot(snapshot);
            return snapshot;
        } finally { CAPTURE.remove(); }
    }

    public static void abortCapture() { CAPTURE.remove(); }

    private static void requireCaptured(Capture capture, String name) {
        if (!capture.records.containsKey(name) || capture.records.get(name) == null) {
            throw captureFailed(capture, "Game snapshot is incomplete; existing progress was retained");
        }
    }

    /** Keep every pending absolute value; newer records supersede older ones. */
    public static Snapshot coalesceSnapshots(Snapshot older, Snapshot newer) {
        if (older == null) return newer;
        if (newer == null) return older;
        Map<String, byte[]> merged = new HashMap<String, byte[]>(older.records);
        Map<String, Long> versions = new HashMap<String, Long>(older.versions);
        for (Map.Entry<String, byte[]> record : newer.records.entrySet()) {
            Long prior = versions.get(record.getKey());
            Long next = newer.versions.get(record.getKey());
            if (prior == null || next.longValue() >= prior.longValue()) {
                merged.put(record.getKey(), record.getValue());
                versions.put(record.getKey(), next);
            }
        }
        boolean gameplay = older.gameplaySnapshot || newer.gameplaySnapshot;
        Long headerVersion = versions.get("RMSHeader");
        boolean headerOnly = !gameplay && merged.size() == 1 && merged.get("RMSHeader") != null
                && ((older.loadingHeaderOnly && headerVersion.equals(older.versions.get("RMSHeader")))
                || (newer.loadingHeaderOnly && headerVersion.equals(newer.versions.get("RMSHeader"))));
        return new Snapshot(merged, versions, older.wholeFarm || newer.wholeFarm, gameplay, headerOnly);
    }

    /** Called only by the controller's single writer, never during capture. */
    public static void applySnapshot(Snapshot snapshot) {
        if (snapshot == null) throw new Failure("No captured game snapshot to save", null);
        if (CAPTURE.get() != null) throw new Failure("Cannot write SQLite while capturing game progress", null);
        boolean committed = false;
        boolean begun = false;
        APPLYING_SNAPSHOT.set(Boolean.TRUE);
        try {
            beginSave(snapshot.wholeFarm, snapshot.loadingHeaderOnly);
            begun = true;
            for (Map.Entry<String, byte[]> record : snapshot.records.entrySet()) {
                Long applied = APPLIED_VERSIONS.get(record.getKey());
                if (applied != null && applied.longValue() > snapshot.versions.get(record.getKey()).longValue()) {
                    // Enqueue order may race an incidental SDK write. An old
                    // absolute snapshot never supersedes an accepted newer one.
                    TOUCHED.add(record.getKey());
                    continue;
                }
                byte[] value = record.getValue();
                mutate(record.getKey(), value, value == null);
            }
            if (snapshot.gameplaySnapshot) commitSave();
            else {
                try { finishLocked(false); }
                finally { LOCK.unlock(); }
            }
            committed = true;
            // commitSave releases LOCK, so reacquire it before publishing the
            // in-memory durable ordering used by an out-of-order retry.
            LOCK.lock();
            try {
                for (Map.Entry<String, Long> version : snapshot.versions.entrySet()) {
                    Long applied = APPLIED_VERSIONS.get(version.getKey());
                    if (applied == null || applied.longValue() < version.getValue().longValue()) {
                        APPLIED_VERSIONS.put(version.getKey(), version.getValue());
                    }
                }
            } finally { LOCK.unlock(); }
        } finally {
            try { if (!committed && begun) abortSave(); }
            finally { APPLYING_SNAPSHOT.remove(); }
        }
    }

    private static Failure captureFailed(Capture capture, String message) {
        // A malformed capture must not poison a transaction already running on
        // the worker. The controller records this failure and retains dirtiness.
        capture.poisoned = true;
        return new Failure(message, null);
    }

    private static String captureName(Capture capture, String name) {
        if (name == null || name.length() == 0 || name.length() > 512 || name.indexOf('\0') >= 0) {
            throw captureFailed(capture, "Invalid game record name");
        }
        return name.replace('/', 'S');
    }

    /** Routes incidental record writes through the same bounded writer queue. */
    public interface WriteDispatcher { void changed(Snapshot mutation); }

    public static void setWriteDispatcher(WriteDispatcher dispatcher) { writeDispatcher = dispatcher; }

    public static boolean isCaptureAvailable() { return logicalReady && !loading; }

    private static Snapshot acceptSnapshot(Map<String, byte[]> records, boolean wholeFarm,
            boolean gameplaySnapshot, boolean loadingHeaderOnly) {
        synchronized (CACHE_LOCK) {
            long version = ++acceptedVersion;
            Map<String, Long> versions = new HashMap<String, Long>();
            for (String name : records.keySet()) versions.put(name, version);
            Snapshot snapshot = new Snapshot(records, versions, wholeFarm, gameplaySnapshot, loadingHeaderOnly);
            publishLogicalSnapshot(snapshot);
            return snapshot;
        }
    }

    private static void publishLogicalSnapshot(Snapshot snapshot) {
        synchronized (CACHE_LOCK) {
            Map<String, byte[]> accepted = new HashMap<String, byte[]>();
            Map<String, Long> versions = new HashMap<String, Long>(logicalVersions);
            for (Map.Entry<String, byte[]> record : snapshot.records.entrySet()) {
                Long prior = versions.get(record.getKey());
                Long next = snapshot.versions.get(record.getKey());
                if (prior == null || next.longValue() >= prior.longValue()) {
                    accepted.put(record.getKey(), record.getValue());
                    versions.put(record.getKey(), next);
                }
            }
            logicalRecords = overlayRecords(logicalRecords, accepted);
            logicalVersions = Collections.unmodifiableMap(versions);
        }
    }

    private static Map<String, byte[]> overlayRecords(Map<String, byte[]> base, Map<String, byte[]> changes) {
        Map<String, byte[]> updated = new HashMap<String, byte[]>(base);
        for (Map.Entry<String, byte[]> record : changes.entrySet()) {
            if (record.getValue() == null) updated.remove(record.getKey());
            else updated.put(record.getKey(), record.getValue());
        }
        return Collections.unmodifiableMap(updated);
    }

    private static boolean dispatchMutation(String name, byte[] value) {
        WriteDispatcher dispatcher = writeDispatcher;
        if (dispatcher == null || loading) return false;
        Map<String, byte[]> records = new HashMap<String, byte[]>();
        records.put(name, value);
        Snapshot mutation = acceptSnapshot(records, false, false, false);
        dispatcher.changed(mutation);
        return true;
    }

    /** App startup entry point. Do not use external or temporary storage. */
    public static void initialize() {
        Context activity = com.gameloft.android.wrapper.au.getContext();
        if (activity == null) throw failed("Android context is not ready for game storage", null);
        Context application = activity.getApplicationContext();
        initialize(application == null ? activity : application);
    }

    /** Also permits an isolated ContextWrapper in Android storage tests. */
    public static void initialize(Context suppliedContext) {
        if (suppliedContext == null) throw failed("Android context is not ready for game storage", null);
        LOCK.lock();
        try {
            if (context == null) context = suppliedContext;
            openLocked();
        } finally { LOCK.unlock(); }
    }

    public static void reopen() {
        LOCK.lock();
        try {
            if (depth != 0) throw failed("Cannot reopen storage while a save is pending", null);
            closeLocked();
            if (context == null) throw failed("Android context is not ready for game storage", null);
            openLocked();
        } finally { LOCK.unlock(); }
    }

    public static void close() {
        LOCK.lock();
        try {
            if (depth != 0) throw failed("Cannot close storage while a save is pending", null);
            closeLocked();
            context = null;
            confirmedRecords = Collections.emptyMap();
            logicalRecords = Collections.emptyMap();
            logicalVersions = Collections.emptyMap();
            logicalReady = false;
            writeDispatcher = null;
            APPLIED_VERSIONS.clear();
            loading = false;
            protectExistingSnapshot = false;
        } finally { LOCK.unlock(); }
    }

    private static void closeLocked() {
        if (helper != null) helper.close();
        helper = null;
        database = null;
        ready = false;
    }

    private static void openLocked() {
        if (depth != 0) throw failed("Cannot reopen storage during a pending save", null);
        if (context == null) {
            Context activity = com.gameloft.android.wrapper.au.getContext();
            if (activity == null) throw failed("Android context is not ready for game storage", null);
            context = activity.getApplicationContext();
            if (context == null) context = activity;
        }
        try {
            if (database == null || !database.isOpen()) {
                helper = new Helper(context);
                helper.setWriteAheadLoggingEnabled(true);
                database = helper.getWritableDatabase();
            }
            // Querying the slot also detects an incompatible/missing schema.
            database.beginTransaction();
            try {
                ContentValues slot = new ContentValues();
                slot.put("slot_id", SLOT);
                slot.put("generation", 0L);
                slot.put("created_at", System.currentTimeMillis());
                slot.put("updated_at", System.currentTimeMillis());
                slot.put("complete_snapshot_generation", 0L);
                slot.put("snapshot_format", 0);
                slot.put("pose_snapshot", 0);
                long inserted = database.insertWithOnConflict("save_slots", null, slot, SQLiteDatabase.CONFLICT_IGNORE);
                if (inserted >= 0) {
                    Cursor previous = database.rawQuery("SELECT MAX(generation) FROM (SELECT generation FROM save_records WHERE slot_id=? UNION ALL SELECT generation FROM save_commits WHERE slot_id=?)", new String[]{SLOT, SLOT});
                    try {
                        if (previous.moveToFirst() && !previous.isNull(0) && previous.getLong(0) > 0) {
                            ContentValues recovered = new ContentValues();
                            recovered.put("generation", previous.getLong(0));
                            database.update("save_slots", recovered, "slot_id=?", new String[]{SLOT});
                        }
                    } finally { previous.close(); }
                    Cursor snapshots = database.rawQuery("SELECT generation,snapshot_format,pose_snapshot FROM save_commits WHERE slot_id=? AND is_snapshot=1 ORDER BY generation DESC LIMIT 1", new String[]{SLOT});
                    try {
                        if (snapshots.moveToFirst() && !snapshots.isNull(0)) {
                            ContentValues recovered = new ContentValues();
                            recovered.put("complete_snapshot_generation", snapshots.getLong(0));
                            recovered.put("snapshot_format", snapshots.getInt(1));
                            recovered.put("pose_snapshot", snapshots.getInt(2));
                            database.update("save_slots", recovered, "slot_id=?", new String[]{SLOT});
                        }
                    } finally { snapshots.close(); }
                }
                importLegacyLocked();
                Cursor recordCount = database.rawQuery("SELECT COUNT(*) FROM save_records WHERE slot_id=?", new String[]{SLOT});
                try {
                    recordCount.moveToFirst();
                    long count = recordCount.getLong(0);
                    if (slotNumber("generation") > 0 && count == 0) {
                        throw failed("Previously saved records are missing; progress was not reset", null);
                    }
                    if (slotNumber("generation") == 0 && count > 0) {
                        boolean complete = readLocked("RMSHeader") != null && readLocked("save_gameplay") != null && readLocked("save_farm0") != null;
                        ContentValues imported = new ContentValues();
                        imported.put("generation", 1L);
                        imported.put("updated_at", System.currentTimeMillis());
                        if (complete) imported.put("complete_snapshot_generation", 1L);
                        database.update("save_slots", imported, "slot_id=?", new String[]{SLOT});
                        database.execSQL("UPDATE save_records SET generation=1 WHERE slot_id=? AND generation=0", new Object[]{SLOT});
                        ContentValues commit = new ContentValues();
                        commit.put("slot_id", SLOT); commit.put("generation", 1L);
                        commit.put("committed_at", System.currentTimeMillis());
                        commit.put("changed_records", count); commit.put("is_snapshot", complete ? 1 : 0); commit.put("snapshot_format", 0); commit.put("pose_snapshot", 0);
                        database.insertOrThrow("save_commits", null, commit);
                    }
                } finally { recordCount.close(); }
                if (slotNumber("complete_snapshot_generation") > 0
                        && (readLocked("RMSHeader") == null || readLocked("save_gameplay") == null || readLocked("save_farm0") == null)) {
                    throw failed("The saved snapshot is incomplete; progress was not reset", null);
                }
                if (slotNumber("snapshot_format") >= 1 && readLocked("pending_rewards_v1") == null) {
                    throw failed("Saved reward state is missing; progress was not reset", null);
                }
                if (slotNumber("pose_snapshot") >= 1 && readLocked("entity_pose_v1") == null) {
                    throw failed("Saved entity orientation is missing; progress was not reset", null);
                }
                database.setTransactionSuccessful();
            } finally { database.endTransaction(); }
            currentGeneration = slotNumber("generation");
            refreshConfirmedRecordsLocked();
            ready = true;
            if (currentStatus == STATUS_FAILED) currentStatus = STATUS_IDLE;
            error = "";
        } catch (Exception cause) {
            throw failed("Cannot open or migrate the game progress database", cause);
        }
    }

    public static void beginSave() { beginSave(true); }

    /** Protect an existing save from defaults/repair writes during decoding. */
    public static void beginLoading() {
        LOCK.lock();
        try {
            if (depth != 0) throw failed("Cannot load while a save is pending", null);
            if (!ready) openLocked();
            loading = true;
            protectExistingSnapshot = readLocked("save_gameplay") != null;
            // The controller drains its writer before entering a new load.
            // Reads during decoding still use SQLite and verify every checksum.
            synchronized (CACHE_LOCK) {
                logicalRecords = confirmedRecords;
                logicalVersions = Collections.emptyMap();
            }
        } finally { LOCK.unlock(); }
    }

    public static void finishLoading() {
        LOCK.lock();
        try {
            if (!ready) throw failed("Saved game data has not loaded successfully", null);
            loading = false;
            protectExistingSnapshot = false;
        } finally { LOCK.unlock(); }
    }

    public static void failLoad(String message) {
        LOCK.lock();
        try {
            loading = true;
            protectExistingSnapshot = true;
            failed(message == null ? "Cannot restore saved progress" : message, null);
        } finally { LOCK.unlock(); }
    }

    public static boolean isLoading() { return loading; }

    /** A visited-farm save may update global state while retaining own-farm data. */
    public static void beginSave(boolean wholeFarm) {
        beginSave(wholeFarm, false);
    }

    private static void beginSave(boolean wholeFarm, boolean loadingHeaderOnly) {
        if (CAPTURE.get() != null) throw new Failure("Cannot open SQLite while capturing game progress", null);
        LOCK.lock();
        boolean acquired = false;
        boolean started = false;
        try {
            if (loading && !loadingHeaderOnly) throw failed("Game progress is still loading", null);
            if (depth == 0) {
                if (!ready) openLocked();
                database.beginTransaction();
                started = true;
                poisoned = false;
                writes = 0;
                TOUCHED.clear();
                RECORD_CHANGES.clear();
                requireWholeFarm = wholeFarm;
                nextGeneration = slotNumber("generation") + 1;
                currentStatus = STATUS_SAVING;
                error = "";
            } else {
                if (poisoned) throw failed("The pending game save has already failed", null);
                requireWholeFarm |= wholeFarm;
            }
            depth++;
            acquired = true;
        } catch (Exception cause) {
            if (started) database.endTransaction();
            throw failed("Cannot begin a game save", cause);
        } finally {
            // Hold the reentrant lock across serialization/commit. Other threads
            // cannot enter SQLite then deadlock the owner waiting to commit.
            if (!acquired) LOCK.unlock();
        }
    }

    public static void commitSave() {
        if (!LOCK.isHeldByCurrentThread() || depth == 0) throw new Failure("No pending game save on this thread", null);
        try {
            if (depth > 1) { depth--; return; }
            try {
                if (!TOUCHED.contains("RMSHeader") || !TOUCHED.contains("save_gameplay") || !TOUCHED.contains("pending_rewards_v1")
                        || (requireWholeFarm && (!TOUCHED.contains("save_farm0") || !TOUCHED.contains("entity_pose_v1")))) {
                    poisoned = true;
                    error = "Game snapshot is incomplete; existing progress was retained";
                }
                if (readLocked("RMSHeader") == null || readLocked("save_gameplay") == null
                        || readLocked("pending_rewards_v1") == null
                        || (requireWholeFarm && (readLocked("save_farm0") == null || readLocked("entity_pose_v1") == null))) {
                    poisoned = true;
                    error = "Game snapshot records are missing; existing progress was retained";
                }
                if (!requireWholeFarm && readLocked("save_farm0") == null) {
                    poisoned = true;
                    error = "Own farm must be saved before saving a visited farm";
                }
                if (!requireWholeFarm && slotNumber("pose_snapshot") >= 1 && readLocked("entity_pose_v1") == null) {
                    poisoned = true;
                    error = "Own-farm orientation must be retained while visiting another farm";
                }
            } catch (Exception cause) {
                failed("Cannot verify the complete game snapshot", cause);
            }
            finishLocked(true);
        } finally { LOCK.unlock(); }
    }

    public static void abortSave() {
        if (!LOCK.isHeldByCurrentThread() || depth == 0) return;
        try {
            poisoned = true;
            if (depth > 1) { depth--; return; }
            try { database.endTransaction(); }
            finally {
                depth = 0;
                TOUCHED.clear();
                RECORD_CHANGES.clear();
                currentStatus = STATUS_FAILED;
                if (error.length() == 0) error = "Game save was cancelled; previous progress was retained";
            }
        } finally { LOCK.unlock(); }
    }

    private static void finishLocked(boolean snapshot) {
        boolean successful = false;
        boolean metadataChanged = false;
        try {
            if (poisoned) throw failed(error.length() == 0 ? "Game save failed; previous progress was retained" : error, null);
            // An unchanged legacy payload can still become the first verified
            // five-record snapshot. Upgrade its marker without rewriting blobs.
            metadataChanged = snapshot && (slotNumber("complete_snapshot_generation") == 0
                    || slotNumber("snapshot_format") < 1 || (requireWholeFarm && slotNumber("pose_snapshot") < 1));
            if (writes > 0 || metadataChanged) {
                ContentValues slot = new ContentValues();
                slot.put("generation", nextGeneration);
                slot.put("updated_at", System.currentTimeMillis());
                if (snapshot) {
                    slot.put("complete_snapshot_generation", nextGeneration);
                    slot.put("snapshot_format", 1);
                    if (requireWholeFarm) slot.put("pose_snapshot", 1);
                }
                if (database.update("save_slots", slot, "slot_id = ?", new String[]{SLOT}) != 1) {
                    throw failed("Game save slot is missing", null);
                }
                ContentValues commit = new ContentValues();
                commit.put("slot_id", SLOT);
                commit.put("generation", nextGeneration);
                commit.put("committed_at", System.currentTimeMillis());
                commit.put("changed_records", writes);
                commit.put("is_snapshot", snapshot ? 1 : 0);
                commit.put("snapshot_format", snapshot ? 1 : 0);
                commit.put("pose_snapshot", snapshot ? slotNumber("pose_snapshot") : 0L);
                database.insertOrThrow("save_commits", null, commit);
                database.execSQL("DELETE FROM save_commits WHERE slot_id=? AND generation<?",
                        new Object[]{SLOT, Math.max(0L, nextGeneration - 31)});
            }
            database.setTransactionSuccessful();
            successful = true;
        } catch (Exception cause) {
            throw failed("Cannot commit game progress; previous save was retained", cause);
        } finally {
            try { database.endTransaction(); }
            catch (Exception cause) { successful = false; throw failed("SQLite could not finish the game save", cause); }
            finally {
                depth = 0;
                TOUCHED.clear();
                if (successful) {
                    if (writes > 0 || metadataChanged) currentGeneration = nextGeneration;
                    publishCommittedRecordsLocked();
                    currentStatus = STATUS_SAVED;
                    error = "";
                }
                RECORD_CHANGES.clear();
            }
        }
    }

    public static byte[] read(String storeName) {
        Capture capture = CAPTURE.get();
        if (capture != null) {
            String name = captureName(capture, storeName);
            byte[] value = capture.records.containsKey(name) ? capture.records.get(name) : capture.base.get(name);
            return value == null ? null : Arrays.copyOf(value, value.length);
        }
        if (writeDispatcher != null && !loading && logicalReady && APPLYING_SNAPSHOT.get() == null) {
            byte[] value = logicalRecords.get(detachedName(storeName));
            return value == null ? null : Arrays.copyOf(value, value.length);
        }
        LOCK.lock();
        try {
            if (!ready) {
                if (depth != 0) throw failed("The pending game save has already failed", null);
                openLocked();
            }
            String name = normalizeName(storeName);
            importRequestedLegacyLocked(name);
            byte[] value = readLocked(name);
            // read() can perform a one-time lazy import of a headerless store.
            // Never publish transaction-local bytes before their commit.
            if (depth == 0) publishConfirmedRecordLocked(name, value);
            return value;
        } catch (Exception cause) { throw failed("Cannot load saved game data", cause); }
        finally { LOCK.unlock(); }
    }

    private static byte[] readLocked(String name) {
        Cursor cursor = database.query("save_records", new String[]{"payload", "checksum", "deleted"},
                "slot_id = ? AND store_name = ? AND record_id = 1", new String[]{SLOT, name}, null, null, null);
        try {
            if (!cursor.moveToFirst()) return null;
            if (cursor.getInt(2) != 0) return null;
            byte[] payload = cursor.getBlob(0);
            byte[] expected = cursor.getBlob(1);
            if (payload == null || payload.length > MAX_RECORD_BYTES || expected == null
                    || !MessageDigest.isEqual(expected, digest(payload))) {
                throw failed("Saved record checksum is invalid: " + name, null);
            }
            return payload;
        } finally { cursor.close(); }
    }

    private static void importRequestedLegacyLocked(String name) throws IOException {
        Cursor known = database.query("legacy_imports", new String[]{"store_name"},
                "slot_id=? AND store_name=?", new String[]{SLOT, name}, null, null, null);
        try { if (known.moveToFirst()) return; } finally { known.close(); }
        boolean own = depth == 0;
        if (own) database.beginTransaction();
        try {
            // The original reader can recover a store even if its one-byte
            // registry was lost. Inspect the exact requested name read-only;
            // never conclude "new" solely because discovery skipped that file.
            File directory = com.gameloft.android.wrapper.d.bfb ? context.getExternalFilesDir(null) : context.getFilesDir();
            if (directory == null || !directory.isDirectory()) throw new IOException("Legacy save directory is unavailable");
            File[] files = directory.listFiles();
            if (files == null) throw new IOException("Cannot inspect legacy save directory");
            importLegacyStoreLocked(directory, name, files);
            if (own && slotNumber("generation") == 0 && readLocked(name) != null) {
                ContentValues imported = new ContentValues();
                imported.put("generation", 1L);
                imported.put("updated_at", System.currentTimeMillis());
                database.update("save_slots", imported, "slot_id=?", new String[]{SLOT});
                database.execSQL("UPDATE save_records SET generation=1 WHERE slot_id=? AND generation=0", new Object[]{SLOT});
            }
            if (own) database.setTransactionSuccessful();
        } finally {
            if (own) database.endTransaction();
        }
        if (own) currentGeneration = slotNumber("generation");
    }

    public static void write(String storeName, byte[] payload, int offset, int length) {
        Capture capture = CAPTURE.get();
        if (capture != null) {
            if (payload == null || offset < 0 || length < 0 || length > MAX_RECORD_BYTES || offset > payload.length - length) {
                throw captureFailed(capture, "Invalid game record payload");
            }
            String name = captureName(capture, storeName);
            if (capture.poisoned) throw captureFailed(capture, "The captured game snapshot has already failed");
            byte[] value = Arrays.copyOfRange(payload, offset, offset + length);
            captureMutation(capture, name, value);
            return;
        }
        if (writeDispatcher != null && !loading && APPLYING_SNAPSHOT.get() == null) {
            if (payload == null || offset < 0 || length < 0 || length > MAX_RECORD_BYTES || offset > payload.length - length) {
                throw new Failure("Invalid game record payload", null);
            }
            String name = detachedName(storeName);
            if (deferWhileLoading(name)) return;
            if (dispatchMutation(name, Arrays.copyOfRange(payload, offset, offset + length))) return;
        }
        LOCK.lock();
        try {
            if (payload == null || offset < 0 || length < 0 || length > MAX_RECORD_BYTES || offset > payload.length - length) {
                throw failed("Invalid game record payload", null);
            }
            String name = normalizeName(storeName);
            if (deferWhileLoading(name)) return;
            mutate(name, Arrays.copyOfRange(payload, offset, offset + length), false);
        } finally { LOCK.unlock(); }
    }

    public static void delete(String storeName) {
        Capture capture = CAPTURE.get();
        if (capture != null) {
            String name = captureName(capture, storeName);
            if (capture.poisoned) throw captureFailed(capture, "The captured game snapshot has already failed");
            captureMutation(capture, name, null);
            return;
        }
        if (writeDispatcher != null && !loading && APPLYING_SNAPSHOT.get() == null) {
            String name = detachedName(storeName);
            if (deferWhileLoading(name)) return;
            if (dispatchMutation(name, null)) return;
        }
        LOCK.lock();
        try {
            String name = normalizeName(storeName);
            if (deferWhileLoading(name)) return;
            mutate(name, null, true);
        }
        finally { LOCK.unlock(); }
    }

    private static void captureMutation(Capture capture, String name, byte[] value) {
        if (!capture.wholeFarm && (name.equals("save_farm0") || name.equals("entity_pose_v1"))) {
            if (value == null || !Arrays.equals(capture.base.get(name), value)) {
                throw captureFailed(capture, "A visited-farm snapshot cannot replace the saved own farm or its orientation");
            }
            // Retain the worker's newest own-farm record, including a preceding
            // queued full snapshot, rather than reapplying this cached value.
            return;
        }
        capture.records.put(name, value);
        // Reserve ordering when the bytes are serialized, rather than when
        // the entire snapshot finishes. A concurrent newer SDK write wins.
        synchronized (CACHE_LOCK) { capture.versions.put(name, ++acceptedVersion); }
    }

    private static boolean deferWhileLoading(String name) {
        // These writes were calculated before complete state restoration. Never
        // flush them later: the post-load serializer will produce fresh bytes.
        return loading && (protectExistingSnapshot || name.equals("save_gameplay") || name.startsWith("save_farm"));
    }

    private static void mutate(String name, byte[] payload, boolean deleted) {
        LOCK.lock();
        boolean own = depth == 0;
        try {
            if (!ready) {
                if (!own) throw failed("The pending game save has already failed", null);
                openLocked();
            }
            if (!own && poisoned) throw failed("The pending game save has already failed", null);
            if (own) {
                database.beginTransaction();
                poisoned = false;
                writes = 0;
                TOUCHED.clear();
                RECORD_CHANGES.clear();
                nextGeneration = slotNumber("generation") + 1;
                depth = 1;
                currentStatus = STATUS_SAVING;
            }
            TOUCHED.add(name);
            byte[] current = readLocked(name);
            if (!own && !requireWholeFarm && (name.equals("save_farm0") || name.equals("entity_pose_v1"))
                    && (deleted || !Arrays.equals(current, payload))) {
                throw failed("A visited-farm save cannot replace the saved own farm or its orientation", null);
            }
            // A tombstone still matters even when a record has never been read.
            if ((deleted && !isDeletedLocked(name)) || (!deleted && !Arrays.equals(current, payload))) {
                putLocked(name, payload, deleted, nextGeneration);
                RECORD_CHANGES.put(name, deleted ? null : payload);
                writes++;
            }
            if (own) finishLocked(false);
        } catch (Exception cause) {
            poisoned = true;
            if (own && depth != 0) {
                try { database.endTransaction(); } finally { depth = 0; TOUCHED.clear(); RECORD_CHANGES.clear(); }
            }
            throw failed("Cannot save game data; changes remain unsaved", cause);
        } finally { LOCK.unlock(); }
    }

    private static boolean isDeletedLocked(String name) {
        Cursor cursor = database.query("save_records", new String[]{"deleted"},
                "slot_id=? AND store_name=? AND record_id=1", new String[]{SLOT, name}, null, null, null);
        try { return cursor.moveToFirst() && cursor.getInt(0) != 0; }
        finally { cursor.close(); }
    }

    private static void putLocked(String name, byte[] payload, boolean deleted, long generation) {
        ContentValues row = new ContentValues();
        row.put("slot_id", SLOT);
        row.put("store_name", name);
        row.put("record_id", 1);
        if (deleted) { row.putNull("payload"); row.putNull("checksum"); }
        else { row.put("payload", payload); row.put("checksum", digest(payload)); }
        row.put("deleted", deleted ? 1 : 0);
        row.put("generation", generation);
        if (database.insertWithOnConflict("save_records", null, row, SQLiteDatabase.CONFLICT_REPLACE) < 0) {
            throw failed("SQLite did not write the game record", null);
        }
    }

    public static boolean hasGameplaySave() {
        Capture capture = CAPTURE.get();
        if (capture != null) {
            return (capture.records.containsKey("save_gameplay")
                    ? capture.records.get("save_gameplay") : capture.base.get("save_gameplay")) != null;
        }
        if (writeDispatcher != null && !loading && logicalReady && APPLYING_SNAPSHOT.get() == null) {
            return logicalRecords.get("save_gameplay") != null;
        }
        LOCK.lock();
        try {
            if (!ready) openLocked();
            return readLocked("save_gameplay") != null;
        } catch (Exception cause) { throw failed("Cannot inspect saved progress", cause); }
        finally { LOCK.unlock(); }
    }

    public static int status() { return currentStatus; }
    public static String lastError() { return error; }
    public static long generation() { return currentGeneration; }
    public static boolean isReady() { return ready; }

    private static long slotNumber(String column) {
        Cursor cursor = database.query("save_slots", new String[]{column}, "slot_id = ?", new String[]{SLOT}, null, null, null);
        try {
            if (!cursor.moveToFirst()) throw failed("Game save slot is missing", null);
            return cursor.getLong(0);
        } finally { cursor.close(); }
    }

    private static Failure failed(String message, Throwable cause) {
        poisoned = true;
        ready = false;
        currentStatus = STATUS_FAILED;
        error = message;
        return new Failure(message, cause);
    }

    private static String normalizeName(String name) {
        if (name == null || name.length() == 0 || name.length() > 512 || name.indexOf('\0') >= 0) {
            throw failed("Invalid game record name", null);
        }
        return name.replace('/', 'S');
    }

    private static String detachedName(String name) {
        if (name == null || name.length() == 0 || name.length() > 512 || name.indexOf('\0') >= 0) {
            throw new Failure("Invalid game record name", null);
        }
        return name.replace('/', 'S');
    }

    private static void refreshConfirmedRecordsLocked() {
        Map<String, byte[]> records = new HashMap<String, byte[]>();
        Cursor names = database.query("save_records", new String[]{"store_name"},
                "slot_id=? AND record_id=1 AND deleted=0", new String[]{SLOT}, null, null, null);
        try {
            while (names.moveToNext()) {
                String name = names.getString(0);
                records.put(name, readLocked(name));
            }
        } finally { names.close(); }
        confirmedRecords = Collections.unmodifiableMap(records);
        if (!logicalReady) {
            synchronized (CACHE_LOCK) {
                logicalRecords = confirmedRecords;
                logicalReady = true;
            }
        }
    }

    private static void publishConfirmedRecordLocked(String name, byte[] value) {
        Map<String, byte[]> change = new HashMap<String, byte[]>();
        change.put(name, value == null ? null : Arrays.copyOf(value, value.length));
        confirmedRecords = overlayRecords(confirmedRecords, change);
        if (writeDispatcher == null && APPLYING_SNAPSHOT.get() == null) {
            synchronized (CACHE_LOCK) {
                // A decoder read can observe disk before an explicit pending
                // language setting commits. It must not roll back accepted RAM.
                if (!logicalVersions.containsKey(name)) logicalRecords = overlayRecords(logicalRecords, change);
            }
        }
    }

    private static void publishCommittedRecordsLocked() {
        if (RECORD_CHANGES.isEmpty()) return;
        confirmedRecords = overlayRecords(confirmedRecords, RECORD_CHANGES);
        // Captures have already published their accepted absolute state. An
        // older commit or retry must never replace a newer queued value.
        if (APPLYING_SNAPSHOT.get() == null) {
            acceptSnapshot(RECORD_CHANGES, false, false, false);
        }
    }

    private static byte[] digest(byte[] data) {
        try { return MessageDigest.getInstance("SHA-256").digest(data); }
        catch (NoSuchAlgorithmException cause) { throw failed("Game checksum algorithm is unavailable", cause); }
    }

    /** Import only record-1 stores, once, without opening/deleting original RMS. */
    private static void importLegacyLocked() throws IOException {
        File directory = com.gameloft.android.wrapper.d.bfb ? context.getExternalFilesDir(null) : context.getFilesDir();
        if (directory == null || !directory.isDirectory()) throw new IOException("Legacy save directory is unavailable");
        File[] files = directory.listFiles();
        if (files == null) throw new IOException("Cannot inspect legacy save directory");
        Set<String> stores = new TreeSet<String>();
        for (File file : files) {
            Matcher matcher = LEGACY_FILE.matcher(file.getName());
            if (matcher.matches()) {
                String name = matcher.group(1);
                File header = new File(directory, name);
                if ((header.isFile() && header.length() == 1) || isGameplayName(name)) stores.add(name);
            }
            if (file.isFile() && file.length() == 1 && isGameplayName(file.getName())) stores.add(file.getName());
        }
        for (String name : stores) importLegacyStoreLocked(directory, name, files);
    }

    private static boolean isGameplayName(String name) {
        return name.equals("RMSHeader") || name.equals("save_gameplay") || name.matches("save_farm[0-9]+")
                || name.equals("online_gameplay") || name.startsWith("RMS_")
                || name.equals("GDPRUPDATEFlag") || name.equals("ADSUPDATEFlag");
    }

    private static void importLegacyStoreLocked(File directory, String name, File[] files) throws IOException {
        Cursor known = database.query("legacy_imports", new String[]{"store_name"}, "slot_id = ? AND store_name = ?", new String[]{SLOT, name}, null, null, null);
        try { if (known.moveToFirst()) return; } finally { known.close(); }
        Cursor existing = database.query("save_records", new String[]{"store_name"}, "slot_id = ? AND store_name = ? AND record_id = 1", new String[]{SLOT, name}, null, null, null);
        boolean present;
        try { present = existing.moveToFirst(); } finally { existing.close(); }
        // Once SQLite has durable history, a missing authoritative record is
        // not an invitation to roll progress back to an old retained RMS file.
        // Generic settings with a lost registry can still be imported lazily.
        if (!present && (!isPrimaryStateRecord(name) || slotNumber("generation") == 0)) {
            byte[] value = readLegacy(directory, name, files);
            if (value != null) putLocked(name, value, false, 0);
        }
        ContentValues imported = new ContentValues();
        imported.put("slot_id", SLOT); imported.put("store_name", name); imported.put("imported_at", System.currentTimeMillis());
        database.insertOrThrow("legacy_imports", null, imported);
    }

    private static boolean isPrimaryStateRecord(String name) {
        return isGameplayName(name) || name.equals("pending_rewards_v1") || name.equals("entity_pose_v1");
    }

    private static byte[] readLegacy(File directory, String name, File[] files) throws IOException {
        TreeSet<Integer> generations = new TreeSet<Integer>();
        String prefix = name + "_1_saved";
        boolean artifacts = false;
        for (File file : files) {
            if (file.getName().startsWith(name + "_1")) artifacts = true;
            if (file.getName().startsWith(prefix)) {
                try {
                    int generation = Integer.parseInt(file.getName().substring(prefix.length()));
                    if (generation >= 1 && generation <= 10000) generations.add(generation);
                }
                catch (NumberFormatException ignored) { /* unrelated filename */ }
            }
        }
        while (!generations.isEmpty()) {
            int generation = generations.last(); generations.remove(generation);
            File record = new File(directory, prefix + generation);
            File crc = new File(directory, name + "_1_CRC" + generation);
            if (!crc.isFile() || crc.length() != 8) continue;
            try {
                byte[] payload = readLegacyPayload(record);
                DataInputStream input = new DataInputStream(new FileInputStream(crc));
                long expected;
                try { expected = input.readLong(); } finally { input.close(); }
                CRC32 actual = new CRC32(); actual.update(payload);
                if (expected == actual.getValue()) return payload;
            } catch (IOException ignored) { /* try an earlier valid backup */ }
        }
        File canonical = new File(directory, name + "_1");
        if (canonical.isFile()) return readLegacyPayload(canonical);
        File header = new File(directory, name);
        if (header.isFile()) {
            FileInputStream input = new FileInputStream(header);
            int count;
            try { count = input.read(); } finally { input.close(); }
            if (count > 0) throw new IOException("Legacy save record is missing: " + name);
        }
        if (artifacts) throw new IOException("No valid legacy save generation: " + name);
        return null;
    }

    private static byte[] readLegacyPayload(File file) throws IOException {
        DataInputStream input = new DataInputStream(new FileInputStream(file));
        try {
            int id = input.readUnsignedByte();
            int length = input.readInt();
            if (id != 1 || length < 0 || length > MAX_RECORD_BYTES || file.length() != 5L + length) {
                throw new IOException("Invalid legacy save record: " + file.getName());
            }
            byte[] payload = new byte[length]; input.readFully(payload);
            return payload;
        } finally { input.close(); }
    }

    private static final class CorruptionHandler implements DatabaseErrorHandler {
        public void onCorruption(SQLiteDatabase broken) {
            // Android's default handler deletes corrupt files. Preserve them for
            // retry/backup recovery instead of silently creating a new game.
            throw failed("Game progress database is corrupt; data was retained", null);
        }
    }

    private static final class Helper extends SQLiteOpenHelper {
        Helper(Context context) { super(context, DATABASE_NAME, null, VERSION, new CorruptionHandler()); }

        public void onConfigure(SQLiteDatabase db) {
            db.setForeignKeyConstraintsEnabled(true);
            db.execSQL("PRAGMA synchronous=FULL");
        }

        public void onCreate(SQLiteDatabase db) {
            createVersionOne(db);
            upgradeOneToTwo(db);
            upgradeTwoToThree(db);
            upgradeThreeToFour(db);
        }

        public void onOpen(SQLiteDatabase db) {
            // Repair a missing auxiliary table without resetting records. If all
            // tables of an existing versioned database vanished, do not call it
            // a new player merely because CREATE TABLE could recreate empties.
            boolean slots = hasTable(db, "save_slots");
            boolean records = hasTable(db, "save_records");
            boolean commits = hasTable(db, "save_commits");
            boolean imports = hasTable(db, "legacy_imports");
            if (!slots && !records && !commits && !imports) {
                throw failed("The progress database schema is missing; data was not reset", null);
            }
            long previousGeneration = 0;
            if (slots) previousGeneration = Math.max(previousGeneration, maxGeneration(db, "save_slots"));
            if (commits) previousGeneration = Math.max(previousGeneration, maxGeneration(db, "save_commits"));
            if (previousGeneration > 0 && (!records || !hasAnyRecords(db))) {
                throw failed("Saved SQLite records are missing; legacy files were not used to reset progress", null);
            }
            db.beginTransaction();
            try {
                createVersionOne(db);
                Cursor columns = db.rawQuery("PRAGMA table_info(save_slots)", null);
                boolean completeColumn = false;
                try {
                    while (columns.moveToNext()) {
                        if ("complete_snapshot_generation".equals(columns.getString(1))) completeColumn = true;
                    }
                } finally { columns.close(); }
                if (!completeColumn) upgradeOneToTwo(db);
                upgradeTwoToThree(db);
                upgradeThreeToFour(db);
                db.setTransactionSuccessful();
            } finally { db.endTransaction(); }
        }

        private static boolean hasTable(SQLiteDatabase db, String name) {
            Cursor cursor = db.rawQuery("SELECT 1 FROM sqlite_master WHERE type='table' AND name=?", new String[]{name});
            try { return cursor.moveToFirst(); } finally { cursor.close(); }
        }

        private static long maxGeneration(SQLiteDatabase db, String table) {
            Cursor cursor = db.rawQuery("SELECT MAX(generation) FROM " + table + " WHERE slot_id=?", new String[]{SLOT});
            try { return cursor.moveToFirst() && !cursor.isNull(0) ? cursor.getLong(0) : 0L; }
            finally { cursor.close(); }
        }

        private static boolean hasAnyRecords(SQLiteDatabase db) {
            Cursor cursor = db.rawQuery("SELECT 1 FROM save_records WHERE slot_id=? LIMIT 1", new String[]{SLOT});
            try { return cursor.moveToFirst(); } finally { cursor.close(); }
        }

        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (oldVersion < 1 || oldVersion > VERSION || newVersion != VERSION) {
                throw failed("Unsupported progress database migration; data was retained", null);
            }
            if (oldVersion < 2) upgradeOneToTwo(db);
            if (oldVersion < 3) upgradeTwoToThree(db);
            if (oldVersion < 4) upgradeThreeToFour(db);
        }

        public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            throw failed("Cannot downgrade the progress database; data was retained", null);
        }

        private static void createVersionOne(SQLiteDatabase db) {
            db.execSQL("CREATE TABLE IF NOT EXISTS save_slots (slot_id TEXT PRIMARY KEY NOT NULL, generation INTEGER NOT NULL DEFAULT 0 CHECK(generation>=0), created_at INTEGER NOT NULL, updated_at INTEGER NOT NULL)");
            db.execSQL("CREATE TABLE IF NOT EXISTS save_records (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, record_id INTEGER NOT NULL CHECK(record_id=1), payload BLOB, checksum BLOB, deleted INTEGER NOT NULL DEFAULT 0 CHECK(deleted IN(0,1)), generation INTEGER NOT NULL CHECK(generation>=0), PRIMARY KEY(slot_id,store_name,record_id), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id), CHECK((deleted=1 AND payload IS NULL AND checksum IS NULL) OR (deleted=0 AND payload IS NOT NULL AND checksum IS NOT NULL AND length(checksum)=32 AND length(payload)<=67108864)))");
            db.execSQL("CREATE TABLE IF NOT EXISTS save_commits (slot_id TEXT NOT NULL, generation INTEGER NOT NULL, committed_at INTEGER NOT NULL, changed_records INTEGER NOT NULL, is_snapshot INTEGER NOT NULL CHECK(is_snapshot IN(0,1)), PRIMARY KEY(slot_id,generation), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))");
            db.execSQL("CREATE TABLE IF NOT EXISTS legacy_imports (slot_id TEXT NOT NULL, store_name TEXT NOT NULL, imported_at INTEGER NOT NULL, PRIMARY KEY(slot_id,store_name), FOREIGN KEY(slot_id) REFERENCES save_slots(slot_id))");
            db.execSQL("CREATE INDEX IF NOT EXISTS save_records_generation ON save_records(slot_id,generation)");
        }

        private static void upgradeOneToTwo(SQLiteDatabase db) {
            db.execSQL("ALTER TABLE save_slots ADD COLUMN complete_snapshot_generation INTEGER NOT NULL DEFAULT 0");
            db.execSQL("UPDATE save_slots SET complete_snapshot_generation=generation WHERE EXISTS (SELECT 1 FROM save_records WHERE save_records.slot_id=save_slots.slot_id AND store_name='save_gameplay' AND deleted=0) AND EXISTS (SELECT 1 FROM save_records WHERE save_records.slot_id=save_slots.slot_id AND store_name='save_farm0' AND deleted=0)");
        }

        private static void upgradeTwoToThree(SQLiteDatabase db) {
            if (!hasColumn(db, "save_slots", "snapshot_format")) db.execSQL("ALTER TABLE save_slots ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1))");
            if (!hasColumn(db, "save_commits", "snapshot_format")) db.execSQL("ALTER TABLE save_commits ADD COLUMN snapshot_format INTEGER NOT NULL DEFAULT 0 CHECK(snapshot_format IN(0,1))");
        }

        private static void upgradeThreeToFour(SQLiteDatabase db) {
            if (!hasColumn(db, "save_slots", "pose_snapshot")) db.execSQL("ALTER TABLE save_slots ADD COLUMN pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1))");
            if (!hasColumn(db, "save_commits", "pose_snapshot")) db.execSQL("ALTER TABLE save_commits ADD COLUMN pose_snapshot INTEGER NOT NULL DEFAULT 0 CHECK(pose_snapshot IN(0,1))");
        }

        private static boolean hasColumn(SQLiteDatabase db, String table, String name) {
            Cursor cursor = db.rawQuery("PRAGMA table_info(" + table + ")", null);
            try {
                while (cursor.moveToNext()) if (name.equals(cursor.getString(1))) return true;
                return false;
            } finally { cursor.close(); }
        }
    }
}
