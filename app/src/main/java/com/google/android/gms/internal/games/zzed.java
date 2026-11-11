package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.video.c;

/* JADX INFO: loaded from: classes.dex */
final class zzed implements c {
    private final /* synthetic */ Status zzbc;

    zzed(zzec zzecVar, Status status) {
        this.zzbc = status;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }

    public final boolean isAvailable() {
        return false;
    }
}
