package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
final class co implements ar {
    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.ar
    public void D(int i, String str) {
        if (i != 200) {
            ci.aWS = true;
            String str2 = "[OnlineQuest]OnlineQuestRefresh Error " + Integer.toString(i);
        } else {
            String str3 = "[OnlineQuest]OnlineQuestRefresh Success " + str;
            ci.aWS = false;
            ci.dr(str);
        }
    }
}
