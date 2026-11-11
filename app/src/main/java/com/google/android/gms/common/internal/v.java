package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;

/* JADX INFO: loaded from: classes.dex */
public class v implements Parcelable.Creator<ConnectionInfo> {
    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: nL, reason: merged with bridge method [inline-methods] */
    public ConnectionInfo[] newArray(int i) {
        return new ConnectionInfo[i];
    }

    @Override // android.os.Parcelable.Creator
    /* JADX INFO: renamed from: q, reason: merged with bridge method [inline-methods] */
    public ConnectionInfo createFromParcel(Parcel parcel) {
        int iW = com.google.android.gms.common.internal.safeparcel.a.w(parcel);
        Feature[] featureArr = null;
        Bundle bundleP = null;
        while (parcel.dataPosition() < iW) {
            int iV = com.google.android.gms.common.internal.safeparcel.a.v(parcel);
            switch (com.google.android.gms.common.internal.safeparcel.a.nT(iV)) {
                case 1:
                    bundleP = com.google.android.gms.common.internal.safeparcel.a.p(parcel, iV);
                    break;
                case 2:
                    featureArr = (Feature[]) com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV, Feature.CREATOR);
                    break;
                default:
                    com.google.android.gms.common.internal.safeparcel.a.b(parcel, iV);
                    break;
            }
        }
        com.google.android.gms.common.internal.safeparcel.a.D(parcel, iW);
        return new ConnectionInfo(bundleP, featureArr);
    }
}
