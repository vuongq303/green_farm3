"""Execute the actual GUI reset, render, hitbox and listener boundaries.

The fixture isolates these methods from unrelated Android initialization. Sprite
and draw peers only record calls; no fixture is packaged into the application.
The source routing checks supplement this with the removed SDK/network callers.
"""

from pathlib import Path
import subprocess
import struct
import sys
import tempfile
import unittest

from test_aa_tile_renderer import extract_method


ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool

PACKAGE = 'com/gameloft/android/ANMP/GloftGF2F/S800x480'
CORE = ROOT / 'app/src/main/java' / PACKAGE

SHELL = r'''
package com.gameloft.android.ANMP.GloftGF2F.S800x480;
import java.util.*;
class GLURLConnection { static final int HTTP_ACCEPTED=202; }
class a {
 int bG(int frame){return 1;}
 void a(int frame,int module,int[] rectangle,int offset){
  rectangle[0]=2;rectangle[1]=3;rectangle[2]=48;rectangle[3]=48;
 }
}
public class MoreGamesProbe {
 static short[][][] atc=new short[50][160][],atd=new short[50][160][];
 static String[][] ate=new String[50][160];
 static boolean aTK=true,auS;
 static int auR=-1,aAL,draws,animations,closed,dispatched;
 static int[] aAN=new int[200];
 static a[] atf={new a()},aZf={new a()};
 static List<Integer> hitboxes=new ArrayList<>();
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static boolean gN(int layer){return false;}
 static boolean bf(int layer,int item){return false;}
 static int DD(){return 5;}
 static int gI(int type){return 19;}
 static void x(int layer,int item,int animation,int ignored){animations++;}
 static void bo(int layer,int item){animations++;}
 static void DI(){animations=0;}
 static void qB(){closed++;}
 static void d(int id,int kind,int x,int y,int width,int height,int flags){hitboxes.add(id);}
 static void a(int layer,short[] row,int x,int y){draws++;}
 static void b(int layer,short[] row,int x,int y){draws++;}
 static void a(String text,int layer,int item,int x,int y){draws++;}
 static void e(String text,int layer,int item,int x,int y){draws++;}
 static void a(int layer,short[] row,int x,int y,int item){draws++;}
 %METHODS%
 static short[] row(int type){
  short[] result=new short[24];result[0]=(short)type;result[5]=0;
  result[7]=-1;result[10]=1;result[11]=1;result[15]=1;
  result[17]=-1;result[18]=1;return result;
 }
 static void reset(){
  for(int l=0;l<50;l++)for(int n=0;n<160;n++){
   atc[l][n]=new short[24];atd[l][n]=new short[24];
  }
  hitboxes.clear();Arrays.fill(aAN,-1);draws=animations=closed=dispatched=0;
 }
 public static void main(String[] args){
  reset();int checks=0;
  for(int layer=0;layer<50;layer++)for(int item=0;item<160;item++){
   boolean removed=(layer==1&&(item==34||item==38))||(layer==27&&(item==22||item==23));
   check(MoreGamesRemoval.isWidget(layer,item)==removed,"wrong identity "+layer+":"+item);checks++;
  }
  int[][] removed={{1,34},{1,38},{27,22},{27,23}};
  int[][] retained={{1,4},{1,5},{1,7},{1,46},{1,81},{27,8},{27,10},{27,12},{26,22}};
  for(int[] widget:removed){int l=widget[0],n=widget[1];
   for(int type:new int[]{202,204,205}){
    atd[l][n]=row(type);bd(l,n);
    int flag=type==205?10:type==204?15:11;
    check(atc[l][n][flag]==0,"reset retained raw visibility");
    if(type==205)check(atc[l][n][18]==0,"reset retained raw input");
    check(!bg(l,n)&&!bh(l,n),"reset retained More Games flags");
    e(l,n,true);f(l,n,true);
    check(atc[l][n][flag]==0,"enable resurrected recommendation visibility");
    if(type==205)check(atc[l][n][18]==0,"enable resurrected input");
    // Direct packed-flag writes must still never render or register hitboxes.
    atc[l][n]=row(type);ate[l][n]="recommendation";
    draws=0;a(l,atc[l][n],n);check(draws==0,"recommendation rendered");
    check(!bg(l,n)&&!bh(l,n),"raw flags bypassed removal");checks+=5;
   }
   gO((l<<16)|n);check(dispatched==0,"removed listener reached dispatch");checks++;
  }
  for(int[] widget:retained){int l=widget[0],n=widget[1];
   atd[l][n]=row(205);bd(l,n);e(l,n,true);f(l,n,true);
   check(bg(l,n)&&bh(l,n),"unrelated button suppressed");
   draws=0;a(l,atc[l][n],n);check(draws==1,"unrelated button did not render");
   int before=dispatched;gO((l<<16)|n);check(dispatched==before+1,"unrelated listener suppressed");checks+=3;
  }
  hitboxes.clear();gK(1);gK(27);
  for(int[] widget:removed)check(!hitboxes.contains((widget[0]<<16)|widget[1]),"removed hitbox survived");
  for(int[] widget:retained)if(widget[0]!=26)
   check(hitboxes.contains((widget[0]<<16)|widget[1]),"unrelated hitbox missing");
  aH(true);check(animations==0&&!bg(1,34)&&!bh(1,34),"More Games animation survived");
  for(int state:new int[]{0,1,2,3,5,6,7,8})check(!lk(state),"legacy IGP state retained");
  check(closed==1,"legacy IGP state did not leave on entry");
  retiredConfirmation();check(closed==2,"obsolete confirmation did not close");
  System.out.println("PASS: "+checks+" identity/reset/render/listener checks; hitboxes, currency controls and stale states verified");
 }
}
'''


class MoreGamesRemovalTest(unittest.TestCase):
    def setUp(self):
        self.source = (CORE / 'ci.java').read_text()

    def fixture(self, mutations=()):
        signatures = [
            'static void a(int i, short[] sArr, int i2)',
            'static void aH(boolean z)', 'static void bd(int i, int i2)',
            'static boolean bg(int i, int i2)', 'static boolean bh(int i, int i2)',
            'static void e(int i, int i2, boolean z)',
            'static void f(int i, int i2, boolean z)',
            'static void gK(int i)', 'static boolean lk(int i)',
        ]
        methods = '\n'.join(extract_method(self.source, signature) for signature in signatures)
        # Execute the actual listener guard; the remainder of the large switch is
        # a recorder because all its unrelated actions require the Android game.
        dispatch = extract_method(self.source, 'static void gO(int i)')
        methods += '\n' + dispatch[:dispatch.index('        switch (')] + 'dispatched++;}'
        confirmation = extract_method(self.source, 'public static void IW()')
        start = confirmation.index('            case 1:')
        branch = confirmation[start:confirmation.index('            case 13:', start)]
        methods += '\nstatic void retiredConfirmation(){switch(1){' + branch + '}}'
        for before, after in mutations:
            self.assertIn(before, methods)
            methods = methods.replace(before, after, 1)
        return SHELL.replace('%METHODS%', methods)

    def run_fixture(self, source):
        with tempfile.TemporaryDirectory(prefix='more-games-removal-') as temp:
            directory = Path(temp)
            package = directory / PACKAGE
            package.mkdir(parents=True)
            java = package / 'MoreGamesProbe.java'
            java.write_text(source)
            helper = package / 'MoreGamesRemoval.java'
            helper.write_text((CORE / 'MoreGamesRemoval.java').read_text())
            subprocess.run([str(jdk_tool('javac')), '-d', str(directory), str(java), str(helper)],
                           check=True, capture_output=True, text=True)
            return subprocess.run([str(jdk_tool('java')), '-cp', str(directory),
                                   PACKAGE.replace('/', '.') + '.MoreGamesProbe'],
                                  capture_output=True, text=True)

    def test_actual_gui_methods_remove_rendering_input_and_hitboxes(self):
        result = self.run_fixture(self.fixture())
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn('currency controls and stale states verified', result.stdout)
        print(result.stdout.strip())

    def test_regressions_in_each_removal_boundary_are_detected(self):
        mutations = [
            ('MoreGamesRemoval.removeWidget(i, i2, atc[i][i2]);', ''),
            ('if (MoreGamesRemoval.isWidget(i, i2)) {\n            return;', 'if (false) {\n            return;'),
            ('if (!MoreGamesRemoval.isWidget(i, i3) &&', 'if (true &&'),
            ('if (MoreGamesRemoval.isWidget((i >> 16) & 65535, i & 65535))', 'if (false)'),
        ]
        for mutation in mutations:
            with self.subTest(boundary=mutation[0]):
                result = self.run_fixture(self.fixture([mutation]))
                self.assertNotEqual(result.returncode, 0, 'regression escaped: ' + mutation[0])
                self.assertIn('AssertionError', result.stderr)

    def test_recommendation_launchers_and_exclusive_network_job_are_deleted(self):
        self.assertNotRegex(self.source, r'\b(?:Cn|sn)\s*\(')
        self.assertNotRegex(self.source, r'\bj\((?:4|aYU), false\)')
        self.assertNotIn('aw.', self.source)
        self.assertNotIn('igp_launch_value.json', self.source)
        self.assertNotIn('URL-TEMPLATE-GAME', self.source)
        self.assertNotIn('dt(12501)', self.source)
        self.assertNotIn('public static void dB(String str)', self.source)
        self.assertNotIn('static void bp(int i, int i2)', self.source)
        self.assertNotRegex(self.source, r'case (?:65570|1769494):')
        self.assertNotRegex(self.source, r'\bdM\(aYU\)')
        # IGP also transports unrelated legal/support/update links. Those callers
        # and rewarded advertisements retain their original SDK implementation.
        for retained in ('GLLib.a(z, z2, i, i2)', 'wrapper.au.eu(', 'wrapper.au.ev(',
                         'ctg=UPDATE', 'ctg=GAME_REVIEW', 'AdsManager.IsAdOnScreen(3)'):
            self.assertIn(retained, self.source)

    def welcome_fixture(self, mutation=None):
        resume = extract_method(self.source, 'public void jG()')
        # Keep the actual final resume block, including the shared CRM resume
        # callback. Everything before it is unrelated game/SDK initialization.
        start = resume.rindex('        if (com.gameloft.a.a.k.VR() != null)')
        resume = 'static void resume(){\n' + resume[start:]
        farm = extract_method(self.source, 'static boolean jM(int i)')
        marker = '                bas++;\n            }'
        start = farm.index(marker) + len(marker)
        end = farm.index('            Ex();', start)
        farm = 'static void farmEntry(){\n' + farm[start:end] + '\n}'
        if mutation == 'resume':
            resume = resume[:-1] + '''
                if(qD()<=2||aTO<=-1)return;
                if(lF())lG();dM(aYU);
            }'''
        if mutation == 'farm':
            farm = farm.replace('b(true, false, false);',
                                'if(FC()>=3)dM(aYU);b(true, false, false);')
        # A fixture peer replaces only the package qualification, so no SDK is
        # loaded. The actual retained resume branch/call still executes.
        resume = resume.replace('com.gameloft.a.a.k', 'SharedCrm')
        return r'''
package com.gameloft.android.ANMP.GloftGF2F.S800x480;
class ch {}
class SharedCrm {
 static int resumes;static final SharedCrm manager=new SharedCrm();
 static SharedCrm VR(){return manager;}void VT(){resumes++;}
}
public class WelcomeRemovalProbe {
 static int aYU,aTO=21,avR,state=18,level=3,promotions,checks,closes,gameplay;
 static int qD(){return state;}static int FC(){return level;}
 static void dM(int language){promotions++;}
 static boolean lF(){checks++;return true;}static void lG(){closes++;}
 static void b(boolean x,boolean y,boolean z){gameplay++;}
 static void c(boolean x,int y,int z){gameplay++;}
 static void aC(boolean value){gameplay++;}static void a(boolean value,ch target){gameplay++;}
 %RESUME%
 %FARM%
 public static void main(String[]args){
  int runs=0;
  for(int language:new int[]{0,1,2,3,4,5,7,9,10,11,12,15,16,20})
   for(int currentState:new int[]{1,2,18})for(int age:new int[]{-1,21})for(int currentLevel:new int[]{1,2,3,10}){
    aYU=language;state=currentState;aTO=age;level=currentLevel;resume();farmEntry();runs++;
   }
  if(promotions!=0||checks!=0||closes!=0)throw new AssertionError("Welcome promotion route survived");
  if(SharedCrm.resumes!=runs||gameplay!=runs*4)throw new AssertionError("shared resume/gameplay work removed");
  System.out.println("PASS: "+runs+" actual resume/farm entry neighborhoods; no Welcome promotion, shared calls retained");
 }
}
'''.replace('%RESUME%', resume).replace('%FARM%', farm)

    def run_welcome_fixture(self, mutation=None):
        with tempfile.TemporaryDirectory(prefix='welcome-removal-') as temp:
            directory = Path(temp)
            source = directory / 'WelcomeRemovalProbe.java'
            source.write_text(self.welcome_fixture(mutation))
            compiled = subprocess.run([str(jdk_tool('javac')), '-d', str(directory), str(source)],
                                      capture_output=True, text=True)
            self.assertEqual(compiled.returncode, 0, compiled.stderr)
            return subprocess.run([str(jdk_tool('java')), '-cp', str(directory),
                                   PACKAGE.replace('/', '.') + '.WelcomeRemovalProbe'],
                                  capture_output=True, text=True)

    def test_automatic_welcome_promotion_routes_are_removed(self):
        result = self.run_welcome_fixture()
        self.assertEqual(result.returncode, 0, result.stderr)
        self.assertIn('shared calls retained', result.stdout)
        print(result.stdout.strip())

    def test_reintroducing_either_actual_welcome_route_is_detected(self):
        for mutation in ('resume', 'farm'):
            with self.subTest(route=mutation):
                result = self.run_welcome_fixture(mutation)
                self.assertNotEqual(result.returncode, 0)
                self.assertIn('Welcome promotion route survived', result.stderr)

    def test_original_text_id_identifies_feature_in_every_packaged_language(self):
        expected = {
            'VI': '+ Trò chơi', 'EN': '+ Games', 'FR': '+ Jeux', 'DE': '+ Spiele',
            'IT': '+ Giochi', 'ES': '+ Juegos', 'BR': '+ Jogos', 'TR': '+ Oyunlar',
            'RU': '+ игры', 'LATAM': '+ Juegos', 'PL': '+ gry', 'TH': '+ เกม',
            'JP': '+ゲーム ', 'KR': '\n\n+게임 ',
        }
        # GLLib's original pack header, Text_LoadTextFromStream and dt use
        # little-endian offsets, a 10-bit string index, and UTF-8 text. Decode
        # read-only to establish the identities; never rewrite packed assets.
        for language, label in expected.items():
            with self.subTest(language=language):
                data = (ROOT / 'app/src/main/assets/800x480' / language).read_bytes()
                entries, segments = struct.unpack_from('<HH', data)
                pack, index = 12501 >> 10, 12501 & 1023
                self.assertLess(pack, entries)
                start = struct.unpack_from('<I', data, 4 + 2 * segments + 4 * pack)[0]
                self.assertEqual(data[start], 2)
                payload = data[start + 1:]
                self.assertEqual(payload[0], pack)
                count = struct.unpack_from('<I', payload, 1)[0]
                self.assertLess(index, count)
                offsets = (0,) + struct.unpack_from('<' + 'I' * count, payload, 5)
                text = payload[5 + 4 * count:]
                self.assertEqual(text[offsets[index]:offsets[index + 1]].decode('utf-8'), label)


if __name__ == '__main__':
    unittest.main()
