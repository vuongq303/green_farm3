package com.google.android.gms.internal.games;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.gms.common.api.aa;
import com.google.android.gms.common.api.w;
import com.google.android.gms.games.c;
import com.google.android.gms.games.snapshot.Snapshot;
import com.google.android.gms.games.snapshot.SnapshotContents;
import com.google.android.gms.games.snapshot.SnapshotMetadata;
import com.google.android.gms.games.snapshot.b;
import com.google.android.gms.games.snapshot.d;
import com.google.android.gms.games.snapshot.e;
import com.google.android.gms.games.snapshot.f;
import com.google.android.gms.games.snapshot.g;
import com.google.android.gms.games.snapshot.h;

/* JADX INFO: loaded from: classes.dex */
public final class zzci implements d {
    public final aa<e> commitAndClose(w wVar, Snapshot snapshot, b bVar) {
        return wVar.b(new zzcl(this, wVar, snapshot, bVar));
    }

    public final aa<f> delete(w wVar, SnapshotMetadata snapshotMetadata) {
        return wVar.b(new zzcm(this, wVar, snapshotMetadata));
    }

    public final void discardAndClose(w wVar, Snapshot snapshot) {
        c.a(wVar).b(snapshot);
    }

    public final int getMaxCoverImageSize(w wVar) {
        return c.a(wVar).adE();
    }

    public final int getMaxDataSize(w wVar) {
        return c.a(wVar).adC();
    }

    public final Intent getSelectSnapshotIntent(w wVar, String str, boolean z, boolean z2, int i) {
        return c.a(wVar).b(str, z, z2, i);
    }

    public final SnapshotMetadata getSnapshotFromBundle(Bundle bundle) {
        if (bundle == null || !bundle.containsKey("com.google.android.gms.games.SNAPSHOT_METADATA")) {
            return null;
        }
        return (SnapshotMetadata) bundle.getParcelable("com.google.android.gms.games.SNAPSHOT_METADATA");
    }

    public final aa<g> load(w wVar, boolean z) {
        return wVar.a(new zzcj(this, wVar, z));
    }

    public final aa<h> open(w wVar, SnapshotMetadata snapshotMetadata) {
        return open(wVar, snapshotMetadata.afv(), false);
    }

    public final aa<h> open(w wVar, SnapshotMetadata snapshotMetadata, int i) {
        return open(wVar, snapshotMetadata.afv(), false, i);
    }

    public final aa<h> open(w wVar, String str, boolean z) {
        return open(wVar, str, z, -1);
    }

    public final aa<h> open(w wVar, String str, boolean z, int i) {
        return wVar.b(new zzck(this, wVar, str, z, i));
    }

    public final aa<h> resolveConflict(w wVar, String str, Snapshot snapshot) {
        SnapshotMetadata snapshotMetadataAfn = snapshot.afn();
        return resolveConflict(wVar, str, snapshotMetadataAfn.getSnapshotId(), new com.google.android.gms.games.snapshot.c().a(snapshotMetadataAfn).afB(), snapshot.afo());
    }

    public final aa<h> resolveConflict(w wVar, String str, String str2, b bVar, SnapshotContents snapshotContents) {
        return wVar.b(new zzcn(this, wVar, str, str2, bVar, snapshotContents));
    }
}
