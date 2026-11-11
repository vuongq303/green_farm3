package com.google.android.gms.games.video;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.games.internal.zzd;

/* JADX INFO: loaded from: classes.dex */
public final class VideoCapabilities extends zzd {
    public static final Parcelable.Creator<VideoCapabilities> CREATOR = new g();
    private final boolean bNx;
    private final boolean bXl;
    private final boolean bXm;
    private final boolean[] bXn;
    private final boolean[] bXo;

    public VideoCapabilities(boolean z, boolean z2, boolean z3, boolean[] zArr, boolean[] zArr2) {
        this.bXl = z;
        this.bNx = z2;
        this.bXm = z3;
        this.bXn = zArr;
        this.bXo = zArr2;
    }

    public final boolean afQ() {
        return this.bNx;
    }

    public final boolean afR() {
        return this.bXl;
    }

    public final boolean afS() {
        return this.bXm;
    }

    public final boolean[] afT() {
        return this.bXn;
    }

    public final boolean[] afU() {
        return this.bXo;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof VideoCapabilities)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        VideoCapabilities videoCapabilities = (VideoCapabilities) obj;
        return bd.b(videoCapabilities.afT(), afT()) && bd.b(videoCapabilities.afU(), afU()) && bd.b(Boolean.valueOf(videoCapabilities.afR()), Boolean.valueOf(afR())) && bd.b(Boolean.valueOf(videoCapabilities.afQ()), Boolean.valueOf(afQ())) && bd.b(Boolean.valueOf(videoCapabilities.afS()), Boolean.valueOf(afS()));
    }

    public final int hashCode() {
        return bd.hashCode(afT(), afU(), Boolean.valueOf(afR()), Boolean.valueOf(afQ()), Boolean.valueOf(afS()));
    }

    public final String toString() {
        return bd.C(this).c("SupportedCaptureModes", afT()).c("SupportedQualityLevels", afU()).c("CameraSupported", Boolean.valueOf(afR())).c("MicSupported", Boolean.valueOf(afQ())).c("StorageWriteSupported", Boolean.valueOf(afS())).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, afR());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, afQ());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, afS());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, afT(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, afU(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
