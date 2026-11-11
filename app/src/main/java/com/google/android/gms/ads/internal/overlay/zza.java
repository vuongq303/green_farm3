package com.google.android.gms.ads.internal.overlay;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zza {
    private static boolean a(Context context, Intent intent, zzt zztVar) {
        try {
            String strValueOf = String.valueOf(intent.toURI());
            zzakb.v(strValueOf.length() != 0 ? "Launching an intent: ".concat(strValueOf) : new String("Launching an intent: "));
            zzbv.zzek();
            zzakk.zza(context, intent);
            if (zztVar != null) {
                zztVar.zzbl();
            }
            return true;
        } catch (ActivityNotFoundException e) {
            zzakb.zzdk(e.getMessage());
            return false;
        }
    }

    public static boolean zza(Context context, zzc zzcVar, zzt zztVar) {
        int i;
        if (zzcVar == null) {
            zzakb.zzdk("No intent data for launcher overlay.");
            return false;
        }
        zznk.initialize(context);
        if (zzcVar.intent != null) {
            return a(context, zzcVar.intent, zztVar);
        }
        Intent intent = new Intent();
        if (TextUtils.isEmpty(zzcVar.url)) {
            zzakb.zzdk("Open GMSG did not contain a URL.");
            return false;
        }
        if (TextUtils.isEmpty(zzcVar.mimeType)) {
            intent.setData(Uri.parse(zzcVar.url));
        } else {
            intent.setDataAndType(Uri.parse(zzcVar.url), zzcVar.mimeType);
        }
        intent.setAction("android.intent.action.VIEW");
        if (!TextUtils.isEmpty(zzcVar.packageName)) {
            intent.setPackage(zzcVar.packageName);
        }
        if (!TextUtils.isEmpty(zzcVar.zzbxj)) {
            String[] strArrSplit = zzcVar.zzbxj.split("/", 2);
            if (strArrSplit.length < 2) {
                String strValueOf = String.valueOf(zzcVar.zzbxj);
                zzakb.zzdk(strValueOf.length() != 0 ? "Could not parse component name from open GMSG: ".concat(strValueOf) : new String("Could not parse component name from open GMSG: "));
                return false;
            }
            intent.setClassName(strArrSplit[0], strArrSplit[1]);
        }
        String str = zzcVar.zzbxk;
        if (!TextUtils.isEmpty(str)) {
            try {
                i = Integer.parseInt(str);
            } catch (NumberFormatException e) {
                zzakb.zzdk("Could not parse intent flags.");
                i = 0;
            }
            intent.addFlags(i);
        }
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbea)).booleanValue()) {
            intent.addFlags(268435456);
            intent.putExtra("android.support.customtabs.extra.user_opt_out", true);
        } else {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbdz)).booleanValue()) {
                zzbv.zzek();
                zzakk.zzb(context, intent);
            }
        }
        return a(context, intent, zztVar);
    }
}
