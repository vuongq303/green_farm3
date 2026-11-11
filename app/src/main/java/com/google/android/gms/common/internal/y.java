package com.google.android.gms.common.internal;

import android.accounts.Account;
import android.content.Context;
import android.os.Handler;
import android.os.IInterface;
import android.os.Looper;
import com.google.android.gms.common.Feature;
import com.google.android.gms.common.api.Scope;
import java.util.Iterator;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public abstract class y<T extends IInterface> extends e<T> implements com.google.android.gms.common.api.l, aa {
    private final Set<Scope> mScopes;
    private final r zzgf;
    private final Account zzs;

    protected y(Context context, Handler handler, int i, r rVar) {
        this(context, handler, ab.aH(context), com.google.android.gms.common.e.YB(), i, rVar, (com.google.android.gms.common.api.y) null, (com.google.android.gms.common.api.z) null);
    }

    protected y(Context context, Handler handler, ab abVar, com.google.android.gms.common.e eVar, int i, r rVar, com.google.android.gms.common.api.y yVar, com.google.android.gms.common.api.z zVar) {
        super(context, handler, abVar, eVar, i, zza(yVar), zza(zVar));
        this.zzgf = (r) bf.checkNotNull(rVar);
        this.zzs = rVar.getAccount();
        this.mScopes = zza(rVar.aag());
    }

    protected y(Context context, Looper looper, int i, r rVar) {
        this(context, looper, ab.aH(context), com.google.android.gms.common.e.YB(), i, rVar, (com.google.android.gms.common.api.y) null, (com.google.android.gms.common.api.z) null);
    }

    protected y(Context context, Looper looper, int i, r rVar, com.google.android.gms.common.api.y yVar, com.google.android.gms.common.api.z zVar) {
        this(context, looper, ab.aH(context), com.google.android.gms.common.e.YB(), i, rVar, (com.google.android.gms.common.api.y) bf.checkNotNull(yVar), (com.google.android.gms.common.api.z) bf.checkNotNull(zVar));
    }

    protected y(Context context, Looper looper, ab abVar, com.google.android.gms.common.e eVar, int i, r rVar, com.google.android.gms.common.api.y yVar, com.google.android.gms.common.api.z zVar) {
        super(context, looper, abVar, eVar, i, zza(yVar), zza(zVar), rVar.aaj());
        this.zzgf = rVar;
        this.zzs = rVar.getAccount();
        this.mScopes = zza(rVar.aag());
    }

    private static f zza(com.google.android.gms.common.api.y yVar) {
        if (yVar == null) {
            return null;
        }
        return new bp(yVar);
    }

    private static g zza(com.google.android.gms.common.api.z zVar) {
        if (zVar == null) {
            return null;
        }
        return new bq(zVar);
    }

    private final Set<Scope> zza(Set<Scope> set) {
        Set<Scope> setValidateScopes = validateScopes(set);
        Iterator<Scope> it = setValidateScopes.iterator();
        while (it.hasNext()) {
            if (!set.contains(it.next())) {
                throw new IllegalStateException("Expanding scopes is not permitted, use implied scopes instead");
            }
        }
        return setValidateScopes;
    }

    @Override // com.google.android.gms.common.internal.e
    public final Account getAccount() {
        return this.zzs;
    }

    protected final r getClientSettings() {
        return this.zzgf;
    }

    @Override // com.google.android.gms.common.internal.e, com.google.android.gms.common.api.l
    public int getMinApkVersion() {
        return super.getMinApkVersion();
    }

    @Override // com.google.android.gms.common.internal.e
    public Feature[] getRequiredFeatures() {
        return new Feature[0];
    }

    @Override // com.google.android.gms.common.internal.e
    protected final Set<Scope> getScopes() {
        return this.mScopes;
    }

    protected Set<Scope> validateScopes(Set<Scope> set) {
        return set;
    }
}
