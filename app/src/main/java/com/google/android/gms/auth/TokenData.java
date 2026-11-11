package com.google.android.gms.auth;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public class TokenData extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<TokenData> CREATOR = new l();
    private final Long bEW;
    private final boolean bEX;
    private final List<String> bEY;
    private final boolean zzac;
    private final int zzy;
    private final String zzz;

    TokenData(int i, String str, Long l, boolean z, boolean z2, List<String> list) {
        this.zzy = i;
        this.zzz = bf.id(str);
        this.bEW = l;
        this.bEX = z;
        this.zzac = z2;
        this.bEY = list;
    }

    public static TokenData c(Bundle bundle, String str) {
        bundle.setClassLoader(TokenData.class.getClassLoader());
        Bundle bundle2 = bundle.getBundle(str);
        if (bundle2 == null) {
            return null;
        }
        bundle2.setClassLoader(TokenData.class.getClassLoader());
        return (TokenData) bundle2.getParcelable("TokenData");
    }

    public final String XA() {
        return this.zzz;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof TokenData)) {
            return false;
        }
        TokenData tokenData = (TokenData) obj;
        return TextUtils.equals(this.zzz, tokenData.zzz) && bd.b(this.bEW, tokenData.bEW) && this.bEX == tokenData.bEX && this.zzac == tokenData.zzac && bd.b(this.bEY, tokenData.bEY);
    }

    public int hashCode() {
        return bd.hashCode(this.zzz, this.bEW, Boolean.valueOf(this.bEX), Boolean.valueOf(this.zzac), this.bEY);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzy);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.zzz, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bEW, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bEX);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.zzac);
        com.google.android.gms.common.internal.safeparcel.c.b(parcel, 6, this.bEY, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
