package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
public final class zzawq extends zzbbo<zzawq, zza> implements zzbcw {
    private static volatile zzbdf<zzawq> zzakh;
    private static final zzawq zzdjo = new zzawq();
    private zzaww zzdjl;
    private zzawm zzdjm;
    private int zzdjn;

    public final class zza extends zzbbo.zza<zzawq, zza> implements zzbcw {
        private zza() {
            super(zzawq.zzdjo);
        }

        /* synthetic */ zza(zzawr zzawrVar) {
            this();
        }
    }

    static {
        zzbbo.zza((Class<zzawq>) zzawq.class, zzdjo);
    }

    private zzawq() {
    }

    public static zzawq zzxx() {
        return zzdjo;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzawq>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzawr zzawrVar = null;
        switch (zzawr.zzakf[i - 1]) {
            case 1:
                return new zzawq();
            case 2:
                return new zza(zzawrVar);
            case 3:
                return zza(zzdjo, "\u0000\u0003\u0000\u0000\u0001\u0003\u0003\u0004\u0000\u0000\u0000\u0001\t\u0002\t\u0003\f", new Object[]{"zzdjl", "zzdjm", "zzdjn"});
            case 4:
                return zzdjo;
            case 5:
                zzbdf<zzawq> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzawq.class) {
                    zzbdf<zzawq> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        zzbbo.zzb zzbVar = new zzbbo.zzb(zzdjo);
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

    public final zzaww zzxu() {
        return this.zzdjl == null ? zzaww.zzyk() : this.zzdjl;
    }

    public final zzawm zzxv() {
        return this.zzdjm == null ? zzawm.zzxq() : this.zzdjm;
    }

    public final zzawk zzxw() {
        zzawk zzawkVarZzaq = zzawk.zzaq(this.zzdjn);
        return zzawkVarZzaq == null ? zzawk.UNRECOGNIZED : zzawkVarZzaq;
    }
}
