package com.google.android.gms.internal.ads;

import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzajb {
    public static Uri zzb(Uri uri, Context context) {
        if (!com.google.android.gms.ads.internal.zzbv.zzfh().zzx(context) || !TextUtils.isEmpty(uri.getQueryParameter("fbs_aeid"))) {
            return uri;
        }
        String strZzab = com.google.android.gms.ads.internal.zzbv.zzfh().zzab(context);
        Uri uriZzb = zzb(uri.toString(), "fbs_aeid", strZzab);
        com.google.android.gms.ads.internal.zzbv.zzfh().zze(context, strZzab);
        return uriZzb;
    }

    private static Uri zzb(String str, String str2, String str3) {
        int iIndexOf = str.indexOf("&adurl");
        if (iIndexOf == -1) {
            iIndexOf = str.indexOf("?adurl");
        }
        return iIndexOf != -1 ? Uri.parse(str.substring(0, iIndexOf + 1) + str2 + "=" + str3 + "&" + str.substring(iIndexOf + 1)) : Uri.parse(str).buildUpon().appendQueryParameter(str2, str3).build();
    }

    public static String zzb(String str, Context context) {
        String strZzab;
        if (!com.google.android.gms.ads.internal.zzbv.zzfh().zzs(context) || TextUtils.isEmpty(str) || (strZzab = com.google.android.gms.ads.internal.zzbv.zzfh().zzab(context)) == null) {
            return str;
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzaxr)).booleanValue()) {
            if (str.contains("fbs_aeid")) {
                return str;
            }
            if (com.google.android.gms.ads.internal.zzbv.zzek().zzcx(str)) {
                com.google.android.gms.ads.internal.zzbv.zzfh().zze(context, strZzab);
                return zzb(str, "fbs_aeid", strZzab).toString();
            }
            if (!com.google.android.gms.ads.internal.zzbv.zzek().zzcy(str)) {
                return str;
            }
            com.google.android.gms.ads.internal.zzbv.zzfh().zzf(context, strZzab);
            return zzb(str, "fbs_aeid", strZzab).toString();
        }
        CharSequence charSequence = (String) zzkb.zzik().zzd(zznk.zzaxs);
        if (!str.contains(charSequence)) {
            return str;
        }
        if (com.google.android.gms.ads.internal.zzbv.zzek().zzcx(str)) {
            com.google.android.gms.ads.internal.zzbv.zzfh().zze(context, strZzab);
            return str.replace(charSequence, strZzab);
        }
        if (!com.google.android.gms.ads.internal.zzbv.zzek().zzcy(str)) {
            return str;
        }
        com.google.android.gms.ads.internal.zzbv.zzfh().zzf(context, strZzab);
        return str.replace(charSequence, strZzab);
    }

    public static String zzc(String str, Context context) {
        String strZzab;
        if (!com.google.android.gms.ads.internal.zzbv.zzfh().zzs(context) || TextUtils.isEmpty(str) || (strZzab = com.google.android.gms.ads.internal.zzbv.zzfh().zzab(context)) == null || !com.google.android.gms.ads.internal.zzbv.zzek().zzcy(str)) {
            return str;
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzaxr)).booleanValue()) {
            return !str.contains("fbs_aeid") ? zzb(str, "fbs_aeid", strZzab).toString() : str;
        }
        CharSequence charSequence = (String) zzkb.zzik().zzd(zznk.zzaxs);
        return str.contains(charSequence) ? str.replace(charSequence, strZzab) : str;
    }
}
