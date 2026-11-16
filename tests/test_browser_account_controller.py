"""Run the production callback/launch adapter with in-memory Android/vault peers.

This verifies validated browser callbacks, cold recovery and warm Intent ownership.
It does not replace Android Keystore or an installed-device task/lifecycle test.
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
        public android.content.Intent launched; private android.content.Intent incoming;
        public int finishes; public boolean destroyed, failLaunch;
        public boolean isFinishing(){return finishes>0;} public boolean isDestroyed(){return destroyed;}
        public void onCreate(android.os.Bundle b){} public void onNewIntent(android.content.Intent i){}
        public android.content.Intent getIntent(){return incoming;} public void setIntent(android.content.Intent i){incoming=i;}
        public void finish(){finishes++;} public void runOnUiThread(Runnable r){r.run();}
        public void startActivity(android.content.Intent i){if(failLaunch)throw new RuntimeException("no browser");launched=i;}}''',
    'android/content/Intent.java': '''package android.content; public class Intent {
        public static final String ACTION_VIEW="android.intent.action.VIEW",CATEGORY_BROWSABLE="android.intent.category.BROWSABLE";
        public static final int FLAG_ACTIVITY_NEW_TASK=0x10000000,FLAG_ACTIVITY_CLEAR_TOP=0x04000000,
          FLAG_ACTIVITY_SINGLE_TOP=0x20000000,FLAG_ACTIVITY_REORDER_TO_FRONT=0x00020000;
        public String action; public android.net.Uri uri; public Class<?> target; public int flags;
        public android.net.Uri getData(){return uri;} public Intent addFlags(int f){flags|=f;return this;}
        public Intent(Context c,Class<?> t){target=t;} public Intent(String a,android.net.Uri u){action=a;uri=u;}
        public Intent addCategory(String c){return this;}}''',
    'android/net/Uri.java': '''package android.net; public class Uri {private final String value;
        private Uri(String s){value=s;} public static Uri parse(String s){return new Uri(s);}
        public String toString(){return value;}}''',
    'android/os/Bundle.java': 'package android.os; public class Bundle {}',
    'com/quanhoang/greenfarm3/Start.java': 'package com.quanhoang.greenfarm3; public class Start extends android.app.Activity {}',
    'android/os/Build.java': '''package android.os; public class Build {public static class VERSION {public static int SDK_INT=28;}}''',
    'android/util/Log.java': LOG,
    'javax/microedition/midlet/MIDlet.java': '''package javax.microedition.midlet; public class MIDlet extends android.app.Activity {
        public static MIDlet cnz; public static android.app.Activity cnA=new android.app.Activity();}''',
    'com/quanhoang/greenfarm3/S800x480/LocalDevelopmentConfig.java': '''package com.quanhoang.greenfarm3.S800x480;
        public class LocalDevelopmentConfig {public static final String AUTH_BASE_URL="https://auth.example/";}''',
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
import com.quanhoang.greenfarm3.Start
import javax.microedition.midlet.MIDlet
import android.content.Intent
import android.net.Uri
import android.util.Log

class LiveFarmActivity:MIDlet()
fun settle(expected:Int){
 val until=System.nanoTime()+5_000_000_000L
 while(System.nanoTime()<until){SocialAccountController.tick();if(SocialAccountController.status()==expected)return;Thread.sleep(5)}
 error("Unexpected adapter status ${SocialAccountController.status()}, expected $expected")
}
fun receive(uri:String):SocialAuthCallbackActivity {
 val receiver=SocialAuthCallbackActivity()
 receiver.intent=Intent(Intent.ACTION_VIEW,Uri.parse(uri));receiver.onCreate(null)
 return receiver
}
fun checkedReturn(receiver:SocialAuthCallbackActivity,target:Class<*>) {
 check(receiver.finishes==1&&receiver.launched!!.target==target)
 check(receiver.launched!!.flags and Intent.FLAG_ACTIVITY_CLEAR_TOP==0){"Callback must not destroy gameplay above launcher"}
 check(receiver.launched!!.flags and Intent.FLAG_ACTIVITY_REORDER_TO_FRONT!=0)
 check(receiver.launched!!.uri==null){"Codes and credentials stay out of host Intents"}
}
fun main(){
 val ctx=android.content.Context();val state="s".repeat(43);val verifier="v".repeat(43);val code="c".repeat(43)
 val url="https://auth.example/auth/web/login?attempt="+"a".repeat(43)
 val cb=SocialAccountFlow.REDIRECT_URI+"?state=$state&code=$code"
 check(SocialAccountController.accountName()=="")
 SecureAccountStore.pending=SocialAttempt("password",state,verifier,url,System.currentTimeMillis()+300000)
 check(!SocialAccountController.recordCallback(ctx,cb.replace(state,"x".repeat(43))))
 check(!SocialAccountController.recordCallback(ctx,cb+"&access_token=private"))
 check(!SocialAccountController.recordCallback(ctx,cb+"&game_id=GF000999&display_name=Forged"))
 check(SecureAccountStore.returned==null)
 val rejected=receive(cb+"&state=duplicate");check(rejected.finishes==1&&rejected.launched==null)
 // Cold return: record must survive before the account flow/platform adapter exists.
 MIDlet.cnA=null;MIDlet.cnz=null
 checkedReturn(receive(cb),Start::class.java)
 check(SecureAccountStore.returned==SocialCallback(state,code,null)&&SecureAccountStore.saved==null)
 SocialAccountController.initialize(ctx,"https://auth.example/")
 SocialAccountController.startupGate();settle(SocialAccountController.WELCOME)
 check(SocialAccountController.accountName()=="Player"&&SocialAccountController.gameId()=="GF000001")
 SocialAccountController.continueSaved();settle(SocialAccountController.READY)
 check(!SocialAccountController.recordCallback(ctx,cb)){"Used attempt cannot be replayed"}
 SocialAccountController.logout();settle(SocialAccountController.SIGNED_OUT)
 check(SecureAccountStore.saved==null&&SocialAccountController.accountName()=="")
 val host=LiveFarmActivity();val launcher=Start();MIDlet.cnA=launcher;MIDlet.cnz=host
 SocialAccountController.choose("password");settle(SocialAccountController.BROWSER)
 val browser=host.launched!!
 check(browser.target==null&&browser.action==Intent.ACTION_VIEW&&browser.uri.toString()==url)
 check(launcher.launched==null){"The live MIDlet, not the stale launcher, must own browser launch"}
 check(SocialAccountController.accountName()==""&&SecureAccountStore.saved==null)
 val ownCallback=SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"
 // cnA still points to Start; cnz points to gameplay. Warm return must prefer cnz.
 val warm=receive(ownCallback);checkedReturn(warm,LiveFarmActivity::class.java)
 warm.onNewIntent(Intent(Intent.ACTION_VIEW,Uri.parse(ownCallback)))
 settle(SocialAccountController.WELCOME)
 check(SocialAccountController.accountName()=="Player"&&SocialAccountController.gameId()=="GF000001")
 SocialAccountController.continueSaved();settle(SocialAccountController.READY)
 SocialAccountController.logout();settle(SocialAccountController.SIGNED_OUT)
 SocialAccountController.choose("password");settle(SocialAccountController.BROWSER)
 SecureAccountStore.pending=SecureAccountStore.pending!!.copy(expiresAt=System.currentTimeMillis()-1)
 check(!SocialAccountController.recordCallback(ctx,SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"))
 SocialAccountController.cancel()
 host.failLaunch=true;SocialAccountController.choose("password");settle(SocialAccountController.ERROR)
 check(SecureAccountStore.saved==null&&SocialAccountController.accountName()=="")
 host.failLaunch=false;SocialAccountController.choose("facebook");settle(SocialAccountController.BROWSER)
 check(host.launched!!.action==Intent.ACTION_VIEW&&host.launched!!.uri.toString().contains("/auth/browser/start?id="))
 val facebookCallback=SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"
 check(SocialAccountController.recordCallback(ctx,facebookCallback));settle(SocialAccountController.READY)
 check(SocialAccountController.gameId()=="GF000001"&&SocialAccountController.accountName()=="Player")
 SocialAccountController.logout();settle(SocialAccountController.SIGNED_OUT)
 SocialAccountController.choose("password");settle(SocialAccountController.BROWSER)
 host.destroyed=true
 checkedReturn(receive(SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"),Start::class.java)
 settle(SocialAccountController.WELCOME)
 check(!Log.joined().contains(state)&&!Log.joined().contains(code)&&!Log.joined().contains(verifier))
 println("PASS production controller: external Website/Facebook, bound cold/warm callbacks, name/restore/logout and preserved gameplay Intent")
}
'''



class BrowserAccountControllerTest(unittest.TestCase):
    def test_external_launch_and_bound_cold_warm_callback(self):
        java = Path(os.environ.get('JAVA_HOME', '/workspace/.tools/jdk-25')) / 'bin/java'
        if not java.exists():
            self.skipTest('Requires JDK and Kotlin compiler')
        with tempfile.TemporaryDirectory(prefix='browser-controller-') as temp:
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
            compile_mixed([source / (name + '.kt') for name in ('SocialAccountFlow', 'SocialAccountController', 'SocialAuthCallbackActivity')]
                          + [peers, harness], java_sources, classes)
            result = subprocess.run([str(java), '-cp', runtime_classpath(classes), 'ControllerChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS production controller', result.stdout)
        manifest = ET.parse(ROOT / 'app/src/main/AndroidManifest.xml')
        attr = '{http://schemas.android.com/apk/res/android}'
        activities = manifest.findall('./application/activity')
        self.assertFalse(any(item.get(attr + 'name', '').endswith('.EmbeddedAccountActivity') for item in activities))
        activity = next(item for item in activities if item.get(attr + 'name', '').endswith('.SocialAuthCallbackActivity'))
        self.assertEqual(activity.get(attr + 'exported'), 'true')
        data = activity.find('./intent-filter/data')
        self.assertEqual((data.get(attr + 'scheme'), data.get(attr + 'host'), data.get(attr + 'path')),
                         ('com.quanhoang.greenfarm3', 'oauth', '/callback'))



if __name__ == '__main__':
    unittest.main()
