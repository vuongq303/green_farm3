package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Build;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Future;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zznm {
    private Context mContext;
    private String zzaej;
    private String zzbfx = (String) zzkb.zzik().zzd(zznk.zzawi);
    private Map<String, String> zzbfy = new LinkedHashMap();

    public zznm(Context context, String str) {
        this.mContext = null;
        this.zzaej = null;
        this.mContext = context;
        this.zzaej = str;
        this.zzbfy.put("s", "gmob_sdk");
        this.zzbfy.put("v", "3");
        this.zzbfy.put("os", Build.VERSION.RELEASE);
        this.zzbfy.put(ServerProtocol.DIALOG_PARAM_SDK_VERSION, Build.VERSION.SDK);
        Map<String, String> map = this.zzbfy;
        com.google.android.gms.ads.internal.zzbv.zzek();
        map.put("device", zzakk.zzri());
        this.zzbfy.put("app", context.getApplicationContext() != null ? context.getApplicationContext().getPackageName() : context.getPackageName());
        Map<String, String> map2 = this.zzbfy;
        com.google.android.gms.ads.internal.zzbv.zzek();
        map2.put("is_lite_sdk", zzakk.zzav(context) ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        Future<zzaga> futureZzq = com.google.android.gms.ads.internal.zzbv.zzev().zzq(this.mContext);
        try {
            futureZzq.get();
            this.zzbfy.put("network_coarse", Integer.toString(futureZzq.get().zzcjx));
            this.zzbfy.put("network_fine", Integer.toString(futureZzq.get().zzcjy));
        } catch (Exception e) {
            com.google.android.gms.ads.internal.zzbv.zzeo().zza(e, "CsiConfiguration.CsiConfiguration");
        }
    }

    final Context getContext() {
        return this.mContext;
    }

    final String zzfw() {
        return this.zzaej;
    }

    final String zzjd() {
        return this.zzbfx;
    }

    final Map<String, String> zzje() {
        return this.zzbfy;
    }
}
