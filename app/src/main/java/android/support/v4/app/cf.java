package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
class cf extends ce {
    cf() {
    }

    @Override // android.support.v4.app.ce, android.support.v4.app.cd, android.support.v4.app.cc, android.support.v4.app.cb, android.support.v4.app.ca, android.support.v4.app.cg, android.support.v4.app.ci
    public Notification a(bv bvVar, bw bwVar) {
        cr crVar = new cr(bvVar.mContext, bvVar.dB, bvVar.cS, bvVar.cT, bvVar.cY, bvVar.cW, bvVar.cZ, bvVar.cU, bvVar.cV, bvVar.cX, bvVar.dg, bvVar.dh, bvVar.di, bvVar.db, bvVar.dc, bvVar.da, bvVar.de, bvVar.dn, bvVar.mCategory, bvVar.dC, bvVar.mExtras, bvVar.dq, bvVar.dr, bvVar.ds, bvVar.dj, bvVar.dk, bvVar.dl, bvVar.df, bvVar.dt, bvVar.du, bvVar.dv, bvVar.dw, bvVar.dx, bvVar.dy, bvVar.dz, bvVar.f0do, bvVar.dp, bvVar.dA);
        bq.a(crVar, bvVar.dm);
        bq.b(crVar, bvVar.dd);
        Notification notificationA = bwVar.a(bvVar, crVar);
        if (bvVar.dd != null) {
            bvVar.dd.a(bq.a(notificationA));
        }
        return notificationA;
    }
}
