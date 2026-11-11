package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class HintRequest extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<HintRequest> CREATOR = new j();
    private final String[] bFP;
    private final boolean bFS;
    private final CredentialPickerConfig bFU;
    private final boolean bFV;
    private final boolean bFW;
    private final String zzcz;
    private final String zzda;
    private final int zzy;

    HintRequest(int i, CredentialPickerConfig credentialPickerConfig, boolean z, boolean z2, String[] strArr, boolean z3, String str, String str2) {
        this.zzy = i;
        this.bFU = (CredentialPickerConfig) bf.checkNotNull(credentialPickerConfig);
        this.bFV = z;
        this.bFW = z2;
        this.bFP = (String[]) bf.checkNotNull(strArr);
        if (this.zzy < 2) {
            this.bFS = true;
            this.zzcz = null;
            this.zzda = null;
        } else {
            this.bFS = z3;
            this.zzcz = str;
            this.zzda = str2;
        }
    }

    public final String[] XP() {
        return this.bFP;
    }

    public final boolean XS() {
        return this.bFS;
    }

    public final String XT() {
        return this.zzcz;
    }

    public final String XU() {
        return this.zzda;
    }

    public final CredentialPickerConfig XV() {
        return this.bFU;
    }

    public final boolean XW() {
        return this.bFV;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, (Parcelable) XV(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, XW());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bFW);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, XP(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, XS());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, XT(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, XU(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzy);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
