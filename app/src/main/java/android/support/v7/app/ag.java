package android.support.v7.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: AppCompatDelegateImplV14.java */
/* JADX INFO: loaded from: classes.dex */
class ag extends BroadcastReceiver {
    final /* synthetic */ af lp;

    ag(af afVar) {
        this.lp = afVar;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        this.lp.bL();
    }
}
