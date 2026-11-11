package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.google.android.gms.ads.AdRequest;
import java.util.ArrayList;

/* JADX INFO: compiled from: NotificationCompatApi20.java */
/* JADX INFO: loaded from: classes.dex */
public class cl implements bo, bp {
    private int dA;
    private Notification.Builder dQ;
    private RemoteViews dt;
    private RemoteViews du;
    private Bundle mExtras;

    public cl(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, ArrayList<String> arrayList, Bundle bundle, String str, boolean z5, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3, int i5) {
        this.dQ = new Notification.Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z).setLocalOnly(z4).setGroup(str).setGroupSummary(z5).setSortKey(str2);
        this.mExtras = new Bundle();
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.mExtras.putStringArray("android.people", (String[]) arrayList.toArray(new String[arrayList.size()]));
        }
        this.dt = remoteViews2;
        this.du = remoteViews3;
        this.dA = i5;
    }

    @Override // android.support.v4.app.bo
    public void a(ct ctVar) {
        ck.a(this.dQ, ctVar);
    }

    @Override // android.support.v4.app.bp
    public Notification.Builder Z() {
        return this.dQ;
    }

    @Override // android.support.v4.app.bp
    public Notification build() {
        this.dQ.setExtras(this.mExtras);
        Notification notificationBuild = this.dQ.build();
        if (this.dt != null) {
            notificationBuild.contentView = this.dt;
        }
        if (this.du != null) {
            notificationBuild.bigContentView = this.du;
        }
        if (this.dA != 0) {
            if (notificationBuild.getGroup() != null && (notificationBuild.flags & AdRequest.MAX_CONTENT_URL_LENGTH) != 0 && this.dA == 2) {
                b(notificationBuild);
            }
            if (notificationBuild.getGroup() != null && (notificationBuild.flags & AdRequest.MAX_CONTENT_URL_LENGTH) == 0 && this.dA == 1) {
                b(notificationBuild);
            }
        }
        return notificationBuild;
    }

    private void b(Notification notification) {
        notification.sound = null;
        notification.vibrate = null;
        notification.defaults &= -2;
        notification.defaults &= -3;
    }
}
