package com.gameloft.android.ANMP.GloftGF2F.PushNotification;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import com.gameloft.android.ANMP.GloftGF2F.R;

/* JADX INFO: compiled from: PushBuilderV11.java */
/* JADX INFO: loaded from: classes.dex */
public class n extends l {
    public n(Context context) {
        super(context);
    }

    @Override // com.gameloft.android.ANMP.GloftGF2F.PushNotification.l
    public Notification build() {
        Notification.Builder builder = new Notification.Builder(this.context);
        builder.setContentTitle(this.title).setContentText(this.message).setSmallIcon(R.drawable.pn_status_icon).setWhen(this.when).setContentIntent(this.Fx).setTicker(this.title).setAutoCancel(this.Fy);
        if (!d.v(this.context)) {
            if (q.FK && q.FL != null) {
                try {
                    if (this.context.getResources().getIdentifier(q.FL, "raw", this.context.getPackageName()) > 0) {
                        builder.setSound(Uri.parse("android.resource://" + this.context.getPackageName() + "/raw/" + q.FL));
                        builder.setDefaults(6);
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
        return builder.getNotification();
    }
}
