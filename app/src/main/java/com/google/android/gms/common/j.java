package com.google.android.gms.common;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class j implements Parcelable.Creator<GoogleCertificatesQuery> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: m, reason: merged with bridge method [inline-methods] */
    public GoogleCertificatesQuery createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        boolean zC = false;
        IBinder iBinderO = null;
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    iBinderO = com.google.android.gms.common.internal.safeparcel.a.o(parcel, iV);
                    break;
                case 3:
                    zC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new GoogleCertificatesQuery(strN, iBinderO, zC);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nu, reason: merged with bridge method [inline-methods] */
    public GoogleCertificatesQuery[] newArray(int i) {
        return new GoogleCertificatesQuery[i];
    }
}
