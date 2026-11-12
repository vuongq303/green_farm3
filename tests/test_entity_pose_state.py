"""Exercise the actual transform helper with original allocator/grid/aa methods.

Android storage, sprite loading and farm parsing are fixtures. The original
render-list insertion and grid functions reproduce duplicate reordering and
the coordinate quantization used by the farm codec.
"""
from pathlib import Path
import os
import subprocess
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'

FIXTURE = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
class GLLib {
 static byte[] record;static boolean failWrite;
 static void a(String key,byte[] data,String binding){if(failWrite)throw new IllegalStateException("write failed");record=data.clone();}
 static byte[] B(String key,String binding){return record==null?null:record.clone();}
}
class aa {protected int aaN,aaR;
AA_METHODS
}
class ci {static String rv(){return "device";}
GRID_METHODS
}
class ch {
 static ch[]als=new ch[6];static int api;
 int Kx,aoC=-1,aol,aom,aon,aov,aoG=1,aoW,aok=-1,aoU=4000;ch aoi,aoj;aa aoO;
 void d(int kind,int[]pose){Kx=kind;aol=pose[0];aom=pose[1];aon=pose[2];aoO=new aa();}
CH_METHODS
}
'''

RUNNER = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;
public class EntityPoseChecks {
 static void check(boolean b,String message){if(!b)throw new AssertionError(message);}
 static void clear(){ch.als=new ch[6];EntityPoseState.resetForLoad();}
 static ch entity(int type,int definition,int x,int y,int transform){ch e=ch.b(type,1,new int[]{0,0,12});e.aoC=definition;e.aol=ci.bI(x,y);e.aom=ci.bJ(x,y);e.aoO.aaN=transform;e.aoO.dS(931);return e;}
 static void order(ch...entities){ch.als[1]=entities.length==0?null:entities[0];for(int i=0;i<entities.length;i++){entities[i].aoi=i+1<entities.length?entities[i+1]:null;entities[i].aoj=i>0?entities[i-1]:null;}}
 static void intAt(byte[]b,int at,int n){b[at]=(byte)(n>>>24);b[at+1]=(byte)(n>>>16);b[at+2]=(byte)(n>>>8);b[at+3]=(byte)n;}
 static int intAt(byte[]b,int at){return (b[at]&255)<<24|(b[at+1]&255)<<16|(b[at+2]&255)<<8|(b[at+3]&255);}
 static void failRestore(byte[]bad,ch a,ch b){GLLib.record=bad;EntityPoseState.resetForLoad();a.aoO.aaN=3;b.aoO.aaN=4;boolean failed=false;try{EntityPoseState.restoreSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed,"bad identity/format accepted");check(a.aoO.aaN==3&&b.aoO.aaN==4,"failure partially changed transforms");}
 public static void main(String[]args){
  clear();GLLib.record=null;EntityPoseState.restoreSnapshot();
  clear();ch a=entity(54,7,4,5,1);ch empty=entity(54,7,4,5,0);empty.aoO=null;ch b=entity(54,7,4,5,6);a.aol+=2;a.aom+=2;order(a,empty,b);
  check(ci.bG(a.aol,a.aom)==4&&ci.bH(a.aol,a.aom)==5,"fixture grid quantization");
  EntityPoseState.writeSnapshot();byte[]saved=GLLib.record.clone();check(intAt(saved,8)==2&&a.aoO.aaN==1&&b.aoO.aaN==6&&a.aoO.lL()==931&&ch.als[1]==a,"save mutated state or captured null pose");
  clear();ch loadedA=entity(54,7,4,5,0);ch loadedEmpty=entity(54,7,4,5,0);loadedEmpty.aoO=null;ch loadedB=entity(54,7,4,5,0);
  check(ch.als[1]==loadedB&&loadedB.aoi==loadedEmpty&&loadedEmpty.aoi==loadedA,"actual allocator did not reverse tied decode order");
  EntityPoseState.restoreSnapshot();check(loadedA.aoO.aaN==1&&loadedB.aoO.aaN==6,"duplicate occurrence transforms swapped");check(loadedA.aoO.lL()==931&&loadedB.aoO.lL()==931,"transform restore changed elapsed timer");
  loadedA.aoO.aaN=7;EntityPoseState.restoreSnapshot();check(loadedA.aoO.aaN==7,"restore ran twice");
  aa semantic=new aa();semantic.dS(1234);semantic.dU(1);check(semantic.aaN==2&&semantic.lL()==1234,"fixture omitted original aa transform/timer distinction");
  // Decode/identity errors are resolved for every target before the first write.
  byte[]bad=saved.clone();intAt(bad,0,0);failRestore(bad,loadedA,loadedB);bad=saved.clone();intAt(bad,4,2);failRestore(bad,loadedA,loadedB);bad=saved.clone();intAt(bad,8,-1);failRestore(bad,loadedA,loadedB);bad=saved.clone();intAt(bad,8,20000);failRestore(bad,loadedA,loadedB);failRestore(Arrays.copyOf(saved,saved.length-1),loadedA,loadedB);failRestore(Arrays.copyOf(saved,saved.length+1),loadedA,loadedB);
  bad=saved.clone();intAt(bad,12+5*4,8);failRestore(bad,loadedA,loadedB);bad=saved.clone();intAt(bad,12+2*4,65536);failRestore(bad,loadedA,loadedB);bad=saved.clone();intAt(bad,12+6*4+4*4,0);failRestore(bad,loadedA,loadedB);bad=saved.clone();intAt(bad,12+6*4+1*4,8);failRestore(bad,loadedA,loadedB);
  GLLib.record=saved;EntityPoseState.resetForLoad();loadedB.aoO=null;boolean failed=false;try{EntityPoseState.restoreSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed&&loadedA.aoO.aaN==3,"null target caused partial restore");loadedB.aoO=new aa();EntityPoseState.restoreSnapshot();check(loadedA.aoO.aaN==1&&loadedB.aoO.aaN==6,"retry after failed matching did not recover");
  // Match the exact original type allowlist; decorations 50/22,23 and effects are omitted.
  clear();int[]types={13,20,22,42,48,49,50,52,69,54};ch[]all=new ch[13];for(int i=0;i<types.length;i++)all[i]=entity(types[i],types[i]==13?-1:1,i,i,2);all[10]=entity(50,22,1,1,5);all[11]=entity(50,23,1,1,5);all[12]=entity(47,1,1,1,5);order(all);ch.b(54,3,new int[]{0,0,12});EntityPoseState.writeSnapshot();check(intAt(GLLib.record,8)==10,"persistent entity filter differs from farm codec");
  clear();ch plot=entity(49,65535,3,6,5);EntityPoseState.writeSnapshot();byte[]sentinel=GLLib.record.clone();check(plot.aoC==65535,"save changed definition");clear();ch decodedPlot=entity(49,-1,3,6,0);EntityPoseState.restoreSnapshot();check(decodedPlot.aoO.aaN==5,"signed-byte plot sentinel did not match");
  GLLib.failWrite=true;failed=false;try{EntityPoseState.writeSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed&&decodedPlot.aoO.aaN==5&&Arrays.equals(sentinel,GLLib.record),"failed save mutated live pose/record");
  System.out.println("PASS entity transforms, original reversed allocation/grid codec, duplicate occurrences, aaN vs aaR, malformed/mismatch atomicity and retry");
 }
}
'''


class EntityPoseStateTest(unittest.TestCase):
    def test_actual_helper_and_original_pose_codec(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        suffix = '.exe' if os.name == 'nt' else ''
        javac, java = home / 'bin' / ('javac' + suffix), home / 'bin' / ('java' + suffix)
        if not javac.is_file() or not java.is_file():
            self.skipTest('Requires installed JDK')
        source = ROOT / 'app/src/main/java' / PACKAGE
        entity, game, animation = ((source / name).read_text() for name in ('ch.java', 'ci.java', 'aa.java'))
        methods = lambda text, signatures: '\n'.join(extract_method(text, signature) for signature in signatures)
        fixture_text = FIXTURE.replace('AA_METHODS', methods(animation, (
            'public int lL()', 'public void dS(int i)', 'public final void dU(int i)')))
        fixture_text = fixture_text.replace('GRID_METHODS', methods(game, tuple(
            f'static int {name}(int i, int i2)' for name in ('bG', 'bH', 'bI', 'bJ'))))
        fixture_text = fixture_text.replace('CH_METHODS', methods(entity, (
            'static ch b(int i, int i2, int[] iArr)', 'static void a(ch chVar, int i)', 'int oW()')))
        with tempfile.TemporaryDirectory(prefix='entity-pose-check-') as temporary:
            folder = Path(temporary)
            fixture = folder / 'Fixture.java'
            runner = folder / 'EntityPoseChecks.java'
            fixture.write_text(fixture_text)
            runner.write_text(RUNNER)
            result = subprocess.run([str(javac), '-source', '8', '-target', '8', '-d', str(folder),
                                     str(fixture), str(runner), str(source / 'EntityPoseState.java')],
                                    capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            result = subprocess.run([str(java), '-cp', str(folder),
                                     PACKAGE.replace('/', '.') + '.EntityPoseChecks'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS entity transforms', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
