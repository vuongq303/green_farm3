"""Run the production game HTTP worker with deterministic HTTP/Log peers.

Only the transport and Android services are replaced. The real run(), response
reading, flags, exception capture and cancellation execute together with the
production Kotlin diagnostic helper. No socket or player data is opened.
"""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from game_source_context import current_source_path, kotlin_source_directory, qualified_package
from kotlin_test_tools import compile_mixed, runtime_classpath


LOG = r'''package android.util;
import java.util.*;
public class Log {
 public static final List<String> lines=new ArrayList<String>();
 public static int i(String tag,String message){lines.add("I "+tag+" "+message);return 0;}
 public static int e(String tag,String message){lines.add("E "+tag+" "+message);return 0;}
 public static String joined(){return String.join("\n",lines);}
}
'''

PEERS = r'''package PACKAGE;
import java.net.URI;
class w {static boolean XR,XZ,Vf,Vl,XP;static boolean XS=true;static long XQ=30000;}
class an implements javax.net.ssl.TrustManager {}
class GameRuntime {
 static long Tn,To;
 static void c(boolean ok,String message){if(!ok)throw new AssertionError(message);}
 static void b(String message,String tag,int level){}
 static void d(Exception error){}
 static URI uri(String value){return URI.create(value);}
 static String by(String value){return uri(value).getScheme();}
 static String bA(String value){return uri(value).getHost();}
 static String bC(String value){int port=uri(value).getPort();return port<0?null:Integer.toString(port);}
 static String bB(String value){URI u=uri(value);return u.getRawPath()+(u.getRawQuery()==null?"":"?"+u.getRawQuery());}
}
'''

PROBE = r'''package PACKAGE;
import java.net.*;
import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import android.util.Log;
public class HttpRunDiagnosticsProbe {
 static int status=200;
 static IOException responseFailure,bodyFailure;
 static Connection last;
 static final String SECRET="private-login-token";
 static final String BODY="response-sensitive-token";
 static final String FORM="username=private-name&password=private-password&access_token="+SECRET;
 static void check(boolean ok,String label){if(!ok)throw new AssertionError(label+"\n"+Log.joined());}
 static void set(Object instance,String name,Object value)throws Exception{Field f=am.class.getDeclaredField(name);f.setAccessible(true);f.set(instance,value);}
 static Object get(Object instance,String name)throws Exception{Field f=am.class.getDeclaredField(name);f.setAccessible(true);return f.get(instance);}
 static class Connection extends HttpURLConnection {
  int responses,disconnects,reads,closes;
  final ByteArrayOutputStream sent=new ByteArrayOutputStream();
  Connection(URL url){super(url);}
  public void connect(){}public boolean usingProxy(){return false;}
  public void disconnect(){disconnects++;}
  public int getResponseCode()throws IOException{responses++;if(responseFailure!=null)throw responseFailure;return status;}
  public String getResponseMessage(){return "server-message-"+SECRET;}
  public InputStream getInputStream(){
   reads++;
   return new ByteArrayInputStream(BODY.getBytes(java.nio.charset.StandardCharsets.UTF_8)){
    public int read(byte[] b,int offset,int length){if(bodyFailure!=null)return fail(bodyFailure);return super.read(b,offset,length);}
    public void close(){closes++;}
   };
  }
  public OutputStream getOutputStream(){return sent;}
 }
 // The real worker constructs its own URL; intercept only transport creation.
 static int fail(IOException error){return HttpRunDiagnosticsProbe.<RuntimeException,Integer>raise(error);}
 @SuppressWarnings("unchecked") static <E extends Throwable,T>T raise(Throwable error)throws E{throw (E)error;}
 static class Handler extends URLStreamHandler {
  protected URLConnection openConnection(URL url){return last=new Connection(url);}
 }
 static am run(int code,IOException response,IOException body,boolean post)throws Exception{
  status=code;responseFailure=response;bodyFailure=body;last=null;Log.lines.clear();
  am request=new am(false);
  set(request,"BH","http://10.61.217.55:8787/authorize?access_token="+SECRET);
  set(request,"BI",post?FORM:null);set(request,"acE",post?1:0);set(request,"BR",true);
  request.run();
  check(last!=null&&last.responses==1,"response code must be delegated once");
  check(last.disconnects==1,"connection must be disconnected exactly once");
  check(!request.isInProgress()&&get(request,"acD")==null&&get(request,"BK")==null&&get(request,"BL")==null,"worker cleanup unchanged");
  check(Log.joined().contains("GreenFarmNetwork request url=http://10.61.217.55:8787/authorize"),"actual worker emits sanitized destination");
  for(String secret:new String[]{SECRET,FORM,BODY,"private-name","private-password","access_token="})
   check(!Log.joined().contains(secret),"diagnostics leaked request, response or exception data: "+secret);
  if(post)check(last.sent.toString("UTF-8").equals(FORM),"logging changed form bytes");
  return request;
 }
 public static void main(String[] args)throws Exception{
  URL.setURLStreamHandlerFactory(protocol->protocol.equals("http")?new Handler():null);
  am ok=run(200,null,null,true);
  check(ok.Bg==200&&!ok.BT&&ok.acI==null&&BODY.equals(ok.BM),"HTTP200 preserves payload/success fields");
  check(last.reads==1&&last.closes==1&&Log.joined().contains("response method=POST")&&Log.joined().contains("status=200"),"success status and stream lifecycle");
  for(int code:new int[]{401,503}){
   am rejected=run(code,null,null,false);
   check(rejected.Bg==code&&rejected.BT&&rejected.acI==null&&rejected.BM==null&&last.reads==0,"HTTP failure preserves flags and does not consume body");
   check(Log.joined().contains("status="+code)&&Log.joined().contains("kind=HTTP_ERROR"),"HTTP failure is explicit in logs");
  }
  IOException refused=new ConnectException("password="+SECRET);
  am failed=run(0,refused,null,false);
  check(failed.BT&&failed.Bg==0&&failed.acI==refused&&last.reads==0,"connection error preserves exact cause and cleanup");
  check(Log.joined().contains("kind=CONNECT_ERROR"),"connection failure must be categorized");
  IOException timedOut=new SocketTimeoutException("token="+SECRET);
  failed=run(0,timedOut,null,false);
  check(failed.BT&&failed.acI==timedOut&&Log.joined().contains("kind=TIMEOUT"),"timeout keeps exact exception");
  IOException brokenBody=new IOException("body secret="+SECRET);
  failed=run(200,null,brokenBody,false);
  check(failed.BT&&failed.Bg==200&&failed.acI==brokenBody&&last.reads==1&&last.closes==1,"response body failure preserves status, cause and stream closure");
  check(Log.joined().contains("status=200")&&Log.joined().contains("kind=IO_ERROR")&&Log.joined().indexOf("status=200")<Log.joined().indexOf("network_error"),"body failure logged after response with same request context");
  System.out.println("PASS real HTTP worker: success/form, 401/503, refused connection, timeout and broken body retain flags, exact exceptions and cleanup with private diagnostics");
 }
}
'''


class HttpRunDiagnosticsTest(unittest.TestCase):
    def test_production_http_run_and_diagnostics_preserve_transport_outcomes(self):
        package = qualified_package()
        with tempfile.TemporaryDirectory(prefix='http-run-diagnostics-') as temporary:
            root = Path(temporary)
            source = root / package.replace('.', '/')
            source.mkdir(parents=True)
            worker = source / 'am.java'
            worker.write_text(current_source_path('am').read_text())
            peers = source / 'Peers.java'
            peers.write_text(PEERS.replace('PACKAGE', package))
            probe = source / 'HttpRunDiagnosticsProbe.java'
            probe.write_text(PROBE.replace('PACKAGE', package))
            log = root / 'android/util/Log.java'
            log.parent.mkdir(parents=True)
            log.write_text(LOG)
            clock = root / 'android/os/SystemClock.java'
            clock.parent.mkdir(parents=True)
            clock.write_text('package android.os; public class SystemClock {public static long elapsedRealtime(){return 0;}}')
            web = root / 'com/gameloft/glads/AndroidWebView.java'
            web.parent.mkdir(parents=True)
            web.write_text('package com.gameloft.glads; public class AndroidWebView {public static final String UTF_8="UTF-8";}')
            output = root / 'classes'
            compile_mixed([kotlin_source_directory() / 'NetworkDiagnostics.kt'],
                          [worker, peers, probe, log, clock, web], output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output),
                                     package + '.HttpRunDiagnosticsProbe'],
                                    capture_output=True, text=True, timeout=30)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS real HTTP worker:', result.stdout)
            print(result.stdout.strip())


if __name__ == '__main__':
    unittest.main()
