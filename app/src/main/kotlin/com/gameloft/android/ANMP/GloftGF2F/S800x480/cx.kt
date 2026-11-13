package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class cx(@JvmField var bbW: String?, @JvmField var bbX: Int) : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        val diagnostic = "[OnlineQuest]PostOnlineQuestScore " + statusCode + " " + responseBody
        if (responseBody == null) return
        for (questIndex in 0 until ci.aVW) {
            if (ci.aVX[questIndex]!!.equals(bbW)) {
                if (responseBody == "-2") {
                    ci.lS(questIndex)
                    return
                }
                if (bbX == ci.aWD[questIndex]) {
                    if (statusCode != 200) ci.aWE[questIndex] = ci.aWF[questIndex] + 60000
                    else ci.aWA[questIndex] = false
                }
                return
            }
        }
    }
}
