package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class bn {
    private var description: String? = null
    private var endDate: String? = null
    open fun a(data: bc?): Int {
        val rawPromotion = data!!.cd("promotion") ?: return -2147479542
        val promotion = rawPromotion as bc
        val rawEndDate = promotion.cd("end_date") ?: return -2147483646
        endDate = rawEndDate as String
        val rawDescription = promotion.cd("description") ?: return -2147483646
        description = rawDescription as String
        return 0
    }
    open fun hM() { endDate = ""; description = "" }
    open fun nE(): String? = endDate
    open fun hX(): String? = description
}
