package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class co implements com.google.android.gms.games.video.c {
    private final Status bUl;
    private final boolean bVa;

    co(Status status, boolean z) {
        this.bUl = status;
        this.bVa = z;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
