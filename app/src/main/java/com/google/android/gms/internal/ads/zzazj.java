package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes.dex */
public final class zzazj implements zzauk {
    private Mac zzdoj;
    private final int zzdok;
    private final String zzdol;
    private final Key zzdom;

    public zzazj(String str, Key key, int i) throws NoSuchAlgorithmException, InvalidKeyException, InvalidAlgorithmParameterException {
        if (i < 10) {
            throw new InvalidAlgorithmParameterException("tag size too small, need at least 10 bytes");
        }
        switch (str) {
            case "HMACSHA1":
                if (i > 20) {
                    throw new InvalidAlgorithmParameterException("tag size too big");
                }
                break;
            case "HMACSHA256":
                if (i > 32) {
                    throw new InvalidAlgorithmParameterException("tag size too big");
                }
                break;
            case "HMACSHA512":
                if (i > 64) {
                    throw new InvalidAlgorithmParameterException("tag size too big");
                }
                break;
            default:
                String strValueOf = String.valueOf(str);
                throw new NoSuchAlgorithmException(strValueOf.length() != 0 ? "unknown Hmac algorithm: ".concat(strValueOf) : new String("unknown Hmac algorithm: "));
        }
        this.zzdol = str;
        this.zzdok = i;
        this.zzdom = key;
        this.zzdoj = zzayy.zzdoa.zzek(str);
        this.zzdoj.init(key);
    }

    @Override // com.google.android.gms.internal.ads.zzauk
    public final byte[] zzg(byte[] bArr) throws GeneralSecurityException {
        Mac macZzek;
        try {
            macZzek = (Mac) this.zzdoj.clone();
        } catch (CloneNotSupportedException e) {
            macZzek = zzayy.zzdoa.zzek(this.zzdol);
            macZzek.init(this.zzdom);
        }
        macZzek.update(bArr);
        byte[] bArr2 = new byte[this.zzdok];
        System.arraycopy(macZzek.doFinal(), 0, bArr2, 0, this.zzdok);
        return bArr2;
    }
}
