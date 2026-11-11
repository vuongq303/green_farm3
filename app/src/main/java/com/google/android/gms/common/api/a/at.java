package com.google.android.gms.common.api.a;

import com.google.android.gms.common.ConnectionResult;
import java.util.ArrayList;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class at extends ba {
    final /* synthetic */ aq bKw;
    private final Map<com.google.android.gms.common.api.l, as> bKz;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public at(aq aqVar, Map<com.google.android.gms.common.api.l, as> map) {
        super(aqVar, null);
        this.bKw = aqVar;
        this.bKz = map;
    }

    @Override // com.google.android.gms.common.api.a.ba
    public final void zzaq() {
        int i = 0;
        com.google.android.gms.common.internal.ag agVar = new com.google.android.gms.common.internal.ag(this.bKw.zzgk);
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        for (com.google.android.gms.common.api.l lVar : this.bKz.keySet()) {
            if (!lVar.requiresGooglePlayServices() || this.bKz.get(lVar).bKy) {
                arrayList2.add(lVar);
            } else {
                arrayList.add(lVar);
            }
        }
        int iA = -1;
        if (!arrayList.isEmpty()) {
            ArrayList arrayList3 = arrayList;
            int size = arrayList3.size();
            int i2 = 0;
            while (i2 < size) {
                Object obj = arrayList3.get(i2);
                i2++;
                iA = agVar.a(this.bKw.mContext, (com.google.android.gms.common.api.l) obj);
                if (iA != 0) {
                    break;
                }
            }
        } else {
            ArrayList arrayList4 = arrayList2;
            int size2 = arrayList4.size();
            while (i < size2) {
                Object obj2 = arrayList4.get(i);
                i++;
                iA = agVar.a(this.bKw.mContext, (com.google.android.gms.common.api.l) obj2);
                if (iA == 0) {
                    break;
                }
            }
        }
        int i3 = iA;
        if (i3 != 0) {
            this.bKw.bKe.a(new au(this, this.bKw, new ConnectionResult(i3, null)));
            return;
        }
        if (this.bKw.bKp) {
            this.bKw.bKo.connect();
        }
        for (com.google.android.gms.common.api.l lVar2 : this.bKz.keySet()) {
            as asVar = this.bKz.get(lVar2);
            if (!lVar2.requiresGooglePlayServices() || agVar.a(this.bKw.mContext, lVar2) == 0) {
                lVar2.connect(asVar);
            } else {
                this.bKw.bKe.a(new av(this, this.bKw, asVar));
            }
        }
    }
}
