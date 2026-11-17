package com.quanhoang.greenfarm3.S800x480

import android.content.Context
import android.os.Build
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import android.util.Base64
import android.util.Log
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import org.json.JSONObject

/** A separate encrypted preference store. Never opens the gameplay database. */
class SecureAccountStore(context: Context, private val expectedOrigin: String? = null) : SocialAccountFlow.Vault {
    companion object { private val KEY_LOCK = Any() }
    private val preferences = context.applicationContext.getSharedPreferences("greenfarm_online_auth_v1", Context.MODE_PRIVATE)
    private val packageName = context.packageName
    private val alias = "$packageName.online-auth.v1"

    init { if (Build.VERSION.SDK_INT < 23) throw SocialAuthFailure("unsupported_device") }

    @Synchronized override fun session(): SocialSession? = read("session")?.let { value ->
        try {
            if (expectedOrigin == null || value.optString("origin", "") != expectedOrigin) return@let null
            val user = value.getJSONObject("user")
            SocialSession(value.getString("access_token"), value.getString("refresh_token"),
                value.getLong("access_expires_at"), value.getLong("refresh_expires_at"),
                // display_name is optional in the public backend contract.
                // A missing/null name must not make an otherwise valid remembered
                // session unreadable on the next launch.
                SocialIdentity(user.getString("game_id"), user.getString("provider"),
                    if (user.isNull("display_name")) "" else user.optString("display_name", "")))
        } catch (failure: Exception) {
            Log.w("GreenFarmAccount", "stored_session_parse_failed cause=${failure.javaClass.simpleName}")
            throw SocialAuthFailure("storage_error")
        }
    }

    @Synchronized override fun saveSession(value: SocialSession) = write("session", JSONObject()
        .put("origin", expectedOrigin ?: throw SocialAuthFailure("storage_error"))
        .put("access_token", value.accessToken).put("refresh_token", value.refreshToken)
        .put("access_expires_at", value.accessExpiresAt).put("refresh_expires_at", value.refreshExpiresAt)
        .put("user", JSONObject().put("game_id", value.user.gameId).put("provider", value.user.provider)
            .put("display_name", value.user.displayName)))

    @Synchronized override fun clearSession() { remove("session") }

    @Synchronized override fun attempt(): SocialAttempt? = read("attempt")?.let { value ->
        try {
            if (expectedOrigin != null) {
                if (!SocialAuthClient.isAuthorizationUrl(value.getString("authorization_url"), expectedOrigin, value.getString("provider"))) return@let null
            }
            SocialAttempt(value.getString("provider"), value.getString("state"), value.getString("verifier"),
                value.getString("authorization_url"), value.getLong("expires_at"))
        } catch (_: Exception) { throw SocialAuthFailure("storage_error") }
    }

    @Synchronized override fun saveAttempt(value: SocialAttempt) = write("attempt", JSONObject()
        .put("provider", value.provider).put("state", value.state).put("verifier", value.verifier)
        .put("authorization_url", value.authorizationUrl).put("expires_at", value.expiresAt))

    @Synchronized override fun callback(): SocialCallback? = read("callback")?.let { value ->
        try {
            SocialCallback(value.getString("state"), value.optString("code", "").ifEmpty { null },
                value.optString("error", "").ifEmpty { null })
        } catch (_: Exception) { throw SocialAuthFailure("storage_error") }
    }

    @Synchronized override fun saveCallback(value: SocialCallback) = write("callback", JSONObject()
        .put("state", value.state).put("code", value.code ?: "").put("error", value.error ?: ""))

    @Synchronized override fun clearAttempt() {
        if (!preferences.edit().remove("attempt").remove("callback").commit()) throw SocialAuthFailure("storage_error")
    }

    private fun remove(name: String) {
        if (!preferences.edit().remove(name).commit()) throw SocialAuthFailure("storage_error")
    }

    private fun key(): SecretKey = synchronized(KEY_LOCK) {
        val store = KeyStore.getInstance("AndroidKeyStore")
        store.load(null)
        if (store.containsAlias(alias)) return@synchronized store.getKey(alias, null) as SecretKey
        val generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        generator.init(KeyGenParameterSpec.Builder(alias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM).setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .setRandomizedEncryptionRequired(true).setUserAuthenticationRequired(false).build())
        generator.generateKey()
    }

    private fun write(name: String, value: JSONObject) {
        try {
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.ENCRYPT_MODE, key())
            cipher.updateAAD((packageName + ":" + name).toByteArray(StandardCharsets.UTF_8))
            val encrypted = cipher.doFinal(value.toString().toByteArray(StandardCharsets.UTF_8))
            val record = "1:" + Base64.encodeToString(cipher.iv, Base64.NO_WRAP) + ":" + Base64.encodeToString(encrypted, Base64.NO_WRAP)
            if (!preferences.edit().putString(name, record).commit()) throw SocialAuthFailure("storage_error")
        } catch (_: Exception) { throw SocialAuthFailure("storage_error") }
    }

    private fun read(name: String): JSONObject? {
        val record = preferences.getString(name, null) ?: return null
        try {
            if (record.length > 32_768) throw SocialAuthFailure("storage_error")
            val parts = record.split(':')
            if (parts.size != 3 || parts[0] != "1") throw SocialAuthFailure("storage_error")
            val iv = Base64.decode(parts[1], Base64.NO_WRAP)
            if (iv.size != 12) throw SocialAuthFailure("storage_error")
            val cipher = Cipher.getInstance("AES/GCM/NoPadding")
            cipher.init(Cipher.DECRYPT_MODE, key(), GCMParameterSpec(128, iv))
            cipher.updateAAD((packageName + ":" + name).toByteArray(StandardCharsets.UTF_8))
            return JSONObject(String(cipher.doFinal(Base64.decode(parts[2], Base64.NO_WRAP)), StandardCharsets.UTF_8))
        } catch (failure: Exception) {
            // No exception message, plaintext, token or encrypted content in logs.
            Log.w("GreenFarmAccount", "secure_auth_record_read_failed type=$name cause=${failure.javaClass.simpleName}")
            throw SocialAuthFailure("storage_error")
        }
    }
}
