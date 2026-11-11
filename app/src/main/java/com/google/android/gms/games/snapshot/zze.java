package com.google.android.gms.games.snapshot;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class zze extends zzd implements b {
    public static final Parcelable.Creator<zze> CREATOR = new k();
    private final Long bWJ;
    private final Uri bWL;
    private final Long bWU;
    private BitmapTeleporter bWV;
    private final String description;

    zze() {
        this(null, null, null, null, null);
    }

    zze(String str, Long l, BitmapTeleporter bitmapTeleporter, Uri uri, Long l2) {
        this.description = str;
        this.bWU = l;
        this.bWV = bitmapTeleporter;
        this.bWL = uri;
        this.bWJ = l2;
        if (this.bWV != null) {
            bf.a(this.bWL == null, "Cannot set both a URI and an image");
        } else if (this.bWL != null) {
            bf.a(this.bWV == null, "Cannot set both a URI and an image");
        }
    }

    @Override // com.google.android.gms.games.snapshot.b
    public final BitmapTeleporter afA() {
        return this.bWV;
    }

    public final Long afD() {
        return this.bWU;
    }

    public final Long afE() {
        return this.bWJ;
    }

    public final String getDescription() {
        return this.description;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, getDescription(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, afD(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) this.bWL, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, (Parcelable) this.bWV, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, afE(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
