package com.gameloft.android.ANMP.GloftGF2F.S800x480

import java.util.Vector

open class ba {
    private var prices = Vector<az>()
    open fun a(data: Array<bc?>?): Int {
        prices.removeAllElements()
        for (entry in data!!) {
            val itemPrice = az()
            itemPrice.a(entry)
            prices.addElement(itemPrice)
        }
        return 0
    }
    open fun hP(): Int = prices.size
    open fun eG(index: Int): String? = if (index < prices.size) prices.elementAt(index).nn() else null
    open fun eH(index: Int): Double = if (index < prices.size) prices.elementAt(index).nm() else 0.0
}
