package com.google.android.gms.internal.auth;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.c;

/* JADX INFO: loaded from: classes.dex */
public final class zzak extends AbstractSafeParcelable {
    public static final Parcelable.Creator<zzak> CREATOR = new zzal();
    private final String accountType;
    private final PendingIntent zzch;
    private final int zzy;

    zzak(int i, String str, PendingIntent pendingIntent) {
        this.zzy = 1;
        this.accountType = (String) bf.checkNotNull(str);
        this.zzch = (PendingIntent) bf.checkNotNull(pendingIntent);
    }

    public zzak(String str, PendingIntent pendingIntent) {
        this(1, str, pendingIntent);
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = c.x(parcel);
        c.c(parcel, 1, this.zzy);
        c.a(parcel, 2, this.accountType, false);
        c.a(parcel, 3, (Parcelable) this.zzch, i, false);
        c.G(parcel, iX);
    }
}
