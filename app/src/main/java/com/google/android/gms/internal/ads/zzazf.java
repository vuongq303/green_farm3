package com.google.android.gms.internal.ads;

import java.security.MessageDigest;
import java.security.Provider;

/* JADX INFO: loaded from: classes.dex */
public final class zzazf implements zzayz<MessageDigest> {
    @Override // com.google.android.gms.internal.ads.zzayz
    public final /* synthetic */ MessageDigest zzb(String str, Provider provider) {
        return provider == null ? MessageDigest.getInstance(str) : MessageDigest.getInstance(str, provider);
    }
}
