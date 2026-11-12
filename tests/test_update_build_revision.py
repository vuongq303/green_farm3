"""Verify build-size metadata against the original APK's reader and XOR decoder.

Only test summaries leave the Java oracle; the decrypted application definition
is not printed or stored. Android lifecycle/termination remains a runtime check.
"""

from pathlib import Path
import hashlib
import json
import os
import subprocess
import tempfile
import unittest
from unittest import mock
import zipfile
import sys


ROOT = Path(__file__).resolve().parents[1]
APK_HASH = "34ce4414370d66e6e0ed06d63612b9fe63b27c902bda8c6220c7bc013d464c5b"
sys.path.insert(0, str(ROOT / "tools"))
import build_core_source

ORACLE = r"""
import java.nio.charset.*;
import java.nio.file.*;
import java.security.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.regex.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public final class BuildRevisionOracle {
    static final String SEED = "19GmLfT99";
    static final Pattern PROPERTY = Pattern.compile("(?m)^([ \\t]*REVISION-2408[ \\t]*:[ \\t]*)([0-9]+)(?=[ \\t]*\\r?$)");
    static byte[] crypt(int mode, byte[] bytes) throws Exception {
        byte[] digest = MessageDigest.getInstance("SHA-256").digest(SEED.getBytes(StandardCharsets.UTF_8));
        StringBuilder hex = new StringBuilder();
        for (byte b : digest) hex.append(String.format("%02x", b & 255));
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
        cipher.init(mode, new SecretKeySpec(hex.substring(0,16).getBytes(StandardCharsets.US_ASCII),"AES"));
        return cipher.doFinal(bytes);
    }
    static byte[] decrypt(Path input) throws Exception {
        byte[] encoded = Files.readAllBytes(input);
        String originalReader = com.gameloft.android.wrapper.h.Oz().T(new String(encoded,StandardCharsets.US_ASCII),SEED);
        if (originalReader == null) throw new AssertionError("Original DefReader rejected metadata");
        byte[] plain = crypt(Cipher.DECRYPT_MODE,Base64.getMimeDecoder().decode(encoded));
        if (!originalReader.equals(new String(plain,StandardCharsets.UTF_8))) throw new AssertionError("Original DefReader differs");
        return plain;
    }
    static Matcher property(String text) {
        Matcher m = PROPERTY.matcher(text);
        if (!m.find()) throw new AssertionError("Oracle revision missing");
        return m;
    }
    static long mask() throws Exception {
        com.gameloft.android.ANMP.GloftGF2F.Pt original = com.gameloft.android.ANMP.GloftGF2F.Pt.hb();
        Field field = original.getClass().getDeclaredField("EW"); field.setAccessible(true);
        return original.a((byte[])field.get(original));
    }
    public static void main(String[] args) throws Exception {
        if (args[0].equals("variant")) {
            byte[] plain = decrypt(Paths.get(args[1]));
            String text = new String(plain,StandardCharsets.ISO_8859_1);
            Matcher m = property(text);
            String changed;
            switch (args[3]) {
                case "missing": changed=text.replace("REVISION-2408","REMOVED-2408"); break;
                case "duplicate": changed=text+"\r\nREVISION-2408: "+m.group(2)+"\r\n"; break;
                case "duplicate_invalid": changed=text+"\r\nREVISION-2408: not-a-number\r\n"; break;
                case "invalid": changed=text.substring(0,m.start(2))+"not-a-number"+text.substring(m.end(2)); break;
                default: throw new AssertionError("Unknown variant");
            }
            Files.write(Paths.get(args[2]),Base64.getEncoder().encode(crypt(Cipher.ENCRYPT_MODE,changed.getBytes(StandardCharsets.ISO_8859_1))));
            return;
        }
        byte[] before=decrypt(Paths.get(args[0])), after=decrypt(Paths.get(args[1]));
        String a=new String(before,StandardCharsets.ISO_8859_1), b=new String(after,StandardCharsets.ISO_8859_1);
        Matcher old=property(a), newer=property(b);
        long originalSize=Long.parseLong(args[2]), newSize=Long.parseLong(args[3]);
        long actualMask=mask(), oldValue=Long.parseLong(old.group(2)), newValue=Long.parseLong(newer.group(2));
        if (actualMask != 0x226bcL) throw new AssertionError("Original Pt mask differs");
        if ((oldValue ^ actualMask) != originalSize) throw new AssertionError("Original guard rejects original metadata");
        if ((newValue ^ actualMask) != newSize) throw new AssertionError("Original guard rejects generated metadata");
        if (newSize != originalSize && (oldValue ^ actualMask) == newSize) throw new AssertionError("Stale metadata unexpectedly accepted");
        if ((newValue ^ actualMask) == newSize+1) throw new AssertionError("Tampered byte count unexpectedly accepted");
        if (!(a.substring(0,old.start(2))+a.substring(old.end(2)))
                .equals(b.substring(0,newer.start(2))+b.substring(newer.end(2)))) throw new AssertionError("Other plaintext bytes changed");
        if (newer.find()) throw new AssertionError("Generated duplicate revision");
        System.out.println("Original DefReader/Pt accept generated revision; stale/tampered sizes reject; all unrelated plaintext bytes preserved.");
    }
}
"""


class BuildRevisionMetadataTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        home=Path(os.environ.get("JAVA_HOME","/workspace/tooling/jdk-21.0.9+10"))
        cls.java, javac=home/"bin/java",home/"bin/javac"
        cls.jar=Path(os.environ.get("GREENFARM_ORIGINAL_JAR","/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        cls.apk=ROOT/"green-farm-3.apk"
        if not cls.java.exists() or not javac.exists() or not cls.jar.exists() or not cls.apk.exists():
            raise unittest.SkipTest("Requires JDK and the verified original APK/dex2jar artifact")
        if hashlib.sha256(cls.apk.read_bytes()).hexdigest()!=APK_HASH:
            raise AssertionError("Original APK differs from reviewed version")
        cls.temp=tempfile.TemporaryDirectory(prefix="build-revision-tests-")
        cls.path=Path(cls.temp.name)
        cls.original=cls.path/"original-content.bin"
        with zipfile.ZipFile(cls.apk) as z:
            cls.original.write_bytes(z.read("res/raw/content.bin"))
            cls.size=sum(i.file_size for i in z.infolist() if i.filename.endswith(".dex"))
        cls.original_hash=hashlib.sha256(cls.original.read_bytes()).hexdigest()
        oracle=cls.path/"BuildRevisionOracle.java";oracle.write_text(ORACLE)
        result=subprocess.run([str(javac),"-cp",str(cls.jar),"-d",str(cls.path),str(oracle),str(ROOT/"tools/UpdateBuildRevision.java")],capture_output=True,text=True)
        if result.returncode:
            cls.temp.cleanup();raise AssertionError(result.stderr)
        cls.classpath=str(cls.path)+os.pathsep+str(cls.jar)

    @classmethod
    def tearDownClass(cls): cls.temp.cleanup()

    def run_tool(self, source, output, old_size=None, new_size=None):
        return subprocess.run([str(self.java),"-cp",self.classpath,"UpdateBuildRevision",str(source),str(output),
                               str(self.size if old_size is None else old_size),
                               str(self.size+123 if new_size is None else new_size)],capture_output=True,text=True)

    def assert_oracle(self, generated, new_size):
        result=subprocess.run([str(self.java),"-cp",self.classpath,"BuildRevisionOracle",str(self.original),str(generated),str(self.size),str(new_size)],capture_output=True,text=True)
        self.assertEqual(result.returncode,0,result.stdout+result.stderr)
        self.assertIn("all unrelated plaintext bytes preserved",result.stdout)

    def test_changes_only_revision_digits_and_original_guard_accepts_new_size(self):
        output=self.path/"updated.bin"
        result=self.run_tool(self.original,output)
        self.assertEqual(result.returncode,0,result.stdout+result.stderr)
        self.assert_oracle(output,self.size+123)
        report=json.loads(output.with_suffix(".bin.json").read_text())
        self.assertTrue(report["other_definition_bytes_preserved"])
        self.assertTrue(report["original_guard_accepts_generated_revision"])
        self.assertEqual(report["generated_metadata_sha256"],hashlib.sha256(output.read_bytes()).hexdigest())
        self.assertEqual(hashlib.sha256(self.original.read_bytes()).hexdigest(),self.original_hash)

    def test_same_size_keeps_identical_original_ciphertext(self):
        output=self.path/"same-size.bin"
        result=self.run_tool(self.original,output,new_size=self.size)
        self.assertEqual(result.returncode,0,result.stdout+result.stderr)
        self.assertEqual(output.read_bytes(),self.original.read_bytes())
        self.assert_oracle(output,self.size)

    def test_long_size_and_changed_digit_count_preserve_other_bytes(self):
        output=self.path/"long-size.bin";size=2147484672
        result=self.run_tool(self.original,output,new_size=size)
        self.assertEqual(result.returncode,0,result.stdout+result.stderr)
        self.assert_oracle(output,size)

    def test_refuses_original_metadata_overwrite_and_hardlink_alias(self):
        result=self.run_tool(self.original,self.original)
        self.assertNotEqual(result.returncode,0)
        alias=self.path/"original-hardlink.bin";os.link(self.original,alias)
        result=self.run_tool(self.original,alias)
        self.assertNotEqual(result.returncode,0)
        self.assertEqual(hashlib.sha256(self.original.read_bytes()).hexdigest(),self.original_hash)

    def test_rejects_wrong_original_size_and_nonpositive_sizes(self):
        for index,(old,new) in enumerate(((self.size+1,self.size+123),(0,self.size+123),(self.size,0),(self.size,-1))):
            with self.subTest(old=old,new=new):
                output=self.path/f"invalid-size-{index}.bin"
                result=self.run_tool(self.original,output,old_size=old,new_size=new)
                self.assertNotEqual(result.returncode,0)
                self.assertFalse(output.exists())

    def test_rejects_missing_duplicate_and_invalid_properties(self):
        for kind in ("missing","duplicate","invalid","duplicate_invalid"):
            with self.subTest(kind=kind):
                variant=self.path/f"{kind}.bin";output=self.path/f"rejected-{kind}.bin"
                create=subprocess.run([str(self.java),"-cp",self.classpath,"BuildRevisionOracle","variant",str(self.original),str(variant),kind],capture_output=True,text=True)
                self.assertEqual(create.returncode,0,create.stdout+create.stderr)
                result=self.run_tool(variant,output)
                self.assertNotEqual(result.returncode,0,result.stdout+result.stderr)
                self.assertFalse(output.exists())

    def test_rejects_corrupt_ciphertext_before_output(self):
        import base64
        corrupt=self.path/"corrupt.bin";output=self.path/"corrupt-output.bin"
        cipher=base64.b64decode(self.original.read_bytes())
        corrupt.write_bytes(base64.b64encode(cipher[:-1]))
        result=self.run_tool(corrupt,output)
        self.assertNotEqual(result.returncode,0)
        self.assertFalse(output.exists())

    def test_packaging_hook_changes_only_generated_metadata_entry(self):
        directory=self.path/"hook";directory.mkdir()
        unsigned=directory/"unsigned.apk"
        # A size-only fixture exercises packaging and the unchanged guard, not
        # Android DEX execution. Keep every original entry except this payload.
        with zipfile.ZipFile(self.apk) as source, zipfile.ZipFile(unsigned,"w") as output:
            for entry in source.infolist():
                data=source.read(entry)
                if entry.filename=="classes.dex": data+=b"\0"*123
                output.writestr(entry,data)
        with zipfile.ZipFile(unsigned) as before:
            snapshot={entry.filename:(before.read(entry),entry.compress_type) for entry in before.infolist()}
        with mock.patch.dict(os.environ,{"JAVA_HOME":str(self.java.parent.parent)}):
            report=build_core_source.regenerate_build_revision(self.apk,unsigned,directory)
        self.assertEqual(report["original_dex_bytes"],self.size)
        self.assertEqual(report["rebuilt_dex_bytes"],self.size+123)
        with zipfile.ZipFile(unsigned) as after:
            self.assertEqual(set(after.namelist()),set(snapshot))
            for entry in after.infolist():
                old,compression=snapshot[entry.filename]
                self.assertEqual(entry.compress_type,compression)
                if entry.filename=="res/raw/content.bin":
                    generated=directory/"oracle-generated.bin"
                    generated.write_bytes(after.read(entry))
                    self.assertNotEqual(after.read(entry),old)
                else: self.assertEqual(after.read(entry),old)
        self.assert_oracle(generated,self.size+123)
        self.assertEqual(hashlib.sha256(self.apk.read_bytes()).hexdigest(),APK_HASH)


if __name__=="__main__": unittest.main()
