package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzaxn extends zzbbo<zzaxn, zza> implements zzbcw {
    private static volatile zzbdf<zzaxn> zzakh;
    private static final zzaxn zzdlk = new zzaxn();
    private String zzdks = "";
    private zzbah zzdkt = zzbah.zzdpq;
    private int zzdlj;

    public final class zza extends zzbbo.zza<zzaxn, zza> implements zzbcw {
        private zza() {
            super(zzaxn.zzdlk);
        }

        /* synthetic */ zza(zzaxo zzaxoVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzaxn>) zzaxn.class, zzdlk);
    }

    private zzaxn() {
    }

    public static zzaxn zzzc() {
        return zzdlk;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxn>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzaxo zzaxoVar = null;
        switch (zzaxo.zzakf[i - 1]) {
            case 1:
                return new zzaxn();
            case 2:
                return new zza(zzaxoVar);
            case 3:
                return zza(zzdlk, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001Ȉ\u0002\n\u0003\f", new Object[]{"zzdks", "zzdkt", "zzdlj"});
            case 4:
                return zzdlk;
            case 5:
                zzbdf<zzaxn> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzaxn.class) {
                    zzbdf<zzaxn> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdlk);
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

    public final String zzyw() {
        return this.zzdks;
    }

    public final zzbah zzyx() {
        return this.zzdkt;
    }
}
