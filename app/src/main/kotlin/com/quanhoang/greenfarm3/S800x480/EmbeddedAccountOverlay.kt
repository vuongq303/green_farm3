package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.app.Dialog
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.Window
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
import android.widget.ProgressBar
import java.io.ByteArrayInputStream
import java.util.concurrent.atomic.AtomicBoolean

/** Private own-account website only. Facebook authentication never enters this WebView. */
class EmbeddedAccountOverlay(private val host: Activity) : Dialog(host) {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var root: FrameLayout
    private lateinit var overlay: FrameLayout
    private lateinit var message: TextView
    private lateinit var retry: TextView
    private lateinit var close: TextView
    private lateinit var spinner: ProgressBar
    private lateinit var errorPanel: LinearLayout
    private var web: WebView? = null
    private var policy: AuthWebViewPolicy? = null
    private var pendingUrl = ""
    private var expiresAt = 0L
    private var accepted = false
    private var closed = false
    private var destroyed = false
    private var loadFailed = false
    private var cookieLease = false
    private var pageGeneration = 0L
    private var retryNewAttempt = false
    private val loadTimeout = Runnable { showError("Không tải được trang tài khoản trong thời gian chờ. Kiểm tra mạng và thử lại.") }
    private val attemptTimeout = Runnable { showError("Yêu cầu đăng nhập đã hết hạn. Hãy thử lại.", true) }
    private val accountPoll = object : Runnable {
        override fun run() {
            if (closed || destroyed) return
            SocialAccountController.tick()
            renderAccountState(SocialAccountController.status(), SocialAccountController.message())
            if (!closed && !destroyed) handler.postDelayed(this, 100L)
        }
    }

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setOwnerActivity(host)
        setCanceledOnTouchOutside(false)
        buildShell()
        window?.apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            addFlags(WindowManager.LayoutParams.FLAG_SECURE or WindowManager.LayoutParams.FLAG_DIM_BEHIND)
            addFlags(host.window.attributes.flags and WindowManager.LayoutParams.FLAG_FULLSCREEN)
            decorView.systemUiVisibility = host.window.decorView.systemUiVisibility
            setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
            attributes = attributes.apply { dimAmount = 0.26f }
            // API 31 blur is optional. The dim layer remains on devices without compositor blur.
            if (Build.VERSION.SDK_INT >= 31) try {
                val params = attributes
                params.javaClass.getMethod("setBlurBehindRadius", Int::class.javaPrimitiveType).invoke(params, dp(14))
                attributes = params
                addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND)
            } catch (_: Exception) { }
        }
    }

    override fun show() {
        if (closed || destroyed || isShowing) return
        showLoading()
        super.show()
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        root.post {
            if (!closed && !destroyed && !loadFailed && web == null) try {
                web = createWebView()
            } catch (_: Exception) {
                showError("Không mở được trang tài khoản. Cập nhật Android System WebView rồi thử lại.")
            }
        }
        handler.postDelayed(accountPoll, 100L)
    }

    /** Called on the host UI thread after the secure pending descriptor has been persisted. */
    fun open(url: String, expiresAt: Long) {
        if (closed || destroyed) return
        if (pendingUrl == url && web != null) return
        policy = try { AuthWebViewPolicy(SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL), url) }
            catch (_: Exception) { null }
        if (policy == null) { showError("Địa chỉ trang tài khoản không hợp lệ.", true); return }
        pendingUrl = url
        this.expiresAt = expiresAt
        // A late descriptor must not silently erase the timeout/error already shown to the player.
        if (loadFailed) { retryNewAttempt = true; return }
        handler.removeCallbacks(attemptTimeout)
        handler.postDelayed(attemptTimeout, maxOf(1L, expiresAt - System.currentTimeMillis()))
        loadPage()
    }

    /** Session verification, not receipt of a web callback, controls successful dismissal. */
    fun renderAccountState(status: Int, value: String) {
        if (closed || destroyed) return
        if (accepted && status == SocialAccountController.READY) closeAccount()
        else if (status == SocialAccountController.ERROR &&
            (!loadFailed || !retryNewAttempt || message.text.toString() != value)) showError(value, true)
    }

    private fun dp(value: Int) = (value * host.resources.displayMetrics.density + 0.5f).toInt()

    private fun panel(color: Int, radius: Int = 14): GradientDrawable = GradientDrawable().apply {
        setColor(color)
        cornerRadius = dp(radius).toFloat()
        setStroke(dp(2), Color.rgb(76, 112, 39))
    }

    private fun text(value: String, size: Float, color: Int): TextView = TextView(context).apply {
        text = value
        textSize = size
        setTextColor(color)
        gravity = Gravity.CENTER
        setPadding(dp(14), dp(8), dp(14), dp(8))
    }

    private fun buildShell() {
        root = FrameLayout(context).apply {
            isClickable = true
            isFocusable = true
        }
        overlay = FrameLayout(context).apply { isClickable = true; isFocusable = true }
        spinner = ProgressBar(context).apply {
            isIndeterminate = true
            contentDescription = "Đang tải trang tài khoản"
            if (Build.VERSION.SDK_INT >= 21) indeterminateTintList = ColorStateList.valueOf(Color.rgb(255, 239, 190))
        }
        overlay.addView(spinner, FrameLayout.LayoutParams(dp(44), dp(44), Gravity.CENTER))
        errorPanel = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.CENTER
            setPadding(dp(18), dp(14), dp(18), dp(14))
            background = panel(Color.rgb(255, 243, 205), 14)
            visibility = View.GONE
        }
        message = text("", 16f, Color.rgb(41, 87, 32))
        errorPanel.addView(message, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT))
        retry = text("Thử lại", 16f, Color.WHITE).apply {
            background = panel(Color.rgb(83, 133, 43), 12)
            setOnClickListener { retryPage() }
        }
        errorPanel.addView(retry, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(44)))
        val cancel = text("Đóng", 16f, Color.rgb(33, 83, 29)).apply { setOnClickListener { closeAccount() } }
        errorPanel.addView(cancel, LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dp(44)))
        overlay.addView(errorPanel, FrameLayout.LayoutParams(minOf(dp(360), host.resources.displayMetrics.widthPixels - dp(32)),
            ViewGroup.LayoutParams.WRAP_CONTENT, Gravity.CENTER))
        root.addView(overlay, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        close = text("Đóng", 15f, Color.rgb(33, 83, 29)).apply {
            background = panel(Color.rgb(255, 239, 190), 10)
            contentDescription = "Đóng trang tài khoản và quay lại trò chơi"
            visibility = View.GONE
            setOnClickListener { closeAccount() }
        }
        root.addView(close, FrameLayout.LayoutParams(dp(88), dp(38), Gravity.TOP or Gravity.END).apply {
            topMargin = dp(7); rightMargin = dp(12)
        })
        setContentView(root)
    }

    @Suppress("DEPRECATION")
    private fun createWebView(): WebView {
        val view = WebView(context)
        view.isSaveEnabled = false
        view.alpha = 0f
        view.setBackgroundColor(Color.WHITE)
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
            override fun onPageCommitVisible(view: WebView?, url: String?) {
                if (Build.VERSION.SDK_INT >= 23 && view != null && url != null) {
                    val generation = pageGeneration
                    view.postVisualStateCallback(generation, object : WebView.VisualStateCallback() {
                        override fun onComplete(requestId: Long) { revealPage(view, url, generation) }
                    })
                }
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                // Older WebViews lack commit-visible/frame callbacks. Wait for a posted draw frame,
                // nonzero document height and a live trusted URL before showing their content.
                if (Build.VERSION.SDK_INT < 23 && view != null && url != null) {
                    val generation = pageGeneration
                    view.postOnAnimation { if (view.contentHeight > 0) revealPage(view, url, generation) }
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
                    400, 401, 403, 410 -> showError("Yêu cầu đăng nhập đã hết hạn. Hãy thử lại.", true)
                    429 -> showError("Bạn đã thử quá nhiều lần. Vui lòng đợi rồi thử lại.", true)
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
        root.addView(view, 0, FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
        return view
    }

    private fun resource(url: String): WebResourceResponse? = if (!destroyed && policy?.allowResource(url) == true) null else
        WebResourceResponse("text/plain", "UTF-8", 403, "Blocked", mapOf("Cache-Control" to "no-store"), ByteArrayInputStream(ByteArray(0)))

    private fun navigate(url: String, mainFrame: Boolean): Boolean {
        if (destroyed || closed) return true
        if (mainFrame && policy?.isCallback(url) == true) {
            if (SocialAccountController.recordEmbeddedCallback(context, url)) {
                accepted = true
                showLoading()
                // Verification owns its existing native deadline; the page-load timeout no longer applies.
                handler.removeCallbacks(loadTimeout)
                handler.removeCallbacks(attemptTimeout)
            } else {
                showError("Kết quả đăng nhập không hợp lệ. Hãy thử lại.", true)
            }
            return true
        }
        val allowed = if (mainFrame) policy?.allowMainFrame(url) == true else policy?.allowResource(url) == true
        if (!allowed && mainFrame) showError("Chỉ trang tài khoản Green Farm 3 được phép mở trong game.")
        return !allowed
    }

    private fun showLoading() {
        if (destroyed || closed) return
        pageGeneration++
        loadFailed = false
        retryNewAttempt = false
        web?.alpha = 0f
        spinner.visibility = View.VISIBLE
        errorPanel.visibility = View.GONE
        close.visibility = View.GONE
        overlay.visibility = View.VISIBLE
        handler.removeCallbacks(loadTimeout)
        handler.postDelayed(loadTimeout, 30_000L)
    }

    private fun revealPage(view: WebView, url: String, generation: Long) {
        if (destroyed || closed || accepted || loadFailed || generation != pageGeneration || view !== web ||
            policy?.allowMainFrame(url) != true || view.url != url) return
        handler.removeCallbacks(loadTimeout)
        try { CookieManager.getInstance().flush() } catch (_: Exception) { }
        view.alpha = 1f
        spinner.visibility = View.GONE
        overlay.visibility = View.GONE
        close.visibility = View.VISIBLE
    }

    private fun showError(value: String, newAttempt: Boolean = false) {
        if (destroyed || closed || !::overlay.isInitialized) return
        pageGeneration++
        loadFailed = true
        retryNewAttempt = newAttempt || accepted || pendingUrl.isEmpty()
        web?.stopLoading()
        web?.alpha = 0f
        handler.removeCallbacks(loadTimeout)
        message.text = value
        spinner.visibility = View.GONE
        errorPanel.visibility = View.VISIBLE
        close.visibility = View.GONE
        overlay.visibility = View.VISIBLE
    }

    private fun retryPage() {
        if (closed || destroyed) return
        if (retryNewAttempt) {
            accepted = false
            closeAccount()
            SocialAccountController.choose("password")
        } else loadPage()
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
        if (accepted) {
            accepted = false
            closeAccount()
            return
        }
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
        if (!(accepted && SocialAccountController.status() == SocialAccountController.READY)) {
            if (pendingUrl.isNotEmpty()) SocialAccountController.closeEmbedded(context, pendingUrl)
            // The encrypted attempt is consumed before exchange; cancellation must also invalidate
            // an in-flight exchange whose attempt is no longer present in the vault.
            SocialAccountController.cancel()
        }
        dispose()
        super.dismiss()
    }

    override fun dismiss() { closeAccount() }

    override fun onDetachedFromWindow() {
        closeAccount()
        dispose()
        super.onDetachedFromWindow()
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

    private fun dispose() {
        if (destroyed) return
        destroyed = true
        handler.removeCallbacksAndMessages(null)
        if (cookieLease) {
            cookieLease = false
            releaseCookieAcceptance(policy?.origin)
        }
        destroyWebView()
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

}
