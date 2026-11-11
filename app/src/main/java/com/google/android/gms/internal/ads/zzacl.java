package com.google.android.gms.internal.ads;

import android.view.ViewTreeObserver;
import java.lang.ref.WeakReference;

/* JADX INFO: loaded from: classes.dex */
final class zzacl implements ViewTreeObserver.OnScrollChangedListener {
    private final /* synthetic */ zzace zzcbi;
    private final /* synthetic */ WeakReference zzcbj;

    zzacl(zzace zzaceVar, WeakReference weakReference) {
        this.zzcbi = zzaceVar;
        this.zzcbj = weakReference;
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        this.zzcbi.zza((WeakReference<zzaqw>) this.zzcbj, true);
    }
}
