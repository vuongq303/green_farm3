package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
class cc extends cb {
    cc() {
    }

    @Override // android.support.v4.app.cb, android.support.v4.app.ca, android.support.v4.app.cg, android.support.v4.app.ci
    public Notification a(bv bvVar, bw bwVar) {
        cl clVar = new cl(bvVar.mContext, bvVar.dB, bvVar.ag(), bvVar.af(), bvVar.cY, bvVar.cW, bvVar.cZ, bvVar.cU, bvVar.cV, bvVar.cX, bvVar.dg, bvVar.dh, bvVar.di, bvVar.db, bvVar.dc, bvVar.da, bvVar.de, bvVar.dn, bvVar.dC, bvVar.mExtras, bvVar.dj, bvVar.dk, bvVar.dl, bvVar.dt, bvVar.du, bvVar.dA);
        bq.a(clVar, bvVar.dm);
        bq.a(clVar, bvVar.dd);
        Notification notificationA = bwVar.a(bvVar, clVar);
        if (bvVar.dd != null) {
            bvVar.dd.a(bq.a(notificationA));
        }
        return notificationA;
    }
}
