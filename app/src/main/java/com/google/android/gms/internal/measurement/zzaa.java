package com.google.android.gms.internal.measurement;

import com.google.android.gms.a.a.a;
import com.google.android.gms.a.a.b;
import com.google.android.gms.a.a.c;
import com.google.android.gms.a.l;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzaa extends l<zzaa> {
    private b zzrd;
    private final List<a> zzrg = new ArrayList();
    private final List<c> zzrf = new ArrayList();
    private final Map<String, List<a>> zzre = new HashMap();

    public final String toString() {
        HashMap map = new HashMap();
        if (!this.zzrg.isEmpty()) {
            map.put("products", this.zzrg);
        }
        if (!this.zzrf.isEmpty()) {
            map.put("promotions", this.zzrf);
        }
        if (!this.zzre.isEmpty()) {
            map.put("impressions", this.zzre);
        }
        map.put("productAction", this.zzrd);
        return zza((Object) map);
    }

    public final b zzat() {
        return this.zzrd;
    }

    public final List<a> zzau() {
        return Collections.unmodifiableList(this.zzrg);
    }

    public final Map<String, List<a>> zzav() {
        return this.zzre;
    }

    public final List<c> zzaw() {
        return Collections.unmodifiableList(this.zzrf);
    }

    @Override // com.google.android.gms.a.l
    public final /* synthetic */ void zzb(l lVar) {
        zzaa zzaaVar = (zzaa) lVar;
        zzaaVar.zzrg.addAll(this.zzrg);
        zzaaVar.zzrf.addAll(this.zzrf);
        for (Map.Entry<String, List<a>> entry : this.zzre.entrySet()) {
            String key = entry.getKey();
            for (a aVar : entry.getValue()) {
                if (aVar != null) {
                    String str = key == null ? "" : key;
                    if (!zzaaVar.zzre.containsKey(str)) {
                        zzaaVar.zzre.put(str, new ArrayList());
                    }
                    zzaaVar.zzre.get(str).add(aVar);
                }
            }
        }
        if (this.zzrd != null) {
            zzaaVar.zzrd = this.zzrd;
        }
    }
}
