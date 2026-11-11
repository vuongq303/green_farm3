package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class c implements Parcelable.Creator<zzr> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        ArrayList<String> arrayListA = null;
        ArrayList<String> arrayListA2 = null;
        ArrayList<String> arrayListA3 = null;
        ArrayList<String> arrayListA4 = null;
        ArrayList<String> arrayListA5 = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    arrayListA5 = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                case 3:
                    arrayListA4 = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                case 4:
                    arrayListA3 = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                case 5:
                    arrayListA2 = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                case 6:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new zzr(iD, arrayListA5, arrayListA4, arrayListA3, arrayListA2, arrayListA);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzr[] newArray(int i) {
        return new zzr[i];
    }
}
