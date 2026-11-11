package com.google.android.gms.internal.ads;

import android.os.Bundle;
import android.os.RemoteException;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;
import com.google.android.gms.c.d;
import com.google.android.gms.common.internal.bf;

/* JADX INFO: loaded from: classes.dex */
@zzadh
public final class zzaif implements MediationRewardedVideoAdListener {
    private final zzaic zzcmj;

    public zzaif(zzaic zzaicVar) {
        this.zzcmj = zzaicVar;
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onAdClicked(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdClicked.");
        try {
            this.zzcmj.zzv(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onAdClosed(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdClosed.");
        try {
            this.zzcmj.zzu(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onAdFailedToLoad(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter, int i) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdFailedToLoad.");
        try {
            this.zzcmj.zzd(d.F(mediationRewardedVideoAdAdapter), i);
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onAdLeftApplication(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdLeftApplication.");
        try {
            this.zzcmj.zzw(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onAdLoaded(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdLoaded.");
        try {
            this.zzcmj.zzr(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onAdOpened(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdOpened.");
        try {
            this.zzcmj.zzs(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onInitializationFailed(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter, int i) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onInitializationFailed.");
        try {
            this.zzcmj.zzc(d.F(mediationRewardedVideoAdAdapter), i);
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onInitializationSucceeded(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onInitializationSucceeded.");
        try {
            this.zzcmj.zzq(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onRewarded(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter, RewardItem rewardItem) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onRewarded.");
        try {
            if (rewardItem != null) {
                this.zzcmj.zza(d.F(mediationRewardedVideoAdAdapter), new zzaig(rewardItem));
            } else {
                this.zzcmj.zza(d.F(mediationRewardedVideoAdAdapter), new zzaig("", 1));
            }
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onVideoCompleted(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onVideoCompleted.");
        try {
            this.zzcmj.zzx(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void onVideoStarted(MediationRewardedVideoAdAdapter mediationRewardedVideoAdAdapter) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onVideoStarted.");
        try {
            this.zzcmj.zzt(d.F(mediationRewardedVideoAdAdapter));
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }

    @Override // com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener
    public final void zzc(Bundle bundle) {
        bf.hV("#008 Must be called on the main UI thread.");
        zzane.zzck("Adapter called onAdMetadataChanged.");
        try {
            this.zzcmj.zzc(bundle);
        } catch (RemoteException e) {
            zzane.zzd("#007 Could not call remote method.", e);
        }
    }
}
