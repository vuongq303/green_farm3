"""Local saves retain codec bindings without depending on a changing SDK ID."""
from pathlib import Path
import subprocess,sys,tempfile,unittest
from test_aa_tile_renderer import extract_method
ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool

class LocalSaveIdentityTest(unittest.TestCase):
    def test_legacy_binding_roundtrip_new_local_marker_and_malformed_record_rejection(self):
        p=ROOT/'app/src/main/java/com/quanhoang/greenfarm3/S800x480'
        game=(p/'FarmGameEngine.java').read_text();runtime=(p/'GameRuntime.java').read_text()
        loader=extract_method(game,'static boolean ax(boolean z)')
        self.assertNotIn('SaveTransferIdentity.matches',loader)
        self.assertNotIn('wrapper.au.Os()',loader)
        methods='\n'.join([extract_method(game,'static String readLocalSaveBinding(byte[] payload, int offset)'),
                          extract_method(game,'static String localSaveBindingForWrite()'),
                          extract_method(runtime,'public static int l(byte[] bArr, int i)')])
        java='''import java.nio.*;import java.nio.charset.*;
public class LocalSaveProbe {
 static String aCL; METHODS
 static byte[] record(String binding){byte[] b=binding.getBytes(StandardCharsets.UTF_8);return ByteBuffer.allocate(51200).order(ByteOrder.LITTLE_ENDIAN).put((byte)4).putInt(b.length).put(b).array();}
 static void bad(byte[] b,int at){try{readLocalSaveBinding(b,at);throw new AssertionError("malformed binding accepted");}catch(IllegalStateException expected){}}
 public static void main(String[]args){
  String original="07639811-1111-2222-3333-123456789abc";
  byte[] saved=record(original);aCL=readLocalSaveBinding(saved,1);
  if(!aCL.equals(original)||!localSaveBindingForWrite().equals(original))throw new AssertionError("valid local binding changed");
  // A new SDK/device identity never enters the local decoder's contract.
  aCL=readLocalSaveBinding(saved.clone(),1);if(!original.equals(aCL))throw new AssertionError("reopen rejected existing binding");
  aCL=null;if(!localSaveBindingForWrite().equals("device-local"))throw new AssertionError("new local farm requires device ID");
  bad(new byte[2],1);bad(ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).put((byte)4).putInt(-1).array(),1);
  bad(ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).put((byte)4).putInt(1025).array(),1);
  bad(ByteBuffer.allocate(5).order(ByteOrder.LITTLE_ENDIAN).put((byte)4).putInt(36).array(),1);
 }
}'''.replace('METHODS',methods)
        with tempfile.TemporaryDirectory() as d:
            f=Path(d)/'LocalSaveProbe.java';f.write_text(java)
            r=subprocess.run([str(jdk_tool('javac')),'-d',d,str(f)],capture_output=True,text=True)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
            r=subprocess.run([str(jdk_tool('java')),'-cp',d,'LocalSaveProbe'],capture_output=True,text=True,timeout=10)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
