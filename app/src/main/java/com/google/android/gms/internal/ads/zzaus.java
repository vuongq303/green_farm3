package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.util.logging.Logger;

/* JADX INFO: loaded from: classes.dex */
class zzaus implements zzaug<zzatz> {
    private static final Logger logger = Logger.getLogger(zzaus.class.getName());

    zzaus() {
        zzauo.zza("type.googleapis.com/google.crypto.tink.AesCtrKey", new zzaut());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @Override // com.google.android.gms.internal.ads.zzaug
    /* JADX INFO: renamed from: zzd, reason: merged with bridge method [inline-methods] */
    public final zzatz zza(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            zzavo zzavoVarZzi = zzavo.zzi(zzbahVar);
            if (!(zzavoVarZzi instanceof zzavo)) {
                throw new GeneralSecurityException("expected AesCtrHmacAeadKey proto");
            }
            zzavo zzavoVar = zzavoVarZzi;
            zzazq.zzj(zzavoVar.getVersion(), 0);
            return new zzayx((zzazi) zzauo.zzb("type.googleapis.com/google.crypto.tink.AesCtrKey", zzavoVar.zzwn()), (zzauk) zzauo.zzb("type.googleapis.com/google.crypto.tink.HmacKey", zzavoVar.zzwo()), zzavoVar.zzwo().zzym().zzyt());
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesCtrHmacAeadKey proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final int getVersion() {
        return 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final /* synthetic */ zzatz zza(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzavo)) {
            throw new GeneralSecurityException("expected AesCtrHmacAeadKey proto");
        }
        zzavo zzavoVar = (zzavo) zzbcuVar;
        zzazq.zzj(zzavoVar.getVersion(), 0);
        return new zzayx((zzazi) zzauo.zzb("type.googleapis.com/google.crypto.tink.AesCtrKey", zzavoVar.zzwn()), (zzauk) zzauo.zzb("type.googleapis.com/google.crypto.tink.HmacKey", zzavoVar.zzwo()), zzavoVar.zzwo().zzym().zzyt());
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbah zzbahVar) throws GeneralSecurityException {
        try {
            return zzb(zzavq.zzj(zzbahVar));
        } catch (zzbbu e) {
            throw new GeneralSecurityException("expected serialized AesCtrHmacAeadKeyFormat proto", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzbcu zzb(zzbcu zzbcuVar) throws GeneralSecurityException {
        if (!(zzbcuVar instanceof zzavq)) {
            throw new GeneralSecurityException("expected AesCtrHmacAeadKeyFormat proto");
        }
        zzavq zzavqVar = (zzavq) zzbcuVar;
        zzavs zzavsVar = (zzavs) zzauo.zza("type.googleapis.com/google.crypto.tink.AesCtrKey", zzavqVar.zzwr());
        return zzavo.zzwp().zzb(zzavsVar).zzb((zzaxc) zzauo.zza("type.googleapis.com/google.crypto.tink.HmacKey", zzavqVar.zzws())).zzal(0).zzadi();
    }

    @Override // com.google.android.gms.internal.ads.zzaug
    public final zzaxi zzc(zzbah zzbahVar) {
        return zzaxi.zzyz().zzeb("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey").zzai(((zzavo) zzb(zzbahVar)).zzaav()).zzb(zzaxi.zzb.SYMMETRIC).zzadi();
    }
}
