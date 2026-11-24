package com.quanhoang.greenfarm3.PushNotification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.os.Build;

/* JADX INFO: compiled from: PushBuilder.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class l {
    Context context;
    String message = null;
    String title = null;
    PendingIntent Fx = null;
    PendingIntent deleteIntent = null;
    int icon = 0;
    long when = 0;
    boolean Fy = true;
    int Fz = 0;
    String[] FA = null;

    public abstract Notification build();

    public l(Context context) {
        this.context = context;
    }

    public void setMessage(String str) {
        this.message = str;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public void setTime(long j) {
        this.when = j;
    }

    public void setAutoCancel(boolean z) {
        this.Fy = z;
    }

    public void c(PendingIntent pendingIntent) {
        this.Fx = pendingIntent;
    }

    public void d(PendingIntent pendingIntent) {
        this.deleteIntent = pendingIntent;
    }

    public void bb(int i) {
        this.Fz = i;
    }

    public void c(String[] strArr) {
        this.FA = strArr;
    }

    public static l X(Context context) {
        if (Build.VERSION.SDK_INT < 11) {
            return new m(context);
        }
        if (Build.VERSION.SDK_INT < 16) {
            return new n(context);
        }
        return new o(context);
    }
}
