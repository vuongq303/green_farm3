package com.google.android.gms.games.internal;

import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class cm implements com.google.android.gms.games.multiplayer.turnbased.f {
    private final String bUZ;
    private final Status bUl;

    cm(Status status, String str) {
        this.bUl = status;
        this.bUZ = str;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }
}
