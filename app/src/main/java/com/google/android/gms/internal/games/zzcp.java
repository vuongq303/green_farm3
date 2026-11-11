package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.e;

/* JADX INFO: loaded from: classes.dex */
final class zzcp implements e {
    private final /* synthetic */ Status zzbc;

    zzcp(zzco zzcoVar, Status status) {
        this.zzbc = status;
    }

    public final SnapshotMetadata getSnapshotMetadata() {
        return null;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.zzbc;
    }
}
