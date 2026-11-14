"""Execute production pack I/O against shipped assets and controlled broken streams.

No Android assets/cache/player data are modified. The fixture supplies only the
asset opener and logger; real pack header/offset/read/seek/close code is used.
"""
from pathlib import Path
import hashlib
import struct
import subprocess
import sys
import tempfile
import unittest
from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory, qualified_package

SIGNATURES = [
    'public static final class ResourceFailure',
    'private static ResourceFailure packFailure(String stage, Throwable cause)',
    'private static void bf(String str)', 'private static byte[] cT(int i)',
    'private static void jZ()', 'private static void ka()', 'private static void kb()',
    'private static void kc()', 'public static int cX(int i)',
    'public static final void bk(String str)',
    'private static void a(String str, byte[] bArr, int i, boolean z)',
    'private static InputStream bl(String str)',
    'public static final void ke()', 'private static final void ag(boolean z)',
    'private static final void kf()', 'public static int da(int i)',
    'public static final byte[] db(int i)', 'public static void dc(int i)',
    'public static void dd(int i)', 'public static int kg()',
    'public static int kh()', 'public static int ki()',
    'public static int c(byte[] bArr, int i, int i2)', 'public static void bm(String str)',
]

FIXTURE = r'''
package PACKAGE;
import java.io.*;import java.nio.file.*;import java.util.*;import java.util.concurrent.*;
import android.util.Log;
class w {static boolean Wl,Wk,Wj,Vf;static int Wh=256;}
class AssetSource {
 static final Map<String,byte[]> replacements=new HashMap<>();static int opens,closed;
 static String fault;static boolean missing,zeroBulk,zeroSkip,readError;
 static Path root;
 InputStream bj(String name){
  opens++;
  if(name.equals(fault)&&missing)return null;
  byte[] data;
  try{data=replacements.containsKey(name)?replacements.get(name):Files.readAllBytes(root.resolve(name.substring(1)));}
  catch(IOException missingFile){return null;}
  return new ByteArrayInputStream(data){boolean released;
   public synchronized int read(byte[] b,int at,int len){
    if(name.equals(fault)&&readError)throw new IllegalStateException("transient fixture stream failure");
    if(name.equals(fault)&&zeroBulk)return 0;
    return super.read(b,at,len);
   }
   public synchronized long skip(long n){return name.equals(fault)&&zeroSkip?0:super.skip(n);}
   public void close(){if(!released){closed++;released=true;}}
  };
 }
 static void clearFault(){fault=null;missing=zeroBulk=zeroSkip=readError=false;replacements.clear();}
}
class GameRuntime {
 static String RI,UM;static InputStream RJ;static int RK,RM,RN,RS,RT,UN;
 static boolean RU;static byte[] RL,RV,Sq;static byte[][]RW;static short RO,RQ;static int[] RP;static short[]RR;
 static AssetSource QD=new AssetSource();
 static void c(boolean ignored,String message){}
 static void be(String message){}static void d(Exception failure){}
 static void a(InputStream stream,int size){throw new AssertionError("compression is disabled in shipped config");}
 static void bh(String name){throw new AssertionError("INIEX flag is disabled");}
 static void bg(String name){throw new AssertionError("CORE flag is disabled");}
 static void kk(){}static int cZ(int i){throw new AssertionError("INIEX flag is disabled");}
 static int cV(int i){throw new AssertionError("CORE flag is disabled");}
 static byte[]cY(int i){throw new AssertionError("INIEX flag is disabled");}
 static byte[]cW(int i){throw new AssertionError("CORE flag is disabled");}
 METHODS
}
public class ResourcePackProbe {
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static String sha(byte[] data)throws Exception{
  StringBuilder out=new StringBuilder();for(byte value:java.security.MessageDigest.getInstance("SHA-256").digest(data))out.append(String.format("%02x",value&255));return out.toString();
 }
 static void failure(Runnable action,String path,String stage){
  try{action.run();throw new AssertionError("broken stream was accepted");}
  catch(GameRuntime.ResourceFailure failure){
   check(path.equals(failure.resourcePath),"asset path missing: "+failure.getMessage());
   check(failure.stage.startsWith(stage),"wrong failure stage: "+failure.getMessage());
   check(GameRuntime.RI==null&&GameRuntime.RJ==null&&GameRuntime.RP==null,"failed cache remained reusable");
  }
 }
 static void reset(){GameRuntime.ke();AssetSource.clearFault();w.Wh=256;GameRuntime.RW=null;}
 static void shipped(Path hashes)throws Exception{
  int checked=0;
  for(String line:Files.readAllLines(hashes)){
   String[] cols=line.split(" ");String pack=cols[0];int index=Integer.parseInt(cols[1]);
   GameRuntime.bk("/"+pack);byte[] data=GameRuntime.db(index);
   check(cols[2].equals(sha(data)),"shipped payload differs: "+pack+":"+index);
   check(Integer.parseInt(cols[3])==GameRuntime.RT,"resource type changed");checked++;
  }
  GameRuntime.bk("/3");int opens=AssetSource.opens;
  for(int i=0;i<20;i++)GameRuntime.bk("/3");
  check(AssetSource.opens==opens,"valid open pack was loaded repeatedly");
  GameRuntime.ke();GameRuntime.bk("/3");check(AssetSource.opens==opens+1,"closed pack not reopened");
  System.out.println("PASS "+checked+" forward/reverse shipped records, segmented packs, repeat-open cache and reopen");
 }
 static void failedOpen()throws Exception{
  reset();AssetSource.fault="/3";AssetSource.missing=true;
  failure(()->GameRuntime.bk("/3"),"/3","open-stream");
  AssetSource.clearFault();GameRuntime.bk("/3");check(GameRuntime.db(0).length>0,"same path stayed poisoned after open failure");
  reset();byte[] data=Files.readAllBytes(AssetSource.root.resolve("3"));
  AssetSource.fault="/3";AssetSource.replacements.put("/3",Arrays.copyOf(data,8));
  failure(()->GameRuntime.bk("/3"),"/3","read-byte-eof");
  AssetSource.clearFault();GameRuntime.bk("/3");check(GameRuntime.db(1).length>0,"partial header prevented retry");
  System.out.println("PASS missing/partial header failures invalidate only the failed open and allow same-name recovery");
 }
 static void stalledReads()throws Exception{
  reset();GameRuntime.bk("/3");byte[] expected=GameRuntime.db(2);GameRuntime.ke();
  AssetSource.fault="/3";AssetSource.zeroBulk=true;GameRuntime.bk("/3");
  check(Arrays.equals(expected,GameRuntime.db(2)),"zero-length bulk read lost bytes");
  reset();w.Wh=0;AssetSource.fault="/3";AssetSource.zeroSkip=true;GameRuntime.bk("/3");
  check(Arrays.equals(expected,GameRuntime.db(2)),"zero-progress skip did not fall back to read");
  System.out.println("PASS zero-progress bulk read and skip complete without spinning");
 }
 static void truncatedAndExceptions()throws Exception{
  reset();byte[] data=Files.readAllBytes(AssetSource.root.resolve("3"));
  AssetSource.fault="/3";AssetSource.replacements.put("/3",Arrays.copyOf(data,60));GameRuntime.bk("/3");
  failure(()->GameRuntime.db(0),"/3","read-eof");
  AssetSource.clearFault();GameRuntime.bk("/3");check(GameRuntime.db(0).length>0,"failed payload prevented retry");
  reset();GameRuntime.bk("/3");
  // A stream exception must terminate, not leave the old no-progress loop alive.
  AssetSource.fault="/3";AssetSource.readError=true;
  try{GameRuntime.db(0);throw new AssertionError("stream exception accepted");}
  catch(GameRuntime.ResourceFailure expected){check(expected.getCause()!=null,"cause discarded");}
  System.out.println("PASS EOF and stream errors fail without partial payload or infinite read loop");
 }
 static void mimeIsolation()throws Exception{
  reset();GameRuntime.bk("/3");GameRuntime.db(0);
  InputStream previous=GameRuntime.RJ;int position=GameRuntime.RN;
  GameRuntime.bm("/0");
  check(GameRuntime.RJ==previous&&GameRuntime.RN==position&&"/3".equals(GameRuntime.RI),"MIME disturbed active pack state");
  int opens=AssetSource.opens;GameRuntime.bm("/0");check(AssetSource.opens==opens,"valid MIME cache loaded again");
  GameRuntime.RW=null;AssetSource.fault="/0";AssetSource.replacements.put("/0",new byte[]{2,3,'a'});
  try{GameRuntime.bm("/0");throw new AssertionError("partial MIME accepted");}
  catch(GameRuntime.ResourceFailure failure){check("/0".equals(failure.resourcePath)&&"mime".equals(failure.stage),"MIME failure lacks resource/stage");}
  check(GameRuntime.RW==null&&GameRuntime.RJ==previous&&GameRuntime.RN==position,"MIME failure poisoned either cache");
  AssetSource.clearFault();GameRuntime.bm("/0");check(GameRuntime.RW.length==2,"MIME retry failed");
  check(GameRuntime.db(1).length>0,"active pack could not continue after MIME load");
  System.out.println("PASS MIME cache publishes atomically, retains valid entries and preserves unrelated pack cursor");
 }
 static void lock()throws Exception{
  reset();ExecutorService executor=Executors.newSingleThreadExecutor();Future<byte[]> future;
  synchronized(GameRuntime.class){
   future=executor.submit(()->{synchronized(GameRuntime.class){GameRuntime.bk("/3");try{return GameRuntime.db(0);}finally{GameRuntime.ke();}}});
   Thread.sleep(40);check(!future.isDone(),"pack read bypassed staged-font resource lock");
  }
  check(future.get(5,TimeUnit.SECONDS).length>0,"serialized resource read failed");executor.shutdownNow();
  System.out.println("PASS shared pack operations respect the session monitor");
 }
 public static void main(String[]args)throws Exception{
  AssetSource.root=Paths.get(args[1]);
  switch(args[0]){case "shipped":shipped(Paths.get(args[2]));break;case "open":failedOpen();break;case "stalled":stalledReads();break;case "truncated":truncatedAndExceptions();break;case "mime":mimeIsolation();break;case "lock":lock();break;default:throw new AssertionError(args[0]);}
  GameRuntime.ke();
 }
}
'''


class ResourcePackIOTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.temp = tempfile.TemporaryDirectory(prefix='resource-pack-io-')
        cls.addClassCleanup(cls.temp.cleanup)
        cls.folder = Path(cls.temp.name)
        source = (core_source_directory() / 'GameRuntime.java').read_text()
        methods = '\n'.join(extract_method(source, signature) for signature in SIGNATURES)
        java = cls.folder / 'ResourcePackProbe.java'
        java.write_text(FIXTURE.replace('PACKAGE', qualified_package()).replace('METHODS', methods))
        logger = cls.folder / 'android/util/Log.java'
        logger.parent.mkdir(parents=True)
        logger.write_text('package android.util;public class Log {public static int e(String tag,String message,Throwable cause){return 0;}}')
        result = subprocess.run([str(jdk_tool('javac')), '-d', str(cls.folder), str(java), str(logger)], capture_output=True, text=True)
        if result.returncode:
            raise AssertionError(result.stderr)
        cls.assets = ROOT / 'app/src/main/assets/800x480'
        expected = []
        for name in [str(number) for number in range(1, 16)] + ['EN', 'DE', 'FR', 'IT', 'ES', 'BR', 'JP', 'KR', 'RU', 'TR', 'PL', 'TH', 'VI', 'LATAM']:
            data = (cls.assets / name).read_bytes()
            count, parts = struct.unpack_from('<HH', data)
            starts = struct.unpack_from('<' + 'H'*parts, data, 4)
            rows = []
            for part in range(parts):
                raw = data if part == 0 else (cls.assets / (name+'.'+str(part))).read_bytes()
                n = (starts[part+1] if part+1<parts else count) - starts[part]
                at = 4+parts*2 if part == 0 else 0
                offsets = struct.unpack_from('<'+'i'*(n+1), raw, at)
                for entry in range(n):
                    record = raw[offsets[entry]:offsets[entry+1]]
                    rows.append(f'{name} {starts[part]+entry} {hashlib.sha256(record[1:]).hexdigest()} {record[0]}')
            expected += rows + rows[::-1]
        cls.hashes = cls.folder / 'expected.txt'
        cls.hashes.write_text('\n'.join(expected)+'\n')

    def run_case(self, name):
        result = subprocess.run([str(jdk_tool('java')), '-cp', str(self.folder), qualified_package()+'.ResourcePackProbe', name, str(self.assets), str(self.hashes)], capture_output=True, text=True, timeout=15)
        self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
        print(result.stdout.strip())

    def test_failed_and_stale_renderer_cannot_teardown_retry(self):
        source = (core_source_directory() / 'GameRuntime.java').read_text()
        init = extract_method(source, 'protected void Init()')
        self.assertGreater(init.index('initializationReady = true'), init.index('aa.lV()'))
        self.assertIn('initializationReady = false', extract_method(source, 'protected void jE()'))
        methods = '\n'.join(extract_method(source, signature) for signature in (
            'public final void markInitializationFailed()',
            'public void onDrawFrame(GL10 gl10)',
            'public void onSurfaceCreated(GL10 gl10, EGLConfig eGLConfig)',
            'public void onSurfaceChanged(GL10 gl10, int i, int i2)',
        ))
        fixture = r'''package PACKAGE;
class GL10 {} class EGLConfig {}
class GLES20 {static void glClear(int flags){}}
class w {static boolean Vy,Vw,VA;}
class Midlet {int quits;void akD(){quits++;}}
class GameRuntime {
 private volatile boolean initializationFailed;
 boolean initializationReady;
 static GameRuntime QD;static int Qn,SE,SF,SG,SH,Tk,closed;static boolean Qp;static Midlet Qu=new Midlet();
 int draws,created,changed,disposed;
 static void ke(){closed++;}static void f(int a,int b,int c,int d){}
 void jJ(){draws++;}void a(GL10 gl){created++;}void ku(){changed++;}void jE(){initializationReady=false;disposed++;}
 static void be(String text){}static void b(String a,String b,int c){}static void d(Exception error){}
 METHODS
}
public class RendererLifecycleProbe {
 static void check(boolean ok,String text){if(!ok)throw new AssertionError(text);}
 static void callbacks(GameRuntime game){game.onSurfaceCreated(null,null);game.onSurfaceChanged(null,800,480);game.onDrawFrame(null);}
 public static void main(String[]args){
  GameRuntime failed=new GameRuntime();GameRuntime.QD=failed;GameRuntime.Qn=0;failed.markInitializationFailed();
  check(GameRuntime.Qn==-1&&GameRuntime.closed==1,"failed owner did not close its pack");
  GameRuntime retry=new GameRuntime();GameRuntime.QD=retry;GameRuntime.Qn=0;callbacks(failed);
  check(failed.draws==0&&failed.created==0&&failed.changed==0&&failed.disposed==0&&GameRuntime.Qu.quits==0,"failed callbacks damaged retry");
  callbacks(retry);check(retry.draws==0&&retry.disposed==0&&GameRuntime.Qu.quits==0&&retry.created==1&&retry.changed==1,"pre-Init draw was treated as quit or blocked surface setup");
  retry.initializationReady=true;retry.onDrawFrame(null);check(retry.draws==1,"ready renderer did not run");
  GameRuntime active=new GameRuntime();active.initializationReady=true;GameRuntime.QD=active;callbacks(retry);failed.markInitializationFailed();
  check(retry.draws==1&&retry.created==1&&GameRuntime.closed==1&&GameRuntime.Qn==0,"stale owner touched active resources");
  GameRuntime.Qn=-1;active.onDrawFrame(null);active.onDrawFrame(null);check(active.disposed==1&&GameRuntime.Qu.quits==1,"normal valid-owner quit was suppressed or repeated");
  System.out.println("PASS failed/stale GL callbacks cannot tear down retry; normal active-owner lifecycle preserved");
 }
}
'''.replace('PACKAGE', qualified_package()).replace('METHODS', methods)
        with tempfile.TemporaryDirectory(prefix='renderer-owner-') as temp:
            path = Path(temp) / 'RendererLifecycleProbe.java'
            path.write_text(fixture)
            result = subprocess.run([str(jdk_tool('javac')), '-d', temp, str(path)], text=True, capture_output=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', temp, qualified_package()+'.RendererLifecycleProbe'], text=True, capture_output=True, timeout=10)
            self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
            print(result.stdout.strip())

    def test_shipped_pack_records_and_reopen(self): self.run_case('shipped')
    def test_missing_and_partial_open_recover_on_same_path(self): self.run_case('open')
    def test_zero_progress_reads_and_skips(self): self.run_case('stalled')
    def test_eof_and_io_exception_fail_without_spinning(self): self.run_case('truncated')
    def test_mime_cache_is_atomic_and_preserves_active_pack(self): self.run_case('mime')
    def test_shared_pack_lock(self): self.run_case('lock')
