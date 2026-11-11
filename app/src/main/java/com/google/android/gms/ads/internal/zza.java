package com.google.android.gms.ads.internal;

import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.MessengerShareContentUtility;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.gmsg.zzb;
import com.google.android.gms.ads.internal.overlay.zzt;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzaaw;
import com.google.android.gms.internal.ads.zzabc;
import com.google.android.gms.internal.ads.zzabm;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzadj;
import com.google.android.gms.internal.ads.zzagp;
import com.google.android.gms.internal.ads.zzagx;
import com.google.android.gms.internal.ads.zzahe;
import com.google.android.gms.internal.ads.zzaig;
import com.google.android.gms.internal.ads.zzajb;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzajj;
import com.google.android.gms.internal.ads.zzajs;
import com.google.android.gms.internal.ads.zzaju;
import com.google.android.gms.internal.ads.zzajz;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzamu;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzasi;
import com.google.android.gms.internal.ads.zzes;
import com.google.android.gms.internal.ads.zzhu;
import com.google.android.gms.internal.ads.zzjd;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjk;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzke;
import com.google.android.gms.internal.ads.zzkh;
import com.google.android.gms.internal.ads.zzkt;
import com.google.android.gms.internal.ads.zzkx;
import com.google.android.gms.internal.ads.zzla;
import com.google.android.gms.internal.ads.zzlg;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zzlu;
import com.google.android.gms.internal.ads.zzms;
import com.google.android.gms.internal.ads.zzmu;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zznv;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzod;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.CountDownLatch;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public abstract class zza extends zzkt implements zzb, com.google.android.gms.ads.internal.gmsg.zzd, zzt, zzabm, zzadj, zzajs, zzjd {
    protected zznx zzvr;
    protected zznv zzvs;
    private zznv zzvt;
    protected final zzbw zzvw;
    protected transient zzjj zzvx;
    protected final zzes zzvy;
    protected com.google.android.gms.c.a zzwb;
    protected final zzw zzwc;
    protected boolean zzvu = false;
    private final Bundle zzvz = new Bundle();
    private boolean zzwa = false;
    protected final zzbl zzvv = new zzbl(this);

    zza(zzbw zzbwVar, zzbl zzblVar, zzw zzwVar) {
        this.zzvw = zzbwVar;
        this.zzwc = zzwVar;
        zzbv.zzek().zzak(this.zzvw.zzrt);
        zzbv.zzek().zzal(this.zzvw.zzrt);
        zzajz.zzai(this.zzvw.zzrt);
        zzbv.zzfi().initialize(this.zzvw.zzrt);
        zzbv.zzeo().zzd(this.zzvw.zzrt, this.zzvw.zzacr);
        zzbv.zzeq().initialize(this.zzvw.zzrt);
        this.zzvy = zzbv.zzeo().zzqd();
        zzbv.zzen().initialize(this.zzvw.zzrt);
        zzbv.zzfk().initialize(this.zzvw.zzrt);
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbci)).booleanValue()) {
            Timer timer = new Timer();
            timer.schedule(new p(this, new CountDownLatch(((Integer) zzkb.zzik().zzd(zznk.zzbck)).intValue()), timer), 0L, ((Long) zzkb.zzik().zzd(zznk.zzbcj)).longValue());
        }
    }

    protected static boolean zza(zzjj zzjjVar) {
        Bundle bundle = zzjjVar.zzaqg.getBundle("com.google.ads.mediation.admob.AdMobAdapter");
        return bundle == null || !bundle.containsKey("gw");
    }

    private static long zzq(String str) {
        int iIndexOf = str.indexOf("ufe");
        int iIndexOf2 = str.indexOf(44, iIndexOf);
        if (iIndexOf2 == -1) {
            iIndexOf2 = str.length();
        }
        try {
            return Long.parseLong(str.substring(iIndexOf + 4, iIndexOf2));
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            zzane.zzb("", e);
            return -1L;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void destroy() {
        bf.hV("#008 Must be called on the main UI thread.: destroy");
        this.zzvv.cancel();
        this.zzvy.zzi(this.zzvw.zzacw);
        zzbw zzbwVar = this.zzvw;
        if (zzbwVar.bCW != null) {
            zzbwVar.bCW.zzfs();
        }
        zzbwVar.bCY = null;
        zzbwVar.bDa = null;
        zzbwVar.bCZ = null;
        zzbwVar.bDj = null;
        zzbwVar.bDb = null;
        zzbwVar.zzg(false);
        if (zzbwVar.bCW != null) {
            zzbwVar.bCW.removeAllViews();
        }
        zzbwVar.zzfm();
        zzbwVar.zzfn();
        zzbwVar.zzacw = null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public String getAdUnitId() {
        return this.zzvw.zzacp;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public zzlo getVideoController() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean isLoading() {
        return this.zzvu;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final boolean isReady() {
        bf.hV("#008 Must be called on the main UI thread.: isLoaded");
        return this.zzvw.zzact == null && this.zzvw.zzacu == null && this.zzvw.zzacw != null;
    }

    @Override // com.google.android.gms.internal.ads.zzjd
    public void onAdClicked() {
        if (this.zzvw.zzacw == null) {
            zzakb.zzdk("Ad state was null when trying to ping click URLs.");
            return;
        }
        zzakb.zzck("Pinging click URLs.");
        if (this.zzvw.zzacy != null) {
            this.zzvw.zzacy.zzpn();
        }
        if (this.zzvw.zzacw.zzbsn != null) {
            zzbv.zzek();
            zzakk.zza(this.zzvw.zzrt, this.zzvw.zzacr.zzcw, zzc(this.zzvw.zzacw.zzbsn));
        }
        if (this.zzvw.bCX != null) {
            try {
                this.zzvw.bCX.onAdClicked();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzd
    public final void onAppEvent(String str, String str2) {
        if (this.zzvw.bCZ != null) {
            try {
                this.zzvw.bCZ.onAppEvent(str, str2);
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void pause() {
        bf.hV("#008 Must be called on the main UI thread.: pause");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void resume() {
        bf.hV("#008 Must be called on the main UI thread.: resume");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void setImmersiveMode(boolean z) {
        throw new IllegalStateException("#005 Unexpected call to an abstract (unimplemented) method.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void setManualImpressionsEnabled(boolean z) {
        zzakb.zzdk("Attempt to call setManualImpressionsEnabled for an unsupported ad type.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void setUserId(String str) {
        bf.hV("#008 Must be called on the main UI thread.: setUserId");
        this.zzvw.zzadr = str;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void stopLoading() {
        bf.hV("#008 Must be called on the main UI thread.: stopLoading");
        this.zzvu = false;
        this.zzvw.zzg(true);
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void zza(zzaaw zzaawVar) {
        zzakb.zzdk("#006 Unexpected call to a deprecated method.");
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzabc zzabcVar, String str) {
        zzakb.zzdk("#006 Unexpected call to a deprecated method.");
    }

    public final void zza(zzagx zzagxVar) {
        bf.hV("#008 Must be called on the main UI thread.: setRewardedAdSkuListener");
        this.zzvw.bDl = zzagxVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzahe zzaheVar) {
        bf.hV("#008 Must be called on the main UI thread.: setRewardedVideoAdListener");
        this.zzvw.bDk = zzaheVar;
    }

    protected final void zza(zzaig zzaigVar) {
        if (this.zzvw.bDk == null) {
            return;
        }
        String str = "";
        int i = 1;
        if (zzaigVar != null) {
            try {
                str = zzaigVar.type;
                i = zzaigVar.zzcmk;
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
                return;
            }
        }
        zzagp zzagpVar = new zzagp(str, i);
        this.zzvw.bDk.zza(zzagpVar);
        if (this.zzvw.bDl != null) {
            this.zzvw.bDl.zza(zzagpVar, this.zzvw.zzacx.zzcgs.zzcdi);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzadj
    public final void zza(zzaji zzajiVar) {
        if (zzajiVar.zzcos.zzceu != -1 && !TextUtils.isEmpty(zzajiVar.zzcos.zzcfd)) {
            long jZzq = zzq(zzajiVar.zzcos.zzcfd);
            if (jZzq != -1) {
                this.zzvr.zza(this.zzvr.zzd(jZzq + zzajiVar.zzcos.zzceu), "stc");
            }
        }
        this.zzvr.zzan(zzajiVar.zzcos.zzcfd);
        this.zzvr.zza(this.zzvs, "arf");
        this.zzvt = this.zzvr.zzjj();
        this.zzvr.zze("gqi", zzajiVar.zzcos.zzamj);
        this.zzvw.zzact = null;
        this.zzvw.zzacx = zzajiVar;
        zzajiVar.zzcoq.zza(new af(this, zzajiVar));
        zzajiVar.zzcoq.zza(zzhu.zza.zzb.AD_LOADED);
        zza(zzajiVar, this.zzvr);
    }

    protected abstract void zza(zzaji zzajiVar, zznx zznxVar);

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzjn zzjnVar) {
        bf.hV("#008 Must be called on the main UI thread.: setAdSize");
        this.zzvw.zzacv = zzjnVar;
        if (this.zzvw.zzacw != null && this.zzvw.zzacw.zzbyo != null && this.zzvw.zzadv == 0) {
            this.zzvw.zzacw.zzbyo.zza(zzasi.zzb(zzjnVar));
        }
        if (this.zzvw.bCW == null) {
            return;
        }
        if (this.zzvw.bCW.getChildCount() > 1) {
            this.zzvw.bCW.removeView(this.zzvw.bCW.getNextView());
        }
        this.zzvw.bCW.setMinimumWidth(zzjnVar.widthPixels);
        this.zzvw.bCW.setMinimumHeight(zzjnVar.heightPixels);
        this.zzvw.bCW.requestLayout();
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzke zzkeVar) {
        bf.hV("#008 Must be called on the main UI thread.: setAdClickListener");
        this.zzvw.bCX = zzkeVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzkh zzkhVar) {
        bf.hV("#008 Must be called on the main UI thread.: setAdListener");
        this.zzvw.bCY = zzkhVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzkx zzkxVar) {
        this.zzvw.bDa = zzkxVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzla zzlaVar) {
        bf.hV("#008 Must be called on the main UI thread.: setAppEventListener");
        this.zzvw.bCZ = zzlaVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzlg zzlgVar) {
        bf.hV("#008 Must be called on the main UI thread.: setCorrelationIdProvider");
        this.zzvw.bDb = zzlgVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzlu zzluVar) {
        bf.hV("#008 Must be called on the main UI thread.: setIconAdOptions");
        this.zzvw.zzadl = zzluVar;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zza(zzmu zzmuVar) {
        bf.hV("#008 Must be called on the main UI thread.: setVideoOptions");
        this.zzvw.bDh = zzmuVar;
    }

    public final void zza(zznv zznvVar) {
        this.zzvr = new zznx(((Boolean) zzkb.zzik().zzd(zznk.zzawh)).booleanValue(), "load_ad", this.zzvw.zzacv.zzarb);
        this.zzvt = new zznv(-1L, null, null);
        if (zznvVar == null) {
            this.zzvs = new zznv(-1L, null, null);
        } else {
            this.zzvs = new zznv(zznvVar.getTime(), zznvVar.zzjg(), zznvVar.zzjh());
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public void zza(zzod zzodVar) {
        throw new IllegalStateException("#005 Unexpected call to an abstract (unimplemented) method.");
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzb
    public final void zza(String str, Bundle bundle) {
        this.zzvz.putAll(bundle);
        if (!this.zzwa || this.zzvw.bDa == null) {
            return;
        }
        try {
            this.zzvw.bDa.zzt();
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzajs
    public final void zza(HashSet<zzajj> hashSet) {
        this.zzvw.zza(hashSet);
    }

    boolean zza(zzajh zzajhVar) {
        return false;
    }

    protected abstract boolean zza(zzajh zzajhVar, zzajh zzajhVar2);

    protected abstract boolean zza(zzjj zzjjVar, zznx zznxVar);

    protected final List<String> zzb(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(zzajb.zzc(it.next(), this.zzvw.zzrt));
        }
        return arrayList;
    }

    @Override // com.google.android.gms.internal.ads.zzabm
    public void zzb(zzajh zzajhVar) {
        this.zzvr.zza(this.zzvt, "awr");
        this.zzvw.zzacu = null;
        if (zzajhVar.errorCode != -2 && zzajhVar.errorCode != 3 && this.zzvw.zzfl() != null) {
            zzbv.zzep().zzb(this.zzvw.zzfl());
        }
        if (zzajhVar.errorCode == -1) {
            this.zzvu = false;
            return;
        }
        if (zza(zzajhVar)) {
            zzakb.zzck("Ad refresh scheduled.");
        }
        if (zzajhVar.errorCode != -2) {
            if (zzajhVar.errorCode == 3) {
                zzajhVar.zzcoq.zza(zzhu.zza.zzb.AD_FAILED_TO_LOAD_NO_FILL);
            } else {
                zzajhVar.zzcoq.zza(zzhu.zza.zzb.AD_FAILED_TO_LOAD);
            }
            zzi(zzajhVar.errorCode);
            return;
        }
        if (this.zzvw.zzadt == null) {
            this.zzvw.zzadt = new zzaju(this.zzvw.zzacp);
        }
        if (this.zzvw.bCW != null) {
            this.zzvw.bCW.zzfr().zzdc(zzajhVar.zzcfl);
        }
        this.zzvy.zzh(this.zzvw.zzacw);
        if (zza(this.zzvw.zzacw, zzajhVar)) {
            this.zzvw.zzacw = zzajhVar;
            zzbw zzbwVar = this.zzvw;
            if (zzbwVar.zzacy != null) {
                if (zzbwVar.zzacw != null) {
                    zzbwVar.zzacy.zzh(zzbwVar.zzacw.zzcoh);
                    zzbwVar.zzacy.zzi(zzbwVar.zzacw.zzcoi);
                    zzbwVar.zzacy.zzz(zzbwVar.zzacw.zzceq);
                }
                zzbwVar.zzacy.zzy(zzbwVar.zzacv.zzarc);
            }
            this.zzvr.zze("is_mraid", this.zzvw.zzacw.zzfz() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            this.zzvr.zze("is_mediation", this.zzvw.zzacw.zzceq ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            if (this.zzvw.zzacw.zzbyo != null && this.zzvw.zzacw.zzbyo.zzuf() != null) {
                this.zzvr.zze("is_delay_pl", this.zzvw.zzacw.zzbyo.zzuf().zzux() ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            }
            this.zzvr.zza(this.zzvs, "ttc");
            if (zzbv.zzeo().zzpy() != null) {
                zzbv.zzeo().zzpy().zza(this.zzvr);
            }
            zzbv();
            if (this.zzvw.zzfo()) {
                zzbq();
            }
        }
        if (zzajhVar.zzbsr != null) {
            zzbv.zzek().zza(this.zzvw.zzrt, zzajhVar.zzbsr);
        }
    }

    protected void zzb(boolean z) {
        zzakb.v("Ad finished loading.");
        this.zzvu = z;
        this.zzwa = true;
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdLoaded();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
        if (this.zzvw.bDk != null) {
            try {
                this.zzvw.bDk.onRewardedVideoAdLoaded();
            } catch (RemoteException e2) {
                zzakb.zzd("#007 Could not call remote method.", e2);
            }
        }
        if (this.zzvw.bDa != null) {
            try {
                this.zzvw.bDa.zzt();
            } catch (RemoteException e3) {
                zzakb.zzd("#007 Could not call remote method.", e3);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public boolean zzb(zzjj zzjjVar) {
        bf.hV("#008 Must be called on the main UI thread.: loadAd");
        zzbv.zzeq().zzhh();
        this.zzvz.clear();
        this.zzwa = false;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzayo)).booleanValue()) {
            zzjjVar = zzjjVar.zzhv();
            if (((Boolean) zzkb.zzik().zzd(zznk.zzayp)).booleanValue()) {
                zzjjVar.extras.putBoolean(AdMobAdapter.NEW_BUNDLE, true);
            }
        }
        if (com.google.android.gms.common.d.i.aO(this.zzvw.zzrt) && zzjjVar.zzaqe != null) {
            zzjjVar = new zzjk(zzjjVar).zza(null).zzhw();
        }
        if (this.zzvw.zzact != null || this.zzvw.zzacu != null) {
            if (this.zzvx != null) {
                zzakb.zzdk("Aborting last ad request since another ad request is already in progress. The current request object will still be cached for future refreshes.");
            } else {
                zzakb.zzdk("Loading already in progress, saving this object for future refreshes.");
            }
            this.zzvx = zzjjVar;
            return false;
        }
        zzakb.zzdj("Starting ad request.");
        zza((zznv) null);
        this.zzvs = this.zzvr.zzjj();
        if (zzjjVar.zzapz) {
            zzakb.zzdj("This request is sent from a test device.");
        } else {
            zzkb.zzif();
            String strZzbc = zzamu.zzbc(this.zzvw.zzrt);
            zzakb.zzdj(new StringBuilder(String.valueOf(strZzbc).length() + 71).append("Use AdRequest.Builder.addTestDevice(\"").append(strZzbc).append("\") to get test ads on this device.").toString());
        }
        this.zzvv.zzf(zzjjVar);
        this.zzvu = zza(zzjjVar, this.zzvr);
        return this.zzvu;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final Bundle zzba() {
        return this.zzwa ? this.zzvz : new Bundle();
    }

    public final zzw zzbi() {
        return this.zzwc;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final com.google.android.gms.c.a zzbj() {
        bf.hV("#008 Must be called on the main UI thread.: getAdFrame");
        return com.google.android.gms.c.d.F(this.zzvw.bCW);
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzjn zzbk() {
        bf.hV("#008 Must be called on the main UI thread.: getAdSize");
        if (this.zzvw.zzacv == null) {
            return null;
        }
        return new zzms(this.zzvw.zzacv);
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzt
    public final void zzbl() {
        zzbo();
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final void zzbm() {
        bf.hV("#008 Must be called on the main UI thread.: recordManualImpression");
        if (this.zzvw.zzacw == null) {
            zzakb.zzdk("Ad state was null when trying to ping manual tracking URLs.");
            return;
        }
        zzakb.zzck("Pinging manual tracking URLs.");
        if (this.zzvw.zzacw.zzcoo) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        if (this.zzvw.zzacw.zzces != null) {
            arrayList.addAll(this.zzvw.zzacw.zzces);
        }
        if (this.zzvw.zzacw.zzbtw != null && this.zzvw.zzacw.zzbtw.zzbrz != null) {
            arrayList.addAll(this.zzvw.zzacw.zzbtw.zzbrz);
        }
        if (arrayList.isEmpty()) {
            return;
        }
        zzbv.zzek();
        zzakk.zza(this.zzvw.zzrt, this.zzvw.zzacr.zzcw, arrayList);
        this.zzvw.zzacw.zzcoo = true;
    }

    protected void zzbn() {
        zzakb.v("Ad closing.");
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdClosed();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
        if (this.zzvw.bDk != null) {
            try {
                this.zzvw.bDk.onRewardedVideoAdClosed();
            } catch (RemoteException e2) {
                zzakb.zzd("#007 Could not call remote method.", e2);
            }
        }
    }

    protected final void zzbo() {
        zzakb.v("Ad leaving application.");
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdLeftApplication();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
        if (this.zzvw.bDk != null) {
            try {
                this.zzvw.bDk.onRewardedVideoAdLeftApplication();
            } catch (RemoteException e2) {
                zzakb.zzd("#007 Could not call remote method.", e2);
            }
        }
    }

    protected final void zzbp() {
        zzakb.v("Ad opening.");
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdOpened();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
        if (this.zzvw.bDk != null) {
            try {
                this.zzvw.bDk.onRewardedVideoAdOpened();
            } catch (RemoteException e2) {
                zzakb.zzd("#007 Could not call remote method.", e2);
            }
        }
    }

    protected void zzbq() {
        zzb(false);
    }

    public final void zzbr() {
        zzakb.zzdj("Ad impression.");
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdImpression();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void zzbs() {
        zzakb.zzdj("Ad clicked.");
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdClicked();
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    protected final void zzbt() {
        if (this.zzvw.bDk == null) {
            return;
        }
        try {
            this.zzvw.bDk.onRewardedVideoStarted();
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }

    protected final void zzbu() {
        if (this.zzvw.bDk == null) {
            return;
        }
        try {
            this.zzvw.bDk.onRewardedVideoCompleted();
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }

    public final void zzbv() {
        zzajh zzajhVar = this.zzvw.zzacw;
        if (zzajhVar == null || TextUtils.isEmpty(zzajhVar.zzcfl) || zzajhVar.zzcop || !zzbv.zzeu().zzrx()) {
            return;
        }
        zzakb.zzck("Sending troubleshooting signals to the server.");
        zzbv.zzeu().zzb(this.zzvw.zzrt, this.zzvw.zzacr.zzcw, zzajhVar.zzcfl, this.zzvw.zzacp);
        zzajhVar.zzcop = true;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzla zzbw() {
        return this.zzvw.bCZ;
    }

    @Override // com.google.android.gms.internal.ads.zzks
    public final zzkh zzbx() {
        return this.zzvw.bCY;
    }

    protected final void zzby() {
        if (this.zzwb != null) {
            zzbv.zzfa().zzn(this.zzwb);
            this.zzwb = null;
        }
    }

    protected final String zzbz() {
        zzaji zzajiVar = this.zzvw.zzacx;
        if (zzajiVar == null || zzajiVar.zzcos == null) {
            return "javascript";
        }
        String str = zzajiVar.zzcos.zzcfq;
        if (TextUtils.isEmpty(str)) {
            return "javascript";
        }
        try {
            if (new JSONObject(str).optInt(MessengerShareContentUtility.MEDIA_TYPE, -1) == 0) {
                return null;
            }
            return "javascript";
        } catch (JSONException e) {
            zzane.zzc("", e);
            return "javascript";
        }
    }

    protected final List<String> zzc(List<String> list) {
        ArrayList arrayList = new ArrayList(list.size());
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(zzajb.zzb(it.next(), this.zzvw.zzrt));
        }
        return arrayList;
    }

    protected void zzc(int i, boolean z) {
        zzakb.zzdk(new StringBuilder(30).append("Failed to load ad: ").append(i).toString());
        this.zzvu = z;
        if (this.zzvw.bCY != null) {
            try {
                this.zzvw.bCY.onAdFailedToLoad(i);
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        }
        if (this.zzvw.bDk != null) {
            try {
                this.zzvw.bDk.onRewardedVideoAdFailedToLoad(i);
            } catch (RemoteException e2) {
                zzakb.zzd("#007 Could not call remote method.", e2);
            }
        }
    }

    protected boolean zzc(zzjj zzjjVar) {
        if (this.zzvw.bCW == null) {
            return false;
        }
        Object parent = this.zzvw.bCW.getParent();
        if (!(parent instanceof View)) {
            return false;
        }
        View view = (View) parent;
        return zzbv.zzek().zza(view, view.getContext());
    }

    protected final void zzg(View view) {
        zzbx zzbxVar = this.zzvw.bCW;
        if (zzbxVar != null) {
            zzbxVar.addView(view, zzbv.zzem().zzro());
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void zzi(int i) {
        zzc(i, false);
    }
}
