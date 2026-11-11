package com.google.android.gms.a;

import android.os.Build;
import com.google.android.gms.common.internal.bf;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class j {
    private final List<u> bEA;
    private final m bEt;
    private boolean bEu;
    private long bEv;
    private long bEw;
    private long bEx;
    private long bEy;
    private boolean bEz;
    private final com.google.android.gms.common.d.e zzrj;
    private long zzrp;
    private final Map<Class<? extends l>, l> zzrr;

    private j(j jVar) {
        this.bEt = jVar.bEt;
        this.zzrj = jVar.zzrj;
        this.bEv = jVar.bEv;
        this.bEw = jVar.bEw;
        this.bEx = jVar.bEx;
        this.bEy = jVar.bEy;
        this.zzrp = jVar.zzrp;
        this.bEA = new ArrayList(jVar.bEA);
        this.zzrr = new HashMap(jVar.zzrr.size());
        for (Map.Entry<Class<? extends l>, l> entry : jVar.zzrr.entrySet()) {
            l lVarC = c(entry.getKey());
            entry.getValue().zzb(lVarC);
            this.zzrr.put(entry.getKey(), lVarC);
        }
    }

    j(m mVar, com.google.android.gms.common.d.e eVar) {
        bf.checkNotNull(mVar);
        bf.checkNotNull(eVar);
        this.bEt = mVar;
        this.zzrj = eVar;
        this.bEy = 1800000L;
        this.zzrp = 3024000000L;
        this.zzrr = new HashMap();
        this.bEA = new ArrayList();
    }

    private static <T extends l> T c(Class<T> cls) {
        try {
            return cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Exception e) {
            if (e instanceof InstantiationException) {
                throw new IllegalArgumentException("dataType doesn't have default constructor", e);
            }
            if (e instanceof IllegalAccessException) {
                throw new IllegalArgumentException("dataType default constructor is not accessible", e);
            }
            if (Build.VERSION.SDK_INT < 19 || !(e instanceof ReflectiveOperationException)) {
                throw new RuntimeException(e);
            }
            throw new IllegalArgumentException("Linkage exception", e);
        }
    }

    public final j Xp() {
        return new j(this);
    }

    public final Collection<l> Xq() {
        return this.zzrr.values();
    }

    public final long Xr() {
        return this.bEv;
    }

    public final void Xs() {
        this.bEt.Xw().e(this);
    }

    public final boolean Xt() {
        return this.bEu;
    }

    final void Xu() {
        this.bEx = this.zzrj.elapsedRealtime();
        if (this.bEw != 0) {
            this.bEv = this.bEw;
        } else {
            this.bEv = this.zzrj.currentTimeMillis();
        }
        this.bEu = true;
    }

    final m Xv() {
        return this.bEt;
    }

    public final <T extends l> T a(Class<T> cls) {
        return (T) this.zzrr.get(cls);
    }

    public final void a(l lVar) {
        bf.checkNotNull(lVar);
        Class<?> cls = lVar.getClass();
        if (cls.getSuperclass() != l.class) {
            throw new IllegalArgumentException();
        }
        lVar.zzb(b(cls));
    }

    public final <T extends l> T b(Class<T> cls) {
        T t = (T) this.zzrr.get(cls);
        if (t != null) {
            return t;
        }
        T t2 = (T) c(cls);
        this.zzrr.put((Class<? extends l>) cls, t2);
        return t2;
    }

    public final void zza(long j) {
        this.bEw = j;
    }

    public final List<u> zzq() {
        return this.bEA;
    }

    final boolean zzw() {
        return this.bEz;
    }

    final void zzx() {
        this.bEz = true;
    }
}
