"""Exercise the D8 annotation allowance through the merge tool's APK inputs."""
import json
import os
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / "tools"))
from android_build_tools import jdk_tool

LAMBDA_METHOD = "Lcom/android/tools/r8/annotations/LambdaMethod;"

FIXTURE = r"""
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import org.jf.dexlib2.AnnotationVisibility;
import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.Opcodes;
import org.jf.dexlib2.iface.ClassDef;
import org.jf.dexlib2.iface.Method;
import org.jf.dexlib2.iface.value.EncodedValue;
import org.jf.dexlib2.immutable.ImmutableAnnotation;
import org.jf.dexlib2.immutable.ImmutableAnnotationElement;
import org.jf.dexlib2.immutable.ImmutableClassDef;
import org.jf.dexlib2.immutable.ImmutableMethod;
import org.jf.dexlib2.immutable.ImmutableMethodImplementation;
import org.jf.dexlib2.immutable.instruction.ImmutableInstruction10x;
import org.jf.dexlib2.immutable.reference.ImmutableFieldReference;
import org.jf.dexlib2.immutable.value.ImmutableArrayEncodedValue;
import org.jf.dexlib2.immutable.value.ImmutableEnumEncodedValue;
import org.jf.dexlib2.writer.io.MemoryDataStore;
import org.jf.dexlib2.writer.pool.DexPool;

public final class D8LambdaFixture {
    private static final String CORE = "Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/";
    private static final String ANNOTATIONS = "Ljava/lang/annotation/";
    private static final String R8 = "Lcom/android/tools/r8/annotations/";

    private static ImmutableAnnotation annotation(String name, EncodedValue value) {
        return new ImmutableAnnotation(AnnotationVisibility.RUNTIME, ANNOTATIONS + name + ";",
                Collections.singleton(new ImmutableAnnotationElement("value", value)));
    }

    private static ImmutableEnumEncodedValue enumeration(String name, String constant) {
        String type = ANNOTATIONS + name + ";";
        return new ImmutableEnumEncodedValue(new ImmutableFieldReference(type, constant, type));
    }

    private static ClassDef lambdaAnnotation(String mode) {
        String owner = R8 + (mode.equals("unreviewed") ? "OtherAnnotation;" : "LambdaMethod;");
        List<Method> methods = new ArrayList<>();
        for (String name : Arrays.asList("holder", "method", "proto")) {
            String returnType = mode.equals("wrong-signature") && name.equals("proto")
                    ? "I" : "Ljava/lang/String;";
            methods.add(new ImmutableMethod(owner, name, Collections.emptyList(), returnType,
                    0x0401, Collections.emptySet(), Collections.emptySet(), null));
        }
        if (mode.equals("code")) {
            methods.add(new ImmutableMethod(owner, "extra", Collections.emptyList(), "V",
                    0x0009, Collections.emptySet(), Collections.emptySet(),
                    new ImmutableMethodImplementation(0,
                            Collections.singletonList(new ImmutableInstruction10x(Opcode.RETURN_VOID)),
                            Collections.emptyList(), Collections.emptyList())));
        }
        EncodedValue retention = enumeration("RetentionPolicy", "RUNTIME");
        EncodedValue target = new ImmutableArrayEncodedValue(
                Collections.singletonList(enumeration("ElementType", "TYPE")));
        return new ImmutableClassDef(owner, 0x3601, "Ljava/lang/Object;",
                Collections.singletonList(ANNOTATIONS + "Annotation;"), "D8$$SyntheticClass",
                Arrays.asList(annotation("Retention", retention), annotation("Target", target)),
                Collections.emptyList(), methods);
    }

    public static void main(String[] args) throws Exception {
        DexPool pool = new DexPool(Opcodes.forDexVersion(35));
        for (String core : Arrays.asList("GLLib", "ci", "GloftGF2F", "l", "g")) {
            pool.internClass(new ImmutableClassDef(CORE + core + ";", 1, "Ljava/lang/Object;",
                    Collections.emptyList(), null, Collections.emptyList(), Collections.emptyList(),
                    Collections.emptyList()));
        }
        if (!args[0].equals("core")) pool.internClass(lambdaAnnotation(args[0]));
        MemoryDataStore bytes = new MemoryDataStore();
        try (ZipOutputStream apk = new ZipOutputStream(Files.newOutputStream(Path.of(args[1])))) {
            pool.writeTo(bytes);
            apk.putNextEntry(new ZipEntry("classes.dex"));
            apk.write(bytes.getData());
            apk.closeEntry();
        } finally {
            bytes.close();
        }
    }
}
"""


class D8LambdaMergeTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        gradle_home = Path(os.environ.get("GRADLE_USER_HOME", Path.home() / ".gradle"))
        cache = gradle_home / "caches/modules-2/files-2.1"
        jars = sorted(cache.glob("org.smali/dexlib2/2.5.2/*/dexlib2-2.5.2.jar"))
        guava = sorted(cache.glob("com.google.guava/guava/27.1-android/*/guava-27.1-android.jar"))
        if not jars or not guava:
            raise unittest.SkipTest("Requires cached Gradle dexlib2 and its Guava dependency")
        cls.java = str(jdk_tool("java"))
        javac = str(jdk_tool("javac"))
        temp = tempfile.TemporaryDirectory(prefix="d8-lambda-merge-")
        cls.addClassCleanup(temp.cleanup)
        cls.directory = Path(temp.name)
        fixture = cls.directory / "D8LambdaFixture.java"
        fixture.write_text(FIXTURE, encoding="utf-8")
        cls.classpath = os.pathsep.join(map(str, (cls.directory, jars[0], guava[0])))
        result = subprocess.run(
            [javac, "-cp", cls.classpath, "-d", str(cls.directory), str(fixture),
             str(ROOT / "tools/MergeCoreDex.java"), str(ROOT / "tools/RelocateGamePackage.java")],
            capture_output=True, text=True, timeout=60,
        )
        if result.returncode != 0:
            raise AssertionError(result.stderr)

    def run_tool(self, name, *args):
        return subprocess.run(
            [self.java, "-cp", self.classpath, name, *map(str, args)],
            capture_output=True, text=True, timeout=30,
        )

    def merge(self, original_mode, overlay_mode):
        with tempfile.TemporaryDirectory(dir=self.directory) as temporary:
            path = Path(temporary)
            original, overlay, dex = (path / name for name in ("original.apk", "overlay.apk", "merged.dex"))
            for mode, apk in ((original_mode, original), (overlay_mode, overlay)):
                result = self.run_tool("D8LambdaFixture", mode, apk)
                self.assertEqual(result.returncode, 0, result.stderr)
            result = self.run_tool("MergeCoreDex", original, overlay, dex)
            report = dex.with_suffix(".dex.json")
            if result.returncode == 0:
                self.assertTrue(dex.is_file())
                return result, json.loads(report.read_text(encoding="utf-8"))
            self.assertFalse(dex.exists())
            self.assertFalse(report.exists())
            return result, None

    def test_allow_verified_d8_annotation_and_preserve_dex_and_provenance(self):
        result, report = self.merge("core", "valid")
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertEqual(report["added_classes"], [LAMBDA_METHOD])
        self.assertEqual(report["dex_version"], 35)
        self.assertTrue(report["original_non_overlay_semantics_verified"])
        self.assertTrue(report["compiled_overlay_semantics_verified"])

    def test_reject_malformed_annotation_and_existing_original_definition(self):
        for original, overlay in (("core", "wrong-signature"), ("core", "code"), ("valid", "valid")):
            with self.subTest(original=original, overlay=overlay):
                result, report = self.merge(original, overlay)
                self.assertIsNone(report)
                self.assertIn("Unexpected definition of D8 lambda annotation", result.stderr)

    def test_reject_unreviewed_class_in_r8_namespace(self):
        result, report = self.merge("core", "unreviewed")
        self.assertIsNone(report)
        self.assertIn("Overlay class is outside whitelist or protected", result.stderr)


if __name__ == "__main__":
    unittest.main()
