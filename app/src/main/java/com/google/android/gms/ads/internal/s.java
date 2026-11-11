package com.google.android.gms.ads.internal;

import android.os.Bundle;
import com.google.android.gms.internal.ads.zzaef;
import com.google.android.gms.internal.ads.zzaeg;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzano;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzpb;
import java.util.concurrent.Callable;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final class s implements Callable<zzpb> {
    private final /* synthetic */ zzbc bBN;
    private final /* synthetic */ int bBO;
    private final /* synthetic */ JSONArray bBP;
    private final /* synthetic */ int bBQ;
    private final /* synthetic */ zzaji zzwg;

    s(zzbc zzbcVar, int i, JSONArray jSONArray, int i2, zzaji zzajiVar) {
        this.bBN = zzbcVar;
        this.bBO = i;
        this.bBP = jSONArray;
        this.bBQ = i2;
        this.zzwg = zzajiVar;
    }

    @Override // java.util.concurrent.Callable
    public final /* synthetic */ zzpb call() throws JSONException {
        if (this.bBO >= this.bBP.length()) {
            return null;
        }
        JSONArray jSONArray = new JSONArray();
        jSONArray.put(this.bBP.get(this.bBO));
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("ads", jSONArray);
        zzbc zzbcVar = new zzbc(this.bBN.zzvw.zzrt, this.bBN.zzwc, this.bBN.zzvw.zzacv, this.bBN.zzvw.zzacp, this.bBN.zzwh, this.bBN.zzvw.zzacr, true);
        zzbc zzbcVar2 = this.bBN;
        zzbc.a(this.bBN.zzvw, zzbcVar.zzvw);
        zzbcVar.WZ();
        zzbcVar.zza(this.bBN.zzvs);
        zznx zznxVar = zzbcVar.zzvr;
        int i = this.bBO;
        zznxVar.zze("num_ads_requested", String.valueOf(this.bBQ));
        zznxVar.zze("ad_index", String.valueOf(i));
        zzaef zzaefVar = this.zzwg.zzcgs;
        String string = jSONObject.toString();
        Bundle bundle = zzaefVar.zzccv.extras != null ? new Bundle(zzaefVar.zzccv.extras) : new Bundle();
        bundle.putString("_ad", string);
        zzbcVar.zza(new zzaeg(zzaefVar.zzccu, new zzjj(zzaefVar.zzccv.versionCode, zzaefVar.zzccv.zzapw, bundle, zzaefVar.zzccv.zzapx, zzaefVar.zzccv.zzapy, zzaefVar.zzccv.zzapz, zzaefVar.zzccv.zzaqa, zzaefVar.zzccv.zzaqb, zzaefVar.zzccv.zzaqc, zzaefVar.zzccv.zzaqd, zzaefVar.zzccv.zzaqe, zzaefVar.zzccv.zzaqf, zzaefVar.zzccv.zzaqg, zzaefVar.zzccv.zzaqh, zzaefVar.zzccv.zzaqi, zzaefVar.zzccv.zzaqj, zzaefVar.zzccv.zzaqk, zzaefVar.zzccv.zzaql), zzaefVar.zzacv, zzaefVar.zzacp, zzaefVar.applicationInfo, zzaefVar.zzccw, zzaefVar.zzccy, zzaefVar.zzccz, zzaefVar.zzacr, zzaefVar.zzcda, zzaefVar.zzads, zzaefVar.zzcdk, zzaefVar.zzcdc, zzaefVar.zzcdd, zzaefVar.zzcde, zzaefVar.zzcdf, zzaefVar.zzagu, zzaefVar.zzcdg, zzaefVar.zzcdh, zzaefVar.zzcdi, zzaefVar.zzcdj, zzaefVar.zzaco, zzaefVar.zzadj, zzaefVar.zzcdm, zzaefVar.zzcdn, zzaefVar.zzcdt, zzaefVar.zzcdo, zzaefVar.zzcdp, zzaefVar.zzcdq, zzaefVar.zzcdr, zzano.zzi(zzaefVar.zzcds), zzaefVar.zzcdu, zzaefVar.zzbss, zzaefVar.zzcdv, zzaefVar.zzcdw, zzaefVar.zzcdx, zzaefVar.zzadl, zzaefVar.zzcdy, zzaefVar.zzcdz, zzaefVar.zzced, zzano.zzi(zzaefVar.zzccx), zzaefVar.zzadn, zzaefVar.zzcee, zzaefVar.zzcef, 1, zzaefVar.zzceh, zzaefVar.zzcei, zzaefVar.zzcej, zzaefVar.zzcek), zzbcVar.zzvr);
        return zzbcVar.Xa().get();
    }
}
