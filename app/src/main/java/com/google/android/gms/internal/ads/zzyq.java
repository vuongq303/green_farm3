package com.google.android.gms.internal.ads;

import android.os.RemoteException;
import com.google.ads.b;
import com.google.ads.mediation.j;
import com.google.ads.mediation.k;
import com.google.ads.mediation.l;
import com.google.ads.mediation.m;
import com.google.ads.mediation.n;
import com.google.ads.mediation.q;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzyq<NETWORK_EXTRAS extends q, SERVER_PARAMETERS extends n> implements k, m {
    private final zzxt zzbuu;

    public zzyq(zzxt zzxtVar) {
        this.zzbuu = zzxtVar;
    }

    public final void onClick(j<?, ?> jVar) {
        zzane.zzck("Adapter called onClick.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyr(this));
        } else {
            try {
                this.zzbuu.onAdClicked();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onDismissScreen(j<?, ?> jVar) {
        zzane.zzck("Adapter called onDismissScreen.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzdk("#008 Must be called on the main UI thread.");
            zzamu.zzsy.post(new zzyu(this));
        } else {
            try {
                this.zzbuu.onAdClosed();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onDismissScreen(l<?, ?> lVar) {
        zzane.zzck("Adapter called onDismissScreen.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyz(this));
        } else {
            try {
                this.zzbuu.onAdClosed();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.k
    public final void onFailedToReceiveAd(j<?, ?> jVar, b bVar) {
        String strValueOf = String.valueOf(bVar);
        zzane.zzck(new StringBuilder(String.valueOf(strValueOf).length() + 47).append("Adapter called onFailedToReceiveAd with error. ").append(strValueOf).toString());
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyv(this, bVar));
        } else {
            try {
                this.zzbuu.onAdFailedToLoad(zzzc.zza(bVar));
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    @Override // com.google.ads.mediation.m
    public final void onFailedToReceiveAd(l<?, ?> lVar, b bVar) {
        String strValueOf = String.valueOf(bVar);
        zzane.zzck(new StringBuilder(String.valueOf(strValueOf).length() + 47).append("Adapter called onFailedToReceiveAd with error ").append(strValueOf).append(".").toString());
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzza(this, bVar));
        } else {
            try {
                this.zzbuu.onAdFailedToLoad(zzzc.zza(bVar));
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onLeaveApplication(j<?, ?> jVar) {
        zzane.zzck("Adapter called onLeaveApplication.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyw(this));
        } else {
            try {
                this.zzbuu.onAdLeftApplication();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onLeaveApplication(l<?, ?> lVar) {
        zzane.zzck("Adapter called onLeaveApplication.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzzb(this));
        } else {
            try {
                this.zzbuu.onAdLeftApplication();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onPresentScreen(j<?, ?> jVar) {
        zzane.zzck("Adapter called onPresentScreen.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyx(this));
        } else {
            try {
                this.zzbuu.onAdOpened();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onPresentScreen(l<?, ?> lVar) {
        zzane.zzck("Adapter called onPresentScreen.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzys(this));
        } else {
            try {
                this.zzbuu.onAdOpened();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onReceivedAd(j<?, ?> jVar) {
        zzane.zzck("Adapter called onReceivedAd.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyy(this));
        } else {
            try {
                this.zzbuu.onAdLoaded();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }

    public final void onReceivedAd(l<?, ?> lVar) {
        zzane.zzck("Adapter called onReceivedAd.");
        zzkb.zzif();
        if (!zzamu.zzsh()) {
            zzane.zzd("#008 Must be called on the main UI thread.", null);
            zzamu.zzsy.post(new zzyt(this));
        } else {
            try {
                this.zzbuu.onAdLoaded();
            } catch (RemoteException e) {
                zzane.zzd("#007 Could not call remote method.", e);
            }
        }
    }
}
