package com.google.android.gms.internal.measurement;

import com.google.android.gms.a.l;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzx extends l<zzx> {
    private Map<Integer, Double> zzto = new HashMap(4);

    public final String toString() {
        HashMap map = new HashMap();
        for (Map.Entry<Integer, Double> entry : this.zzto.entrySet()) {
            String strValueOf = String.valueOf(entry.getKey());
            map.put(new StringBuilder(String.valueOf(strValueOf).length() + 6).append("metric").append(strValueOf).toString(), entry.getValue());
        }
        return zza((Object) map);
    }

    public final Map<Integer, Double> zzar() {
        return Collections.unmodifiableMap(this.zzto);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        ((zzx) lVar).zzto.putAll(this.zzto);
    }
}
