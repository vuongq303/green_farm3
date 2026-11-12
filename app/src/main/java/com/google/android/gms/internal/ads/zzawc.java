package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzawc extends zzbbo<zzawc, zza> implements zzbcw {
    private static volatile zzbdf<zzawc> zzakh;
    private static final zzawc zzdiy = new zzawc();
    private int zzdit;

    public final class zza extends zzbbo.zza<zzawc, zza> implements zzbcw {
        private zza() {
            super(zzawc.zzdiy);
        }

        /* synthetic */ zza(zzawd zzawdVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzawc>) zzawc.class, zzdiy);
    }

    private zzawc() {
    }

    public static zzawc zzxi() {
        return zzdiy;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawc>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzawd zzawdVar = null;
        switch (zzawd.zzakf[i - 1]) {
            case 1:
                return new zzawc();
            case 2:
                return new zza(zzawdVar);
            case 3:
                return zza(zzdiy, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0002\u0000\u0000\u0000\u0001\u000b", new Object[]{"zzdit"});
            case 4:
                return zzdiy;
            case 5:
                zzbdf<zzawc> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzawc.class) {
                    zzbdf<zzawc> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdiy);
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

    public final int zzxb() {
        return this.zzdit;
    }
}
