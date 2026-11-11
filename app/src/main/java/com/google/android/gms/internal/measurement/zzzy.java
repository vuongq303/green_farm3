package com.google.android.gms.internal.measurement;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/* JADX INFO: loaded from: classes.dex */
final class zzzy {
    private static final zzzy zzbwb = new zzzy();
    private final zzaab zzbwc;
    private final ConcurrentMap<Class<?>, zzaaa<?>> zzbwd = new ConcurrentHashMap();

    private zzzy() {
        zzaab zzaabVarZzfo = null;
        String[] strArr = {"com.google.protobuf.AndroidProto3SchemaFactory"};
        for (int i = 0; i <= 0; i++) {
            zzaabVarZzfo = zzfo(strArr[0]);
            if (zzaabVarZzfo != null) {
                break;
            }
        }
        this.zzbwc = zzaabVarZzfo == null ? new zzzi() : zzaabVarZzfo;
    }

    private static zzaab zzfo(String str) {
        try {
            return (zzaab) Class.forName(str).getConstructor(new Class[0]).newInstance(new Object[0]);
        } catch (Throwable th) {
            return null;
        }
    }

    public static zzzy zzuv() {
        return zzbwb;
    }

    public final <T> zzaaa<T> zzv(T t) {
        Class<?> cls = t.getClass();
        zzyw.zza(cls, "messageType");
        zzaaa<T> zzaaaVar = (zzaaa) this.zzbwd.get(cls);
        if (zzaaaVar != null) {
            return zzaaaVar;
        }
        zzaaa<T> zzaaaVarZzg = this.zzbwc.zzg(cls);
        zzyw.zza(cls, "messageType");
        zzyw.zza(zzaaaVarZzg, "schema");
        zzaaa<T> zzaaaVar2 = (zzaaa) this.zzbwd.putIfAbsent(cls, zzaaaVarZzg);
        return zzaaaVar2 != null ? zzaaaVar2 : zzaaaVarZzg;
    }
}
