package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;

/* JADX INFO: loaded from: classes.dex */
final class zzauu implements zzaug<zzatz> {
    zzauu() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzatz zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzavy zzavyVarZzo = zzavy.zzo(zzbahVar);
            if (!(zzavyVarZzo instanceof zzavy)) {
                throw new GeneralSecurityException("expected AesEaxKey proto");
            }
            zzavy zzavyVar = zzavyVarZzo;
            zzazq.zzj(zzavyVar.getVersion(), 0);
            zzazq.zzbi(zzavyVar.zzwv().size());
            if (zzavyVar.zzxe().zzxb() == 12 || zzavyVar.zzxe().zzxb() == 16) {
                return new zzayi(zzavyVar.zzwv().toByteArray(), zzavyVar.zzxe().zzxb());
            }
            throw new GeneralSecurityException("invalid IV size; acceptable values have 12 or 16 bytes");
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesEaxKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzavy)) {
            throw new GeneralSecurityException("expected AesEaxKey proto");
        }
        zzavy zzavyVar = (zzavy) zzbcuVar;
        zzazq.zzj(zzavyVar.getVersion(), 0);
        zzazq.zzbi(zzavyVar.zzwv().size());
        if (zzavyVar.zzxe().zzxb() == 12 || zzavyVar.zzxe().zzxb() == 16) {
            return new zzayi(zzavyVar.zzwv().toByteArray(), zzavyVar.zzxe().zzxb());
        }
        throw new GeneralSecurityException("invalid IV size; acceptable values have 12 or 16 bytes");
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzawa.zzq(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesEaxKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzawa)) {
            throw new GeneralSecurityException("expected AesEaxKeyFormat proto");
        }
        zzawa zzawaVar = (zzawa) zzbcuVar;
        zzazq.zzbi(zzawaVar.getKeySize());
        if (zzawaVar.zzxe().zzxb() == 12 || zzawaVar.zzxe().zzxb() == 16) {
            return zzavy.zzxf().zzp(zzbah.zzo(zzazl.zzbh(zzawaVar.getKeySize()))).zzb(zzawaVar.zzxe()).zzan(0).zzadi();
        }
        throw new GeneralSecurityException("invalid IV size; acceptable values have 12 or 16 bytes");
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.AesEaxKey").zzai(((zzavy) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.SYMMETRIC).zzadi();
    }
}
