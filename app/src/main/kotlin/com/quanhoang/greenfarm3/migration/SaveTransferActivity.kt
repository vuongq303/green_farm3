package com.quanhoang.greenfarm3.migration

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.ComponentName
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Build
import android.os.Environment
import android.os.ResultReceiver
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import java.io.File
import java.io.IOException
import java.util.concurrent.Executors

/** User-driven SAF transfer. This activity must run in the separate :save_transfer process. */
class SaveTransferActivity : Activity() {
    private val worker = Executors.newSingleThreadExecutor { command -> Thread(command, "GreenFarm-SaveTransfer") }
    private lateinit var status: TextView
    private val buttons = ArrayList<Button>()
    private var busy = false
    private var prepared = false
    private var preparationStarted = false
    private var preparationEpoch = 0
    private var stagedImport: SaveArchive.Snapshot? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(24, 24, 24, 24)
        }
        layout.addView(TextView(this).apply {
            text = "Chuyển bản lưu Green Farm 3\n\nXuất ở bản cũ, chọn tệp đó để nhập ở bản mới. Game sẽ lưu và đóng trước khi chuyển. Tệp chứa tiến độ riêng tư; không chia sẻ công khai."
        })
        status = TextView(this).apply { setPadding(0, 16, 0, 16) }
        layout.addView(status)
        fun button(label: String, clicked: () -> Unit) {
            val view = Button(this).apply { text = label; setOnClickListener { clicked() } }
            buttons.add(view); layout.addView(view)
        }
        button("Chuẩn bị / thử lại") { prepareGame() }
        button("Xuất bản lưu hiện tại") { createDocument(EXPORT_CURRENT) }
        button("Nhập bản lưu từ tệp") { selectDocument() }
        button("Xuất bản sao trước lần nhập gần nhất") { createDocument(EXPORT_BACKUP) }
        button("Mở game") { launchGame() }
        setContentView(layout)
        status.text = "Nhấn Chuẩn bị để lưu và đóng phiên game trước khi chuyển dữ liệu."
    }

    private fun prepareGame() {
        if (preparationStarted || busy) return
        preparationStarted = true
        val epoch = ++preparationEpoch
        prepared = false
        setBusy(true, "Đang hoàn tất checkpoint và đóng phiên game…")
        val response = object : ResultReceiver(Handler(Looper.getMainLooper())) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                if (isFinishing || epoch != preparationEpoch || !preparationStarted) return
                if (resultCode != SaveTransferPreparationReceiver.RESULT_READY) {
                    preparationStarted = false
                    setBusy(false, "Chưa lưu xong. Game vẫn được giữ nguyên. Hãy quay lại game hoặc thử lại; chưa có dữ liệu nào bị thay thế.")
                    return
                }
                // Wait for the old process to end and the kernel to release its lock.
                worker.execute {
                    var ready = false
                    repeat(50) {
                        if (!ready) {
                            ready = try { SaveTransferGate.withTransferLock(this@SaveTransferActivity) { true } }
                            catch (_: IOException) { false }
                            if (!ready) Thread.sleep(100)
                        }
                    }
                    runOnUiThread {
                        if (!isFinishing) {
                            preparationStarted = false
                            prepared = ready
                            setBusy(false, if (ready) "Phiên game đã đóng. Có thể xuất hoặc nhập bản lưu."
                                else "Phiên game chưa đóng xong. Hãy thử Chuẩn bị lại; chưa thay đổi bản lưu.")
                        }
                    }
                }
            }
        }
        sendBroadcast(Intent(SaveTransferPreparationReceiver.ACTION_PREPARE)
            .setComponent(ComponentName(packageName, SaveTransferPreparationReceiver::class.java.name))
            .putExtra(SaveTransferPreparationReceiver.EXTRA_RESPONSE, response))
        Handler(Looper.getMainLooper()).postDelayed({
            if (preparationStarted && epoch == preparationEpoch && !isFinishing) {
                preparationStarted = false
                setBusy(false, "Chưa nhận được checkpoint. Hãy thử lại; dữ liệu chưa bị thay thế.")
            }
        }, 20000)
    }

    private fun requirePrepared(): Boolean {
        if (prepared && !busy) return true
        status.text = "Hãy nhấn Chuẩn bị và chờ game lưu/đóng trước."
        return false
    }

    private fun createDocument(request: Int) {
        if (!requirePrepared()) return
        if (request == EXPORT_BACKUP && !File(filesDir, SaveArchiveDatabase.BACKUP_FILE).isFile) {
            status.text = "Chưa có bản sao trước khi nhập."; return
        }
        if (Build.VERSION.SDK_INT < 19) {
            val directory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val destination = File(directory, "greenfarm3-${System.currentTimeMillis()}.gf3save")
            AlertDialog.Builder(this).setTitle("Xuất bản lưu")
                .setMessage("Android này chưa có trình chọn nơi lưu SAF. Xuất vào ${destination.absolutePath}?")
                .setNegativeButton("Hủy", null).setPositiveButton("Xuất") { _, _ ->
                    directory.mkdirs()
                    export(Uri.fromFile(destination), request == EXPORT_BACKUP)
                }.show()
            return
        }
        openPicker(Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = SaveArchive.MIME_TYPE
            putExtra(Intent.EXTRA_TITLE, if (request == EXPORT_BACKUP) "greenfarm-before-import.gf3save" else "greenfarm3-progress.gf3save")
        }, request)
    }

    private fun selectDocument() {
        if (!requirePrepared()) return
        openPicker(Intent(if (Build.VERSION.SDK_INT >= 19) Intent.ACTION_OPEN_DOCUMENT else Intent.ACTION_GET_CONTENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "*/*"
        }, IMPORT)
    }

    private fun openPicker(intent: Intent, request: Int) {
        try { startActivityForResult(intent, request) }
        catch (_: ActivityNotFoundException) {
            status.text = "Thiết bị chưa có trình chọn tệp. Hãy cài/bật ứng dụng quản lý tệp rồi thử lại; dữ liệu chưa bị thay đổi."
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_OK) return
        val uri = data?.data ?: return
        if (requestCode == IMPORT) inspectImport(uri)
        else if (requestCode == EXPORT_CURRENT || requestCode == EXPORT_BACKUP) export(uri, requestCode == EXPORT_BACKUP)
    }

    private fun export(uri: Uri, backup: Boolean) {
        if (!requirePrepared()) return
        execute("Đang xuất bản lưu…") {
            SaveTransferGate.withTransferLock(this) {
                contentResolver.openOutputStream(uri, "wt")?.use { output ->
                    if (backup) {
                        val snapshot = File(filesDir, SaveArchiveDatabase.BACKUP_FILE).inputStream().use(SaveArchive::read)
                        SaveArchive.write(snapshot, output)
                    }
                    else SaveArchive.write(SaveArchiveDatabase.read(this), output)
                } ?: throw IOException("Không mở được tệp đích.")
            }
            "Đã xuất bản lưu. Hãy giữ ứng dụng cũ cho đến khi bản mới nhập và mở lại farm thành công."
        }
    }

    private fun inspectImport(uri: Uri) {
        if (!requirePrepared()) return
        setBusy(true, "Đang kiểm tra checksum và cấu trúc bản lưu…")
        worker.execute {
            try {
                val snapshot = contentResolver.openInputStream(uri)?.use(SaveArchive::read)
                    ?: throw IOException("Không mở được tệp đã chọn.")
                runOnUiThread {
                    if (!isFinishing) {
                        stagedImport = snapshot
                        setBusy(false, "Tệp hợp lệ; đang chờ xác nhận nhập.")
                        AlertDialog.Builder(this).setTitle("Thay thế tiến độ trong ứng dụng này?")
                            .setMessage("Bản lưu được xuất từ ${snapshot.sourcePackage}, gồm ${snapshot.records.size} bản ghi. Tiến độ hiện tại sẽ được sao lưu trước khi thay thế. Ứng dụng cũ không bị xóa hoặc thay đổi.")
                            .setNegativeButton("Hủy") { _, _ -> stagedImport = null; status.text = "Đã hủy; bản lưu hiện tại không đổi." }
                            .setOnCancelListener { stagedImport = null; status.text = "Đã hủy; bản lưu hiện tại không đổi." }
                            .setPositiveButton("Nhập bản lưu") { _, _ ->
                                val accepted = stagedImport
                                stagedImport = null
                                if (accepted != null) execute("Đang nhập bản lưu…") {
                                    SaveTransferGate.withTransferLock(this) { SaveArchiveDatabase.install(this, accepted) }
                                    "Đã nhập và kiểm tra bản lưu. Nhấn Mở game; không gỡ ứng dụng cũ trước khi kiểm tra farm mới."
                                }
                            }.show()
                    }
                }
            } catch (failure: Exception) { showFailure(failure) }
        }
    }

    private fun execute(message: String, operation: () -> String) {
        setBusy(true, message)
        worker.execute {
            try {
                val result = operation()
                runOnUiThread { if (!isFinishing) setBusy(false, result) }
            } catch (failure: Exception) { showFailure(failure) }
        }
    }

    private fun showFailure(failure: Exception) {
        val message = when (failure) {
            is IOException -> failure.message ?: "Không đọc/ghi được tệp."
            else -> "Không thể chuyển bản lưu. Kiểm tra bộ nhớ và tệp; bản lưu chưa được xác nhận thay thế."
        }
        runOnUiThread { if (!isFinishing) setBusy(false, "Lỗi: $message") }
    }

    private fun launchGame() {
        if (busy) return
        val launch = packageManager.getLaunchIntentForPackage(packageName)
        if (launch == null) { status.text = "Không tìm thấy màn hình game."; return }
        startActivity(launch.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        finish()
    }

    private fun setBusy(value: Boolean, message: String) {
        busy = value
        status.text = message
        buttons.forEach { it.isEnabled = !value }
    }

    override fun onDestroy() {
        worker.shutdown()
        super.onDestroy()
    }

    companion object {
        private const val EXPORT_CURRENT = 6101
        private const val IMPORT = 6102
        private const val EXPORT_BACKUP = 6103
        @JvmStatic fun launch(activity: Activity) {
            activity.startActivity(Intent(activity, SaveTransferActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        }
    }
}
