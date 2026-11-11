package com.google.android.gms.internal.ads;

import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.interfaces.ECPrivateKey;
import java.security.interfaces.ECPublicKey;
import java.security.spec.ECPoint;
import java.security.spec.ECPrivateKeySpec;

/* JADX INFO: loaded from: classes.dex */
final class zzava implements zzaug<zzaue> {
    zzava() {
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzf, reason: merged with bridge method [inline-methods] */
    public final zzaue zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzaws zzawsVarZzx = zzaws.zzx(zzbahVar);
            if (!(zzawsVarZzx instanceof zzaws)) {
                throw new GeneralSecurityException("expected EciesAeadHkdfPrivateKey proto");
            }
            zzaws zzawsVar = zzawsVarZzx;
            zzazq.zzj(zzawsVar.getVersion(), 0);
            zzavh.zza(zzawsVar.zzxz().zzxs());
            zzawq zzawqVarZzxs = zzawsVar.zzxz().zzxs();
            zzaww zzawwVarZzxu = zzawqVarZzxs.zzxu();
            zzayv zzayvVarZza = zzavh.zza(zzawwVarZzxu.zzyh());
            byte[] byteArray = zzawsVar.zzwv().toByteArray();
            return new zzayo((ECPrivateKey) zzayy.zzdof.zzek("EC").generatePrivate(new ECPrivateKeySpec(new BigInteger(1, byteArray), zzayt.zza(zzayvVarZza))), zzawwVarZzxu.zzyj().toByteArray(), zzavh.zza(zzawwVarZzxu.zzyi()), zzavh.zza(zzawqVarZzxs.zzxw()), new zzavj(zzawqVarZzxs.zzxv().zzxp()));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized EciesAeadHkdfPrivateKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzaue zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzaws)) {
            throw new GeneralSecurityException("expected EciesAeadHkdfPrivateKey proto");
        }
        zzaws zzawsVar = (zzaws) zzbcuVar;
        zzazq.zzj(zzawsVar.getVersion(), 0);
        zzavh.zza(zzawsVar.zzxz().zzxs());
        zzawq zzawqVarZzxs = zzawsVar.zzxz().zzxs();
        zzaww zzawwVarZzxu = zzawqVarZzxs.zzxu();
        zzayv zzayvVarZza = zzavh.zza(zzawwVarZzxu.zzyh());
        byte[] byteArray = zzawsVar.zzwv().toByteArray();
        return new zzayo((ECPrivateKey) zzayy.zzdof.zzek("EC").generatePrivate(new ECPrivateKeySpec(new BigInteger(1, byteArray), zzayt.zza(zzayvVarZza))), zzawwVarZzxu.zzyj().toByteArray(), zzavh.zza(zzawwVarZzxu.zzyi()), zzavh.zza(zzawqVarZzxs.zzxw()), new zzavj(zzawqVarZzxs.zzxv().zzxp()));
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzawo.zzw(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("invalid EciesAeadHkdf key format", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzawo)) {
            throw new GeneralSecurityException("expected EciesAeadHkdfKeyFormat proto");
        }
        zzawo zzawoVar = (zzawo) zzbcuVar;
        zzavh.zza(zzawoVar.zzxs());
        KeyPair keyPairZza = zzayt.zza(zzayt.zza(zzavh.zza(zzawoVar.zzxs().zzxu().zzyh())));
        ECPublicKey eCPublicKey = (ECPublicKey) keyPairZza.getPublic();
        ECPrivateKey eCPrivateKey = (ECPrivateKey) keyPairZza.getPrivate();
        ECPoint w = eCPublicKey.getW();
        return zzaws.zzya().zzar(0).zzb(zzawu.zzye().zzas(0).zzc(zzawoVar.zzxs()).zzac(zzbah.zzo(w.getAffineX().toByteArray())).zzad(zzbah.zzo(w.getAffineY().toByteArray())).zzadi()).zzy(zzbah.zzo(eCPrivateKey.getS().toByteArray())).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.EciesAeadHkdfPrivateKey").zzai(((zzaws) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.ASYMMETRIC_PRIVATE).zzadi();
    }
}
