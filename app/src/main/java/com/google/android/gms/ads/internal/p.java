package com.google.android.gms.ads.internal;

import android.os.Debug;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/* JADX INFO: loaded from: classes.dex */
final class p extends TimerTask {
    private final /* synthetic */ Timer bBG;
    private final /* synthetic */ zza bBH;
    private final /* synthetic */ CountDownLatch zzwd;

    p(zza zzaVar, CountDownLatch countDownLatch, Timer timer) {
        this.bBH = zzaVar;
        this.zzwd = countDownLatch;
        this.bBG = timer;
    }

    @Override // java.util.TimerTask, java.lang.Runnable
    public final void run() {
        if (((Integer) zzkb.zzik().zzd(zznk.zzbck)).intValue() != this.zzwd.getCount()) {
            zzakb.zzck("Stopping method tracing");
            Debug.stopMethodTracing();
            if (this.zzwd.getCount() == 0) {
                this.bBG.cancel();
                return;
            }
        }
        String strConcat = String.valueOf(this.bBH.zzvw.zzrt.getPackageName()).concat("_adsTrace_");
        try {
            zzakb.zzck("Starting method tracing");
            this.zzwd.countDown();
            Debug.startMethodTracing(new StringBuilder(String.valueOf(strConcat).length() + 20).append(strConcat).append(zzbv.zzer().currentTimeMillis()).toString(), ((Integer) zzkb.zzik().zzd(zznk.zzbcl)).intValue());
        } catch (Exception e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }
}
