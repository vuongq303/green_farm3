"""Exercise the real merged DEX and adversarial fixtures without changing the app."""

import hashlib
import json
import os
from pathlib import Path
import shutil
import subprocess
import tempfile
import unittest


ROOT = Path(__file__).resolve().parents[1]
ORIGINAL = "https://eve.gameloft.com/"
LOCAL = "http://10.0.2.2:8787/"
OWNER = "Lcom/gameloft/a/b/a;"
METHOD = "aL(Ljava/lang/String;)Ljava/lang/String;"

ORACLE = r"""
import java.nio.file.*;
import java.util.*;
import org.jf.dexlib2.*;
import org.jf.dexlib2.dexbacked.*;
import org.jf.dexlib2.iface.*;
import org.jf.dexlib2.iface.instruction.*;
import org.jf.dexlib2.iface.reference.*;
import org.jf.dexlib2.immutable.*;
import org.jf.dexlib2.immutable.instruction.*;
import org.jf.dexlib2.immutable.reference.*;
import org.jf.dexlib2.rewriter.*;
import org.jf.dexlib2.writer.io.*;
import org.jf.dexlib2.writer.pool.*;

public final class BackendEndpointOracle {
    static final String OWNER="Lcom/gameloft/a/b/a;", OLD="https://eve.gameloft.com/";
    static final Opcodes OPS=Opcodes.forDexVersion(35);
    static byte[] canonical(Collection<? extends ClassDef> classes) throws Exception {
        DexPool pool=new DexPool(OPS);
        TreeMap<String,ClassDef> sorted=new TreeMap<>();
        for(ClassDef c:classes) sorted.put(c.getType(),c);
        for(ClassDef c:sorted.values()) pool.internClass(c);
        MemoryDataStore data=new MemoryDataStore();
        try {pool.writeTo(data);return data.getData();} finally {data.close();}
    }
    static TreeMap<String,ClassDef> read(String path) throws Exception {
        TreeMap<String,ClassDef> result=new TreeMap<>();
        for(ClassDef c:new DexBackedDexFile(OPS,Files.readAllBytes(Paths.get(path))).getClasses())
            if(result.put(c.getType(),c)!=null) throw new AssertionError("duplicate class");
        return result;
    }
    static Method target(ClassDef c) {
        Method found=null;
        for(Method m:c.getMethods()) if(m.getName().equals("aL")
                &&m.getParameterTypes().equals(Collections.singletonList("Ljava/lang/String;"))
                &&m.getReturnType().equals("Ljava/lang/String;")) {
            if(found!=null) throw new AssertionError("duplicate target"); found=m;
        }
        if(found==null) throw new AssertionError("missing target"); return found;
    }
    static String literal(Instruction i) {
        if(i instanceof ReferenceInstruction && ((ReferenceInstruction)i).getReference() instanceof StringReference)
            return ((StringReference)((ReferenceInstruction)i).getReference()).getString();
        return null;
    }
    static Method fixtureMethod(String owner,String name,String value,boolean jumbo,boolean duplicate) {
        List<Instruction> code=new ArrayList<>();
        for(int n=0;n<(duplicate?2:1);n++) code.add(jumbo
            ?new ImmutableInstruction31c(Opcode.CONST_STRING_JUMBO,0,new ImmutableStringReference(value))
            :new ImmutableInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference(value)));
        code.add(new ImmutableInstruction11x(Opcode.RETURN_OBJECT,0));
        return new ImmutableMethod(owner,name,Collections.singletonList(
            new ImmutableMethodParameter("Ljava/lang/String;",Collections.emptySet(),"route")),"Ljava/lang/String;",
            10,Collections.emptySet(),Collections.emptySet(),
            new ImmutableMethodImplementation(2,code,Collections.emptyList(),Collections.emptyList()));
    }
    static void fixture(String mode,String path) throws Exception {
        String owner=mode.equals("wrong_owner")?"Lwrong/Owner;":OWNER;
        String name=mode.equals("wrong_method")?"other":"aL";
        String value=mode.equals("missing_literal")?"https://different.example/":OLD;
        List<Method> methods=new ArrayList<>();
        methods.add(fixtureMethod(owner,name,value,!mode.equals("narrow"),mode.equals("duplicate_literal")));
        if(mode.equals("extra_owner_literal")) methods.add(fixtureMethod(owner,"extra",OLD,true,false));
        if(mode.equals("missing_method")) methods.clear();
        ClassDef c=new ImmutableClassDef(owner,1,"Ljava/lang/Object;",Collections.emptyList(),
            "Fixture.java",Collections.emptySet(),Collections.emptyList(),methods);
        Files.write(Paths.get(path),canonical(Collections.singletonList(c)));
    }
    public static void main(String[] args) throws Exception {
        if(args[0].equals("fixture")) {fixture(args[1],args[2]);return;}
        TreeMap<String,ClassDef> before=read(args[1]), after=read(args[2]);
        if(!before.keySet().equals(after.keySet())) throw new AssertionError("class set changed");
        ClassDef oldOwner=before.remove(OWNER), newOwner=after.remove(OWNER);
        if(!Arrays.equals(canonical(before.values()),canonical(after.values()))) throw new AssertionError("other classes changed");
        List<Instruction> oldCode=new ArrayList<>(),newCode=new ArrayList<>();
        for(Instruction i:target(oldOwner).getImplementation().getInstructions()) oldCode.add(i);
        for(Instruction i:target(newOwner).getImplementation().getInstructions()) newCode.add(i);
        if(oldCode.size()!=newCode.size()) throw new AssertionError("instruction count changed");
        int changes=0;
        for(int n=0;n<oldCode.size();n++) {
            Instruction a=oldCode.get(n),b=newCode.get(n);
            if(!Objects.equals(literal(a),literal(b))) {
                changes++;
                if(!OLD.equals(literal(a))||!args[3].equals(literal(b))||a.getOpcode()!=b.getOpcode()
                    ||a.getCodeUnits()!=b.getCodeUnits()
                    ||((OneRegisterInstruction)a).getRegisterA()!=((OneRegisterInstruction)b).getRegisterA())
                    throw new AssertionError("unexpected instruction change");
            }
        }
        if(changes!=1) throw new AssertionError("expected one changed literal");
        // Independent rewriter-based reversal, including fields, annotations, debug and exception metadata.
        DexRewriter reverse=new DexRewriter(new RewriterModule() {
            @Override public Rewriter<Instruction> getInstructionRewriter(Rewriters rewriters) {
                return i -> {
                    if(!args[3].equals(literal(i))) return i;
                    int register=((OneRegisterInstruction)i).getRegisterA();
                    return i.getOpcode()==Opcode.CONST_STRING_JUMBO
                        ?new ImmutableInstruction31c(i.getOpcode(),register,new ImmutableStringReference(OLD))
                        :new ImmutableInstruction21c(i.getOpcode(),register,new ImmutableStringReference(OLD));
                };
            }
        });
        ClassDef reversed=reverse.getClassDefRewriter().rewrite(newOwner);
        if(!Arrays.equals(canonical(Collections.singletonList(oldOwner)),canonical(Collections.singletonList(reversed))))
            throw new AssertionError("target has unrelated semantic changes");
        System.out.println("Read-back verified one literal, unchanged opcode/register/width, all other classes and reversed target canonical DEX.");
    }
}
"""


class BackendEndpointPatchTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        jdk = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        cls.java = jdk / "bin/java"
        javac = jdk / "bin/javac"
        jars = Path("/workspace/tooling/dexmerge")
        if not cls.java.exists() or not javac.exists() or not (jars / "dexlib2-2.5.2.jar").exists():
            raise unittest.SkipTest("Requires JDK and cached dexlib2 2.5.2 tool dependencies")
        cls.temp = tempfile.TemporaryDirectory(prefix="backend-endpoint-tests-")
        cls.path = Path(cls.temp.name)
        oracle = cls.path / "BackendEndpointOracle.java"
        oracle.write_text(ORACLE)
        cls.classpath = str(cls.path) + os.pathsep + str(jars / "*")
        result = subprocess.run([str(javac), "-cp", cls.classpath, "-d", str(cls.path),
                                 str(ROOT / "tools/PatchBackendEndpoint.java"), str(oracle)], capture_output=True, text=True)
        if result.returncode:
            cls.temp.cleanup()
            raise AssertionError(result.stderr)
        cls.fixture = cls.path / "fixture.dex"
        result = cls.oracle("fixture", "valid", cls.fixture)
        if result.returncode:
            cls.temp.cleanup()
            raise AssertionError(result.stderr)

    @classmethod
    def tearDownClass(cls):
        cls.temp.cleanup()

    @classmethod
    def command(cls, main, *args):
        return subprocess.run([str(cls.java), "-Xmx2g", "-cp", cls.classpath, main, *map(str, args)],
                              capture_output=True, text=True, timeout=120)

    @classmethod
    def oracle(cls, *args):
        return cls.command("BackendEndpointOracle", *args)

    def patch(self, source, output, url=LOCAL):
        return self.command("PatchBackendEndpoint", source, output, url)

    def verify_patch(self, source, output, url=LOCAL):
        before = hashlib.sha256(source.read_bytes()).hexdigest()
        result = self.patch(source, output, url)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        report = json.loads(output.with_name(output.name + ".json").read_text())
        self.assertEqual(report["input_dex_sha256"], before)
        self.assertEqual(report["output_dex_sha256"], hashlib.sha256(output.read_bytes()).hexdigest())
        self.assertEqual(report["target_class"], OWNER)
        self.assertEqual(report["target_method"], METHOD)
        self.assertEqual(report["original_url"], ORIGINAL)
        self.assertEqual(report["replacement_url"], url)
        self.assertEqual(report["replacement_count"], 1)
        self.assertTrue(report["unchanged_other_classes_verified"])
        self.assertTrue(report["reverse_patch_semantics_verified"])
        result = self.oracle("verify", source, output, url)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertEqual(hashlib.sha256(source.read_bytes()).hexdigest(), before)

    def test_real_merged_dex_changes_one_sdk_literal_and_preserves_other_classes(self):
        source = Path(os.environ.get("GREEN_FARM_MERGED_DEX", ROOT / "app/build/intermediates/original-sdk/debug/app-debug/merged/classes.dex"))
        if not source.exists():
            self.skipTest("Build the recovered merged DEX first, or set GREEN_FARM_MERGED_DEX")
        self.verify_patch(source, self.path / "actual-patched.dex")

    def test_both_const_string_widths_preserve_instruction_layout(self):
        for mode in ("valid", "narrow"):
            with self.subTest(mode=mode):
                source = self.path / f"{mode}.dex"
                result = self.oracle("fixture", mode, source)
                self.assertEqual(result.returncode, 0, result.stderr)
                self.verify_patch(source, self.path / f"{mode}-patched.dex")

    def test_allows_explicit_loopback_and_https_bases(self):
        for n, url in enumerate(("http://127.0.0.1:8787/", "http://localhost:8787/", "http://[::1]:8787/",
                                 "https://backend.example/api/")):
            with self.subTest(url=url):
                self.verify_patch(self.fixture, self.path / f"allowed-{n}.dex", url)

    def test_rejects_bad_or_nonlocal_cleartext_urls_without_publishing(self):
        invalid = ("http://example.com/", "http://10.0.2.3/", "http://192.168.1.1/", "ftp://localhost/",
                   "http://localhost:8787", "http://user@localhost/", "https://example.com/?q=1",
                   "https://example.com/#fragment", "https://example.com:70000/", "http://localhost:0/",
                   "//localhost/", "http://localhost\\@example.com/", "http://localhost/\n", ORIGINAL)
        for n, url in enumerate(invalid):
            with self.subTest(url=url):
                output = self.path / f"invalid-url-{n}.dex"
                result = self.patch(self.fixture, output, url)
                self.assertNotEqual(result.returncode, 0)
                self.assertFalse(output.exists())
                self.assertFalse(output.with_name(output.name + ".json").exists())

    def test_rejects_wrong_missing_duplicate_and_already_patched_targets(self):
        for mode in ("wrong_owner", "wrong_method", "missing_method", "missing_literal",
                     "duplicate_literal", "extra_owner_literal"):
            with self.subTest(mode=mode):
                source, output = self.path / f"bad-{mode}.dex", self.path / f"rejected-{mode}.dex"
                result = self.oracle("fixture", mode, source)
                self.assertEqual(result.returncode, 0, result.stderr)
                original = source.read_bytes()
                result = self.patch(source, output)
                self.assertNotEqual(result.returncode, 0)
                self.assertFalse(output.exists())
                self.assertEqual(source.read_bytes(), original)
        patched = self.path / "already-patched.dex"
        self.verify_patch(self.fixture, patched)
        output = self.path / "repatch.dex"
        self.assertNotEqual(self.patch(patched, output).returncode, 0)
        self.assertFalse(output.exists())

    def test_rejects_input_output_and_report_aliases_without_modifying_input(self):
        source = self.path / "alias-source.dex"
        shutil.copyfile(self.fixture, source)
        original = source.read_bytes()
        self.assertNotEqual(self.patch(source, source).returncode, 0)
        for kind in ("hardlink", "symlink"):
            output = self.path / f"alias-{kind}.dex"
            if kind == "hardlink": os.link(source, output)
            else: output.symlink_to(source)
            self.assertNotEqual(self.patch(source, output).returncode, 0)
        output = self.path / "report-alias.dex"
        os.link(source, output.with_name(output.name + ".json"))
        self.assertNotEqual(self.patch(source, output).returncode, 0)
        self.assertFalse(output.exists())
        self.assertEqual(source.read_bytes(), original)

    def test_rejects_non_dex_and_preserves_existing_output_on_failure(self):
        source, output = self.path / "not.dex", self.path / "existing.dex"
        source.write_bytes(b"not a DEX")
        output.write_bytes(b"existing output")
        report = output.with_name(output.name + ".json")
        report.write_text("existing report")
        result = self.patch(source, output)
        self.assertNotEqual(result.returncode, 0)
        self.assertEqual(output.read_bytes(), b"existing output")
        self.assertEqual(report.read_text(), "existing report")


if __name__ == "__main__":
    unittest.main()
