package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
public final class zzaul {
    @Deprecated
    public static final zzauh zzh(byte[] bArr) throws GeneralSecurityException {
        try {
            zzaxr zzaxrVarZzj = zzaxr.zzj(bArr);
            for (zzaxr.zzb zzbVar : zzaxrVarZzj.zzzl()) {
                if (zzbVar.zzzp().zzyy() == zzaxi.zzb.UNKNOWN_KEYMATERIAL || zzbVar.zzzp().zzyy() == zzaxi.zzb.SYMMETRIC || zzbVar.zzzp().zzyy() == zzaxi.zzb.ASYMMETRIC_PRIVATE) {
                    throw new GeneralSecurityException("keyset contains secret key material");
                }
            }
            return zzauh.zza(zzaxrVarZzj);
        } catch (zzbbu e) {
            throw new GeneralSecurityException("invalid keyset");
        }
    }
}
