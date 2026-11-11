package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
public final class zzck {
    private static Cipher zzrf = null;
    private static final Object zzrg = new Object();
    private static final Object zzrh = new Object();
    private final SecureRandom zzre = null;

    public zzck(SecureRandom secureRandom) {
    }

    private static Cipher getCipher() {
        Cipher cipher;
        synchronized (zzrh) {
            if (zzrf == null) {
                zzrf = Cipher.getInstance("AES/CBC/PKCS5Padding");
            }
            cipher = zzrf;
        }
        return cipher;
    }

    public final byte[] zza(byte[] bArr, String str) throws zzcl {
        byte[] bArrDoFinal;
        if (bArr.length != 16) {
            throw new zzcl(this);
        }
        try {
            byte[] bArrZza = zzbi.zza(str, false);
            if (bArrZza.length <= 16) {
                throw new zzcl(this);
            }
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(bArrZza.length);
            byteBufferAllocate.put(bArrZza);
            byteBufferAllocate.flip();
            byte[] bArr2 = new byte[16];
            byte[] bArr3 = new byte[bArrZza.length - 16];
            byteBufferAllocate.get(bArr2);
            byteBufferAllocate.get(bArr3);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            synchronized (zzrg) {
                getCipher().init(2, secretKeySpec, new IvParameterSpec(bArr2));
                bArrDoFinal = getCipher().doFinal(bArr3);
            }
            return bArrDoFinal;
        } catch (IllegalArgumentException e) {
            throw new zzcl(this, e);
        } catch (InvalidAlgorithmParameterException e2) {
            throw new zzcl(this, e2);
        } catch (InvalidKeyException e3) {
            throw new zzcl(this, e3);
        } catch (NoSuchAlgorithmException e4) {
            throw new zzcl(this, e4);
        } catch (BadPaddingException e5) {
            throw new zzcl(this, e5);
        } catch (IllegalBlockSizeException e6) {
            throw new zzcl(this, e6);
        } catch (NoSuchPaddingException e7) {
            throw new zzcl(this, e7);
        }
    }

    public final String zzb(byte[] bArr, byte[] bArr2) throws zzcl {
        byte[] bArrDoFinal;
        byte[] iv;
        if (bArr.length != 16) {
            throw new zzcl(this);
        }
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(bArr, "AES");
            synchronized (zzrg) {
                getCipher().init(1, secretKeySpec, (SecureRandom) null);
                bArrDoFinal = getCipher().doFinal(bArr2);
                iv = getCipher().getIV();
            }
            int length = bArrDoFinal.length + iv.length;
            ByteBuffer byteBufferAllocate = ByteBuffer.allocate(length);
            byteBufferAllocate.put(iv).put(bArrDoFinal);
            byteBufferAllocate.flip();
            byte[] bArr3 = new byte[length];
            byteBufferAllocate.get(bArr3);
            return zzbi.zza(bArr3, false);
        } catch (InvalidKeyException e) {
            throw new zzcl(this, e);
        } catch (NoSuchAlgorithmException e2) {
            throw new zzcl(this, e2);
        } catch (BadPaddingException e3) {
            throw new zzcl(this, e3);
        } catch (IllegalBlockSizeException e4) {
            throw new zzcl(this, e4);
        } catch (NoSuchPaddingException e5) {
            throw new zzcl(this, e5);
        }
    }

    public final byte[] zzl(String str) throws zzcl {
        try {
            byte[] bArrZza = zzbi.zza(str, false);
            if (bArrZza.length != 32) {
                throw new zzcl(this);
            }
            byte[] bArr = new byte[16];
            ByteBuffer.wrap(bArrZza, 4, 16).get(bArr);
            for (int i = 0; i < 16; i++) {
                bArr[i] = (byte) (bArr[i] ^ 68);
            }
            return bArr;
        } catch (IllegalArgumentException e) {
            throw new zzcl(this, e);
        }
    }
}
