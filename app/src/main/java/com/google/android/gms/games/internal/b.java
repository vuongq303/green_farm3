package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class b implements Parcelable.Creator<zzaa> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaa createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        IBinder iBinderO = null;
        Bundle bundleP = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case 2:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new zzaa(bundleP, iBinderO);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzaa[] newArray(int i) {
        return new zzaa[i];
    }
}
