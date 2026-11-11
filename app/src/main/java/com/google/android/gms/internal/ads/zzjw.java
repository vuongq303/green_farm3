package com.google.android.gms.internal.ads;

import android.content.Context;
import com.google.android.gms.c.d;

/* JADX INFO: loaded from: classes.dex */
final class zzjw extends zzjr.zza<zzlj> {
    private final /* synthetic */ Context val$context;
    private final /* synthetic */ zzjr zzart;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzjw(zzjr zzjrVar, Context context) {
        super();
        this.zzart = zzjrVar;
        this.val$context = context;
    }

    @Override // com.google.android.gms.internal.ads.zzjr.zza
    public final /* synthetic */ zzlj zza(zzld zzldVar) {
        return zzldVar.getMobileAdsSettingsManagerWithClientJarVersion(d.F(this.val$context), 12451000);
    }

    @Override // com.google.android.gms.internal.ads.zzjr.zza
    public final /* synthetic */ zzlj zzib() {
        zzlj zzljVarZzg = this.zzart.zzarl.zzg(this.val$context);
        if (zzljVarZzg != null) {
            return zzljVarZzg;
        }
        zzjr zzjrVar = this.zzart;
        zzjr.zza(this.val$context, "mobile_ads_settings");
        return new zzml();
    }
}
