package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzhm implements Parcelable.Creator<zzhl> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzhl createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        boolean zC = false;
        long jF = 0;
        Bundle bundleP = null;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        long jF2 = 0;
        String strN4 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN4 = a.n(parcel, iV);
                    break;
                case 3:
                    jF2 = a.f(parcel, iV);
                    break;
                case 4:
                    strN3 = a.n(parcel, iV);
                    break;
                case 5:
                    strN2 = a.n(parcel, iV);
                    break;
                case 6:
                    strN = a.n(parcel, iV);
                    break;
                case 7:
                    bundleP = a.p(parcel, iV);
                    break;
                case 8:
                    zC = a.c(parcel, iV);
                    break;
                case 9:
                    jF = a.f(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzhl(strN4, jF2, strN3, strN2, strN, bundleP, zC, jF);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzhl[] newArray(int i) {
        return new zzhl[i];
    }
}
