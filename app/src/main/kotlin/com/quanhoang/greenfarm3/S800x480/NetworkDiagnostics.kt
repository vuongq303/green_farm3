package com.quanhoang.greenfarm3.S800x480

import android.util.Log
import java.io.IOException
import java.io.InterruptedIOException
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.NoRouteToHostException
import java.net.Proxy
import java.net.SocketTimeoutException
import java.net.URI
import java.net.URL
import java.net.URLConnection
import java.net.UnknownHostException
import java.util.Locale
import javax.net.ssl.SSLException

/** Diagnostics shared by source HTTP and the retained SDK HTTP worker.
 * Only an origin and a fixed route template are retained, never request data.
 */
class NetworkDiagnostics private constructor() {
    private class Request(val url: String, val started: Long)

    companion object {
        private const val TAG = "GreenFarmNetwork"
        private val request = ThreadLocal<Request>()

        @JvmStatic fun begin() { request.remove() }

        @JvmStatic fun startup(baseUrl: String, local: Boolean) {
            write(false, "startup url=${safeUrl(baseUrl)} mode=${if (local) "LOCAL" else "NETWORK"}")
        }

        @JvmStatic fun accountStartup(baseUrl: String) {
            val endpoint = safeUrl(baseUrl)
            val configured = endpoint != "<invalid-url>" && baseUrl.startsWith("https://")
            write(!configured, "account_startup url=$endpoint configured=$configured mode=OAUTH" +
                if (configured) "" else " hint=Set AUTH_BASE_URL to the HTTPS authentication backend origin.")
        }

        @JvmStatic fun safeUrl(value: String?): String {
            if (value == null) return "<invalid-url>"
            return try {
                val uri = URI(value)
                val scheme = uri.scheme?.lowercase(Locale.ROOT)
                val host = uri.host?.lowercase(Locale.ROOT)
                if ((scheme != "http" && scheme != "https") || host.isNullOrEmpty() ||
                    host.length > 255 || !host.matches(Regex("[a-z0-9.\\[\\]:-]+")) ||
                    uri.port < -1 || uri.port == 0 || uri.port > 65535) return "<invalid-url>"
                val port = if (uri.port == -1) { if (scheme == "https") 443 else 80 } else uri.port
                val path = uri.rawPath.orEmpty()
                val route = when {
                    path.isEmpty() || path == "/" -> "/"
                    path in arrayOf("/health", "/healthz", "/authorize", "/locate/auth",
                        "/locate/storage", "/profiles/me/myprofile", "/users/me/compliance",
                        "/auth/attempt", "/auth/facebook", "/auth/google", "/auth/exchange",
                        "/auth/me", "/auth/logout", "/auth/refresh", "/auth/browser/start",
                        "/auth/callback/facebook", "/auth/callback/google") -> path
                    path.matches(Regex("/config/[^/]+/datacenters")) -> "/config/:client/datacenters"
                    path.matches(Regex("/config/[^/]+/datacenters/[^/]+/urls")) -> "/config/:client/datacenters/:name/urls"
                    else -> "/<redacted>"
                }
                "$scheme://$host:$port$route"
            } catch (_: Exception) { "<invalid-url>" }
        }

        @JvmStatic @Throws(IOException::class)
        fun openConnection(url: URL): URLConnection {
            starting(url)
            return url.openConnection()
        }

        @JvmStatic @Throws(IOException::class)
        fun openConnection(url: URL, proxy: Proxy): URLConnection {
            starting(url)
            return url.openConnection(proxy)
        }

        private fun starting(url: URL) {
            val endpoint = safeUrl(url.toExternalForm())
            request.set(Request(endpoint, System.nanoTime()))
            write(false, "request url=$endpoint")
        }

        @JvmStatic @Throws(IOException::class)
        fun responseCode(connection: HttpURLConnection): Int = reportResponse(connection, true)

        /** The new JSON account API accepts standard 2xx, including create/logout. */
        @JvmStatic @Throws(IOException::class)
        fun authResponseCode(connection: HttpURLConnection): Int = reportResponse(connection, false)

        private fun reportResponse(connection: HttpURLConnection, legacy: Boolean): Int {
            val status = connection.responseCode
            // A platform redirect may have changed the connection's URL.
            val endpoint = safeUrl(connection.url.toExternalForm())
            val current = request.get()
            if (current != null) request.set(Request(endpoint, current.started))
            val method = when (connection.requestMethod) {
                "GET", "POST", "DELETE", "HEAD", "PUT", "PATCH", "OPTIONS" -> connection.requestMethod
                else -> "OTHER"
            }
            var message = "response method=$method url=$endpoint status=$status elapsed_ms=${elapsed()}"
            if (status in 300..399) {
                // Diagnostics must never turn a successful HTTP operation into a failure.
                try {
                    val location = connection.getHeaderField("Location")
                    if (location != null) message += " redirect_to=${safeUrl(URL(connection.url, location).toExternalForm())}"
                } catch (_: Exception) { }
            }
            val rejected = if (legacy) status != 200 && status != 202 && status != 302 else status !in 200..299
            if (status >= 400) message += " kind=HTTP_ERROR hint=${httpHint(status)}"
            else if (rejected) message += if (legacy)
                " kind=UNEXPECTED_STATUS hint=This client expects HTTP 200/202; check the backend API response."
                else " kind=UNEXPECTED_STATUS hint=Account API redirects are refused; check the HTTPS origin and proxy configuration."
            write(rejected, message)
            return status
        }

        @JvmStatic fun errorKind(error: Throwable): String {
            var cause: Throwable? = error
            var ioFailure = false
            repeat(8) {
                val current = cause ?: return@repeat
                when (current) {
                    is UnknownHostException -> return "DNS_ERROR"
                    is SocketTimeoutException -> return "TIMEOUT"
                    is ConnectException, is NoRouteToHostException -> return "CONNECT_ERROR"
                    is SSLException -> return "TLS_ERROR"
                    is MalformedURLException, is java.net.URISyntaxException -> return "INVALID_URL"
                }
                if (current.message?.contains("CLEARTEXT", ignoreCase = true) == true) return "CLEARTEXT_BLOCKED"
                if (current is IOException) ioFailure = true
                cause = current.cause
            }
            if (ioFailure) return "IO_ERROR"
            return when (error) {
                is InterruptedIOException -> "IO_ERROR"
                is IOException -> "IO_ERROR"
                is IllegalArgumentException -> "INVALID_URL"
                else -> "UNKNOWN_ERROR"
            }
        }

        @JvmStatic fun failure(error: Throwable) {
            val kind = errorKind(error)
            write(true, "network_error url=${request.get()?.url ?: "<unknown>"} kind=$kind" +
                " type=${error.javaClass.name} elapsed_ms=${elapsed()} hint=${hint(kind)}")
            request.remove()
        }

        private fun elapsed(): Long = request.get()?.let {
            ((System.nanoTime() - it.started) / 1_000_000L).coerceAtLeast(0)
        } ?: 0L

        private fun hint(kind: String): String = when (kind) {
            "DNS_ERROR" -> "Cannot resolve host; check hostname and device DNS."
            "CONNECT_ERROR" -> "Cannot connect; check server IP, listening port, device route and firewall."
            "TIMEOUT" -> "Request timed out; check device connectivity and backend response."
            "TLS_ERROR" -> "TLS handshake failed; check server certificate and device clock."
            "CLEARTEXT_BLOCKED" -> "Android blocked HTTP; check the APK network security configuration."
            "INVALID_URL" -> "Invalid endpoint; check BACKEND_BASE_URL and backend discovery URLs."
            "IO_ERROR" -> "Connection or response stream failed."
            else -> "Request failed; inspect the exception type and preceding response status."
        }

        private fun httpHint(status: Int): String = when (status) {
            400 -> "Backend rejected the request format."
            401 -> "Backend rejected authentication or the session expired."
            403 -> "Backend denied access."
            404 -> "API route was not found; check backend address and version."
            503 -> "Backend unavailable; check backend and MySQL health."
            in 500..599 -> "Backend server error; check server logs."
            else -> "Backend returned an HTTP error."
        }

        private fun write(error: Boolean, message: String) {
            try {
                if (error) Log.e(TAG, message) else Log.i(TAG, message)
            } catch (_: RuntimeException) {
                // Logging must not change login, retry, cancellation or save behavior.
            }
        }
    }
}
