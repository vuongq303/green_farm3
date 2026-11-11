package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzeu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzeu> CREATOR = new zzev();
    public final String name;
    public final String zzaek;
    public final zzer zzafo;
    public final long zzafz;

    zzeu(zzeu zzeuVar, long j) {
        bf.checkNotNull(zzeuVar);
        this.name = zzeuVar.name;
        this.zzafo = zzeuVar.zzafo;
        this.zzaek = zzeuVar.zzaek;
        this.zzafz = j;
    }

    public zzeu(String str, zzer zzerVar, String str2, long j) {
        this.name = str;
        this.zzafo = zzerVar;
        this.zzaek = str2;
        this.zzafz = j;
    }

    public final String toString() {
        String str = this.zzaek;
        String str2 = this.name;
        String strValueOf = String.valueOf(this.zzafo);
        return new StringBuilder(String.valueOf(str).length() + 21 + String.valueOf(str2).length() + String.valueOf(strValueOf).length()).append("origin=").append(str).append(",name=").append(str2).append(",params=").append(strValueOf).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.name, false);
        c.a(parcel, 3, (Parcelable) this.zzafo, i, false);
        c.a(parcel, 4, this.zzaek, false);
        c.a(parcel, 5, this.zzafz);
        c.G(parcel, iX);
    }
}
