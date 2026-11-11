package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzavd implements zzaua<zzaue> {
    @Override // com.google.android.gms.internal.ads.zzaua
    public final zzaug<zzaue> zzb(String str, String str2, int i) throws GeneralSecurityException {
        switch (str2.toLowerCase()) {
            case "hybriddecrypt":
                switch (str) {
                    case "type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey":
                        zzava zzavaVar = new zzava();
                        if (i > 0) {
                            throw new GeneralSecurityException(String.format("No key manager for key type '%s' with version at least %d.", str, Integer.valueOf(i)));
                        }
                        return zzavaVar;
                    default:
                        throw new GeneralSecurityException(String.format("No support for primitive 'HybridEncrypt' with key type '%s'.", str));
                }
            default:
                throw new GeneralSecurityException(String.format("No support for primitive '%s'.", str2));
        }
    }
}
