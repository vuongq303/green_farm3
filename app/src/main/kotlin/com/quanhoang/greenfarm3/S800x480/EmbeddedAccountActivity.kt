package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.ClientCertRequest
import android.webkit.CookieManager
import android.webkit.GeolocationPermissions
import android.webkit.HttpAuthHandler
import android.webkit.JsPromptResult
import android.webkit.JsResult
import android.webkit.PermissionRequest
import android.webkit.RenderProcessGoneDetail
import android.webkit.SafeBrowsingResponse
import android.webkit.SslErrorHandler
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import java.io.ByteArrayInputStream
import java.util.concurrent.atomic.AtomicBoolean

/** Private own-account website only. Facebook authentication never enters this WebView. */
class EmbeddedAccountActivity : Activity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var root: FrameLayout
    private lateinit var overlay: FrameLayout
    private lateinit var message: TextView
    private lateinit var retry: TextView
    private var web: WebView? = null
    private var policy: AuthWebViewPolicy? = null
    private var pendingUrl = ""
    private var expiresAt = 0L
    private var accepted = false
    private var closed = false
    private var destroyed = false
    private var loadFailed = false
    private var cookieLease = false
    private val loadTimeout = Runnable { showError("Không tải được trang tài khoản trong thời gian chờ. Kiểm tra mạng và thử lại.") }
    private val attemptTimeout = Runnable { failAccount("attempt_expired") }

    override fun onCreate(state: Bundle?) {
        super.onCreate(state)
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        val page = SocialAccountController.embeddedPage(this)
        if (page == null) { finish(); return }
        pendingUrl = page.first
        expiresAt = page.second
        policy = try { AuthWebViewPolicy(SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL), pendingUrl) }
            catch (_: Exception) { null }
        if (policy == null) { finish(); return }
        buildShell()
        handler.postDelayed(attemptTimeout, maxOf(1L, expiresAt - System.currentTimeMillis()))
        loadPage()
    }

    private fun dp(value: Int) = (value * resources.displayMetrics.density + 0.5f).toInt()

    private fun panel(color: Int, radius: Int = 14): GradientDrawable = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radius).toFloat()
        setStroke(dp(2), Color.rgb(76, 112, 39))
    }

    private fun text(value: String, size: Float, color: Int): TextView = TextView(this).apply {
        text = value
        textSize = size
        setTextColor(color)
        gravity = Gravity.CENTER
        setPadding(dp(14), dp(8), dp(14), dp(8))
    }

    private fun buildShell() {
        root = FrameLayout(this).apply { setBackgroundColor(Color.rgb(24, 69, 33)) }
        val brand = text("Green Farm 3", 19f, Color.rgb(255, 240, 187)).apply { setTypeface(typeface, Typeface.BOLD) }
        root.addView(brand, FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, dp(52), Gravity.TOP or Gravity.START))
        val close = text("Đóng", 15f, Color.rgb(33, 83, 29)).apply {
            background = panel(Color.rgb(255, 239, 190), 10)
            contentDescription = "Đóng trang tài khoản và quay lại trò chơi"
            setOnClickListener { closeAccount() }
        }
        root.addView(close, FrameLayout.LayoutParams(dp(88), dp(38), Gravity.TOP or Gravity.END).apply {
            topMargin = dp(7); rightMargin = dp(12)
        })
        overlay = FrameLayout(this).apply { setBackgroundColor(0xCC12351B.toInt()) }
        val card = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(18), dp(14), dp(18), dp(14))
            background = panel(Color.rgb(255, 243, 205), 20)
        }
        card.addView(FarmSpinner(), LinearLayout.LayoutParams(dp(46), dp(46)))
        message = text("Đang tải trang tài khoản...", 16f, Color.rgb(41, 87, 32))
        card.addView(message, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        retry = text("Thử lại", 16f, Color.WHITE).apply {
            background = panel(Color.rgb(83, 133, 43), 12)
            visibility = View.GONE
            setOnClickListener { loadPage() }
        }
        card.addView(retry, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(44)).apply { topMargin = dp(8) })
        overlay.addView(card, FrameLayout.LayoutParams(minOf(dp(360), resources.displayMetrics.widthPixels - dp(32)),
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER))
        root.addView(overlay, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply { topMargin = dp(52) })
        setContentView(root)
    }

    @Suppress("DEPRECATION")
    private fun createWebView(): WebView {
        val view = WebView(this)
        view.isSaveEnabled = false
        if (Build.VERSION.SDK_INT >= 26) view.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO_EXCLUDE_DESCENDANTS
        WebView.setWebContentsDebuggingEnabled(false)
        view.settings.apply {
            javaScriptEnabled = true // Same-origin form script only; no native JS interface.
            domStorageEnabled = false
            databaseEnabled = false
            allowFileAccess = false
            allowContentAccess = false
            allowFileAccessFromFileURLs = false
            allowUniversalAccessFromFileURLs = false
            mixedContentMode = WebSettings.MIXED_CONTENT_NEVER_ALLOW
            javaScriptCanOpenWindowsAutomatically = false
            setSupportMultipleWindows(false)
            setGeolocationEnabled(false)
            savePassword = false
            saveFormData = false
            cacheMode = WebSettings.LOAD_NO_CACHE
            if (Build.VERSION.SDK_INT >= 26) safeBrowsingEnabled = true
        }
        CookieManager.getInstance().setAcceptThirdPartyCookies(view, false)
        view.webChromeClient = object : WebChromeClient() {
            override fun onPermissionRequest(request: PermissionRequest) { request.deny() }
            override fun onGeolocationPermissionsShowPrompt(origin: String?, callback: GeolocationPermissions.Callback?) {
                callback?.invoke(origin, false, false)
            }
            override fun onCreateWindow(view: WebView?, dialog: Boolean, gesture: Boolean, result: android.os.Message?) = false
            override fun onShowFileChooser(view: WebView?, callback: ValueCallback<Array<Uri>>?, parameters: FileChooserParams?): Boolean {
                callback?.onReceiveValue(null); return true
            }
            override fun onJsAlert(view: WebView?, url: String?, message: String?, result: JsResult): Boolean { result.cancel(); return true }
            override fun onJsConfirm(view: WebView?, url: String?, message: String?, result: JsResult): Boolean { result.cancel(); return true }
            override fun onJsPrompt(view: WebView?, url: String?, message: String?, default: String?, result: JsPromptResult): Boolean { result.cancel(); return true }
        }
        view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean = navigate(request.url.toString(), request.isForMainFrame)
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean = navigate(url, true)
            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest): WebResourceResponse? = resource(request.url.toString())
            override fun shouldInterceptRequest(view: WebView?, url: String): WebResourceResponse? = resource(url)
            override fun onPageStarted(view: WebView?, url: String?, icon: android.graphics.Bitmap?) {
                if (destroyed || url == null) return
                if (policy?.allowMainFrame(url) != true) { view?.stopLoading(); showError("Đã chặn điều hướng không hợp lệ."); return }
                showLoading()
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                if (!destroyed && !loadFailed && url != null && policy?.allowMainFrame(url) == true) {
                    handler.removeCallbacks(loadTimeout)
                    try { CookieManager.getInstance().flush() } catch (_: Exception) { }
                    overlay.visibility = View.GONE
                }
            }
            override fun onReceivedError(view: WebView?, request: WebResourceRequest, error: WebResourceError?) {
                if (request.isForMainFrame || policy?.isRequiredAsset(request.url.toString()) == true) {
                    showError("Không kết nối được trang tài khoản. Kiểm tra mạng và thử lại.")
                }
            }
            override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest, response: WebResourceResponse?) {
                if (!request.isForMainFrame) {
                    if (policy?.isRequiredAsset(request.url.toString()) == true) {
                        showError("Không tải được giao diện tài khoản. Kiểm tra mạng và thử lại.")
                    }
                    return // Credential/API failures remain owned by the form script.
                }
                when (response?.statusCode) {
                    400, 401, 403, 410 -> failAccount("attempt_expired")
                    429 -> failAccount("rate_limited")
                    else -> showError("Máy chủ tài khoản chưa sẵn sàng. Kiểm tra cấu hình HTTPS và thử lại.")
                }
            }
            override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler, error: SslError?) {
                handler.cancel()
                showError("Không thể xác minh chứng chỉ HTTPS. Trang tài khoản đã bị chặn.")
            }
            override fun onReceivedHttpAuthRequest(view: WebView?, handler: HttpAuthHandler, host: String?, realm: String?) { handler.cancel() }
            override fun onReceivedClientCertRequest(view: WebView?, request: ClientCertRequest) { request.cancel() }
            override fun onSafeBrowsingHit(view: WebView?, request: WebResourceRequest?, threatType: Int, callback: SafeBrowsingResponse) {
                callback.backToSafety(true)
                showError("Trang tài khoản không an toàn và đã bị chặn.")
            }
            override fun onRenderProcessGone(view: WebView?, detail: RenderProcessGoneDetail?): Boolean {
                destroyWebView()
                showError("Trang tài khoản đã dừng. Bạn có thể thử tải lại.")
                return true
            }
        }
        view.setDownloadListener { _, _, _, _, _ -> showError("Trang tài khoản không hỗ trợ tải tệp.") }
        root.addView(view, 1, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply { topMargin = dp(52) })
        return view
    }

    private fun resource(url: String): WebResourceResponse? = if (!destroyed && policy?.allowResource(url) == true) null else
        WebResourceResponse("text/plain", "UTF-8", 403, "Blocked", mapOf("Cache-Control" to "no-store"), ByteArrayInputStream(ByteArray(0)))

    private fun navigate(url: String, mainFrame: Boolean): Boolean {
        if (destroyed || closed) return true
        if (mainFrame && policy?.isCallback(url) == true) {
            if (SocialAccountController.recordEmbeddedCallback(this, url)) {
                accepted = true
                closeAccount()
            } else {
                failAccount("invalid_callback")
            }
            return true
        }
        val allowed = if (mainFrame) policy?.allowMainFrame(url) == true else policy?.allowResource(url) == true
        if (!allowed && mainFrame) showError("Chỉ trang tài khoản Green Farm 3 được phép mở trong game.")
        return !allowed
    }

    private fun showLoading() {
        if (destroyed || closed) return
        loadFailed = false
        message.text = "Đang tải trang tài khoản..."
        retry.visibility = View.GONE
        overlay.visibility = View.VISIBLE
        handler.removeCallbacks(loadTimeout)
        handler.postDelayed(loadTimeout, 30_000L)
    }

    private fun showError(value: String) {
        if (destroyed || closed || !::overlay.isInitialized) return
        loadFailed = true
        web?.stopLoading()
        handler.removeCallbacks(loadTimeout)
        message.text = value
        retry.visibility = View.VISIBLE
        overlay.visibility = View.VISIBLE
    }

    private fun loadPage() {
        if (closed || destroyed) return
        if (System.currentTimeMillis() >= expiresAt) { attemptTimeout.run(); return }
        showLoading()
        try {
            val view = web ?: createWebView().also { web = it }
            if (!cookieLease) {
                acquireCookieAcceptance()
                cookieLease = true
            }
            // Retain the short-lived attempt cookie across reload/process recovery.
            // The server binds it to this pending attempt and replaces it for a new one.
            view.loadUrl(pendingUrl)
        } catch (_: Exception) { showError("Không mở được trang tài khoản. Cập nhật Android System WebView rồi thử lại.") }
    }

    @Suppress("DEPRECATION")
    override fun onBackPressed() {
        val view = web
        if (!loadFailed && view != null && view.canGoBack()) {
            val history = view.copyBackForwardList()
            if (policy?.allowMainFrame(history.getItemAtIndex(history.currentIndex - 1).url) == true) {
                view.goBack(); return
            }
        }
        closeAccount()
    }

    private fun closeAccount() {
        if (closed) return
        closed = true
        handler.removeCallbacksAndMessages(null)
        if (!accepted && pendingUrl.isNotEmpty()) SocialAccountController.closeEmbedded(this, pendingUrl)
        finish()
    }

    private fun failAccount(reason: String) {
        SocialAccountController.closeEmbedded(this, pendingUrl, reason)
        closeAccount()
    }

    private fun destroyWebView() {
        web?.let { view ->
            view.stopLoading()
            view.settings.javaScriptEnabled = false
            view.clearHistory()
            view.clearFormData()
            (view.parent as? ViewGroup)?.removeView(view)
            view.removeAllViews()
            view.destroy()
        }
        web = null
    }

    override fun onDestroy() {
        destroyed = true
        handler.removeCallbacksAndMessages(null)
        if (isFinishing && !closed && !accepted && pendingUrl.isNotEmpty()) {
            SocialAccountController.closeEmbedded(this, pendingUrl)
        }
        if (cookieLease) {
            cookieLease = false
            releaseCookieAcceptance(if (isFinishing || closed || accepted) policy?.origin else null)
        }
        destroyWebView()
        super.onDestroy()
    }

    companion object {
        private val cookieLock = Any()
        private var cookieUsers = 0
        private var previousCookieAcceptance = true

        /** Legacy survey code disables cookies globally; enable first-party auth only while owned. */
        private fun acquireCookieAcceptance() = synchronized(cookieLock) {
            val cookies = CookieManager.getInstance()
            if (cookieUsers == 0) previousCookieAcceptance = cookies.acceptCookie()
            cookies.setAcceptCookie(true)
            cookieUsers++
        }

        private fun releaseCookieAcceptance(expireOrigin: String?) {
            val cookies = CookieManager.getInstance()
            val released = AtomicBoolean(false)
            val release = {
                if (released.compareAndSet(false, true)) {
                    synchronized(cookieLock) {
                        cookieUsers--
                        if (cookieUsers == 0) cookies.setAcceptCookie(previousCookieAcceptance)
                    }
                }
                Unit
            }
            if (expireOrigin == null) { release(); return }
            try {
                // Expire only the first-party auth cookie; no global cookie/cache/profile deletion.
                cookies.setCookie(expireOrigin,
                    "__Host-gf_auth_web=; Max-Age=0; Path=/; Secure; HttpOnly; SameSite=Strict") {
                    try { cookies.flush() } catch (_: Exception) { } finally { release() }
                }
            } catch (_: Exception) { release() }
        }
    }

    private inner class FarmSpinner : View(this@EmbeddedAccountActivity) {
        private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply { style = Paint.Style.STROKE; strokeWidth = dp(4).toFloat(); strokeCap = Paint.Cap.ROUND }
        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            val margin = dp(6).toFloat()
            paint.color = Color.rgb(221, 196, 108)
            canvas.drawArc(margin, margin, width - margin, height - margin, 0f, 360f, false, paint)
            paint.color = Color.rgb(75, 128, 41)
            canvas.drawArc(margin, margin, width - margin, height - margin, (SystemClock.uptimeMillis() % 1000L) * 0.36f, 95f, false, paint)
            if (!destroyed && !closed && isShown) postInvalidateDelayed(32L)
        }
    }
}
