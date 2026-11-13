package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class bi {
    private var invalidValueDetected = false
    private var encodedValue = ce()
    private var randomMask = ce()
    @JvmField var ajz = 0L
    constructor() { setValue(0) }
    constructor(value: Int) { setValue(value) }
    open fun getValue(): Int {
        val decodedValue = (encodedValue.get() xor randomMask.get()) xor -1
        if (al.q(decodedValue.toLong()) == ajz) return decodedValue
        setValue(0)
        invalidValueDetected = true
        return 0
    }
    open fun setValue(value: Int) {
        randomMask.set(bo.next())
        encodedValue.set((value xor -1) xor randomMask.get())
        ajz = al.q(value.toLong())
    }
}
