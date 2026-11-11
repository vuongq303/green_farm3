package com.google.android.gms.internal.ads;

import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzms extends zzjn {
    public zzms(zzjn zzjnVar) {
        super(zzjnVar.zzarb, zzjnVar.height, zzjnVar.heightPixels, zzjnVar.zzarc, zzjnVar.width, zzjnVar.widthPixels, zzjnVar.zzard, zzjnVar.zzare, zzjnVar.zzarf, zzjnVar.zzarg);
    }

    @Override // com.google.android.gms.internal.ads.zzjn, android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.zzarb, false);
        c.c(parcel, 3, this.height);
        c.c(parcel, 6, this.width);
        c.G(parcel, iX);
    }
}
