package com.google.android.gms.internal.ads;

/* JADX INFO: loaded from: classes.dex */
@zzadh
final class zzud {
    private static final zzua zzbpe = zzua.zzlk();
    private static final float zzbpf = ((Float) zzkb.zzik().zzd(zznk.zzazk)).floatValue();
    private static final long zzbpg = ((Long) zzkb.zzik().zzd(zznk.zzazi)).longValue();
    private static final float zzbph = ((Float) zzkb.zzik().zzd(zznk.zzazl)).floatValue();
    private static final long zzbpi = ((Long) zzkb.zzik().zzd(zznk.zzazj)).longValue();

    private static int zzb(long j, int i) {
        return (int) ((j >>> ((i % 16) * 4)) & 15);
    }

    static boolean zzlv() {
        int iZzb;
        int iZzb2 = Integer.MAX_VALUE;
        int iZzlr = zzbpe.zzlr();
        int iZzls = zzbpe.zzls();
        int iZzlp = zzbpe.zzlp() + zzbpe.zzlq();
        if (iZzlr >= 16 || zzbpi == 0) {
            iZzb = zzbph != 0.0f ? ((int) (zzbph * iZzlr)) + 1 : Integer.MAX_VALUE;
        } else {
            iZzb = zzb(zzbpi, iZzlr);
        }
        if (iZzls <= iZzb) {
            if (iZzlr < 16 && zzbpg != 0) {
                iZzb2 = zzb(zzbpg, iZzlr);
            } else if (zzbpf != 0.0f) {
                iZzb2 = (int) (zzbpf * iZzlr);
            }
            if (iZzlp <= iZzb2) {
                return true;
            }
        }
        return false;
    }
}
