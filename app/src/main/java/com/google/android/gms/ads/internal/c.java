package com.google.android.gms.ads.internal;

import com.google.android.gms.internal.ads.zzanj;
import com.google.android.gms.internal.ads.zzano;
import com.google.android.gms.internal.ads.zzanz;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
final /* synthetic */ class c implements zzanj {
    static final zzanj zzxn = new c();

    private c() {
    }

    @Override // com.google.android.gms.internal.ads.zzanj
    public final zzanz zzc(Object obj) throws JSONException {
        JSONObject jSONObject = (JSONObject) obj;
        if (jSONObject.optBoolean("isSuccessful", false)) {
            zzbv.zzeo().zzqh().zzcs(jSONObject.getString("appSettingsJson"));
        }
        return zzano.zzi(null);
    }
}
