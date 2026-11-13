"""Closed-set SDK removal with real DEX preservation and adversarial dependency roots."""
import hashlib
import json
import os
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest
import zipfile

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from verify_runtime_apk import dex_classes, verify_google_ads_absent

ORACLE = r'''
import java.nio.file.*;import java.util.*;import org.jf.dexlib2.*;import org.jf.dexlib2.dexbacked.*;import org.jf.dexlib2.iface.*;import org.jf.dexlib2.iface.instruction.*;import org.jf.dexlib2.immutable.*;import org.jf.dexlib2.immutable.debug.ImmutableStartLocal;import org.jf.dexlib2.immutable.instruction.*;import org.jf.dexlib2.immutable.reference.*;import org.jf.dexlib2.immutable.value.*;
public final class PruneAdsOracle {
 static final String OWNER="Lsafe/Consumer;", AD="Lcom/google/android/gms/ads/AdView;", ID="Lcom/google/android/gms/ads/identifier/AdvertisingIdClient;";
 static final Opcodes OPS=Opcodes.forDexVersion(35);
 static Map<String,ClassDef> read(String path)throws Exception{Map<String,ClassDef>r=new TreeMap<>();for(ClassDef c:new DexBackedDexFile(OPS,Files.readAllBytes(Paths.get(path))).getClasses())r.put(c.getType(),c);return r;}
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static ClassDef fixture(String mode){
  Set<Annotation> annotations=new HashSet<>();List<Field>fields=new ArrayList<>();List<Method>methods=new ArrayList<>();List<Instruction>code=new ArrayList<>();List<ImmutableTryBlock>tries=new ArrayList<>();List<MethodParameter>parameters=new ArrayList<>();String parent="Ljava/lang/Object;",returns="V";List<String>interfaces=new ArrayList<>();
  if(mode.equals("super"))parent=AD;
  if(mode.equals("interface"))interfaces.add(AD);
  if(mode.equals("field_type"))fields.add(new ImmutableField(OWNER,"items","[["+AD,1,null,Collections.emptySet(),Collections.emptySet()));
  if(mode.equals("encoded_type"))fields.add(new ImmutableField(OWNER,"type","Ljava/lang/Class;",9,new ImmutableTypeEncodedValue(AD),Collections.emptySet(),Collections.emptySet()));
  if(mode.equals("encoded_string"))fields.add(new ImmutableField(OWNER,"type","Ljava/lang/String;",9,new ImmutableStringEncodedValue("com.google.android.gms.ads.MobileAds"),Collections.emptySet(),Collections.emptySet()));
  if(mode.equals("annotation_type"))annotations.add(new ImmutableAnnotation(1,AD,Collections.emptySet()));
  if(mode.equals("nested_annotation"))annotations.add(new ImmutableAnnotation(1,"Lsafe/Ann;",Collections.singleton(new ImmutableAnnotationElement("types",new ImmutableArrayEncodedValue(Collections.singletonList(new ImmutableAnnotationEncodedValue("Lsafe/Nested;",Collections.singleton(new ImmutableAnnotationElement("value",new ImmutableTypeEncodedValue(AD))))))))));
  if(mode.equals("return_type"))returns=AD;
  if(mode.equals("parameter_type"))parameters.add(new ImmutableMethodParameter("["+AD,Collections.emptySet(),"input"));
  if(mode.equals("invoke"))code.add(new ImmutableInstruction35c(Opcode.INVOKE_STATIC,0,0,0,0,0,0,new ImmutableMethodReference(AD,"initialize",Collections.emptyList(),"V")));
  if(mode.equals("field_reference"))code.add(new ImmutableInstruction21c(Opcode.SGET_OBJECT,0,new ImmutableFieldReference(AD,"value","Ljava/lang/Object;")));
  if(mode.equals("class_reference"))code.add(new ImmutableInstruction21c(Opcode.CONST_CLASS,0,new ImmutableTypeReference(AD)));
  if(mode.equals("identifier"))code.add(new ImmutableInstruction21c(Opcode.CONST_CLASS,0,new ImmutableTypeReference(ID)));
  if(mode.equals("reflection"))code.add(new ImmutableInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference("com.google.android.gms.ads.MobileAds")));
  if(mode.equals("mixed_identifier_and_ads"))code.add(new ImmutableInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference("com.google.android.gms.ads.identifier.AdvertisingIdClient com.google.android.gms.ads.MobileAds")));
  if(mode.equals("slash_reflection"))code.add(new ImmutableInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference("com/google/android/gms/internal/ads/zzabc")));
  if(mode.equals("identifier"))code.add(new ImmutableInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference("com.google.android.gms.ads.identifier.AdvertisingIdClient")));
  if(mode.equals("unknown_identifier"))code.add(new ImmutableInstruction21c(Opcode.CONST_CLASS,0,new ImmutableTypeReference("Lcom/google/android/gms/ads/identifier/Unknown;")));
  code.add(new ImmutableInstruction10x(Opcode.RETURN_VOID));
  if(mode.equals("exception"))tries.add(new ImmutableTryBlock(0,1,Collections.singletonList(new ImmutableExceptionHandler(AD,0))));
  methods.add(new ImmutableMethod(OWNER,"run",parameters,returns,9,Collections.emptySet(),Collections.emptySet(),new ImmutableMethodImplementation(2,code,tries,mode.equals("debug_type")?Collections.singletonList(new ImmutableStartLocal(0,0,"ad",AD,null)):Collections.emptyList())));
  return new ImmutableClassDef(OWNER,1,parent,interfaces,"Fixture.java",annotations,fields,methods);
 }
 public static void main(String[]a)throws Exception{
  if(a[0].equals("audit")){
   String mode=a[1];Map<String,ClassDef>all=new TreeMap<>();all.put(OWNER,fixture(mode));all.put(AD,new ImmutableClassDef(AD,1,"Ljava/lang/Object;",Collections.emptyList(),null,Collections.emptySet(),Collections.emptyList(),Collections.emptyList()));
   boolean rejected=false;try{Map<String,ClassDef>r=PruneGoogleAds.prune(all,false);check(!r.containsKey(AD)&&r.containsKey(OWNER),"closed set removes only SDK");}catch(java.io.IOException expected){rejected=true;}
   check(rejected==!(mode.equals("identifier")||mode.equals("valid")),"dependency case: "+mode+" rejected="+rejected);return;
  }
  Map<String,ClassDef>before=read(a[1]),after=read(a[2]);Set<String>removed=new TreeSet<>();
  for(String name:before.keySet())if((name.startsWith("Lcom/google/android/gms/ads/")&&!name.startsWith("Lcom/google/android/gms/ads/identifier/"))||name.startsWith("Lcom/google/android/gms/internal/ads/")||name.startsWith("Lcom/google/ads/"))removed.add(name);
  check(removed.size()==1880,"original SDK inventory");for(String name:removed)before.remove(name);
  check(before.keySet().equals(after.keySet()),"only reviewed SDK class set removed");
  check(Arrays.equals(RelocateGamePackage.semanticBytes(before.values(),OPS),RelocateGamePackage.semanticBytes(after.values(),OPS)),"retained canonical semantics");
 }
}'''


class GoogleAdsPruningTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        jdk = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25')); jars = Path('/workspace/tooling/dexmerge')
        if not (jdk / 'bin/javac').exists() or not (jars / 'dexlib2-2.5.2.jar').exists():
            raise unittest.SkipTest('Requires JDK and cached verified dexlib2 tool dependencies')
        cls.temp = tempfile.TemporaryDirectory(prefix='google-ads-pruning-'); cls.path = Path(cls.temp.name)
        cls.java = jdk / 'bin/java'; cls.cp = str(cls.path) + os.pathsep + str(jars / '*')
        oracle = cls.path / 'PruneAdsOracle.java'; oracle.write_text(ORACLE)
        result = subprocess.run([str(jdk / 'bin/javac'), '-cp', cls.cp, '-d', str(cls.path),
            *map(str, (ROOT / 'tools/RelocateGamePackage.java', ROOT / 'tools/PatchDisabledSdkLifecycle.java', ROOT / 'tools/PruneGoogleAds.java')), str(oracle)], capture_output=True, text=True)
        if result.returncode: raise AssertionError(result.stderr)
        cls.original = cls.path / 'original.dex'
        with zipfile.ZipFile(ROOT / 'green-farm-3.apk') as apk: cls.original.write_bytes(apk.read('classes.dex'))

    @classmethod
    def tearDownClass(cls): cls.temp.cleanup()
    @classmethod
    def command(cls, main, *args): return subprocess.run([str(cls.java), '-Xmx2g', '-cp', cls.cp, main, *map(str, args)], capture_output=True, text=True, timeout=120)
    def require(self, result): self.assertEqual(result.returncode, 0, result.stdout + result.stderr)

    def test_real_sdk_closed_set_retains_original_runtime_and_shared_identifier(self):
        output = self.path / 'pruned.dex'; original_hash = hashlib.sha256(self.original.read_bytes()).hexdigest()
        self.require(self.command('PruneGoogleAds', self.original, output))
        proof = json.loads(output.with_suffix('.dex.json').read_text())
        self.assertEqual(proof['removed_class_count'], 1880); self.assertEqual(proof['retained_class_count'], 4503)
        self.assertEqual(proof['input_dex_sha256'], original_hash)
        self.assertEqual(proof['output_dex_sha256'], hashlib.sha256(output.read_bytes()).hexdigest())
        self.require(self.command('PruneAdsOracle', 'verify', self.original, output))
        classes = dex_classes(output.read_bytes()); self.assertIn('Ljavax/microedition/midlet/App;', classes)
        self.assertEqual(len(verify_google_ads_absent(classes, True)), 6)
        self.assertEqual(hashlib.sha256(self.original.read_bytes()).hexdigest(), original_hash)
        self.assertNotEqual(self.command('PruneGoogleAds', output, self.path / 'twice.dex').returncode, 0)

    def test_each_incoming_reference_kind_and_reflection_fails_closed(self):
        for mode in ('valid', 'identifier', 'super', 'interface', 'field_type', 'encoded_type', 'encoded_string',
                     'annotation_type', 'nested_annotation', 'return_type', 'parameter_type', 'invoke',
                     'field_reference', 'class_reference', 'reflection', 'mixed_identifier_and_ads',
                     'slash_reflection', 'unknown_identifier', 'exception', 'debug_type'):
            with self.subTest(mode=mode): self.require(self.command('PruneAdsOracle', 'audit', mode))

    def test_input_alias_is_rejected_without_modifying_original(self):
        before = self.original.read_bytes()
        self.assertNotEqual(self.command('PruneGoogleAds', self.original, self.original).returncode, 0)
        self.assertEqual(self.original.read_bytes(), before)


if __name__ == '__main__': unittest.main()
