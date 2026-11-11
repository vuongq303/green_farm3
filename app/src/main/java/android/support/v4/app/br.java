package android.support.v4.app;

import android.app.PendingIntent;
import android.os.Bundle;

/* JADX INFO: compiled from: NotificationCompat.java */
/* JADX INFO: loaded from: classes.dex */
public class br extends ct {
    public static final cu cN = new bs();
    public PendingIntent actionIntent;
    private final db[] cK;
    private final db[] cL;
    private boolean cM;
    public int icon;
    final Bundle mExtras;
    public CharSequence title;

    @Override // android.support.v4.app.ct
    public int getIcon() {
        return this.icon;
    }

    @Override // android.support.v4.app.ct
    public CharSequence getTitle() {
        return this.title;
    }

    @Override // android.support.v4.app.ct
    public PendingIntent getActionIntent() {
        return this.actionIntent;
    }

    @Override // android.support.v4.app.ct
    public Bundle getExtras() {
        return this.mExtras;
    }

    @Override // android.support.v4.app.ct
    public boolean getAllowGeneratedReplies() {
        return this.cM;
    }

    @Override // android.support.v4.app.ct
    /* JADX INFO: renamed from: aa, reason: merged with bridge method [inline-methods] */
    public db[] ad() {
        return this.cK;
    }

    @Override // android.support.v4.app.ct
    /* JADX INFO: renamed from: ab, reason: merged with bridge method [inline-methods] */
    public db[] ac() {
        return this.cL;
    }
}
