package com.google.android.gms.internal.ads;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzafz {
    private final int errorCode;
    private final String type;
    private String url;
    private final String zzcdi;
    private final List<String> zzcjd;
    private final String zzcje;
    private final String zzcjf;
    private final boolean zzcjg;
    private final String zzcjh;
    private final boolean zzcji;
    private final JSONObject zzcjj;

    public zzafz(int i, Map<String, String> map) {
        this.url = map.get("url");
        this.zzcje = map.get("base_uri");
        this.zzcjf = map.get("post_parameters");
        this.zzcjg = parseBoolean(map.get("drt_include"));
        this.zzcdi = map.get("request_id");
        this.type = map.get("type");
        this.zzcjd = zzbz(map.get("errors"));
        this.errorCode = i;
        this.zzcjh = map.get("fetched_ad");
        this.zzcji = parseBoolean(map.get("render_test_ad_label"));
        this.zzcjj = new JSONObject();
    }

    public zzafz(JSONObject jSONObject) {
        this.url = jSONObject.optString("url");
        this.zzcje = jSONObject.optString("base_uri");
        this.zzcjf = jSONObject.optString("post_parameters");
        this.zzcjg = parseBoolean(jSONObject.optString("drt_include"));
        this.zzcdi = jSONObject.optString("request_id");
        this.type = jSONObject.optString("type");
        this.zzcjd = zzbz(jSONObject.optString("errors"));
        this.errorCode = jSONObject.optInt("valid", 0) == 1 ? -2 : 1;
        this.zzcjh = jSONObject.optString("fetched_ad");
        this.zzcji = jSONObject.optBoolean("render_test_ad_label");
        JSONObject jSONObjectOptJSONObject = jSONObject.optJSONObject("preprocessor_flags");
        this.zzcjj = jSONObjectOptJSONObject == null ? new JSONObject() : jSONObjectOptJSONObject;
    }

    private static boolean parseBoolean(String str) {
        return str != null && (str.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES) || str.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE));
    }

    private static List<String> zzbz(String str) {
        if (str == null) {
            return null;
        }
        return Arrays.asList(str.split(","));
    }

    public final int getErrorCode() {
        return this.errorCode;
    }

    public final String getType() {
        return this.type;
    }

    public final String getUrl() {
        return this.url;
    }

    public final void setUrl(String str) {
        this.url = str;
    }

    public final List<String> zzoh() {
        return this.zzcjd;
    }

    public final String zzoi() {
        return this.zzcje;
    }

    public final String zzoj() {
        return this.zzcjf;
    }

    public final boolean zzok() {
        return this.zzcjg;
    }

    public final String zzol() {
        return this.zzcdi;
    }

    public final String zzom() {
        return this.zzcjh;
    }

    public final boolean zzon() {
        return this.zzcji;
    }
}
