package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class zzayh implements zzazi {
    private final SecretKeySpec zzdmu;
    private final int zzdmv;
    private final int zzdmw = zzayy.zzdnz.zzek("AES/CTR/NoPadding").getBlockSize();

    public zzayh(byte[] bArr, int i) throws GeneralSecurityException {
        this.zzdmu = new SecretKeySpec(bArr, "AES");
        if (i < 12 || i > this.zzdmw) {
            throw new GeneralSecurityException("invalid IV size");
        }
        this.zzdmv = i;
    }

    @Override // com.google.android.gms.internal.ads.zzazi
    public final byte[] zzk(byte[] bArr) throws GeneralSecurityException {
        if (bArr.length > Integer.MAX_VALUE - this.zzdmv) {
            throw new GeneralSecurityException(new StringBuilder(43).append("plaintext length can not exceed ").append(Integer.MAX_VALUE - this.zzdmv).toString());
        }
        byte[] bArr2 = new byte[this.zzdmv + bArr.length];
        byte[] bArrZzbh = zzazl.zzbh(this.zzdmv);
        System.arraycopy(bArrZzbh, 0, bArr2, 0, this.zzdmv);
        int length = bArr.length;
        int i = this.zzdmv;
        Cipher cipherZzek = zzayy.zzdnz.zzek("AES/CTR/NoPadding");
        byte[] bArr3 = new byte[this.zzdmw];
        System.arraycopy(bArrZzbh, 0, bArr3, 0, this.zzdmv);
        cipherZzek.init(1, this.zzdmu, new IvParameterSpec(bArr3));
        if (cipherZzek.doFinal(bArr, 0, length, bArr2, i) != length) {
            throw new GeneralSecurityException("stored output's length does not match input's length");
        }
        return bArr2;
    }
}
