package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzavb implements zzaug<zzauf> {
    zzavb() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzg, reason: merged with bridge method [inline-methods] */
    public final zzauf zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzawu zzawuVarZzab = zzawu.zzab(zzbahVar);
            if (!(zzawuVarZzab instanceof zzawu)) {
                throw new GeneralSecurityException("expected EciesAeadHkdfPublicKey proto");
            }
            zzawu zzawuVar = zzawuVarZzab;
            zzazq.zzj(zzawuVar.getVersion(), 0);
            zzavh.zza(zzawuVar.zzxs());
            zzawq zzawqVarZzxs = zzawuVar.zzxs();
            zzaww zzawwVarZzxu = zzawqVarZzxs.zzxu();
            return new zzayp(zzayt.zza(zzavh.zza(zzawwVarZzxu.zzyh()), zzawuVar.zzyc().toByteArray(), zzawuVar.zzyd().toByteArray()), zzawwVarZzxu.zzyj().toByteArray(), zzavh.zza(zzawwVarZzxu.zzyi()), zzavh.zza(zzawqVarZzxs.zzxw()), new zzavj(zzawqVarZzxs.zzxv().zzxp()));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized EciesAeadHkdfPublicKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzauf zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzawu)) {
            throw new GeneralSecurityException("expected EciesAeadHkdfPublicKey proto");
        }
        zzawu zzawuVar = (zzawu) zzbcuVar;
        zzazq.zzj(zzawuVar.getVersion(), 0);
        zzavh.zza(zzawuVar.zzxs());
        zzawq zzawqVarZzxs = zzawuVar.zzxs();
        zzaww zzawwVarZzxu = zzawqVarZzxs.zzxu();
        return new zzayp(zzayt.zza(zzavh.zza(zzawwVarZzxu.zzyh()), zzawuVar.zzyc().toByteArray(), zzawuVar.zzyd().toByteArray()), zzawwVarZzxu.zzyj().toByteArray(), zzavh.zza(zzawwVarZzxu.zzyi()), zzavh.zza(zzawqVarZzxs.zzxw()), new zzavj(zzawqVarZzxs.zzxv().zzxp()));
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        throw new GeneralSecurityException("Not implemented.");
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        throw new GeneralSecurityException("Not implemented.");
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) throws GeneralSecurityException {
        throw new GeneralSecurityException("Not implemented.");
    }
}
