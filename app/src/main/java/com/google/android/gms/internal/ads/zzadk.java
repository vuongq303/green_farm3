package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.CookieManager;
import com.google.ads.mediation.admob.AdMobAdapter;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzadk extends zzajx implements zzadx {
    private final Context mContext;
    private zzwy zzbtj;
    private zzaef zzbuc;
    private zzaej zzbzf;
    private Runnable zzbzg;
    private final Object zzbzh = new Object();
    private final zzadj zzccf;
    private final zzaeg zzccg;
    private final zzhs zzcch;
    private final zzhx zzcci;

    @GuardedBy("mCancelLock")
    zzalc zzccj;

    public zzadk(Context context, zzaeg zzaegVar, zzadj zzadjVar, zzhx zzhxVar) {
        this.zzccf = zzadjVar;
        this.mContext = context;
        this.zzccg = zzaegVar;
        this.zzcci = zzhxVar;
        this.zzcch = new zzhs(this.zzcci);
        this.zzcch.zza(new zzht(this) { // from class: com.google.android.gms.internal.ads.zzadl
            private final zzadk zzcck;

            {
                this.zzcck = this;
            }

            @Override // com.google.android.gms.internal.ads.zzht
            public final void zza(zzii zziiVar) {
                this.zzcck.zzc(zziiVar);
            }
        });
        final zzit zzitVar = new zzit();
        zzitVar.zzaot = Integer.valueOf(this.zzccg.zzacr.zzcve);
        zzitVar.zzaou = Integer.valueOf(this.zzccg.zzacr.zzcvf);
        zzitVar.zzaov = Integer.valueOf(this.zzccg.zzacr.zzcvg ? 0 : 2);
        this.zzcch.zza(new zzht(zzitVar) { // from class: com.google.android.gms.internal.ads.zzadm
            private final zzit zzccl;

            {
                this.zzccl = zzitVar;
            }

            @Override // com.google.android.gms.internal.ads.zzht
            public final void zza(zzii zziiVar) {
                zziiVar.zzanm.zzamx = this.zzccl;
            }
        });
        if (this.zzccg.zzccw != null) {
            this.zzcch.zza(new zzht(this) { // from class: com.google.android.gms.internal.ads.zzadn
                private final zzadk zzcck;

                {
                    this.zzcck = this;
                }

                @Override // com.google.android.gms.internal.ads.zzht
                public final void zza(zzii zziiVar) {
                    this.zzcck.zzb(zziiVar);
                }
            });
        }
        zzjn zzjnVar = this.zzccg.zzacv;
        if (zzjnVar.zzarc && "interstitial_mb".equals(zzjnVar.zzarb)) {
            this.zzcch.zza(zzado.zzccm);
        } else if (zzjnVar.zzarc && "reward_mb".equals(zzjnVar.zzarb)) {
            this.zzcch.zza(zzadp.zzccm);
        } else if (zzjnVar.zzare || zzjnVar.zzarc) {
            this.zzcch.zza(zzadr.zzccm);
        } else {
            this.zzcch.zza(zzadq.zzccm);
        }
        this.zzcch.zza(zzhu.zza.zzb.AD_REQUEST);
    }

    private final zzjn zza(zzaef zzaefVar) throws zzadu {
        if (((this.zzbuc == null || this.zzbuc.zzadn == null || this.zzbuc.zzadn.size() <= 1) ? false : true) && this.zzbtj != null && !this.zzbtj.zzbte) {
            return null;
        }
        if (this.zzbzf.zzarf) {
            for (zzjn zzjnVar : zzaefVar.zzacv.zzard) {
                if (zzjnVar.zzarf) {
                    return new zzjn(zzjnVar, zzaefVar.zzacv.zzard);
                }
            }
        }
        if (this.zzbzf.zzcet == null) {
            throw new zzadu("The ad response must specify one of the supported ad sizes.", 0);
        }
        String[] strArrSplit = this.zzbzf.zzcet.split("x");
        if (strArrSplit.length != 2) {
            String strValueOf = String.valueOf(this.zzbzf.zzcet);
            throw new zzadu(strValueOf.length() != 0 ? "Invalid ad size format from the ad response: ".concat(strValueOf) : new String("Invalid ad size format from the ad response: "), 0);
        }
        try {
            int i = Integer.parseInt(strArrSplit[0]);
            int i2 = Integer.parseInt(strArrSplit[1]);
            for (zzjn zzjnVar2 : zzaefVar.zzacv.zzard) {
                float f = this.mContext.getResources().getDisplayMetrics().density;
                int i3 = zzjnVar2.width == -1 ? (int) (zzjnVar2.widthPixels / f) : zzjnVar2.width;
                int i4 = zzjnVar2.height == -2 ? (int) (zzjnVar2.heightPixels / f) : zzjnVar2.height;
                if (i == i3 && i2 == i4 && !zzjnVar2.zzarf) {
                    return new zzjn(zzjnVar2, zzaefVar.zzacv.zzard);
                }
            }
            String strValueOf2 = String.valueOf(this.zzbzf.zzcet);
            throw new zzadu(strValueOf2.length() != 0 ? "The ad size from the ad response was not one of the requested sizes: ".concat(strValueOf2) : new String("The ad size from the ad response was not one of the requested sizes: "), 0);
        } catch (NumberFormatException e) {
            String strValueOf3 = String.valueOf(this.zzbzf.zzcet);
            throw new zzadu(strValueOf3.length() != 0 ? "Invalid ad size number from the ad response: ".concat(strValueOf3) : new String("Invalid ad size number from the ad response: "), 0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zzc(int i, String str) {
        if (i == 3 || i == -1) {
            zzakb.zzdj(str);
        } else {
            zzakb.zzdk(str);
        }
        if (this.zzbzf == null) {
            this.zzbzf = new zzaej(i);
        } else {
            this.zzbzf = new zzaej(i, this.zzbzf.zzbsu);
        }
        this.zzccf.zza(new zzaji(this.zzbuc != null ? this.zzbuc : new zzaef(this.zzccg, -1L, null, null, null), this.zzbzf, this.zzbtj, null, i, -1L, this.zzbzf.zzceu, null, this.zzcch, null));
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void onStop() {
        synchronized (this.zzbzh) {
            if (this.zzccj != null) {
                this.zzccj.cancel();
            }
        }
    }

    final zzalc zza(zzang zzangVar, zzaol<zzaef> zzaolVar) {
        Context context = this.mContext;
        if (new zzadw(context).zza(zzangVar)) {
            zzakb.zzck("Fetching ad response from local ad request service.");
            zzaec zzaecVar = new zzaec(context, zzaolVar, this);
            zzaecVar.zznt();
            return zzaecVar;
        }
        zzakb.zzck("Fetching ad response from remote ad request service.");
        zzkb.zzif();
        if (zzamu.zzbe(context)) {
            return new zzaed(context, zzangVar, zzaolVar, this);
        }
        zzakb.zzdk("Failed to connect to remote ad request service.");
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzadx
    public final void zza(zzaej zzaejVar) {
        JSONObject jSONObject;
        Bundle bundle;
        Boolean bool = null;
        zzakb.zzck("Received ad response.");
        this.zzbzf = zzaejVar;
        long jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
        synchronized (this.zzbzh) {
            this.zzccj = null;
        }
        com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzae(this.zzbzf.zzcdr);
        if (((Boolean) zzkb.zzik().zzd(zznk.zzayy)).booleanValue()) {
            if (this.zzbzf.zzced) {
                com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzcp(this.zzbuc.zzacp);
            } else {
                com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzcq(this.zzbuc.zzacp);
            }
        }
        try {
            if (this.zzbzf.errorCode != -2 && this.zzbzf.errorCode != -3) {
                throw new zzadu(new StringBuilder(66).append("There was a problem getting an ad response. ErrorCode: ").append(this.zzbzf.errorCode).toString(), this.zzbzf.errorCode);
            }
            if (this.zzbzf.errorCode != -3) {
                if (TextUtils.isEmpty(this.zzbzf.zzceo)) {
                    throw new zzadu("No fill from ad server.", 3);
                }
                com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzab(this.zzbzf.zzcdd);
                if (this.zzbzf.zzceq) {
                    try {
                        this.zzbtj = new zzwy(this.zzbzf.zzceo);
                        com.google.android.gms.ads.internal.zzbv.zzeo().zzaa(this.zzbtj.zzbss);
                    } catch (JSONException e) {
                        zzakb.zzb("Could not parse mediation config.", e);
                        String strValueOf = String.valueOf(this.zzbzf.zzceo);
                        throw new zzadu(strValueOf.length() != 0 ? "Could not parse mediation config: ".concat(strValueOf) : new String("Could not parse mediation config: "), 0);
                    }
                } else {
                    com.google.android.gms.ads.internal.zzbv.zzeo().zzaa(this.zzbzf.zzbss);
                }
                if (!TextUtils.isEmpty(this.zzbzf.zzcds)) {
                    if (((Boolean) zzkb.zzik().zzd(zznk.zzbdj)).booleanValue()) {
                        zzakb.zzck("Received cookie from server. Setting webview cookie in CookieManager.");
                        CookieManager cookieManagerZzax = com.google.android.gms.ads.internal.zzbv.zzem().zzax(this.mContext);
                        if (cookieManagerZzax != null) {
                            cookieManagerZzax.setCookie("googleads.g.doubleclick.net", this.zzbzf.zzcds);
                        }
                    }
                }
            }
            zzjn zzjnVarZza = this.zzbuc.zzacv.zzard != null ? zza(this.zzbuc) : null;
            com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzac(this.zzbzf.zzcfa);
            com.google.android.gms.ads.internal.zzbv.zzeo().zzqh().zzad(this.zzbzf.zzcfm);
            if (TextUtils.isEmpty(this.zzbzf.zzcey)) {
                jSONObject = null;
            } else {
                try {
                    jSONObject = new JSONObject(this.zzbzf.zzcey);
                } catch (Exception e2) {
                    zzakb.zzb("Error parsing the JSON for Active View.", e2);
                    jSONObject = null;
                }
            }
            if (this.zzbzf.zzcfo == 2) {
                zzjj zzjjVar = this.zzbuc.zzccv;
                Bundle bundle2 = zzjjVar.zzaqg != null ? zzjjVar.zzaqg : new Bundle();
                if (bundle2.getBundle(AdMobAdapter.class.getName()) != null) {
                    bundle = bundle2.getBundle(AdMobAdapter.class.getName());
                } else {
                    Bundle bundle3 = new Bundle();
                    bundle2.putBundle(AdMobAdapter.class.getName(), bundle3);
                    bundle = bundle3;
                }
                bundle.putBoolean("render_test_label", true);
                bool = true;
            }
            if (this.zzbzf.zzcfo == 1) {
                bool = false;
            }
            this.zzccf.zza(new zzaji(this.zzbuc, this.zzbzf, this.zzbtj, zzjnVarZza, -2, jElapsedRealtime, this.zzbzf.zzceu, jSONObject, this.zzcch, this.zzbzf.zzcfo == 0 ? Boolean.valueOf(zzamm.zzo(this.zzbuc.zzccv)) : bool));
            zzakk.zzcrm.removeCallbacks(this.zzbzg);
        } catch (zzadu e3) {
            zzc(e3.getErrorCode(), e3.getMessage());
            zzakk.zzcrm.removeCallbacks(this.zzbzg);
        }
    }

    final /* synthetic */ void zzb(zzii zziiVar) {
        zziiVar.zzanm.zzamu = this.zzccg.zzccw.packageName;
    }

    final /* synthetic */ void zzc(zzii zziiVar) {
        zziiVar.zzanh = this.zzccg.zzcdi;
    }

    @Override // com.google.android.gms.internal.ads.zzajx
    public final void zzdn() {
        String string;
        zzakb.zzck("AdLoaderBackgroundTask started.");
        this.zzbzg = new zzads(this);
        zzakk.zzcrm.postDelayed(this.zzbzg, ((Long) zzkb.zzik().zzd(zznk.zzban)).longValue());
        long jElapsedRealtime = com.google.android.gms.ads.internal.zzbv.zzer().elapsedRealtime();
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbak)).booleanValue() && this.zzccg.zzccv.extras != null && (string = this.zzccg.zzccv.extras.getString("_ad")) != null) {
            this.zzbuc = new zzaef(this.zzccg, jElapsedRealtime, null, null, null);
            zza(zzafs.zza(this.mContext, this.zzbuc, string));
            return;
        }
        zzaop zzaopVar = new zzaop();
        zzaki.zzb(new zzadt(this, zzaopVar));
        String strZzz = com.google.android.gms.ads.internal.zzbv.zzfh().zzz(this.mContext);
        String strZzaa = com.google.android.gms.ads.internal.zzbv.zzfh().zzaa(this.mContext);
        String strZzab = com.google.android.gms.ads.internal.zzbv.zzfh().zzab(this.mContext);
        com.google.android.gms.ads.internal.zzbv.zzfh().zzg(this.mContext, strZzab);
        this.zzbuc = new zzaef(this.zzccg, jElapsedRealtime, strZzz, strZzaa, strZzab);
        zzaopVar.zzk(this.zzbuc);
    }
}
