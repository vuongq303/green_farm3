package com.google.android.gms.ads.internal;

import android.R;
import android.content.Context;
import android.graphics.Rect;
import android.os.RemoteException;
import android.view.View;
import android.view.ViewTreeObserver;
import com.google.android.gms.internal.ads.zzadh;
import com.google.android.gms.internal.ads.zzagx;
import com.google.android.gms.internal.ads.zzahe;
import com.google.android.gms.internal.ads.zzajh;
import com.google.android.gms.internal.ads.zzaji;
import com.google.android.gms.internal.ads.zzajj;
import com.google.android.gms.internal.ads.zzaju;
import com.google.android.gms.internal.ads.zzajx;
import com.google.android.gms.internal.ads.zzakb;
import com.google.android.gms.internal.ads.zzalc;
import com.google.android.gms.internal.ads.zzamj;
import com.google.android.gms.internal.ads.zzamu;
import com.google.android.gms.internal.ads.zzang;
import com.google.android.gms.internal.ads.zzce;
import com.google.android.gms.internal.ads.zzci;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzke;
import com.google.android.gms.internal.ads.zzkh;
import com.google.android.gms.internal.ads.zzkx;
import com.google.android.gms.internal.ads.zzla;
import com.google.android.gms.internal.ads.zzlg;
import com.google.android.gms.internal.ads.zzlu;
import com.google.android.gms.internal.ads.zzmu;
import com.google.android.gms.internal.ads.zznk;
import com.google.android.gms.internal.ads.zzod;
import com.google.android.gms.internal.ads.zzpl;
import com.google.android.gms.internal.ads.zzqw;
import com.google.android.gms.internal.ads.zzqz;
import com.google.android.gms.internal.ads.zzrc;
import com.google.android.gms.internal.ads.zzrf;
import com.google.android.gms.internal.ads.zzri;
import com.google.android.gms.internal.ads.zzrl;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzbw implements ViewTreeObserver.OnGlobalLayoutListener, ViewTreeObserver.OnScrollChangedListener {
    final zzci bCV;
    zzbx bCW;
    zzke bCX;
    zzkh bCY;
    zzla bCZ;
    zzkx bDa;
    zzlg bDb;
    zzqw bDc;
    zzqz bDd;
    zzrl bDe;
    android.support.v4.e.r<String, zzrc> bDf;
    android.support.v4.e.r<String, zzrf> bDg;
    zzmu bDh;
    zzri bDi;
    zzod bDj;
    zzahe bDk;
    zzagx bDl;
    View bDm;
    private HashSet<zzajj> bDn;
    private boolean bDo;
    private boolean bDp;
    final String zzaco;
    public String zzacp;
    public final zzang zzacr;
    public zzajx zzact;
    public zzalc zzacu;
    public zzjn zzacv;
    public zzajh zzacw;
    public zzaji zzacx;
    public zzajj zzacy;
    zzpl zzadj;
    zzlu zzadl;
    List<Integer> zzadn;
    public String zzadr;
    List<String> zzads;
    public zzaju zzadt;
    public int zzadv;
    private int zzadx;
    private int zzady;
    private zzamj zzadz;
    private boolean zzaec;
    public final Context zzrt;
    boolean zzze;

    public zzbw(Context context, zzjn zzjnVar, String str, zzang zzangVar) {
        this(context, zzjnVar, str, zzangVar, null);
    }

    private zzbw(Context context, zzjn zzjnVar, String str, zzang zzangVar, zzci zzciVar) {
        this.zzadt = null;
        this.bDm = null;
        this.zzadv = 0;
        this.zzze = false;
        this.bDn = null;
        this.zzadx = -1;
        this.zzady = -1;
        this.bDo = true;
        this.bDp = true;
        this.zzaec = false;
        zznk.initialize(context);
        if (zzbv.zzeo().zzpy() != null) {
            List<String> listZzjc = zznk.zzjc();
            if (zzangVar.zzcve != 0) {
                listZzjc.add(Integer.toString(zzangVar.zzcve));
            }
            zzbv.zzeo().zzpy().zzg(listZzjc);
        }
        this.zzaco = UUID.randomUUID().toString();
        if (zzjnVar.zzarc || zzjnVar.zzare) {
            this.bCW = null;
        } else {
            this.bCW = new zzbx(context, str, zzangVar.zzcw, this, this);
            this.bCW.setMinimumWidth(zzjnVar.widthPixels);
            this.bCW.setMinimumHeight(zzjnVar.heightPixels);
            this.bCW.setVisibility(4);
        }
        this.zzacv = zzjnVar;
        this.zzacp = str;
        this.zzrt = context;
        this.zzacr = zzangVar;
        this.bCV = new zzci(new zzag(this));
        this.zzadz = new zzamj(200L);
        this.bDg = new android.support.v4.e.r<>();
    }

    private final void zzf(boolean z) {
        View viewFindViewById;
        if (this.bCW == null || this.zzacw == null || this.zzacw.zzbyo == null || this.zzacw.zzbyo.zzuf() == null) {
            return;
        }
        if (!z || this.zzadz.tryAcquire()) {
            if (this.zzacw.zzbyo.zzuf().zzfz()) {
                int[] iArr = new int[2];
                this.bCW.getLocationOnScreen(iArr);
                zzkb.zzif();
                int iZzb = zzamu.zzb(this.zzrt, iArr[0]);
                zzkb.zzif();
                int iZzb2 = zzamu.zzb(this.zzrt, iArr[1]);
                if (iZzb != this.zzadx || iZzb2 != this.zzady) {
                    this.zzadx = iZzb;
                    this.zzady = iZzb2;
                    this.zzacw.zzbyo.zzuf().zza(this.zzadx, this.zzady, z ? false : true);
                }
            }
            if (this.bCW == null || (viewFindViewById = this.bCW.getRootView().findViewById(R.id.content)) == null) {
                return;
            }
            Rect rect = new Rect();
            Rect rect2 = new Rect();
            this.bCW.getGlobalVisibleRect(rect);
            viewFindViewById.getGlobalVisibleRect(rect2);
            if (rect.top != rect2.top) {
                this.bDo = false;
            }
            if (rect.bottom != rect2.bottom) {
                this.bDp = false;
            }
        }
    }

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        zzf(false);
    }

    @Override // android.view.ViewTreeObserver.OnScrollChangedListener
    public final void onScrollChanged() {
        zzf(true);
        this.zzaec = true;
    }

    public final void zza(HashSet<zzajj> hashSet) {
        this.bDn = hashSet;
    }

    public final HashSet<zzajj> zzfl() {
        return this.bDn;
    }

    public final void zzfm() {
        if (this.zzacw == null || this.zzacw.zzbyo == null) {
            return;
        }
        this.zzacw.zzbyo.destroy();
    }

    public final void zzfn() {
        if (this.zzacw == null || this.zzacw.zzbtx == null) {
            return;
        }
        try {
            this.zzacw.zzbtx.destroy();
        } catch (RemoteException e) {
            zzakb.zzdk("Could not destroy mediation adapter.");
        }
    }

    public final boolean zzfo() {
        return this.zzadv == 0;
    }

    public final boolean zzfp() {
        return this.zzadv == 1;
    }

    public final String zzfq() {
        if (this.bDo && this.bDp) {
            return "";
        }
        if (this.bDo) {
            return this.zzaec ? "top-scrollable" : "top-locked";
        }
        if (this.bDp) {
            return this.zzaec ? "bottom-scrollable" : "bottom-locked";
        }
        return "";
    }

    public final void zzg(boolean z) {
        if (this.zzadv == 0 && this.zzacw != null && this.zzacw.zzbyo != null) {
            this.zzacw.zzbyo.stopLoading();
        }
        if (this.zzact != null) {
            this.zzact.cancel();
        }
        if (this.zzacu != null) {
            this.zzacu.cancel();
        }
        if (z) {
            this.zzacw = null;
        }
    }

    final void zzj(View view) {
        zzce zzceVarZzaa;
        if (((Boolean) zzkb.zzik().zzd(zznk.zzbat)).booleanValue() && (zzceVarZzaa = this.bCV.zzaa()) != null) {
            zzceVarZzaa.zzb(view);
        }
    }
}
