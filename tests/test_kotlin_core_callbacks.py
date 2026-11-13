"""Compare real Kotlin small-core implementations with original APK bytecode.

Only boundary peers replace Android/SDK services. The reference callbacks and
algorithms are loaded unchanged from the original converted JAR in isolated
class loaders; the original player database is never opened.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, kotlin_runtime_jars

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
QUALIFIED = PACKAGE.replace('/', '.')
SOURCE = Path(os.environ.get('KOTLIN_CALLBACK_SOURCE_DIR', ROOT / 'app/src/main/kotlin' / PACKAGE))
ORIGINAL = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))

PEERS = {
    'o.java': 'package %PACKAGE%; public abstract class o implements Runnable {}',
    'GLLib.java': '''package %PACKAGE%; public class GLLib {
 public static int Rl=777;public static long Tn,To;public static void be(String value){ci.trace+="log:"+value+";";}public static void c(boolean condition,String message){if(!condition)throw new ArithmeticException(message);}
}''',
    'ci.java': '''package %PACKAGE%; import java.util.*; public class ci {
 public static boolean aWS,aPU,bbi,bbj,bbk,bbl,bbm,bbr; public static String aWZ,baQ;
 public static int[]apC={52},aWD={3,4};public static int apB,aPZ=3,aVW=2;
 public static String[]aVX={"q1","q2"};public static long[]aWE={11,12},aWF={Long.MAX_VALUE-1,-80000};public static boolean[]aWA={true,true};
 public static String trace="";public static Thread bbI;public static Vector bbM;
 public static com.gameloft.android2d.d.b baf=new com.gameloft.android2d.d.b();
 public static void dr(String v){trace+="dr:"+v+";";} public static void dd(String v){trace+="dd:"+v+";";}
 public static void dn(String v){trace+="dn:"+v+";";}public static boolean M(String id,String body){trace+="M:"+id+":"+body+";";return false;}
 public static void lS(int n){trace+="lS:"+n+";";}
 public static void tracker(int n,String[]v){trace+="tracker:"+n+":"+Arrays.toString(v)+";";bbI=null;}
 public static void reset(){aWS=true;aPU=false;aWZ="old";aPZ=3;apC[0]=52;baQ="AU";bbi=bbj=bbk=bbl=bbm=bbr=false;trace="";aVX=new String[]{"q1","q2"};aWD=new int[]{3,4};aWE=new long[]{11,12};aWF=new long[]{Long.MAX_VALUE-1,-80000};aWA=new boolean[]{true,true};}
 public static String snapshot(){return trace+"|"+aWS+"|"+aPU+"|"+aWZ+"|"+Arrays.toString(aWE)+"|"+Arrays.toString(aWA)+"|"+bbi+bbj+bbk+bbl+bbm+bbr;}
 public static class ThrowOnceVector extends Vector {boolean thrown;public synchronized Object firstElement(){if(!thrown){thrown=true;throw new IllegalStateException("transient fixture event failure");}return super.firstElement();}}
 public static void queue(){bbI=new Thread();bbM=new ThrowOnceVector();}
}''',
}
EXTRA_PEERS = {
 'com/gameloft/android2d/d/b.java': '''package com.gameloft.android2d.d;public class b {public void b(int n,String[]v){%PACKAGE%.ci.tracker(n,v);}}''',
 'com/gameloft/a/b/l.java': 'package com.gameloft.a.b;public class l {}',
 'com/gameloft/a/b/m.java': 'package com.gameloft.a.b;public class m {public String bxA;public int bxy;}',
}

RUNNER = r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.util.*;
public class KotlinCoreComparison {
 static String p="%PACKAGE%.";static int checks;
 static Class<?> type(ClassLoader l,String n)throws Exception{return Class.forName(p+n,true,l);}
 static Method method(Class<?> c,String n,Class<?>...args)throws Exception{Method m=c.getDeclaredMethod(n,args);m.setAccessible(true);return m;}
 static Constructor<?> constructor(Class<?> c,Class<?>...args)throws Exception{Constructor<?> v=c.getDeclaredConstructor(args);v.setAccessible(true);return v;}
 static Object call(Class<?> c,Object target,String n,Class<?>[]types,Object...args)throws Exception{return method(c,n,types).invoke(target,args);}
 static Object get(Class<?> c,Object target,String n)throws Exception{Field f=c.getDeclaredField(n);f.setAccessible(true);return f.get(target);}
 static void set(Class<?> c,Object target,String n,Object v)throws Exception{Field f=c.getDeclaredField(n);f.setAccessible(true);f.set(target,v);}
 static void same(Object a,Object b,String context){checks++;if(!Objects.deepEquals(a,b))throw new AssertionError(context+": "+String.valueOf(a)+" != "+String.valueOf(b));}
 static String failure(Executable e,Object target,Object...args)throws Exception{try{if(e instanceof Constructor)((Constructor<?>)e).newInstance(args);else((Method)e).invoke(target,args);return "none";}catch(InvocationTargetException x){return x.getCause().getClass().getName();}}
 static URLClassLoader load(String fixtures,String replacement,String original,String stdlib)throws Exception{
  ArrayList<URL>urls=new ArrayList<>();urls.add(Paths.get(fixtures).toUri().toURL());if(!replacement.equals("-"))urls.add(Paths.get(replacement).toUri().toURL());urls.add(Paths.get(original).toUri().toURL());urls.add(Paths.get(stdlib).toUri().toURL());return new URLClassLoader(urls.toArray(new URL[0]),null);
 }
 static void algorithms(ClassLoader a,ClassLoader b)throws Exception{
  Random random=new Random(0x474633L);Class<?> oa=type(a,"al"),na=type(b,"al"),ob=type(a,"bo"),nb=type(b,"bo");
  long[]edges={Long.MIN_VALUE,Long.MAX_VALUE,-1,0,1,0xffff,0x10000,0xffffffffL,0x100000000L};
  for(long value:edges)same(call(oa,null,"q",new Class[]{long.class},value),call(na,null,"q",new Class[]{long.class},value),"hash edge");
  for(int i=0;i<10000;i++){long v=random.nextLong();same(call(oa,null,"q",new Class[]{long.class},v),call(na,null,"q",new Class[]{long.class},v),"hash signed overflow");int x=random.nextInt(),y=random.nextInt();same(call(ob,null,"aq",new Class[]{int.class,int.class},x,y),call(nb,null,"aq",new Class[]{int.class,int.class},x,y),"multiplication overflow");}
  for(int seed:new int[]{2016,0,-1,Integer.MIN_VALUE,Integer.MAX_VALUE}){set(ob,null,"ajP",seed);set(nb,null,"ajP",seed);for(int i=0;i<1000;i++)same(call(ob,null,"next",new Class[0]),call(nb,null,"next",new Class[0]),"seeded generator");}
  for(String name:new String[]{"ce","cf","bi","bk"}){
   Class<?> old=type(a,name),now=type(b,name);Object x=constructor(old).newInstance(),y=constructor(now).newInstance();boolean wide=name.equals("cf")||name.equals("bk");String setter=name.equals("bk")?"r":name.equals("bi")?"setValue":"set";String getter=name.equals("bi")||name.equals("bk")?"getValue":"get";
   for(int i=0;i<1000;i++){Object value;if(wide)value=Long.valueOf(random.nextLong());else value=Integer.valueOf(random.nextInt());call(old,x,setter,new Class[]{wide?long.class:int.class},value);call(now,y,setter,new Class[]{wide?long.class:int.class},value);same(call(old,x,getter,new Class[0]),call(now,y,getter,new Class[0]),name+" roundtrip");}
  }
  for(String name:new String[]{"bi","bk"}){
   Class<?> old=type(a,name),now=type(b,name);boolean wide=name.equals("bk");Object x=constructor(old).newInstance(),y=constructor(now).newInstance();String storage=wide?"cf":"ce";
   for(Object[]pair:new Object[][]{{old,x},{now,y}}){Class<?>c=(Class<?>)pair[0];Object entity=pair[1];Field selected=null;for(Field f:c.getDeclaredFields())if(f.getType().getName().equals(p+storage)){selected=f;break;}selected.setAccessible(true);Object v=selected.get(entity);Object corruption;if(wide)corruption=Long.valueOf(123456789L);else corruption=Integer.valueOf(123456789);call(v.getClass(),v,"set",new Class[]{wide?long.class:int.class},corruption);}
   same(call(old,x,"getValue",new Class[0]),call(now,y,"getValue",new Class[0]),"corrupted guarded value");
  }
  Class<?> od=type(a,"bd"),nd=type(b,"bd");Object x=constructor(od,int.class).newInstance(1024),y=constructor(nd,int.class).newInstance(1024);ArrayList<byte[]>keys=new ArrayList<>();
  for(int i=0;i<200;i++){byte[]bytes=new byte[3];random.nextBytes(bytes);keys.add(bytes);int prefix=((bytes[0]&255)<<8)|(bytes[1]&255);same(call(od,x,"a",new Class[]{int.class,byte.class,int.class},prefix,bytes[2],i),call(nd,y,"a",new Class[]{int.class,byte.class,int.class},prefix,bytes[2],i),"linked-hash insert");}
  for(byte[]bytes:keys)same(call(od,x,"f",new Class[]{byte[].class,int.class,int.class},bytes,0,3),call(nd,y,"f",new Class[]{byte[].class,int.class,int.class},bytes,0,3),"linked-hash find");
  same(call(od,x,"f",new Class[]{byte[].class,int.class,int.class},null,0,2),call(nd,y,"f",new Class[]{byte[].class,int.class,int.class},null,0,2),"null input short-circuit");
  same(failure(method(od,"f",byte[].class,int.class,int.class),x,null,0,3),failure(method(nd,"f",byte[].class,int.class,int.class),y,null,0,3),"null input dereference");
  same(failure(constructor(od,int.class),null,36864),failure(constructor(nd,int.class),null,36864),"size guard");call(od,x,"reset",new Class[0]);call(nd,y,"reset",new Class[0]);
  for(byte[]bytes:keys)same(call(od,x,"f",new Class[]{byte[].class,int.class,int.class},bytes,0,3),call(nd,y,"f",new Class[]{byte[].class,int.class,int.class},bytes,0,3),"hash reset");
  for(String name:new String[]{"bb","cc","cd","q","r"}){
   Class<?> old=type(a,name),now=type(b,name);Object v=constructor(old).newInstance(),w=constructor(now).newInstance();for(Field field:old.getDeclaredFields())if(!Modifier.isPrivate(field.getModifiers()))same(get(old,v,field.getName()),get(now,w,field.getName()),name+" defaults/array");
  }
  Class<?> op=type(a,"bp"),np=type(b,"bp");byte[]before={1,-2,3},after=before.clone();Object po=constructor(op,byte[].class).newInstance((Object)before),pn=constructor(np,byte[].class).newInstance((Object)after);before[0]=9;after[0]=9;same(get(op,po,"data"),get(np,pn,"data"),"save byte copy");same(((byte[])get(np,pn,"data"))[0],(byte)1,"copy is detached");same(failure(constructor(op,byte[].class),null,(Object)null),failure(constructor(np,byte[].class),null,(Object)null),"null save source");
  Class<?> ou=type(a,"u"),nu=type(b,"u");Class<?>[]ut={int.class,String.class,byte[].class,String.class,int.class,String.class,Exception.class};byte[]bytes="farm_é".getBytes();Object uo=constructor(ou,ut).newInstance(201,null,bytes,"tag",-3,null,null),un=constructor(nu,ut).newInstance(201,null,bytes.clone(),"tag",-3,null,null);
  for(String getter:new String[]{"jw","jx","jy","jz","jA","jB"})same(call(ou,uo,getter,new Class[0]),call(nu,un,getter,new Class[0]),"response getter "+getter);
 }
 static void requestMonitor(ClassLoader loader)throws Exception{
  Class<?>request=type(loader,"aq");Object entity=constructor(request).newInstance();Method idle=method(request,"mK");java.util.concurrent.CountDownLatch entered=new java.util.concurrent.CountDownLatch(1),done=new java.util.concurrent.CountDownLatch(1);Throwable[]failure={null};
  Thread worker=new Thread(()->{entered.countDown();try{idle.invoke(entity);}catch(Throwable x){failure[0]=x;}finally{done.countDown();}});
  synchronized(entity){worker.start();entered.await();long deadline=System.nanoTime()+2000000000L;while(worker.getState()!=Thread.State.BLOCKED&&done.getCount()!=0&&System.nanoTime()<deadline)Thread.yield();same(worker.getState(),Thread.State.BLOCKED,"request idle check holds original instance monitor");same(done.getCount(),1L,"request remains blocked until monitor released");}
  worker.join(3000);same(done.getCount(),0L,"request proceeds after monitor release");same(failure[0],null,"request monitor invocation");
 }
 static void callbacks(ClassLoader a,ClassLoader b)throws Exception{
  Class<?> oc=type(a,"ci"),nc=type(b,"ci");String[]bodies={null,"", "-2","-3","ok","{bad json}"};int[]codes={200,201,404,409,500,-1};
  for(String name:new String[]{"co","cu","cv","cw","cx"})for(int code:codes)for(String body:bodies)for(int variant=0;variant<4;variant++){
   call(oc,null,"reset",new Class[0]);call(nc,null,"reset",new Class[0]);Object x,y;Class<?>old=type(a,name),now=type(b,name);
   if(name.equals("cv")){x=constructor(old,int.class).newInstance(variant%2==0?3:9);y=constructor(now,int.class).newInstance(variant%2==0?3:9);}
   else if(name.equals("cw")){x=constructor(old,String.class).newInstance("q1");y=constructor(now,String.class).newInstance("q1");}
   else if(name.equals("cx")){x=constructor(old,String.class,int.class).newInstance(variant==3?null:"q1",variant%2==0?3:8);y=constructor(now,String.class,int.class).newInstance(variant==3?null:"q1",variant%2==0?3:8);}
   else{x=constructor(old).newInstance();y=constructor(now).newInstance();}
   if(variant==2){set(oc,null,"apC",new int[]{18});set(nc,null,"apC",new int[]{18});}
   if(name.equals("cx")&&variant==3){set(oc,null,"aVX",new String[]{null,"q2"});set(nc,null,"aVX",new String[]{null,"q2"});}
   same(failure(method(old,"D",int.class,String.class),x,code,body),failure(method(now,"D",int.class,String.class),y,code,body),"callback exception "+name);
   same(call(oc,null,"snapshot",new Class[0]),call(nc,null,"snapshot",new Class[0]),"callback state "+name);
  }
  for(String name:new String[]{"cn","cr"})for(String policy:new String[]{null,"{}","COPPA","ITALY","OFT","ordinary"})for(String country:new String[]{"AU","NZ","US","au"}){
   call(oc,null,"reset",new Class[0]);call(nc,null,"reset",new Class[0]);set(oc,null,"baQ",country);set(nc,null,"baQ",country);
   Class<?>old=type(a,name),now=type(b,name),om=Class.forName("com.gameloft.a.b.m",true,a),nm=Class.forName("com.gameloft.a.b.m",true,b),ol=Class.forName("com.gameloft.a.b.l",true,a),nl=Class.forName("com.gameloft.a.b.l",true,b);
   Object od=constructor(om).newInstance(),nd=constructor(nm).newInstance();set(om,od,"bxA",policy);set(nm,nd,"bxA",policy);Object x=name.equals("cn")?constructor(old).newInstance():constructor(old,oc).newInstance((Object)null),y=name.equals("cn")?constructor(now).newInstance():constructor(now,nc).newInstance((Object)null);
   same(failure(method(old,"a",ol,om),x,null,od),failure(method(now,"a",nl,nm),y,null,nd),"privacy exception");same(call(oc,null,"snapshot",new Class[0]),call(nc,null,"snapshot",new Class[0]),"privacy flags");
  }
  for(ClassLoader l:new ClassLoader[]{a,b}){
   Class<?>c=type(l,"ci");call(c,null,"reset",new Class[0]);call(c,null,"queue",new Class[0]);Object event=constructor(type(l,"dd"),int.class,String[].class).newInstance(17,new String[]{"x",null});((Vector)get(c,null,"bbM")).addElement(event);Object worker=constructor(type(l,"cl")).newInstance();call(type(l,"cl"),worker,"run",new Class[0]);
  }
  same(call(oc,null,"snapshot",new Class[0]),call(nc,null,"snapshot",new Class[0]),"event worker retries before dequeue");same(((Vector)get(oc,null,"bbM")).size(),((Vector)get(nc,null,"bbM")).size(),"event queue drained exactly once");
 }
 static void constants(String fixtures,String replacement,String original,String stdlib)throws Exception{
  for(int variant=0;variant<3;variant++)try(URLClassLoader a=load(fixtures,"-",original,stdlib);URLClassLoader b=load(fixtures,replacement,original,stdlib)){
   for(ClassLoader l:new ClassLoader[]{a,b}){Class<?>w=type(l,"w");set(w,null,"screenWidth",800+variant*177);set(w,null,"screenHeight",480+variant*101);set(w,null,"VP",8+variant*8);set(w,null,"VQ",8+variant*2);set(w,null,"VX",variant);set(w,null,"Yi",7+variant);}
   for(String name:new String[]{"y","bh","ag","ax","av","af"}){Class<?>old=type(a,name),now=type(b,name);for(Field f:old.getDeclaredFields())if(!Modifier.isPrivate(f.getModifiers())&&Modifier.isStatic(f.getModifiers()))same(get(old,null,f.getName()),get(now,null,f.getName()),"static field "+name+"."+f.getName());}
   Class<?>old=type(a,"af"),now=type(b,"af");Random r=new Random(99);for(int i=0;i<1000;i++){int numerator=r.nextInt(),denominator=r.nextInt();if(denominator==0)denominator=1;same(call(old,null,"af",new Class[]{int.class,int.class},numerator,denominator),call(now,null,"af",new Class[]{int.class,int.class},numerator,denominator),"fixedpoint divide/overflow");}
  }
 }
 public static void main(String[]args)throws Exception{
  try(URLClassLoader a=load(args[0],"-",args[2],args[3]);URLClassLoader b=load(args[0],args[1],args[2],args[3])){algorithms(a,b);requestMonitor(a);requestMonitor(b);callbacks(a,b);}constants(args[0],args[1],args[2],args[3]);System.out.println("PASS "+checks+" original-bytecode comparisons: hash/RNG, guarded values, linked hash, arrays/nulls, callbacks/privacy/event queue, constants and fixedpoint");
 }
}
'''


class KotlinCoreCallbacksTest(unittest.TestCase):
    def test_original_bytecode_behavior(self):
        self.assertTrue(ORIGINAL.is_file(), 'Run prepareCoreDependencies to obtain the verified original JAR')
        selected = ('aq al bo ce cf bi bk bd bb bp cc cd q r y u bh ag ax av af ar co cu cv cw cx cn cr cl dd').split()
        with tempfile.TemporaryDirectory(prefix='kotlin-core-comparison-') as temporary:
            root = Path(temporary)
            peers, output, runner = root / 'peers', root / 'kotlin', root / 'runner'
            java_sources = []
            for name, source in PEERS.items():
                path = peers / PACKAGE / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(source.replace('%PACKAGE%', QUALIFIED))
                java_sources.append(path)
            for name, source in EXTRA_PEERS.items():
                path = peers / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(source.replace('%PACKAGE%', QUALIFIED))
                java_sources.append(path)
            runner.mkdir()
            source = runner / 'KotlinCoreComparison.java'
            source.write_text(RUNNER.replace('%PACKAGE%', QUALIFIED))
            built = subprocess.run([str(jdk_tool('javac')), '-cp', str(ORIGINAL), '-d', str(peers), *map(str, java_sources)], capture_output=True, text=True)
            self.assertEqual(built.returncode, 0, built.stderr)
            compile_mixed([SOURCE / (name + '.kt') for name in selected], [], output, classpath=[peers, ORIGINAL, Path(os.environ.get('ANDROID_HOME', '/workspace/tooling/android-sdk')) / 'platforms/android-28/android.jar'])
            built = subprocess.run([str(jdk_tool('javac')), '-d', str(runner), str(source)], capture_output=True, text=True)
            self.assertEqual(built.returncode, 0, built.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', str(runner), 'KotlinCoreComparison', str(peers), str(output), str(ORIGINAL), str(kotlin_runtime_jars()[0])], capture_output=True, text=True, timeout=90)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('original-bytecode comparisons', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
