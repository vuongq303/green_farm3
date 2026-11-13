package com.quanhoang.greenfarm3.S800x480

open class cx(@JvmField var bbW: String?, @JvmField var bbX: Int) : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        val diagnostic = "[OnlineQuest]PostOnlineQuestScore " + statusCode + " " + responseBody
        if (responseBody == null) return
        for (questIndex in 0 until FarmGameEngine.aVW) {
            if (FarmGameEngine.aVX[questIndex]!!.equals(bbW)) {
                if (responseBody == "-2") {
                    FarmGameEngine.lS(questIndex)
                    return
                }
                if (bbX == FarmGameEngine.aWD[questIndex]) {
                    if (statusCode != 200) FarmGameEngine.aWE[questIndex] = FarmGameEngine.aWF[questIndex] + 60000
                    else FarmGameEngine.aWA[questIndex] = false
                }
                return
            }
        }
    }
}
