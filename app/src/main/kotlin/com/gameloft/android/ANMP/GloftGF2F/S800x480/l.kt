package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.io.DataInput
import java.io.DataInputStream
import java.io.InputStream

/**
 * Endian-aware asset reader, restored against the original l bytecode.
 * The short class/field names are retained at this Java/DEX ABI boundary.
 * order == 0 means big endian; every other value means little endian.
 * InputStream.close/mark/skip defaults deliberately remain inherited.
 */
open class l(inputStream: InputStream?) : InputStream(), DataInput {
    @JvmField var Ln: InputStream? = inputStream
    @JvmField var Lm: DataInputStream? = DataInputStream(inputStream)
    @JvmField var order: Int = 0
    private val scratchBytes = ByteArray(8)

    open fun cf(byteOrderCode: Int) { setByteOrder(byteOrderCode) }
    fun setByteOrder(byteOrderCode: Int) { order = byteOrderCode }

    override fun available(): Int = Lm!!.available()
    override fun read(): Int = Ln!!.read()

    final override fun readFloat(): Float = if (order == 0) java.lang.Float.intBitsToFloat(readInt())
        else java.lang.Float.intBitsToFloat(readLittleEndianInt())
    final override fun readDouble(): Double = if (order == 0) java.lang.Double.longBitsToDouble(readLong())
        else java.lang.Double.longBitsToDouble(readLittleEndianLong())
    final override fun readShort(): Short = if (order == 0) Lm!!.readShort() else readLittleEndianUnsignedShort().toShort()
    final override fun readUnsignedShort(): Int = if (order == 0) Lm!!.readUnsignedShort() else readLittleEndianUnsignedShort()
    final override fun readInt(): Int = if (order == 0) Lm!!.readInt() else readLittleEndianInt()
    final override fun readLong(): Long = if (order == 0) Lm!!.readLong() else readLittleEndianLong()

    private fun readLittleEndianUnsignedShort(): Int {
        Lm!!.readFully(scratchBytes, 0, 2)
        return ((scratchBytes[1].toInt() and 255) shl 8) or (scratchBytes[0].toInt() and 255)
    }
    private fun readLittleEndianInt(): Int {
        Lm!!.readFully(scratchBytes, 0, 4)
        return (scratchBytes[3].toInt() shl 24) or
            ((scratchBytes[2].toInt() and 255) shl 16) or
            ((scratchBytes[1].toInt() and 255) shl 8) or (scratchBytes[0].toInt() and 255)
    }
    private fun readLittleEndianLong(): Long {
        Lm!!.readFully(scratchBytes, 0, 8)
        return (scratchBytes[7].toLong() shl 56) or
            ((scratchBytes[6].toLong() and 255L) shl 48) or
            ((scratchBytes[5].toLong() and 255L) shl 40) or
            ((scratchBytes[4].toLong() and 255L) shl 32) or
            ((scratchBytes[3].toLong() and 255L) shl 24) or
            ((scratchBytes[2].toLong() and 255L) shl 16) or
            ((scratchBytes[1].toLong() and 255L) shl 8) or (scratchBytes[0].toLong() and 255L)
    }

    // Original DEX declared DataInput but omitted these unused interface
    // methods. Complete that contract with the existing DataInputStream; all
    // endian-sensitive game methods above preserve their original behavior.
    override fun readFully(buffer: ByteArray) = Lm!!.readFully(buffer)
    override fun readFully(buffer: ByteArray, offset: Int, byteCount: Int) = Lm!!.readFully(buffer, offset, byteCount)
    override fun skipBytes(byteCount: Int): Int = Lm!!.skipBytes(byteCount)
    override fun readBoolean(): Boolean = Lm!!.readBoolean()
    override fun readByte(): Byte = Lm!!.readByte()
    override fun readUnsignedByte(): Int = Lm!!.readUnsignedByte()
    override fun readChar(): Char = Lm!!.readChar()
    @Suppress("DEPRECATION")
    override fun readLine(): String? = Lm!!.readLine()
    override fun readUTF(): String = Lm!!.readUTF()
}
