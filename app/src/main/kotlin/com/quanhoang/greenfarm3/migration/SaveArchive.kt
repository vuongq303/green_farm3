package com.quanhoang.greenfarm3.migration

import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.DataInputStream
import java.io.DataOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.charset.CodingErrorAction
import java.security.DigestInputStream
import java.security.DigestOutputStream
import java.security.MessageDigest

/** Bounded, checksummed transfer of the original opaque RMS records, never SQL or executable code. */
object SaveArchive {
    const val MIME_TYPE = "application/vnd.greenfarm3.save"
    const val FILE_EXTENSION = ".gf3save"
    const val DATABASE_NAME = "greenfarm-progress.db"
    const val SLOT_ID = "device-local"
    const val MAX_RECORD_BYTES = 64 * 1024 * 1024
    const val MAX_TOTAL_BYTES = 128L * 1024 * 1024
    const val MAX_RECORD_COUNT = 2048
    private const val ARCHIVE_VERSION = 1
    private val MAGIC = byteArrayOf(71, 70, 51, 83, 65, 86, 69, 49) // GF3SAVE1
    private val SOURCE_PACKAGES = setOf("com.gameloft.android.ANMP.GloftGF2F", "com.quanhoang.greenfarm3")

    data class Record(val name: String, val payload: ByteArray?, val generation: Long)
    data class Snapshot(
        val sourcePackage: String,
        val generation: Long,
        val completeSnapshotGeneration: Long,
        val snapshotFormat: Int,
        val poseSnapshot: Int,
        val createdAtMillis: Long,
        val updatedAtMillis: Long,
        val records: List<Record>
    )

    fun checksum(payload: ByteArray): ByteArray = MessageDigest.getInstance("SHA-256").digest(payload)

    /** The format4 writer prefixes a UTF-8 device binding with a little-endian length. */
    fun gameplayBinding(payload: ByteArray): String {
        requireValid(payload.size >= 6 && payload[0].toInt() and 255 == 4,
            "Chỉ chuyển bản lưu format4 đã mở và lưu thành công bằng bản khôi phục hiện tại.")
        val length = ByteBuffer.wrap(payload, 1, 4).order(ByteOrder.LITTLE_ENDIAN).int
        requireValid(length in 1..1024 && 5L + length <= payload.size, "Định danh bản lưu không hợp lệ.")
        val binding = decodeUtf8(payload.copyOfRange(5, 5 + length))
        requireValid(binding.none { it == '\u0000' || it < ' ' }, "Định danh bản lưu không hợp lệ.")
        return binding
    }

    fun validate(snapshot: Snapshot) {
        requireValid(snapshot.sourcePackage in SOURCE_PACKAGES, "Tệp không được xuất bởi phiên bản Green Farm 3 được hỗ trợ.")
        requireValid(snapshot.generation > 0 && snapshot.completeSnapshotGeneration in 1..snapshot.generation,
            "Bản lưu chưa có checkpoint nông trại hoàn chỉnh.")
        requireValid(snapshot.snapshotFormat in 0..1 && snapshot.poseSnapshot in 0..1,
            "Định dạng checkpoint không được hỗ trợ.")
        requireValid(snapshot.createdAtMillis >= 0 && snapshot.updatedAtMillis >= 0, "Thời gian bản lưu không hợp lệ.")
        requireValid(snapshot.records.size in 1..MAX_RECORD_COUNT, "Số bản ghi vượt giới hạn.")
        val names = HashSet<String>()
        var totalBytes = 0L
        for (record in snapshot.records) {
            requireValid(record.name.isNotEmpty() && record.name.length <= 512 && '\u0000' !in record.name
                && '/' !in record.name && names.add(record.name), "Tên bản ghi trống, trùng hoặc không hợp lệ.")
            requireValid(record.generation in 0..snapshot.generation, "Thứ tự bản ghi không hợp lệ.")
            val size = record.payload?.size ?: 0
            totalBytes += size
            requireValid(size <= MAX_RECORD_BYTES && totalBytes <= MAX_TOTAL_BYTES, "Bản lưu vượt giới hạn kích thước.")
        }
        val liveRecords = snapshot.records.filter { it.payload != null }.associate { it.name to it.payload!! }
        requireValid((liveRecords["RMSHeader"]?.size ?: 0) >= 35, "Thiếu header ngôn ngữ/cài đặt.")
        val gameplay = liveRecords["save_gameplay"] ?: throw IOException("Thiếu dữ liệu tiến trình.")
        requireValid((liveRecords["save_farm0"]?.size ?: 0) > 0, "Thiếu dữ liệu nông trại.")
        gameplayBinding(gameplay)
        if (snapshot.snapshotFormat == 1) validateWordRecord(liveRecords["pending_rewards_v1"], 0x47465257, 28, true)
        if (snapshot.poseSnapshot == 1) validateWordRecord(liveRecords["entity_pose_v1"], 0x47465053, 6, false)
    }

    private fun validateWordRecord(payload: ByteArray?, magic: Int, words: Int, rewards: Boolean) {
        requireValid(payload != null && payload.size >= 12, "Thiếu trạng thái thưởng hoặc hướng đối tượng.")
        val bytes = payload!!
        val buffer = ByteBuffer.wrap(bytes).order(ByteOrder.BIG_ENDIAN)
        requireValid(buffer.int == magic && buffer.int == 1, "Trạng thái thưởng/hướng đối tượng không được hỗ trợ.")
        val count = buffer.int
        requireValid(count in 0..16384 && bytes.size.toLong() == 12L + count.toLong() * words * 4,
            "Trạng thái thưởng/hướng đối tượng bị cắt hoặc sai số lượng.")
        repeat(count) {
            val entry = IntArray(words) { buffer.int }
            if (rewards) requireValid(entry[19] and 3.inv() == 0, "Cờ thưởng không hợp lệ.")
            else requireValid(entry[4] >= 0 && entry[5] in 0..7, "Hướng đối tượng không hợp lệ.")
        }
    }

    fun write(snapshot: Snapshot, destination: OutputStream) {
        validate(snapshot)
        val digest = MessageDigest.getInstance("SHA-256")
        val hashed = DigestOutputStream(BufferedOutputStream(destination), digest)
        val output = DataOutputStream(hashed)
        output.write(MAGIC)
        output.writeInt(ARCHIVE_VERSION)
        output.writeInt(4) // SQLite schema; the opaque game payload format is separately validated.
        writeString(output, snapshot.sourcePackage)
        output.writeLong(snapshot.generation)
        output.writeLong(snapshot.completeSnapshotGeneration)
        output.writeInt(snapshot.snapshotFormat)
        output.writeInt(snapshot.poseSnapshot)
        output.writeLong(snapshot.createdAtMillis)
        output.writeLong(snapshot.updatedAtMillis)
        output.writeInt(snapshot.records.size)
        for (record in snapshot.records.sortedBy { it.name }) {
            writeString(output, record.name)
            output.writeLong(record.generation)
            val payload = record.payload
            output.writeInt(payload?.size ?: -1)
            if (payload != null) {
                output.write(payload)
                output.write(checksum(payload))
            }
        }
        output.flush()
        hashed.on(false)
        output.write(digest.digest())
        output.flush()
    }

    fun read(source: InputStream): Snapshot {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashed = DigestInputStream(BufferedInputStream(source), digest)
        val input = DataInputStream(hashed)
        val magic = ByteArray(MAGIC.size).also { input.readFully(it) }
        requireValid(magic.contentEquals(MAGIC) && input.readInt() == ARCHIVE_VERSION && input.readInt() == 4,
            "Tệp không phải bản lưu Green Farm 3 được hỗ trợ.")
        val sourcePackage = readString(input)
        val generation = input.readLong()
        val completeGeneration = input.readLong()
        val snapshotFormat = input.readInt()
        val poseSnapshot = input.readInt()
        val createdAt = input.readLong()
        val updatedAt = input.readLong()
        val count = input.readInt()
        requireValid(count in 1..MAX_RECORD_COUNT, "Số bản ghi vượt giới hạn.")
        val records = ArrayList<Record>(count)
        var totalBytes = 0L
        repeat(count) {
            val name = readString(input)
            val recordGeneration = input.readLong()
            val size = input.readInt()
            requireValid(size in -1..MAX_RECORD_BYTES, "Kích thước bản ghi không hợp lệ.")
            totalBytes += if (size < 0) 0 else size
            requireValid(totalBytes <= MAX_TOTAL_BYTES, "Bản lưu vượt giới hạn kích thước.")
            val payload = if (size == -1) null else ByteArray(size).also {
                input.readFully(it)
                val expectedChecksum = ByteArray(32).also(input::readFully)
                requireValid(MessageDigest.isEqual(expectedChecksum, checksum(it)), "Checksum bản ghi không hợp lệ.")
            }
            records.add(Record(name, payload, recordGeneration))
        }
        hashed.on(false)
        val expectedArchiveChecksum = ByteArray(32).also(input::readFully)
        requireValid(MessageDigest.isEqual(expectedArchiveChecksum, digest.digest()), "Checksum tệp không hợp lệ.")
        requireValid(input.read() == -1, "Tệp có dữ liệu dư không được hỗ trợ.")
        return Snapshot(sourcePackage, generation, completeGeneration, snapshotFormat, poseSnapshot,
            createdAt, updatedAt, records).also(::validate)
    }

    private fun writeString(output: DataOutputStream, value: String) {
        val bytes = value.toByteArray(Charsets.UTF_8)
        requireValid(bytes.size in 1..4096, "Chuỗi bản lưu vượt giới hạn.")
        output.writeInt(bytes.size)
        output.write(bytes)
    }

    private fun readString(input: DataInputStream): String {
        val size = input.readInt()
        requireValid(size in 1..4096, "Chuỗi bản lưu vượt giới hạn.")
        return decodeUtf8(ByteArray(size).also(input::readFully))
    }

    private fun decodeUtf8(bytes: ByteArray): String = Charsets.UTF_8.newDecoder()
        .onMalformedInput(CodingErrorAction.REPORT).onUnmappableCharacter(CodingErrorAction.REPORT)
        .decode(ByteBuffer.wrap(bytes)).toString()

    private fun requireValid(condition: Boolean, message: String) {
        if (!condition) throw IOException(message)
    }
}
