package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzavq extends zzbbo<zzavq, zza> implements zzbcw {
    private static volatile zzbdf<zzavq> zzakh;
    private static final zzavq zzdin = new zzavq();
    private zzavu zzdil;
    private zzaxe zzdim;

    public final class zza extends zzbbo.zza<zzavq, zza> implements zzbcw {
        private zza() {
            super(zzavq.zzdin);
        }

        /* synthetic */ zza(zzavr zzavrVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzavq>) zzavq.class, zzdin);
    }

    private zzavq() {
    }

    public static zzavq zzj(zzbah zzbahVar) {
        return (zzavq) zzbbo.zza(zzdin, zzbahVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzavq>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzavr zzavrVar = null;
        switch (zzavr.zzakf[i - 1]) {
            case 1:
                return new zzavq();
            case 2:
                return new zza(zzavrVar);
            case 3:
                return zza(zzdin, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0003\u0000\u0000\u0000\u0001\t\u0002\t", new Object[]{"zzdil", "zzdim"});
            case 4:
                return zzdin;
            case 5:
                zzbdf<zzavq> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzavq.class) {
                    zzbdf<zzavq> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdin);
                        zzakh = zzbVar;
                        obj3 = zzbVar;
                    }
                    break;
                }
                return obj3;
            case 6:
                return (byte) 1;
            case 7:
                return null;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public final zzavu zzwr() {
        return this.zzdil == null ? zzavu.zzwz() : this.zzdil;
    }

    public final zzaxe zzws() {
        return this.zzdim == null ? zzaxe.zzyq() : this.zzdim;
    }
}
