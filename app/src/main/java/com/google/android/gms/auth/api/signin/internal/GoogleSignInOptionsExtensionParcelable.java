package com.google.android.gms.auth.api.signin.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class GoogleSignInOptionsExtensionParcelable extends AbstractSafeParcelable {
    public static final Parcelable.Creator<GoogleSignInOptionsExtensionParcelable> CREATOR = new a();
    private int bGX;
    private Bundle bGY;
    private final int versionCode;

    GoogleSignInOptionsExtensionParcelable(int i, int i2, Bundle bundle) {
        this.versionCode = i;
        this.bGX = i2;
        this.bGY = bundle;
    }

    public Bundle getBundle() {
        return this.bGY;
    }

    public int getType() {
        return this.bGX;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.versionCode);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, getType());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, getBundle(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
