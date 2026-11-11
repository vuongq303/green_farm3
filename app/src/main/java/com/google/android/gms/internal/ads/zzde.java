package com.google.android.gms.internal.ads;

import java.lang.reflect.Method;
import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzde {
    static zzauf zzso;

    static boolean zzb(zzcz zzczVar) {
        Method methodZza;
        if (zzso != null) {
            return true;
        }
        String str = (String) zzkb.zzik().zzd(zznk.zzbax);
        if (str == null || str.length() == 0) {
            str = (zzczVar == null || (methodZza = zzczVar.zza("4o7tecxtkw7XaNt5hPj+0H1LvOi0SgxCIJTY9VcbazM/HSl/sFlxBFwnc8glnvoB", "RgSY6YxU2k1vLXOV3vapBnQwJDzYDlmX50wbm2tDcnw=")) == null) ? null : (String) methodZza.invoke(null, new Object[0]);
            if (str == null) {
                return false;
            }
        }
        try {
            try {
                zzauh zzauhVarZzh = zzaul.zzh(zzbi.zza(str, true));
                for (zzaxp zzaxpVar : zzavc.zzdht.zzaal()) {
                    if (zzaxpVar.zzyw().isEmpty()) {
                        throw new GeneralSecurityException("Missing type_url.");
                    }
                    if (zzaxpVar.zzze().isEmpty()) {
                        throw new GeneralSecurityException("Missing primitive_name.");
                    }
                    if (zzaxpVar.zzzh().isEmpty()) {
                        throw new GeneralSecurityException("Missing catalogue_name.");
                    }
                    zzauo.zza(zzaxpVar.zzyw(), zzauo.zzdy(zzaxpVar.zzzh()).zzb(zzaxpVar.zzyw(), zzaxpVar.zzze(), zzaxpVar.zzzf()), zzaxpVar.zzzg());
                }
                zzso = zzavf.zza(zzauhVarZzh);
                return zzso != null;
            } catch (GeneralSecurityException e) {
                return false;
            }
        } catch (IllegalArgumentException e2) {
            return false;
        }
    }
}
