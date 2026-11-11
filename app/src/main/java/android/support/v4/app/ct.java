package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;

/* JADX INFO: compiled from: NotificationCompatBase.java */
/* JADX INFO: loaded from: classes.dex */
public abstract class ct {
    public abstract dj[] ac();

    public abstract dj[] ad();

    public abstract PendingIntent getActionIntent();

    public abstract boolean getAllowGeneratedReplies();

    public abstract Bundle getExtras();

    public abstract int getIcon();

    public abstract CharSequence getTitle();
}
