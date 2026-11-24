"""Full farm record codec with 100 crops and ten buildings on the host JVM.

Production ry/az/d/p/h and crop/building binary codecs execute unchanged. Entity
allocation and resource lookup are explicit peers (four deterministic seed rows),
while production crop fi/fj and bitflag methods restore the actual persisted
fields. This does not validate sprite assets, Android SQLite, simulation timing,
or gameplay on a device. Global inventory/currency coverage is in the adjacent
full global-gameplay codec test.
"""
from pathlib import Path
import hashlib
import re
import subprocess
import tempfile
import unittest

from test_aa_tile_renderer import extract_method
from test_global_gameplay_codec import ROOT, JAVA, PACKAGE, RUNTIME_HELPERS, jdk_tool

FARM_METHODS = (
    'static void ry()', 'static boolean az(boolean z)', 'static boolean d(boolean z, boolean z2)',
    'static int p(byte[] bArr, int i)', 'static int h(byte[] bArr, int i, int i2)',
    'static int g(byte[] bArr, int i, FarmEntity chVar)', 'static int w(byte[] bArr, int i)',
    'static int c(byte[] bArr, int i, FarmEntity chVar)', 'static int s(byte[] bArr, int i)',
    'static int ih(int i)', 'static int ii(int i)', 'static String rv()',
    'static void FY()', 'static int rm()', 'static int gk(int i)',
    *(f'static int {name}(int i, int i2)' for name in ('bG', 'bH', 'bI', 'bJ')),
)
ACTOR_METHODS = ('void fi(int i)', 'void fj(int i)', 'void fy(int i)', 'boolean fz(int i)')

FIXTURE = r'''
package com.quanhoang.greenfarm3.S800x480;
class Constants {static final int APP_INDEXING_SCHEDULE_INTERVAL_MS=1000;}
class bt {static boolean nY(){return false;}}
class ProgressSaveController {
 static boolean atSnapshotBoundary(){return true;}static boolean canSerialize(){return true;}
 static void requestSave(){throw new AssertionError("unexpected deferred save");}
 static void capturedBackendFarm(String p,int l,int m){throw new AssertionError("unexpected cloud call");}
}
class GameRuntime {
 static byte[]record;static int reads,writes;
 static byte[]B(String name,String binding){if(!name.equals("save_farm0"))throw new AssertionError(name);reads++;return record==null?null:record.clone();}
 static void a(String name,byte[]data,String binding){if(!name.equals("save_farm0"))throw new AssertionError(name);writes++;record=data.clone();}
 %RUNTIME%
}
class FarmGameEngine extends GameRuntime {
 %FIELDS%
 static String aYO;
 static void n(FarmEntity e){}static void k(FarmEntity e){}
 static int FC(){return 5;}static boolean qN(){return false;}
 static String e(byte[]d,int a,int n){throw new AssertionError("unexpected cloud encode");}
 static byte[]bJ(String name){throw new AssertionError("unexpected legacy map read");}
 static long MD(){return 777777777L;}
 static int lZ(int seed){return 10;}
 // Deterministic resource rows used by the unchanged production fi method.
 static void hd(int seed){avS=(char)seed;avT=1;avU=100;avV=seed*10;avW=0;avZ=2000+seed;awa=seed;awb=seed;awd=seed;awe=(byte)seed;}
 static void a(int event,FarmEntity e){throw new AssertionError("unexpected empty-plot event");}
 static FarmEntity h(int kind,int list,boolean state){return FarmEntity.allocate(kind,0);}
 static FarmEntity bs(int group,int definition){if(group!=3)throw new AssertionError();return FarmEntity.allocate(50,definition);}
 // Every unsupported actor path fails instead of pretending to restore.
 static int a(byte[]d,int p,FarmEntity e){throw new AssertionError("character not in fixture");}
 static int b(byte[]d,int p,FarmEntity e){throw new AssertionError("camera not in fixture");}
 static int d(byte[]d,int p,FarmEntity e){throw new AssertionError("decoration not in fixture");}
 static int e(byte[]d,int p,FarmEntity e){throw new AssertionError("disposable not in fixture");}
 static int f(byte[]d,int p,FarmEntity e){throw new AssertionError("retrievable not in fixture");}
 static int h(byte[]d,int p,FarmEntity e){throw new AssertionError("tree not in fixture");}
 static int i(byte[]d,int p,FarmEntity e){throw new AssertionError("animal not in fixture");}
 static int r(byte[]d,int p){throw new AssertionError("camera not in fixture");}
 static int t(byte[]d,int p){throw new AssertionError("decoration not in fixture");}
 static int v(byte[]d,int p){throw new AssertionError("retrievable not in fixture");}
 static int q(byte[]d,int p){throw new AssertionError("character not in fixture");}
 static int u(byte[]d,int p){throw new AssertionError("disposable not in fixture");}
 static int x(byte[]d,int p){throw new AssertionError("tree not in fixture");}
 static int y(byte[]d,int p){throw new AssertionError("animal not in fixture");}
 %ENGINE%
}
class FarmEntity {
 %ACTOR_FIELDS%
 static FarmEntity[]als=new FarmEntity[6];
 static FarmEntity allocate(int kind,int definition){
  FarmEntity e=new FarmEntity();e.Kx=kind;e.aoC=definition;
  if(als[1]==null)als[1]=e;else{FarmEntity tail=als[1];while(tail.aoi!=null)tail=tail.aoi;tail.aoi=e;}
  return e;
 }
 void qr(){}void qq(){}
 %ACTOR%
}
'''

RUNNER = r'''
package com.quanhoang.greenfarm3.S800x480;
import java.lang.reflect.*;import java.nio.file.*;import java.util.*;
public class ComplexFarmCodecCheck {
 static void check(boolean b,String m){if(!b)throw new AssertionError(m);}
 static void initialize()throws Exception{
  for(Field f:FarmGameEngine.class.getDeclaredFields()){
   Class<?>type=f.getType();
   if(type.isArray())f.set(null,Array.newInstance(type.getComponentType(),32));
   if(type==String.class)f.set(null,"");
  }
  FarmGameEngine.aCx=null;FarmGameEngine.aYO="4.4.2";FarmGameEngine.asL=406;
  FarmGameEngine.aCY=3;FarmGameEngine.aCZ=0x1357;FarmGameEngine.avD=-1;FarmGameEngine.aZB=0;
  FarmGameEngine.aEe=new int[5];FarmGameEngine.ayK=new boolean[13];
  FarmGameEngine.aZE=new short[]{2000};
 }
 static FarmEntity crop(int index){
  FarmEntity e=FarmEntity.allocate(49,index%4+1);FarmGameEngine.hd(e.aoC);
  e.fi(e.aoC);e.fj(new int[]{3,4,5,8}[index%4]);
  e.aol=FarmGameEngine.bI(index%10+5,index/10+5);
  e.aom=FarmGameEngine.bJ(index%10+5,index/10+5);
  e.HL=512|(index%2==0?2:0);e.amd=1670000000000L+index*1234567L;
  e.ame=FarmGameEngine.ih(10+index*19);return e;
 }
 static void populate(){
  for(int i=0;i<100;i++)crop(i);
  for(int i=0;i<10;i++){
   FarmEntity e=FarmEntity.allocate(50,i==0?11:i==1?12:i+1);
   e.aol=FarmGameEngine.bI(30+i,10);e.aom=FarmGameEngine.bJ(30+i,10);
   e.aon=12;e.HL=514;e.Bj=i%5;e.alV=1670000000000L+i*333333L;
  }
  FarmGameEngine.ayK[0]=true;FarmGameEngine.ayK[4]=true;FarmGameEngine.ayK[12]=true;
  for(int i=0;i<5;i++)FarmGameEngine.aEe[i]=FarmGameEngine.ih(i*7+11);
 }
 static void verify(){
  FarmEntity e=FarmEntity.als[1];
  for(int i=0;i<100;i++){
   check(e!=null&&e.Kx==49,"crop count/type changed at "+i);
   check(e.aoC==i%4+1&&e.Bj==new int[]{3,4,5,8}[i%4],"crop kind/state changed at "+i);
   check(e.amd==1670000000000L+i*1234567L,"crop timestamp changed at "+i);
   check(FarmGameEngine.ii(e.ame)==10+i*19,"crop grow duration changed at "+i);
   check(FarmGameEngine.bG(e.aol,e.aom)==i%10+5&&FarmGameEngine.bH(e.aol,e.aom)==i/10+5,"crop coordinate changed at "+i);
   check(e.HL==(512|(i%2==0?2:0)),"crop flags changed at "+i);
   e=e.aoi;
  }
  for(int i=0;i<10;i++){
   int definition=i==0?11:i==1?12:i+1;
   check(e!=null&&e.Kx==50&&e.aoC==definition,"building changed at "+i);
   check(e.Bj==i%5&&e.aon==12&&e.HL==514,"building state changed at "+i);
   if(i<2)check(e.alV==1670000000000L+i*333333L,"building timer changed at "+i);
   e=e.aoi;
  }
  check(e==null,"restore duplicated actors");
  check(FarmGameEngine.ayK[0]&&FarmGameEngine.ayK[4]&&FarmGameEngine.ayK[12]&&!FarmGameEngine.ayK[1],"expansion state changed");
  for(int i=0;i<5;i++)check(FarmGameEngine.ii(FarmGameEngine.aEe[i])==i*7+11,"farm inventory changed");
 }
 public static void main(String[]args)throws Exception{
  initialize();System.setProperty("HDIDFV",args[2]);Path file=Paths.get(args[1]);
  if(args[0].equals("save")){
   populate();FarmGameEngine.ry();check(GameRuntime.writes==1,"farm encoder did not write");
   check(FarmGameEngine.aCz>2500,"farm actors not encoded");
   Files.write(file,GameRuntime.record);System.out.println("PASS saved100crop10building farm record");return;
  }
  byte[]original=Files.readAllBytes(file);GameRuntime.record=original.clone();
  check(FarmGameEngine.az(false),"farm record rejected");verify();
  check(GameRuntime.reads==1&&GameRuntime.writes==0,"restore wrote or reread data");
  check(Arrays.equals(original,GameRuntime.record),"decode mutated source buffer");
  FarmGameEngine.ry();check(Arrays.equals(original,GameRuntime.record),"full farm record reencode changed bytes");
  check(Arrays.equals(original,Files.readAllBytes(file)),"original farm file overwritten");
  System.out.println("PASS full farm codec100crops/multiple kinds/grow times10buildings");
 }
}
'''


class ComplexFarmCodecTest(unittest.TestCase):
    @classmethod
    def setUpClass(cls):
        cls.java, cls.javac = jdk_tool('java'), jdk_tool('javac')
        cls.temporary = tempfile.TemporaryDirectory(prefix='greenfarm-complex-farm-codec-')
        cls.addClassCleanup(cls.temporary.cleanup)
        cls.directory = Path(cls.temporary.name)
        engine = (JAVA / 'FarmGameEngine.java').read_text()
        actor = (JAVA / 'FarmEntity.java').read_text()
        runtime = (JAVA / 'GameRuntime.java').read_text()
        engine_methods = '\n'.join(extract_method(engine, signature) for signature in FARM_METHODS)
        actor_methods = '\n'.join(extract_method(actor, signature) for signature in ACTOR_METHODS)
        # Names referenced by resource peers must also use the declared production types.
        all_references = engine_methods + actor_methods + FIXTURE + RUNNER
        fields = []
        for match in re.finditer(r'^    (?:(?:public|private|protected|final) )*static (\S+) (\w+)(?: = [^;\n]*)?;$', engine, re.M):
            typ, name = match.groups()
            if re.search(r'\b' + re.escape(name) + r'\b', all_references):
                fields.append('static ' + typ + ' ' + name + ';')
        actor_fields = []
        for match in re.finditer(r'^    (?:(?:public|private|protected|final) )*(?!static)(\S+) (\w+)(?: = [^;\n]*)?;$', actor, re.M):
            typ, name = match.groups()
            if re.search(r'\b' + re.escape(name) + r'\b', all_references):
                actor_fields.append(typ + ' ' + name + ';')
        fixture = (FIXTURE.replace('%FIELDS%', '\n'.join(fields))
                   .replace('%ACTOR_FIELDS%', '\n'.join(actor_fields))
                   .replace('%ACTOR%', actor_methods)
                   .replace('%ENGINE%', engine_methods)
                   .replace('%RUNTIME%', '\n'.join(extract_method(runtime, signature) for signature in RUNTIME_HELPERS)))
        (cls.directory / 'Fixture.java').write_text(fixture)
        (cls.directory / 'ComplexFarmCodecCheck.java').write_text(RUNNER)
        cls.classes = cls.directory / 'classes'
        cls.classes.mkdir()
        result = subprocess.run([str(cls.javac), '-encoding', 'UTF-8', '-d', str(cls.classes),
                                 str(cls.directory / 'Fixture.java'), str(cls.directory / 'ComplexFarmCodecCheck.java')],
                                capture_output=True, text=True, timeout=60)
        if result.returncode:
            raise AssertionError(result.stdout + result.stderr)

    def run_check(self, action, file, identity):
        result = subprocess.run([str(self.java), '-cp', str(self.classes), PACKAGE + '.ComplexFarmCodecCheck',
                                 action, str(file), identity], capture_output=True, text=True, timeout=30)
        self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
        self.assertIn('PASS', result.stdout)

    def test_one_hundred_crops_and_ten_buildings_roundtrip_in_twenty_new_processes(self):
        file = self.directory / 'farm-record.bin'
        self.run_check('save', file, 'identity-A')
        digest = hashlib.sha256(file.read_bytes()).digest()
        for attempt in range(20):
            with self.subTest(cold_process=attempt + 1):
                self.run_check('load', file, 'identity-' + str(attempt))
                self.assertEqual(hashlib.sha256(file.read_bytes()).digest(), digest)


if __name__ == '__main__':
    unittest.main()
