package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class c {
    private var billingName = ""
    private var billingType = ""
    private var prices = ba()
    private var replacedPrices = ba()
    open fun a(data: bc?): Int {
        val rawType = data!!.cd("type")
        if (rawType != null) billingType = rawType as String else hM()
        val rawName = data.cd("name")
        if (rawName != null) billingName = rawName as String else hM()
        val rawPrices = data.cd("price")
        if (rawPrices != null) {
            if (rawPrices.javaClass.isArray) {
                val values = rawPrices as Array<*>
                val entries = arrayOfNulls<bc>(values.size)
                for (index in values.indices) entries[index] = values[index] as bc?
                prices.a(entries)
            }
        } else hM()
        if (prices.hP() < 1) return -2147483646
        val rawReplacedPrices = data.cd("replaced_price")
        if (rawReplacedPrices == null || !rawReplacedPrices.javaClass.isArray) return 0
        val values = rawReplacedPrices as Array<*>
        val entries = arrayOfNulls<bc>(values.size)
        for (index in values.indices) entries[index] = values[index] as bc?
        replacedPrices.a(entries)
        return 0
    }
    open fun hM() {
        billingName = String(charArrayOf())
        billingType = String(charArrayOf())
        prices = ba()
        replacedPrices = ba()
    }
    open fun hN(): ba = prices
    open fun hO(): ba = replacedPrices
}
