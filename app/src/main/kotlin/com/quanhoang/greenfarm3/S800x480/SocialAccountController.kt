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
                    override fun authRecovery(stage: String, reason: String) {
                        Log.w("GreenFarmAccount", "auth_recovery stage=$stage reason=$reason")
                    }
                    override fun openBrowser(url: String) {
                        val activity = hostActivity() ?: throw SocialAuthFailure("browser_unavailable")
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
        @JvmStatic fun choose(provider: String) { flow?.choose(provider) }
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

        /** cnA belongs to the launcher; cnz is the running MIDlet/game Activity. */
        internal fun hostActivity(): Activity? {
            val game = MIDlet.cnz as Activity?
            if (game != null && !game.isFinishing && !game.isDestroyed) return game
            val launcher = MIDlet.cnA as Activity?
            return if (launcher != null && !launcher.isFinishing && !launcher.isDestroyed) launcher else null
        }

        /** The callback Activity accepts no tokens and never trusts Intent extras. */
        @JvmStatic fun recordCallback(context: Context, rawUri: String?): Boolean {
            if (rawUri == null || rawUri.length > 2048) return rejectedCallback()
            if (Build.VERSION.SDK_INT < 23) return rejectedCallback()
            val callback = SocialAccountFlow.parseCallback(rawUri) ?: return rejectedCallback()
            return try {
                val origin = SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL)
                val vault = SecureAccountStore(context, origin)
                val expected = vault.attempt() ?: return rejectedCallback()
                if (expected.provider !in arrayOf("password", "facebook") ||
                    System.currentTimeMillis() >= expected.expiresAt ||
                    !SocialAccountFlow.sameState(expected.state, callback.state)) return rejectedCallback()
                // Persist before waking the host. A cold process recovers the same state/PKCE attempt.
                // Only exchangeSaved may turn this one-use code into a verified session.
                vault.saveCallback(callback)
                flow?.enqueueCallback()
                true
            } catch (_: Exception) { rejectedCallback() }
        }

        private fun rejectedCallback(): Boolean {
            try { Log.w("GreenFarmAccount", "callback_rejected") } catch (_: RuntimeException) { }
            return false
        }
    }
}
