package com.google.android.gms.internal.ads;

import java.security.interfaces.ECPrivateKey;

/* JADX INFO: loaded from: classes.dex */
public final class zzayo implements zzaue {
    private static final byte[] zzdhv = new byte[0];
    private final ECPrivateKey zzdnc;
    private final zzayq zzdnd;
    private final String zzdne;
    private final byte[] zzdnf;
    private final zzayw zzdng;
    private final zzayn zzdnh;

    public zzayo(ECPrivateKey eCPrivateKey, byte[] bArr, String str, zzayw zzaywVar, zzayn zzaynVar) {
        this.zzdnc = eCPrivateKey;
        this.zzdnd = new zzayq(eCPrivateKey);
        this.zzdnf = bArr;
        this.zzdne = str;
        this.zzdng = zzaywVar;
        this.zzdnh = zzaynVar;
    }
}
