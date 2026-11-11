package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<AuthAccountResult> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: M, reason: merged with bridge method [inline-methods] */
    public AuthAccountResult createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        Intent intent = null;
        int iD = 0;
        int iD2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 3:
                    intent = (Intent) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, Intent.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new AuthAccountResult(iD2, iD, intent);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: or, reason: merged with bridge method [inline-methods] */
    public AuthAccountResult[] newArray(int i) {
        return new AuthAccountResult[i];
    }
}
