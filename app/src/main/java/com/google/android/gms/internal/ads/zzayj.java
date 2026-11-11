package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class zzayj implements zzatz {
    private final SecretKey zzdna;

    public zzayj(byte[] bArr) {
        this.zzdna = new SecretKeySpec(bArr, "AES");
    }

    @Override // com.google.android.gms.internal.ads.zzatz
    public final byte[] zzc(byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        if (bArr.length > 2147483619) {
            throw new GeneralSecurityException("plaintext too long");
        }
        byte[] bArr3 = new byte[bArr.length + 12 + 16];
        byte[] bArrZzbh = zzazl.zzbh(12);
        System.arraycopy(bArrZzbh, 0, bArr3, 0, 12);
        Cipher cipherZzek = zzayy.zzdnz.zzek("AES/GCM/NoPadding");
        cipherZzek.init(1, this.zzdna, new GCMParameterSpec(128, bArrZzbh));
        if (bArr2 == null) {
            bArr2 = new byte[0];
        }
        cipherZzek.updateAAD(bArr2);
        cipherZzek.doFinal(bArr, 0, bArr.length, bArr3, 12);
        return bArr3;
    }
}
