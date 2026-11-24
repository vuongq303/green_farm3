package com.quanhoang.greenfarm3.S800x480

/** Original serialized online-operation worker; session monitor ownership and polling are preserved. */
open class cy(@JvmField var adx: Array<Any?>?, @JvmField var bbY: Int) : Thread() {
    @JvmField var Bj = 0
    private fun executeRequest(): Boolean {
        val request = adx!!
        fun string(index: Int): String? = request[index] as String?
        fun bool(index: Int): Boolean = (request[index] as Boolean?)!!
        fun number(index: Int): Int = (request[index] as Int?)!!
        return when (number(0)) {
            0 -> FarmGameEngine.j(string(1), bool(2))
            1 -> FarmGameEngine.a(string(1), bool(2), number(3), string(4), string(5), string(6))
            2 -> FarmGameEngine.a(string(1), string(2), string(3), bool(4), string(5), bool(6))
            3 -> FarmGameEngine.a(string(1), string(2), string(3), string(4), bool(5))
            4 -> FarmGameEngine.mg(t.Nv)
            5 -> FarmGameEngine.a(string(1), bool(2), string(3), number(4), string(5), string(6), string(7), string(8), string(9), request[10] as Array<String?>?)
            6 -> FarmGameEngine.bx(bool(1))
            7 -> FarmGameEngine.LY()
            8 -> FarmGameEngine.by(bool(1))
            9 -> FarmGameEngine.N(string(1), string(2))
            10 -> FarmGameEngine.q(string(1), string(2), string(3))
            11 -> FarmGameEngine.r(string(1), string(2), string(3))
            12 -> FarmGameEngine.dv(string(1))
            13 -> FarmGameEngine.dw(string(1))
            14 -> FarmGameEngine.s(string(1), string(2), string(3))
            15 -> FarmGameEngine.f(string(1), string(2), string(3), string(4), string(5))
            16 -> FarmGameEngine.t(string(1), string(2), string(3))
            17 -> FarmGameEngine.h(string(1), string(2), string(3), string(4))
            18 -> FarmGameEngine.g(string(1), string(2), string(3), string(4), string(5), string(6))
            19 -> FarmGameEngine.mg(t.ND)
            else -> false
        }
    }
    private fun pollSession() {
        synchronized(FarmGameEngine.MM()) {
            if (bbY != FarmGameEngine.MN()) { Bj = -1; return }
            if (Bj == 0 && !FarmGameEngine.MO()) {
                Bj = 1
                if (!executeRequest()) finishRequest()
            } else if (Bj == 1 && FarmGameEngine.MP() != 1) finishRequest()
            if (Bj >= 0) {
                // Original polling sleeps while holding this same monitor; do not silently reorder it.
                try { sleep(250L) } catch (_: Exception) { }
            }
        }
    }
    private fun finishRequest() {
        Bj = -1
        synchronized(FarmGameEngine.MM()) { if (bbY == FarmGameEngine.MN()) FarmGameEngine.bD(false) }
    }
    override fun run() { while (Bj >= 0) pollSession() }
}
