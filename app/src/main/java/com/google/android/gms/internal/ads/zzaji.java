package com.google.android.gms.internal.ads;

import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaji {
    public final int errorCode;
    public final zzjn zzacv;
    public final zzaef zzcgs;
    public final JSONObject zzcob;
    public final zzwy zzcod;
    public final long zzcoh;
    public final long zzcoi;
    public final zzhs zzcoq;
    public final boolean zzcor;
    public final zzaej zzcos;

    public zzaji(zzaef zzaefVar, zzaej zzaejVar, zzwy zzwyVar, zzjn zzjnVar, int i, long j, long j2, JSONObject jSONObject, zzhs zzhsVar, Boolean bool) {
        this.zzcgs = zzaefVar;
        this.zzcos = zzaejVar;
        this.zzcod = zzwyVar;
        this.zzacv = zzjnVar;
        this.errorCode = i;
        this.zzcoh = j;
        this.zzcoi = j2;
        this.zzcob = jSONObject;
        this.zzcoq = zzhsVar;
        if (bool != null) {
            this.zzcor = bool.booleanValue();
        } else if (zzamm.zzo(zzaefVar.zzccv)) {
            this.zzcor = true;
        } else {
            this.zzcor = false;
        }
    }

    public zzaji(zzaef zzaefVar, zzaej zzaejVar, zzwy zzwyVar, zzjn zzjnVar, int i, long j, long j2, JSONObject jSONObject, zzhx zzhxVar) {
        this.zzcgs = zzaefVar;
        this.zzcos = zzaejVar;
        this.zzcod = null;
        this.zzacv = null;
        this.errorCode = i;
        this.zzcoh = j;
        this.zzcoi = j2;
        this.zzcob = null;
        this.zzcoq = new zzhs(zzhxVar);
        this.zzcor = false;
    }
}
