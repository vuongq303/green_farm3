package javax.microedition.midlet;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/* JADX INFO: compiled from: BaseActivity.java */
/* JADX INFO: loaded from: classes.dex */
public class h extends BroadcastReceiver {
    final /* synthetic */ BaseActivity cny;

    public h(BaseActivity baseActivity) {
        this.cny = baseActivity;
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("com.gameloft.android.wrapper.FISH_ALL_ACTIVITIES_RUNNING.com.gameloft.android.ANMP.GloftGF2F")) {
            this.cny.finish();
        }
    }
}
