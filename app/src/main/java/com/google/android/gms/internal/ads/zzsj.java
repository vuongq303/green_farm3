package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.a;

/* JADX INFO: loaded from: classes.dex */
public final class zzsj implements Parcelable.Creator<zzsi> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzsi createFromParcel(Parcel parcel) {
        int iW = a.w(parcel);
        long jF = 0;
        boolean zC = false;
        String[] strArrY = null;
        String[] strArrY2 = null;
        byte[] bArrQ = null;
        int iD = 0;
        String strN = null;
        boolean zC2 = false;
        while (parcel.dataPosition() < iW) {
            int iV = a.v(parcel);
            switch (a.nT(iV)) {
                case 1:
                    zC2 = a.c(parcel, iV);
                    break;
                case 2:
                    strN = a.n(parcel, iV);
                    break;
                case 3:
                    iD = a.d(parcel, iV);
                    break;
                case 4:
                    bArrQ = a.q(parcel, iV);
                    break;
                case 5:
                    strArrY2 = a.y(parcel, iV);
                    break;
                case 6:
                    strArrY = a.y(parcel, iV);
                    break;
                case 7:
                    zC = a.c(parcel, iV);
                    break;
                case 8:
                    jF = a.f(parcel, iV);
                    break;
                default:
                    a.b(parcel, iV);
                    break;
            }
        }
        a.D(parcel, iW);
        return new zzsi(zC2, strN, iD, bArrQ, strArrY2, strArrY, zC, jF);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzsi[] newArray(int i) {
        return new zzsi[i];
    }
}
