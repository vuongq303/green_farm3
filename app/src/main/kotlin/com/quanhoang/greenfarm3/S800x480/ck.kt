package com.quanhoang.greenfarm3.S800x480

import org.json.JSONException
import org.json.JSONObject

open class ck(@JvmField val bbN: FarmGameEngine?) : com.gameloft.a.a.h {
    override fun a(success: Boolean, response: JSONObject?) {
        val diagnostic = "CRM_Callback :: success = " + success + "data = " + response
        if (success) {
            try {
                val actionCode = response!!.getInt(com.gameloft.a.a.m.buv)
                if (com.gameloft.a.a.o.VW().VY()) {
                    when (actionCode) {
                        6, 9, 10, 19, 20, 21 -> return
                        22 -> {
                            val actionDiagnostic = "CRM_Callback :: 'CRM_ACTION_GLADS_INCENTIVIZED_VIDEO' handle: " + response.getString(com.gameloft.a.a.m.buo)
                            val action = response.getString(com.gameloft.a.a.m.buo)
                            if (b.Kb) {
                                b.Kb = false
                                b.hL()
                                b.ar(action)
                                return
                            }
                            return
                        }
                        else -> return
                    }
                }
            } catch (failure: JSONException) {}
        }
    }
}
