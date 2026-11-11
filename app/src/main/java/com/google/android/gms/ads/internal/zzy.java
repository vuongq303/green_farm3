package com.google.android.gms.ads.internal;

import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewTreeObserver;
import android.webkit.WebView;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzait;
import com.google.android.gms.internal.ads.zzaix;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzaor;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarl;
import com.google.android.gms.internal.ads.zzasc;
import com.google.android.gms.internal.ads.zzasf;
import com.google.android.gms.internal.ads.zzasg;
import com.google.android.gms.internal.ads.zzasi;
import com.google.android.gms.internal.ads.zzfp;
import com.google.android.gms.internal.ads.zzhu;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzxn;
import com.google.android.gms.internal.ads.zzxz;
import com.google.android.gms.internal.ads.zzyc;
import java.lang.ref.WeakReference;
import java.util.List;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzy extends zzi implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    private boolean bDC;
    private WeakReference<Object> bDD;
    private boolean zzvm;

    public zzy(Context context, zzjn zzjnVar, String str, zzxn zzxnVar, zzang zzangVar, zzw zzwVar) {
        super(context, zzjnVar, str, zzxnVar, zzangVar, zzwVar);
        this.bDD = new WeakReference<>(null);
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
    private final boolean c(zzajh zzajhVar, zzajh zzajhVar2) {
        if (zzajhVar2.zzceq) {
            View viewZze = zzas.zze(zzajhVar2);
            if (viewZze == null) {
                zzakb.zzdk("Could not get mediation view");
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
                    if (zzbv.zzfh().zzt(this.zzvw.zzrt)) {
                        new zzfp(this.zzvw.zzrt, viewZze).zza(new zzaix(this.zzvw.zzrt, this.zzvw.zzacp));
                    }
                    if (zzajhVar2.zzcof != null) {
                        this.zzvw.bCW.setMinimumWidth(zzajhVar2.zzcof.widthPixels);
                        this.zzvw.bCW.setMinimumHeight(zzajhVar2.zzcof.heightPixels);
                    }
                    zzg(viewZze);
                } catch (Exception e) {
                    zzbv.zzeo().zza(e, "BannerAdManager.swapViews");
                    zzakb.zzc("Could not add mediation view to view hierarchy.", e);
                    return false;
                }
            }
        } else if (zzajhVar2.zzcof != null && zzajhVar2.zzbyo != null) {
            zzajhVar2.zzbyo.zza(zzasi.zzb(zzajhVar2.zzcof));
            this.zzvw.bCW.removeAllViews();
            this.zzvw.bCW.setMinimumWidth(zzajhVar2.zzcof.widthPixels);
            this.zzvw.bCW.setMinimumHeight(zzajhVar2.zzcof.heightPixels);
            zzg(zzajhVar2.zzbyo.getView());
        }
        if (this.zzvw.bCW.getChildCount() > 1) {
            this.zzvw.bCW.showNext();
        }
        if (zzajhVar != null) {
            View nextView2 = this.zzvw.bCW.getNextView();
            if (nextView2 instanceof zzaqw) {
                ((zzaqw) nextView2).destroy();
            } else if (nextView2 != 0) {
                this.zzvw.bCW.removeView(nextView2);
            }
            this.zzvw.zzfn();
        }
        this.zzvw.bCW.setVisibility(0);
        return true;
    }

    private final void e(zzaqw zzaqwVar) {
        WebView webView;
        View view;
        if (Xb() && (webView = zzaqwVar.getWebView()) != null && (view = zzaqwVar.getView()) != null && zzbv.zzfa().zzi(this.zzvw.zzrt)) {
            this.zzwb = zzbv.zzfa().zza(new StringBuilder(23).append(this.zzvw.zzacr.zzcve).append(".").append(this.zzvw.zzacr.zzcvf).toString(), webView, "", "javascript", zzbz());
            if (this.zzwb != null) {
                zzbv.zzfa().zza(this.zzwb, view);
                zzbv.zzfa().zzm(this.zzwb);
                this.bDC = true;
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.zzi
    protected final zzaqw a(zzaji zzajiVar, zzx zzxVar, zzait zzaitVar) {
        AdSize adSizeZzhy;
        zzjn zzjnVar;
        if (this.zzvw.zzacv.zzard == null && this.zzvw.zzacv.zzarf) {
            zzbw zzbwVar = this.zzvw;
            if (zzajiVar.zzcos.zzarf) {
                zzjnVar = this.zzvw.zzacv;
            } else {
                String str = zzajiVar.zzcos.zzcet;
                if (str != null) {
                    String[] strArrSplit = str.split("[xX]");
                    strArrSplit[0] = strArrSplit[0].trim();
                    strArrSplit[1] = strArrSplit[1].trim();
                    adSizeZzhy = new AdSize(Integer.parseInt(strArrSplit[0]), Integer.parseInt(strArrSplit[1]));
                } else {
                    adSizeZzhy = this.zzvw.zzacv.zzhy();
                }
                zzjnVar = new zzjn(this.zzvw.zzrt, adSizeZzhy);
            }
            zzbwVar.zzacv = zzjnVar;
        }
        return super.a(zzajiVar, zzxVar, zzaitVar);
    }

    final void a(zzajh zzajhVar) {
        if (zzajhVar == null || zzajhVar.zzcoc || this.zzvw.bCW == null || !zzbv.zzek().zza(this.zzvw.bCW, this.zzvw.zzrt) || !this.zzvw.bCW.getGlobalVisibleRect(new Rect(), null)) {
            return;
        }
        if (zzajhVar != null && zzajhVar.zzbyo != null && zzajhVar.zzbyo.zzuf() != null) {
            zzajhVar.zzbyo.zzuf().zza((zzasg) null);
        }
        zza(zzajhVar, false);
        zzajhVar.zzcoc = true;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final zzlo getVideoController() {
        bf.hV("getVideoController must be called from the main thread.");
        if (this.zzvw.zzacw == null || this.zzvw.zzacw.zzbyo == null) {
            return null;
        }
        return this.zzvw.zzacw.zzbyo.zztm();
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        a(this.zzvw.zzacw);
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        a(this.zzvw.zzacw);
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void setManualImpressionsEnabled(boolean z) {
        bf.hV("setManualImpressionsEnabled must be called from the main thread.");
        this.zzvm = z;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzks
    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by BannerAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zzd
    protected final void zza(zzajh zzajhVar, boolean z) {
        if (Xb()) {
            zzaqw zzaqwVar = zzajhVar != null ? zzajhVar.zzbyo : null;
            if (zzaqwVar != null) {
                if (!this.bDC) {
                    e(zzaqwVar);
                }
                if (this.zzwb != null) {
                    zzaqwVar.zza("onSdkImpression", new android.support.v4.e.a());
                }
            }
        }
        super.zza(zzajhVar, z);
        if (zzas.zzf(zzajhVar)) {
            zzac zzacVar = new zzac(this);
            if (zzajhVar == null || !zzas.zzf(zzajhVar)) {
                return;
            }
            zzaqw zzaqwVar2 = zzajhVar.zzbyo;
            View view = zzaqwVar2 != null ? zzaqwVar2.getView() : null;
            if (view == null) {
                zzakb.zzdk("AdWebView is null");
                return;
            }
            try {
                List<String> list = zzajhVar.zzbtw != null ? zzajhVar.zzbtw.zzbsi : null;
                if (list == null || list.isEmpty()) {
                    zzakb.zzdk("No template ids present in mediation response");
                    return;
                }
                zzxz zzxzVarZzmo = zzajhVar.zzbtx != null ? zzajhVar.zzbtx.zzmo() : null;
                zzyc zzycVarZzmp = zzajhVar.zzbtx != null ? zzajhVar.zzbtx.zzmp() : null;
                if (list.contains("2") && zzxzVarZzmo != null) {
                    zzxzVarZzmo.zzk(com.google.android.gms.c.d.F(view));
                    if (!zzxzVarZzmo.getOverrideImpressionRecording()) {
                        zzxzVarZzmo.recordImpression();
                    }
                    zzaqwVar2.zza("/nativeExpressViewClicked", zzas.a(zzxzVarZzmo, null, zzacVar));
                    return;
                }
                if (!list.contains(AppEventsConstants.EVENT_PARAM_VALUE_YES) || zzycVarZzmp == null) {
                    zzakb.zzdk("No matching template id and mapper");
                    return;
                }
                zzycVarZzmp.zzk(com.google.android.gms.c.d.F(view));
                if (!zzycVarZzmp.getOverrideImpressionRecording()) {
                    zzycVarZzmp.recordImpression();
                }
                zzaqwVar2.zza("/nativeExpressViewClicked", zzas.a(null, zzycVarZzmp, zzacVar));
            } catch (RemoteException e) {
                zzakb.zzc("Error occurred while recording impression and registering for clicks", e);
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:60:0x0119  */
    /* JADX WARN: Code duplicated, block: B:67:0x0149  */
    @Override // com.google.android.gms.ads.internal.zzi, com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    public final boolean zza(zzajh zzajhVar, final zzajh zzajhVar2) {
        View view;
        if (!super.zza(zzajhVar, zzajhVar2)) {
            return false;
        }
        if (this.zzvw.zzfo() && !c(zzajhVar, zzajhVar2)) {
            if (zzajhVar2.zzcoq != null) {
                zzajhVar2.zzcoq.zza(zzhu.zza.zzb.AD_FAILED_TO_LOAD);
            }
            zzi(0);
            return false;
        }
        zzb(zzajhVar2, false);
        if (zzajhVar2.zzcfh) {
            a(zzajhVar2);
            zzbv.zzfg();
            zzaor.zza((View) this.zzvw.bCW, (ViewTreeObserver.OnGlobalLayoutListener) this);
            zzbv.zzfg();
            zzaor.zza((View) this.zzvw.bCW, (ViewTreeObserver.OnScrollChangedListener) this);
            if (!zzajhVar2.zzcoc) {
                final b bVar = new b(this);
                zzasc zzascVarZzuf = zzajhVar2.zzbyo != null ? zzajhVar2.zzbyo.zzuf() : null;
                if (zzascVarZzuf != null) {
                    zzascVarZzuf.zza(new zzasg(zzajhVar2, bVar) { // from class: com.google.android.gms.ads.internal.av
                        private final zzajh bDE;
                        private final Runnable zzxi;

                        {
                            this.bDE = zzajhVar2;
                            this.zzxi = bVar;
                        }

                        @Override // com.google.android.gms.internal.ads.zzasg
                        public final void zzda() {
                            zzajh zzajhVar3 = this.bDE;
                            Runnable runnable = this.zzxi;
                            if (zzajhVar3.zzcoc) {
                                return;
                            }
                            zzbv.zzek();
                            zzakk.zzd(runnable);
                        }
                    });
                }
            }
        } else if (this.zzvw.zzfp()) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbo)).booleanValue()) {
                zza(zzajhVar2, false);
            }
        } else {
            zza(zzajhVar2, false);
        }
        if (zzajhVar2.zzbyo != null) {
            zzarl zzarlVarZztm = zzajhVar2.zzbyo.zztm();
            zzasc zzascVarZzuf2 = zzajhVar2.zzbyo.zzuf();
            if (zzascVarZzuf2 != null) {
                zzascVarZzuf2.zzuz();
            }
            if (this.zzvw.bDh != null && zzarlVarZztm != null) {
                zzarlVarZztm.zzb(this.zzvw.bDh);
            }
        }
        if (com.google.android.gms.common.d.s.abv()) {
            if (this.zzvw.zzfo()) {
                if (zzajhVar2.zzbyo != null) {
                    if (zzajhVar2.zzcob != null) {
                        this.zzvy.zza(this.zzvw.zzacv, zzajhVar2);
                    }
                    view = zzajhVar2.zzbyo.getView();
                    final zzfp zzfpVar = new zzfp(this.zzvw.zzrt, view);
                    if (zzbv.zzfh().zzt(this.zzvw.zzrt) && zza(zzajhVar2.zzccv) && !TextUtils.isEmpty(this.zzvw.zzacp)) {
                        zzfpVar.zza(new zzaix(this.zzvw.zzrt, this.zzvw.zzacp));
                    }
                    if (zzajhVar2.zzfz()) {
                        zzfpVar.zza(zzajhVar2.zzbyo);
                    } else {
                        zzajhVar2.zzbyo.zzuf().zza(new zzasf(zzfpVar, zzajhVar2) { // from class: com.google.android.gms.ads.internal.a
                            private final zzfp bAQ;
                            private final zzajh zzxk;

                            {
                                this.bAQ = zzfpVar;
                                this.zzxk = zzajhVar2;
                            }

                            @Override // com.google.android.gms.internal.ads.zzasf
                            public final void zzdb() {
                                this.bAQ.zza(this.zzxk.zzbyo);
                            }
                        });
                    }
                } else {
                    view = null;
                }
            } else if (this.zzvw.bDm == null || zzajhVar2.zzcob == null) {
                view = null;
            } else {
                this.zzvy.zza(this.zzvw.zzacv, zzajhVar2, this.zzvw.bDm);
                view = this.zzvw.bDm;
            }
            if (!zzajhVar2.zzceq) {
                this.zzvw.zzj(view);
            }
        }
        return true;
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final boolean zzb(zzjj zzjjVar) {
        if (zzjjVar.zzaqb != this.zzvm) {
            zzjjVar = new zzjj(zzjjVar.versionCode, zzjjVar.zzapw, zzjjVar.extras, zzjjVar.zzapx, zzjjVar.zzapy, zzjjVar.zzapz, zzjjVar.zzaqa, zzjjVar.zzaqb || this.zzvm, zzjjVar.zzaqc, zzjjVar.zzaqd, zzjjVar.zzaqe, zzjjVar.zzaqf, zzjjVar.zzaqg, zzjjVar.zzaqh, zzjjVar.zzaqi, zzjjVar.zzaqj, zzjjVar.zzaqk, zzjjVar.zzaql);
        }
        return super.zzb(zzjjVar);
    }

    @Override // com.google.android.gms.ads.internal.zzi, com.google.android.gms.ads.internal.zza
    protected final void zzbq() {
        zzaqw zzaqwVar = this.zzvw.zzacw != null ? this.zzvw.zzacw.zzbyo : null;
        if (!this.bDC && zzaqwVar != null) {
            e(zzaqwVar);
        }
        super.zzbq();
    }

    @Override // com.google.android.gms.ads.internal.zzd
    protected final boolean zzca() {
        boolean z = true;
        zzbv.zzek();
        if (!zzakk.zzl(this.zzvw.zzrt, "android.permission.INTERNET")) {
            zzkb.zzif().zza(this.zzvw.bCW, this.zzvw.zzacv, "Missing internet permission in AndroidManifest.xml.", "Missing internet permission in AndroidManifest.xml. You must have the following declaration: <uses-permission android:name=\"android.permission.INTERNET\" />");
            z = false;
        }
        zzbv.zzek();
        if (!zzakk.zzaj(this.zzvw.zzrt)) {
            zzkb.zzif().zza(this.zzvw.bCW, this.zzvw.zzacv, "Missing AdActivity with android:configChanges in AndroidManifest.xml.", "Missing AdActivity with android:configChanges in AndroidManifest.xml. You must have the following declaration within the <application> element: <activity android:name=\"com.google.android.gms.ads.AdActivity\" android:configChanges=\"keyboard|keyboardHidden|orientation|screenLayout|uiMode|screenSize|smallestScreenSize\" />");
            z = false;
        }
        if (!z && this.zzvw.bCW != null) {
            this.zzvw.bCW.setVisibility(0);
        }
        return z;
    }

    @Override // com.google.android.gms.internal.ads.zzaam
    public final void zzcz() {
        this.zzvv.zzdy();
    }
}
