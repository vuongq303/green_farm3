package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import com.facebook.appevents.codeless.internal.Constants;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class CredentialRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<CredentialRequest> CREATOR = new i();
    private final boolean bFO;
    private final String[] bFP;
    private final CredentialPickerConfig bFQ;
    private final CredentialPickerConfig bFR;
    private final boolean bFS;
    private final boolean bFT;
    private final String zzcz;
    private final String zzda;
    private final int zzy;

    CredentialRequest(int i, boolean z, String[] strArr, CredentialPickerConfig credentialPickerConfig, CredentialPickerConfig credentialPickerConfig2, boolean z2, String str, String str2, boolean z3) {
        this.zzy = i;
        this.bFO = z;
        this.bFP = (String[]) bf.checkNotNull(strArr);
        this.bFQ = credentialPickerConfig == null ? new a().XN() : credentialPickerConfig;
        this.bFR = credentialPickerConfig2 == null ? new a().XN() : credentialPickerConfig2;
        if (i < 3) {
            this.bFS = true;
            this.zzcz = null;
            this.zzda = null;
        } else {
            this.bFS = z2;
            this.zzcz = str;
            this.zzda = str2;
        }
        this.bFT = z3;
    }

    public final boolean XO() {
        return this.bFO;
    }

    public final String[] XP() {
        return this.bFP;
    }

    public final CredentialPickerConfig XQ() {
        return this.bFQ;
    }

    public final CredentialPickerConfig XR() {
        return this.bFR;
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

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, XO());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, XP(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable) XQ(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) XR(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, XS());
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, XT(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, XU(), false);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS, this.zzy);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.bFT);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
