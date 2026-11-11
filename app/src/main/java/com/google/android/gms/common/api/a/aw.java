package com.google.android.gms.common.api.a;

import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
final class aw extends ba {
    private final ArrayList<com.google.android.gms.common.api.l> bKD;
    private final /* synthetic */ aq bKw;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public aw(aq aqVar, ArrayList<com.google.android.gms.common.api.l> arrayList) {
        super(aqVar, null);
        this.bKw = aqVar;
        this.bKD = arrayList;
    }

    @Override // com.google.android.gms.common.api.a.ba
    public final void zzaq() {
        this.bKw.bKe.bLd.bKP = this.bKw.Zz();
        ArrayList<com.google.android.gms.common.api.l> arrayList = this.bKD;
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            com.google.android.gms.common.api.l lVar = arrayList.get(i);
            i++;
            lVar.getRemoteService(this.bKw.bKr, this.bKw.bKe.bLd.bKP);
        }
    }
}
