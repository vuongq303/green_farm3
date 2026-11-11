package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.RemoteViews;
import com.google.android.gms.ads.AdRequest;
import java.util.ArrayList;
import java.util.Iterator;

/* JADX INFO: compiled from: NotificationCompatApi24.java */
/* JADX INFO: loaded from: classes.dex */
public class cp implements bo, bp {
    private int dA;
    private Notification.Builder dQ;

    public cp(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int i, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int i2, int i3, boolean z, boolean z2, boolean z3, int i4, CharSequence charSequence4, boolean z4, String str, ArrayList<String> arrayList, Bundle bundle, int i5, int i6, Notification notification2, String str2, boolean z5, String str3, CharSequence[] charSequenceArr, RemoteViews remoteViews2, RemoteViews remoteViews3, RemoteViews remoteViews4, int i7) {
        this.dQ = new Notification.Builder(context).setWhen(notification.when).setShowWhen(z2).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS).setOngoing((notification.flags & 2) != 0).setOnlyAlertOnce((notification.flags & 8) != 0).setAutoCancel((notification.flags & 16) != 0).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setSubText(charSequence4).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent).setFullScreenIntent(pendingIntent2, (notification.flags & 128) != 0).setLargeIcon(bitmap).setNumber(i).setUsesChronometer(z3).setPriority(i4).setProgress(i2, i3, z).setLocalOnly(z4).setExtras(bundle).setGroup(str2).setGroupSummary(z5).setSortKey(str3).setCategory(str).setColor(i5).setVisibility(i6).setPublicVersion(notification2).setRemoteInputHistory(charSequenceArr);
        if (remoteViews2 != null) {
            this.dQ.setCustomContentView(remoteViews2);
        }
        if (remoteViews3 != null) {
            this.dQ.setCustomBigContentView(remoteViews3);
        }
        if (remoteViews4 != null) {
            this.dQ.setCustomHeadsUpContentView(remoteViews4);
        }
        Iterator<String> it = arrayList.iterator();
        while (it.hasNext()) {
            this.dQ.addPerson(it.next());
        }
        this.dA = i7;
    }

    @Override // android.support.v4.app.bo
    public void a(ct ctVar) {
        co.a(this.dQ, ctVar);
    }

    @Override // android.support.v4.app.bp
    public Notification.Builder Z() {
        return this.dQ;
    }

    @Override // android.support.v4.app.bp
    public Notification build() {
        Notification notificationBuild = this.dQ.build();
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
