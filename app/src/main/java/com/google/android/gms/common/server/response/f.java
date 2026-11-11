package com.google.android.gms.common.server.response;

import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public class f implements Parcelable.Creator<FieldMappingDictionary.FieldMapPair> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: C, reason: merged with bridge method [inline-methods] */
    public FieldMappingDictionary.FieldMapPair createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        FastJsonResponse.Field field = null;
        String strN = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    strN = com.google.android.gms.common.internal.safeparcel.a.n(parcel, iV);
                    break;
                case 3:
                    field = (FastJsonResponse.Field) com.google.android.gms.common.internal.safeparcel.a.a(parcel, iV, FastJsonResponse.Field.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new FieldMappingDictionary.FieldMapPair(iD, strN, field);
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nY, reason: merged with bridge method [inline-methods] */
    public FieldMappingDictionary.FieldMapPair[] newArray(int i) {
        return new FieldMappingDictionary.FieldMapPair[i];
    }
}
