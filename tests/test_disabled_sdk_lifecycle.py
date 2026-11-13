"""Validate the scoped SDK policy against original bytecode and rejected mutations."""
import hashlib
import json
import os
from pathlib import Path
import subprocess
import tempfile
import unittest
import zipfile

ROOT = Path(__file__).resolve().parents[1]
ORACLE = r'''
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
public final class DisabledSdkOracle {
 static final String MID="Ljavax/microedition/midlet/MIDlet;", FB="Ljavax/microedition/midlet/v;", AD="Ljavax/microedition/midlet/o;", NATIVE="Lcom/gameloft/adsmanager/AdsManager;";
 static final Opcodes OPS=Opcodes.forDexVersion(35);
 static Map<String,ClassDef> read(String path)throws Exception{
  Map<String,ClassDef> result=new TreeMap<>();for(ClassDef c:new DexBackedDexFile(OPS,Files.readAllBytes(Paths.get(path))).getClasses())result.put(c.getType(),c);return result;
 }
 static MethodReference ref(Instruction i){if(i instanceof ReferenceInstruction&&((ReferenceInstruction)i).getReference() instanceof MethodReference)return(MethodReference)((ReferenceInstruction)i).getReference();return null;}
 static int width(Method m){int n=0;for(Instruction i:m.getImplementation().getInstructions())n+=i.getCodeUnits();return n;}
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 public static void main(String[]args)throws Exception{
  Map<String,ClassDef> before=read(args[1]);
  if(args[0].equals("source_profile")){
   before.remove("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/ci;");before.remove("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/b;");
   Files.write(Paths.get(args[2]),RelocateGamePackage.serialize(before.values(),OPS));return;
  }
  if(args[0].equals("verify")){
   Map<String,ClassDef> after=read(args[2]);check(before.keySet().equals(after.keySet()),"class set");
   Set<String> owners=new TreeSet<>(PatchDisabledSdkLifecycle.NATIVE_RETIREMENT.keySet());owners.add(MID);
   Set<String> nativeMethods=new TreeSet<>();for(Method m:before.get(NATIVE).getMethods())if((m.getAccessFlags()&256)!=0)nativeMethods.add(PatchDisabledSdkLifecycle.signature(m));
   int oldCalls=0,newCalls=0;
   for(String owner:owners){ClassDef original=before.remove(owner),patched=after.remove(owner);
   Map<String,Method> methods=new TreeMap<>();for(Method m:original.getMethods())methods.put(PatchDisabledSdkLifecycle.signature(m),m);
   for(Method m:patched.getMethods())if(m.getImplementation()!=null){
    Method old=methods.get(PatchDisabledSdkLifecycle.signature(m));check(width(old)==width(m),"code offsets");
    check(old.getImplementation().getRegisterCount()==m.getImplementation().getRegisterCount(),"registers");
    for(Instruction i:old.getImplementation().getInstructions()){MethodReference r=ref(i);if(r!=null&&((owner.equals(MID)&&(r.getDefiningClass().equals(FB)||r.getDefiningClass().equals(AD)))||(r.getDefiningClass().equals(NATIVE)&&nativeMethods.contains(PatchDisabledSdkLifecycle.signature(r)))))oldCalls++;}
    for(Instruction i:m.getImplementation().getInstructions()){MethodReference r=ref(i);if(r!=null&&((owner.equals(MID)&&(r.getDefiningClass().equals(FB)||r.getDefiningClass().equals(AD)))||(r.getDefiningClass().equals(NATIVE)&&nativeMethods.contains(PatchDisabledSdkLifecycle.signature(r)))))newCalls++;}
   }
   }check(Arrays.equals(RelocateGamePackage.semanticBytes(before.values(),OPS),RelocateGamePackage.semanticBytes(after.values(),OPS)),"other class semantics");
   check(oldCalls==33&&newCalls==0,"only reviewed SDK dispatches removed");return;
  }
  String mode=args[2];List<Method> methods=new ArrayList<>();boolean changed=false;
  for(Method m:before.get(MID).getMethods()){
   if(m.getImplementation()==null){methods.add(m);continue;}
   List<Instruction> code=new ArrayList<>();boolean query=false;
   for(Instruction i:m.getImplementation().getInstructions()){
    MethodReference r=ref(i);
    if(!changed&&mode.equals("missing")&&r!=null&&r.getDefiningClass().equals(FB)&&r.getName().equals("onCreate")){
     for(int n=0;n<3;n++)code.add(new ImmutableInstruction10x(Opcode.NOP));changed=true;continue;
    }
    if(!changed&&mode.equals("duplicate")&&m.getName().equals("onCreate")&&r!=null&&r.getDefiningClass().equals("Ljavax/microedition/midlet/u;")){
     i=new ImmutableInstruction35c(Opcode.INVOKE_STATIC,1,1,0,0,0,0,new ImmutableMethodReference(FB,"onCreate",Collections.singletonList("Landroid/os/Bundle;"),"V"));changed=true;
    }
    if(!changed&&mode.equals("wrong_caller")&&r!=null&&r.getDefiningClass().equals(FB)&&r.getName().equals("onResume")){
     i=new ImmutableInstruction35c(Opcode.INVOKE_STATIC,1,0,0,0,0,0,new ImmutableMethodReference(FB,"onCreate",Collections.singletonList("Landroid/os/Bundle;"),"V"));changed=true;
    }
    if(!changed&&mode.equals("result_type")&&query&&i.getOpcode()==Opcode.MOVE_RESULT){i=new ImmutableInstruction11x(Opcode.MOVE_RESULT_OBJECT,((OneRegisterInstruction)i).getRegisterA());changed=true;}
    query=r!=null&&r.getDefiningClass().equals(NATIVE)&&r.getName().equals("IsFullScreenAdOnScreen");code.add(i);
   }
   MethodImplementation b=m.getImplementation();methods.add(new ImmutableMethod(m.getDefiningClass(),m.getName(),m.getParameters(),m.getReturnType(),m.getAccessFlags(),m.getAnnotations(),m.getHiddenApiRestrictions(),new ImmutableMethodImplementation(b.getRegisterCount(),code,b.getTryBlocks(),b.getDebugItems())));
  }
  ClassDef c=before.get(MID);ClassDef modified=new ImmutableClassDef(c.getType(),c.getAccessFlags(),c.getSuperclass(),c.getInterfaces(),c.getSourceFile(),c.getAnnotations(),c.getFields(),methods);
  List<ClassDef> selected=new ArrayList<>();if(!mode.equals("missing_owner"))selected.add(modified);selected.add(before.get("Ljavax/microedition/midlet/App;"));
  for(String owner:PatchDisabledSdkLifecycle.NATIVE_RETIREMENT.keySet())selected.add(before.get(owner));
  if(mode.equals("missing_privacy_native")||mode.equals("extra_privacy_native")){
   String owner="Lcom/gameloft/a/b/i;";ClassDef privacy=before.get(owner);List<Method> privacyMethods=new ArrayList<>();
   for(Method m:privacy.getMethods()){
    if(mode.equals("missing_privacy_native")&&m.getName().equals("bQ")){
     List<Instruction>code=new ArrayList<>();for(Instruction i:m.getImplementation().getInstructions()){
      MethodReference r=ref(i);if(r!=null&&r.getDefiningClass().equals(NATIVE)&&r.getName().equals("SetAge")){for(int n=0;n<3;n++)code.add(new ImmutableInstruction10x(Opcode.NOP));changed=true;}else code.add(i);
     }MethodImplementation body=m.getImplementation();m=new ImmutableMethod(m.getDefiningClass(),m.getName(),m.getParameters(),m.getReturnType(),m.getAccessFlags(),m.getAnnotations(),m.getHiddenApiRestrictions(),new ImmutableMethodImplementation(body.getRegisterCount(),code,body.getTryBlocks(),body.getDebugItems()));
    }privacyMethods.add(m);
   }
   if(mode.equals("extra_privacy_native")){
    privacyMethods.add(new ImmutableMethod(owner,"unreviewedAdPropagation",Collections.emptyList(),"V",9,Collections.emptySet(),Collections.emptySet(),new ImmutableMethodImplementation(1,Arrays.asList(new ImmutableInstruction11n(Opcode.CONST_4,0,1),new ImmutableInstruction35c(Opcode.INVOKE_STATIC,1,0,0,0,0,0,new ImmutableMethodReference(NATIVE,"SetAge",Collections.singletonList("I"),"V")),new ImmutableInstruction10x(Opcode.RETURN_VOID)),Collections.emptyList(),Collections.emptyList())));changed=true;
   }
   selected.remove(privacy);selected.add(new ImmutableClassDef(owner,privacy.getAccessFlags(),privacy.getSuperclass(),privacy.getInterfaces(),privacy.getSourceFile(),privacy.getAnnotations(),privacy.getFields(),privacyMethods));
  }
  check(mode.equals("valid")||mode.equals("missing_owner")||changed,"mutation actually applied");
  Files.write(Paths.get(args[3]),RelocateGamePackage.serialize(selected,OPS));
 }
}'''


class DisabledSdkLifecycleTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        jdk = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        jars = Path('/workspace/tooling/dexmerge')
        if not (jdk / 'bin/javac').exists() or not (jars / 'dexlib2-2.5.2.jar').exists():
            raise unittest.SkipTest('Requires JDK and verified cached dexlib2 tool dependencies')
        cls.temp = tempfile.TemporaryDirectory(prefix='disabled-sdk-tests-'); cls.path = Path(cls.temp.name)
        cls.java = jdk / 'bin/java'; cls.cp = str(cls.path) + os.pathsep + str(jars / '*')
        source = cls.path / 'DisabledSdkOracle.java'; source.write_text(ORACLE)
        result = subprocess.run([str(jdk / 'bin/javac'), '-cp', cls.cp, '-d', str(cls.path),
            str(ROOT / 'tools/RelocateGamePackage.java'), str(ROOT / 'tools/PatchDisabledSdkLifecycle.java'), str(source)], capture_output=True, text=True)
        if result.returncode: raise AssertionError(result.stderr)
        cls.original = cls.path / 'original.dex'
        with zipfile.ZipFile(ROOT / 'green-farm-3.apk') as apk: cls.original.write_bytes(apk.read('classes.dex'))
        cls.fixture = cls.path / 'fixture.dex'
        cls.require(cls.command('DisabledSdkOracle', 'fixture', cls.original, 'valid', cls.fixture))
        cls.source_profile = cls.path / 'source-profile.dex'
        cls.require(cls.command('DisabledSdkOracle', 'source_profile', cls.original, cls.source_profile))

    @classmethod
    def tearDownClass(cls): cls.temp.cleanup()

    @classmethod
    def command(cls, main, *args):
        return subprocess.run([str(cls.java), '-Xmx2g', '-cp', cls.cp, main, *map(str, args)], capture_output=True, text=True, timeout=120)

    @classmethod
    def require(cls, result):
        if result.returncode: raise AssertionError(result.stdout + result.stderr)

    def test_authoritative_original_runtime_and_other_sdk_classes_are_preserved(self):
        for source in (self.fixture, self.source_profile):
            with self.subTest(source=source.name):
                original_hash = hashlib.sha256(source.read_bytes()).hexdigest(); output = self.path / (source.stem + '-disabled.dex')
                self.require(self.command('PatchDisabledSdkLifecycle', source, output))
                proof = json.loads(output.with_suffix('.dex.json').read_text())
                self.assertEqual(proof['input_dex_sha256'], original_hash)
                self.assertEqual(proof['output_dex_sha256'], hashlib.sha256(output.read_bytes()).hexdigest())
                self.assertTrue(proof['inverse_target_semantics_verified']); self.assertTrue(proof['unchanged_other_classes_verified'])
                self.assertEqual(proof['total_sdk_invocation_sites_disabled'], 33)
                self.assertEqual(proof['native_ads_manager_invocations_remaining'], 0)
                self.require(self.command('DisabledSdkOracle', 'verify', source, output))
                self.assertEqual(hashlib.sha256(source.read_bytes()).hexdigest(), original_hash)

    def test_global_audit_rejects_remaining_native_calls_in_unconverted_game_sources(self):
        output = self.path / 'unconverted-game-rejected.dex'
        result = self.command('PatchDisabledSdkLifecycle', self.original, output)
        self.assertNotEqual(result.returncode, 0)
        self.assertIn('Native AdsManager invocation remains', result.stderr)
        self.assertFalse(output.exists())

    def test_unreviewed_missing_duplicate_caller_and_query_encoding_fail_closed(self):
        for mode in ('missing_owner', 'missing', 'duplicate', 'wrong_caller', 'result_type',
                     'missing_privacy_native', 'extra_privacy_native'):
            source = self.path / (mode + '.dex'); output = self.path / (mode + '-out.dex')
            self.require(self.command('DisabledSdkOracle', 'fixture', self.original, mode, source))
            result = self.command('PatchDisabledSdkLifecycle', source, output)
            with self.subTest(mode=mode):
                self.assertNotEqual(result.returncode, 0); self.assertFalse(output.exists())
                self.assertFalse(output.with_suffix('.dex.json').exists())

    def test_does_not_overwrite_input_or_apply_policy_twice(self):
        before = self.fixture.read_bytes()
        self.assertNotEqual(self.command('PatchDisabledSdkLifecycle', self.fixture, self.fixture).returncode, 0)
        self.assertEqual(self.fixture.read_bytes(), before)
        output = self.path / 'once.dex'; self.require(self.command('PatchDisabledSdkLifecycle', self.fixture, output))
        twice = self.path / 'twice.dex'
        self.assertNotEqual(self.command('PatchDisabledSdkLifecycle', output, twice).returncode, 0)
        self.assertFalse(twice.exists())


if __name__ == '__main__': unittest.main()
