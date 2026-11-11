package com.google.android.gms.internal.ads;

import com.gameloft.glads.AndroidWebView;
import java.nio.charset.Charset;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzaup {
    private static final Charset UTF_8 = Charset.forName(AndroidWebView.UTF_8);

    public static zzaxt zzb(zzaxr zzaxrVar) {
        zzaxt.zza zzaVarZzbb = zzaxt.zzzu().zzbb(zzaxrVar.zzzk());
        for (zzaxr.zzb zzbVar : zzaxrVar.zzzl()) {
            zzaVarZzbb.zzb(zzaxt.zzb.zzzw().zzeh(zzbVar.zzzp().zzyw()).zzb(zzbVar.zzzq()).zzb(zzbVar.zzzs()).zzbd(zzbVar.zzzr()).zzadi());
        }
        return zzaVarZzbb.zzadi();
    }

    public static void zzc(zzaxr zzaxrVar) throws GeneralSecurityException {
        if (zzaxrVar.zzzm() == 0) {
            throw new GeneralSecurityException("empty keyset");
        }
        int iZzzk = zzaxrVar.zzzk();
        boolean z = true;
        boolean z2 = false;
        for (zzaxr.zzb zzbVar : zzaxrVar.zzzl()) {
            if (!zzbVar.zzzo()) {
                throw new GeneralSecurityException(String.format("key %d has no key data", Integer.valueOf(zzbVar.zzzr())));
            }
            if (zzbVar.zzzs() == zzayd.UNKNOWN_PREFIX) {
                throw new GeneralSecurityException(String.format("key %d has unknown prefix", Integer.valueOf(zzbVar.zzzr())));
            }
            if (zzbVar.zzzq() == zzaxl.UNKNOWN_STATUS) {
                throw new GeneralSecurityException(String.format("key %d has unknown status", Integer.valueOf(zzbVar.zzzr())));
            }
            if (zzbVar.zzzq() == zzaxl.ENABLED && zzbVar.zzzr() == iZzzk) {
                if (z2) {
                    throw new GeneralSecurityException("keyset contains multiple primary keys");
                }
                z2 = true;
            }
            z = zzbVar.zzzp().zzyy() != zzaxi.zzb.ASYMMETRIC_PUBLIC ? false : z;
        }
        if (!z2 && !z) {
            throw new GeneralSecurityException("keyset doesn't contain a valid primary key");
        }
    }
}
