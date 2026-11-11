package com.google.android.gms.internal.ads;

import android.os.Bundle;
import com.google.android.gms.c.a;
import com.google.android.gms.c.d;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzahv extends zzaid {
    private volatile zzahw zzclm;
    private volatile zzaht zzcma;
    private volatile zzahu zzcmb;
    private volatile zzaia zzcmc;

    public zzahv(zzahu zzahuVar) {
        this.zzcmb = zzahuVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zza(a aVar, zzaig zzaigVar) {
        if (this.zzcmb != null) {
            this.zzcmb.zzc(zzaigVar);
        }
    }

    public final void zza(zzaht zzahtVar) {
        this.zzcma = zzahtVar;
    }

    public final void zza(zzahw zzahwVar) {
        this.zzclm = zzahwVar;
    }

    public final void zza(zzaia zzaiaVar) {
        this.zzcmc = zzaiaVar;
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzc(Bundle bundle) {
        if (this.zzcmc != null) {
            this.zzcmc.zzc(bundle);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzc(a aVar, int i) {
        if (this.zzcma != null) {
            this.zzcma.zzac(i);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzd(a aVar, int i) {
        if (this.zzclm != null) {
            this.zzclm.zza(d.b(aVar).getClass().getName(), i);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzq(a aVar) {
        if (this.zzcma != null) {
            this.zzcma.zzpc();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzr(a aVar) {
        if (this.zzclm != null) {
            this.zzclm.zzcb(d.b(aVar).getClass().getName());
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzs(a aVar) {
        if (this.zzcmb != null) {
            this.zzcmb.onRewardedVideoAdOpened();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzt(a aVar) {
        if (this.zzcmb != null) {
            this.zzcmb.onRewardedVideoStarted();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzu(a aVar) {
        if (this.zzcmb != null) {
            this.zzcmb.onRewardedVideoAdClosed();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzv(a aVar) {
        if (this.zzcmb != null) {
            this.zzcmb.zzdm();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzw(a aVar) {
        if (this.zzcmb != null) {
            this.zzcmb.onRewardedVideoAdLeftApplication();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaic
    public final void zzx(a aVar) {
        if (this.zzcmb != null) {
            this.zzcmb.onRewardedVideoCompleted();
        }
    }
}
