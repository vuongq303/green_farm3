"""Read back the retained MIDlet's Facebook-only lifecycle reference rewrite.

Executes production DEX patching and an independent reverse oracle against
adversarial fixtures and the actual shipped MIDlet class. No app is installed.
"""
import hashlib
import json
import os
from pathlib import Path
import subprocess
import tempfile
import unittest
import zipfile

from test_backend_endpoint_patch import ORACLE as ENDPOINT_ORACLE

ROOT = Path(__file__).resolve().parents[1]
MIDLET = 'Ljavax/microedition/midlet/MIDlet;'
DELEGATE = 'Ljavax/microedition/midlet/v;'
HELPER = 'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LegacyFacebookLifecycle;'
METHODS = (
    'onCreate(Landroid/os/Bundle;)V', 'onDestroy()V', 'onPause()V',
    'onSaveInstanceState(Landroid/os/Bundle;)V', 'onRestart()V', 'onResume()V',
    'onStart()V', 'onStop()V', 'onNewIntent(Landroid/content/Intent;)V',
    'onActivityResult(IILandroid/content/Intent;)V', 'onWindowFocusChanged(Z)V',
    'onConfigurationChanged(Landroid/content/res/Configuration;)V',
)

ORACLE = r'''
import java.nio.file.*;
import java.util.*;
import org.jf.dexlib2.*;
import org.jf.dexlib2.iface.*;
import org.jf.dexlib2.iface.instruction.*;
import org.jf.dexlib2.iface.reference.*;
import org.jf.dexlib2.immutable.*;
import org.jf.dexlib2.immutable.debug.*;
import org.jf.dexlib2.immutable.instruction.*;
import org.jf.dexlib2.immutable.reference.*;
import org.jf.dexlib2.immutable.value.*;
import org.jf.dexlib2.rewriter.*;

public class FacebookLifecycleOracle {
    static final String MIDLET="Ljavax/microedition/midlet/MIDlet;";
    static final String OLD="Ljavax/microedition/midlet/v;";
    static final String HELPER="Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LegacyFacebookLifecycle;";
    static final List<MethodReference> METHODS=Arrays.asList(
        ref("onCreate","Landroid/os/Bundle;"),ref("onDestroy"),ref("onPause"),
        ref("onSaveInstanceState","Landroid/os/Bundle;"),ref("onRestart"),ref("onResume"),
        ref("onStart"),ref("onStop"),ref("onNewIntent","Landroid/content/Intent;"),
        ref("onActivityResult","I","I","Landroid/content/Intent;"),ref("onWindowFocusChanged","Z"),
        ref("onConfigurationChanged","Landroid/content/res/Configuration;"));
    static MethodReference ref(String name,String... parameters){return new ImmutableMethodReference(OLD,name,Arrays.asList(parameters),"V");}
    static String key(MethodReference ref){StringBuilder b=new StringBuilder(ref.getName()).append('(');for(CharSequence p:ref.getParameterTypes())b.append(p);return b.append(')').append(ref.getReturnType()).toString();}
    static List<ImmutableMethodParameter> parameters(MethodReference ref){List<ImmutableMethodParameter> p=new ArrayList<>();for(CharSequence t:ref.getParameterTypes())p.add(new ImmutableMethodParameter(t.toString(),Collections.emptySet(),null));return p;}
    static ClassDef clazz(String owner,List<Method> methods){
        return new ImmutableClassDef(owner,1,"Ljava/lang/Object;",Collections.emptyList(),"Fixture.java",Collections.emptySet(),
            Collections.singletonList(new ImmutableField(owner,"unchangedMarker","I",9,new ImmutableIntEncodedValue(123),Collections.emptySet(),Collections.emptySet())),methods);
    }
    static MethodReference to(String owner,MethodReference r){return new ImmutableMethodReference(owner,r.getName(),r.getParameterTypes(),r.getReturnType());}
    static Instruction invoke(MethodReference ref,boolean range,Opcode opcode,int count){
        if(range)return new ImmutableInstruction3rc(opcode, count==0?0:1,count,ref);
        return new ImmutableInstruction35c(opcode,count,1,2,3,4,0,ref);
    }
    static Method fixtureMethod(MethodReference spec,int n,String mode){
        String name=mode.equals("wrongcaller")&&n==2?"notThePauseCallback":spec.getName();
        MethodReference delegate=to(mode.equals("already")&&n==5?HELPER:OLD,spec);
        if(mode.equals("wrongprototype")&&n==3)delegate=new ImmutableMethodReference(OLD,spec.getName(),Collections.singletonList("Landroid/content/Intent;"),"V");
        int count=spec.getParameterTypes().size();
        if(mode.equals("wrongregisters")&&n==6)count=1;
        boolean range=(n%2)==1;
        Opcode opcode=range?Opcode.INVOKE_STATIC_RANGE:Opcode.INVOKE_STATIC;
        if(mode.equals("wrongopcode")&&n==0)opcode=Opcode.INVOKE_VIRTUAL;
        List<Instruction> code=new ArrayList<>();
        code.add(new ImmutableInstruction35c(Opcode.INVOKE_STATIC,0,0,0,0,0,0,new ImmutableMethodReference("Lother/Module;","unchanged",Collections.emptyList(),"V")));
        code.add(invoke(delegate,range,opcode,count));
        if(mode.equals("duplicate")&&n==6)code.add(invoke(delegate,range,opcode,count));
        if(mode.equals("unknown")&&n==0)code.add(invoke(ref("onUnexpected"),false,Opcode.INVOKE_STATIC,0));
        code.add(new ImmutableInstruction10x(Opcode.RETURN_VOID));
        int handler=code.stream().mapToInt(Instruction::getCodeUnits).sum();
        code.add(new ImmutableInstruction11x(Opcode.MOVE_EXCEPTION,0));
        code.add(new ImmutableInstruction10x(Opcode.RETURN_VOID));
        return new ImmutableMethod(MIDLET,name,parameters(spec),"V",1,Collections.emptySet(),Collections.emptySet(),
            new ImmutableMethodImplementation(5,code,Collections.singletonList(new ImmutableTryBlock(3,3,
                Collections.singletonList(new ImmutableExceptionHandler("Ljava/lang/Exception;",handler)))),
                Collections.singletonList(new ImmutableLineNumber(3,100+n))));
    }
    static void fixture(String mode,String output,String original)throws Exception{
        BackendEndpointOracle.fixture("narrow",output);
        TreeMap<String,ClassDef> classes=BackendEndpointOracle.read(output);
        if(mode.equals("actual")) {
            ClassDef real=BackendEndpointOracle.read(original).get(MIDLET);
            if(real==null)throw new AssertionError("Actual shipped MIDlet missing");
            classes.put(MIDLET,real);
        }else if(!mode.equals("missing_midlet")){
            List<Method> methods=new ArrayList<>();
            for(int n=0;n<METHODS.size();n++)if(!(mode.equals("missing")&&n==7))methods.add(fixtureMethod(METHODS.get(n),n,mode));
            classes.put(MIDLET,clazz(MIDLET,methods));
        }
        if(!mode.equals("helpermissing")){
            List<Method> methods=new ArrayList<>();
            for(int n=0;n<METHODS.size();n++){
                MethodReference spec=METHODS.get(n);
                if(mode.equals("helperproto")&&n==0)spec=ref(spec.getName(),"I");
                int access=mode.equals("helpernonstatic")&&n==0?1:9;
                methods.add(new ImmutableMethod(HELPER,spec.getName(),parameters(spec),"V",access,Collections.emptySet(),Collections.emptySet(),
                    new ImmutableMethodImplementation(5,Collections.singletonList(new ImmutableInstruction10x(Opcode.RETURN_VOID)),Collections.emptyList(),Collections.emptyList())));
            }
            classes.put(HELPER,clazz(HELPER,methods));
        }
        Files.write(Paths.get(output),BackendEndpointOracle.canonical(classes.values()));
    }
    static MethodReference target(Instruction i){if(!(i instanceof ReferenceInstruction))return null;Object r=((ReferenceInstruction)i).getReference();return r instanceof MethodReference?(MethodReference)r:null;}
    static int[] registers(Instruction i){
        if(i instanceof FiveRegisterInstruction){FiveRegisterInstruction x=(FiveRegisterInstruction)i;return new int[]{x.getRegisterCount(),x.getRegisterC(),x.getRegisterD(),x.getRegisterE(),x.getRegisterF(),x.getRegisterG()};}
        RegisterRangeInstruction x=(RegisterRangeInstruction)i;return new int[]{x.getRegisterCount(),x.getStartRegister()};
    }
    static void verify(String source,String output)throws Exception{
        TreeMap<String,ClassDef> before=BackendEndpointOracle.read(source),after=BackendEndpointOracle.read(output);
        ClassDef original=before.get(MIDLET),modified=after.get(MIDLET);
        Map<String,Method> current=new TreeMap<>();for(Method method:modified.getMethods())current.put(key(method),method);
        Map<String,Integer> counts=new TreeMap<>();
        for(Method method:original.getMethods()){
            Method changed=current.get(key(method));if(changed==null)throw new AssertionError("Method set changed");
            if(method.getImplementation()==null){if(changed.getImplementation()!=null)throw new AssertionError();continue;}
            List<Instruction> a=new ArrayList<>(),b=new ArrayList<>();method.getImplementation().getInstructions().forEach(a::add);changed.getImplementation().getInstructions().forEach(b::add);
            if(a.size()!=b.size())throw new AssertionError("Instruction count changed");
            for(int n=0;n<a.size();n++){
                Instruction x=a.get(n),y=b.get(n);if(x.getOpcode()!=y.getOpcode()||x.getCodeUnits()!=y.getCodeUnits())throw new AssertionError("Original opcode/width changed");
                MethodReference old=target(x),now=target(y);
                if(old!=null&&old.getDefiningClass().equals(OLD)&&old.getName().startsWith("on")){
                    if(now==null||!now.getDefiningClass().equals(HELPER)||!key(old).equals(key(now))||!Arrays.equals(registers(x),registers(y)))throw new AssertionError("Lifecycle target/prototype/registers changed");
                    counts.merge(key(old),1,Integer::sum);
                }
            }
        }
        Map<String,Integer> expected=new TreeMap<>();for(MethodReference spec:METHODS)expected.put(key(spec),1);
        if(!counts.equals(expected))throw new AssertionError("Wrong lifecycle call set "+counts);
        // Independent immutable instruction reversal, preserving all other method,
        // field, try/catch and debug metadata through dexlib's generic rewriter.
        DexRewriter reverse=new DexRewriter(new RewriterModule(){@Override public Rewriter<Instruction> getInstructionRewriter(Rewriters r){
            return i->{MethodReference ref=target(i);if(ref==null||!ref.getDefiningClass().equals(HELPER))return i;
                MethodReference back=to(OLD,ref);
                if(i instanceof FiveRegisterInstruction){FiveRegisterInstruction x=(FiveRegisterInstruction)i;return new ImmutableInstruction35c(i.getOpcode(),x.getRegisterCount(),x.getRegisterC(),x.getRegisterD(),x.getRegisterE(),x.getRegisterF(),x.getRegisterG(),back);}
                RegisterRangeInstruction x=(RegisterRangeInstruction)i;return new ImmutableInstruction3rc(i.getOpcode(),x.getStartRegister(),x.getRegisterCount(),back);
            };}});
        ClassDef restored=reverse.getClassDefRewriter().rewrite(modified);
        if(!Arrays.equals(BackendEndpointOracle.canonical(Collections.singletonList(original)),BackendEndpointOracle.canonical(Collections.singletonList(restored))))throw new AssertionError("MIDlet other semantics changed");
        for(String owner:Arrays.asList(MIDLET,BackendEndpointOracle.OWNER,BackendEndpointOracle.CORE_OWNER)){before.remove(owner);after.remove(owner);}
        if(!Arrays.equals(BackendEndpointOracle.canonical(before.values()),BackendEndpointOracle.canonical(after.values())))throw new AssertionError("Other classes changed");
        System.out.println("PASS 12 exact lifecycle references, unchanged opcodes/registers/try/debug/fields and independent reverse MIDlet proof");
    }
    public static void main(String[]args)throws Exception{if(args[0].equals("fixture"))fixture(args[1],args[2],args.length>3?args[3]:null);else verify(args[1],args[2]);}
}
'''


class LegacyFacebookLifecyclePatchTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        home = Path(os.environ.get('JAVA_HOME', '/usr/lib/jvm/default-java'))
        java, javac = home / 'bin/java', home / 'bin/javac'
        cache = Path(os.environ.get('GRADLE_USER_HOME', Path.home() / '.gradle')) / 'caches/modules-2/files-2.1'
        jars = list(cache.glob('org.smali/dexlib2/2.5.2/*/*.jar')) + list(cache.glob('com.google.guava/guava/*/*/*.jar'))
        if not javac.is_file() or not jars:
            raise unittest.SkipTest('Requires JDK and Gradle-resolved dexlib2')
        cls.tmp = tempfile.TemporaryDirectory(prefix='facebook-lifecycle-dex-')
        cls.addClassCleanup(cls.tmp.cleanup)
        cls.directory = Path(cls.tmp.name)
        cls.java = java
        cls.cp = os.pathsep.join(map(str, [cls.directory, *jars]))
        sources = []
        for name, content in [('BackendEndpointOracle', ENDPOINT_ORACLE), ('FacebookLifecycleOracle', ORACLE)]:
            path = cls.directory / (name + '.java')
            path.write_text(content)
            sources.append(str(path))
        result = subprocess.run([str(javac), '-encoding', 'UTF-8', '-cp', cls.cp, '-d', str(cls.directory),
                                 str(ROOT / 'tools/PatchBackendEndpoint.java'), *sources], capture_output=True, text=True)
        if result.returncode:
            raise AssertionError(result.stdout + result.stderr)

    @classmethod
    def command(cls, main, *args):
        return subprocess.run([str(cls.java), '-Xmx1g', '-cp', cls.cp, main, *map(str, args)],
                              capture_output=True, text=True, timeout=60)

    def fixture(self, mode):
        source = self.directory / (mode + '.dex')
        result = self.command('FacebookLifecycleOracle', 'fixture', mode, source)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        return source

    def patch(self, source, output):
        return self.command('PatchBackendEndpoint', source, output, 'https://accounts.example.test/',
                            '--guard-legacy-facebook-lifecycle')

    def check_patch(self, source, output):
        digest = hashlib.sha256(source.read_bytes()).hexdigest()
        result = self.patch(source, output)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        result = self.command('FacebookLifecycleOracle', 'verify', source, output)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        proof = json.loads(output.with_suffix('.dex.json').read_text())
        self.assertEqual(proof['input_dex_sha256'], digest)
        self.assertEqual(proof['output_dex_sha256'], hashlib.sha256(output.read_bytes()).hexdigest())
        self.assertEqual(proof['legacy_facebook_lifecycle'], {
            'owner': MIDLET, 'original_delegate': DELEGATE, 'helper': HELPER,
            'call_count': 12, 'method_counts': dict.fromkeys(METHODS, 1),
            'helper_methods_verified': True, 'method_references_only': True,
            'reverse_patch_semantics_verified': True,
        })
        self.assertEqual(hashlib.sha256(source.read_bytes()).hexdigest(), digest)

    def test_reference_only_rewrite_preserves_other_child_modules_and_exception_metadata(self):
        self.check_patch(self.fixture('valid'), self.directory / 'valid-patched.dex')

    def test_actual_shipped_midlet_has_exactly_twelve_reversible_lifecycle_calls(self):
        original = self.directory / 'shipped-original.dex'
        with zipfile.ZipFile(ROOT / 'green-farm-3.apk') as apk:
            original.write_bytes(apk.read('classes.dex'))
        source = self.directory / 'actual.dex'
        result = self.command('FacebookLifecycleOracle', 'fixture', 'actual', source, original)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.check_patch(source, self.directory / 'actual-patched.dex')

    def test_missing_ambiguous_or_malformed_calls_and_helpers_never_publish_output(self):
        for mode in ('missing', 'duplicate', 'wrongopcode', 'wrongcaller', 'wrongprototype',
                     'wrongregisters', 'already', 'unknown', 'missing_midlet',
                     'helpermissing', 'helperproto', 'helpernonstatic'):
            with self.subTest(mode=mode):
                source = self.fixture(mode)
                output = self.directory / (mode + '-must-not-exist.dex')
                digest = hashlib.sha256(source.read_bytes()).hexdigest()
                result = self.patch(source, output)
                self.assertNotEqual(result.returncode, 0)
                self.assertFalse(output.exists())
                self.assertFalse(output.with_suffix('.dex.json').exists())
                self.assertEqual(hashlib.sha256(source.read_bytes()).hexdigest(), digest)


if __name__ == '__main__':
    unittest.main()
