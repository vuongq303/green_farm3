package com.gameloft.android.ANMP.GloftGF2F.S800x480;

/* JADX INFO: compiled from: GraphUser.java */
/* JADX INFO: loaded from: classes.dex */
public class ai extends com.gameloft.android2d.socialnetwork.i {
    int acy = -1;

    public ay ml() {
        if (this.acy == -2) {
            if (bu.akl == null || (getId() != null && bu.userId != null && bu.userId.compareTo(getId()) != 0)) {
                javax.microedition.lcdui.ao aoVarUN = super.UN();
                if (aoVarUN == null) {
                    return null;
                }
                bu.akl = new ay(aoVarUN);
                bu.userId = getId();
            }
            return bu.akl;
        }
        if (!bu.cE(getId())) {
            javax.microedition.lcdui.ao aoVarUN2 = super.UN();
            if (aoVarUN2 == null) {
                return null;
            }
            ay ayVar = new ay(aoVarUN2);
            if (ayVar != null) {
                bu.a(getId(), ayVar);
                return ayVar;
            }
            return ayVar;
        }
        return bu.oc();
    }

    public void eu(int i) {
        this.acy = i;
    }
}
