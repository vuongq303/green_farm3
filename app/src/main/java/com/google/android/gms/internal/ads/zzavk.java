package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import javax.crypto.spec.SecretKeySpec;

/* JADX INFO: loaded from: classes.dex */
final class zzavk implements zzaug<zzauk> {
    zzavk() {
    }

    private static void zza(zzaxg zzaxgVar) throws GeneralSecurityException {
        if (zzaxgVar.zzyt() < 10) {
            throw new GeneralSecurityException("tag size too small");
        }
        switch (zzaxgVar.zzys()) {
            case SHA1:
                if (zzaxgVar.zzyt() > 20) {
                    throw new GeneralSecurityException("tag size too big");
                }
                return;
            case SHA256:
                if (zzaxgVar.zzyt() > 32) {
                    throw new GeneralSecurityException("tag size too big");
                }
                return;
            case SHA512:
                if (zzaxgVar.zzyt() > 64) {
                    throw new GeneralSecurityException("tag size too big");
                }
                return;
            default:
                throw new GeneralSecurityException("unknown hash type");
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzh, reason: merged with bridge method [inline-methods] */
    public final zzauk zza(zzbah zzbahVar) throws GeneralSecurityException {
        zzazj zzazjVar;
        try {
            zzaxc zzaxcVarZzae = zzaxc.zzae(zzbahVar);
            if (!(zzaxcVarZzae instanceof zzaxc)) {
                throw new GeneralSecurityException("expected HmacKey proto");
            }
            zzaxc zzaxcVar = zzaxcVarZzae;
            zzazq.zzj(zzaxcVar.getVersion(), 0);
            if (zzaxcVar.zzwv().size() < 16) {
                throw new GeneralSecurityException("key too short");
            }
            zza(zzaxcVar.zzym());
            zzaxa zzaxaVarZzys = zzaxcVar.zzym().zzys();
            SecretKeySpec secretKeySpec = new SecretKeySpec(zzaxcVar.zzwv().toByteArray(), "HMAC");
            int iZzyt = zzaxcVar.zzym().zzyt();
            switch (zzaxaVarZzys) {
                case SHA1:
                    zzazjVar = new zzazj("HMACSHA1", secretKeySpec, iZzyt);
                    break;
                case SHA256:
                    zzazjVar = new zzazj("HMACSHA256", secretKeySpec, iZzyt);
                    break;
                case SHA512:
                    zzazjVar = new zzazj("HMACSHA512", secretKeySpec, iZzyt);
                    break;
                default:
                    throw new GeneralSecurityException("unknown hash");
            }
            return zzazjVar;
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized HmacKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzauk zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzaxc)) {
            throw new GeneralSecurityException("expected HmacKey proto");
        }
        zzaxc zzaxcVar = (zzaxc) zzbcuVar;
        zzazq.zzj(zzaxcVar.getVersion(), 0);
        if (zzaxcVar.zzwv().size() < 16) {
            throw new GeneralSecurityException("key too short");
        }
        zza(zzaxcVar.zzym());
        zzaxa zzaxaVarZzys = zzaxcVar.zzym().zzys();
        SecretKeySpec secretKeySpec = new SecretKeySpec(zzaxcVar.zzwv().toByteArray(), "HMAC");
        int iZzyt = zzaxcVar.zzym().zzyt();
        switch (zzaxaVarZzys) {
            case SHA1:
                return new zzazj("HMACSHA1", secretKeySpec, iZzyt);
            case SHA256:
                return new zzazj("HMACSHA256", secretKeySpec, iZzyt);
            case SHA512:
                return new zzazj("HMACSHA512", secretKeySpec, iZzyt);
            default:
                throw new GeneralSecurityException("unknown hash");
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzaxe.zzag(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized HmacKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzaxe)) {
            throw new GeneralSecurityException("expected HmacKeyFormat proto");
        }
        zzaxe zzaxeVar = (zzaxe) zzbcuVar;
        if (zzaxeVar.getKeySize() < 16) {
            throw new GeneralSecurityException("key too short");
        }
        zza(zzaxeVar.zzym());
        return zzaxc.zzyn().zzav(0).zzc(zzaxeVar.zzym()).zzaf(zzbah.zzo(zzazl.zzbh(zzaxeVar.getKeySize()))).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.HmacKey").zzai(((zzaxc) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.SYMMETRIC).zzadi();
    }
}
