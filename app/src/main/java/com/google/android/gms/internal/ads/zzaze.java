package com.google.android.gms.internal.ads;

import java.security.Provider;
import javax.crypto.Mac;

/* JADX INFO: loaded from: classes.dex */
public final class zzaze implements zzayz<Mac> {
    @Override // com.google.android.gms.internal.ads.zzayz
    public final /* synthetic */ Mac zzb(String str, Provider provider) {
        return provider == null ? Mac.getInstance(str) : Mac.getInstance(str, provider);
    }
}
