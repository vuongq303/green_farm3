package com.quanhoang.greenfarm3.S800x480

open class cw(@JvmField var bbW: String?) : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (responseBody == null) return
        if (statusCode == 200) {
            val diagnostic = "[OnlineQuest]OnlineQuestGetRewardCallback Success " + responseBody
            if (responseBody == "-3") FarmGameEngine.dn(bbW) else FarmGameEngine.M(bbW, responseBody)
            return
        }
        if (statusCode == 404) FarmGameEngine.dn(bbW)
        else if (statusCode == 409) FarmGameEngine.dn(bbW)
        else {
            val diagnostic = "[OnlineQuest]OnlineQuestGetRewardCallback Error " + Integer.toString(statusCode)
        }
    }
}
