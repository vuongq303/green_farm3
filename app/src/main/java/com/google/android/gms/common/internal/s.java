package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import java.util.Collection;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class s {
    private View bIC;
    private Map<com.google.android.gms.common.api.a<?>, t> bNF;
    private android.support.v4.e.c<Scope> bNI;
    private String zzcz;
    private String zzda;
    private Account zzs;
    private int bIB = 0;
    private com.google.android.gms.signin.d bNG = com.google.android.gms.signin.d.bYk;

    public final r aan() {
        return new r(this.zzs, this.bNI, this.bNF, this.bIB, this.bIC, this.zzcz, this.zzda, this.bNG);
    }

    public final s b(Account account) {
        this.zzs = account;
        return this;
    }

    public final s c(Collection<Scope> collection) {
        if (this.bNI == null) {
            this.bNI = new android.support.v4.e.c<>();
        }
        this.bNI.addAll(collection);
        return this;
    }

    public final s hX(String str) {
        this.zzcz = str;
        return this;
    }

    public final s hY(String str) {
        this.zzda = str;
        return this;
    }
}
