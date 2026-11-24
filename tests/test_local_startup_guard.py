"""Read back the production DEX guard, including unchanged SDK branches/handlers."""
import json
import os
from pathlib import Path
import subprocess
import tempfile
import unittest
from test_backend_endpoint_patch import ORACLE as ENDPOINT_ORACLE

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
import org.jf.dexlib2.immutable.debug.*;
import org.jf.dexlib2.immutable.instruction.*;
import org.jf.dexlib2.immutable.reference.*;

public class LocalStartupGuardOracle {
    static final String OWNER="Lcom/gameloft/android/wrapper/au;";
    static final String HELPER="Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LocalDevelopmentMode;";
    static Method startup(ClassDef c) {
        for(Method m:c.getMethods()) if(m.getName().equals("Qh")) return m;
        throw new AssertionError("Missing Qh");
    }
    static ClassDef clazz(String owner,List<Method> methods) {
        return new ImmutableClassDef(owner,1,"Ljava/lang/Object;",Collections.emptyList(),
            "Fixture.java",Collections.emptySet(),Collections.emptyList(),methods);
    }
    static Method method(String owner,String name,String type,int registers,List<Instruction> code,
                         List<ImmutableTryBlock> blocks,List<ImmutableLineNumber> lines) {
        return new ImmutableMethod(owner,name,Collections.emptyList(),type,9,Collections.emptySet(),Collections.emptySet(),
            new ImmutableMethodImplementation(registers,code,blocks,lines));
    }
    static void fixture(String path,boolean helper,String predicate) throws Exception {
        BackendEndpointOracle.fixture("narrow",path);
        TreeMap<String,ClassDef> classes=BackendEndpointOracle.read(path);
        List<Instruction> code=Arrays.asList(
            new ImmutableInstruction11n(Opcode.CONST_4,0,1),
            new ImmutableInstruction21t(Opcode.IF_EQZ,0,5),
            new ImmutableInstruction35c(Opcode.INVOKE_STATIC,0,0,0,0,0,0,
                new ImmutableMethodReference("Lbackend/Client;","login",Collections.emptyList(),"V")),
            new ImmutableInstruction10x(Opcode.RETURN_VOID),
            new ImmutableInstruction11x(Opcode.MOVE_EXCEPTION,1),
            new ImmutableInstruction10x(Opcode.RETURN_VOID));
        classes.put(OWNER,clazz(OWNER,Collections.singletonList(method(OWNER,"Qh","V",2,code,
            Collections.singletonList(new ImmutableTryBlock(3,3,Collections.singletonList(
                new ImmutableExceptionHandler("Ljava/lang/Exception;",7)))),
            Collections.singletonList(new ImmutableLineNumber(3,77))))));
        if(helper) classes.put(HELPER,clazz(HELPER,Collections.singletonList(method(HELPER,predicate,"Z",1,
            Arrays.asList(new ImmutableInstruction11n(Opcode.CONST_4,0,0),new ImmutableInstruction11x(Opcode.RETURN,0)),
            Collections.emptyList(),Collections.emptyList()))));
        Files.write(Paths.get(path),BackendEndpointOracle.canonical(classes.values()));
    }
    static void verify(String source,String output) throws Exception {
        TreeMap<String,ClassDef> before=BackendEndpointOracle.read(source), after=BackendEndpointOracle.read(output);
        Method old=startup(before.get(OWNER)), guarded=startup(after.get(OWNER));
        List<Instruction> a=new ArrayList<>(),b=new ArrayList<>();
        old.getImplementation().getInstructions().forEach(a::add);
        guarded.getImplementation().getInstructions().forEach(b::add);
        if(b.size()!=a.size()+4) throw new AssertionError("Wrong guard size");
        if(b.get(0).getOpcode()!=Opcode.INVOKE_STATIC || b.get(1).getOpcode()!=Opcode.MOVE_RESULT
            || b.get(2).getOpcode()!=Opcode.IF_EQZ || b.get(3).getOpcode()!=Opcode.RETURN_VOID)
            throw new AssertionError("Wrong guard opcodes");
        MethodReference ref=(MethodReference)((ReferenceInstruction)b.get(0)).getReference();
        if(!ref.getDefiningClass().equals(HELPER)||!ref.getName().equals("skipLegacyBackend")
            ||!ref.getParameterTypes().isEmpty()||!ref.getReturnType().equals("Z"))
            throw new AssertionError("Guard calls an unexpected predicate");
        if(((OneRegisterInstruction)b.get(1)).getRegisterA()!=0
            ||((OneRegisterInstruction)b.get(2)).getRegisterA()!=0
            ||((OffsetInstruction)b.get(2)).getCodeOffset()!=3)
            throw new AssertionError("Guard must fall through to return or branch exactly to original start");
        // Offline or online-account mode must skip legacy anonymous startup;
        // ordinary legacy mode still enters the original SDK path.
        for(boolean local:new boolean[]{false,true}) for(boolean online:new boolean[]{false,true}) {
            boolean skip=local||online;
            int index=2;
            index=skip?index+1:index+2;
            boolean callsOriginal=b.get(index).getOpcode()!=Opcode.RETURN_VOID;
            if(callsOriginal==skip) throw new AssertionError("Incorrect legacy/offline/online-account branch");
        }
        for(int n=0;n<a.size();n++) {
            Instruction x=a.get(n),y=b.get(n+4);
            if(x.getOpcode()!=y.getOpcode()||x.getCodeUnits()!=y.getCodeUnits())
                throw new AssertionError("Original opcode/layout changed");
            if(x instanceof OffsetInstruction && ((OffsetInstruction)x).getCodeOffset()!=((OffsetInstruction)y).getCodeOffset())
                throw new AssertionError("Original branch changed");
        }
        TryBlock<?> block=guarded.getImplementation().getTryBlocks().get(0);
        if(block.getStartCodeAddress()!=10 ||block.getCodeUnitCount()!=3
            ||block.getExceptionHandlers().get(0).getHandlerCodeAddress()!=14)
            throw new AssertionError("Guard overlaps original SDK exception handler");
        if(guarded.getImplementation().getDebugItems().iterator().next().getCodeAddress()!=10)
            throw new AssertionError("Original debug address not preserved");
        // Other methods/fields/annotations are preserved: replace only the
        // independently checked implementation with the original then compare.
        ClassDef current=after.get(OWNER);
        List<Method> restored=new ArrayList<>();
        for(Method m:current.getMethods()) restored.add(m.getName().equals("Qh")?old:m);
        ClassDef restoredOwner=new ImmutableClassDef(current.getType(),current.getAccessFlags(),current.getSuperclass(),
            current.getInterfaces(),current.getSourceFile(),current.getAnnotations(),current.getFields(),restored);
        if(!Arrays.equals(BackendEndpointOracle.canonical(Collections.singleton(before.get(OWNER))),
            BackendEndpointOracle.canonical(Collections.singleton(restoredOwner))))
            throw new AssertionError("Other SDK owner semantics changed");
        System.out.println("PASS: offline/online-account modes return, legacy original path retained, original branches/try/debug metadata intact");
    }
    public static void main(String[] args) throws Exception {
        if(args[0].equals("fixture")) fixture(args[1],Boolean.parseBoolean(args[2]),args.length>3?args[3]:"skipLegacyBackend");
        else verify(args[1],args[2]);
    }
}
'''


class LocalStartupGuardTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        java_home = Path(os.environ.get('JAVA_HOME', '/usr/lib/jvm/default-java'))
        cls.java = java_home / 'bin/java'
        javac = java_home / 'bin/javac'
        cache = Path(os.environ.get('GRADLE_USER_HOME', Path.home() / '.gradle')) / 'caches/modules-2/files-2.1'
        jars = list(cache.glob('org.smali/dexlib2/2.5.2/*/*.jar')) + list(cache.glob('com.google.guava/guava/*/*/*.jar'))
        if not javac.is_file() or not jars:
            raise unittest.SkipTest('Requires JDK and Gradle-resolved dexlib2 tool dependencies')
        cls.tmp = tempfile.TemporaryDirectory(prefix='local-startup-guard-')
        cls.addClassCleanup(cls.tmp.cleanup)
        cls.directory = Path(cls.tmp.name)
        cls.cp = os.pathsep.join(map(str, [cls.directory, *jars]))
        sources = []
        for name, content in [('BackendEndpointOracle', ENDPOINT_ORACLE), ('LocalStartupGuardOracle', ORACLE)]:
            path = cls.directory / (name + '.java')
            path.write_text(content)
            sources.append(path)
        result = subprocess.run([str(javac), '-cp', cls.cp, '-d', str(cls.directory),
                                 str(ROOT / 'tools/PatchBackendEndpoint.java'), *map(str, sources)], capture_output=True, text=True)
        if result.returncode:
            raise AssertionError(result.stdout + result.stderr)

    def command(self, entry, *arguments):
        return subprocess.run([str(self.java), '-cp', self.cp, entry, *map(str, arguments)],
                              capture_output=True, text=True)

    def test_conditional_guard_keeps_original_sdk_branch_and_handler(self):
        source, output = self.directory / 'source.dex', self.directory / 'guarded.dex'
        result = self.command('LocalStartupGuardOracle', 'fixture', source, 'true')
        self.assertEqual(result.returncode, 0, result.stderr)
        result = self.command('PatchBackendEndpoint', source, output, 'http://192.168.1.5:8787/', '--guard-local-startup')
        self.assertEqual(result.returncode, 0, result.stderr)
        result = self.command('LocalStartupGuardOracle', 'verify', source, output)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        proof = json.loads(output.with_suffix('.dex.json').read_text())['local_startup_guard']
        self.assertEqual(proof['predicate'],
                         'Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/LocalDevelopmentMode;->skipLegacyBackend()Z')
        self.assertTrue(proof['original_false_branch_preserved'])
        self.assertTrue(proof['reverse_patch_semantics_verified'])

    def test_guard_rejects_missing_helper_without_publishing_output(self):
        source, output = self.directory / 'missing-helper.dex', self.directory / 'must-not-exist.dex'
        result = self.command('LocalStartupGuardOracle', 'fixture', source, 'false')
        self.assertEqual(result.returncode, 0, result.stderr)
        result = self.command('PatchBackendEndpoint', source, output, 'http://192.168.1.5:8787/', '--guard-local-startup')
        self.assertNotEqual(result.returncode, 0)
        self.assertFalse(output.exists())
        self.assertIn('helper is missing', result.stderr)

    def test_old_offline_only_predicate_cannot_satisfy_the_online_account_guard(self):
        source, output = self.directory / 'old-helper.dex', self.directory / 'old-must-not-exist.dex'
        result = self.command('LocalStartupGuardOracle', 'fixture', source, 'true', 'isEnabled')
        self.assertEqual(result.returncode, 0, result.stderr)
        result = self.command('PatchBackendEndpoint', source, output, 'http://192.168.1.5:8787/', '--guard-local-startup')
        self.assertNotEqual(result.returncode, 0)
        self.assertFalse(output.exists())
        self.assertIn('helper is missing', result.stderr)
