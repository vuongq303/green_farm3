package com.google.android.gms.internal.ads;

import android.content.Context;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.View;
import com.google.android.gms.c.d;
import com.google.android.gms.common.internal.bf;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.ParametersAreNonnullByDefault;

/* JADX INFO: loaded from: classes.dex */
@ParametersAreNonnullByDefault
@zzadh
public final class zzoy extends zzpd {
    private Object mLock;
    private zzxz zzbit;
    private zzyc zzbiu;
    private zzyf zzbiv;
    private final zzpa zzbiw;
    private zzoz zzbix;
    private boolean zzbiy;

    private zzoy(Context context, zzpa zzpaVar, zzci zzciVar, zzpb zzpbVar) {
        super(context, zzpaVar, null, zzciVar, null, zzpbVar, null, null);
        this.zzbiy = false;
        this.mLock = new Object();
        this.zzbiw = zzpaVar;
    }

    public zzoy(Context context, zzpa zzpaVar, zzci zzciVar, zzxz zzxzVar, zzpb zzpbVar) {
        this(context, zzpaVar, zzciVar, zzpbVar);
        this.zzbit = zzxzVar;
    }

    public zzoy(Context context, zzpa zzpaVar, zzci zzciVar, zzyc zzycVar, zzpb zzpbVar) {
        this(context, zzpaVar, zzciVar, zzpbVar);
        this.zzbiu = zzycVar;
    }

    public zzoy(Context context, zzpa zzpaVar, zzci zzciVar, zzyf zzyfVar, zzpb zzpbVar) {
        this(context, zzpaVar, zzciVar, zzpbVar);
        this.zzbiv = zzyfVar;
    }

    private static HashMap<String, View> zzb(Map<String, WeakReference<View>> map) {
        HashMap<String, View> map2 = new HashMap<>();
        if (map == null) {
            return map2;
        }
        synchronized (map) {
            for (Map.Entry<String, WeakReference<View>> entry : map.entrySet()) {
                View view = entry.getValue().get();
                if (view != null) {
                    map2.put(entry.getKey(), view);
                }
            }
        }
        return map2;
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void cancelUnconfirmedClick() {
        synchronized (this.mLock) {
            if (this.zzbix != null) {
                this.zzbix.cancelUnconfirmedClick();
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void setClickConfirmingView(View view) {
        synchronized (this.mLock) {
            if (this.zzbix != null) {
                this.zzbix.setClickConfirmingView(view);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x001a, code lost:
    
        r0 = null;
     */
    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.view.View zza(android.view.View.OnClickListener r5, boolean r6) {
        /*
            r4 = this;
            r1 = 0
            java.lang.Object r2 = r4.mLock
            monitor-enter(r2)
            com.google.android.gms.internal.ads.zzoz r0 = r4.zzbix     // Catch: java.lang.Throwable -> L24
            if (r0 == 0) goto L10
            com.google.android.gms.internal.ads.zzoz r0 = r4.zzbix     // Catch: java.lang.Throwable -> L24
            android.view.View r0 = r0.zza(r5, r6)     // Catch: java.lang.Throwable -> L24
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L24
        Lf:
            return r0
        L10:
            com.google.android.gms.internal.ads.zzyf r0 = r4.zzbiv     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            if (r0 == 0) goto L27
            com.google.android.gms.internal.ads.zzyf r0 = r4.zzbiv     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            com.google.android.gms.c.a r0 = r0.zzmv()     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
        L1a:
            if (r0 == 0) goto L46
            java.lang.Object r0 = com.google.android.gms.c.d.b(r0)     // Catch: java.lang.Throwable -> L24
            android.view.View r0 = (android.view.View) r0     // Catch: java.lang.Throwable -> L24
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L24
            goto Lf
        L24:
            r0 = move-exception
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L24
            throw r0
        L27:
            com.google.android.gms.internal.ads.zzxz r0 = r4.zzbit     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            if (r0 == 0) goto L32
            com.google.android.gms.internal.ads.zzxz r0 = r4.zzbit     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            com.google.android.gms.c.a r0 = r0.zzmv()     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            goto L1a
        L32:
            com.google.android.gms.internal.ads.zzyc r0 = r4.zzbiu     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            if (r0 == 0) goto L44
            com.google.android.gms.internal.ads.zzyc r0 = r4.zzbiu     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            com.google.android.gms.c.a r0 = r0.zzmv()     // Catch: java.lang.Throwable -> L24 android.os.RemoteException -> L3d
            goto L1a
        L3d:
            r0 = move-exception
            java.lang.String r3 = "Failed to call getAdChoicesContent"
            com.google.android.gms.internal.ads.zzakb.zzc(r3, r0)     // Catch: java.lang.Throwable -> L24
        L44:
            r0 = r1
            goto L1a
        L46:
            monitor-exit(r2)     // Catch: java.lang.Throwable -> L24
            r0 = r1
            goto Lf
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.ads.zzoy.zza(android.view.View$OnClickListener, boolean):android.view.View");
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zza(View view, Map<String, WeakReference<View>> map) {
        bf.hV("recordImpression must be called on the main UI thread.");
        synchronized (this.mLock) {
            this.zzbjd = true;
            if (this.zzbix != null) {
                this.zzbix.zza(view, map);
                this.zzbiw.recordImpression();
            } else {
                try {
                    if (this.zzbiv != null && !this.zzbiv.getOverrideImpressionRecording()) {
                        this.zzbiv.recordImpression();
                        this.zzbiw.recordImpression();
                    } else if (this.zzbit != null && !this.zzbit.getOverrideImpressionRecording()) {
                        this.zzbit.recordImpression();
                        this.zzbiw.recordImpression();
                    } else if (this.zzbiu != null && !this.zzbiu.getOverrideImpressionRecording()) {
                        this.zzbiu.recordImpression();
                        this.zzbiw.recordImpression();
                    }
                } catch (RemoteException e) {
                    zzakb.zzc("Failed to call recordImpression", e);
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zza(View view, Map<String, WeakReference<View>> map, Bundle bundle, View view2) {
        bf.hV("performClick must be called on the main UI thread.");
        synchronized (this.mLock) {
            if (this.zzbix != null) {
                this.zzbix.zza(view, map, bundle, view2);
                this.zzbiw.onAdClicked();
            } else {
                try {
                    if (this.zzbiv != null && !this.zzbiv.getOverrideClickHandling()) {
                        this.zzbiv.zzj(d.F(view));
                        this.zzbiw.onAdClicked();
                    } else if (this.zzbit != null && !this.zzbit.getOverrideClickHandling()) {
                        this.zzbit.zzj(d.F(view));
                        this.zzbiw.onAdClicked();
                    } else if (this.zzbiu != null && !this.zzbiu.getOverrideClickHandling()) {
                        this.zzbiu.zzj(d.F(view));
                        this.zzbiw.onAdClicked();
                    }
                } catch (RemoteException e) {
                    zzakb.zzc("Failed to call performClick", e);
                }
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd
    public final void zza(View view, Map<String, WeakReference<View>> map, Map<String, WeakReference<View>> map2, View.OnTouchListener onTouchListener, View.OnClickListener onClickListener) {
        synchronized (this.mLock) {
            this.zzbiy = true;
            HashMap<String, View> mapZzb = zzb(map);
            HashMap<String, View> mapZzb2 = zzb(map2);
            try {
                if (this.zzbiv != null) {
                    this.zzbiv.zzb(d.F(view), d.F(mapZzb), d.F(mapZzb2));
                } else if (this.zzbit != null) {
                    this.zzbit.zzb(d.F(view), d.F(mapZzb), d.F(mapZzb2));
                    this.zzbit.zzk(d.F(view));
                } else if (this.zzbiu != null) {
                    this.zzbiu.zzb(d.F(view), d.F(mapZzb), d.F(mapZzb2));
                    this.zzbiu.zzk(d.F(view));
                }
            } catch (RemoteException e) {
                zzakb.zzc("Failed to call prepareAd", e);
            }
            this.zzbiy = false;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zza(zzro zzroVar) {
        synchronized (this.mLock) {
            if (this.zzbix != null) {
                this.zzbix.zza(zzroVar);
            }
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zzb(View view, Map<String, WeakReference<View>> map) {
        synchronized (this.mLock) {
            try {
                if (this.zzbiv != null) {
                    this.zzbiv.zzl(d.F(view));
                } else if (this.zzbit != null) {
                    this.zzbit.zzl(d.F(view));
                } else if (this.zzbiu != null) {
                    this.zzbiu.zzl(d.F(view));
                }
            } catch (RemoteException e) {
                zzakb.zzc("Failed to call untrackView", e);
            }
        }
    }

    public final void zzc(zzoz zzozVar) {
        synchronized (this.mLock) {
            this.zzbix = zzozVar;
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zzcr() {
        if (this.zzbix != null) {
            this.zzbix.zzcr();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zzcs() {
        if (this.zzbix != null) {
            this.zzbix.zzcs();
        }
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final boolean zzkj() {
        boolean zZzkj;
        synchronized (this.mLock) {
            zZzkj = this.zzbix != null ? this.zzbix.zzkj() : this.zzbiw.zzcu();
        }
        return zZzkj;
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final boolean zzkk() {
        boolean zZzkk;
        synchronized (this.mLock) {
            zZzkk = this.zzbix != null ? this.zzbix.zzkk() : this.zzbiw.zzcv();
        }
        return zZzkk;
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zzkl() {
        bf.hV("recordDownloadedImpression must be called on main UI thread.");
        synchronized (this.mLock) {
            this.zzbje = true;
            if (this.zzbix != null) {
                this.zzbix.zzkl();
            }
        }
    }

    public final boolean zzkm() {
        boolean z;
        synchronized (this.mLock) {
            z = this.zzbiy;
        }
        return z;
    }

    public final zzoz zzkn() {
        zzoz zzozVar;
        synchronized (this.mLock) {
            zzozVar = this.zzbix;
        }
        return zzozVar;
    }

    @Override // com.google.android.gms.internal.ads.zzpd
    public final zzaqw zzko() {
        return null;
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zzkp() {
    }

    @Override // com.google.android.gms.internal.ads.zzpd, com.google.android.gms.internal.ads.zzoz
    public final void zzkq() {
    }
}
