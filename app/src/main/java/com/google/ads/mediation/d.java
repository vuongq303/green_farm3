package com.google.ads.mediation;

import android.view.View;
import com.google.android.gms.ads.formats.NativeAdViewHolder;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.mediation.UnifiedNativeAdMapper;
import java.util.Map;

/* JADX INFO: loaded from: classes.dex */
final class d extends UnifiedNativeAdMapper {
    private final UnifiedNativeAd byX;

    public d(UnifiedNativeAd unifiedNativeAd) {
        this.byX = unifiedNativeAd;
        setHeadline(unifiedNativeAd.getHeadline());
        setImages(unifiedNativeAd.getImages());
        setBody(unifiedNativeAd.getBody());
        setIcon(unifiedNativeAd.getIcon());
        setCallToAction(unifiedNativeAd.getCallToAction());
        setAdvertiser(unifiedNativeAd.getAdvertiser());
        setStarRating(unifiedNativeAd.getStarRating());
        setStore(unifiedNativeAd.getStore());
        setPrice(unifiedNativeAd.getPrice());
        zzl(unifiedNativeAd.zzbh());
        setOverrideImpressionRecording(true);
        setOverrideClickHandling(true);
        zza(unifiedNativeAd.getVideoController());
    }

    @Override // com.google.android.gms.ads.mediation.UnifiedNativeAdMapper
    public final void trackViews(View view, Map<String, View> map, Map<String, View> map2) {
        if (view instanceof UnifiedNativeAdView) {
            ((UnifiedNativeAdView) view).setNativeAd(this.byX);
            return;
        }
        NativeAdViewHolder nativeAdViewHolder = NativeAdViewHolder.zzvk.get(view);
        if (nativeAdViewHolder != null) {
            nativeAdViewHolder.setNativeAd(this.byX);
        }
    }
}
