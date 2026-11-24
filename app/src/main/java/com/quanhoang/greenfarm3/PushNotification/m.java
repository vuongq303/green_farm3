package com.quanhoang.greenfarm3.PushNotification;

import android.app.Notification;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.bv;
import com.quanhoang.greenfarm3.R;

/* JADX INFO: compiled from: PushBuilderV10.java */
/* JADX INFO: loaded from: classes.dex */
public class m extends l {
    public m(Context context) {
        super(context);
    }

    @Override // com.quanhoang.greenfarm3.PushNotification.l
    public Notification build() {
        bv bvVar = new bv(this.context);
        bvVar.b(this.title).c(this.message).l(R.mipmap.ic_launcher).c(this.when).a(this.Fx).d(this.title).d(this.Fy);
        if (!d.v(this.context)) {
            if (q.FK && q.FL != null) {
                try {
                    if (this.context.getResources().getIdentifier(q.FL, "raw", this.context.getPackageName()) > 0) {
                        bvVar.a(Uri.parse("android.resource://" + this.context.getPackageName() + "/raw/" + q.FL));
                        bvVar.n(6);
                    } else {
                        bvVar.n(-1);
                    }
                } catch (Exception e) {
                    bvVar.n(-1);
                    e.printStackTrace();
                }
            } else {
                bvVar.n(-1);
            }
        }
        bvVar.a(BitmapFactory.decodeResource(this.context.getResources(), z.getIcon()));
        if (this.Fz > 1) {
            bvVar.m(this.Fz);
        }
        if (this.deleteIntent != null) {
            bvVar.b(this.deleteIntent);
        }
        return bvVar.build();
    }
}
