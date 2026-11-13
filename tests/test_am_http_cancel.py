"""Check HTTP cancellation handlers/cleanup against original method bytecode."""

from pathlib import Path
import os
import shutil
import subprocess
import tempfile
import unittest
import zipfile

import test_gllib_recovered_algorithms as fixtures
from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
import sys
sys.path.insert(0, str(ROOT / "tools"))
from game_source_context import legacy_core_source, current_source_path

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
RUNNER = r'''
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.io.*;
import java.util.*;
import sun.misc.Unsafe;
public class HttpCancelComparison {
    static final String P="com.gameloft.android.ANMP.GloftGF2F.S800x480.";
    static Unsafe unsafe;
    static {try{Field f=Unsafe.class.getDeclaredField("theUnsafe");f.setAccessible(true);unsafe=(Unsafe)f.get(null);}catch(Exception e){throw new RuntimeException(e);}}
    static Class<?> load(String path)throws Exception{
        URLClassLoader loader=new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL()},null);
        return Class.forName(P+"am",true,loader);
    }
    static void set(Class<?> type,Object object,String name,Object value)throws Exception{
        Field f=type.getDeclaredField(name);f.setAccessible(true);f.set(object,value);
    }
    static Object get(Class<?> type,Object object,String name)throws Exception{
        Field f=type.getDeclaredField(name);f.setAccessible(true);return f.get(object);
    }
    static class Connection extends HttpURLConnection{
        final List<String> calls;final int mode;
        Connection(List<String> c,int m)throws Exception{super(new URL("http://example.invalid"));calls=c;mode=m;}
        public void connect(){}public boolean usingProxy(){return false;}
        public void disconnect(){calls.add("disconnect:"+Thread.holdsLock(this));if(mode==3)throw new IllegalStateException("disconnect");if(mode==6)throw new AssertionError("disconnect");}
    }
    static String run(Class<?> type,int flags,int mode)throws Exception{
        List<String> calls=new ArrayList<>();Object object=unsafe.allocateInstance(type);
        Connection connection=new Connection(calls,mode);
        InputStream input=new InputStream(){public int read(){return -1;}public void close()throws IOException{
            calls.add("input:"+Thread.holdsLock(connection));if(mode==1)throw new IOException("input");if(mode==4)throw new AssertionError("input");}};
        OutputStream output=new OutputStream(){public void write(int b){}public void close()throws IOException{
            calls.add("output:"+Thread.holdsLock(this));if(mode==2)throw new IOException("output");if(mode==5)throw new AssertionError("output");}};
        Class<?> options=Class.forName(P+"w",true,type.getClassLoader());
        set(options,null,"XR",(flags&2)!=0);set(options,null,"XZ",(flags&4)!=0);
        Class<?> logger=Class.forName(P+"GLLib",true,type.getClassLoader());
        set(logger,null,"hook",(Runnable)()->calls.add("log:"+Thread.holdsLock(object)));
        set(type,object,"BS",(flags&1)!=0);set(type,object,"BR",true);
        set(type,object,"acD",(flags&8)!=0?connection:null);
        set(type,object,"BK",(flags&16)!=0?input:null);set(type,object,"BL",(flags&32)!=0?output:null);
        set(type,object,"BG",new Thread());set(type,object,"acJ","test");set(type,object,"acI",new Exception("before"));
        Method cancel=type.getDeclaredMethod("cancel");cancel.setAccessible(true);String error="";
        try{cancel.invoke(object);}catch(InvocationTargetException e){error=e.getCause().getClass().getName()+":"+e.getCause().getMessage();}
        StringBuilder result=new StringBuilder(error).append('/').append(calls);
        for(String name:new String[]{"BS","BR"})result.append('/').append(get(type,object,name));
        for(String name:new String[]{"acD","BK","BL","BG"})result.append('/').append(get(type,object,name)==null);
        Exception last=(Exception)get(type,object,"acI");
        result.append('/').append(last.getClass().getName()).append(':').append(last.getMessage());
        result.append('/').append(Thread.holdsLock(object)).append('/').append(Thread.holdsLock(connection)).append('/').append(Thread.holdsLock(output));
        return result.toString();
    }
    public static void main(String[] args)throws Exception{
        Class<?> original=load(args[0]),restored=load(args[1]);
        for(int flags=0;flags<64;flags++)for(int mode=0;mode<7;mode++){
            String before=run(original,flags,mode),after=run(restored,flags,mode);
            if(!before.equals(after))throw new AssertionError("flags="+flags+" mode="+mode+"\noriginal="+before+"\nrestored="+after);
        }
        System.out.println("PASS: 448 original/recovered HTTP cancellation traces, fields and exception outcomes match");
    }
}
'''


class HttpCancelTest(unittest.TestCase):
    def test_handlers_locks_cleanup_and_state_match_original_bytecode(self):
        jar = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        java_home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-21.0.9+10'))
        java, javac = java_home / 'bin/java', java_home / 'bin/javac'
        if not jar.exists() or not java.is_file() or not javac.is_file():
            self.skipTest('Requires verified original dex2jar artifact and JDK')
        text = legacy_core_source('am')
        with tempfile.TemporaryDirectory(prefix='am-cancel-differential-') as temp:
            temporary = Path(temp); source = temporary / 'source' / PACKAGE; source.mkdir(parents=True)
            prefix = 'package com.gameloft.android.ANMP.GloftGF2F.S800x480;\n'
            fields = 'boolean BS,BR;java.io.InputStream BK;java.io.OutputStream BL;java.net.HttpURLConnection acD;Thread BG;String acJ;Exception acI;'
            methods = extract_method(text,'public synchronized void cancel()')+'\n'+extract_method(text,'private void e(Exception exc)')
            (source/'am.java').write_text(prefix+'public class am {'+fields+'\n'+methods+'\n}')
            (source/'w.java').write_text(prefix+'public class w {public static boolean XR,XZ;}')
            (source/'GLLib.java').write_text(prefix+'public class GLLib {public static Runnable hook;public static void b(String value,String tag,int level){hook.run();}}')
            restored = temporary/'restored';restored.mkdir()
            result=subprocess.run([str(javac),'-source','8','-target','8','-d',str(restored),*map(str,source.glob('*.java'))],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            original=temporary/'original';shutil.copytree(restored,original)
            old_methods,old_fields=fixtures.METHODS,fixtures.FIELDS
            try:
                fixtures.METHODS={('cancel','()V'),('e','(Ljava/lang/Exception;)V')}
                fixtures.FIELDS={'BS','BR','BK','BL','acD','BG','acJ','acI'}
                with zipfile.ZipFile(jar) as archive:fixture=fixtures.method_fixture(archive.read(PACKAGE+'/am.class'))
            finally:fixtures.METHODS,fixtures.FIELDS=old_methods,old_fields
            (original/PACKAGE/'am.class').write_bytes(fixture)
            runner=temporary/'HttpCancelComparison.java';runner.write_text(RUNNER)
            result=subprocess.run([str(javac),str(runner)],capture_output=True,text=True)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            result=subprocess.run([str(java),'-cp',str(temporary),'HttpCancelComparison',str(original),str(restored)],capture_output=True,text=True,timeout=60)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr)
            self.assertIn('PASS: 448',result.stdout)


if __name__=='__main__':unittest.main()
