package com.google.android.gms.internal.ads;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import java.util.Map;
import java.util.WeakHashMap;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzamq {
    private boolean zzcue;
    private Context zzsz;
    private boolean zzsh = false;

    @GuardedBy("this")
    private final Map<BroadcastReceiver, IntentFilter> zzcud = new WeakHashMap();

    @GuardedBy("this")
    private final BroadcastReceiver zzcuc = new zzamr(this);

    /* JADX INFO: Access modifiers changed from: private */
    public final synchronized void zzc(Context context, Intent intent) {
        for (Map.Entry<BroadcastReceiver, IntentFilter> entry : this.zzcud.entrySet()) {
            if (entry.getValue().hasAction(intent.getAction())) {
                entry.getKey().onReceive(context, intent);
            }
        }
    }

    public final synchronized void initialize(Context context) {
        if (!this.zzsh) {
            this.zzsz = context.getApplicationContext();
            if (this.zzsz == null) {
                this.zzsz = context;
            }
            zznk.initialize(this.zzsz);
            this.zzcue = ((Boolean) zzkb.zzik().zzd(zznk.zzbcc)).booleanValue();
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.intent.action.SCREEN_ON");
            intentFilter.addAction("android.intent.action.SCREEN_OFF");
            intentFilter.addAction("android.intent.action.USER_PRESENT");
            this.zzsz.registerReceiver(this.zzcuc, intentFilter);
            this.zzsh = true;
        }
    }

    public final synchronized void zza(Context context, BroadcastReceiver broadcastReceiver) {
        if (this.zzcue) {
            this.zzcud.remove(broadcastReceiver);
        } else {
            context.unregisterReceiver(broadcastReceiver);
        }
    }

    public final synchronized void zza(Context context, BroadcastReceiver broadcastReceiver, IntentFilter intentFilter) {
        if (this.zzcue) {
            this.zzcud.put(broadcastReceiver, intentFilter);
        } else {
            context.registerReceiver(broadcastReceiver, intentFilter);
        }
    }
}
