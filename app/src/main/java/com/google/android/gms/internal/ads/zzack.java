package com.google.android.gms.internal.ads;

import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class zzack implements ViewTreeObserver.OnGlobalLayoutListener {
    private final /* synthetic */ zzace zzcbi;
    private final /* synthetic */ WeakReference zzcbj;

    zzack(zzace zzaceVar, WeakReference weakReference) {
        this.zzcbi = zzaceVar;
        this.zzcbj = weakReference;
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        this.zzcbi.zza((WeakReference<zzaqw>) this.zzcbj, false);
    }
}
