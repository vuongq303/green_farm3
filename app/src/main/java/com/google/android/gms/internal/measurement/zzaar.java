package com.google.android.gms.internal.measurement;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.lang.reflect.Field;
import java.nio.Buffer;
import java.nio.ByteOrder;
import java.security.AccessController;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.Unsafe;

/* JADX INFO: loaded from: classes.dex */
final class zzaar {
    private static final zzd zzbxa;
    private static final boolean zzbxb;
    private static final boolean zzbxc;
    private static final long zzbxd;
    private static final long zzbxe;
    private static final long zzbxf;
    private static final long zzbxg;
    private static final long zzbxh;
    private static final long zzbxi;
    private static final long zzbxj;
    private static final long zzbxk;
    private static final long zzbxl;
    private static final long zzbxm;
    private static final long zzbxn;
    private static final long zzbxo;
    private static final long zzbxp;
    private static final long zzbxq;
    private static final long zzbxr;
    private static final boolean zzbxs;
    private static final Logger logger = Logger.getLogger(zzaar.class.getName());
    private static final Unsafe zzbvu = zzvj();
    private static final Class<?> zzbth = zzya.zzto();
    private static final boolean zzbwy = zzk(Long.TYPE);
    private static final boolean zzbwz = zzk(Integer.TYPE);

    final class zza extends zzd {
        zza(Unsafe unsafe) {
            super(unsafe);
        }
    }

    final class zzb extends zzd {
        zzb(Unsafe unsafe) {
            super(unsafe);
        }
    }

    final class zzc extends zzd {
        zzc(Unsafe unsafe) {
            super(unsafe);
        }
    }

    abstract class zzd {
        Unsafe zzbxt;

        zzd(Unsafe unsafe) {
            this.zzbxt = unsafe;
        }
    }

    static {
        zzd zzcVar;
        Field field = null;
        if (zzbvu == null) {
            zzcVar = null;
        } else if (!zzya.zztn()) {
            zzcVar = new zzc(zzbvu);
        } else if (zzbwy) {
            zzcVar = new zzb(zzbvu);
        } else {
            zzcVar = zzbwz ? new zza(zzbvu) : null;
        }
        zzbxa = zzcVar;
        zzbxb = zzvl();
        zzbxc = zzvk();
        zzbxd = zzi(byte[].class);
        zzbxe = zzi(boolean[].class);
        zzbxf = zzj(boolean[].class);
        zzbxg = zzi(int[].class);
        zzbxh = zzj(int[].class);
        zzbxi = zzi(long[].class);
        zzbxj = zzj(long[].class);
        zzbxk = zzi(float[].class);
        zzbxl = zzj(float[].class);
        zzbxm = zzi(double[].class);
        zzbxn = zzj(double[].class);
        zzbxo = zzi(Object[].class);
        zzbxp = zzj(Object[].class);
        zzbxq = zza(zzvm());
        Field fieldZza = zza(String.class, FirebaseAnalytics.Param.VALUE);
        if (fieldZza != null && fieldZza.getType() == char[].class) {
            field = fieldZza;
        }
        zzbxr = zza(field);
        zzbxs = ByteOrder.nativeOrder() == ByteOrder.BIG_ENDIAN;
    }

    private zzaar() {
    }

    private static long zza(Field field) {
        if (field == null || zzbxa == null) {
            return -1L;
        }
        return zzbxa.zzbxt.objectFieldOffset(field);
    }

    private static Field zza(Class<?> cls, String str) {
        try {
            Field declaredField = cls.getDeclaredField(str);
            declaredField.setAccessible(true);
            return declaredField;
        } catch (Throwable th) {
            return null;
        }
    }

    private static int zzi(Class<?> cls) {
        if (zzbxc) {
            return zzbxa.zzbxt.arrayBaseOffset(cls);
        }
        return -1;
    }

    private static int zzj(Class<?> cls) {
        if (zzbxc) {
            return zzbxa.zzbxt.arrayIndexScale(cls);
        }
        return -1;
    }

    private static boolean zzk(Class<?> cls) {
        if (!zzya.zztn()) {
            return false;
        }
        try {
            Class<?> cls2 = zzbth;
            cls2.getMethod("peekLong", cls, Boolean.TYPE);
            cls2.getMethod("pokeLong", cls, Long.TYPE, Boolean.TYPE);
            cls2.getMethod("pokeInt", cls, Integer.TYPE, Boolean.TYPE);
            cls2.getMethod("peekInt", cls, Boolean.TYPE);
            cls2.getMethod("pokeByte", cls, Byte.TYPE);
            cls2.getMethod("peekByte", cls);
            cls2.getMethod("pokeByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            cls2.getMethod("peekByteArray", cls, byte[].class, Integer.TYPE, Integer.TYPE);
            return true;
        } catch (Throwable th) {
            return false;
        }
    }

    static Unsafe zzvj() {
        try {
            return (Unsafe) AccessController.doPrivileged(new zzaas());
        } catch (Throwable th) {
            return null;
        }
    }

    private static boolean zzvk() {
        if (zzbvu == null) {
            return false;
        }
        try {
            Class<?> cls = zzbvu.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("arrayBaseOffset", Class.class);
            cls.getMethod("arrayIndexScale", Class.class);
            cls.getMethod("getInt", Object.class, Long.TYPE);
            cls.getMethod("putInt", Object.class, Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            cls.getMethod("putLong", Object.class, Long.TYPE, Long.TYPE);
            cls.getMethod("getObject", Object.class, Long.TYPE);
            cls.getMethod("putObject", Object.class, Long.TYPE, Object.class);
            if (zzya.zztn()) {
                return true;
            }
            cls.getMethod("getByte", Object.class, Long.TYPE);
            cls.getMethod("putByte", Object.class, Long.TYPE, Byte.TYPE);
            cls.getMethod("getBoolean", Object.class, Long.TYPE);
            cls.getMethod("putBoolean", Object.class, Long.TYPE, Boolean.TYPE);
            cls.getMethod("getFloat", Object.class, Long.TYPE);
            cls.getMethod("putFloat", Object.class, Long.TYPE, Float.TYPE);
            cls.getMethod("getDouble", Object.class, Long.TYPE);
            cls.getMethod("putDouble", Object.class, Long.TYPE, Double.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeArrayOperations", new StringBuilder(String.valueOf(strValueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(strValueOf).toString());
            return false;
        }
    }

    private static boolean zzvl() {
        if (zzbvu == null) {
            return false;
        }
        try {
            Class<?> cls = zzbvu.getClass();
            cls.getMethod("objectFieldOffset", Field.class);
            cls.getMethod("getLong", Object.class, Long.TYPE);
            if (zzvm() == null) {
                return false;
            }
            if (zzya.zztn()) {
                return true;
            }
            cls.getMethod("getByte", Long.TYPE);
            cls.getMethod("putByte", Long.TYPE, Byte.TYPE);
            cls.getMethod("getInt", Long.TYPE);
            cls.getMethod("putInt", Long.TYPE, Integer.TYPE);
            cls.getMethod("getLong", Long.TYPE);
            cls.getMethod("putLong", Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Long.TYPE, Long.TYPE, Long.TYPE);
            cls.getMethod("copyMemory", Object.class, Long.TYPE, Object.class, Long.TYPE, Long.TYPE);
            return true;
        } catch (Throwable th) {
            Logger logger2 = logger;
            Level level = Level.WARNING;
            String strValueOf = String.valueOf(th);
            logger2.logp(level, "com.google.protobuf.UnsafeUtil", "supportsUnsafeByteBufferOperations", new StringBuilder(String.valueOf(strValueOf).length() + 71).append("platform method missing - proto runtime falling back to safer methods: ").append(strValueOf).toString());
            return false;
        }
    }

    private static Field zzvm() {
        Field fieldZza;
        if (zzya.zztn() && (fieldZza = zza(Buffer.class, "effectiveDirectAddress")) != null) {
            return fieldZza;
        }
        Field fieldZza2 = zza(Buffer.class, "address");
        if (fieldZza2 == null || fieldZza2.getType() != Long.TYPE) {
            return null;
        }
        return fieldZza2;
    }
}
