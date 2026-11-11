package com.google.firebase.iid;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.v4.content.v;
import android.util.Log;
import com.google.android.gms.common.d.a.b;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzb extends Service {
    private final Object lock;
    final ExecutorService zzall;
    private Binder zzbpo;
    private int zzbpp;
    private int zzbpq;

    public zzb() {
        String strValueOf = String.valueOf(getClass().getSimpleName());
        this.zzall = Executors.newSingleThreadExecutor(new b(strValueOf.length() != 0 ? "Firebase-".concat(strValueOf) : new String("Firebase-")));
        this.lock = new Object();
        this.zzbpq = 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zze(Intent intent) {
        if (intent != null) {
            v.completeWakefulIntent(intent);
        }
        synchronized (this.lock) {
            this.zzbpq--;
            if (this.zzbpq == 0) {
                stopSelfResult(this.zzbpp);
            }
        }
    }

    @Override // android.app.Service
    public final synchronized IBinder onBind(Intent intent) {
        if (Log.isLoggable("EnhancedIntentService", 3)) {
            Log.d("EnhancedIntentService", "Service received bind request");
        }
        if (this.zzbpo == null) {
            this.zzbpo = new zzf(this);
        }
        return this.zzbpo;
    }

    @Override // android.app.Service
    public final int onStartCommand(Intent intent, int i, int i2) {
        synchronized (this.lock) {
            this.zzbpp = i2;
            this.zzbpq++;
        }
        Intent intentZzf = zzf(intent);
        if (intentZzf == null) {
            zze(intent);
            return 2;
        }
        if (zzg(intentZzf)) {
            zze(intent);
            return 2;
        }
        this.zzall.execute(new zzc(this, intentZzf, intent));
        return 3;
    }

    protected Intent zzf(Intent intent) {
        return intent;
    }

    public boolean zzg(Intent intent) {
        return false;
    }

    public abstract void zzh(Intent intent);
}
