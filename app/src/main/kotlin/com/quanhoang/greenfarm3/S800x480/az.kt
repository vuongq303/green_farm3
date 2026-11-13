package com.quanhoang.greenfarm3.S800x480

open class az {
    private var currencyCode = ""
    private var price = 0.0
    open fun a(data: bc?): Int {
        val currency = data!!.cd("currency") ?: return -2147483646
        currencyCode = currency as String
        val rawPrice = data.cd("price") ?: return -2147483646
        return try {
            price = java.lang.Double.parseDouble(rawPrice as String)
            if (price < 0.0) -2147483646 else 0
        } catch (failure: NumberFormatException) { -2147483646 }
    }
    open fun nm(): Double = price
    open fun nn(): String = currencyCode
}
