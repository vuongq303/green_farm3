package com.google.android.gms.ads.internal;

import android.content.Context;
import android.text.TextUtils;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajl;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzanm;
import com.google.android.gms.internal.ads.zzano;
import com.google.android.gms.internal.ads.zzanz;
import com.google.android.gms.internal.ads.zzaoe;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzwf;
import com.google.android.gms.internal.ads.zzwk;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzad {
    private Context mContext;
    private final Object mLock = new Object();
    private long bAS = 0;

    final void a(Context context, zzang zzangVar, boolean z, zzajl zzajlVar, String str, String str2, Runnable runnable) {
        boolean z2;
        if (zzbv.zzer().elapsedRealtime() - this.bAS < 5000) {
            zzakb.zzdk("Not retrying to fetch app settings");
            return;
        }
        this.bAS = zzbv.zzer().elapsedRealtime();
        if (zzajlVar == null) {
            z2 = true;
        } else {
            z2 = (((zzbv.zzer().currentTimeMillis() - zzajlVar.zzps()) > ((Long) zzkb.zzik().zzd(zznk.zzbcu)).longValue() ? 1 : ((zzbv.zzer().currentTimeMillis() - zzajlVar.zzps()) == ((Long) zzkb.zzik().zzd(zznk.zzbcu)).longValue() ? 0 : -1)) > 0) || !zzajlVar.zzpt();
        }
        if (z2) {
            if (context == null) {
                zzakb.zzdk("Context not provided to fetch application settings");
                return;
            }
            if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
                zzakb.zzdk("App settings could not be fetched. Required parameters missing");
                return;
            }
            Context applicationContext = context.getApplicationContext();
            if (applicationContext == null) {
                applicationContext = context;
            }
            this.mContext = applicationContext;
            zzwf zzwfVarZza = zzbv.zzey().zzb(this.mContext, zzangVar).zza("google.afma.config.fetchAppSettings", zzwk.zzbrc, zzwk.zzbrc);
            try {
                JSONObject jSONObject = new JSONObject();
                if (!TextUtils.isEmpty(str)) {
                    jSONObject.put("app_id", str);
                } else if (!TextUtils.isEmpty(str2)) {
                    jSONObject.put("ad_unit_id", str2);
                }
                jSONObject.put("is_init", z);
                jSONObject.put("pn", context.getPackageName());
                zzanz zzanzVarZzf = zzwfVarZza.zzf(jSONObject);
                zzanz zzanzVarZza = zzano.zza(zzanzVarZzf, c.zzxn, zzaoe.zzcvz);
                if (runnable != null) {
                    zzanzVarZzf.zza(runnable, zzaoe.zzcvz);
                }
                zzanm.zza(zzanzVarZza, "ConfigLoader.maybeFetchNewAppSettings");
            } catch (Exception e) {
                zzakb.zzb("Error requesting application settings", e);
            }
        }
    }

    public final void zza(Context context, zzang zzangVar, String str, Runnable runnable) {
        a(context, zzangVar, true, null, str, null, runnable);
    }
}
