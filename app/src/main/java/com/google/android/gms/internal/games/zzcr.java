package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.snapshot.f;

/* JADX INFO: loaded from: classes.dex */
final class zzcr implements f {
    private final /* synthetic */ Status zzbc;

    zzcr(zzcq zzcqVar, Status status) {
        this.zzbc = status;
    }

    public final String getSnapshotId() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
