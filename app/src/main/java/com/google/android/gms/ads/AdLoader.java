package com.google.android.gms.ads;

import android.content.Context;
import android.os.RemoteException;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeCustomTemplateAd;
import com.google.android.gms.ads.formats.OnPublisherAdViewLoadedListener;
import com.google.android.gms.ads.formats.PublisherAdViewOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.common.internal.bf;
import com.google.android.gms.internal.ads.zzane;
import com.google.android.gms.internal.ads.zzjf;
import com.google.android.gms.internal.ads.zzjm;
import com.google.android.gms.internal.ads.zzjn;
import com.google.android.gms.internal.ads.zzkb;
import com.google.android.gms.internal.ads.zzkk;
import com.google.android.gms.internal.ads.zzkn;
import com.google.android.gms.internal.ads.zzlw;
import com.google.android.gms.internal.ads.zzpl;
import com.google.android.gms.internal.ads.zzrx;
import com.google.android.gms.internal.ads.zzry;
import com.google.android.gms.internal.ads.zzrz;
import com.google.android.gms.internal.ads.zzsa;
import com.google.android.gms.internal.ads.zzsb;
import com.google.android.gms.internal.ads.zzsd;
import com.google.android.gms.internal.ads.zzxm;

/* JADX INFO: loaded from: classes.dex */
public class AdLoader {
    private final zzkk bzn;
    private final Context mContext;
    private final zzjm zzuk;

    public class Builder {
        private final zzkn bzo;
        private final Context mContext;

        private Builder(Context context, zzkn zzknVar) {
            this.mContext = context;
            this.bzo = zzknVar;
        }

        public Builder(Context context, String str) {
            this((Context) bf.d(context, "context cannot be null"), zzkb.zzig().zzb(context, str, new zzxm()));
        }

        public AdLoader build() {
            try {
                return new AdLoader(this.mContext, this.bzo.zzdh());
            } catch (RemoteException e) {
                zzane.zzb("Failed to build AdLoader.", e);
                return null;
            }
        }

        public Builder forAppInstallAd(NativeAppInstallAd.OnAppInstallAdLoadedListener onAppInstallAdLoadedListener) {
            try {
                this.bzo.zza(new zzrx(onAppInstallAdLoadedListener));
            } catch (RemoteException e) {
                zzane.zzc("Failed to add app install ad listener", e);
            }
            return this;
        }

        public Builder forContentAd(NativeContentAd.OnContentAdLoadedListener onContentAdLoadedListener) {
            try {
                this.bzo.zza(new zzry(onContentAdLoadedListener));
            } catch (RemoteException e) {
                zzane.zzc("Failed to add content ad listener", e);
            }
            return this;
        }

        public Builder forCustomTemplateAd(String str, NativeCustomTemplateAd.OnCustomTemplateAdLoadedListener onCustomTemplateAdLoadedListener, NativeCustomTemplateAd.OnCustomClickListener onCustomClickListener) {
            try {
                this.bzo.zza(str, new zzsa(onCustomTemplateAdLoadedListener), onCustomClickListener == null ? null : new zzrz(onCustomClickListener));
            } catch (RemoteException e) {
                zzane.zzc("Failed to add custom template ad listener", e);
            }
            return this;
        }

        public Builder forPublisherAdView(OnPublisherAdViewLoadedListener onPublisherAdViewLoadedListener, AdSize... adSizeArr) {
            if (adSizeArr == null || adSizeArr.length <= 0) {
                throw new IllegalArgumentException("The supported ad sizes must contain at least one valid ad size.");
            }
            try {
                this.bzo.zza(new zzsb(onPublisherAdViewLoadedListener), new zzjn(this.mContext, adSizeArr));
            } catch (RemoteException e) {
                zzane.zzc("Failed to add publisher banner ad listener", e);
            }
            return this;
        }

        public Builder forUnifiedNativeAd(UnifiedNativeAd.OnUnifiedNativeAdLoadedListener onUnifiedNativeAdLoadedListener) {
            try {
                this.bzo.zza(new zzsd(onUnifiedNativeAdLoadedListener));
            } catch (RemoteException e) {
                zzane.zzc("Failed to add google native ad listener", e);
            }
            return this;
        }

        public Builder withAdListener(AdListener adListener) {
            try {
                this.bzo.zzb(new zzjf(adListener));
            } catch (RemoteException e) {
                zzane.zzc("Failed to set AdListener.", e);
            }
            return this;
        }

        public Builder withCorrelator(Correlator correlator) {
            bf.checkNotNull(correlator);
            try {
                this.bzo.zzb(correlator.bzs);
            } catch (RemoteException e) {
                zzane.zzc("Failed to set correlator.", e);
            }
            return this;
        }

        public Builder withNativeAdOptions(NativeAdOptions nativeAdOptions) {
            try {
                this.bzo.zza(new zzpl(nativeAdOptions));
            } catch (RemoteException e) {
                zzane.zzc("Failed to specify native ad options", e);
            }
            return this;
        }

        public Builder withPublisherAdViewOptions(PublisherAdViewOptions publisherAdViewOptions) {
            try {
                this.bzo.zza(publisherAdViewOptions);
            } catch (RemoteException e) {
                zzane.zzc("Failed to specify DFP banner ad options", e);
            }
            return this;
        }
    }

    AdLoader(Context context, zzkk zzkkVar) {
        this(context, zzkkVar, zzjm.zzara);
    }

    private AdLoader(Context context, zzkk zzkkVar, zzjm zzjmVar) {
        this.mContext = context;
        this.bzn = zzkkVar;
        this.zzuk = zzjmVar;
    }

    private final void zza(zzlw zzlwVar) {
        try {
            this.bzn.zzd(zzjm.zza(this.mContext, zzlwVar));
        } catch (RemoteException e) {
            zzane.zzb("Failed to load ad.", e);
        }
    }

    @Deprecated
    public String getMediationAdapterClassName() {
        try {
            return this.bzn.zzck();
        } catch (RemoteException e) {
            zzane.zzc("Failed to get the mediation adapter class name.", e);
            return null;
        }
    }

    public boolean isLoading() {
        try {
            return this.bzn.isLoading();
        } catch (RemoteException e) {
            zzane.zzc("Failed to check if ad is loading.", e);
            return false;
        }
    }

    public void loadAd(AdRequest adRequest) {
        zza(adRequest.zzay());
    }

    public void loadAd(PublisherAdRequest publisherAdRequest) {
        zza(publisherAdRequest.zzay());
    }

    public void loadAds(AdRequest adRequest, int i) {
        try {
            this.bzn.zza(zzjm.zza(this.mContext, adRequest.zzay()), i);
        } catch (RemoteException e) {
            zzane.zzb("Failed to load ads.", e);
        }
    }
}
