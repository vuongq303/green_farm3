package com.google.android.gms.internal.ads;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzjj extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzjj> CREATOR = new zzjl();
    public final Bundle extras;
    public final int versionCode;
    public final long zzapw;
    public final int zzapx;
    public final List<String> zzapy;
    public final boolean zzapz;
    public final int zzaqa;
    public final boolean zzaqb;
    public final String zzaqc;
    public final zzmq zzaqd;
    public final Location zzaqe;
    public final String zzaqf;
    public final Bundle zzaqg;
    public final Bundle zzaqh;
    public final List<String> zzaqi;
    public final String zzaqj;
    public final String zzaqk;
    public final boolean zzaql;

    public zzjj(int i, long j, Bundle bundle, int i2, List<String> list, boolean z, int i3, boolean z2, String str, zzmq zzmqVar, Location location, String str2, Bundle bundle2, Bundle bundle3, List<String> list2, String str3, String str4, boolean z3) {
        this.versionCode = i;
        this.zzapw = j;
        this.extras = bundle == null ? new Bundle() : bundle;
        this.zzapx = i2;
        this.zzapy = list;
        this.zzapz = z;
        this.zzaqa = i3;
        this.zzaqb = z2;
        this.zzaqc = str;
        this.zzaqd = zzmqVar;
        this.zzaqe = location;
        this.zzaqf = str2;
        this.zzaqg = bundle2 == null ? new Bundle() : bundle2;
        this.zzaqh = bundle3;
        this.zzaqi = list2;
        this.zzaqj = str3;
        this.zzaqk = str4;
        this.zzaql = z3;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzjj)) {
            return false;
        }
        zzjj zzjjVar = (zzjj) obj;
        return this.versionCode == zzjjVar.versionCode && this.zzapw == zzjjVar.zzapw && bd.b(this.extras, zzjjVar.extras) && this.zzapx == zzjjVar.zzapx && bd.b(this.zzapy, zzjjVar.zzapy) && this.zzapz == zzjjVar.zzapz && this.zzaqa == zzjjVar.zzaqa && this.zzaqb == zzjjVar.zzaqb && bd.b(this.zzaqc, zzjjVar.zzaqc) && bd.b(this.zzaqd, zzjjVar.zzaqd) && bd.b(this.zzaqe, zzjjVar.zzaqe) && bd.b(this.zzaqf, zzjjVar.zzaqf) && bd.b(this.zzaqg, zzjjVar.zzaqg) && bd.b(this.zzaqh, zzjjVar.zzaqh) && bd.b(this.zzaqi, zzjjVar.zzaqi) && bd.b(this.zzaqj, zzjjVar.zzaqj) && bd.b(this.zzaqk, zzjjVar.zzaqk) && this.zzaql == zzjjVar.zzaql;
    }

    public final int hashCode() {
        return bd.hashCode(Integer.valueOf(this.versionCode), Long.valueOf(this.zzapw), this.extras, Integer.valueOf(this.zzapx), this.zzapy, Boolean.valueOf(this.zzapz), Integer.valueOf(this.zzaqa), Boolean.valueOf(this.zzaqb), this.zzaqc, this.zzaqd, this.zzaqe, this.zzaqf, this.zzaqg, this.zzaqh, this.zzaqi, this.zzaqj, this.zzaqk, Boolean.valueOf(this.zzaql));
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.versionCode);
        c.a(parcel, 2, this.zzapw);
        c.a(parcel, 3, this.extras, false);
        c.c(parcel, 4, this.zzapx);
        c.b(parcel, 5, this.zzapy, false);
        c.a(parcel, 6, this.zzapz);
        c.c(parcel, 7, this.zzaqa);
        c.a(parcel, 8, this.zzaqb);
        c.a(parcel, 9, this.zzaqc, false);
        c.a(parcel, 10, (Parcelable) this.zzaqd, i, false);
        c.a(parcel, 11, (Parcelable) this.zzaqe, i, false);
        c.a(parcel, 12, this.zzaqf, false);
        c.a(parcel, 13, this.zzaqg, false);
        c.a(parcel, 14, this.zzaqh, false);
        c.b(parcel, 15, this.zzaqi, false);
        c.a(parcel, 16, this.zzaqj, false);
        c.a(parcel, 17, this.zzaqk, false);
        c.a(parcel, 18, this.zzaql);
        c.G(parcel, iX);
    }

    public final zzjj zzhv() {
        Bundle bundle = this.zzaqg.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        if (bundle == null) {
            bundle = this.extras;
            this.zzaqg.putBundle("com.google.ads.mediation.admob.AdMobAdapter", this.extras);
        }
        return new zzjj(this.versionCode, this.zzapw, bundle, this.zzapx, this.zzapy, this.zzapz, this.zzaqa, this.zzaqb, this.zzaqc, this.zzaqd, this.zzaqe, this.zzaqf, this.zzaqg, this.zzaqh, this.zzaqi, this.zzaqj, this.zzaqk, this.zzaql);
    }
}
