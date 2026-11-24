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
import org.json.JSONObject

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
        private var authClient: SocialAuthClient? = null

        @JvmStatic @Synchronized fun initialize(context: Context, authBaseUrl: String) {
            application = context.applicationContext
            configuredBaseUrl = authBaseUrl
            if (flow != null) return
            if (Build.VERSION.SDK_INT < 23) {
                initializationError = "unsupported_device"
                logStage("initialization", initializationError)
                return
            }
            try {
                val client = SocialAuthClient(authBaseUrl)
                val vault = SecureAccountStore(context, client.origin)
                val platform = object : SocialAccountFlow.Platform {
                    override fun now() = System.currentTimeMillis()
                    override fun randomBytes(size: Int) = ByteArray(size).also { random.nextBytes(it) }
                    override fun execute(work: () -> Unit) { worker.execute(work) }
                    override fun authStage(stage: String, code: String) = logStage(stage, code)
                    override fun authRecovery(stage: String, reason: String) {
                        Log.w("GreenFarmAccount", "auth_recovery stage=$stage reason=$reason")
                    }
                    override fun openBrowser(url: String) {
                        val activity = hostActivity() ?: throw SocialAuthFailure("browser_unavailable")
                        activity.runOnUiThread {
                            if (flow?.canOpenBrowser(url) != true) return@runOnUiThread
                            try {
                                activity.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)).addCategory(Intent.CATEGORY_BROWSABLE))
                                logStage("browser_launch", "ok")
                            } catch (_: Exception) { flow?.browserFailed(url) }
                        }
                    }
                }
                authClient = client
                flow = SocialAccountFlow(vault, client, platform)
                logStage("initialization", "ok")
            } catch (failure: SocialAuthFailure) {
                initializationError = failure.code
                logStage("initialization", initializationError)
            } catch (_: Exception) {
                initializationError = "storage_error"
                logStage("initialization", initializationError)
            }
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
        @JvmStatic fun message(language: Int): String {
            val active = flow
            return SocialAccountText.message(language, if (active == null) initializationError else active.failureCode,
                active?.busy == true)
        }

        @JvmStatic fun withReadyAccess(operation: (SocialIdentity, String) -> Unit, onFailure: (String) -> Unit) {
            val active = flow
            if (active == null) worker.execute { onFailure(initializationError) }
            else active.withReadyAccess(operation, onFailure)
        }

        internal fun giftRequest(method: String, path: String, payload: JSONObject?, bearer: String): JSONObject =
            (authClient ?: throw SocialAuthFailure("configuration_required")).giftRequest(method, path, payload, bearer)
        @JvmStatic fun gameId(): String = flow?.gameId ?: ""
        @JvmStatic fun accountName(): String = flow?.accountName ?: ""
        @JvmStatic fun busy(): Boolean = flow?.busy ?: false

        @JvmStatic fun reportWelcomeLayoutFailure(failure: RuntimeException) {
            Log.w("GreenFarmAccount", "auth stage=welcome_layout code=font_measurement_failed type=${failure.javaClass.simpleName}")
        }

        /** cnA belongs to the launcher; cnz is the running MIDlet/game Activity. */
        internal fun hostActivity(): Activity? {
            val game = MIDlet.cnz as Activity?
            if (game?.let(::isUsable) == true) return game
            val launcher = MIDlet.cnA as Activity?
            return launcher?.takeIf(::isUsable)
        }

        private fun isUsable(activity: Activity): Boolean =
            !activity.isFinishing && (Build.VERSION.SDK_INT < 17 || !activity.isDestroyed)

        /** The callback Activity accepts no tokens and never trusts Intent extras. */
        @JvmStatic fun recordCallback(context: Context, rawUri: String?): Boolean {
            if (rawUri == null || rawUri.length > 2048) return rejectedCallback("invalid_callback")
            if (Build.VERSION.SDK_INT < 23) return rejectedCallback("unsupported_device")
            val callback = SocialAccountFlow.parseCallback(rawUri) ?: return rejectedCallback("invalid_callback")
            var matchedPending = false
            return try {
                val origin = SocialAuthClient.validateOrigin(LocalDevelopmentConfig.AUTH_BASE_URL)
                val vault = SecureAccountStore(context, origin)
                val expected = vault.attempt() ?: return rejectedCallback("missing_attempt")
                if (expected.provider !in arrayOf("password", "facebook") ||
                    !SocialAccountFlow.sameState(expected.state, callback.state)) return rejectedCallback("invalid_callback")
                if (System.currentTimeMillis() >= expected.expiresAt) return rejectedCallback("attempt_expired")
                matchedPending = true
                // Persist before waking the host. A cold process recovers the same state/PKCE attempt.
                // Only exchangeSaved may turn this one-use code into a verified session.
                vault.saveCallback(callback)
                logStage("callback_storage", "ok")
                flow?.enqueueCallback()
                true
            } catch (failure: Exception) {
                val code = (failure as? SocialAuthFailure)?.code ?: "storage_error"
                if (matchedPending) flow?.callbackStorageFailed()
                logStage("callback_storage", code)
                false
            }
        }

        internal fun logStage(stage: String, code: String) {
            val safeStage = stage.takeIf { it.matches(Regex("[a-z_]{1,48}")) } ?: "unknown"
            val safeCode = code.takeIf { it.matches(Regex("[a-z_]{1,48}")) } ?: "unknown"
            try { Log.w("GreenFarmAccount", "auth stage=$safeStage code=$safeCode") } catch (_: RuntimeException) { }
        }

        private fun rejectedCallback(code: String): Boolean {
            logStage("callback_validation", code)
            return false
        }
    }
}
