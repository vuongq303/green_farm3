package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzauq implements zzaua<zzatz> {
    @Override // com.google.android.gms.internal.ads.zzaua
    public final zzaug<zzatz> zzb(String str, String str2, int i) throws GeneralSecurityException {
        zzaug<zzatz> zzauzVar;
        switch (str2.toLowerCase()) {
            case "aead":
                switch (str) {
                    case "type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey":
                        zzauzVar = new zzaus();
                        break;
                    case "type.googleapis.com/google.crypto.tink.AesEaxKey":
                        zzauzVar = new zzauu();
                        break;
                    case "type.googleapis.com/google.crypto.tink.AesGcmKey":
                        zzauzVar = new zzauv();
                        break;
                    case "type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key":
                        zzauzVar = new zzauw();
                        break;
                    case "type.googleapis.com/google.crypto.tink.KmsAeadKey":
                        zzauzVar = new zzaux();
                        break;
                    case "type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey":
                        zzauzVar = new zzauz();
                        break;
                    default:
                        throw new GeneralSecurityException(String.format("No support for primitive 'Aead' with key type '%s'.", str));
                }
                if (zzauzVar.getVersion() < i) {
                    throw new GeneralSecurityException(String.format("No key manager for key type '%s' with version at least %d.", str, Integer.valueOf(i)));
                }
                return zzauzVar;
            default:
                throw new GeneralSecurityException(String.format("No support for primitive '%s'.", str2));
        }
    }
}
