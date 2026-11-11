package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzsh implements Parcelable.Creator<zzsg> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzsg createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String[] strArrY = null;
        String[] strArrY2 = null;
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    strN = a.n(parcel, iV);
                    break;
                case 2:
                    strArrY2 = a.y(parcel, iV);
                    break;
                case 3:
                    strArrY = a.y(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzsg(strN, strArrY2, strArrY);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzsg[] newArray(int i) {
        return new zzsg[i];
    }
}
