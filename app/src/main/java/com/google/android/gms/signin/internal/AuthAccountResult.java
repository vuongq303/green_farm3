package com.google.android.gms.signin.internal;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.ae;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class AuthAccountResult extends AbstractSafeParcelable implements ae {
    public static final Parcelable.Creator<AuthAccountResult> CREATOR = new a();
    private int bYv;
    private Intent bYw;
    private final int zzal;

    public AuthAccountResult() {
        this(0, null);
    }

    AuthAccountResult(int i, int i2, Intent intent) {
        this.zzal = i;
        this.bYv = i2;
        this.bYw = intent;
    }

    public AuthAccountResult(int i, Intent intent) {
        this(2, i, intent);
    }

    public int agi() {
        return this.bYv;
    }

    public Intent agj() {
        return this.bYw;
    }

    @Override // com.google.android.gms.common.api.ae
    public Status getStatus() {
        return this.bYv == 0 ? Status.bIO : Status.bIS;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, agi());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) agj(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
