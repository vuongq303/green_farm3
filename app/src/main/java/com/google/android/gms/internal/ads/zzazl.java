package com.google.android.gms.internal.ads;

import java.security.SecureRandom;

/* JADX INFO: loaded from: classes.dex */
public final class zzazl {
    private static final ThreadLocal<SecureRandom> zzdon = new zzazm();

    /* JADX INFO: Access modifiers changed from: private */
    public static SecureRandom zzaar() {
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextLong();
        return secureRandom;
    }

    public static byte[] zzbh(int i) {
        byte[] bArr = new byte[i];
        zzdon.get().nextBytes(bArr);
        return bArr;
    }
}
