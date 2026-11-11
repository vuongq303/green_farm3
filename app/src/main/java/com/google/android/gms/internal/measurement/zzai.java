package com.google.android.gms.internal.measurement;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import com.google.android.gms.a.n;
import com.google.android.gms.common.internal.bf;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
public final class zzai extends zzar {
    private final zzbf zzva;

    public zzai(zzat zzatVar, zzav zzavVar) {
        super(zzatVar);
        bf.checkNotNull(zzavVar);
        this.zzva = new zzbf(zzatVar, zzavVar);
    }

    final void onServiceConnected() {
        n.zzab();
        this.zzva.onServiceConnected();
    }

    public final void setLocalDispatchPeriod(int i) {
        zzch();
        zzb("setLocalDispatchPeriod (sec)", Integer.valueOf(i));
        zzbw().c(new zzaj(this, i));
    }

    public final void start() {
        this.zzva.start();
    }

    public final long zza(zzaw zzawVar) {
        zzch();
        bf.checkNotNull(zzawVar);
        n.zzab();
        long jZza = this.zzva.zza(zzawVar, true);
        if (jZza == 0) {
            this.zzva.zzb(zzawVar);
        }
        return jZza;
    }

    public final void zza(zzca zzcaVar) {
        zzch();
        zzbw().c(new zzao(this, zzcaVar));
    }

    public final void zza(zzch zzchVar) {
        bf.checkNotNull(zzchVar);
        zzch();
        zzb("Hit delivery requested", zzchVar);
        zzbw().c(new zzam(this, zzchVar));
    }

    public final void zza(String str, Runnable runnable) {
        bf.d(str, (Object) "campaign param can't be empty");
        zzbw().c(new zzal(this, str, runnable));
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        this.zzva.zzm();
    }

    public final void zzbn() {
        zzch();
        zzbw().c(new zzan(this));
    }

    public final void zzbo() {
        zzch();
        Context context = getContext();
        if (!zzct.zza(context) || !zzcu.zze(context)) {
            zza((zzca) null);
            return;
        }
        Intent intent = new Intent("com.google.android.gms.analytics.ANALYTICS_DISPATCH");
        intent.setComponent(new ComponentName(context, "com.google.android.gms.analytics.AnalyticsService"));
        context.startService(intent);
    }

    public final boolean zzbp() {
        zzch();
        try {
            zzbw().a(new zzap(this)).get(4L, TimeUnit.SECONDS);
            return true;
        } catch (InterruptedException e) {
            zzd("syncDispatchLocalHits interrupted", e);
            return false;
        } catch (ExecutionException e2) {
            zze("syncDispatchLocalHits failed", e2);
            return false;
        } catch (TimeoutException e3) {
            zzd("syncDispatchLocalHits timed out", e3);
            return false;
        }
    }

    public final void zzbq() {
        zzch();
        n.zzab();
        zzbf zzbfVar = this.zzva;
        n.zzab();
        zzbfVar.zzch();
        zzbfVar.zzq("Service disconnected");
    }

    final void zzbr() {
        n.zzab();
        this.zzva.zzbr();
    }
}
