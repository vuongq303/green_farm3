package com.google.android.gms.plus.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class PlusCommonExtras extends AbstractSafeParcelable {
    public static final Parcelable.Creator<PlusCommonExtras> CREATOR = new j();
    private final int zzw;
    private String zzx;
    private String zzy;

    public PlusCommonExtras() {
        this.zzw = 1;
        this.zzx = "";
        this.zzy = "";
    }

    PlusCommonExtras(int i, String str, String str2) {
        this.zzw = i;
        this.zzx = str;
        this.zzy = str2;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlusCommonExtras)) {
            return false;
        }
        PlusCommonExtras plusCommonExtras = (PlusCommonExtras) obj;
        return this.zzw == plusCommonExtras.zzw && bd.b(this.zzx, plusCommonExtras.zzx) && bd.b(this.zzy, plusCommonExtras.zzy);
    }

    public int hashCode() {
        return bd.hashCode(Integer.valueOf(this.zzw), this.zzx, this.zzy);
    }

    public String toString() {
        return bd.C(this).c("versionCode", Integer.valueOf(this.zzw)).c("Gpsrc", this.zzx).c("ClientCallingPackage", this.zzy).toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.zzx, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.zzy, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzw);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
