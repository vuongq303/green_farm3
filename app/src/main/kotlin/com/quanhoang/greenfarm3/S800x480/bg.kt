package com.quanhoang.greenfarm3.S800x480

open class bg {
    private var initialized = false
    private var items = bw()
    private var promotion = bn()
    open fun aw(buffer: String?): Int {
        if (initialized) {
            GLLib.be("Offline Store is already initialized.")
            return -2147483645
        }
        items = bw()
        promotion = bn()
        val result = e(bc(buffer).cd("offline_store") as bc?)
        if (result != 0) {
            GLLib.be("Offline Store failed to parse the buffer.")
            return result
        }
        initialized = true
        return result
    }
    open fun cs(buffer: String?): Int {
        if (e(bc(buffer).cd("offline_store") as bc?) == 0) return 0
        GLLib.be("Offline Store failed to parse the buffer.")
        return -2147483622
    }
    open fun nw(): bw = items
    open fun e(data: bc?): Int {
        if (data == null) return -2147483622
        val result = items.a(data)
        if (result != 0) {
            GLLib.be("Offline Store failed to parse the items buffer.")
            return result
        }
        promotion.hM()
        promotion.a(data)
        // Preserve the old parser's result check, without an unrelated logic fix.
        if (result != 0) {
            GLLib.be("Offline Store failed to parse the promotion buffer.")
            return result
        }
        return result
    }
    open fun nx(): String? = promotion.hX()
    open fun ny(): String? = promotion.nE()
    companion object {
        private var instance: bg? = null
        @JvmStatic fun nv(): bg {
            if (instance == null) instance = bg()
            return instance!!
        }
    }
}
