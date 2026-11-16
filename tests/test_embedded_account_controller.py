"""Run the production callback/launch adapter with in-memory Android/vault peers.

This verifies immediate overlay ownership, duplicate taps and callback boundaries. It does
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
        public android.content.Intent launched; public boolean isFinishing(){return false;} public boolean isDestroyed(){return false;}
        public boolean queueUi=false;
        public final java.util.ArrayDeque<Runnable> uiJobs=new java.util.ArrayDeque<>();
        public void runOnUiThread(Runnable r){if(queueUi)uiJobs.add(r);else r.run();}
        public void drainUi(){while(!uiJobs.isEmpty())uiJobs.remove().run();}
        public void startActivity(android.content.Intent i){launched=i;}}''',
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
}

PEERS = r'''
package com.quanhoang.greenfarm3.S800x480
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
class EmbeddedAccountOverlay(val host:android.app.Activity) {
 val ownerActivity=host
 companion object {
  var constructions=0;var shows=0;var opens=0;var latest:EmbeddedAccountOverlay?=null
  var throwOnShow=false;var dismisses=0
 }
 var isShowing=false
 var url=""
 var expiresAt=0L
 private var dismissed:((Any?)->Unit)?=null
 init {constructions++;latest=this}
 fun show(){check(!isShowing);if(throwOnShow)error("fixture window unavailable");isShowing=true;shows++}
 fun open(url:String,expiresAt:Long){check(isShowing);this.url=url;this.expiresAt=expiresAt;opens++}
 fun setOnDismissListener(listener:(Any?)->Unit){dismissed=listener}
 fun dismiss(){isShowing=false;dismisses++;dismissed?.invoke(null)}
}
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
 companion object {
  @Volatile var attempts=0
  @Volatile var blockAttempts=false
  var attemptGate=CountDownLatch(1)
  fun validateOrigin(base:String):String {check(base=="https://auth.example/");return base}
 }
 private var provider="password"
 override fun attempt(provider:String,state:String,challenge:String):SocialAttemptReply {
  this.provider=provider
  attempts++
  if(blockAttempts) check(attemptGate.await(5,TimeUnit.SECONDS)){"attempt fixture was never released"}
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
 EmbeddedAccountOverlay.throwOnShow=true
 val attemptsBeforeFailure=SocialAuthClient.attempts
 SocialAccountController.choose("password")
 check(SocialAccountController.status()==SocialAccountController.ERROR){"Failed overlay did not report an account error"}
 check(SocialAuthClient.attempts==attemptsBeforeFailure&&SecureAccountStore.pending==null){"Failed overlay started an auth request"}
 check(MIDlet.cnA.launched==null){"Failed overlay escaped into an external Activity"}
 check(!EmbeddedAccountOverlay.latest!!.isShowing&&EmbeddedAccountOverlay.dismisses==1){"Failed overlay retained a stale window"}
 EmbeddedAccountOverlay.throwOnShow=false
 val constructionsBeforeRetry=EmbeddedAccountOverlay.constructions
 SocialAuthClient.blockAttempts=true
 SocialAccountController.choose("password")
 val embedded=EmbeddedAccountOverlay.latest?:error("Website tap did not show an overlay immediately")
 check(embedded.host===MIDlet.cnA&&embedded.isShowing&&EmbeddedAccountOverlay.shows==1)
 check(MIDlet.cnA.launched==null){"Password login launched an Activity or external browser"}
 repeat(10){SocialAccountController.choose("password")}
 check(EmbeddedAccountOverlay.constructions==constructionsBeforeRetry+1&&EmbeddedAccountOverlay.shows==1){"Repeated taps stacked overlays"}
 check(EmbeddedAccountOverlay.opens==0){"Attempt-dependent form opened before its secure descriptor existed"}
 SocialAuthClient.blockAttempts=false
 SocialAuthClient.attemptGate.countDown()
 settle(SocialAccountController.BROWSER)
 check(EmbeddedAccountOverlay.latest===embedded&&EmbeddedAccountOverlay.opens==1)
 check(embedded.url==url&&embedded.expiresAt>System.currentTimeMillis())
 val descriptor=SocialAccountController.embeddedPage(ctx)!!
 check(descriptor.first==url&&!descriptor.toString().contains(SecureAccountStore.pending!!.verifier))
 SocialAccountController.closeEmbedded(ctx,url+"x");check(SocialAccountController.status()==SocialAccountController.BROWSER)
 SocialAccountController.closeEmbedded(ctx,url);check(SocialAccountController.status()==SocialAccountController.SIGNED_OUT&&SecureAccountStore.pending==null)
 embedded.dismiss()
 // Queue the Android launch after the secure descriptor is prepared, then cancel
 // before the UI thread handles it. A stale callback must not recreate a window.
 SocialAccountController.choose("password")
 MIDlet.cnA.queueUi=true
 val opensBeforeCancel=EmbeddedAccountOverlay.opens
 settle(SocialAccountController.BROWSER)
 check(MIDlet.cnA.uiJobs.size==1&&EmbeddedAccountOverlay.opens==opensBeforeCancel)
 SocialAccountController.cancel()
 EmbeddedAccountOverlay.latest!!.dismiss()
 val windowsBeforeDrain=EmbeddedAccountOverlay.constructions
 MIDlet.cnA.queueUi=false
 MIDlet.cnA.drainUi()
 check(SocialAccountController.status()==SocialAccountController.SIGNED_OUT&&SecureAccountStore.pending==null)
 check(EmbeddedAccountOverlay.opens==opensBeforeCancel&&EmbeddedAccountOverlay.constructions==windowsBeforeDrain){"Canceled queued launch reopened Website login"}
 SocialAccountController.choose("password");settle(SocialAccountController.BROWSER)
 check(MIDlet.cnA.launched==null&&EmbeddedAccountOverlay.latest!!.isShowing&&SocialAccountController.accountName()=="")
 val ownCallback=SocialAccountFlow.REDIRECT_URI+"?state="+SecureAccountStore.pending!!.state+"&code=$code"
 check(!SocialAccountController.recordCallback(ctx,ownCallback))
 check(SocialAccountController.recordEmbeddedCallback(ctx,ownCallback));settle(SocialAccountController.READY)
 check(SocialAccountController.accountName()=="Player"&&SocialAccountController.gameId()=="GF000001")
 EmbeddedAccountOverlay.latest!!.dismiss()
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
 println("PASS production controller: immediate single game overlay, failed-show recovery, canceled-launch suppression, official external Facebook, bound callbacks and no credential Intents/logs")
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
        activities = [item.get(attr + 'name', '') for item in manifest.findall('./application/activity')]
        self.assertFalse(any(name.endswith('.EmbeddedAccountActivity') for name in activities),
                         'Website overlay must remain in the host game Activity')


if __name__ == '__main__':
    unittest.main()
