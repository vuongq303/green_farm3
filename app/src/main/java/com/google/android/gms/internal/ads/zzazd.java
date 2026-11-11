package com.google.android.gms.internal.ads;

import java.security.KeyPairGenerator;
import java.security.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class zzazd implements zzayz<KeyPairGenerator> {
    @Override // com.google.android.gms.internal.ads.zzayz
    public final /* synthetic */ KeyPairGenerator zzb(String str, Provider provider) {
        return provider == null ? KeyPairGenerator.getInstance(str) : KeyPairGenerator.getInstance(str, provider);
    }
}
