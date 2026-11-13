package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class aq {
    @JvmField var Bj = 0
    @JvmField var acE = 0
    @JvmField var adA: String? = ""
    @JvmField var adB = 0
    @JvmField var adC: ar? = null
    @JvmField var adD: am? = null
    @JvmField var adz: String? = ""
    constructor() { adB = 20000 }
    constructor(url: String?, payload: String?, requestMethod: Int, callback: ar?, timeoutMillis: Int) {
        adz = url
        adA = payload
        acE = requestMethod
        adC = callback
        adB = if (timeoutMillis > 0) timeoutMillis else 20000
        Bj = 1
    }
    open fun a(request: aq?, worker: am?) {
        val source = request!!
        adz = source.adz
        adA = source.adA
        acE = source.acE
        adC = source.adC
        adB = source.adB
        Bj = source.Bj
        adD = worker
    }
    open fun dV(elapsedMillis: Int) {
        when (Bj) {
            1, 2 -> {
                adD!!.c(adz, adA, acE)
                ez(3)
            }
            3 -> if (!adD!!.isInProgress()) {
                mL()
                ez(7)
            }
        }
    }
    open fun ez(requestState: Int) { Bj = requestState }
    @Synchronized open fun mK(): Boolean = adD == null || Bj == 0 || Bj == 4 || Bj == 5 || Bj == 6 || Bj == 7
    open fun mL() {
        if (adC == null || adD == null) return
        adC!!.D(adD!!.Bg, adD!!.BM)
    }
}
