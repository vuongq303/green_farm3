package com.quanhoang.greenfarm3.S800x480

import android.os.SystemClock
import java.util.Random

/** Original digest-driven SecureRandom algorithm, including its counter and byte order. */
open class bs : Random(0L) {
    @JvmField var akf = k(0)
    private var stateBytes = ByteArray(akf.im())
    private var reseedCounter = 1L
    private var outputBytes = ByteArray(akf.im())
    private var outputCounter = 1L
    init {
        setSeed(if (w.Vl) (SystemClock.elapsedRealtime() + GameRuntime.Tn) - GameRuntime.To else System.currentTimeMillis())
    }
    override fun nextBytes(bytes: ByteArray?) { h(bytes) }
    override fun setSeed(seed: Long) {
        // Random's constructor dispatches setSeed(0) before this class initializes its fields.
        if (seed != 0L) t(seed)
    }
    override fun nextInt(): Int {
        val bytes = ByteArray(4)
        nextBytes(bytes)
        var result = 0
        for (index in 0 until 4) result = (result shl 8) + (bytes[index].toInt() and 255)
        return result
    }
    open fun t(seed: Long) {
        synchronized(this) {
            updateDigestWithLong(seed)
            akf.b(stateBytes, 0, stateBytes.size)
            akf.f(stateBytes, 0)
        }
    }
    open fun h(bytes: ByteArray?) { g(bytes, 0, bytes!!.size) }
    open fun g(bytes: ByteArray?, offset: Int, length: Int) {
        synchronized(this) {
            generateBlock()
            val endOffset = offset + length
            var destinationOffset = offset
            var blockOffset = 0
            while (destinationOffset != endOffset) {
                if (blockOffset == outputBytes.size) { generateBlock(); blockOffset = 0 }
                bytes!![destinationOffset] = outputBytes[blockOffset]
                destinationOffset++
                blockOffset++
            }
        }
    }
    private fun reseedState() {
        akf.b(stateBytes, 0, stateBytes.size)
        val counter = reseedCounter
        reseedCounter = 1L + counter
        updateDigestWithLong(counter)
        akf.f(stateBytes, 0)
    }
    private fun generateBlock() {
        val counter = outputCounter
        outputCounter = 1L + counter
        updateDigestWithLong(counter)
        akf.b(outputBytes, 0, outputBytes.size)
        akf.b(stateBytes, 0, stateBytes.size)
        akf.f(outputBytes, 0)
        if (outputCounter % reseedInterval == 0L) reseedState()
    }
    private fun updateDigestWithLong(value: Long) {
        var remaining = value
        for (index in 0 until 8) {
            akf.c(remaining.toByte())
            remaining = remaining ushr 8
        }
    }
    companion object { private var reseedInterval = 10L }
}
