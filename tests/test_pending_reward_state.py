"""Compile the actual queue snapshot helper and original pickup settlement routine.

Android storage, sprites and the entity allocator are fixtures. The real helper
must preserve the pending animation's authoritative state and the extracted
ch.pX method must award it once; these tests do not claim SQLite/device coverage.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from game_source_context import (qualified_package, core_source_directory, kotlin_source_directory, type_name, adapt_fixture, legacy_fixture_names)
PACKAGE = qualified_package().replace('.', '/')

FIXTURE = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;
class GLLib {
 static int Qy=1; static byte[] record; static boolean failWrite;
 static void a(String key,byte[] data,String binding){if(failWrite)throw new IllegalStateException("write failed");record=data.clone();}
 static byte[] B(String key,String binding){return record==null?null:record.clone();}
 static void bk(String name){} static void ke(){}
}
class FacebookRequestErrorClassification {static final int EC_INVALID_SESSION=102;}
class Sprite {int by(int i){return 0;}}
class ci {
 static int xp,coins,cash,energy,chest,items,objectiveEvents,selectedReward,resourceLoads;
 static boolean axd,aCk,axW; static int aYP=100,aYS;static long avC;
 static ch aZj=new ch();static int[][][]atc=new int[2][256][4];static Sprite[]atf={new Sprite(),new Sprite()};
 static short[]aZL=new short[256];
 static String rv(){return "test-device";}static void s(int a,int b,int c,int d){resourceLoads++;}static void fP(int i){}
 static long MD(){return 1000;}static void gX(int i){}
 static void hX(int i){xp+=i;}static void hS(int i){coins+=i;}static void hT(int i){cash+=i;}
 static void hV(int i){energy+=i;}static void hU(int i){items+=i;}
 static void N(int a,int b,int c){objectiveEvents++;}static void aa(int a,int b,int c){objectiveEvents++;}
 static void O(int a,int b,int c){items+=c;}static void hI(int i){}static void Fy(){}
 static void bN(int a,int b){items+=a;}static void hg(int i){cash+=i;}static void bW(int a,int b){}
 static void h(boolean a,int b){chest++;selectedReward=b;}
}
class ch {
 static ch[]als=new ch[6];static int failFactory=-1;
 int Kx,HL,aol,aom,aon,aot,aou,aov,aoo,aoE,aoF,aoG,aoH;
 int anG,anH,anI,anJ,anK,anL,anM,anP,anQ,anR,anS,anT,anU,anV,anW;
 boolean anN,anO,anX; ch aoi,aoj;int aok=-1;
 static ch b(int kind,int list,int[]pose){if(failFactory==0)throw new IllegalStateException("allocator failed");if(failFactory>0)failFactory--;ch e=new ch();e.Kx=kind;e.HL=520;e.aol=pose[0];e.aom=pose[1];e.aon=pose[2];e.anX=true;e.aok=list;e.aoi=als[list];if(e.aoi!=null)e.aoi.aoj=e;als[list]=e;return e;}
 void qr(){}
'''

RUNNER = r'''package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;import java.io.*;
public class PendingRewardChecks {
 static void check(boolean b,String message){if(!b)throw new AssertionError(message);}
 static ch pickup(int kind,int amount){ch e=ch.b(47,3,new int[]{17,28,14});e.anG=kind;e.anM=amount;e.anN=true;e.anO=true;e.anI=100;e.anH=101;e.anJ=-17;e.anK=28;e.aot=7;e.aou=8;e.aov=9;e.aoo=2;e.aoE=10;e.aoF=11;e.aoG=32769;e.aoH=kind;e.anL=12;e.anP=13;e.anQ=-14;e.anR=4;e.anS=15;e.anT=16;e.anU=17;e.anV=3;e.anW=-1;return e;}
 static int count(){int n=0;for(ch e=ch.als[3];e!=null;e=e.aoi)n++;return n;}
 static void clear(){ch.als=new ch[6];PendingRewardState.resetForLoad();ch.failFactory=-1;}
 static void tick(){for(ch e=ch.als[3];e!=null;){ch next=e.aoi;e.pX();e=next;}}
 static void failRestore(byte[]bad){clear();ch cosmetic=ch.b(46,3,new int[]{0,0,0});GLLib.record=bad;boolean failed=false;try{PendingRewardState.restoreSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed,"malformed record accepted");check(ch.als[3]==cosmetic&&count()==1,"malformed restore mutated queue");}
 static void intAt(byte[]b,int at,int n){b[at]=(byte)(n>>>24);b[at+1]=(byte)(n>>>16);b[at+2]=(byte)(n>>>8);b[at+3]=(byte)n;}
 public static void main(String[]args){
  clear();GLLib.record=null;PendingRewardState.restoreSnapshot();check(count()==0,"missing record made rewards");
  clear();ch cosmetic=ch.b(46,3,new int[]{0,0,0});ch credited=pickup(36,999);credited.anX=false;ch coin=pickup(36,31);ch xp=pickup(37,19);
  PendingRewardState.writeSnapshot();byte[]saved=GLLib.record.clone();check(count()==4&&ch.als[3]==xp&&coin.anX&&ci.xp==0&&ci.coins==0,"save settled or removed live rewards");
  clear();PendingRewardState.restoreSnapshot();check(count()==2&&ch.als[3].anG==37&&ch.als[3].aoi.anG==36,"pending filter/order");ch r=ch.als[3];check(r.aol==17&&r.aom==28&&r.aon==14&&r.anJ==-17&&r.anP==13&&r.anQ==-14&&r.anW==-1&&r.anN&&r.anO&&r.aoo==2&&r.aot==7,"animation state lost");
  PendingRewardState.restoreSnapshot();check(count()==2,"repeated restore duplicated rewards");tick();check(ci.xp==19&&ci.coins==31&&count()==0,"natural original settlement failed");tick();check(ci.xp==19&&ci.coins==31,"awarded twice");
  PendingRewardState.writeSnapshot();clear();PendingRewardState.restoreSnapshot();check(count()==0&&ci.xp==19&&ci.coins==31,"settled reward persisted");
  // A cold load of the same atomic pre-credit snapshot resumes the same amounts.
  ci.xp=0;ci.coins=0;GLLib.record=saved;clear();PendingRewardState.restoreSnapshot();tick();check(ci.xp==19&&ci.coins==31,"cold-load award mismatch");
  clear();pickup(59,110|(2<<16));PendingRewardState.writeSnapshot();clear();PendingRewardState.restoreSnapshot();tick();check(ci.energy==2&&ci.objectiveEvents==3,"item settlement omitted original objective events");
  clear();ch chest=pickup(60,1);chest.aoG=32768|112;chest.aoH=2;chest.anN=false;chest.anV=3;PendingRewardState.writeSnapshot();clear();PendingRewardState.restoreSnapshot();check(ci.resourceLoads==1,"resolved chest sprite not loaded");tick();check(ci.chest==1&&ci.selectedReward==14,"chest reward selection changed");PendingRewardState.writeSnapshot();clear();PendingRewardState.restoreSnapshot();tick();check(ci.chest==1,"chest awarded twice after bounce-stage restore");
  byte[]bad=saved.clone();intAt(bad,0,0);failRestore(bad);bad=saved.clone();intAt(bad,4,2);failRestore(bad);bad=saved.clone();intAt(bad,8,-1);failRestore(bad);bad=saved.clone();intAt(bad,8,20000);failRestore(bad);failRestore(Arrays.copyOf(saved,saved.length-1));bad=Arrays.copyOf(saved,saved.length+1);failRestore(bad);bad=saved.clone();intAt(bad,12+19*4,4);failRestore(bad);
  clear();GLLib.record=saved;ch marker=ch.b(46,3,new int[]{0,0,0});ch.failFactory=1;boolean failed=false;try{PendingRewardState.restoreSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed&&ch.als[3]==marker&&count()==1,"partial restore was not rolled back");ch.failFactory=-1;PendingRewardState.restoreSnapshot();check(count()==3,"retry restore did not recover");
  clear();pickup(36,7);GLLib.record=saved;failed=false;try{PendingRewardState.restoreSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed&&count()==1,"existing pending queue was duplicated");
  GLLib.failWrite=true;ch live=ch.als[3];failed=false;try{PendingRewardState.writeSnapshot();}catch(IllegalStateException expected){failed=true;}check(failed&&ch.als[3]==live&&live.anX,"failed write changed live rewards");
  System.out.println("PASS pending reward snapshots, original settlement once, cold load, chest stage, malformed records, rollback and retry");
 }
}
'''


class PendingRewardStateTest(unittest.TestCase):
    def test_actual_snapshot_and_original_settlement(self):
        home = Path(os.environ.get('JAVA_HOME', '/workspace/tooling/jdk-25'))
        suffix = '.exe' if os.name == 'nt' else ''
        javac, java = home / 'bin' / ('javac' + suffix), home / 'bin' / ('java' + suffix)
        if not javac.is_file() or not java.is_file():
            self.skipTest('Requires installed JDK')
        source = core_source_directory()
        entity = legacy_fixture_names((source / (type_name('ch') + '.java')).read_text())
        methods = '\n'.join(extract_method(entity, signature) for signature in
                            ('void fx(int i)', 'boolean fz(int i)', 'void oN()', 'void pX()'))
        with tempfile.TemporaryDirectory(prefix='pending-reward-check-') as temporary:
            folder = Path(temporary)
            fixture = folder / 'Fixture.java'
            runner = folder / 'PendingRewardChecks.java'
            fixture.write_text(adapt_fixture(FIXTURE + methods + '\n}'))
            runner.write_text(adapt_fixture(RUNNER))
            compile_mixed([ROOT / 'app/src/main/kotlin' / PACKAGE / 'PendingRewardState.kt'],
                          [fixture, runner], folder)
            result = subprocess.run([str(java), '-cp', runtime_classpath(folder),
                                     PACKAGE.replace('/', '.') + '.PendingRewardChecks'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS pending reward snapshots', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
