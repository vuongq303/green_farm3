package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
class cd extends cc {
    cd() {
    }

    @Override // android.support.v4.app.cc, android.support.v4.app.cb, android.support.v4.app.ca, android.support.v4.app.cg, android.support.v4.app.ci
    public Notification a(bv bvVar, bw bwVar) {
        cn cnVar = new cn(bvVar.mContext, bvVar.dB, bvVar.ag(), bvVar.af(), bvVar.cY, bvVar.cW, bvVar.cZ, bvVar.cU, bvVar.cV, bvVar.cX, bvVar.dg, bvVar.dh, bvVar.di, bvVar.db, bvVar.dc, bvVar.da, bvVar.de, bvVar.dn, bvVar.mCategory, bvVar.dC, bvVar.mExtras, bvVar.dq, bvVar.dr, bvVar.ds, bvVar.dj, bvVar.dk, bvVar.dl, bvVar.dt, bvVar.du, bvVar.dv, bvVar.dA);
        bq.a(cnVar, bvVar.dm);
        bq.a(cnVar, bvVar.dd);
        Notification notificationA = bwVar.a(bvVar, cnVar);
        if (bvVar.dd != null) {
            bvVar.dd.a(bq.a(notificationA));
        }
        return notificationA;
    }
}
