package com.google.android.gms.internal.ads;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzxg {
    private static String zza(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str3)) {
            str3 = "";
        }
        return str.replaceAll(str2, str3);
    }

    public static List<String> zza(JSONObject jSONObject, String str) {
        JSONArray jSONArrayOptJSONArray = jSONObject.optJSONArray(str);
        if (jSONArrayOptJSONArray == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(jSONArrayOptJSONArray.length());
        for (int i = 0; i < jSONArrayOptJSONArray.length(); i++) {
            arrayList.add(jSONArrayOptJSONArray.getString(i));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public static void zza(Context context, String str, zzajh zzajhVar, String str2, boolean z, List<String> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        String str3 = z ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO;
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String strZza = zza(zza(zza(zza(zza(zza(zza(it.next(), "@gw_adlocid@", str2), "@gw_adnetrefresh@", str3), "@gw_qdata@", zzajhVar.zzcod.zzbst), "@gw_sdkver@", str), "@gw_sessid@", zzkb.zzih()), "@gw_seqnum@", zzajhVar.zzccy), "@gw_adnetstatus@", zzajhVar.zzcoe);
            if (zzajhVar.zzbtw != null) {
                strZza = zza(zza(strZza, "@gw_adnetid@", zzajhVar.zzbtw.zzbrs), "@gw_allocid@", zzajhVar.zzbtw.zzbru);
            }
            String strZzb = zzajb.zzb(strZza, context);
            com.google.android.gms.ads.internal.zzbv.zzek();
            zzakk.zzd(context, str, strZzb);
        }
    }

    public static void zza(Context context, String str, List<String> list, String str2, zzaig zzaigVar) {
        if (list == null || list.isEmpty()) {
            return;
        }
        if (!TextUtils.isEmpty(str2) && zzamy.isEnabled()) {
            str2 = "fakeUserForAdDebugLog";
        }
        long jCurrentTimeMillis = com.google.android.gms.ads.internal.zzbv.zzer().currentTimeMillis();
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String strZza = zza(zza(it.next(), "@gw_rwd_userid@", Uri.encode(str2)), "@gw_tmstmp@", Long.toString(jCurrentTimeMillis));
            if (zzaigVar != null) {
                strZza = zza(zza(strZza, "@gw_rwd_itm@", Uri.encode(zzaigVar.type)), "@gw_rwd_amt@", Integer.toString(zzaigVar.zzcmk));
            }
            com.google.android.gms.ads.internal.zzbv.zzek();
            zzakk.zzd(context, str, strZza);
        }
    }

    public static boolean zza(String str, int[] iArr) {
        if (TextUtils.isEmpty(str) || iArr.length != 2) {
            return false;
        }
        String[] strArrSplit = str.split("x");
        if (strArrSplit.length != 2) {
            return false;
        }
        try {
            iArr[0] = Integer.parseInt(strArrSplit[0]);
            iArr[1] = Integer.parseInt(strArrSplit[1]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
