package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.os.Bundle;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
class y extends a {
    final /* synthetic */ q Gi;

    y(q qVar) {
        this.Gi = qVar;
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0072  */
    /* JADX WARN: Code duplicated, block: B:21:0x0091  */
    /* JADX WARN: Code duplicated, block: B:23:0x0099  */
    /* JADX WARN: Code duplicated, block: B:24:0x00a0  */
    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.a
    protected Integer e(Bundle bundle) {
        boolean z;
        String string = bundle.getString("juid");
        String string2 = bundle.getString("jusrpss");
        bundle.getString("jusrcred");
        String string3 = k.Q(q.FU.get()).getString("RandomUserID", null);
        q.FW = true;
        if (!q.ai(string3)) {
            String strA = q.a(string3, string3, "anonymous", 1);
            if (q.bc(5) == 0) {
                if (!q.ai(strA)) {
                    String str = q.FZ + ":" + string;
                    z = q.b(str, string2, strA) == 0;
                    if (z) {
                        q.an(str);
                        q.FH[1] = true;
                    }
                }
                if (z) {
                    q.r(1, 0);
                    this.Gi.s(1, 0);
                } else if (q.bc(1) != 2020) {
                    this.Gi.a(1, -2, bundle);
                } else {
                    this.Gi.a(1, -1, bundle);
                }
                return new Integer(0);
            }
            q.r(1, q.bc(5));
        } else {
            q.r(1, 4001);
        }
        z = false;
        if (z) {
            q.r(1, 0);
            this.Gi.s(1, 0);
        } else if (q.bc(1) != 2020) {
            this.Gi.a(1, -2, bundle);
        } else {
            this.Gi.a(1, -1, bundle);
        }
        return new Integer(0);
    }
}
