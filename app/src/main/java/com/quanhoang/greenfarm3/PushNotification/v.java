package com.quanhoang.greenfarm3.PushNotification;

import android.os.Bundle;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
class v extends a {
    final /* synthetic */ q Gi;

    v(q qVar) {
        this.Gi = qVar;
    }

    @Override // com.quanhoang.greenfarm3.PushNotification.a
    protected Integer e(Bundle bundle) {
        int i;
        String string = bundle.getString("juid");
        String string2 = bundle.getString("jlbl");
        String string3 = bundle.getString("jbdy");
        String string4 = bundle.getString("jdly");
        String string5 = bundle.getString("jtkn");
        String string6 = bundle.getString("jxtr");
        String string7 = bundle.getString("jrqst");
        String strA = q.a(string, string2, string3, string4, string5, string6);
        if (strA.compareTo("e") == 0) {
            if (q.bc(2) != 2020) {
                i = -2;
            } else {
                i = -1;
            }
        } else {
            q.r(2, 0);
            i = 0;
        }
        bundle.putString("pn_push_id", strA);
        bundle.putString("pn_request_id", string7);
        this.Gi.a(2, i, bundle);
        return new Integer(0);
    }
}
