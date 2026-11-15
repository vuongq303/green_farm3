"""Exercise the production diagnostic wrappers without a network or Android UI.

The fake connections retain HTTP behavior and expose only deterministic outcomes;
the logger records exactly what the Kotlin helper sends to Android Logcat.
"""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import kotlin_source_directory, qualified_package
from kotlin_test_tools import compile_mixed, runtime_classpath


LOG = r'''package android.util;
import java.util.*;
public class Log {
 public static final List<String> entries=Collections.synchronizedList(new ArrayList<String>());
 public static boolean fail;
 public static int i(String tag,String text){if(fail)throw new IllegalStateException("log unavailable");entries.add("I "+tag+" "+text);return 0;}
 public static int d(String tag,String text){entries.add("D "+tag+" "+text);return 0;}
 public static int w(String tag,String text){entries.add("W "+tag+" "+text);return 0;}
 public static int e(String tag,String text){if(fail)throw new IllegalStateException("log unavailable");entries.add("E "+tag+" "+text);return 0;}
 public static int w(String tag,String text,Throwable error){throw new AssertionError("Raw Throwable logging can expose credentials");}
 public static int e(String tag,String text,Throwable error){throw new AssertionError("Raw Throwable logging can expose credentials");}
 public static String joined(){return String.join("\n",entries);}
}
'''


PROBE = r'''package PACKAGE;
import android.util.Log;
import java.io.*;
import java.net.*;
import javax.net.ssl.*;
public class NetworkDiagnosticsProbe {
 static final String SECRET="super-secret-token";
 static void check(boolean ok,String message){if(!ok)throw new AssertionError(message+"\n"+Log.joined());}
 static void clean(){Log.fail=false;Log.entries.clear();NetworkDiagnostics.begin();}
 static void privateLogs(){
  String text=Log.joined();
  for(String secret:new String[]{SECRET,"private-user","private-pass","private-client","private-name","private-profile","private-fragment","access_token="})
   check(!text.contains(secret),"Diagnostic leaked "+secret);
  for(String line:Log.entries)check(line.contains("GreenFarmNetwork")&&!line.contains("\n")&&!line.contains("\r"),"Unexpected tag or multiline log");
 }
 static final class Connection extends HttpURLConnection {
  int status=200,responseCalls,connectCalls,disconnectCalls,inputCalls;
  IOException responseFailure;String location;
  Connection(URL url){super(url);}
  public void connect(){connectCalls++;}public boolean usingProxy(){return false;}
  public void disconnect(){disconnectCalls++;}
  public int getResponseCode()throws IOException{responseCalls++;if(responseFailure!=null)throw responseFailure;return status;}
  public String getHeaderField(String name){return "Location".equals(name)?location:null;}
  public InputStream getInputStream(){inputCalls++;return new ByteArrayInputStream("payload".getBytes());}
 }
 static final class Handler extends URLStreamHandler {
  Connection last;int opens,proxyOpens;Proxy proxy;IOException openFailure;
  protected URLConnection openConnection(URL url)throws IOException{opens++;return make(url);}
  protected URLConnection openConnection(URL url,Proxy value)throws IOException{proxyOpens++;proxy=value;return make(url);}
  Connection make(URL url)throws IOException{if(openFailure!=null)throw openFailure;return last=new Connection(url);}
 }
 static URL url(Handler h,String host,String path)throws Exception{return new URL("http",host,8787,path,h);}
 static void safeUrls(){
  check(NetworkDiagnostics.safeUrl("http://private-user:private-pass@10.61.217.55:8787/authorize?access_token="+SECRET+"#private-fragment").equals("http://10.61.217.55:8787/authorize"),"Authentication URL sanitization");
  check(NetworkDiagnostics.safeUrl("https://example.invalid/health").equals("https://example.invalid:443/health"),"HTTPS default port absent");
  for(String path:new String[]{"/auth/web/login","/auth/web/register","/auth/login","/auth/register"})
   check(NetworkDiagnostics.safeUrl("https://example.invalid"+path+"?attempt="+SECRET+"&csrf="+SECRET).equals("https://example.invalid:443"+path),"Embedded account route/query sanitization");
  check(NetworkDiagnostics.safeUrl("http://example.invalid/locate/auth?access_token="+SECRET).equals("http://example.invalid:80/locate/auth"),"Auth discovery URL");
  String data=NetworkDiagnostics.safeUrl("http://example.invalid/config/private-client/datacenters/private-name/urls?token="+SECRET);
  check(data.contains("datacenters")&&!data.contains("private-client")&&!data.contains("private-name")&&!data.contains(SECRET),"Discovery identifiers must be templated");
  String profile=NetworkDiagnostics.safeUrl("http://example.invalid/profiles/me/myprofile?access_token="+SECRET);
  check(profile.equals("http://example.invalid:80/profiles/me/myprofile"),"Profile query leaked");
  check(NetworkDiagnostics.safeUrl("http://example.invalid/private-profile?token="+SECRET).equals("http://example.invalid:80/<redacted>"),"Unknown path should be redacted");
  for(String raw:new String[]{null,"not a url "+SECRET,"file:///private-profile", "http://example.invalid/authorize\r\nAuthorization: "+SECRET}){
   String value=NetworkDiagnostics.safeUrl(raw);
   check(!value.contains(SECRET)&&!value.contains("\n")&&!value.contains("\r")&&!value.contains("private-profile"),"Invalid URL leaked content");
  }
 }
 static void requestsAndResponses()throws Exception{
  clean();
  NetworkDiagnostics.startup("http://private-user:private-pass@10.61.217.55:8787/authorize?access_token="+SECRET,false);
  check(Log.joined().contains("startup")&&Log.joined().contains("10.61.217.55:8787")&&Log.joined().contains("mode=NETWORK"),"Startup must report network destination");
  NetworkDiagnostics.startup("http://10.61.217.55:8787/",true);
  check(Log.joined().contains("mode=LOCAL"),"Local startup mode absent");
  privateLogs();
  for(int status:new int[]{200,201,202,204,401,403,404,500,503}){
   clean();Handler h=new Handler();URL u=url(h,"10.61.217.55","/authorize?access_token="+SECRET);
   URLConnection opened=NetworkDiagnostics.openConnection(u);
   check(opened==h.last&&h.opens==1,"Open wrapper changed delegation");
   h.last.setRequestMethod("POST");h.last.status=status;
   check(NetworkDiagnostics.responseCode(h.last)==status&&h.last.responseCalls==1,"Response wrapper changed HTTP status/call count");
   check(Log.joined().contains("request")&&Log.joined().contains("url=http://10.61.217.55:8787/authorize"),"Missing request URL/IP");
   check(Log.joined().contains("status="+status)&&Log.joined().contains("method=POST"),"Missing HTTP status/method");
   if(status==201||status==204)check(Log.joined().contains("E GreenFarmNetwork response")&&Log.joined().contains("kind=UNEXPECTED_STATUS")&&Log.joined().contains("hint="),"Unsupported success status lacks client-specific diagnostic");
   if(status==200||status==202)check(!Log.joined().contains("E GreenFarmNetwork")&&!Log.joined().contains("kind=UNEXPECTED_STATUS"),"Accepted HTTP status reported as a failure");
   if(status>=400)check(Log.joined().contains("E GreenFarmNetwork response")&&Log.joined().contains("kind=HTTP_ERROR"),"HTTP failure omitted its error category");
   check(h.last.connectCalls==0&&h.last.disconnectCalls==0&&h.last.inputCalls==0,"Diagnostics changed connection/body ownership");
   privateLogs();
  }
  clean();Handler h=new Handler();Proxy proxy=new Proxy(Proxy.Type.HTTP,new InetSocketAddress("127.0.0.1",8888));
  URLConnection opened=NetworkDiagnostics.openConnection(url(h,"10.61.217.55","/health"),proxy);
  check(opened==h.last&&h.proxy.equals(proxy)&&h.proxyOpens==1&&h.opens==0,"Proxy overload changed route");
  privateLogs();
  h.last.status=302;h.last.location="http://private-user:private-pass@10.61.217.99:8787/authorize?access_token="+SECRET+"#private-fragment";
  check(NetworkDiagnostics.responseCode(h.last)==302,"Redirect status changed");
  check(Log.joined().contains("redirect_to=http://10.61.217.99:8787/authorize"),"Redirect destination omitted");
  check(h.opens==0&&h.proxyOpens==1,"Diagnostic unexpectedly followed redirect itself");privateLogs();
  // Redirects open another destination through the same wrapper. Logs must
  // identify the latest route without recording redirect credentials.
  NetworkDiagnostics.openConnection(url(h,"10.61.217.99","/locate/auth?access_token="+SECRET));
  NetworkDiagnostics.failure(new ConnectException("Failed "+SECRET));
  check(Log.joined().contains("network_error")&&Log.joined().contains("url=http://10.61.217.99:8787/locate/auth"),"Redirect failure retained the previous host");
  privateLogs();
  Log.entries.clear();NetworkDiagnostics.failure(new IOException(SECRET));
  check(!Log.joined().contains("10.61.217.99"),"Failure retained stale request destination for next error");privateLogs();
 }
 static void failures()throws Exception{
  Throwable[] errors={new UnknownHostException(SECRET),new ConnectException(SECRET),new SocketTimeoutException(SECRET),new SSLHandshakeException(SECRET),new UnknownServiceException("CLEARTEXT communication to secret-host not permitted by network security policy "+SECRET),new IOException(SECRET),new MalformedURLException(SECRET),new IllegalStateException(SECRET)};
  String[] kinds={"DNS_ERROR","CONNECT_ERROR","TIMEOUT","TLS_ERROR","CLEARTEXT_BLOCKED","IO_ERROR","INVALID_URL","UNKNOWN_ERROR"};
  for(int i=0;i<errors.length;i++){
   check(NetworkDiagnostics.errorKind(errors[i]).equals(kinds[i]),"Wrong error classification "+kinds[i]);
   clean();Handler h=new Handler();NetworkDiagnostics.openConnection(url(h,"10.61.217.55","/authorize?access_token="+SECRET));
   NetworkDiagnostics.failure(errors[i]);
   check(Log.joined().contains("network_error")&&Log.joined().contains("kind="+kinds[i])&&Log.joined().contains("type=")&&Log.joined().contains("hint="),"Error lacks actionable fields");
   privateLogs();
  }
  check(NetworkDiagnostics.errorKind(new IOException(SECRET,new ConnectException(SECRET))).equals("CONNECT_ERROR"),"Wrapped connection cause hidden");
  check(NetworkDiagnostics.errorKind(new IllegalStateException(SECRET,new IOException(SECRET))).equals("IO_ERROR"),"Wrapped generic I/O cause hidden");
  clean();NetworkDiagnostics.failure(new IllegalStateException(SECRET,new IOException(SECRET)));
  check(Log.joined().contains("kind=IO_ERROR")&&Log.joined().contains("type=java.lang.IllegalStateException"),"Nested I/O diagnostic lost wrapper type or cause classification");privateLogs();
  clean();Handler h=new Handler();h.openFailure=new ConnectException(SECRET);
  try{NetworkDiagnostics.openConnection(url(h,"10.61.217.55","/authorize"));throw new AssertionError("Open failure swallowed");}
  catch(ConnectException expected){check(expected==h.openFailure,"Open failure replaced");NetworkDiagnostics.failure(expected);}
  check(h.opens==1&&Log.joined().contains("kind=CONNECT_ERROR"),"Failed open missing diagnostics");privateLogs();
  clean();h=new Handler();NetworkDiagnostics.openConnection(url(h,"10.61.217.55","/authorize"));
  h.last.responseFailure=new SocketTimeoutException(SECRET);
  try{NetworkDiagnostics.responseCode(h.last);throw new AssertionError("Timeout swallowed");}
  catch(SocketTimeoutException expected){check(expected==h.last.responseFailure,"Timeout replaced");NetworkDiagnostics.failure(expected);}
  check(h.last.responseCalls==1&&h.last.inputCalls==0&&Log.joined().contains("kind=TIMEOUT"),"Response timeout changed behavior");privateLogs();
  // A new worker/request must not accidentally attribute an early error to
  // a URL left by an earlier request on the same pooled thread.
  clean();NetworkDiagnostics.failure(new IOException(SECRET));
  check(!Log.joined().contains("10.61.217.55"),"begin retained stale request destination");privateLogs();
  clean();h=new Handler();Log.fail=true;
  NetworkDiagnostics.startup("http://10.61.217.55:8787/",false);
  URLConnection opened=NetworkDiagnostics.openConnection(url(h,"10.61.217.55","/authorize"));
  check(opened==h.last&&h.opens==1,"Log failure interrupted opening connection");
  check(NetworkDiagnostics.responseCode(h.last)==200,"Info log failure changed response");
  h.last.status=503;check(NetworkDiagnostics.responseCode(h.last)==503,"Error log failure changed HTTP failure");
  NetworkDiagnostics.failure(new ConnectException(SECRET));
  Log.fail=false;check(Log.entries.isEmpty(),"Unavailable logger unexpectedly captured data");
 }
 static void workerIsolation()throws Exception{
  clean();final java.util.concurrent.CountDownLatch opened=new java.util.concurrent.CountDownLatch(2);
  final java.util.List<Throwable> failed=java.util.Collections.synchronizedList(new java.util.ArrayList<Throwable>());
  Thread first=new Thread(()->{try{
   NetworkDiagnostics.begin();NetworkDiagnostics.openConnection(url(new Handler(),"10.61.217.11","/authorize"));
   opened.countDown();opened.await();NetworkDiagnostics.failure(new ConnectException(SECRET));
  }catch(Throwable failure){failed.add(failure);}});
  Thread second=new Thread(()->{try{
   NetworkDiagnostics.begin();NetworkDiagnostics.openConnection(url(new Handler(),"10.61.217.22","/locate/auth"));
   opened.countDown();opened.await();NetworkDiagnostics.failure(new SocketTimeoutException(SECRET));
  }catch(Throwable failure){failed.add(failure);}});
  first.start();second.start();first.join(5000);second.join(5000);
  check(!first.isAlive()&&!second.isAlive()&&failed.isEmpty(),"Concurrent diagnostic workers failed");
  int errors=0;for(String line:Log.entries)if(line.contains("network_error")){
   errors++;
   if(line.contains("kind=CONNECT_ERROR"))check(line.contains("10.61.217.11:8787/authorize"),"Concurrent workers exchanged request destinations");
   if(line.contains("kind=TIMEOUT"))check(line.contains("10.61.217.22:8787/locate/auth"),"Concurrent workers exchanged request destinations");
  }
  check(errors==2,"Each concurrent failure must be reported once");
  privateLogs();
 }
 public static void main(String[] args)throws Exception{
  safeUrls();requestsAndResponses();failures();workerIsolation();
  System.out.println("PASS production network diagnostics: startup/destinations, HTTP delegation/statuses, error causes, redirects and credential redaction");
 }
}
'''


class NetworkDiagnosticsTest(unittest.TestCase):
    def test_real_logging_wrappers_preserve_network_outcomes_and_redact_credentials(self):
        helper = kotlin_source_directory() / 'NetworkDiagnostics.kt'
        with tempfile.TemporaryDirectory(prefix='network-diagnostics-') as temp:
            folder = Path(temp)
            log = folder / 'android/util/Log.java'
            log.parent.mkdir(parents=True)
            log.write_text(LOG)
            probe = folder / 'NetworkDiagnosticsProbe.java'
            probe.write_text(PROBE.replace('PACKAGE', qualified_package()))
            compile_mixed([helper], [log, probe], folder)
            result = subprocess.run(
                [str(jdk_tool('java')), '-cp', runtime_classpath(folder),
                 qualified_package() + '.NetworkDiagnosticsProbe'],
                capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS production network diagnostics', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
