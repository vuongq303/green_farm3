package com.quanhoang.greenfarm3.S800x480

import android.content.Context
import android.content.pm.PackageManager
import android.util.Base64
import com.quanhoang.greenfarm3.migration.SaveArchive
import org.json.JSONObject
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
import java.util.UUID

/** New owned API. Legacy Gameloft anonymous credentials never authorize these saves. */
class CloudBackendTransport(context: Context) {
    class Failure(val code: String, val status: Int = 0) : IOException(code)
    data class Session(val playerId: String, val accessToken: String, val expiresAt: Long, val initialization: JSONObject? = null)
    data class RemoteSave(val revision: Long, val operationId: String, val hash: String,
        val payload: ByteArray, val snapshot: SaveArchive.Snapshot)
    data class Receipt(val revision: Long, val currentRevision: Long, val operationId: String, val hash: String)
    data class AdoptionGrant(val token: String, val expiresAt: Long)
    private val baseUrl: String

    init {
        val value = context.packageManager.getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData?.getString("greenfarm.backendBaseUrl") ?: throw Failure("BACKEND_URL_MISSING")
        val uri = URI(value)
        if (uri.scheme !in listOf("http", "https") || uri.host == null || uri.userInfo != null ||
            uri.rawQuery != null || uri.rawFragment != null || uri.path != "/") throw Failure("BACKEND_URL_INVALID")
        baseUrl = value
    }

    fun session(installationSecret: String, previousPlayerId: String?): Session {
        val request = JSONObject().put("installationSecret", installationSecret)
        if (previousPlayerId != null) request.put("previousPlayerId", previousPlayerId)
        val result = request("POST", "api/v1/guest/sessions", null, request)
        val player = result.getString("playerId")
        val token = result.getString("accessToken")
        val expires = result.getLong("expiresAt")
        if (!validUuid(player) || !token.matches(Regex("[A-Za-z0-9_-]{43}")) || expires <= 0 || expires > 253402300799L) throw Failure("INVALID_SESSION_RESPONSE")
        if (previousPlayerId != null && player != previousPlayerId) throw Failure("SESSION_IDENTITY_CONFLICT")
        val initialization = result.optJSONObject("initialization")
        if (!result.isNull("initialization") && initialization == null) throw Failure("INVALID_INITIALIZATION_RESPONSE")
        if (initialization != null && (!validHash(initialization.optString("configHash")) || initialization.optJSONObject("defaults") == null)) throw Failure("INVALID_INITIALIZATION_RESPONSE")
        return Session(player, token, expires, initialization)
    }

    fun load(token: String): RemoteSave? {
        val result = try { request("GET", "api/v1/save", token, null) }
        catch (failure: Failure) {
            if (failure.status == 404 && failure.code == "NO_SAVE") return null
            throw failure
        }
        val revision = result.getLong("revision")
        val operation = result.getString("operationId")
        val hash = result.getString("snapshotHash")
        val encoded = result.getString("snapshotBase64")
        if (revision !in 1..MAX_REVISION || !validUuid(operation) || !validHash(hash) || encoded.length > MAX_BASE64) throw Failure("INVALID_SAVE_RESPONSE")
        val payload = try { Base64.decode(encoded, Base64.DEFAULT) } catch (_: IllegalArgumentException) { throw Failure("INVALID_SAVE_BASE64") }
        if (payload.size > MAX_SNAPSHOT_BYTES || Base64.encodeToString(payload, Base64.NO_WRAP) != encoded || CloudSaveJournal.hash(payload) != hash) throw Failure("INVALID_SAVE_HASH")
        val snapshot = try { SaveArchive.read(ByteArrayInputStream(payload)) } catch (_: Exception) { throw Failure("INVALID_SAVE_ARCHIVE") }
        if (snapshot.records.any { it.name == "offline_test_balances_v1" }) throw Failure("TEST_SAVE_REJECTED")
        return RemoteSave(revision, operation, hash, payload, snapshot)
    }

    fun save(token: String, work: CloudSaveJournal.Work): Receipt {
        if (work.payload.size > MAX_SNAPSHOT_BYTES || CloudSaveJournal.hash(work.payload) != work.snapshotHash) throw Failure("INVALID_PENDING_SNAPSHOT")
        val body = JSONObject().put("operationId", work.operationId).put("expectedRevision", work.expectedRevision)
            .put("snapshotBase64", Base64.encodeToString(work.payload, Base64.NO_WRAP)).put("snapshotHash", work.snapshotHash)
        if (work.adoptionToken != null) body.put("adoptionToken", work.adoptionToken)
        val result = request("PUT", "api/v1/save", token, body)
        return Receipt(result.getLong("revision"), result.getLong("currentRevision"), result.getString("operationId"), result.getString("snapshotHash")).also {
            if (it.operationId != work.operationId || it.hash != work.snapshotHash || it.revision !in 1..MAX_REVISION || it.currentRevision !in it.revision..MAX_REVISION) throw Failure("INVALID_SAVE_ACKNOWLEDGEMENT")
        }
    }

    fun authorizeAdoption(token: String, work: CloudSaveJournal.Work): AdoptionGrant {
        val result = request("POST", "api/v1/save/adoption", token,
            JSONObject().put("operationId", work.operationId).put("snapshotHash", work.snapshotHash))
        val grant = result.getString("adoptionToken")
        val expires = result.getLong("expiresAt")
        if (!grant.matches(Regex("[A-Za-z0-9_-]{43}")) || expires <= 0L || expires > 253402300799L) throw Failure("INVALID_ADOPTION_GRANT")
        return AdoptionGrant(grant, expires)
    }

    private fun request(method: String, path: String, token: String?, body: JSONObject?): JSONObject {
        var connection: HttpURLConnection? = null
        try {
            connection = URL(baseUrl + path).openConnection() as HttpURLConnection
            connection.instanceFollowRedirects = false
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.requestMethod = method
            connection.setRequestProperty("Accept", "application/json")
            if (token != null) connection.setRequestProperty("Authorization", "Bearer $token")
            if (body != null) {
                val bytes = body.toString().toByteArray(Charsets.UTF_8)
                connection.doOutput = true
                connection.setRequestProperty("Content-Type", "application/json; charset=utf-8")
                connection.setFixedLengthStreamingMode(bytes.size)
                connection.outputStream.use { it.write(bytes) }
            }
            val status = connection.responseCode
            val stream = if (status in 200..299) connection.inputStream else connection.errorStream
            val bytes = ByteArrayOutputStream()
            stream?.use { input ->
                val buffer = ByteArray(8192)
                while (true) {
                    val length = input.read(buffer)
                    if (length < 0) break
                    if (bytes.size().toLong() + length > MAX_BASE64 + 8192L) throw Failure("RESPONSE_TOO_LARGE", status)
                    bytes.write(buffer, 0, length)
                }
            }
            val result = try { JSONObject(bytes.toString("UTF-8")) } catch (_: Exception) { throw Failure("INVALID_SERVER_RESPONSE", status) }
            if (status !in 200..299) {
                val code = result.optString("code", "HTTP_ERROR")
                throw Failure(if (code.matches(Regex("[A-Z][A-Z0-9_]{0,63}"))) code else "HTTP_ERROR", status)
            }
            return result
        } catch (failure: Failure) { throw failure }
        catch (_: Exception) { throw Failure("NETWORK_UNAVAILABLE") }
        finally { connection?.disconnect() }
    }

    private companion object {
        const val MAX_SNAPSHOT_BYTES = 16 * 1024 * 1024
        const val MAX_BASE64 = (MAX_SNAPSHOT_BYTES + 2) / 3 * 4
        const val MAX_REVISION = 9007199254740991L
        fun validUuid(value: String): Boolean = try { UUID.fromString(value).toString() == value } catch (_: IllegalArgumentException) { false }
        fun validHash(value: String): Boolean = value.matches(Regex("[0-9a-f]{64}"))
    }
}
