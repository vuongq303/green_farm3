"""Exercise the actual recovered RecordStore writer with a local file adapter.

The Android context adapter alone is replaced. The writer, CRC/long encoding,
backup generation lookup and binary-search helpers are extracted verbatim from
the production source so these checks cannot pass against a different writer.
"""

from pathlib import Path
import os
import shutil
import subprocess
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
SOURCE = ROOT / "app/src/main/java/javax/microedition/b/b.java"


def method(text, signature):
    start = text.index(signature)
    cursor = text.index("{", start)
    depth = 1
    end = cursor + 1
    while depth:
        if text[end] == "{":
            depth += 1
        elif text[end] == "}":
            depth -= 1
        end += 1
    return text[start:end]


HARNESS = r"""
import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.zip.CRC32;

public class RecordStoreWriterHarness {
    private String cpa = "farm";
    private int cpd;
    public static int cpe;
    private static Path root;
    private static boolean failWrite;
    private static boolean failClose;
    private static boolean closed;
    private static final class MIDlet { static boolean coj, cok; }

    // File adapters correspond to the original internal-storage context methods.
    private static FileInputStream hc(String name) throws FileNotFoundException {
        return new FileInputStream(root.resolve(name).toFile());
    }
    private static FileOutputStream jj(String name) throws FileNotFoundException {
        if (name.contains("_saved") && (failWrite || failClose)) {
            return new FileOutputStream(root.resolve(name).toFile()) {
                @Override public void write(byte[] bytes, int offset, int length) throws IOException {
                    if (failWrite) throw new IOException("write failure");
                    super.write(bytes, offset, length);
                }
                @Override public void close() throws IOException {
                    closed = true;
                    super.close();
                    if (failClose) throw new IOException("close failure");
                }
            };
        }
        return new FileOutputStream(root.resolve(name).toFile());
    }
    private static boolean jk(String name) { return Files.exists(root.resolve(name)); }
    private static boolean deleteFile(String name) {
        try { return Files.deleteIfExists(root.resolve(name)); }
        catch (IOException failure) { return false; }
    }

    /* PRODUCTION_METHODS */

    private static void check(boolean value, String message) {
        if (!value) throw new AssertionError(message);
    }
    private static byte[] read(String name) throws IOException { return Files.readAllBytes(root.resolve(name)); }
    private static void seed(String name) throws IOException { Files.write(root.resolve(name), new byte[8]); }
    private static void expect(byte[] actual, byte[] expected, String label) {
        check(Arrays.equals(actual, expected), label + ": " + Arrays.toString(actual));
    }
    private static byte[] crc(byte[] bytes, int length) {
        CRC32 value = new CRC32();
        value.update(bytes, 0, length);
        long number = value.getValue();
        // Golden serialization is the legacy format: an unsigned CRC32 in BE64.
        return java.nio.ByteBuffer.allocate(8).putLong(number).array();
    }
    public static void main(String[] args) throws Exception {
        root = Paths.get(args[1]);
        RecordStoreWriterHarness store = new RecordStoreWriterHarness();
        byte[] data = new byte[]{10, 20, 30, 40, 50};
        switch (args[0]) {
            case "format":
                store.b(257, data, 0, data.length);
                expect(read("farm_257_saved1"), new byte[]{1, 0, 0, 0, 5, 10, 20, 30, 40, 50}, "record format");
                expect(read("farm_257_CRC1"), crc(data, data.length), "CRC format");
                check(store.cpd == 5, "writer updates cpd");
                check(!jk("farm_257"), "writer does not invent a canonical record write");
                break;
            case "offset":
                store.b(1, data, 1, 3);
                expect(read("farm_1_saved1"), new byte[]{1, 0, 0, 0, 3, 20, 30, 40}, "slice payload");
                expect(read("farm_1_CRC1"), crc(data, 3), "original offset-independent CRC");
                check(!Arrays.equals(read("farm_1_CRC1"), crc(new byte[]{20,30,40}, 3)), "legacy CRC offset quirk stays visible");
                break;
            case "retention":
                cpe = 2;
                for (int i = 0; i < 3; i++) store.b(1, data, 0, data.length);
                check(!jk("farm_1_saved1") && jk("farm_1_saved2") && jk("farm_1_saved3"), "two backups retained");
                check(jk("farm_1_CRC1") && jk("farm_1_CRC2") && jk("farm_1_CRC3"), "CRC deletion waits for checkpoint");
                break;
            case "checkpoint":
                cpe = 2;
                for (int i = 1; i <= 624; i++) seed("farm_1_CRC" + i);
                seed("farm_1_saved623"); seed("farm_1_saved624");
                store.b(1, data, 0, data.length);
                check(jk("farm_1_CRC1") && !jk("farm_1_CRC2") && !jk("farm_1_CRC622"), "625-generation sparse checkpoint cleanup");
                check(jk("farm_1_CRC623") && jk("farm_1_CRC624") && jk("farm_1_CRC625"), "recent CRCs retained");
                check(!jk("farm_1_saved623") && jk("farm_1_saved624") && jk("farm_1_saved625"), "checkpoint backup retention");
                break;
            case "cap":
                cpe = 2;
                seed("farm_1_CRC1"); seed("farm_1_CRC9999"); seed("farm_1_CRC10000");
                seed("farm_1_saved9998"); seed("farm_1_saved10000");
                store.b(1, data, 0, data.length);
                expect(read("farm_1_saved10000"), new byte[]{1,0,0,0,5,10,20,30,40,50}, "generation 10000 is overwritten");
                check(jk("farm_1_CRC1") && jk("farm_1_CRC9999"), "checkpoint cleanup disabled at generation cap");
                check(!jk("farm_1_saved9998"), "normal retention still applies at cap");
                break;
            case "demo":
                MIDlet.coj = MIDlet.cok = true;
                store.cpd = 99;
                store.b(1, data, 0, data.length);
                check(store.cpd == 99, "demo guard does not mutate cpd");
                try (java.util.stream.Stream<Path> entries = Files.list(root)) {
                    check(entries.count() == 0, "demo guard performs no writes");
                }
                break;
            case "write_failure":
                failWrite = true;
                try { store.b(1, data, 0, data.length); throw new AssertionError("write should fail"); }
                catch (IOException failure) { check(failure.getMessage().equals("write failure"), "write exception retained"); }
                check(closed, "stream closed after failed payload write");
                check(jk("farm_1_CRC1"), "original CRC-before-data ordering retained on failure");
                break;
            case "close_failure":
                failWrite = failClose = true;
                try { store.b(1, data, 0, data.length); throw new AssertionError("close should fail"); }
                catch (IOException failure) { check(failure.getMessage().equals("close failure"), "finally close exception replaces write exception as in original"); }
                check(closed, "close attempted");
                break;
            default: throw new AssertionError("unknown test");
        }
    }
}
"""


class RecordStoreWriterTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        cls.javac = str(home / "bin/javac") if (home / "bin/javac").exists() else shutil.which("javac")
        cls.java = str(home / "bin/java") if (home / "bin/java").exists() else shutil.which("java")
        if not cls.javac or not cls.java:
            raise unittest.SkipTest("Requires a JDK to compile the actual recovered writer")
        cls.temp = tempfile.TemporaryDirectory(prefix="recordstore-writer-")
        cls.path = Path(cls.temp.name)
        source = SOURCE.read_text()
        methods = "\n".join(method(source, signature) for signature in (
            "private void b(int recordId,", "private byte[] S(long ",
            "private void a(String str, byte[]", "private int pj(int ", "private int pk(int ",
        ))
        (cls.path / "RecordStoreWriterHarness.java").write_text(HARNESS.replace("/* PRODUCTION_METHODS */", methods))
        result = subprocess.run([cls.javac, "-d", str(cls.path), str(cls.path / "RecordStoreWriterHarness.java")],
                                capture_output=True, text=True)
        if result.returncode:
            cls.temp.cleanup()
            raise AssertionError(result.stderr)

    @classmethod
    def tearDownClass(cls):
        cls.temp.cleanup()

    def run_case(self, name):
        with tempfile.TemporaryDirectory(prefix="recordstore-files-") as directory:
            result = subprocess.run([self.java, "-cp", str(self.path), "RecordStoreWriterHarness", name, directory],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

    def test_original_record_and_crc_file_format(self): self.run_case("format")
    def test_payload_offset_and_original_crc_quirk(self): self.run_case("offset")
    def test_backup_retention_preserves_crc_until_checkpoint(self): self.run_case("retention")
    def test_sparse_crc_checkpoints_every_625_generations(self): self.run_case("checkpoint")
    def test_generation_10000_cap_does_not_run_crc_cleanup(self): self.run_case("cap")
    def test_demo_guard_performs_no_io(self): self.run_case("demo")
    def test_failed_write_still_closes_stream_and_propagates(self): self.run_case("write_failure")
    def test_close_failure_replaces_inflight_exception(self): self.run_case("close_failure")


if __name__ == "__main__":
    unittest.main()
