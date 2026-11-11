package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import com.google.android.gms.c.a;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzub extends zzkt {
    private final zzss zzbom;
    private com.google.android.gms.ads.internal.zzal zzbor;
    private final zztt zzbpd;
    private final String zzye;
    private boolean zzyu;

    public zzub(Context context, String str, zzxn zzxnVar, zzang zzangVar, com.google.android.gms.ads.internal.zzw zzwVar) {
        this(str, new zzss(context, zzxnVar, zzangVar, zzwVar));
    }

    private zzub(String str, zzss zzssVar) {
        this.zzye = str;
        this.zzbom = zzssVar;
        this.zzbpd = new zztt();
        com.google.android.gms.ads.internal.zzbv.zzex().zza(zzssVar);
    }

    private final void abort() {
        if (this.zzbor != null) {
            return;
        }
        this.zzbor = this.zzbom.zzav(this.zzye);
        this.zzbpd.zzd(this.zzbor);
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void destroy() {
        if (this.zzbor != null) {
            this.zzbor.destroy();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final String getAdUnitId() {
        throw new IllegalStateException("getAdUnitId not implemented");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final String getMediationAdapterClassName() {
        if (this.zzbor != null) {
            return this.zzbor.getMediationAdapterClassName();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzlo getVideoController() {
        throw new IllegalStateException("getVideoController not implemented for interstitials");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean isLoading() {
        return this.zzbor != null && this.zzbor.isLoading();
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean isReady() {
        return this.zzbor != null && this.zzbor.isReady();
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void pause() {
        if (this.zzbor != null) {
            this.zzbor.pause();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void resume() {
        if (this.zzbor != null) {
            this.zzbor.resume();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setImmersiveMode(boolean z) {
        this.zzyu = z;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setManualImpressionsEnabled(boolean z) {
        abort();
        if (this.zzbor != null) {
            this.zzbor.setManualImpressionsEnabled(z);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setUserId(String str) {
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void showInterstitial() {
        if (this.zzbor == null) {
            zzakb.zzdk("Interstitial ad must be loaded before showInterstitial().");
        } else {
            this.zzbor.setImmersiveMode(this.zzyu);
            this.zzbor.showInterstitial();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void stopLoading() {
        if (this.zzbor != null) {
            this.zzbor.stopLoading();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzaaw zzaawVar) {
        zzakb.zzdk("setInAppPurchaseListener is deprecated and should not be called.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzabc zzabcVar, String str) {
        zzakb.zzdk("setPlayStorePurchaseParams is deprecated and should not be called.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzahe zzaheVar) {
        this.zzbpd.zzboh = zzaheVar;
        if (this.zzbor != null) {
            this.zzbpd.zzd(this.zzbor);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzjn zzjnVar) {
        if (this.zzbor != null) {
            this.zzbor.zza(zzjnVar);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzke zzkeVar) {
        this.zzbpd.zzbog = zzkeVar;
        if (this.zzbor != null) {
            this.zzbpd.zzd(this.zzbor);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzkh zzkhVar) {
        this.zzbpd.zzxs = zzkhVar;
        if (this.zzbor != null) {
            this.zzbpd.zzd(this.zzbor);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzkx zzkxVar) {
        this.zzbpd.zzbod = zzkxVar;
        if (this.zzbor != null) {
            this.zzbpd.zzd(this.zzbor);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzla zzlaVar) {
        this.zzbpd.zzboe = zzlaVar;
        if (this.zzbor != null) {
            this.zzbpd.zzd(this.zzbor);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzlg zzlgVar) {
        abort();
        if (this.zzbor != null) {
            this.zzbor.zza(zzlgVar);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzlu zzluVar) {
        throw new IllegalStateException("Unused method");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzmu zzmuVar) {
        throw new IllegalStateException("getVideoController not implemented for interstitials");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzod zzodVar) {
        this.zzbpd.zzbof = zzodVar;
        if (this.zzbor != null) {
            this.zzbpd.zzd(this.zzbor);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean zzb(zzjj zzjjVar) {
        if (!zztw.zzh(zzjjVar).contains("gw")) {
            abort();
        }
        if (zztw.zzh(zzjjVar).contains("_skipMediation")) {
            abort();
        }
        if (zzjjVar.zzaqd != null) {
            abort();
        }
        if (this.zzbor != null) {
            return this.zzbor.zzb(zzjjVar);
        }
        zztw zztwVarZzex = com.google.android.gms.ads.internal.zzbv.zzex();
        if (zztw.zzh(zzjjVar).contains("_ad")) {
            zztwVarZzex.zzb(zzjjVar, this.zzye);
        }
        zztz zztzVarZza = zztwVarZzex.zza(zzjjVar, this.zzye);
        if (zztzVarZza == null) {
            abort();
            zzua.zzlk().zzlo();
            return this.zzbor.zzb(zzjjVar);
        }
        if (zztzVarZza.zzwa) {
            zzua.zzlk().zzln();
        } else {
            zztzVarZza.load();
            zzua.zzlk().zzlo();
        }
        this.zzbor = zztzVarZza.zzbor;
        zztzVarZza.zzbot.zza(this.zzbpd);
        this.zzbpd.zzd(this.zzbor);
        return zztzVarZza.zzbov;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final Bundle zzba() {
        return this.zzbor != null ? this.zzbor.zzba() : new Bundle();
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final a zzbj() {
        if (this.zzbor != null) {
            return this.zzbor.zzbj();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzjn zzbk() {
        if (this.zzbor != null) {
            return this.zzbor.zzbk();
        }
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zzbm() {
        if (this.zzbor != null) {
            this.zzbor.zzbm();
        } else {
            zzakb.zzdk("Interstitial ad must be loaded before pingManualTrackingUrl().");
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzla zzbw() {
        throw new IllegalStateException("getIAppEventListener not implemented");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzkh zzbx() {
        throw new IllegalStateException("getIAdListener not implemented");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final String zzck() {
        if (this.zzbor != null) {
            return this.zzbor.zzck();
        }
        return null;
    }
}
