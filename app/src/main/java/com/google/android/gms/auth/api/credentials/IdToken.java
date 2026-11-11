package com.google.android.gms.auth.api.credentials;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class IdToken extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<IdToken> CREATOR = new k();
    private final String bFX;
    private final String zzbx;

    public IdToken(String str, String str2) {
        bf.b(!TextUtils.isEmpty(str), "account type string cannot be null or empty");
        bf.b(TextUtils.isEmpty(str2) ? false : true, "id token string cannot be null or empty");
        this.zzbx = str;
        this.bFX = str2;
    }

    public final String XJ() {
        return this.zzbx;
    }

    public final String XX() {
        return this.bFX;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 1, XJ(), false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, XX(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
