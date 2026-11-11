package com.google.android.gms.auth.api.accounttransfer;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class DeviceMetaData extends AbstractSafeParcelable {
    public static final Parcelable.Creator<DeviceMetaData> CREATOR = new f();
    private boolean bFp;
    private final boolean bFq;
    private long zzcd;
    private final int zzy;

    DeviceMetaData(int i, boolean z, long j, boolean z2) {
        this.zzy = i;
        this.bFp = z;
        this.zzcd = j;
        this.bFq = z2;
    }

    public boolean XD() {
        return this.bFp;
    }

    public long XE() {
        return this.zzcd;
    }

    public boolean XF() {
        return this.bFq;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzy);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, XD());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, XE());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, XF());
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
