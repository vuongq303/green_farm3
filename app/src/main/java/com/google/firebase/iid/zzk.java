package com.google.firebase.iid;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.f.f;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.annotation.concurrent.GuardedBy;

/* JADX INFO: loaded from: classes.dex */
public final class zzk {

    @GuardedBy("MessengerIpcClient.class")
    private static zzk zzbqv;
    private final ScheduledExecutorService zzbqw;

    @GuardedBy("this")
    private zzm zzbqx = new zzm(this);

    @GuardedBy("this")
    private int zzbqy = 1;
    private final Context zzqs;

    private zzk(Context context, ScheduledExecutorService scheduledExecutorService) {
        this.zzbqw = scheduledExecutorService;
        this.zzqs = context.getApplicationContext();
    }

    private final synchronized <T> f<T> zza(zzt<T> zztVar) {
        if (Log.isLoggable("MessengerIpcClient", 3)) {
            String strValueOf = String.valueOf(zztVar);
            Log.d("MessengerIpcClient", new StringBuilder(String.valueOf(strValueOf).length() + 9).append("Queueing ").append(strValueOf).toString());
        }
        if (!this.zzbqx.zzb(zztVar)) {
            this.zzbqx = new zzm(this);
            this.zzbqx.zzb(zztVar);
        }
        return zztVar.zzbri.ZK();
    }

    private final synchronized int zzsp() {
        int i;
        i = this.zzbqy;
        this.zzbqy = i + 1;
        return i;
    }

    public static synchronized zzk zzv(Context context) {
        if (zzbqv == null) {
            zzbqv = new zzk(context, Executors.newSingleThreadScheduledExecutor());
        }
        return zzbqv;
    }

    public final f<Void> zza(int i, Bundle bundle) {
        return zza(new zzs(zzsp(), 2, bundle));
    }

    public final f<Bundle> zzb(int i, Bundle bundle) {
        return zza(new zzv(zzsp(), 1, bundle));
    }
}
