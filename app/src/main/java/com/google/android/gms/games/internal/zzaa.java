package com.google.android.gms.games.internal;

import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;

/* JADX INFO: loaded from: classes.dex */
public final class zzaa extends zzd {
    public static final Parcelable.Creator<zzaa> CREATOR = new b();
    private final Bundle bTL;
    private final IBinder bTM;

    zzaa(Bundle bundle, IBinder iBinder) {
        this.bTL = bundle;
        this.bTM = iBinder;
    }

    public zzaa(e eVar) {
        this.bTL = eVar.adg();
        this.bTM = eVar.bTM;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.bTL, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bTM, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
