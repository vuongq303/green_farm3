package com.google.android.gms.a.a;

import com.google.android.gms.a.l;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class c {
    private Map<String, String> bEo = new HashMap();

    public final Map<String, String> hI(String str) {
        HashMap map = new HashMap();
        for (Map.Entry<String, String> entry : this.bEo.entrySet()) {
            String strValueOf = String.valueOf(str);
            String strValueOf2 = String.valueOf(entry.getKey());
            map.put(strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf), entry.getValue());
        }
        return map;
    }

    public String toString() {
        return l.zza((Map) this.bEo);
    }
}
