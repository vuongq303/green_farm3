package com.google.android.gms.internal.measurement;

import com.google.android.gms.internal.measurement.zzyv;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/* JADX INFO: loaded from: classes.dex */
public abstract class zzyv<MessageType extends zzyv<MessageType, BuilderType>, BuilderType> extends zzxz<MessageType, BuilderType> {
    private static Map<Object, zzyv<?, ?>> zzbum = new ConcurrentHashMap();
    protected zzaap zzbuk = zzaap.zzvi();
    private int zzbul = -1;

    public abstract class zza<MessageType extends zza<MessageType, BuilderType>, BuilderType> extends zzyv<MessageType, BuilderType> implements zzzr {
        protected zzyr<Object> zzbun = zzyr.zzua();
    }

    public final class zzb {
        private static final int zzbuo = 1;
        private static final int zzbup = 2;
        public static final int zzbuq = 3;
        private static final int zzbur = 4;
        private static final int zzbus = 5;
        public static final int zzbut = 6;
        public static final int zzbuu = 7;
        private static final /* synthetic */ int[] zzbuv = {1, 2, 3, 4, 5, 6, 7};
        public static final int zzbuw = 1;
        private static final int zzbux = 2;
        private static final /* synthetic */ int[] zzbuy = {1, 2};
        private static final int zzbuz = 1;
        private static final int zzbva = 2;
        private static final /* synthetic */ int[] zzbvb = {1, 2};
    }

    static Object zza(Method method, Object obj, Object... objArr) {
        try {
            return method.invoke(obj, objArr);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Couldn't use Java reflection to implement protocol message reflection.", e);
        } catch (InvocationTargetException e2) {
            Throwable cause = e2.getCause();
            if (cause instanceof RuntimeException) {
                throw ((RuntimeException) cause);
            }
            if (cause instanceof Error) {
                throw ((Error) cause);
            }
            throw new RuntimeException("Unexpected exception thrown by generated accessor method.", cause);
        }
    }

    static <T extends zzyv<?, ?>> T zzf(Class<T> cls) {
        T t = (T) zzbum.get(cls);
        if (t == null) {
            try {
                Class.forName(cls.getName(), true, cls.getClassLoader());
                t = (T) zzbum.get(cls);
            } catch (ClassNotFoundException e) {
                throw new IllegalStateException("Class initialization cannot fail.", e);
            }
        }
        if (t != null) {
            return t;
        }
        String strValueOf = String.valueOf(cls.getName());
        throw new IllegalStateException(strValueOf.length() != 0 ? "Unable to get default instance for: ".concat(strValueOf) : new String("Unable to get default instance for: "));
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (((zzyv) zza(6, (Object) null, (Object) null)).getClass().isInstance(obj)) {
            return zzzy.zzuv().zzv(this).equals(this, (zzyv) obj);
        }
        return false;
    }

    public int hashCode() {
        if (this.zzbtf != 0) {
            return this.zzbtf;
        }
        this.zzbtf = zzzy.zzuv().zzv(this).hashCode(this);
        return this.zzbtf;
    }

    public String toString() {
        return zzzs.zza(this, super.toString());
    }

    protected abstract Object zza(int i, Object obj, Object obj2);
}
