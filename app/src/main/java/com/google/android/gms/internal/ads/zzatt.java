package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzatt extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzatt> CREATOR = new zzatu();
    private final String packageName;
    private final int versionCode;
    private final String zzdhd;

    zzatt(int i, String str, String str2) {
        this.versionCode = i;
        this.packageName = str;
        this.zzdhd = str2;
    }

    public zzatt(String str, String str2) {
        this(1, str, str2);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.versionCode);
        c.a(parcel, 2, this.packageName, false);
        c.a(parcel, 3, this.zzdhd, false);
        c.G(parcel, iX);
    }
}
