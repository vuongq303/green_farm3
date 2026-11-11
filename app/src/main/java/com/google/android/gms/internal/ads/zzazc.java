package com.google.android.gms.internal.ads;

import java.security.KeyFactory;
import java.security.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class zzazc implements zzayz<KeyFactory> {
    @Override // com.google.android.gms.internal.ads.zzayz
    public final /* synthetic */ KeyFactory zzb(String str, Provider provider) {
        return provider == null ? KeyFactory.getInstance(str) : KeyFactory.getInstance(str, provider);
    }
}
