package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzjt implements Parcelable.Creator<zzjs> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzjs createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        int iD = 0;
        long jF = 0;
        Double dL = null;
        String strN = null;
        String strN2 = null;
        Float fJ = null;
        Long lG = null;
        String strN3 = null;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    iD = a.d(parcel, iV);
                    break;
                case 2:
                    strN3 = a.n(parcel, iV);
                    break;
                case 3:
                    jF = a.f(parcel, iV);
                    break;
                case 4:
                    lG = a.g(parcel, iV);
                    break;
                case 5:
                    fJ = a.j(parcel, iV);
                    break;
                case 6:
                    strN2 = a.n(parcel, iV);
                    break;
                case 7:
                    strN = a.n(parcel, iV);
                    break;
                case 8:
                    dL = a.l(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzjs(iD, strN3, jF, lG, fJ, strN2, strN, dL);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzjs[] newArray(int i) {
        return new zzjs[i];
    }
}
