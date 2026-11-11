package com.google.android.gms.internal.ads;

import android.view.MotionEvent;
import android.view.View;

/* JADX INFO: loaded from: classes.dex */
final class zzpr implements zzox {
    private final /* synthetic */ View zzbkc;
    private final /* synthetic */ zzpp zzbkj;

    zzpr(zzpp zzppVar, View view) {
        this.zzbkj = zzppVar;
        this.zzbkc = view;
    }

    @Override // com.google.android.gms.internal.ads.zzox
    public final void zzc(MotionEvent motionEvent) {
        this.zzbkj.onTouch(null, motionEvent);
    }

    @Override // com.google.android.gms.internal.ads.zzox
    public final void zzki() {
        if (this.zzbkj.zza(zzpp.zzbjs)) {
            this.zzbkj.onClick(this.zzbkc);
        }
    }
}
