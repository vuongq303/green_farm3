"""Execute production embedded-dialog load, frame-reveal and lifecycle logic.

Deterministic Android peers exercise the Kotlin control flow. These checks do
not claim rendered Android/WebView pixels, keyboard or compositor blur coverage.
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
    'override fun show()',
    'fun open(url: String, expiresAt: Long)',
    'fun renderAccountState(status: Int, value: String)',
    'private fun showLoading()',
    'private fun revealPage(view: WebView, url: String, generation: Long)',
    'private fun showError(value: String, newAttempt: Boolean = false)',
    'private fun retryPage()',
    'private fun loadPage()',
    'private fun navigate(url: String, mainFrame: Boolean)',
    'override fun onBackPressed()',
    'private fun closeAccount()',
    'override fun dismiss()',
    'override fun onDetachedFromWindow()',
    'private fun destroyWebView()',
    'private fun dispose()',
    'companion object',
)
CALLBACKS = (
    'override fun onPageStarted(view: WebView?, url: String?, icon: android.graphics.Bitmap?)',
    'override fun onPageCommitVisible(view: WebView?, url: String?)',
    'override fun onPageFinished(view: WebView?, url: String?)',
    'override fun onReceivedError(view: WebView?, request: WebResourceRequest, error: WebResourceError?)',
    'override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest, response: WebResourceResponse?)',
)

SHELL = r'''
package com.quanhoang.greenfarm3.S800x480
import java.util.concurrent.atomic.AtomicBoolean

class EmbeddedAccountOverlay : Dialog() {
%FIELDS%
 private var createFailures = false
 init {
  root = FrameLayout(); overlay = FrameLayout(); message = TextView(); retry = TextView()
  close = TextView(); spinner = ProgressBar(); errorPanel = LinearLayout()
 }
 private fun createWebView(): WebView {
  Events.values.add("create")
  check(overlay.visibility == View.VISIBLE && spinner.visibility == View.VISIBLE) {"WebView created before the loading overlay"}
  if (createFailures) throw IllegalStateException("fixture WebView failure")
  return WebView().also { it.loadCheck = {
   check(overlay.visibility == View.VISIBLE && spinner.visibility == View.VISIBLE && errorPanel.visibility == View.GONE)
  } }
 }
 private val client = object : WebViewClient() {
%CALLBACKS%
 }
 fun load() = loadPage()
 fun retryLoad() = retryPage()
 fun back() = onBackPressed()
 fun callback(url: String) = navigate(url,true)
 fun widget(): WebView = web ?: error("missing fixture WebView")
 fun hasWebView() = web != null
 fun activeScopes() = cookieUsers
 fun showingSpinner() = spinner.visibility == View.VISIBLE && overlay.visibility == View.VISIBLE && errorPanel.visibility == View.GONE
 fun errorVisible() = spinner.visibility == View.GONE && errorPanel.visibility == View.VISIBLE && overlay.visibility == View.VISIBLE
 fun formVisible() = overlay.visibility == View.GONE && close.visibility == View.VISIBLE && web?.alpha == 1f
 fun pageStarted(url: String = INITIAL) = client.onPageStarted(web,url,null)
 fun commit(url: String = INITIAL) = client.onPageCommitVisible(web,url)
 fun pageFinished(url: String = INITIAL) = client.onPageFinished(web,url)
 fun networkFailure(url: String, main: Boolean = false) = client.onReceivedError(web, WebResourceRequest(Uri(url),main),null)
 fun httpFailure(url: String, status: Int, main: Boolean = false) = client.onReceivedHttpError(web, WebResourceRequest(Uri(url),main),WebResourceResponse(status))
 fun hasAttemptTimeout() = handler.callbacks.contains(attemptTimeout)
 fun hasPageTimeout() = handler.callbacks.contains(loadTimeout)
 fun timeout() = loadTimeout.run()
 fun bootstrapError() = renderAccountState(SocialAccountController.ERROR,"fixture bootstrap error")
 fun failCreation() { createFailures = true }
%METHODS%
}

const val ORIGIN = "https://accounts.example/"
val INITIAL = ORIGIN+"auth/web/login?attempt="+"a".repeat(43)
val REGISTER = ORIGIN+"auth/web/register?attempt="+"a".repeat(43)
val CALLBACK = "com.quanhoang.greenfarm3://oauth/callback?state="+"s".repeat(43)+"&code="+"c".repeat(43)
object Events { val values=mutableListOf<String>() }
class Context
open class Dialog {
 val context=Context()
 var isShowing=false
 var dismisses=0
 val window=Window()
 open fun show() { isShowing=true;Events.values.add("dialog.show") }
 open fun dismiss() { isShowing=false;dismisses++;Events.values.add("dismiss") }
 open fun onBackPressed() {}
 open fun onDetachedFromWindow() {}
}
class Window { fun setLayout(w:Int,h:Int) {} }
class Looper { companion object { fun getMainLooper()=Looper() } }
class Handler(looper:Looper) {
 val callbacks=mutableListOf<Runnable>()
 fun removeCallbacksAndMessages(token:Any?) { callbacks.clear() }
 fun removeCallbacks(callback:Runnable) { callbacks.remove(callback) }
 fun postDelayed(callback:Runnable, delay:Long) { callbacks.add(callback) }
}
object Build { object VERSION { var SDK_INT=28 } }
open class View {
 var visibility=VISIBLE
 var alpha=0f
 fun post(work:()->Unit) { Tasks.pending.add(work) }
 companion object { const val VISIBLE=0;const val GONE=8 }
}
object Tasks {
 val pending=mutableListOf<()->Unit>()
 fun drain() { val work=pending.toList();pending.clear();work.forEach{it()} }
}
open class ViewGroup:View() {
 var removed=0
 fun removeView(v:WebView) { removed++ }
 object LayoutParams { const val MATCH_PARENT=-1 }
}
class FrameLayout:ViewGroup()
class LinearLayout:ViewGroup()
class ProgressBar:View()
class TextView:View() { var text="" }
class Uri(private val value:String) { override fun toString()=value }
class WebResourceRequest(val url:Uri,val isForMainFrame:Boolean)
class WebResourceResponse(val statusCode:Int)
class WebResourceError
open class WebViewClient {
 open fun onPageStarted(view:WebView?,url:String?,icon:android.graphics.Bitmap?) {}
 open fun onPageCommitVisible(view:WebView?,url:String?) {}
 open fun onPageFinished(view:WebView?,url:String?) {}
 open fun onReceivedError(view:WebView?,request:WebResourceRequest,error:WebResourceError?) {}
 open fun onReceivedHttpError(view:WebView?,request:WebResourceRequest,response:WebResourceResponse?) {}
}
class WebSettings { var javaScriptEnabled=true }
class HistoryItem(val url:String)
class History(val urls:List<String>) {
 val currentIndex get()=urls.lastIndex
 fun getItemAtIndex(index:Int)=HistoryItem(urls[index])
}
class WebView:ViewGroup() {
 abstract class VisualStateCallback { abstract fun onComplete(requestId:Long) }
 val settings=WebSettings()
 val parent=ViewGroup()
 val loads=mutableListOf<String?>()
 val frames=mutableListOf<Pair<Long,VisualStateCallback>>()
 var history=listOf(INITIAL)
 var url:String?=null
 var contentHeight=0
 var wentBack=0
 var stopped=0
 var destroyed=0
 var loadCheck:()->Unit={}
 fun loadUrl(value:String) {
  loadCheck();Events.values.add("load")
  check(value==INITIAL)
  url=value
  val cookies=CookieManager.getInstance()
  check(cookies.acceptCookie()) {"first-party cookies disabled during form load"}
  loads.add(cookies.jar["__Host-gf_auth_web"])
  cookies.jar["__Host-gf_auth_web"]="bound-attempt"
 }
 fun postVisualStateCallback(id:Long,cb:VisualStateCallback) { frames.add(Pair(id,cb)) }
 fun drawFrame() { val work=frames.toList();frames.clear();work.forEach{it.second.onComplete(it.first)} }
 fun postOnAnimation(work:()->Unit) { Tasks.pending.add(work) }
 fun canGoBack()=history.size>1
 fun copyBackForwardList()=History(history)
 fun goBack() { wentBack++ }
 fun stopLoading() { stopped++ }
 fun clearHistory() {}
 fun clearFormData() {}
 fun removeAllViews() {}
 fun destroy() { destroyed++ }
}
class CookieManager {
 var acceptance=false
 var flushes=0
 val writes=mutableListOf<Pair<String,String>>()
 val callbacks=mutableListOf<(Boolean)->Unit>()
 val jar=mutableMapOf("unrelated-game-cookie" to "preserved")
 fun acceptCookie()=acceptance
 fun setAcceptCookie(value:Boolean) { acceptance=value }
 fun setCookie(origin:String,value:String,callback:(Boolean)->Unit) {
  check(acceptance)
  check(origin==ORIGIN && value=="__Host-gf_auth_web=; Max-Age=0; Path=/; Secure; HttpOnly; SameSite=Strict")
  writes.add(Pair(origin,value));jar.remove("__Host-gf_auth_web");callbacks.add(callback)
 }
 fun flush() { flushes++ }
 fun complete() { val work=callbacks.toList();callbacks.clear();work.forEach{it(true);it(true)} }
 companion object {
  private var current=CookieManager()
  fun getInstance()=current
  fun reset(acceptance:Boolean=false):CookieManager {
   current=CookieManager().also{it.acceptance=acceptance}
   SocialAccountController.reset();Events.values.clear();Tasks.pending.clear();Build.VERSION.SDK_INT=28
   return current
  }
 }
}
object LocalDevelopmentConfig { const val AUTH_BASE_URL=ORIGIN }
object SocialAuthClient { fun validateOrigin(value:String)=value }
object SocialAccountController {
 const val READY=5; const val ERROR=6
 var statusValue=2
 var accepted=true
 var cancels=0
 var callbacks=0
 val closes=mutableListOf<String>()
 val chooses=mutableListOf<String>()
 fun tick() {}
 fun status()=statusValue
 fun message()="fixture state"
 fun cancel() { cancels++ }
 fun choose(provider:String) { chooses.add(provider) }
 fun recordEmbeddedCallback(context:Context,url:String):Boolean { callbacks++;return accepted }
 fun closeEmbedded(context:Context,url:String) { check(url==INITIAL);closes.add(url) }
 fun reset() { statusValue=2;accepted=true;cancels=0;callbacks=0;closes.clear();chooses.clear() }
}
fun open():EmbeddedAccountOverlay=EmbeddedAccountOverlay().also{it.show();it.open(INITIAL,Long.MAX_VALUE)}
fun done(dialog:EmbeddedAccountOverlay,cookies:CookieManager) {
 dialog.dismiss();Tasks.drain();cookies.complete();check(dialog.activeScopes()==0)
 check(cookies.jar["unrelated-game-cookie"]=="preserved")
}
fun main() {
 // The game window remains behind one Dialog, and loading is visible before any WebView load.
 var cookies=CookieManager.reset();var d=open();var view=d.widget()
 check(d.showingSpinner() && view.loads==listOf<String?>(null))
 check(Events.values.indexOf("dialog.show")<Events.values.indexOf("create"))
 check(Events.values.indexOf("create")<Events.values.indexOf("load"))
 d.open(INITIAL,Long.MAX_VALUE);Tasks.drain()
 check(view===d.widget() && view.loads.size==1) {"duplicate tap/descriptor made a second load"}
 d.pageStarted();d.pageFinished()
 check(d.showingSpinner() && !d.formVisible()) {"onPageFinished alone exposed an unpainted WebView"}
 d.commit();check(d.showingSpinner())
 view.drawFrame();check(d.formVisible() && !d.showingSpinner())
 // Commit callbacks from the previous generation cannot erase a required asset failure.
 d.commit();d.networkFailure(ORIGIN+"auth/assets/password.js");view.drawFrame()
 check(d.errorVisible() && !d.formVisible())
 d.retryLoad();check(view===d.widget() && view.loads==listOf(null,"bound-attempt"))
 d.pageStarted();d.commit();view.drawFrame();check(d.formVisible())
 d.timeout();check(d.errorVisible() && view.stopped>0)
 d.retryLoad();d.pageStarted();d.commit();view.drawFrame();check(d.formVisible())
 // Unrelated images and credential response errors stay owned by the web form.
 d.httpFailure(ORIGIN+"auth/login",401);d.networkFailure(ORIGIN+"auth/assets/green-farm-3.png")
 check(d.formVisible())
 // A valid callback starts verification but cannot close before the confirmed account session.
 check(d.hasPageTimeout()==false) // The visible form has already canceled its page deadline.
 check(d.callback(CALLBACK));check(SocialAccountController.callbacks==1 && d.isShowing && d.showingSpinner())
 check(!d.hasPageTimeout() && !d.hasAttemptTimeout()) {"page deadline leaked into native session verification"}
 d.renderAccountState(3,"exchange");check(d.isShowing)
 SocialAccountController.statusValue=SocialAccountController.READY
 d.renderAccountState(SocialAccountController.READY,"")
 check(!d.isShowing && d.dismisses==1 && SocialAccountController.closes.isEmpty())
 cookies.complete();check(d.activeScopes()==0 && !cookies.acceptCookie())
 // Failed exchange exposes retry; retry starts a new in-game password attempt.
 cookies=CookieManager.reset();d=open();d.callback(CALLBACK);d.renderAccountState(SocialAccountController.ERROR,"retry me")
 check(d.errorVisible());d.retryLoad();cookies.complete()
 check(!d.isShowing && SocialAccountController.chooses==listOf("password") && d.activeScopes()==0)
 // Initial bootstrap failure (before descriptor) must also end loading and permit close/retry.
 cookies=CookieManager.reset();d=EmbeddedAccountOverlay();d.show();d.bootstrapError()
 check(d.errorVisible());d.retryLoad();Tasks.drain();cookies.complete()
 check(SocialAccountController.cancels==1 && SocialAccountController.chooses==listOf("password"))
 // A descriptor arriving after timeout cannot clear the error or initiate a hidden load.
 cookies=CookieManager.reset();d=EmbeddedAccountOverlay();d.show();d.timeout();d.open(INITIAL,Long.MAX_VALUE)
 check(d.errorVisible());Tasks.drain();check(d.errorVisible() && !d.hasWebView());done(d,cookies)
 // Closing an unprepared overlay cancels the operation; its posted initializer cannot reopen it.
 cookies=CookieManager.reset();d=EmbeddedAccountOverlay();d.show();d.dismiss();Tasks.drain()
 check(SocialAccountController.cancels==1 && !d.isShowing && Events.values.none{it=="create"})
 // Back only follows same pending form history; unrelated history closes the Dialog safely.
 cookies=CookieManager.reset();d=open();view=d.widget();view.history=listOf(REGISTER,INITIAL)
 d.back();check(view.wentBack==1 && d.isShowing)
 view.history=listOf("https://attacker.invalid/",INITIAL);d.back();done(d,cookies)
 check(SocialAccountController.closes==listOf(INITIAL) && view.destroyed==1)
 // Back during exchange explicitly cancels rather than silently verifying behind a closed window.
 cookies=CookieManager.reset();d=open();d.callback(CALLBACK);d.back();done(d,cookies)
 check(SocialAccountController.cancels==1 && !d.isShowing)
 // Transient page errors reuse the same cookie/WebView. Expired/rate-limited attempts start anew.
 for (status in listOf(400,401,403,410,429)) {
  cookies=CookieManager.reset();d=open();d.httpFailure(INITIAL,status,true)
  check(d.errorVisible() && d.isShowing);d.retryLoad();cookies.complete()
  check(SocialAccountController.chooses==listOf("password") && d.activeScopes()==0)
 }
 // A hostile callback is never promoted into an account session.
 cookies=CookieManager.reset();SocialAccountController.accepted=false;d=open();d.callback(CALLBACK)
 check(d.errorVisible() && d.isShowing);done(d,cookies)
 // Old platform fallback needs an actual content/draw signal in addition to page completion.
 cookies=CookieManager.reset();Build.VERSION.SDK_INT=22;d=open();view=d.widget();d.pageFinished();Tasks.drain()
 check(d.showingSpinner());view.contentHeight=100;d.pageFinished();Tasks.drain();check(d.formVisible());done(d,cookies)
 // Existing globally enabled cookies and overlapping overlays retain their original preference.
 cookies=CookieManager.reset(true);d=open();val next=open();check(d.activeScopes()==2)
 d.dismiss();cookies.complete();check(next.activeScopes()==1 && cookies.acceptCookie())
 done(next,cookies);check(cookies.acceptCookie())
 // Unsupported WebView creation stops loading instead of leaving a spinner forever.
 cookies=CookieManager.reset();d=EmbeddedAccountOverlay();d.show();d.failCreation();Tasks.drain()
 check(d.errorVisible());done(d,cookies)
 println("PASS production embedded overlay: immediate loading/load, commit/frame reveal, single WebView, verified exchange, retry, Back and cookie cleanup")
}
'''


class EmbeddedAccountActivityTest(unittest.TestCase):
    def test_production_overlay_loading_readiness_and_lifecycle(self):
        source = (ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/EmbeddedAccountOverlay.kt').read_text()
        names = ('handler', 'root', 'web', 'policy', 'pendingUrl', 'expiresAt', 'accepted', 'closed', 'destroyed',
                 'loadFailed', 'cookieLease', 'pageGeneration', 'retryNewAttempt', 'overlay', 'message', 'retry',
                 'close', 'spinner', 'errorPanel')
        fields = []
        for name in names:
            match = re.search(r'^    private (?:lateinit )?(?:val|var) ' + name + r'\b[^\n]*', source, re.MULTILINE)
            self.assertIsNotNone(match, 'Production lifecycle field missing: ' + name)
            fields.append(match.group(0))
        for signature in ('private val attemptTimeout = Runnable', 'private val loadTimeout = Runnable',
                          'private val accountPoll = object : Runnable'):
            fields.append(extract_method(source, signature))
        harness = (SHELL.replace('%FIELDS%', '\n'.join(fields))
                   .replace('%METHODS%', '\n'.join(extract_method(source, signature) for signature in METHODS))
                   .replace('%CALLBACKS%', '\n'.join(extract_method(source, signature) for signature in CALLBACKS)))
        with tempfile.TemporaryDirectory(prefix='embedded-account-overlay-') as temp:
            directory = Path(temp)
            fixture = directory / 'OverlayChecks.kt'
            fixture.write_text(harness)
            bitmap = directory / 'android/graphics/Bitmap.java'
            bitmap.parent.mkdir(parents=True)
            bitmap.write_text('package android.graphics; public class Bitmap {}')
            output = directory / 'classes'
            compile_mixed([fixture, ROOT / 'app/src/main/kotlin/com/quanhoang/greenfarm3/S800x480/AuthWebViewPolicy.kt'], [bitmap], output)
            result = subprocess.run([str(jdk_tool('java')), '-cp', runtime_classpath(output), 'com.quanhoang.greenfarm3.S800x480.OverlayChecksKt'],
                                    capture_output=True, text=True, timeout=20)
            self.assertEqual(result.returncode, 0, result.stdout + result.stderr)
            self.assertIn('PASS production embedded overlay', result.stdout)


if __name__ == '__main__':
    unittest.main()
