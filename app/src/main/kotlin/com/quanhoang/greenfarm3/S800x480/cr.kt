package com.quanhoang.greenfarm3.S800x480

open class cr(@JvmField val bbN: FarmGameEngine?) : com.gameloft.a.b.k {
    override fun a(request: com.gameloft.a.b.l?, response: com.gameloft.a.b.m?) {
        val diagnostic = "JW_PrivacyPolicy :: RequestPrivacyPolicy: " + response!!.bxA
        val policyData = response.bxA
        val ageDiagnostic = "JW_PrivacyPolicy :: ppData.age: " + response.bxy
        if (policyData!!.equals("{}")) {
            FarmGameEngine.bbi = false
            FarmGameEngine.bbj = true
            FarmGameEngine.bbk = false
            FarmGameEngine.bbl = false
            FarmGameEngine.bbm = false
            FarmGameEngine.bbr = true
            return
        }
        FarmGameEngine.bbj = false
        if (policyData.indexOf("COPPA") >= 0) {
            FarmGameEngine.bbk = true
            FarmGameEngine.bbi = false
            FarmGameEngine.bbl = false
            FarmGameEngine.bbm = false
            return
        }
        if (policyData.indexOf("ITALY") >= 0) FarmGameEngine.bbl = true
        else if (policyData.indexOf("OFT") < 0 || (FarmGameEngine.baQ.uppercase(java.util.Locale.getDefault()).indexOf("AU") < 0 && FarmGameEngine.baQ.uppercase(java.util.Locale.getDefault()).indexOf("NZ") < 0)) FarmGameEngine.bbi = true
        else FarmGameEngine.bbm = true
        FarmGameEngine.bbk = false
    }
}
