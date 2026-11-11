package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzzu implements Parcelable.Creator<zzzt> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzzt createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        int iD2 = 0;
        int iD3 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD3 = a.d(parcel, iV);
                    break;
                case 2:
                    iD2 = a.d(parcel, iV);
                    break;
                case 3:
                    iD = a.d(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzzt(iD3, iD2, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzzt[] newArray(int i) {
        return new zzzt[i];
    }
}
