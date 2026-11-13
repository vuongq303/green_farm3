package com.quanhoang.greenfarm3.S800x480

open class cs(@JvmField val bbN: FarmGameEngine?) : Thread() {
    constructor(owner: FarmGameEngine?, ignored: cj?) : this(owner)
    private fun mJ() { FarmGameEngine.a(bbN, false) }
    override fun run() {
        FarmGameEngine.a(bbN, true)
        FarmGameEngine.Mn()
        mJ()
    }
}
