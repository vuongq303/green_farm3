package com.google.android.gms.internal.ads;

import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
public final /* synthetic */ class zzup {
    public static void zza(zzuo zzuoVar, String str, String str2) {
        zzuoVar.zzbe(new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length()).append(str).append("(").append(str2).append(");").toString());
    }

    public static void zza(zzuo zzuoVar, String str, Map map) {
        try {
            zzuoVar.zza(str, com.google.android.gms.ads.internal.zzbv.zzek().zzn(map));
        } catch (JSONException e) {
            zzakb.zzdk("Could not convert parameters to JSON.");
        }
    }

    public static void zza(zzuo zzuoVar, String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        zzuoVar.zzf(str, jSONObject.toString());
    }

    public static void zzb(zzuo zzuoVar, String str, JSONObject jSONObject) {
        if (jSONObject == null) {
            jSONObject = new JSONObject();
        }
        String string = jSONObject.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("(window.AFMA_ReceiveMessage || function() {})('");
        sb.append(str);
        sb.append("'");
        sb.append(",");
        sb.append(string);
        sb.append(");");
        String strValueOf = String.valueOf(sb.toString());
        zzakb.zzck(strValueOf.length() != 0 ? "Dispatching AFMA event: ".concat(strValueOf) : new String("Dispatching AFMA event: "));
        zzuoVar.zzbe(sb.toString());
    }
}
