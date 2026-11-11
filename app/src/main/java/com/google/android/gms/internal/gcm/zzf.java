package com.google.android.gms.internal.gcm;

import java.io.PrintStream;

/* JADX INFO: loaded from: classes.dex */
public final class zzf {
    private static final zzg zzdc;
    private static final int zzdd;

    final class zzd extends zzg {
        zzd() {
        }

        @Override // com.google.android.gms.internal.gcm.zzg
        public final void zzd(Throwable th, Throwable th2) {
        }
    }

    static {
        zzg zzdVar;
        Integer numZzy = null;
        try {
            numZzy = zzy();
            if (numZzy == null || numZzy.intValue() < 19) {
                zzdVar = !Boolean.getBoolean("com.google.devtools.build.android.desugar.runtime.twr_disable_mimic") ? new zzj() : new zzd();
            } else {
                zzdVar = new zzk();
            }
        } catch (Throwable th) {
            PrintStream printStream = System.err;
            String name = zzd.class.getName();
            printStream.println(new StringBuilder(String.valueOf(name).length() + 132).append("An error has occured when initializing the try-with-resources desuguring strategy. The default strategy ").append(name).append("will be used. The error is: ").toString());
            th.printStackTrace(System.err);
            zzdVar = new zzd();
        }
        zzdc = zzdVar;
        zzdd = numZzy == null ? 1 : numZzy.intValue();
    }

    public static void zzd(Throwable th, Throwable th2) {
        zzdc.zzd(th, th2);
    }

    private static Integer zzy() {
        try {
            return (Integer) Class.forName("android.os.Build$VERSION").getField("SDK_INT").get(null);
        } catch (Exception e) {
            System.err.println("Failed to retrieve value from android.os.Build$VERSION.SDK_INT due to the following exception.");
            e.printStackTrace(System.err);
            return null;
        }
    }
}
