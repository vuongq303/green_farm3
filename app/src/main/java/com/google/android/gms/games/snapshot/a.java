package com.google.android.gms.games.snapshot;

import com.google.android.gms.common.data.DataHolder;

/* JADX INFO: loaded from: classes.dex */
public final class a extends com.google.android.gms.common.data.a<SnapshotMetadata> {
    public a(DataHolder dataHolder) {
        super(dataHolder);
    }

    @Override // com.google.android.gms.common.data.c
    /* JADX INFO: renamed from: op, reason: merged with bridge method [inline-methods] */
    public final SnapshotMetadata get(int i) {
        return new SnapshotMetadataRef(this.mDataHolder, i);
    }
}
