package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzanh implements Parcelable.Creator<zzang> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzang createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        String strN = null;
        boolean zC = false;
        boolean zC2 = false;
        int iD = 0;
        int iD2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                case 3:
                    iD2 = a.d(parcel, iV);
                    break;
                case 4:
                    iD = a.d(parcel, iV);
                    break;
                case 5:
                    zC2 = a.c(parcel, iV);
                    break;
                case 6:
                    zC = a.c(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzang(strN, iD2, iD, zC2, zC);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzang[] newArray(int i) {
        return new zzang[i];
    }
}
