package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class cv implements ar {
    int bbV;

    public cv(int i) {
        this.bbV = i;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.ar
    public void D(int i, String str) {
        if (i == 200) {
            String str2 = "[OnlineQuest]OnlineQuestGetLeaderboard Success " + str;
            if (ci.apC[ci.apB] == 52 && this.bbV == ci.aPZ) {
                ci.dd(str);
            }
        } else {
            String str3 = "[OnlineQuest]OnlineQuestGetLeaderboard Error " + Integer.toString(i);
        }
        ci.aPU = true;
    }
}
