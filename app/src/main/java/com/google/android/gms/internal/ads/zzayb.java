package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzayb extends zzbbo<zzayb, zza> implements zzbcw {
    private static volatile zzbdf<zzayb> zzakh;
    private static final zzayb zzdmj = new zzayb();
    private String zzdmh = "";
    private zzaxn zzdmi;

    public final class zza extends zzbbo.zza<zzayb, zza> implements zzbcw {
        private zza() {
            super(zzayb.zzdmj);
        }

        /* synthetic */ zza(zzayc zzaycVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzayb>) zzayb.class, zzdmj);
    }

    private zzayb() {
    }

    public static zzayb zzaaj() {
        return zzdmj;
    }

    public static zzayb zzam(zzbah zzbahVar) {
        return (zzayb) zzbbo.zza(zzdmj, zzbahVar);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzayb>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzayc zzaycVar = null;
        switch (zzayc.zzakf[i - 1]) {
            case 1:
                return new zzayb();
            case 2:
                return new zza(zzaycVar);
            case 3:
                return zza(zzdmj, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0003\u0000\u0000\u0000\u0001Ȉ\u0002\t", new Object[]{"zzdmh", "zzdmi"});
            case 4:
                return zzdmj;
            case 5:
                zzbdf<zzayb> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzayb.class) {
                    zzbdf<zzayb> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        ?? zzbVar = new zzbbo.zzb(zzdmj);
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

    public final String zzaah() {
        return this.zzdmh;
    }

    public final zzaxn zzaai() {
        return this.zzdmi == null ? zzaxn.zzzc() : this.zzdmi;
    }
}
