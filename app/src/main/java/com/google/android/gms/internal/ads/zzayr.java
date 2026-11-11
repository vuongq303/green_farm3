package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.ECPoint;
import java.security.spec.EllipticCurve;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class zzayr {
    private ECPublicKey zzdnj;

    public zzayr(ECPublicKey eCPublicKey) {
        this.zzdnj = eCPublicKey;
    }

    public final zzays zza(String str, byte[] bArr, byte[] bArr2, int i, zzayw zzaywVar) throws GeneralSecurityException {
        byte[] bArr3;
        int i2 = 1;
        KeyPair keyPairZza = zzayt.zza(this.zzdnj.getParams());
        ECPublicKey eCPublicKey = (ECPublicKey) keyPairZza.getPublic();
        ECPrivateKey eCPrivateKey = (ECPrivateKey) keyPairZza.getPrivate();
        ECPublicKey eCPublicKey2 = this.zzdnj;
        ECParameterSpec params = eCPublicKey2.getParams();
        ECParameterSpec params2 = eCPrivateKey.getParams();
        if (!params.getCurve().equals(params2.getCurve()) || !params.getGenerator().equals(params2.getGenerator()) || !params.getOrder().equals(params2.getOrder()) || params.getCofactor() != params2.getCofactor()) {
            throw new GeneralSecurityException("invalid public key spec");
        }
        byte[] bArrZza = zzayt.zza(eCPrivateKey, eCPublicKey2.getW());
        EllipticCurve curve = eCPublicKey.getParams().getCurve();
        ECPoint w = eCPublicKey.getW();
        zzayt.zza(w, curve);
        int iZzb = zzayt.zzb(curve);
        switch (zzaywVar) {
            case UNCOMPRESSED:
                byte[] bArr4 = new byte[(iZzb * 2) + 1];
                byte[] byteArray = w.getAffineX().toByteArray();
                byte[] byteArray2 = w.getAffineY().toByteArray();
                System.arraycopy(byteArray2, 0, bArr4, ((iZzb * 2) + 1) - byteArray2.length, byteArray2.length);
                System.arraycopy(byteArray, 0, bArr4, (iZzb + 1) - byteArray.length, byteArray.length);
                bArr4[0] = 4;
                bArr3 = bArr4;
                break;
            case COMPRESSED:
                bArr3 = new byte[iZzb + 1];
                byte[] byteArray3 = w.getAffineX().toByteArray();
                System.arraycopy(byteArray3, 0, bArr3, (iZzb + 1) - byteArray3.length, byteArray3.length);
                bArr3[0] = (byte) (w.getAffineY().testBit(0) ? 3 : 2);
                break;
            default:
                String strValueOf = String.valueOf(zzaywVar);
                throw new GeneralSecurityException(new StringBuilder(String.valueOf(strValueOf).length() + 15).append("invalid format:").append(strValueOf).toString());
        }
        byte[] bArrZza2 = zzayk.zza(bArr3, bArrZza);
        Mac macZzek = zzayy.zzdoa.zzek(str);
        if (i > macZzek.getMacLength() * 255) {
            throw new GeneralSecurityException("size too large");
        }
        if (bArr == null || bArr.length == 0) {
            macZzek.init(new SecretKeySpec(new byte[macZzek.getMacLength()], str));
        } else {
            macZzek.init(new SecretKeySpec(bArr, str));
        }
        byte[] bArr5 = new byte[i];
        macZzek.init(new SecretKeySpec(macZzek.doFinal(bArrZza2), str));
        byte[] bArrDoFinal = new byte[0];
        int length = 0;
        while (true) {
            macZzek.update(bArrDoFinal);
            macZzek.update(bArr2);
            macZzek.update((byte) i2);
            bArrDoFinal = macZzek.doFinal();
            if (bArrDoFinal.length + length >= i) {
                System.arraycopy(bArrDoFinal, 0, bArr5, length, i - length);
                return new zzays(bArr3, bArr5);
            }
            System.arraycopy(bArrDoFinal, 0, bArr5, length, bArrDoFinal.length);
            length += bArrDoFinal.length;
            i2++;
        }
    }
}
