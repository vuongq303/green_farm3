package com.google.android.gms.internal.ads;

import android.content.Context;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzacq implements zzacm<zzaqw> {
    private final Context mContext;
    private String zzaae;
    private final zzci zzbjc;
    private final com.google.android.gms.ads.internal.zzbc zzcbc;
    private zzanz<zzaqw> zzcbm;
    private final com.google.android.gms.ads.internal.gmsg.zzab zzcbn;
    private final zzpe zzcbo;
    private final zzang zzzw;

    public zzacq(Context context, com.google.android.gms.ads.internal.zzbc zzbcVar, String str, zzci zzciVar, zzang zzangVar) {
        zzakb.zzdj("Webview loading for native ads.");
        this.mContext = context;
        this.zzcbc = zzbcVar;
        this.zzbjc = zzciVar;
        this.zzzw = zzangVar;
        this.zzaae = str;
        com.google.android.gms.ads.internal.zzbv.zzel();
        zzanz<zzaqw> zzanzVarZza = zzarc.zza(this.mContext, this.zzzw, (String) zzkb.zzik().zzd(zznk.zzbbp), this.zzbjc, this.zzcbc.zzbi());
        this.zzcbn = new com.google.android.gms.ads.internal.gmsg.zzab(this.mContext);
        this.zzcbo = new zzpe(zzbcVar, str);
        this.zzcbm = zzano.zza(zzanzVarZza, new zzanj(this) { // from class: com.google.android.gms.internal.ads.zzacr
            private final zzacq zzcbp;

            {
                this.zzcbp = this;
            }

            @Override // com.google.android.gms.internal.ads.zzanj
            public final zzanz zzc(Object obj) {
                return this.zzcbp.zzh((zzaqw) obj);
            }
        }, zzaoe.zzcvz);
        zzanm.zza(this.zzcbm, "WebViewNativeAdsUtil.constructor");
    }

    final /* synthetic */ zzanz zza(JSONObject jSONObject, zzaqw zzaqwVar) throws JSONException {
        jSONObject.put("ads_id", this.zzaae);
        zzaqwVar.zzb("google.afma.nativeAds.handleDownloadedImpressionPing", jSONObject);
        return zzano.zzi(new JSONObject());
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final void zza(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super zzaqw> zzvVar) {
        zzano.zza(this.zzcbm, new zzacx(this, str, zzvVar), zzaoe.zzcvy);
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final void zza(String str, JSONObject jSONObject) {
        zzano.zza(this.zzcbm, new zzacz(this, str, jSONObject), zzaoe.zzcvy);
    }

    final /* synthetic */ zzanz zzb(JSONObject jSONObject, zzaqw zzaqwVar) throws JSONException {
        jSONObject.put("ads_id", this.zzaae);
        zzaqwVar.zzb("google.afma.nativeAds.handleImpressionPing", jSONObject);
        return zzano.zzi(new JSONObject());
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final void zzb(String str, com.google.android.gms.ads.internal.gmsg.zzv<? super zzaqw> zzvVar) {
        zzano.zza(this.zzcbm, new zzacy(this, str, zzvVar), zzaoe.zzcvy);
    }

    final /* synthetic */ zzanz zzc(JSONObject jSONObject, zzaqw zzaqwVar) throws JSONException {
        jSONObject.put("ads_id", this.zzaae);
        zzaqwVar.zzb("google.afma.nativeAds.handleClickGmsg", jSONObject);
        return zzano.zzi(new JSONObject());
    }

    final /* synthetic */ zzanz zzd(JSONObject jSONObject, zzaqw zzaqwVar) throws JSONException {
        jSONObject.put("ads_id", this.zzaae);
        zzaoj zzaojVar = new zzaoj();
        zzaqwVar.zza("/nativeAdPreProcess", new zzacw(this, zzaqwVar, zzaojVar));
        zzaqwVar.zzb("google.afma.nativeAds.preProcessJsonGmsg", jSONObject);
        return zzaojVar;
    }

    final /* synthetic */ zzanz zzh(zzaqw zzaqwVar) {
        zzakb.zzdj("Javascript has loaded for native ads.");
        zzaqwVar.zzuf().zza(this.zzcbc, this.zzcbc, this.zzcbc, this.zzcbc, this.zzcbc, false, null, new com.google.android.gms.ads.internal.zzx(this.mContext, null, null), null, null);
        zzaqwVar.zza("/logScionEvent", this.zzcbn);
        zzaqwVar.zza("/logScionEvent", this.zzcbo);
        return zzano.zzi(zzaqwVar);
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final zzanz<JSONObject> zzh(final JSONObject jSONObject) {
        return zzano.zza(this.zzcbm, new zzanj(this, jSONObject) { // from class: com.google.android.gms.internal.ads.zzacs
            private final JSONObject zzcbg;
            private final zzacq zzcbp;

            {
                this.zzcbp = this;
                this.zzcbg = jSONObject;
            }

            @Override // com.google.android.gms.internal.ads.zzanj
            public final zzanz zzc(Object obj) {
                return this.zzcbp.zzd(this.zzcbg, (zzaqw) obj);
            }
        }, zzaoe.zzcvy);
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final zzanz<JSONObject> zzi(final JSONObject jSONObject) {
        return zzano.zza(this.zzcbm, new zzanj(this, jSONObject) { // from class: com.google.android.gms.internal.ads.zzact
            private final JSONObject zzcbg;
            private final zzacq zzcbp;

            {
                this.zzcbp = this;
                this.zzcbg = jSONObject;
            }

            @Override // com.google.android.gms.internal.ads.zzanj
            public final zzanz zzc(Object obj) {
                return this.zzcbp.zzc(this.zzcbg, (zzaqw) obj);
            }
        }, zzaoe.zzcvy);
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final zzanz<JSONObject> zzj(final JSONObject jSONObject) {
        return zzano.zza(this.zzcbm, new zzanj(this, jSONObject) { // from class: com.google.android.gms.internal.ads.zzacu
            private final JSONObject zzcbg;
            private final zzacq zzcbp;

            {
                this.zzcbp = this;
                this.zzcbg = jSONObject;
            }

            @Override // com.google.android.gms.internal.ads.zzanj
            public final zzanz zzc(Object obj) {
                return this.zzcbp.zzb(this.zzcbg, (zzaqw) obj);
            }
        }, zzaoe.zzcvy);
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final zzanz<JSONObject> zzk(final JSONObject jSONObject) {
        return zzano.zza(this.zzcbm, new zzanj(this, jSONObject) { // from class: com.google.android.gms.internal.ads.zzacv
            private final JSONObject zzcbg;
            private final zzacq zzcbp;

            {
                this.zzcbp = this;
                this.zzcbg = jSONObject;
            }

            @Override // com.google.android.gms.internal.ads.zzanj
            public final zzanz zzc(Object obj) {
                return this.zzcbp.zza(this.zzcbg, (zzaqw) obj);
            }
        }, zzaoe.zzcvy);
    }

    @Override // com.google.android.gms.internal.ads.zzacm
    public final void zzmc() {
        zzano.zza(this.zzcbm, new zzada(this), zzaoe.zzcvy);
    }
}
