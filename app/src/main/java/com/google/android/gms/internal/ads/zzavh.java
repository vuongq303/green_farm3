package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;

/* JADX INFO: loaded from: classes.dex */
final class zzavh {
    public static zzayv zza(zzawy zzawyVar) throws GeneralSecurityException {
        switch (zzavi.zzdia[zzawyVar.ordinal()]) {
            case 1:
                return zzayv.NIST_P256;
            case 2:
                return zzayv.NIST_P384;
            case 3:
                return zzayv.NIST_P521;
            default:
                String strValueOf = String.valueOf(zzawyVar);
                throw new GeneralSecurityException(new StringBuilder(String.valueOf(strValueOf).length() + 20).append("unknown curve type: ").append(strValueOf).toString());
        }
    }

    public static zzayw zza(zzawk zzawkVar) throws GeneralSecurityException {
        switch (zzavi.zzdib[zzawkVar.ordinal()]) {
            case 1:
                return zzayw.UNCOMPRESSED;
            case 2:
                return zzayw.COMPRESSED;
            default:
                String strValueOf = String.valueOf(zzawkVar);
                throw new GeneralSecurityException(new StringBuilder(String.valueOf(strValueOf).length() + 22).append("unknown point format: ").append(strValueOf).toString());
        }
    }

    public static String zza(zzaxa zzaxaVar) throws NoSuchAlgorithmException {
        switch (zzavi.zzdhz[zzaxaVar.ordinal()]) {
            case 1:
                return "HmacSha1";
            case 2:
                return "HmacSha256";
            case 3:
                return "HmacSha512";
            default:
                String strValueOf = String.valueOf(zzaxaVar);
                throw new NoSuchAlgorithmException(new StringBuilder(String.valueOf(strValueOf).length() + 27).append("hash unsupported for HMAC: ").append(strValueOf).toString());
        }
    }

    public static void zza(zzawq zzawqVar) throws GeneralSecurityException {
        zzayt.zza(zza(zzawqVar.zzxu().zzyh()));
        zza(zzawqVar.zzxu().zzyi());
        if (zzawqVar.zzxw() == zzawk.UNKNOWN_FORMAT) {
            throw new GeneralSecurityException("unknown EC point format");
        }
        zzauo.zza(zzawqVar.zzxv().zzxp());
    }
}
