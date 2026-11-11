package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class df implements com.google.android.gms.games.f {
    private final Status bUl;
    private final String bVi;

    df(Status status, String str) {
        this.bUl = status;
        this.bVi = str;
    }

    @Override // com.google.android.gms.games.f
    public final String acp() {
        return this.bVi;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
