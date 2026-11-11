package com.google.android.gms.ads.internal.gmsg;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajb;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzami;
import com.google.android.gms.internal.ads.zzapw;
import com.google.android.gms.internal.ads.zzaqc;
import com.google.android.gms.internal.ads.zzaqd;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarr;
import com.google.android.gms.internal.ads.zzarz;
import com.google.android.gms.internal.ads.zzasa;
import com.google.android.gms.internal.ads.zzasb;
import com.google.android.gms.internal.ads.zzci;
import com.google.android.gms.internal.ads.zzcj;
import com.google.android.gms.internal.ads.zzue;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzf {
    private static final zzv<Object> bAd = new j();
    public static final zzv<zzaqw> zzblo = e.bAe;
    public static final zzv<zzaqw> zzblp = f.bAe;
    public static final zzv<zzaqw> zzblq = g.bAe;
    public static final zzv<zzaqw> zzblr = new l();
    public static final zzv<zzaqw> zzbls = new m();
    public static final zzv<zzaqw> zzblt = h.bAe;
    public static final zzv<Object> zzblu = new n();
    public static final zzv<zzaqw> zzblv = new o();
    public static final zzv<zzaqw> zzblw = i.bAe;
    public static final zzv<zzaqw> zzblx = new p();
    public static final zzv<zzaqw> zzbly = new q();
    public static final zzv<zzapw> zzblz = new zzaqc();
    public static final zzv<zzapw> zzbma = new zzaqd();
    public static final zzv<zzaqw> zzbmb = new zze();
    public static final zzaf zzbmc = new zzaf();
    public static final zzv<zzaqw> zzbmd = new r();
    public static final zzv<zzaqw> zzbme = new s();
    public static final zzv<zzaqw> zzbmf = new k();

    static final /* synthetic */ void a(zzarr zzarrVar, Map map) {
        String str = (String) map.get("u");
        if (str == null) {
            zzakb.zzdk("URL missing from httpTrack GMSG.");
        } else {
            new zzami(zzarrVar.getContext(), ((zzasa) zzarrVar).zztq().zzcw, str).zzqo();
        }
    }

    static final /* synthetic */ void a(zzarz zzarzVar, Map map) {
        String str = (String) map.get("tx");
        String str2 = (String) map.get("ty");
        String str3 = (String) map.get("td");
        try {
            int i = Integer.parseInt(str);
            int i2 = Integer.parseInt(str2);
            int i3 = Integer.parseInt(str3);
            zzci zzciVarZzui = zzarzVar.zzui();
            if (zzciVarZzui != null) {
                zzciVarZzui.zzaa().zza(i, i2, i3);
            }
        } catch (NumberFormatException e) {
            zzakb.zzdk("Could not parse touch parameters from gmsg.");
        }
    }

    static final /* synthetic */ void a(zzue zzueVar, Map map) {
        String str = (String) map.get("u");
        if (str == null) {
            zzakb.zzdk("URL missing from click GMSG.");
            return;
        }
        Uri uriZza = Uri.parse(str);
        try {
            zzci zzciVarZzui = ((zzarz) zzueVar).zzui();
            uriZza = (zzciVarZzui == null || !zzciVarZzui.zzb(uriZza)) ? uriZza : zzciVarZzui.zza(uriZza, ((zzarr) zzueVar).getContext(), ((zzasb) zzueVar).getView(), ((zzarr) zzueVar).zzto());
        } catch (zzcj e) {
            String strValueOf = String.valueOf(str);
            zzakb.zzdk(strValueOf.length() != 0 ? "Unable to append parameter to URL: ".concat(strValueOf) : new String("Unable to append parameter to URL: "));
        }
        new zzami(((zzarr) zzueVar).getContext(), ((zzasa) zzueVar).zztq().zzcw, zzajb.zzb(uriZza, ((zzarr) zzueVar).getContext()).toString()).zzqo();
    }

    static final /* synthetic */ void b(zzarr zzarrVar, Map map) {
        Intent intent;
        PackageManager packageManager = zzarrVar.getContext().getPackageManager();
        try {
            try {
                JSONArray jSONArray = new JSONObject((String) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA)).getJSONArray("intents");
                JSONObject jSONObject = new JSONObject();
                for (int i = 0; i < jSONArray.length(); i++) {
                    try {
                        JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                        String strOptString = jSONObject2.optString(ShareConstants.WEB_DIALOG_PARAM_ID);
                        String strOptString2 = jSONObject2.optString("u");
                        String strOptString3 = jSONObject2.optString("i");
                        String strOptString4 = jSONObject2.optString("m");
                        String strOptString5 = jSONObject2.optString("p");
                        String strOptString6 = jSONObject2.optString("c");
                        jSONObject2.optString("f");
                        jSONObject2.optString("e");
                        String strOptString7 = jSONObject2.optString("intent_url");
                        if (TextUtils.isEmpty(strOptString7)) {
                            intent = null;
                        } else {
                            try {
                                intent = Intent.parseUri(strOptString7, 0);
                            } catch (URISyntaxException e) {
                                String strValueOf = String.valueOf(strOptString7);
                                zzakb.zzb(strValueOf.length() != 0 ? "Error parsing the url: ".concat(strValueOf) : new String("Error parsing the url: "), e);
                                intent = null;
                            }
                        }
                        if (intent == null) {
                            intent = new Intent();
                            if (!TextUtils.isEmpty(strOptString2)) {
                                intent.setData(Uri.parse(strOptString2));
                            }
                            if (!TextUtils.isEmpty(strOptString3)) {
                                intent.setAction(strOptString3);
                            }
                            if (!TextUtils.isEmpty(strOptString4)) {
                                intent.setType(strOptString4);
                            }
                            if (!TextUtils.isEmpty(strOptString5)) {
                                intent.setPackage(strOptString5);
                            }
                            if (!TextUtils.isEmpty(strOptString6)) {
                                String[] strArrSplit = strOptString6.split("/", 2);
                                if (strArrSplit.length == 2) {
                                    intent.setComponent(new ComponentName(strArrSplit[0], strArrSplit[1]));
                                }
                            }
                        }
                        try {
                            jSONObject.put(strOptString, packageManager.resolveActivity(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) != null);
                        } catch (JSONException e2) {
                            zzakb.zzb("Error constructing openable urls response.", e2);
                        }
                    } catch (JSONException e3) {
                        zzakb.zzb("Error parsing the intent data.", e3);
                    }
                }
                ((zzue) zzarrVar).zza("openableIntents", jSONObject);
            } catch (JSONException e4) {
                ((zzue) zzarrVar).zza("openableIntents", new JSONObject());
            }
        } catch (JSONException e5) {
            ((zzue) zzarrVar).zza("openableIntents", new JSONObject());
        }
    }

    static final /* synthetic */ void c(zzarr zzarrVar, Map map) {
        String str = (String) map.get("urls");
        if (TextUtils.isEmpty(str)) {
            zzakb.zzdk("URLs missing in canOpenURLs GMSG.");
            return;
        }
        String[] strArrSplit = str.split(",");
        HashMap map2 = new HashMap();
        PackageManager packageManager = zzarrVar.getContext().getPackageManager();
        for (String str2 : strArrSplit) {
            String[] strArrSplit2 = str2.split(";", 2);
            map2.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(strArrSplit2.length > 1 ? strArrSplit2[1].trim() : "android.intent.action.VIEW", Uri.parse(strArrSplit2[0].trim())), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) != null));
        }
        ((zzue) zzarrVar).zza("openableURLs", map2);
    }
}
