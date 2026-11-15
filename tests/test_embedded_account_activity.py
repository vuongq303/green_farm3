"""Execute the Activity's production retry, Back and cookie-lifecycle methods.

Android widgets and CookieManager are deterministic peers. This verifies the
actual Kotlin control flow, including asynchronous cookie deletion, without
claiming installed WebView or device lifecycle validation.
"""
from pathlib import Path
import re
import subprocess
import sys
import tempfile
import unittest

ROOT = Path(__file__).resolve().parents[1]
sys.path.insert(0, str(ROOT / 'tools'))
from android_build_tools import jdk_tool
from kotlin_test_tools import compile_mixed, runtime_classpath
from test_aa_tile_renderer import extract_method

METHODS = (
    'private fun loadPage()',
    'override fun onBackPressed()',
    'private fun closeAccount()',
    'private fun failAccount(reason: String)',
    'private fun showLoading()',
    'private fun showError(value: String)',
    'private fun destroyWebView()',
    'override fun onDestroy()',
    'companion object',
)

CALLBACKS = (
    'override fun onPageFinished(view: WebView?, url: String?)',
    'override fun onReceivedError(view: WebView?, request: WebResourceRequest, error: WebResourceError?)',
    'override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest, response: WebResourceResponse?)',
)

SHELL = r'''
package com.quanhoang.greenfarm3.S800x480
import java.util.concurrent.atomic.AtomicBoolean

class EmbeddedAccountActivity : Activity() {
%FIELDS%
 private var createFailures = false
 val loadingCalls get() = handler.posts
 val errorCalls get() = message.errors
 init {
  pendingUrl = INITIAL
  expiresAt = Long.MAX_VALUE
  policy = AuthWebViewPolicy(ORIGIN, INITIAL)
  overlay = FrameLayout()
  message = TextView()
  retry = TextView()
 }
 private fun createWebView(): WebView {
  if (createFailures) throw IllegalStateException("fixture WebView failure")
  return WebView()
 }
 fun load() = loadPage()
 fun back() = onBackPressed()
 fun close() = closeAccount()
 fun destroy(finishing: Boolean = false) { if (finishing) isFinishing = true; onDestroy() }
 fun succeed() { accepted = true; closeAccount() }
 fun expire() { expiresAt = 0L }
 fun failCreation() { createFailures = true }
 fun widget(): WebView = web ?: error("missing fixture WebView")
 fun activeScopes() = cookieUsers
 fun errorVisible() = overlay.visibility == View.VISIBLE && retry.visibility == View.VISIBLE
 fun formVisible() = overlay.visibility == View.GONE
 private val client = object : WebViewClient() {
%CALLBACKS%
 }
 fun networkFailure(url: String, main: Boolean = false) = client.onReceivedError(web, WebResourceRequest(Uri(url), main), null)
 fun httpFailure(url: String, status: Int, main: Boolean = false) = client.onReceivedHttpError(web, WebResourceRequest(Uri(url), main), WebResourceResponse(status))
 fun pageFinished(url: String = INITIAL) = client.onPageFinished(web, url)
%METHODS%
}

const val ORIGIN = "https://accounts.example/"
val INITIAL = ORIGIN + "auth/web/login?attempt=" + "a".repeat(43)
val REGISTER = ORIGIN + "auth/web/register?attempt=" + "a".repeat(43)

open class Activity {
 var isFinishing = false
 var finishes = 0
 var destroyedCalls = 0
 fun finish() { isFinishing = true; finishes++ }
 open fun onBackPressed() {}
 open fun onDestroy() { destroyedCalls++ }
}
class Looper { companion object { fun getMainLooper() = Looper() } }
class Handler(looper: Looper) {
 var removals = 0
 var posts = 0
 fun removeCallbacksAndMessages(token: Any?) { removals++ }
 fun removeCallbacks(callback: Runnable) { removals++ }
 fun postDelayed(callback: Runnable, delay: Long) { posts++ }
}
open class View {
 var visibility = VISIBLE
 companion object { const val VISIBLE = 0; const val GONE = 8 }
}
open class ViewGroup : View() {
 var removed = 0
 fun removeView(view: WebView) { removed++ }
}
class FrameLayout : ViewGroup()
class TextView : View() {
 var errors = 0
 var text = ""
  set(value) { field = value; if (value != "Đang tải trang tài khoản...") errors++ }
}
class Uri(private val value: String) { override fun toString() = value }
class WebResourceRequest(val url: Uri, val isForMainFrame: Boolean)
class WebResourceResponse(val statusCode: Int)
class WebResourceError
open class WebViewClient {
 open fun onPageFinished(view: WebView?, url: String?) {}
 open fun onReceivedError(view: WebView?, request: WebResourceRequest, error: WebResourceError?) {}
 open fun onReceivedHttpError(view: WebView?, request: WebResourceRequest, response: WebResourceResponse?) {}
}
class WebSettings { var javaScriptEnabled = true }
class HistoryItem(val url: String)
class History(val urls: List<String>) {
 val currentIndex get() = urls.lastIndex
 fun getItemAtIndex(index: Int) = HistoryItem(urls[index])
}
class WebView : ViewGroup() {
 val settings = WebSettings()
 val parent = ViewGroup()
 val loads = mutableListOf<String?>()
 var history = listOf(INITIAL)
 var wentBack = 0
 var stopped = 0
 var historyCleared = 0
 var formCleared = 0
 var childrenRemoved = 0
 var destroyed = 0
 fun loadUrl(url: String) {
  check(url == INITIAL)
  val cookies = CookieManager.getInstance()
  check(cookies.acceptCookie()) { "legacy global cookie refusal was not scoped for login" }
  loads.add(cookies.jar["__Host-gf_auth_web"])
  check(!cookies.serverStarted || cookies.jar["__Host-gf_auth_web"] == "bound-attempt") {
   "a started attempt cannot be reopened after deleting its bound cookie"
  }
  cookies.serverStarted = true
  cookies.jar["__Host-gf_auth_web"] = "bound-attempt"
 }
 fun canGoBack() = history.size > 1
 fun copyBackForwardList() = History(history)
 fun goBack() { wentBack++ }
 fun stopLoading() { stopped++ }
 fun clearHistory() { historyCleared++ }
 fun clearFormData() { formCleared++ }
 fun removeAllViews() { childrenRemoved++ }
 fun destroy() { destroyed++ }
}
class CookieManager {
 var acceptance = false
 var serverStarted = false
 var throwOnSet = false
 var throwOnFlush = false
 var flushes = 0
 val changes = mutableListOf<Boolean>()
 val writes = mutableListOf<Pair<String,String>>()
 val callbacks = mutableListOf<(Boolean) -> Unit>()
 val jar = mutableMapOf("unrelated-game-cookie" to "preserved")
 fun acceptCookie() = acceptance
 fun setAcceptCookie(value: Boolean) { acceptance = value; changes.add(value) }
 fun setCookie(origin: String, value: String, callback: (Boolean) -> Unit) {
  writes.add(Pair(origin, value))
  if (throwOnSet) throw IllegalStateException("fixture cookie error")
  check(acceptance) { "acceptance restored before deleting auth cookie" }
  check(origin == ORIGIN && value == "__Host-gf_auth_web=; Max-Age=0; Path=/; Secure; HttpOnly; SameSite=Strict")
  jar.remove("__Host-gf_auth_web")
  callbacks.add(callback)
 }
 fun flush() { flushes++; if (throwOnFlush) throw IllegalStateException("fixture flush error") }
 fun complete(twice: Boolean = false) {
  val pending = callbacks.toList(); callbacks.clear()
  pending.forEach { it(true); if (twice) it(true) }
 }
 companion object {
  private var current = CookieManager()
  fun getInstance() = current
  fun reset(acceptance: Boolean = false): CookieManager {
   current = CookieManager().also { it.acceptance = acceptance }
   SocialAccountController.events.clear()
   return current
  }
 }
}
object SocialAccountController {
 val events = mutableListOf<String?>()
 fun closeEmbedded(activity: EmbeddedAccountActivity, url: String, failure: String? = null) {
  check(url == INITIAL); events.add(failure)
 }
}

fun main() {
 // Actual loadPage must preserve the one-use server attempt's cookie on retry.
 var cookies = CookieManager.reset()
 var first = EmbeddedAccountActivity()
 first.load(); val firstView = first.widget()
 first.load()
 check(first.loadingCalls == 2 && first.errorCalls == 0 && firstView.loads == listOf(null, "bound-attempt"))
 check(cookies.writes.isEmpty() && cookies.acceptCookie() && first.activeScopes() == 1)
 // A recreated Activity can acquire before the old instance is destroyed.
 var restored = EmbeddedAccountActivity(); restored.load()
 check(restored.widget().loads == listOf("bound-attempt") && restored.activeScopes() == 2)
 first.destroy()
 check(cookies.writes.isEmpty() && cookies.acceptCookie() && restored.activeScopes() == 1)
 check(firstView.destroyed == 1 && firstView.parent.removed == 1 && !firstView.settings.javaScriptEnabled)
 check(firstView.formCleared == 1 && firstView.historyCleared == 1 && firstView.childrenRemoved == 1)
 restored.close(); restored.close(); restored.destroy()
 check(restored.finishes == 1 && SocialAccountController.events == listOf<String?>(null))
 check(cookies.writes.size == 1 && cookies.acceptCookie() && restored.activeScopes() == 1)
 cookies.complete(twice = true)
 check(!cookies.acceptCookie() && restored.activeScopes() == 0 && cookies.jar["unrelated-game-cookie"] == "preserved")
 // A sequential recreation also retains its bound cookie while restoring the prior global flag.
 cookies = CookieManager.reset()
 first = EmbeddedAccountActivity(); first.load(); first.destroy()
 check(!cookies.acceptCookie() && cookies.writes.isEmpty() && first.activeScopes() == 0)
 restored = EmbeddedAccountActivity(); restored.load()
 check(restored.widget().loads == listOf("bound-attempt") && restored.errorCalls == 0)
 restored.succeed(); restored.destroy()
 check(SocialAccountController.events.isEmpty() && cookies.acceptCookie())
 cookies.complete(); check(!cookies.acceptCookie() && restored.activeScopes() == 0)
 // Existing cookie-enabled callers retain their original global preference after success.
 cookies = CookieManager.reset(true)
 first = EmbeddedAccountActivity(); first.load(); first.succeed(); first.destroy(); cookies.complete()
 check(cookies.acceptCookie() && first.activeScopes() == 0 && SocialAccountController.events.isEmpty())
 // Back traverses only this pending form; an unrelated history entry closes/cancels instead.
 cookies = CookieManager.reset()
 first = EmbeddedAccountActivity(); first.load(); first.widget().history = listOf(REGISTER, INITIAL)
 first.back(); check(first.widget().wentBack == 1 && first.finishes == 0 && SocialAccountController.events.isEmpty())
 first.widget().history = listOf("https://attacker.invalid/", INITIAL)
 first.back(); check(first.finishes == 1 && SocialAccountController.events == listOf<String?>(null))
 first.destroy(); cookies.complete(); check(first.activeScopes() == 0 && !cookies.acceptCookie())
 // Cleanup failures and duplicate callbacks cannot leak or over-release the global cookie lease.
 for (failure in listOf("set", "flush")) {
  cookies = CookieManager.reset(); first = EmbeddedAccountActivity(); first.load()
  cookies.throwOnSet = failure == "set"; cookies.throwOnFlush = failure == "flush"
  first.close(); first.destroy(); cookies.complete(twice = true)
  check(!cookies.acceptCookie() && first.activeScopes() == 0)
 }
 // Expired attempts and WebView creation failures never acquire a cookie scope or open a form.
 cookies = CookieManager.reset(); first = EmbeddedAccountActivity(); first.expire(); first.load(); first.destroy()
 check(first.finishes == 1 && SocialAccountController.events.first() == "attempt_expired")
 check(first.activeScopes() == 0 && cookies.writes.isEmpty() && !cookies.acceptCookie())
 cookies = CookieManager.reset(); first = EmbeddedAccountActivity(); first.failCreation(); first.load(); first.destroy()
 check(first.errorCalls == 1 && first.activeScopes() == 0 && cookies.writes.isEmpty() && !cookies.acceptCookie())
 // HTML completion cannot hide a required script/stylesheet failure or leave an inert form visible.
 for (asset in listOf("password.js", "styles.css")) {
  cookies = CookieManager.reset(); first = EmbeddedAccountActivity(); first.load(); first.pageFinished()
  check(first.formVisible() && first.errorCalls == 0)
  first.networkFailure(ORIGIN + "auth/assets/" + asset)
  first.pageFinished()
  check(first.errorVisible() && first.errorCalls == 1 && first.finishes == 0)
  first.load(); first.pageFinished()
  check(first.formVisible() && first.loadingCalls == 2 && first.widget().loads == listOf(null, "bound-attempt"))
  first.httpFailure(ORIGIN + "auth/assets/" + asset, 404)
  first.pageFinished()
  check(first.errorVisible() && first.errorCalls == 2 && first.finishes == 0)
  first.close(); first.destroy(); cookies.complete(); check(first.activeScopes() == 0)
 }
 // Credential XHR errors remain owned by the web form's error handling, not native session cancellation.
 cookies = CookieManager.reset(); first = EmbeddedAccountActivity(); first.load(); first.pageFinished()
 for ((url, status) in listOf(ORIGIN + "auth/login" to 401, ORIGIN + "auth/register" to 409,
   ORIGIN + "auth/assets/green-farm-3.png" to 404, "https://attacker.invalid/auth/assets/password.js" to 404,
   ORIGIN + "auth/assets/password.js?untrusted=1" to 404)) {
  first.httpFailure(url, status); first.networkFailure(url); first.pageFinished()
  check(first.errorCalls == 0 && first.formVisible() && first.finishes == 0 && SocialAccountController.events.isEmpty())
 }
 first.httpFailure(INITIAL, 500, true); first.pageFinished()
 check(first.errorVisible() && first.errorCalls == 1 && first.finishes == 0)
 first.close(); first.destroy(); cookies.complete(); check(first.activeScopes() == 0)
 // Expired/rate-limited main pages close the unusable pending flow, allowing a fresh game-side retry.
 for ((status, reason) in listOf(400 to "attempt_expired", 401 to "attempt_expired", 403 to "attempt_expired",
   410 to "attempt_expired", 429 to "rate_limited")) {
  cookies = CookieManager.reset(); first = EmbeddedAccountActivity(); first.load()
  first.httpFailure(INITIAL, status, true)
  check(first.finishes == 1 && SocialAccountController.events.first() == reason)
  first.destroy(); cookies.complete(); check(first.activeScopes() == 0)
 }
 println("PASS actual Activity retry/recreation cookie binding, scoped cleanup, Back, cancellation, resource release and required-asset network errors")
}
'''


class EmbeddedAccountActivityTest(unittest.TestCase):
    def test_production_cookie_retry_back_and_lifecycle_methods(self):
        source = (ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/EmbeddedAccountActivity.kt').read_text()
        names = ('handler', 'web', 'policy', 'pendingUrl', 'expiresAt', 'accepted', 'closed', 'destroyed', 'loadFailed', 'cookieLease', 'overlay', 'message', 'retry')
        fields = []
        for name in names:
            match = re.search(r'^    private (?:lateinit )?(?:val|var) ' + name + r'\b[^\n]*', source, re.MULTILINE)
            self.assertIsNotNone(match, 'Production lifecycle field missing: ' + name)
            fields.append(match.group(0))
        fields.append(extract_method(source, 'private val attemptTimeout = Runnable'))
        fields.append(extract_method(source, 'private val loadTimeout = Runnable'))
        harness = (SHELL.replace('%FIELDS%', '\n'.join(fields))
                   .replace('%METHODS%', '\n'.join(extract_method(source, signature) for signature in METHODS))
                   .replace('%CALLBACKS%', '\n'.join(extract_method(source, signature) for signature in CALLBACKS)))
        with tempfile.TemporaryDirectory(prefix='embedded-account-activity-') as temp:
            directory = Path(temp)
            fixture = directory / 'ActivityChecks.kt'
            fixture.write_text(harness)
            output = directory / 'classes'
            compile_mixed([fixture, ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/AuthWebViewPolicy.kt'], [], output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), 'com.quanhoang.greenfarm3.S800x480.ActivityChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS actual Activity retry/recreation', result.stdout)


if __name__ == '__main__':
    unittest.main()
