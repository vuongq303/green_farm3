package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzauw implements zzaug<zzatz> {
    zzauw() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzatz zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzawi zzawiVarZzu = zzawi.zzu(zzbahVar);
            if (!(zzawiVarZzu instanceof zzawi)) {
                throw new GeneralSecurityException("expected ChaCha20Poly1305Key proto");
            }
            zzawi zzawiVar = zzawiVarZzu;
            zzazq.zzj(zzawiVar.getVersion(), 0);
            if (zzawiVar.zzwv().size() != 32) {
                throw new GeneralSecurityException("invalid ChaCha20Poly1305Key: incorrect key length");
            }
            return new zzaym(zzawiVar.zzwv().toByteArray());
        } catch (zzbbu e) {
            throw new GeneralSecurityException("invalid ChaCha20Poly1305 key", e);
        }
    }

    private static zzawi zzwl() {
        return zzawi.zzxn().zzap(0).zzv(zzbah.zzo(zzazl.zzbh(32))).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzawi)) {
            throw new GeneralSecurityException("expected ChaCha20Poly1305Key proto");
        }
        zzawi zzawiVar = (zzawi) zzbcuVar;
        zzazq.zzj(zzawiVar.getVersion(), 0);
        if (zzawiVar.zzwv().size() != 32) {
            throw new GeneralSecurityException("invalid ChaCha20Poly1305Key: incorrect key length");
        }
        return new zzaym(zzawiVar.zzwv().toByteArray());
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) {
        return zzwl();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) {
        return zzwl();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.ChaCha20Poly1305Key").zzai(zzwl().zzaav()).zzb(zzaxi.zzb.SYMMETRIC).zzadi();
    }
}
