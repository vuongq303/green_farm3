package com.google.android.gms.auth;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class AccountChangeEventsRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<AccountChangeEventsRequest> CREATOR = new f();
    private final int bEP;

    @Deprecated
    private String bER;
    private int zzl;
    private Account zzn;

    public AccountChangeEventsRequest() {
        this.bEP = 1;
    }

    AccountChangeEventsRequest(int i, int i2, String str, Account account) {
        this.bEP = i;
        this.zzl = i2;
        this.bER = str;
        if (account != null || TextUtils.isEmpty(str)) {
            this.zzn = account;
        } else {
            this.zzn = new Account(str, "com.google");
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.bEP);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 2, this.zzl);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, this.bER, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, (Parcelable) this.zzn, i, false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
