package com.google.android.gms.internal.measurement;

import com.facebook.R;

/* JADX INFO: loaded from: classes.dex */
public final class zzabn extends zzabd<zzabn> {
    private static volatile zzabn[] zzcaf;
    public String zzcag = "";

    public zzabn() {
        this.zzbzh = null;
        this.zzbzs = -1;
    }

    public static zzabn[] zzwh() {
        if (zzcaf == null) {
            synchronized (zzabh.zzbzr) {
                if (zzcaf == null) {
                    zzcaf = new zzabn[0];
                }
            }
        }
        return zzcaf;
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    protected final int zza() {
        int iZza = super.zza();
        return (this.zzcag == null || this.zzcag.equals("")) ? iZza : iZza + zzabb.zzd(1, this.zzcag);
    }

    @Override // com.google.android.gms.internal.measurement.zzabd, com.google.android.gms.internal.measurement.zzabj
    public final void zza(zzabb zzabbVar) throws zzabc {
        if (this.zzcag != null && !this.zzcag.equals("")) {
            zzabbVar.zzc(1, this.zzcag);
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
                default:
                    if (!super.zza(zzabaVar, iZzvo)) {
                    }
                    break;
            }
        }
        return this;
    }
}
