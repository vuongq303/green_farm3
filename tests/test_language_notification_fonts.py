"""Exercise the production target-font staging transaction using controlled pack peers.

This validates font family/slot mapping, cache validity, retry bounds and rollback;
actual glyph decode/GL layout still requires Android verification.
"""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest
from test_aa_tile_renderer import extract_method

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import core_source_directory, qualified_package


class LanguageNotificationFontsTest(unittest.TestCase):
    def test_atomic_selected_font_mapping_retry_and_existing_owners(self):
        source = (core_source_directory() / 'FarmGameEngine.java').read_text()
        methods = '\n'.join(extract_method(source, signature) for signature in (
            'static String languageNotificationFontPack(int language)',
            'private static boolean languageNotificationFontsReady(a[] fonts)',
            'static boolean prepareLanguageNotificationFonts(int language)',
        ))
        code = '''package PACKAGE;
class a {
 short[] GM={1};final String pack;final int index;int[] map;
 static int releases;
 a(String p,int i){pack=p;index=i;}
 int hw(){return GM==null?0:1;}void e(int[] m){map=m;}
 void hs(){GM=null;releases++;}
}
class GameRuntime {
 static String RI="/11",failPack;static int failures,opens,factories;
 static void bk(String pack){opens++;if(pack.equals(failPack)&&failures-->0)throw new IllegalStateException("transient pack failure");RI=pack;}
 static void ke(){RI=null;}static void ai(boolean value){}
 static Object de(int index){return new int[]{index,42};}
}
class FarmGameEngine {
 static a[] aZh=new a[8];static a[][] asA=new a[3][];static int aYU;
 static a[] languageNotificationFonts,languageNotificationPreviousFonts;static String languageNotificationFontPack;
 static a a(int index,int palette,int x,int y,boolean z,boolean z2,boolean z3,int n,int m){GameRuntime.factories++;return new a(GameRuntime.RI,index);}
 static a b(int index,int palette,boolean z,boolean z2){GameRuntime.factories++;return new a(GameRuntime.RI,index);}
 METHODS
}
public class FontStagingProbe {
 static void check(boolean ok,String text){if(!ok)throw new AssertionError(text);}
 static void reset(){
  FarmGameEngine.aYU=0;FarmGameEngine.aZh=new a[8];
  for(int i:new int[]{0,1,2,4,6})FarmGameEngine.aZh[i]=new a("old",i);
  FarmGameEngine.asA[2]=FarmGameEngine.aZh;
  FarmGameEngine.languageNotificationFonts=null;FarmGameEngine.languageNotificationPreviousFonts=null;FarmGameEngine.languageNotificationFontPack=null;
  GameRuntime.RI="/11";GameRuntime.failPack=null;GameRuntime.opens=GameRuntime.factories=GameRuntime.failures=0;a.releases=0;
 }
 public static void main(String[] args){
  reset();a[] old=FarmGameEngine.aZh;
  check(FarmGameEngine.prepareLanguageNotificationFonts(1)&&GameRuntime.opens==0,"valid same-family fonts reloaded");
  check(FarmGameEngine.prepareLanguageNotificationFonts(16),"VI font staging failed");
  check(FarmGameEngine.aYU==0&&GameRuntime.RI.equals("/11"),"font preparation changed locale or lost pack owner");
  check(FarmGameEngine.aZh==FarmGameEngine.asA[2]&&FarmGameEngine.aZh!=old,"font group not atomically published");
  for(int index:new int[]{0,1,2,4,6}){
   check(FarmGameEngine.aZh[index].pack.equals("/4")&&FarmGameEngine.aZh[index].map[0]==7,"wrong VI glyph group");
   check(old[index].GM!=null,"released live original font");
  }
  int opens=GameRuntime.opens;FarmGameEngine.asA[2]=null;
  check(FarmGameEngine.prepareLanguageNotificationFonts(16)&&GameRuntime.opens==opens&&FarmGameEngine.asA[2]==FarmGameEngine.aZh,
    "cached target fonts reloaded or font-group alias not restored");
  a[] selected=FarmGameEngine.aZh;GameRuntime.failPack="/5";GameRuntime.failures=99;
  check(!FarmGameEngine.prepareLanguageNotificationFonts(9),"permanent failure reported success");
  check(FarmGameEngine.aZh==selected&&FarmGameEngine.asA[2]==selected&&GameRuntime.RI.equals("/11"),"failure published partial fonts or lost prior pack");
  check(GameRuntime.failures==97,"font loader did not stop after two attempts");
  GameRuntime.failures=1;
  check(FarmGameEngine.prepareLanguageNotificationFonts(9),"temporary failure did not recover within bound");
  check(FarmGameEngine.aZh[0].pack.equals("/2")&&FarmGameEngine.aZh[1].pack.equals("/5"),"CJK Latin/target small fonts not mapped");
  check(FarmGameEngine.aZh[2]==FarmGameEngine.aZh[4]&&FarmGameEngine.aZh[4]==FarmGameEngine.aZh[6],"CJK original aliases missing");
  check(FarmGameEngine.aZh[2].map[0]==3&&FarmGameEngine.aZh[0].map[0]==7,"CJK/Latin char maps mixed");
  opens=GameRuntime.opens;FarmGameEngine.aZh[2].GM=null;
  check(FarmGameEngine.prepareLanguageNotificationFonts(9)&&GameRuntime.opens>opens,"released font cache was reused");
  FarmGameEngine.aZh[2].GM=null;GameRuntime.failPack="/5";GameRuntime.failures=99;
  check(!FarmGameEngine.prepareLanguageNotificationFonts(9)&&FarmGameEngine.aZh==FarmGameEngine.languageNotificationPreviousFonts,
    "resource error was left with released target glyphs");
  reset();check(FarmGameEngine.prepareLanguageNotificationFonts(15)&&FarmGameEngine.aZh[6]==FarmGameEngine.aZh[2]
    &&FarmGameEngine.aZh[2].map[0]==5,"TH aliases/map lost");
  reset();check(FarmGameEngine.prepareLanguageNotificationFonts(7)&&FarmGameEngine.aZh[1].pack.equals("/6"),"JP pack wrong");
  System.out.println("PASS selected VI/TH/JP/KR and Latin fonts, atomic publication, retained owners, valid cache reuse, released-cache reload, bounded retry and pack restoration");
 }
}
'''.replace('PACKAGE', qualified_package()).replace('METHODS', methods)
        with tempfile.TemporaryDirectory() as temp:
            folder = Path(temp)
            java = folder / 'FontStagingProbe.java'
            java.write_text(code)
            log = folder / 'android/util/Log.java'
            log.parent.mkdir(parents=True)
            log.write_text('package android.util; public class Log {public static int e(String t,String m,Throwable x){return 0;}public static int w(String t,String m,Throwable x){return 0;}}')
            result = subprocess.run([str(jdk_tool('javac')), '-d', temp, str(java), str(log)], text=True, capture_output=True)
            self.assertEqual(result.returncode, 0, result.stderr)
            result = subprocess.run([str(jdk_tool('java')), '-cp', temp, qualified_package()+'.FontStagingProbe'], text=True, capture_output=True)
            self.assertEqual(result.returncode, 0, result.stdout+result.stderr)
            print(result.stdout.strip())
