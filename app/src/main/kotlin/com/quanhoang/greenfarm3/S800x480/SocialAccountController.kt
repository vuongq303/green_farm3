package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.util.Log
import java.security.SecureRandom
import java.util.concurrent.Executors
import javax.microedition.midlet.MIDlet

/** Platform adapter. The renderer owns all visible account UI, using the game's original components. */
class SocialAccountController private constructor() {
    companion object {
        const val SIGNED_OUT = SocialAccountFlow.SIGNED_OUT
        const val CHECKING = SocialAccountFlow.CHECKING
        const val BROWSER = SocialAccountFlow.BROWSER
        const val EXCHANGING = SocialAccountFlow.EXCHANGING
        const val WELCOME = SocialAccountFlow.WELCOME
        const val READY = SocialAccountFlow.READY
        const val ERROR = SocialAccountFlow.ERROR
        const val LOGGING_OUT = SocialAccountFlow.LOGGING_OUT
        private val worker = Executors.newSingleThreadExecutor { task -> Thread(task, "GreenFarmAccount").apply { isDaemon = true } }
        private val random = SecureRandom()
        @Volatile private var flow: SocialAccountFlow? = null
        @Volatile private var initializationError = "configuration_required"
        private var application: Context? = null
        private var configuredBaseUrl = ""
        // Owned by Android's UI thread. Website login overlays the live game window.
        private var embedded: EmbeddedAccountOverlay? = null

        private fun websiteOverlay(activity: Activity): EmbeddedAccountOverlay {
            embedded?.takeIf { it.isShowing }?.let { existing ->
                if (existing.ownerActivity === activity) return existing
                existing.dismiss()
                throw SocialAuthFailure("webview_unavailable")
            }
            return EmbeddedAccountOverlay(activity).also { overlay ->
                embedded = overlay
                overlay.setOnDismissListener { if (embedded === overlay) embedded = null }
                try { overlay.show() }
                catch (failure: Exception) {
                    if (embedded === overlay) embedded = null
                    overlay.dismiss()
                    throw failure
                }
            }
        }

        @JvmStatic @Synchronized fun initialize(context: Context, authBaseUrl: String) {
            application = context.applicationContext
            configuredBaseUrl = authBaseUrl
            if (flow != null) return
            if (Build.VERSION.SDK_INT < 23) {
                initializationError = "unsupported_device"
                return
            }
            try {
                val client = SocialAuthClient(authBaseUrl)
                val vault = SecureAccountStore(context, client.origin)
                val platform = object : SocialAccountFlow.Platform {
                    override fun now() = System.currentTimeMillis()
                    override fun randomBytes(size: Int) = ByteArray(size).also { random.nextBytes(it) }
                    override fun execute(work: () -> Unit) { worker.execute(work) }
                    override fun openEmbedded(url: String) {
                        AuthWebViewPolicy(client.origin, url)
                        val activity = MIDlet.cnA as Activity? ?: throw SocialAuthFailure("webview_unavailable")
                        if (activity.isFinishing || activity.isDestroyed) throw SocialAuthFailure("webview_unavailable")
                        activity.runOnUiThread {
                            flow?.withEmbeddedAttempt(url) { expiresAt ->
                                try {
                                    if (activity.isFinishing || activity.isDestroyed || MIDlet.cnA !== activity) {
                                        throw SocialAuthFailure("webview_unavailable")
                                    }
                                    websiteOverlay(activity).open(url, expiresAt)
                                } catch (_: Exception) { flow?.embeddedFailed("webview_unavailable") }
                            }
                        }
                    }
                    override fun openBrowser(url: String) {
                        val activity = MIDlet.cnA as Activity? ?: throw SocialAuthFailure("browser_unavailable")
                        if (activity.isFinishing) throw SocialAuthFailure("browser_unavailable")
                        activity.runOnUiThread {
                            try {
                                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addCategory(Intent.CATEGORY_BROWSABLE))
                            } catch (_: Exception) { flow?.browserFailed() }
                        }
                    }
                }
                flow = SocialAccountFlow(vault, client, platform)
            } catch (failure: SocialAuthFailure) { initializationError = failure.code }
            catch (_: Exception) { initializationError = "storage_error" }
        }

        @JvmStatic fun startupGate(): Boolean = flow?.startupGate() ?: true
        @JvmStatic fun tick() { flow?.tick() }
        @JvmStatic fun choose(provider: String) {
            val active = flow ?: return
            if (provider != "password") { active.choose(provider); return }
            val activity = MIDlet.cnA as Activity? ?: run {
                active.embeddedFailed("webview_unavailable")
                return
            }
            activity.runOnUiThread {
                if (active.status != SIGNED_OUT && active.status != ERROR) return@runOnUiThread
                if (activity.isFinishing || activity.isDestroyed || MIDlet.cnA !== activity) {
                    active.embeddedFailed("webview_unavailable"); return@runOnUiThread
                }
                try {
                    // Mount the touch-blocking spinner before any asynchronous account work.
                    websiteOverlay(activity)
                    active.choose(provider)
                } catch (_: Exception) { active.embeddedFailed("webview_unavailable") }
            }
        }
        @JvmStatic fun continueSaved() { flow?.continueSaved() }
        @JvmStatic fun logout() { flow?.logout() }
        @JvmStatic fun cancel() { flow?.cancel() }
        @JvmStatic fun retry() {
            if (flow == null) application?.let { initialize(it, configuredBaseUrl) }
            flow?.retry()
        }
        @JvmStatic fun onHostPaused() { flow?.onHostPaused() }
        @JvmStatic fun onHostResumed() { flow?.onHostResumed() }
        @JvmStatic fun status(): Int = flow?.status ?: ERROR
        @JvmStatic fun message(): String = flow?.message ?: SocialAccountFlow.messageFor(initializationError)
        @JvmStatic fun gameId(): String = flow?.gameId ?: ""
        @JvmStatic fun accountName(): String = flow?.accountName ?: ""
        @JvmStatic fun busy(): Boolean = flow?.busy ?: false

        /** The callback Activity accepts no tokens and never trusts Intent extras. */
        @JvmStatic fun recordCallback(context: Context, rawUri: String?): Boolean = receiveCallback(context, rawUri, false)

        /** Only the in-process website overlay may hand off a password-account code. */
        @JvmStatic fun recordEmbeddedCallback(context: Context, rawUri: String?): Boolean = receiveCallback(context, rawUri, true)

        private fun receiveCallback(context: Context, rawUri: String?, embedded: Boolean): Boolean {
            if (rawUri == null || rawUri.length > 2048) return rejectedCallback()
            if (Build.VERSION.SDK_INT < 23) return rejectedCallback()
            val callback = SocialAccountFlow.parseCallback(rawUri) ?: return rejectedCallback()
            return try {
                val origin = SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL)
                val vault = SecureAccountStore(context, origin)
                val expected = vault.attempt() ?: return rejectedCallback()
                if ((expected.provider == "password") != embedded) return rejectedCallback()
                if (System.currentTimeMillis() >= expected.expiresAt ||
                    !SocialAccountFlow.sameState(expected.state, callback.state)) return rejectedCallback()
                vault.saveCallback(callback)
                flow?.enqueueCallback()
                true
            } catch (_: Exception) { rejectedCallback() }
        }

        /** Non-secret launch descriptor; the PKCE verifier stays in the encrypted vault. */
        @JvmStatic fun embeddedPage(context: Context): Pair<String, Long>? = try {
            if (Build.VERSION.SDK_INT < 23) null
            else {
                initialize(context, LocalDevelopmentConfig.AUTH_BASE_URL)
                val origin = SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL)
                val vault = SecureAccountStore(context, origin)
                val attempt = vault.attempt()
                if (attempt == null || attempt.provider != "password" || vault.callback() != null ||
                    System.currentTimeMillis() >= attempt.expiresAt) null
                else {
                    AuthWebViewPolicy(origin, attempt.authorizationUrl)
                    Pair(attempt.authorizationUrl, attempt.expiresAt)
                }
            }
        } catch (_: Exception) { null }

        @JvmStatic fun closeEmbedded(context: Context, authorizationUrl: String, failure: String? = null) {
            try {
                val vault = SecureAccountStore(context, SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL))
                val attempt = vault.attempt() ?: return
                if (attempt.provider != "password" || attempt.authorizationUrl != authorizationUrl) return
                if (failure == null) {
                    flow?.cancel()
                    if (flow == null) vault.clearAttempt()
                } else if (flow?.embeddedFailed(failure) != true) vault.clearAttempt()
            } catch (_: Exception) { }
        }

        private fun rejectedCallback(): Boolean {
            try { Log.w("GreenFarmAccount", "callback_rejected") } catch (_: RuntimeException) { }
            return false
        }
    }
}
