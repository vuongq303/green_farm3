package com.google.android.gms.plus.internal;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
public final class zzn extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzn> CREATOR = new k();
    private final String bRM;
    private final String bXY;
    private final String[] bXZ;
    private final String[] bYa;
    private final String[] bYb;
    private final String bYc;
    private final String bYd;
    private final PlusCommonExtras bYe;
    private final String zzag;
    private final int zzw;

    zzn(int i, String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, String str5, PlusCommonExtras plusCommonExtras) {
        this.zzw = i;
        this.bXY = str;
        this.bXZ = strArr;
        this.bYa = strArr2;
        this.bYb = strArr3;
        this.bRM = str2;
        this.bYc = str3;
        this.zzag = str4;
        this.bYd = str5;
        this.bYe = plusCommonExtras;
    }

    public zzn(String str, String[] strArr, String[] strArr2, String[] strArr3, String str2, String str3, String str4, PlusCommonExtras plusCommonExtras) {
        this.zzw = 1;
        this.bXY = str;
        this.bXZ = strArr;
        this.bYa = strArr2;
        this.bYb = strArr3;
        this.bRM = str2;
        this.bYc = str3;
        this.zzag = null;
        this.bYd = null;
        this.bYe = plusCommonExtras;
    }

    public final String acc() {
        return this.bRM;
    }

    public final String[] afZ() {
        return this.bYa;
    }

    public final Bundle aga() {
        Bundle bundle = new Bundle();
        bundle.setClassLoader(PlusCommonExtras.class.getClassLoader());
        bundle.putByteArray("android.gms.plus.internal.PlusCommonExtras.extraPlusCommon", com.google.android.gms.common.internal.safeparcel.d.a(this.bYe));
        return bundle;
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof zzn)) {
            return false;
        }
        zzn zznVar = (zzn) obj;
        return this.zzw == zznVar.zzw && bd.b(this.bXY, zznVar.bXY) && Arrays.equals(this.bXZ, zznVar.bXZ) && Arrays.equals(this.bYa, zznVar.bYa) && Arrays.equals(this.bYb, zznVar.bYb) && bd.b(this.bRM, zznVar.bRM) && bd.b(this.bYc, zznVar.bYc) && bd.b(this.zzag, zznVar.zzag) && bd.b(this.bYd, zznVar.bYd) && bd.b(this.bYe, zznVar.bYe);
    }

    public final int hashCode() {
        return bd.hashCode(Integer.valueOf(this.zzw), this.bXY, this.bXZ, this.bYa, this.bYb, this.bRM, this.bYc, this.zzag, this.bYd, this.bYe);
    }

    public final String toString() {
        return bd.C(this).c("versionCode", Integer.valueOf(this.zzw)).c("accountName", this.bXY).c("requestedScopes", this.bXZ).c("visibleActivities", this.bYa).c("requiredFeatures", this.bYb).c("packageNameForAuth", this.bRM).c("callingPackageName", this.bYc).c("applicationName", this.zzag).c("extra", this.bYe.toString()).toString();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, this.bXY, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bXZ, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bYa, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bYb, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.bRM, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, this.bYc, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, this.zzag, false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzw);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.bYd, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 9, (Parcelable) this.bYe, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
