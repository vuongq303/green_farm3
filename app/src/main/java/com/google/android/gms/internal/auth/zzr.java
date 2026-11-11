package com.google.android.gms.internal.auth;

import android.accounts.Account;
import com.google.android.gms.auth.a.c;
import com.google.android.gms.common.api.Status;

/* JADX INFO: loaded from: classes.dex */
final class zzr implements c {
    private final Status mStatus;
    private final Account zzn;

    public zzr(Status status, Account account) {
        this.mStatus = status;
        this.zzn = account;
    }

    public final Account getAccount() {
        return this.zzn;
    }

    @Override // com.google.android.gms.common.api.ae
    public final Status getStatus() {
        return this.mStatus;
    }
}
