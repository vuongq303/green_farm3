package com.google.android.gms.ads.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.ads.zzadh;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaq extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzaq> CREATOR = new zzar();
    private final String bBy;
    public final boolean zzze;
    public final boolean zzzf;
    public final boolean zzzh;
    public final float zzzi;
    public final int zzzj;
    public final boolean zzzk;
    public final boolean zzzl;
    public final boolean zzzm;

    zzaq(boolean z, boolean z2, String str, boolean z3, float f, int i, boolean z4, boolean z5, boolean z6) {
        this.zzze = z;
        this.zzzf = z2;
        this.bBy = str;
        this.zzzh = z3;
        this.zzzi = f;
        this.zzzj = i;
        this.zzzk = z4;
        this.zzzl = z5;
        this.zzzm = z6;
    }

    public zzaq(boolean z, boolean z2, boolean z3, float f, int i, boolean z4, boolean z5, boolean z6) {
        this(z, z2, null, z3, f, i, z4, z5, z6);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.zzze);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.zzzf);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bBy, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.zzzh);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, this.zzzi);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 7, this.zzzj);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.zzzk);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, this.zzzl);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 10, this.zzzm);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
