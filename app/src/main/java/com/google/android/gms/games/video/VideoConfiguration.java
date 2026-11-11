package com.google.android.gms.games.video;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class VideoConfiguration extends AbstractSafeParcelable {
    public static final Parcelable.Creator<VideoConfiguration> CREATOR = new h();
    private final int bXi;
    private final int bXp;
    private final boolean zzsa;

    public VideoConfiguration(int i, int i2, boolean z) {
        bf.ca(Q(i, false));
        bf.ca(R(i2, false));
        this.bXp = i;
        this.bXi = i2;
        this.zzsa = z;
    }

    public static boolean Q(int i, boolean z) {
        switch (i) {
            case -1:
                return z;
            case 0:
            case 1:
            case 2:
            case 3:
                return true;
            default:
                return false;
        }
    }

    public static boolean R(int i, boolean z) {
        switch (i) {
            case -1:
            case 1:
                return z;
            case 0:
                return true;
            default:
                return false;
        }
    }

    public final int afV() {
        return this.bXp;
    }

    public final int afW() {
        return this.bXi;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, afV());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, afW());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, this.zzsa);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
