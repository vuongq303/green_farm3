package com.quanhoang.greenfarm3.S800x480

open class cw(@JvmField var bbW: String?) : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (responseBody == null) return
        if (statusCode == 200) {
            val diagnostic = "[OnlineQuest]OnlineQuestGetRewardCallback Success " + responseBody
            if (responseBody == "-3") ci.dn(bbW) else ci.M(bbW, responseBody)
            return
        }
        if (statusCode == 404) ci.dn(bbW)
        else if (statusCode == 409) ci.dn(bbW)
        else {
            val diagnostic = "[OnlineQuest]OnlineQuestGetRewardCallback Error " + Integer.toString(statusCode)
        }
    }
}
