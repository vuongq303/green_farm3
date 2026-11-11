package com.google.android.gms.internal.stable;

import java.io.PrintStream;

/* JADX INFO: loaded from: classes.dex */
public final class zzk {
    private static final zzl zzahg;
    private static final int zzahh;

    final class zza extends zzl {
        zza() {
        }

        @Override // com.google.android.gms.internal.stable.zzl
        public final void zza(Throwable th, Throwable th2) {
        }
    }

    static {
        zzl zzaVar;
        Integer numZzdw = null;
        try {
            numZzdw = zzdw();
            if (numZzdw == null || numZzdw.intValue() < 19) {
                zzaVar = !Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? new zzo() : new zza();
            } else {
                zzaVar = new zzp();
            }
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            String name = zza.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzaVar = new zza();
        }
        zzahg = zzaVar;
        zzahh = numZzdw == null ? 1 : numZzdw.intValue();
    }

    public static void zza(Throwable th, Throwable th2) {
        zzahg.zza(th, th2);
    }

    private static Integer zzdw() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
