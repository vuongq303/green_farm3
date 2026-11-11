package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzang extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzang> CREATOR = new zzanh();
    public int zzcve;
    public int zzcvf;
    public boolean zzcvg;
    public boolean zzcvh;
    public String zzcw;

    public zzang(int i, int i2, boolean z) {
        this(i, i2, z, false, false);
    }

    public zzang(int i, int i2, boolean z, boolean z2) {
        this(12451000, i2, true, false, z2);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    private zzang(int i, int i2, boolean z, boolean z2, boolean z3) {
        String str = z ? AppEventsConstants.EVENT_PARAM_VALUE_NO : AppEventsConstants.EVENT_PARAM_VALUE_YES;
        this(new StringBuilder(String.valueOf(str).length() + 36).append("afma-sdk-a-v").append(i).append(".").append(i2).append(".").append(str).toString(), i, i2, z, z3);
    }

    zzang(String str, int i, int i2, boolean z, boolean z2) {
        this.zzcw = str;
        this.zzcve = i;
        this.zzcvf = i2;
        this.zzcvg = z;
        this.zzcvh = z2;
    }

    public static zzang zzsl() {
        return new zzang(12451009, 12451009, true);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.zzcw, false);
        c.c(parcel, 3, this.zzcve);
        c.c(parcel, 4, this.zzcvf);
        c.a(parcel, 5, this.zzcvg);
        c.a(parcel, 6, this.zzcvh);
        c.G(parcel, iX);
    }
}
