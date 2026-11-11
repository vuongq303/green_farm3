package com.google.android.gms.games.internal;

import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotEntity;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.SnapshotMetadataEntity;
import com.google.android.gms.games.snapshot.zza;

/* JADX INFO: loaded from: classes.dex */
final class an extends dc implements com.google.android.gms.games.snapshot.h {
    private final SnapshotContents bUA;
    private final Snapshot bUx;
    private final String bUy;
    private final Snapshot bUz;

    an(DataHolder dataHolder, Contents contents) {
        this(dataHolder, null, contents, null, null);
    }

    an(DataHolder dataHolder, String str, Contents contents, Contents contents2, Contents contents3) {
        super(dataHolder);
        com.google.android.gms.games.snapshot.a aVar = new com.google.android.gms.games.snapshot.a(dataHolder);
        try {
            if (aVar.getCount() == 0) {
                this.bUx = null;
                this.bUz = null;
            } else if (aVar.getCount() == 1) {
                com.google.android.gms.common.internal.c.bZ(dataHolder.getStatusCode() != 4004);
                this.bUx = new SnapshotEntity(new SnapshotMetadataEntity((SnapshotMetadata) aVar.get(0)), new zza(contents));
                this.bUz = null;
            } else {
                this.bUx = new SnapshotEntity(new SnapshotMetadataEntity((SnapshotMetadata) aVar.get(0)), new zza(contents));
                this.bUz = new SnapshotEntity(new SnapshotMetadataEntity((SnapshotMetadata) aVar.get(1)), new zza(contents2));
            }
            aVar.release();
            this.bUy = str;
            this.bUA = new zza(contents3);
        } catch (Throwable th) {
            aVar.release();
            throw th;
        }
    }
}
