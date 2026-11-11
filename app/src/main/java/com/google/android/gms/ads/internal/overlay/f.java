package com.google.android.gms.ads.internal.overlay;

import android.graphics.drawable.Drawable;

/* JADX INFO: loaded from: classes.dex */
final class f implements Runnable {
    private final /* synthetic */ Drawable bAJ;
    private final /* synthetic */ e bAK;

    f(e eVar, Drawable drawable) {
        this.bAK = eVar;
        this.bAJ = drawable;
    }

    @Override // java.lang.Runnable
    public final void run() {
        this.bAK.bAG.mActivity.getWindow().setBackgroundDrawable(this.bAJ);
    }
}
