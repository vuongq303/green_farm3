package com.gameloft.android.ANMP.GloftGF2F.S800x480

open class cv(@JvmField var bbV: Int) : ar {
    override fun D(statusCode: Int, responseBody: String?) {
        if (statusCode == 200) {
            val diagnostic = "[OnlineQuest]OnlineQuestGetLeaderboard Success " + responseBody
            if (ci.apC[ci.apB] == 52 && bbV == ci.aPZ) ci.dd(responseBody)
        } else {
            val diagnostic = "[OnlineQuest]OnlineQuestGetLeaderboard Error " + Integer.toString(statusCode)
        }
        ci.aPU = true
    }
}
