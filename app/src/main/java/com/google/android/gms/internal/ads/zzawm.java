package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzawm extends zzbbo<zzawm, zza> implements zzbcw {
    private static volatile zzbdf<zzawm> zzakh;
    private static final zzawm zzdji = new zzawm();
    private zzaxn zzdjh;

    public final class zza extends zzbbo.zza<zzawm, zza> implements zzbcw {
        private zza() {
            super(zzawm.zzdji);
        }

        /* synthetic */ zza(zzawn zzawnVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzawm>) zzawm.class, zzdji);
    }

    private zzawm() {
    }

    public static zzawm zzxq() {
        return zzdji;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawm>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzawn zzawnVar = null;
        switch (zzawn.zzakf[i - 1]) {
            case 1:
                return new zzawm();
            case 2:
                return new zza(zzawnVar);
            case 3:
                return zza(zzdji, "\u0000\u0001\u0000\u0000\u0002\u0002\u0001\u0003\u0000\u0000\u0000\u0002\t", new Object[]{"zzdjh"});
            case 4:
                return zzdji;
            case 5:
                zzbdf<zzawm> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzawm.class) {
                    zzbdf<zzawm> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdji);
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

    public final zzaxn zzxp() {
        return this.zzdjh == null ? zzaxn.zzzc() : this.zzdjh;
    }
}
