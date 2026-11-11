package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class cw implements ar {
    String bbW;

    public cw(String str) {
        this.bbW = str;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.ar
    public void D(int i, String str) {
        if (str == null) {
            return;
        }
        if (i == 200) {
            String str2 = "[OnlineQuest]OnlineQuestGetRewardCallback Success " + str;
            if (str.equals("-3")) {
                ci.dn(this.bbW);
                return;
            } else {
                ci.M(this.bbW, str);
                return;
            }
        }
        if (i == 404) {
            ci.dn(this.bbW);
        } else if (i == 409) {
            ci.dn(this.bbW);
        } else {
            String str3 = "[OnlineQuest]OnlineQuestGetRewardCallback Error " + Integer.toString(i);
        }
    }
}
