package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.text.TextUtils;
import com.google.ads.mediation.AdUrlAdapter;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.c.d;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzxb implements zzxf {
    private final Context mContext;
    private zzjj zzaao;
    private final zzjn zzaau;
    private final boolean zzael;
    private final String zzbth;
    private final long zzbti;
    private final zzwy zzbtj;
    private final zzwx zzbtk;
    private final List<String> zzbtl;
    private final List<String> zzbtm;
    private final boolean zzbtn;
    private final boolean zzbto;

    @GuardedBy("mLock")
    private zzxq zzbtp;
    private zzxw zzbtr;
    private final zzxn zzwh;
    private final zzpl zzyb;
    private final List<String> zzyc;
    private final zzang zzyf;
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private int zzbtq = -2;

    public zzxb(Context context, String str, zzxn zzxnVar, zzwy zzwyVar, zzwx zzwxVar, zzjj zzjjVar, zzjn zzjnVar, zzang zzangVar, boolean z, boolean z2, zzpl zzplVar, List<String> list, List<String> list2, List<String> list3, boolean z3) {
        this.mContext = context;
        this.zzwh = zzxnVar;
        this.zzbtk = zzwxVar;
        if ("com.google.ads.mediation.customevent.CustomEventAdapter".equals(str)) {
            this.zzbth = zzmh();
        } else {
            this.zzbth = str;
        }
        this.zzbtj = zzwyVar;
        if (zzwxVar.zzbsl != -1) {
            this.zzbti = zzwxVar.zzbsl;
        } else if (zzwyVar.zzbsl != -1) {
            this.zzbti = zzwyVar.zzbsl;
        } else {
            this.zzbti = 10000L;
        }
        this.zzaao = zzjjVar;
        this.zzaau = zzjnVar;
        this.zzyf = zzangVar;
        this.zzael = z;
        this.zzbtn = z2;
        this.zzyb = zzplVar;
        this.zzyc = list;
        this.zzbtl = list2;
        this.zzbtm = list3;
        this.zzbto = z3;
    }

    private static zzxq zza(MediationAdapter mediationAdapter) {
        return new zzyk(mediationAdapter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final void zza(zzxa zzxaVar) {
        String strZzbk = zzbk(this.zzbtk.zzbsb);
        try {
            if (this.zzyf.zzcvf < 4100000) {
                if (this.zzaau.zzarc) {
                    this.zzbtp.zza(d.F(this.mContext), this.zzaao, strZzbk, zzxaVar);
                    return;
                } else {
                    this.zzbtp.zza(d.F(this.mContext), this.zzaau, this.zzaao, strZzbk, zzxaVar);
                    return;
                }
            }
            if (!this.zzael && !this.zzbtk.zzmg()) {
                if (this.zzaau.zzarc) {
                    this.zzbtp.zza(d.F(this.mContext), this.zzaao, strZzbk, this.zzbtk.zzbrr, zzxaVar);
                    return;
                }
                if (!this.zzbtn) {
                    this.zzbtp.zza(d.F(this.mContext), this.zzaau, this.zzaao, strZzbk, this.zzbtk.zzbrr, zzxaVar);
                    return;
                } else if (this.zzbtk.zzbsf != null) {
                    this.zzbtp.zza(d.F(this.mContext), this.zzaao, strZzbk, this.zzbtk.zzbrr, zzxaVar, new zzpl(zzbl(this.zzbtk.zzbsj)), this.zzbtk.zzbsi);
                    return;
                } else {
                    this.zzbtp.zza(d.F(this.mContext), this.zzaau, this.zzaao, strZzbk, this.zzbtk.zzbrr, zzxaVar);
                    return;
                }
            }
            ArrayList arrayList = new ArrayList(this.zzyc);
            if (this.zzbtl != null) {
                for (String str : this.zzbtl) {
                    String str2 = ":false";
                    if (this.zzbtm != null && this.zzbtm.contains(str)) {
                        str2 = ":true";
                    }
                    arrayList.add(new StringBuilder(String.valueOf(str).length() + 7 + String.valueOf(str2).length()).append("custom:").append(str).append(str2).toString());
                }
            }
            this.zzbtp.zza(d.F(this.mContext), this.zzaao, strZzbk, this.zzbtk.zzbrr, zzxaVar, this.zzyb, arrayList);
        } catch (RemoteException e) {
            zzakb.zzc("Could not request ad from mediation adapter.", e);
            zzx(5);
        }
    }

    @GuardedBy("mLock")
    private final String zzbk(String str) {
        if (str == null || !zzmk() || zzy(2)) {
            return str;
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            jSONObject.remove("cpm_floor_cents");
            return jSONObject.toString();
        } catch (JSONException e) {
            zzakb.zzdk("Could not remove field. Returning the original value");
            return str;
        }
    }

    private static NativeAdOptions zzbl(String str) {
        int i = 0;
        NativeAdOptions.Builder builder = new NativeAdOptions.Builder();
        if (str == null) {
            return builder.build();
        }
        try {
            JSONObject jSONObject = new JSONObject(str);
            builder.setRequestMultipleImages(jSONObject.optBoolean("multiple_images", false));
            builder.setReturnUrlsForImageAssets(jSONObject.optBoolean("only_urls", false));
            String strOptString = jSONObject.optString("native_image_orientation", "any");
            if ("landscape".equals(strOptString)) {
                i = 2;
            } else if ("portrait".equals(strOptString)) {
                i = 1;
            } else if (!"any".equals(strOptString)) {
                i = -1;
            }
            builder.setImageOrientation(i);
        } catch (JSONException e) {
            zzakb.zzc("Exception occurred when creating native ad options", e);
        }
        return builder.build();
    }

    private final String zzmh() {
        try {
            return (TextUtils.isEmpty(this.zzbtk.zzbrv) || !this.zzwh.zzbn(this.zzbtk.zzbrv)) ? "com.google.ads.mediation.customevent.CustomEventAdapter" : "com.google.android.gms.ads.mediation.customevent.CustomEventAdapter";
        } catch (RemoteException e) {
            zzakb.zzdk("Fail to determine the custom event's version, assuming the old one.");
        }
        return "com.google.ads.mediation.customevent.CustomEventAdapter";
    }

    @GuardedBy("mLock")
    private final zzxw zzmi() {
        if (this.zzbtq != 0 || !zzmk()) {
            return null;
        }
        try {
            if (zzy(4) && this.zzbtr != null && this.zzbtr.zzmm() != 0) {
                return this.zzbtr;
            }
        } catch (RemoteException e) {
            zzakb.zzdk("Could not get cpm value from MediationResponseMetadata");
        }
        return new zzxd(zzml());
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final zzxq zzmj() {
        String strValueOf = String.valueOf(this.zzbth);
        zzakb.zzdj(strValueOf.length() != 0 ? "Instantiating mediation adapter: ".concat(strValueOf) : new String("Instantiating mediation adapter: "));
        if (!this.zzael && !this.zzbtk.zzmg()) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbai)).booleanValue() && "com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzbth)) {
                return zza(new AdMobAdapter());
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbaj)).booleanValue() && "com.google.ads.mediation.AdUrlAdapter".equals(this.zzbth)) {
                return zza(new AdUrlAdapter());
            }
            if ("com.google.ads.mediation.admob.AdMobCustomTabsAdapter".equals(this.zzbth)) {
                return new zzyk(new zzzv());
            }
        }
        try {
            return this.zzwh.zzbm(this.zzbth);
        } catch (RemoteException e) {
            String strValueOf2 = String.valueOf(this.zzbth);
            zzakb.zza(strValueOf2.length() != 0 ? "Could not instantiate mediation adapter: ".concat(strValueOf2) : new String("Could not instantiate mediation adapter: "), e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean zzmk() {
        return this.zzbtj.zzbsx != -1;
    }

    @GuardedBy("mLock")
    private final int zzml() {
        if (this.zzbtk.zzbsb == null) {
            return 0;
        }
        try {
            JSONObject jSONObject = new JSONObject(this.zzbtk.zzbsb);
            if ("com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzbth)) {
                return jSONObject.optInt("cpm_cents", 0);
            }
            int iOptInt = zzy(2) ? jSONObject.optInt("cpm_floor_cents", 0) : 0;
            return iOptInt == 0 ? jSONObject.optInt("penalized_average_cpm_cents", 0) : iOptInt;
        } catch (JSONException e) {
            zzakb.zzdk("Could not convert to json. Returning 0");
            return 0;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @GuardedBy("mLock")
    public final boolean zzy(int i) {
        Bundle interstitialAdapterInfo;
        try {
            if (this.zzael) {
                interstitialAdapterInfo = this.zzbtp.zzmr();
            } else {
                interstitialAdapterInfo = this.zzaau.zzarc ? this.zzbtp.getInterstitialAdapterInfo() : this.zzbtp.zzmq();
            }
            return interstitialAdapterInfo != null && (interstitialAdapterInfo.getInt("capabilities", 0) & i) == i;
        } catch (RemoteException e) {
            zzakb.zzdk("Could not get adapter info. Returning false");
            return false;
        }
    }

    public final void cancel() {
        synchronized (this.mLock) {
            try {
                if (this.zzbtp != null) {
                    this.zzbtp.destroy();
                }
            } catch (RemoteException e) {
                zzakb.zzc("Could not destroy mediation adapter.", e);
            }
            this.zzbtq = -1;
            this.mLock.notify();
        }
    }

    public final zzxe zza(long j, long j2) {
        zzxe zzxeVar;
        synchronized (this.mLock) {
            long jElapsedRealtime = SystemClock.elapsedRealtime();
            zzxa zzxaVar = new zzxa();
            zzakk.zzcrm.post(new zzxc(this, zzxaVar));
            long j3 = this.zzbti;
            while (this.zzbtq == -2) {
                long jElapsedRealtime2 = SystemClock.elapsedRealtime();
                long j4 = j3 - (jElapsedRealtime2 - jElapsedRealtime);
                long j5 = j2 - (jElapsedRealtime2 - j);
                if (j4 <= 0 || j5 <= 0) {
                    zzakb.zzdj("Timed out waiting for adapter.");
                    this.zzbtq = 3;
                } else {
                    try {
                        this.mLock.wait(Math.min(j4, j5));
                    } catch (InterruptedException e) {
                        this.zzbtq = 5;
                    }
                }
            }
            zzxeVar = new zzxe(this.zzbtk, this.zzbtp, this.zzbth, zzxaVar, this.zzbtq, zzmi(), com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime() - jElapsedRealtime);
        }
        return zzxeVar;
    }

    @Override // com.google.android.gms.internal.ads.zzxf
    public final void zza(int i, zzxw zzxwVar) {
        synchronized (this.mLock) {
            this.zzbtq = 0;
            this.zzbtr = zzxwVar;
            this.mLock.notify();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzxf
    public final void zzx(int i) {
        synchronized (this.mLock) {
            this.zzbtq = i;
            this.mLock.notify();
        }
    }
}
