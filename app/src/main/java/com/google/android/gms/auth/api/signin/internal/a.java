package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class a implements Parcelable.Creator<GoogleSignInOptionsExtensionParcelable> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: j, reason: merged with bridge method [inline-methods] */
    public GoogleSignInOptionsExtensionParcelable createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        Bundle bundleP = null;
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
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new GoogleSignInOptionsExtensionParcelable(iD2, iD, bundleP);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nn, reason: merged with bridge method [inline-methods] */
    public GoogleSignInOptionsExtensionParcelable[] newArray(int i) {
        return new GoogleSignInOptionsExtensionParcelable[i];
    }
}
