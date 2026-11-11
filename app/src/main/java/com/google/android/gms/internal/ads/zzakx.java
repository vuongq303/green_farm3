package com.google.android.gms.internal.ads;

import android.view.View;

/* JADX INFO: loaded from: classes.dex */
public class zzakx extends zzakw {
    @Override // com.google.android.gms.internal.ads.zzakq
    public boolean isAttachedToWindow(View view) {
        return super.isAttachedToWindow(view) || view.getWindowId() != null;
    }

    @Override // com.google.android.gms.internal.ads.zzakq
    public final int zzrn() {
        return 14;
    }
}
