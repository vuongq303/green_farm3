package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.view.View;
import com.google.android.gms.common.api.Scope;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.annotation.Nullable;

/* JADX INFO: loaded from: classes.dex */
public final class r {
    private final int bIB;
    private final View bIC;
    private final Set<Scope> bIz;
    private final Set<Scope> bNE;
    private final Map<com.google.android.gms.common.api.a<?>, t> bNF;
    private final com.google.android.gms.signin.d bNG;
    private Integer bNH;
    private final String zzcz;
    private final String zzda;
    private final Account zzs;

    public r(Account account, Set<Scope> set, Map<com.google.android.gms.common.api.a<?>, t> map, int i, View view, String str, String str2, com.google.android.gms.signin.d dVar) {
        this.zzs = account;
        this.bIz = set == null ? Collections.EMPTY_SET : Collections.unmodifiableSet(set);
        this.bNF = map == null ? Collections.EMPTY_MAP : map;
        this.bIC = view;
        this.bIB = i;
        this.zzcz = str;
        this.zzda = str2;
        this.bNG = dVar;
        HashSet hashSet = new HashSet(this.bIz);
        Iterator<t> it = this.bNF.values().iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().mScopes);
        }
        this.bNE = Collections.unmodifiableSet(hashSet);
    }

    @Nullable
    @Deprecated
    public final String aad() {
        if (this.zzs != null) {
            return this.zzs.name;
        }
        return null;
    }

    public final int aae() {
        return this.bIB;
    }

    public final Set<Scope> aaf() {
        return this.bIz;
    }

    public final Set<Scope> aag() {
        return this.bNE;
    }

    public final Map<com.google.android.gms.common.api.a<?>, t> aah() {
        return this.bNF;
    }

    @Nullable
    public final String aai() {
        return this.zzcz;
    }

    @Nullable
    public final String aaj() {
        return this.zzda;
    }

    @Nullable
    public final View aak() {
        return this.bIC;
    }

    @Nullable
    public final com.google.android.gms.signin.d aal() {
        return this.bNG;
    }

    @Nullable
    public final Integer aam() {
        return this.bNH;
    }

    public final void c(Integer num) {
        this.bNH = num;
    }

    public final Set<Scope> e(com.google.android.gms.common.api.a<?> aVar) {
        t tVar = this.bNF.get(aVar);
        if (tVar == null || tVar.mScopes.isEmpty()) {
            return this.bIz;
        }
        HashSet hashSet = new HashSet(this.bIz);
        hashSet.addAll(tVar.mScopes);
        return hashSet;
    }

    @Nullable
    public final Account getAccount() {
        return this.zzs;
    }

    public final Account getAccountOrDefault() {
        return this.zzs != null ? this.zzs : new Account(e.DEFAULT_ACCOUNT, "com.google");
    }
}
