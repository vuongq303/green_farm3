package com.google.android.gms.internal.games;

import com.google.android.gms.common.api.c;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.internal.i;
import com.google.android.gms.games.snapshot.SnapshotMetadata;

/* JADX INFO: loaded from: classes.dex */
final class zzcm extends zzcq {
    private final /* synthetic */ SnapshotMetadata zzke;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    zzcm(zzci zzciVar, w wVar, SnapshotMetadata snapshotMetadata) {
        super(wVar, null);
        this.zzke = snapshotMetadata;
    }

    @Override // com.google.android.gms.common.api.a.e
    protected final /* synthetic */ void doExecute(c cVar) {
        ((i) cVar).i(this, this.zzke.getSnapshotId());
    }
}
