package com.google.android.gms.internal.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzai extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzai> CREATOR = new zzaj();
    private final String accountType;
    private final byte[] zzbz;
    private final int zzy;

    zzai(int i, String str, byte[] bArr) {
        this.zzy = 1;
        this.accountType = (String) bf.checkNotNull(str);
        this.zzbz = (byte[]) bf.checkNotNull(bArr);
    }

    public zzai(String str, byte[] bArr) {
        this(1, str, bArr);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.zzy);
        c.a(parcel, 2, this.accountType, false);
        c.a(parcel, 3, this.zzbz, false);
        c.G(parcel, iX);
    }
}
