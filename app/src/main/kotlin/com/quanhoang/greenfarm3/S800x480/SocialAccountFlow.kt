package com.quanhoang.greenfarm3.S800x480

import java.nio.charset.StandardCharsets
import java.net.URI
import java.net.URLDecoder
import java.security.MessageDigest
import java.util.concurrent.ConcurrentLinkedQueue

/** Account identity only. This module has no dependency on the farm or its SQLite store. */
data class SocialIdentity(val gameId: String, val provider: String, val displayName: String)
data class SocialSession(val accessToken: String, val refreshToken: String,
    val accessExpiresAt: Long, val refreshExpiresAt: Long, val user: SocialIdentity) {
    override fun toString() = "SocialSession(credentials=<redacted>)"
}
data class SocialAttempt(val provider: String, val state: String, val verifier: String,
    val authorizationUrl: String, val expiresAt: Long) {
    override fun toString() = "SocialAttempt(credentials=<redacted>)"
}
data class SocialCallback(val state: String, val code: String?, val error: String?) {
    override fun toString() = "SocialCallback(credentials=<redacted>)"
}
data class SocialAttemptReply(val authorizationUrl: String, val expiresIn: Long) {
    override fun toString() = "SocialAttemptReply(credentials=<redacted>)"
}
class SocialAuthFailure(val code: String) : RuntimeException(code)

/** Deterministic flow; platform/network results are only applied by tick on the game thread. */
class SocialAccountFlow(private val vault: Vault, private val backend: Backend, private val platform: Platform) {
    interface Vault {
        fun session(): SocialSession?
        fun saveSession(value: SocialSession)
        fun clearSession()
        fun attempt(): SocialAttempt?
        fun saveAttempt(value: SocialAttempt)
        fun callback(): SocialCallback?
        fun saveCallback(value: SocialCallback)
        /** Removes only pending auth/callback records, never any game data. */
        fun clearAttempt()
    }
    interface Backend {
        fun attempt(provider: String, state: String, challenge: String): SocialAttemptReply
        fun exchange(code: String, verifier: String): SocialSession
        fun me(accessToken: String): SocialIdentity
        fun refresh(refreshToken: String): SocialSession
        fun logout(accessToken: String, refreshToken: String)
    }
    interface Platform {
        fun now(): Long
        fun randomBytes(size: Int): ByteArray
        fun execute(work: () -> Unit)
        /** Open the system browser; implementations marshal this operation to Android's UI thread. */
        fun openBrowser(url: String)
        /** Only the private account website is embedded; providers stay in the system browser. */
        fun openEmbedded(url: String)
    }

    companion object {
        const val SIGNED_OUT = 0
        const val CHECKING = 1
        const val BROWSER = 2
        const val EXCHANGING = 3
        const val WELCOME = 4
        const val READY = 5
        const val ERROR = 6
        const val LOGGING_OUT = 7
        const val REDIRECT_URI = "com.quanhoang.greenfarm3://oauth/callback"

        @JvmStatic fun sameState(expected: String, actual: String): Boolean = MessageDigest.isEqual(
            expected.toByteArray(StandardCharsets.US_ASCII), actual.toByteArray(StandardCharsets.US_ASCII))

        @JvmStatic fun base64Url(bytes: ByteArray): String {
            val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_"
            val result = StringBuilder((bytes.size * 4 + 2) / 3)
            var value = 0
            var bits = 0
            for (byte in bytes) {
                value = (value shl 8) or (byte.toInt() and 255)
                bits += 8
                while (bits >= 6) {
                    bits -= 6
                    result.append(alphabet[(value ushr bits) and 63])
                }
            }
            if (bits > 0) result.append(alphabet[(value shl (6 - bits)) and 63])
            return result.toString()
        }

        @JvmStatic fun challenge(verifier: String): String = base64Url(
            MessageDigest.getInstance("SHA-256").digest(verifier.toByteArray(StandardCharsets.US_ASCII)))

        @JvmStatic fun parseCallback(value: String): SocialCallback? = try {
            val uri = URI(value)
            if (uri.scheme != "com.quanhoang.greenfarm3" || uri.rawAuthority != "oauth" ||
                uri.rawPath != "/callback" || uri.rawFragment != null) null
            else {
                val values = HashMap<String, String>()
                for (part in uri.rawQuery.orEmpty().split('&')) {
                    val pair = part.split('=', limit = 2)
                    if (pair.size != 2) throw IllegalArgumentException()
                    val name = URLDecoder.decode(pair[0], "UTF-8")
                    val text = URLDecoder.decode(pair[1], "UTF-8")
                    if (name !in arrayOf("state", "code", "error") || values.put(name, text) != null) throw IllegalArgumentException()
                }
                val state = values["state"] ?: throw IllegalArgumentException()
                val code = values["code"]
                val error = values["error"]
                if (!state.matches(Regex("[A-Za-z0-9_-]{32,128}")) || (code == null) == (error == null) ||
                    (code != null && !code.matches(Regex("[A-Za-z0-9_-]{32,128}"))) ||
                    (error != null && error !in arrayOf("access_denied", "authentication_failed", "attempt_expired"))) null
                else SocialCallback(state, code, error)
            }
        } catch (_: Exception) { null }

        @JvmStatic fun messageFor(code: String): String = when (code) {
            "configuration_required" -> "Chưa cấu hình domain HTTPS cho backend tài khoản."
            "auth_not_configured" -> "Backend tài khoản chưa được cấu hình HTTPS. Hãy kiểm tra cấu hình máy chủ."
            "oauth_not_configured" -> "Phương thức đăng nhập đã chọn chưa được cấu hình trên backend."
            "database_unavailable" -> "Máy chủ tài khoản tạm thời không sẵn sàng. Vui lòng thử lại."
            "unsupported_device" -> "Đăng nhập an toàn cần Android 6.0 trở lên. Dữ liệu nông trại vẫn được giữ nguyên."
            "storage_error" -> "Không thể đọc hoặc lưu phiên đăng nhập an toàn. Hãy thử đăng nhập lại."
            "invalid_callback" -> "Kết quả đăng nhập không hợp lệ. Hãy thử lại."
            "access_denied" -> "Bạn đã hủy đăng nhập. Có thể thử lại."
            "authentication_failed" -> "Không thể xác thực tài khoản. Vui lòng thử lại."
            "attempt_expired", "invalid_code", "expired_code" -> "Yêu cầu đăng nhập đã hết hạn. Hãy thử lại."
            "invalid_session", "session_expired", "invalid_refresh_token" -> "Phiên đã hết hạn. Vui lòng đăng nhập lại."
            "rate_limited" -> "Bạn đã thử quá nhiều lần. Vui lòng đợi rồi thử lại."
            "browser_unavailable" -> "Không mở được trình duyệt. Hãy kiểm tra trình duyệt trên thiết bị rồi thử lại."
            "webview_unavailable" -> "Không mở được trang tài khoản trong game. Hãy cập nhật Android System WebView rồi thử lại."
            "webview_closed" -> "Đăng nhập tài khoản chưa hoàn tất. Bạn có thể thử lại."
            "browser_closed" -> "Đăng nhập chưa hoàn tất trong trình duyệt. Bạn có thể thử lại."
            "timeout" -> "Kết nối xác thực quá thời gian chờ. Hãy kiểm tra mạng và thử lại."
            "tls_error" -> "Không thể xác minh kết nối HTTPS. Hãy kiểm tra chứng chỉ server và ngày giờ thiết bị."
            "network_error" -> "Không kết nối được backend. Hãy kiểm tra mạng rồi thử lại."
            else -> "Đăng nhập chưa thành công. Vui lòng thử lại."
        }
    }

    @Volatile private var generation = 0L
    private val completions = ConcurrentLinkedQueue<() -> Unit>()
    private var initialized = false
    private var currentSession: SocialSession? = null
    private var pending: SocialAttempt? = null
    private var browserWasPaused = false
    private var resumedAt = 0L
    private var operationDeadline = 0L
    var status: Int = SIGNED_OUT; private set
    var message: String = "Vui lòng đăng nhập tài khoản để tiếp tục"; private set
    val gameId: String get() = currentSession?.user?.gameId ?: ""
    /** A remembered name is visible only after this process verifies the session with the backend. */
    val accountName: String get() = if (status == WELCOME || status == READY)
        currentSession?.user?.displayName.orEmpty().trim() else ""
    val busy: Boolean get() = status == CHECKING || status == BROWSER || status == EXCHANGING || status == LOGGING_OUT
    val needsGate: Boolean get() = status != READY

    @Synchronized fun startupGate(): Boolean {
        if (!initialized) {
            initialized = true
            recover()
        }
        return needsGate
    }

    @Synchronized fun tick() {
        if ((status == CHECKING || status == EXCHANGING || status == LOGGING_OUT) &&
            operationDeadline != 0L && platform.now() >= operationDeadline) {
            val wasLogout = status == LOGGING_OUT
            ++generation
            pending = null
            resumedAt = 0L
            try {
                vault.clearAttempt()
                if (wasLogout) {
                    vault.clearSession()
                    currentSession = null
                    status = SIGNED_OUT
                    message = "Vui lòng đăng nhập tài khoản để tiếp tục"
                } else {
                    status = ERROR
                    message = messageFor("timeout")
                }
            } catch (_: Exception) { status = ERROR; message = messageFor("storage_error") }
        }
        while (true) (completions.poll() ?: break).invoke()
        if (status == BROWSER) {
            if (pending?.expiresAt?.let { platform.now() >= it } == true) failPending("attempt_expired")
            else if (resumedAt != 0L && platform.now() - resumedAt >= 2000L) failPending("browser_closed")
        }
    }

    @Synchronized fun choose(provider: String) {
        if (status != SIGNED_OUT && status != ERROR) return
        if (provider != "facebook" && provider != "password") return
        val state = base64Url(platform.randomBytes(32))
        val verifier = base64Url(platform.randomBytes(32))
        val id = ++generation
        status = CHECKING
        operationDeadline = platform.now() + 60_000L
        message = "Đang kết nối tài khoản..."
        resumedAt = 0L
        browserWasPaused = false
        platform.execute {
            if (generation != id) return@execute
            try {
                val reply = backend.attempt(provider, state, challenge(verifier))
                if (reply.expiresIn !in 1..600) throw SocialAuthFailure("invalid_response")
                val attempt = SocialAttempt(provider, state, verifier, reply.authorizationUrl,
                    platform.now() + reply.expiresIn * 1000L)
                synchronized(this) {
                    if (generation != id || platform.now() >= operationDeadline) return@execute
                    vault.clearAttempt()
                    vault.saveAttempt(attempt)
                }
                publish(id) {
                    pending = attempt
                    status = BROWSER
                    message = if (provider == "password") "Đang mở trang tài khoản trong game..." else "Đang chờ xác thực trong trình duyệt..."
                    openAttempt(attempt)
                }
            } catch (failure: Exception) { publishFailure(id, failure) }
        }
    }

    @Synchronized fun callbackAvailable() {
        if (status != BROWSER) return
        status = EXCHANGING
        operationDeadline = platform.now() + 60_000L
        message = "Đang xác minh tài khoản..."
        exchangeSaved(++generation)
    }

    fun enqueueCallback() {
        completions.add { callbackAvailable() }
    }

    fun browserFailed() {
        val id = generation
        publish(id) { if (status == BROWSER) failPending("browser_unavailable") }
    }

    @Synchronized fun continueSaved() {
        if (status != WELCOME) return
        val session = currentSession ?: return
        val id = ++generation
        status = CHECKING
        operationDeadline = platform.now() + 60_000L
        message = "Đang kiểm tra phiên đăng nhập..."
        validateSession(id, session, true)
    }

    @Synchronized fun logout() {
        if (status == LOGGING_OUT) return
        val session = currentSession
        val id = ++generation
        status = LOGGING_OUT
        operationDeadline = platform.now() + 60_000L
        message = "Đang đăng xuất..."
        pending = null
        platform.execute {
            try {
                synchronized(this) {
                    if (generation != id) return@execute
                    vault.clearAttempt()
                    vault.clearSession()
                }
                if (session != null) {
                    try { backend.logout(session.accessToken, session.refreshToken) } catch (_: Exception) { }
                }
                publish(id) {
                    currentSession = null
                    status = SIGNED_OUT
                    message = "Vui lòng đăng nhập tài khoản để tiếp tục"
                }
            } catch (failure: Exception) { publishFailure(id, failure) }
        }
    }

    @Synchronized fun cancel() {
        if (status == READY || status == LOGGING_OUT) return
        ++generation
        pending = null
        resumedAt = 0L
        browserWasPaused = false
        try { vault.clearAttempt() }
        catch (_: Exception) { status = ERROR; message = messageFor("storage_error"); return }
        status = SIGNED_OUT
        message = "Vui lòng đăng nhập tài khoản để tiếp tục"
    }

    @Synchronized fun retry() {
        if (status != ERROR && status != SIGNED_OUT) return
        recover()
    }

    @Synchronized fun onHostPaused() {
        if (status == BROWSER && pending?.provider != "password") browserWasPaused = true
    }

    @Synchronized fun onHostResumed() {
        if (status == BROWSER && pending?.provider != "password" && browserWasPaused) resumedAt = platform.now()
    }

    private fun recover() {
        val id = ++generation
        status = CHECKING
        operationDeadline = platform.now() + 60_000L
        message = "Đang kiểm tra tài khoản..."
        platform.execute {
            if (generation != id) return@execute
            try {
                val callback = vault.callback()
                val attempt = vault.attempt()
                if (callback != null && attempt != null) {
                    publish(id) {
                        pending = attempt
                        status = EXCHANGING
                        operationDeadline = platform.now() + 60_000L
                        message = "Đang xác minh tài khoản..."
                        exchangeSaved(id)
                    }
                    return@execute
                }
                if (callback != null) vault.clearAttempt()
                if (attempt != null && platform.now() < attempt.expiresAt) {
                    publish(id) {
                        pending = attempt
                        status = BROWSER
                        message = if (attempt.provider == "password") "Đang khôi phục trang tài khoản trong game..." else "Đang chờ xác thực trong trình duyệt. Bạn có thể hủy và thử lại."
                        if (attempt.provider == "password") openAttempt(attempt)
                    }
                    return@execute
                }
                if (attempt != null) vault.clearAttempt()
                val session = vault.session()
                if (session == null) publish(id) {
                    status = SIGNED_OUT
                    message = "Vui lòng đăng nhập tài khoản để tiếp tục"
                } else validateSession(id, session, false)
            } catch (failure: Exception) { publishFailure(id, failure) }
        }
    }

    private fun validateSession(id: Long, session: SocialSession, enter: Boolean) {
        platform.execute {
            if (generation != id) return@execute
            try {
                var active = session
                if (platform.now() >= active.refreshExpiresAt) throw SocialAuthFailure("session_expired")
                if (platform.now() >= active.accessExpiresAt - 30_000L) {
                    active = backend.refresh(active.refreshToken)
                }
                val user = try { backend.me(active.accessToken) }
                catch (failure: SocialAuthFailure) {
                    if (failure.code != "invalid_session" && failure.code != "session_expired") throw failure
                    active = backend.refresh(active.refreshToken)
                    backend.me(active.accessToken)
                }
                if (user.gameId != session.user.gameId || user.provider != session.user.provider ||
                    active.user.gameId != session.user.gameId || active.user.provider != session.user.provider) {
                    throw SocialAuthFailure("invalid_session")
                }
                active = active.copy(user = user)
                synchronized(this) {
                    if (generation != id || platform.now() >= operationDeadline) return@execute
                    vault.saveSession(active)
                }
                val verified = active
                publish(id) {
                    currentSession = verified
                    status = if (enter) READY else WELCOME
                    message = "Chào mừng ${verified.user.gameId} trở lại!"
                }
            } catch (failure: Exception) { publishFailure(id, failure) }
        }
    }

    private fun exchangeSaved(id: Long) {
        platform.execute {
            if (generation != id) return@execute
            try {
                val attempt = vault.attempt() ?: throw SocialAuthFailure("invalid_callback")
                val callback = vault.callback() ?: throw SocialAuthFailure("invalid_callback")
                if (platform.now() >= attempt.expiresAt) throw SocialAuthFailure("attempt_expired")
                if (!sameState(attempt.state, callback.state)) throw SocialAuthFailure("invalid_callback")
                synchronized(this) {
                    if (generation != id) return@execute
                    // Local replay is rejected even if this process dies during exchange.
                    vault.clearAttempt()
                }
                if (callback.error != null) throw SocialAuthFailure(callback.error)
                val code = callback.code ?: throw SocialAuthFailure("invalid_callback")
                val session = backend.exchange(code, attempt.verifier)
                if (session.user.provider != attempt.provider) {
                    try { backend.logout(session.accessToken, session.refreshToken) } catch (_: Exception) { }
                    throw SocialAuthFailure("invalid_session")
                }
                val retained = synchronized(this) {
                    if (generation != id || platform.now() >= operationDeadline) {
                        false
                    } else {
                        vault.saveSession(session)
                        true
                    }
                }
                if (!retained) {
                    try { backend.logout(session.accessToken, session.refreshToken) } catch (_: Exception) { }
                    return@execute
                }
                publish(id) {
                    pending = null
                    currentSession = session
                    status = READY
                    message = "Chào mừng ${session.user.gameId}!"
                }
            } catch (failure: Exception) { publishFailure(id, failure) }
        }
    }

    @Synchronized fun embeddedFailed(code: String): Boolean {
        if (status != BROWSER || pending?.provider != "password") return false
        failPending(code)
        return true
    }

    private fun openAttempt(attempt: SocialAttempt) {
        try {
            if (attempt.provider == "password") platform.openEmbedded(attempt.authorizationUrl)
            else platform.openBrowser(attempt.authorizationUrl)
        } catch (_: Exception) {
            failPending(if (attempt.provider == "password") "webview_unavailable" else "browser_unavailable")
        }
    }

    private fun failPending(code: String) {
        ++generation
        pending = null
        resumedAt = 0L
        try { vault.clearAttempt() } catch (_: Exception) { }
        status = ERROR
        message = messageFor(code)
    }

    private fun publish(id: Long, result: () -> Unit) {
        completions.add { if (generation == id) result() }
    }

    private fun publishFailure(id: Long, failure: Exception) {
        val code = if (failure is SocialAuthFailure) failure.code else "storage_error"
        publish(id) {
            pending = null
            resumedAt = 0L
            status = ERROR
            message = messageFor(code)
        }
    }
}
