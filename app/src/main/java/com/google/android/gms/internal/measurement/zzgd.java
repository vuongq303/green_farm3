package com.google.android.gms.internal.measurement;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.measurement.a;

/* JADX INFO: loaded from: classes.dex */
final class zzgd implements Runnable {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzgl zzafi;
    private final /* synthetic */ zzfg zzala;
    private final /* synthetic */ long zzalb;
    private final /* synthetic */ Bundle zzalc;
    private final /* synthetic */ BroadcastReceiver.PendingResult zzqp;

    zzgd(zzgb zzgbVar, zzgl zzglVar, long j, Bundle bundle, Context context, zzfg zzfgVar, BroadcastReceiver.PendingResult pendingResult) {
        this.zzafi = zzglVar;
        this.zzalb = j;
        this.zzalc = bundle;
        this.val$context = context;
        this.zzala = zzfgVar;
        this.zzqp = pendingResult;
    }

    @Override // java.lang.Runnable
    public final void run() {
        long j = this.zzafi.zzgh().zzajy.get();
        long j2 = this.zzalb;
        if (j > 0 && (j2 >= j || j2 <= 0)) {
            j2 = j - 1;
        }
        if (j2 > 0) {
            this.zzalc.putLong("click_timestamp", j2);
        }
        this.zzalc.putString("_cis", "referrer broadcast");
        a.aX(this.val$context).b("auto", "_cmp", this.zzalc);
        this.zzala.zzir().log("Install campaign recorded");
        if (this.zzqp != null) {
            this.zzqp.finish();
        }
    }
}
