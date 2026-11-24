package com.quanhoang.greenfarm3.S800x480

class co : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (statusCode != 200) {
            FarmGameEngine.aWS = true
            val diagnostic = "[OnlineQuest]OnlineQuestRefresh Error " + Integer.toString(statusCode)
        } else {
            val diagnostic = "[OnlineQuest]OnlineQuestRefresh Success " + responseBody
            FarmGameEngine.aWS = false
            FarmGameEngine.dr(responseBody)
        }
    }
}
