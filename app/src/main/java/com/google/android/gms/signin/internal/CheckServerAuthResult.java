package com.google.android.gms.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class CheckServerAuthResult extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CheckServerAuthResult> CREATOR = new c();
    private final boolean bYx;
    private final List<Scope> bYy;
    private final int zzal;

    CheckServerAuthResult(int i, boolean z, List<Scope> list) {
        this.zzal = i;
        this.bYx = z;
        this.bYy = list;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bYx);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.bYy, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
