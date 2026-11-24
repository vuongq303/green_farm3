"""Compare the recovered request lookup with the original synchronized Code.

The original method is isolated from Android initialization; its Code attribute
is retained unchanged. These fixtures are temporary and are never packaged.
"""

from pathlib import Path
import os
import shutil
import subprocess
import tempfile
import unittest
import zipfile

import test_gllib_recovered_algorithms as fixtures
from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
import sys
sys.path.insert(0, str(ROOT / "tools"))
from game_source_context import legacy_core_source, current_source_path

PACKAGE = "com/gameloft/android/ANMP/GloftGF2F/S800x480"
RUNNER = r"""
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.*;

public class RequestLookupComparison {
    static final String NAME = "com.gameloft.android.ANMP.GloftGF2F.S800x480.s";
    static final int[] VALUES = {-1, 0, 1, Integer.MAX_VALUE};
    static final int[] TARGETS = {Integer.MIN_VALUE, -2, -1, 0, 1, 2, Integer.MAX_VALUE};

    static Class<?> load(String path) throws Exception {
        return Class.forName(NAME, true,
                new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()}, null));
    }
    static Field slots(Class<?> type) throws Exception {
        Field field = type.getDeclaredField("MQ");
        field.setAccessible(true);
        return field;
    }
    static Method lookup(Class<?> type) throws Exception {
        Method method = type.getDeclaredMethod("cz", int.class);
        method.setAccessible(true);
        return method;
    }
    static String run(Class<?> type, int[] values, int target) throws Exception {
        int[] copy = values == null ? null : values.clone();
        slots(type).set(null, copy);
        String result;
        try {
            result = "index=" + lookup(type).invoke(null, target);
        } catch (InvocationTargetException error) {
            result = "failure=" + error.getCause().getClass().getName();
        }
        if (!Arrays.equals(copy, values)) throw new AssertionError("Lookup changed request IDs");
        if (Thread.holdsLock(type)) throw new AssertionError("Lookup leaked its class monitor");
        return result;
    }
    static int compare(Class<?> original, Class<?> restored, int[] values) throws Exception {
        int count = 0;
        for (int target : TARGETS) {
            String before = run(original, values, target), after = run(restored, values, target);
            if (!before.equals(after)) throw new AssertionError(Arrays.toString(values)
                    + "/" + target + "\noriginal=" + before + "\nrestored=" + after);
            String expected;
            if (values == null) expected = "failure=java.lang.NullPointerException";
            else {
                int first = -1;
                for (int i = 0; i < values.length; i++) if (values[i] == target) {first = i; break;}
                expected = "index=" + first;
            }
            if (!after.equals(expected)) throw new AssertionError("First-match contract: " + after);
            count++;
        }
        return count;
    }
    static void checkMonitor(Class<?> type) throws Exception {
        // A failed lookup must release the monitor before another thread enters.
        run(type, null, 9);
        AtomicReference<Throwable> failure = new AtomicReference<>();
        CountDownLatch started = new CountDownLatch(1), done = new CountDownLatch(1);
        Thread worker = new Thread(() -> {
            started.countDown();
            try {
                Object value = lookup(type).invoke(null, 9);
                if (!Integer.valueOf(0).equals(value)) throw new AssertionError("Read occurred before monitor acquisition");
                if (Thread.holdsLock(type)) throw new AssertionError("Worker leaked class monitor");
            } catch (Throwable error) {
                failure.set(error);
            } finally {
                done.countDown();
            }
        }, "request-lookup-monitor");
        worker.setDaemon(true);
        synchronized (type) {
            slots(type).set(null, null);
            worker.start();
            if (!started.await(5, TimeUnit.SECONDS)) throw new AssertionError("Worker did not start");
            long deadline = System.nanoTime() + TimeUnit.SECONDS.toNanos(5);
            while (worker.getState() != Thread.State.BLOCKED && done.getCount() != 0
                    && System.nanoTime() < deadline) LockSupport.parkNanos(1000000L);
            if (worker.getState() != Thread.State.BLOCKED || done.getCount() != 1)
                throw new AssertionError("Lookup did not wait on its class monitor", failure.get());
            slots(type).set(null, new int[]{9});
        }
        if (!done.await(5, TimeUnit.SECONDS)) throw new AssertionError("Lookup retained class monitor after failure");
        if (failure.get() != null) throw new AssertionError("Contended lookup failed", failure.get());
    }
    public static void main(String[] args) throws Exception {
        Class<?> original = load(args[0]), restored = load(args[1]);
        int count = 0;
        for (int length = 0, combinations = 1; length <= 4; length++, combinations *= VALUES.length) {
            for (int code = 0; code < combinations; code++) {
                int[] values = new int[length];
                for (int i = 0, rest = code; i < length; i++, rest /= VALUES.length)
                    values[i] = VALUES[rest % VALUES.length];
                count += compare(original, restored, values);
            }
        }
        count += compare(original, restored, null);
        for (int[] values : new int[][]{{Integer.MIN_VALUE}, {2, -2, 2}, {0, 0, 0, 0, 0}})
            count += compare(original, restored, values);
        checkMonitor(original);
        checkMonitor(restored);
        System.out.println("PASS: " + count + " original/recovered first-match outcomes; null-failure release and class-monitor contention match");
    }
}
"""


class RequestLookupTest(unittest.TestCase):
    def test_original_first_match_and_monitor_contract(self):
        jar = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", ROOT / ".local/bytecode-tools/greenfarm-original.jar"))
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        java, javac = home / "bin/java", home / "bin/javac"
        if not jar.exists() or not java.is_file() or not javac.is_file():
            self.skipTest("Requires verified original dex2jar artifact and JDK")
        text = legacy_core_source('s')
        with tempfile.TemporaryDirectory(prefix="s-lookup-differential-") as temp:
            temporary = Path(temp)
            source = temporary / "source" / PACKAGE
            source.mkdir(parents=True)
            (source / "s.java").write_text(
                "package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n"
                "public class s {private static int[] MQ;"
                + extract_method(text, "private static synchronized int cz(int i)") + "}")
            restored = temporary / "restored"
            restored.mkdir()
            result = subprocess.run([str(javac), "-source", "8", "-target", "8", "-d", str(restored), str(source / "s.java")], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            original = temporary / "original"
            shutil.copytree(restored, original)
            old_methods, old_fields = fixtures.METHODS, fixtures.FIELDS
            try:
                fixtures.METHODS, fixtures.FIELDS = {("cz", "(I)I")}, {"MQ"}
                with zipfile.ZipFile(jar) as archive:
                    fixture = fixtures.method_fixture(archive.read(PACKAGE + "/s.class"))
            finally:
                fixtures.METHODS, fixtures.FIELDS = old_methods, old_fields
            (original / PACKAGE / "s.class").write_bytes(fixture)
            runner = temporary / "RequestLookupComparison.java"
            runner.write_text(RUNNER)
            subprocess.run([str(javac), str(runner)], check=True, capture_output=True, text=True)
            result = subprocess.run([str(java), "-cp", str(temporary), "RequestLookupComparison", str(original), str(restored)], capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn("PASS: 2415", result.stdout)


if __name__ == "__main__":
    unittest.main()
