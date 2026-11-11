package com.google.android.gms.internal.ads;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.ads.VideoOptions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzmu extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzmu> CREATOR = new zzmv();
    public final boolean zzato;
    public final boolean zzatp;
    public final boolean zzatq;

    public zzmu(VideoOptions videoOptions) {
        this(videoOptions.getStartMuted(), videoOptions.getCustomControlsRequested(), videoOptions.getClickToExpandRequested());
    }

    public zzmu(boolean z, boolean z2, boolean z3) {
        this.zzato = z;
        this.zzatp = z2;
        this.zzatq = z3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.a(parcel, 2, this.zzato);
        c.a(parcel, 3, this.zzatp);
        c.a(parcel, 4, this.zzatq);
        c.G(parcel, iX);
    }
}
