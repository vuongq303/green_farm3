package com.quanhoang.greenfarm3.S800x480

open class cs(@JvmField val bbN: ci?) : Thread() {
    constructor(owner: ci?, ignored: cj?) : this(owner)
    private fun mJ() { ci.a(bbN, false) }
    override fun run() {
        ci.a(bbN, true)
        ci.Mn()
        mJ()
    }
}
