"""Verify game-side legacy Facebook reads cannot initialize its SDK in account mode."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

PACKAGE = 'com/quanhoang/greenfarm3/S800x480/'
JAVA = {
    PACKAGE + 'LocalDevelopmentConfig.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class LocalDevelopmentConfig {public static boolean ONLINE_ACCOUNTS=true, OFFLINE_PLAY=false;}''',
    PACKAGE + 'w.java': 'package com.quanhoang.greenfarm3.S800x480;public class w {public static boolean ZR=true;}',
    PACKAGE + 'GameRuntime.java': 'package com.quanhoang.greenfarm3.S800x480;public class GameRuntime {public static void be(String s){}}',
    PACKAGE + 'ay.java': 'package com.quanhoang.greenfarm3.S800x480;public class ay {public ay(Object image){}}',
    PACKAGE + 'ai.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class ai extends com.gameloft.android2d.socialnetwork.i {public int index=-1;public void eu(int value){index=value;}}''',
    PACKAGE + 'bu.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class bu {public static int clears,cacheReads,cacheWrites;public static ay cached;
          public static void clear(){clears++;}public static boolean cE(String id){cacheReads++;return cached!=null;}
          public static void a(String id,ay image){cacheWrites++;cached=image;}public static ay oc(){return cached;}}''',
    'com/gameloft/android2d/socialnetwork/j.java': 'package com.gameloft.android2d.socialnetwork;public class j {}',
    'com/gameloft/android2d/socialnetwork/i.java': '''package com.gameloft.android2d.socialnetwork;
        public class i {private String name="",first="",last="",middle="",id="",country="";private boolean friend;private int score;private j[] data;
          public String getName(){return name;}public void setName(String s){name=s;}
          public String getFirstName(){return first;}public void gU(String s){first=s;}
          public String getLastName(){return last;}public void gV(String s){last=s;}
          public String getMiddleName(){return middle;}public void gW(String s){middle=s;}
          public String getId(){return id;}public void fG(String s){id=s;}
          public String getCountry(){return country;}public void gX(String s){country=s;}
          public boolean UO(){return friend;}public void bL(boolean b){friend=b;}
          public int getScore(){return score;}public void mT(int n){score=n;}
          public j[] UP(){return data;}public void a(j[] values){data=values;}}''',
    'com/gameloft/android2d/socialnetwork/Recorder.java': '''package com.gameloft.android2d.socialnetwork;
        import java.util.*;public class Recorder {public static int loads;public static final List<String> calls=new ArrayList<>();}''',
    'com/gameloft/android2d/socialnetwork/l.java': '''package com.gameloft.android2d.socialnetwork;
        import com.quanhoang.greenfarm3.S800x480.LocalDevelopmentConfig;
        public class l {
          static {if(LocalDevelopmentConfig.ONLINE_ACCOUNTS)throw new ExceptionInInitializerError("Website touched legacy Facebook SDK");Recorder.loads++;}
          private static void call(String name){Recorder.calls.add(name);}
          public static void I(String a,String b){call("I");}public static void ar(int a,int b){call("ar");}
          public static void eL(int i){call("eL");}public static void nT(){call("nT");}public static void nU(){call("nU");}public static void nV(){call("nV");}
          public static boolean nW(){call("nW");return true;}public static boolean nX(){call("nX");return true;}public static boolean nY(){call("nY");return true;}
          private static i profile(){i p=new i();p.setName("Legacy Name");p.fG("legacy-id");p.gU("First");p.gV("Last");p.gW("Middle");p.gX("VN");p.mT(42);p.bL(true);return p;}
          public static i UR(){call("UR");return profile();}public static String getPassword(){call("password");return "legacy-session";}
          public static Object ha(String id){call("ha");return new Object();}
          public static void a(i user,i[] friends,String a,String b){call("one");}public static void a(i[] first,i[] second,String a,String b){call("many");}
          public static i mV(int index){call("mV");return profile();}public static int oa(){call("oa");return 2;}
          public static void b(String a,String b,String c,String d,String e,String f,String g,String h,String i){call("share");}
          public static void g(String a,String b,String c,String d){call("g");}
        }''',
}

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.*
import com.gameloft.android2d.socialnetwork.Recorder

fun disabled(){
 bt.I("unused","unused");bt.ar(32,32);bt.nS();bt.eL(16);bt.nT();bt.nU();bt.nV()
 check(!bt.nW()&&!bt.nX()&&!bt.nY())
 check(bt.nZ().name==""&&bt.getPassword()==""&&bt.cD("profile")==null)
 bt.a(null as ai?,null,"a","b");bt.a(null as Array<ai?>?,null,"a","b")
 check(bt.eM(0)==null&&bt.oa()==0&&bt.ob()==null)
 bt.b("a","b","c","d","e","f","g","h","i");bt.g("a","b","c","d")
}
fun main(){
 disabled()
 check(Recorder.loads==0&&Recorder.calls.isEmpty()&&bu.clears==0&&bu.cacheReads==0&&bu.cacheWrites==0)
 LocalDevelopmentConfig.ONLINE_ACCOUNTS=false
 LocalDevelopmentConfig.OFFLINE_PLAY=true;disabled()
 check(Recorder.loads==0&&Recorder.calls.isEmpty()&&bu.clears==0&&bu.cacheReads==0&&bu.cacheWrites==0)
 LocalDevelopmentConfig.OFFLINE_PLAY=false
 bt.I("legacy","legacy");bt.ar(32,32);bt.nS();bt.eL(16);bt.nT();bt.nU();bt.nV()
 check(bt.nW()&&bt.nX()&&bt.nY()&&bt.getPassword()=="legacy-session")
 val profile=bt.nZ();check(profile.name=="Legacy Name"&&profile.id=="legacy-id"&&profile.firstName=="First"&&profile.score==42&&profile.index==-2)
 val image=bt.cD("profile");check(image!=null&&bu.cacheWrites==1);check(bt.cD("profile")===image)
 bt.a(profile,arrayOf(profile),"a","b");bt.a(arrayOf(profile),arrayOf(profile),"a","b")
 check(bt.eM(0)!!.index==0&&bt.oa()==2&&bt.ob()!!.size==2)
 bt.b("a","b","c","d","e","f","g","h","i");bt.g("a","b","c","d")
 check(Recorder.loads==1&&bu.clears==3)
 for(name in listOf("I","ar","eL","nT","nU","nV","nW","nX","nY","UR","password","ha","one","many","mV","oa","share","g"))check(Recorder.calls.contains(name))
 val calls=Recorder.calls.size;val clears=bu.clears;val reads=bu.cacheReads
 LocalDevelopmentConfig.ONLINE_ACCOUNTS=true;disabled()
 check(Recorder.calls.size==calls&&bu.clears==clears&&bu.cacheReads==reads)
 LocalDevelopmentConfig.ONLINE_ACCOUNTS=false;w.ZR=false;disabled()
 check(Recorder.calls.size==calls&&bu.clears==clears&&bu.cacheReads==reads)
 println("PASS actual legacy social adapter: website accounts never load SDK/read Facebook tokens; legacy calls and disabled returns preserved")
}
'''


class LegacySocialAdapterTest(unittest.TestCase):
    def test_online_accounts_never_read_or_initialize_legacy_facebook(self):
        with tempfile.TemporaryDirectory(prefix='legacy-social-adapter-') as temp:
            directory = Path(temp)
            peers = []
            for name, contents in JAVA.items():
                path = directory / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(contents)
                peers.append(path)
            harness = directory / 'AdapterChecks.kt'
            harness.write_text(HARNESS)
            output = directory / 'classes'
            compile_mixed([ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/bt.kt', harness], peers, output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), 'AdapterChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS actual legacy social adapter', result.stdout)


if __name__ == '__main__':
    unittest.main()
