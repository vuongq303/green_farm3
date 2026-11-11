package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class cn implements com.gameloft.a.b.k {
    @Override // com.gameloft.a.b.k
    public void a(com.gameloft.a.b.l lVar, com.gameloft.a.b.m mVar) {
        String str = "JW_PrivacyPolicy aaaaaaaaaaaaaaa:: RequestPrivacyPolicy: " + mVar.bxA;
        String str2 = mVar.bxA;
        String str3 = "JW_PrivacyPolicy aaaaaaaaa::  ppData.age: " + mVar.bxy;
        if (str2.equals("{}")) {
            ci.bbi = false;
            ci.bbj = true;
            ci.bbk = false;
            ci.bbl = false;
            ci.bbm = false;
            ci.bbr = true;
            return;
        }
        ci.bbj = false;
        if (str2.indexOf("COPPA") >= 0) {
            ci.bbi = false;
            ci.bbk = true;
            ci.bbl = false;
            ci.bbm = false;
            return;
        }
        if (str2.indexOf("ITALY") >= 0) {
            ci.bbl = true;
        } else if (str2.indexOf("OFT") < 0 || (ci.baQ.toUpperCase().indexOf("AU") < 0 && ci.baQ.toUpperCase().indexOf("NZ") < 0)) {
            ci.bbi = true;
        } else {
            ci.bbm = true;
        }
        ci.bbk = false;
    }
}
