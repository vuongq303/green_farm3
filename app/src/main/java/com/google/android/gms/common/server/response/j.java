package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class j implements Parcelable.Creator<SafeParcelResponse> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: F, reason: merged with bridge method [inline-methods] */
    public SafeParcelResponse createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        FieldMappingDictionary fieldMappingDictionary = null;
        Parcel parcelB = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    parcelB = com.google.android.gms.common.internal.safeparcel.a.B(parcel, iV);
                    break;
                case 3:
                    fieldMappingDictionary = (FieldMappingDictionary) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, FieldMappingDictionary.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new SafeParcelResponse(iD, parcelB, fieldMappingDictionary);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: ob, reason: merged with bridge method [inline-methods] */
    public SafeParcelResponse[] newArray(int i) {
        return new SafeParcelResponse[i];
    }
}
