package com.google.android.gms.auth.api.signin;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public class SignInAccount extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<SignInAccount> CREATOR = new g();

    @Deprecated
    private String bGU;
    private GoogleSignInAccount bGV;

    @Deprecated
    private String bGW;

    SignInAccount(String str, GoogleSignInAccount googleSignInAccount, String str2) {
        this.bGV = googleSignInAccount;
        this.bGU = bf.d(str, (Object) "8.3 and 8.4 SDKs require non-null email");
        this.bGW = bf.d(str2, (Object) "8.3 and 8.4 SDKs require non-null userId");
    }

    @Nullable
    public final GoogleSignInAccount Yq() {
        return this.bGV;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bGU, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 7, (Parcelable) this.bGV, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 8, this.bGW, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
