package com.google.android.gms.internal.measurement;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzef extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzef> CREATOR = new zzeg();
    public String packageName;
    public String zzaek;
    public zzjs zzael;
    public long zzaem;
    public boolean zzaen;
    public String zzaeo;
    public zzeu zzaep;
    public long zzaeq;
    public zzeu zzaer;
    public long zzaes;
    public zzeu zzaet;

    zzef(zzef zzefVar) {
        bf.checkNotNull(zzefVar);
        this.packageName = zzefVar.packageName;
        this.zzaek = zzefVar.zzaek;
        this.zzael = zzefVar.zzael;
        this.zzaem = zzefVar.zzaem;
        this.zzaen = zzefVar.zzaen;
        this.zzaeo = zzefVar.zzaeo;
        this.zzaep = zzefVar.zzaep;
        this.zzaeq = zzefVar.zzaeq;
        this.zzaer = zzefVar.zzaer;
        this.zzaes = zzefVar.zzaes;
        this.zzaet = zzefVar.zzaet;
    }

    zzef(String str, String str2, zzjs zzjsVar, long j, boolean z, String str3, zzeu zzeuVar, long j2, zzeu zzeuVar2, long j3, zzeu zzeuVar3) {
        this.packageName = str;
        this.zzaek = str2;
        this.zzael = zzjsVar;
        this.zzaem = j;
        this.zzaen = z;
        this.zzaeo = str3;
        this.zzaep = zzeuVar;
        this.zzaeq = j2;
        this.zzaer = zzeuVar2;
        this.zzaes = j3;
        this.zzaet = zzeuVar3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.packageName, false);
        c.a(parcel, 3, this.zzaek, false);
        c.a(parcel, 4, (Parcelable) this.zzael, i, false);
        c.a(parcel, 5, this.zzaem);
        c.a(parcel, 6, this.zzaen);
        c.a(parcel, 7, this.zzaeo, false);
        c.a(parcel, 8, (Parcelable) this.zzaep, i, false);
        c.a(parcel, 9, this.zzaeq);
        c.a(parcel, 10, (Parcelable) this.zzaer, i, false);
        c.a(parcel, 11, this.zzaes);
        c.a(parcel, 12, (Parcelable) this.zzaet, i, false);
        c.G(parcel, iX);
    }
}
