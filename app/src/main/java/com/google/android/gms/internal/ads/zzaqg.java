package com.google.android.gms.internal.ads;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaqg implements Iterable<zzaqe> {
    private final List<zzaqe> zzday = new ArrayList();

    public static boolean zzb(zzapw zzapwVar) {
        zzaqe zzaqeVarZzc = zzc(zzapwVar);
        if (zzaqeVarZzc == null) {
            return false;
        }
        zzaqeVarZzc.zzdav.abort();
        return true;
    }

    static zzaqe zzc(zzapw zzapwVar) {
        for (zzaqe zzaqeVar : com.google.android.gms.ads.internal.zzbv.zzff()) {
            if (zzaqeVar.zzcyg == zzapwVar) {
                return zzaqeVar;
            }
        }
        return null;
    }

    @Override // java.lang.Iterable
    public final Iterator<zzaqe> iterator() {
        return this.zzday.iterator();
    }

    public final void zza(zzaqe zzaqeVar) {
        this.zzday.add(zzaqeVar);
    }

    public final void zzb(zzaqe zzaqeVar) {
        this.zzday.remove(zzaqeVar);
    }

    public final int zztx() {
        return this.zzday.size();
    }
}
