package com.quanhoang.greenfarm3.S800x480

open class cv(@JvmField var bbV: Int) : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (statusCode == 200) {
            val diagnostic = "[OnlineQuest]OnlineQuestGetLeaderboard Success " + responseBody
            if (FarmGameEngine.apC[FarmGameEngine.apB] == 52 && bbV == FarmGameEngine.aPZ) FarmGameEngine.dd(responseBody)
        } else {
            val diagnostic = "[OnlineQuest]OnlineQuestGetLeaderboard Error " + Integer.toString(statusCode)
        }
        FarmGameEngine.aPU = true
    }
}
