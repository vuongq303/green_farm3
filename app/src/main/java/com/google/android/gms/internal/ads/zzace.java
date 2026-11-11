package com.google.android.gms.internal.ads;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import java.lang.ref.WeakReference;
import javax.annotation.concurrent.GuardedBy;
import org.json.JSONObject;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzace {
    private final Context mContext;
    private final Object mLock = new Object();

    @GuardedBy("mLock")
    private int zzadx = -1;

    @GuardedBy("mLock")
    private int zzady = -1;
    private zzamj zzadz = new zzamj(200);
    private final DisplayMetrics zzagj;
    private final zzci zzbjc;
    private final zzaji zzbze;
    private final com.google.android.gms.ads.internal.zzbc zzcbc;
    private ViewTreeObserver.OnGlobalLayoutListener zzcbd;
    private ViewTreeObserver.OnScrollChangedListener zzcbe;
    private final zznx zzvr;

    public zzace(Context context, zzci zzciVar, zzaji zzajiVar, zznx zznxVar, com.google.android.gms.ads.internal.zzbc zzbcVar) {
        this.mContext = context;
        this.zzbjc = zzciVar;
        this.zzbze = zzajiVar;
        this.zzvr = zznxVar;
        this.zzcbc = zzbcVar;
        com.google.android.gms.ads.internal.zzbv.zzek();
        this.zzagj = zzakk.zza((WindowManager) context.getSystemService("window"));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void zza(WeakReference<zzaqw> weakReference, boolean z) {
        zzaqw zzaqwVar;
        if (weakReference == null || (zzaqwVar = weakReference.get()) == null || zzaqwVar.getView() == null) {
            return;
        }
        if (!z || this.zzadz.tryAcquire()) {
            int[] iArr = new int[2];
            zzaqwVar.getView().getLocationOnScreen(iArr);
            zzkb.zzif();
            int iZzb = zzamu.zzb(this.zzagj, iArr[0]);
            zzkb.zzif();
            int iZzb2 = zzamu.zzb(this.zzagj, iArr[1]);
            synchronized (this.mLock) {
                if (this.zzadx != iZzb || this.zzady != iZzb2) {
                    this.zzadx = iZzb;
                    this.zzady = iZzb2;
                    zzaqwVar.zzuf().zza(this.zzadx, this.zzady, !z);
                }
            }
        }
    }

    final /* synthetic */ void zza(zzaoj zzaojVar, zzaqw zzaqwVar, boolean z) {
        this.zzcbc.zzdw();
        zzaojVar.set(zzaqwVar);
    }

    final /* synthetic */ void zza(final JSONObject jSONObject, final zzaoj zzaojVar) {
        try {
            com.google.android.gms.ads.internal.zzbv.zzel();
            final zzaqw zzaqwVarZza = zzarc.zza(this.mContext, zzasi.zzvq(), "native-video", false, false, this.zzbjc, this.zzbze.zzcgs.zzacr, this.zzvr, null, this.zzcbc.zzbi(), this.zzbze.zzcoq);
            zzaqwVarZza.zza(zzasi.zzvr());
            this.zzcbc.zzf(zzaqwVarZza);
            WeakReference weakReference = new WeakReference(zzaqwVarZza);
            zzasc zzascVarZzuf = zzaqwVarZza.zzuf();
            if (this.zzcbd == null) {
                this.zzcbd = new zzack(this, weakReference);
            }
            ViewTreeObserver.OnGlobalLayoutListener onGlobalLayoutListener = this.zzcbd;
            if (this.zzcbe == null) {
                this.zzcbe = new zzacl(this, weakReference);
            }
            zzascVarZzuf.zza(onGlobalLayoutListener, this.zzcbe);
            zzaqwVarZza.zza("/video", com.google.android.gms.ads.internal.gmsg.zzf.zzblz);
            zzaqwVarZza.zza("/videoMeta", com.google.android.gms.ads.internal.gmsg.zzf.zzbma);
            zzaqwVarZza.zza("/precache", new zzaql());
            zzaqwVarZza.zza("/delayPageLoaded", com.google.android.gms.ads.internal.gmsg.zzf.zzbmd);
            zzaqwVarZza.zza("/instrument", com.google.android.gms.ads.internal.gmsg.zzf.zzbmb);
            zzaqwVarZza.zza("/log", com.google.android.gms.ads.internal.gmsg.zzf.zzblu);
            zzaqwVarZza.zza("/videoClicked", com.google.android.gms.ads.internal.gmsg.zzf.zzblv);
            zzaqwVarZza.zza("/trackActiveViewUnit", new zzaci(this));
            zzaqwVarZza.zza("/untrackActiveViewUnit", new zzacj(this));
            zzaqwVarZza.zzuf().zza(new zzase(zzaqwVarZza, jSONObject) { // from class: com.google.android.gms.internal.ads.zzacg
                private final JSONObject zzcbg;
                private final zzaqw zzcbh;

                {
                    this.zzcbh = zzaqwVarZza;
                    this.zzcbg = jSONObject;
                }

                @Override // com.google.android.gms.internal.ads.zzase
                public final void zzly() {
                    this.zzcbh.zzb("google.afma.nativeAds.renderVideo", this.zzcbg);
                }
            });
            zzaqwVarZza.zzuf().zza(new zzasd(this, zzaojVar, zzaqwVarZza) { // from class: com.google.android.gms.internal.ads.zzach
                private final zzaoj zzcag;
                private final zzace zzcbf;
                private final zzaqw zzzp;

                {
                    this.zzcbf = this;
                    this.zzcag = zzaojVar;
                    this.zzzp = zzaqwVarZza;
                }

                @Override // com.google.android.gms.internal.ads.zzasd
                public final void zze(boolean z) {
                    this.zzcbf.zza(this.zzcag, this.zzzp, z);
                }
            });
            zzaqwVarZza.loadUrl((String) zzkb.zzik().zzd(zznk.zzbbs));
        } catch (Exception e) {
            zzakb.zzc("Exception occurred while getting video view", e);
            zzaojVar.set(null);
        }
    }
}
