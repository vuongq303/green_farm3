"""Actual DEX relocation and >65535-string canonicalization regressions."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool

ORACLE = r'''
import java.util.*;
import org.jf.dexlib2.*;
import org.jf.dexlib2.builder.*;
import org.jf.dexlib2.builder.instruction.*;
import org.jf.dexlib2.dexbacked.*;
import org.jf.dexlib2.iface.*;
import org.jf.dexlib2.iface.instruction.*;
import org.jf.dexlib2.iface.reference.*;
import org.jf.dexlib2.iface.value.*;
import org.jf.dexlib2.immutable.*;
import org.jf.dexlib2.immutable.reference.*;
import org.jf.dexlib2.immutable.value.*;
public final class PackageRelocationOracle {
 static final Opcodes OPS=Opcodes.forApi(16);
 static final String OLD="L"+RelocateGamePackage.OLD_INTERNAL+"/", NEW="L"+RelocateGamePackage.NEW_INTERNAL+"/";
 static void check(boolean v,String reason){if(!v)throw new AssertionError(reason);}
 static ClassDef fixture(String owner,String literal){
  MethodImplementationBuilder body=new MethodImplementationBuilder(1);
  Label start=body.addLabel("start");body.addLineNumber(4);
  body.addInstruction(new BuilderInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference(literal)));
  body.addInstruction(new BuilderInstruction10t(Opcode.GOTO,body.getLabel("done")));
  Label end=body.addLabel("end");body.addInstruction(new BuilderInstruction10x(Opcode.NOP));
  Label done=body.addLabel("done");body.addLineNumber(5);body.addInstruction(new BuilderInstruction10x(Opcode.RETURN_VOID));
  body.addCatch("Ljava/lang/Exception;",start,end,done);
  Method method=new ImmutableMethod(owner,"read",Collections.emptyList(),"V",9,Collections.emptySet(),Collections.emptySet(),body.getMethodImplementation());
  Field field=new ImmutableField(owner,"entity","[["+OLD+"S800x480/ch;",1,null,Collections.emptySet(),Collections.emptySet());
  Annotation annotation=new ImmutableAnnotation(1,"Lkotlin/Metadata;",Collections.singletonList(
   new ImmutableAnnotationElement("d2",new ImmutableArrayEncodedValue(Collections.singletonList(new ImmutableStringEncodedValue(OLD+"S800x480/ci;"))))));
  return new ImmutableClassDef(owner,1,"Ljava/lang/Object;",Collections.emptyList(),"Game.kt",Collections.singletonList(annotation),Collections.singletonList(field),Collections.singletonList(method));
 }
 static String literal(ClassDef definition){for(Method m:definition.getMethods())for(Instruction i:m.getImplementation().getInstructions())if(i instanceof ReferenceInstruction&&((ReferenceInstruction)i).getReference() instanceof StringReference)return ((StringReference)((ReferenceInstruction)i).getReference()).getString();throw new AssertionError("no literal");}
 static ClassDef provider(int authorityCopies,boolean includeUri,String methodName){
  String owner=NEW+"KeyProvider;";MethodImplementationBuilder body=new MethodImplementationBuilder(1);
  if(includeUri)body.addInstruction(new BuilderInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference("content://"+RelocateGamePackage.NEW_PACKAGE+".KeyProvider/key")));
  for(int n=0;n<authorityCopies;n++)body.addInstruction(new BuilderInstruction21c(Opcode.CONST_STRING,0,new ImmutableStringReference(RelocateGamePackage.NEW_PACKAGE+".KeyProvider")));
  body.addInstruction(new BuilderInstruction10x(Opcode.RETURN_VOID));
  Method init=new ImmutableMethod(owner,methodName,Collections.emptyList(),"V",8,Collections.emptySet(),Collections.emptySet(),body.getMethodImplementation());
  return new ImmutableClassDef(owner,1,"Landroid/content/ContentProvider;",Collections.emptyList(),null,Collections.emptySet(),Collections.emptyList(),Collections.singletonList(init));
 }
 public static void main(String[] args)throws Exception{
  ClassDef game=fixture(OLD+"S800x480/Test;",RelocateGamePackage.OLD_PACKAGE+".Start");
  ClassDef migration=fixture(OLD+"migration/Transfer;",RelocateGamePackage.OLD_PACKAGE);
  ClassDef migrated=RelocateGamePackage.rewriteClass(game,false);
  check(migrated.getType().equals(NEW+"S800x480/Test;"),"class type");
  check(literal(migrated).equals(RelocateGamePackage.NEW_PACKAGE+".Start"),"reflection component literal");
  check(migrated.getFields().iterator().next().getType().equals("[["+NEW+"S800x480/ch;"),"array type");
  Annotation annotation=migrated.getAnnotations().iterator().next();
  ArrayEncodedValue values=(ArrayEncodedValue)annotation.getElements().iterator().next().getValue();
  check(((StringEncodedValue)values.getValue().get(0)).getValue().equals(NEW+"S800x480/ci;"),"Kotlin metadata descriptor");
  check(literal(RelocateGamePackage.rewriteClass(migration,false)).equals(RelocateGamePackage.OLD_PACKAGE),"migration retains legacy identity");
  ClassDef newMigration=fixture(NEW+"migration/Transfer;",RelocateGamePackage.NEW_PACKAGE);
  check(literal(RelocateGamePackage.rewriteClass(newMigration,true)).equals(RelocateGamePackage.NEW_PACKAGE),"migration retains target identity during normalization");
  check(Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(game),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(RelocateGamePackage.rewriteClass(migrated,true)),OPS)),"inverse proof");
  ClassDef engineBase=fixture(OLD+"S800x480/ci;",RelocateGamePackage.OLD_PACKAGE+".S800x480.GLLib");
  List<Field> engineFields=new ArrayList<>();for(Field f:engineBase.getFields())engineFields.add(f);
  engineFields.add(new ImmutableField(engineBase.getType(),"aYR","I",8,new ImmutableIntEncodedValue(0),Collections.emptySet(),Collections.emptySet()));
  engineFields.add(new ImmutableField(engineBase.getType(),"baa","Z",8,ImmutableBooleanEncodedValue.FALSE_VALUE,Collections.emptySet(),Collections.emptySet()));
  List<Method> engineMethods=new ArrayList<>();for(Method m:engineBase.getMethods())engineMethods.add(m);
  for(String name:new String[]{"qD","qE"}){
   MethodImplementationBuilder getter=new MethodImplementationBuilder(1);
   getter.addInstruction(new BuilderInstruction11n(Opcode.CONST_4,0,1));getter.addInstruction(new BuilderInstruction11x(Opcode.RETURN,0));
   engineMethods.add(new ImmutableMethod(engineBase.getType(),name,Collections.emptyList(),"I",8,Collections.emptySet(),Collections.emptySet(),getter.getMethodImplementation()));
  }
  ClassDef engine=new ImmutableClassDef(engineBase.getType(),engineBase.getAccessFlags(),engineBase.getSuperclass(),engineBase.getInterfaces(),engineBase.getSourceFile(),engineBase.getAnnotations(),engineFields,engineMethods);
  ClassDef renamed=RelocateGamePackage.rewriteClass(engine,false,true);
  check(renamed.getType().equals(NEW+"S800x480/FarmGameEngine;"),"engine class mapping");
  Set<String> fieldNames=new HashSet<>();for(Field f:renamed.getFields())fieldNames.add(f.getName());
  check(fieldNames.contains("currentTutorialStepIndex")&&fieldNames.contains("hasUnsavedGameChanges")&&!fieldNames.contains("aYR")&&!fieldNames.contains("baa"),"exact engine field mapping");
  Set<String> methodNames=new HashSet<>();for(Method m:renamed.getMethods())methodNames.add(m.getName());
  check(methodNames.contains("currentEngineState")&&methodNames.contains("previousEngineState")&&!methodNames.contains("qD")&&!methodNames.contains("qE"),"exact state accessor method mapping");
  check(literal(renamed).equals(RelocateGamePackage.NEW_PACKAGE+".S800x480.GameRuntime"),"qualified reflection class name mapping");
  check(RelocateGamePackage.mapType("[["+OLD+"S800x480/ch;",false,true).equals("[["+NEW+"S800x480/FarmEntity;"),"renamed array element");
  check(RelocateGamePackage.mapType(OLD+"S800x480/ciOther;",false,true).equals(NEW+"S800x480/ciOther;"),"whole-class boundary");
  check(Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(engine),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(RelocateGamePackage.rewriteClass(renamed,true,true)),OPS)),"symbol mapping inverse proof");
  ClassDef reflector=fixture("Ljavax/microedition/lcdui/f;",".GLLib");
  check(literal(RelocateGamePackage.rewriteClass(reflector,false,true)).equals(".GameRuntime"),"audited reflection suffix");
  check(literal(RelocateGamePackage.rewriteClass(fixture("Lvendor/Other;",".GLLib"),false,true)).equals(".GLLib"),"unrelated suffix remains");
  ClassDef mainProvider=provider(1,true,"<clinit>");
  ClassDef bridgeProvider=RelocateGamePackage.providerIdentity(mainProvider,true);
  List<String> providerStrings=new ArrayList<>();for(Method m:bridgeProvider.getMethods())for(Instruction i:m.getImplementation().getInstructions())if(i instanceof ReferenceInstruction)providerStrings.add(((StringReference)((ReferenceInstruction)i).getReference()).getString());
  check(providerStrings.equals(Arrays.asList("content://"+RelocateGamePackage.OLD_PACKAGE+".KeyProvider/key",RelocateGamePackage.OLD_PACKAGE+".KeyProvider")),"legacy bridge keeps only its authority/URI");
  check(bridgeProvider.getType().equals(NEW+"KeyProvider;"),"bridge provider class still uses new namespace");
  check(Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(mainProvider),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(RelocateGamePackage.providerIdentity(bridgeProvider,false)),OPS)),"provider policy inverse proof");
  ClassDef offlineProvider=RelocateGamePackage.providerIdentity(mainProvider,RelocateGamePackage.NEW_PACKAGE,RelocateGamePackage.OFFLINE_PACKAGE);
  List<String> offlineStrings=new ArrayList<>();for(Method m:offlineProvider.getMethods())for(Instruction i:m.getImplementation().getInstructions())if(i instanceof ReferenceInstruction)offlineStrings.add(((StringReference)((ReferenceInstruction)i).getReference()).getString());
  check(offlineStrings.equals(Arrays.asList("content://"+RelocateGamePackage.OFFLINE_PACKAGE+".KeyProvider/key",RelocateGamePackage.OFFLINE_PACKAGE+".KeyProvider")),"offline app provider URI follows isolated application ID");
  check(offlineProvider.getType().equals(NEW+"KeyProvider;"),"offline provider code namespace unchanged");
  check(Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(mainProvider),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(RelocateGamePackage.providerIdentity(offlineProvider,RelocateGamePackage.OFFLINE_PACKAGE,RelocateGamePackage.NEW_PACKAGE)),OPS)),"offline provider policy inverse proof");
  for(ClassDef invalid:Arrays.asList(provider(0,true,"<clinit>"),provider(2,true,"<clinit>"),provider(1,false,"<clinit>"),provider(1,true,"otherMethod"))){
   try{RelocateGamePackage.providerIdentity(invalid,true);throw new AssertionError("unreviewed provider literal site accepted");}catch(java.io.IOException expected){}
  }
  try{RelocateGamePackage.rewriteClasses(Arrays.asList(game,RelocateGamePackage.rewriteClass(game,false)),false);throw new AssertionError("collision accepted");}catch(java.io.IOException expected){}

  ClassDef small=fixture(OLD+"S800x480/Test;","zzzz-last");
  List<EncodedValue> strings=new ArrayList<>();for(int i=0;i<66000;i++)strings.add(new ImmutableStringEncodedValue(String.format("a%05d",i)));
  ClassDef filler=new ImmutableClassDef("Lvendor/Strings;",1,"Ljava/lang/Object;",Collections.emptyList(),null,Collections.emptySet(),
   Collections.singletonList(new ImmutableField("Lvendor/Strings;","values","[Ljava/lang/String;",9,new ImmutableArrayEncodedValue(strings),Collections.emptySet(),Collections.emptySet())),Collections.emptyList());
  byte[] combined=RelocateGamePackage.serialize(Arrays.asList(small,filler),OPS);
  DexBackedDexFile written=new DexBackedDexFile(OPS,combined);
  check(written.getStringReferences().size()>65535,"fixture must exceed normal string index");
  ClassDef wide=null;for(ClassDef c:written.getClasses())if(c.getType().equals(small.getType()))wide=c;
  boolean jumbo=false;for(Method m:wide.getMethods())for(Instruction i:m.getImplementation().getInstructions())if(i.getOpcode()==Opcode.CONST_STRING_JUMBO)jumbo=true;
  check(jumbo,"writer must really promote const-string to jumbo");
  check(!Arrays.equals(RelocateGamePackage.serialize(Collections.singletonList(small),OPS),RelocateGamePackage.serialize(Collections.singletonList(wide),OPS)),"old comparison must expose false mismatch");
  check(Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(small),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(wide),OPS)),"jumbo/branch/try/debug semantic canonicalization");
  ClassDef mutation=fixture(small.getType(),"zzzz-wrong-value");
  check(!Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(small),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(mutation),OPS)),"literal mutation must fail proof");
  Method originalMethod=small.getMethods().iterator().next();
  MutableMethodImplementation wrongBranch=new MutableMethodImplementation(originalMethod.getImplementation());
  wrongBranch.replaceInstruction(1,new BuilderInstruction10t(Opcode.GOTO,wrongBranch.newLabelForIndex(0)));
  Method mutatedMethod=new ImmutableMethod(originalMethod.getDefiningClass(),originalMethod.getName(),originalMethod.getParameters(),originalMethod.getReturnType(),originalMethod.getAccessFlags(),originalMethod.getAnnotations(),originalMethod.getHiddenApiRestrictions(),wrongBranch);
  ClassDef branchMutation=new ImmutableClassDef(small.getType(),small.getAccessFlags(),small.getSuperclass(),small.getInterfaces(),small.getSourceFile(),small.getAnnotations(),small.getFields(),Collections.singletonList(mutatedMethod));
  check(!Arrays.equals(RelocateGamePackage.semanticBytes(Collections.singletonList(small),OPS),RelocateGamePackage.semanticBytes(Collections.singletonList(branchMutation),OPS)),"branch target mutation must fail proof");
  System.out.println("PASS package/types/metadata/reflection/migration/collision, real jumbo promotion and literal mutation rejection");
 }
}
'''


class PackageRelocationTest(unittest.TestCase):
    def test_structural_relocation_and_jumbo_pool_semantics(self):
        jars = Path('/workspace/tooling/dexmerge')
        if not (jars / 'dexlib2-2.5.2.jar').is_file():
            self.skipTest('Requires cached dexlib2 2.5.2 test dependencies')
        with tempfile.TemporaryDirectory(prefix='package-relocation-') as temporary:
            directory = Path(temporary)
            oracle = directory / 'PackageRelocationOracle.java'
            oracle.write_text(ORACLE)
            classpath = str(directory) + os.pathsep + str(jars / '*')
            result = subprocess.run([str(jdk_tool('javac')), '-cp', classpath, '-d', str(directory),
                                     str(ROOT / 'tools/RelocateGamePackage.java'), str(oracle)],
                                    capture_output=True, text=True, timeout=120)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-Xmx2g', '-cp', classpath,
                                     'PackageRelocationOracle'],capture_output=True,text=True,timeout=120)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS package/types/metadata/reflection/migration/collision', result.stdout)


if __name__ == '__main__':
    unittest.main()
