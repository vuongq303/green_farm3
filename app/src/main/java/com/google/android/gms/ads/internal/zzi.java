package com.google.android.gms.ads.internal;

import android.content.Context;
import android.view.View;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzaam;
import com.google.android.gms.internal.ads.zzabl;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzait;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarc;
import com.google.android.gms.internal.ads.zzasi;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzod;
import com.google.android.gms.internal.ads.zzxn;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public abstract class zzi extends zzd implements zzaf, zzaam {
    private boolean bBM;

    public zzi(Context context, zzjn zzjnVar, String str, zzxn zzxnVar, zzang zzangVar, zzw zzwVar) {
        super(context, zzjnVar, str, zzxnVar, zzangVar, zzwVar);
    }

    protected final boolean Xb() {
        return (this.zzvw.zzacx == null || this.zzvw.zzacx.zzcos == null || !this.zzvw.zzacx.zzcos.zzcfp) ? false : true;
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
    protected zzaqw a(zzaji zzajiVar, zzx zzxVar, zzait zzaitVar) {
        View nextView = this.zzvw.bCW.getNextView();
        if (nextView instanceof zzaqw) {
            ((zzaqw) nextView).destroy();
        }
        if (nextView != 0) {
            this.zzvw.bCW.removeView(nextView);
        }
        zzbv.zzel();
        zzaqw zzaqwVarZza = zzarc.zza(this.zzvw.zzrt, zzasi.zzb(this.zzvw.zzacv), this.zzvw.zzacv.zzarb, false, false, this.zzvw.bCV, this.zzvw.zzacr, this.zzvr, this, this.zzwc, zzajiVar.zzcoq);
        if (this.zzvw.zzacv.zzard == null) {
            zzg(zzaqwVarZza.getView());
        }
        zzaqwVarZza.zzuf().zza(this, this, this, this, this, false, null, zzxVar, this, zzaitVar);
        c(zzaqwVarZza);
        zzaqwVarZza.zzdr(zzajiVar.zzcgs.zzcdi);
        return zzaqwVarZza;
    }

    protected final void c(zzaqw zzaqwVar) {
        zzaqwVar.zza("/trackActiveViewUnit", new ak(this));
    }

    final void d(zzaqw zzaqwVar) {
        if (this.zzvw.zzacw != null) {
            this.zzvy.zza(this.zzvw.zzacv, this.zzvw.zzacw, zzaqwVar.getView(), zzaqwVar);
            this.bBM = false;
        } else {
            this.bBM = true;
            zzakb.zzdk("Request to enable ActiveView before adState is available.");
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaam
    public final void zza(int i, int i2, int i3, int i4) {
        zzbp();
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected void zza(zzaji zzajiVar, zznx zznxVar) {
        if (zzajiVar.errorCode != -2) {
            zzakk.zzcrm.post(new al(this, zzajiVar));
            return;
        }
        if (zzajiVar.zzacv != null) {
            this.zzvw.zzacv = zzajiVar.zzacv;
        }
        if (!zzajiVar.zzcos.zzceq || zzajiVar.zzcos.zzarg) {
            zzakk.zzcrm.post(new am(this, zzajiVar, this.zzwc.zzxa.zza(this.zzvw.zzrt, this.zzvw.zzacr, zzajiVar.zzcos), zznxVar));
        } else {
            this.zzvw.zzadv = 0;
            zzbw zzbwVar = this.zzvw;
            zzbv.zzej();
            zzbwVar.zzacu = zzabl.zza(this.zzvw.zzrt, this, zzajiVar, this.zzvw.bCV, null, this.zzwh, this, zznxVar);
        }
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void zza(zzod zzodVar) {
        bf.hV("setOnCustomRenderedAdLoadedListener must be called on the main UI thread.");
        this.zzvw.bDj = zzodVar;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    protected boolean zza(zzajh zzajhVar, zzajh zzajhVar2) {
        if (this.zzvw.zzfo() && this.zzvw.bCW != null) {
            this.zzvw.bCW.zzfr().zzdb(zzajhVar2.zzcev);
        }
        try {
            if (zzajhVar2.zzbyo != null && !zzajhVar2.zzceq && zzajhVar2.zzcor) {
                if (((Boolean) zzkb.zzik().zzd(zznk.zzbfq)).booleanValue() && !zzajhVar2.zzccv.extras.containsKey("sdk_less_server_data")) {
                    try {
                        zzajhVar2.zzbyo.zzus();
                    } catch (Throwable th) {
                        zzakb.v("Could not render test Ad label.");
                    }
                }
            }
        } catch (RuntimeException e) {
            zzakb.v("Could not render test AdLabel.");
        }
        return super.zza(zzajhVar, zzajhVar2);
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected void zzbq() {
        super.zzbq();
        if (this.bBM) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbcb)).booleanValue()) {
                d(this.zzvw.zzacw.zzbyo);
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.zzaf
    public final void zzcn() {
        onAdClicked();
    }

    @Override // com.google.android.gms.ads.internal.zzaf
    public final void zzco() {
        recordImpression();
        zzbm();
    }

    @Override // com.google.android.gms.internal.ads.zzaam
    public final void zzcq() {
        zzbn();
    }

    @Override // com.google.android.gms.ads.internal.zzaf
    public final void zzh(View view) {
        this.zzvw.bDm = view;
        zzb(new zzajh(this.zzvw.zzacx, null, null, null, null, null, null, null));
    }
}
