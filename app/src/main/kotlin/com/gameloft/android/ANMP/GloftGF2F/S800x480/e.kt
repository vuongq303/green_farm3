package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.Vector

/** Original CloudSave model; public short names remain the legacy caller ABI. */
open class e {
    private var playerIdentifier: String? = null
    private var description: String? = null
    private var operation: Byte = 0
    private val saveKeys = Vector<String?>()
    private var serializedByteCount = 0L
    private var appendedChunkCount = 0
    private var saveDate = 0L
    private var storageOwner: String? = "anonymous"
    private val saveVersion: String? = "1"
    private val serializedSave = bp()

    open fun l(saveDate: Long) { this.saveDate = saveDate }
    open fun `as`(description: String?) { this.description = description }
    open fun b(operation: Byte) { this.operation = operation }
    open fun hQ(): Byte = operation
    open fun a(chunk: bp?) {
        val existing = serializedSave.data
        if (existing != null) {
            val priorLength = existing.size
            val combinedLength = chunk!!.data!!.size + priorLength
            val priorCopy = ByteArray(priorLength)
            System.arraycopy(existing, 0, priorCopy, 0, priorLength)
            serializedSave.data = ByteArray(combinedLength)
            System.arraycopy(priorCopy, 0, serializedSave.data, 0, priorLength)
            System.arraycopy(chunk!!.data, 0, serializedSave.data, priorLength, chunk!!.data!!.size)
        } else {
            serializedSave.data = ByteArray(chunk!!.data!!.size)
            System.arraycopy(chunk!!.data, 0, serializedSave.data, 0, chunk!!.data!!.size)
        }
        serializedByteCount += chunk!!.data!!.size.toLong()
        appendedChunkCount++
    }
    open fun at(playerIdentifier: String?) { this.playerIdentifier = playerIdentifier }
    open fun au(saveKey: String?) { saveKeys.addElement(saveKey) }
    open fun hR(): String? = storageOwner
    open fun av(storageOwner: String?) { this.storageOwner = storageOwner }
    open fun hS(): Long = saveDate
    open fun hT(): bp = serializedSave
    open fun hU(): String? = playerIdentifier
    open fun hV(): String? = saveKeys.lastElement()
    open fun hW(): String? = saveVersion
    open fun hX(): String? = description
}
