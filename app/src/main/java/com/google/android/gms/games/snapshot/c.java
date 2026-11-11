package com.google.android.gms.games.snapshot;

import android.net.Uri;
import com.google.android.gms.common.data.BitmapTeleporter;

/* JADX INFO: loaded from: classes.dex */
public final class c {
    private Long bWI;
    private Long bWJ;
    private BitmapTeleporter bWK;
    private Uri bWL;
    private String description;

    public final c a(SnapshotMetadata snapshotMetadata) {
        this.description = snapshotMetadata.getDescription();
        this.bWI = Long.valueOf(snapshotMetadata.afx());
        this.bWJ = Long.valueOf(snapshotMetadata.afz());
        if (this.bWI.longValue() == -1) {
            this.bWI = null;
        }
        this.bWL = snapshotMetadata.afs();
        if (this.bWL != null) {
            this.bWK = null;
        }
        return this;
    }

    public final b afB() {
        return new zze(this.description, this.bWI, this.bWK, this.bWL, this.bWJ);
    }
}
