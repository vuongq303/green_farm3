package com.google.android.gms.internal.measurement;

import com.google.android.gms.a.l;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzw extends l<zzw> {
    private Map<Integer, String> zztn = new HashMap(4);

    public final String toString() {
        HashMap map = new HashMap();
        for (Map.Entry<Integer, String> entry : this.zztn.entrySet()) {
            String strValueOf = String.valueOf(entry.getKey());
            map.put(new StringBuilder(String.valueOf(strValueOf).length() + 9).append("dimension").append(strValueOf).toString(), entry.getValue());
        }
        return zza((Object) map);
    }

    public final Map<Integer, String> zzaq() {
        return Collections.unmodifiableMap(this.zztn);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        ((zzw) lVar).zztn.putAll(this.zztn);
    }
}
