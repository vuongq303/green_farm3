package com.google.android.gms.internal.measurement;

import java.io.PrintStream;
import java.util.List;

/* JADX INFO: loaded from: classes.dex */
final class zzwm extends zzwj {
    private final zzwk zzbnf = new zzwk();

    zzwm() {
    }

    @Override // com.google.android.gms.internal.measurement.zzwj
    public final void zza(Throwable th, PrintStream printStream) {
        th.printStackTrace(printStream);
        List<Throwable> listZza = this.zzbnf.zza(th, false);
        if (listZza == null) {
            return;
        }
        synchronized (listZza) {
            for (Throwable th2 : listZza) {
                printStream.print("Suppressed: ");
                th2.printStackTrace(printStream);
            }
        }
    }
}
