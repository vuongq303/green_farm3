package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.Vector

open class bw {
    private var items = Vector<bv>()
    open fun a(data: bc?): Int {
        items.removeAllElements()
        if (data!!.cd("prices") == null) return -2147483646
        var prices: Array<bc?>? = null
        try { prices = data.cj("prices") } catch (failure: Exception) {}
        for (price in prices!!) {
            val item = bv()
            item.a(price)
            items.addElement(item)
        }
        return 0
    }
    open fun oi(): Int = items.size
    open fun eO(index: Int): bv = items.elementAt(index)
}
