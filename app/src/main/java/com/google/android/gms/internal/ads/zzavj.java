package com.google.android.gms.internal.ads;

import java.security.GeneralSecurityException;
import java.util.Arrays;

/* JADX INFO: loaded from: classes.dex */
final class zzavj implements zzayn {
    private final String zzdic;
    private final int zzdid;
    private zzawe zzdie;
    private zzavo zzdif;
    private int zzdig;

    zzavj(zzaxn zzaxnVar) throws GeneralSecurityException {
        this.zzdic = zzaxnVar.zzyw();
        if (this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesGcmKey")) {
            try {
                zzawg zzawgVarZzt = zzawg.zzt(zzaxnVar.zzyx());
                this.zzdie = (zzawe) zzauo.zzb(zzaxnVar);
                this.zzdid = zzawgVarZzt.getKeySize();
                return;
            } catch (zzbbu e) {
                throw new GeneralSecurityException("invalid KeyFormat protobuf, expected AesGcmKeyFormat", e);
            }
        }
        if (!this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")) {
            String strValueOf = String.valueOf(this.zzdic);
            throw new GeneralSecurityException(strValueOf.length() != 0 ? "unsupported AEAD DEM key type: ".concat(strValueOf) : new String("unsupported AEAD DEM key type: "));
        }
        try {
            zzavq zzavqVarZzj = zzavq.zzj(zzaxnVar.zzyx());
            this.zzdif = (zzavo) zzauo.zzb(zzaxnVar);
            this.zzdig = zzavqVarZzj.zzwr().getKeySize();
            this.zzdid = zzavqVarZzj.zzws().getKeySize() + this.zzdig;
        } catch (zzbbu e2) {
            throw new GeneralSecurityException("invalid KeyFormat protobuf, expected AesGcmKeyFormat", e2);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzayn
    public final zzatz zzi(byte[] bArr) throws GeneralSecurityException {
        if (bArr.length != this.zzdid) {
            throw new GeneralSecurityException("Symmetric key has incorrect length");
        }
        if (this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesGcmKey")) {
            return (zzatz) zzauo.zzb(this.zzdic, zzawe.zzxk().zza(this.zzdie).zzs(zzbah.zzc(bArr, 0, this.zzdid)).zzadi());
        }
        if (!this.zzdic.equals("type.googleapis.com/google.crypto.tink.AesCtrHmacAeadKey")) {
            throw new GeneralSecurityException("unknown DEM key type");
        }
        byte[] bArrCopyOfRange = Arrays.copyOfRange(bArr, 0, this.zzdig);
        byte[] bArrCopyOfRange2 = Arrays.copyOfRange(bArr, this.zzdig, this.zzdid);
        zzavs zzavsVarZzadi = zzavs.zzww().zza(this.zzdif.zzwn()).zzm(zzbah.zzo(bArrCopyOfRange)).zzadi();
        return (zzatz) zzauo.zzb(this.zzdic, zzavo.zzwp().zzal(this.zzdif.getVersion()).zzb(zzavsVarZzadi).zzb(zzaxc.zzyn().zza(this.zzdif.zzwo()).zzaf(zzbah.zzo(bArrCopyOfRange2)).zzadi()).zzadi());
    }

    @Override // com.google.android.gms.internal.ads.zzayn
    public final int zzwm() {
        return this.zzdid;
    }
}
