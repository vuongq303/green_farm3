"""Receive buffering/error and queue/sleep ordering against original bytecode."""

from pathlib import Path
import os
import shutil
import subprocess
import tempfile
import unittest
import zipfile


ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
RUNNER = r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.io.*;import java.util.*;
public class TcpReceiverComparison {
    static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
    static Class<?> load(String path)throws Exception{return Class.forName(P+"by",true,new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()},null));}
    static Class<?> peer(Class<?> c,String name)throws Exception{return Class.forName(P+name,true,c.getClassLoader());}
    static void set(Class<?> c,Object o,String name,Object value)throws Exception{Field f=c.getDeclaredField(name);f.setAccessible(true);f.set(o,value);}
    static Object get(Class<?> c,Object o,String name)throws Exception{Field f=c.getDeclaredField(name);f.setAccessible(true);return f.get(o);}
    static Object make(Class<?> type,InputStream input,Vector queue,boolean packets,boolean closing)throws Exception{
        Class<?> owner=peer(type,"bx");Object connection=owner.getConstructor().newInstance();
        set(owner,connection,"aky",closing);
        Constructor<?> ctor=type.getDeclaredConstructor(owner,InputStream.class,Vector.class,boolean.class,owner);ctor.setAccessible(true);
        return ctor.newInstance(connection,input,queue,packets,connection);
    }
    static String receive(Class<?> type,int mode,int failure,boolean closing,byte[] data,int remaining)throws Exception{
        Class<?> options=peer(type,"w");String[] names={"XT","XU","XV","XW","XX"};
        for(int i=0;i<names.length;i++)set(options,null,names[i],(mode&(1<<i))!=0);
        set(peer(type,"bx"),null,"remaining",remaining);
        List<String> calls=new ArrayList<>();
        set(peer(type,"GLLib"),null,"hook",(java.util.function.Consumer<String>)calls::add);
        InputStream input=new InputStream(){ByteArrayInputStream bytes=new ByteArrayInputStream(data);int count;
            void check(String label)throws IOException{calls.add(label);if(count++==failure)throw new IOException("feed");}
            public int read()throws IOException{check("read");return bytes.read();}
            public int read(byte[] target,int offset,int length)throws IOException{check("bulk:"+offset+":"+length);return bytes.read(target,offset,length);}
            public int available()throws IOException{check("available");return bytes.available();}};
        Object receiver=make(type,input,new Vector(),(mode&32)!=0,closing);
        set(type,receiver,"akK",true);set(type,receiver,"akN",new byte[]{9,8,7});set(type,receiver,"akM",1);
        Method read=type.getDeclaredMethod("ok");read.setAccessible(true);String error="";
        try{read.invoke(receiver);}catch(InvocationTargetException e){error=e.getCause().getClass().getName();}
        StringBuilder result=new StringBuilder(error).append('/').append(calls);
        for(String name:new String[]{"len","akK","akM"})result.append('/').append(get(type,receiver,name));
        for(String name:new String[]{"akL","akN"})result.append('/').append(Base64.getEncoder().encodeToString((byte[])get(type,receiver,name)));
        Object owner=get(type,receiver,"akP");Class<?> ownerType=peer(type,"bx");
        result.append('/').append(get(ownerType,owner,"BT")).append('/').append(get(ownerType,owner,"akz"));
        Exception recorded=(Exception)get(ownerType,owner,"acI");
        result.append('/').append(recorded==null?"null":recorded.getClass().getName()+":"+recorded.getMessage());
        result.append('/').append(get(ownerType,null,"remaining"));return result.toString();
    }
    static String runOne(Class<?> type,boolean packets,boolean debug)throws Exception{
        Class<?> options=peer(type,"w");for(String name:new String[]{"XT","XU","XV","XW"})set(options,null,name,false);
        set(options,null,"XX",debug);List<String> calls=new ArrayList<>();final Object[] receiver={null};
        Vector queue=new Vector(){public synchronized void addElement(Object value){calls.add("queue:"+Thread.holdsLock(this));super.addElement(value);
            try{TcpReceiverComparison.set(type,receiver[0],"akK",false);}catch(Exception e){throw new RuntimeException(e);}}};
        set(peer(type,"GLLib"),null,"hook",(java.util.function.Consumer<String>)(text)->calls.add(text+":queueLocked="+Thread.holdsLock(queue)));
        byte[] data=packets?new byte[]{0,3,65,66,67}:new byte[]{65,66,67};
        receiver[0]=make(type,new ByteArrayInputStream(data),queue,packets,false);set(type,receiver[0],"akK",true);
        Method run=type.getDeclaredMethod("run");run.setAccessible(true);Thread.currentThread().interrupt();
        try{run.invoke(receiver[0]);}finally{if(Thread.interrupted())throw new AssertionError("Original interrupt handler was bypassed");}
        return calls+"/"+Base64.getEncoder().encodeToString((byte[])queue.get(0))+"/"+Thread.holdsLock(queue);
    }
    public static void main(String[] args)throws Exception{
        Class<?> original=load(args[0]),restored=load(args[1]);int checks=0;
        byte[][] inputs={new byte[0],new byte[]{0},new byte[]{3,4,5},"5|abcde".getBytes("UTF-8"),"{\"x\":1}".getBytes("UTF-8")};
        for(int mode=0;mode<64;mode++)for(int failure:new int[]{-1,0,1,2})for(boolean closing:new boolean[]{false,true}){
            byte[] input=inputs[(mode+failure+1)%inputs.length];int remaining=(mode&1)==0?-1:5000;
            String before=receive(original,mode,failure,closing,input,remaining),after=receive(restored,mode,failure,closing,input,remaining);
            if(!before.equals(after))throw new AssertionError("receive mode="+mode+" failure="+failure+" closing="+closing+"\noriginal="+before+"\nrestored="+after);checks++;
        }
        for(boolean packets:new boolean[]{false,true})for(boolean debug:new boolean[]{false,true}){
            String before=runOne(original,packets,debug),after=runOne(restored,packets,debug);
            if(!before.equals(after))throw new AssertionError("run packets="+packets+" debug="+debug+"\noriginal="+before+"\nrestored="+after);checks++;
        }
        System.out.println("PASS: "+checks+" original/recovered TCP receive and queue/interrupt outcomes match");
    }
}
'''


class TcpReceiverTest(unittest.TestCase):
    def test_buffering_errors_queue_locks_and_original_interrupt_handler(self):
        jar=Path(os.environ.get('GREENFARM_ORIGINAL_JAR',ROOT/'.local/bytecode-tools/greenfarm-original.jar'))
        home=Path(os.environ.get('JAVA_HOME','/workspace/tooling/jdk-21.0.9+10'));java,javac=home/'bin/java',home/'bin/javac'
        if not jar.exists() or not java.is_file() or not javac.is_file():self.skipTest('Requires verified original dex2jar artifact and JDK')
        with tempfile.TemporaryDirectory(prefix='by-tcp-differential-')as temp:
            temporary=Path(temp);source=temporary/'source'/PACKAGE;source.mkdir(parents=True)
            shutil.copyfile(ROOT/'app/src/main/java'/PACKAGE/'by.java',source/'by.java')
            prefix='package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
            (source/'bx.java').write_text(prefix+'''public class bx {public boolean aky,BT;public String akz;public Exception acI;
                public static int remaining=-1;public static int nO(){return remaining;}public static int eP(int i){return remaining-=i;}public static int eQ(int i){return remaining=i;}}''')
            (source/'w.java').write_text(prefix+'public class w {public static boolean XT,XU,XV,XW,XX;}')
            (source/'GLLib.java').write_text(prefix+'''public class GLLib {public static java.util.function.Consumer<String> hook;
                public static void b(String value,String tag,int level){hook.accept(value);}public static void d(Exception e){hook.accept("exception:"+e.getMessage());}}''')
            restored=temporary/'restored';restored.mkdir()
            result=subprocess.run([str(javac),'-source','8','-target','8','-d',str(restored),*map(str,source.glob('*.java'))],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original=temporary/'original';shutil.copytree(restored,original)
            with zipfile.ZipFile(jar)as archive:(original/PACKAGE/'by.class').write_bytes(archive.read(PACKAGE+'/by.class'))
            runner=temporary/'TcpReceiverComparison.java';runner.write_text(RUNNER)
            result=subprocess.run([str(javac),str(runner)],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            result=subprocess.run([str(java),'-cp',str(temporary),'TcpReceiverComparison',str(original),str(restored)],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr);self.assertIn('PASS: 516',result.stdout)


if __name__=='__main__':unittest.main()
