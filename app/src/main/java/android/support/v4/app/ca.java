package android.support.v4.app;

import android.app.Notification;
import android.os.Bundle;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ca extends cg {
    ca() {
    }

    @Override // android.support.v4.app.cg, android.support.v4.app.ci
    public Notification a(bv bvVar, bw bwVar) {
        Bundle bundleA;
        cw cwVar = new cw(bvVar.mContext, bvVar.dB, bvVar.ag(), bvVar.af(), bvVar.cY, bvVar.cW, bvVar.cZ, bvVar.cU, bvVar.cV, bvVar.cX, bvVar.dg, bvVar.dh, bvVar.di, bvVar.dc, bvVar.da, bvVar.de, bvVar.dn, bvVar.mExtras, bvVar.dj, bvVar.dk, bvVar.dl, bvVar.dt, bvVar.du);
        bq.a(cwVar, bvVar.dm);
        bq.a(cwVar, bvVar.dd);
        Notification notificationA = bwVar.a(bvVar, cwVar);
        if (bvVar.dd != null && (bundleA = bq.a(notificationA)) != null) {
            bvVar.dd.a(bundleA);
        }
        return notificationA;
    }
}
