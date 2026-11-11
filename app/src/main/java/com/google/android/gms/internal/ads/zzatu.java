package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzatu implements Parcelable.Creator<zzatt> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzatt createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        String strN = null;
        String strN2 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    break;
                case 2:
                    strN2 = a.n(parcel, iV);
                    break;
                case 3:
                    strN = a.n(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzatt(iD, strN2, strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzatt[] newArray(int i) {
        return new zzatt[i];
    }
}
