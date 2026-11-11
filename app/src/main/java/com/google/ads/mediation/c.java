package com.google.ads.mediation;

import android.view.View;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAdViewHolder;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;

/* JADX INFO: loaded from: classes.dex */
final class c extends NativeContentAdMapper {
    private final NativeContentAd byW;

    public c(NativeContentAd nativeContentAd) {
        this.byW = nativeContentAd;
        setHeadline(nativeContentAd.getHeadline().toString());
        setImages(nativeContentAd.getImages());
        setBody(nativeContentAd.getBody().toString());
        if (nativeContentAd.getLogo() != null) {
            setLogo(nativeContentAd.getLogo());
        }
        setCallToAction(nativeContentAd.getCallToAction().toString());
        setAdvertiser(nativeContentAd.getAdvertiser().toString());
        setOverrideImpressionRecording(true);
        setOverrideClickHandling(true);
        zza(nativeContentAd.getVideoController());
    }

    @Override // com.google.android.gms.ads.mediation.NativeAdMapper
    public final void trackView(View view) {
        if (view instanceof NativeAdView) {
            ((NativeAdView) view).setNativeAd(this.byW);
        }
        NativeAdViewHolder nativeAdViewHolder = NativeAdViewHolder.zzvk.get(view);
        if (nativeAdViewHolder != null) {
            nativeAdViewHolder.setNativeAd(this.byW);
        }
    }
}
