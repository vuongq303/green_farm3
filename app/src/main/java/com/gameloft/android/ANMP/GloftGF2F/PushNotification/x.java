package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.os.Bundle;
import com.gameloft.android.wrapper.au;

/* JADX INFO: compiled from: PushNotification.java */
/* JADX INFO: loaded from: classes.dex */
class x extends a {
    final /* synthetic */ q Gi;

    x(q qVar) {
        this.Gi = qVar;
    }

    /* JADX WARN: Code duplicated, block: B:12:0x0066  */
    /* JADX WARN: Code duplicated, block: B:19:0x0089  */
    /* JADX WARN: Code duplicated, block: B:21:0x0091  */
    /* JADX WARN: Code duplicated, block: B:22:0x0098  */
    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.a
    protected Integer e(Bundle bundle) {
        boolean z;
        String string = bundle.getString("juid");
        String string2 = bundle.getString("jusrpss");
        String string3 = bundle.getString("jusrcred");
        q.FW = false;
        String strA = q.a(string, string2, string3, 1);
        au.an(q.FU.get());
        String deviceId = au.getDeviceId();
        if (!q.ai(string)) {
            if (!q.ai(strA)) {
                if (!q.ai(deviceId)) {
                    if (q.a(strA, deviceId, 1)) {
                        q.an(string3 + ":" + string);
                        q.FH[0] = true;
                        z = true;
                    }
                } else {
                    q.r(0, 1003);
                    z = false;
                }
            } else {
                q.r(0, 1002);
                z = false;
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
        q.r(0, 1001);
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
