"""Prevent the observed render-thread spin by checking original slot selection."""

from pathlib import Path
import os
import shutil
import subprocess
import tempfile
import unittest
import zipfile
import sys

import test_gllib_recovered_algorithms as fixtures
from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from game_source_context import qualified_package, kotlin_source_directory
from kotlin_test_tools import compile_mixed, kotlin_runtime_jars
from test_kotlin_core_callbacks import identity_normalized_reference
PACKAGE = qualified_package().replace('.', '/')
RUNNER = r'''
import java.lang.reflect.*;import java.net.*;import java.nio.file.*;import java.util.*;
public class AvailableSlotComparison {
    static final String P="%PACKAGE%."; static URL reference,stdlib;
    static Class<?> load(String path)throws Exception{return Class.forName(P+"as",true,new URLClassLoader(new URL[]{Paths.get(path).toUri().toURL(),reference,stdlib},null));}
    static String run(Class<?> type,int[] states)throws Exception{
        Class<?> slot=Class.forName(P+"aq",true,type.getClassLoader());
        List<String> calls=(List<String>)slot.getField("calls").get(null);calls.clear();slot.getField("lastFailure").set(null,null);
        Object array=null;
        if(states!=null){array=Array.newInstance(slot,states.length);
            for(int i=0;i<states.length;i++)if(states[i]!=2)Array.set(array,i,slot.getConstructor(int.class,int.class).newInstance(i,states[i]));}
        Field field;try{field=type.getDeclaredField("adE");}catch(NoSuchFieldException renamed){field=type.getDeclaredField("requestSlots");}field.setAccessible(true);field.set(null,array);
        Method find;Object receiver=null;try{find=type.getDeclaredMethod("jf");}catch(NoSuchMethodException kotlin){receiver=type.getField("Companion").get(null);find=receiver.getClass().getDeclaredMethod("availableSlotIndex");}find.setAccessible(true);String result;
        try{result="slot="+find.invoke(receiver);}catch(InvocationTargetException exception){
            Throwable failure=exception.getCause(),recorded=(Throwable)slot.getField("lastFailure").get(null);
            result="failure="+failure.getClass().getName()+":same="+(recorded==null||recorded==failure);}
        return result+"/"+calls+"/released="+!Thread.holdsLock(type);
    }
    public static void main(String[] args)throws Exception{
        reference=Paths.get(args[2]).toUri().toURL();stdlib=Paths.get(args[3]).toUri().toURL();Class<?> original=load(args[0]),restored=load(args[1]);int count=0;
        for(int a=0;a<5;a++)for(int b=0;b<5;b++)for(int c=0;c<5;c++){
            int[] states={a,b,c};String before=run(original,states),after=run(restored,states);
            if(!before.equals(after))throw new AssertionError(Arrays.toString(states)+"\noriginal="+before+"\nrestored="+after);count++;}
        for(int[] states:new int[][]{null,new int[0],new int[]{0},new int[]{0,0},new int[]{1}}){
            String before=run(original,states),after=run(restored,states);
            if(!before.equals(after))throw new AssertionError(Arrays.toString(states)+"\noriginal="+before+"\nrestored="+after);count++;}
        System.out.println("PASS: "+count+" original/recovered slot, short-circuit, failure and monitor-release outcomes match");
    }
}
'''


class AvailableSlotTest(unittest.TestCase):
    def test_first_ready_slot_failure_short_circuit_and_monitor_release(self):
        jar = Path(os.environ.get('GREENFARM_ORIGINAL_JAR',ROOT/'.local/bytecode-tools/greenfarm-original.jar'))
        home = Path(os.environ.get('JAVA_HOME','/workspace/tooling/jdk-21.0.9+10'));java,javac=home/'bin/java',home/'bin/javac'
        if not jar.exists() or not java.is_file() or not javac.is_file():self.skipTest('Requires verified original dex2jar artifact and JDK')
        with tempfile.TemporaryDirectory(prefix='as-slot-differential-')as temp:
            temporary=Path(temp);source=temporary/'source'/PACKAGE;source.mkdir(parents=True)
            reference=identity_normalized_reference(temporary)
            prefix='package '+qualified_package()+';\n'
            peer=source/'aq.java'
            peer.write_text(prefix+'''public class aq {
                public static java.util.List<String> calls=new java.util.ArrayList<>();public static Throwable lastFailure;
                private int id,state;public aq(int id,int state){this.id=id;this.state=state;}
                public aq(){this(0,0);}public aq(String url,String body,int method,ar callback,int timeout){this(0,0);}
                public void a(aq request,am worker){throw new AssertionError("unexpected dispatch in slot probe");}
                public void dV(int elapsed){throw new AssertionError("unexpected tick in slot probe");}
                public boolean mK(){calls.add(id+":"+Thread.holdsLock(as.class));
                    if(state==3){RuntimeException failure=new IllegalStateException("slot-"+id);lastFailure=failure;throw failure;}
                    if(state==4){Error failure=new AssertionError("slot-"+id);lastFailure=failure;throw failure;}
                    return state==1;}}''')
            restored=temporary/'restored';restored.mkdir()
            compile_mixed([kotlin_source_directory()/'as.kt'],[peer],restored,classpath=[reference])
            original=temporary/'original';shutil.copytree(restored,original)
            old_methods,old_fields=fixtures.METHODS,fixtures.FIELDS
            try:
                fixtures.METHODS={('jf','()I')};fixtures.FIELDS={'adE'}
                with zipfile.ZipFile(reference)as archive:fixture=fixtures.method_fixture(archive.read(PACKAGE+'/as.class'))
            finally:fixtures.METHODS,fixtures.FIELDS=old_methods,old_fields
            (original/PACKAGE/'as.class').write_bytes(fixture)
            runner=temporary/'AvailableSlotComparison.java';runner.write_text(RUNNER.replace('%PACKAGE%',qualified_package()))
            subprocess.run([str(javac),str(runner)],check=True,capture_output=True,text=True)
            result=subprocess.run([str(java),'-cp',str(temporary),'AvailableSlotComparison',str(original),str(restored),str(reference),str(kotlin_runtime_jars()[0])],capture_output=True,text=True,timeout=30)
            self.assertEqual(result.returncode,0,result.stdout+result.stderr);self.assertIn('PASS: 130',result.stdout)


if __name__=='__main__':unittest.main()
