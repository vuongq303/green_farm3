package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.SparseArray;
import android.widget.RemoteViews;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: compiled from: NotificationCompatKitKat.java */
/* JADX INFO: loaded from: classes.dex */
public class cy implements bo, bp {
    private Notification.Builder dQ;
    private List<Bundle> dW = new ArrayList();
    private RemoteViews dt;
    private RemoteViews du;
    private Bundle mExtras;

    public cy(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, ArrayList<String> arrayList, Bundle bundle, String str, boolean z5, String str2, RemoteViews remoteViews2, RemoteViews remoteViews3) {
        this.dQ = new Notification.Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z);
        this.mExtras = new Bundle();
        if (bundle != null) {
            this.mExtras.putAll(bundle);
        }
        if (arrayList != null && !arrayList.isEmpty()) {
            this.mExtras.putStringArray("android.people", (String[]) arrayList.toArray(new String[arrayList.size()]));
        }
        if (z4) {
            this.mExtras.putBoolean("android.support.localOnly", true);
        }
        if (str != null) {
            this.mExtras.putString("android.support.groupKey", str);
            if (z5) {
                this.mExtras.putBoolean("android.support.isGroupSummary", true);
            } else {
                this.mExtras.putBoolean("android.support.useSideChannel", true);
            }
        }
        if (str2 != null) {
            this.mExtras.putString("android.support.sortKey", str2);
        }
        this.dt = remoteViews2;
        this.du = remoteViews3;
    }

    @Override // android.support.v4.app.bo
    public void a(ct ctVar) {
        this.dW.add(cv.b(this.dQ, ctVar));
    }

    @Override // android.support.v4.app.bp
    public Notification.Builder Z() {
        return this.dQ;
    }

    @Override // android.support.v4.app.bp
    public Notification build() {
        SparseArray<Bundle> sparseArrayC = cv.c(this.dW);
        if (sparseArrayC != null) {
            this.mExtras.putSparseParcelableArray("android.support.actionExtras", sparseArrayC);
        }
        this.dQ.setExtras(this.mExtras);
        Notification notificationBuild = this.dQ.build();
        if (this.dt != null) {
            notificationBuild.contentView = this.dt;
        }
        if (this.du != null) {
            notificationBuild.bigContentView = this.du;
        }
        return notificationBuild;
    }
}
