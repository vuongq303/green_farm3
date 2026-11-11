package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class Feature extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new d();

    @Deprecated
    private final int bIb;
    private final long bIc;
    private final String name;

    public Feature(String str, int i, long j) {
        this.name = str;
        this.bIb = i;
        this.bIc = j;
    }

    public long YA() {
        return this.bIc == -1 ? this.bIb : this.bIc;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Feature)) {
            return false;
        }
        Feature feature = (Feature) obj;
        return ((getName() != null && getName().equals(feature.getName())) || (getName() == null && feature.getName() == null)) && YA() == feature.YA();
    }

    public String getName() {
        return this.name;
    }

    public int hashCode() {
        return bd.hashCode(getName(), Long.valueOf(YA()));
    }

    public String toString() {
        return bd.C(this).c("name", getName()).c("version", Long.valueOf(YA())).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, getName(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, this.bIb);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, YA());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
