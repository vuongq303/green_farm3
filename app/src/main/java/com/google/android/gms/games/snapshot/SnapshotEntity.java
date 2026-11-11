package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class SnapshotEntity extends zzd implements Snapshot {
    public static final Parcelable.Creator<SnapshotEntity> CREATOR = new j();
    private final SnapshotMetadataEntity bWF;
    private final zza bWG;

    public SnapshotEntity(SnapshotMetadata snapshotMetadata, zza zzaVar) {
        this.bWF = new SnapshotMetadataEntity(snapshotMetadata);
        this.bWG = zzaVar;
    }

    @Override // com.google.android.gms.games.snapshot.Snapshot
    public final SnapshotMetadata afn() {
        return this.bWF;
    }

    @Override // com.google.android.gms.games.snapshot.Snapshot
    public final SnapshotContents afo() {
        if (this.bWG.isClosed()) {
            return null;
        }
        return this.bWG;
    }

    @Override // com.google.android.gms.common.data.k
    /* JADX INFO: renamed from: afq, reason: merged with bridge method [inline-methods] */
    public final Snapshot freeze() {
        return this;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Snapshot) {
            if (this == obj) {
                return true;
            }
            Snapshot snapshot = (Snapshot) obj;
            if (bd.b(snapshot.afn(), afn()) && bd.b(snapshot.afo(), afo())) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return bd.hashCode(afn(), afo());
    }

    public final String toString() {
        return bd.C(this).c("Metadata", afn()).c("HasContents", Boolean.valueOf(afo() != null)).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) afn(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) afo(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
