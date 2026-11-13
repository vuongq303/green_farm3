package com.quanhoang.greenfarm3.S800x480

class co : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (statusCode != 200) {
            ci.aWS = true
            val diagnostic = "[OnlineQuest]OnlineQuestRefresh Error " + Integer.toString(statusCode)
        } else {
            val diagnostic = "[OnlineQuest]OnlineQuestRefresh Success " + responseBody
            ci.aWS = false
            ci.dr(responseBody)
        }
    }
}
