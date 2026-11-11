package com.google.android.gms.internal.ads;

import android.content.Context;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzabt extends zzajx {
    private final Object mLock;
    private final zzabm zzbzd;
    private final zzaji zzbze;
    private final zzaej zzbzf;
    private final zzabv zzbzu;
    private Future<zzajh> zzbzv;

    public zzabt(Context context, com.google.android.gms.ads.internal.zzbc zzbcVar, zzaji zzajiVar, zzci zzciVar, zzabm zzabmVar, zznx zznxVar) {
        this(zzajiVar, zzabmVar, new zzabv(context, zzbcVar, new zzalt(context), zzciVar, zzajiVar, zznxVar));
    }

    private zzabt(zzaji zzajiVar, zzabm zzabmVar, zzabv zzabvVar) {
        this.mLock = new Object();
        this.zzbze = zzajiVar;
        this.zzbzf = zzajiVar.zzcos;
        this.zzbzd = zzabmVar;
        this.zzbzu = zzabvVar;
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
        synchronized (this.mLock) {
            if (this.zzbzv != null) {
                this.zzbzv.cancel(true);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        zzajh zzajhVar;
        int i = -2;
        try {
            synchronized (this.mLock) {
                this.zzbzv = zzaki.zza(this.zzbzu);
            }
            zzajhVar = this.zzbzv.get(60000L, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            i = 0;
            zzajhVar = null;
        } catch (CancellationException e2) {
            i = 0;
            zzajhVar = null;
        } catch (ExecutionException e3) {
            i = 0;
            zzajhVar = null;
        } catch (TimeoutException e4) {
            zzakb.zzdk("Timed out waiting for native ad.");
            i = 2;
            this.zzbzv.cancel(true);
            zzajhVar = null;
        }
        if (zzajhVar == null) {
            zzajhVar = new zzajh(this.zzbze.zzcgs.zzccv, null, null, i, null, null, this.zzbzf.orientation, this.zzbzf.zzbsu, this.zzbze.zzcgs.zzccy, false, null, null, null, null, null, this.zzbzf.zzcer, this.zzbze.zzacv, this.zzbzf.zzcep, this.zzbze.zzcoh, this.zzbzf.zzceu, this.zzbzf.zzcev, this.zzbze.zzcob, null, null, null, null, this.zzbze.zzcos.zzcfh, this.zzbze.zzcos.zzcfi, null, null, this.zzbzf.zzcfl, this.zzbze.zzcoq, this.zzbze.zzcos.zzzl, false, this.zzbze.zzcos.zzcfp, null, this.zzbze.zzcos.zzzm, this.zzbze.zzcos.zzcfq);
        }
        zzakk.zzcrm.post(new zzabu(this, zzajhVar));
    }
}
