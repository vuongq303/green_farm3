package com.google.android.gms.auth.a;

import android.accounts.Account;
import android.os.Parcel;
import com.google.android.gms.internal.auth.zze;
import com.google.android.gms.internal.auth.zzf;

/* JADX INFO: loaded from: classes.dex */
public abstract class e extends zze implements d {
    public e() {
        super("com.google.android.gms.auth.account.IWorkAccountCallback");
    }

    @Override // com.google.android.gms.internal.auth.zze
    protected final boolean dispatchTransaction(int i, Parcel parcel, Parcel parcel2, int i2) {
        switch (i) {
            case 1:
                zzf((Account) zzf.zzd(parcel, Account.CREATOR));
                return true;
            case 2:
                zzd(zzf.zzd(parcel));
                return true;
            default:
                return false;
        }
    }
}
