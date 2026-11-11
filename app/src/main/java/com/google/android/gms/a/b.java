package com.google.android.gms.a;

import com.google.android.gms.internal.measurement.zzat;
import com.google.android.gms.internal.measurement.zzde;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/* JADX INFO: loaded from: classes.dex */
public final class b extends d {
    private static List<Runnable> bEk = new ArrayList();
    private Set<Object> bEl;
    private boolean bEm;
    private volatile boolean bEn;
    private boolean zzqw;

    public b(zzat zzatVar) {
        super(zzatVar);
        this.bEl = new HashSet();
    }

    public static void zzn() {
        synchronized (b.class) {
            if (bEk != null) {
                Iterator<Runnable> it = bEk.iterator();
                while (it.hasNext()) {
                    it.next().run();
                }
                bEk = null;
            }
        }
    }

    public final boolean Xj() {
        return this.bEm;
    }

    public final boolean Xk() {
        return this.bEn;
    }

    public final void bV(boolean z) {
        this.bEm = z;
    }

    public final boolean isInitialized() {
        return this.zzqw;
    }

    public final void zzm() {
        zzde zzdeVarZzca = Xm().zzca();
        zzdeVarZzca.zzfm();
        if (zzdeVarZzca.zzfn()) {
            bV(zzdeVarZzca.zzfo());
        }
        zzdeVarZzca.zzfm();
        this.zzqw = true;
    }
}
