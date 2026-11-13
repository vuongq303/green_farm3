"""Remove the real Facebook SDK while preserving the profile data compatibility surface.

The fixture omits three old game dispatchers that production replaces from source.
It is never installed; the final APK gets separate complete-runtime verification.
"""
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
import java.nio.file.*;import java.util.*;import org.jf.dexlib2.*;import org.jf.dexlib2.iface.*;import org.jf.dexlib2.immutable.*;import org.jf.dexlib2.immutable.instruction.*;import org.jf.dexlib2.immutable.value.*;
public final class PruneFacebookOracle {
 static final Opcodes OPS=Opcodes.forDexVersion(35);static final String PROFILE="Lcom/gameloft/android2d/socialnetwork/i;";
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static ClassDef copy(ClassDef c,Iterable<? extends Field>fields,Iterable<? extends Method>methods){return new ImmutableClassDef(c.getType(),c.getAccessFlags(),c.getSuperclass(),c.getInterfaces(),c.getSourceFile(),c.getAnnotations(),fields,methods);}
 static ClassDef withoutAvatar(ClassDef c){List<Method>methods=new ArrayList<>();for(Method m:c.getMethods())if(!m.getName().equals("UN"))methods.add(m);return copy(c,c.getFields(),methods);}
 public static void main(String[]a)throws Exception{
  Map<String,ClassDef>before=PruneGoogleAds.classes(Files.readAllBytes(Paths.get(a[1])),OPS);
  if(a[0].equals("verify")){
   Map<String,ClassDef>after=PruneGoogleAds.classes(Files.readAllBytes(Paths.get(a[2])),OPS);Set<String>removed=new TreeSet<>();
   for(String name:before.keySet())if(name.startsWith("Lcom/facebook/")||PruneFacebookSdk.WRAPPERS.contains(name))removed.add(name);
   check(removed.size()==741,"exact original SDK and bridge inventory");for(String name:removed)before.remove(name);
   check(before.keySet().equals(after.keySet()),"only SDK/provider bridge removed");
   ClassDef oldProfile=before.remove(PROFILE),newProfile=after.remove(PROFILE);
   check(Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(withoutAvatar(oldProfile)),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(withoutAvatar(newProfile)),OPS)),"profile fields and data getters changed");
   check(Arrays.equals(RelocateGamePackage.semanticBytes(before.values(),OPS),RelocateGamePackage.semanticBytes(after.values(),OPS)),"other retained classes changed");
   PruneFacebookSdk.verifyUnavailableAvatar(newProfile);check(after.containsKey("Ljavax/microedition/midlet/App;"),"real Application retained");return;
  }
  String mode=a[2];Map<String,ClassDef>fixture=new TreeMap<>(before);
  // Production compiles these real dispatchers with SDK calls removed. The
  // bytecode-only fixture excludes them to test the SDK operation independently.
  for(String name:new ArrayList<>(fixture.keySet()))if(name.equals("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/ci;")||name.equals("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/bt;")||name.equals("Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/cm;"))fixture.remove(name);
  if(mode.equals("incoming_type")||mode.equals("incoming_wrapper")||mode.equals("incoming_string")){
   String owner="Lunsafe/StillUsesProvider;",type=mode.equals("incoming_wrapper")?"Lcom/gameloft/android2d/socialnetwork/l;":"Lcom/facebook/AccessToken;";
   Field field=new ImmutableField(owner,"provider",mode.equals("incoming_string")?"Ljava/lang/String;":type,9,mode.equals("incoming_string")?new ImmutableStringEncodedValue("com.facebook.FacebookSdk"):null,Collections.emptySet(),Collections.emptySet());
   fixture.put(owner,new ImmutableClassDef(owner,1,"Ljava/lang/Object;",Collections.emptyList(),null,Collections.emptySet(),Collections.singletonList(field),Collections.emptyList()));
  }
  if(mode.equals("profile_changed")){
   ClassDef old=fixture.get(PROFILE);List<Method>methods=new ArrayList<>();for(Method m:old.getMethods()){
    if(m.getName().equals("getName"))m=new ImmutableMethod(m.getDefiningClass(),m.getName(),m.getParameters(),m.getReturnType(),m.getAccessFlags(),m.getAnnotations(),m.getHiddenApiRestrictions(),new ImmutableMethodImplementation(1,Arrays.asList(new ImmutableInstruction11n(Opcode.CONST_4,0,0),new ImmutableInstruction11x(Opcode.RETURN_OBJECT,0)),Collections.emptyList(),Collections.emptyList()));methods.add(m);
   }fixture.put(PROFILE,copy(old,old.getFields(),methods));
  }
  if(mode.equals("missing_sdk"))fixture.remove("Lcom/facebook/FacebookSdk;");
  Files.write(Paths.get(a[3]),RelocateGamePackage.serialize(fixture.values(),OPS));
 }
}'''


class FacebookSdkPruningTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        jdk = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25')); jars = Path('/workspace/tooling/dexmerge')
        if not (jdk / 'bin/javac').exists() or not (jars / 'dexlib2-2.5.2.jar').exists():
            raise unittest.SkipTest('Requires JDK and verified cached dexlib2 dependencies')
        cls.temp = tempfile.TemporaryDirectory(prefix='facebook-sdk-pruning-'); cls.path = Path(cls.temp.name)
        cls.java = jdk / 'bin/java'; cls.cp = str(cls.path) + os.pathsep + str(jars / '*')
        oracle = cls.path / 'PruneFacebookOracle.java'; oracle.write_text(ORACLE)
        tools = [ROOT / 'tools' / (name + '.java') for name in ('RelocateGamePackage', 'PatchDisabledSdkLifecycle', 'PruneGoogleAds', 'PruneFacebookSdk')]
        result = subprocess.run([str(jdk / 'bin/javac'), '-cp', cls.cp, '-d', str(cls.path), *map(str, tools), str(oracle)], capture_output=True, text=True)
        if result.returncode: raise AssertionError(result.stderr)
        original = cls.path / 'original.dex'
        with zipfile.ZipFile(ROOT / 'green-farm-3.apk') as apk: original.write_bytes(apk.read('classes.dex'))
        lifecycle = cls.path / 'lifecycle.dex'; cls.require(cls.command('PatchDisabledSdkLifecycle', original, lifecycle))
        cls.source = cls.path / 'google-free.dex'; cls.require(cls.command('PruneGoogleAds', lifecycle, cls.source))
        cls.fixture = cls.path / 'source-compatible-fixture.dex'
        cls.require(cls.command('PruneFacebookOracle', 'fixture', cls.source, 'valid', cls.fixture))

    @classmethod
    def tearDownClass(cls): cls.temp.cleanup()
    @classmethod
    def command(cls, main, *args): return subprocess.run([str(cls.java), '-Xmx2g', '-cp', cls.cp, main, *map(str, args)], capture_output=True, text=True, timeout=120)
    @classmethod
    def require(cls, result):
        if result.returncode: raise AssertionError(result.stdout + result.stderr)

    def test_real_sdk_removal_preserves_profile_data_and_other_runtime_classes(self):
        output = self.path / 'facebook-free.dex'; source_hash = hashlib.sha256(self.fixture.read_bytes()).hexdigest()
        self.require(self.command('PruneFacebookSdk', self.fixture, output))
        proof = json.loads(output.with_suffix('.dex.json').read_text())
        self.assertEqual(proof['removed_class_count'], 741); self.assertEqual(proof['removed_facebook_class_count'], 717)
        self.assertTrue(proof['other_profile_data_and_methods_preserved']); self.assertTrue(proof['inverse_class_set_and_method_semantics_verified'])
        self.assertEqual(proof['input_dex_sha256'], source_hash)
        self.assertEqual(proof['output_dex_sha256'], hashlib.sha256(output.read_bytes()).hexdigest())
        self.require(self.command('PruneFacebookOracle', 'verify', self.fixture, output))
        self.assertEqual(hashlib.sha256(self.fixture.read_bytes()).hexdigest(), source_hash)
        self.assertNotEqual(self.command('PruneFacebookSdk', output, self.path / 'twice.dex').returncode, 0)

    def test_provider_dependencies_and_changed_profile_fail_before_output(self):
        for mode in ('incoming_type', 'incoming_wrapper', 'incoming_string', 'profile_changed', 'missing_sdk'):
            source = self.path / (mode + '.dex'); output = self.path / (mode + '-out.dex')
            self.require(self.command('PruneFacebookOracle', 'fixture', self.source, mode, source))
            result = self.command('PruneFacebookSdk', source, output)
            with self.subTest(mode=mode):
                self.assertNotEqual(result.returncode, 0); self.assertFalse(output.exists()); self.assertFalse(output.with_suffix('.dex.json').exists())

    def test_input_alias_cannot_modify_the_original_fixture(self):
        before = self.fixture.read_bytes()
        self.assertNotEqual(self.command('PruneFacebookSdk', self.fixture, self.fixture).returncode, 0)
        self.assertEqual(self.fixture.read_bytes(), before)


if __name__ == '__main__': unittest.main()
