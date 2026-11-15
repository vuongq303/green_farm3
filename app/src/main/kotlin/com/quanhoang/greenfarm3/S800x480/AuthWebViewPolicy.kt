package com.quanhoang.greenfarm3.S800x480

import java.net.URI
import java.util.Locale

/** Narrow navigation boundary for the first-party password form. No OAuth pages are embedded. */
class AuthWebViewPolicy(origin: String, val initialUrl: String) {
    val origin: String
    private val base: URI
    private val attemptQuery: String

    init {
        val parsed = parse(origin) ?: throw IllegalArgumentException("Invalid authentication origin")
        require(parsed.scheme == "https" && !parsed.host.isNullOrEmpty() && parsed.rawUserInfo == null &&
            parsed.rawPath in arrayOf("", "/") && parsed.rawQuery == null && parsed.rawFragment == null &&
            validPort(parsed)) { "Invalid authentication origin" }
        val port = if (parsed.port == -1 || parsed.port == 443) "" else ":${parsed.port}"
        this.origin = "https://" + parsed.host.lowercase(Locale.ROOT) + port + "/"
        base = URI(this.origin)
        val first = trusted(initialUrl) ?: throw IllegalArgumentException("Invalid account form URL")
        require(first.rawPath == "/auth/web/login" &&
            first.rawQuery.orEmpty().matches(Regex("attempt=[A-Za-z0-9_-]{43}"))) { "Invalid account form URL" }
        attemptQuery = first.rawQuery
    }

    fun allowMainFrame(url: String): Boolean {
        val target = trusted(url) ?: return false
        return target.rawPath in arrayOf("/auth/web/login", "/auth/web/register") && target.rawQuery == attemptQuery
    }

    /** Includes XHR form submissions, but excludes account tokens, provider endpoints and arbitrary backend paths. */
    fun allowResource(url: String): Boolean {
        val target = trusted(url) ?: return false
        if (allowMainFrame(url)) return true
        if (target.rawQuery != null) return false
        return target.rawPath in arrayOf("/auth/login", "/auth/register") ||
            target.rawPath.orEmpty().matches(Regex("/auth/assets/[A-Za-z0-9][A-Za-z0-9._-]{0,127}"))
    }

    /** Missing form code/styles must expose retry instead of leaving a non-working form. */
    fun isRequiredAsset(url: String): Boolean {
        val target = trusted(url) ?: return false
        return target.rawQuery == null && target.rawPath in arrayOf("/auth/assets/password.js", "/auth/assets/styles.css")
    }

    /** State must still be compared to the encrypted pending attempt before the callback can be accepted. */
    fun isCallback(url: String): Boolean {
        val target = parse(url) ?: return false
        if (target.scheme != "com.quanhoang.greenfarm3" || target.rawAuthority != "oauth" ||
            target.rawPath != "/callback" || target.rawFragment != null) return false
        val query = target.rawQuery ?: return false
        val values = HashMap<String, String>()
        for (part in query.split('&')) {
            val pair = part.split('=', limit = 2)
            if (pair.size != 2 || pair[0] !in arrayOf("state", "code", "error") ||
                values.put(pair[0], pair[1]) != null) return false
        }
        val state = values["state"] ?: return false
        val code = values["code"]
        val error = values["error"]
        return state.matches(Regex("[A-Za-z0-9_-]{32,128}")) && (code == null) != (error == null) &&
            (code == null || code.matches(Regex("[A-Za-z0-9_-]{32,128}"))) &&
            (error == null || error in arrayOf("access_denied", "authentication_failed", "attempt_expired"))
    }

    private fun trusted(value: String): URI? {
        val target = parse(value) ?: return null
        if (target.scheme != "https" || target.rawUserInfo != null || target.rawFragment != null ||
            !target.host.orEmpty().equals(base.host, ignoreCase = true) || !validPort(target) ||
            effectivePort(target) != effectivePort(base)) return null
        return target
    }

    companion object {
        private fun parse(value: String): URI? = try {
            if (value.isEmpty() || value.length > 2048 || value != value.trim()) null else URI(value)
        } catch (_: Exception) { null }
        private fun validPort(uri: URI) = uri.port == -1 || uri.port in 1..65535
        private fun effectivePort(uri: URI) = if (uri.port == -1) 443 else uri.port
    }
}
