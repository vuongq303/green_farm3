package com.google.android.gms.internal.ads;

import java.util.List;

/* JADX INFO: loaded from: classes.dex */
public final class zzaxr extends zzbbo<zzaxr, zza> implements zzbcw {
    private static volatile zzbdf<zzaxr> zzakh;
    private static final zzaxr zzdlt = new zzaxr();
    private int zzdlq;
    private int zzdlr;
    private zzbbt<zzb> zzdls = zzadd();

    public final class zza extends zzbbo.zza<zzaxr, zza> implements zzbcw {
        private zza() {
            super(zzaxr.zzdlt);
        }

        /* synthetic */ zza(zzaxs zzaxsVar) {
            this();
        }
    }

    public final class zzb extends zzbbo<zzb, zza> implements zzbcw {
        private static volatile zzbdf<zzb> zzakh;
        private static final zzb zzdlx = new zzb();
        private int zzdlj;
        private zzaxi zzdlu;
        private int zzdlv;
        private int zzdlw;

        public final class zza extends zzbbo.zza<zzb, zza> implements zzbcw {
            private zza() {
                super(zzb.zzdlx);
            }

            /* synthetic */ zza(zzaxs zzaxsVar) {
                this();
            }
        }

        static {
            zzbbo.zza((Class<zzb>) zzb.class, zzdlx);
        }

        private zzb() {
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr$zzb>] */
        @Override // com.google.android.gms.internal.ads.zzbbo
        protected final Object zza(int i, Object obj, Object obj2) {
            Object obj3;
            zzaxs zzaxsVar = null;
            switch (zzaxs.zzakf[i - 1]) {
                case 1:
                    return new zzb();
                case 2:
                    return new zza(zzaxsVar);
                case 3:
                    return zza(zzdlx, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0005\u0000\u0000\u0000\u0001\t\u0002\f\u0003\u000b\u0004\f", new Object[]{"zzdlu", "zzdlv", "zzdlw", "zzdlj"});
                case 4:
                    return zzdlx;
                case 5:
                    zzbdf<zzb> zzbdfVar = zzakh;
                    if (zzbdfVar != null) {
                        return zzbdfVar;
                    }
                    synchronized (zzb.class) {
                        zzbdf<zzb> zzbdfVar2 = zzakh;
                        obj3 = zzbdfVar2;
                        if (zzbdfVar2 == null) {
                            ?? zzbVar = new zzbbo.zzb(zzdlx);
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

        public final boolean zzzo() {
            return this.zzdlu != null;
        }

        public final zzaxi zzzp() {
            return this.zzdlu == null ? zzaxi.zzza() : this.zzdlu;
        }

        public final zzaxl zzzq() {
            zzaxl zzaxlVarZzax = zzaxl.zzax(this.zzdlv);
            return zzaxlVarZzax == null ? zzaxl.UNRECOGNIZED : zzaxlVarZzax;
        }

        public final int zzzr() {
            return this.zzdlw;
        }

        public final zzayd zzzs() {
            zzayd zzaydVarZzbg = zzayd.zzbg(this.zzdlj);
            return zzaydVarZzbg == null ? zzayd.UNRECOGNIZED : zzaydVarZzbg;
        }
    }

    static {
        zzbbo.zza((Class<zzaxr>) zzaxr.class, zzdlt);
    }

    private zzaxr() {
    }

    public static zzaxr zzj(byte[] bArr) {
        return (zzaxr) zzbbo.zzb(zzdlt, bArr);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v8, types: [com.google.android.gms.internal.ads.zzbbo$zzb, com.google.android.gms.internal.ads.zzbdf<com.google.android.gms.internal.ads.zzaxr>] */
    @Override // com.google.android.gms.internal.ads.zzbbo
    protected final Object zza(int i, Object obj, Object obj2) {
        Object obj3;
        zzaxs zzaxsVar = null;
        switch (zzaxs.zzakf[i - 1]) {
            case 1:
                return new zzaxr();
            case 2:
                return new zza(zzaxsVar);
            case 3:
                return zza(zzdlt, "\u0000\u0002\u0000\u0001\u0001\u0002\u0002\u0003\u0000\u0001\u0000\u0001\u000b\u0002\u001b", new Object[]{"zzdlq", "zzdlr", "zzdls", zzb.class});
            case 4:
                return zzdlt;
            case 5:
                zzbdf<zzaxr> zzbdfVar = zzakh;
                if (zzbdfVar != null) {
                    return zzbdfVar;
                }
                synchronized (zzaxr.class) {
                    zzbdf<zzaxr> zzbdfVar2 = zzakh;
                    obj3 = zzbdfVar2;
                    if (zzbdfVar2 == null) {
                        ?? zzbVar = new zzbbo.zzb(zzdlt);
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

    public final int zzzk() {
        return this.zzdlr;
    }

    public final List<zzb> zzzl() {
        return this.zzdls;
    }

    public final int zzzm() {
        return this.zzdls.size();
    }
}
