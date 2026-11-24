"""Execute the actual SDK lifecycle adapter without loading Facebook in online mode.

APK call-site rewriting and reverse-semantic proofs are checked separately by
the packaging tests. This test verifies the source adapter's two runtime modes.
"""
from pathlib import Path
import hashlib
import re
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath

JAVA = {
    'android/os/Bundle.java': 'package android.os; public class Bundle {}',
    'android/content/Intent.java': 'package android.content; public class Intent {}',
    'android/content/res/Configuration.java': 'package android.content.res; public class Configuration {}',
    'com/quanhoang/greenfarm3/S800x480/LocalDevelopmentConfig.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class LocalDevelopmentConfig {public static boolean ONLINE_ACCOUNTS=true, OFFLINE_PLAY=false;}''',
    'com/quanhoang/greenfarm3/S800x480/SocialAccountController.java': """package com.quanhoang.greenfarm3.S800x480;
        public class SocialAccountController {public static int pauses,resumes;
          public static void onHostPaused(){pauses++;} public static void onHostResumed(){resumes++;}}""",
    'javax/microedition/midlet/Recorder.java': '''package javax.microedition.midlet;
        import java.util.*;
        public class Recorder {public static int classLoads;
          public static final List<String> events=new ArrayList<>();
          public static final List<List<Object>> arguments=new ArrayList<>();
          public static void record(String event,Object... args){events.add(event);arguments.add(Arrays.asList(args));}}''',
    'javax/microedition/midlet/v.java': '''package javax.microedition.midlet;
        import android.os.Bundle;import android.content.Intent;import android.content.res.Configuration;
        import com.quanhoang.greenfarm3.S800x480.LocalDevelopmentConfig;
        public class v {
          static {if(LocalDevelopmentConfig.ONLINE_ACCOUNTS)throw new ExceptionInInitializerError("Online accounts loaded the legacy Facebook SDK");Recorder.classLoads++;}
          public static void onCreate(Bundle b){Recorder.record("create",b);}
          public static void onDestroy(){Recorder.record("destroy");}
          public static void onPause(){Recorder.record("pause");}
          public static void onSaveInstanceState(Bundle b){Recorder.record("save",b);}
          public static void onRestart(){Recorder.record("restart");}
          public static void onResume(){Recorder.record("resume");}
          public static void onStart(){Recorder.record("start");}
          public static void onStop(){Recorder.record("stop");}
          public static void onNewIntent(Intent i){Recorder.record("intent",i);}
          public static void onActivityResult(int request,int result,Intent i){Recorder.record("result",request,result,i);}
          public static void onWindowFocusChanged(boolean focused){Recorder.record("focus",focused);}
          public static void onConfigurationChanged(Configuration c){Recorder.record("configuration",c);}
        }''',
}

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.LegacyFacebookLifecycle
import com.quanhoang.greenfarm3.S800x480.LocalDevelopmentConfig
import com.quanhoang.greenfarm3.S800x480.SocialAccountController
import javax.microedition.midlet.Recorder

fun main(){
 val bundle=android.os.Bundle();val intent=android.content.Intent();val configuration=android.content.res.Configuration()
 fun callbacks(){
  LegacyFacebookLifecycle.onCreate(bundle)
  LegacyFacebookLifecycle.onStart()
  LegacyFacebookLifecycle.onResume()
  LegacyFacebookLifecycle.onSaveInstanceState(bundle)
  LegacyFacebookLifecycle.onPause()
  LegacyFacebookLifecycle.onStop()
  LegacyFacebookLifecycle.onRestart()
  LegacyFacebookLifecycle.onNewIntent(intent)
  LegacyFacebookLifecycle.onActivityResult(73,-1,intent)
  LegacyFacebookLifecycle.onWindowFocusChanged(true)
  LegacyFacebookLifecycle.onConfigurationChanged(configuration)
  LegacyFacebookLifecycle.onDestroy()
 }
 callbacks()
 check(Recorder.classLoads==0&&Recorder.events.isEmpty()) {"Online website/browser accounts touched the legacy Facebook SDK"}
 check(SocialAccountController.pauses==1&&SocialAccountController.resumes==1){"The actual MIDlet bridge must forward each online host pause/resume once"}
 LocalDevelopmentConfig.ONLINE_ACCOUNTS=false
 LocalDevelopmentConfig.OFFLINE_PLAY=true
 callbacks()
 check(Recorder.classLoads==0&&Recorder.events.isEmpty())
 check(SocialAccountController.pauses==1&&SocialAccountController.resumes==1)
 LocalDevelopmentConfig.OFFLINE_PLAY=false
 callbacks()
 check(Recorder.classLoads==1)
 check(SocialAccountController.pauses==1&&SocialAccountController.resumes==1){"Legacy callbacks must not enter online auth"}
 check(Recorder.events==listOf("create","start","resume","save","pause","stop","restart","intent","result","focus","configuration","destroy"))
 check(Recorder.arguments[0][0]===bundle&&Recorder.arguments[3][0]===bundle)
 check(Recorder.arguments[7][0]===intent&&Recorder.arguments[8]==listOf(73,-1,intent))
 check(Recorder.arguments[9]==listOf(true)&&Recorder.arguments[10][0]===configuration)
 LegacyFacebookLifecycle.onCreate(null);LegacyFacebookLifecycle.onSaveInstanceState(null)
 LegacyFacebookLifecycle.onNewIntent(null);LegacyFacebookLifecycle.onActivityResult(-2,0,null)
 LegacyFacebookLifecycle.onConfigurationChanged(null)
 check(Recorder.arguments[12]==listOf(null)&&Recorder.arguments[13]==listOf(null)&&Recorder.arguments[14]==listOf(null))
 check(Recorder.arguments[15]==listOf(-2,0,null)&&Recorder.arguments[16]==listOf(null))
 LocalDevelopmentConfig.ONLINE_ACCOUNTS=true
 val calls=Recorder.events.size;callbacks()
 check(Recorder.events.size==calls) {"SDK callbacks resumed in online mode"}
 check(SocialAccountController.pauses==2&&SocialAccountController.resumes==2)
 println("PASS actual legacy Facebook lifecycle: no SDK initialization in online accounts; original callbacks/arguments retained in legacy mode")
}
'''


class LegacyFacebookLifecycleTest(unittest.TestCase):
    def test_original_sdk_is_never_initialized_for_online_accounts(self):
        with tempfile.TemporaryDirectory(prefix='legacy-facebook-lifecycle-') as temp:
            directory = Path(temp)
            peers = []
            for name, contents in JAVA.items():
                path = directory / name
                path.parent.mkdir(parents=True, exist_ok=True)
                path.write_text(contents)
                peers.append(path)
            harness = directory / 'LifecycleChecks.kt'
            harness.write_text(HARNESS)
            output = directory / 'classes'
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/LegacyFacebookLifecycle.kt'
            compile_mixed([source, harness], peers, output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), 'LifecycleChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS actual legacy Facebook lifecycle', result.stdout)

    def test_reference_midlet_changes_only_social_lifecycle_ownership(self):
        relative = 'app/src/main/java/javax/microedition/midlet/MIDlet.java'
        normalized, count = re.subn(r'com\.quanhoang\.greenfarm3\.S800x480\.LegacyFacebookLifecycle\.(on\w+)\(',
                                    r'v.\1(', (ROOT / relative).read_text())
        self.assertEqual(count, 12)
        # Pinned reference from origin/main 5ec1a965; no Git history is needed in CI.
        self.assertEqual(hashlib.sha256(normalized.encode()).hexdigest(),
                         'b6b9891ab4029e1f4f401626df1d4c19650bd011d02a510a28e0fb55c1e24244',
                         'Unrelated MIDlet lifecycle modules or gameplay startup changed')


if __name__ == '__main__':
    unittest.main()
