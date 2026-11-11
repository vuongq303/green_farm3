package com.google.android.gms.common.internal;

import android.app.Activity;
import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class bm extends w {
    private final /* synthetic */ Intent bOC;
    private final /* synthetic */ Activity val$activity;
    private final /* synthetic */ int val$requestCode;

    bm(Intent intent, Activity activity, int i) {
        this.bOC = intent;
        this.val$activity = activity;
        this.val$requestCode = i;
    }

    @Override // com.google.android.gms.common.internal.w
    public final void aap() {
        if (this.bOC != null) {
            this.val$activity.startActivityForResult(this.bOC, this.val$requestCode);
        }
    }
}
