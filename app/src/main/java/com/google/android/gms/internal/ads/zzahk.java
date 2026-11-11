package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzahk extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzahk> CREATOR = new zzahl();
    public final String zzacp;
    public final zzjj zzccv;

    public zzahk(zzjj zzjjVar, String str) {
        this.zzccv = zzjjVar;
        this.zzacp = str;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, (Parcelable) this.zzccv, i, false);
        c.a(parcel, 3, this.zzacp, false);
        c.G(parcel, iX);
    }
}
