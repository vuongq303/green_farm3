package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.common.d.i;

/* JADX INFO: loaded from: classes.dex */
final class zzadw implements zzady {
    private final /* synthetic */ Context val$context;

    zzadw(Context context) {
        this.val$context = context;
    }

    @Override // com.google.android.gms.internal.ads.zzady
    public final boolean zza(zzang zzangVar) {
        zzkb.zzif();
        boolean zZzbe = zzamu.zzbe(this.val$context);
        boolean z = ((Boolean) zzkb.zzik().zzd(zznk.zzbeq)).booleanValue() && zzangVar.zzcvg;
        if (zzadv.zzc(this.val$context, zzangVar.zzcvg) && zZzbe && !z) {
            if (!i.aO(this.val$context)) {
                return false;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzawa)).booleanValue()) {
                return false;
            }
        }
        return true;
    }
}
