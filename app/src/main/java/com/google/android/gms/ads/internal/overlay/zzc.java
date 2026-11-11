package com.google.android.gms.ads.internal.overlay;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.ads.zzadh;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzc extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzc> CREATOR = new zzb();
    private final String bAk;
    private final String bAl;
    public final Intent intent;
    public final String mimeType;
    public final String packageName;
    public final String url;
    public final String zzbxj;
    public final String zzbxk;

    public zzc(Intent intent) {
        this(null, null, null, null, null, null, null, intent);
    }

    public zzc(String str, String str2, String str3, String str4, String str5, String str6, String str7) {
        this(str, str2, str3, str4, str5, str6, str7, null);
    }

    public zzc(String str, String str2, String str3, String str4, String str5, String str6, String str7, Intent intent) {
        this.bAk = str;
        this.url = str2;
        this.mimeType = str3;
        this.packageName = str4;
        this.zzbxj = str5;
        this.zzbxk = str6;
        this.bAl = str7;
        this.intent = intent;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bAk, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.url, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.mimeType, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.packageName, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, this.zzbxj, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, this.zzbxk, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.bAl, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, (Parcelable) this.intent, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
