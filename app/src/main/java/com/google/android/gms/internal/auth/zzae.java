package com.google.android.gms.internal.auth;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzae extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzae> CREATOR = new zzaf();
    private final String accountType;
    private final int zzcg;
    private final int zzy;

    zzae(int i, String str, int i2) {
        this.zzy = 1;
        this.accountType = (String) bf.checkNotNull(str);
        this.zzcg = i2;
    }

    public zzae(String str, int i) {
        this(1, str, i);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.zzy);
        c.a(parcel, 2, this.accountType, false);
        c.c(parcel, 3, this.zzcg);
        c.G(parcel, iX);
    }
}
