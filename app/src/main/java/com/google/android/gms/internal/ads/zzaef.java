package com.google.android.gms.internal.ads;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzaef extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzaef> CREATOR = new zzaeh();
    public final ApplicationInfo applicationInfo;
    public final int versionCode;
    public final String zzaco;
    public final String zzacp;
    public final zzang zzacr;
    public final zzjn zzacv;
    public final zzpl zzadj;
    public final zzlu zzadl;
    public final List<Integer> zzadn;
    public final List<String> zzads;
    public final float zzagu;
    public final boolean zzbss;
    public final Bundle zzccu;
    public final zzjj zzccv;
    public final PackageInfo zzccw;
    public final String zzccx;
    public final String zzccy;
    public final String zzccz;
    public final Bundle zzcda;
    public final int zzcdb;
    public final Bundle zzcdc;
    public final boolean zzcdd;
    public final int zzcde;
    public final int zzcdf;
    public final String zzcdg;
    public final long zzcdh;
    public final String zzcdi;
    public final List<String> zzcdj;
    public final List<String> zzcdk;
    public final long zzcdl;
    public final String zzcdm;
    public final float zzcdn;
    public final int zzcdo;
    public final int zzcdp;
    public final boolean zzcdq;
    public final boolean zzcdr;
    public final String zzcds;
    public final boolean zzcdt;
    public final String zzcdu;
    public final int zzcdv;
    public final Bundle zzcdw;
    public final String zzcdx;
    public final boolean zzcdy;
    public final Bundle zzcdz;
    public final String zzcea;
    public final String zzceb;
    public final String zzcec;
    public final boolean zzced;
    public final String zzcee;
    public final List<String> zzcef;
    public final int zzceg;
    public final boolean zzceh;
    public final boolean zzcei;
    public final boolean zzcej;
    public final ArrayList<String> zzcek;

    zzaef(int i, Bundle bundle, zzjj zzjjVar, zzjn zzjnVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, zzang zzangVar, Bundle bundle2, int i2, List<String> list, Bundle bundle3, boolean z, int i3, int i4, float f, String str5, long j, String str6, List<String> list2, String str7, zzpl zzplVar, List<String> list3, long j2, String str8, float f2, boolean z2, int i5, int i6, boolean z3, boolean z4, String str9, String str10, boolean z5, int i7, Bundle bundle4, String str11, zzlu zzluVar, boolean z6, Bundle bundle5, String str12, String str13, String str14, boolean z7, List<Integer> list4, String str15, List<String> list5, int i8, boolean z8, boolean z9, boolean z10, ArrayList<String> arrayList) {
        this.versionCode = i;
        this.zzccu = bundle;
        this.zzccv = zzjjVar;
        this.zzacv = zzjnVar;
        this.zzacp = str;
        this.applicationInfo = applicationInfo;
        this.zzccw = packageInfo;
        this.zzccx = str2;
        this.zzccy = str3;
        this.zzccz = str4;
        this.zzacr = zzangVar;
        this.zzcda = bundle2;
        this.zzcdb = i2;
        this.zzads = list;
        this.zzcdk = list3 == null ? Collections.emptyList() : Collections.unmodifiableList(list3);
        this.zzcdc = bundle3;
        this.zzcdd = z;
        this.zzcde = i3;
        this.zzcdf = i4;
        this.zzagu = f;
        this.zzcdg = str5;
        this.zzcdh = j;
        this.zzcdi = str6;
        this.zzcdj = list2 == null ? Collections.emptyList() : Collections.unmodifiableList(list2);
        this.zzaco = str7;
        this.zzadj = zzplVar;
        this.zzcdl = j2;
        this.zzcdm = str8;
        this.zzcdn = f2;
        this.zzcdt = z2;
        this.zzcdo = i5;
        this.zzcdp = i6;
        this.zzcdq = z3;
        this.zzcdr = z4;
        this.zzcds = str9;
        this.zzcdu = str10;
        this.zzbss = z5;
        this.zzcdv = i7;
        this.zzcdw = bundle4;
        this.zzcdx = str11;
        this.zzadl = zzluVar;
        this.zzcdy = z6;
        this.zzcdz = bundle5;
        this.zzcea = str12;
        this.zzceb = str13;
        this.zzcec = str14;
        this.zzced = z7;
        this.zzadn = list4;
        this.zzcee = str15;
        this.zzcef = list5;
        this.zzceg = i8;
        this.zzceh = z8;
        this.zzcei = z9;
        this.zzcej = z10;
        this.zzcek = arrayList;
    }

    private zzaef(Bundle bundle, zzjj zzjjVar, zzjn zzjnVar, String str, ApplicationInfo applicationInfo, PackageInfo packageInfo, String str2, String str3, String str4, zzang zzangVar, Bundle bundle2, int i, List<String> list, List<String> list2, Bundle bundle3, boolean z, int i2, int i3, float f, String str5, long j, String str6, List<String> list3, String str7, zzpl zzplVar, long j2, String str8, float f2, boolean z2, int i4, int i5, boolean z3, boolean z4, String str9, String str10, boolean z5, int i6, Bundle bundle4, String str11, zzlu zzluVar, boolean z6, Bundle bundle5, String str12, String str13, String str14, boolean z7, List<Integer> list4, String str15, List<String> list5, int i7, boolean z8, boolean z9, boolean z10, ArrayList<String> arrayList) {
        this(24, bundle, zzjjVar, zzjnVar, str, applicationInfo, packageInfo, str2, str3, str4, zzangVar, bundle2, i, list, bundle3, z, i2, i3, f, str5, j, str6, list3, str7, zzplVar, list2, j2, str8, f2, z2, i4, i5, z3, z4, str9, str10, z5, i6, bundle4, str11, zzluVar, z6, bundle5, str12, str13, str14, z7, list4, str15, list5, i7, z8, z9, z10, arrayList);
    }

    public zzaef(zzaeg zzaegVar, long j, String str, String str2, String str3) {
        this(zzaegVar.zzccu, zzaegVar.zzccv, zzaegVar.zzacv, zzaegVar.zzacp, zzaegVar.applicationInfo, zzaegVar.zzccw, (String) zzano.zza(zzaegVar.zzcem, ""), zzaegVar.zzccy, zzaegVar.zzccz, zzaegVar.zzacr, zzaegVar.zzcda, zzaegVar.zzcdb, zzaegVar.zzads, zzaegVar.zzcdk, zzaegVar.zzcdc, zzaegVar.zzcdd, zzaegVar.zzcde, zzaegVar.zzcdf, zzaegVar.zzagu, zzaegVar.zzcdg, zzaegVar.zzcdh, zzaegVar.zzcdi, zzaegVar.zzcdj, zzaegVar.zzaco, zzaegVar.zzadj, j, zzaegVar.zzcdm, zzaegVar.zzcdn, zzaegVar.zzcdt, zzaegVar.zzcdo, zzaegVar.zzcdp, zzaegVar.zzcdq, zzaegVar.zzcdr, (String) zzano.zza(zzaegVar.zzcel, "", 1L, TimeUnit.SECONDS), zzaegVar.zzcdu, zzaegVar.zzbss, zzaegVar.zzcdv, zzaegVar.zzcdw, zzaegVar.zzcdx, zzaegVar.zzadl, zzaegVar.zzcdy, zzaegVar.zzcdz, str, str2, str3, zzaegVar.zzced, zzaegVar.zzadn, zzaegVar.zzcee, zzaegVar.zzcef, zzaegVar.zzceg, zzaegVar.zzceh, zzaegVar.zzcei, zzaegVar.zzcej, zzaegVar.zzcek);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.versionCode);
        c.a(parcel, 2, this.zzccu, false);
        c.a(parcel, 3, (Parcelable) this.zzccv, i, false);
        c.a(parcel, 4, (Parcelable) this.zzacv, i, false);
        c.a(parcel, 5, this.zzacp, false);
        c.a(parcel, 6, (Parcelable) this.applicationInfo, i, false);
        c.a(parcel, 7, (Parcelable) this.zzccw, i, false);
        c.a(parcel, 8, this.zzccx, false);
        c.a(parcel, 9, this.zzccy, false);
        c.a(parcel, 10, this.zzccz, false);
        c.a(parcel, 11, (Parcelable) this.zzacr, i, false);
        c.a(parcel, 12, this.zzcda, false);
        c.c(parcel, 13, this.zzcdb);
        c.b(parcel, 14, this.zzads, false);
        c.a(parcel, 15, this.zzcdc, false);
        c.a(parcel, 16, this.zzcdd);
        c.c(parcel, 18, this.zzcde);
        c.c(parcel, 19, this.zzcdf);
        c.a(parcel, 20, this.zzagu);
        c.a(parcel, 21, this.zzcdg, false);
        c.a(parcel, 25, this.zzcdh);
        c.a(parcel, 26, this.zzcdi, false);
        c.b(parcel, 27, this.zzcdj, false);
        c.a(parcel, 28, this.zzaco, false);
        c.a(parcel, 29, (Parcelable) this.zzadj, i, false);
        c.b(parcel, 30, this.zzcdk, false);
        c.a(parcel, 31, this.zzcdl);
        c.a(parcel, 33, this.zzcdm, false);
        c.a(parcel, 34, this.zzcdn);
        c.c(parcel, 35, this.zzcdo);
        c.c(parcel, 36, this.zzcdp);
        c.a(parcel, 37, this.zzcdq);
        c.a(parcel, 38, this.zzcdr);
        c.a(parcel, 39, this.zzcds, false);
        c.a(parcel, 40, this.zzcdt);
        c.a(parcel, 41, this.zzcdu, false);
        c.a(parcel, 42, this.zzbss);
        c.c(parcel, 43, this.zzcdv);
        c.a(parcel, 44, this.zzcdw, false);
        c.a(parcel, 45, this.zzcdx, false);
        c.a(parcel, 46, (Parcelable) this.zzadl, i, false);
        c.a(parcel, 47, this.zzcdy);
        c.a(parcel, 48, this.zzcdz, false);
        c.a(parcel, 49, this.zzcea, false);
        c.a(parcel, 50, this.zzceb, false);
        c.a(parcel, 51, this.zzcec, false);
        c.a(parcel, 52, this.zzced);
        c.a(parcel, 53, this.zzadn, false);
        c.a(parcel, 54, this.zzcee, false);
        c.b(parcel, 55, this.zzcef, false);
        c.c(parcel, 56, this.zzceg);
        c.a(parcel, 57, this.zzceh);
        c.a(parcel, 58, this.zzcei);
        c.a(parcel, 59, this.zzcej);
        c.b(parcel, 60, this.zzcek, false);
        c.G(parcel, iX);
    }
}
