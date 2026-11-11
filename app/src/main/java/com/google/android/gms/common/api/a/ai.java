package com.google.android.gms.common.api.a;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class ai implements com.google.android.gms.common.api.ab {
    private final /* synthetic */ g bJY;
    private final /* synthetic */ ah bJZ;

    ai(ah ahVar, g gVar) {
        this.bJZ = ahVar;
        this.bJY = gVar;
    }

    @Override // com.google.android.gms.common.api.ab
    public final void a(Status status) {
        this.bJZ.bJW.remove(this.bJY);
    }
}
