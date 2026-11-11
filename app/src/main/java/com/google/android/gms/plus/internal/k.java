package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;

/* JADX INFO: loaded from: classes.dex */
public final class k implements Parcelable.Creator<zzn> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzn createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        PlusCommonExtras plusCommonExtras = null;
        String strN = null;
        String strN2 = null;
        String strN3 = null;
        String strN4 = null;
        String[] strArrY = null;
        String[] strArrY2 = null;
        String[] strArrY3 = null;
        String strN5 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN5 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    strArrY3 = com.google.android.gms.common.internal.safeparcel.a.y(parcel, iV);
                    break;
                case 3:
                    strArrY2 = com.google.android.gms.common.internal.safeparcel.a.y(parcel, iV);
                    break;
                case 4:
                    strArrY = com.google.android.gms.common.internal.safeparcel.a.y(parcel, iV);
                    break;
                case 5:
                    strN4 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 6:
                    strN3 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 7:
                    strN2 = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 8:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 9:
                    plusCommonExtras = (PlusCommonExtras) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, PlusCommonExtras.CREATOR);
                    break;
                case Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS /* 1000 */:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new zzn(iD, strN5, strArrY3, strArrY2, strArrY, strN4, strN3, strN2, strN, plusCommonExtras);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzn[] newArray(int i) {
        return new zzn[i];
    }
}
