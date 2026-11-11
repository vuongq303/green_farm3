package com.google.android.gms.internal.ads;

import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
public final class zzamb extends zzr<zzp> {
    private final zzaoj<zzp> zzctn;
    private final Map<String, String> zzcto;
    private final zzamy zzctp;

    public zzamb(String str, zzaoj<zzp> zzaojVar) {
        this(str, null, zzaojVar);
    }

    private zzamb(String str, Map<String, String> map, zzaoj<zzp> zzaojVar) {
        super(0, str, new zzamc(zzaojVar));
        this.zzcto = null;
        this.zzctn = zzaojVar;
        this.zzctp = new zzamy();
        this.zzctp.zza(str, "GET", null, null);
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // com.google.android.gms.internal.ads.zzr
    protected final zzx<zzp> zza(zzp zzpVar) {
        return zzx.zza(zzpVar, zzap.zzb(zzpVar));
    }

    @Override // com.google.android.gms.internal.ads.zzr
    protected final /* synthetic */ void zza(zzp zzpVar) {
        zzp zzpVar2 = zzpVar;
        this.zzctp.zza(zzpVar2.zzab, zzpVar2.statusCode);
        zzamy zzamyVar = this.zzctp;
        byte[] bArr = zzpVar2.data;
        if (zzamy.isEnabled() && bArr != null) {
            zzamyVar.zzf(bArr);
        }
        this.zzctn.set(zzpVar2);
    }
}
