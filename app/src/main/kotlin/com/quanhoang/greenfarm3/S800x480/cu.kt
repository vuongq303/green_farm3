package com.quanhoang.greenfarm3.S800x480

open class cu : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (statusCode != 200) {
            val diagnostic = "[OnlineQuest]OnlineQuestGetAnoNameCallback Error " + Integer.toString(statusCode)
            ci.aWZ = ""
            return
        }
        val diagnostic = "[OnlineQuest]OnlineQuestGetAnoNameCallback Success " + responseBody
        ci.aWZ = responseBody
        if (ci.aWZ == null) ci.aWZ = ""
    }
}
