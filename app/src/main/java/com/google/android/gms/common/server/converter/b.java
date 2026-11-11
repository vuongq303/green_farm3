package com.google.android.gms.common.server.converter;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.ArrayList;

/* JADX INFO: loaded from: classes.dex */
public class b implements Parcelable.Creator<StringToIntConverter> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nV, reason: merged with bridge method [inline-methods] */
    public StringToIntConverter[] newArray(int i) {
        return new StringToIntConverter[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: z, reason: merged with bridge method [inline-methods] */
    public StringToIntConverter createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        int iD = 0;
        ArrayList arrayListC = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    iD = com.google.android.gms.common.internal.safeparcel.a.d(parcel, iV);
                    break;
                case 2:
                    arrayListC = com.google.android.gms.common.internal.safeparcel.a.c(parcel, iV, StringToIntConverter.Entry.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new StringToIntConverter(iD, arrayListC);
    }
}
