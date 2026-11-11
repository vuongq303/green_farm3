package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzaux implements zzaug<zzatz> {
    zzaux() {
    }

    private static zzatz zzc(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzaxv)) {
            throw new GeneralSecurityException("expected KmsAeadKey proto");
        }
        zzaxv zzaxvVar = (zzaxv) zzbcuVar;
        zzazq.zzj(zzaxvVar.getVersion(), 0);
        String strZzaab = zzaxvVar.zzzy().zzaab();
        return zzauj.zzdx(strZzaab).zzdw(strZzaab);
    }

    private static zzatz zzd(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzc(zzaxv.zzaj(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected KmsAeadKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbah zzbahVar) {
        return zzd(zzbahVar);
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbcu zzbcuVar) {
        return zzc(zzbcuVar);
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzaxx.zzak(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized KmsAeadKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzaxx)) {
            throw new GeneralSecurityException("expected KmsAeadKeyFormat proto");
        }
        return zzaxv.zzzz().zzb((zzaxx) zzbcuVar).zzbe(0).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.KmsAeadKey").zzai(((zzaxv) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.REMOTE).zzadi();
    }
}
