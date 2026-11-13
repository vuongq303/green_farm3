package com.quanhoang.greenfarm3.S800x480

class cn : com.gameloft.a.b.k {
    override fun a(request: com.gameloft.a.b.l?, response: com.gameloft.a.b.m?) {
        val diagnostic = "JW_PrivacyPolicy :: RequestPrivacyPolicy: " + response!!.bxA
        val policyData = response.bxA
        val ageDiagnostic = "JW_PrivacyPolicy :: ppData.age: " + response.bxy
        if (policyData!!.equals("{}")) {
            ci.bbi = false
            ci.bbj = true
            ci.bbk = false
            ci.bbl = false
            ci.bbm = false
            ci.bbr = true
            return
        }
        ci.bbj = false
        if (policyData.indexOf("COPPA") >= 0) {
            ci.bbi = false
            ci.bbk = true
            ci.bbl = false
            ci.bbm = false
            return
        }
        if (policyData.indexOf("ITALY") >= 0) ci.bbl = true
        else if (policyData.indexOf("OFT") < 0 || (ci.baQ.uppercase(java.util.Locale.getDefault()).indexOf("AU") < 0 && ci.baQ.uppercase(java.util.Locale.getDefault()).indexOf("NZ") < 0)) ci.bbi = true
        else ci.bbm = true
        ci.bbk = false
    }
}
