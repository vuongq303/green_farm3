package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.os.Bundle;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
class w extends a {
    final /* synthetic */ q Gi;

    w(q qVar) {
        this.Gi = qVar;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.a
    protected Integer e(Bundle bundle) {
        String string = k.Q(q.FU.get()).getString("RandomUserID", null);
        if (q.ai(string)) {
            string = q.hl();
        }
        String strA = q.a(string, string, "anonymous", 0);
        boolean z = !q.ai(strA) && q.j(string, strA) == 0;
        if (z) {
            q.r(0, 0);
            this.Gi.s(0, 0);
        } else if (q.bc(0) != 2020) {
            this.Gi.a(0, -2, bundle);
        } else {
            this.Gi.a(0, -1, bundle);
        }
        return new Integer(0);
    }
}
