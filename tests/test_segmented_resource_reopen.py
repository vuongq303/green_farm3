"""Execute the second pack reader, including failure/retry and split offsets."""
from pathlib import Path
import subprocess,sys,tempfile,unittest
from test_aa_tile_renderer import extract_method
ROOT=Path(__file__).resolve().parents[1]
sys.path.insert(0,str(ROOT/'tools'))
from android_build_tools import jdk_tool

class SegmentedResourceTest(unittest.TestCase):
    def test_failed_header_closes_stream_and_same_path_can_reopen(self):
        source=(ROOT/'app/src/main/java/com/quanhoang/greenfarm3/S800x480/GameRuntime.java').read_text()
        signatures=['public static final class ResourceFailure','private static ResourceFailure packFailure(String stage, Throwable cause)',
          'private static void kb()','private static void kc()',
          'private static <Failure extends Throwable> RuntimeException propagateChecked(Throwable failure)',
          'private static InputStream a(String str, int i, byte[] bArr, int i2)',
          'static int b(InputStream inputStream)','static int c(InputStream inputStream)']
        methods='\n'.join(extract_method(source,x) for x in signatures)
        java='''import java.io.*;
class Log {static void e(String a,String b,Throwable c){}}
class GameRuntime {
 static String RI,UM;static InputStream RJ;static byte[] RL,data;static int RM,RN,UN,RC,RD,closed;static short RO,RQ;static int[]RP;static short[]RR;
 static InputStream bl(String name){if(data==null)return null;return new ByteArrayInputStream(data){public void close(){closed++;}};}
 METHODS
 static void check(boolean ok){if(!ok)throw new AssertionError();}
 static void fail(){try{a("/split",0,null,0);throw new AssertionError("accepted bad header");}catch(ResourceFailure e){check(e.resourcePath.equals("/split")&&RJ==null&&RI==null&&RP==null);}}
 public static void main(String[]args){
  data=null;fail();data=new byte[]{2,0,10};fail();check(closed==1);
  // The second offset intentionally reaches into a later split file.
  data=new byte[]{2,0,10,0,0,0,100,0,0,0,1};a("/split",0,null,0);check(RJ!=null&&RP[1]==100);kc();
  data=new byte[]{2,0,10};fail();check(closed==3);
  data=new byte[]{2,0,10,0,0,0,100,0,0,0,1};a("/split",0,null,0);check(RJ!=null);kc();
  System.out.println("PASS split header EOF, closed stream, same-path retry, offsets across subpacks");
 }
}'''.replace('METHODS',methods)
        with tempfile.TemporaryDirectory() as d:
            p=Path(d)/'GameRuntime.java';p.write_text(java)
            r=subprocess.run([str(jdk_tool('javac')),'-d',d,str(p)],capture_output=True,text=True)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
            r=subprocess.run([str(jdk_tool('java')),'-cp',d,'GameRuntime'],capture_output=True,text=True,timeout=10)
            self.assertEqual(r.returncode,0,r.stdout+r.stderr)
