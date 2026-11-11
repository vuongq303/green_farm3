package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzaut implements zzaug<zzazi> {
    zzaut() {
    }

    private static void zza(zzavw zzavwVar) throws GeneralSecurityException {
        if (zzavwVar.zzxb() < 12 || zzavwVar.zzxb() > 16) {
            throw new GeneralSecurityException("invalid IV size");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zze, reason: merged with bridge method [inline-methods] */
    public final zzayh zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzavs zzavsVarZzl = zzavs.zzl(zzbahVar);
            if (!(zzavsVarZzl instanceof zzavs)) {
                throw new GeneralSecurityException("expected AesCtrKey proto");
            }
            zzavs zzavsVar = zzavsVarZzl;
            zzazq.zzj(zzavsVar.getVersion(), 0);
            zzazq.zzbi(zzavsVar.zzwv().size());
            zza(zzavsVar.zzwu());
            return new zzayh(zzavsVar.zzwv().toByteArray(), zzavsVar.zzwu().zzxb());
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesCtrKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzazi zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzavs)) {
            throw new GeneralSecurityException("expected AesCtrKey proto");
        }
        zzavs zzavsVar = (zzavs) zzbcuVar;
        zzazq.zzj(zzavsVar.getVersion(), 0);
        zzazq.zzbi(zzavsVar.zzwv().size());
        zza(zzavsVar.zzwu());
        return new zzayh(zzavsVar.zzwv().toByteArray(), zzavsVar.zzwu().zzxb());
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzavu.zzn(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesCtrKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzavu)) {
            throw new GeneralSecurityException("expected AesCtrKeyFormat proto");
        }
        zzavu zzavuVar = (zzavu) zzbcuVar;
        zzazq.zzbi(zzavuVar.getKeySize());
        zza(zzavuVar.zzwu());
        return zzavs.zzww().zzc(zzavuVar.zzwu()).zzm(zzbah.zzo(zzazl.zzbh(zzavuVar.getKeySize()))).zzam(0).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.AesCtrKey").zzai(((zzavs) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.SYMMETRIC).zzadi();
    }
}
