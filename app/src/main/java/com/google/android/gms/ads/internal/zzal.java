package com.google.android.gms.ads.internal;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.Window;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.gmsg.zzai;
import com.google.android.gms.ads.internal.gmsg.zzz;
import com.google.android.gms.ads.internal.overlay.AdOverlayInfoParcel;
import com.google.android.gms.ads.internal.overlay.zzl;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzaej;
import com.google.android.gms.internal.ads.zzafs;
import com.google.android.gms.internal.ads.zzago;
import com.google.android.gms.internal.ads.zzaig;
import com.google.android.gms.internal.ads.zzait;
import com.google.android.gms.internal.ads.zzaix;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzakq;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarc;
import com.google.android.gms.internal.ads.zzasc;
import com.google.android.gms.internal.ads.zzasf;
import com.google.android.gms.internal.ads.zzasi;
import com.google.android.gms.internal.ads.zzfp;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzwx;
import com.google.android.gms.internal.ads.zzwy;
import com.google.android.gms.internal.ads.zzxn;
import java.util.Collections;
import java.util.HashMap;
import javax.annotation.ParametersAreNonnullByDefault;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzal extends zzi implements zzai, zzz {
    private transient boolean bBn;
    private int bBo;
    private boolean bBp;
    private float bBq;
    private String bBr;
    private final String bBs;
    private final zzago bBt;
    private boolean zzyu;
    private zzaix zzyv;

    public zzal(Context context, zzjn zzjnVar, String str, zzxn zzxnVar, zzang zzangVar, zzw zzwVar) {
        super(context, zzjnVar, str, zzxnVar, zzangVar, zzwVar);
        this.bBo = -1;
        this.bBn = false;
        boolean z = zzjnVar != null && "reward_mb".equals(zzjnVar.zzarb);
        this.bBs = z ? "/Rewarded" : "/Interstitial";
        this.bBt = z ? new zzago(this.zzvw, this.zzwh, new g(this), this, this) : null;
    }

    private static zzaji a(zzaji zzajiVar) {
        try {
            String string = zzafs.zzb(zzajiVar.zzcos).toString();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(com.google.ads.mediation.a.AD_UNIT_ID_PARAMETER, zzajiVar.zzcgs.zzacp);
            zzwx zzwxVar = new zzwx(string, null, Collections.singletonList("com.google.ads.mediation.admob.AdMobAdapter"), null, null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), jSONObject.toString(), null, Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), null, null, null, null, null, Collections.emptyList(), null, -1L);
            zzaej zzaejVar = zzajiVar.zzcos;
            zzwy zzwyVar = new zzwy(Collections.singletonList(zzwxVar), ((Long) zzkb.zzik().zzd(zznk.zzbao)).longValue(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), Collections.emptyList(), zzaejVar.zzbsr, zzaejVar.zzbss, "", -1L, 0, 1, null, 0, -1, -1L, false);
            return new zzaji(zzajiVar.zzcgs, new zzaej(zzajiVar.zzcgs, zzaejVar.zzbyq, zzaejVar.zzceo, Collections.emptyList(), Collections.emptyList(), zzaejVar.zzcep, true, zzaejVar.zzcer, Collections.emptyList(), zzaejVar.zzbsu, zzaejVar.orientation, zzaejVar.zzcet, zzaejVar.zzceu, zzaejVar.zzcev, zzaejVar.zzcew, zzaejVar.zzcex, null, zzaejVar.zzcez, zzaejVar.zzare, zzaejVar.zzcdd, zzaejVar.zzcfa, zzaejVar.zzcfb, zzaejVar.zzamj, zzaejVar.zzarf, zzaejVar.zzarg, null, Collections.emptyList(), Collections.emptyList(), zzaejVar.zzcfh, zzaejVar.zzcfi, zzaejVar.zzcdr, zzaejVar.zzcds, zzaejVar.zzbsr, zzaejVar.zzbss, zzaejVar.zzcfj, null, zzaejVar.zzcfl, zzaejVar.zzcfm, zzaejVar.zzced, zzaejVar.zzzl, 0, zzaejVar.zzcfp, Collections.emptyList(), zzaejVar.zzzm, zzaejVar.zzcfq), zzwyVar, zzajiVar.zzacv, zzajiVar.errorCode, zzajiVar.zzcoh, zzajiVar.zzcoi, null, zzajiVar.zzcoq, null);
        } catch (JSONException e) {
            zzakb.zzb("Unable to generate ad state for an interstitial ad with pooling.", e);
            return zzajiVar;
        }
    }

    private final boolean bU(boolean z) {
        return this.bBt != null && z;
    }

    private final void s(Bundle bundle) {
        zzbv.zzek().zzb(this.zzvw.zzrt, this.zzvw.zzacr.zzcw, "gmob-apps", bundle, false);
    }

    protected final boolean WY() {
        if (!(this.zzvw.zzrt instanceof Activity)) {
            return false;
        }
        Window window = ((Activity) this.zzvw.zzrt).getWindow();
        if (window == null || window.getDecorView() == null) {
            return false;
        }
        Rect rect = new Rect();
        Rect rect2 = new Rect();
        window.getDecorView().getGlobalVisibleRect(rect, null);
        window.getDecorView().getWindowVisibleDisplayFrame(rect2);
        return (rect.bottom == 0 || rect2.bottom == 0 || rect.top != rect2.top) ? false : true;
    }

    @Override // com.google.android.gms.ads.internal.zzi
    protected final zzaqw a(zzaji zzajiVar, zzx zzxVar, zzait zzaitVar) {
        zzbv.zzel();
        zzaqw zzaqwVarZza = zzarc.zza(this.zzvw.zzrt, zzasi.zzb(this.zzvw.zzacv), this.zzvw.zzacv.zzarb, false, false, this.zzvw.bCV, this.zzvw.zzacr, this.zzvr, this, this.zzwc, zzajiVar.zzcoq);
        zzaqwVarZza.zzuf().zza(this, this, null, this, this, ((Boolean) zzkb.zzik().zzd(zznk.zzaxe)).booleanValue(), this, zzxVar, this, zzaitVar);
        c(zzaqwVarZza);
        zzaqwVarZza.zzdr(zzajiVar.zzcgs.zzcdi);
        zzaqwVarZza.zza("/reward", new com.google.android.gms.ads.internal.gmsg.zzah(this));
        return zzaqwVarZza;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void setImmersiveMode(boolean z) {
        bf.hV("setImmersiveMode must be called on the main UI thread.");
        this.zzyu = z;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzks
    public final void showInterstitial() {
        Bitmap bitmapZzar;
        bf.hV("showInterstitial must be called on the main UI thread.");
        if (bU(this.zzvw.zzacw != null && this.zzvw.zzacw.zzceq)) {
            this.bBt.zzw(this.zzyu);
            return;
        }
        if (zzbv.zzfh().zzv(this.zzvw.zzrt)) {
            this.bBr = zzbv.zzfh().zzy(this.zzvw.zzrt);
            String strValueOf = String.valueOf(this.bBr);
            String strValueOf2 = String.valueOf(this.bBs);
            this.bBr = strValueOf2.length() != 0 ? strValueOf.concat(strValueOf2) : new String(strValueOf);
        }
        if (this.zzvw.zzacw == null) {
            zzakb.zzdk("The interstitial has not loaded.");
            return;
        }
        if (((Boolean) zzkb.zzik().zzd(zznk.zzazx)).booleanValue()) {
            String packageName = this.zzvw.zzrt.getApplicationContext() != null ? this.zzvw.zzrt.getApplicationContext().getPackageName() : this.zzvw.zzrt.getPackageName();
            if (!this.bBn) {
                zzakb.zzdk("It is not recommended to show an interstitial before onAdLoaded completes.");
                Bundle bundle = new Bundle();
                bundle.putString("appid", packageName);
                bundle.putString(NativeProtocol.WEB_DIALOG_ACTION, "show_interstitial_before_load_finish");
                s(bundle);
            }
            zzbv.zzek();
            if (!zzakk.zzaq(this.zzvw.zzrt)) {
                zzakb.zzdk("It is not recommended to show an interstitial when app is not in foreground.");
                Bundle bundle2 = new Bundle();
                bundle2.putString("appid", packageName);
                bundle2.putString(NativeProtocol.WEB_DIALOG_ACTION, "show_interstitial_app_not_in_foreground");
                s(bundle2);
            }
        }
        if (this.zzvw.zzfp()) {
            return;
        }
        if (this.zzvw.zzacw.zzceq && this.zzvw.zzacw.zzbtx != null) {
            try {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzayr)).booleanValue()) {
                    this.zzvw.zzacw.zzbtx.setImmersiveMode(this.zzyu);
                }
                this.zzvw.zzacw.zzbtx.showInterstitial();
                return;
            } catch (RemoteException e) {
                zzakb.zzc("Could not show interstitial.", e);
                zzdj();
                return;
            }
        }
        if (this.zzvw.zzacw.zzbyo == null) {
            zzakb.zzdk("The interstitial failed to load.");
            return;
        }
        if (this.zzvw.zzacw.zzbyo.zzuj()) {
            zzakb.zzdk("The interstitial is already showing.");
            return;
        }
        this.zzvw.zzacw.zzbyo.zzai(true);
        this.zzvw.zzj(this.zzvw.zzacw.zzbyo.getView());
        if (this.zzvw.zzacw.zzcob != null) {
            this.zzvy.zza(this.zzvw.zzacv, this.zzvw.zzacw);
        }
        if (com.google.android.gms.common.d.s.abv()) {
            final zzajh zzajhVar = this.zzvw.zzacw;
            if (zzajhVar.zzfz()) {
                new zzfp(this.zzvw.zzrt, zzajhVar.zzbyo.getView()).zza(zzajhVar.zzbyo);
            } else {
                zzajhVar.zzbyo.zzuf().zza(new zzasf(this, zzajhVar) { // from class: com.google.android.gms.ads.internal.f
                    private final zzal bBu;
                    private final zzajh zzxk;

                    {
                        this.bBu = this;
                        this.zzxk = zzajhVar;
                    }

                    @Override // com.google.android.gms.internal.ads.zzasf
                    public final void zzdb() {
                        zzal zzalVar = this.bBu;
                        zzajh zzajhVar2 = this.zzxk;
                        new zzfp(zzalVar.zzvw.zzrt, zzajhVar2.zzbyo.getView()).zza(zzajhVar2.zzbyo);
                    }
                });
            }
        }
        if (this.zzvw.zzze) {
            zzbv.zzek();
            bitmapZzar = zzakk.zzar(this.zzvw.zzrt);
        } else {
            bitmapZzar = null;
        }
        this.bBo = zzbv.zzfe().zzb(bitmapZzar);
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbbg)).booleanValue() && bitmapZzar != null) {
            new h(this, this.bBo).zzqo();
            return;
        }
        zzaq zzaqVar = new zzaq(this.zzvw.zzze, WY(), false, 0.0f, -1, this.zzyu, this.zzvw.zzacw.zzzl, this.zzvw.zzacw.zzzm);
        int requestedOrientation = this.zzvw.zzacw.zzbyo.getRequestedOrientation();
        if (requestedOrientation == -1) {
            requestedOrientation = this.zzvw.zzacw.orientation;
        }
        AdOverlayInfoParcel adOverlayInfoParcel = new AdOverlayInfoParcel(this, this, this, this.zzvw.zzacw.zzbyo, requestedOrientation, this.zzvw.zzacr, this.zzvw.zzacw.zzcev, zzaqVar);
        zzbv.zzei();
        zzl.zza(this.zzvw.zzrt, adOverlayInfoParcel, true);
    }

    @Override // com.google.android.gms.ads.internal.zzi, com.google.android.gms.ads.internal.zza
    public final void zza(zzaji zzajiVar, zznx zznxVar) {
        if (zzajiVar.errorCode != -2) {
            super.zza(zzajiVar, zznxVar);
            return;
        }
        if (bU(zzajiVar.zzcod != null)) {
            this.bBt.zzou();
            return;
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzayy)).booleanValue()) {
            super.zza(zzajiVar, zznxVar);
            return;
        }
        boolean z = zzajiVar.zzcos.zzceq ? false : true;
        if (zza(zzajiVar.zzcgs.zzccv) && z) {
            this.zzvw.zzacx = a(zzajiVar);
        }
        super.zza(this.zzvw.zzacx, zznxVar);
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzz
    public final void zza(boolean z, float f) {
        this.bBp = z;
        this.bBq = f;
    }

    @Override // com.google.android.gms.ads.internal.zzi, com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    public final boolean zza(zzajh zzajhVar, zzajh zzajhVar2) {
        if (bU(zzajhVar2.zzceq)) {
            return zzago.zza(zzajhVar, zzajhVar2);
        }
        if (!super.zza(zzajhVar, zzajhVar2)) {
            return false;
        }
        if (!this.zzvw.zzfo() && this.zzvw.bDm != null && zzajhVar2.zzcob != null) {
            this.zzvy.zza(this.zzvw.zzacv, zzajhVar2, this.zzvw.bDm);
        }
        zzb(zzajhVar2, false);
        return true;
    }

    @Override // com.google.android.gms.ads.internal.zzd
    protected final boolean zza(zzjj zzjjVar, zzajh zzajhVar, boolean z) {
        if (this.zzvw.zzfo() && zzajhVar.zzbyo != null) {
            zzbv.zzem();
            zzakq.zzi(zzajhVar.zzbyo);
        }
        return this.zzvv.zzdz();
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    public final boolean zza(zzjj zzjjVar, zznx zznxVar) {
        if (this.zzvw.zzacw != null) {
            zzakb.zzdk("An interstitial is already loading. Aborting.");
            return false;
        }
        if (this.zzyv == null && zza(zzjjVar) && zzbv.zzfh().zzv(this.zzvw.zzrt) && !TextUtils.isEmpty(this.zzvw.zzacp)) {
            this.zzyv = new zzaix(this.zzvw.zzrt, this.zzvw.zzacp);
        }
        return super.zza(zzjjVar, zznxVar);
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzai
    public final void zzb(zzaig zzaigVar) {
        if (bU(this.zzvw.zzacw != null && this.zzvw.zzacw.zzceq)) {
            zza(this.bBt.zzd(zzaigVar));
            return;
        }
        if (this.zzvw.zzacw != null) {
            if (this.zzvw.zzacw.zzcfg != null) {
                zzbv.zzek();
                zzakk.zza(this.zzvw.zzrt, this.zzvw.zzacr.zzcw, this.zzvw.zzacw.zzcfg);
            }
            if (this.zzvw.zzacw.zzcfe != null) {
                zzaigVar = this.zzvw.zzacw.zzcfe;
            }
        }
        zza(zzaigVar);
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected final void zzbn() {
        zzdj();
        super.zzbn();
    }

    @Override // com.google.android.gms.ads.internal.zzi, com.google.android.gms.ads.internal.zza
    protected final void zzbq() {
        zzaqw zzaqwVar = this.zzvw.zzacw != null ? this.zzvw.zzacw.zzbyo : null;
        zzaji zzajiVar = this.zzvw.zzacx;
        if (zzajiVar != null && zzajiVar.zzcos != null && zzajiVar.zzcos.zzcfp && zzaqwVar != null && zzbv.zzfa().zzi(this.zzvw.zzrt)) {
            this.zzwb = zzbv.zzfa().zza(new StringBuilder(23).append(this.zzvw.zzacr.zzcve).append(".").append(this.zzvw.zzacr.zzcvf).toString(), zzaqwVar.getWebView(), "", "javascript", zzbz());
            if (this.zzwb != null && zzaqwVar.getView() != null) {
                zzbv.zzfa().zza(this.zzwb, zzaqwVar.getView());
                zzbv.zzfa().zzm(this.zzwb);
            }
        }
        super.zzbq();
        this.bBn = true;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.overlay.zzn
    public final void zzcb() {
        super.zzcb();
        this.zzvy.zzh(this.zzvw.zzacw);
        if (this.zzyv != null) {
            this.zzyv.zzx(false);
        }
        zzby();
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.overlay.zzn
    public final void zzcc() {
        zzasc zzascVarZzuf;
        recordImpression();
        super.zzcc();
        if (this.zzvw.zzacw != null && this.zzvw.zzacw.zzbyo != null && (zzascVarZzuf = this.zzvw.zzacw.zzbyo.zzuf()) != null) {
            zzascVarZzuf.zzuz();
        }
        if (zzbv.zzfh().zzv(this.zzvw.zzrt) && this.zzvw.zzacw != null && this.zzvw.zzacw.zzbyo != null) {
            zzbv.zzfh().zzd(this.zzvw.zzacw.zzbyo.getContext(), this.bBr);
        }
        if (this.zzyv != null) {
            this.zzyv.zzx(true);
        }
        if (this.zzwb == null || this.zzvw.zzacw == null || this.zzvw.zzacw.zzbyo == null) {
            return;
        }
        this.zzvw.zzacw.zzbyo.zza("onSdkImpression", new HashMap());
    }

    @Override // com.google.android.gms.internal.ads.zzaam
    public final void zzcz() {
        com.google.android.gms.ads.internal.overlay.zzd zzdVarZzub = this.zzvw.zzacw.zzbyo.zzub();
        if (zzdVarZzub != null) {
            zzdVarZzub.close();
        }
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzz
    public final void zzd(boolean z) {
        this.zzvw.zzze = z;
    }

    public final void zzdj() {
        zzbv.zzfe().zzb(Integer.valueOf(this.bBo));
        if (this.zzvw.zzfo()) {
            this.zzvw.zzfm();
            this.zzvw.zzacw = null;
            this.zzvw.zzze = false;
            this.bBn = false;
        }
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzai
    public final void zzdk() {
        if (bU(this.zzvw.zzacw != null && this.zzvw.zzacw.zzceq)) {
            this.bBt.zzov();
            zzbt();
            return;
        }
        if (this.zzvw.zzacw != null && this.zzvw.zzacw.zzcog != null) {
            zzbv.zzek();
            zzakk.zza(this.zzvw.zzrt, this.zzvw.zzacr.zzcw, this.zzvw.zzacw.zzcog);
        }
        zzbt();
    }

    @Override // com.google.android.gms.ads.internal.gmsg.zzai
    public final void zzdl() {
        if (bU(this.zzvw.zzacw != null && this.zzvw.zzacw.zzceq)) {
            this.bBt.zzow();
        }
        zzbu();
    }
}
