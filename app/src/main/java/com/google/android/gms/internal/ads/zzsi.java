package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzsi extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzsi> CREATOR = new zzsj();
    public final byte[] data;
    public final int statusCode;
    public final boolean zzac;
    public final long zzad;
    public final String[] zzbnh;
    public final String[] zzbni;
    public final boolean zzbnj;
    public final String zzbnk;

    zzsi(boolean z, String str, int i, byte[] bArr, String[] strArr, String[] strArr2, boolean z2, long j) {
        this.zzbnj = z;
        this.zzbnk = str;
        this.statusCode = i;
        this.data = bArr;
        this.zzbnh = strArr;
        this.zzbni = strArr2;
        this.zzac = z2;
        this.zzad = j;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 1, this.zzbnj);
        c.a(parcel, 2, this.zzbnk, false);
        c.c(parcel, 3, this.statusCode);
        c.a(parcel, 4, this.data, false);
        c.a(parcel, 5, this.zzbnh, false);
        c.a(parcel, 6, this.zzbni, false);
        c.a(parcel, 7, this.zzac);
        c.a(parcel, 8, this.zzad);
        c.G(parcel, iX);
    }
}
