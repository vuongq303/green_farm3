package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: loaded from: classes.dex */
class cx implements ar {
    String bbW;
    int bbX;

    public cx(String str, int i) {
        this.bbW = str;
        this.bbX = i;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.S800x480.ar
    public void D(int i, String str) {
        String str2 = "[OnlineQuest]PostOnlineQuestScore " + i + " " + str;
        if (str == null) {
            return;
        }
        for (int i2 = 0; i2 < ci.aVW; i2++) {
            if (ci.aVX[i2].equals(this.bbW)) {
                if (str.equals("-2")) {
                    ci.lS(i2);
                    return;
                } else {
                    if (this.bbX == ci.aWD[i2]) {
                        if (i != 200) {
                            ci.aWE[i2] = ci.aWF[i2] + 60000;
                            return;
                        } else {
                            ci.aWA[i2] = false;
                            return;
                        }
                    }
                    return;
                }
            }
        }
    }
}
