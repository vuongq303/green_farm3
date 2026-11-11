package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ConnectionInfo extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ConnectionInfo> CREATOR = new v();
    private Bundle bNK;
    private Feature[] bNL;

    public ConnectionInfo() {
    }

    ConnectionInfo(Bundle bundle, Feature[] featureArr) {
        this.bNK = bundle;
        this.bNL = featureArr;
    }

    public Bundle aao() {
        return this.bNK;
    }

    public Feature[] getAvailableFeatures() {
        return this.bNL;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.bNK, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable[]) this.bNL, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
