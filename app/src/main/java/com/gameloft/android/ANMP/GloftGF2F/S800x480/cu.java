package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class cu implements ar {
    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.ar
    public void D(int i, String str) {
        if (i != 200) {
            String str2 = "[OnlineQuest]OnlineQuestGetAnoNameCallback Error " + Integer.toString(i);
            ci.aWZ = "";
            return;
        }
        String str3 = "[OnlineQuest]OnlineQuestGetAnoNameCallback Success " + str;
        ci.aWZ = str;
        if (ci.aWZ == null) {
            ci.aWZ = "";
        }
    }
}
