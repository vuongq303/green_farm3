package com.google.android.gms.signin.internal;

import android.accounts.Account;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;

/* JADX INFO: loaded from: classes.dex */
public class RecordConsentRequest extends AbstractSafeParcelable {
    public static final Parcelable.Creator<RecordConsentRequest> CREATOR = new j();
    private final Scope[] bYz;
    private final int zzal;
    private final Account zzs;
    private final String zzw;

    RecordConsentRequest(int i, Account account, Scope[] scopeArr, String str) {
        this.zzal = i;
        this.zzs = account;
        this.bYz = scopeArr;
        this.zzw = str;
    }

    public String XT() {
        return this.zzw;
    }

    public Scope[] agk() {
        return this.bYz;
    }

    public Account getAccount() {
        return this.zzs;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        int iX = com.google.android.gms.common.internal.safeparcel.c.x(parcel);
        com.google.android.gms.common.internal.safeparcel.c.c(parcel, 1, this.zzal);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 2, (Parcelable) getAccount(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 3, (Parcelable[]) agk(), i, false);
        com.google.android.gms.common.internal.safeparcel.c.a(parcel, 4, XT(), false);
        com.google.android.gms.common.internal.safeparcel.c.G(parcel, iX);
    }
}
