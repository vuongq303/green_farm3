package com.google.android.gms.games.internal;

import android.os.Bundle;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class aa implements com.google.android.gms.games.multiplayer.turnbased.j {
    private final Status bUl;
    private final com.google.android.gms.games.multiplayer.turnbased.a bUm;

    aa(Status status, Bundle bundle) {
        this.bUl = status;
        this.bUm = new com.google.android.gms.games.multiplayer.turnbased.a(bundle);
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.bUl;
    }

    @Override // com.google.android.gms.common.api.ac
    public final void release() {
        this.bUm.release();
    }
}
