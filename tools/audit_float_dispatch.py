#!/usr/bin/env python3
"""Find possible lost float conversions by comparing original/current bytecode.

This read-only audit finds calls in matching containing methods where an original
float overload became a corresponding integer overload. Findings require source
and smali review: shared decompiler blocks can duplicate calls, and formatting
calls can legitimately differ without changing game behavior. It does not audit
methods whose own descriptors changed, conversions other than float to int, or
calls renamed/inlined by a decompiler.
"""

import argparse
import json
import os
from pathlib import Path
import subprocess
import tempfile


ROOT = Path(__file__).resolve().parents[1]
EXPORT = '--add-exports=java.base/jdk.internal.org.objectweb.asm=ALL-UNNAMED'
JAVA_SOURCE = r'''
import java.nio.file.*;
import java.util.*;
import java.util.jar.*;
import jdk.internal.org.objectweb.asm.*;

public class AuditFloatDispatch {
 static class Call {
  final String owner,name,descriptor;
  Call(String o,String n,String d){owner=o;name=n;descriptor=d;}
  public String toString(){return owner+"."+name+descriptor;}
  public boolean equals(Object other){return other instanceof Call&&toString().equals(other.toString());}
  public int hashCode(){return toString().hashCode();}
 }
 static Map<String,List<Call>> calls(byte[] bytes){
  Map<String,List<Call>> methods=new LinkedHashMap<>();
  new ClassReader(bytes).accept(new ClassVisitor(Opcodes.ASM8){
   public MethodVisitor visitMethod(int access,String name,String descriptor,String signature,String[] errors){
    List<Call> invocations=new ArrayList<>();methods.put(name+descriptor,invocations);
    return new MethodVisitor(Opcodes.ASM8){
     public void visitMethodInsn(int op,String owner,String name,String descriptor,boolean itf){
      invocations.add(new Call(owner,name,descriptor));
     }
    };
   }
  },ClassReader.SKIP_DEBUG|ClassReader.SKIP_FRAMES);
  return methods;
 }
 static boolean lostFloat(Call before,Call after){
  if(!before.owner.equals(after.owner)||!before.name.equals(after.name))return false;
  Type[] oldArgs=Type.getArgumentTypes(before.descriptor),newArgs=Type.getArgumentTypes(after.descriptor);
  if(oldArgs.length!=newArgs.length)return false;
  boolean changed=false;
  for(int i=0;i<oldArgs.length;i++){
   if(oldArgs[i].equals(newArgs[i]))continue;
   if(oldArgs[i].getSort()==Type.FLOAT&&newArgs[i].getSort()==Type.INT){changed=true;continue;}
   return false;
  }
  return changed;
 }
 static long count(List<Call> calls,Call target){return calls.stream().filter(target::equals).count();}
 public static void main(String[] args)throws Exception{
  Path directory=Paths.get(args[1]);int checked=0;
  try(JarFile jar=new JarFile(args[0]);var paths=Files.walk(directory)){
   for(Path path:(Iterable<Path>)paths.filter(p->p.toString().endsWith(".class")).sorted()::iterator){
    String relative=directory.relativize(path).toString();JarEntry entry=jar.getJarEntry(relative);
    if(entry==null)continue;checked++;
    byte[] originalBytes;
    try(var input=jar.getInputStream(entry)){originalBytes=input.readAllBytes();}
    var original=calls(originalBytes);var current=calls(Files.readAllBytes(path));
    for(var method:original.entrySet()){
     List<Call> restored=current.get(method.getKey());if(restored==null)continue;
     for(Call target:new LinkedHashSet<>(method.getValue())){
      long oldCount=count(method.getValue(),target),newCount=count(restored,target);
      if(newCount>=oldCount)continue;
      for(Call candidate:new LinkedHashSet<>(restored)){
       if(!lostFloat(target,candidate))continue;
       long oldInteger=count(method.getValue(),candidate),newInteger=count(restored,candidate);
       if(newInteger<=oldInteger)continue;
       System.out.println(String.join("\t",relative,method.getKey(),target.toString(),candidate.toString(),
        Long.toString(oldCount),Long.toString(newCount),Long.toString(oldInteger),Long.toString(newInteger)));
      }
     }
    }
   }
  }
  System.out.println("CHECKED\t"+checked);
 }
}
'''


def audit(original_jar, classes_directory, java_home):
    with tempfile.TemporaryDirectory(prefix='float-dispatch-audit-') as temporary:
        directory = Path(temporary)
        source = directory/'AuditFloatDispatch.java'
        source.write_text(JAVA_SOURCE)
        subprocess.run([str(java_home/'bin/javac'), EXPORT, str(source)], check=True,
                       capture_output=True, text=True)
        result = subprocess.run([str(java_home/'bin/java'), EXPORT, '-cp', str(directory),
                                 'AuditFloatDispatch', str(original_jar), str(classes_directory)],
                                check=True, capture_output=True, text=True)
    findings = []
    checked = 0
    for line in result.stdout.splitlines():
        parts = line.split('\t')
        if parts[0] == 'CHECKED':
            checked = int(parts[1])
            continue
        owner, method, before, after, old_float, new_float, old_int, new_int = parts
        findings.append({'class': owner, 'method': method, 'original_target': before,
                         'current_integer_target': after,
                         'original_float_count': int(old_float), 'current_float_count': int(new_float),
                         'original_integer_count': int(old_int), 'current_integer_count': int(new_int)})
    return {'checked_classes': checked, 'findings': findings}


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument('--original-jar', type=Path, default=Path(os.environ.get(
        'GREENFARM_ORIGINAL_JAR', '/workspace/tooling/recovery-bytecode/greenfarm-original.jar')))
    parser.add_argument('--classes-directory', type=Path, default=ROOT/
                        'core-source/build/intermediates/javac/debug/compileDebugJavaWithJavac/classes')
    parser.add_argument('--java-home', type=Path, default=Path(os.environ.get(
        'JAVA_HOME', '/workspace/tooling/jdk-21.0.9+10')))
    parser.add_argument('--json-output', type=Path)
    args = parser.parse_args()
    report = audit(args.original_jar, args.classes_directory, args.java_home)
    if args.json_output:
        args.json_output.write_text(json.dumps(report, indent=2)+'\n')
    for finding in report['findings']:
        print(f"{finding['class']} :: {finding['method']} :: {finding['original_target']} "
              f"[{finding['original_float_count']} -> {finding['current_float_count']}] => "
              f"{finding['current_integer_target']} "
              f"[{finding['original_integer_count']} -> {finding['current_integer_count']}]")
    print(f"Checked {report['checked_classes']} classes; {len(report['findings'])} possible float-dispatch losses")


if __name__ == '__main__':
    main()
