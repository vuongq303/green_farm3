package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzauv implements zzaug<zzatz> {
    zzauv() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzatz zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzawe zzaweVarZzr = zzawe.zzr(zzbahVar);
            if (!(zzaweVarZzr instanceof zzawe)) {
                throw new GeneralSecurityException("expected AesGcmKey proto");
            }
            zzawe zzaweVar = zzaweVarZzr;
            zzazq.zzj(zzaweVar.getVersion(), 0);
            zzazq.zzbi(zzaweVar.zzwv().size());
            return new zzayj(zzaweVar.zzwv().toByteArray());
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected AesGcmKey proto");
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzawe)) {
            throw new GeneralSecurityException("expected AesGcmKey proto");
        }
        zzawe zzaweVar = (zzawe) zzbcuVar;
        zzazq.zzj(zzaweVar.getVersion(), 0);
        zzazq.zzbi(zzaweVar.zzwv().size());
        return new zzayj(zzaweVar.zzwv().toByteArray());
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzawg.zzt(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesGcmKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzawg)) {
            throw new GeneralSecurityException("expected AesGcmKeyFormat proto");
        }
        zzawg zzawgVar = (zzawg) zzbcuVar;
        zzazq.zzbi(zzawgVar.getKeySize());
        return zzawe.zzxk().zzs(zzbah.zzo(zzazl.zzbh(zzawgVar.getKeySize()))).zzao(0).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.AesGcmKey").zzai(((zzawe) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.SYMMETRIC).zzadi();
    }
}
