package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.formats.PublisherAdViewOptions;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzkh;
import com.google.android.gms.internal.ads.zzkl;
import com.google.android.gms.internal.ads.zzlg;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzpl;
import com.google.android.gms.internal.ads.zzqw;
import com.google.android.gms.internal.ads.zzqz;
import com.google.android.gms.internal.ads.zzrc;
import com.google.android.gms.internal.ads.zzrf;
import com.google.android.gms.internal.ads.zzri;
import com.google.android.gms.internal.ads.zzrl;
import com.google.android.gms.internal.ads.zzxn;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzah extends zzkl {
    private final zzqw bAW;
    private final zzrl bAX;
    private final zzqz bAY;
    private final zzri bAZ;
    private final zzjn bBa;
    private final PublisherAdViewOptions bBb;
    private final android.support.v4.e.r<String, zzrf> bBc;
    private final android.support.v4.e.r<String, zzrc> bBd;
    private final zzlg bBe;
    private final Context mContext;
    private final zzw zzwc;
    private final zzxn zzwh;
    private final zzkh zzxs;
    private final zzpl zzyb;
    private final String zzye;
    private final zzang zzyf;
    private WeakReference<zzd> zzyg;
    private final Object mLock = new Object();
    private final List<String> zzyc = WX();

    zzah(Context context, String str, zzxn zzxnVar, zzang zzangVar, zzkh zzkhVar, zzqw zzqwVar, zzrl zzrlVar, zzqz zzqzVar, android.support.v4.e.r<String, zzrf> rVar, android.support.v4.e.r<String, zzrc> rVar2, zzpl zzplVar, zzlg zzlgVar, zzw zzwVar, zzri zzriVar, zzjn zzjnVar, PublisherAdViewOptions publisherAdViewOptions) {
        this.mContext = context;
        this.zzye = str;
        this.zzwh = zzxnVar;
        this.zzyf = zzangVar;
        this.zzxs = zzkhVar;
        this.bAY = zzqzVar;
        this.bAW = zzqwVar;
        this.bAX = zzrlVar;
        this.bBc = rVar;
        this.bBd = rVar2;
        this.zzyb = zzplVar;
        this.bBe = zzlgVar;
        this.zzwc = zzwVar;
        this.bAZ = zzriVar;
        this.bBa = zzjnVar;
        this.bBb = publisherAdViewOptions;
        zznk.initialize(this.mContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final boolean WV() {
        return ((Boolean) zzkb.zzik().zzd(zznk.zzaym)).booleanValue() && this.bAZ != null;
    }

    private final boolean WW() {
        return (this.bAW == null && this.bAY == null && this.bAX == null && (this.bBc == null || this.bBc.size() <= 0)) ? false : true;
    }

    private final List<String> WX() {
        ArrayList arrayList = new ArrayList();
        if (this.bAY != null) {
            arrayList.add(AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
        if (this.bAW != null) {
            arrayList.add("2");
        }
        if (this.bAX != null) {
            arrayList.add("6");
        }
        if (this.bBc.size() > 0) {
            arrayList.add("3");
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(zzjj zzjjVar) {
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzbcg)).booleanValue() && this.bAX != null) {
            zzi(0);
            return;
        }
        zzq zzqVar = new zzq(this.mContext, this.zzwc, this.bBa, this.zzye, this.zzwh, this.zzyf);
        this.zzyg = new WeakReference<>(zzqVar);
        zzri zzriVar = this.bAZ;
        bf.hV("setOnPublisherAdViewLoadedListener must be called on the main UI thread.");
        zzqVar.zzvw.bDi = zzriVar;
        if (this.bBb != null) {
            if (this.bBb.zzbg() != null) {
                zzqVar.zza(this.bBb.zzbg());
            }
            zzqVar.setManualImpressionsEnabled(this.bBb.getManualImpressionsEnabled());
        }
        zzqw zzqwVar = this.bAW;
        bf.hV("setOnAppInstallAdLoadedListener must be called on the main UI thread.");
        zzqVar.zzvw.bDc = zzqwVar;
        zzrl zzrlVar = this.bAX;
        bf.hV("setOnUnifiedNativeAdLoadedListener must be called on the main UI thread.");
        zzqVar.zzvw.bDe = zzrlVar;
        zzqz zzqzVar = this.bAY;
        bf.hV("setOnContentAdLoadedListener must be called on the main UI thread.");
        zzqVar.zzvw.bDd = zzqzVar;
        android.support.v4.e.r<String, zzrf> rVar = this.bBc;
        bf.hV("setOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        zzqVar.zzvw.bDg = rVar;
        android.support.v4.e.r<String, zzrc> rVar2 = this.bBd;
        bf.hV("setOnCustomClickListener must be called on the main UI thread.");
        zzqVar.zzvw.bDf = rVar2;
        zzpl zzplVar = this.zzyb;
        bf.hV("setNativeAdOptions must be called on the main UI thread.");
        zzqVar.zzvw.zzadj = zzplVar;
        zzqVar.zzd(WX());
        zzqVar.zza(this.zzxs);
        zzqVar.zza(this.bBe);
        ArrayList arrayList = new ArrayList();
        if (WW()) {
            arrayList.add(1);
        }
        if (this.bAZ != null) {
            arrayList.add(2);
        }
        zzqVar.zze(arrayList);
        if (WW()) {
            zzjjVar.extras.putBoolean("ina", true);
        }
        if (this.bAZ != null) {
            zzjjVar.extras.putBoolean("iba", true);
        }
        zzqVar.zzb(zzjjVar);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void a(zzjj zzjjVar, int i) {
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzbcg)).booleanValue() && this.bAX != null) {
            zzi(0);
            return;
        }
        zzbc zzbcVar = new zzbc(this.mContext, this.zzwc, zzjn.zzf(this.mContext), this.zzye, this.zzwh, this.zzyf);
        this.zzyg = new WeakReference<>(zzbcVar);
        zzqw zzqwVar = this.bAW;
        bf.hV("setOnAppInstallAdLoadedListener must be called on the main UI thread.");
        zzbcVar.zzvw.bDc = zzqwVar;
        zzrl zzrlVar = this.bAX;
        bf.hV("setOnUnifiedNativeAdLoadedListener must be called on the main UI thread.");
        zzbcVar.zzvw.bDe = zzrlVar;
        zzqz zzqzVar = this.bAY;
        bf.hV("setOnContentAdLoadedListener must be called on the main UI thread.");
        zzbcVar.zzvw.bDd = zzqzVar;
        android.support.v4.e.r<String, zzrf> rVar = this.bBc;
        bf.hV("setOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        zzbcVar.zzvw.bDg = rVar;
        zzbcVar.zza(this.zzxs);
        android.support.v4.e.r<String, zzrc> rVar2 = this.bBd;
        bf.hV("setOnCustomClickListener must be called on the main UI thread.");
        zzbcVar.zzvw.bDf = rVar2;
        zzbcVar.zzd(WX());
        zzpl zzplVar = this.zzyb;
        bf.hV("setNativeAdOptions must be called on the main UI thread.");
        zzbcVar.zzvw.zzadj = zzplVar;
        zzbcVar.zza(this.bBe);
        zzbcVar.zzj(i);
        zzbcVar.zzb(zzjjVar);
    }

    private static void runOnUiThread(Runnable runnable) {
        zzakk.zzcrm.post(runnable);
    }

    private final void zzi(int i) {
        if (this.zzxs != null) {
            try {
                this.zzxs.onAdFailedToLoad(0);
            } catch (RemoteException e) {
                zzakb.zzc("Failed calling onAdFailedToLoad.", e);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzkk
    public final String getMediationAdapterClassName() {
        synchronized (this.mLock) {
            if (this.zzyg == null) {
                return null;
            }
            zzd zzdVar = this.zzyg.get();
            return zzdVar != null ? zzdVar.getMediationAdapterClassName() : null;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzkk
    public final boolean isLoading() {
        synchronized (this.mLock) {
            if (this.zzyg == null) {
                return false;
            }
            zzd zzdVar = this.zzyg.get();
            return zzdVar != null ? zzdVar.isLoading() : false;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzkk
    public final void zza(zzjj zzjjVar, int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Number of ads has to be more than 0");
        }
        runOnUiThread(new e(this, zzjjVar, i));
    }

    @Override // com.google.android.gms.internal.ads.zzkk
    public final String zzck() {
        synchronized (this.mLock) {
            if (this.zzyg == null) {
                return null;
            }
            zzd zzdVar = this.zzyg.get();
            return zzdVar != null ? zzdVar.zzck() : null;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzkk
    public final void zzd(zzjj zzjjVar) {
        runOnUiThread(new d(this, zzjjVar));
    }
}
