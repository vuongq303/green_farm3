package com.google.android.gms.internal.ads;

import android.view.MotionEvent;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;

/* JADX INFO: loaded from: classes.dex */
final class zzot implements zzox {
    private final /* synthetic */ zzos zzbir;

    zzot(zzos zzosVar) {
        this.zzbir = zzosVar;
    }

    @Override // com.google.android.gms.internal.ads.zzox
    public final void zzc(MotionEvent motionEvent) {
    }

    @Override // com.google.android.gms.internal.ads.zzox
    public final void zzki() {
        this.zzbir.performClick(NativeCustomTemplateAd.ASSET_NAME_VIDEO);
    }
}
