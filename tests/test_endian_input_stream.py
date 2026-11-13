"""Differential tests against the actual original DEX-converted asset reader."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

ORACLE = r'''
import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.util.*;
public final class EndianReaderOracle {
 static int checks;
 static void equal(Object a,Object b){checks++;if(!Objects.equals(a,b))throw new AssertionError(a+" != "+b);}
 static Object invoke(Class<?> type,Object reader,String method) throws Exception {
  try {
   Object value=type.getMethod(method).invoke(reader);
   if(value instanceof Float)return Float.floatToRawIntBits((Float)value);
   if(value instanceof Double)return Double.doubleToRawLongBits((Double)value);
   return value;
  }catch(InvocationTargetException ex){return ex.getCause().getClass().getName();}
 }
 static Object create(Class<?> type,byte[] bytes,int order)throws Exception{
  Object reader=type.getConstructor(InputStream.class).newInstance(new ByteArrayInputStream(bytes));
  type.getMethod("cf",int.class).invoke(reader,order);
  equal(type.getField("order").get(reader),order);return reader;
 }
 static class Tracking extends ByteArrayInputStream {
  boolean closed;
  Tracking(){super(new byte[]{1,2,3,4});}
  public void close(){closed=true;}
 }
 public static void main(String[] args)throws Exception{
  try(URLClassLoader legacy=new URLClassLoader(new URL[]{new File(args[0]).toURI().toURL()},null)){
   Class<?> old=legacy.loadClass("com.gameloft.android.ANMP.GloftGF2F.S800x480.l");
   Class<?> now=Class.forName(args[1]);
   for(String field:new String[]{"Lm","Ln","order"}){
    Field a=old.getField(field),b=now.getField(field);equal(a.getType(),b.getType());equal(a.getModifiers(),b.getModifiers());
   }
   for(String method:new String[]{"available","read","readFloat","readDouble","readShort","readUnsignedShort","readInt","readLong"}){
    Method a=old.getMethod(method),b=now.getMethod(method);equal(a.getReturnType(),b.getReturnType());equal(a.getModifiers(),b.getModifiers());
   }
   equal(old.getMethod("cf",int.class).getModifiers(),now.getMethod("cf",int.class).getModifiers());
   String[] methods={"readShort","readUnsignedShort","readInt","readLong","readFloat","readDouble"};
   Random random=new Random(781923);
   for(int sample=0;sample<2000;sample++){
    byte[] bytes=new byte[16];random.nextBytes(bytes);
    for(int order:new int[]{0,1,-1,42})for(String method:methods){
     Object a=create(old,bytes,order),b=create(now,bytes,order);
     equal(invoke(old,a,method),invoke(now,b,method));
     equal(invoke(old,a,"available"),invoke(now,b,"available"));
     equal(invoke(old,a,"read"),invoke(now,b,"read"));
    }
   }
   for(int size=0;size<8;size++)for(int order:new int[]{0,1})for(String method:methods){
    byte[] bytes=new byte[size];equal(invoke(old,create(old,bytes,order),method),invoke(now,create(now,bytes,order),method));
   }
   for(Class<?> type:new Class<?>[]{old,now}){
    Tracking stream=new Tracking();InputStream reader=(InputStream)type.getConstructor(InputStream.class).newInstance(stream);
    reader.close();equal(stream.closed,false);equal(reader.read(),1);equal(reader.markSupported(),false);
    try{reader.reset();throw new AssertionError("Unexpected reset");}catch(IOException expected){}
    type.getConstructor(InputStream.class).newInstance(new Object[]{null});
   }
   // Added DataInput interface methods delegate to the original backing stream;
   // they were absent from the legacy DEX and are not falsely called differential.
   ByteArrayOutputStream output=new ByteArrayOutputStream();DataOutputStream data=new DataOutputStream(output);
   data.writeBoolean(true);data.writeByte(-7);data.writeByte(250);data.writeChar('Đ');data.writeUTF("Farm\u0000Việt");
   DataInput input=(DataInput)create(now,output.toByteArray(),1);
   equal(input.readBoolean(),true);equal(input.readByte(),(byte)-7);equal(input.readUnsignedByte(),250);
   equal(input.readChar(),'Đ');equal(input.readUTF(),"Farm\u0000Việt");
   System.out.println("PASS endian original-bytecode comparisons and completed DataInput contract; checks="+checks);
  }
 }
}
'''


class EndianInputStreamTest(unittest.TestCase):
    def test_original_bytecode_endian_eof_abi_and_lifecycle_behavior(self):
        original = Path(os.environ.get('GREENFARM_ORIGINAL_JAR', ROOT / '.local/bytecode-tools/greenfarm-original.jar'))
        if not original.is_file():
            self.skipTest('Prepare the pinned original bytecode jar before this differential test')
        candidates = list((ROOT / 'app/src/main/kotlin').glob('**/S800x480/l.kt'))
        self.assertEqual(len(candidates), 1)
        package = candidates[0].read_text().splitlines()[0].split()[1]
        with tempfile.TemporaryDirectory(prefix='endian-reader-') as temporary:
            directory = Path(temporary)
            oracle = directory / 'EndianReaderOracle.java'
            oracle.write_text(ORACLE)
            compile_mixed(candidates, [oracle], directory / 'classes')
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(directory / 'classes'),
                                     'EndianReaderOracle', str(original), package + '.l'],
                                    capture_output=True, text=True, timeout=120)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS endian original-bytecode comparisons', result.stdout)


if __name__ == '__main__':
    unittest.main()
