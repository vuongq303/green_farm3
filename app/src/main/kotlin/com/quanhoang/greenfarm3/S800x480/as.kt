package com.quanhoang.greenfarm3.S800x480

/** ABI for retired online event clients. No queues, workers or network IO. */
@Suppress("UNUSED_PARAMETER")
open class `as` {
    companion object {
        @JvmStatic fun Init() {}
        @JvmStatic fun a(url: String?, payload: String?, requestMethod: Int, callback: ar?, timeoutMillis: Int) {}
        @JvmStatic fun a(url: String?, payload: String?, callback: ar?, timeoutMillis: Int) {}
        @JvmStatic fun dV(elapsedMillis: Int) {}
    }
}
