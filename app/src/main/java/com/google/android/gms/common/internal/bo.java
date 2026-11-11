package com.google.android.gms.common.internal;

import android.content.Intent;

/* JADX INFO: loaded from: classes.dex */
final class bo extends w {
    private final /* synthetic */ Intent bOC;
    private final /* synthetic */ com.google.android.gms.common.api.a.u bOD;
    private final /* synthetic */ int val$requestCode;

    bo(Intent intent, com.google.android.gms.common.api.a.u uVar, int i) {
        this.bOC = intent;
        this.bOD = uVar;
        this.val$requestCode = i;
    }

    @Override // com.google.android.gms.common.internal.w
    public final void aap() {
        if (this.bOC != null) {
            this.bOD.startActivityForResult(this.bOC, this.val$requestCode);
        }
    }
}
