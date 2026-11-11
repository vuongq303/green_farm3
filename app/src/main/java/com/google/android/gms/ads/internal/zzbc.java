package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzaaw;
import com.google.android.gms.internal.ads.zzabl;
import com.google.android.gms.internal.ads.zzacm;
import com.google.android.gms.internal.ads.zzacq;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzaki;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzanz;
import com.google.android.gms.internal.ads.zzaoj;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzev;
import com.google.android.gms.internal.ads.zzjj;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzlo;
import com.google.android.gms.internal.ads.zzlr;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zznx;
import com.google.android.gms.internal.ads.zzod;
import com.google.android.gms.internal.ads.zzoo;
import com.google.android.gms.internal.ads.zzoq;
import com.google.android.gms.internal.ads.zzos;
import com.google.android.gms.internal.ads.zzov;
import com.google.android.gms.internal.ads.zzox;
import com.google.android.gms.internal.ads.zzoy;
import com.google.android.gms.internal.ads.zzoz;
import com.google.android.gms.internal.ads.zzpa;
import com.google.android.gms.internal.ads.zzpb;
import com.google.android.gms.internal.ads.zzpd;
import com.google.android.gms.internal.ads.zzqs;
import com.google.android.gms.internal.ads.zzrc;
import com.google.android.gms.internal.ads.zzrf;
import com.google.android.gms.internal.ads.zzwy;
import com.google.android.gms.internal.ads.zzxn;
import com.google.android.gms.internal.ads.zzxq;
import com.google.android.gms.internal.ads.zzxz;
import com.google.android.gms.internal.ads.zzyc;
import com.google.android.gms.internal.ads.zzyf;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import javax.annotation.ParametersAreNonnullByDefault;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzbc extends zzd implements zzpa {
    private boolean bBI;
    private zzaoj<zzpb> bBJ;
    private zzaqw bBK;
    private zzaqw bBL;
    private boolean bBM;
    private final Object mLock;
    private int zzaac;

    @GuardedBy("mLock")
    private zzacm zzaad;
    private final String zzaae;

    public zzbc(Context context, zzw zzwVar, zzjn zzjnVar, String str, zzxn zzxnVar, zzang zzangVar) {
        this(context, zzwVar, zzjnVar, str, zzxnVar, zzangVar, false);
    }

    public zzbc(Context context, zzw zzwVar, zzjn zzjnVar, String str, zzxn zzxnVar, zzang zzangVar, boolean z) {
        super(context, zzjnVar, str, zzxnVar, zzangVar, zzwVar);
        this.mLock = new Object();
        this.bBJ = new zzaoj<>();
        this.zzaac = 1;
        this.zzaae = UUID.randomUUID().toString();
        this.bBI = z;
    }

    private final boolean Xb() {
        return this.zzvw.zzacw != null && this.zzvw.zzacw.zzcfp;
    }

    private final zzwy Xc() {
        if (this.zzvw.zzacw == null || !this.zzvw.zzacw.zzceq) {
            return null;
        }
        return this.zzvw.zzacw.zzcod;
    }

    private final void Xd() {
        zzacm zzacmVarZzdr = zzdr();
        if (zzacmVarZzdr != null) {
            zzacmVarZzdr.zzmc();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static zzov a(zzpb zzpbVar) {
        zzov zzovVar = null;
        Object objB = null;
        if (zzpbVar instanceof zzoq) {
            zzoq zzoqVar = (zzoq) zzpbVar;
            zzovVar = new zzov(zzoqVar.getHeadline(), zzoqVar.getImages(), zzoqVar.getBody(), zzoqVar.zzkg(), zzoqVar.getCallToAction(), zzoqVar.getAdvertiser(), -1.0d, null, null, zzoqVar.zzkc(), zzoqVar.getVideoController(), zzoqVar.zzkd(), zzoqVar.zzke(), zzoqVar.getMediationAdapterClassName(), zzoqVar.getExtras());
            objB = zzoqVar.zzka() != null ? com.google.android.gms.c.d.b(zzoqVar.zzka()) : null;
        } else if (zzpbVar instanceof zzoo) {
            zzoo zzooVar = (zzoo) zzpbVar;
            zzovVar = new zzov(zzooVar.getHeadline(), zzooVar.getImages(), zzooVar.getBody(), zzooVar.zzjz(), zzooVar.getCallToAction(), null, zzooVar.getStarRating(), zzooVar.getStore(), zzooVar.getPrice(), zzooVar.zzkc(), zzooVar.getVideoController(), zzooVar.zzkd(), zzooVar.zzke(), zzooVar.getMediationAdapterClassName(), zzooVar.getExtras());
            objB = zzooVar.zzka() != null ? com.google.android.gms.c.d.b(zzooVar.zzka()) : null;
        }
        if (objB instanceof zzpd) {
            zzovVar.zzb((zzpd) objB);
        }
        return zzovVar;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void a(zzbw zzbwVar, zzbw zzbwVar2) {
        if (zzbwVar2.bDc == null) {
            zzbwVar2.bDc = zzbwVar.bDc;
        }
        if (zzbwVar2.bDd == null) {
            zzbwVar2.bDd = zzbwVar.bDd;
        }
        if (zzbwVar2.bDf == null) {
            zzbwVar2.bDf = zzbwVar.bDf;
        }
        if (zzbwVar2.bDg == null) {
            zzbwVar2.bDg = zzbwVar.bDg;
        }
        if (zzbwVar2.bDh == null) {
            zzbwVar2.bDh = zzbwVar.bDh;
        }
        if (zzbwVar2.zzadj == null) {
            zzbwVar2.zzadj = zzbwVar.zzadj;
        }
        if (zzbwVar2.zzads == null) {
            zzbwVar2.zzads = zzbwVar.zzads;
        }
        if (zzbwVar2.zzacy == null) {
            zzbwVar2.zzacy = zzbwVar.zzacy;
        }
        if (zzbwVar2.zzadt == null) {
            zzbwVar2.zzadt = zzbwVar.zzadt;
        }
        if (zzbwVar2.bCX == null) {
            zzbwVar2.bCX = zzbwVar.bCX;
        }
        if (zzbwVar2.bCY == null) {
            zzbwVar2.bCY = zzbwVar.bCY;
        }
        if (zzbwVar2.zzacv == null) {
            zzbwVar2.zzacv = zzbwVar.zzacv;
        }
        if (zzbwVar2.zzacw == null) {
            zzbwVar2.zzacw = zzbwVar.zzacw;
        }
        if (zzbwVar2.zzacx == null) {
            zzbwVar2.zzacx = zzbwVar.zzacx;
        }
    }

    private final void a(zzoo zzooVar) {
        zzakk.zzcrm.post(new u(this, zzooVar));
    }

    private final void a(zzoq zzoqVar) {
        zzakk.zzcrm.post(new w(this, zzoqVar));
    }

    private final void a(zzov zzovVar) {
        zzakk.zzcrm.post(new v(this, zzovVar));
    }

    final void WZ() {
        synchronized (this.mLock) {
            zzakb.v("Initializing webview native ads utills");
            this.zzaad = new zzacq(this.zzvw.zzrt, this, this.zzaae, this.zzvw.bCV, this.zzvw.zzacr);
        }
    }

    protected final Future<zzpb> Xa() {
        return this.bBJ;
    }

    protected final void a(com.google.android.gms.c.a aVar) {
        Object objB = aVar != null ? com.google.android.gms.c.d.b(aVar) : null;
        if (objB instanceof zzoz) {
            ((zzoz) objB).zzkl();
        }
        super.zzb(this.zzvw.zzacw, false);
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final String getAdUnitId() {
        return this.zzvw.zzacp;
    }

    public final String getUuid() {
        return this.zzaae;
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void pause() {
        throw new IllegalStateException("Native Ad DOES NOT support pause().");
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void resume() {
        throw new IllegalStateException("Native Ad DOES NOT support resume().");
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzks
    public final void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by NativeAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void zza(zzaaw zzaawVar) {
        throw new IllegalStateException("In App Purchase is NOT supported by NativeAdManager.");
    }

    @Override // com.google.android.gms.ads.internal.zza
    public final void zza(zzaji zzajiVar, zznx zznxVar) {
        if (zzajiVar.zzacv != null) {
            this.zzvw.zzacv = zzajiVar.zzacv;
        }
        if (zzajiVar.errorCode != -2) {
            zzakk.zzcrm.post(new r(this, zzajiVar));
            return;
        }
        int i = zzajiVar.zzcgs.zzceg;
        if (i == 1) {
            this.zzvw.zzadv = 0;
            zzbw zzbwVar = this.zzvw;
            zzbv.zzej();
            zzbwVar.zzacu = zzabl.zza(this.zzvw.zzrt, this, zzajiVar, this.zzvw.bCV, null, this.zzwh, this, zznxVar);
            String strValueOf = String.valueOf(this.zzvw.zzacu.getClass().getName());
            zzakb.zzck(strValueOf.length() != 0 ? "AdRenderer: ".concat(strValueOf) : new String("AdRenderer: "));
            return;
        }
        JSONArray jSONArray = new JSONArray();
        try {
            JSONArray jSONArray2 = new JSONObject(zzajiVar.zzcos.zzceo).getJSONArray("slots");
            for (int i2 = 0; i2 < jSONArray2.length(); i2++) {
                JSONArray jSONArray3 = jSONArray2.getJSONObject(i2).getJSONArray("ads");
                for (int i3 = 0; i3 < jSONArray3.length(); i3++) {
                    jSONArray.put(jSONArray3.get(i3));
                }
            }
            Xd();
            ArrayList arrayList = new ArrayList();
            for (int i4 = 0; i4 < i; i4++) {
                arrayList.add(zzaki.zza(new s(this, i4, jSONArray, i, zzajiVar)));
            }
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                try {
                    zzakk.zzcrm.post(new t(this, (zzpb) ((zzanz) arrayList.get(i5)).get(((Long) zzkb.zzik().zzd(zznk.zzbao)).longValue(), TimeUnit.MILLISECONDS), i5, arrayList));
                } catch (InterruptedException e) {
                    zzane.zzc("", e);
                    Thread.currentThread().interrupt();
                } catch (CancellationException e2) {
                    e = e2;
                    zzane.zzc("", e);
                } catch (ExecutionException e3) {
                    e = e3;
                    zzane.zzc("", e);
                } catch (TimeoutException e4) {
                    e = e4;
                    zzane.zzc("", e);
                }
            }
        } catch (JSONException e5) {
            zzakb.zzc("Malformed native ad response", e5);
            zzi(0);
        }
    }

    @Override // com.google.android.gms.ads.internal.zza, com.google.android.gms.internal.ads.zzks
    public final void zza(zzod zzodVar) {
        throw new IllegalStateException("CustomRendering is NOT supported by NativeAdManager.");
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zza(zzox zzoxVar) {
        if (this.bBK != null) {
            this.bBK.zzb(zzoxVar);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zza(zzoz zzozVar) {
        if (this.zzvw.zzacw.zzcob != null) {
            zzbv.zzeo().zzqd().zza(this.zzvw.zzacv, this.zzvw.zzacw, new zzev(zzozVar), (zzaqw) null);
        }
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    protected final boolean zza(zzajh zzajhVar, zzajh zzajhVar2) {
        zzd(null);
        if (!this.zzvw.zzfo()) {
            throw new IllegalStateException("Native ad DOES NOT have custom rendering mode.");
        }
        if (zzajhVar2.zzceq) {
            Xd();
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
                    a(zzooVar);
                } else if (zzycVarZzmp != null && this.zzvw.bDe != null) {
                    zzov zzovVar3 = new zzov(zzycVarZzmp.getHeadline(), zzycVarZzmp.getImages(), zzycVarZzmp.getBody(), zzycVarZzmp.zzkg() != null ? zzycVarZzmp.zzkg() : null, zzycVarZzmp.getCallToAction(), zzycVarZzmp.getAdvertiser(), -1.0d, null, null, null, zzycVarZzmp.getVideoController(), zzycVarZzmp.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzycVarZzmp.zzmw()) : null, zzycVarZzmp.zzke(), strZzc, zzycVarZzmp.getExtras());
                    zzovVar3.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzycVarZzmp, zzovVar3));
                    a(zzovVar3);
                } else if (zzycVarZzmp != null && this.zzvw.bDd != null) {
                    zzoq zzoqVar = new zzoq(zzycVarZzmp.getHeadline(), zzycVarZzmp.getImages(), zzycVarZzmp.getBody(), zzycVarZzmp.zzkg() != null ? zzycVarZzmp.zzkg() : null, zzycVarZzmp.getCallToAction(), zzycVarZzmp.getAdvertiser(), null, zzycVarZzmp.getExtras(), zzycVarZzmp.getVideoController(), zzycVarZzmp.zzmw() != null ? (View) com.google.android.gms.c.d.b(zzycVarZzmp.zzmw()) : null, zzycVarZzmp.zzke(), strZzc);
                    zzoqVar.zzb(new zzoy(this.zzvw.zzrt, this, this.zzvw.bCV, zzycVarZzmp, zzoqVar));
                    a(zzoqVar);
                } else {
                    if (zzqsVarZzmt == null || this.zzvw.bDg == null || this.zzvw.bDg.get(zzqsVarZzmt.getCustomTemplateId()) == null) {
                        zzakb.zzdk("No matching mapper/listener for retrieved native ad template.");
                        zzi(0);
                        return false;
                    }
                    zzakk.zzcrm.post(new y(this, zzqsVarZzmt));
                }
            } catch (RemoteException e) {
                zzakb.zzd("#007 Could not call remote method.", e);
            }
        } else {
            zzpb zzpbVar = zzajhVar2.zzcoj;
            if (this.bBI) {
                this.bBJ.set(zzpbVar);
            } else if ((zzpbVar instanceof zzoq) && this.zzvw.bDe != null) {
                a(a(zzajhVar2.zzcoj));
            } else if ((zzpbVar instanceof zzoq) && this.zzvw.bDd != null) {
                a((zzoq) zzajhVar2.zzcoj);
            } else if ((zzpbVar instanceof zzoo) && this.zzvw.bDe != null) {
                a(a(zzajhVar2.zzcoj));
            } else if ((zzpbVar instanceof zzoo) && this.zzvw.bDc != null) {
                a((zzoo) zzajhVar2.zzcoj);
            } else {
                if (!(zzpbVar instanceof zzos) || this.zzvw.bDg == null || this.zzvw.bDg.get(((zzos) zzpbVar).getCustomTemplateId()) == null) {
                    zzakb.zzdk("No matching listener for retrieved native ad template.");
                    zzi(0);
                    return false;
                }
                zzakk.zzcrm.post(new x(this, ((zzos) zzpbVar).getCustomTemplateId(), zzajhVar2));
            }
        }
        return super.zza(zzajhVar, zzajhVar2);
    }

    @Override // com.google.android.gms.ads.internal.zzd
    protected final boolean zza(zzjj zzjjVar, zzajh zzajhVar, boolean z) {
        return this.zzvv.zzdz();
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.ads.internal.zza
    public final boolean zza(zzjj zzjjVar, zznx zznxVar) {
        try {
            WZ();
            return super.zza(zzjjVar, zznxVar, this.zzaac);
        } catch (Exception e) {
            if (zzane.isLoggable(4)) {
                Log.i("Ads", "Error initializing webview.", e);
            }
            return false;
        }
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected final void zzb(boolean z) {
        String str;
        zzaqw zzaqwVar;
        super.zzb(z);
        if (this.bBM) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbcb)).booleanValue()) {
                zzdt();
            }
        }
        if (Xb()) {
            if (this.bBL == null && this.bBK == null) {
                return;
            }
            if (this.bBL != null) {
                str = null;
                zzaqwVar = this.bBL;
            } else if (this.bBK != null) {
                str = "javascript";
                zzaqwVar = this.bBK;
            } else {
                str = null;
                zzaqwVar = null;
            }
            if (zzaqwVar.getWebView() == null || !zzbv.zzfa().zzi(this.zzvw.zzrt)) {
                return;
            }
            this.zzwb = zzbv.zzfa().zza(new StringBuilder(23).append(this.zzvw.zzacr.zzcve).append(".").append(this.zzvw.zzacr.zzcvf).toString(), zzaqwVar.getWebView(), "", "javascript", str);
            if (this.zzwb != null) {
                zzbv.zzfa().zzm(this.zzwb);
            }
        }
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected final void zzbq() {
        zzb(false);
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected final void zzc(int i, boolean z) {
        Xd();
        super.zzc(i, z);
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzwz
    public final void zzcd() {
        zzlr zzlrVarZzio;
        zzajh zzajhVar = this.zzvw.zzacw;
        if (zzajhVar.zzbtx == null) {
            super.zzcd();
            return;
        }
        try {
            zzxq zzxqVar = zzajhVar.zzbtx;
            zzlo videoController = null;
            zzxz zzxzVarZzmo = zzxqVar.zzmo();
            if (zzxzVarZzmo != null) {
                videoController = zzxzVarZzmo.getVideoController();
            } else {
                zzyc zzycVarZzmp = zzxqVar.zzmp();
                if (zzycVarZzmp != null) {
                    videoController = zzycVarZzmp.getVideoController();
                } else {
                    zzqs zzqsVarZzmt = zzxqVar.zzmt();
                    if (zzqsVarZzmt != null) {
                        videoController = zzqsVarZzmt.getVideoController();
                    }
                }
            }
            if (videoController == null || (zzlrVarZzio = videoController.zzio()) == null) {
                return;
            }
            zzlrVarZzio.onVideoEnd();
        } catch (RemoteException e) {
            zzakb.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzwz
    public final void zzce() {
        if (this.zzvw.zzacw == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzvw.zzacw.zzbty)) {
            super.zzce();
        } else {
            zzbs();
        }
    }

    @Override // com.google.android.gms.ads.internal.zzd, com.google.android.gms.internal.ads.zzwz
    public final void zzcj() {
        if (this.zzvw.zzacw == null || !"com.google.ads.mediation.admob.AdMobAdapter".equals(this.zzvw.zzacw.zzbty)) {
            super.zzcj();
        } else {
            zzbr();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzcr() {
        if (Xb() && this.zzwb != null) {
            zzaqw zzaqwVar = null;
            if (this.bBL != null) {
                zzaqwVar = this.bBL;
            } else if (this.bBK != null) {
                zzaqwVar = this.bBK;
            }
            if (zzaqwVar != null) {
                zzaqwVar.zza("onSdkImpression", new HashMap());
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzcs() {
        super.zzby();
        if (this.bBL != null) {
            this.bBL.destroy();
            this.bBL = null;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzct() {
        if (this.bBK != null) {
            this.bBK.destroy();
            this.bBK = null;
        }
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

    public final zzacm zzdr() {
        zzacm zzacmVar;
        synchronized (this.mLock) {
            zzacmVar = this.zzaad;
        }
        return zzacmVar;
    }

    public final void zzdt() {
        if (this.zzvw.zzacw == null || this.bBK == null) {
            this.bBM = true;
            zzakb.zzdk("Request to enable ActiveView before adState is available.");
        } else {
            zzbv.zzeo().zzqd().zza(this.zzvw.zzacv, this.zzvw.zzacw, this.bBK.getView(), this.bBK);
            this.bBM = false;
        }
    }

    public final void zzdu() {
        this.bBM = false;
        if (this.zzvw.zzacw == null || this.bBK == null) {
            zzakb.zzdk("Request to enable ActiveView before adState is available.");
        } else {
            zzbv.zzeo().zzqd().zzh(this.zzvw.zzacw);
        }
    }

    public final android.support.v4.e.r<String, zzrf> zzdv() {
        bf.hV("getOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        return this.zzvw.bDg;
    }

    public final void zzdw() {
        if (this.bBK == null || this.bBK.zztm() == null || this.zzvw.zzadj == null || this.zzvw.zzadj.zzbjr == null) {
            return;
        }
        this.bBK.zztm().zzb(this.zzvw.zzadj.zzbjr);
    }

    public final void zzf(zzaqw zzaqwVar) {
        this.bBK = zzaqwVar;
    }

    public final void zzg(zzaqw zzaqwVar) {
        this.bBL = zzaqwVar;
    }

    @Override // com.google.android.gms.ads.internal.zza
    protected final void zzi(int i) {
        zzc(i, false);
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final void zzi(View view) {
        if (this.zzwb != null) {
            zzbv.zzfa().zza(this.zzwb, view);
        }
    }

    public final void zzj(int i) {
        bf.hV("setMaxNumberOfAds must be called on the main UI thread.");
        this.zzaac = i;
    }

    @Override // com.google.android.gms.internal.ads.zzpa
    public final zzrc zzr(String str) {
        bf.hV("getOnCustomClickListener must be called on the main UI thread.");
        if (this.zzvw.bDf == null) {
            return null;
        }
        return this.zzvw.bDf.get(str);
    }
}
