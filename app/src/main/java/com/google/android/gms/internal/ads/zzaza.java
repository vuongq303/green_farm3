package com.google.android.gms.internal.ads;

import java.security.Provider;
import javax.crypto.Cipher;

/* JADX INFO: loaded from: classes.dex */
public final class zzaza implements zzayz<Cipher> {
    @Override // com.google.android.gms.internal.ads.zzayz
    public final /* synthetic */ Cipher zzb(String str, Provider provider) {
        return provider == null ? Cipher.getInstance(str) : Cipher.getInstance(str, provider);
    }
}
