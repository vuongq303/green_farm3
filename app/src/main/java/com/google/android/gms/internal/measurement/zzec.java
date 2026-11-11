package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzec extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzec> CREATOR = new zzed();
    public final String packageName;
    public final String zzadh;
    public final String zzadj;
    public final long zzadn;
    public final String zzado;
    public final long zzadp;
    public final long zzadq;
    public final boolean zzadr;
    public final long zzads;
    public final boolean zzadt;
    public final boolean zzadu;
    public final String zzaef;
    public final boolean zzaeg;
    public final long zzaeh;
    public final int zzaei;
    public final boolean zzaej;
    public final String zztc;

    zzec(String str, String str2, String str3, long j, String str4, long j2, long j3, String str5, boolean z, boolean z2, String str6, long j4, long j5, int i, boolean z3, boolean z4, boolean z5) {
        bf.id(str);
        this.packageName = str;
        this.zzadh = TextUtils.isEmpty(str2) ? null : str2;
        this.zztc = str3;
        this.zzadn = j;
        this.zzado = str4;
        this.zzadp = j2;
        this.zzadq = j3;
        this.zzaef = str5;
        this.zzadr = z;
        this.zzaeg = z2;
        this.zzadj = str6;
        this.zzads = j4;
        this.zzaeh = j5;
        this.zzaei = i;
        this.zzadt = z3;
        this.zzadu = z4;
        this.zzaej = z5;
    }

    zzec(String str, String str2, String str3, String str4, long j, long j2, String str5, boolean z, boolean z2, long j3, String str6, long j4, long j5, int i, boolean z3, boolean z4, boolean z5) {
        this.packageName = str;
        this.zzadh = str2;
        this.zztc = str3;
        this.zzadn = j3;
        this.zzado = str4;
        this.zzadp = j;
        this.zzadq = j2;
        this.zzaef = str5;
        this.zzadr = z;
        this.zzaeg = z2;
        this.zzadj = str6;
        this.zzads = j4;
        this.zzaeh = j5;
        this.zzaei = i;
        this.zzadt = z3;
        this.zzadu = z4;
        this.zzaej = z5;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.packageName, false);
        c.a(parcel, 3, this.zzadh, false);
        c.a(parcel, 4, this.zztc, false);
        c.a(parcel, 5, this.zzado, false);
        c.a(parcel, 6, this.zzadp);
        c.a(parcel, 7, this.zzadq);
        c.a(parcel, 8, this.zzaef, false);
        c.a(parcel, 9, this.zzadr);
        c.a(parcel, 10, this.zzaeg);
        c.a(parcel, 11, this.zzadn);
        c.a(parcel, 12, this.zzadj, false);
        c.a(parcel, 13, this.zzads);
        c.a(parcel, 14, this.zzaeh);
        c.c(parcel, 15, this.zzaei);
        c.a(parcel, 16, this.zzadt);
        c.a(parcel, 17, this.zzadu);
        c.a(parcel, 18, this.zzaej);
        c.G(parcel, iX);
    }
}
