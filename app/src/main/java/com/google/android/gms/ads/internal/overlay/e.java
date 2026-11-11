package com.google.android.gms.ads.internal.overlay;

import android.graphics.Bitmap;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajx;
import com.google.android.gms.internal.ads.zzakk;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class e extends zzajx {
    final /* synthetic */ zzd bAG;

    private e(zzd zzdVar) {
        this.bAG = zzdVar;
    }

    /* synthetic */ e(zzd zzdVar, b bVar) {
        this(zzdVar);
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        Bitmap bitmapZza = zzbv.zzfe().zza(Integer.valueOf(this.bAG.bAn.zzbyw.zzzj));
        if (bitmapZza != null) {
            zzakk.zzcrm.post(new f(this, zzbv.zzem().zza(this.bAG.mActivity, bitmapZza, this.bAG.bAn.zzbyw.zzzh, this.bAG.bAn.zzbyw.zzzi)));
        }
    }
}
