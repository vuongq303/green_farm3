package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
class ce extends cd {
    ce() {
    }

    @Override // android.support.v4.app.cd, android.support.v4.app.cc, android.support.v4.app.cb, android.support.v4.app.ca, android.support.v4.app.cg, android.support.v4.app.ci
    public Notification a(bv bvVar, bw bwVar) {
        cp cpVar = new cp(bvVar.mContext, bvVar.dB, bvVar.cS, bvVar.cT, bvVar.cY, bvVar.cW, bvVar.cZ, bvVar.cU, bvVar.cV, bvVar.cX, bvVar.dg, bvVar.dh, bvVar.di, bvVar.db, bvVar.dc, bvVar.da, bvVar.de, bvVar.dn, bvVar.mCategory, bvVar.dC, bvVar.mExtras, bvVar.dq, bvVar.dr, bvVar.ds, bvVar.dj, bvVar.dk, bvVar.dl, bvVar.df, bvVar.dt, bvVar.du, bvVar.dv, bvVar.dA);
        bq.a(cpVar, bvVar.dm);
        bq.b(cpVar, bvVar.dd);
        Notification notificationA = bwVar.a(bvVar, cpVar);
        if (bvVar.dd != null) {
            bvVar.dd.a(bq.a(notificationA));
        }
        return notificationA;
    }
}
