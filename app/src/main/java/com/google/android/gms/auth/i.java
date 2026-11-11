package com.google.android.gms.auth;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import com.google.android.gms.internal.auth.zzbr;
import com.google.android.gms.internal.auth.zzi;
import java.io.IOException;

/* JADX INFO: loaded from: classes.dex */
final class i implements k<TokenData> {
    private final /* synthetic */ Bundle bHz;
    private final /* synthetic */ Account zzr;
    private final /* synthetic */ String zzs;

    i(Account account, String str, Bundle bundle) {
        this.zzr = account;
        this.zzs = str;
        this.bHz = bundle;
    }

    @Override // com.google.android.gms.auth.k
    public final /* synthetic */ TokenData f(IBinder iBinder) throws IOException, a {
        Bundle bundle = (Bundle) h.w(zzi.zzd(iBinder).zzd(this.zzr, this.zzs, this.bHz));
        TokenData tokenDataC = TokenData.c(bundle, "tokenDetails");
        if (tokenDataC != null) {
            return tokenDataC;
        }
        String string = bundle.getString("Error");
        Intent intent = (Intent) bundle.getParcelable("userRecoveryIntent");
        zzbr zzbrVarZzh = zzbr.zzh(string);
        if (!zzbr.zzd(zzbrVarZzh)) {
            if (zzbr.NETWORK_ERROR.equals(zzbrVarZzh) || zzbr.SERVICE_UNAVAILABLE.equals(zzbrVarZzh)) {
                throw new IOException(string);
            }
            throw new a(string);
        }
        com.google.android.gms.common.b.a aVar = h.bHy;
        String strValueOf = String.valueOf(zzbrVarZzh);
        aVar.b("GoogleAuthUtil", new StringBuilder(String.valueOf(strValueOf).length() + 31).append("isUserRecoverableError status: ").append(strValueOf).toString());
        throw new d(string, intent);
    }
}
