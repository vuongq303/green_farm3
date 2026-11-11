package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Binder;
import android.os.RemoteException;
import android.util.Log;

/* JADX INFO: loaded from: classes.dex */
public class a extends ai {
    private int bNs;
    private Context mContext;
    private Account zzs;

    public static Account a(ah ahVar) {
        Account account = null;
        if (ahVar != null) {
            long jClearCallingIdentity = Binder.clearCallingIdentity();
            try {
                account = ahVar.getAccount();
            } catch (RemoteException e) {
                Log.w("AccountAccessor", "Remote account accessor probably died");
            } finally {
                Binder.restoreCallingIdentity(jClearCallingIdentity);
            }
        }
        return account;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof a) {
            return this.zzs.equals(((a) obj).zzs);
        }
        return false;
    }

    @Override // com.google.android.gms.common.internal.ah
    public Account getAccount() {
        int callingUid = Binder.getCallingUid();
        if (callingUid == this.bNs) {
            return this.zzs;
        }
        if (!com.google.android.gms.common.m.isGooglePlayServicesUid(this.mContext, callingUid)) {
            throw new SecurityException("Caller is not GooglePlayServices");
        }
        this.bNs = callingUid;
        return this.zzs;
    }
}
