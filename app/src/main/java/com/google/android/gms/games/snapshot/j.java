package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class j implements Parcelable.Creator<SnapshotEntity> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ SnapshotEntity createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        zza zzaVar = null;
        SnapshotMetadataEntity snapshotMetadataEntity = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    snapshotMetadataEntity = (SnapshotMetadataEntity) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, SnapshotMetadataEntity.CREATOR);
                    break;
                case 2:
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
                case 3:
                    zzaVar = (zza) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, zza.CREATOR);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new SnapshotEntity(snapshotMetadataEntity, zzaVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ SnapshotEntity[] newArray(int i) {
        return new SnapshotEntity[i];
    }
}
