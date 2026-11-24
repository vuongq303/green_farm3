package com.quanhoang.greenfarm3.PushNotification;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import com.quanhoang.greenfarm3.R;

/* JADX INFO: compiled from: PushBuilderV16.java */
/* JADX INFO: loaded from: classes.dex */
public class o extends l {
    public o(Context context) {
        super(context);
    }

    @Override // com.quanhoang.greenfarm3.PushNotification.l
    public Notification build() {
        Notification.Builder builder = new Notification.Builder(this.context);
        builder.setContentTitle(this.title).setContentText(this.message).setSmallIcon(R.drawable.pn_status_icon).setWhen(this.when).setContentIntent(this.Fx).setTicker(this.title).setAutoCancel(this.Fy).setStyle(new Notification.BigTextStyle().bigText(this.message));
        if (Build.VERSION.SDK_INT > 23) {
            builder.setShowWhen(true);
        }
        if (Build.VERSION.SDK_INT > 25) {
            if (!d.v(this.context)) {
                builder.setChannelId(q.Gb);
            } else {
                builder.setChannelId(q.Gc);
            }
        }
        if (q.FM && q.FN != null && aa.hp() != null) {
            Notification.BigPictureStyle bigPictureStyle = new Notification.BigPictureStyle();
            bigPictureStyle.setBigContentTitle(this.title);
            bigPictureStyle.setSummaryText(this.message);
            bigPictureStyle.bigPicture(aa.hp());
            builder.setStyle(bigPictureStyle);
        } else if (this.FA != null && this.FA.length > 1) {
            Notification.InboxStyle inboxStyle = new Notification.InboxStyle();
            inboxStyle.setBigContentTitle(this.title);
            for (int length = this.FA.length - 1; length >= 0; length--) {
                inboxStyle.addLine(this.FA[length]);
            }
            builder.setStyle(inboxStyle);
        }
        if (!d.v(this.context)) {
            if (q.FK && q.FL != null) {
                try {
                    if (this.context.getResources().getIdentifier(q.FL, "raw", this.context.getPackageName()) > 0) {
                        builder.setSound(Uri.parse("android.resource://" + this.context.getPackageName() + "/raw/" + q.FL));
                        builder.setDefaults(6);
                        if (Build.VERSION.SDK_INT > 25) {
                            builder.setChannelId(q.Gd);
                        }
                    } else {
                        builder.setDefaults(-1);
                    }
                } catch (Exception e) {
                    builder.setDefaults(-1);
                    e.printStackTrace();
                }
            } else {
                builder.setDefaults(-1);
            }
        }
        builder.setLargeIcon(BitmapFactory.decodeResource(this.context.getResources(), z.getIcon()));
        if (this.Fz > 1) {
            builder.setNumber(this.Fz);
        }
        if (this.deleteIntent != null) {
            builder.setDeleteIntent(this.deleteIntent);
        }
        return builder.build();
    }
}
