package com.google.android.gms.internal.ads;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import java.security.interfaces.ECPublicKey;

/* JADX INFO: loaded from: classes.dex */
public final class zzayp implements zzauf {
    private static final byte[] zzdhv = new byte[0];
    private final String zzdne;
    private final byte[] zzdnf;
    private final zzayw zzdng;
    private final zzayn zzdnh;
    private final zzayr zzdni;

    public zzayp(ECPublicKey eCPublicKey, byte[] bArr, String str, zzayw zzaywVar, zzayn zzaynVar) throws GeneralSecurityException {
        zzayt.zza(eCPublicKey.getW(), eCPublicKey.getParams().getCurve());
        this.zzdni = new zzayr(eCPublicKey);
        this.zzdnf = bArr;
        this.zzdne = str;
        this.zzdng = zzaywVar;
        this.zzdnh = zzaynVar;
    }

    @Override // com.google.android.gms.internal.ads.zzauf
    public final byte[] zzc(byte[] bArr, byte[] bArr2) throws GeneralSecurityException {
        zzays zzaysVarZza = this.zzdni.zza(this.zzdne, this.zzdnf, bArr2, this.zzdnh.zzwm(), this.zzdng);
        byte[] bArrZzc = this.zzdnh.zzi(zzaysVarZza.zzaaq()).zzc(bArr, zzdhv);
        byte[] bArrZzaap = zzaysVarZza.zzaap();
        return ByteBuffer.allocate(bArrZzaap.length + bArrZzc.length).put(bArrZzaap).put(bArrZzc).array();
    }
}
