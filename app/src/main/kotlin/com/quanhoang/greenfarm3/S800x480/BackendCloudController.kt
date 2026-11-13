package com.quanhoang.greenfarm3.S800x480

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ClipData
import android.content.ClipboardManager
import android.os.SystemClock
import android.os.Build
import android.util.Base64
import android.util.Log
import com.quanhoang.greenfarm3.migration.SaveArchiveDatabase
import java.lang.ref.WeakReference
import java.security.SecureRandom
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicBoolean
import org.json.JSONObject

/** Real guest ownership, startup reconciliation and a persistent transactional outbox. */
object BackendCloudController {
    private const val PREFERENCES = "greenfarm-backend-session-v1"
    private val worker = Executors.newSingleThreadScheduledExecutor { task -> Thread(task, "GreenFarm-Cloud").apply { isDaemon = true } }
    private val preparing = AtomicBoolean()
    private val scheduled = AtomicBoolean()
    @Volatile private var prepared = false
    @Volatile private var application: Context? = null
    @Volatile private var activity = WeakReference<Activity>(null)
    @Volatile private var quitAction: Runnable? = null
    @Volatile private var quitCheckpointStarted = false
    @Volatile private var quitCheckpointDone = false
    @Volatile private var quitDeadlineMillis = 0L
    @Volatile private var warningVisible = false
    private var transport: CloudBackendTransport? = null
    @Volatile private var pristineInitialization: String? = null

    @JvmStatic fun isPrepared(): Boolean = prepared
    @JvmStatic fun isQuitPending(): Boolean = quitAction != null
    /** Available only for a server-confirmed empty profile with no existing local farm. */
    @JvmStatic fun newPlayerInitialization(): JSONObject? = pristineInitialization?.let { JSONObject(it) }

    /** Called by the lightweight launcher before any mutable farm is loaded. */
    @JvmStatic fun prepare(supplied: Activity, approveLocalCopy: Boolean = false) {
        activity = WeakReference(supplied)
        application = supplied.applicationContext ?: supplied
        if (!preparing.compareAndSet(false, true)) return
        worker.execute {
            try {
                reconcile(approveLocalCopy)
                prepared = true
                warningVisible = false
                val target = activity.get()
                target?.runOnUiThread {
                    // Start is singleTop: starting the same top component would
                    // reuse this gate instance, then finish the entire launcher.
                    if (!target.isFinishing && (Build.VERSION.SDK_INT < 17 || !target.isDestroyed)) target.recreate()
                }
                scheduleSync(1000L)
            } catch (_: LocalCopyApprovalRequired) {
                preparing.set(false)
                val target = activity.get()
                target?.runOnUiThread {
                    if (!target.isFinishing) AlertDialog.Builder(target)
                        .setTitle("Giữ bản lưu hiện có")
                        .setMessage("Máy chủ xác nhận hồ sơ khách mới chưa có nông trại. Bạn có muốn sao chép bản lưu trên máy vào hồ sơ khách này? Bản gốc được sao lưu; thao tác này không liên kết hay xác nhận tài khoản Facebook cũ.")
                        .setCancelable(false)
                        .setPositiveButton("Sao chép bản lưu") { _, _ -> prepare(target, true) }
                        .setNegativeButton("Đóng") { _, _ -> target.finish() }.show()
                }
            } catch (failure: Exception) {
                preparing.set(false)
                val code = safeCode(failure)
                Log.w("GreenFarmCloud", "startup_failed code=$code")
                showStartupFailure(code)
            } finally { preparing.set(false) }
        }
    }

    private class LocalCopyApprovalRequired : RuntimeException()

    private fun reconcile(approveLocalCopy: Boolean) {
        pristineInitialization = null
        val context = application ?: throw CloudBackendTransport.Failure("CONTEXT_UNAVAILABLE")
        check(context.packageName != "com.quanhoang.greenfarm3.offlinetest") { "Offline test data cannot be synchronized" }
        GameSaveStore.initialize(context)
        transport = CloudBackendTransport(context)
        val session = session()
        GameSaveStore.cloudDatabase(true) { db -> CloudSaveJournal.bindPlayer(db, session.playerId) }
        val before = GameSaveStore.cloudDatabase { CloudSaveJournal.state(it) }
        if (before.conflict != null) throw CloudBackendTransport.Failure(before.conflict)
        flushPending(16, Long.MAX_VALUE)
        val remote = authenticated { token -> transport!!.load(token) }
        val state = GameSaveStore.cloudDatabase { CloudSaveJournal.state(it) }
        val hasFarm = GameSaveStore.cloudDatabase { CloudSaveJournal.hasFarm(it) }
        if (remote == null) {
            if (state.revision != 0L) throw CloudBackendTransport.Failure("SERVER_SAVE_MISSING")
            if (hasFarm && !state.adopted && !approveLocalCopy) throw LocalCopyApprovalRequired()
            if (!hasFarm && !GameSaveStore.cloudDatabase { CloudSaveJournal.hasPending(it) }) {
                val initialization = session.initialization ?: throw CloudBackendTransport.Failure("NEW_PLAYER_DEFAULTS_MISSING")
                NewPlayerDefaults.validateInitialization(initialization)
                pristineInitialization = initialization.toString()
            }
            if (!state.adopted) GameSaveStore.cloudDatabase(true) { db -> CloudSaveJournal.adoptLocal(db, context) }
            flushPending(16, Long.MAX_VALUE)
            return
        }
        if (!state.adopted && hasFarm) {
            // No evidence ties these existing local bytes to this server save.
            // Retain both; never silently choose or overwrite either snapshot.
            preserveRemoteConflict(remote)
            throw CloudBackendTransport.Failure("LOCAL_SERVER_IDENTITY_CONFLICT")
        }
        if (remote.revision < state.revision) throw CloudBackendTransport.Failure("SERVER_REVISION_REGRESSED")
        if (!hasFarm || remote.revision > state.revision) {
            check(!GameSaveStore.cloudDatabase { CloudSaveJournal.hasPending(it) }) { "Pending local work cannot be replaced" }
            GameSaveStore.close()
            try { SaveArchiveDatabase.installCloud(context, remote.snapshot, session.playerId, remote.revision, remote.hash) }
            finally { GameSaveStore.initialize(context) }
        } else if (state.lastAckHash != null && state.lastAckHash != remote.hash) {
            preserveRemoteConflict(remote)
            throw CloudBackendTransport.Failure("SERVER_HASH_CONFLICT")
        }
    }

    /** Persist proof before any request, and never mint a replacement identity over bound data. */
    private fun session(forceRenew: Boolean = false): CloudBackendTransport.Session {
        val context = application ?: throw CloudBackendTransport.Failure("CONTEXT_UNAVAILABLE")
        val preferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        val state = GameSaveStore.cloudDatabase { CloudSaveJournal.state(it) }
        val pending = GameSaveStore.cloudDatabase { CloudSaveJournal.hasPending(it) }
        val storedPlayer = preferences.getString("player_id", null)
        val previousPlayer = state.playerId ?: storedPlayer
        if (state.playerId != null && storedPlayer != null && state.playerId != storedPlayer) throw CloudBackendTransport.Failure("SESSION_IDENTITY_CONFLICT")
        var secret = preferences.getString("installation_secret", null)
        if (secret == null) {
            if (previousPlayer != null || pending) throw CloudBackendTransport.Failure("SESSION_RECOVERY_REQUIRED")
            secret = Base64.encodeToString(ByteArray(32).also { SecureRandom().nextBytes(it) }, Base64.URL_SAFE or Base64.NO_WRAP or Base64.NO_PADDING)
            if (!preferences.edit().putString("installation_secret", secret).commit()) throw CloudBackendTransport.Failure("SESSION_STORAGE_FAILED")
        }
        if (!secret.matches(Regex("[A-Za-z0-9_-]{43}"))) throw CloudBackendTransport.Failure("SESSION_RECOVERY_REQUIRED")
        val token = preferences.getString("access_token", null)
        val expires = preferences.getLong("expires_at", 0L)
        val initialization = preferences.getString("initialization", null)?.let {
            try { JSONObject(it) } catch (_: Exception) { throw CloudBackendTransport.Failure("SESSION_INITIALIZATION_CORRUPT") }
        }
        if (!forceRenew && previousPlayer != null && token != null && token.matches(Regex("[A-Za-z0-9_-]{43}")) &&
            expires > System.currentTimeMillis() / 1000L + 30L) return CloudBackendTransport.Session(previousPlayer, token, expires, initialization)
        val issued = transport!!.session(secret, previousPlayer)
        if (!preferences.edit().putString("player_id", issued.playerId).putString("access_token", issued.accessToken)
            .putLong("expires_at", issued.expiresAt).putString("initialization", issued.initialization?.toString()).commit()) throw CloudBackendTransport.Failure("SESSION_STORAGE_FAILED")
        return issued
    }

    private fun <T> authenticated(action: (String) -> T): T {
        try { return action(session().accessToken) }
        catch (failure: CloudBackendTransport.Failure) {
            if (failure.status != 401) throw failure
            return action(session(true).accessToken)
        }
    }

    private fun flushPending(maximum: Int, deadlineMillis: Long) {
        repeat(maximum) {
            if (SystemClock.elapsedRealtime() >= deadlineMillis) return
            val work = GameSaveStore.cloudDatabase { CloudSaveJournal.head(it) } ?: return
            try {
                val receipt = saveWork(work)
                if (receipt.currentRevision != receipt.revision) throw CloudBackendTransport.Failure("REVISION_CONFLICT", 409)
                GameSaveStore.cloudDatabase(true) { db ->
                    CloudSaveJournal.acknowledge(db, work, receipt.revision, receipt.currentRevision, receipt.hash)
                }
            } catch (failure: CloudBackendTransport.Failure) {
                if (failure.status == 409 || failure.code == "INITIALIZATION_MISMATCH" ||
                    failure.code == "ADOPTION_AUTHORIZATION_REQUIRED") {
                    GameSaveStore.cloudDatabase(true) { CloudSaveJournal.recordConflict(it, failure.code) }
                    try { authenticated { token -> transport!!.load(token) }?.let(::preserveRemoteConflict) } catch (_: Exception) { /* Pending local work remains durable. */ }
                }
                throw failure
            }
        }
    }

    private fun saveWork(work: CloudSaveJournal.Work): CloudBackendTransport.Receipt {
        try { return authenticated { token -> transport!!.save(token, work) } }
        catch (failure: CloudBackendTransport.Failure) {
            val requiresApproval = (failure.status == 403 && failure.code == "ADOPTION_AUTHORIZATION_REQUIRED") ||
                (failure.status == 400 && failure.code == "INITIALIZATION_MISMATCH")
            if (!requiresApproval || work.expectedRevision != 0L ||
                GameSaveStore.cloudDatabase { CloudSaveJournal.state(it).originKind } != "approved-local-copy") throw failure
            // This path is available only after the user approved a copy of
            // existing local progress. A failed fresh initialization never uses it.
            val grant = authenticated { token -> transport!!.authorizeAdoption(token, work) }
            GameSaveStore.cloudDatabase(true) { CloudSaveJournal.authorizeAdoption(it, work, grant.token) }
            val authorized = work.copy(adoptionToken = grant.token)
            return authenticated { token -> transport!!.save(token, authorized) }
        }
    }

    private fun preserveRemoteConflict(remote: CloudBackendTransport.RemoteSave) {
        val context = application ?: return
        CloudSaveJournal.writeBackup(context, remote.snapshot, "greenfarm-server-conflict-${remote.revision}.gf3save")
    }

    @JvmStatic fun localCommitAvailable() { if (prepared) scheduleSync(500L) }

    private fun scheduleSync(delayMillis: Long) {
        if (!prepared || !scheduled.compareAndSet(false, true)) return
        worker.schedule({
            var retry = false
            try {
                val state = GameSaveStore.cloudDatabase { CloudSaveJournal.state(it) }
                if (state.conflict == null) {
                    flushPending(8, SystemClock.elapsedRealtime() + 15000L)
                    warningVisible = false
                } else showConflict(state.conflict)
            } catch (failure: Exception) {
                val code = safeCode(failure)
                Log.w("GreenFarmCloud", "sync_pending code=$code")
                retry = true
                if (failure is CloudBackendTransport.Failure && (failure.status == 409 || failure.code == "INITIALIZATION_MISMATCH" ||
                    failure.code == "ADOPTION_AUTHORIZATION_REQUIRED")) showConflict(code)
            } finally {
                scheduled.set(false)
                if (prepared) {
                    val needsWork = try { GameSaveStore.cloudDatabase { CloudSaveJournal.state(it).conflict == null && CloudSaveJournal.hasPending(it) } }
                    catch (_: Exception) { false }
                    if (needsWork) scheduleSync(if (retry) 8000L else 500L)
                }
            }
        }, delayMillis, TimeUnit.MILLISECONDS)
    }

    /** Called on the game thread before quit cleanup discards any farm entities. */
    @JvmStatic fun requestQuit(continuation: Runnable) {
        requestQuit(Runnable { }, continuation)
    }

    /** Settle earned rewards/cancel edits before taking the final immutable snapshot. */
    @JvmStatic fun requestQuit(preparation: Runnable, continuation: Runnable) {
        if (quitAction != null) return
        preparation.run()
        quitCheckpointDone = false
        quitCheckpointStarted = false
        quitAction = continuation
    }

    /** Continuations run only here on the game thread, never on the network writer. */
    @JvmStatic fun frameFinished() {
        val action = quitAction ?: return
        if (!quitCheckpointStarted) {
            if (!ProgressSaveController.flushForLifecycle()) return
            quitCheckpointStarted = true
            quitDeadlineMillis = SystemClock.elapsedRealtime() + 8000L
            worker.execute {
                try { flushPending(4, quitDeadlineMillis) }
                catch (failure: Exception) { Log.w("GreenFarmCloud", "quit_save_pending code=${safeCode(failure)}") }
                finally { if (quitAction === action) quitCheckpointDone = true }
            }
            return
        }
        if (SystemClock.elapsedRealtime() >= quitDeadlineMillis) quitCheckpointDone = true
        if (quitCheckpointDone) {
            // SDK callbacks during the request also become locally durable.
            // If network did not acknowledge them, the outbox survives exit.
            if (!ProgressSaveController.flushForLifecycle()) return
            quitAction = null
            quitCheckpointStarted = false
            quitCheckpointDone = false
            action.run()
        }
    }

    private fun showStartupFailure(code: String) {
        val target = activity.get() ?: return
        val approval = if (code == "LOCAL_ADOPTION_APPROVAL_REQUIRED") try {
            GameSaveStore.cloudDatabase { db ->
                val owner = CloudSaveJournal.state(db).playerId
                val pending = CloudSaveJournal.head(db)
                if (owner != null && owner.matches(Regex("[0-9a-f-]{36}")) && pending != null && pending.snapshotHash.matches(Regex("[0-9a-f]{64}")))
                    "node scripts/approve-local-adoption.js --player-id $owner --snapshot-hash ${pending.snapshotHash} --expires-in 900" else null
            }
        } catch (_: Exception) { null } else null
        target.runOnUiThread {
            if (!target.isFinishing) {
                val builder = AlertDialog.Builder(target)
                .setTitle("Không thể đồng bộ tiến độ")
                .setMessage(if (approval != null) "Bản lưu cục bộ và bản chờ được giữ nguyên. Người quản lý backend cần phê duyệt bản sao này một lần. Chạy lệnh sau trong thư mục backend rồi nhấn Thử lại:\n\n$approval"
                    else "Chưa mở nông trại vì máy chủ chưa xác nhận dữ liệu/phiên hợp lệ. Bản lưu và bản chờ trên máy được giữ nguyên. Kiểm tra backend rồi thử lại. Mã: $code")
                .setCancelable(false)
                .setPositiveButton("Thử lại") { _, _ -> prepare(target) }
                .setNegativeButton("Đóng") { _, _ -> target.finish() }
                if (approval != null) builder.setNeutralButton("Sao chép lệnh") { _, _ ->
                    (target.getSystemService(Context.CLIPBOARD_SERVICE) as? ClipboardManager)?.setPrimaryClip(ClipData.newPlainText("Phê duyệt bản sao Green Farm", approval))
                    showStartupFailure(code)
                }
                builder.show()
            }
        }
    }

    private fun showConflict(code: String) {
        if (warningVisible) return
        warningVisible = true
        val target = javax.microedition.midlet.MIDlet.cnz ?: activity.get() ?: return
        target.runOnUiThread {
            if (!target.isFinishing) AlertDialog.Builder(target)
                .setTitle("Bản lưu cần đối chiếu")
                .setMessage("Máy chủ có phiên bản khác. Bản lưu chờ và bản máy chủ được giữ riêng; ứng dụng không tự ghi đè. Mã: $code")
                .setPositiveButton("Đã hiểu", null).show()
        }
    }

    private fun safeCode(failure: Exception): String = if (failure is CloudBackendTransport.Failure) failure.code else "LOCAL_STORAGE_OR_STATE_ERROR"
}
