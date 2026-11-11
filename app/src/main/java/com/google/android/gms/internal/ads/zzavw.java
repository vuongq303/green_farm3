package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzavw extends zzbbo<zzavw, zza> implements zzbcw {
    private static volatile zzbdf<zzavw> zzakh;
    private static final zzavw zzdiu = new zzavw();
    private int zzdit;

    public final class zza extends zzbbo.zza<zzavw, zza> implements zzbcw {
        private zza() {
            super(zzavw.zzdiu);
        }

        /* synthetic */ zza(zzavx zzavxVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzavw>) zzavw.class, zzdiu);
    }

    private zzavw() {
    }

    public static zzavw zzxc() {
        return zzdiu;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzavw>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzavx zzavxVar = null;
        switch (zzavx.zzakf[i - 1]) {
            case 1:
                return new zzavw();
            case 2:
                return new zza(zzavxVar);
            case 3:
                return zza(zzdiu, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0002\u0000\u0000\u0000\u0001\u000b", new Object[]{"zzdit"});
            case 4:
                return zzdiu;
            case 5:
                zzbdf<zzavw> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzavw.class) {
                    zzbdf<zzavw> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        ?? zzbVar = new zzbbo.zzb(zzdiu);
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
