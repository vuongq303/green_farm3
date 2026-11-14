package greenfarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.quanhoang.greenfarm3.S800x480.AsyncSaveQueue;
import com.quanhoang.greenfarm3.S800x480.GameSaveStore;
import java.nio.ByteBuffer;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;
import org.robolectric.annotation.SQLiteMode;
import org.robolectric.annotation.GraphicsMode;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 35, manifest = Config.NONE)
@SQLiteMode(SQLiteMode.Mode.NATIVE)
@GraphicsMode(GraphicsMode.Mode.LEGACY)
public class SQLiteSaveTest {
    private Context context;
    @Before public void openIsolatedDatabase() {
        context = RuntimeEnvironment.getApplication();
        GameSaveStore.close();
        context.deleteDatabase(GameSaveStore.DATABASE_NAME);
        GameSaveStore.initialize(context);
    }
    @After public void close() { GameSaveStore.close(); }
    private static byte[] value(int n) { return ByteBuffer.allocate(4).putInt(n).array(); }
    private static int read(String name) { return ByteBuffer.wrap(GameSaveStore.read(name)).getInt(); }
    private static GameSaveStore.Snapshot snapshot(int n) {
        GameSaveStore.beginAuxCapture();
        for (String name : new String[] {"test_coins", "test_milk", "test_planted"}) {
            byte[] data = value(n);
            GameSaveStore.write(name, data, 0, data.length);
        }
        return GameSaveStore.finishCapture();
    }
    @Test public void absoluteRetryStaleOrderAndReopen() {
        GameSaveStore.Snapshot old = snapshot(1), latest = snapshot(100);
        GameSaveStore.applySnapshot(old);
        GameSaveStore.applySnapshot(latest);
        long generation = GameSaveStore.generation();
        GameSaveStore.applySnapshot(latest);
        GameSaveStore.applySnapshot(old);
        assertEquals("replays must not generate writes", generation, GameSaveStore.generation());
        GameSaveStore.reopen();
        for (String name : new String[] {"test_coins", "test_milk", "test_planted"}) assertEquals(100, read(name));
    }
    @Test public void oneTenFiftyHundredAndCoalescedWriter() throws Exception {
        for (int count : new int[] {1, 10, 50, 100}) {
            LinkedBlockingQueue<AsyncSaveQueue.Result> results = new LinkedBlockingQueue<>();
            AsyncSaveQueue queue = new AsyncSaveQueue(results::add);
            for (int i = 1; i <= count; i++) queue.submit(snapshot(i), i, null);
            assertTrue("writer did not drain", queue.awaitIdle(10000));
            while (!results.isEmpty()) assertNull(results.remove().failure);
            assertEquals(count, queue.durableSequence());
            queue.close();
            GameSaveStore.reopen();
            assertEquals(count, read("test_coins"));
            assertEquals(count, read("test_milk"));
            assertEquals(count, read("test_planted"));
        }
    }
    @Test public void walAndSchemaThreeUpgradeKeepRecords() {
        GameSaveStore.applySnapshot(snapshot(37));
        GameSaveStore.close();
        SQLiteDatabase external = SQLiteDatabase.openDatabase(context.getDatabasePath(GameSaveStore.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        external.execSQL("ALTER TABLE save_slots DROP COLUMN pose_snapshot");
        external.execSQL("ALTER TABLE save_commits DROP COLUMN pose_snapshot");
        external.setVersion(3);
        external.close();
        GameSaveStore.initialize(context);
        assertEquals(37, read("test_milk"));
        external = SQLiteDatabase.openDatabase(context.getDatabasePath(GameSaveStore.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        assertEquals(4, external.getVersion());
        try (android.database.Cursor rows = external.rawQuery("PRAGMA journal_mode", null)) {
            assertTrue(rows.moveToFirst()); assertEquals("wal", rows.getString(0));
        }
        external.close();
    }
    @Test public void lockedWriterRetriesWithoutPartialState() throws Exception {
        GameSaveStore.applySnapshot(snapshot(7));
        SQLiteDatabase external = SQLiteDatabase.openDatabase(context.getDatabasePath(GameSaveStore.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        external.beginTransactionNonExclusive();
        LinkedBlockingQueue<AsyncSaveQueue.Result> results = new LinkedBlockingQueue<>();
        AsyncSaveQueue queue = new AsyncSaveQueue(results::add);
        try {
            queue.submit(snapshot(9), 1, null);
            AsyncSaveQueue.Result failed = results.poll(10, TimeUnit.SECONDS);
            assertNotNull("a locked write must return a bounded failure", failed);
            assertNotNull(failed.failure);
        } finally {
            external.endTransaction(); external.close();
            assertTrue("writer did not recover after lock release", queue.awaitIdle(10000));
            queue.close();
        }
        GameSaveStore.reopen();
        assertEquals(9, read("test_milk"));
    }
    @Test public void completeSnapshotReplaysAndReopenKeepAllFiveRecords() throws Exception {
        GameSaveStore.beginCapture(true);
        for (String name : new String[] {"RMSHeader", "save_gameplay", "save_farm0", "pending_rewards_v1", "entity_pose_v1"}) {
            byte[] data = new byte[35]; ByteBuffer.wrap(data).putInt(71);
            GameSaveStore.write(name, data, 0, data.length);
        }
        // Keep the timing fixture compilable with the unchanged HEAD classpath.
        GameSaveStore.class.getMethod("overrideCapturedLanguage", int.class).invoke(null, 16);
        GameSaveStore.Snapshot full = GameSaveStore.finishCapture();
        GameSaveStore.applySnapshot(full);
        long generation = GameSaveStore.generation();
        GameSaveStore.applySnapshot(full);
        assertEquals(generation, GameSaveStore.generation());
        GameSaveStore.reopen();
        assertEquals(16, GameSaveStore.read("RMSHeader")[4]);
        for (String name : new String[] {"RMSHeader", "save_gameplay", "save_farm0", "pending_rewards_v1", "entity_pose_v1"}) assertEquals(71, read(name));
    }
    @Test public void deferredLanguageSurvivesDirectOptionsHeaderWrites() throws Exception {
        byte[] oldHeader = new byte[35]; oldHeader[4] = 0;
        GameSaveStore.class.getMethod("setLanguageWriteOverride", Integer.class).invoke(null, 16);
        GameSaveStore.write("RMSHeader", oldHeader, 0, oldHeader.length);
        assertEquals("caller-owned buffer mutated", 0, oldHeader[4]);
        assertEquals(16, GameSaveStore.read("RMSHeader")[4]);
        GameSaveStore.reopen();
        assertEquals(16, GameSaveStore.read("RMSHeader")[4]);
        GameSaveStore.class.getMethod("setLanguageWriteOverride", Integer.class).invoke(null, new Object[] {null});
        GameSaveStore.write("RMSHeader", oldHeader, 0, oldHeader.length);
        GameSaveStore.reopen();
        assertEquals(0, GameSaveStore.read("RMSHeader")[4]);
    }
    @Test public void storageOnlyTimingSample() {
        // Identical host workload for HEAD and changed source. Not farm/FPS data.
        long[] capture = new long[100], write = new long[100];
        for (int iteration = -5; iteration < 100; iteration++) {
            int n = iteration + 6;
            long start = System.nanoTime();
            GameSaveStore.beginAuxCapture();
            for (String name : new String[] {"test_coins", "test_milk", "test_planted"}) {
                byte[] data = new byte[32768];
                ByteBuffer.wrap(data).putInt(n);
                GameSaveStore.write(name, data, 0, data.length);
            }
            GameSaveStore.Snapshot batch = GameSaveStore.finishCapture();
            long captured = System.nanoTime();
            GameSaveStore.applySnapshot(batch);
            if (iteration >= 0) { capture[iteration] = captured - start; write[iteration] = System.nanoTime() - captured; }
        }
        java.util.Arrays.sort(capture); java.util.Arrays.sort(write);
        assertEquals(105, read("test_milk"));
        System.out.printf(java.util.Locale.ROOT,
            "STORAGE_ONLY transactions=100 changed_rows=300 payload_bytes_per_batch=98304 capture_p50_ms=%.3f capture_p95_ms=%.3f writer_p50_ms=%.3f writer_p95_ms=%.3f%n",
            capture[49]/1e6,capture[94]/1e6,write[49]/1e6,write[94]/1e6);
    }
    @Test public void failedTransactionDoesNotPublishPartialRowsAndRetryIsIdempotent() {
        GameSaveStore.applySnapshot(snapshot(7));
        SQLiteDatabase external = SQLiteDatabase.openDatabase(context.getDatabasePath(GameSaveStore.DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
        // Fail the commit metadata after every data row was written; all must roll back.
        external.execSQL("CREATE TRIGGER reject_batch BEFORE INSERT ON save_commits BEGIN SELECT RAISE(ABORT,'injected'); END");
        GameSaveStore.Snapshot newer = snapshot(9);
        try { GameSaveStore.applySnapshot(newer); fail("injected transaction must fail"); }
        catch (GameSaveStore.Failure expected) { /* no partial commit */ }
        try (android.database.Cursor rows = external.rawQuery("SELECT payload FROM save_records WHERE store_name LIKE 'test_%'", null)) {
            int count = 0;
            while (rows.moveToNext()) { assertEquals(7, ByteBuffer.wrap(rows.getBlob(0)).getInt()); count++; }
            assertEquals(3, count);
        }
        external.execSQL("DROP TRIGGER reject_batch");
        external.close();
        GameSaveStore.applySnapshot(newer);
        GameSaveStore.applySnapshot(newer);
        GameSaveStore.reopen();
        assertEquals(9, read("test_coins"));
        assertEquals(9, read("test_milk"));
        assertEquals(9, read("test_planted"));
    }
}
