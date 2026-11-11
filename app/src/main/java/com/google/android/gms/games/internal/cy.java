package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class cy implements com.google.android.gms.games.snapshot.f {
    private final Status bUl;
    private final String bVg;

    cy(int i, String str) {
        this.bUl = com.google.android.gms.games.n.og(i);
        this.bVg = str;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
