package com.google.android.gms.internal.measurement;

import java.io.PrintStream;

/* JADX INFO: loaded from: classes.dex */
public final class zzwi {
    private static final zzwj zzbmz;
    private static final int zzbna;

    final class zza extends zzwj {
        zza() {
        }

        @Override // com.google.android.gms.internal.measurement.zzwj
        public final void zza(Throwable th, PrintStream printStream) {
            th.printStackTrace(printStream);
        }
    }

    static {
        zzwj zzaVar;
        Integer numZzrk = null;
        try {
            numZzrk = zzrk();
            if (numZzrk == null || numZzrk.intValue() < 19) {
                zzaVar = !Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? new zzwm() : new zza();
            } else {
                zzaVar = new zzwn();
            }
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            String name = zza.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzaVar = new zza();
        }
        zzbmz = zzaVar;
        zzbna = numZzrk == null ? 1 : numZzrk.intValue();
    }

    public static void zza(Throwable th, PrintStream printStream) {
        zzbmz.zza(th, printStream);
    }

    private static Integer zzrk() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
