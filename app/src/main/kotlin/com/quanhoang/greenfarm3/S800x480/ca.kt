package com.quanhoang.greenfarm3.S800x480

open class ca(@JvmField val akV: bz?, @JvmField var adx: Array<Any?>?) : Thread() {
    private fun mJ() {
        bz.`access$002`(false)
        bz.aq(true)
        GLLib.be("oss: thread ended")
    }
    override fun run() {
        if (w.ZU) {
            when (bz.os()) {
                0 -> bz.`access$302`(bz.b(akV, adx!![1] as String?))
                1 -> bz.`access$302`(bz.a(akV))
                2 -> bz.`access$302`(bz.b(akV))
                3 -> bz.`access$302`(bz.c(akV))
                4 -> bz.`access$302`(bz.a(akV, adx!![1] as bc?, adx!![2] as String?))
                5 -> bz.`access$302`(bz.b(akV, adx!![1] as bc?, adx!![2] as String?))
                6 -> bz.`access$302`(bz.c(akV, adx!![1] as bc?, adx!![2] as String?))
            }
            mJ()
        }
    }
}
