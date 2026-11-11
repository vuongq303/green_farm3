package com.google.android.gms.auth.api.proxy;

import android.app.PendingIntent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class ProxyResponse extends AbstractSafeParcelable {
    public static final Parcelable.Creator<ProxyResponse> CREATOR = new d();
    public final byte[] bGt;
    private final Bundle bGu;
    public final int bGv;
    public final PendingIntent bGw;
    public final int statusCode;
    private final int versionCode;

    ProxyResponse(int i, int i2, PendingIntent pendingIntent, int i3, Bundle bundle, byte[] bArr) {
        this.versionCode = i;
        this.bGv = i2;
        this.statusCode = i3;
        this.bGu = bundle;
        this.bGt = bArr;
        this.bGw = pendingIntent;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.bGv);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) this.bGw, i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 3, this.statusCode);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bGu, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.bGt, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.versionCode);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
