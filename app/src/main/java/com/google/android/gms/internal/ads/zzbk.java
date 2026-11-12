package com.google.android.gms.internal.ads;

import com.gameloft.glads.AndroidWebView;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes.dex */
final class zzbk {
    private static boolean zzhy = false;
    private static MessageDigest zzhz = null;
    private static final Object zzia = new Object();
    private static final Object zzib = new Object();
    static CountDownLatch zzic = new CountDownLatch(1);

    static String zza(zzba zzbaVar, String str) throws GeneralSecurityException {
        byte[] bArrZza;
        byte[] bArrZzb = zzbfi.zzb(zzbaVar);
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbay)).booleanValue()) {
            Vector<byte[]> vectorZza = zza(bArrZzb, 255);
            if (vectorZza == null || vectorZza.size() == 0) {
                bArrZza = zza(zzbfi.zzb(zzc(4096L)), str, true);
            } else {
                zzbg zzbgVar = new zzbg();
                zzbgVar.zzgv = new byte[vectorZza.size()][];
                Iterator<byte[]> it = vectorZza.iterator();
                int i = 0;
                while (it.hasNext()) {
                    zzbgVar.zzgv[i] = zza(it.next(), str, false);
                    i++;
                }
                zzbgVar.zzgq = zzb(bArrZzb);
                bArrZza = zzbfi.zzb(zzbgVar);
            }
        } else {
            if (zzde.zzso == null) {
                throw new GeneralSecurityException();
            }
            byte[] bArrZzc = zzde.zzso.zzc(bArrZzb, str != null ? str.getBytes() : new byte[0]);
            zzbg zzbgVar2 = new zzbg();
            zzbgVar2.zzgv = new byte[][]{bArrZzc};
            zzbgVar2.zzfe = 2;
            bArrZza = zzbfi.zzb(zzbgVar2);
        }
        return zzbi.zza(bArrZza, true);
    }

    private static Vector<byte[]> zza(byte[] bArr, int i) {
        if (bArr == null || bArr.length <= 0) {
            return null;
        }
        int length = ((bArr.length + 255) - 1) / 255;
        Vector<byte[]> vector = new Vector<>();
        for (int i2 = 0; i2 < length; i2++) {
            int i3 = i2 * 255;
            try {
                vector.add(Arrays.copyOfRange(bArr, i3, bArr.length - i3 > 255 ? i3 + 255 : bArr.length));
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
        return vector;
    }

    private static byte[] zza(byte[] bArr, String str, boolean z) {
        byte[] bArrArray;
        int i = z ? 239 : 255;
        if (bArr.length > i) {
            bArr = zzbfi.zzb(zzc(4096L));
        }
        if (bArr.length < i) {
            byte[] bArr2 = new byte[i - bArr.length];
            new SecureRandom().nextBytes(bArr2);
            bArrArray = ByteBuffer.allocate(i + 1).put((byte) bArr.length).put(bArr).put(bArr2).array();
        } else {
            bArrArray = ByteBuffer.allocate(i + 1).put((byte) bArr.length).put(bArr).array();
        }
        if (z) {
            bArrArray = ByteBuffer.allocate(256).put(zzb(bArrArray)).put(bArrArray).array();
        }
        byte[] bArr3 = new byte[256];
        for (zzbp zzbpVar : new zzbn().zzpq) {
            zzbpVar.zza(bArrArray, bArr3);
        }
        if (str != null && str.length() > 0) {
            if (str.length() > 32) {
                str = str.substring(0, 32);
            }
            new zzazx(str.getBytes(AndroidWebView.UTF_8)).zzn(bArr3);
        }
        return bArr3;
    }

    public static byte[] zzb(byte[] bArr) throws NoSuchAlgorithmException {
        byte[] bArrDigest;
        synchronized (zzia) {
            MessageDigest messageDigestZzw = zzw();
            if (messageDigestZzw == null) {
                throw new NoSuchAlgorithmException("Cannot compute hash");
            }
            messageDigestZzw.reset();
            messageDigestZzw.update(bArr);
            bArrDigest = zzhz.digest();
        }
        return bArrDigest;
    }

    private static zzba zzc(long j) {
        zzba zzbaVar = new zzba();
        zzbaVar.zzdu = 4096L;
        return zzbaVar;
    }

    static void zzv() {
        synchronized (zzib) {
            if (!zzhy) {
                zzhy = true;
                new Thread(new zzbm()).start();
            }
        }
    }

    private static MessageDigest zzw() {
        zzv();
        boolean zAwait = false;
        try {
            zAwait = zzic.await(2L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
        }
        if (zAwait && zzhz != null) {
            return zzhz;
        }
        return null;
    }
}
