package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class bk {
    private var invalidValueDetected = false
    private var encodedValue = cf()
    private var randomMask = cf()
    @JvmField var ajz = 0L
    constructor() { r(0L) }
    constructor(value: Long) { r(value) }
    open fun getValue(): Long {
        val decodedValue = (encodedValue.get() xor randomMask.get()) xor -1L
        if (al.q(decodedValue) == ajz) return decodedValue
        r(0L)
        invalidValueDetected = true
        return 0L
    }
    open fun r(value: Long) {
        randomMask.set(bo.next().toLong())
        encodedValue.set((-1L xor value) xor randomMask.get())
        ajz = al.q(value)
    }
}
