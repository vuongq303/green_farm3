package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzabl;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzod;
import com.google.android.gms.internal.ads.zzoo;
import com.google.android.gms.internal.ads.zzoq;
import com.google.android.gms.internal.ads.zzov;
import com.google.android.gms.internal.ads.zzox;
import com.google.android.gms.internal.ads.zzoy;
import com.google.android.gms.internal.ads.zzoz;
import com.google.android.gms.internal.ads.zzpa;
import com.google.android.gms.internal.ads.zzqs;
import com.google.android.gms.internal.ads.zzrc;
import com.google.android.gms.internal.ads.zzwy;
import com.google.android.gms.internal.ads.zzxn;
import com.google.android.gms.internal.ads.zzxz;
import com.google.android.gms.internal.ads.zzyc;
import com.google.android.gms.internal.ads.zzyf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzq extends zzd implements zzpa {
    private zzajh bDy;
    private boolean bDz;
    private boolean zzvm;

    public zzq(Context context, zzw zzwVar, zzjn zzjnVar, String str, zzxn zzxnVar, zzang zzangVar) {
        super(context, zzjnVar, str, zzxnVar, zzangVar, zzwVar);
        this.bDz = false;
    }

    private final zzwy Xc() {
        if (this.zzvw.zzacw == null || !this.zzvw.zzacw.zzceq) {
            return null;
        }
        return this.zzvw.zzacw.zzcod;
    }

    private static zzajh a(zzaji zzajiVar, int i) {
        return new zzajh(zzajiVar.zzcgs.zzccv, null, zzajiVar.zzcos.zzbsn, i, zzajiVar.zzcos.zzbso, zzajiVar.zzcos.zzces, zzajiVar.zzcos.orientation, zzajiVar.zzcos.zzbsu, zzajiVar.zzcgs.zzccy, zzajiVar.zzcos.zzceq, null, null, null, zzajiVar.zzcod, null, zzajiVar.zzcos.zzcer, zzajiVar.zzacv, zzajiVar.zzcos.zzcep, zzajiVar.zzcoh, zzajiVar.zzcoi, zzajiVar.zzcos.zzcev, zzajiVar.zzcob, null, zzajiVar.zzcos.zzcfe, zzajiVar.zzcos.zzcff, zzajiVar.zzcos.zzcff, zzajiVar.zzcos.zzcfh, zzajiVar.zzcos.zzcfi, null, zzajiVar.zzcos.zzbsr, zzajiVar.zzcos.zzcfl, zzajiVar.zzcoq, zzajiVar.zzcos.zzzl, zzajiVar.zzcor, zzajiVar.zzcos.zzcfp, zzajiVar.zzcos.zzbsp, zzajiVar.zzcos.zzzm, zzajiVar.zzcos.zzcfq);
    }

    private final void a(zzov zzovVar) {
        zzakk.zzcrm.post(new ar(this, zzovVar));
    }

    private final boolean a(zzajh zzajhVar, zzajh zzajhVar2) {
        zzd(null);
        if (!this.zzvw.zzfo()) {
            zzakb.zzdk("Native ad does not have custom rendering mode.");
            zzi(0);
            return false;
        }
        try {
            zzyf zzyfVarZzmu = zzajhVar2.zzbtx != null ? zzajhVar2.zzbtx.zzmu() : null;
            zzxz zzxzVarZzmo = zzajhVar2.zzbtx != null ? zzajhVar2.zzbtx.zzmo() : null;
            zzyc zzycVarZzmp = zzajhVar2.zzbtx != null ? zzajhVar2.zzbtx.zzmp() : null;
            zzqs zzqsVarZzmt = zzajhVar2.zzbtx != null ? zzajhVar2.zzbtx.zzmt() : null;
            String strZzc = zzc(zzajhVar2);
            if (zzyfVarZzmu != null && this.zzvw.bDe != null) {
                zzov zzovVar = new zzov(zzyfVarZzmu.getHeadline(), zzyfVarZzmu.getImages(), zzyfVarZzmu.getBody(), zzyfVarZzmu.zzjz() != null ? zzyfVarZzmu.zzjz() : null, zzyfVarZzmu.getCallToAction(), zzyfVarZzmu.getAdvertiser(), zzyfVarZzmu.getStarRating(), zzyfVarZzmu.getStore(), zzyfVarZzmu.getPrice(), null, zzyfVarZzmu.getVideoController(), zzyfVarZzmu.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzyfVarZzmu.zzmw()) : null, zzyfVarZzmu.zzke(), strZzc, zzyfVarZzmu.getExtras());
                zzovVar.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzyfVarZzmu, zzovVar));
                a(zzovVar);
            } else if (zzxzVarZzmo != null && this.zzvw.bDe != null) {
                zzov zzovVar2 = new zzov(zzxzVarZzmo.getHeadline(), zzxzVarZzmo.getImages(), zzxzVarZzmo.getBody(), zzxzVarZzmo.zzjz() != null ? zzxzVarZzmo.zzjz() : null, zzxzVarZzmo.getCallToAction(), null, zzxzVarZzmo.getStarRating(), zzxzVarZzmo.getStore(), zzxzVarZzmo.getPrice(), null, zzxzVarZzmo.getVideoController(), zzxzVarZzmo.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzxzVarZzmo.zzmw()) : null, zzxzVarZzmo.zzke(), strZzc, zzxzVarZzmo.getExtras());
                zzovVar2.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzxzVarZzmo, zzovVar2));
                a(zzovVar2);
            } else if (zzxzVarZzmo != null && this.zzvw.bDc != null) {
                zzoo zzooVar = new zzoo(zzxzVarZzmo.getHeadline(), zzxzVarZzmo.getImages(), zzxzVarZzmo.getBody(), zzxzVarZzmo.zzjz() != null ? zzxzVarZzmo.zzjz() : null, zzxzVarZzmo.getCallToAction(), zzxzVarZzmo.getStarRating(), zzxzVarZzmo.getStore(), zzxzVarZzmo.getPrice(), null, zzxzVarZzmo.getExtras(), zzxzVarZzmo.getVideoController(), zzxzVarZzmo.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzxzVarZzmo.zzmw()) : null, zzxzVarZzmo.zzke(), strZzc);
                zzooVar.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzxzVarZzmo, zzooVar));
                zzakk.zzcrm.post(new as(this, zzooVar));
            } else if (zzycVarZzmp != null && this.zzvw.bDe != null) {
                zzov zzovVar3 = new zzov(zzycVarZzmp.getHeadline(), zzycVarZzmp.getImages(), zzycVarZzmp.getBody(), zzycVarZzmp.zzkg() != null ? zzycVarZzmp.zzkg() : null, zzycVarZzmp.getCallToAction(), zzycVarZzmp.getAdvertiser(), -1.0d, null, null, null, zzycVarZzmp.getVideoController(), zzycVarZzmp.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzycVarZzmp.zzmw()) : null, zzycVarZzmp.zzke(), strZzc, zzycVarZzmp.getExtras());
                zzovVar3.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzycVarZzmp, zzovVar3));
                a(zzovVar3);
            } else if (zzycVarZzmp != null && this.zzvw.bDd != null) {
                zzoq zzoqVar = new zzoq(zzycVarZzmp.getHeadline(), zzycVarZzmp.getImages(), zzycVarZzmp.getBody(), zzycVarZzmp.zzkg() != null ? zzycVarZzmp.zzkg() : null, zzycVarZzmp.getCallToAction(), zzycVarZzmp.getAdvertiser(), null, zzycVarZzmp.getExtras(), zzycVarZzmp.getVideoController(), zzycVarZzmp.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzycVarZzmp.zzmw()) : null, zzycVarZzmp.zzke(), strZzc);
                zzoqVar.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzycVarZzmp, zzoqVar));
                zzakk.zzcrm.post(new at(this, zzoqVar));
            } else {
                if (zzqsVarZzmt == null || this.zzvw.bDg == null || this.zzvw.bDg.get(zzqsVarZzmt.getCustomTemplateId()) == null) {
                    zzakb.zzdk("No matching mapper/listener for retrieved native ad template.");
                    zzi(0);
                    return false;
                }
                zzakk.zzcrm.post(new au(this, zzqsVarZzmt));
            }
            return super.zza(zzajhVar, zzajhVar2);
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
            zzi(0);
            return false;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference fix 'apply assigned field type' failed
    java.lang.UnsupportedOperationException: ArgType.getObject(), call class: class jadx.core.dex.instructions.args.ArgType$UnknownArg
    	at jadx.core.dex.instructions.args.ArgType.getObject(ArgType.java:596)
    	at jadx.core.dex.attributes.nodes.ClassTypeVarsAttr.getTypeVarsMapFor(ClassTypeVarsAttr.java:35)
    	at jadx.core.dex.nodes.utils.TypeUtils.replaceClassGenerics(TypeUtils.java:177)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.insertExplicitUseCast(FixTypesVisitor.java:397)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.tryFieldTypeWithNewCasts(FixTypesVisitor.java:359)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.applyFieldType(FixTypesVisitor.java:309)
    	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:94)
     */
    private final boolean b(zzajh zzajhVar, zzajh zzajhVar2) {
        View viewZze = zzas.zze(zzajhVar2);
        if (viewZze == null) {
            return false;
        }
        View nextView = this.zzvw.bCW.getNextView();
        if (nextView != 0) {
            if (nextView instanceof zzaqw) {
                ((zzaqw) nextView).destroy();
            }
            this.zzvw.bCW.removeView(nextView);
        }
        if (!zzas.zzf(zzajhVar2)) {
            try {
                zzg(viewZze);
            } catch (Throwable th) {
                zzbv.zzeo().zza(th, "AdLoaderManager.swapBannerViews");
                zzakb.zzc("Could not add mediation view to view hierarchy.", th);
                return false;
            }
        }
        if (this.zzvw.bCW.getChildCount() > 1) {
            this.zzvw.bCW.showNext();
        }
        if (zzajhVar != null) {
            View nextView2 = this.zzvw.bCW.getNextView();
            if (nextView2 != null) {
                this.zzvw.bCW.removeView(nextView2);
            }
            this.zzvw.zzfn();
        }
        this.zzvw.bCW.setMinimumWidth(zzbk().widthPixels);
        this.zzvw.bCW.setMinimumHeight(zzbk().heightPixels);
        this.zzvw.bCW.requestLayout();
        this.zzvw.bCW.setVisibility(0);
        return true;
    }

    protected final void a(com.google.android.gms.c.a aVar) {
        Object objB = aVar != null ? com.google.android.gms.c.d.b(aVar) : null;
        if (objB instanceof zzoz) {
            ((zzoz) objB).zzkl();
        }
        super.zzb(this.zzvw.zzacw, false);
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final zzlo getVideoController() {
        return null;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void pause() {
        if (!this.bDz) {
            throw new IllegalStateException("Native Ad does not support pause().");
        }
        super.pause();
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void resume() {
        if (!this.bDz) {
            throw new IllegalStateException("Native Ad does not support resume().");
        }
        super.resume();
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void setManualImpressionsEnabled(boolean z) {
        bf.hV("setManualImpressionsEnabled must be called from the main thread.");
        this.zzvm = z;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzks
    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is not supported by AdLoaderManager.");
    }

    @Override // com.google.android.gms.ads.internal.zza
    public final void zza(zzaji zzajiVar, zznx zznxVar) {
        this.bDy = null;
        if (zzajiVar.errorCode != -2) {
            this.bDy = a(zzajiVar, zzajiVar.errorCode);
        } else if (!zzajiVar.zzcos.zzceq) {
            zzakb.zzdk("partialAdState is not mediation");
            this.bDy = a(zzajiVar, 0);
        }
        if (this.bDy != null) {
            zzakk.zzcrm.post(new aq(this));
            return;
        }
        if (zzajiVar.zzacv != null) {
            this.zzvw.zzacv = zzajiVar.zzacv;
        }
        this.zzvw.zzadv = 0;
        zzbw zzbwVar = this.zzvw;
        zzbv.zzej();
        zzbwVar.zzacu = zzabl.zza(this.zzvw.zzrt, this, zzajiVar, this.zzvw.bCV, null, this.zzwh, this, zznxVar);
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void zza(zzod zzodVar) {
        throw new IllegalStateException("CustomRendering is not supported by AdLoaderManager.");
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zza(zzox zzoxVar) {
        zzane.zzd("#005 Unexpected call to an abstract (unimplemented) method.", null);
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zza(zzoz zzozVar) {
        zzane.zzd("#005 Unexpected call to an abstract (unimplemented) method.", null);
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    protected final boolean zza(zzajh zzajhVar, zzajh zzajhVar2) {
        boolean z;
        if (!this.zzvw.zzfo()) {
            throw new IllegalStateException("AdLoader API does not support custom rendering.");
        }
        if (!zzajhVar2.zzceq) {
            zzi(0);
            zzakb.zzdk("newState is not mediation.");
            return false;
        }
        if (zzajhVar2.zzbtw != null && zzajhVar2.zzbtw.zzmf()) {
            if (this.zzvw.zzfo() && this.zzvw.bCW != null) {
                this.zzvw.bCW.zzfr().zzdb(zzajhVar2.zzcev);
            }
            if (!super.zza(zzajhVar, zzajhVar2)) {
                z = false;
            } else if (!this.zzvw.zzfo() || b(zzajhVar, zzajhVar2)) {
                if (!this.zzvw.zzfp()) {
                    super.zza(zzajhVar2, false);
                }
                z = true;
            } else {
                zzi(0);
                z = false;
            }
            if (!z) {
                return false;
            }
            this.bDz = true;
        } else {
            if (zzajhVar2.zzbtw == null || !zzajhVar2.zzbtw.zzmg()) {
                zzi(0);
                zzakb.zzdk("Response is neither banner nor native.");
                return false;
            }
            if (!a(zzajhVar, zzajhVar2)) {
                return false;
            }
        }
        zze(new ArrayList(Arrays.asList(2)));
        return true;
    }

    @Override // com.google.android.gms.ads.internal.zzd
    protected final boolean zza(zzjj zzjjVar, zzajh zzajhVar, boolean z) {
        return false;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final boolean zzb(zzjj zzjjVar) {
        if (this.zzvw.zzadn != null && this.zzvw.zzadn.size() == 1 && this.zzvw.zzadn.get(0).intValue() == 2) {
            zzakb.e("Requesting only banner Ad from AdLoader or calling loadAd on returned banner is not yet supported");
            zzi(0);
            return false;
        }
        if (this.zzvw.bDi == null) {
            return super.zzb(zzjjVar);
        }
        if (zzjjVar.zzaqb != this.zzvm) {
            zzjjVar = new zzjj(zzjjVar.versionCode, zzjjVar.zzapw, zzjjVar.extras, zzjjVar.zzapx, zzjjVar.zzapy, zzjjVar.zzapz, zzjjVar.zzaqa, zzjjVar.zzaqb || this.zzvm, zzjjVar.zzaqc, zzjjVar.zzaqd, zzjjVar.zzaqe, zzjjVar.zzaqf, zzjjVar.zzaqg, zzjjVar.zzaqh, zzjjVar.zzaqi, zzjjVar.zzaqj, zzjjVar.zzaqk, zzjjVar.zzaql);
        }
        return super.zzb(zzjjVar);
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected final void zzbq() {
        super.zzbq();
        zzajh zzajhVar = this.zzvw.zzacw;
        if (zzajhVar == null || zzajhVar.zzbtw == null || !zzajhVar.zzbtw.zzmf() || this.zzvw.bDi == null) {
            return;
        }
        try {
            this.zzvw.bDi.zza(this, com.google.android.gms.c.d.F(this.zzvw.zzrt));
            super.zzb(this.zzvw.zzacw, false);
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzwz
    public final void zzce() {
        if (this.zzvw.zzacw == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzvw.zzacw.zzbty) || this.zzvw.zzacw.zzbtw == null || !this.zzvw.zzacw.zzbtw.zzmg()) {
            super.zzce();
        } else {
            zzbs();
        }
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzwz
    public final void zzcj() {
        if (this.zzvw.zzacw == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzvw.zzacw.zzbty) || this.zzvw.zzacw.zzbtw == null || !this.zzvw.zzacw.zzbtw.zzmg()) {
            super.zzcj();
        } else {
            zzbr();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzcr() {
        zzane.zzd("#005 Unexpected call to an abstract (unimplemented) method.", null);
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzcs() {
        zzane.zzd("#005 Unexpected call to an abstract (unimplemented) method.", null);
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzct() {
        zzane.zzd("#005 Unexpected call to an abstract (unimplemented) method.", null);
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final boolean zzcu() {
        if (Xc() != null) {
            return Xc().zzbta;
        }
        return false;
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final boolean zzcv() {
        if (Xc() != null) {
            return Xc().zzbtb;
        }
        return false;
    }

    public final void zzd(List<String> list) {
        bf.hV("setNativeTemplates must be called on the main UI thread.");
        this.zzvw.zzads = list;
    }

    public final void zze(List<Integer> list) {
        bf.hV("setAllowedAdTypes must be called on the main UI thread.");
        this.zzvw.zzadn = list;
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzi(View view) {
        zzane.zzd("#005 Unexpected call to an abstract (unimplemented) method.", null);
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final zzrc zzr(String str) {
        bf.hV("getOnCustomClickListener must be called on the main UI thread.");
        return this.zzvw.bDf.get(str);
    }
}
