"""Exercise the production account state machine without touching a game database."""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.*
import java.util.ArrayDeque

class Vault : SocialAccountFlow.Vault {
 var saved:SocialSession?=null;var pending:SocialAttempt?=null;var returned:SocialCallback?=null
 var writes=0;var clears=0;var attemptRead:(()->Unit)?=null
 override fun session()=saved
 override fun saveSession(value:SocialSession){saved=value;writes++}
 override fun clearSession(){saved=null;clears++}
 override fun attempt():SocialAttempt? {attemptRead?.invoke();return pending}
 override fun saveAttempt(value:SocialAttempt){pending=value}
 override fun callback()=returned
 override fun saveCallback(value:SocialCallback){returned=value}
 override fun clearAttempt(){pending=null;returned=null}
}
class Platform:SocialAccountFlow.Platform {
 var time=1_700_000_000_000L;var random=0;var opened=0;var browsers=0;val jobs=ArrayDeque<()->Unit>()
 override fun now()=time
 override fun randomBytes(size:Int)=ByteArray(size){(++random).toByte()}
 override fun execute(work:()->Unit){jobs.add(work)}
 override fun openBrowser(url:String){check(url.startsWith("https://auth.example/auth/browser/start?id=")||url.startsWith("https://auth.example/auth/web/login?attempt="));opened++;browsers++}
}
class Backend(val platform:Platform):SocialAccountFlow.Backend {
 var attempts=0;var exchanges=0;var checks=0;var refreshes=0;var revokes=0
 var user=SocialIdentity("GF000001","password","Player")
 var attemptFailure:String?=null;var checkFailure:String?=null;var refreshFailure:String?=null;var exchangeFailure:String?=null
 val attemptProviders=mutableListOf<String>()
 var offlineLogout=false;var attemptAction:(()->Unit)?=null;var exchangeAction:(()->Unit)?=null;var checkAction:(()->Unit)?=null
 var lastState="";var lastChallenge="";var lastVerifier=""
 fun session(token:String="new-access")=SocialSession(token,"new-refresh",platform.time+900_000,platform.time+2_592_000_000,user)
 override fun attempt(provider:String,state:String,challenge:String):SocialAttemptReply{
  attempts++;attemptProviders.add(provider);lastState=state;lastChallenge=challenge;attemptAction?.invoke();attemptFailure?.let{throw SocialAuthFailure(it)}
  return SocialAttemptReply("https://auth.example/"+(if(provider=="password")"auth/web/login?attempt=" else "auth/browser/start?id=")+"a".repeat(43),300)
 }
 override fun exchange(code:String,verifier:String):SocialSession{
  exchanges++;lastVerifier=verifier;exchangeAction?.invoke();exchangeFailure?.let{throw SocialAuthFailure(it)}
  check(code=="c".repeat(43));return session()
 }
 override fun me(accessToken:String):SocialIdentity{
  checks++;checkAction?.invoke();checkFailure?.let{val code=it;checkFailure=null;throw SocialAuthFailure(code)};return user
 }
 override fun refresh(refreshToken:String):SocialSession{
  refreshes++;refreshFailure?.let{throw SocialAuthFailure(it)};return session("refreshed-access")
 }
 override fun logout(accessToken:String,refreshToken:String){revokes++;if(offlineLogout)throw SocialAuthFailure("network_error")}
}
fun pump(flow:SocialAccountFlow,p:Platform){
 for(n in 0..30){while(!p.jobs.isEmpty())p.jobs.removeFirst().invoke();flow.tick();if(p.jobs.isEmpty())return}
 error("flow did not settle")
}
fun complete(flow:SocialAccountFlow,v:Vault,p:Platform){
 val pending=v.pending!!;v.returned=SocialCallback(pending.state,"c".repeat(43),null)
 flow.callbackAvailable();pump(flow,p)
}
fun main(){
 check(SocialAccountFlow.challenge("dBjftJeZ4CVP-mB92K27uhbUJU1p1r_wW1gFWFOEjXk")=="E9Melhoa2OwvFrEMTJguCHaoeK1t8URWbuGJSstw-cM")
 val state="s".repeat(43);val code="c".repeat(43)
 val callback=SocialAccountFlow.REDIRECT_URI+"?state=$state&code=$code"
 check(SocialAccountFlow.parseCallback(callback)==SocialCallback(state,code,null))
 for(bad in listOf(callback+"&access_token=secret",callback+"&state=$state",callback+"#fragment",
   callback.replace("oauth/","user@oauth/"),callback.replace("com.quanhoang.greenfarm3:","https:"),
   callback.replace("state=$state","state=short"),callback.replace("code=$code","code=bad%0Avalue"),
   callback+"&error=access_denied",SocialAccountFlow.REDIRECT_URI+"?code=$code")){
  check(SocialAccountFlow.parseCallback(bad)==null){"accepted invalid callback"}
 }
 check(SocialAccountFlow.parseCallback(SocialAccountFlow.REDIRECT_URI+"?state=$state&error=access_denied")!!.error=="access_denied")
 check(SocialAccountFlow.sameState(state,state)&&!SocialAccountFlow.sameState(state,"x".repeat(43)))
 check(!SocialAccountFlow.messageFor("auth_not_configured").contains("Facebook"))
 check(!SocialAccountFlow.messageFor("oauth_not_configured").contains("Facebook"))
 check(SocialAccountFlow.messageFor("database_unavailable").contains("Máy chủ"))
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);b.attemptFailure="oauth_not_configured";f.choose("facebook");pump(f,p)
  check(f.status==SocialAccountFlow.ERROR)
  b.attemptFailure=null;f.choose("password");pump(f,p)
  check(b.attemptProviders==listOf("facebook","password")&&p.browsers==1)
  complete(f,v,p);check(f.status==SocialAccountFlow.WELCOME&&f.accountName=="Player")
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);b.attemptFailure="auth_not_configured";f.choose("password");pump(f,p)
  check(f.status==SocialAccountFlow.ERROR&&b.attemptProviders==listOf("password")&&p.browsers==0)
  check(f.accountName==""&&!f.message.contains("Facebook")){"Website configuration errors must not demand a Facebook token/app"}
  b.attemptFailure=null;f.choose("password");pump(f,p);complete(f,v,p)
  check(f.status==SocialAccountFlow.WELCOME&&f.accountName=="Player"&&p.browsers==1)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  check(f.accountName=="");check(f.startupGate());pump(f,p);check(f.status==SocialAccountFlow.SIGNED_OUT&&b.checks==0&&f.accountName=="")
  f.choose("password");f.choose("facebook");pump(f,p)
  check(b.attempts==1&&b.attemptProviders==listOf("password")&&p.opened==1&&p.browsers==1&&f.status==SocialAccountFlow.BROWSER&&f.accountName=="")
  check(v.pending!!.state.length==43&&v.pending!!.verifier.length==43&&b.lastChallenge==SocialAccountFlow.challenge(v.pending!!.verifier))
  val verifier=v.pending!!.verifier;complete(f,v,p)
  check(f.status==SocialAccountFlow.WELCOME&&f.needsGate&&f.accountName=="Player")
  f.continueSaved();pump(f,p)
  check(f.status==SocialAccountFlow.READY&&!f.needsGate&&f.gameId=="GF000001"&&v.saved!=null&&v.pending==null&&v.returned==null)
  check(b.exchanges==1&&b.lastVerifier==verifier&&f.accountName=="Player")
  check(!v.saved.toString().contains("new-access")&&!v.saved.toString().contains("new-refresh"))
  check(!SocialAttempt("google",state,verifier,"https://auth.example/?private",p.time).toString().contains(verifier))
  check(!SocialCallback(state,code,null).toString().contains(code))
  f.callbackAvailable();pump(f,p);check(b.exchanges==1)
  val reopened=SocialAccountFlow(v,b,p);check(reopened.accountName=="");check(reopened.startupGate());check(reopened.accountName=="")
  b.user=b.user.copy(displayName="Updated Website Name");pump(reopened,p)
  check(reopened.status==SocialAccountFlow.WELCOME&&reopened.needsGate&&reopened.gameId=="GF000001"&&reopened.accountName=="Updated Website Name")
  check(v.saved!!.user.displayName=="Updated Website Name"){"Use the verified /me name rather than the cached name"}
  reopened.continueSaved();check(reopened.accountName=="");pump(reopened,p);check(reopened.status==SocialAccountFlow.READY&&b.checks==3&&reopened.accountName=="Updated Website Name")
  b.offlineLogout=true;reopened.logout();pump(reopened,p)
  check(reopened.status==SocialAccountFlow.SIGNED_OUT&&v.saved==null&&v.clears==1&&b.revokes==1&&reopened.accountName=="")
  b.user=SocialIdentity("GF000002","facebook","Other");reopened.choose("facebook");pump(reopened,p);complete(reopened,v,p)
  check(reopened.gameId=="GF000002"&&v.saved!!.user.gameId=="GF000002"&&reopened.accountName=="Other")
 }
 run{
  // Process death after leaving the app: encrypted vault is represented by shared records.
  val v=Vault();val p=Platform();val b=Backend(p);val first=SocialAccountFlow(v,b,p)
  first.startupGate();pump(first,p);first.choose("password");pump(first,p)
  v.returned=SocialCallback(v.pending!!.state,code,null)
  val recovered=SocialAccountFlow(v,b,p);recovered.startupGate();pump(recovered,p)
  check(recovered.status==SocialAccountFlow.WELCOME&&recovered.accountName=="Player"&&b.exchanges==1)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);f.choose("password");pump(f,p)
  v.returned=SocialCallback("wrong".repeat(9),code,null);f.callbackAvailable();pump(f,p)
  check(f.status==SocialAccountFlow.ERROR&&b.exchanges==0&&v.saved==null)
  f.choose("password");pump(f,p);v.returned=SocialCallback(v.pending!!.state,null,"access_denied");f.callbackAvailable();pump(f,p)
  check(f.status==SocialAccountFlow.ERROR&&b.exchanges==0&&v.pending==null)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);f.choose("facebook");pump(f,p)
  f.onHostPaused();f.onHostResumed();p.time+=2001;f.tick()
  check(f.status==SocialAccountFlow.ERROR&&v.pending==null)
  f.choose("password");pump(f,p);p.time+=300001;f.tick()
  check(f.status==SocialAccountFlow.ERROR&&v.pending==null&&b.exchanges==0)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);v.saved=b.session().copy(accessExpiresAt=p.time-1)
  val f=SocialAccountFlow(v,b,p);f.startupGate();pump(f,p)
  check(f.status==SocialAccountFlow.WELCOME&&b.refreshes==1&&v.saved!!.accessToken=="refreshed-access")
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);v.saved=b.session();b.checkFailure="invalid_session"
  val f=SocialAccountFlow(v,b,p);f.startupGate();pump(f,p)
  check(f.status==SocialAccountFlow.WELCOME&&b.refreshes==1&&b.checks==2)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);v.saved=b.session();b.checkFailure="network_error"
  val f=SocialAccountFlow(v,b,p);f.startupGate();pump(f,p)
  check(f.status==SocialAccountFlow.ERROR&&f.needsGate&&b.refreshes==0&&f.accountName=="")
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);v.saved=b.session();b.user=SocialIdentity("GF000999","google","Wrong")
  val f=SocialAccountFlow(v,b,p);f.startupGate();pump(f,p);check(f.status==SocialAccountFlow.ERROR&&f.needsGate)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);b.attemptAction={f.cancel()};f.choose("password");pump(f,p)
  check(f.status==SocialAccountFlow.SIGNED_OUT&&v.pending==null&&v.saved==null&&p.opened==0)
  b.attemptAction=null;f.choose("password");pump(f,p);b.exchangeAction={f.cancel()};complete(f,v,p)
  check(f.status==SocialAccountFlow.SIGNED_OUT&&v.saved==null&&b.revokes==1)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();p.time+=60001;f.tick();check(f.status==SocialAccountFlow.ERROR&&f.needsGate)
  pump(f,p);check(f.status==SocialAccountFlow.ERROR&&b.checks==0&&v.saved==null)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);f.choose("password");pump(f,p)
  v.returned=SocialCallback(v.pending!!.state,code,null);f.callbackAvailable();p.time+=60001;f.tick();pump(f,p)
  check(f.status==SocialAccountFlow.ERROR&&v.saved==null&&b.exchanges==0)
  f.choose("password");pump(f,p);b.exchangeAction={p.time+=60001};complete(f,v,p)
  check(f.status==SocialAccountFlow.ERROR&&v.saved==null&&b.revokes==1)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);v.saved=b.session()
  val f=SocialAccountFlow(v,b,p);f.startupGate();pump(f,p);check(f.status==SocialAccountFlow.WELCOME)
  b.checkAction={p.time+=60001};f.continueSaved();pump(f,p)
  check(f.status==SocialAccountFlow.ERROR&&f.needsGate)
  f.logout();p.time+=60001;f.tick();pump(f,p)
  check(f.status==SocialAccountFlow.SIGNED_OUT&&v.saved==null&&f.needsGate)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);f.choose("google");pump(f,p)
  check(b.attempts==0&&f.status==SocialAccountFlow.SIGNED_OUT){"Removed Google button must not start OAuth"}
  f.choose("password");pump(f,p)
  val restored=SocialAccountFlow(v,b,p);restored.startupGate();pump(restored,p)
  check(restored.status==SocialAccountFlow.BROWSER&&p.browsers==1){"Pending recovery must not reopen the browser"}
  restored.onHostPaused();restored.onHostResumed();p.time+=2001;restored.tick()
  check(restored.status==SocialAccountFlow.ERROR&&v.pending==null){"Returning without Website auth must be cancellable"}
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);f.choose("password");pump(f,p)
  b.user=SocialIdentity("GF000001","google","Mismatch");complete(f,v,p)
  check(f.status==SocialAccountFlow.ERROR&&v.saved==null&&b.revokes==1){"Exchange must match the pending authentication method"}
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);b.user=SocialIdentity("GF000009","google","Existing Google")
  v.saved=b.session();val f=SocialAccountFlow(v,b,p);f.startupGate();pump(f,p)
  check(f.status==SocialAccountFlow.WELCOME&&f.gameId=="GF000009"){"Existing Google identities and sessions remain readable"}
  f.continueSaved();pump(f,p);check(f.status==SocialAccountFlow.READY)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val original=SocialAccountFlow(v,b,p)
  original.startupGate();pump(original,p);original.choose("password");pump(original,p)
  val recovered=SocialAccountFlow(v,b,p);recovered.startupGate()
  v.attemptRead={
   v.attemptRead=null
   v.returned=SocialCallback(v.pending!!.state,code,null)
   recovered.enqueueCallback() // Queued while CHECKING, before recovery publishes BROWSER.
  }
  p.jobs.removeFirst().invoke();recovered.tick();pump(recovered,p)
  check(recovered.status==SocialAccountFlow.WELCOME&&b.exchanges==1&&v.returned==null)
 }
 run{
  val v=Vault();val p=Platform();val b=Backend(p);val f=SocialAccountFlow(v,b,p)
  f.startupGate();pump(f,p);f.choose("password");pump(f,p)
  f.onHostPaused();f.onHostResumed();p.time+=2001
  v.returned=SocialCallback(v.pending!!.state,code,null);f.enqueueCallback();f.tick();pump(f,p)
  check(f.status==SocialAccountFlow.WELCOME&&b.exchanges==1){"A persisted callback wins over the return timer"}
 }
 println("PASS production social account flow: PKCE/state/callback validation, browser cancellation/timeouts, process recovery, welcome/refresh/logout and account isolation")
}
'''


class SocialAccountFlowTest(unittest.TestCase):
    def test_verified_sessions_callbacks_and_lifecycle_without_game_database(self):
        java = Path(os.environ.get('JAVA_HOME', '/workspace/.tools/jdk-25')) / 'bin/java'
        if not java.exists():
            self.skipTest('Requires JDK')
        with tempfile.TemporaryDirectory(prefix='social-flow-') as temporary:
            directory = Path(temporary)
            harness = directory / 'SocialAccountChecks.kt'
            harness.write_text(HARNESS)
            classes = directory / 'classes'
            compile_mixed([ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/SocialAccountFlow.kt', harness], [], classes)
            result = subprocess.run([str(java), '-cp', runtime_classpath(classes), 'SocialAccountChecksKt'],
                                    capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS production social account flow', result.stdout)


if __name__ == '__main__':
    unittest.main()
