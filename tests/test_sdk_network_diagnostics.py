"""Read back the SDK HTTP hooks, preserving invocation registers and DEX control flow."""
import hashlib
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
import org.jf.dexlib2.builder.*;
import org.jf.dexlib2.builder.instruction.*;
import org.jf.dexlib2.iface.*;
import org.jf.dexlib2.iface.instruction.*;
import org.jf.dexlib2.iface.reference.*;
import org.jf.dexlib2.immutable.*;
import org.jf.dexlib2.immutable.debug.*;
import org.jf.dexlib2.immutable.instruction.*;
import org.jf.dexlib2.immutable.reference.*;

public class SdkNetworkOracle {
 static final String OWNER="Lcom/gameloft/a/b/e;";
 static final String HELPER="Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/NetworkDiagnostics;";
 static final List<MethodReference> ORIGINAL=Arrays.asList(
  new ImmutableMethodReference("Ljava/net/URL;","openConnection",Collections.emptyList(),"Ljava/net/URLConnection;"),
  new ImmutableMethodReference("Ljava/net/URL;","openConnection",Collections.singletonList("Ljava/net/Proxy;"),"Ljava/net/URLConnection;"),
  new ImmutableMethodReference("Ljava/net/HttpURLConnection;","getResponseCode",Collections.emptyList(),"I"),
  new ImmutableMethodReference("Ljava/lang/Exception;","printStackTrace",Collections.emptyList(),"V"));
 static final List<MethodReference> WRAPPERS=Arrays.asList(
  new ImmutableMethodReference(HELPER,"openConnection",Collections.singletonList("Ljava/net/URL;"),"Ljava/net/URLConnection;"),
  new ImmutableMethodReference(HELPER,"openConnection",Arrays.asList("Ljava/net/URL;","Ljava/net/Proxy;"),"Ljava/net/URLConnection;"),
  new ImmutableMethodReference(HELPER,"responseCode",Collections.singletonList("Ljava/net/HttpURLConnection;"),"I"),
  new ImmutableMethodReference(HELPER,"failure",Collections.singletonList("Ljava/lang/Throwable;"),"V"));
 static ClassDef clazz(String owner,List<Method> methods){return new ImmutableClassDef(owner,1,"Ljava/lang/Object;",Collections.emptyList(),"Fixture.java",Collections.emptySet(),Collections.emptyList(),methods);}
 static Method method(String owner,String name,List<? extends CharSequence> params,String result,int flags,int registers,List<? extends Instruction> code,List<ImmutableTryBlock> tries,List<ImmutableLineNumber> lines){
  List<ImmutableMethodParameter> p=new ArrayList<>();for(CharSequence type:params)p.add(new ImmutableMethodParameter(type.toString(),Collections.emptySet(),null));
  return new ImmutableMethod(owner,name,p,result,flags,Collections.emptySet(),Collections.emptySet(),new ImmutableMethodImplementation(registers,code,tries,lines));
 }
 static List<Method> helpers(){
  List<Method> out=new ArrayList<>();List<MethodReference> refs=new ArrayList<>(WRAPPERS);
  refs.add(new ImmutableMethodReference(HELPER,"begin",Collections.emptyList(),"V"));
  for(MethodReference ref:refs){
   List<Instruction> code=new ArrayList<>();
   if(!ref.getReturnType().equals("V"))code.add(new ImmutableInstruction11n(Opcode.CONST_4,0,0));
   code.add(ref.getReturnType().equals("V")?new ImmutableInstruction10x(Opcode.RETURN_VOID):new ImmutableInstruction11x(ref.getReturnType().equals("I")?Opcode.RETURN:Opcode.RETURN_OBJECT,0));
   out.add(method(HELPER,ref.getName(),ref.getParameterTypes(),ref.getReturnType(),9,Math.max(1,ref.getParameterTypes().size()),code,Collections.emptyList(),Collections.emptyList()));
  }return out;
 }
 static Instruction invoke(MethodReference ref,int count,int first,boolean range){
  return range?new ImmutableInstruction3rc(Opcode.INVOKE_VIRTUAL_RANGE,first,count,ref)
   :new ImmutableInstruction35c(Opcode.INVOKE_VIRTUAL,count,first,count==2?1:0,0,0,0,ref);
 }
 static void fixture(String mode,String path)throws Exception{
  BackendEndpointOracle.fixture("narrow",path);TreeMap<String,ClassDef> defs=BackendEndpointOracle.read(path);
  boolean range=mode.equals("range");
  List<Instruction> code=new ArrayList<>(Arrays.asList(
   new ImmutableInstruction11n(Opcode.CONST_4,0,0),new ImmutableInstruction11n(Opcode.CONST_4,1,0),
   new ImmutableInstruction21t(Opcode.IF_EQZ,0,14),
   invoke(ORIGINAL.get(0),1,0,range),new ImmutableInstruction11x(Opcode.MOVE_RESULT_OBJECT,2),
   invoke(ORIGINAL.get(1),2,0,range),new ImmutableInstruction11x(Opcode.MOVE_RESULT_OBJECT,2),
   invoke(ORIGINAL.get(2),1,2,range),new ImmutableInstruction11x(Opcode.MOVE_RESULT,3),
   new ImmutableInstruction10x(Opcode.RETURN_VOID),new ImmutableInstruction11x(Opcode.MOVE_EXCEPTION,4),
   invoke(ORIGINAL.get(3),1,4,range),new ImmutableInstruction10x(Opcode.RETURN_VOID)));
  if(mode.equals("missing_hook"))code.set(7,new ImmutableInstruction35c(Opcode.INVOKE_VIRTUAL,1,2,0,0,0,0,new ImmutableMethodReference("Ljava/net/HttpURLConnection;","getContentLength",Collections.emptyList(),"I")));
  if(mode.equals("bad_invoke"))code.set(3,new ImmutableInstruction35c(Opcode.INVOKE_STATIC,1,0,0,0,0,0,ORIGINAL.get(0)));
  if(mode.equals("bad_registers"))code.set(3,new ImmutableInstruction35c(Opcode.INVOKE_VIRTUAL,2,0,1,0,0,0,ORIGINAL.get(0)));
  if(mode.equals("duplicate_hook"))code.set(11,invoke(ORIGINAL.get(0),1,0,range));
  List<ImmutableTryBlock> tries=Collections.singletonList(new ImmutableTryBlock(4,12,Collections.singletonList(new ImmutableExceptionHandler("Ljava/lang/Exception;",17))));
  List<Method> methods=new ArrayList<>();
  methods.add(method(OWNER,mode.equals("wrong_method")?"notRun":"run",Collections.emptyList(),"V",mode.equals("static_run")?9:1,6,code,tries,Collections.singletonList(new ImmutableLineNumber(4,1187))));
  methods.add(method(OWNER,"unrelated",Collections.emptyList(),"V",9,1,Arrays.asList(invoke(ORIGINAL.get(3),1,0,false),new ImmutableInstruction10x(Opcode.RETURN_VOID)),Collections.emptyList(),Collections.emptyList()));
  if(!mode.equals("missing_owner"))defs.put(OWNER,clazz(OWNER,methods));
  if(!mode.equals("missing_helper")){
   List<Method> helpers=helpers();if(mode.equals("missing_helper_method"))helpers.remove(0);
   defs.put(HELPER,clazz(HELPER,helpers));
  }
  Files.write(Paths.get(path),BackendEndpointOracle.canonical(defs.values()));
 }
 static Method run(ClassDef c){for(Method m:c.getMethods())if(m.getName().equals("run"))return m;throw new AssertionError("missing run");}
 static MethodReference ref(Instruction i){return i instanceof ReferenceInstruction&&((ReferenceInstruction)i).getReference() instanceof MethodReference?(MethodReference)((ReferenceInstruction)i).getReference():null;}
 static int indexOf(List<MethodReference> values,MethodReference x){if(x==null)return -1;for(int i=0;i<values.size();i++)if(values.get(i).toString().equals(x.toString()))return i;return -1;}
 static void verify(String input,String output)throws Exception{
  TreeMap<String,ClassDef> before=BackendEndpointOracle.read(input),after=BackendEndpointOracle.read(output);
  if(!before.keySet().equals(after.keySet()))throw new AssertionError("class set changed");
  Method original=run(before.get(OWNER)),patched=run(after.get(OWNER));
  List<Instruction>a=new ArrayList<>(),b=new ArrayList<>();original.getImplementation().getInstructions().forEach(a::add);patched.getImplementation().getInstructions().forEach(b::add);
  if(a.size()+1!=b.size()||b.get(0).getOpcode()!=Opcode.INVOKE_STATIC||!ref(b.get(0)).toString().equals(HELPER+"->begin()V")||((VariableRegisterInstruction)b.get(0)).getRegisterCount()!=0)throw new AssertionError("wrong diagnostic entry");
  if(original.getImplementation().getRegisterCount()!=patched.getImplementation().getRegisterCount())throw new AssertionError("register count changed");
  int changes=0;
  for(int i=0;i<a.size();i++){
   Instruction x=a.get(i),y=b.get(i+1);int hook=indexOf(ORIGINAL,ref(x));
   if(hook<0){if(x.getOpcode()!=y.getOpcode())throw new AssertionError("other opcode changed");}
   else{
    changes++;if(indexOf(WRAPPERS,ref(y))!=hook)throw new AssertionError("wrong wrapper");
    if(y.getOpcode()!=(x.getOpcode()==Opcode.INVOKE_VIRTUAL_RANGE?Opcode.INVOKE_STATIC_RANGE:Opcode.INVOKE_STATIC))throw new AssertionError("wrong static invoke format");
    if(((VariableRegisterInstruction)x).getRegisterCount()!=((VariableRegisterInstruction)y).getRegisterCount())throw new AssertionError("invoke arguments changed");
    if(x instanceof RegisterRangeInstruction){if(((RegisterRangeInstruction)x).getStartRegister()!=((RegisterRangeInstruction)y).getStartRegister())throw new AssertionError("range changed");}
    else{FiveRegisterInstruction p=(FiveRegisterInstruction)x,q=(FiveRegisterInstruction)y;if(p.getRegisterC()!=q.getRegisterC()||p.getRegisterD()!=q.getRegisterD()||p.getRegisterE()!=q.getRegisterE()||p.getRegisterF()!=q.getRegisterF()||p.getRegisterG()!=q.getRegisterG())throw new AssertionError("argument registers changed");}
   }
   if(x.getCodeUnits()!=y.getCodeUnits())throw new AssertionError("instruction width changed");
   if(x instanceof OffsetInstruction&&((OffsetInstruction)x).getCodeOffset()!=((OffsetInstruction)y).getCodeOffset())throw new AssertionError("branch changed");
  }
  if(changes!=4)throw new AssertionError("missing hook");
  List<? extends TryBlock<? extends ExceptionHandler>> oldTries=original.getImplementation().getTryBlocks(),newTries=patched.getImplementation().getTryBlocks();
  if(oldTries.size()!=newTries.size())throw new AssertionError("try count changed");
  for(int i=0;i<oldTries.size();i++){TryBlock<? extends ExceptionHandler>x=oldTries.get(i),y=newTries.get(i);if(x.getStartCodeAddress()+3!=y.getStartCodeAddress()||x.getCodeUnitCount()!=y.getCodeUnitCount()||x.getExceptionHandlers().get(0).getHandlerCodeAddress()+3!=y.getExceptionHandlers().get(0).getHandlerCodeAddress())throw new AssertionError("handler metadata changed");}
  // Independently reverse the wrappers; compare canonical owner incl debug,
  // annotations, fields and untouched methods, not just the instrumented code.
  MutableMethodImplementation reverse=new MutableMethodImplementation(patched.getImplementation());reverse.removeInstruction(0);
  for(int i=0;i<reverse.getInstructions().size();i++){
   Instruction ins=reverse.getInstructions().get(i);int hook=indexOf(WRAPPERS,ref(ins));if(hook<0)continue;
   if(ins instanceof RegisterRangeInstruction){RegisterRangeInstruction r=(RegisterRangeInstruction)ins;reverse.replaceInstruction(i,new BuilderInstruction3rc(Opcode.INVOKE_VIRTUAL_RANGE,r.getStartRegister(),r.getRegisterCount(),ORIGINAL.get(hook)));}
   else{FiveRegisterInstruction r=(FiveRegisterInstruction)ins;reverse.replaceInstruction(i,new BuilderInstruction35c(Opcode.INVOKE_VIRTUAL,r.getRegisterCount(),r.getRegisterC(),r.getRegisterD(),r.getRegisterE(),r.getRegisterF(),r.getRegisterG(),ORIGINAL.get(hook)));}
  }
  ClassDef owner=after.get(OWNER);List<Method> restored=new ArrayList<>();for(Method m:owner.getMethods())restored.add(m.getName().equals("run")?new ImmutableMethod(m.getDefiningClass(),m.getName(),m.getParameters(),m.getReturnType(),m.getAccessFlags(),m.getAnnotations(),m.getHiddenApiRestrictions(),reverse):m);
  ClassDef reverted=new ImmutableClassDef(owner.getType(),owner.getAccessFlags(),owner.getSuperclass(),owner.getInterfaces(),owner.getSourceFile(),owner.getAnnotations(),owner.getFields(),restored);
  if(!Arrays.equals(BackendEndpointOracle.canonical(Collections.singletonList(before.get(OWNER))),BackendEndpointOracle.canonical(Collections.singletonList(reverted))))throw new AssertionError("SDK method cannot be restored exactly");
  for(String excluded:Arrays.asList(OWNER,BackendEndpointOracle.OWNER,BackendEndpointOracle.CORE_OWNER)){before.remove(excluded);after.remove(excluded);}
  if(!Arrays.equals(BackendEndpointOracle.canonical(before.values()),BackendEndpointOracle.canonical(after.values())))throw new AssertionError("other classes changed");
 }
 public static void main(String[]args)throws Exception{
  if(args[0].equals("fixture"))fixture(args[1],args[2]);else verify(args[1],args[2]);
 }
}
'''


class SdkNetworkDiagnosticsTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        java_home = Path(os.environ.get('JAVA_HOME', '/usr/lib/jvm/default-java'))
        cls.java = java_home / 'bin/java'
        javac = java_home / 'bin/javac'
        cache = Path(os.environ.get('GRADLE_USER_HOME', Path.home() / '.gradle')) / 'caches/modules-2/files-2.1'
        jars = list(cache.glob('org.smali/dexlib2/2.5.2/*/*.jar')) + list(cache.glob('com.google.guava/guava/*/*/*.jar'))
        if not cls.java.is_file() or not javac.is_file() or not jars:
            raise unittest.SkipTest('Requires JDK and Gradle-resolved dexlib2 tool dependencies')
        cls.tmp = tempfile.TemporaryDirectory(prefix='sdk-network-diagnostics-')
        cls.addClassCleanup(cls.tmp.cleanup)
        cls.directory = Path(cls.tmp.name)
        cls.cp = os.pathsep.join(map(str, [cls.directory, *jars]))
        sources = []
        for name, content in [('BackendEndpointOracle', ENDPOINT_ORACLE), ('SdkNetworkOracle', ORACLE)]:
            source = cls.directory / (name + '.java')
            source.write_text(content)
            sources.append(str(source))
        result = subprocess.run([str(javac), '-cp', cls.cp, '-d', str(cls.directory),
                                 str(ROOT / 'tools/PatchBackendEndpoint.java'), *sources], capture_output=True, text=True)
        if result.returncode:
            raise AssertionError(result.stdout + result.stderr)

    def command(self, main, *args):
        return subprocess.run([str(self.java), '-cp', self.cp, main, *map(str, args)],
                              capture_output=True, text=True, timeout=120)

    def fixture(self, mode):
        path = self.directory / (mode + '.dex')
        result = self.command('SdkNetworkOracle', 'fixture', mode, path)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        return path

    def test_hooks_keep_original_invocation_registers_branches_handlers_and_debug(self):
        for mode in ('valid', 'range'):
            with self.subTest(mode=mode):
                source = self.fixture(mode)
                output = self.directory / (mode + '-patched.dex')
                original = source.read_bytes()
                result = self.command('PatchBackendEndpoint', source, output, 'http://192.168.1.5:8787/', '--network-diagnostics')
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                proof = json.loads(output.with_suffix('.dex.json').read_text())
                network = proof['network_diagnostics']
                self.assertEqual(network['owner'], 'Lcom/gameloft/a/b/e;')
                self.assertEqual(network['hook_counts'], {'open_connection': 1, 'open_connection_proxy': 1, 'response_code': 1, 'failure': 1})
                self.assertTrue(network['reverse_patch_semantics_verified'])
                self.assertEqual(proof['input_dex_sha256'], hashlib.sha256(original).hexdigest())
                self.assertEqual(proof['output_dex_sha256'], hashlib.sha256(output.read_bytes()).hexdigest())
                result = self.command('SdkNetworkOracle', 'verify', source, output)
                self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
                self.assertEqual(source.read_bytes(), original)

    def test_rejects_missing_helper_bad_method_and_unexpected_hook_shapes_before_publication(self):
        for mode in ('missing_helper', 'missing_helper_method', 'missing_owner', 'missing_hook', 'duplicate_hook',
                     'bad_invoke', 'bad_registers', 'wrong_method', 'static_run'):
            with self.subTest(mode=mode):
                source = self.fixture(mode)
                output = self.directory / ('rejected-' + mode + '.dex')
                output.write_bytes(b'previous output')
                report = output.with_suffix('.dex.json')
                report.write_bytes(b'previous report')
                original = source.read_bytes()
                result = self.command('PatchBackendEndpoint', source, output, 'http://192.168.1.5:8787/', '--network-diagnostics')
                self.assertNotEqual(result.returncode, 0)
                self.assertEqual(source.read_bytes(), original)
                self.assertEqual(output.read_bytes(), b'previous output')
                self.assertEqual(report.read_bytes(), b'previous report')
