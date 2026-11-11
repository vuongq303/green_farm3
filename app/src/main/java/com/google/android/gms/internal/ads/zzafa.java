package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.internal.gmsg.HttpClient;
import com.google.android.gms.common.k;
import com.google.android.gms.common.l;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzafa extends zzajx {
    private final Context mContext;
    private final Object zzbzh;
    private final zzadj zzccf;
    private final zzaeg zzccg;
    private zzhx zzcci;
    private zzvs zzcgi;
    private static final long zzcgc = TimeUnit.SECONDS.toMillis(10);
    private static final Object sLock = new Object();

    @GuardedBy("sLock")
    private static boolean zzcgd = false;
    private static zzvf zzcge = null;
    private static HttpClient zzcgf = null;
    private static com.google.android.gms.ads.internal.gmsg.zzaa zzcgg = null;
    private static com.google.android.gms.ads.internal.gmsg.zzv<Object> zzcgh = null;

    public zzafa(Context context, zzaeg zzaegVar, zzadj zzadjVar, zzhx zzhxVar) {
        super(true);
        this.zzbzh = new Object();
        this.zzccf = zzadjVar;
        this.mContext = context;
        this.zzccg = zzaegVar;
        this.zzcci = zzhxVar;
        synchronized (sLock) {
            if (!zzcgd) {
                zzcgg = new com.google.android.gms.ads.internal.gmsg.zzaa();
                zzcgf = new HttpClient(context.getApplicationContext(), zzaegVar.zzacr);
                zzcgh = new zzafi();
                zzcge = new zzvf(this.mContext.getApplicationContext(), this.zzccg.zzacr, (String) zzkb.zzik().zzd(zznk.zzaub), new zzafh(), new zzafg());
                zzcgd = true;
            }
        }
    }

    private final JSONObject zza(zzaef zzaefVar, String str) {
        zzaga zzagaVar;
        AdvertisingIdClient.Info advertisingIdInfo;
        Bundle bundle = zzaefVar.zzccv.extras.getBundle("sdk_less_server_data");
        if (bundle == null) {
            return null;
        }
        try {
            zzagaVar = com.google.android.gms.ads.internal.zzbv.zzev().zzq(this.mContext).get();
        } catch (Exception e) {
            zzakb.zzc("Error grabbing device info: ", e);
            zzagaVar = null;
        }
        Context context = this.mContext;
        zzafl zzaflVar = new zzafl();
        zzaflVar.zzcgs = zzaefVar;
        zzaflVar.zzcgt = zzagaVar;
        JSONObject jSONObjectZza = zzafs.zza(context, zzaflVar);
        if (jSONObjectZza == null) {
            return null;
        }
        try {
            advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.mContext);
        } catch (k | l | IOException | IllegalStateException e2) {
            zzakb.zzc("Cannot get advertising id info", e2);
            advertisingIdInfo = null;
        }
        HashMap map = new HashMap();
        map.put("request_id", str);
        map.put("request_param", jSONObjectZza);
        map.put(ShareConstants.WEB_DIALOG_PARAM_DATA, bundle);
        if (advertisingIdInfo != null) {
            map.put("adid", advertisingIdInfo.getId());
            map.put("lat", Integer.valueOf(advertisingIdInfo.isLimitAdTrackingEnabled() ? 1 : 0));
        }
        try {
            return com.google.android.gms.ads.internal.zzbv.zzek().zzn(map);
        } catch (JSONException e3) {
            return null;
        }
    }

    protected static void zzb(zzuu zzuuVar) {
        zzuuVar.zza("/loadAd", zzcgg);
        zzuuVar.zza("/fetchHttpRequest", zzcgf);
        zzuuVar.zza("/invalidRequest", zzcgh);
    }

    private final zzaej zzc(zzaef zzaefVar) {
        com.google.android.gms.ads.internal.zzbv.zzek();
        String strZzrh = zzakk.zzrh();
        JSONObject jSONObjectZza = zza(zzaefVar, strZzrh);
        if (jSONObjectZza == null) {
            return new zzaej(0);
        }
        long jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
        Future<JSONObject> futureZzas = zzcgg.zzas(strZzrh);
        zzamu.zzsy.post(new zzafc(this, jSONObjectZza, strZzrh));
        try {
            JSONObject jSONObject = futureZzas.get(zzcgc - (com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime() - jElapsedRealtime), TimeUnit.MILLISECONDS);
            if (jSONObject == null) {
                return new zzaej(-1);
            }
            zzaej zzaejVarZza = zzafs.zza(this.mContext, zzaefVar, jSONObject.toString());
            return (zzaejVarZza.errorCode == -3 || !TextUtils.isEmpty(zzaejVarZza.zzceo)) ? zzaejVarZza : new zzaej(3);
        } catch (InterruptedException e) {
            return new zzaej(-1);
        } catch (CancellationException e2) {
            return new zzaej(-1);
        } catch (ExecutionException e3) {
            return new zzaej(0);
        } catch (TimeoutException e4) {
            return new zzaej(2);
        }
    }

    protected static void zzc(zzuu zzuuVar) {
        zzuuVar.zzb("/loadAd", zzcgg);
        zzuuVar.zzb("/fetchHttpRequest", zzcgf);
        zzuuVar.zzb("/invalidRequest", zzcgh);
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
        synchronized (this.zzbzh) {
            zzamu.zzsy.post(new zzaff(this));
        }
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        zzakb.zzck("SdkLessAdLoaderBackgroundTask started.");
        String strZzab = com.google.android.gms.ads.internal.zzbv.zzfh().zzab(this.mContext);
        zzaef zzaefVar = new zzaef(this.zzccg, -1L, com.google.android.gms.ads.internal.zzbv.zzfh().zzz(this.mContext), com.google.android.gms.ads.internal.zzbv.zzfh().zzaa(this.mContext), strZzab);
        com.google.android.gms.ads.internal.zzbv.zzfh().zzg(this.mContext, strZzab);
        zzaej zzaejVarZzc = zzc(zzaefVar);
        zzamu.zzsy.post(new zzafb(this, new zzaji(zzaefVar, zzaejVarZzc, null, null, zzaejVarZzc.errorCode, com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime(), zzaejVarZzc.zzceu, null, this.zzcci)));
    }
}
