package com.google.android.gms.common.internal;

import android.content.Intent;
import android.support.v4.app.Fragment;

/* JADX INFO: loaded from: classes.dex */
final class bn extends w {
    private final /* synthetic */ Intent bOC;
    private final /* synthetic */ Fragment bw;
    private final /* synthetic */ int val$requestCode;

    bn(Intent intent, Fragment fragment, int i) {
        this.bOC = intent;
        this.bw = fragment;
        this.val$requestCode = i;
    }

    @Override // com.google.android.gms.common.internal.w
    public final void aap() {
        if (this.bOC != null) {
            this.bw.startActivityForResult(this.bOC, this.val$requestCode);
        }
    }
}
