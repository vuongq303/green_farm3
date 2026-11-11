package com.google.android.gms.internal.measurement;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.gms.common.d.e;

/* JADX INFO: loaded from: classes.dex */
public final class zzjk extends zzhk {
    private Handler handler;
    private long zzaqo;
    private final zzem zzaqp;
    private final zzem zzaqq;

    zzjk(zzgl zzglVar) {
        super(zzglVar);
        this.zzaqp = new zzjl(this, this.zzacr);
        this.zzaqq = new zzjm(this, this.zzacr);
        this.zzaqo = zzbt().elapsedRealtime();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzaf(long j) {
        zzab();
        zzkq();
        this.zzaqp.cancel();
        this.zzaqq.cancel();
        zzgg().zzir().zzg("Activity resumed, time", Long.valueOf(j));
        this.zzaqo = j;
        if (zzbt().currentTimeMillis() - zzgh().zzaki.get() > zzgh().zzakk.get()) {
            zzgh().zzakj.set(true);
            zzgh().zzakl.set(0L);
        }
        if (zzgh().zzakj.get()) {
            this.zzaqp.zzh(Math.max(0L, zzgh().zzakh.get() - zzgh().zzakl.get()));
        } else {
            this.zzaqq.zzh(Math.max(0L, 3600000 - zzgh().zzakl.get()));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzag(long j) {
        zzab();
        zzkq();
        this.zzaqp.cancel();
        this.zzaqq.cancel();
        zzgg().zzir().zzg("Activity paused, time", Long.valueOf(j));
        if (this.zzaqo != 0) {
            zzgh().zzakl.set(zzgh().zzakl.get() + (j - this.zzaqo));
        }
    }

    private final void zzkq() {
        synchronized (this) {
            if (this.handler == null) {
                this.handler = new Handler(Looper.getMainLooper());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzkr() {
        zzab();
        zzm(false);
        zzfs().zzk(zzbt().elapsedRealtime());
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ Context getContext() {
        return super.getContext();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzab() {
        super.zzab();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ e zzbt() {
        return super.zzbt();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfq() {
        super.zzfq();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ void zzfr() {
        super.zzfr();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzdx zzfs() {
        return super.zzfs();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzee zzft() {
        return super.zzft();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzhm zzfu() {
        return super.zzfu();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfb zzfv() {
        return super.zzfv();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeo zzfw() {
        return super.zzfw();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzil zzfx() {
        return super.zzfx();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzih zzfy() {
        return super.zzfy();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfc zzfz() {
        return super.zzfz();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzei zzga() {
        return super.zzga();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfe zzgb() {
        return super.zzgb();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjv zzgc() {
        return super.zzgc();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgf zzgd() {
        return super.zzgd();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzjk zzge() {
        return super.zzge();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzgg zzgf() {
        return super.zzgf();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfg zzgg() {
        return super.zzgg();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzfr zzgh() {
        return super.zzgh();
    }

    @Override // com.google.android.gms.internal.measurement.zzhj
    public final /* bridge */ /* synthetic */ zzeh zzgi() {
        return super.zzgi();
    }

    @Override // com.google.android.gms.internal.measurement.zzhk
    protected final boolean zzhh() {
        return false;
    }

    public final boolean zzm(boolean z) {
        zzab();
        zzch();
        long jElapsedRealtime = zzbt().elapsedRealtime();
        zzgh().zzakk.set(zzbt().currentTimeMillis());
        long j = jElapsedRealtime - this.zzaqo;
        if (!z && j < 1000) {
            zzgg().zzir().zzg("Screen exposed for less than 1000 ms. Event not sent. time", Long.valueOf(j));
            return false;
        }
        zzgh().zzakl.set(j);
        zzgg().zzir().zzg("Recording user engagement, ms", Long.valueOf(j));
        Bundle bundle = new Bundle();
        bundle.putLong("_et", j);
        zzih.zza((zzig) zzfy().zzkk(), bundle, true);
        zzfu().zza("auto", "_e", bundle);
        this.zzaqo = jElapsedRealtime;
        this.zzaqq.cancel();
        this.zzaqq.zzh(Math.max(0L, 3600000 - zzgh().zzakl.get()));
        return true;
    }
}
