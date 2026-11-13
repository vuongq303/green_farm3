package com.quanhoang.greenfarm3.S800x480

open class ap(@JvmField val ady: ao?, @JvmField var adx: Array<Any?>?) : Thread() {
    private fun mJ() {
        ao.a(ady, false)
        ao.b(ady, true)
        GLLib.be("thread ended")
    }
    override fun run() {
        when (ao.a(ady)) {
            0 -> ao.`access$302`(ao.ms().D(adx!![1] as String?, adx!![2] as String?))
            1 -> ao.`access$302`(ao.a(ady, adx!![1] as Int))
        }
        mJ()
    }
}
