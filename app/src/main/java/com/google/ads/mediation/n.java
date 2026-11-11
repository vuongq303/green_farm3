package com.google.ads.mediation;

import com.google.android.gms.internal.ads.zzane;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
@Deprecated
public class n {
    public void b(Map<String, String> map) throws o {
        HashMap map2 = new HashMap();
        for (Field field : getClass().getFields()) {
            p pVar = (p) field.getAnnotation(p.class);
            if (pVar != null) {
                map2.put(pVar.name(), field);
            }
        }
        if (map2.isEmpty()) {
            zzane.zzdk("No server options fields detected. To suppress this message either add a field with the @Parameter annotation, or override the load() method.");
        }
        for (Map.Entry<String, String> entry : map.entrySet()) {
            Field field2 = (Field) map2.remove(entry.getKey());
            if (field2 != null) {
                try {
                    field2.set(this, entry.getValue());
                } catch (IllegalAccessException e) {
                    String key = entry.getKey();
                    zzane.zzdk(new StringBuilder(String.valueOf(key).length() + 49).append("Server option \"").append(key).append("\" could not be set: Illegal Access").toString());
                } catch (IllegalArgumentException e2) {
                    String key2 = entry.getKey();
                    zzane.zzdk(new StringBuilder(String.valueOf(key2).length() + 43).append("Server option \"").append(key2).append("\" could not be set: Bad Type").toString());
                }
            } else {
                String key3 = entry.getKey();
                String value = entry.getValue();
                zzane.zzck(new StringBuilder(String.valueOf(key3).length() + 31 + String.valueOf(value).length()).append("Unexpected server option: ").append(key3).append(" = \"").append(value).append("\"").toString());
            }
        }
        StringBuilder sb = new StringBuilder();
        for (Field field3 : map2.values()) {
            if (((p) field3.getAnnotation(p.class)).WI()) {
                String strValueOf = String.valueOf(((p) field3.getAnnotation(p.class)).name());
                zzane.zzdk(strValueOf.length() != 0 ? "Required server option missing: ".concat(strValueOf) : new String("Required server option missing: "));
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(((p) field3.getAnnotation(p.class)).name());
            }
        }
        if (sb.length() > 0) {
            String strValueOf2 = String.valueOf(sb.toString());
            throw new o(strValueOf2.length() != 0 ? "Required server option(s) missing: ".concat(strValueOf2) : new String("Required server option(s) missing: "));
        }
    }
}
