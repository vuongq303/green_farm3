package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzauz implements zzaug<zzatz> {
    zzauz() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzatz zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzaxz zzaxzVarZzal = zzaxz.zzal(zzbahVar);
            if (!(zzaxzVarZzal instanceof zzaxz)) {
                throw new GeneralSecurityException("expected KmsEnvelopeAeadKey proto");
            }
            zzaxz zzaxzVar = zzaxzVarZzal;
            zzazq.zzj(zzaxzVar.getVersion(), 0);
            String strZzaah = zzaxzVar.zzaae().zzaah();
            return new zzauy(zzaxzVar.zzaae().zzaai(), zzauj.zzdx(strZzaah).zzdw(strZzaah));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized KmSEnvelopeAeadKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzaxz)) {
            throw new GeneralSecurityException("expected KmsEnvelopeAeadKey proto");
        }
        zzaxz zzaxzVar = (zzaxz) zzbcuVar;
        zzazq.zzj(zzaxzVar.getVersion(), 0);
        String strZzaah = zzaxzVar.zzaae().zzaah();
        return new zzauy(zzaxzVar.zzaae().zzaai(), zzauj.zzdx(strZzaah).zzdw(strZzaah));
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzayb.zzam(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized KmsEnvelopeAeadKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzayb)) {
            throw new GeneralSecurityException("expected KmsEnvelopeAeadKeyFormat proto");
        }
        return zzaxz.zzaaf().zzb((zzayb) zzbcuVar).zzbf(0).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.KmsEnvelopeAeadKey").zzai(((zzaxz) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.REMOTE).zzadi();
    }
}
