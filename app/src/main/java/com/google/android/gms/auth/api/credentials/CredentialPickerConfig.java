package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class CredentialPickerConfig extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<CredentialPickerConfig> CREATOR = new h();
    private final boolean bFJ;
    private final boolean bFK;

    @Deprecated
    private final boolean bFL;
    private final int bFM;
    private final int zzy;

    CredentialPickerConfig(int i, boolean z, boolean z2, boolean z3, int i2) {
        this.zzy = i;
        this.bFJ = z;
        this.bFK = z2;
        if (i < 2) {
            this.bFL = z3;
            this.bFM = z3 ? 3 : 1;
        } else {
            this.bFL = i2 == 3;
            this.bFM = i2;
        }
    }

    private CredentialPickerConfig(a aVar) {
        this(2, aVar.bFJ, aVar.bFK, false, aVar.bFN);
    }

    public final boolean XK() {
        return this.bFJ;
    }

    public final boolean XL() {
        return this.bFK;
    }

    @Deprecated
    public final boolean XM() {
        return this.bFM == 3;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, XK());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, XL());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, XM());
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 4, this.bFM);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzy);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
