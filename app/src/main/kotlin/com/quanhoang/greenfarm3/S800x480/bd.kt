package com.quanhoang.greenfarm3.S800x480

open class bd(capacity: Int) {
    private val slotToEntry: ShortArray
    private val entryKeys: IntArray
    private val entryValues: IntArray
    private var size = 0
    init {
        if (capacity > 36863) throw RuntimeException("invalid table size")
        slotToEntry = ShortArray((capacity.toDouble() * 1.333).toInt())
        entryKeys = IntArray(capacity)
        entryValues = IntArray(capacity)
        reset()
    }
    open fun reset() {
        for (slotIndex in slotToEntry.indices) slotToEntry[slotIndex] = -1
        size = 0
    }
    private fun hashSlot(key: Int): Int = Math.abs((((((((key and 255) + 31) * 31) + ((key ushr 8) and 255)) * 31) + ((key ushr 16) and 255)) * 31) + ((key ushr 24) and 255)) % slotToEntry.size
    private fun findSlot(key: Int): Int {
        var slotIndex = hashSlot(key)
        var probeIndex = 1
        while (true) {
            val entryIndex = slotToEntry[slotIndex].toInt()
            if (entryIndex < 0 || entryKeys[entryIndex] == key) break
            slotIndex = Math.abs(slotIndex + (probeIndex * probeIndex)) % slotToEntry.size
            probeIndex++
        }
        return slotIndex
    }
    open fun a(previousKey: Int, nextByte: Byte, value: Int): Int {
        val key = ((65535 and previousKey) shl 8) or (nextByte.toInt() and 255)
        if (value >= 0) {
            val slotIndex = findSlot(key)
            if (slotToEntry[slotIndex] < 0 && size != entryValues.size) {
                slotToEntry[slotIndex] = size.toShort()
                entryKeys[size] = key
                entryValues[size] = value
                size++
            }
        }
        return key
    }
    open fun f(bytes: ByteArray?, offset: Int, length: Int): Int {
        if (length > 2) {
            val source = bytes!!
            val key = 0 or ((source[offset].toInt() and 255) shl 16) or ((source[offset + 1].toInt() and 255) shl 8) or (source[offset + 2].toInt() and 255)
            val entryIndex = slotToEntry[findSlot(key)].toInt()
            if (entryIndex >= 0) return entryValues[entryIndex]
        }
        return -1
    }
}
