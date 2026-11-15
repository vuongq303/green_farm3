package com.quanhoang.greenfarm3.S800x480

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.net.SocketTimeoutException
import java.net.URI
import java.net.URL
import java.nio.charset.StandardCharsets
import java.security.SecureRandom
import java.util.Locale
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLException
import org.json.JSONObject

/** New account requests never inherit the legacy game's permissive global TLS factory. */
class SocialAuthClient(baseUrl: String, private val operationTimeoutMillis: Long = 45_000L,
    private val monotonicNanos: () -> Long = { System.nanoTime() },
    private val now: () -> Long = { System.currentTimeMillis() }) : SocialAccountFlow.Backend {
    val origin: String = validateOrigin(baseUrl)
    private val trustedTls = SSLContext.getInstance("TLS").apply { init(null, null, SecureRandom()) }.socketFactory
    private val hostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier()

    companion object {
        private val deadlines = Executors.newSingleThreadScheduledExecutor { task ->
            Thread(task, "GreenFarmAuthDeadline").apply { isDaemon = true }
        }
        @JvmStatic fun validateOrigin(value: String): String {
            try {
                if (value.isEmpty() || value != value.trim()) throw SocialAuthFailure("configuration_required")
                val uri = URI(value)
                if (uri.scheme != "https" || uri.host.isNullOrEmpty() || uri.rawUserInfo != null ||
                    uri.rawQuery != null || uri.rawFragment != null || uri.rawPath !in arrayOf("", "/") ||
                    uri.port == 0 || uri.port < -1 || uri.port > 65535) throw SocialAuthFailure("configuration_required")
                val port = if (uri.port == -1 || uri.port == 443) "" else ":${uri.port}"
                return "https://" + uri.host.lowercase(Locale.ROOT) + port + "/"
            } catch (_: Exception) { throw SocialAuthFailure("configuration_required") }
        }

        @JvmStatic fun isAuthorizationUrl(value: String, origin: String): Boolean = try {
            val target = URI(value)
            val base = URI(origin)
            target.scheme == "https" && target.rawUserInfo == null && target.rawFragment == null &&
                target.host.orEmpty().equals(base.host, ignoreCase = true) &&
                (if (target.port == -1) 443 else target.port) == (if (base.port == -1) 443 else base.port) &&
                target.rawPath == "/auth/browser/start" &&
                target.rawQuery.orEmpty().matches(Regex("id=[A-Za-z0-9_-]{32,128}"))
        } catch (_: Exception) { false }
    }

    override fun attempt(provider: String, state: String, challenge: String): SocialAttemptReply {
        try {
            val response = request("POST", "auth/attempt", JSONObject().put("provider", provider)
                .put("state", state).put("code_challenge", challenge).put("redirect_uri", SocialAccountFlow.REDIRECT_URI))
            val authorization = response.getString("authorization_url")
            if (!isAuthorizationUrl(authorization, origin)) throw SocialAuthFailure("invalid_response")
            return SocialAttemptReply(authorization, response.getLong("expires_in"))
        } catch (failure: SocialAuthFailure) { throw failure }
        catch (_: Exception) { throw SocialAuthFailure("invalid_response") }
    }

    override fun exchange(code: String, verifier: String): SocialSession = tokens(request("POST", "auth/exchange",
        JSONObject().put("code", code).put("code_verifier", verifier).put("redirect_uri", SocialAccountFlow.REDIRECT_URI)))

    override fun me(accessToken: String): SocialIdentity = try {
        identity(request("GET", "auth/me", null, accessToken).getJSONObject("user"))
    } catch (failure: SocialAuthFailure) { throw failure }
    catch (_: Exception) { throw SocialAuthFailure("invalid_response") }

    override fun refresh(refreshToken: String): SocialSession = tokens(request("POST", "auth/refresh",
        JSONObject().put("refresh_token", refreshToken)))

    override fun logout(accessToken: String, refreshToken: String) {
        request("POST", "auth/logout", JSONObject().put("refresh_token", refreshToken), accessToken, true)
    }

    private fun tokens(response: JSONObject): SocialSession {
        try {
            val access = response.getString("access_token")
            val refresh = response.getString("refresh_token")
            if (!access.matches(Regex("[A-Za-z0-9._~-]{32,2048}")) ||
                !refresh.matches(Regex("[A-Za-z0-9._~-]{32,2048}"))) throw SocialAuthFailure("invalid_response")
            val lifetime = response.getLong("expires_in")
            val refreshLifetime = response.getLong("refresh_expires_in")
            if (lifetime !in 1..86_400 || refreshLifetime !in lifetime..7_776_000) throw SocialAuthFailure("invalid_response")
            val issuedAt = now()
            return SocialSession(access, refresh, issuedAt + lifetime * 1000L,
                issuedAt + refreshLifetime * 1000L, identity(response.getJSONObject("user")))
        } catch (failure: SocialAuthFailure) { throw failure }
        catch (_: Exception) { throw SocialAuthFailure("invalid_response") }
    }

    private fun identity(value: JSONObject): SocialIdentity {
        try {
            val gameId = value.getString("game_id")
            val provider = value.getString("provider")
            val displayName = value.optString("display_name", "")
            if (!gameId.matches(Regex("GF[0-9]{6,20}")) || provider !in arrayOf("facebook", "google") ||
                displayName.length > 256 || displayName.any { it.code < 32 }) throw SocialAuthFailure("invalid_response")
            return SocialIdentity(gameId, provider, displayName)
        } catch (failure: SocialAuthFailure) { throw failure }
        catch (_: Exception) { throw SocialAuthFailure("invalid_response") }
    }

    private fun request(method: String, path: String, payload: JSONObject?, bearer: String? = null,
                        acceptEmpty: Boolean = false): JSONObject {
        var connection: HttpsURLConnection? = null
        var deadlineTask: ScheduledFuture<*>? = null
        val timedOut = AtomicBoolean(false)
        val deadline = monotonicNanos() + operationTimeoutMillis * 1_000_000L
        NetworkDiagnostics.begin()
        try {
            connection = NetworkDiagnostics.openConnection(URL(origin + path)) as HttpsURLConnection
            val activeConnection = connection
            deadlineTask = deadlines.schedule({
                timedOut.set(true)
                try { activeConnection.disconnect() } catch (_: Exception) { }
            }, operationTimeoutMillis, TimeUnit.MILLISECONDS)
            // Each connection has system CA validation and the platform hostname verifier.
            // In particular it cannot inherit am.mn()'s old SDK SSL socket factory.
            connection.sslSocketFactory = trustedTls
            connection.hostnameVerifier = hostnameVerifier
            connection.connectTimeout = 10_000
            connection.readTimeout = 15_000
            connection.instanceFollowRedirects = false
            connection.useCaches = false
            connection.requestMethod = method
            connection.setRequestProperty("Accept", "application/json")
            if (bearer != null) connection.setRequestProperty("Authorization", "Bearer $bearer")
            if (payload != null) {
                val bytes = payload.toString().toByteArray(StandardCharsets.UTF_8)
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                connection.setFixedLengthStreamingMode(bytes.size)
                connection.outputStream.use { it.write(bytes) }
            }
            val status = NetworkDiagnostics.authResponseCode(connection)
            ensureDeadline(deadline, timedOut)
            if (acceptEmpty && status == 204) return JSONObject()
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val text = if (stream == null) "" else stream.use { readBounded(it, deadline, timedOut) }
            if (status !in 200..299) {
                val error = try { JSONObject(text).getJSONObject("error").getString("code") }
                    catch (_: Exception) { if (status == 401) "invalid_session" else if (status == 429) "rate_limited" else "network_error" }
                throw SocialAuthFailure(if (error.matches(Regex("[a-z_]{1,64}"))) error else "authentication_failed")
            }
            if (!connection.contentType.orEmpty().lowercase().startsWith("application/json")) throw SocialAuthFailure("invalid_response")
            val parsed = try { JSONObject(text) } catch (_: Exception) { throw SocialAuthFailure("invalid_response") }
            ensureDeadline(deadline, timedOut)
            return parsed
        } catch (failure: SocialAuthFailure) {
            if (failure.code == "timeout") NetworkDiagnostics.failure(SocketTimeoutException("Authentication operation deadline exceeded"))
            throw failure
        }
        catch (failure: Exception) {
            val expired = timedOut.get() || monotonicNanos() >= deadline
            NetworkDiagnostics.failure(if (expired) SocketTimeoutException("Authentication operation deadline exceeded") else failure)
            throw SocialAuthFailure(if (expired) "timeout" else when (failure) {
                is SocketTimeoutException -> "timeout"
                is SSLException -> "tls_error"
                else -> "network_error"
            })
        } finally {
            deadlineTask?.cancel(false)
            connection?.disconnect()
        }
    }

    private fun ensureDeadline(deadline: Long, timedOut: AtomicBoolean) {
        if (timedOut.get() || monotonicNanos() >= deadline) throw SocialAuthFailure("timeout")
    }

    private fun readBounded(stream: InputStream, deadline: Long, timedOut: AtomicBoolean): String {
        val output = ByteArrayOutputStream()
        val buffer = ByteArray(4096)
        while (true) {
            ensureDeadline(deadline, timedOut)
            val count = stream.read(buffer)
            ensureDeadline(deadline, timedOut)
            if (count == -1) break
            if (count == 0) throw SocialAuthFailure("invalid_response")
            if (output.size() + count > 65_536) throw SocialAuthFailure("invalid_response")
            output.write(buffer, 0, count)
        }
        return String(output.toByteArray(), StandardCharsets.UTF_8)
    }
}
