package com.google.android.gms.internal.ads;

import android.os.Build;
import android.os.ConditionVariable;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/* JADX INFO: loaded from: classes.dex */
public class zzcc {
    private static final ConditionVariable zzpt = new ConditionVariable();
    protected static volatile zzhx zzpu = null;
    private static volatile Random zzpw = null;
    private zzcz zzps;
    protected volatile Boolean zzpv;

    public zzcc(zzcz zzczVar) {
        this.zzps = zzczVar;
        zzczVar.zzab().execute(new zzcd(this));
    }

    public static int zzx() {
        try {
            return Build.VERSION.SDK_INT >= 21 ? ThreadLocalRandom.current().nextInt() : zzy().nextInt();
        } catch (RuntimeException e) {
            return zzy().nextInt();
        }
    }

    private static Random zzy() {
        if (zzpw == null) {
            synchronized (zzcc.class) {
                if (zzpw == null) {
                    zzpw = new Random();
                }
            }
        }
        return zzpw;
    }

    public final void zza(int i, int i2, long j) {
        try {
            zzpt.block();
            if (!this.zzpv.booleanValue() || zzpu == null) {
                return;
            }
            zzaw zzawVar = new zzaw();
            zzawVar.zzco = this.zzps.zzrt.getPackageName();
            zzawVar.zzcp = Long.valueOf(j);
            zzhz zzhzVarZzd = zzpu.zzd(zzbfi.zzb(zzawVar));
            zzhzVarZzd.zzr(i2);
            zzhzVarZzd.zzs(i);
            zzhzVarZzd.zzbd();
        } catch (Exception e) {
        }
    }
}
