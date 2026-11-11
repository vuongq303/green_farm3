package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzafk implements Parcelable.Creator<zzafj> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzafj createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzafj(strN);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzafj[] newArray(int i) {
        return new zzafj[i];
    }
}
