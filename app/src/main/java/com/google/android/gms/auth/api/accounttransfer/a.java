package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;
import java.util.HashSet;

/* JADX INFO: loaded from: classes.dex */
public final class a implements Parcelable.Creator<zzo> {
    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzo createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        HashSet hashSet = new HashSet();
        zzr zzrVar = null;
        int iD = 0;
        ArrayList arrayListC = null;
        int iD2 = 0;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD2 = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    hashSet.add(1);
                    break;
                case 2:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, zzu.CREATOR);
                    hashSet.add(2);
                    break;
                case 3:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    hashSet.add(3);
                    break;
                case 4:
                    zzr zzrVar2 = (zzr) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, zzr.CREATOR);
                    hashSet.add(4);
                    zzrVar = zzrVar2;
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        if (parcel.dataPosition() != iW) {
            throw new com.google.android.gms.common.internal.safeparcel.b(new StringBuilder(37).append("Overread allowed size end=").append(iW).toString(), parcel);
        }
        return new zzo(hashSet, iD2, arrayListC, iD, zzrVar);
    }

    @Override // android.os.Parcelable.Creator
    public final /* synthetic */ zzo[] newArray(int i) {
        return new zzo[i];
    }
}
