package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzzt extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzzt> CREATOR = new zzzu();
    private final int major;
    private final int minor;
    private final int zzbvo;

    zzzt(int i, int i2, int i3) {
        this.major = i;
        this.minor = i2;
        this.zzbvo = i3;
    }

    public static zzzt zza(zzatk zzatkVar) {
        return new zzzt(zzatkVar.zzdgt, zzatkVar.zzdgu, zzatkVar.zzdgv);
    }

    public final String toString() {
        int i = this.major;
        int i2 = this.minor;
        return new StringBuilder(35).append(i).append(".").append(i2).append(".").append(this.zzbvo).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.major);
        c.c(parcel, 2, this.minor);
        c.c(parcel, 3, this.zzbvo);
        c.G(parcel, iX);
    }
}
