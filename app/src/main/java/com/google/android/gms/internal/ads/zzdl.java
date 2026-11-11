package com.google.android.gms.internal.ads;

import android.app.Activity;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public final class zzdl extends zzei {
    private final Activity zztk;
    private final View zztl;

    public zzdl(zzcz zzczVar, String str, String str2, zzba zzbaVar, int i, int i2, View view, Activity activity) {
        super(zzczVar, str, str2, zzbaVar, i, 62);
        this.zztl = view;
        this.zztk = activity;
    }

    @Override // com.google.android.gms.internal.ads.zzei
    protected final void zzar() {
        if (this.zztl == null) {
            return;
        }
        boolean zBooleanValue = ((Boolean) zzkb.zzik().zzd(zznk.zzbas)).booleanValue();
        Object[] objArr = (Object[]) this.zztz.invoke(null, this.zztl, this.zztk, Boolean.valueOf(zBooleanValue));
        synchronized (this.zztq) {
            this.zztq.zzfa = Long.valueOf(((Long) objArr[0]).longValue());
            this.zztq.zzfb = Long.valueOf(((Long) objArr[1]).longValue());
            if (zBooleanValue) {
                this.zztq.zzfc = (String) objArr[2];
            }
        }
    }
}
