package com.google.android.gms.a.a;

import com.google.android.gms.a.l;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class b {
    private Map<String, String> bEo;

    public final Map<String, String> Xl() {
        return new HashMap(this.bEo);
    }

    public String toString() {
        HashMap map = new HashMap();
        for (Map.Entry<String, String> entry : this.bEo.entrySet()) {
            if (entry.getKey().startsWith("&")) {
                map.put(entry.getKey().substring(1), entry.getValue());
            } else {
                map.put(entry.getKey(), entry.getValue());
            }
        }
        return l.zza((Map) map);
    }
}
