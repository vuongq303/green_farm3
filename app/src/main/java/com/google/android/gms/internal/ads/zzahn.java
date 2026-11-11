package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzahn extends zzajx implements zzaht, zzahw, zzaia {
    private final Context mContext;
    public final String zzbth;
    private final zzaji zzbze;
    private final zzaib zzcll;
    private final zzahw zzclm;
    private final String zzcln;
    private final zzwx zzclo;
    private final long zzclp;
    private zzahq zzclr;
    private Future zzcls;
    private volatile com.google.android.gms.ads.internal.gmsg.zzb zzclt;
    private int zzclq = 0;
    private int mErrorCode = 3;
    private final Object mLock = new Object();

    public zzahn(Context context, String str, String str2, zzwx zzwxVar, zzaji zzajiVar, zzaib zzaibVar, zzahw zzahwVar, long j) {
        this.mContext = context;
        this.zzbth = str;
        this.zzcln = str2;
        this.zzclo = zzwxVar;
        this.zzbze = zzajiVar;
        this.zzcll = zzaibVar;
        this.zzclm = zzahwVar;
        this.zzclp = j;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzjj zzjjVar, zzxq zzxqVar) {
        this.zzcll.zzpf().zza((zzahw) this);
        try {
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzbth)) {
                zzxqVar.zza(zzjjVar, this.zzcln, this.zzclo.zzbrr);
            } else {
                zzxqVar.zzc(zzjjVar, this.zzcln);
            }
        } catch (RemoteException e) {
            zzakb.zzc("Fail to load ad from adapter.", e);
            zza(this.zzbth, 0);
        }
    }

    private final boolean zzf(long j) {
        long jElapsedRealtime = this.zzclp - (com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime() - j);
        if (jElapsedRealtime <= 0) {
            this.mErrorCode = 4;
            return false;
        }
        try {
            this.mLock.wait(jElapsedRealtime);
            return true;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            this.mErrorCode = 5;
            return false;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
    }

    public final void zza(com.google.android.gms.ads.internal.gmsg.zzb zzbVar) {
        this.zzclt = zzbVar;
    }

    @Override // com.google.android.gms.internal.ads.zzahw
    public final void zza(String str, int i) {
        synchronized (this.mLock) {
            this.zzclq = 2;
            this.mErrorCode = i;
            this.mLock.notify();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaht
    public final void zzac(int i) {
        zza(this.zzbth, 0);
    }

    @Override // com.google.android.gms.internal.ads.zzaia
    public final void zzc(Bundle bundle) {
        com.google.android.gms.ads.internal.gmsg.zzb zzbVar = this.zzclt;
        if (zzbVar != null) {
            zzbVar.zza("", bundle);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzahw
    public final void zzcb(String str) {
        synchronized (this.mLock) {
            this.zzclq = 1;
            this.mLock.notify();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        long jElapsedRealtime;
        if (this.zzcll == null || this.zzcll.zzpf() == null || this.zzcll.zzpe() == null) {
            return;
        }
        zzahv zzahvVarZzpf = this.zzcll.zzpf();
        zzahvVarZzpf.zza((zzahw) null);
        zzahvVarZzpf.zza((zzaht) this);
        zzahvVarZzpf.zza((zzaia) this);
        zzjj zzjjVar = this.zzbze.zzcgs.zzccv;
        zzxq zzxqVarZzpe = this.zzcll.zzpe();
        try {
            if (zzxqVarZzpe.isInitialized()) {
                zzamu.zzsy.post(new zzaho(this, zzjjVar, zzxqVarZzpe));
            } else {
                zzamu.zzsy.post(new zzahp(this, zzxqVarZzpe, zzjjVar, zzahvVarZzpf));
            }
            while (true) {
                synchronized (this.mLock) {
                    if (this.zzclq != 0) {
                        this.zzclr = new zzahs().zzg(com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime() - jElapsedRealtime).zzad(1 == this.zzclq ? 6 : this.mErrorCode).zzcc(this.zzbth).zzcd(this.zzclo.zzbru).zzpd();
                    } else if (!zzf(jElapsedRealtime)) {
                        this.zzclr = new zzahs().zzad(this.mErrorCode).zzg(com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime() - jElapsedRealtime).zzcc(this.zzbth).zzcd(this.zzclo.zzbru).zzpd();
                    }
                }
            }
        } catch (RemoteException e) {
            zzakb.zzc("Fail to check if adapter is initialized.", e);
            zza(this.zzbth, 0);
        }
        jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
        zzahvVarZzpf.zza((zzahw) null);
        zzahvVarZzpf.zza((zzaht) null);
        if (this.zzclq == 1) {
            this.zzclm.zzcb(this.zzbth);
        } else {
            this.zzclm.zza(this.zzbth, this.mErrorCode);
        }
    }

    public final Future zzoz() {
        if (this.zzcls != null) {
            return this.zzcls;
        }
        zzanz zzanzVar = (zzanz) zznt();
        this.zzcls = zzanzVar;
        return zzanzVar;
    }

    public final zzahq zzpa() {
        zzahq zzahqVar;
        synchronized (this.mLock) {
            zzahqVar = this.zzclr;
        }
        return zzahqVar;
    }

    public final zzwx zzpb() {
        return this.zzclo;
    }

    @Override // com.google.android.gms.internal.ads.zzaht
    public final void zzpc() {
        zza(this.zzbze.zzcgs.zzccv, this.zzcll.zzpe());
    }
}
