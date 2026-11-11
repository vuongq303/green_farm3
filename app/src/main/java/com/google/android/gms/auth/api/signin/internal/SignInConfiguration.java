package com.google.android.gms.auth.api.signin.internal;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public final class SignInConfiguration extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Parcelable.Creator<SignInConfiguration> CREATOR = new l();
    private final String bHa;
    private GoogleSignInOptions bHb;

    public SignInConfiguration(String str, GoogleSignInOptions googleSignInOptions) {
        this.bHa = bf.id(str);
        this.bHb = googleSignInOptions;
    }

    public final GoogleSignInOptions Ys() {
        return this.bHb;
    }

    public final boolean equals(Object obj) {
        boolean z = false;
        if (obj != null) {
            try {
                SignInConfiguration signInConfiguration = (SignInConfiguration) obj;
                if (this.bHa.equals(signInConfiguration.bHa) && (this.bHb != null ? this.bHb.equals(signInConfiguration.bHb) : signInConfiguration.bHb == null)) {
                    z = true;
                }
            } catch (ClassCastException e) {
            }
        }
        return z;
    }

    public final int hashCode() {
        return new b().u(this.bHa).u(this.bHb).Yr();
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bHa, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, (Parcelable) this.bHb, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
