package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataEntity;

/* JADX INFO: loaded from: classes.dex */
final class cw extends dc implements com.google.android.gms.games.snapshot.e {
    private final SnapshotMetadata bVf;

    cw(DataHolder dataHolder) {
        super(dataHolder);
        com.google.android.gms.games.snapshot.a aVar = new com.google.android.gms.games.snapshot.a(dataHolder);
        try {
            if (aVar.getCount() > 0) {
                this.bVf = new SnapshotMetadataEntity((SnapshotMetadata) aVar.get(0));
            } else {
                this.bVf = null;
            }
        } finally {
            aVar.release();
        }
    }
}
