package com.google.android.gms.internal.ads;

import com.facebook.share.internal.ShareConstants;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzwq<I, O> implements zzwf<I, O> {
    private final zzvf zzbrh;
    private final zzwh<O> zzbri;
    private final zzwi<I> zzbrj;
    private final String zzbrk;

    zzwq(zzvf zzvfVar, String str, zzwi<I> zzwiVar, zzwh<O> zzwhVar) {
        this.zzbrh = zzvfVar;
        this.zzbrk = str;
        this.zzbrj = zzwiVar;
        this.zzbri = zzwhVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(zzvs zzvsVar, zzwb zzwbVar, I i, zzaoj<O> zzaojVar) {
        try {
            com.google.android.gms.ads.internal.zzbv.zzek();
            String strZzrh = zzakk.zzrh();
            com.google.android.gms.ads.internal.gmsg.zzf.zzbmc.zza(strZzrh, new zzwt(this, zzvsVar, zzaojVar));
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(ShareConstants.WEB_DIALOG_PARAM_ID, strZzrh);
            jSONObject.put("args", this.zzbrj.zzg(i));
            zzwbVar.zzb(this.zzbrk, jSONObject);
        } catch (Exception e) {
            try {
                zzaojVar.setException(e);
                zzakb.zzb("Unable to invokeJavaScript", e);
            } finally {
                zzvsVar.release();
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzanj
    public final zzanz<O> zzc(@Nullable I i) {
        return zzf(i);
    }

    @Override // com.google.android.gms.internal.ads.zzwf
    public final zzanz<O> zzf(I i) {
        zzaoj zzaojVar = new zzaoj();
        zzvs zzvsVarZzb = this.zzbrh.zzb((zzci) null);
        zzvsVarZzb.zza(new zzwr(this, zzvsVarZzb, i, zzaojVar), new zzws(this, zzaojVar, zzvsVarZzb));
        return zzaojVar;
    }
}
