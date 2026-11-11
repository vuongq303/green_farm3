package com.google.android.gms.ads.internal.overlay;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import com.facebook.appevents.codeless.internal.Constants;
import com.gameloft.glads.AndroidWebView;
import com.google.android.gms.ads.internal.zzbv;
import com.google.android.gms.common.d.s;
import com.google.android.gms.internal.ads.zzaal;
import com.google.android.gms.internal.ads.zzaaq;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzakk;
import com.google.android.gms.internal.ads.zzakq;
import com.google.android.gms.internal.ads.zzaqw;
import com.google.android.gms.internal.ads.zzarc;
import com.google.android.gms.internal.ads.zzasc;
import com.google.android.gms.internal.ads.zzasd;
import com.google.android.gms.internal.ads.zzhs;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zznk;
import java.util.Collections;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public class zzd extends zzaaq implements zzw {
    private static final int bAm = Color.argb(0, 0, 0, 0);
    private boolean bAA;
    private boolean bAB;
    AdOverlayInfoParcel bAn;
    private zzi bAo;
    private zzo bAp;
    private FrameLayout bAr;
    private WebChromeClient.CustomViewCallback bAs;
    private d bAv;
    private Runnable bAz;
    protected final Activity mActivity;
    zzaqw zzbnd;
    private boolean bAq = false;
    private boolean bAt = false;
    private boolean bAu = false;
    private boolean bAw = false;
    int bAx = 0;
    private final Object bAy = new Object();
    private boolean bAC = false;
    private boolean bAD = false;
    private boolean bAE = true;

    public zzd(Activity activity) {
        this.mActivity = activity;
    }

    private final void WR() {
        if (!this.mActivity.isFinishing() || this.bAC) {
            return;
        }
        this.bAC = true;
        if (this.zzbnd != null) {
            this.zzbnd.zzai(this.bAx);
            synchronized (this.bAy) {
                if (!this.bAA && this.zzbnd.zzun()) {
                    this.bAz = new b(this);
                    zzakk.zzcrm.postDelayed(this.bAz, ((Long) zzkb.zzik().zzd(zznk.zzayq)).longValue());
                    return;
                }
            }
        }
        WS();
    }

    private final void bS(boolean z) {
        int iIntValue = ((Integer) zzkb.zzik().zzd(zznk.zzben)).intValue();
        g gVar = new g();
        gVar.size = 50;
        gVar.paddingLeft = z ? iIntValue : 0;
        gVar.paddingRight = z ? 0 : iIntValue;
        gVar.paddingTop = 0;
        gVar.paddingBottom = iIntValue;
        this.bAp = new zzo(this.mActivity, gVar, this);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(10);
        layoutParams.addRule(z ? 11 : 9);
        zza(z, this.bAn.zzbyr);
        this.bAv.addView(this.bAp, layoutParams);
    }

    /* JADX WARN: Code duplicated, block: B:114:0x0311  */
    private final void bT(boolean z) throws c {
        boolean zZza;
        if (!this.bAB) {
            this.mActivity.requestWindowFeature(1);
        }
        Window window = this.mActivity.getWindow();
        if (window == null) {
            throw new c("Invalid activity, no window available.");
        }
        if (s.abE()) {
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbel)).booleanValue()) {
                zzbv.zzek();
                zZza = zzakk.zza(this.mActivity, this.mActivity.getResources().getConfiguration());
            } else {
                zZza = true;
            }
        } else {
            zZza = true;
        }
        boolean z2 = this.bAn.zzbyw != null && this.bAn.zzbyw.zzzf;
        if ((!this.bAu || z2) && zZza) {
            window.setFlags(1024, 1024);
            if (((Boolean) zzkb.zzik().zzd(zznk.zzayr)).booleanValue() && s.abA() && this.bAn.zzbyw != null && this.bAn.zzbyw.zzzk) {
                window.getDecorView().setSystemUiVisibility(4098);
            }
        }
        zzasc zzascVarZzuf = this.bAn.zzbyo != null ? this.bAn.zzbyo.zzuf() : null;
        boolean zZzfz = zzascVarZzuf != null ? zzascVarZzuf.zzfz() : false;
        this.bAw = false;
        if (zZzfz) {
            if (this.bAn.orientation == zzbv.zzem().zzrl()) {
                this.bAw = this.mActivity.getResources().getConfiguration().orientation == 1;
            } else if (this.bAn.orientation == zzbv.zzem().zzrm()) {
                this.bAw = this.mActivity.getResources().getConfiguration().orientation == 2;
            }
        }
        zzakb.zzck(new StringBuilder(46).append("Delay onShow to next orientation change: ").append(this.bAw).toString());
        setRequestedOrientation(this.bAn.orientation);
        if (zzbv.zzem().zza(window)) {
            zzakb.zzck("Hardware acceleration on the AdActivity window enabled.");
        }
        if (this.bAu) {
            this.bAv.setBackgroundColor(bAm);
        } else {
            this.bAv.setBackgroundColor(-16777216);
        }
        this.mActivity.setContentView(this.bAv);
        this.bAB = true;
        if (z) {
            try {
                zzbv.zzel();
                this.zzbnd = zzarc.zza(this.mActivity, this.bAn.zzbyo != null ? this.bAn.zzbyo.zzud() : null, this.bAn.zzbyo != null ? this.bAn.zzbyo.zzue() : null, true, zZzfz, null, this.bAn.zzacr, null, null, this.bAn.zzbyo != null ? this.bAn.zzbyo.zzbi() : null, zzhs.zzhm());
                this.zzbnd.zzuf().zza(null, this.bAn.zzbyx, null, this.bAn.zzbyp, this.bAn.zzbyt, true, null, this.bAn.zzbyo != null ? this.bAn.zzbyo.zzuf().zzut() : null, null, null);
                this.zzbnd.zzuf().zza(new zzasd(this) { // from class: com.google.android.gms.ads.internal.overlay.a
                    private final zzd bAF;

                    {
                        this.bAF = this;
                    }

                    @Override // com.google.android.gms.internal.ads.zzasd
                    public final void zze(boolean z3) {
                        this.bAF.zzbnd.zzno();
                    }
                });
                if (this.bAn.url != null) {
                    this.zzbnd.loadUrl(this.bAn.url);
                } else {
                    if (this.bAn.zzbys == null) {
                        throw new c("No URL or HTML to display in ad overlay.");
                    }
                    this.zzbnd.loadDataWithBaseURL(this.bAn.zzbyq, this.bAn.zzbys, "text/html", AndroidWebView.UTF_8, null);
                }
                if (this.bAn.zzbyo != null) {
                    this.bAn.zzbyo.zzb(this);
                }
            } catch (Exception e) {
                zzakb.zzb("Error obtaining webview.", e);
                throw new c("Could not obtain webview for the overlay.");
            }
        } else {
            this.zzbnd = this.bAn.zzbyo;
            this.zzbnd.zzbm(this.mActivity);
        }
        this.zzbnd.zza(this);
        ViewParent parent = this.zzbnd.getParent();
        if (parent != null && (parent instanceof ViewGroup)) {
            ((ViewGroup) parent).removeView(this.zzbnd.getView());
        }
        if (this.bAu) {
            this.zzbnd.zzur();
        }
        this.bAv.addView(this.zzbnd.getView(), -1, -1);
        if (!z && !this.bAw) {
            zzno();
        }
        bS(zZzfz);
        if (this.zzbnd.zzuh()) {
            zza(zZzfz, true);
        }
    }

    private final void zzno() {
        this.zzbnd.zzno();
    }

    final void WS() {
        if (this.bAD) {
            return;
        }
        this.bAD = true;
        if (this.zzbnd != null) {
            this.bAv.removeView(this.zzbnd.getView());
            if (this.bAo != null) {
                this.zzbnd.zzbm(this.bAo.zzrt);
                this.zzbnd.zzai(false);
                this.bAo.parent.addView(this.zzbnd.getView(), this.bAo.index, this.bAo.zzbyi);
                this.bAo = null;
            } else if (this.mActivity.getApplicationContext() != null) {
                this.zzbnd.zzbm(this.mActivity.getApplicationContext());
            }
            this.zzbnd = null;
        }
        if (this.bAn == null || this.bAn.zzbyn == null) {
            return;
        }
        this.bAn.zzbyn.zzcb();
    }

    public final void close() {
        this.bAx = 2;
        this.mActivity.finish();
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onActivityResult(int i, int i2, Intent intent) {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onBackPressed() {
        this.bAx = 0;
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public void onCreate(Bundle bundle) {
        this.mActivity.requestWindowFeature(1);
        this.bAt = bundle != null ? bundle.getBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", false) : false;
        try {
            this.bAn = AdOverlayInfoParcel.zzc(this.mActivity.getIntent());
            if (this.bAn == null) {
                throw new c("Could not get info for ad overlay.");
            }
            if (this.bAn.zzacr.zzcvf > 7500000) {
                this.bAx = 3;
            }
            if (this.mActivity.getIntent() != null) {
                this.bAE = this.mActivity.getIntent().getBooleanExtra("shouldCallOnOverlayOpened", true);
            }
            if (this.bAn.zzbyw != null) {
                this.bAu = this.bAn.zzbyw.zzze;
            } else {
                this.bAu = false;
            }
            if (((Boolean) zzkb.zzik().zzd(zznk.zzbbg)).booleanValue() && this.bAu && this.bAn.zzbyw.zzzj != -1) {
                new e(this, null).zzqo();
            }
            if (bundle == null) {
                if (this.bAn.zzbyn != null && this.bAE) {
                    this.bAn.zzbyn.zzcc();
                }
                if (this.bAn.zzbyu != 1 && this.bAn.zzbym != null) {
                    this.bAn.zzbym.onAdClicked();
                }
            }
            this.bAv = new d(this.mActivity, this.bAn.zzbyv, this.bAn.zzacr.zzcw);
            this.bAv.setId(Constants.APP_INDEXING_SCHEDULE_INTERVAL_MS);
            switch (this.bAn.zzbyu) {
                case 1:
                    bT(false);
                    return;
                case 2:
                    this.bAo = new zzi(this.bAn.zzbyo);
                    bT(false);
                    return;
                case 3:
                    bT(true);
                    return;
                default:
                    throw new c("Could not determine ad overlay type.");
            }
        } catch (c e) {
            zzakb.zzdk(e.getMessage());
            this.bAx = 3;
            this.mActivity.finish();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onDestroy() {
        if (this.zzbnd != null) {
            this.bAv.removeView(this.zzbnd.getView());
        }
        WR();
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onPause() {
        zznh();
        if (this.bAn.zzbyn != null) {
            this.bAn.zzbyn.onPause();
        }
        if (!((Boolean) zzkb.zzik().zzd(zznk.zzbem)).booleanValue() && this.zzbnd != null && (!this.mActivity.isFinishing() || this.bAo == null)) {
            zzbv.zzem();
            zzakq.zzi(this.zzbnd);
        }
        WR();
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onRestart() {
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onResume() {
        if (this.bAn.zzbyn != null) {
            this.bAn.zzbyn.onResume();
        }
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbem)).booleanValue()) {
            return;
        }
        if (this.zzbnd == null || this.zzbnd.isDestroyed()) {
            zzakb.zzdk("The webview does not exist. Ignoring action.");
        } else {
            zzbv.zzem();
            zzakq.zzj(this.zzbnd);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean("com.google.android.gms.ads.internal.overlay.hasResumed", this.bAt);
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onStart() {
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbem)).booleanValue()) {
            if (this.zzbnd == null || this.zzbnd.isDestroyed()) {
                zzakb.zzdk("The webview does not exist. Ignoring action.");
            } else {
                zzbv.zzem();
                zzakq.zzj(this.zzbnd);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void onStop() {
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbem)).booleanValue() && this.zzbnd != null && (!this.mActivity.isFinishing() || this.bAo == null)) {
            zzbv.zzem();
            zzakq.zzi(this.zzbnd);
        }
        WR();
    }

    public final void setRequestedOrientation(int i) {
        if (this.mActivity.getApplicationInfo().targetSdkVersion >= ((Integer) zzkb.zzik().zzd(zznk.zzbfs)).intValue()) {
            if (this.mActivity.getApplicationInfo().targetSdkVersion <= ((Integer) zzkb.zzik().zzd(zznk.zzbft)).intValue()) {
                if (Build.VERSION.SDK_INT >= ((Integer) zzkb.zzik().zzd(zznk.zzbfu)).intValue()) {
                    if (Build.VERSION.SDK_INT <= ((Integer) zzkb.zzik().zzd(zznk.zzbfv)).intValue()) {
                        return;
                    }
                }
            }
        }
        this.mActivity.setRequestedOrientation(i);
    }

    public final void zza(View view, WebChromeClient.CustomViewCallback customViewCallback) {
        this.bAr = new FrameLayout(this.mActivity);
        this.bAr.setBackgroundColor(-16777216);
        this.bAr.addView(view, -1, -1);
        this.mActivity.setContentView(this.bAr);
        this.bAB = true;
        this.bAs = customViewCallback;
        this.bAq = true;
    }

    public final void zza(boolean z, boolean z2) {
        boolean z3 = false;
        boolean z4 = ((Boolean) zzkb.zzik().zzd(zznk.zzays)).booleanValue() && this.bAn != null && this.bAn.zzbyw != null && this.bAn.zzbyw.zzzl;
        boolean z5 = ((Boolean) zzkb.zzik().zzd(zznk.zzayt)).booleanValue() && this.bAn != null && this.bAn.zzbyw != null && this.bAn.zzbyw.zzzm;
        if (z && z2 && z4 && !z5) {
            new zzaal(this.zzbnd, "useCustomClose").zzbw("Custom close has been disabled for interstitial ads in this ad slot.");
        }
        if (this.bAp != null) {
            zzo zzoVar = this.bAp;
            if (z5 || (z2 && !z4)) {
                z3 = true;
            }
            zzoVar.zzu(z3);
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void zzax() {
        this.bAB = true;
    }

    public final void zznh() {
        if (this.bAn != null && this.bAq) {
            setRequestedOrientation(this.bAn.orientation);
        }
        if (this.bAr != null) {
            this.mActivity.setContentView(this.bAv);
            this.bAB = true;
            this.bAr.removeAllViews();
            this.bAr = null;
        }
        if (this.bAs != null) {
            this.bAs.onCustomViewHidden();
            this.bAs = null;
        }
        this.bAq = false;
    }

    @Override // com.google.android.gms.ads.internal.overlay.zzw
    public final void zzni() {
        this.bAx = 1;
        this.mActivity.finish();
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final boolean zznj() {
        this.bAx = 0;
        if (this.zzbnd == null) {
            return true;
        }
        boolean zZzul = this.zzbnd.zzul();
        if (zZzul) {
            return zZzul;
        }
        this.zzbnd.zza("onbackblocked", Collections.emptyMap());
        return zZzul;
    }

    public final void zznk() {
        this.bAv.removeView(this.bAp);
        bS(true);
    }

    public final void zznn() {
        if (this.bAw) {
            this.bAw = false;
            zzno();
        }
    }

    public final void zznp() {
        this.bAv.bAI = true;
    }

    public final void zznq() {
        synchronized (this.bAy) {
            this.bAA = true;
            if (this.bAz != null) {
                zzakk.zzcrm.removeCallbacks(this.bAz);
                zzakk.zzcrm.post(this.bAz);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzaap
    public final void zzo(com.google.android.gms.c.a aVar) {
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbel)).booleanValue() && s.abE()) {
            Configuration configuration = (Configuration) com.google.android.gms.c.d.b(aVar);
            zzbv.zzek();
            if (zzakk.zza(this.mActivity, configuration)) {
                this.mActivity.getWindow().addFlags(1024);
                this.mActivity.getWindow().clearFlags(2048);
            } else {
                this.mActivity.getWindow().addFlags(2048);
                this.mActivity.getWindow().clearFlags(1024);
            }
        }
    }
}
