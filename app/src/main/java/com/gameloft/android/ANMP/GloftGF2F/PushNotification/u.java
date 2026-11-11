package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.os.Bundle;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
class u extends a {
    final /* synthetic */ q Gi;

    u(q qVar) {
        this.Gi = qVar;
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.a
    protected Integer e(Bundle bundle) {
        String string = bundle.getString("jphid");
        int iAm = q.am(string);
        bundle.putString("pn_push_id", string);
        bundle.putString("pn_result", iAm + "");
        if (iAm == 1) {
            this.Gi.a(3, 0, bundle);
        } else {
            this.Gi.a(3, -1, bundle);
        }
        return new Integer(0);
    }
}
