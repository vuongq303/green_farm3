package com.google.android.gms.common.api;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class Scope extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<Scope> CREATOR = new am();
    private final String bIN;
    private final int zzal;

    Scope(int i, String str) {
        bf.d(str, (Object) "scopeUri must not be null or empty");
        this.zzal = i;
        this.bIN = str;
    }

    public Scope(String str) {
        this(1, str);
    }

    public final String YQ() {
        return this.bIN;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Scope) {
            return this.bIN.equals(((Scope) obj).bIN);
        }
        return false;
    }

    public final int hashCode() {
        return this.bIN.hashCode();
    }

    public final String toString() {
        return this.bIN;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, YQ(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
