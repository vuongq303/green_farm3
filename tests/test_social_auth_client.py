"""Exercise the production HTTPS auth transport with bounded, deterministic replies.

Only TLS sockets/Android Log are peers. No real provider, credential, farm or
SQLite file is opened by this test.
"""
from pathlib import Path
import os
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from test_network_diagnostics import LOG

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.*
import java.io.*
import java.net.*
import java.security.cert.Certificate
import java.security.cert.X509Certificate
import javax.net.ssl.*
import org.json.JSONObject
import android.util.Log

data class Reply(val status:Int=200,val body:String="{}",val type:String="application/json",val failure:IOException?=null,val location:String?=null,val afterRead:(()->Unit)?=null)
class Connection(url:URL,val reply:Reply):HttpsURLConnection(url){
 val sent=ByteArrayOutputStream();var disconnects=0;var responses=0;var reads=0;var bodyReads=0
 override fun connect(){};override fun usingProxy()=false;override fun disconnect(){disconnects++}
 override fun getCipherSuite()="TLS_AES_128_GCM_SHA256"
 override fun getLocalCertificates():Array<Certificate>?=null
 override fun getServerCertificates():Array<Certificate> = emptyArray()
 override fun getOutputStream():OutputStream=sent
 override fun getResponseCode():Int{responses++;reply.failure?.let{throw it};return reply.status}
 override fun getContentType()=reply.type
 override fun getInputStream():InputStream{reads++;return object:ByteArrayInputStream(reply.body.toByteArray(Charsets.UTF_8)){
  override fun read(bytes:ByteArray,offset:Int,length:Int):Int{
   val count=super.read(bytes,offset,if(reply.afterRead==null)length else minOf(length,1))
   bodyReads++;reply.afterRead?.invoke();return count
  }
 }}
 override fun getErrorStream():InputStream=ByteArrayInputStream(reply.body.toByteArray(Charsets.UTF_8))
 override fun getHeaderField(name:String):String?=if(name=="Location")reply.location else null
}
object Transport{
 var reply=Reply();var last:Connection?=null;var opens=0
 fun install(){URL.setURLStreamHandlerFactory{protocol->if(protocol=="https")object:URLStreamHandler(){
  override fun openConnection(url:URL):URLConnection{opens++;return Connection(url,reply).also{last=it}}
 }else null}}
}
fun expect(code:String,work:()->Unit){
 try{work();error("Expected $code")}catch(f:SocialAuthFailure){check(f.code==code){"Expected $code, got ${f.code}"}}
}
val secret="PRIVATE-CREDENTIAL-"+"x".repeat(36)
fun user(id:String="GF000001",provider:String="google")=JSONObject().put("game_id",id).put("provider",provider).put("display_name","Player")
fun tokens(id:String="GF000001")=JSONObject().put("access_token",secret).put("refresh_token",secret+"r")
 .put("expires_in",900).put("refresh_expires_in",2592000).put("user",user(id)).toString()
fun clean(reply:Reply){Transport.reply=reply;Log.entries.clear()}
fun secureConnection(compromised:SSLSocketFactory,method:String,path:String){
 val c=Transport.last!!
 check(c.url.toString()=="https://auth.example/$path")
 check(c.requestMethod==method&&c.connectTimeout==10000&&c.readTimeout==15000)
 check(!c.instanceFollowRedirects&&!c.useCaches&&c.disconnects==1&&c.responses==1)
 check(c.sslSocketFactory!==compromised){"Auth inherited legacy permissive TLS factory"}
 check(c.getRequestProperty("Accept")=="application/json")
}
fun main(){
 Transport.install()
 val insecure=SSLContext.getInstance("TLS").apply{init(null,arrayOf(object:X509TrustManager{
  override fun getAcceptedIssuers():Array<X509Certificate> = emptyArray()
  override fun checkClientTrusted(c:Array<out X509Certificate>?,a:String?){}
  override fun checkServerTrusted(c:Array<out X509Certificate>?,a:String?){}
 }),java.security.SecureRandom())}.socketFactory
 HttpsURLConnection.setDefaultSSLSocketFactory(insecure)
 for(bad in listOf("", "http://auth.example/", " https://auth.example/", "https://user:pass@auth.example/",
  "https://auth.example/path", "https://auth.example/?x=y", "https://auth.example/#bad", "https://auth.example:0/")){
  expect("configuration_required"){SocialAuthClient(bad)}
 }
 val client=SocialAuthClient("https://auth.example/"){123456L}
 val state="s".repeat(43);val verifier="v".repeat(43);val challenge=SocialAccountFlow.challenge(verifier)
 clean(Reply(201,JSONObject().put("authorization_url","https://auth.example/auth/browser/start?id="+"a".repeat(43)).put("expires_in",300).toString()))
 val attempt=client.attempt("google",state,challenge)
 check(attempt.expiresIn==300L&&attempt.authorizationUrl.startsWith("https://auth.example/"))
 secureConnection(insecure,"POST","auth/attempt")
 val start=JSONObject(Transport.last!!.sent.toString("UTF-8"))
 check(start.getString("provider")=="google"&&start.getString("state")==state&&start.getString("code_challenge")==challenge)
 check(start.getString("redirect_uri")==SocialAccountFlow.REDIRECT_URI)
 clean(Reply(201,JSONObject().put("authorization_url","https://evil.example/auth/browser/start?id="+"a".repeat(43)).put("expires_in",300).toString()))
 expect("invalid_response"){client.attempt("google",state,challenge)}
 // Explicit HTTPS443 and host casing are the same trusted origin.
 val explicit=SocialAuthClient("https://AUTH.example:443/")
 clean(Reply(201,JSONObject().put("authorization_url","https://auth.example/auth/browser/start?id="+"a".repeat(43)).put("expires_in",300).toString()))
 check(explicit.attempt("google",state,challenge).expiresIn==300L)
 clean(Reply(201,"{}"));expect("invalid_response"){client.attempt("google",state,challenge)}
 clean(Reply(200,tokens()))
 val session=client.exchange("c".repeat(43),verifier)
 check(session.user.gameId=="GF000001"&&session.user.provider=="google")
 check(session.accessExpiresAt==123456L+900000&&session.refreshExpiresAt==123456L+2592000000)
 secureConnection(insecure,"POST","auth/exchange")
 val exchange=JSONObject(Transport.last!!.sent.toString("UTF-8"))
 check(exchange.getString("code_verifier")==verifier&&exchange.getString("code")=="c".repeat(43))
 clean(Reply(200,JSONObject().put("user",user()).toString()));check(client.me(secret).gameId=="GF000001")
 secureConnection(insecure,"GET","auth/me")
 check(Transport.last!!.sent.size()==0&&Transport.last!!.getRequestProperty("Authorization")=="Bearer $secret")
 clean(Reply(200,"{}"));expect("invalid_response"){client.me(secret)}
 clean(Reply(200,tokens()));check(client.refresh(secret).accessToken==secret)
 secureConnection(insecure,"POST","auth/refresh")
 check(JSONObject(Transport.last!!.sent.toString("UTF-8")).getString("refresh_token")==secret)
 clean(Reply(204,"not-json"));client.logout(secret,secret+"r")
 secureConnection(insecure,"POST","auth/logout")
 check(Transport.last!!.getRequestProperty("Authorization")=="Bearer $secret"&&Transport.last!!.reads==0)
 for((status,code)in listOf(400 to "invalid_request",401 to "invalid_session",429 to "rate_limited",503 to "oauth_not_configured")){
  clean(Reply(status,JSONObject().put("error",JSONObject().put("code",code).put("message",secret)).toString()))
  expect(code){client.me(secret)};check(Transport.last!!.disconnects==1)
  check(!Log.joined().contains(secret)){"Auth log leaked a credential or server error body"}
 }
 clean(Reply(401,"not-json"));expect("invalid_session"){client.me(secret)}
 clean(Reply(429,"not-json"));expect("rate_limited"){client.me(secret)}
 val opens=Transport.opens
 clean(Reply(302,"",location="https://evil.example/steal?token=$secret"))
 expect("network_error"){client.me(secret)}
 check(Transport.opens==opens+1&&!Transport.last!!.instanceFollowRedirects){"Auth followed redirect with bearer credentials"}
 check(!Log.joined().contains(secret))
 clean(Reply(200,"x".repeat(65537)));expect("invalid_response"){client.me(secret)}
 clean(Reply(200,JSONObject().put("user",user()).toString(),"text/html"));expect("invalid_response"){client.me(secret)}
 clean(Reply(200,tokens().replace("GF000001","bad-id")));expect("invalid_response"){client.exchange("c".repeat(43),verifier)}
 clean(Reply(failure=SocketTimeoutException(secret)));expect("timeout"){client.me(secret)};check(!Log.joined().contains(secret))
 clean(Reply(failure=SSLHandshakeException(secret)));expect("tls_error"){client.me(secret)};check(!Log.joined().contains(secret))
 clean(Reply(failure=ConnectException(secret)));expect("network_error"){client.me(secret)};check(!Log.joined().contains(secret))
 // A peer can keep making progress inside each read timeout. The whole request
 // deadline must still terminate it, without sleeping or waiting for watchdogs.
 var nanos=0L
 val bounded=SocialAuthClient("https://auth.example/",operationTimeoutMillis=1000,monotonicNanos={nanos})
 clean(Reply(200,JSONObject().put("user",user()).toString(),afterRead={nanos+=400_000_000L}))
 expect("timeout"){bounded.me(secret)}
 check(Transport.last!!.bodyReads==3&&Transport.last!!.disconnects==1&&Transport.last!!.responses==1){"Slow response progress bypassed operation deadline"}
 check(Log.joined().contains("TIMEOUT")&&!Log.joined().contains(secret))
 println("PASS production social auth HTTPS client: dedicated CA trust, origin binding, API methods, bounded parsing, status/error mapping and no credential logs")
}
'''


class SocialAuthClientTest(unittest.TestCase):
    def test_https_transport_and_response_validation(self):
        java = Path(os.environ.get('JAVA_HOME', '/workspace/.tools/jdk-25')) / 'bin/java'
        cache = Path(os.environ.get('GRADLE_USER_HOME', '/workspace/.cache/gradle'))
        jars = list((cache / 'caches/modules-2/files-2.1/org.robolectric/android-all/9-robolectric-4913185-2').glob('*/*.jar'))
        if not java.exists() or not jars:
            self.skipTest('Requires JDK and cached Android JSON runtime')
        with tempfile.TemporaryDirectory(prefix='social-client-') as temporary:
            directory = Path(temporary)
            harness = directory / 'SocialAuthClientChecks.kt'
            harness.write_text(HARNESS)
            log = directory / 'android/util/Log.java'
            log.parent.mkdir(parents=True)
            log.write_text(LOG)
            source = ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480'
            classes = directory / 'classes'
            compile_mixed([source / (name + '.kt') for name in ('SocialAuthClient', 'SocialAccountFlow', 'NetworkDiagnostics')] + [harness],
                          [log], classes, classpath=[jars[0]])
            result = subprocess.run([str(java), '-cp', runtime_classpath(classes, jars[0]), 'SocialAuthClientChecksKt'],
                                    capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS production social auth HTTPS client', result.stdout)


if __name__ == '__main__':
    unittest.main()
