package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
class cb extends ca {
    cb() {
    }

    @Override // android.support.v4.app.ca, android.support.v4.app.cg, android.support.v4.app.ci
    public Notification a(bv bvVar, bw bwVar) {
        cy cyVar = new cy(bvVar.mContext, bvVar.dB, bvVar.ag(), bvVar.af(), bvVar.cY, bvVar.cW, bvVar.cZ, bvVar.cU, bvVar.cV, bvVar.cX, bvVar.dg, bvVar.dh, bvVar.di, bvVar.db, bvVar.dc, bvVar.da, bvVar.de, bvVar.dn, bvVar.dC, bvVar.mExtras, bvVar.dj, bvVar.dk, bvVar.dl, bvVar.dt, bvVar.du);
        bq.a(cyVar, bvVar.dm);
        bq.a(cyVar, bvVar.dd);
        return bwVar.a(bvVar, cyVar);
    }
}
