package com.google.android.gms.internal.measurement;

import com.google.android.gms.common.internal.bf;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public class zzcm extends zzar {
    private static zzcm zzaaq;

    public zzcm(zzat zzatVar) {
        super(zzatVar);
    }

    private static String zzd(Object obj) {
        if (obj == null) {
            return null;
        }
        Object objValueOf = obj instanceof Integer ? Long.valueOf(((Integer) obj).intValue()) : obj;
        if (!(objValueOf instanceof Long)) {
            if (objValueOf instanceof Boolean) {
                return String.valueOf(objValueOf);
            }
            return objValueOf instanceof Throwable ? objValueOf.getClass().getCanonicalName() : "-";
        }
        if (Math.abs(((Long) objValueOf).longValue()) < 100) {
            return String.valueOf(objValueOf);
        }
        String str = String.valueOf(objValueOf).charAt(0) == '-' ? "-" : "";
        String strValueOf = String.valueOf(Math.abs(((Long) objValueOf).longValue()));
        return str + Math.round(Math.pow(10.0d, strValueOf.length() - 1)) + "..." + str + Math.round(Math.pow(10.0d, strValueOf.length()) - 1.0d);
    }

    public static zzcm zzes() {
        return zzaaq;
    }

    public final void zza(zzch zzchVar, String str) {
        String string = zzchVar != null ? zzchVar.toString() : "no hit data";
        String strValueOf = String.valueOf(str);
        zzd(strValueOf.length() != 0 ? "Discarding hit. ".concat(strValueOf) : new String("Discarding hit. "), string);
    }

    public final void zza(Map<String, String> map, String str) {
        String string;
        if (map != null) {
            StringBuilder sb = new StringBuilder();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                if (sb.length() > 0) {
                    sb.append(',');
                }
                sb.append(entry.getKey());
                sb.append('=');
                sb.append(entry.getValue());
            }
            string = sb.toString();
        } else {
            string = "no hit data";
        }
        String strValueOf = String.valueOf(str);
        zzd(strValueOf.length() != 0 ? "Discarding hit. ".concat(strValueOf) : new String("Discarding hit. "), string);
    }

    @Override // com.google.android.gms.internal.measurement.zzar
    protected final void zzac() {
        synchronized (zzcm.class) {
            zzaaq = this;
        }
    }

    public final synchronized void zzb(int i, String str, Object obj, Object obj2, Object obj3) {
        synchronized (this) {
            bf.checkNotNull(str);
            int i2 = i >= 0 ? i : 0;
            int i3 = i2 >= 9 ? 8 : i2;
            char c = zzbv().zzds() ? 'C' : 'c';
            char cCharAt = "01VDIWEA?".charAt(i3);
            String str2 = zzas.VERSION;
            String strZzc = zzc(str, zzd(obj), zzd(obj2), zzd(obj3));
            String string = new StringBuilder(String.valueOf(str2).length() + 4 + String.valueOf(strZzc).length()).append("3").append(cCharAt).append(c).append(str2).append(":").append(strZzc).toString();
            if (string.length() > 1024) {
                string = string.substring(0, 1024);
            }
            zzcq zzcqVarZzcl = zzbs().zzcl();
            if (zzcqVarZzcl != null) {
                zzcqVarZzcl.zzff().zzad(string);
            }
        }
    }
}
