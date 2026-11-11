package com.google.android.gms.common.api;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class Status extends AbstractSafeParcelable implements ae, ReflectedParcelable {
    private final String bFl;
    private final int bHY;
    private final PendingIntent bHZ;
    private final int zzal;
    public static final Status bIO = new Status(0);
    public static final Status bIP = new Status(14);
    public static final Status bIQ = new Status(8);
    public static final Status bIR = new Status(15);
    public static final Status bIS = new Status(16);
    private static final Status bIT = new Status(17);
    public static final Status bIU = new Status(18);
    public static final Parcelable.Creator<Status> CREATOR = new an();

    public Status(int i) {
        this(i, null);
    }

    Status(int i, int i2, String str, PendingIntent pendingIntent) {
        this.zzal = i;
        this.bHY = i2;
        this.bFl = str;
        this.bHZ = pendingIntent;
    }

    public Status(int i, String str) {
        this(1, i, str, null);
    }

    public Status(int i, String str, PendingIntent pendingIntent) {
        this(1, i, str, pendingIntent);
    }

    public final boolean Sp() {
        return this.bHY <= 0;
    }

    public final String YR() {
        return this.bFl;
    }

    public final String YS() {
        return this.bFl != null ? this.bFl : s.nx(this.bHY);
    }

    public final boolean Yy() {
        return this.bHZ != null;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof Status)) {
            return false;
        }
        Status status = (Status) obj;
        return this.zzal == status.zzal && this.bHY == status.bHY && bd.b(this.bFl, status.bFl) && bd.b(this.bHZ, status.bHZ);
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this;
    }

    public final int getStatusCode() {
        return this.bHY;
    }

    public final int hashCode() {
        return bd.hashCode(Integer.valueOf(this.zzal), Integer.valueOf(this.bHY), this.bFl, this.bHZ);
    }

    public final String toString() {
        return bd.C(this).c("statusCode", YS()).c("resolution", this.bHZ).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, getStatusCode());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, YR(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) this.bHZ, i, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
