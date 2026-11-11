package com.google.android.gms.internal.measurement;

import com.facebook.R;
import com.gameloft.adsmanager.JavaUtils;

/* JADX INFO: loaded from: classes.dex */
public final class zzabo extends zzabd<zzabo> {
    public String zzcag = "";
    public String zzcah = "";
    public long zzcai = 0;
    public String zzcaj = "";
    public long zzcak = 0;
    public long zzrp = 0;
    private String zzbsl = "";
    public String zzbsm = "";
    public String zzbsp = "";
    public String zzbsn = "";
    public String zzbso = "";
    public int zzcal = 0;
    public zzabn[] zzcam = zzabn.zzwh();

    public zzabo() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        if (this.zzcag != null && !this.zzcag.equals("")) {
            iZza += zzabb.zzd(1, this.zzcag);
        }
        if (this.zzcah != null && !this.zzcah.equals("")) {
            iZza += zzabb.zzd(2, this.zzcah);
        }
        if (this.zzcai != 0) {
            iZza += zzabb.zzc(3, this.zzcai);
        }
        if (this.zzcaj != null && !this.zzcaj.equals("")) {
            iZza += zzabb.zzd(4, this.zzcaj);
        }
        if (this.zzcak != 0) {
            iZza += zzabb.zzc(5, this.zzcak);
        }
        if (this.zzrp != 0) {
            iZza += zzabb.zzc(6, this.zzrp);
        }
        if (this.zzbsl != null && !this.zzbsl.equals("")) {
            iZza += zzabb.zzd(7, this.zzbsl);
        }
        if (this.zzbsm != null && !this.zzbsm.equals("")) {
            iZza += zzabb.zzd(8, this.zzbsm);
        }
        if (this.zzbsp != null && !this.zzbsp.equals("")) {
            iZza += zzabb.zzd(9, this.zzbsp);
        }
        if (this.zzbsn != null && !this.zzbsn.equals("")) {
            iZza += zzabb.zzd(10, this.zzbsn);
        }
        if (this.zzbso != null && !this.zzbso.equals("")) {
            iZza += zzabb.zzd(11, this.zzbso);
        }
        if (this.zzcal != 0) {
            iZza += zzabb.zzf(12, this.zzcal);
        }
        if (this.zzcam == null || this.zzcam.length <= 0) {
            return iZza;
        }
        int iZzb = iZza;
        for (int i = 0; i < this.zzcam.length; i++) {
            zzabn zzabnVar = this.zzcam[i];
            if (zzabnVar != null) {
                iZzb += zzabb.zzb(13, zzabnVar);
            }
        }
        return iZzb;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzcag != null && !this.zzcag.equals("")) {
            zzabbVar.zzc(1, this.zzcag);
        }
        if (this.zzcah != null && !this.zzcah.equals("")) {
            zzabbVar.zzc(2, this.zzcah);
        }
        if (this.zzcai != 0) {
            zzabbVar.zzb(3, this.zzcai);
        }
        if (this.zzcaj != null && !this.zzcaj.equals("")) {
            zzabbVar.zzc(4, this.zzcaj);
        }
        if (this.zzcak != 0) {
            zzabbVar.zzb(5, this.zzcak);
        }
        if (this.zzrp != 0) {
            zzabbVar.zzb(6, this.zzrp);
        }
        if (this.zzbsl != null && !this.zzbsl.equals("")) {
            zzabbVar.zzc(7, this.zzbsl);
        }
        if (this.zzbsm != null && !this.zzbsm.equals("")) {
            zzabbVar.zzc(8, this.zzbsm);
        }
        if (this.zzbsp != null && !this.zzbsp.equals("")) {
            zzabbVar.zzc(9, this.zzbsp);
        }
        if (this.zzbsn != null && !this.zzbsn.equals("")) {
            zzabbVar.zzc(10, this.zzbsn);
        }
        if (this.zzbso != null && !this.zzbso.equals("")) {
            zzabbVar.zzc(11, this.zzbso);
        }
        if (this.zzcal != 0) {
            zzabbVar.zze(12, this.zzcal);
        }
        if (this.zzcam != null && this.zzcam.length > 0) {
            for (int i = 0; i < this.zzcam.length; i++) {
                zzabn zzabnVar = this.zzcam[i];
                if (zzabnVar != null) {
                    zzabbVar.zza(13, zzabnVar);
                }
            }
        }
        super.zza(zzabbVar);
    }

    @Override // com.google.android.gms.internal.measurement.zzabj
    public final /* synthetic */ zzabj zzb(zzaba zzabaVar) throws zzabi {
        while (true) {
            int iZzvo = zzabaVar.zzvo();
            switch (iZzvo) {
                case 0:
                    break;
                case R.styleable.CardView_contentPaddingRight /* 10 */:
                    this.zzcag = zzabaVar.readString();
                    break;
                case 18:
                    this.zzcah = zzabaVar.readString();
                    break;
                case 24:
                    this.zzcai = zzabaVar.zzvp();
                    break;
                case 34:
                    this.zzcaj = zzabaVar.readString();
                    break;
                case 40:
                    this.zzcak = zzabaVar.zzvp();
                    break;
                case 48:
                    this.zzrp = zzabaVar.zzvp();
                    break;
                case 58:
                    this.zzbsl = zzabaVar.readString();
                    break;
                case 66:
                    this.zzbsm = zzabaVar.readString();
                    break;
                case 74:
                    this.zzbsp = zzabaVar.readString();
                    break;
                case 82:
                    this.zzbsn = zzabaVar.readString();
                    break;
                case JavaUtils.Constants.TABLET_HEIGHT_DP /* 90 */:
                    this.zzbso = zzabaVar.readString();
                    break;
                case 96:
                    this.zzcal = zzabaVar.zzvq();
                    break;
                case 106:
                    int iZzb = zzabm.zzb(zzabaVar, 106);
                    int length = this.zzcam == null ? 0 : this.zzcam.length;
                    zzabn[] zzabnVarArr = new zzabn[iZzb + length];
                    if (length != 0) {
                        System.arraycopy(this.zzcam, 0, zzabnVarArr, 0, length);
                    }
                    while (length < zzabnVarArr.length - 1) {
                        zzabnVarArr[length] = new zzabn();
                        zzabaVar.zza(zzabnVarArr[length]);
                        zzabaVar.zzvo();
                        length++;
                    }
                    zzabnVarArr[length] = new zzabn();
                    zzabaVar.zza(zzabnVarArr[length]);
                    this.zzcam = zzabnVarArr;
                    break;
                default:
                    if (!super.zza(zzabaVar, iZzvo)) {
                    }
                    break;
            }
        }
        return this;
    }
}
