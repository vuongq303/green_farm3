package javax.microedition.midlet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: App.java */
/* JADX INFO: loaded from: classes.dex */
final class d extends BroadcastReceiver {
    final /* synthetic */ App cnt;

    d(App app) {
        this.cnt = app;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (this.cnt.cno) {
            this.cnt.cno = false;
            this.cnt.akv();
        }
    }
}
