import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import com.gameloft.android.ANMP.GloftGF2F.S800x480.GameSaveStore;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Android storage benchmark. Opaque deltas are NOT valid gameplay actions.
 * Only files/sqlite-performance-* databases are opened, never player storage.
 */
public final class GameSavePerformanceProbe {
    private static final String PACKAGE = "com.gameloft.android.ANMP.GloftGF2F";
    private static final String[] RECORDS = {"RMSHeader", "save_gameplay", "save_farm0", "pending_rewards_v1", "entity_pose_v1"};

    private static final class IsolatedContext extends ContextWrapper {
        final File root;
        final File files;
        IsolatedContext(Context base, String name) {
            super(base);
            root = new File(base.getFilesDir(), "sqlite-performance-" + name);
            check(!root.exists(), "Use a new isolated fixture name");
            files = new File(root, "files");
            check(files.mkdirs(), "Cannot create isolated files");
        }
        @Override public Context getApplicationContext() { return this; }
        @Override public File getFilesDir() { return files; }
        @Override public File getExternalFilesDir(String type) {
            File result = new File(root, "external");
            check(result.isDirectory() || result.mkdirs(), "Cannot create isolated external files");
            return result;
        }
        @Override public File getDatabasePath(String name) {
            check(GameSaveStore.DATABASE_NAME.equals(name), "Unexpected database name");
            File directory = new File(root, "databases");
            check(directory.isDirectory() || directory.mkdirs(), "Cannot create isolated databases");
            return new File(directory, name);
        }
        @Override public SQLiteDatabase openOrCreateDatabase(String name, int mode, SQLiteDatabase.CursorFactory factory) {
            return openOrCreateDatabase(name, mode, factory, null);
        }
        @Override public SQLiteDatabase openOrCreateDatabase(String name, int mode,
                SQLiteDatabase.CursorFactory factory, DatabaseErrorHandler handler) {
            int flags = SQLiteDatabase.CREATE_IF_NECESSARY | SQLiteDatabase.OPEN_READWRITE;
            if ((mode & Context.MODE_ENABLE_WRITE_AHEAD_LOGGING) != 0) flags |= SQLiteDatabase.ENABLE_WRITE_AHEAD_LOGGING;
            return SQLiteDatabase.openDatabase(getDatabasePath(name).getPath(), factory, flags, handler);
        }
    }

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }

    private static Map<String, byte[]> fixture(File file) throws Exception {
        Map<String, byte[]> result = new LinkedHashMap<String, byte[]>();
        DataInputStream input = new DataInputStream(new FileInputStream(file));
        try {
            check(input.readInt() == 0x47465031 && input.readInt() == RECORDS.length, "Invalid fixture header");
            for (String expected : RECORDS) {
                String name = input.readUTF();
                check(expected.equals(name), "Unexpected fixture record");
                int size = input.readInt();
                check(size > 0 && size <= 64 * 1024 * 1024, "Invalid fixture payload length");
                byte[] payload = new byte[size];
                input.readFully(payload);
                result.put(name, payload);
            }
            check(input.read() == -1, "Trailing fixture bytes");
        } finally { input.close(); }
        return result;
    }

    private static Map<String, byte[]> action(Map<String, byte[]> original, int index, boolean delta) {
        Map<String, byte[]> result = new LinkedHashMap<String, byte[]>();
        for (Map.Entry<String, byte[]> record : original.entrySet()) {
            byte[] payload = record.getValue();
            if (delta && (record.getKey().equals("save_gameplay") || record.getKey().equals("save_farm0"))) {
                payload = payload.clone();
                check(payload.length >= 4, "Fixture is too small for opaque delta");
                // Deliberately opaque stress bytes. Never load these as gameplay.
                for (int j = 0; j < 4; j++) payload[payload.length - 4 + j] ^= (byte) ((index + 1) >>> (8 * j));
            }
            result.put(record.getKey(), payload);
        }
        return result;
    }

    private static void write(Map<String, byte[]> records) {
        for (Map.Entry<String, byte[]> record : records.entrySet()) {
            byte[] payload = record.getValue();
            GameSaveStore.write(record.getKey(), payload, 0, payload.length);
        }
    }

    private static void synchronous(Map<String, byte[]> records) {
        GameSaveStore.beginSave(true);
        boolean committed = false;
        try {
            write(records);
            GameSaveStore.commitSave();
            committed = true;
        } finally { if (!committed) GameSaveStore.abortSave(); }
    }

    /** Reflection keeps this exact probe compatible with the old APK, which
     * has neither Snapshot nor AsyncSaveQueue. The worker is production code.
     */
    private static final class AsyncAccess {
        final Object queue;
        final Method begin;
        final Method finish;
        final Method abort;
        final Method submit;
        final Method awaitIdle;
        final Method durableSequence;
        final Method close;
        final List<Long> workerDurations = Collections.synchronizedList(new ArrayList<Long>());
        volatile boolean failed;

        AsyncAccess() throws Exception {
            final Class<?> listener = Class.forName("com.gameloft.android.ANMP.GloftGF2F.S800x480.AsyncSaveQueue$Listener");
            Class<?> type = Class.forName("com.gameloft.android.ANMP.GloftGF2F.S800x480.AsyncSaveQueue");
            Class<?> snapshot = Class.forName(GameSaveStore.class.getName() + "$Snapshot");
            Object callback = Proxy.newProxyInstance(listener.getClassLoader(), new Class<?>[]{listener}, new InvocationHandler() {
                public Object invoke(Object proxy, Method method, Object[] values) throws Exception {
                    if (method.getName().equals("completed")) {
                        Object result = values[0];
                        workerDurations.add(result.getClass().getField("durationNanos").getLong(result));
                        if (result.getClass().getField("failure").get(result) != null) failed = true;
                    } else if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
                    else if (method.getName().equals("equals")) return proxy == values[0];
                    else if (method.getName().equals("toString")) return "StoragePerformanceListener";
                    return null;
                }
            });
            queue = type.getConstructor(listener).newInstance(callback);
            begin = GameSaveStore.class.getMethod("beginCapture", boolean.class);
            finish = GameSaveStore.class.getMethod("finishCapture");
            abort = GameSaveStore.class.getMethod("abortCapture");
            submit = type.getMethod("submit", snapshot, long.class, Object.class);
            awaitIdle = type.getMethod("awaitIdle", long.class);
            durableSequence = type.getMethod("durableSequence");
            close = type.getMethod("close");
        }

        void submit(Map<String, byte[]> records, long sequence) throws Exception {
            begin.invoke(null, true);
            Object snapshot;
            try { write(records); snapshot = finish.invoke(null); }
            catch (Exception failure) { abort.invoke(null); throw failure; }
            submit.invoke(queue, snapshot, sequence, null);
        }

        void drain(long sequence) throws Exception {
            check(Boolean.TRUE.equals(awaitIdle.invoke(queue, 45000L)), "Writer did not drain before deadline");
            check(!failed && ((Long) durableSequence.invoke(queue)).longValue() == sequence, "Writer did not durably apply final request");
        }
    }

    private static Map<String, Long> io() {
        Map<String, Long> result = new TreeMap<String, Long>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("/proc/self/io"));
            try {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    if (parts.length == 2) result.put(parts[0], Long.parseLong(parts[1].trim()));
                }
            } finally { reader.close(); }
        } catch (Exception unavailable) { result.clear(); }
        return result;
    }

    private static long scalar(SQLiteDatabase db, String sql) {
        Cursor cursor = db.rawQuery(sql, null);
        try { check(cursor.moveToFirst(), "Missing SQL scalar"); return cursor.getLong(0); }
        finally { cursor.close(); }
    }

    private static double milliseconds(long nanoseconds) { return nanoseconds / 1000000.0; }

    public static void main(String[] args) {
        try { run(args); }
        catch (Throwable failure) { failure.printStackTrace(System.err); System.exit(1); }
    }

    private static void run(String[] args) throws Exception {
        check(args.length == 6, "Usage: <fixture filename> <isolated name> <count> <interval-ms> <unchanged|delta> <synchronous|async>");
        check(args[0].matches("sqlite-performance-code-[A-Za-z0-9_-]{1,40}/fixture.bin"), "Unsafe fixture path");
        check(args[1].matches("[A-Za-z0-9_-]{1,48}"), "Unsafe isolated name");
        int count = Integer.parseInt(args[2]);
        int interval = Integer.parseInt(args[3]);
        check(count >= 50 && count <= 100 && interval >= 0 && interval <= 100, "Invalid workload bounds");
        check(args[4].equals("delta") || args[4].equals("unchanged"), "Invalid workload kind");
        check(args[5].equals("synchronous") || args[5].equals("async"), "Invalid delivery");
        if (android.os.Looper.myLooper() == null) android.os.Looper.prepareMainLooper();
        Class.forName("android.database.sqlite.SQLiteCompatibilityWalFlags")
                .getDeclaredMethod("init", String.class).invoke(null, "compatibility_wal_supported=false");
        Class<?> threadClass = Class.forName("android.app.ActivityThread");
        Object thread = threadClass.getDeclaredMethod("systemMain").invoke(null);
        Context system = (Context) threadClass.getDeclaredMethod("getSystemContext").invoke(thread);
        Context base = system.createPackageContext(PACKAGE, 0);
        IsolatedContext context = new IsolatedContext(base, args[1]);
        Map<String, byte[]> original = fixture(new File(base.getFilesDir(), args[0]));
        GameSaveStore.initialize(context);
        synchronous(original);
        java.lang.reflect.Field field = GameSaveStore.class.getDeclaredField("database");
        field.setAccessible(true);
        SQLiteDatabase database = (SQLiteDatabase) field.get(null);
        database.beginTransaction();
        long policy;
        try {
            policy = scalar(database, "PRAGMA synchronous");
            Cursor journal = database.rawQuery("PRAGMA journal_mode", null);
            try { check(journal.moveToFirst() && "wal".equalsIgnoreCase(journal.getString(0)), "Production writer is not using WAL"); }
            finally { journal.close(); }
        }
        finally { database.endTransaction(); }
        check(policy == 2, "Production writer is not FULL synchronous");
        AsyncAccess async = args[5].equals("async") ? new AsyncAccess() : null;
        long initialGeneration = GameSaveStore.generation();
        Map<String, Long> before = io();
        long[] calls = new long[count];
        long[] cpuCalls = new long[count];
        long started = System.nanoTime();
        for (int index = 0; index < count; index++) {
            long scheduled = started + index * interval * 1000000L;
            long delay = scheduled - System.nanoTime();
            if (delay > 0) Thread.sleep(delay / 1000000L, (int) (delay % 1000000L));
            Map<String, byte[]> records = action(original, index, args[4].equals("delta"));
            long cpuStart = android.os.Debug.threadCpuTimeNanos();
            long callStart = System.nanoTime();
            if (async == null) synchronous(records); else async.submit(records, index + 1L);
            calls[index] = System.nanoTime() - callStart;
            cpuCalls[index] = android.os.Debug.threadCpuTimeNanos() - cpuStart;
        }
        long submitted = System.nanoTime();
        if (async != null) async.drain(count);
        long finished = System.nanoTime();
        Map<String, Long> after = io();
        long generation = GameSaveStore.generation();
        Map<String, byte[]> expected = action(original, count - 1, args[4].equals("delta"));
        for (Map.Entry<String, byte[]> record : expected.entrySet()) {
            check(Arrays.equals(record.getValue(), GameSaveStore.read(record.getKey())), "Final payload mismatch");
        }
        GameSaveStore.reopen();
        check(GameSaveStore.generation() == generation, "Final generation was not durable");
        for (Map.Entry<String, byte[]> record : expected.entrySet()) {
            check(Arrays.equals(record.getValue(), GameSaveStore.read(record.getKey())), "Reopened payload mismatch");
        }
        long[] sorted = calls.clone();
        long[] sortedCpu = cpuCalls.clone();
        Arrays.sort(sorted);
        Arrays.sort(sortedCpu);
        long total = 0;
        long cpuTotal = 0;
        for (long call : calls) total += call;
        for (long call : cpuCalls) cpuTotal += call;
        StringBuilder result = new StringBuilder("{\"passed\":true,\"isolated\":true,\"normal_player_database_opened\":false");
        result.append(",\"workload\":\"").append(args[4]).append("_opaque_record_storage_replay\"");
        result.append(",\"delivery\":\"").append(async == null ? "synchronous_store_api" : "capture_and_production_async_queue").append("\",\"actual_harvest_actions\":0");
        result.append(",\"submitted_snapshots\":").append(count).append(",\"target_interval_ms\":").append(interval);
        result.append(",\"durable_changed_commits\":").append(generation - initialGeneration);
        result.append(",\"full_synchronous\":true,\"final_payloads_verified_after_reopen\":true");
        result.append(",\"caller_p50_ms\":").append(milliseconds(sorted[(int) Math.ceil(count * .50) - 1]));
        result.append(",\"caller_p95_ms\":").append(milliseconds(sorted[(int) Math.ceil(count * .95) - 1]));
        result.append(",\"caller_max_ms\":").append(milliseconds(sorted[count - 1]));
        result.append(",\"caller_total_ms\":").append(milliseconds(total));
        result.append(",\"caller_cpu_p50_ms\":").append(milliseconds(sortedCpu[(int) Math.ceil(count * .50) - 1]));
        result.append(",\"caller_cpu_p95_ms\":").append(milliseconds(sortedCpu[(int) Math.ceil(count * .95) - 1]));
        result.append(",\"caller_cpu_total_ms\":").append(milliseconds(cpuTotal));
        result.append(",\"elapsed_ms\":").append(milliseconds(finished - started));
        result.append(",\"final_drain_ms\":").append(milliseconds(finished - submitted));
        if (async != null) {
            long[] worker = new long[async.workerDurations.size()];
            long workerTotal = 0;
            for (int i = 0; i < worker.length; i++) { worker[i] = async.workerDurations.get(i); workerTotal += worker[i]; }
            Arrays.sort(worker);
            check(worker.length > 0, "No worker completion measurements");
            result.append(",\"worker_apply_calls\":").append(worker.length);
            result.append(",\"worker_p50_ms\":").append(milliseconds(worker[(int) Math.ceil(worker.length * .50) - 1]));
            result.append(",\"worker_p95_ms\":").append(milliseconds(worker[(int) Math.ceil(worker.length * .95) - 1]));
            result.append(",\"worker_total_ms\":").append(milliseconds(workerTotal));
        }
        result.append(",\"caller_samples_ms\":[");
        for (int i = 0; i < count; i++) { if (i != 0) result.append(','); result.append(milliseconds(calls[i])); }
        result.append("],\"process_io_delta\":{");
        boolean comma = false;
        for (String key : before.keySet()) {
            if (!after.containsKey(key)) continue;
            if (comma) result.append(',');
            result.append('"').append(key).append("\":").append(after.get(key) - before.get(key));
            comma = true;
        }
        result.append("}}");
        if (async != null) async.close.invoke(async.queue);
        GameSaveStore.close();
        System.out.println(result);
        System.out.flush();
        System.exit(0);
    }
}
