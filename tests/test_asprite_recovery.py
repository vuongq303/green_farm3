"""Differential ASprite packet/text checks against original APK method bytecode.

Only unrelated Android methods are omitted from fixture classes. Tested method
Code attributes are retained unchanged; no game implementation is stubbed.
"""
from pathlib import Path
import os
import struct
import subprocess
import tempfile
import unittest
import zipfile

from test_gllib_recovered_algorithms import constant_pool

BASE = "com/gameloft/android/ANMP/GloftGF2F/S800x480/"
import sys
sys.path.insert(0, str(Path(__file__).resolve().parents[1] / "tools"))
from game_source_context import legacy_core_source

METHODS = {
    ("<init>", "()V"), ("<clinit>", "()V"), ("bp", "(I)Z"),
    ("a", "(I[BIIIIIIZ)Z"), ("x", "(II)Ljava/lang/Object;"), ("bS", "(I)I"),
    ("a", "(Ljava/lang/String;IIZ)[S"), ("a", "(C)I"), ("b", "(C)I"),
    ("c", "(C)Z"), ("bN", "(I)I"), ("bO", "(I)I"), ("bP", "(I)I"),
    ("bw", "(I)I"), ("bx", "(I)I"), ("hD", "()I"), ("hF", "()I"), ("hG", "()I"),
    ("a", "(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/l;I)V"),
    *{(name, "(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/l;)V") for name in ("b", "d", "e", "f")},
    ("a", "(Lcom/gameloft/android/ANMP/GloftGF2F/S800x480/l;IIII)V"),
}


def fixture(data, selected):
    cp, pos = constant_pool(data)
    header = data[:pos]
    _, owner, _ = struct.unpack_from(">HHH", data, pos)
    obj = next(i for i, v in cp.items() if isinstance(v, tuple) and v[0] == 7 and cp[v[1]] == "java/lang/Object")
    pos += 8 + 2 * struct.unpack_from(">H", data, pos + 6)[0]

    def members(offset):
        result = []
        count = struct.unpack_from(">H", data, offset)[0]
        offset += 2
        for _ in range(count):
            start = offset
            _, name, desc, attrs = struct.unpack_from(">HHHH", data, offset)
            offset += 8
            for _ in range(attrs):
                size = struct.unpack_from(">I", data, offset + 2)[0]
                offset += 6 + size
            result.append((cp[name], cp[desc], data[start:offset]))
        return result, offset

    fields, pos = members(pos)
    fields = [(name, desc, raw) for name, desc, raw in fields
              if not any(prefix in desc for prefix in ("Lcom/", "Landroid/", "Ljavax/"))]
    methods, _ = members(pos)
    actual = {(name, desc) for name, desc, _ in methods}
    if not selected <= actual:
        raise ValueError(f"Missing fixture methods: {selected - actual}")
    methods = [raw for name, desc, raw in methods if (name, desc) in selected
               or name == "propagateChecked"]
    return (header + struct.pack(">HHHH", 0x21, owner, obj, 0)
            + struct.pack(">H", len(fields)) + b"".join(raw for _, _, raw in fields)
            + struct.pack(">H", len(methods)) + b"".join(methods) + b"\0\0")


RUNNER = r'''
import java.lang.reflect.*;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
public class ASpriteComparison {
 static final String PKG="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
 static void set(Class<?> t,Object obj,String n,Object v)throws Exception{Field f=t.getDeclaredField(n);f.setAccessible(true);f.set(obj,v);}
 static Object get(Class<?> t,Object obj,String n)throws Exception{Field f=t.getDeclaredField(n);f.setAccessible(true);return f.get(obj);}
 static Method method(Class<?> t,String n,Class<?>...args)throws Exception{Method m=t.getDeclaredMethod(n,args);m.setAccessible(true);return m;}
 static class Game {
  Class<?> sprite,config; Object instance; Method decode,text;
  Game(String path)throws Exception{
   URLClassLoader loader=new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()},null);
   sprite=Class.forName(PKG+"a",true,loader);config=Class.forName(PKG+"w",true,loader);
   instance=sprite.getConstructor().newInstance();
   decode=method(sprite,"a",int.class,byte[].class,int.class,int.class,int.class,int.class,int.class,int.class,boolean.class);
   text=method(sprite,"a",String.class,int.class,int.class,boolean.class);
   for(String flag:new String[]{"WN","WO","WP","WM","WL","WK","WQ","WT","WR","WS","WU","WV","WW"})set(config,null,flag,true);
   set(config,null,"Xg",false);set(config,null,"XE",false);
   int[] colors=new int[256];for(int i=0;i<256;i++)colors[i]=0xff000000|(i*65793);
   set(sprite,instance,"HC",new int[][][]{new int[][]{colors}});set(sprite,instance,"HE",1);
   set(sprite,instance,"HI",new int[]{0});set(sprite,instance,"HO",new int[]{15});set(sprite,instance,"HP",new int[]{4});
   char[] chars=new char[256];int[] metrics=new int[512];
   for(int i=0;i<256;i++){chars[i]=(char)i;metrics[2*i]=3+i%7;metrics[2*i+1]=11;}
   set(sprite,instance,"IG",chars);set(sprite,instance,"IH",metrics);
   set(sprite,instance,"IO",11);set(sprite,instance,"IR",4);set(sprite,instance,"IQ",1);
  }
  Object run(int format,byte[] bytes,boolean rgba,int stride)throws Exception{
   set(sprite,instance,"HM",(short)format);set(sprite,null,"Gs",new int[2048]);set(sprite,null,"Gu",new byte[2048]);
   try{return decode.invoke(instance,0,bytes.clone(),0,0,0,16,4,stride,rgba);}
   catch(InvocationTargetException e){return e.getTargetException().getClass().getName();}
  }
 }
 public static void main(String[] args)throws Exception{
  Game old=new Game(args[0]),now=new Game(args[1]);Random rng=new Random(44220);int cases=0;
  int[] formats={25840,10225,22258,5632,1024,512,22018,-23198,-24476,-24280,-22976,-24281,-23978};
  for(int format:formats)for(boolean rgba:new boolean[]{true,false}){
   // Alpha formats have no index-only rendering path in the original; exercise RGBA.
   if(!rgba && format<0)continue;
   for(int trial=0;trial<20;trial++)for(int stride:new int[]{0,24}){
    byte[] packed=new byte[1024];if(trial!=0)rng.nextBytes(packed);
    Object a=old.run(format,packed,rgba,stride),b=now.run(format,packed,rgba,stride);
    if(!Objects.equals(a,b))throw new AssertionError("Outcome mismatch "+format+"/"+trial+": "+a+" != "+b);
    if(!Arrays.equals((int[])get(old.sprite,null,"Gs"),(int[])get(now.sprite,null,"Gs"))
       ||!Arrays.equals((byte[])get(old.sprite,null,"Gu"),(byte[])get(now.sprite,null,"Gu")))
      throw new AssertionError("Pixel mismatch "+format+"/"+trial+"/"+rgba+"/"+stride);
    cases++;
   }
  }
  String[] edge={"","a","a b c","a\u0001b","a\u0002b","a\u0003b","\u0001","\u0003","a\n b","ab|cd","a\\^bc","a\\_bc"};
  int textCases=0;
  for(boolean formatted:new boolean[]{false,true}){
   set(old.config,null,"XG",formatted);set(now.config,null,"XG",formatted);
   for(int trial=0;trial<200;trial++){
    String value;
    if(trial<edge.length)value=edge[trial];else{
     StringBuilder v=new StringBuilder();String alphabet="abcdefgh  \n|\u0001\u0002\u0003";
     for(int i=0,n=rng.nextInt(50);i<n;i++)v.append(alphabet.charAt(rng.nextInt(alphabet.length())));value=v.toString();
    }
    for(int width:new int[]{8,20,100}){
     Object a,b;boolean cut=(trial&1)==0;
     try{a=((short[])old.text.invoke(old.instance,value,width,0,cut)).clone();}catch(InvocationTargetException e){a=e.getTargetException().getClass().getName();}
     try{b=((short[])now.text.invoke(now.instance,value,width,0,cut)).clone();}catch(InvocationTargetException e){b=e.getTargetException().getClass().getName();}
     if(a instanceof short[] && b instanceof short[]){if(!Arrays.equals((short[])a,(short[])b))throw new AssertionError("Text mismatch "+trial+"/"+width+"/"+formatted);}
     else if(!Objects.equals(a,b))throw new AssertionError("Text exception mismatch "+trial+": "+a+" != "+b);
     textCases++;
    }
   }
  }
  int ioCases=0;
  for(Game game:new Game[]{old,now}){
   Class<?> streamType=Class.forName(PKG+"l",true,game.sprite.getClassLoader());
   set(game.config,null,"WC",true);set(game.config,null,"WH",true);
   set(game.sprite,game.instance,"HA",4194304);set(game.sprite,game.instance,"Gz",1);
   for(int which=0;which<6;which++){
    IOException marker=new IOException("original sprite stream failure");
    InputStream broken=new InputStream(){public int read()throws IOException{throw marker;}};
    Object stream=streamType.getConstructor(InputStream.class).newInstance(broken);
    Method target;Object[] parameters;
    if(which==0){target=method(game.sprite,"a",streamType,int.class);parameters=new Object[]{stream,0};}
    else if(which<5){target=method(game.sprite,new String[]{"","b","d","e","f"}[which],streamType);parameters=new Object[]{stream};}
    else{target=method(game.sprite,"a",streamType,int.class,int.class,int.class,int.class);parameters=new Object[]{stream,-30712,1,0,0};}
    try{target.invoke(game.instance,parameters);throw new AssertionError("IO failure swallowed in path "+which);}
    catch(InvocationTargetException failure){if(failure.getTargetException()!=marker)throw new AssertionError("IO exception replaced in path "+which,failure.getTargetException());}
    ioCases++;
   }
  }
  System.out.println(cases+" decoder cases across 13 formats and row strides; "+textCases+" text layout cases; "+ioCases+" IO exception checks match original bytecode.");
 }
}
'''


class ASpriteRecoveryTest(unittest.TestCase):
    def test_differential_decoder_and_control_characters(self):
        project = Path(__file__).resolve().parents[1]
        home = Path(os.environ.get("JAVA_HOME", "/workspace/tooling/jdk-21.0.9+10"))
        original = Path(os.environ.get("GREENFARM_ORIGINAL_JAR", "/workspace/tooling/recovery-bytecode/greenfarm-original.jar"))
        android = Path(os.environ.get("ANDROID_HOME", "/workspace/tooling/android-sdk")) / "platforms/android-28/android.jar"
        if not original.exists() or not android.exists() or not (home / "bin/javac").exists():
            self.skipTest("Requires verified original conversion, JDK and Android SDK")
        with tempfile.TemporaryDirectory(prefix="asprite-recovery-") as temp:
            root = Path(temp)
            classes = root / "compiled"
            source = root / "a.java"
            source.write_text(legacy_core_source("a"))
            result = subprocess.run([str(home / "bin/javac"), "-source", "8", "-target", "8", "-proc:none",
                                     "-sourcepath", "", "-cp", f"{android}{os.pathsep}{original}",
                                     "-d", str(classes), str(source)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            with zipfile.ZipFile(original) as z:
                for label, data in (("original",z.read(BASE+"a.class")),("restored",(classes/BASE/"a.class").read_bytes())):
                    output=root/label/BASE;output.mkdir(parents=True)
                    (output/"a.class").write_bytes(fixture(data,METHODS))
                    (output/"w.class").write_bytes(z.read(BASE+"w.class"))
                    (output/"l.class").write_bytes(z.read(BASE+"l.class"))
                    (output/"GLLib.class").write_bytes(fixture(z.read(BASE+"GLLib.class"),{("c","(ZLjava/lang/String;)V")}))
            runner=root/"ASpriteComparison.java";runner.write_text(RUNNER)
            subprocess.run([str(home/"bin/javac"),str(runner)],check=True,capture_output=True,text=True)
            result=subprocess.run([str(home/"bin/java"),"-cp",str(root),"ASpriteComparison",str(root/"original"),str(root/"restored")],
                                  capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn("text layout cases; 12 IO exception checks match original bytecode",result.stdout)
            print(result.stdout.strip())


if __name__ == "__main__": unittest.main()
