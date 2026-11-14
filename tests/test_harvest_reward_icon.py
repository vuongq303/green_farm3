"""Execute production reward initialization and the production render gate on JVM.

No device/GL context is emulated; sprite calls are observed with distinct peers.
"""
from pathlib import Path
import subprocess
import tempfile
import unittest
import sys
from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory, qualified_package


class HarvestRewardIconTest(unittest.TestCase):
    def test_each_item_keeps_its_atlas_and_frame_and_reaches_render(self):
        source = (core_source_directory() / 'FarmEntity.java').read_text()
        init = extract_method(source, 'void b(int i, int i2, int i3, int i4, boolean z, boolean z2, int i5)')
        draw = extract_method(source, 'void oS()')
        amount = extract_method(source, 'void pZ()')
        # Exercise the exact production gate that used to reject negative atlas handles.
        gate = draw[draw.index('        if (fz(8)'):draw.index('        switch (this.Kx)')]
        code = '''package PACKAGE;
class FarmGameEngine {
 static short[] aZM={14,27,109,110,111,120},aZN={0,3,5,1,2,7};
 static int aYU,aYP=100,textDraws,formats;static FarmEntity aZj=new FarmEntity();
 static String a(int n,int language,String separator){formats++;return Integer.toString(n);}
 static void a(String text,int x,int y,int a,int b,int c,int d,int e,int f,boolean g,boolean h){
  if(!text.equals("+1"))throw new AssertionError("wrong quantity");textDraws++;
 }
static int Ie(){return 0;}static void s(int a,int b,int c,int d){}static void fP(int i){}
}
class GameRuntime {static Object Qj;static void bk(String s){}static void ke(){}static void l(int a,int b,int c){}static void be(String s){}}
class ProgressSaveController {static int changes;static void requestSave(){changes++;}}
class NativeProtocol {static final int MESSAGE_GET_ACCESS_TOKEN_REQUEST=256;}
class w {static boolean Zo;}
class Sprite {void bQ(int i){}int hw(){return 2;}}
class aa {Sprite aaO=new Sprite();int aaL,aaM,aaW;int lN(){return 0;}void lR(){}}
class FarmEntity {
 int Kx=47,anG,anJ,anK,aot,aou,anI,anL,anH,anM,aoG,aoH,anT,anU,anS,anR,anV;
 int aoI,draws,counts,aol,aom,aow,aox;String rewardAmountText;int rewardTextAmount,rewardTextLanguage=-1;boolean anN,anO,apk=true;aa aoO;
 boolean fz(int bit){return bit==8;}boolean a(Sprite s,int f){return true;}
 void aE(int sprite,int frame){
  if(sprite<0)throw new AssertionError("negative handle");
  if((sprite&32767)!=FarmGameEngine.aZM[anM&65535]||frame!=FarmGameEngine.aZN[anM&65535])throw new AssertionError("wrong item sprite");
  draws++;
 }
 void pg(){}
 AMOUNT
 INIT
 void draw(){GATE}
}
public class HarvestIconProbe {
 public static void main(String[] args){
  for(int n:new int[]{1,10,50,100}){
   FarmGameEngine.textDraws=FarmGameEngine.formats=0;
   FarmEntity actor=new FarmEntity();
   for(int i=0;i<n;i++){
    int item=i%FarmGameEngine.aZM.length;
    actor.b(i,i,59,2000,false,true,(1<<16)|item);for(int frame=0;frame<10;frame++)actor.draw();
    if(actor.aoG!=(0x8000|FarmGameEngine.aZM[item])||actor.aoH!=FarmGameEngine.aZN[item])throw new AssertionError("mapping lost");
    if(actor.anM>>>16!=1)throw new AssertionError("quantity changed");
   }
   if(actor.draws!=n*10||FarmGameEngine.textDraws!=n*10||FarmGameEngine.formats!=1)throw new AssertionError("icon/quantity skipped");
  }
  System.out.println("PASS 1/10/50/100 mixed item effects: original atlas/frame, icon + quantity render, no fallback");
 }
}
'''.replace('PACKAGE', qualified_package()).replace('INIT', init).replace('GATE', gate).replace('AMOUNT', amount)
        with tempfile.TemporaryDirectory() as temp:
            folder = Path(temp)
            java = folder / 'HarvestIconProbe.java'
            java.write_text(code)
            result = subprocess.run([str(jdk_tool('javac')), '-d', temp, str(java)], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', temp, qualified_package()+'.HarvestIconProbe'], capture_output=True, text=True)
            self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
            print(result.stdout.strip())
