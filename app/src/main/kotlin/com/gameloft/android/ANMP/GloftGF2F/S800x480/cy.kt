package com.gameloft.android.ANMP.GloftGF2F.S800x480

/** Original serialized online-operation worker; session monitor ownership and polling are preserved. */
open class cy(@JvmField var adx: Array<Any?>?, @JvmField var bbY: Int) : Thread() {
    @JvmField var Bj = 0
    private fun executeRequest(): Boolean {
        val request = adx!!
        fun string(index: Int): String? = request[index] as String?
        fun bool(index: Int): Boolean = (request[index] as Boolean?)!!
        fun number(index: Int): Int = (request[index] as Int?)!!
        return when (number(0)) {
            0 -> ci.j(string(1), bool(2))
            1 -> ci.a(string(1), bool(2), number(3), string(4), string(5), string(6))
            2 -> ci.a(string(1), string(2), string(3), bool(4), string(5), bool(6))
            3 -> ci.a(string(1), string(2), string(3), string(4), bool(5))
            4 -> ci.mg(t.Nv)
            5 -> ci.a(string(1), bool(2), string(3), number(4), string(5), string(6), string(7), string(8), string(9), request[10] as Array<String?>?)
            6 -> ci.bx(bool(1))
            7 -> ci.LY()
            8 -> ci.by(bool(1))
            9 -> ci.N(string(1), string(2))
            10 -> ci.q(string(1), string(2), string(3))
            11 -> ci.r(string(1), string(2), string(3))
            12 -> ci.dv(string(1))
            13 -> ci.dw(string(1))
            14 -> ci.s(string(1), string(2), string(3))
            15 -> ci.f(string(1), string(2), string(3), string(4), string(5))
            16 -> ci.t(string(1), string(2), string(3))
            17 -> ci.h(string(1), string(2), string(3), string(4))
            18 -> ci.g(string(1), string(2), string(3), string(4), string(5), string(6))
            19 -> ci.mg(t.ND)
            else -> false
        }
    }
    private fun pollSession() {
        synchronized(ci.MM()) {
            if (bbY != ci.MN()) { Bj = -1; return }
            if (Bj == 0 && !ci.MO()) {
                Bj = 1
                if (!executeRequest()) finishRequest()
            } else if (Bj == 1 && ci.MP() != 1) finishRequest()
            if (Bj >= 0) {
                // Original polling sleeps while holding this same monitor; do not silently reorder it.
                try { sleep(250L) } catch (_: Exception) { }
            }
        }
    }
    private fun finishRequest() {
        Bj = -1
        synchronized(ci.MM()) { if (bbY == ci.MN()) ci.bD(false) }
    }
    override fun run() { while (Bj >= 0) pollSession() }
}
