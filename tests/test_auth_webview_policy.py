"""Run the production embedded-form navigation boundary against hostile URL cases."""
from pathlib import Path
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from kotlin_test_tools import compile_mixed, runtime_classpath
from android_build_tools import jdk_tool

HARNESS = r'''
import com.quanhoang.greenfarm3.S800x480.AuthWebViewPolicy
fun main() {
 val id="a".repeat(43);val other="b".repeat(43)
 val origin="https://auth.example/";val initial=origin+"auth/web/login?attempt="+id
 val policy=AuthWebViewPolicy(origin,initial)
 check(policy.origin==origin&&policy.initialUrl==initial)
 for(page in listOf("login","register")) {
  val url=origin+"auth/web/"+page+"?attempt="+id
  check(policy.allowMainFrame(url)&&policy.allowResource(url))
 }
 for(url in listOf(origin+"auth/login",origin+"auth/register",origin+"auth/assets/account.js",
   origin+"auth/assets/styles.css",origin+"auth/assets/green-farm-3.png")) {
  check(policy.allowResource(url)&&!policy.allowMainFrame(url))
 }
 check(policy.allowMainFrame(initial.replace("auth.example","AUTH.EXAMPLE:443")))
 for(asset in listOf("password.js","styles.css")) {
  val required=origin+"auth/assets/"+asset
  check(policy.isRequiredAsset(required)&&policy.isRequiredAsset(required.replace("auth.example","AUTH.EXAMPLE:443")))
  for(bad in listOf(required.replace("auth.example","evil.example"),required.replace("auth.example","user@auth.example"),
    required.replace("auth.example","auth.example:444"),required+"?cache=1",required+"#",required.replace("/assets/","/assets/./"))) {
   check(!policy.isRequiredAsset(bad)){"untrusted resource classified as required form asset"}
  }
 }
 for(optional in listOf(origin+"auth/assets/green-farm-3.png",origin+"auth/assets/account.js",origin+"auth/login",origin+"auth/register",initial)) {
  check(!policy.isRequiredAsset(optional)){"credential API or optional image must not own native form errors"}
 }
 val denied=listOf("http://auth.example/auth/web/login?attempt=$id", "https://evil.example/auth/web/login?attempt=$id",
  initial.replace("auth.example","auth.example.evil.example"), initial.replace("auth.example","evil.example@auth.example"),
  initial.replace("auth.example","@auth.example"),initial.replace("auth.example","auth.example:444"),
  initial.replace("auth.example","auth.example."),initial+"#",initial+"#secret",initial+"&extra=1",
  initial+"&attempt=$id",initial.replace("attempt=$id","attempt=$other"),initial.replace("attempt=","%61ttempt="),
  initial.replace("auth/web/login","auth/web/%6cogin"),initial.replace("auth/web/login","auth/web/./login"),
  initial.replace("auth/web/login","auth/web/register/../login"),initial.replace("auth/web/login","auth/web//login"),
  origin+"auth/web/login",origin+"auth/web/login?attempt=short",origin+"auth/web/login?attempt=$id%00",
  "javascript:alert(1)","file:///data/data/app/secrets", "content://private/token", "data:text/html,hello", "about:blank",
  "https://accounts.google.com/", "https://www.facebook.com/dialog/oauth",origin+"auth/browser/start?id=$id",
  origin+"auth/callback/facebook?code=$id&state=$id", origin+"auth/exchange",origin+"auth/me",origin+"auth/logout",
  origin+"auth/refresh",origin+"auth/assets/../me",origin+"auth/assets/%2e%2e/me",origin+"auth/assets/./styles.css",
  origin+"auth/assets/styles.css?token=secret",origin+"auth/assets/subdir/styles.css",origin+"auth/login?token=secret",
  " "+initial,initial+" ","https://auth.example:0/auth/web/login?attempt=$id")
 for(url in denied) check(!policy.allowMainFrame(url)&&!policy.allowResource(url)&&!policy.isRequiredAsset(url)){"permitted unsafe network URL"}
 val state="s".repeat(43);val code="c".repeat(43)
 val callback="com.quanhoang.greenfarm3://oauth/callback?code=$code&state=$state"
 check(policy.isCallback(callback))
 check(policy.isCallback("com.quanhoang.greenfarm3://oauth/callback?state=$state&error=access_denied"))
 for(url in listOf(callback+"&access_token=secret",callback+"&code=$code",callback+"&state=$state",callback+"#",
  callback+"&error=access_denied",callback.replace("oauth/","evil@oauth/"),callback.replace("code=","%63ode="),
  callback.replace("state=$state","state=short"),callback.replace("code=$code","code=%63"+"c".repeat(42)),
  "com.quanhoang.greenfarm3://oauth/callback?state=$state&error=unknown",callback.replace("/callback?","/callback/?"))) {
  check(!policy.isCallback(url)){"permitted malformed callback"}
 }
 check(!policy.allowResource(callback)&&!policy.allowMainFrame(callback))
 for(bad in listOf("http://auth.example/","https://user@auth.example/",origin+"path",origin+"?x=1",origin+"#", "")) {
  check(runCatching{AuthWebViewPolicy(bad,initial)}.isFailure)
 }
 for(bad in denied.filterNot { it==initial.replace("attempt=$id","attempt=$other") }+
   listOf(initial.replace("/login?","/register?"))) {
  check(runCatching{AuthWebViewPolicy(origin,bad)}.isFailure)
 }
 val portPolicy=AuthWebViewPolicy("https://auth.example:8443/",initial.replace("auth.example","auth.example:8443"))
 check(portPolicy.allowMainFrame(initial.replace("auth.example","auth.example:8443"))&&!portPolicy.allowMainFrame(initial))
 println("PASS strict HTTPS origin, immutable attempt, page/API/asset allowlists and callback boundary")
}
'''


class AuthWebViewPolicyTest(unittest.TestCase):
    def test_embedded_form_network_and_callback_boundaries(self):
        with tempfile.TemporaryDirectory(prefix='auth-webview-policy-') as temp:
            path = Path(temp)
            harness = path / 'PolicyChecks.kt'
            harness.write_text(HARNESS)
            output = path / 'classes'
            compile_mixed([ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/AuthWebViewPolicy.kt', harness], [], output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), 'PolicyChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS strict HTTPS origin', result.stdout)


if __name__ == '__main__':
    unittest.main()
