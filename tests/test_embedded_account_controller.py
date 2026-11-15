"""Run the production callback/launch adapter with in-memory Android/vault peers.

This verifies the public/private callback boundary and Intent ownership. It does
not replace a real WebView, Android Keystore, or live-backend device test.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest
import xml.etree.ElementTree as ET

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from test_network_diagnostics import LOG

JAVA = {
    'android/content/Context.java': '''package android.content; public class Context {
        public Context getApplicationContext(){return this;}}''',
    'android/app/Activity.java': '''package android.app; public class Activity extends android.content.Context {
        public android.content.Intent launched; public boolean isFinishing(){return false;}
        public void runOnUiThread(Runnable r){r.run();} public void startActivity(android.content.Intent i){launched=i;}}''',
    'android/content/Intent.java': '''package android.content; public class Intent {
        public static final String ACTION_VIEW="android.intent.action.VIEW",CATEGORY_BROWSABLE="android.intent.category.BROWSABLE";
        public String action; public android.net.Uri uri; public Class<?> target;
        public Intent(Context c,Class<?> t){target=t;} public Intent(String a,android.net.Uri u){action=a;uri=u;}
        public Intent addCategory(String c){return this;}}''',
    'android/net/Uri.java': '''package android.net; public class Uri {private final String value;
        private Uri(String s){value=s;} public static Uri parse(String s){return new Uri(s);}
        public String toString(){return value;}}''',
    'android/os/Build.java': '''package android.os; public class Build {public static class VERSION {public static int SDK_INT=28;}}''',
    'android/util/Log.java': LOG,
    'javax/microedition/midlet/MIDlet.java': '''package javax.microedition.midlet; public class MIDlet {
        public static android.app.Activity cnA=new android.app.Activity();}''',
    'com/quanhoang/greenfarm3/S800x480/LocalDevelopmentConfig.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class LocalDevelopmentConfig {public static final String AUTH_BASE_URL="https://auth.example/";}''',
    'com/quanhoang/greenfarm3/S800x480/EmbeddedAccountActivity.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class EmbeddedAccountActivity extends android.app.Activity {}''',
}

PEERS = r'''
package com.quanhoang.greenfarm3.S800x480
class SecureAccountStore(context:android.content.Context, origin:String?=null):SocialAccountFlow.Vault {
 companion object {var pending:SocialAttempt?=null;var returned:SocialCallback?=null;var saved:SocialSession?=null}
 override fun session()=saved
 override fun saveSession(value:SocialSession){saved=value}
 override fun clearSession(){saved=null}
 override fun attempt()=pending
 override fun saveAttempt(value:SocialAttempt){pending=value}
 override fun callback()=returned
 override fun saveCallback(value:SocialCallback){returned=value}
 override fun clearAttempt(){pending=null;returned=null}
}
class SocialAuthClient(base:String):SocialAccountFlow.Backend {
 val origin=validateOrigin(base)
 companion object {fun validateOrigin(base:String):String {check(base=="https://auth.example/");return base}}
 private var provider="password"
 override fun attempt(provider:String,state:String,challenge:String):SocialAttemptReply {
  this.provider=provider
  return SocialAttemptReply(origin+(if(provider=="password")"auth/web/login?attempt=" else "auth/browser/start?id=")+"a".repeat(43),300)
 }
 override fun exchange(code:String,verifier:String)=SocialSession("access", "refresh",System.currentTimeMillis()+900000,
  System.currentTimeMillis()+2592000000,SocialIdentity("GF000001",provider,"Player"))
 override fun me(accessToken:String)=SocialIdentity("GF000001",provider,"Player")
 override fun refresh(refreshToken:String)=exchange("unused","unused")
 override fun logout(accessToken:String,refreshToken:String){}
}
'''

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.*
import javax.microedition.midlet.MIDlet
import android.content.Intent
import android.util.Log

fun settle(expected:Int){
 val until=System.nanoTime()+5_000_000_000L
 while(System.nanoTime()<until){SocialAccountController.tick();if(SocialAccountController.status()==expected)return;Thread.sleep(5)}
 error("Unexpected adapter status ${SocialAccountController.status()}, expected $expected")
}
fun main(){
 val ctx=android.content.Context();val state="s".repeat(43);val verifier="v".repeat(43);val code="c".repeat(43)
 val url="https://auth.example/auth/web/login?attempt="+"a".repeat(43)
 val cb=SocialAccountFlow.REDIRECT_URI+"?state=$state&code=$code"
 check(SocialAccountController.accountName()=="")
 SecureAccountStore.pending=SocialAttempt("password",state,verifier,url,System.currentTimeMillis()+300000)
 check(!SocialAccountController.recordCallback(ctx,cb)){"Exported callback accepted a password-account handoff"}
 check(SecureAccountStore.returned==null)
 check(!SocialAccountController.recordEmbeddedCallback(ctx,cb.replace(state,"x".repeat(43))))
 check(!SocialAccountController.recordEmbeddedCallback(ctx,cb+"&access_token=private"))
 check(!SocialAccountController.recordEmbeddedCallback(ctx,cb+"&game_id=GF000999&display_name=Forged"))
 check(SecureAccountStore.returned==null)
 check(SocialAccountController.recordEmbeddedCallback(ctx,cb))
 check(SecureAccountStore.returned==SocialCallback(state,code,null))
 SecureAccountStore.returned=null
 SecureAccountStore.pending=SecureAccountStore.pending!!.copy(provider="facebook",authorizationUrl="https://auth.example/auth/browser/start?id="+"a".repeat(43))
 check(!SocialAccountController.recordEmbeddedCallback(ctx,cb)){"Facebook must remain outside WebView"}
 check(SocialAccountController.recordCallback(ctx,cb))
 SecureAccountStore.returned=null
 SecureAccountStore.pending=SecureAccountStore.pending!!.copy(provider="password",authorizationUrl=url,expiresAt=System.currentTimeMillis()-1)
 check(!SocialAccountController.recordEmbeddedCallback(ctx,cb))
 SecureAccountStore.pending=null
 SocialAccountController.initialize(ctx,"https://auth.example/")
 SocialAccountController.startupGate();settle(SocialAccountController.SIGNED_OUT)
 SocialAccountController.choose("password");settle(SocialAccountController.BROWSER)
 val embedded=MIDlet.cnA.launched!!
 check(embedded.target==EmbeddedAccountActivity::class.java&&embedded.action==null&&embedded.uri==null){"Password login escaped into a public browser Intent"}
 val descriptor=SocialAccountController.embeddedPage(ctx)!!
 check(descriptor.first==url&&!descriptor.toString().contains(SecureAccountStore.pending!!.verifier))
 SocialAccountController.closeEmbedded(ctx,url+"x");check(SocialAccountController.status()==SocialAccountController.BROWSER)
 SocialAccountController.closeEmbedded(ctx,url);check(SocialAccountController.status()==SocialAccountController.SIGNED_OUT&&SecureAccountStore.pending==null)
 SocialAccountController.choose("password");settle(SocialAccountController.BROWSER)
 check(MIDlet.cnA.launched!!.target==EmbeddedAccountActivity::class.java&&SocialAccountController.accountName()=="")
 val ownCallback=SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"
 check(!SocialAccountController.recordCallback(ctx,ownCallback))
 check(SocialAccountController.recordEmbeddedCallback(ctx,ownCallback));settle(SocialAccountController.READY)
 check(SocialAccountController.accountName()=="Player"&&SocialAccountController.gameId()=="GF000001")
 SocialAccountController.logout();settle(SocialAccountController.SIGNED_OUT)
 check(SocialAccountController.accountName()==""&&SecureAccountStore.saved==null)
 SocialAccountController.choose("facebook");settle(SocialAccountController.BROWSER)
 val facebook=MIDlet.cnA.launched!!
 check(facebook.target==null&&facebook.action==Intent.ACTION_VIEW&&facebook.uri.toString().contains("/auth/browser/start?id="))
 check(!SocialAccountController.recordEmbeddedCallback(ctx,SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"))
 check(SocialAccountController.recordCallback(ctx,SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"))
 settle(SocialAccountController.READY)
 check(SocialAccountController.gameId()=="GF000001"&&SocialAccountController.accountName()=="Player")
 check(!Log.joined().contains(state)&&!Log.joined().contains(code)&&!Log.joined().contains(verifier))
 println("PASS production controller: private embedded launch, official external Facebook, bound callbacks and no credential Intents/logs")
}
'''


class EmbeddedAccountControllerTest(unittest.TestCase):
    def test_private_launch_and_callback_boundary(self):
        java = Path(os.environ.get('JAVA_HOME', '/workspace/.tools/jdk-25')) / 'bin/java'
        if not java.exists():
            self.skipTest('Requires JDK and Kotlin compiler')
        with tempfile.TemporaryDirectory(prefix='embedded-controller-') as temp:
            path = Path(temp)
            java_sources = []
            for filename, contents in JAVA.items():
                file = path / filename
                file.parent.mkdir(parents=True, exist_ok=True)
                file.write_text(contents)
                java_sources.append(file)
            peers, harness = path / 'Peers.kt', path / 'ControllerChecks.kt'
            peers.write_text(PEERS)
            harness.write_text(HARNESS)
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480'
            classes = path / 'classes'
            compile_mixed([source / (name + '.kt') for name in ('SocialAccountFlow', 'SocialAccountController', 'AuthWebViewPolicy')]
                          + [peers, harness], java_sources, classes)
            result = subprocess.run([str(java), '-cp', runtime_classpath(classes), 'ControllerChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS production controller', result.stdout)
        manifest = ET.parse(ROOT / 'app/src/main/AndroidManifest.xml')
        attr = '{http://schemas.android.com/apk/res/android}'
        activity = next(item for item in manifest.findall('./application/activity')
                        if item.get(attr + 'name', '').endswith('.EmbeddedAccountActivity'))
        self.assertEqual(activity.get(attr + 'exported'), 'false')
        self.assertEqual(activity.findall('intent-filter'), [])


if __name__ == '__main__':
    unittest.main()
