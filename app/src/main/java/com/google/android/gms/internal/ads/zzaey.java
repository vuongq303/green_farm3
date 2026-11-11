package com.google.android.gms.internal.ads;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;
import java.util.List;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzaey extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzaey> CREATOR = new zzaez();
    private final ApplicationInfo applicationInfo;
    private final String packageName;

    @Nullable
    private final PackageInfo zzccw;
    private final List<String> zzcdj;
    private final String zzcds;
    private final Bundle zzcfy;
    private final zzang zzcfz;
    private final boolean zzcga;
    private final String zzcgb;

    public zzaey(Bundle bundle, zzang zzangVar, ApplicationInfo applicationInfo, String str, List<String> list, @Nullable PackageInfo packageInfo, String str2, boolean z, String str3) {
        this.zzcfy = bundle;
        this.zzcfz = zzangVar;
        this.packageName = str;
        this.applicationInfo = applicationInfo;
        this.zzcdj = list;
        this.zzccw = packageInfo;
        this.zzcds = str2;
        this.zzcga = z;
        this.zzcgb = str3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 1, this.zzcfy, false);
        c.a(parcel, 2, (Parcelable) this.zzcfz, i, false);
        c.a(parcel, 3, (Parcelable) this.applicationInfo, i, false);
        c.a(parcel, 4, this.packageName, false);
        c.b(parcel, 5, this.zzcdj, false);
        c.a(parcel, 6, (Parcelable) this.zzccw, i, false);
        c.a(parcel, 7, this.zzcds, false);
        c.a(parcel, 8, this.zzcga);
        c.a(parcel, 9, this.zzcgb, false);
        c.G(parcel, iX);
    }
}
