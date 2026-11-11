package com.google.android.gms.internal.ads;

import android.net.Uri;
import android.text.TextUtils;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzafx {
    private String zzbhy;
    private final zzaef zzbuc;
    private List<String> zzcab;
    private String zzcae;
    private String zzchw;
    private String zzchx;
    private List<String> zzchy;
    private String zzchz;
    private String zzcia;
    private String zzcib;
    private List<String> zzcic;
    private List<String> zzcid;
    private zzaig zzcip;
    private List<String> zzciq;
    private List<String> zzcir;
    private String zzciu;
    private List<String> zzciv;
    private boolean zzciw;
    private String zzcix;
    private zzaiq zzciy;
    private boolean zzciz;
    private boolean zzcja;
    private boolean zzcjb;
    private boolean zzcjc;
    private zzael zzxe;
    private long zzcie = -1;
    private boolean zzcif = false;
    private final long zzcig = -1;
    private long zzcih = -1;
    private int mOrientation = -1;
    private boolean zzcii = false;
    private boolean zzcij = false;
    private boolean zzcik = false;
    private boolean zzcil = true;
    private boolean zzcim = true;
    private String zzcin = "";
    private boolean zzcio = false;
    private boolean zzbtn = false;
    private boolean zzcis = false;
    private boolean zzcit = false;

    public zzafx(zzaef zzaefVar, String str) {
        this.zzchx = str;
        this.zzbuc = zzaefVar;
    }

    private static String zza(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    private static long zzb(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list != null && !list.isEmpty()) {
            String str2 = list.get(0);
            try {
                return (long) (Float.parseFloat(str2) * 1000.0f);
            } catch (NumberFormatException e) {
                zzakb.zzdk(new StringBuilder(String.valueOf(str).length() + 36 + String.valueOf(str2).length()).append("Could not parse float from ").append(str).append(" header: ").append(str2).toString());
            }
        }
        return -1L;
    }

    private static List<String> zzc(Map<String, List<String>> map, String str) {
        String str2;
        List<String> list = map.get(str);
        if (list == null || list.isEmpty() || (str2 = list.get(0)) == null) {
            return null;
        }
        return Arrays.asList(str2.trim().split("\\s+"));
    }

    private static boolean zzd(Map<String, List<String>> map, String str) {
        List<String> list = map.get(str);
        if (list == null || list.isEmpty()) {
            return false;
        }
        return Boolean.valueOf(list.get(0)).booleanValue();
    }

    public final zzaej zza(long j, boolean z) {
        return new zzaej(this.zzbuc, this.zzchx, this.zzbhy, this.zzchy, this.zzcic, this.zzcie, this.zzcif, -1L, this.zzcab, this.zzcih, this.mOrientation, this.zzchw, j, this.zzcia, this.zzcib, this.zzcii, this.zzcij, this.zzcik, this.zzcil, false, this.zzcin, this.zzcio, this.zzbtn, this.zzcip, this.zzciq, this.zzcir, this.zzcis, this.zzxe, this.zzcit, this.zzciu, this.zzciv, this.zzciw, this.zzcix, this.zzciy, this.zzchz, this.zzcim, this.zzciz, this.zzcja, z ? 2 : 1, this.zzcjb, this.zzcid, this.zzcjc, this.zzcae);
    }

    public final void zza(String str, Map<String, List<String>> map, String str2) {
        this.zzbhy = str2;
        zzl(map);
    }

    public final void zzl(Map<String, List<String>> map) {
        this.zzchw = zza(map, "X-Afma-Ad-Size");
        this.zzcix = zza(map, "X-Afma-Ad-Slot-Size");
        List<String> listZzc = zzc(map, "X-Afma-Click-Tracking-Urls");
        if (listZzc != null) {
            this.zzchy = listZzc;
        }
        this.zzchz = zza(map, "X-Afma-Debug-Signals");
        List<String> list = map.get("X-Afma-Debug-Dialog");
        if (list != null && !list.isEmpty()) {
            this.zzcia = list.get(0);
        }
        List<String> listZzc2 = zzc(map, "X-Afma-Tracking-Urls");
        if (listZzc2 != null) {
            this.zzcic = listZzc2;
        }
        List<String> listZzc3 = zzc(map, "X-Afma-Downloaded-Impression-Urls");
        if (listZzc3 != null) {
            this.zzcid = listZzc3;
        }
        long jZzb = zzb(map, "X-Afma-Interstitial-Timeout");
        if (jZzb != -1) {
            this.zzcie = jZzb;
        }
        this.zzcif |= zzd(map, "X-Afma-Mediation");
        List<String> listZzc4 = zzc(map, "X-Afma-Manual-Tracking-Urls");
        if (listZzc4 != null) {
            this.zzcab = listZzc4;
        }
        long jZzb2 = zzb(map, "X-Afma-Refresh-Rate");
        if (jZzb2 != -1) {
            this.zzcih = jZzb2;
        }
        List<String> list2 = map.get("X-Afma-Orientation");
        if (list2 != null && !list2.isEmpty()) {
            String str = list2.get(0);
            if ("portrait".equalsIgnoreCase(str)) {
                this.mOrientation = com.google.android.gms.ads.internal.zzbv.zzem().zzrm();
            } else if ("landscape".equalsIgnoreCase(str)) {
                this.mOrientation = com.google.android.gms.ads.internal.zzbv.zzem().zzrl();
            }
        }
        this.zzcib = zza(map, "X-Afma-ActiveView");
        List<String> list3 = map.get("X-Afma-Use-HTTPS");
        if (list3 != null && !list3.isEmpty()) {
            this.zzcik = Boolean.valueOf(list3.get(0)).booleanValue();
        }
        this.zzcii |= zzd(map, "X-Afma-Custom-Rendering-Allowed");
        this.zzcij = AnalyticsEvents.PARAMETER_SHARE_DIALOG_SHOW_NATIVE.equals(zza(map, "X-Afma-Ad-Format"));
        List<String> list4 = map.get("X-Afma-Content-Url-Opted-Out");
        if (list4 != null && !list4.isEmpty()) {
            this.zzcil = Boolean.valueOf(list4.get(0)).booleanValue();
        }
        List<String> list5 = map.get("X-Afma-Content-Vertical-Opted-Out");
        if (list5 != null && !list5.isEmpty()) {
            this.zzcim = Boolean.valueOf(list5.get(0)).booleanValue();
        }
        List<String> list6 = map.get("X-Afma-Gws-Query-Id");
        if (list6 != null && !list6.isEmpty()) {
            this.zzcin = list6.get(0);
        }
        String strZza = zza(map, "X-Afma-Fluid");
        if (strZza != null && strZza.equals("height")) {
            this.zzcio = true;
        }
        this.zzbtn = "native_express".equals(zza(map, "X-Afma-Ad-Format"));
        this.zzcip = zzaig.zzce(zza(map, "X-Afma-Rewards"));
        if (this.zzciq == null) {
            this.zzciq = zzc(map, "X-Afma-Reward-Video-Start-Urls");
        }
        if (this.zzcir == null) {
            this.zzcir = zzc(map, "X-Afma-Reward-Video-Complete-Urls");
        }
        this.zzcis |= zzd(map, "X-Afma-Use-Displayed-Impression");
        this.zzcit |= zzd(map, "X-Afma-Auto-Collect-Location");
        this.zzciu = zza(map, "Set-Cookie");
        String strZza2 = zza(map, "X-Afma-Auto-Protection-Configuration");
        if (strZza2 == null || TextUtils.isEmpty(strZza2)) {
            Uri.Builder builderBuildUpon = Uri.parse("https://pagead2.googlesyndication.com/pagead/gen_204").buildUpon();
            builderBuildUpon.appendQueryParameter(ShareConstants.WEB_DIALOG_PARAM_ID, "gmob-apps-blocked-navigation");
            if (!TextUtils.isEmpty(this.zzcia)) {
                builderBuildUpon.appendQueryParameter("debugDialog", this.zzcia);
            }
            boolean zBooleanValue = ((Boolean) zzkb.zzik().zzd(zznk.zzaum)).booleanValue();
            String string = builderBuildUpon.toString();
            this.zzxe = new zzael(zBooleanValue, Arrays.asList(new StringBuilder(String.valueOf(string).length() + 31).append(string).append("&navigationURL={NAVIGATION_URL}").toString()));
        } else {
            try {
                this.zzxe = zzael.zzl(new JSONObject(strZza2));
            } catch (JSONException e) {
                zzakb.zzc("Error parsing configuration JSON", e);
                this.zzxe = new zzael();
            }
        }
        List<String> listZzc5 = zzc(map, "X-Afma-Remote-Ping-Urls");
        if (listZzc5 != null) {
            this.zzciv = listZzc5;
        }
        String strZza3 = zza(map, "X-Afma-Safe-Browsing");
        if (!TextUtils.isEmpty(strZza3)) {
            try {
                this.zzciy = zzaiq.zzo(new JSONObject(strZza3));
            } catch (JSONException e2) {
                zzakb.zzc("Error parsing safe browsing header", e2);
            }
        }
        this.zzciw |= zzd(map, "X-Afma-Render-In-Browser");
        String strZza4 = zza(map, "X-Afma-Pool");
        if (!TextUtils.isEmpty(strZza4)) {
            try {
                this.zzciz = new JSONObject(strZza4).getBoolean("never_pool");
            } catch (JSONException e3) {
                zzakb.zzc("Error parsing interstitial pool header", e3);
            }
        }
        this.zzcja = zzd(map, "X-Afma-Custom-Close-Blocked");
        this.zzcjb = zzd(map, "X-Afma-Enable-Omid");
        this.zzcjc = zzd(map, "X-Afma-Disable-Closable-Area");
        this.zzcae = zza(map, "X-Afma-Omid-Settings");
    }
}
