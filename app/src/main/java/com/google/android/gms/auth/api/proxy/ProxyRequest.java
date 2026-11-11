package com.google.android.gms.auth.api.proxy;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ProxyRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ProxyRequest> CREATOR = new c();
    public static final int bGi = 0;
    public static final int bGj = 1;
    public static final int bGk = 2;
    public static final int bGl = 3;
    public static final int bGm = 4;
    public static final int bGn = 5;
    public static final int bGo = 6;
    public static final int bGp = 7;
    public static final int bGq = 7;
    public final int bGr;
    public final long bGs;
    public final byte[] bGt;
    private Bundle bGu;
    public final String url;
    private final int versionCode;

    ProxyRequest(int i, String str, int i2, long j, byte[] bArr, Bundle bundle) {
        this.versionCode = i;
        this.url = str;
        this.bGr = i2;
        this.bGs = j;
        this.bGt = bArr;
        this.bGu = bundle;
    }

    public String toString() {
        String str = this.url;
        return new StringBuilder(String.valueOf(str).length() + 42).append("ProxyRequest[ url: ").append(str).append(", method: ").append(this.bGr).append(" ]").toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.url, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, this.bGr);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bGs);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bGt, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.bGu, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.versionCode);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
