package com.google.android.gms.internal.ads;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public abstract class zzgq {
    private static MessageDigest zzaix = null;
    protected Object mLock = new Object();

    protected final MessageDigest zzhg() {
        MessageDigest messageDigest;
        synchronized (this.mLock) {
            if (zzaix != null) {
                messageDigest = zzaix;
            } else {
                for (int i = 0; i < 2; i++) {
                    try {
                        zzaix = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                    }
                }
                messageDigest = zzaix;
            }
        }
        return messageDigest;
    }

    abstract byte[] zzx(String str);
}
