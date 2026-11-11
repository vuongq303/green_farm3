package android.support.v4.app;

import android.app.Notification;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class bw {
    protected bw() {
    }

    public Notification a(bv bvVar, bp bpVar) {
        Notification notificationBuild = bpVar.build();
        if (bvVar.dt != null) {
            notificationBuild.contentView = bvVar.dt;
        }
        return notificationBuild;
    }
}
