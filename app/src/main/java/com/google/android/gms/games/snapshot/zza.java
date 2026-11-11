package com.google.android.gms.games.snapshot;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.drive.Contents;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class zza extends zzd implements SnapshotContents {
    private Contents bWT;
    private static final Object bWS = new Object();
    public static final Parcelable.Creator<zza> CREATOR = new i();

    public zza(Contents contents) {
        this.bWT = contents;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final Contents afp() {
        return this.bWT;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final void close() {
        this.bWT = null;
    }

    @Override // com.google.android.gms.games.snapshot.SnapshotContents
    public final boolean isClosed() {
        return this.bWT == null;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) this.bWT, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
