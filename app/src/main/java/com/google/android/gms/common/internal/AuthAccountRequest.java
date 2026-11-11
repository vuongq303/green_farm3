package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class AuthAccountRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AuthAccountRequest> CREATOR = new d();

    @Deprecated
    private final IBinder bNt;
    private final Scope[] bNu;
    private Integer bNv;
    private Integer bNw;
    private final int zzal;
    private Account zzs;

    AuthAccountRequest(int i, IBinder iBinder, Scope[] scopeArr, Integer num, Integer num2, Account account) {
        this.zzal = i;
        this.bNt = iBinder;
        this.bNu = scopeArr;
        this.bNv = num;
        this.bNw = num2;
        this.zzs = account;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, this.bNt, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable[]) this.bNu, i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, this.bNv, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 5, this.bNw, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 6, (Parcelable) this.zzs, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
