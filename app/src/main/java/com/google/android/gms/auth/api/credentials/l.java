package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public final class l implements Parcelable.Creator<PasswordSpecification> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PasswordSpecification createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        int iD2 = 0;
        ArrayList<Integer> arrayListZ = null;
        ArrayList<String> arrayListA = null;
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 2:
                    arrayListA = com.google.android.gms.common.internal.safeparcel.a.A(parcel, iV);
                    break;
                case 3:
                    arrayListZ = com.google.android.gms.common.internal.safeparcel.a.z(parcel, iV);
                    break;
                case 4:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 5:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new PasswordSpecification(strN, arrayListA, arrayListZ, iD2, iD);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ PasswordSpecification[] newArray(int i) {
        return new PasswordSpecification[i];
    }
}
