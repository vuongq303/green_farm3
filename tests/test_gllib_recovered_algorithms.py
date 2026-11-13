"""Differential tests against the original APK's converted method bytecode.

Requires the local dex2jar artifact, JDK and SDK28; compiles current source on
every run. This does not load Android, replace game code or modify the APK: fixtures retain the
actual method bytecode but omit unrelated class initialization and Android base
classes. Both implementations receive identical field state.
"""

from pathlib import Path
import lzma
import os
import random
import struct
import subprocess
import tempfile
import unittest
import zipfile
import sys
sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "tools"))
from kotlin_test_tools import compile_mixed
from game_source_context import qualified_package, core_source_directory, kotlin_source_directory, type_name, uses_verified_names
from relocate_compile_view import rewrite_class
from test_kotlin_core_callbacks import identity_normalized_reference


CLASS = "com/gameloft/android/ANMP/GloftGF2F/S800x480/GLLib.class"
METHODS = {
    ("a", "(JILjava/lang/String;)Ljava/lang/String;"), ("a", "([CI)V"),
    ("g", "(C)I"), ("h", "(C)C"), ("i", "([BI)V"), ("dh", "(I)I"),
    ("a", "(IB)I"), ("di", "(I)I"), ("O", "(II)I"), ("P", "(II)I"),
    ("Q", "(II)I"), ("dj", "(I)I"), ("a", "(IIII[BI)V"),
    ("b", "(Ljava/io/InputStream;)I"), ("c", "(Ljava/io/InputStream;)I"),
    ("h", "(Ljava/io/InputStream;)I"), ("i", "(Ljava/io/InputStream;)I"),
    ("g", "(Ljava/io/InputStream;)I"),
}
FIELDS = {"Sl", "Sm", "Sn", "So", "Sp", "Sq", "Sr", "Ss", "Tr", "UY", "UZ"}


def constant_pool(data):
    cp_count = struct.unpack_from(">H", data, 8)[0]
    cp = {}
    pos, index = 10, 1
    while index < cp_count:
        tag = data[pos]
        pos += 1
        if tag == 1:
            length = struct.unpack_from(">H", data, pos)[0]
            cp[index] = data[pos + 2:pos + 2 + length].decode("utf-8", errors="replace")
            pos += 2 + length
        elif tag in (3, 4): pos += 4
        elif tag in (5, 6): pos += 8; index += 1
        elif tag in (7, 8, 16, 19, 20):
            cp[index] = (tag, struct.unpack_from(">H", data, pos)[0]); pos += 2
        elif tag in (9, 10, 11, 12, 17, 18): pos += 4
        elif tag == 15: pos += 3
        else: raise ValueError(f"Unknown constant-pool tag {tag}")
        index += 1
    return cp, pos


def method_fixture(data):
    """Retain selected Code attributes unchanged in an isolated class shell."""
    cp, pos = constant_pool(data)
    header = data[:pos]
    _, this_class, _ = struct.unpack_from(">HHH", data, pos)
    object_class = next(i for i, value in cp.items()
                        if isinstance(value, tuple) and value[0] == 7 and cp[value[1]] == "java/lang/Object")
    interface_count = struct.unpack_from(">H", data, pos + 6)[0]
    pos += 8 + 2 * interface_count

    def members(position):
        count = struct.unpack_from(">H", data, position)[0]
        position += 2
        result = []
        for _ in range(count):
            start = position
            flags, name, descriptor, attributes = struct.unpack_from(">HHHH", data, position)
            position += 8
            for _ in range(attributes):
                length = struct.unpack_from(">I", data, position + 2)[0]
                position += 6 + length
            result.append((cp[name], cp[descriptor], flags, data[start:position]))
        return result, position

    fields, pos = members(pos)
    methods, pos = members(pos)
    selected_fields = [struct.pack(">H", flags & ~0x10) + raw[2:]
                       for name, _, flags, raw in fields if name in FIELDS]
    optional = {("propagateChecked", "(Ljava/lang/Throwable;)Ljava/lang/RuntimeException;")}
    selected_methods = [raw for name, descriptor, _, raw in methods if (name, descriptor) in METHODS | optional]
    if not METHODS.issubset({(name, descriptor) for name, descriptor, _, _ in methods}):
        raise ValueError("Fixture did not find every original method descriptor")
    return (header + struct.pack(">HHHH", 0x21, this_class, object_class, 0)
            + struct.pack(">H", len(selected_fields)) + b"".join(selected_fields)
            + struct.pack(">H", len(selected_methods)) + b"".join(selected_methods)
            + b"\0\0")


RUNNER = r"""
import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
public class GLLibAlgorithmComparison {
    static Class<?> load(String path) throws Exception {
        URLClassLoader loader = new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()}, null);
        return Class.forName("com.gameloft.android.ANMP.GloftGF2F.S800x480.GLLib", true, loader);
    }
    static Method method(Class<?> type, Class<?>... args) throws Exception {
        Method m = type.getDeclaredMethod("a", args); m.setAccessible(true); return m;
    }
    static void set(Class<?> type, String name, Object value) throws Exception {
        Field f = type.getDeclaredField(name); f.setAccessible(true); f.set(null, value);
    }
    static Object get(Class<?> type, String name) throws Exception {
        Field f = type.getDeclaredField(name); f.setAccessible(true); return f.get(null);
    }
    public static void main(String[] args) throws Exception {
        Class<?> original = load(args[0]), restored = load(args[1]);
        Random rng = new Random(1984);
        Method oldNumber = method(original, long.class, int.class, String.class);
        Method newNumber = method(restored, long.class, int.class, String.class);
        long[] edge = {Long.MIN_VALUE, -1000, -1, 0, 999, 1000, 1001, 9999, 10000, 1000000, Long.MAX_VALUE};
        int numberChecks = 0;
        for (int language = -2; language <= 24; language++) {
            for (String separator : new String[]{null, "", " ", "/"}) {
                for (int i = 0; i < 100; i++) {
                    long value = i < edge.length ? edge[i] : rng.nextLong() & Long.MAX_VALUE;
                    Object oldValue = oldNumber.invoke(null, value, language, separator);
                    Object newValue = newNumber.invoke(null, value, language, separator);
                    if (!Objects.equals(oldValue, newValue)) throw new AssertionError("Number mismatch " + value + "/" + language);
                    numberChecks++;
                }
            }
        }
        char[][] table = new char[128][2];
        for (int i = 0; i < table.length; i++) { table[i][0] = (char)(i % 6); table[i][1] = (char)(0xe000 + i * 4); }
        table[1604 - 1569][0] = 2;
        for (int alef : new int[]{1570,1571,1573,1575}) table[alef - 1569][0] = 1;
        set(original, "UY", table); set(restored, "UY", table);
        set(original, "UZ", 1569 + table.length - 1); set(restored, "UZ", 1569 + table.length - 1);
        Method oldShape = method(original, char[].class, int.class), newShape = method(restored, char[].class, int.class);
        for (int trial = 0; trial < 2000; trial++) {
            char[] before = new char[rng.nextInt(32)];
            for (int i = 0; i < before.length; i++) before[i] = (char)(1560 + rng.nextInt(140));
            char[] a = before.clone(), b = before.clone();
            int length = rng.nextInt(before.length + 1);
            oldShape.invoke(null, a, length); newShape.invoke(null, b, length);
            if (!Arrays.equals(a,b)) throw new AssertionError("Arabic mismatch at trial " + trial);
        }
        Method oldDecode = method(original, int.class,int.class,int.class,int.class,byte[].class,int.class);
        Method newDecode = method(restored, int.class,int.class,int.class,int.class,byte[].class,int.class);
        Path vectors = Paths.get(args[2]);
        for (int vector = 0; vector < 7; vector++) {
            byte[] packed = Files.readAllBytes(vectors.resolve(vector + ".lzma"));
            byte[] expected = Files.readAllBytes(vectors.resolve(vector + ".raw"));
            int property = packed[0] & 255, lc = property % 9, lp = property / 9 % 5, pb = property / 45;
            int modelSize = (768 << (lc + lp)) + 1846;
            for (Class<?> type : new Class<?>[]{original,restored}) {
                set(type,"Sq",new byte[expected.length]); set(type,"Ss",new short[modelSize]);
                Method decode = type == original ? oldDecode : newDecode;
                decode.invoke(null,modelSize*2,lc,lp,pb,packed.clone(),expected.length);
                if (!Arrays.equals((byte[])get(type,"Sq"),expected)) throw new AssertionError("LZMA output mismatch vector " + vector + " in " + type.getClassLoader());
            }
            for (String state : new String[]{"Sn","So","Sr","Sp"}) {
                if (!Objects.equals(get(original,state),get(restored,state))) throw new AssertionError("LZMA state mismatch " + state);
            }
            if (!Arrays.equals((short[])get(original,"Ss"),(short[])get(restored,"Ss"))) throw new AssertionError("LZMA probability state mismatch");
        }
        for (String name : new String[]{"b","c","h","i"}) {
            for (Class<?> type : new Class<?>[]{original,restored}) {
                Method read = type.getDeclaredMethod(name, InputStream.class); read.setAccessible(true);
                IOException marker = new IOException("original IO failure");
                InputStream broken = new InputStream() { @Override public int read() throws IOException { throw marker; } };
                try { read.invoke(null,broken); throw new AssertionError("IO failure was swallowed"); }
                catch (InvocationTargetException error) {
                    if (error.getTargetException() != marker) throw new AssertionError("checked exception instance was replaced: " + name + "/" + type.getClassLoader(), error.getTargetException());
                }
            }
        }
        System.out.println(numberChecks + " number-format cases; 2000 Arabic cases; 7 LZMA vectors; 4 IO exception paths match original bytecode.");
    }
}
"""


class GLLibRecoveredAlgorithmsTest(unittest.TestCase):
    def test_differential_original_bytecode_and_lzma_golden_payloads(self):
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        original = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        sdk = Path(os.environ.get("ANDROID_HOME", "/workspace/tooling/android-sdk"))
        android = sdk / "platforms/android-28/android.jar"
        project = Path(__file__).resolve().parents[1]
        if not original.exists() or not android.exists() or not (home / "bin/javac").exists():
            self.skipTest("Requires verified dex2jar original artifact, SDK28 and JDK")
        with tempfile.TemporaryDirectory(prefix="gllib-algorithms-") as temp:
            root = Path(temp)
            # Compile the current sources, never a stale earlier .class. The only
            # compile-view change hides a package/class collision from javac;
            # no runtime class name, descriptor or method code is changed.
            view = root / "compile-view.jar"
            reference = identity_normalized_reference(root)
            with zipfile.ZipFile(original) as pinned:
                old_bytes = pinned.read(CLASS)
            with zipfile.ZipFile(reference) as archive, zipfile.ZipFile(view, "w") as output:
                for entry in archive.infolist():
                    data = archive.read(entry)
                    if entry.filename == "javax/microedition/a/a.class":
                        _, offset = constant_pool(data)
                        flags = struct.unpack_from(">H", data, offset)[0]
                        data = data[:offset] + struct.pack(">H", flags & ~1) + data[offset + 2:]
                    output.writestr(entry, data)
            classes = root / "compiled"
            sources = core_source_directory()
            kotlin_sources = kotlin_source_directory()
            selected_kotlin = [kotlin_sources / "GameSaveStore.kt"]
            selected_java = [sources / (type_name('GLLib') + '.java')]
            for name in ('bm', 'bl'):
                kotlin = kotlin_sources / (name + '.kt')
                (selected_kotlin if kotlin.is_file() else selected_java).append(kotlin if kotlin.is_file() else sources / (name + '.java'))
            compile_mixed(selected_kotlin, selected_java, classes, classpath=[android, view])
            restored = classes / qualified_package().replace('.', '/') / (type_name('GLLib') + '.class')
            # Only class/package identities return to pinned reference names;
            # method instructions and constant-pool indices remain unchanged.
            restored_bytes = rewrite_class(restored.read_bytes(), reverse=True, rename_symbols=uses_verified_names())
            for label, data in (("original", old_bytes), ("restored", restored_bytes)):
                output = root / label / CLASS
                output.parent.mkdir(parents=True)
                output.write_bytes(method_fixture(data))
            vectors = root / "vectors"; vectors.mkdir()
            rng = random.Random(1984)
            samples = [b"", b"a", b"a" * 20000, b"abca" * 4096,
                       bytes(range(256)) * 5, rng.randbytes(4096),
                       (b"a farm grows vegetables and saves progress\n" * 500)]
            for index, raw in enumerate(samples):
                packed = lzma.compress(raw, format=lzma.FORMAT_ALONE)
                (vectors / f"{index}.lzma").write_bytes(packed + b"\0")
                (vectors / f"{index}.raw").write_bytes(raw)
            runner = root / "GLLibAlgorithmComparison.java"; runner.write_text(RUNNER)
            subprocess.run([str(home / "bin/javac"), str(runner)], check=True, capture_output=True, text=True)
            result = subprocess.run([str(home / "bin/java"), "-cp", str(root), "GLLibAlgorithmComparison",
                                     str(root / "original"), str(root / "restored"), str(vectors)],
                                    capture_output=True, text=True, timeout=60)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn("10800 number-format cases; 2000 Arabic cases; 7 LZMA vectors; 4 IO exception paths", result.stdout)


if __name__ == "__main__": unittest.main()
